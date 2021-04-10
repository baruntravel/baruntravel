package me.travelplan.service.place;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.travelplan.web.place.PlaceDto;
import me.travelplan.web.place.PlaceRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlaceMapperTest {
    @Test
    public void createReviewRequestTest() {
        PlaceMapper placeMapper = new PlaceMapperImpl();
        var review = PlaceDto.Review.builder()
                .content("Hello, My Content")
                .score(3.5)
                .build();

        var request = PlaceRequest.CreateReview.builder()
                .review(review)
                .build();

        PlaceReview result = placeMapper.requestToEntity(request);

        assertAll("assert", () -> {
            assertEquals(result.getContent(), request.getReview().getContent());
            assertEquals(result.getScore(), request.getReview().getScore());
        });
    }
}
