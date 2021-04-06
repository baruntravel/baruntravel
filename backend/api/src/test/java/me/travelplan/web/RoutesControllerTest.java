package me.travelplan.web;

import me.travelplan.MvcTest;
import me.travelplan.WithMockCustomUser;
import me.travelplan.service.file.File;
import me.travelplan.service.place.Place;
import me.travelplan.service.place.PlaceCategory;
import me.travelplan.service.route.Route;
import me.travelplan.service.route.RouteMapperImpl;
import me.travelplan.service.route.RoutePlace;
import me.travelplan.service.route.RouteService;
import me.travelplan.web.route.RouteController;
import me.travelplan.web.route.RoutesController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static me.travelplan.ApiDocumentUtils.getDocumentRequest;
import static me.travelplan.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoutesController.class)
@Import({
        RouteMapperImpl.class
})
public class RoutesControllerTest extends MvcTest {
    @MockBean
    private RouteService routeService;

    @Test
    @WithMockCustomUser
    @DisplayName("내 경로들 가져오기 테스트(이름만)")
    public void getMyTest() throws Exception {
        List<Route> routes = new ArrayList<>();
        routes.add(Route.builder().id(1L).name("첫번째 경로").build());
        routes.add(Route.builder().id(2L).name("두번째 경로").build());

        given(routeService.getByUser(any())).willReturn(routes);

        ResultActions results = mockMvc.perform(
                get("/routes/my")
        );

        results.andExpect(status().isOk())
                .andDo(document("routes-getMy",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("routes").type(JsonFieldType.ARRAY).description("경로들"),
                                fieldWithPath("routes[].id").type(JsonFieldType.NUMBER).description("경로 식별자"),
                                fieldWithPath("routes[].name").type(JsonFieldType.STRING).description("경로 이름")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("현재 지도 안에 포함되어 있는 경로 가져오기")
    public void getListTest() throws Exception {
        List<Route> routes = new ArrayList<>();
        routes.add(Route.builder().id(1L).name("첫번째 경로").build());
        routes.add(Route.builder().id(2L).name("두번째 경로").build());
        Route route = Route.builder()
                .id(1L)
                .maxX(97.123)
                .minX(97.124)
                .maxY(124.124)
                .minY(124.123)
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
                        .x(97.124)
                        .y(124.124)
                        .category(PlaceCategory.builder().id("CE7").name("카페").build())
                        .url("https://www.naver.com")
                        .image(File.builder().url("http://loremflickr.com/440/440").build())
                        .build()
        ).build());
        routes.add(route);
        given(routeService.getList(any(), any(), any(), any(), any())).willReturn(new PageImpl<>(routes, PageRequest.of(0, 10), routes.size()));

        ResultActions results = mockMvc.perform(
                get("/routes")
                        .param("page", "1")
                        .param("size", "10")
                        .param("maxX", "37.5")
                        .param("minX", "36.5")
                        .param("maxY", "123.5")
                        .param("minY", "123.2")

        );

        results
                .andExpect(status().isOk())
                .andDo(print())

        ;
    }
}
