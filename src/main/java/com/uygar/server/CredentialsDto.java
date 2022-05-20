package com.uygar.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class CredentialsDto {

    @JsonProperty
    private String username;

    @JsonProperty
    private String password;

    public CredentialsDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public CredentialsDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}