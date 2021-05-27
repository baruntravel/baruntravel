package me.travelplan.service.cart;

import me.travelplan.service.cart.domain.CartPlace;
import me.travelplan.service.cart.exception.CartPlaceNotFoundException;
import me.travelplan.service.cart.exception.PlaceDuplicatedException;
import me.travelplan.service.cart.repository.CartPlaceRepository;
import me.travelplan.service.file.domain.File;
import me.travelplan.service.place.PlaceService;
import me.travelplan.service.place.domain.Place;
import me.travelplan.service.place.repository.PlaceRepository;
import me.travelplan.service.user.domain.User;
import me.travelplan.web.cart.CartPlaceRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CartPlaceServiceTest {
    @InjectMocks
    private CartPlaceService cartPlaceService;
    @Mock
    private PlaceService placeService;
    @Mock
    private CartPlaceRepository cartPlaceRepository;
    @Mock
    private PlaceRepository placeRepository;

    private User user;
    private Place place;
    private CartPlace cartPlace1;
    private CartPlace cartPlace2;

    @BeforeEach
    public void setUp() {
        user = User.builder().name("테스터").email("test@test.com").password("123456").avatar(File.builder().name("files").build()).build();
        place = Place.builder().id(1L).name("테스트 장소1").x(37.12345).y(123.123).build();
        cartPlace1 = CartPlace.builder().id(1L).order(1).build();
        cartPlace2 = CartPlace.builder().id(2L).order(2).build();
    }

    @Test
    @DisplayName("카트에 장소 추가시 장소가 저장,카트 저장 성공")
    public void addPlace() {
        given(placeRepository.findById(1L)).willReturn(Optional.empty());
        given(cartPlaceRepository.findByPlaceIdAndCreatedBy(1L, user)).willReturn(Optional.empty());

        cartPlaceService.addPlace(place, user);

        verify(placeRepository).save(any());
        verify(placeService).updateDetail(any());
        verify(cartPlaceRepository).save(any());
    }

    @Test
    @DisplayName("카트에 장소 추가시 장소가 DB에 있다면 저장하지 않는다.")
    public void addPlaceExistedPlace() {
        given(placeRepository.findById(1L)).willReturn(Optional.of(place));
        given(cartPlaceRepository.findByPlaceIdAndCreatedBy(1L, user)).willReturn(Optional.empty());

        cartPlaceService.addPlace(place, user);

        verify(placeRepository, times(0)).save(any());
        verify(placeService, times(0)).updateDetail(any());
        verify(cartPlaceRepository).save(any());
    }

    @Test
    @DisplayName("예외 테스트: 카트에 이미 있는 장소를 추가하면 예외 발생")
    public void addPlacePlaceDuplicatedException() {
        given(placeRepository.findById(1L)).willReturn(Optional.empty());
        given(cartPlaceRepository.findByPlaceIdAndCreatedBy(1L, user)).willReturn(Optional.of(cartPlace1));

        assertThrows(PlaceDuplicatedException.class, () -> cartPlaceService.addPlace(place, user));
    }

    @Test
    @DisplayName("내 카트 조회 성공")
    public void getMyCart() {
        given(cartPlaceRepository.findAllByCreatedBy(user)).willReturn(Collections.singletonList(cartPlace1));

        cartPlaceService.getMyCart(user);

        verify(cartPlaceRepository).findAllByCreatedBy(any());
    }

    @Test
    @DisplayName("카트에 담겨있는 장소에 메모 성공")
    public void addMemo() {
        CartPlaceRequest.AddMemo request = CartPlaceRequest.AddMemo.builder().memo("커피 마시기").build();
        given(cartPlaceRepository.findByPlaceIdAndCreatedBy(1L, user)).willReturn(Optional.of(cartPlace1));

        cartPlaceService.addMemo(1L, request, user);

        assertEquals(request.getMemo(), cartPlace1.getMemo());
    }

    @Test
    @DisplayName("예외 테스트: 카트에 없는 장소에 메모를 할 경우 예외 발생")
    public void addMemoCartPlaceNotFoundException() {
        CartPlaceRequest.AddMemo request = CartPlaceRequest.AddMemo.builder().memo("커피 마시기").build();
        given(cartPlaceRepository.findByPlaceIdAndCreatedBy(1L, user)).willReturn(Optional.empty());

        assertThrows(CartPlaceNotFoundException.class, () -> cartPlaceService.addMemo(1L, request, user));
    }

    @Test
    @DisplayName("카트에 담겨있는 장소 순서 바꾸기 성공")
    public void updatePlaceOrder() {
        CartPlaceRequest.UpdateOrder request = CartPlaceRequest.UpdateOrder.builder().firstPlaceId(1L).secondPlaceId(2L).build();
        given(cartPlaceRepository.findByPlaceIdAndCreatedBy(1L, user)).willReturn(Optional.of(cartPlace1));
        given(cartPlaceRepository.findByPlaceIdAndCreatedBy(2L, user)).willReturn(Optional.of(cartPlace2));

        cartPlaceService.updatePlaceOrder(request, user);

        assertEquals(2, cartPlace1.getOrder());
        assertEquals(1, cartPlace2.getOrder());
    }

    @Test
    @DisplayName("예외테스트 : 카트에 없는 장소 순서를 바꾸면 예외 발생")
    public void updatePlaceOrderCartPlaceNotFoundException() {
        CartPlaceRequest.UpdateOrder request = CartPlaceRequest.UpdateOrder.builder().firstPlaceId(1L).secondPlaceId(2L).build();
        given(cartPlaceRepository.findByPlaceIdAndCreatedBy(1L, user)).willReturn(Optional.empty());

        assertThrows(CartPlaceNotFoundException.class, () -> cartPlaceService.updatePlaceOrder(request, user));
    }

    @Test
    @DisplayName("카트에 담겨있는 장소 하나를 삭제하면 삭제되고 order 정렬 성공")
    public void deleteOnePlace() {
        given(cartPlaceRepository.findAllByCreatedBy(user)).willReturn(Collections.singletonList(cartPlace2));

        cartPlaceService.deleteOnePlace(1L, user);

        verify(cartPlaceRepository).deleteByPlaceIdAndCreatedBy(1L, user);
        assertEquals(1, cartPlace2.getOrder());
    }

    @Test
    @DisplayName("카트 전체 비우기 성공")
    public void deleteAllPlace() {
        cartPlaceService.deleteAllPlace(user);

        verify(cartPlaceRepository).deleteAllByCreatedBy(user);
    }
}