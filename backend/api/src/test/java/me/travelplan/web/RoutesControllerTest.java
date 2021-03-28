package me.travelplan.web;

import me.travelplan.MvcTest;
import me.travelplan.WithMockCustomUser;
import me.travelplan.service.file.File;
import me.travelplan.service.place.Place;
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
}
