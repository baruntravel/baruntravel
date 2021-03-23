package me.travelplan.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.travelplan.MvcTest;
import me.travelplan.service.user.AuthService;
import me.travelplan.service.user.User;
import me.travelplan.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.Map;

import static me.travelplan.ApiDocumentUtils.getDocumentRequest;
import static me.travelplan.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerTest extends MvcTest {
    @MockBean
    private AuthService authService;
    @MockBean
    private UserService userService;

    @Test
    @DisplayName("회원가입 테스트")
    public void registerTest() throws Exception {
        User user = User.builder().email("test@test.com").password("encodedPasswordd").name("testname").build();

        given(userService.create(any())).willReturn(user);

        Map<String, Object> request = new HashMap<>();
        request.put("email", "test@test.com");
        request.put("password", "password");
        request.put("name", "name");

        ResultActions results = mockMvc.perform(
                post("/auth/register")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
        );

        results.andExpect(status().isCreated())
                .andDo(document("auth-register",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("가입할 이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("가입할 비밀번호"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("가입할 이름")
                        )
                ));
    }

    @Test
    @DisplayName("로그인 테스트")
    public void loginTest() throws Exception {
    }
}
