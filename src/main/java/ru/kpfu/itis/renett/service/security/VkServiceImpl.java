package ru.kpfu.itis.renett.service.security;

import ru.kpfu.itis.renett.exceptions.VkAuthorizationException;
import ru.kpfu.itis.renett.models.User;
import ru.kpfu.itis.renett.repository.UserRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Collectors;

public class VkServiceImpl implements VkService {
    private String CLIENT_SECRET = "XwPMMFjycTN3m45xNx4q";
    private String REDIRECT_URI = "http://localhost:8088/pima/vkOauth";           // :0

    private Parser vkDataParser;
    private UserRepository userRepository;

    public VkServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.vkDataParser = new VkParser();
    }

    @Override
    public User getUserFromVk(String code) throws VkAuthorizationException {
        String tokenIdEmail = getTokenIdEmailFromVk(code);

        String token = vkDataParser.getTokenFromServerResponse(tokenIdEmail);
        String id = vkDataParser.getIdFromServerResponse(tokenIdEmail);
        String email = vkDataParser.getEmailFromServerResponse(tokenIdEmail);

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            String userData = getUserDataFromVk(id, token);
            String firstName = vkDataParser.getFirstNameFromResponse(userData);
            String secondName = vkDataParser.getSecondNameFromResponse(userData);

            User newUser = User.builder()
                    .login(email)
                    .firstName(firstName)
                    .secondName(secondName)
                    .email(email)
                    .build();

            userRepository.save(newUser);
            return newUser;
        }
    }

    private String getUserDataFromVk(String id, String token) {
        String urlToGetData = String.format("https://api.vk.com/method/users.get?user_ids=%s&access_token=%s&v=5.131", id, token);
        return openConnectionWithVk(urlToGetData);
    }

    private String getTokenIdEmailFromVk(String code) {
        String urlToGetToken = String.format("https://oauth.vk.com/access_token?client_id=%d&client_secret=%s&redirect_uri=%s&code=%s", VkService.CLIENT_ID, CLIENT_SECRET, REDIRECT_URI, code);
        return openConnectionWithVk(urlToGetToken);
    }

    private String openConnectionWithVk(String url) {
        URLConnection connection;
        try {
            connection = new URL(url).openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            String result = reader.lines().collect(Collectors.joining(""));
            reader.close();

            return result;
        } catch (IOException e) {
            throw new VkAuthorizationException("Unable to open connection Connection was interrupted", e);
        }
    }
}
