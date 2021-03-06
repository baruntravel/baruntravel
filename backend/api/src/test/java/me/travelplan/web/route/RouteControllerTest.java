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
    @DisplayName("?????? ?????? ?????????")
    public void createTest() throws Exception {
        List<RouteDto.RoutePlaceWithIdAndOrder> routeDtos = IntStream.range(1, 3).mapToObj(i -> RouteDto.RoutePlaceWithIdAndOrder.builder()
                .id((long) i)
                .order(i)
                .build())
                .collect(Collectors.toList());
        RouteRequest.Create request = RouteRequest.Create.builder()
                .name("???????????????")
                .region("??????")
                .places(routeDtos)
                .build();

        given(routeService.create(any())).willReturn(Route.builder().id(1L).name("???????????????").build());

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
                                fieldWithPath("name").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("region").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                                fieldWithPath("places[].id").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("places[].order").type(JsonFieldType.NUMBER).description("????????? ?????? ?????? (????????? ????????? ????????? ?????? ??????)")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("????????? ?????? ?????????"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("????????? ?????? ??????")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("?????? ??????(????????????) ?????????")
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
                                parameterWithName("id").description("?????? ?????????")
                        ),
                        requestFields(
                                fieldWithPath("firstPlaceId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("secondPlaceId").type(JsonFieldType.NUMBER).description("?????? ?????????")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("????????? ?????? ?????? ?????????")
    public void addPlaceTest() throws Exception {
        String request = "{\n" +
                "  \"place\": {\n" +
                "      \"id\" : 123,\n" +
                "      \"image\" : \"https://www.gn.go.kr/tour/images/tour/main_new/mvisual_img07.jpg\",\n" +
                "      \"name\" : \"??????\",\n" +
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
                                parameterWithName("id").description("?????? ?????????")
                        ),
                        requestFields(
                                fieldWithPath("place").type(JsonFieldType.OBJECT).description("????????? ?????????"),
                                fieldWithPath("place.id").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("place.url").type(JsonFieldType.STRING).description("?????? URL"),
                                fieldWithPath("place.image").type(JsonFieldType.STRING).description("?????? ????????? URL"),
                                fieldWithPath("place.name").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("place.x").type(JsonFieldType.NUMBER).description("?????? X???"),
                                fieldWithPath("place.y").type(JsonFieldType.NUMBER).description("?????? Y???"),
                                fieldWithPath("place.category").type(JsonFieldType.STRING).description("???????????? ?????? ??????"),
                                fieldWithPath("place.order").type(JsonFieldType.NUMBER).description("????????? ?????? ?????? (????????? ????????? ????????? ?????? ??????)")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("?????? ???????????? ???????????? ?????????")
    public void getOneTest() throws Exception {
        Route route = Route.builder()
                .id(1L)
                .minX(97.123)
                .minY(122.123)
                .maxX(99.123)
                .maxY(124.123)
                .name("????????? ??????")
                .build();
        route.addPlace(RoutePlace.builder().order(1).place(
                Place.builder()
                        .id(122L)
                        .name("????????? ?????? ??????")
                        .x(97.123)
                        .address("??????")
                        .y(122.123)
                        .url("https://www.naver.com")
                        .category(PlaceCategory.builder().id("CE7").name("??????").build())
                        .thumbnail(File.builder().url("http://loremflickr.com/440/440").build())
                        .build()
        ).build());
        route.addPlace(RoutePlace.builder().order(2).place(
                Place.builder()
                        .id(123L)
                        .name("?????? ????????? ??????")
                        .x(99.123)
                        .y(124.123)
                        .address("??????")
                        .category(PlaceCategory.builder().id("CE7").name("??????").build())
                        .url("https://www.naver.com")
                        .thumbnail(File.builder().url("http://loremflickr.com/440/440").build())
                        .build()
        ).build());

        route.setCreatedAt(LocalDateTime.of(2021, 4, 14, 9, 0));
        route.setUpdatedAt(LocalDateTime.of(2021, 4, 14, 9, 0).plusDays(5));
        route.setCreatedBy(User.builder()
                .name("???????????????")
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
                                parameterWithName("id").description("?????? ?????????")
                        ),
                        responseFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("centerX").type(JsonFieldType.NUMBER).description("????????? ?????? X??????"),
                                fieldWithPath("centerY").type(JsonFieldType.NUMBER).description("????????? ?????? Y??????"),
                                fieldWithPath("reviewCount").type(JsonFieldType.NUMBER).description("?????? ?????? ??????"),
                                fieldWithPath("score").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                fieldWithPath("creator.name").type(JsonFieldType.STRING).description("?????? ????????? ????????? ??????"),
                                fieldWithPath("creator.email").type(JsonFieldType.STRING).description("?????? ????????? ????????? ?????????"),
                                fieldWithPath("creator.avatarUrl").type(JsonFieldType.STRING).description("????????? ????????? ????????? ????????? ????????? url (????????? null)"),
                                fieldWithPath("places").type(JsonFieldType.ARRAY).description("????????? ?????????"),
                                fieldWithPath("places[].id").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("places[].address").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("places[].url").type(JsonFieldType.STRING).description("?????? URL"),
                                fieldWithPath("places[].image").type(JsonFieldType.STRING).description("?????? ????????? URL"),
                                fieldWithPath("places[].name").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("places[].x").type(JsonFieldType.NUMBER).description("?????? X???"),
                                fieldWithPath("places[].y").type(JsonFieldType.NUMBER).description("?????? Y???"),
                                fieldWithPath("places[].category").type(JsonFieldType.STRING).description("?????? ???????????? ??????"),
                                fieldWithPath("places[].order").type(JsonFieldType.NUMBER).description("?????? ?????? ??? (????????? ????????? ????????? ??????????????????)")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("?????? ????????? ?????????")
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
                                parameterWithName("id").description("?????? ?????????")
                        )
                ));
    }
}
