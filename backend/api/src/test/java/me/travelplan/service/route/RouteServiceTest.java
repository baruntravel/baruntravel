package me.travelplan.service.route;

import me.travelplan.service.file.domain.File;
import me.travelplan.service.place.domain.Place;
import me.travelplan.service.place.exception.PlaceNotFoundException;
import me.travelplan.service.place.repository.PlaceRepository;
import me.travelplan.service.route.domain.Route;
import me.travelplan.service.route.domain.RouteLike;
import me.travelplan.service.route.exception.RouteNotFoundException;
import me.travelplan.service.route.repository.RouteLikeRepository;
import me.travelplan.service.route.repository.RouteRepository;
import me.travelplan.service.user.domain.User;
import me.travelplan.web.common.PageDto;
import me.travelplan.web.route.dto.RouteDto;
import me.travelplan.web.route.dto.RouteRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RouteServiceTest {
    @InjectMocks
    private RouteService routeService;
    @Mock
    private RouteRepository routeRepository;
    @Mock
    private PlaceRepository placeRepository;
    @Mock
    private RouteLikeRepository routeLikeRepository;

    private Route route;
    private Place place1;
    private Place place2;
    private User user;

    @BeforeEach
    public void setUp() {
        user = User.builder().name("테스터").email("test@test.com").password("123456").avatar(File.builder().name("files").build()).build();
        route = Route.builder().id(1L).name("테스트 경로").build();
        place1 = Place.builder().name("테스트 장소1").x(37.12345).y(123.123).build();
        place2 = Place.builder().name("테스트 장소2").x(37.789426).y(123.486).build();
    }

    @Test
    @DisplayName("내 경로 생성")
    public void create() {
        RouteRequest.Create request = RouteRequest.Create.builder()
                .name("테스트 경로")
                .places(IntStream.range(0, 2).mapToObj(i -> RouteDto.RoutePlaceWithIdAndOrder.builder().id(1L + i).order(i + 1).build()).collect(Collectors.toList()))
                .build();

        given(placeRepository.findById(1L)).willReturn(Optional.of(place1));
        given(placeRepository.findById(2L)).willReturn(Optional.of(place2));

        routeService.create(request);

        verify(placeRepository, times(2)).findById(any());
        verify(routeRepository).save(any());
    }

    @Test
    @DisplayName("예외 테스트: 없는 장소를 경로에 추가하면 예외 발생")
    public void createPlaceNotFound() {
        RouteRequest.Create request = RouteRequest.Create.builder()
                .name("테스트 경로")
                .places(IntStream.range(0, 2).mapToObj(i -> RouteDto.RoutePlaceWithIdAndOrder.builder().id(1L + i).order(i + 1).build()).collect(Collectors.toList()))
                .build();

        given(placeRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(PlaceNotFoundException.class, () -> routeService.create(request));
    }

    @Test
    @DisplayName("내 경로 조회 성공")
    public void getByUser() {
        routeService.getByUser(user);

        verify(routeRepository).findByCreatedBy(user);
    }

    @Test
    @DisplayName("경로 상세조회 성공")
    public void getOne() {
        given(routeRepository.findByIdWithUser(1L)).willReturn(Optional.of(route));

        routeService.getOne(1L);

        verify(routeRepository).findByIdWithUser(1L);
    }

    @Test
    @DisplayName("예외테스트: 존재하지 않는 경로를 조회하면 예외 발생")
    public void getOneNotFound() {
        given(routeRepository.findByIdWithUser(1L)).willReturn(Optional.empty());

        assertThrows(RouteNotFoundException.class, () -> routeService.getOne(1L));
    }

    @Test
    @DisplayName("지역별 리스트조회 성공")
    public void getListByRegion() {
        RouteRequest.GetListByRegion request = RouteRequest.GetListByRegion.builder()
                .region("서울")
                .build();
        PageDto pageDto = new PageDto(1, 10);
        Page<Route> page = new PageImpl<>(Collections.singletonList(route), pageDto.of(), 2);

        given(routeRepository.findAllByRegion(any(), any(), any())).willReturn(page);

        routeService.getListByRegion(request, pageDto);

        verify(routeRepository).findAllByRegion(any(), any(), any());
    }

    @Test
    @DisplayName("현 지도 안에 있는 경로 리스트조회 성공")
    public void getListByCoordinate() {
        RouteRequest.GetListCoordinate request = RouteRequest.GetListCoordinate.builder()
                .maxX(32.123)
                .maxY(123.123)
                .minX(32.789)
                .minY(123.789)
                .build();
        PageDto pageDto = new PageDto(1, 10);
        Page<Route> page = new PageImpl<>(Collections.singletonList(route), pageDto.of(), 2);

        given(routeRepository.findAllByCoordinate(any(), any(), any(), any(), any(), any())).willReturn(page);

        routeService.getListByCoordinate(request, pageDto);

        verify(routeRepository).findAllByCoordinate(any(), any(), any(), any(), any(), any());
    }

    @Test
    @DisplayName("경로 좋아요 생성")
    public void createOrDeleteLike() {
        given(routeRepository.findById(1L)).willReturn(Optional.of(route));
        given(routeLikeRepository.findByRouteIdAndCreatedBy(any(), any())).willReturn(Optional.empty());

        routeService.createOrDeleteLike(1L, user);

        verify(routeLikeRepository).save(any());
    }

    @Test
    @DisplayName("경로 좋아요 삭제")
    public void DeleteLike() {
        given(routeRepository.findById(1L)).willReturn(Optional.of(route));
        given(routeLikeRepository.findByRouteIdAndCreatedBy(any(), any())).willReturn(Optional.of(RouteLike.builder().build()));

        routeService.createOrDeleteLike(1L, user);

        verify(routeLikeRepository).delete(any());
    }

    @Test
    @DisplayName("예외테스트: 없는 경로를 좋아요하면 예외 발생")
    public void DeleteLikeNotFoundRoute() {
        given(routeRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(RouteNotFoundException.class, () -> routeService.createOrDeleteLike(1L, user));
    }
}