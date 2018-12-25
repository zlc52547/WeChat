package org.cs.weixin.menu.model;

/**
 * Created by zlc on 2018/12/24.
 * Descriptor: TODO 点击型菜单事件
 */
public class ClickButton extends Button{
    // 菜单KEY值，用于消息接口推送，不超过128字节
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
