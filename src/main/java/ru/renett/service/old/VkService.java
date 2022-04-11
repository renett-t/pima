package ru.renett.service.old;

import ru.renett.models.User;

public interface VkService {
    int CLIENT_ID = 7995823;
    User getUserFromVk(String code);
}
