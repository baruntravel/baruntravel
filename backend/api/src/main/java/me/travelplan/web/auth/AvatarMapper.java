package me.travelplan.web.auth;

import lombok.RequiredArgsConstructor;
import me.travelplan.service.file.domain.File;
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