package ru.kpfu.itis.renett.service.security;

import ru.kpfu.itis.renett.models.User;

public interface VkService {
    int CLIENT_ID = 7995823;
    User getUserFromVk(String code);
}
