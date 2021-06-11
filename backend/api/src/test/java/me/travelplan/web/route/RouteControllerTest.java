package me.travelplan.web.route;

import me.travelplan.MvcTest;
import me.travelplan.WithMockCustomUser;
import me.travelplan.service.file.domain.File;
import me.travelplan.service.place.domain.Place;
import me.travelplan.service.place.domain.PlaceCategory;
import me.travelplan.service.route.RouteService;
import me.travelplan.service.route.domain.Route;
import me.travelplan.service.route.domain.RoutePlace;
import me.travelplan.service.user.domain.User;
import me.travelplan.web.route.dto.RouteDto;
import me.travelplan.web.route.dto.RouteRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
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

@WebMvcTest(RouteController.class)
@Import(RouteMapperImpl.class)
public class RouteControllerTest extends MvcTest {
    @MockBean
    private RouteService routeService;

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
                .region("서울")
                .places(routeDtos)
                .build();

        given(routeService.create(any())).willReturn(Route.builder().id(1L).name("테스트경로").build());

        ResultActions results = mockMvc.perform(
                post("/route")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
        );

        results.andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("route-create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("경로 이름"),
                                fieldWithPath("region").type(JsonFieldType.STRING).description("경로가 속한 지역"),
                                fieldWithPath("places[].id").type(JsonFieldType.NUMBER).description("장소 식별자"),
                                fieldWithPath("places[].order").type(JsonFieldType.NUMBER).description("장소들 정렬 순서 (사용할 필요가 있는지 검토 필요)")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("생성된 경로 식별자"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("생성된 경로 이름")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("경로 수정(장소순서) 테스트")
    public void updateTest() throws Exception {
        RouteRequest.Update request = RouteRequest.Update.builder()
                .firstPlaceId(123L)
                .secondPlaceId(124L)
                .build();

        ResultActions results = mockMvc.perform(
                put("/route/{id}", 1)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
        );

        results.andExpect(status().isOk())
                .andDo(document("route-update-place-order",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("경로 식별자")
                        ),
                        requestFields(
                                fieldWithPath("firstPlaceId").type(JsonFieldType.NUMBER).description("장소 식별자"),
                                fieldWithPath("secondPlaceId").type(JsonFieldType.NUMBER).description("장소 식별자")
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
                .minY(122.123)
                .maxX(99.123)
                .maxY(124.123)
                .name("테스트 경로")
                .build();
        route.addPlace(RoutePlace.builder().order(1).place(
                Place.builder()
                        .id(122L)
                        .name("테스트 장소 이름")
                        .x(97.123)
                        .address("서울")
                        .y(122.123)
                        .url("https://www.naver.com")
                        .category(PlaceCategory.builder().id("CE7").name("카페").build())
                        .thumbnail(File.builder().url("http://loremflickr.com/440/440").build())
                        .build()
        ).build());
        route.addPlace(RoutePlace.builder().order(2).place(
                Place.builder()
                        .id(123L)
                        .name("강릉 해돋이 마을")
                        .x(99.123)
                        .y(124.123)
                        .address("강릉")
                        .category(PlaceCategory.builder().id("CE7").name("카페").build())
                        .url("https://www.naver.com")
                        .thumbnail(File.builder().url("http://loremflickr.com/440/440").build())
                        .build()
        ).build());

        route.setCreatedAt(LocalDateTime.of(2021, 4, 14, 9, 0));
        route.setUpdatedAt(LocalDateTime.of(2021, 4, 14, 9, 0).plusDays(5));
        route.setCreatedBy(User.builder()
                .name("테스트유저")
                .email("test@test.com")
                .avatar(File.builder()
                        .url("https://s3.ap-northeast-2.amazonaws.com/s3.baruntravel.me/CFGueDdj5pCNzEoCk26e8gY5FgWwOuFhiMfVyzOlU7D7ckIlZHHGad6yCCxa.png")
                        .build()).build());

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
                                fieldWithPath("centerX").type(JsonFieldType.NUMBER).description("경로의 중심 X좌표"),
                                fieldWithPath("centerY").type(JsonFieldType.NUMBER).description("경로의 중심 Y좌표"),
                                fieldWithPath("reviewCount").type(JsonFieldType.NUMBER).description("경로 댓글 개수"),
                                fieldWithPath("score").type(JsonFieldType.NUMBER).description("경로 평점"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("경로 생성 날짜"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("경로 수정 날짜"),
                                fieldWithPath("creator.name").type(JsonFieldType.STRING).description("경로 생성한 사람의 이름"),
                                fieldWithPath("creator.email").type(JsonFieldType.STRING).description("경로 생성한 사람의 이메일"),
                                fieldWithPath("creator.avatarUrl").type(JsonFieldType.STRING).description("경로의 생성한 사람의 프로필 이미지 url (없다면 null)"),
                                fieldWithPath("places").type(JsonFieldType.ARRAY).description("경로의 장소들"),
                                fieldWithPath("places[].id").type(JsonFieldType.NUMBER).description("장소 식별자"),
                                fieldWithPath("places[].address").type(JsonFieldType.STRING).description("장소 주소"),
                                fieldWithPath("places[].url").type(JsonFieldType.STRING).description("장소 URL"),
                                fieldWithPath("places[].image").type(JsonFieldType.STRING).description("장소 이미지 URL"),
                                fieldWithPath("places[].name").type(JsonFieldType.STRING).description("장소 이름"),
                                fieldWithPath("places[].x").type(JsonFieldType.NUMBER).description("장소 X값"),
                                fieldWithPath("places[].y").type(JsonFieldType.NUMBER).description("장소 Y값"),
                                fieldWithPath("places[].category").type(JsonFieldType.STRING).description("장소 카테고리 이름"),
                                fieldWithPath("places[].order").type(JsonFieldType.NUMBER).description("장소 정렬 값 (사용할 필요가 있는지 검토해봐야함)")
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
                .andDo(document("route-like-create-delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("경로 식별자")
                        )
                ));
    }
}
