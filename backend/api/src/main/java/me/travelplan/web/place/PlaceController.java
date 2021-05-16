package me.travelplan.web.place;

import lombok.RequiredArgsConstructor;
import me.travelplan.security.userdetails.CurrentUser;
import me.travelplan.security.userdetails.CustomUserDetails;
import me.travelplan.service.place.PlaceLikeService;
import me.travelplan.service.place.PlaceService;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping(path = "/place")
@RestController
public class PlaceController {
    private final PlaceMapper placeMapper;
    private final PlaceService placeService;
    private final PlaceLikeService placeLikeService;

    @GetMapping("/{id}")
    public PlaceDto.Response getById(@PathVariable Long id, @CurrentUser CustomUserDetails userDetails) {
        return placeMapper.entityToResponseDto(placeService.getById(id), userDetails.getUser());
    }

    @PostMapping("/{placeId}/like")
    public void like(@PathVariable Long placeId, @CurrentUser CustomUserDetails userDetails) {
        placeLikeService.createOrDeleteLike(placeId, userDetails.getUser());
    }
}
