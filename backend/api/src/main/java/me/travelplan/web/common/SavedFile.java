package me.travelplan.web.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import me.travelplan.service.file.FileServer;
import me.travelplan.service.file.FileType;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class SavedFile {
    private final String originalName;
    private final String name;
    private final String extension;
    private final Long size;
    private final String publicUrl;

    private final Integer width;
    private final Integer height;

    private final boolean isImage;
    private final FileType fileType;
    private final FileServer fileServer;
}
