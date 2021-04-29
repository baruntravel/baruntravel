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
                .id(123L)
                .name("테스트 장소이름")
                .x(36.5)
                .y(127.12)
                .url("www.naver.com")
                .address("서울 종로구 종로3길 17")
                .categoryId("CE7")
                .categoryName("카페")
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
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("장소 식별자"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("장소 이름"),
                                fieldWithPath("address").type(JsonFieldType.STRING).description("장소 주소"),
                                fieldWithPath("x").type(JsonFieldType.NUMBER).description("장소 x 좌표"),
                                fieldWithPath("y").type(JsonFieldType.NUMBER).description("장소 y 좌표"),
                                fieldWithPath("url").type(JsonFieldType.STRING).description("장소 url"),
                                fieldWithPath("categoryId").type(JsonFieldType.STRING).description("장소 카테고리 식별자"),
                                fieldWithPath("categoryName").type(JsonFieldType.STRING).description("장소 카테고리 이름")
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
                        .address("종로구 종로 3길 17")
                        .url("https://www.naver.com")
                        .category(PlaceCategory.builder().id("CE7").name("카페").build())
                        .image(File.builder().url("http://loremflickr.com/440/440").build())
                        .build())
                .memo("첫번째 테스트 메모입니다~!")
                .build();
        cartPlaceList.add(cartPlace1);
        CartPlace cartPlace2 = CartPlace.builder()
                .place(Place.builder()
                        .id(2L)
                        .name("강릉 해돋이 마을")
                        .x(97.123)
                        .y(124.123)
                        .address("테스트 주소")
                        .category(PlaceCategory.builder().id("FD6").name("음식점").build())
                        .url("https://www.naver.com")
                        .image(File.builder().url("http://loremflickr.com/440/440").build())
                        .build())
                .memo("두번째 테스트 메모입니다~!")
                .build();
        cartPlaceList.add(cartPlace2);
        CartPlace cartPlace3 = CartPlace.builder()
                .place(Place.builder()
                        .id(3L)
                        .name("테스트장소2")
                        .x(97.123)
                        .y(124.123)
                        .address("테스트 주소2")
                        .category(PlaceCategory.builder().id("CE7").name("카페").build())
                        .url("https://www.naver.com")
                        .image(File.builder().url("http://loremflickr.com/440/440").build())
                        .build())
                .memo("세번째 테스트 메모입니다~!")
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
                                fieldWithPath("places[].address").description("장소 주소"),
                                fieldWithPath("places[].memo").description("장소에 대한 메모 (메모가 없다면 null)"),
                                fieldWithPath("places[].likeCheck").description("장소 좋아요"),
                                fieldWithPath("places[].url").description("장소 URL"),
                                fieldWithPath("places[].categoryId").description("장소 카테고리 식별자"),
                                fieldWithPath("places[].categoryName").description("장소 카테고리 이름")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("카트에 담겨있는 장소에 메모 추가")
    public void addMemo() throws Exception {
        CartPlaceRequest.AddMemo request = CartPlaceRequest.AddMemo.builder()
                .memo("테스트 메모입니다.")
                .build();

        ResultActions results = mockMvc.perform(
                put("/cart/place/{placeId}/memo",123L)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
        );

        results.andExpect(status().isOk())
                .andDo(document("cart-addMemo",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("placeId").description("카트에 담겨있는 장소 식별자")
                        ),
                        requestFields(
                                fieldWithPath("memo").type(JsonFieldType.STRING).description("카트에 담겨있는 장소에 추가할 메모")
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
