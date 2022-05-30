package ru.renett.dto.oauth.vk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VkUserResponse {
    private String firstName;
    private String secondName;
}
