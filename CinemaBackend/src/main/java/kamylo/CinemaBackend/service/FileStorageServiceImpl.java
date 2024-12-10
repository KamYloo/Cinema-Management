package kamylo.CinemaBackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${application.file.cdn}")
    private String host;

    @Value("${application.file.movies-image-dir}")
    private String moviesImageDir;

    @Override
    public String saveFile(MultipartFile file) {
        try {

            Path folderPath = Paths.get(moviesImageDir);

            if (!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
            }
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = folderPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return host + "movies-photos/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Error saving image file.", e);
        }
    }

    @Override
    public void deleteFile(String filePath) {
        try {
            String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);

            Path file = Paths.get(moviesImageDir + fileName);
            if (Files.exists(file)) {
                Files.delete(file);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error deleting image file.", e);
        }
    }
}