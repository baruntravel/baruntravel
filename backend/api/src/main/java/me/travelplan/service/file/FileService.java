package me.travelplan.service.file;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class FileService {
    private final FileRepository fileRepository;
    private final FileS3Uploader s3Uploader;

    @Transactional
    public File upload(MultipartFile file) {
        return fileRepository.save(File.create(s3Uploader.upload(file)));
    }
}
