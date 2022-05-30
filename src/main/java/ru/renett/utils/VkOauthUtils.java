package ru.renett.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class VkOauthUtils {

    @Value("${oauth2.vk.version}")
    private String apiVersion;

    @Value("${oauth2.vk.client_id}")
    private String clientId;

    @Value("${oauth2.vk.client_secret}")
    private String clientSecret;

    @Value("${oauth2.vk.redirect-url}")
    private String redirectUrl;

    @Value("${oauth2.vk.api.start_auth}")
    private String apiStartOauth;

    @Value("${oauth2.vk.api.get_user}")
    private String apiGetUserUrl;

    @Value("${oauth2.vk.api.get_token}")
    private String apiGetTokenUrl;

    private static String generateURI(String host, Map<String, String> params) {
        StringBuilder resultURI = new StringBuilder(host);
        if (!params.isEmpty()) {
            resultURI.append("?");
        }
        for (String key : params.keySet()) {
            resultURI.append(key).append("=").append(params.get(key)).append("&");
        }
        if (!params.isEmpty()) {
            resultURI.deleteCharAt(resultURI.length() - 1);
        }
        return resultURI.toString();
    }

    public String getOauthRequestUrl() {
        Map<String, String> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("redirect_uri", redirectUrl);
        params.put("display", "popup");
        params.put("scope", "email");
        params.put("response_type", "code");
        params.put("v", apiVersion);
        return generateURI(apiStartOauth, params);
    }

    public String getAccessTokenRequestUrl(String code) {
        Map<String, String> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("redirect_uri", redirectUrl);
        params.put("code", code);
        return generateURI(apiGetTokenUrl, params);
    }

    public String getUserDataRequestUrl(String userId, String token) {
        Map<String, String> params = new HashMap<>();
        params.put("user_ids", userId);
        params.put("access_token", token);
        params.put("v", apiVersion);
        return generateURI(apiGetUserUrl, params);
    }
}