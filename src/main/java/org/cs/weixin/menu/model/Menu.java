package org.cs.weixin.menu.model;

/**
 * Created by zlc on 2018/12/24.
 * Descriptor: TODO 自定义菜单
 */
public class Menu {
    // 一级菜单数组，个数应为1~3个
    private Button[] button;

    public Button[] getButton() {
        return button;
    }

    public void setButton(Button[] button) {
        this.button = button;
    }
}
