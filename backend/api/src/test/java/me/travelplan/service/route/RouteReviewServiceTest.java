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
import me.travelplan.web.common.PageDto;
import me.travelplan.web.route.review.dto.RouteReviewRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.mock.web.MockMultipartFile;

import java.io.InputStream;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
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

    private User user;
    private User exceptionUser;
    private Route route;
    private File file;
    private RouteReview review;

    @BeforeEach
    public void setUp() {
        user = User.builder().id(1L).name("?????????").email("test@test.com").password("123456").avatar(File.builder().name("files").build()).build();
        exceptionUser = User.builder().id(2L).name("?????????").email("exceptionUser@test.com").password("123456").avatar(File.builder().name("files").build()).build();
        route = Route.builder().id(1L).name("????????? ??????").build();
        review = RouteReview.builder().content("????????? ???????????????~~").score(5.0).routeReviewFiles(Collections.singletonList(RouteReviewFile.builder().file(File.builder().name("file").build()).build())).build();
        review.setCreatedBy(user);
        file = File.builder().url("testfile").build();
    }

    @Test
    @DisplayName("?????? ?????? ?????? ??????")
    public void create() throws Exception {
        InputStream is = new ClassPathResource("mock/images/enjoy2.png").getInputStream();
        MockMultipartFile mockFile = new MockMultipartFile("updateFile", "mock_file1.jpg", "image/jpg", is.readAllBytes());

        RouteReviewRequest.Create request = RouteReviewRequest.Create.builder()
                .content("????????? ????????? ???????????????.")
                .score(5.0)
                .files(Collections.singletonList(mockFile))
                .build();

        given(routeRepository.findById(1L)).willReturn(Optional.of(route));
        given(fileService.uploadFiles(any())).willReturn(Collections.singletonList(file));

        routeReviewService.create(request, 1L);

        verify(routeReviewRepository).save(any());
    }

    @Test
    @DisplayName("?????? ?????? ?????? ?????? ??????")
    public void getList() {
        PageDto pageDto = new PageDto(1, 10);
        Page<RouteReview> page = new PageImpl<>(IntStream.range(0, 2).mapToObj(i -> RouteReview.builder().build()).collect(Collectors.toList()), pageDto.of(), 2);

        given(routeReviewRepository.findAllByRouteId(any(), any(), any())).willReturn(page);

        routeReviewService.getList(1L, pageDto);

        verify(routeReviewRepository).findAllByRouteId(any(), any(), any());
    }

    @Test
    @DisplayName("?????? ?????? ?????? ??????(?????? ??????)")
    public void updateWithFile() throws Exception {
        InputStream is = new ClassPathResource("mock/images/enjoy2.png").getInputStream();
        MockMultipartFile mockFile = new MockMultipartFile("updateFile", "mock_file1.jpg", "image/jpg", is.readAllBytes());

        RouteReviewRequest.Update request = RouteReviewRequest.Update.builder()
                .content("????????? ?????? ??????")
                .score(3.5)
                .files(Collections.singletonList(mockFile))
                .fileChange(true)
                .build();

        given(routeReviewRepository.findById(1L)).willReturn(Optional.of(review));

        routeReviewService.update(1L, request, user);

        verify(routeReviewFileRepository).deleteAllByFileIds(any());
        verify(routeReviewFileRepository).saveAll(any());

        assertEquals(request.getScore(), review.getScore());
        assertEquals(request.getContent(), review.getContent());
        assertEquals(request.getFiles().size(), review.getRouteReviewFiles().size());
    }

    @Test
    @DisplayName("?????? ?????? ?????? ??????(?????? ?????? X)")
    public void updateWithOutFile() {
        RouteReviewRequest.Update request = RouteReviewRequest.Update.builder()
                .content("????????? ?????? ??????")
                .score(3.5)
                .files(Collections.emptyList())
                .fileChange(false)
                .build();

        given(routeReviewRepository.findById(1L)).willReturn(Optional.of(review));

        routeReviewService.update(1L, request, user);

        verify(routeReviewFileRepository, times(0)).deleteAllByFileIds(any());
        verify(routeReviewFileRepository, times(0)).saveAll(any());

        assertEquals(request.getScore(), review.getScore());
        assertEquals(request.getContent(), review.getContent());
    }

    @Test
    @DisplayName("?????? ?????????: ?????? ????????? ???????????? ?????? ????????? ????????? ?????? ?????? ??????")
    public void updatePermissionException() {
        RouteReviewRequest.Update request = RouteReviewRequest.Update.builder()
                .content("????????? ?????? ??????")
                .score(3.5)
                .build();

        given(routeReviewRepository.findById(1L)).willReturn(Optional.of(review));

        assertThrows(PermissionDeniedException.class, () -> routeReviewService.update(1L, request, exceptionUser));
    }

    @Test
    @DisplayName("?????? ?????????: ?????? ?????? ????????? ??????????????? ??? ?????? ?????? ??????")
    public void updateNotFound() {
        RouteReviewRequest.Update request = RouteReviewRequest.Update.builder()
                .content("????????? ?????? ??????")
                .score(3.5)
                .build();

        given(routeReviewRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(RouteReviewNotFoundException.class, () -> routeReviewService.update(1L, request, user));
    }

    @Test
    @DisplayName("?????? ?????? ?????? ??????")
    public void delete() {
        given(routeReviewRepository.findById(1L)).willReturn(Optional.of(review));

        routeReviewService.delete(1L, user);

        verify(routeReviewFileRepository).deleteAllByFileIds(any());
        verify(fileService).deleteById(any());
        verify(routeReviewRepository).deleteById(1L);
    }

    @Test
    @DisplayName("?????? ?????????: ?????? ?????? ????????? ??????????????? ??? ?????? ?????? ??????")
    public void deleteNotFound() {
        given(routeReviewRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(RouteReviewNotFoundException.class, () -> routeReviewService.delete(1L, user));
    }

    @Test
    @DisplayName("?????? ?????????: ?????? ?????? ???????????? ?????? ????????? ????????? ?????? ?????? ??????")
    public void deletePermissionException() {
        given(routeReviewRepository.findById(1L)).willReturn(Optional.of(review));

        assertThrows(PermissionDeniedException.class, () -> routeReviewService.delete(1L, exceptionUser));
    }

    @Test
    @DisplayName("?????? ?????? ????????? ?????? ??????")
    public void createLike() {
        given(routeReviewRepository.findById(1L)).willReturn(Optional.of(review));
        given(routeReviewLikeRepository.findByRouteReviewIdAndCreatedBy(any(), any())).willReturn(Optional.empty());

        routeReviewService.createOrDeleteLike(1L, user);

        verify(routeReviewLikeRepository).save(any());
    }

    @Test
    @DisplayName("?????? ?????? ????????? ?????? ??????")
    public void deleteLike() {
        given(routeReviewRepository.findById(1L)).willReturn(Optional.of(review));
        given(routeReviewLikeRepository.findByRouteReviewIdAndCreatedBy(any(), any())).willReturn(Optional.of(RouteReviewLike.builder().build()));

        routeReviewService.createOrDeleteLike(1L, user);

        verify(routeReviewLikeRepository).delete(any());
    }

    @Test
    @DisplayName("???????????????: ?????? ????????? ??????????????? ?????? ??????")
    public void createOrDeleteLikeNotFoundRouteReview() {
        given(routeReviewRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(RouteReviewNotFoundException.class, () -> routeReviewService.createOrDeleteLike(1L, user));
    }
}