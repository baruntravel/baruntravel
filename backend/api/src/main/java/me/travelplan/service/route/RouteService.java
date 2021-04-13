package me.travelplan.service.route;

import lombok.AllArgsConstructor;
import me.travelplan.service.exception.PermissionDeniedException;
import me.travelplan.service.file.File;
import me.travelplan.service.file.FileRepository;
import me.travelplan.service.file.FileS3Uploader;
import me.travelplan.service.place.Place;
import me.travelplan.service.place.PlaceRepository;
import me.travelplan.service.user.User;
import me.travelplan.web.common.SavedFile;
import me.travelplan.web.route.RouteRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RouteService {
    private final RouteRepository routeRepository;
    private final RoutePlaceRepository routePlaceRepository;
    private final PlaceRepository placeRepository;
    private final FileRepository fileRepository;
    private final RouteQueryRepository routeQueryRepository;
    private final RouteReviewRepository routeReviewRepository;
    private final RouteReviewFileRepository routeReviewFileRepository;
    private final FileS3Uploader fileService;


    @Transactional
    public Route createEmpty(Route route) {
        return routeRepository.save(route);
    }

    public List<Route> getByUser(User user) {
        return routeRepository.findByCreatedBy(user);
    }

    @Transactional
    public Route create(Route route) {
        List<RoutePlace> places = route.getPlaces();
        route.calculateCoordinate(places);

        fileRepository.saveAll(route.getPlaces().stream().map(RoutePlace::getPlace).map(Place::getImage).collect(Collectors.toList()));
        placeRepository.saveAll(route.getPlaces().stream().map(RoutePlace::getPlace).collect(Collectors.toList()));
        return routeRepository.save(route);
    }

    @Transactional
    public Route update(Route route) {
        routePlaceRepository.deleteAllByRoute(route);
        fileRepository.saveAll(route.getPlaces().stream().map(RoutePlace::getPlace).map(Place::getImage).collect(Collectors.toList()));
        placeRepository.saveAll(route.getPlaces().stream().map(RoutePlace::getPlace).collect(Collectors.toList()));
        return routeRepository.save(route);
    }

    public Route getOne(Long id) {
        return routeRepository.findById(id).orElseThrow(() -> new RouteNotFoundException("찾을 수 없는 경로입니다."));
    }

    @Transactional
    public Route addPlace(Long id, Place place) {
        Route route = routeRepository.findById(id).orElseThrow(() -> new RouteNotFoundException("찾을 수 없는 경로입니다."));
        fileRepository.save(place.getImage());
        route.addPlace(RoutePlace.builder().order(0).route(route).place(place).build());
        route.calculateCoordinate(route.getPlaces());
        return routeRepository.save(route);
    }

    public Page<Route> getList(Double maxX, Double minX, Double maxY, Double minY, Pageable pageable) {
        return routeQueryRepository.findAllByCoordinate(maxX, minX, maxY, minY, pageable);
    }

    @Transactional
    public void createReview(RouteRequest.CreateOrUpdateReview request, Long id) {
        //TODO RouteNotFoundException 처리
        Route route = routeRepository.findById(id).orElseThrow(() -> new RouteNotFoundException("찾을 수 없는 경로입니다."));
        List<SavedFile> files = s3FileUpload(request);
        List<File> fileList = fileRepository.saveAll(files.stream().map(File::create).collect(Collectors.toList()));
        List<RouteReviewFile> routeReviewFiles = fileList.stream().map(RouteReviewFile::create).collect(Collectors.toList());
        RouteReview routeReview = RouteReview.create(request.getScore(), request.getContent(), routeReviewFiles, route);

        routeReviewRepository.save(routeReview);
    }

    @Transactional
    public RouteReview updateReview(Long id, RouteRequest.CreateOrUpdateReview request, User user) {
        RouteReview routeReview = routeReviewRepository.findById(id).orElseThrow(() -> new RouteReviewNotFoundException("찾을 수 없는 경로 리뷰입니다."));
        if (!routeReview.getCreatedBy().getId().equals(user.getId())) {
            throw new PermissionDeniedException("수정할 권한이 없습니다.");
        }

        List<Long> fileIdList = routeReview.getRouteReviewFiles().stream()
                .map(routeReviewFile -> routeReviewFile.getFile().getId())
                .collect(Collectors.toList());
        // 엔티티 하나하나 삭제하며 delete 쿼리가 나가는 것을 방지하기 위해 in 쿼리사용
        //TODO 파일전체삭제후다시생성 -> 변경된 파일만 수정하기
        routeReviewFileRepository.deleteAllByFileIds(fileIdList);
        fileRepository.deleteAllByIds(fileIdList);

        List<SavedFile> files = s3FileUpload(request);
        List<File> fileList = fileRepository.saveAll(files.stream().map(File::create).collect(Collectors.toList()));

        List<RouteReviewFile> routeReviewFiles = fileList.stream()
                .map(f -> RouteReviewFile.builder().file(f).routeReview(routeReview).build())
                .collect(Collectors.toList());
        routeReviewFileRepository.saveAll(routeReviewFiles);

        routeReview.update(request.getScore(), request.getContent());

        return routeReview;
    }

    @Transactional
    public void deleteReview(Long id, User user) {
        RouteReview routeReview = routeReviewRepository.findById(id).orElseThrow(() -> new RouteReviewNotFoundException("찾을 수 없는 경로 리뷰입니다."));
        //TODO PermissionDeniedException 처리해줘야함
        if (!routeReview.getCreatedBy().getId().equals(user.getId())) {
            throw new PermissionDeniedException("삭제할 권한이 없습니다.");
        }
        List<Long> fileIdList = routeReview.getRouteReviewFiles().stream()
                .map(routeReviewFile -> routeReviewFile.getFile().getId())
                .collect(Collectors.toList());
        // cacade 옵션을 사용하여 Review가 삭제되었을 때 ReviewFile과 file을 지워주면
        // 삭제 대상들을 전부 조회하는 쿼리가 1번 발생하고
        // 삭제 대상들은 1건씩 삭제되어진다. cascade 옵션으로 연관되어진 엔티티들도 1건씩 삭제가 된다.
        // 이 부분을 In 쿼리로 하면 한번에 삭제가 가능하고 제 대상들을 전부 조회하는 select 쿼리도 적게나간다
        routeReviewFileRepository.deleteAllByFileIds(fileIdList);
        fileRepository.deleteAllByIds(fileIdList);
        routeReviewRepository.deleteById(id);
    }

    private List<SavedFile> s3FileUpload(RouteRequest.CreateOrUpdateReview request) {
        List<SavedFile> files = new ArrayList<>();
        if (request.getFiles() != null) {
            files = fileService.uploadFileList(request.getFiles());
        }
        return files;
    }
}
