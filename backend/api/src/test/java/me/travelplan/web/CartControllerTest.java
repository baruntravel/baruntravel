package me.travelplan.web;

import me.travelplan.MvcTest;
import me.travelplan.WithMockCustomUser;
import me.travelplan.service.cart.Cart;
import me.travelplan.service.cart.CartMapperImpl;
import me.travelplan.service.cart.CartPlace;
import me.travelplan.service.cart.CartService;
import me.travelplan.service.file.File;
import me.travelplan.service.place.Place;
import me.travelplan.service.place.PlaceCategory;
import me.travelplan.service.place.PlaceMapperImpl;
import me.travelplan.web.cart.CartController;
import me.travelplan.web.cart.CartRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static me.travelplan.ApiDocumentUtils.getDocumentRequest;
import static me.travelplan.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
@Import(
        CartMapperImpl.class
)
public class CartControllerTest extends MvcTest {
    @MockBean
    private CartService cartService;

    @Test
    @WithMockCustomUser
    @DisplayName("카트가 없다면 카트를 생성하고 장소를 담고 있다면 있는 카트에 장소담기")
    public void createCartAndAddPlace() throws Exception {
        CartRequest.AddPlace request = CartRequest.AddPlace.builder()
                .placeId(1L)
                .build();

        ResultActions results = mockMvc.perform(
                post("/cart")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
        );

        results.andExpect(status().isOk())
                .andDo(document("cart-addPlace",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("placeId").type(JsonFieldType.NUMBER).description("카트에 추가할 장소 식별자")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("나의 카트 조회하기")
    public void getMyCart() throws Exception {
        Cart cart= Cart.builder().build();
        cart.addPlace(CartPlace.builder()
                .place(Place.builder()
                        .id(1L)
                        .name("테스트 장소 이름")
                        .x(97.123)
                        .y(124.123)
                        .url("https://www.naver.com")
                        .category(PlaceCategory.builder().id("CE7").name("카페").build())
                        .image(File.builder().url("http://loremflickr.com/440/440").build())
                        .build())
                .build());
        cart.addPlace(CartPlace.builder()
                .place(Place.builder()
                        .id(2L)
                        .name("강릉 해돋이 마을")
                        .x(97.123)
                        .y(124.123)
                        .category(PlaceCategory.builder().id("CE7").name("카페").build())
                        .url("https://www.naver.com")
                        .image(File.builder().url("http://loremflickr.com/440/440").build())
                        .build())
                .build());
        cart.addPlace(CartPlace.builder()
                .place(Place.builder()
                        .id(3L)
                        .name("테스트장소2")
                        .x(97.123)
                        .y(124.123)
                        .category(PlaceCategory.builder().id("CE7").name("카페").build())
                        .url("https://www.naver.com")
                        .image(File.builder().url("http://loremflickr.com/440/440").build())
                        .build())
                .build());

        given(cartService.getMyCart(any())).willReturn(cart);

        ResultActions results = mockMvc.perform(
                get("/cart/my")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("cart-getMy",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("places[].id").description("장소 식별자"),
                                fieldWithPath("places[].name").description("장소 이름"),
                                fieldWithPath("places[].category").description("장소 카테고리"),
                                fieldWithPath("places[].url").description("장소 URL"),
                                fieldWithPath("places[].image").description("장소 이미지 URL")

                        )
                ));
    }
}
