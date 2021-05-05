package me.travelplan.service.route;

import lombok.AllArgsConstructor;
import me.travelplan.service.exception.PermissionDeniedException;
import me.travelplan.service.file.File;
import me.travelplan.service.file.FileRepository;
import me.travelplan.service.file.FileS3Uploader;
import me.travelplan.service.place.Place;
import me.travelplan.service.place.PlaceRepository;
import me.travelplan.service.place.exception.PlaceNotFoundException;
import me.travelplan.service.route.exception.RouteNotFoundException;
import me.travelplan.service.route.exception.RouteReviewNotFoundException;
import me.travelplan.service.user.User;
import me.travelplan.web.common.SavedFile;
import me.travelplan.web.route.RouteRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private final RouteLikeRepository routeLikeRepository;

    @Transactional
    public Route createEmpty(Route route) {
        return routeRepository.save(route);
    }

    public List<Route> getByUser(User user) {
        return routeRepository.findByCreatedBy(user);
    }

    @Transactional
    public Route create(RouteRequest.Create request) {
        List<RoutePlace> routePlaces = request.getPlaces().stream()
                .map(place -> {
                    Place findPlace = placeRepository.findById(place.getId()).orElseThrow(PlaceNotFoundException::new);
                    return RoutePlace.create(findPlace, place.getOrder());
                }).collect(Collectors.toList());
        Route route = Route.create(request.getName(), routePlaces);
        route.calculateCoordinate(routePlaces);

        return routeRepository.save(route);
    }

    @Transactional
    public Route update(Route route) {
        routePlaceRepository.deleteAllByRoute(route);
        fileRepository.saveAll(route.getRoutePlaces().stream().map(RoutePlace::getPlace).map(Place::getThumbnail).collect(Collectors.toList()));
        placeRepository.saveAll(route.getRoutePlaces().stream().map(RoutePlace::getPlace).collect(Collectors.toList()));
        return routeRepository.save(route);
    }

    public Route getOne(Long id) {
        return routeRepository.findById(id).orElseThrow(RouteNotFoundException::new);
    }

    @Transactional
    public Route addPlace(Long id, Place place) {
        Route route = routeRepository.findById(id).orElseThrow(RouteNotFoundException::new);
        fileRepository.save(place.getThumbnail());
        route.addPlace(RoutePlace.builder().order(0).route(route).place(place).build());
        route.calculateCoordinate(route.getRoutePlaces());
        return routeRepository.save(route);
    }

    public Page<Route> getList(RouteRequest.GetList request, Pageable pageable) {
        return routeQueryRepository.findAllByCoordinate(request.getMaxX(), request.getMinX(), request.getMaxY(), request.getMinY(), pageable);
    }

    @Transactional
    public void createReview(RouteRequest.CreateOrUpdateReview request, Long id) {
        Route route = routeRepository.findById(id).orElseThrow(RouteNotFoundException::new);
        List<SavedFile> files = s3FileUpload(request);
        List<File> fileList = fileRepository.saveAll(files.stream().map(File::create).collect(Collectors.toList()));
        List<RouteReviewFile> routeReviewFiles = fileList.stream().map(RouteReviewFile::create).collect(Collectors.toList());
        RouteReview routeReview = RouteReview.create(request.getScore(), request.getContent(), routeReviewFiles, route);

        routeReviewRepository.save(routeReview);
    }

    @Transactional
    public RouteReview updateReview(Long id, RouteRequest.CreateOrUpdateReview request, User user) {
        RouteReview routeReview = routeReviewRepository.findById(id).orElseThrow(RouteReviewNotFoundException::new);
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
        RouteReview routeReview = routeReviewRepository.findById(id).orElseThrow(RouteReviewNotFoundException::new);
        if (!routeReview.getCreatedBy().getId().equals(user.getId())) {
            throw new PermissionDeniedException("삭제할 권한이 없습니다.");
        }
        List<Long> fileIdList = routeReview.getRouteReviewFiles().stream()
                .map(routeReviewFile -> routeReviewFile.getFile().getId())
                .collect(Collectors.toList());

        routeReviewFileRepository.deleteAllByFileIds(fileIdList);
        //softdelete @SQLDelete 를 적용시키기 위해 in 쿼리 대신 건당 삭제하도록 수정
        fileIdList.forEach(fileRepository::deleteById);
        routeReviewRepository.deleteById(id);
    }

    public List<RouteReview> getReviewList(Long id) {
        return routeReviewRepository.findAllByRouteId(id);
    }

    @Transactional
    public void createOrUpdateLike(Long id, User user) {
        Route route = routeRepository.findById(id).orElseThrow(RouteNotFoundException::new);
        Optional<RouteLike> optionalRouteLike = routeLikeRepository.findByRouteIdAndCreatedBy(id, user);
        if (optionalRouteLike.isEmpty()) {
            routeLikeRepository.save(RouteLike.create(route));
        }
        if (optionalRouteLike.isPresent()) {
            RouteLike routeLike = optionalRouteLike.get();
            routeLikeRepository.delete(routeLike);
        }
    }


    private List<SavedFile> s3FileUpload(RouteRequest.CreateOrUpdateReview request) {
        List<SavedFile> files = new ArrayList<>();
        if (request.getFiles() != null) {
            files = fileService.uploadFileList(request.getFiles());
        }
        return files;
    }
}
