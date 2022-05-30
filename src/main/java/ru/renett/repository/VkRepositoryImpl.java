package ru.renett.repository;

import lombok.RequiredArgsConstructor;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Repository;
import ru.renett.dto.oauth.vk.TokenResponse;
import ru.renett.dto.oauth.vk.VkUserResponse;
import ru.renett.exceptions.OauthRequestException;
import ru.renett.utils.VkOauthUtils;
import ru.renett.utils.impl.VkOauthDataParser;

import java.io.IOException;

@Repository
@RequiredArgsConstructor
public class VkRepositoryImpl implements VkRepository {

    private final VkOauthDataParser parser;
    private final VkOauthUtils utils;
    private final OkHttpClient client;

    @Override
    public TokenResponse getToken(String code) throws OauthRequestException {
        try {
            Request request = new Request.Builder()
                    .url(utils.getAccessTokenRequestUrl(code))
                    .build();

            Call call = client.newCall(request);
            Response resp = call.execute();
            if (resp.isSuccessful()) {
                String response = resp.body().string();
                return TokenResponse.builder()
                        .id(parser.getIdFromServerResponse(response))
                        .accessToken(parser.getTokenFromServerResponse(response))
                        .email(parser.getEmailFromServerResponse(response))
                        .build();
            } else {
                throw new OauthRequestException("Unable to get token for user. " + resp.message());
            }

        } catch (IOException e) {
            throw new OauthRequestException("Unable to perform call to service to get token.", e);
        }
//        call.enqueue(new Callback() {
//            public void onResponse(Call call, Response resp)
//                    throws IOException {
//                System.out.println(resp);
//                String response = resp.body().string();
//                TokenResponse tokens = TokenResponse.builder()
//                        .accessToken(parser.getTokenFromServerResponse(response))
//                        .email(parser.getEmailFromServerResponse(response))
//                        .message("OK")
//                        .build();
//                // return token
//            }
//
//            public void onFailure(Call call, IOException e) {
//                TokenResponse tokens = TokenResponse.builder()
//                        .accessToken(null)
//                        .email(null)
//                        .message("Smth wrong calling to server.")
//                        .build();
//                ;
//            }
//        });
    }

    @Override
    public VkUserResponse getUserData(String userId, String token) throws OauthRequestException {
        try {
            Request request = new Request.Builder()
                    .url(utils.getUserDataRequestUrl(userId, token))
                    .build();

            Call call = client.newCall(request);
            Response resp = call.execute();
            if (resp.isSuccessful()) {
                String response = resp.body().string();
                return VkUserResponse.builder()
                        .firstName(parser.getFirstNameFromResponse(response))
                        .secondName(parser.getSecondNameFromResponse(response))
                        .build();
            } else {
                throw new OauthRequestException("Unable to get user data.");
            }

        } catch (IOException e) {
            throw new OauthRequestException("Unable to perform call to service to get user data.", e);
        }
//        call.enqueue(new Callback() {
//            public void onResponse(Call call, Response resp)
//                    throws IOException {
//                System.out.println(resp);
//                String response = resp.body().string();
//                TokenResponse tokens = TokenResponse.builder()
//                        .accessToken(parser.getTokenFromServerResponse(response))
//                        .email(parser.getEmailFromServerResponse(response))
//                        .message("OK")
//                        .build();
//                // return token
//            }
//
//            public void onFailure(Call call, IOException e) {
//                TokenResponse tokens = TokenResponse.builder()
//                        .accessToken(null)
//                        .email(null)
//                        .message("Smth wrong calling to server.")
//                        .build();
//                ;
//            }
//        });
    }

}
