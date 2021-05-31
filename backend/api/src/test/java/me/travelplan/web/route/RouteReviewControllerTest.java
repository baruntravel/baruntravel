package me.travelplan.web.route;

import me.travelplan.MvcTest;
import me.travelplan.WithMockCustomUser;
import me.travelplan.service.file.domain.File;
import me.travelplan.service.route.RouteReviewService;
import me.travelplan.service.route.domain.RouteReview;
import me.travelplan.service.route.domain.RouteReviewFile;
import me.travelplan.service.user.domain.User;
import me.travelplan.web.route.review.RouteReviewController;
import me.travelplan.web.route.review.RouteReviewMapperImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static me.travelplan.ApiDocumentUtils.getDocumentRequest;
import static me.travelplan.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RouteReviewController.class)
@Import(RouteReviewMapperImpl.class)
class RouteReviewControllerTest extends MvcTest {
    @MockBean
    private RouteReviewService routeReviewService;

    @Test

    @WithMockCustomUser
    @DisplayName("경로에 대한 리뷰생성 테스트")
    public void createRouteReviewTest() throws Exception {
        InputStream is1 = new ClassPathResource("mock/images/enjoy.png").getInputStream();
        InputStream is2 = new ClassPathResource("mock/images/enjoy2.png").getInputStream();
        MockMultipartFile mockFile1 = new MockMultipartFile("files", "mock_file1.jpg", "image/jpg", is1.readAllBytes());
        MockMultipartFile mockFile2 = new MockMultipartFile("files", "mock_file2.jpg", "image/jpg", is2.readAllBytes());

        given(routeReviewService.create(any(), any())).willReturn(RouteReview.builder().id(1L).build());

        ResultActions results = mockMvc.perform(
                fileUpload("/route/{id}/review", 1)
                        .file(mockFile1)
                        .file(mockFile2)
                        .param("content", "테스트 리뷰 내용")
                        .param("score", "5")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .characterEncoding("UTF-8")
        );

        results.andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("route-review-create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("경로 식별자")
                        ),
                        requestParts(
                                partWithName("files").description("리뷰에 추가할 파일")
                        ),
                        requestParameters(
                                parameterWithName("content").description("경로 리뷰 내용"),
                                parameterWithName("score").description("경로 점수")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("생성된 경로 리뷰 식별자")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("경로 리뷰 리스트 조회 테스트")
    public void getRouteReviewListTest() throws Exception {
        List<RouteReview> routeReviews = LongStream.range(1, 6).mapToObj(i -> {
            RouteReview routeReview = RouteReview.builder()
                    .id(i)
                    .content("경로리뷰내용" + i)
                    .score(0.5 + i)
                    .routeReviewFiles(
                            IntStream.range(0, 2).mapToObj(j -> RouteReviewFile
                                    .create(File.builder().url("https://www.naver.com " + j).name("파일이름 " + j).build()))
                                    .collect(Collectors.toList())
                    )
                    .build();
            routeReview.setCreatedAt(LocalDateTime.of(2021, 4, 14, 9, 0));
            routeReview.setUpdatedAt(LocalDateTime.of(2021, 4, 14, 9, 0).plusDays(5));
            routeReview.setCreatedBy(User.builder().name("테스트유저").email("test@test.com").avatar(File.builder().url("https://s3.ap-northeast-2.amazonaws.com/s3.baruntravel.me/NVKd7InpFVTtespa79wvLKMj7MyGdovTroWJI7nInwpF4symIR4J3VQLpTxn.png").build()).build());
            return routeReview;
        }).collect(Collectors.toList());

        given(routeReviewService.getList(any(),any())).willReturn(new PageImpl<>(routeReviews, PageRequest.of(0, 10), routeReviews.size()));


        ResultActions results = mockMvc.perform(
                get("/route/{id}/reviews", 1)
                        .param("page", "1")
                        .param("size", "10")
                        .param("sortType", "best")
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("route-review-list",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("경로 식별자")
                        ),
                        requestParameters(
                                parameterWithName("page").description("조회할 페이지"),
                                parameterWithName("size").description("조회할 경로수"),
                                parameterWithName("sortType").description("최신순: latest or null, 추천순: best")
                        ),
                        relaxedResponseFields(
                                fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("경로 리뷰 식별자"),
                                fieldWithPath("content[].content").type(JsonFieldType.STRING).description("경로 리뷰 내용"),
                                fieldWithPath("content[].score").type(JsonFieldType.NUMBER).description("경로 리뷰 점수"),
                                fieldWithPath("content[].likes").type(JsonFieldType.NUMBER).description("경로 리뷰 좋아요 개수"),
                                fieldWithPath("content[].isLike").type(JsonFieldType.BOOLEAN).description("로그인 유저가 해당 경로 리뷰에 좋아요를 눌렀다면 true"),
                                fieldWithPath("content[].creator.name").type(JsonFieldType.STRING).description("경로 리뷰 작성자 이름"),
                                fieldWithPath("content[].creator.email").type(JsonFieldType.STRING).description("경로 리뷰 작성자 이메일"),
                                fieldWithPath("content[].creator.avatarUrl").type(JsonFieldType.STRING).description("경로 리뷰 작성자 프로필 이미지"),
                                fieldWithPath("content[].images[].url").type(JsonFieldType.STRING).description("경로 리뷰에 첨부되어 있는 파일 url"),
                                fieldWithPath("content[].createdAt").type(JsonFieldType.STRING).description("경로 리뷰 생성날짜"),
                                fieldWithPath("content[].updatedAt").type(JsonFieldType.STRING).description("경로 리뷰 수정날짜"),
                                fieldWithPath("totalElements").description("전체 개수"),
                                fieldWithPath("last").description("마지막 페이지인지 식별"),
                                fieldWithPath("totalPages").description("전체 페이지")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("경로에 대한 리뷰수정 테스트")
    public void updateRouteReviewTest() throws Exception {
        InputStream is1 = new ClassPathResource("mock/images/enjoy.png").getInputStream();
        InputStream is2 = new ClassPathResource("mock/images/enjoy2.png").getInputStream();
        MockMultipartFile mockFile1 = new MockMultipartFile("files", "mock_file1.jpg", "image/jpg", is1.readAllBytes());
        MockMultipartFile mockFile2 = new MockMultipartFile("files", "mock_file2.jpg", "image/jpg", is2.readAllBytes());

        ResultActions results = mockMvc.perform(
                fileUpload("/route/review/{id}", 1)
                        .file(mockFile1)
                        .file(mockFile2)
                        .param("content", "테스트 리뷰 내용 수정")
                        .param("score", "5")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .characterEncoding("UTF-8")
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("route-review-update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("경로 리뷰 식별자")
                        ),
                        requestParts(
                                partWithName("files").description("리뷰수정에 추가할 파일")
                        ),
                        requestParameters(
                                parameterWithName("content").description("경로 리뷰 수정 내용"),
                                parameterWithName("score").description("경로 수정 점수")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("경로에 대한 리뷰삭제 테스트")
    public void deleteRouteReviewTest() throws Exception {
        ResultActions results = mockMvc.perform(
                delete("/route/review/{id}", 1)
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("route-review-delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("경로 리뷰 식별자")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("경로 리뷰 좋아요 테스트")
    public void createRouteReviewLikeTest() throws Exception {
        ResultActions results = mockMvc.perform(
                post("/route/review/{id}/like", 1)
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("route-review-like-create-delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("경로 리뷰 식별자")
                        )
                ));
    }
}