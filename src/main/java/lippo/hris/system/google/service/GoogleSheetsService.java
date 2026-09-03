package lippo.hris.system.google.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import lippo.hris.system.ocrengine.response.KTPData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleSheetsService {

    @Value("${google.client-id}")
    private String clientId;

    @Value("${google.client-secret}")
    private String clientSecret;

    @Value("${google.refresh-token}")
    private String refreshToken;

    public Sheets getSheets() throws Exception {

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

        return new Sheets.Builder(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                requestInitializer
        )
                .setApplicationName("My App")
                .build();
    }

    public List<List<Object>> readSheets(String spreadsheetId, String range) throws Exception {

        Sheets sheets = getSheets();
        ValueRange response = sheets.spreadsheets().values().get(spreadsheetId, range).execute();
        List<List<Object>> values = response.getValues();

        if (values == null) {
            return Collections.emptyList();
        }

        return values;
    }

    public void appendKTP(String spreadsheetId, String sheetName, KTPData data, String result, Integer rowNumber) throws Exception {

        Sheets sheets = getSheets();
        List<Object> row = Arrays.asList(
                data.getAgama() == null ? "" : data.getAgama(),
                result == null ? "PASSED" : result
        );

        ValueRange body = new ValueRange().setValues(Collections.singletonList(row));

//        sheets.spreadsheets()
//                .values()
//                .update(
//                        spreadsheetId,
//                        sheetName + "!S" + rowNumber + ":T" + rowNumber,
//                        body
//                )
//                .setValueInputOption("USER_ENTERED")
//                .execute();
        sheets.spreadsheets()
                .values()
                .append(spreadsheetId, sheetName + "!F:G", body)
                .setValueInputOption("USER_ENTERED")
                .setInsertDataOption("INSERT_ROWS")
                .execute();
    }
}
