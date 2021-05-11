package me.travelplan.service.kakaomap;

import lombok.RequiredArgsConstructor;
import me.travelplan.component.kakaomap.KakaoMapPlace;
import me.travelplan.component.kakaomap.KakaoMapService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@RequiredArgsConstructor
@SpringBootTest
public class KakaoMapServiceTest {
    @Autowired
    private KakaoMapService kakaoMapService;

    @Test
    @Disabled
    public void getKakaoMapPlaceTest() {
    }
}
