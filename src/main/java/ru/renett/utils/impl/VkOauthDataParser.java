package ru.renett.utils.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;
import ru.renett.utils.OauthDataParser;

@Component
public class VkOauthDataParser implements OauthDataParser {
    private final Gson gson;

    public VkOauthDataParser() {
        this.gson = new Gson();
    }

    @Override
    public String getTokenFromServerResponse(String tokenIdEmail) {
        return getJsonObjectFromString(tokenIdEmail).get("access_token").getAsString();
    }

    @Override
    public String getIdFromServerResponse(String tokenIdEmail) {
        return getJsonObjectFromString(tokenIdEmail).get("user_id").getAsString();
    }

    @Override
    public String getEmailFromServerResponse(String tokenIdEmail) {
        return getJsonObjectFromString(tokenIdEmail).get("email").getAsString();
    }

    @Override
    public String getFirstNameFromResponse(String userData) {
        JsonArray array = getJsonObjectFromString(userData).get("response").getAsJsonArray();
        JsonObject jsonObject = array.get(0).getAsJsonObject();
        return jsonObject.get("first_name").getAsString();
    }

    @Override
    public String getSecondNameFromResponse(String userData) {
        JsonArray array = getJsonObjectFromString(userData).get("response").getAsJsonArray();
        JsonObject jsonObject = array.get(0).getAsJsonObject();
        return jsonObject.get("last_name").getAsString();
    }

    private JsonObject getJsonObjectFromString(String input) {
        return gson.fromJson(input, JsonElement.class).getAsJsonObject();
    }
}
