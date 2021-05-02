package me.travelplan.service.kakaomap;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class KakaoMapPlace {
    Long placeId;
    String thumbnail;
    String phone;
    List<OpenHour> openHour;
    List<Photo> photos;

    @Getter(AccessLevel.PUBLIC)
    public static class Photo {
        String imageUrl;
    }

    @Getter
    public static class OpenHour {
        String periodName;
        List<OpenHourTime> timeList;
    }

    @Getter
    public static class OpenHourTime {
        String timeName;
        String timeSE;
        String dayOfWeek;
    }
}
