package me.travelplan.web.place;

import lombok.RequiredArgsConstructor;
import me.travelplan.service.place.PlaceMapper;
import me.travelplan.service.place.PlaceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping(path = "/place")
@RestController
public class PlaceController {
    private final PlaceMapper placeMapper;
    private final PlaceService placeService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{placeId}/review")
    public void createReview(@PathVariable Long placeId, @RequestBody PlaceRequest.CreateReview request) {
        placeService.createReview(placeId, placeMapper.requestToEntity(request));
    }
}
