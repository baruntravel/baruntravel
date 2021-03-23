package me.travelplan.web;

import me.travelplan.MvcTest;
import me.travelplan.WithMockCustomUser;
import me.travelplan.security.jwt.Token;
import me.travelplan.service.user.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static me.travelplan.ApiDocumentUtils.getDocumentRequest;
import static me.travelplan.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import({
        UserMapperImpl.class,
        PasswordEncoderMapper.class
})
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
        var response = AuthResponse.Login.from(new Token("accessToken", LocalDateTime.now()), new Token("refreshToken", LocalDateTime.now()));

        given(authService.login(any(), any())).willReturn(response);

        Map<String, Object> request = new HashMap<>();
        request.put("email", "test@test.com");
        request.put("password", "mytestpassword");

        ResultActions results = mockMvc.perform(
                post("/auth/login")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
        );

        results.andExpect(status().isOk())
                .andDo(document("auth-login",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("로그인할 이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("로그인할 비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("accessToken").type(JsonFieldType.STRING).description("액세스 토큰"),
                                fieldWithPath("accessTokenExpiredAt").type(JsonFieldType.STRING).description("액세스 토큰 민료일"),
                                fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("리프레쉬 토큰(임시)"),
                                fieldWithPath("refreshTokenExpiredAt").type(JsonFieldType.STRING).description("리프레쉬 토큰 만료일(임시)")
                        )
                ));
    }

    @Test
    @DisplayName("내 정보 가져오기 테스트")
    @WithMockCustomUser
    public void meTest() throws Exception {
        var response = AuthResponse.Me.from(User.builder().email("test@test.com").name("test").build());

        given(authService.me(any())).willReturn(response);

        ResultActions results = mockMvc.perform(
                get("/auth/me")
        );

        results.andExpect(status().isOk())
                .andDo(document("auth-me",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("내 이메일"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("내 이름")
                        )
                ));
    }
}
