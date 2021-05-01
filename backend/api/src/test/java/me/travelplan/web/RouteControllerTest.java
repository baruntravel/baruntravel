package me.travelplan.web;

import me.travelplan.MvcTest;
import me.travelplan.WithMockCustomUser;
import me.travelplan.service.file.File;
import me.travelplan.service.place.Place;
import me.travelplan.service.place.PlaceCategory;
import me.travelplan.service.route.*;
import me.travelplan.service.user.User;
import me.travelplan.web.route.RouteController;
import me.travelplan.web.route.RouteDto;
import me.travelplan.web.route.RouteRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
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

@WebMvcTest(RouteController.class)
@Import({
        RouteMapperImpl.class
})
public class RouteControllerTest extends MvcTest {
    @MockBean
    RouteService routeService;

    private String getCreateOrUpdateRequest() {
        return "{\n" +
                "  \"name\": \"나의 테스트 경로\",\n" +
                "  \"places\": [\n" +
                "    {\n" +
                "      \"id\" : 123,\n" +
                "      \"image\" : \"https://www.gn.go.kr/tour/images/tour/main_new/mvisual_img07.jpg\",\n" +
                "      \"name\" : \"강릉바닷가\",\n" +
                "      \"url\" : \"https://www.gn.go.kr/tour/index.do\",\n" +
                "      \"x\" : 37.748125,\n" +
                "      \"y\" : 128.878996,\n" +
                "      \"category\" : \"CE7\",\n" +
                "      \"order\" : 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\" : 124,\n" +
                "      \"image\" : \"https://cf.bstatic.com/xdata/images/hotel/270x200/129750773.jpg?k=d338049190ff48b19261ee5f516ee563aaeb8aeb97c4774c1e171e402cf25891&o=\",\n" +
                "      \"name\" : \"강릉 어린이집\",\n" +
                "      \"url\" : \"https://kr.hotels.com/go/south-korea/kr-best-gangneung-things-to-do\",\n" +
                "      \"x\" : 37.748130,\n" +
                "      \"y\" : 128.8789333,\n" +
                "      \"category\" : \"CE7\",\n" +
                "      \"order\" : 2\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    @Test
    @WithMockCustomUser
    @DisplayName("이름만 있는 빈 경로 생성 테스트")
    public void createEmptyTest() throws Exception {
        String request = "{\"name\" : \"Test Name\"}";

        ResultActions results = mockMvc.perform(
                post("/route/empty")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
        );

        results.andExpect(status().isCreated())
                .andDo(document("route-createEmpty",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("경로 이름")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("경로 생성 테스트")
    public void createTest() throws Exception {
        List<RouteDto.RoutePlaceWithIdAndOrder> routeDtos = IntStream.range(1, 3).mapToObj(i -> RouteDto.RoutePlaceWithIdAndOrder.builder()
                .id((long) i)
                .order(i)
                .build())
                .collect(Collectors.toList());
        RouteRequest.Create request = RouteRequest.Create.builder()
                .name("테스트경로")
                .places(routeDtos)
                .build();

        ResultActions results = mockMvc.perform(
                post("/route")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
        );

        results.andExpect(status().isCreated())
                .andDo(document("route-create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("경로 이름"),
                                fieldWithPath("places[].id").type(JsonFieldType.NUMBER).description("장소 식별자"),
                                fieldWithPath("places[].order").type(JsonFieldType.NUMBER).description("장소들 정렬 순서 (사용할 필요가 있는지 검토 필요)")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("경로 수정 테스트")
    public void updateTest() throws Exception {
        String request = getCreateOrUpdateRequest();

        ResultActions results = mockMvc.perform(
                put("/route/{id}", 1)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
        );

        results.andExpect(status().isOk())
                .andDo(document("route-update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("경로 식별자")
                        ),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("경로 이륾"),
                                fieldWithPath("places").type(JsonFieldType.ARRAY).description("장소들 정보"),
                                fieldWithPath("places[].id").type(JsonFieldType.NUMBER).description("카카오톡에서 제공한 장소 식별자"),
                                fieldWithPath("places[].image").type(JsonFieldType.STRING).description("장소 이미지 URL"),
                                fieldWithPath("places[].name").type(JsonFieldType.STRING).description("장소 이름"),
                                fieldWithPath("places[].url").type(JsonFieldType.STRING).description("장소 URL"),
                                fieldWithPath("places[].x").type(JsonFieldType.NUMBER).description("장소 X값"),
                                fieldWithPath("places[].y").type(JsonFieldType.NUMBER).description("장소 Y값"),
                                fieldWithPath("places[].category").type(JsonFieldType.STRING).description("카테고리 분류 코드"),
                                fieldWithPath("places[].order").type(JsonFieldType.NUMBER).description("장소들 정렬 순서 (사용할 필요가 있는지 검토 필요)")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("경로에 장소 추가 테스트")
    public void addPlaceTest() throws Exception {
        String request = "{\n" +
                "  \"place\": {\n" +
                "      \"id\" : 123,\n" +
                "      \"image\" : \"https://www.gn.go.kr/tour/images/tour/main_new/mvisual_img07.jpg\",\n" +
                "      \"name\" : \"강남\",\n" +
                "      \"url\" : \"https://www.gn.go.kr/tour/index.do\",\n" +
                "      \"x\" : 37.748125,\n" +
                "      \"y\" : 128.878996,\n" +
                "      \"category\" : \"CE7\",\n" +
                "      \"order\" : 1\n" +
                "    }\n" +
                "}";

        ResultActions results = mockMvc.perform(
                put("/route/{id}/place", 1L)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
        );

        results.andExpect(status().isOk())
                .andDo(document("route-addPlace",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("경로 식별자")
                        ),
                        requestFields(
                                fieldWithPath("place").type(JsonFieldType.OBJECT).description("경로의 장소들"),
                                fieldWithPath("place.id").type(JsonFieldType.NUMBER).description("장소 식별자"),
                                fieldWithPath("place.url").type(JsonFieldType.STRING).description("장소 URL"),
                                fieldWithPath("place.image").type(JsonFieldType.STRING).description("장소 이미지 URL"),
                                fieldWithPath("place.name").type(JsonFieldType.STRING).description("장소 이름"),
                                fieldWithPath("place.x").type(JsonFieldType.NUMBER).description("장소 X값"),
                                fieldWithPath("place.y").type(JsonFieldType.NUMBER).description("장소 Y값"),
                                fieldWithPath("place.category").type(JsonFieldType.STRING).description("카테고리 분류 코드"),
                                fieldWithPath("place.order").type(JsonFieldType.NUMBER).description("장소들 정렬 순서 (사용할 필요가 있는지 검토 필요)")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("경로 식별자로 가져오기 테스트")
    public void getOneTest() throws Exception {
        Route route = Route.builder()
                .id(1L)
                .minX(97.123)
                .minY(97.124)
                .maxX(124.124)
                .maxY(124.123)
                .name("테스트 경로")
                .build();
        route.addPlace(RoutePlace.builder().order(1).place(
                Place.builder()
                        .id(12L)
                        .name("테스트 장소 이름")
                        .x(97.123)
                        .y(124.123)
                        .url("https://www.naver.com")
                        .category(PlaceCategory.builder().id("CE7").name("카페").build())
                        .image(File.builder().url("http://loremflickr.com/440/440").build())
                        .build()
        ).build());
        route.addPlace(RoutePlace.builder().order(2).place(
                Place.builder()
                        .id(12L)
                        .name("강릉 해돋이 마을")
                        .x(97.123)
                        .y(124.123)
                        .category(PlaceCategory.builder().id("CE7").name("카페").build())
                        .url("https://www.naver.com")
                        .image(File.builder().url("http://loremflickr.com/440/440").build())
                        .build()
        ).build());

        given(routeService.getOne(any())).willReturn(route);

        ResultActions results = mockMvc.perform(
                get("/route/{id}", 1)
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("route-getOne",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("경로 식별자")
                        ),
                        responseFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("경로 이름"),
                                fieldWithPath("minX").type(JsonFieldType.NUMBER).description("경로에 포함된 장소중 가장 왼쪽 x 좌표"),
                                fieldWithPath("maxX").type(JsonFieldType.NUMBER).description("경로에 포함된 장소중 가장 오른쪽 x 좌표"),
                                fieldWithPath("minY").type(JsonFieldType.NUMBER).description("경로에 포함된 장소중 가장 왼쪽 y 좌표"),
                                fieldWithPath("maxY").type(JsonFieldType.NUMBER).description("경로에 포함된 장소중 가장 오른쪽 y 좌표"),
                                fieldWithPath("places").type(JsonFieldType.ARRAY).description("경로의 장소들"),
                                fieldWithPath("places[].id").type(JsonFieldType.NUMBER).description("장소 식별자"),
                                fieldWithPath("places[].url").type(JsonFieldType.STRING).description("장소 URL"),
                                fieldWithPath("places[].image").type(JsonFieldType.STRING).description("장소 이미지 URL"),
                                fieldWithPath("places[].name").type(JsonFieldType.STRING).description("장소 이름"),
                                fieldWithPath("places[].x").type(JsonFieldType.NUMBER).description("장소 X값"),
                                fieldWithPath("places[].y").type(JsonFieldType.NUMBER).description("장소 Y값"),
                                fieldWithPath("places[].category").type(JsonFieldType.STRING).description("장소 분류 코드"),
                                fieldWithPath("places[].order").type(JsonFieldType.NUMBER).description("장소 정렬 값 (사용할 필요가 있는지 검토해봐야함)")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("경로에 대한 리뷰생성 테스트")
    public void createRouteReviewTest() throws Exception {
        InputStream is1 = new ClassPathResource("mock/images/enjoy.png").getInputStream();
        InputStream is2 = new ClassPathResource("mock/images/enjoy2.png").getInputStream();
        MockMultipartFile mockFile1 = new MockMultipartFile("file1", "mock_file1.jpg", "image/jpg", is1.readAllBytes());
        MockMultipartFile mockFile2 = new MockMultipartFile("file2", "mock_file2.jpg", "image/jpg", is2.readAllBytes());

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
                                partWithName("file1").description("리뷰에 추가할 파일"),
                                partWithName("file2").description("리뷰에 추가할 파일")
                        ),
                        requestParameters(
                                parameterWithName("content").description("경로 리뷰 내용"),
                                parameterWithName("score").description("경로 점수")
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
            routeReview.setCreatedBy(User.builder().name("테스트유저").build());
            return routeReview;
        }).collect(Collectors.toList());

        given(routeService.getReviewList(any())).willReturn(routeReviews);


        ResultActions results = mockMvc.perform(
                get("/route/{id}/reviews", 1)
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("route-review-list",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("경로 식별자")
                        ),
                        responseFields(
                                fieldWithPath("reviews[].id").description("경로 리뷰 식별자"),
                                fieldWithPath("reviews[].content").description("경로 리뷰 내용"),
                                fieldWithPath("reviews[].score").description("경로 리뷰 점수"),
                                fieldWithPath("reviews[].createdBy").description("경로 리뷰 작성자"),
                                fieldWithPath("reviews[].files[].name").description("경로 리뷰에 첨부되어 있는 파일 이름"),
                                fieldWithPath("reviews[].files[].url").description("경로 리뷰에 첨부되어 있는 파일 url"),
                                fieldWithPath("reviews[].createdAt").description("경로 리뷰 생성날짜"),
                                fieldWithPath("reviews[].updatedAt").description("경로 리뷰 수정날짜")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("경로에 대한 리뷰수정 테스트")
    public void updateRouteReviewTest() throws Exception {
        InputStream is1 = new ClassPathResource("mock/images/enjoy.png").getInputStream();
        InputStream is2 = new ClassPathResource("mock/images/enjoy2.png").getInputStream();
        MockMultipartFile mockFile1 = new MockMultipartFile("file1", "mock_file1.jpg", "image/jpg", is1.readAllBytes());
        MockMultipartFile mockFile2 = new MockMultipartFile("file2", "mock_file2.jpg", "image/jpg", is2.readAllBytes());

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
                                partWithName("file1").description("리뷰수정에 추가할 파일"),
                                partWithName("file2").description("리뷰수정에 추가할 파일")
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
    @DisplayName("경로 좋아요 테스트")
    public void createRouteLikeTest() throws Exception {
        ResultActions results = mockMvc.perform(
                post("/route/{id}/like", 1)
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("route-like-create-update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("경로 식별자")
                        )
                ));
    }
}
