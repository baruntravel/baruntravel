package me.travelplan.web.place;

import me.travelplan.MvcTest;
import me.travelplan.WithMockCustomUser;
import me.travelplan.service.place.PlaceReviewService;
import me.travelplan.service.place.domain.PlaceReview;
import me.travelplan.service.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.RequestPartsSnippet;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static me.travelplan.ApiDocumentUtils.getDocumentRequest;
import static me.travelplan.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
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
        mockReview.setCreatedBy(User.builder().name("mockUser").email("mock@mock.com").build());
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

        PlaceReview mockReview = PlaceReview.builder()
                .id(updatedId)
                .score(updatedScore)
                .content(updatedContent)
                .build();
        mockReview.setCreatedAt(LocalDateTime.now());
        mockReview.setUpdatedAt(LocalDateTime.now());
        mockReview.setCreatedBy(User.builder().name("mockUser").email("mock@mock.com").build());
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
                                fieldWithPath("images").type(JsonFieldType.ARRAY).description("리뷰 이미지 경로들"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("리뷰 작성일시"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("리뷰 수정일시"),
                                fieldWithPath("createdBy").type(JsonFieldType.OBJECT).description("리뷰 생성자"),
                                fieldWithPath("createdBy.name").type(JsonFieldType.STRING).description("리뷰 생성자 이름"),
                                fieldWithPath("createdBy.email").type(JsonFieldType.STRING).description("리뷰 생성자 이메일"),
                                fieldWithPath("createdBy.avatarUrl").type(JsonFieldType.STRING).description("리뷰 생성자 아바타 이미지 경로")
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
}
