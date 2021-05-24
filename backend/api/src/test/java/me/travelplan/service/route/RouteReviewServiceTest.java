package me.travelplan.service.route;

import me.travelplan.exception.PermissionDeniedException;
import me.travelplan.service.file.FileService;
import me.travelplan.service.file.domain.File;
import me.travelplan.service.route.domain.Route;
import me.travelplan.service.route.domain.RouteReview;
import me.travelplan.service.route.domain.RouteReviewFile;
import me.travelplan.service.route.domain.RouteReviewLike;
import me.travelplan.service.route.exception.RouteReviewNotFoundException;
import me.travelplan.service.route.repository.RouteRepository;
import me.travelplan.service.route.repository.RouteReviewFileRepository;
import me.travelplan.service.route.repository.RouteReviewLikeRepository;
import me.travelplan.service.route.repository.RouteReviewRepository;
import me.travelplan.service.user.domain.User;
import me.travelplan.web.route.review.dto.RouteReviewRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.InputStream;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RouteReviewServiceTest {
    @InjectMocks
    private RouteReviewService routeReviewService;
    @Mock
    private RouteRepository routeRepository;
    @Mock
    private RouteReviewRepository routeReviewRepository;
    @Mock
    private RouteReviewFileRepository routeReviewFileRepository;
    @Mock
    private RouteReviewLikeRepository routeReviewLikeRepository;
    @Mock
    private FileService fileService;

    @Test
    @DisplayName("경로 리뷰 생성 성공")
    public void create() throws Exception {
        InputStream is = new ClassPathResource("mock/images/enjoy2.png").getInputStream();
        MockMultipartFile mockFile = new MockMultipartFile("updateFile", "mock_file1.jpg", "image/jpg", is.readAllBytes());

        RouteReviewRequest.CreateOrUpdateReview request = RouteReviewRequest.CreateOrUpdateReview.builder()
                .content("테스트 리뷰의 내용입니다.")
                .score(5.0)
                .files(Collections.singletonList(mockFile))
                .build();

        given(routeRepository.findById(1L)).willReturn(Optional.of(Route.builder().build()));
        given(fileService.uploadFiles(any())).willReturn(Collections.singletonList(File.builder().build()));

        routeReviewService.create(request, 1L);

        verify(routeReviewRepository).save(any());
    }

    @Test
    @DisplayName("경로 리뷰 목록 조회 성공")
    public void getList() {
        given(routeReviewRepository.findAllByRouteId(1L)).willReturn(Collections.singletonList(RouteReview.builder().build()));

        routeReviewService.getList(1L);

        verify(routeReviewRepository).findAllByRouteId(1L);
    }

    @Test
    @DisplayName("경로 리뷰 수정 성공")
    public void update() throws Exception {
        User user = createUser(1L, "test@test.com");
        RouteReview routeReview = createReview(user);
        InputStream is = new ClassPathResource("mock/images/enjoy2.png").getInputStream();
        MockMultipartFile mockFile = new MockMultipartFile("updateFile", "mock_file1.jpg", "image/jpg", is.readAllBytes());

        RouteReviewRequest.CreateOrUpdateReview request = RouteReviewRequest.CreateOrUpdateReview.builder()
                .content("테스트 리뷰 수정")
                .score(3.5)
                .files(Collections.singletonList(mockFile))
                .build();

        given(routeReviewRepository.findById(1L)).willReturn(Optional.of(routeReview));

        routeReviewService.update(1L, request, user);

        assertEquals(request.getScore(), routeReview.getScore());
        assertEquals(request.getContent(), routeReview.getContent());
        assertEquals(request.getFiles().size(), routeReview.getRouteReviewFiles().size());
    }

    @Test
    @DisplayName("예외 테스트: 경로 리뷰의 작성자가 아닌 사람이 수정할 경우 예외 발생")
    public void updatePermissionException() {
        User user = createUser(1L, "test@test.com");
        User exceptionUser = createUser(2L, "test2@test.com");
        RouteReview routeReview = createReview(user);

        RouteReviewRequest.CreateOrUpdateReview request = RouteReviewRequest.CreateOrUpdateReview.builder()
                .content("테스트 리뷰 수정")
                .score(3.5)
                .build();

        given(routeReviewRepository.findById(1L)).willReturn(Optional.of(routeReview));

        assertThrows(PermissionDeniedException.class, () -> routeReviewService.update(1L, request, exceptionUser));
    }

    @Test
    @DisplayName("예외 테스트: 없는 경로 리뷰를 수정하려고 할 경우 예외 발생")
    public void updateNotFound() {
        User user = createUser(1L, "test@test.com");
        RouteReviewRequest.CreateOrUpdateReview request = RouteReviewRequest.CreateOrUpdateReview.builder()
                .content("테스트 리뷰 수정")
                .score(3.5)
                .build();

        given(routeReviewRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(RouteReviewNotFoundException.class, () -> routeReviewService.update(1L, request, user));
    }

    @Test
    @DisplayName("경로 리뷰 삭제 성공")
    public void delete() {
        User user = createUser(1L, "test@test.com");
        RouteReview routeReview = createReview(user);

        given(routeReviewRepository.findById(1L)).willReturn(Optional.of(routeReview));

        routeReviewService.delete(1L, user);

        verify(routeReviewFileRepository).deleteAllByFileIds(any());
        verify(fileService).deleteById(any());
        verify(routeReviewRepository).deleteById(1L);
    }

    @Test
    @DisplayName("예외 테스트: 없는 경로 리뷰를 삭제하려고 할 경우 예외 발생")
    public void deleteNotFound() {
        User user = createUser(1L, "test@test.com");

        given(routeReviewRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(RouteReviewNotFoundException.class, () -> routeReviewService.delete(1L, user));
    }

    @Test
    @DisplayName("예외 테스트: 경로 리뷰 작성자가 아닌 사람이 삭제할 경우 예외 발생")
    public void deletePermissionException() {
        User user = createUser(1L, "test@test.com");
        User exceptionUser = createUser(2L, "test2@test.com");
        RouteReview routeReview = createReview(user);

        given(routeReviewRepository.findById(1L)).willReturn(Optional.of(routeReview));

        assertThrows(PermissionDeniedException.class, () -> routeReviewService.delete(1L, exceptionUser));
    }

    @Test
    @DisplayName("경로 리뷰 좋아요 생성 성공")
    public void createOrDeleteLike() {
        User user = createUser(1L, "test@test.com");

        given(routeReviewRepository.findById(1L)).willReturn(Optional.of(RouteReview.builder().build()));
        given(routeReviewLikeRepository.findByRouteReviewIdAndCreatedBy(any(), any())).willReturn(Optional.empty());

        routeReviewService.createOrDeleteLike(1L, user);

        verify(routeReviewLikeRepository).save(any());
    }

    @Test
    @DisplayName("경로 리뷰 좋아요 삭제 성공")
    public void DeleteLike() {
        User user = createUser(1L, "test@test.com");

        given(routeReviewRepository.findById(1L)).willReturn(Optional.of(RouteReview.builder().build()));
        given(routeReviewLikeRepository.findByRouteReviewIdAndCreatedBy(any(), any())).willReturn(Optional.of(RouteReviewLike.builder().build()));

        routeReviewService.createOrDeleteLike(1L, user);

        verify(routeReviewLikeRepository).delete(any());
    }

    @Test
    @DisplayName("예외테스트: 없는 경로를 좋아요하면 예외 발생")
    public void DeleteLikeNotFoundRoute() {
        User user = createUser(1L, "test@test.com");

        given(routeReviewRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(RouteReviewNotFoundException.class, () -> routeReviewService.createOrDeleteLike(1L, user));
    }

    private RouteReview createReview(User user) {
        RouteReview routeReview = RouteReview.builder()
                .id(1L)
                .content("경로 리뷰 생성")
                .score(5.0)
                .routeReviewFiles(Collections.singletonList(RouteReviewFile.builder().file(File.builder().name("file").build()).build()))
                .build();
        routeReview.setCreatedBy(user);
        return routeReview;
    }

    private User createUser(Long id, String email) {
        return User.builder()
                .id(id)
                .name("테스터")
                .email(email)
                .password("123456")
                .avatar(File.builder().name("files").build())
                .build();
    }
}