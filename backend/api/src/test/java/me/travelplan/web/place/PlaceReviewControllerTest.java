package me.travelplan.web.place;

import me.travelplan.MvcTest;
import me.travelplan.WithMockCustomUser;
import me.travelplan.service.file.domain.File;
import me.travelplan.service.place.PlaceReviewService;
import me.travelplan.service.place.domain.PlaceReview;
import me.travelplan.service.place.domain.PlaceReviewImage;
import me.travelplan.service.user.domain.User;
import me.travelplan.web.place.review.PlaceReviewController;
import me.travelplan.web.place.review.PlaceReviewMapperImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static me.travelplan.ApiDocumentUtils.getDocumentRequest;
import static me.travelplan.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlaceReviewController.class)
@Import(PlaceReviewMapperImpl.class)
public class PlaceReviewControllerTest extends MvcTest {
    @MockBean
    PlaceReviewService placeReviewService;

    @Test
    @DisplayName("장소 리뷰 생성 (이미지가 없을 때)")
    @WithMockCustomUser
    public void createReviewTest() throws Exception {
        // given
        Long placeId = 1L;
        Long createdId = 3L;
        String createdContent = "업데이트된 내용입니다";
        Double createdScore = 3.5;

        PlaceReview mockReview = PlaceReview.builder()
                .id(createdId)
                .content(createdContent)
                .score(createdScore)
                .build();
        mockReview.setCreatedAt(LocalDateTime.now());
        mockReview.setUpdatedAt(LocalDateTime.now());
        mockReview.setCreatedBy(User.builder().id(1L).name("이렐리아").email("mock@mock.com").build());
        given(placeReviewService.createReview(any(), any())).willReturn(mockReview);

        // when
        ResultActions results = mockMvc.perform(
                fileUpload("/place/{placeId}/review", placeId)
                        .param("content", createdContent)
                        .param("score", createdScore.toString())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .characterEncoding("UTF-8")
        );

        // then
        results.andExpect(status().isCreated())
                .andDo(document("place-review-create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("placeId").description("장소 식별자")
                        ),
                        requestParameters(
                                parameterWithName("content").description("리뷰 내용"),
                                parameterWithName("score").description("리뷰 점수")
                        )
                ));
    }


    @Test
    @DisplayName("장소 리뷰 수정 (이미지가 없을 때)")
    @WithMockCustomUser
    public void updateReviewTest() throws Exception {
        // given
        Long placeId = 1L;
        Long updatedId = 3L;
        String updatedContent = "업데이트된 내용입니다";
        Double updatedScore = 3.5;

        String mockImageUrl = "https://img1.kakaocdn.net/relay/local/R680x420/?fname=http%3A%2F%2Ft1.daumcdn.net%2Fplace%2FB903BF5B6F1B4BB2ABEA3F10A5FDA30A";
        PlaceReview mockReview = PlaceReview.builder()
                .id(updatedId)
                .score(updatedScore)
                .content(updatedContent)
                .images(List.of(PlaceReviewImage.builder().file(File.createExternalImage(mockImageUrl)).build()))
                .build();
        mockReview.setCreatedAt(LocalDateTime.now());
        mockReview.setUpdatedAt(LocalDateTime.now());
        mockReview.setCreatedBy(User.builder().id(1L).name("아칼리").email("mock@mock.com").build());
        given(placeReviewService.updateReview(any(), any())).willReturn(mockReview);
        Mockito.doNothing().when(placeReviewService).checkReviewUpdatable(any(), any());

        // when
        ResultActions results = mockMvc.perform(
                fileUpload("/place/{placeId}/review/{reviewId}", placeId, updatedId)
                        .param("content", updatedContent)
                        .param("score", updatedScore.toString())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .characterEncoding("UTF-8")
        );

        // then
        results.andExpect(status().isOk())
                .andDo(document("place-review-update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("placeId").description("장소 식별자"),
                                parameterWithName("reviewId").description("리뷰 식별자")
                        ),
                        requestParameters(
                                parameterWithName("content").description("리뷰 내용"),
                                parameterWithName("score").description("리뷰 점수")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("리뷰 식별자"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("리뷰 내용"),
                                fieldWithPath("score").type(JsonFieldType.NUMBER).description("리뷰 점수"),
                                fieldWithPath("mine").type(JsonFieldType.BOOLEAN).description("내가 작성한 리뷰인지 boolean"),
                                fieldWithPath("images").type(JsonFieldType.ARRAY).description("리뷰 이미지들"),
                                fieldWithPath("images[].url").type(JsonFieldType.STRING).description("리뷰 이미지 경로들"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("리뷰 작성일시"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("리뷰 수정일시"),
                                fieldWithPath("createdBy").type(JsonFieldType.OBJECT).description("리뷰 생성자"),
                                fieldWithPath("createdBy.name").type(JsonFieldType.STRING).description("리뷰 생성자 이름"),
                                fieldWithPath("createdBy.email").type(JsonFieldType.STRING).description("리뷰 생성자 이메일"),
                                fieldWithPath("createdBy.avatarUrl").description("리뷰 생성자 아바타 이미지 경로")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    public void deleteReviewTest() throws Exception {
        // given
        Mockito.doNothing().when(placeReviewService).checkReviewUpdatable(any(), any());
        Mockito.doNothing().when(placeReviewService).deleteReview(any());

        // when
        ResultActions results = mockMvc.perform(
                delete("/place/{placeId}/review/{reviewId}", 1L, 3L)
        );

        // then
        results.andExpect(status().isOk())
                .andDo(document("place-review-delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("placeId").description("장소 식별자"),
                                parameterWithName("reviewId").description("리뷰 식별자")
                        )
                ));
    }

    @Test
    @DisplayName("장소 리뷰 가져오기 테스트")
    public void getReviewsTest() throws Exception {
        // given
        List<PlaceReview> reviews = new ArrayList<>();

        String mockImageUrl = "https://img1.kakaocdn.net/relay/local/R680x420/?fname=http%3A%2F%2Ft1.daumcdn.net%2Fplace%2FB903BF5B6F1B4BB2ABEA3F10A5FDA30A";
        PlaceReview review1 = PlaceReview.builder()
                .id(1L)
                .score(3.5)
                .content("안녕하세요 첫번째 리뷰입니다. 재미있었어요!")
                .images(List.of(PlaceReviewImage.builder().file(File.createExternalImage(mockImageUrl)).build()))
                .build();
        review1.setCreatedBy(User.builder().id(1L).name("라이언").email("mock@mock.com").build());
        review1.setCreatedAt(LocalDateTime.now());
        review1.setUpdatedAt(LocalDateTime.now());
        PlaceReview review2 = PlaceReview.builder().id(2L).score(4.5).content("재미있게 잘 놀다 갑니다!").build();
        review2.setCreatedBy(User.builder().id(2L).name("벨코즈").email("mock@mock.com").build());
        review2.setCreatedAt(LocalDateTime.now());
        review2.setUpdatedAt(LocalDateTime.now());

        reviews.add(review1);
        reviews.add(review2);

        given(placeReviewService.getReviews(any())).willReturn(reviews);

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
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("리뷰 식별자"),
                                fieldWithPath("[].content").type(JsonFieldType.STRING).description("리뷰 내용"),
                                fieldWithPath("[].score").type(JsonFieldType.NUMBER).description("리뷰 점수"),
                                fieldWithPath("[].mine").type(JsonFieldType.BOOLEAN).description("내가 작성한 리뷰인지 boolean"),
                                fieldWithPath("[].images").type(JsonFieldType.ARRAY).description("리뷰 이미지들"),
                                fieldWithPath("[].images[].url").type(JsonFieldType.STRING).description("리뷰 이미지 경로들"),
                                fieldWithPath("[].createdAt").type(JsonFieldType.STRING).description("리뷰 작성일시"),
                                fieldWithPath("[].updatedAt").type(JsonFieldType.STRING).description("리뷰 수정일시"),
                                fieldWithPath("[].createdBy").type(JsonFieldType.OBJECT).description("리뷰 생성자"),
                                fieldWithPath("[].createdBy.name").type(JsonFieldType.STRING).description("리뷰 생성자 이름"),
                                fieldWithPath("[].createdBy.email").type(JsonFieldType.STRING).description("리뷰 생성자 이메일"),
                                fieldWithPath("[].createdBy.avatarUrl").description("리뷰 생성자 아바타 이미지 경로")
                        )
                ));
    }

    @Test
    @DisplayName("장소 리뷰 이미지 삭제 테스트")
    public void deleteReviewImageTest() throws Exception {
        // given
        doNothing().when(placeReviewService).checkReviewUpdatable(any(), any());
        doNothing().when(placeReviewService).deleteReviewImage(any());

        // when
        ResultActions results = mockMvc.perform(
                delete("/place/{placeId}/review/{reviewId}/image/{reviewImageId}", 1L, 1L, 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
        );

        results.andExpect(status().isOk())
                .andDo(document("place-review-image-delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("placeId").description("장소 식별자"),
                                parameterWithName("reviewId").description("장소 리뷰 식별자"),
                                parameterWithName("reviewImageId").description("장소 리뷰 이미지 식별자")
                        )
                ));
    }

}
