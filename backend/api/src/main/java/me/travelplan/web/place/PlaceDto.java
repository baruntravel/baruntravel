package me.travelplan.web.place;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public class PlaceDto {
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