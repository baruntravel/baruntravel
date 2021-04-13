package me.travelplan.service.file;

import lombok.*;
import me.travelplan.config.jpa.BaseEntity;
import me.travelplan.service.route.RouteReviewFile;
import me.travelplan.web.common.SavedFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "files")
public class File extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private FileServer server;

    @Enumerated(EnumType.STRING)
    private FileType type;

    private String name;
    private String extension;
    private String url;
    private Long size;

    private Integer width;
    private Integer height;

    @OneToMany(mappedBy = "file", cascade = CascadeType.PERSIST)
    @Builder.Default
    private List<RouteReviewFile> routeReviewFiles = new ArrayList<>();

    public static File create(SavedFile file) {
        return File.builder()
                .name(file.getName())
                .type(file.getFileType())
                .server(file.getFileServer())
                .extension(file.getExtension())
                .height(file.getHeight())
                .width(file.getWidth())
                .size(file.getSize())
                .url(file.getPublicUrl())
                .build();
    }
}
