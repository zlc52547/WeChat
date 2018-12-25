package org.cs.weixin.message.model;

/**
 * Created by zlc on 2018/6/3.
 * 用户Token实体类
 */
public class AccessToken {
    private String token;
    private int expiresIn;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
