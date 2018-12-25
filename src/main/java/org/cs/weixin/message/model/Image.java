package org.cs.weixin.message.model;

/**
 * Created by zlc on 2018/6/3.
 * 图片实体类
 */
public class Image extends BaseMessage {
    // 通过素材管理中的接口上传多媒体文件，得到的id。
    private String MediaId;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
}
