package me.travelplan.web.place;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import me.travelplan.web.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public class PlaceDto {
    public static class Request {
        private Long id;
        private String name;
        private String url;
        private Double x;
        private Double y;
        private String address;
        private String categoryId;
    }

    public static class Response {
        private Long id;
        private String name;
        private String url;
        private Double x;
        private Double y;
        private String address;
        private String categoryId;
        private String thumbnailUrl;
        private List<String> images;
    }

    public static class Place {
        private Long id;
        private String name;
        private String url;
        private Double x;
        private Double y;
        private String address;
        private String categoryId;
    }
}