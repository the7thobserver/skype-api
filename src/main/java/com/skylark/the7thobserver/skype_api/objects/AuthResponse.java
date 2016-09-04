package com.skylark.the7thobserver.skype_api.objects;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Jared on 9/2/16.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class AuthResponse
{
    private String tokenType;
    private String accessToken;

    @JsonProperty("token_type")
    public void setTokenType(String tokenType)
    {
        this.tokenType = tokenType;
    }

    @JsonProperty("access_token")
    public void setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
    }

    public String getAccessToken()
    {
        return accessToken;
    }

    public String getTokenType()
    {
        return tokenType;
    }
}