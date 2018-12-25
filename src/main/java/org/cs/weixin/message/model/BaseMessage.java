package org.cs.weixin.message.model;

/**
 * Created by zlc on 2018/5/31.
 * 文本消息实体类
 */
public class BaseMessage {
    // 开发者微信号(公众号)
    private String ToUserName;
    // 发送方账号(一个open_id)
    private String FromUserName;
    // 消息创建时间 (整型)
    private String CreateTime;
    // 消息类型(文本，图片，语音，视频，小视频，地理位置，链接消息)
    private String MsgType;
    // 文本消息内容
    private String Content;
    // 消息id，64位整型
    private String MsgId;

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }
}
