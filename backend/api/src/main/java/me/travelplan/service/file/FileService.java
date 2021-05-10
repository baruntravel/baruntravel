package me.travelplan.service.file;

import lombok.RequiredArgsConstructor;
import me.travelplan.service.file.component.S3Uploader;
import me.travelplan.service.file.domain.File;
import me.travelplan.service.file.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class FileService {
    private final FileRepository fileRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public File upload(MultipartFile file) {
        return fileRepository.save(File.create(s3Uploader.upload(file)));
    }
}
