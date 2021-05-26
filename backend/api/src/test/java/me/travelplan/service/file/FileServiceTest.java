package me.travelplan.service.file;

import me.travelplan.service.file.component.S3Uploader;
import me.travelplan.service.file.repository.FileRepository;
import me.travelplan.web.common.SavedFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {
    @InjectMocks
    private FileService fileService;
    @Mock
    private FileRepository fileRepository;
    @Mock
    private S3Uploader s3Uploader;

    private MultipartFile multipartFile;
    private SavedFile savedFile;

    @BeforeEach
    public void setUp() throws IOException {
        InputStream is = new ClassPathResource("mock/images/enjoy2.png").getInputStream();
        multipartFile = new MockMultipartFile("updateFile", "mock_file1.jpg", "image/jpg", is.readAllBytes());
        savedFile = SavedFile.builder().name("updateFile").build();
    }

    @Test
    @DisplayName("파일 업로드 성공")
    public void upload() {
        given(s3Uploader.upload(multipartFile)).willReturn(savedFile);

        fileService.upload(multipartFile);

        verify(fileRepository).save(any());
    }

    @Test
    @DisplayName("여러 파일 업로드 성공")
    public void uploadFiles() {
        given(s3Uploader.uploadFileList(Collections.singletonList(multipartFile))).willReturn(Collections.singletonList(savedFile));

        fileService.uploadFiles(Collections.singletonList(multipartFile));

        verify(fileRepository).saveAll(any());
    }

    @Test
    @DisplayName("파일 식별자로 삭제 성공")
    public void deleteById() {
        fileService.deleteById(1L);

        verify(fileRepository).deleteById(any());
    }
}