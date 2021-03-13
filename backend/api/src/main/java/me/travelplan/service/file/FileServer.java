package me.travelplan.service.file;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

public enum FileServer {
    DISK,
    S3,
    EXTERNAL;
}
