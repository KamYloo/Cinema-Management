package kamylo.CinemaBackend.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.SetBucketPolicyArgs;
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

    @Value("${minio.endpoint}")
    private String endpoint;

    @Override
    public String saveFile(MultipartFile file) {
        try {
            ensureBucketExistsAndPublic();

            // Generate unique filename
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            // Upload file to MinIO
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            // Return URL to access the file
            return endpoint + "/" + bucketName + "/" + fileName;

        } catch (Exception e) {
            throw new RuntimeException("Error saving file to MinIO: " + e.getMessage(), e);
        }
    }

        private void ensureBucketExistsAndPublic() throws Exception {
                boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
                if (!exists) {
                        minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                }

                String publicReadPolicy = """
                                {
                                    "Version": "2012-10-17",
                                    "Statement": [
                                        {
                                            "Effect": "Allow",
                                            "Principal": {
                                                "AWS": ["*"]
                                            },
                                            "Action": ["s3:GetObject"],
                                            "Resource": ["arn:aws:s3:::%s/*"]
                                        }
                                    ]
                                }
                                """.formatted(bucketName);

                minioClient.setBucketPolicy(
                                SetBucketPolicyArgs.builder()
                                                .bucket(bucketName)
                                                .config(publicReadPolicy)
                                                .build()
                );
        }

    @Override
    public void deleteFile(String fileUrl) {
        try {
            // Extract filename from URL
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);

            // Delete file from MinIO
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