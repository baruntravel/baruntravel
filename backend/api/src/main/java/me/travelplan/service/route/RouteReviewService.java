package me.travelplan.service.route;

import lombok.RequiredArgsConstructor;
import me.travelplan.exception.PermissionDeniedException;
import me.travelplan.service.file.component.S3Uploader;
import me.travelplan.service.file.domain.File;
import me.travelplan.service.file.repository.FileRepository;
import me.travelplan.service.route.domain.Route;
import me.travelplan.service.route.domain.RouteReview;
import me.travelplan.service.route.domain.RouteReviewFile;
import me.travelplan.service.route.domain.RouteReviewLike;
import me.travelplan.service.route.exception.RouteNotFoundException;
import me.travelplan.service.route.exception.RouteReviewNotFoundException;
import me.travelplan.service.route.repository.RouteRepository;
import me.travelplan.service.route.repository.RouteReviewFileRepository;
import me.travelplan.service.route.repository.RouteReviewLikeRepository;
import me.travelplan.service.route.repository.RouteReviewRepository;
import me.travelplan.service.user.domain.User;
import me.travelplan.web.common.SavedFile;
import me.travelplan.web.route.RouteRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
class RouteReviewService {
    private final RouteRepository routeRepository;
    private final RouteReviewRepository routeReviewRepository;
    private final RouteReviewLikeRepository routeReviewLikeRepository;
    private final FileRepository fileRepository;
    private final RouteReviewFileRepository routeReviewFileRepository;
    private final S3Uploader fileService;

    public void create(RouteRequest.CreateOrUpdateReview request, Long id) {
        Route route = routeRepository.findById(id).orElseThrow(RouteNotFoundException::new);
        List<SavedFile> files = s3FileUpload(request);
        List<File> fileList = fileRepository.saveAll(files.stream().map(File::create).collect(Collectors.toList()));
        List<RouteReviewFile> routeReviewFiles = fileList.stream().map(RouteReviewFile::create).collect(Collectors.toList());
        RouteReview routeReview = RouteReview.create(request.getScore(), request.getContent(), routeReviewFiles, route);

        routeReviewRepository.save(routeReview);
    }

    @Transactional(readOnly = true)
    public List<RouteReview> getList(Long id) {
        return routeReviewRepository.findAllByRouteId(id);
    }

    public RouteReview update(Long id, RouteRequest.CreateOrUpdateReview request, User user) {
        RouteReview routeReview = routeReviewRepository.findById(id).orElseThrow(RouteReviewNotFoundException::new);
        if (!routeReview.getCreatedBy().getId().equals(user.getId())) {
            throw new PermissionDeniedException("수정할 권한이 없습니다.");
        }

        List<Long> fileIdList = routeReview.getRouteReviewFiles().stream()
                .map(routeReviewFile -> routeReviewFile.getFile().getId())
                .collect(Collectors.toList());
        // 엔티티 하나하나 삭제하며 delete 쿼리가 나가는 것을 방지하기 위해 in 쿼리사용
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

    public void delete(Long id, User user) {
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

    public void createOrDeleteReviewLike(Long id, User user) {
        RouteReview route = routeReviewRepository.findById(id).orElseThrow(RouteReviewNotFoundException::new);
        Optional<RouteReviewLike> optionalRouteReviewLike = routeReviewLikeRepository.findByRouteReviewIdAndCreatedBy(id, user);
        if (optionalRouteReviewLike.isEmpty()) {
            routeReviewLikeRepository.save(RouteReviewLike.create(route));
        }
        if (optionalRouteReviewLike.isPresent()) {
            RouteReviewLike routeLike = optionalRouteReviewLike.get();
            routeReviewLikeRepository.delete(routeLike);
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
