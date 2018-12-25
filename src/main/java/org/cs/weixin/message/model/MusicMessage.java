package org.cs.weixin.message.model;

/**
 * Created by zlc on 2018/6/3.
 * Descriptor: TODO 音乐消息实体类
 */
public class MusicMessage extends BaseMessage{
    // 音乐实体类
    private Music Music;

    public Music getMusic() {
        return Music;
    }

    public void setMusic(Music Music) {
        this.Music = Music;
    }
}
