package org.cs.weixin.message.model;

/**
 * Created by zlc on 2018/6/3.
 * Descriptor: TODO 视频消息对象实体类
 */
public class VideoMessage extends BaseMessage{
    // 视频实体类
    private Video Video;

    public org.cs.weixin.message.model.Video getVideo() {
        return Video;
    }

    public void setVideo(org.cs.weixin.message.model.Video video) {
        Video = video;
    }
}
