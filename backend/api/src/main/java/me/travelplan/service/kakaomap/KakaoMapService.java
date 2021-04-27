package me.travelplan.service.kakaomap;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.lambda.LambdaClient;

@RequiredArgsConstructor
@Service
public class KakaoMapService {
    private final LambdaClient lambdaClient;
}
