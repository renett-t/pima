package ru.kpfu.itis.renett.service.security;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class VkParser implements Parser {
    private final Gson gson;

    public VkParser() {
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
