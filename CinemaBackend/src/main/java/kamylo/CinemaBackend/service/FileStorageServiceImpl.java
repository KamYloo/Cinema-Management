package kamylo.CinemaBackend.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final MinioClient minioClient;

    @Value("${minio.bucket-name:cinema-uploads}")
    private String bucketName;

    @Value("${application.file.cdn}")
    private String publicCdn;

    @Override
    public String saveFile(MultipartFile file) {
        try {
            ensureBucketExists();

            
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            
            return publicCdn + bucketName + "/" + fileName;

        } catch (Exception e) {
            throw new RuntimeException("Error saving file to MinIO: " + e.getMessage(), e);
        }
    }

    private void ensureBucketExists() throws Exception {
        boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!exists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    @Override
    public void deleteFile(String fileUrl) {
        try {
            
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);

            
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build()
            );

        } catch (Exception e) {
            throw new RuntimeException("Error deleting file from MinIO: " + e.getMessage(), e);
        }
    }
}
