package me.travelplan.web;

import me.travelplan.MvcTest;
import me.travelplan.WithMockCustomUser;
import me.travelplan.service.cart.CartService;
import me.travelplan.web.cart.CartController;
import me.travelplan.web.cart.CartRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static me.travelplan.ApiDocumentUtils.getDocumentRequest;
import static me.travelplan.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
public class CartControllerTest extends MvcTest {
    @MockBean
    private CartService cartService;

    @Test
    @WithMockCustomUser
    @DisplayName("카트가 없다면 카트를 생성하고 장소를 담고 있다면 있는 카트에 장소담기")
    public void createCartAndAddPlace() throws Exception {
        CartRequest.AddPlace request = CartRequest.AddPlace.builder()
                .placeId(1L)
                .build();

        ResultActions results = mockMvc.perform(
                post("/cart")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
        );

        results.andExpect(status().isOk())
                .andDo(document("cart-addPlace",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("placeId").type(JsonFieldType.NUMBER).description("카트에 추가할 장소 식별자")
                        )
                ));
    }
}
