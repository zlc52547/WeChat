package org.cs.weixin.message.model;

/**
 * Created by zlc on 2018/6/3.
 * 图片消息实体类
 */
public class ImageMessage extends BaseMessage {
    // 图片实体类
    private Image Image;

    public Image getImage() {
        return Image;
    }

    public void setImage(Image Image) {
        this.Image = Image;
    }
}
