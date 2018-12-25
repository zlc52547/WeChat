package org.cs.weixin.message.model;

/**
 * Created by zlc on 2018/12/18.
 * Descriptor: TODO 语音实体类
 */
public class Voice {
    // 通过素材管理中的接口上传多媒体文件，得到的id
    private String MediaId;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
}
