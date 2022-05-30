package ru.renett.service.oauth;

import ru.renett.models.User;

public interface VkService {
    User getUserFromVkByCode(String code);
}
