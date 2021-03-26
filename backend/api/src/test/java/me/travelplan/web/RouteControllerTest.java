package me.travelplan.web;

import me.travelplan.MvcTest;
import me.travelplan.WithMockCustomUser;
import me.travelplan.service.route.RouteMapper;
import me.travelplan.service.route.RouteMapperImpl;
import me.travelplan.service.route.RouteService;
import me.travelplan.web.route.RouteController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.Map;

import static me.travelplan.ApiDocumentUtils.getDocumentRequest;
import static me.travelplan.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RouteController.class)
@Import({
        RouteMapperImpl.class
})
public class RouteControllerTest extends MvcTest {
    @MockBean
    RouteService routeService;

    @Test
    @WithMockCustomUser
    @DisplayName("경로 생성 테스트")
    public void putTest() throws Exception {
        String request = "{\n" +
                "  \"name\": \"나의 테스트 경로\",\n" +
                "  \"places\": [\n" +
                "    {\n" +
                "      \"id\" : 123,\n" +
                "      \"image\" : \"https://www.gn.go.kr/tour/images/tour/main_new/mvisual_img07.jpg\",\n" +
                "      \"name\" : \"강릉바닷가\",\n" +
                "      \"url\" : \"https://www.gn.go.kr/tour/index.do\",\n" +
                "      \"x\" : 37.748125,\n" +
                "      \"y\" : 128.878996,\n" +
                "      \"order\" : 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\" : 124,\n" +
                "      \"image\" : \"https://cf.bstatic.com/xdata/images/hotel/270x200/129750773.jpg?k=d338049190ff48b19261ee5f516ee563aaeb8aeb97c4774c1e171e402cf25891&o=\",\n" +
                "      \"name\" : \"강릉 어린이집\",\n" +
                "      \"url\" : \"https://kr.hotels.com/go/south-korea/kr-best-gangneung-things-to-do\",\n" +
                "      \"x\" : 37.748130,\n" +
                "      \"y\" : 128.8789333,\n" +
                "      \"order\" : 2\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        ResultActions results = mockMvc.perform(
                put("/route")
                    .content(request)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
        );

        results.andExpect(status().isOk())
                .andDo(document("route-put",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("경로 이륾"),
                                fieldWithPath("places").type(JsonFieldType.ARRAY).description("장소들 정보"),
                                fieldWithPath("places[].id").type(JsonFieldType.NUMBER).description("카카오톡에서 제공한 장소 식별자"),
                                fieldWithPath("places[].image").type(JsonFieldType.STRING).description("장소 이미지 URL"),
                                fieldWithPath("places[].name").type(JsonFieldType.STRING).description("장소 이름"),
                                fieldWithPath("places[].url").type(JsonFieldType.STRING).description("장소 URL"),
                                fieldWithPath("places[].x").type(JsonFieldType.NUMBER).description("장소 X값"),
                                fieldWithPath("places[].y").type(JsonFieldType.NUMBER).description("장소 Y값"),
                                fieldWithPath("places[].order").type(JsonFieldType.NUMBER).description("경로에서의 장소 순서")
                        )
                ));
    }
}
