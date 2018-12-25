package org.cs.weixin.menu.model;

/**
 * Created by zlc on 2018/12/24.
 * Descriptor: TODO 视图型菜单事件
 */
public class ViewButton extends Button{
    // 网页 链接，用户点击菜单可打开链接，不超过1024字节。 type为miniprogram时，不支持小程序的老版本客户端将打开本url。
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
