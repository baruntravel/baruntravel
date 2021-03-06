package me.travelplan.component.kakaomap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;
import software.amazon.awssdk.services.lambda.model.LambdaException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class KakaoMapService {
    private final LambdaClient lambdaClient;
    private final ObjectMapper objectMapper;

    public Optional<KakaoMapPlace> getKakaoMapPlace(Long placeId) {
        try {
            HashMap<String, Object> payloadMap = new HashMap<>();
            payloadMap.put("body", "{\"placeId\": " + placeId + "}");
            String payloadStr = objectMapper.writeValueAsString(payloadMap);
            SdkBytes payload = SdkBytes.fromUtf8String(payloadStr);

            InvokeRequest request = InvokeRequest.builder()
                    .functionName("KakaoMapCralwer")
                    .payload(payload)
                    .build();

            InvokeResponse res = lambdaClient.invoke(request);
            String json = res.payload().asUtf8String();

            Map<String, String> responseMap = objectMapper.readValue(json, Map.class);
            String body = objectMapper.writeValueAsString(responseMap.get("body"));
            return Optional.of(objectMapper.readValue(body, KakaoMapPlace.class));
        } catch (LambdaException | JsonProcessingException ex) {
            // ignore
            return Optional.empty();
        }
    }
}
