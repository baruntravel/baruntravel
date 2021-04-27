package me.travelplan.service.kakaomap;

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

@RequiredArgsConstructor
@Service
public class KakaoMapService {
    private final LambdaClient lambdaClient;
    private final ObjectMapper objectMapper;

    public KakaoMapPlace getKakaoMapPlace(Long placeId) {
        try {
            HashMap<String, Object> payloadMap = new HashMap<>();
            payloadMap.put("body", "{\"placeId\": " + placeId + "}");

            SdkBytes payload = SdkBytes.fromUtf8String(objectMapper.writeValueAsString(payloadMap));

            InvokeRequest request = InvokeRequest.builder()
                    .functionName("KakaoMapCralwer")
                    .payload(payload)
                    .build();

            InvokeResponse res = lambdaClient.invoke(request);
            String json = res.payload().asUtf8String();

            Map<String, String> responseMap = objectMapper.readValue(json, Map.class);
            String body = objectMapper.writeValueAsString(responseMap.get("body"));
            return objectMapper.readValue(body, KakaoMapPlace.class);
        } catch (LambdaException | JsonProcessingException ex) {
            // ignore
            return null;
        }
    }
}
