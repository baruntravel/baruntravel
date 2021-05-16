package me.travelplan.web.place;

import me.travelplan.MvcTest;
import me.travelplan.WithMockCustomUser;
import me.travelplan.service.file.domain.File;
import me.travelplan.service.place.PlaceLikeService;
import me.travelplan.service.place.PlaceReviewService;
import me.travelplan.service.place.PlaceService;
import me.travelplan.service.place.domain.Place;
import me.travelplan.service.place.domain.PlaceCategory;
import me.travelplan.service.place.domain.PlaceImage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.ResultActions;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static me.travelplan.ApiDocumentUtils.getDocumentRequest;
import static me.travelplan.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlaceController.class)
@Import(PlaceMapperImpl.class)
public class PlaceControllerTest extends MvcTest {
    @MockBean
    private PlaceService placeService;
    @MockBean
    private PlaceLikeService placeLikeService;

    @Test
    @WithMockCustomUser
    @DisplayName("장소 단건 조회 테스트")
    public void getOnePlaceTest() throws Exception {
        Place place = Place.builder()
                .id(1L)
                .name("옥동식 서교점")
                .address("서울 마포구 양화로7길 44-10 (우)04035")
                .images(IntStream.range(0, 2).mapToObj(i -> PlaceImage.builder().file(File.builder().url("file url" + i).build()).build()).collect(Collectors.toList()))
                .x(37.554619995803215)
                .y(126.91083170563417)
                .openHour("11:00 ~ 19:30")
                .category(PlaceCategory.builder().id("FD6").name("음식점").build())
                .url("https://place.map.kakao.com/1797997961")
                .build();

        given(placeService.getById(any())).willReturn(place);

        ResultActions results = mockMvc.perform(
                get("/place/{id}", 1)
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("place-getOne",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("장소 식별자")
                        ),
                        responseFields(
                                fieldWithPath("id").description("장소 식별자"),
                                fieldWithPath("url").description("장소 url (카카오 지도 URL)"),
                                fieldWithPath("thumbnailUrl").description("장소 썸네일 이미지 경로"),
                                fieldWithPath("name").description("장소 이름"),
                                fieldWithPath("address").description("장소 주소"),
                                fieldWithPath("openHour").description("장소 영업 시간"),
                                fieldWithPath("categoryId").description("장소 카테고리 아이디"),
                                fieldWithPath("categoryName").description("장소 카테고리 이름"),
                                fieldWithPath("score").description("장소 평점"),
                                fieldWithPath("likes").description("장소 좋아요 개수"),
                                fieldWithPath("x").description("장소 x좌표"),
                                fieldWithPath("y").description("장소 y좌표"),
                                fieldWithPath("isLike").description("로그인 유저가 장소 좋아요를 눌렀다면 true"),
                                fieldWithPath("images[].url").description("장소 이미지 url")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("장소 좋아요 테스트")
    public void createPlaceLikeTest() throws Exception {
        ResultActions results = mockMvc.perform(
                post("/place/{placeId}/like", 1)
        );

        results.andExpect(status().isOk())
                .andDo(document("place-like-create-delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("placeId").description("장소 식별자")
                        )
                ));
    }
}
