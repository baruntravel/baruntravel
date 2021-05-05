package me.travelplan.service.kakaomap;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@RequiredArgsConstructor
@SpringBootTest
public class KakaoMapServiceTest {
    @Autowired
    private KakaoMapService kakaoMapService;

    @Test
    @Disabled
    public void getKakaoMapPlaceTest() {
        KakaoMapPlace place = kakaoMapService.getKakaoMapPlace(21361085L);


        System.out.println("hello");
    }
}
