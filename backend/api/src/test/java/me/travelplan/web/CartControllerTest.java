package me.travelplan.web;

import me.travelplan.MvcTest;
import me.travelplan.WithMockCustomUser;
import me.travelplan.service.cart.CartPlaceMapperImpl;
import me.travelplan.service.cart.CartPlace;
import me.travelplan.service.cart.CartPlaceService;
import me.travelplan.service.file.File;
import me.travelplan.service.place.Place;
import me.travelplan.service.place.PlaceCategory;
import me.travelplan.web.cart.CartController;
import me.travelplan.web.cart.CartPlaceRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static me.travelplan.ApiDocumentUtils.getDocumentRequest;
import static me.travelplan.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
@Import(
        CartPlaceMapperImpl.class
)
public class CartControllerTest extends MvcTest {
    @MockBean
    private CartPlaceService cartPlaceService;

    @Test
    @WithMockCustomUser
    @DisplayName("카트가 없다면 카트를 생성하고 장소를 담고 있다면 있는 카트에 장소담기")
    public void createCartAndAddPlace() throws Exception {
        CartPlaceRequest.AddPlace request = CartPlaceRequest.AddPlace.builder()
                .placeId(1L)
                .build();

        ResultActions results = mockMvc.perform(
                post("/cart/place")
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
    @DisplayName("나의 카트 조회")
    public void getMyCart() throws Exception {
        List<CartPlace> cartPlaceList = new ArrayList<>();
        CartPlace cartPlace1 = CartPlace.builder()
                .place(Place.builder()
                        .id(1L)
                        .name("테스트 장소 이름")
                        .x(97.123)
                        .y(124.123)
                        .url("https://www.naver.com")
                        .category(PlaceCategory.builder().id("CE7").name("카페").build())
                        .image(File.builder().url("http://loremflickr.com/440/440").build())
                        .build())
                .build();
        cartPlaceList.add(cartPlace1);
        CartPlace cartPlace2 = CartPlace.builder()
                .place(Place.builder()
                        .id(2L)
                        .name("강릉 해돋이 마을")
                        .x(97.123)
                        .y(124.123)
                        .category(PlaceCategory.builder().id("CE7").name("카페").build())
                        .url("https://www.naver.com")
                        .image(File.builder().url("http://loremflickr.com/440/440").build())
                        .build())
                .build();
        cartPlaceList.add(cartPlace2);
        CartPlace cartPlace3 = CartPlace.builder()
                .place(Place.builder()
                        .id(3L)
                        .name("테스트장소2")
                        .x(97.123)
                        .y(124.123)
                        .category(PlaceCategory.builder().id("CE7").name("카페").build())
                        .url("https://www.naver.com")
                        .image(File.builder().url("http://loremflickr.com/440/440").build())
                        .build())
                .build();
        cartPlaceList.add(cartPlace3);

        given(cartPlaceService.getMyCart(any())).willReturn(cartPlaceList);

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
                                fieldWithPath("places[].likeCheck").description("장소 좋아요"),
                                fieldWithPath("places[].url").description("장소 URL"),
                                fieldWithPath("places[].image").description("장소 이미지 URL")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("카트에 있는 장소 한개 삭제")
    public void deleteOneCartPlace() throws Exception {
        ResultActions results = mockMvc.perform(delete("/cart/place/{placeId}", 123));

        results.andExpect(status().isOk())
                .andDo(document("cart-deleteOnePlace",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("placeId").description("장소 식별자")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("카트에 있는 장소 전체 삭제")
    public void deleteAllCartPlace() throws Exception {
        ResultActions results = mockMvc.perform(delete("/cart/place"));

        results.andExpect(status().isOk())
                .andDo(document("cart-deleteAllPlace",
                        getDocumentRequest(),
                        getDocumentResponse()
                ));
    }
}
