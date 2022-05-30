package ru.renett.repository;

import ru.renett.dto.oauth.vk.TokenResponse;
import ru.renett.dto.oauth.vk.VkUserResponse;
import ru.renett.exceptions.OauthRequestException;

public interface VkRepository {
    TokenResponse getToken(String code) throws OauthRequestException;

    VkUserResponse getUserData(String userId, String token) throws OauthRequestException;
}
