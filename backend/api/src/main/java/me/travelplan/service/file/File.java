package me.travelplan.service.file;

import lombok.NoArgsConstructor;
import me.travelplan.config.jpa.BaseEntity;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name = "files")
public class File extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private FileServer server;

    @Enumerated(EnumType.STRING)
    private FileType type;

    private String url;

    private Long size;

    private Integer width;
    private Integer height;
}
