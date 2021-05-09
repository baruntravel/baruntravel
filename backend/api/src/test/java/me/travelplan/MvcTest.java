package me.travelplan;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.travelplan.config.WebMvcConfig;
import me.travelplan.security.jwt.JwtProps;
import me.travelplan.security.jwt.JwtTokenProvider;
import me.travelplan.security.userdetails.CustomUserDetailsService;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("local")
@Disabled
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "travelplan.me")
@Import({
        WebMvcConfig.class,
        JwtTokenProvider.class,
        JwtProps.class,
})
@WithMockCustomUser
public class MvcTest {
    @Autowired protected MockMvc mockMvc;
    @Autowired protected ObjectMapper objectMapper;
    @MockBean protected CustomUserDetailsService customUserDetailsService;
}
