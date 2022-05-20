package com.uygar.service;

import com.github.kevinsawicki.http.HttpRequest;

import java.io.IOException;
import java.net.URL;

public class UserService {

    public int getUserIdOnCredentials(String username, String password) {
        try {
            String spec = "http://127.0.0.1:8000/userId?username=" + username + "&password=" + password;
            URL url = new URL(spec);
            return Integer.parseInt(HttpRequest.get(url).accept("text/plain").body());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

}