package me.travelplan.service.kakaomap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
class OpenHourTime {
    String timeName;
    String timeSE;
    String dayOfWeek;
}

@Getter
class OpenHour {
    String periodName;
    List<OpenHourTime> timeList;
}

@Getter
class Photo {
    String imageUrl;
}

@Getter
@Builder
@AllArgsConstructor
public class KakaoMapPlace {
    Long placeId;
    String thumbnail;
    String phone;
    List<OpenHour> openHour;
    List<Photo> photos;
}
