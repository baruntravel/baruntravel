package me.travelplan.web.wishlist;

import me.travelplan.MvcTest;
import me.travelplan.WithMockCustomUser;
import me.travelplan.service.file.domain.File;
import me.travelplan.service.place.domain.Place;
import me.travelplan.service.wishlist.WishlistService;
import me.travelplan.service.wishlist.domain.Wishlist;
import me.travelplan.service.wishlist.domain.WishlistPlace;
import me.travelplan.web.wishlist.dto.WishlistRequest;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

@WebMvcTest(WishlistController.class)
@Import(WishlistMapperImpl.class)
class WishlistControllerTest extends MvcTest {
    @MockBean
    private WishlistService wishlistService;

    @Test
    @WithMockCustomUser
    @DisplayName("비어있는 찜목록 생성")
    public void create() throws Exception {
        WishlistRequest.Create request = WishlistRequest.Create.builder().name("서울 여행").build();

        given(wishlistService.create(any())).willReturn(Wishlist.builder().id(1L).name("서울 여행").build());

        ResultActions results = mockMvc.perform(
                post("/wishlist")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
        );

        results.andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("wishlist-create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("찜목록 이름")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("생성된 찜목록 식별자")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("찜목록에 장소 추가")
    public void addPlace() throws Exception {
        WishlistRequest.AddPlace request = WishlistRequest.AddPlace.builder().placeId(1L).build();

        ResultActions results = mockMvc.perform(
                post("/wishlist/{wishlistId}/place", 1L)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("wishlist-addPlace",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("wishlistId").description("찜목록 식별자")
                        ),
                        requestFields(
                                fieldWithPath("placeId").type(JsonFieldType.NUMBER).description("추가할 장소 식별자")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("자신의 찜목록 조회")
    public void getMine() throws Exception {
        List<Wishlist> wishlists = new ArrayList<>();
        Wishlist wishlist1 = Wishlist.builder().id(1L).name("서울 여행")
                .wishlistPlaces(IntStream.range(1, 3).mapToObj(i -> WishlistPlace.builder().place(Place.builder().name("카페" + i).thumbnail(File.builder().url("cafe" + i + ".jpg").build()).build()).build()).collect(Collectors.toList()))
                .build();
        Wishlist wishlist2 = Wishlist.builder().id(1L).name("부산 여행")
                .wishlistPlaces(IntStream.range(1, 3).mapToObj(i -> WishlistPlace.builder().place(Place.builder().name("밥집" + i).thumbnail(File.builder().url("babzip" + i + ".jpg").build()).build()).build()).collect(Collectors.toList()))
                .build();
        wishlists.add(wishlist1);
        wishlists.add(wishlist2);

        given(wishlistService.getMine(any())).willReturn(wishlists);

        ResultActions results = mockMvc.perform(
                get("/wishlist/my", 1L)
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("wishlist-getMine",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("찜목록 식별자"),
                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("찜목록 이름"),
                                fieldWithPath("[].images[].url").type(JsonFieldType.STRING).description("찜목록에 포함되어 있는 장소 이미지 url")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("찜목록에 있는 장소들 조회")
    public void getPlaces() throws Exception {
        List<WishlistPlace> wishlistPlaces = new ArrayList<>();
        WishlistPlace wishlistPlace1 = WishlistPlace.builder().place(Place.builder().id(1L).name("카페").thumbnail(File.builder().url("cafe.jpg").build()).build()).build();
        WishlistPlace wishlistPlace2 = WishlistPlace.builder().place(Place.builder().id(2L).name("밥집").thumbnail(File.builder().url("babzip.jpg").build()).build()).build();
        wishlistPlaces.add(wishlistPlace1);
        wishlistPlaces.add(wishlistPlace2);

        given(wishlistService.getPlaces(any())).willReturn(wishlistPlaces);

        ResultActions results = mockMvc.perform(
                get("/wishlist/{wishlistId}/places", 1L)
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("wishlist-getPlaces",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("wishlistId").description("찜목록 식별자")
                        ),
                        responseFields(
                                fieldWithPath("places[].id").type(JsonFieldType.NUMBER).description("장소 식별자"),
                                fieldWithPath("places[].name").type(JsonFieldType.STRING).description("장소 이름"),
                                fieldWithPath("places[].image").type(JsonFieldType.STRING).description("장소 이미지 url")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("찜목록 삭제")
    public void deleteWishlist() throws Exception {
        ResultActions results = mockMvc.perform(
                delete("/wishlist/{wishlistId}", 1)
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("wishlist-delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("wishlistId").description("찜목록 식별자")
                        )
                ));
    }
}

