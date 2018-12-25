package org.cs.weixin.message.model;

/**
 * Created by zlc on 2018/6/3.
 * Descriptor: TODO 语音消息实体类
 */
public class VoiceMessage extends BaseMessage{
    // 语音实体类
    private Voice Voice;

    public org.cs.weixin.message.model.Voice getVoice() {
        return Voice;
    }

    public void setVoice(org.cs.weixin.message.model.Voice voice) {
        Voice = voice;
    }
}
