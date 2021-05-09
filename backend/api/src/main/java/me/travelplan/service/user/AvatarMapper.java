package me.travelplan.service.user;

import lombok.RequiredArgsConstructor;
import me.travelplan.service.file.File;
import me.travelplan.service.file.FileService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class AvatarMapper {
    private final FileService fileService;

    @AvatarMapping
    public File upload(MultipartFile file) {
        if (file == null) {
            return null;
        }

        return fileService.upload(file);
    }
}
