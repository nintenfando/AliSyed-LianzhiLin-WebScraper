import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePartHeader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Main {
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "credentials.json";

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
            JSON_FACTORY,
            new InputStreamReader(new FileInputStream(CREDENTIALS_FILE_PATH))
        );

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
            .setAccessType("offline")
            .build();

        // Let system choose available port automatically
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public static void main(String[] args) {
        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName("Noti")
                .build();

            // Get latest message addressed to you
            ListMessagesResponse response = service.users().messages()
                .list("me")
                .setQ("is:inbox to:me")
                .setMaxResults(1)
                .execute();

            List<Message> messages = response.getMessages();
            if (!messages.isEmpty()) {  // Removed redundant null check
                String messageId = messages.get(0).getId();
                Message message = service.users().messages()
                    .get("me", messageId)
                    .setFormat("metadata")  // Optimized data fetch
                    .execute();

                // Safely extract headers
                Message.Payload payload = message.getPayload();
                if (payload == null) {
                    System.out.println("Message has no payload");
                    return;
                }

                List<MessagePartHeader> headers = payload.getHeaders();
                String subject = getHeaderValue(headers, "Subject");
                String sender = getHeaderValue(headers, "From");

                System.out.println("Latest message to you:");
                System.out.println("Subject: " + subject);
                System.out.println("From: " + sender);
            } else {
                System.out.println("No messages found in your inbox.");
            }
        } catch (IOException | GeneralSecurityException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String getHeaderValue(List<MessagePartHeader> headers, String name) {
        return Optional.ofNullable(headers)
            .flatMap(hList -> hList.stream()
                .filter(h -> h.getName().equalsIgnoreCase(name))
                .findFirst()
                .map(MessagePartHeader::getValue))
            .orElse(name.equalsIgnoreCase("Subject") ? "(No Subject)" : "Unknown");
    }
}
