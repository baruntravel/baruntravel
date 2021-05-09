package me.travelplan.web;

import me.travelplan.MvcTest;
import me.travelplan.WithMockCustomUser;
import me.travelplan.service.file.domain.File;
import me.travelplan.service.place.*;
import me.travelplan.service.place.domain.Place;
import me.travelplan.service.place.domain.PlaceCategory;
import me.travelplan.service.place.domain.PlaceImage;
import me.travelplan.service.place.domain.PlaceReview;
import me.travelplan.web.place.PlaceController;
import me.travelplan.web.place.PlaceDto;
import me.travelplan.web.place.PlaceMapperImpl;
import me.travelplan.web.place.PlaceRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
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

@WebMvcTest(PlaceController.class)
@Import(
        PlaceMapperImpl.class
)
public class PlaceControllerTest extends MvcTest {
    @MockBean
    private PlaceService placeService;

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

        given(placeService.getOne(any())).willReturn(place);

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
                                fieldWithPath("name").description("장소 이름"),
                                fieldWithPath("address").description("장소 주소"),
                                fieldWithPath("openHour").description("장소 영업 시간"),
                                fieldWithPath("category").description("장소 카테고리 이름"),
                                fieldWithPath("score").description("장소 평점"),
                                fieldWithPath("x").description("장소 x좌표"),
                                fieldWithPath("y").description("장소 y좌표"),
                                fieldWithPath("likeCount").description("장소 좋아요 개수"),
                                fieldWithPath("likeCheck").description("로그인 유저가 장소 좋아요를 눌렀다면 true"),
                                fieldWithPath("images[].url").description("장소 이미지 url")
                        )
                ));
    }

    @Test
    public void getReviewsTest() throws Exception {
        // given
        List<PlaceReview> reviews = new ArrayList<>();

        PlaceReview review1 = PlaceReview.builder().id(1L).score(3.5).content("안녕하세요 첫번째 리뷰입니다. 재미있었어요!").build();
        review1.setCreatedAt(LocalDateTime.now());
        review1.setUpdatedAt(LocalDateTime.now());
        PlaceReview review2 = PlaceReview.builder().id(2L).score(4.5).content("재미있게 잘 놀다 갑니다!").build();
        review2.setCreatedAt(LocalDateTime.now());
        review2.setUpdatedAt(LocalDateTime.now());

        reviews.add(review1);
        reviews.add(review2);

        given(placeService.getReviews(any())).willReturn(reviews);

        // when
        ResultActions results = mockMvc.perform(
                get("/place/{id}/review", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
        );

        results.andExpect(status().isOk())
                .andDo(document("place-getReviews",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("장소 식별자")
                        ),
                        responseFields(
                                fieldWithPath("reviews").type(JsonFieldType.ARRAY).description("리뷰 목록"),
                                fieldWithPath("reviews[].id").type(JsonFieldType.NUMBER).description("리뷰 식별자"),
                                fieldWithPath("reviews[].content").type(JsonFieldType.STRING).description("리뷰 내용"),
                                fieldWithPath("reviews[].score").type(JsonFieldType.NUMBER).description("리뷰 점수"),
                                fieldWithPath("reviews[].createdAt").type(JsonFieldType.STRING).description("리뷰 작성일시"),
                                fieldWithPath("reviews[].updatedAt").type(JsonFieldType.STRING).description("리뷰 수정일시")
                        )
                ));
    }

    @Test
    public void createReviewTest() throws Exception {
        PlaceRequest.PutReview request = PlaceRequest.PutReview.builder()
                .review(PlaceDto.ReviewRequest.builder().content("내가 작성한 리뷰").score(3.5).build()).build();

        ResultActions results = mockMvc.perform(
                post("/place/{placeId}/review", 1L)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
        );

        results.andExpect(status().isCreated())
                .andDo(document("place-createReview",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("placeId").description("장소 식별자")
                        ),
                        requestFields(
                                fieldWithPath("review").type(JsonFieldType.OBJECT).description("생성할 리뷰 정보"),
                                fieldWithPath("review.content").type(JsonFieldType.STRING).description("리뷰 내용"),
                                fieldWithPath("review.score").type(JsonFieldType.NUMBER).description("리뷰 점수")
                        )
                ));

    }

    @Test
    public void updateReviewTest() throws Exception {
        PlaceReview review = PlaceReview.builder().id(3L).score(4.5).content("업데이트한 리뷰").build();
        review.setCreatedAt(LocalDateTime.now());
        review.setUpdatedAt(LocalDateTime.now());

        given(placeService.updateReview(any())).willReturn(review);

        PlaceRequest.PutReview request = PlaceRequest.PutReview.builder()
                .review(PlaceDto.ReviewRequest.builder().content("업데이트한 리뷰").score(4.5).build()).build();

        ResultActions results = mockMvc.perform(
                put("/place/{placeId}/review/{reviewId}", 1L, 3L)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
        );

        results.andExpect(status().isOk())
                .andDo(document("place-updateReviews",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("placeId").description("장소 식별자"),
                                parameterWithName("reviewId").description("리뷰 식별자")
                        ),
                        requestFields(
                                fieldWithPath("review").type(JsonFieldType.OBJECT).description("수정할 리뷰 정보"),
                                fieldWithPath("review.content").type(JsonFieldType.STRING).description("리뷰 내용"),
                                fieldWithPath("review.score").type(JsonFieldType.NUMBER).description("리뷰 점수")
                        ),
                        responseFields(
                                fieldWithPath("review").type(JsonFieldType.OBJECT).description("수정된 리뷰 정보"),
                                fieldWithPath("review.id").type(JsonFieldType.NUMBER).description("리뷰 식별자"),
                                fieldWithPath("review.content").type(JsonFieldType.STRING).description("리뷰 내용"),
                                fieldWithPath("review.score").type(JsonFieldType.NUMBER).description("리뷰 점수"),
                                fieldWithPath("review.createdAt").type(JsonFieldType.STRING).description("리뷰 작성일시"),
                                fieldWithPath("review.updatedAt").type(JsonFieldType.STRING).description("리뷰 수정일시")
                        )
                ));
    }

    @Test
    public void deleteReviewTest() throws Exception {
        ResultActions results = mockMvc.perform(
                delete("/place/{placeId}/review/{reviewId}", 1L, 3L)
        );

        results.andExpect(status().isOk())
                .andDo(document("place-deleteReview",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("placeId").description("장소 식별자"),
                                parameterWithName("reviewId").description("리뷰 식별자")
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
