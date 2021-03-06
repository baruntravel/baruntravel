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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static me.travelplan.ApiDocumentUtils.getDocumentRequest;
import static me.travelplan.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoutesController.class)
@Import(RouteMapperImpl.class)
public class RoutesControllerTest extends MvcTest {
    @MockBean
    private RouteService routeService;

    private final List<Route> routes = new ArrayList<>();

    @BeforeEach
    public void setup() {
        User user = User.builder().name("?????????").email("test@test.com").password("123456").avatar(File.builder().name("files").url("test.jpg").build()).build();
        Route route1 = Route.builder()
                .id(1L)
                .maxX(97.123)
                .maxY(124.124)
                .minX(97.124)
                .minY(124.123)
                .name("????????? ??????")
                .region("??????")
                .build();
        route1.addPlace(RoutePlace.builder().order(1).place(
                Place.builder()
                        .id(12L)
                        .name("?????? ????????? ??????")
                        .x(97.124)
                        .y(124.124)
                        .address("??????")
                        .category(PlaceCategory.builder().id("CE7").name("??????").build())
                        .url("https://www.naver.com")
                        .thumbnail(File.builder().url("http://loremflickr.com/440/440").build())
                        .build())
                .build());
        route1.setCreatedBy(user);
        routes.add(route1);
        Route route2 = Route.builder()
                .id(2L)
                .maxX(97.123)
                .maxY(124.124)
                .minX(97.124)
                .minY(124.123)
                .name("????????? ??????")
                .region("??????")
                .build();
        route2.addPlace(RoutePlace.builder().order(1).place(
                Place.builder()
                        .id(13L)
                        .name("????????? ?????? ??????")
                        .address("??????????????? ??????")
                        .x(97.123)
                        .y(124.123)
                        .url("https://www.naver.com")
                        .category(PlaceCategory.builder().id("CE7").name("??????").build())
                        .thumbnail(File.builder().url("http://loremflickr.com/440/440").build())
                        .build()
        ).build());
        route2.addPlace(RoutePlace.builder().order(2).place(
                Place.builder()
                        .id(12L)
                        .name("?????? ????????? ??????")
                        .address("??????")
                        .x(97.124)
                        .y(124.124)
                        .category(PlaceCategory.builder().id("CE7").name("??????").build())
                        .url("https://www.naver.com")
                        .thumbnail(File.builder().url("http://loremflickr.com/440/440").build())
                        .build())
                .build());
        route2.setCreatedBy(user);
        routes.add(route2);
        Route route3 = Route.builder()
                .id(3L)
                .maxX(97.123)
                .maxY(124.124)
                .minX(97.124)
                .minY(124.123)
                .name("????????? ??????")
                .region("??????")
                .build();
        route3.addPlace(RoutePlace.builder().order(1).place(
                Place.builder()
                        .id(12L)
                        .name("?????? ????????? ??????")
                        .address("??????")
                        .x(97.124)
                        .y(124.124)
                        .category(PlaceCategory.builder().id("CE7").name("??????").build())
                        .url("https://www.naver.com")
                        .thumbnail(File.builder().url("http://loremflickr.com/440/440").build())
                        .build())
                .build());
        route3.setCreatedBy(user);
        routes.add(route3);
    }

    @Test
    @WithMockCustomUser
    @DisplayName("??? ????????? ???????????? ?????????")
    public void getMine() throws Exception {
        given(routeService.getByUser(any())).willReturn(routes);

        ResultActions results = mockMvc.perform(
                get("/routes/my")
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("routes-getMy",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("[].image").type(JsonFieldType.STRING).description("????????? ????????? ????????? ?????? ?????????")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("????????? ?????? ????????????")
    public void getList() throws Exception {
        given(routeService.getListByRegion(any(), any())).willReturn(new PageImpl<>(routes, PageRequest.of(0, 10), routes.size()));

        ResultActions results = mockMvc.perform(
                get("/routes")
                        .param("page", "1")
                        .param("size", "10")
                        .param("sortType", "best")
                        .param("region", "??????")
        );

        results
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("routes-getList-region",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("page").description("????????? ?????????"),
                                parameterWithName("size").description("????????? ?????????"),
                                parameterWithName("sortType").description("?????????: latest or null, ?????????: best"),
                                parameterWithName("region").description("????????? ????????? ????????? ??????")
                        ),
                        relaxedResponseFields(
                                fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("content[].name").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("content[].region").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("content[].creator.name").type(JsonFieldType.STRING).description("?????? ????????? ??????"),
                                fieldWithPath("content[].creator.email").type(JsonFieldType.STRING).description("?????? ????????? ?????????"),
                                fieldWithPath("content[].creator.avatarUrl").description("?????? ????????? ????????? ?????????"),
                                fieldWithPath("content[].places").type(JsonFieldType.ARRAY).description("????????? ??????"),
                                fieldWithPath("content[].places[].id").type(JsonFieldType.NUMBER).description("?????????????????? ????????? ?????? ?????????"),
                                fieldWithPath("content[].places[].name").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("content[].places[].order").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                fieldWithPath("totalElements").description("?????? ??????"),
                                fieldWithPath("last").description("????????? ??????????????? ??????"),
                                fieldWithPath("totalPages").description("?????? ?????????")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("?????? ?????? ?????? ???????????? ?????? ?????? ????????????")
    public void getListByCoordinate() throws Exception {
        given(routeService.getListByCoordinate(any(), any())).willReturn(new PageImpl<>(routes, PageRequest.of(0, 10), routes.size()));

        ResultActions results = mockMvc.perform(
                get("/routes/coordinate")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortType", "best")
                        .param("maxX", "37.5")
                        .param("minX", "36.5")
                        .param("maxY", "123.5")
                        .param("minY", "123.2")
        );

        results
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("routes-getList-coordinate",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("page").description("????????? ?????????"),
                                parameterWithName("size").description("????????? ?????????"),
                                parameterWithName("sortType").description("?????????: latest or null, ?????????: best"),
                                parameterWithName("maxX").description("??????????????? ?????? ??? x??????(?????? ????????? x??????)"),
                                parameterWithName("minX").description("??????????????? ?????? ?????? x??????(?????? ?????? x??????)"),
                                parameterWithName("maxY").description("??????????????? ?????? ??? y??????(?????? ?????? y??????)"),
                                parameterWithName("minY").description("??????????????? ?????? ?????? y??????(?????? ????????? y??????)")
                        ),
                        relaxedResponseFields(
                                fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("content[].name").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("content[].centerX").type(JsonFieldType.NUMBER).description("????????? ?????? X??????"),
                                fieldWithPath("content[].centerY").type(JsonFieldType.NUMBER).description("????????? ?????? Y??????"),
                                fieldWithPath("content[].likes").type(JsonFieldType.NUMBER).description("????????? ????????? ??????"),
                                fieldWithPath("content[].isLike").type(JsonFieldType.BOOLEAN).description("????????? ????????? ?????? ????????? ???????????? ???????????? true"),
                                fieldWithPath("content[].createdBy").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                                fieldWithPath("content[].places").type(JsonFieldType.ARRAY).description("????????? ??????"),
                                fieldWithPath("content[].places[].id").type(JsonFieldType.NUMBER).description("?????????????????? ????????? ?????? ?????????"),
                                fieldWithPath("content[].places[].image").type(JsonFieldType.STRING).description("?????? ????????? URL"),
                                fieldWithPath("content[].places[].name").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("content[].places[].url").type(JsonFieldType.STRING).description("?????? URL"),
                                fieldWithPath("content[].places[].x").type(JsonFieldType.NUMBER).description("?????? X???"),
                                fieldWithPath("content[].places[].y").type(JsonFieldType.NUMBER).description("?????? Y???"),
                                fieldWithPath("content[].places[].category").type(JsonFieldType.STRING).description("???????????? ??????"),
                                fieldWithPath("content[].places[].order").type(JsonFieldType.NUMBER).description("????????? ?????? ?????? (????????? ????????? ????????? ?????? ??????)"),
                                fieldWithPath("totalElements").description("?????? ??????"),
                                fieldWithPath("last").description("????????? ??????????????? ??????"),
                                fieldWithPath("totalPages").description("?????? ?????????")
                        )
                ));
    }
}
