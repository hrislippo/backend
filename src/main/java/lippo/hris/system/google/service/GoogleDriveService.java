package lippo.hris.system.google.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import lippo.hris.system.recruitment.enumeration.GoogleDriveRecruitmentFolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Collections;

@Service
public class GoogleDriveService {

    @Value("${google.client-id}")
    private String clientId;

    @Value("${google.client-secret}")
    private String clientSecret;

    @Value("${google.refresh-token}")
    private String refreshToken;

    @Value("${google.folder-cv}")
    private String folderCv;

    @Value("${google.folder-result}")
    private String folderResult;

    public Drive getDrive() throws Exception {

        GoogleTokenResponse tokenResponse =
                new GoogleRefreshTokenRequest(
                        new NetHttpTransport(),
                        GsonFactory.getDefaultInstance(),
                        refreshToken,
                        clientId,
                        clientSecret
                ).execute();

        GoogleCredentials credentials =
                GoogleCredentials.create(
                        new AccessToken(
                                tokenResponse.getAccessToken(),
                                null
                        )
                );

        HttpRequestInitializer requestInitializer =
                new HttpCredentialsAdapter(credentials);

        return new Drive.Builder(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                requestInitializer
        )
                .setApplicationName("My App")
                .build();
    }

    public String upload(
            MultipartFile multipartFile,
            GoogleDriveRecruitmentFolder folder
    ) throws Exception {

        Drive drive = getDrive();
        File fileMetadata = new File();
        fileMetadata.setName(
                multipartFile.getOriginalFilename()
        );

        if(folder.equals(GoogleDriveRecruitmentFolder.CV)) {
            fileMetadata.setParents(Collections.singletonList(folderCv));
        } else if (folder.equals(GoogleDriveRecruitmentFolder.RESULT_ACTIVITY_RECRUITMENT)){
            fileMetadata.setParents(Collections.singletonList(folderResult));
        }

        byte[] bytes = multipartFile.getBytes();

        InputStreamContent mediaContent =
                new InputStreamContent(
                        "application/octet-stream",
                        new ByteArrayInputStream(bytes)
                );

        mediaContent.setLength(bytes.length);

        File uploadedFile = drive.files()
                .create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();

        String fileId = uploadedFile.getId();

        Permission permission = new Permission();

        permission.setType("anyone");
        permission.setRole("reader");

        drive.permissions()
                .create(fileId, permission)
                .execute();

        return fileId;
    }
}
