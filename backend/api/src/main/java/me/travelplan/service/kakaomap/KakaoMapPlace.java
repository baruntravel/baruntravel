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
    private Long placeId;
    private String thumbnail;
    private String phone;
    private List<OpenHour> openHour;
    private List<Photo> photos;

    @Getter(AccessLevel.PUBLIC)
    public static class Photo {
        private String imageUrl;
    }

    @Getter
    public static class OpenHour {
        private String periodName;
        private List<OpenHourTime> timeList;
    }

    @Getter
    public static class OpenHourTime {
        private String timeName;
        private String timeSE;
        private String dayOfWeek;
    }
}
