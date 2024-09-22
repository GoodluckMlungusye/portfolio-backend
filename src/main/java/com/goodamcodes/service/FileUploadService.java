package com.goodamcodes.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class FileUploadService {

    @Value("${google.drive.folder.id}")
    private String folderId;

    @Value("${google.drive.file.view.url}")
    private String driveFileViewUrl;

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String SERVICE_ACCOUNT_KEY_PATH = getPathToGoogleCredentials();

    private static String getPathToGoogleCredentials() {
        String currentDirectory = System.getProperty("user.dir");
        Path filePath = Paths.get(currentDirectory, "google_drive_credentials.json");
        return filePath.toString();
    }

    public String uploadFileToDrive(File file) throws GeneralSecurityException, IOException {

        String fileUrl = "";

        try {
            Drive drive = createDrive();
            com.google.api.services.drive.model.File fileMetaData = createFileMetadata(file);
            fileUrl = getFileUrlFromDrive(drive, fileMetaData, file);
            if (fileUrl.isEmpty()) {
                throw new IllegalStateException("File URL is null or empty");
            }
        }
        catch (IOException | GeneralSecurityException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException("Cannot recognize file: " + e.getMessage(), e);
        }
        return fileUrl;
    }

    private Drive createDrive() throws GeneralSecurityException, IOException {
        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(SERVICE_ACCOUNT_KEY_PATH))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credential)
                .build();
    }

    private com.google.api.services.drive.model.File createFileMetadata(File file) {
        com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
        fileMetaData.setName(file.getName());
        fileMetaData.setParents(Collections.singletonList(folderId));
        return fileMetaData;
    }

    private String getFileUrlFromDrive(Drive drive, com.google.api.services.drive.model.File fileMetaData, File file)
            throws IOException {
        String mimeType = getMimeType(file);
        FileContent mediaContent = new FileContent(mimeType, file);
        com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                .setFields("id").execute();

        return driveFileViewUrl + uploadedFile.getId();
    }

    private String getMimeType(File file) throws IOException {
        Path filePath = file.toPath();
        String mimeType = Files.probeContentType(filePath);
        return (mimeType != null) ? mimeType : "application/octet-stream";
    }

    public File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename == null) {
            throw new IOException("Original filename is null.");
        }

        File tempFile = File.createTempFile("temp", originalFilename);
        multipartFile.transferTo(tempFile);
        return tempFile;
    }

}
