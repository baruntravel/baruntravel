package me.travelplan.web.place;

import lombok.RequiredArgsConstructor;
import me.travelplan.security.userdetails.CurrentUser;
import me.travelplan.security.userdetails.CustomUserDetails;
import me.travelplan.service.place.PlaceLikeService;
import me.travelplan.service.place.PlaceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping(path = "/place")
@RestController
public class PlaceController {
    private final PlaceMapper placeMapper;
    private final PlaceService placeService;
    private final PlaceLikeService placeLikeService;

    @GetMapping("/{id}")
    public PlaceResponse.GetOne getOne(@PathVariable Long id,@CurrentUser CustomUserDetails customUserDetails) {
        return placeMapper.entityToGetOneDto(placeService.getOne(id),customUserDetails.getUser());
    }

    @PostMapping("/{placeId}/like")
    public void like(@PathVariable Long placeId, @CurrentUser CustomUserDetails userDetails) {
        placeLikeService.createOrDeleteLike(placeId, userDetails.getUser());
    }
}
