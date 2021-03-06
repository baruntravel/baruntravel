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
    @DisplayName("????????? ?????? ???????????? ?????????")
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
                        .param("content", "????????? ?????? ??????")
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
                                parameterWithName("id").description("?????? ?????????")
                        ),
                        requestParts(
                                partWithName("files").description("????????? ????????? ??????")
                        ),
                        requestParameters(
                                parameterWithName("content").description("?????? ?????? ??????"),
                                parameterWithName("score").description("?????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("????????? ?????? ?????? ?????????")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("?????? ?????? ????????? ?????? ?????????")
    public void getRouteReviewListTest() throws Exception {
        List<RouteReview> routeReviews = LongStream.range(1, 6).mapToObj(i -> {
            RouteReview routeReview = RouteReview.builder()
                    .id(i)
                    .content("??????????????????" + i)
                    .score(0.5 + i)
                    .routeReviewFiles(
                            IntStream.range(0, 2).mapToObj(j -> RouteReviewFile
                                    .create(File.builder().url("https://www.naver.com " + j).name("???????????? " + j).build()))
                                    .collect(Collectors.toList())
                    )
                    .build();
            routeReview.setCreatedAt(LocalDateTime.of(2021, 4, 14, 9, 0));
            routeReview.setUpdatedAt(LocalDateTime.of(2021, 4, 14, 9, 0).plusDays(5));
            routeReview.setCreatedBy(User.builder().name("???????????????").email("test@test.com").avatar(File.builder().url("https://s3.ap-northeast-2.amazonaws.com/s3.baruntravel.me/NVKd7InpFVTtespa79wvLKMj7MyGdovTroWJI7nInwpF4symIR4J3VQLpTxn.png").build()).build());
            return routeReview;
        }).collect(Collectors.toList());

        given(routeReviewService.getList(any(), any())).willReturn(new PageImpl<>(routeReviews, PageRequest.of(0, 10), routeReviews.size()));


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
                                parameterWithName("id").description("?????? ?????????")
                        ),
                        requestParameters(
                                parameterWithName("page").description("????????? ?????????"),
                                parameterWithName("size").description("????????? ?????????"),
                                parameterWithName("sortType").description("?????????: latest or null, ?????????: best")
                        ),
                        relaxedResponseFields(
                                fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("?????? ?????? ?????????"),
                                fieldWithPath("content[].content").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                fieldWithPath("content[].score").type(JsonFieldType.NUMBER).description("?????? ?????? ??????"),
                                fieldWithPath("content[].likes").type(JsonFieldType.NUMBER).description("?????? ?????? ????????? ??????"),
                                fieldWithPath("content[].isLike").type(JsonFieldType.BOOLEAN).description("????????? ????????? ?????? ?????? ????????? ???????????? ???????????? true"),
                                fieldWithPath("content[].creator.name").type(JsonFieldType.STRING).description("?????? ?????? ????????? ??????"),
                                fieldWithPath("content[].creator.email").type(JsonFieldType.STRING).description("?????? ?????? ????????? ?????????"),
                                fieldWithPath("content[].creator.avatarUrl").type(JsonFieldType.STRING).description("?????? ?????? ????????? ????????? ?????????"),
                                fieldWithPath("content[].images[].url").type(JsonFieldType.STRING).description("?????? ????????? ???????????? ?????? ?????? url"),
                                fieldWithPath("content[].createdAt").type(JsonFieldType.STRING).description("?????? ?????? ????????????"),
                                fieldWithPath("content[].updatedAt").type(JsonFieldType.STRING).description("?????? ?????? ????????????"),
                                fieldWithPath("totalElements").description("?????? ??????"),
                                fieldWithPath("last").description("????????? ??????????????? ??????"),
                                fieldWithPath("totalPages").description("?????? ?????????")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("????????? ?????? ???????????? ?????????")
    public void updateRouteReviewTest() throws Exception {
        InputStream is1 = new ClassPathResource("mock/images/enjoy.png").getInputStream();
        InputStream is2 = new ClassPathResource("mock/images/enjoy2.png").getInputStream();
        MockMultipartFile mockFile1 = new MockMultipartFile("files", "mock_file1.jpg", "image/jpg", is1.readAllBytes());
        MockMultipartFile mockFile2 = new MockMultipartFile("files", "mock_file2.jpg", "image/jpg", is2.readAllBytes());

        ResultActions results = mockMvc.perform(
                fileUpload("/route/review/{id}", 1)
                        .file(mockFile1)
                        .file(mockFile2)
                        .param("content", "????????? ?????? ?????? ??????")
                        .param("score", "5")
                        .param("fileChange", "true")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .characterEncoding("UTF-8")
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("route-review-update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("?????? ?????? ?????????")
                        ),
                        requestParts(
                                partWithName("files").description("??????????????? ????????? ??????(?????? ????????? ???????????? ????????? null, ?????? ????????? ???????????? ?????? ?????? ??????)")
                        ),
                        requestParameters(
                                parameterWithName("content").description("?????? ?????? ?????? ??????"),
                                parameterWithName("score").description("?????? ?????? ?????? ??????"),
                                parameterWithName("fileChange").description("?????? ?????? ???????????? ?????????????????? true")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("????????? ?????? ???????????? ?????????")
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
                                parameterWithName("id").description("?????? ?????? ?????????")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("?????? ?????? ????????? ?????????")
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
                                parameterWithName("id").description("?????? ?????? ?????????")
                        )
                ));
    }
}