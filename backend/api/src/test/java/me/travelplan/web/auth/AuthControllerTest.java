package me.travelplan.web.auth;

import me.travelplan.MvcTest;
import me.travelplan.WithMockCustomUser;
import me.travelplan.security.jwt.Token;
import me.travelplan.service.file.FileService;
import me.travelplan.service.file.domain.File;
import me.travelplan.service.user.AuthService;
import me.travelplan.service.user.UserService;
import me.travelplan.service.user.domain.User;
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
import java.util.HashMap;
import java.util.Map;

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

@WebMvcTest(AuthController.class)
@Import({
        UserMapperImpl.class
})
public class AuthControllerTest extends MvcTest {
    @MockBean
    private AuthService authService;
    @MockBean
    private UserService userService;
    @MockBean
    private FileService fileService;

    @Test
    @DisplayName("???????????? ?????????")
    public void registerTest() throws Exception {
        User user = User.builder().email("test@test.com").password("encodedPasswordd").name("testname").build();

        given(userService.create(any())).willReturn(user);
        given(fileService.upload(any())).willReturn(File.createExternalImage("hello.png"));

        ResultActions results = mockMvc.perform(
                fileUpload("/auth/register")
                        .param("email", "test@test.com")
                        .param("password", "password")
                        .param("name", "testName")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .characterEncoding("UTF-8")
        );

        results.andExpect(status().isCreated())
                .andDo(document("auth-register",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("email").description("????????? ?????????"),
                                parameterWithName("password").description("????????? ????????????"),
                                parameterWithName("name").description("????????? ??????")
                        )
                ));
    }

    @Test
    @DisplayName("????????? ?????????")
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
                                fieldWithPath("email").type(JsonFieldType.STRING).description("???????????? ?????????"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("???????????? ????????????")
                        ),
                        responseFields(
                                fieldWithPath("accessToken").type(JsonFieldType.STRING).description("????????? ??????"),
                                fieldWithPath("accessTokenExpiredAt").type(JsonFieldType.STRING).description("????????? ?????? ?????????"),
                                fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("???????????? ??????(??????)"),
                                fieldWithPath("refreshTokenExpiredAt").type(JsonFieldType.STRING).description("???????????? ?????? ?????????(??????)")
                        )
                ));
    }

    @Test
    @DisplayName("??? ?????? ???????????? ?????????")
    @WithMockCustomUser
    public void meTest() throws Exception {
        User user = User.builder()
                .name("?????????")
                .email("test@test.com")
                .avatar(File.builder()
                        .url("https://s3.ap-northeast-2.amazonaws.com/s3.baruntravel.me/CFGueDdj5pCNzEoCk26e8gY5FgWwOuFhiMfVyzOlU7D7ckIlZHHGad6yCCxa.png")
                        .build())
                .build();

        given(userService.getMe(any())).willReturn(user);

        ResultActions results = mockMvc.perform(
                get("/auth/me")
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("auth-me",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("??? ?????????"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("??? ??????"),
                                fieldWithPath("avatar").type(JsonFieldType.STRING).description("??? ????????? ????????? url")
                        )
                ));
    }

    @Test
    @DisplayName("??? ?????? ?????? ?????????")
    public void updateTest() throws Exception {
        InputStream is = new ClassPathResource("mock/images/enjoy.png").getInputStream();
        MockMultipartFile mockFile = new MockMultipartFile("avatar", "enjoy.jpg", "image/jpg", is.readAllBytes());

        ResultActions results = mockMvc.perform(
                fileUpload("/auth/update")
                        .file(mockFile)
                        .param("name", "updateName")
                        .param("avatarChange", "true")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .characterEncoding("UTF-8")
        );

        results.andExpect(status().isOk())
                .andDo(document("auth-update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParts(
                                partWithName("avatar").description("????????? ????????? ?????????(????????? null)")
                        ),
                        requestParameters(
                                parameterWithName("name").description("????????? ??????"),
                                parameterWithName("avatarChange").description("????????? ????????? ???????????? ?????????????????? true")
                        )
                ));
    }
}
