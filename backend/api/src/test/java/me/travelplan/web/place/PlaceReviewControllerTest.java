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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlaceReviewController.class)
@Import(PlaceReviewMapperImpl.class)
public class PlaceReviewControllerTest extends MvcTest {
    @MockBean
    PlaceReviewService placeReviewService;

    @Test
    @DisplayName("?????? ?????? ?????? (???????????? ?????? ???)")
    @WithMockCustomUser
    public void createReviewTest() throws Exception {
        // given
        Long placeId = 1L;
        Long createdId = 3L;
        String createdContent = "??????????????? ???????????????";
        Double createdScore = 3.5;

        PlaceReview mockReview = PlaceReview.builder()
                .id(createdId)
                .content(createdContent)
                .score(createdScore)
                .build();
        mockReview.setCreatedAt(LocalDateTime.now());
        mockReview.setUpdatedAt(LocalDateTime.now());
        mockReview.setCreatedBy(User.builder().id(1L).name("????????????").email("mock@mock.com").build());
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
                .andDo(print())
                .andDo(document("place-review-create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("placeId").description("?????? ?????????")
                        ),
                        requestParameters(
                                parameterWithName("content").description("?????? ??????"),
                                parameterWithName("score").description("?????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("?????? ?????????")
                        )
                ));
    }


    @Test
    @DisplayName("?????? ?????? ?????? (???????????? ?????? ???)")
    @WithMockCustomUser
    public void updateReviewTest() throws Exception {
        // given
        Long placeId = 1L;
        Long updatedId = 3L;
        String updatedContent = "??????????????? ???????????????";
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
        mockReview.setCreatedBy(User.builder().id(1L).name("?????????").email("mock@mock.com").build());
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
                                parameterWithName("placeId").description("?????? ?????????"),
                                parameterWithName("reviewId").description("?????? ?????????")
                        ),
                        requestParameters(
                                parameterWithName("content").description("?????? ??????"),
                                parameterWithName("score").description("?????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("score").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                fieldWithPath("mine").type(JsonFieldType.BOOLEAN).description("?????? ????????? ???????????? boolean"),
                                fieldWithPath("likes").type(JsonFieldType.NUMBER).description("?????? ?????? ????????? ??????"),
                                fieldWithPath("isLike").type(JsonFieldType.BOOLEAN).description("????????? ????????? ?????? ?????? ????????? ???????????? ???????????? true"),
                                fieldWithPath("images").type(JsonFieldType.ARRAY).description("?????? ????????????"),
                                fieldWithPath("images[].url").type(JsonFieldType.STRING).description("?????? ????????? ?????????"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("createdBy").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                fieldWithPath("createdBy.name").type(JsonFieldType.STRING).description("?????? ????????? ??????"),
                                fieldWithPath("createdBy.email").type(JsonFieldType.STRING).description("?????? ????????? ?????????"),
                                fieldWithPath("createdBy.avatarUrl").description("?????? ????????? ????????? ????????? ??????")
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
                                parameterWithName("placeId").description("?????? ?????????"),
                                parameterWithName("reviewId").description("?????? ?????????")
                        )
                ));
    }

    @Test
    @DisplayName("?????? ?????? ???????????? ?????????")
    public void getReviewsTest() throws Exception {
        // given
        List<PlaceReview> reviews = new ArrayList<>();

        String mockImageUrl = "https://img1.kakaocdn.net/relay/local/R680x420/?fname=http%3A%2F%2Ft1.daumcdn.net%2Fplace%2FB903BF5B6F1B4BB2ABEA3F10A5FDA30A";
        PlaceReview review1 = PlaceReview.builder()
                .id(1L)
                .score(3.5)
                .content("??????????????? ????????? ???????????????. ??????????????????!")
                .images(List.of(PlaceReviewImage.builder().file(File.createExternalImage(mockImageUrl)).build()))
                .build();
        review1.setCreatedBy(User.builder().id(1L).name("?????????").email("mock@mock.com").build());
        review1.setCreatedAt(LocalDateTime.now());
        review1.setUpdatedAt(LocalDateTime.now());
        PlaceReview review2 = PlaceReview.builder().id(2L).score(4.5).content("???????????? ??? ?????? ?????????!").build();
        review2.setCreatedBy(User.builder().id(2L).name("?????????").email("mock@mock.com").build());
        review2.setCreatedAt(LocalDateTime.now());
        review2.setUpdatedAt(LocalDateTime.now());

        reviews.add(review1);
        reviews.add(review2);

        given(placeReviewService.getReviews(any(),any())).willReturn(new PageImpl<>(reviews, PageRequest.of(0, 10), reviews.size()));

        // when
        ResultActions results = mockMvc.perform(
                get("/place/{id}/review", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "1")
                        .param("size", "10")
                        .param("sortType", "best")
                        .characterEncoding("UTF-8")
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("place-getReviews",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("?????? ?????????")
                        ),
                        requestParameters(
                                parameterWithName("page").description("????????? ?????????"),
                                parameterWithName("size").description("????????? ?????????"),
                                parameterWithName("sortType").description("?????????: latest or null, ?????????: best")
                        ),
                        relaxedResponseFields(
                                fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("content[].content").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("content[].score").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                fieldWithPath("content[].mine").type(JsonFieldType.BOOLEAN).description("?????? ????????? ???????????? boolean"),
                                fieldWithPath("content[].likes").type(JsonFieldType.NUMBER).description("?????? ?????? ????????? ??????"),
                                fieldWithPath("content[].isLike").type(JsonFieldType.BOOLEAN).description("????????? ????????? ?????? ?????? ????????? ???????????? ???????????? true"),
                                fieldWithPath("content[].images").type(JsonFieldType.ARRAY).description("?????? ????????????"),
                                fieldWithPath("content[].images[].url").type(JsonFieldType.STRING).description("?????? ????????? ?????????"),
                                fieldWithPath("content[].createdAt").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("content[].updatedAt").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("content[].createdBy").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                fieldWithPath("content[].createdBy.name").type(JsonFieldType.STRING).description("?????? ????????? ??????"),
                                fieldWithPath("content[].createdBy.email").type(JsonFieldType.STRING).description("?????? ????????? ?????????"),
                                fieldWithPath("content[].createdBy.avatarUrl").description("?????? ????????? ????????? ????????? ??????"),
                                fieldWithPath("totalElements").description("?????? ??????"),
                                fieldWithPath("last").description("????????? ??????????????? ??????"),
                                fieldWithPath("totalPages").description("?????? ?????????")
                        )
                ));
    }

    @Test
    @DisplayName("?????? ?????? ????????? ?????? ?????????")
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
                                parameterWithName("placeId").description("?????? ?????????"),
                                parameterWithName("reviewId").description("?????? ?????? ?????????"),
                                parameterWithName("reviewImageId").description("?????? ?????? ????????? ?????????")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("?????? ?????? ????????? ?????????")
    public void createPlaceReviewLikeTest() throws Exception {
        ResultActions results = mockMvc.perform(
                post("/place/{placeId}/review/{reviewId}/like", 1,1)
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("place-review-like-create-delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("placeId").description("?????? ?????????"),
                                parameterWithName("reviewId").description("?????? ?????? ?????????")
                        )
                ));
    }
}
