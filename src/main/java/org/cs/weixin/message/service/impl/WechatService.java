package org.cs.weixin.message.service.impl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.cs.weixin.message.model.*;
import org.cs.weixin.message.service.IWechatService;
import org.cs.weixin.util.DateUtil;
import org.cs.weixin.util.MessageUtil;
import org.cs.weixin.util.WeixinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zlc on 2018/5/31.
 * 微信接口调用实现类
 */
@Service("WechatService")
public class WechatService implements IWechatService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WechatService.class);

    private static final String APPID="wx6edd4c71d8777f31";   //在基础配置中可查看自己APPID
    private static final String APPSECRET="050f5b650b0992755ad067fa4d4dc644";   //在基础配置中可查看自己APPSECRET
    private static String ACCESS_TOKEN="";// 通过APPID和APPSECRET获取的access_token
    private static String FILE_TYPE="";// 文件类型
    // 获取ACCESS_TOKEN的Url
    private static final String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+APPSECRET;
    // 上传文件Url
    private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
    // 获取永久素材MediaId的Url
    private static final String getMediaId = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN";
    // 获取素材MediaId的Url
    private static final String MediaId_URL= "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token="+ACCESS_TOKEN+"&type="+FILE_TYPE;

    /**
     * 处理微信发来的请求
     * TODO 注意：返回的xml标签一定要为首字母大写，否则会返回不了,出错
     * TODO 微信在线调试工具：https://mp.weixin.qq.com/debug?token=1453338883&lang=zh_CN
     * @param request
     * @return responseMessage
     */
    public String weixinPost(HttpServletRequest request) {
        String responseContent = "初始化信息";
        String responseMessage = "success";//   ֱ保证微信服务器5秒内能接受到回复
        try {
            // xml请求解析
            Map<String, String> requestMap = MessageUtil.xmlToMap(request); //  利用写好的工具类将微信服务器发来的xml格式消息解析到map里
            //从map中拿到相应参数ֵ
            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            // 消息内容
            String content = requestMap.get("Content");
            // 语音内容
            String recvMessage = requestMap.get("Recognition");
            LOGGER.info("FromUserName 是:" + fromUserName + ", ToUserName 是:" + toUserName + ", MsgType 是:" + msgType);
            System.out.println("WeChat发送的消息类型是:" + msgType);
            System.out.println(requestMap);
            // 文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                //  这里根据关键字执行相应的逻辑，只有你想不到的，没有做不到的
                // 发送特定的信息，回复指定的内容
                if (content.equals("1")) {
                    // 创建图文消息(回复用)
                    NewsMessage newsMessage = new NewsMessage();
                    String pic = new String("https://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-660222.jpg");
                    newsMessage.setContent("回复图文消息");
                    newsMessage.setToUserName(fromUserName);
                    newsMessage.setFromUserName(toUserName);
                    newsMessage.setCreateTime(new Date().getTime() + "");
                    newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
                    // 组装图文消息
                    List<Article> articleList = new ArrayList<Article>();
                    Article article = new Article();
                    article.setTitle("天后");
                    article.setDescription("终于找到借口，趁着醉意上心头" + "，如果有一天...");
                    article.setPicUrl(pic);
                    article.setUrl("https://music.163.com/#/song?id=72408");
                    //2018年10月12日之后微信调整了自动回复和客服消息回复,图文消息只允许回复一条
                    Article article1 = new Article();
                    article1.setTitle("走马");
                    article1.setDescription("窗外雨都停了，屋里灯还黑着" + "，数着你的冷漠...");
                    article1.setPicUrl("https://gss1.bdstatic.com/-vo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike92%2C5%2C5%2C92%2C30/sign=f256489e70c6a7efad2ba0749c93c434/5bafa40f4bfbfbedbe23db3e7ef0f736afc31f7c.jpg");
                    article1.setUrl("https://music.163.com/#/song?id=29414037&market=baiduqk");
                    articleList.add(article);
                    // articleList.add(article1);
                    // 设置图文消息个数
                    newsMessage.setArticleCount(articleList.size());
                    // 设置图文消息包含的图文集合
                    newsMessage.setArticles(articleList);
                    // 将图文消息对象转换成xml字符串
                    responseMessage = MessageUtil.newsMessageToXml(newsMessage);
                } else if (content.contains("图片")) {
                    // 创建图片消息(回复用)
                    Image image = new Image();
                    // 通过在线调试工具先上传一个图片,得到媒体的MediaId
                    String mediaId = "zTUaUR9t0hGFxZy6HMpyJgMyMXxyZaLKTfUmCJE3ix7vvWT0xvw7SMzpTIP-kWWk";
                    image.setMediaId(mediaId);
                    // 图片消息(将发过来的图片转发回去)
                    ImageMessage imageMessage = new ImageMessage();
                    imageMessage.setContent("回复图片消息");
                    imageMessage.setToUserName(fromUserName);
                    imageMessage.setFromUserName(toUserName);
                    imageMessage.setCreateTime(new Date().getTime() + "");
                    imageMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_IMAGE);
                    imageMessage.setImage(image);
                    responseMessage = MessageUtil.imageMessageToXml(imageMessage);
                } else if (content.contains("语音")) {
                    // 自动回复语音
                    VoiceMessage voiceMessage = new VoiceMessage();
                    voiceMessage.setToUserName(fromUserName);
                    voiceMessage.setFromUserName(toUserName);
                    voiceMessage.setCreateTime(new Date().getTime() + "");
                    voiceMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_VOICE);
                    Voice voice = new Voice();
                    // 通过在线接口测试工具上传媒体获取的MediaId
                    voice.setMediaId("bMzWRbGc1O_WfPp4h0LNwRQobs6GG8si-bIAzgQ8ygu7XOrdJjb4enhmH0GYN1go");
                    voiceMessage.setVoice(voice);
                    responseMessage = MessageUtil.voiceMessageToXml(voiceMessage);
                } else if (content.contains("视频")) {

                    // 自动回复视频
                    VideoMessage videoMessage= new VideoMessage();
                    videoMessage.setToUserName(fromUserName);
                    videoMessage.setFromUserName(toUserName);
                    videoMessage.setCreateTime(new Date().getTime() + "");
                    videoMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_VIDEO);
                    Video video = new Video();
                    // 通过在线接口测试工具上传媒体获取的MediaId
                    // 方便测试
                    String MediaId = "";
                    String Title = "";
                    String Description = "";
                    if (toUserName.contains("o6HSH0w6npZo4qoo1o9lVUi7EGe8")){
                        MediaId = "1c81IPC075WnP9U5BSEdy01IaARgzWgeZN_oIDflNCcMR6wr1XC7G_xUqGUrZORL";
                        Title = "猪赵薇";
                        Description = "赵薇是个猪,鼻子有两个孔";
                    }else {
                        MediaId = "j-ZKH82NTEj57p-InVx49bIbg2i826X43BbitzeHDNHaGoUEAb1LjGN3QptdFzkd";
                        Title = "去年夏天";
                        Description = "可能你觉得干脆,才认为你是喜欢我吧";
                    }
                    video.setMediaId(MediaId);
                    video.setTitle(Title);
                    video.setDescription(Description);
                    videoMessage.setVideo(video);
                    responseMessage = MessageUtil.videoMessageToXml(videoMessage);
                } else if (content.contains("音乐")) {
                    // 自动回复音乐
                    MusicMessage musicMessage = new MusicMessage();
                    musicMessage.setToUserName(fromUserName);
                    musicMessage.setFromUserName(toUserName);
                    musicMessage.setCreateTime(new Date().getTime() + "");
                    musicMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC);
                    Music music = new Music();
                    music.setTitle("成全");
                    music.setDescription("林宥嘉");
                    music.setMusicUrl("https://music.163.com/#/song?id=562594267");
                    music.setHQMusicUrl("https://music.163.com/#/song?id=562594267");
                    // 通过在线接口测试工具上传媒体获取的ThumbMediaId
                    music.setThumbMediaId("bRalx1y5z4cxUY0TTA1wkuWky8RAs9jJ2nI8QQ5JDATQ9SmEQSH872T-aUwjVOTm");
                    musicMessage.setMusic(music);
                    responseMessage = MessageUtil.musicMessageToXml(musicMessage);
                } else {
                    if (content.equals("A")) {
                        responseContent = "AAAAAA /猪头/猪头/猪头";
                    } else if (content.equals("B")) {
                        responseContent = "BBBBBBB /悠闲/悠闲/悠闲";
                    } else if (content.equals("C")) {
                        responseContent = "CCCCCCC /大笑 /大笑/大笑";
                    } else if (content.equals("？") || content.equals("?")) {
                        responseContent = "你想干什么？[难过] /难过 /::(";
                    } else if (content.indexOf("征服") != -1) {// 如果消息中包含该字段，回复指定的内容
                        responseContent = "就这样被你征服，切断了所有退路";
                    } else {
                        responseContent = "请你给我发个能看懂的好吗,你这个猪/猪头/猪头/猪头";
                    }
                    //自动回复文本消息
                    BaseMessage text = new BaseMessage();
                    text.setContent(responseContent);
                    text.setToUserName(fromUserName);
                    text.setFromUserName(toUserName);
                    text.setCreateTime(new Date().getTime() + "");
                    text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                    responseMessage = MessageUtil.MessageToXml(text);
                }
            }
            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                String eventType = requestMap.get("Event");// 事件类型
                // 订阅
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    BaseMessage text = new BaseMessage();
                    text.setContent("欢迎关注帅比的个人公众号[坏笑]/坏笑/:B-)");
                    text.setToUserName(fromUserName);
                    text.setFromUserName(toUserName);
                    text.setCreateTime(new Date().getTime() + "");
                    text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                    responseMessage = MessageUtil.MessageToXml(text);
                }
                // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {// 取消订阅
                    String time = DateUtil.simpleDate("");
                    System.out.println(time+":用户("+fromUserName+")取消关注!");
                }
                // 自定义菜单点击事件
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    String eventKey = requestMap.get("EventKey");// 事件KEY值，与创建自定义菜单时指定的KEY值对应
                    if (eventKey.equals("customer_telephone")) {
                        BaseMessage text = new BaseMessage();
                        text.setContent("0755-86671980");
                        text.setToUserName(fromUserName);
                        text.setFromUserName(toUserName);
                        text.setCreateTime(new Date().getTime() + "");
                        text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                        responseMessage = MessageUtil.MessageToXml(text);
                    }
                }
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                // 创建图片消息(回复用)
                Image image = new Image();
                // 通过在线调试工具https://mp.weixin.qq.com/debug?token=1453338883&lang=zh_CN先上传一个图片,得到媒体的MediaId
                String mediaId = "7ZvmJaLrP8kCV3fdNLuRDIvHe-ArR7qQCK1luqxHdlJKYDdCF0Rx6VlmQy6gXEVI";
                image.setMediaId(mediaId);
                // 图片消息(将发过来的图片转发回去)
                ImageMessage imageMessage = new ImageMessage();
                imageMessage.setContent("回复图片消息");
                imageMessage.setToUserName(fromUserName);
                imageMessage.setFromUserName(toUserName);
                imageMessage.setCreateTime(new Date().getTime() + "");
                imageMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_IMAGE);
                imageMessage.setImage(image);
                responseMessage = MessageUtil.imageMessageToXml(imageMessage);
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                // 自动回复语音
                VoiceMessage voiceMessage = new VoiceMessage();
                voiceMessage.setToUserName(fromUserName);
                voiceMessage.setFromUserName(toUserName);
                voiceMessage.setCreateTime(new Date().getTime() + "");
                voiceMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_VOICE);
                Voice voice = new Voice();
                // 通过在线接口测试工具上传媒体获取的MediaId
                voice.setMediaId("rivWBtBqz6Qv2lixmMAhntl24pVC6YUwlwSVwDP-RHJN8ybYarFTnUEQS6T74MvB");
                voiceMessage.setVoice(voice);
                responseMessage = MessageUtil.voiceMessageToXml(voiceMessage);
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)){
                // 接收位置消息
                // 地图经纬度查询http://www.gpsspg.com/maps.htm
                // 地理位置纬度(北京-天安门)
                String Latitude = "39.9088230000";
                // 地理位置经度(北京-天安门)
                String Longitude = "116.3974700000";
                // 地理位置精度
                String Precision = "119.385040";
                // 获取用户发送的位置
                String address = requestMap.get("Label");
                // 获取用户地理位置需要微信认证,暂时无法使用
//                LocationMessage locationMessage = new LocationMessage();
//                locationMessage.setToUserName(fromUserName);
//                locationMessage.setFromUserName(toUserName);
//                locationMessage.setCreateTime(new Date().getTime() + "");
//                locationMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_EVENT);
//                locationMessage.setEvent("LOCATION");
//                locationMessage.setLatitude(Latitude);
//                locationMessage.setLongitude(Longitude);
//                locationMessage.setPrecision(Precision);
//                responseMessage = MessageUtil.MessageToXml(locationMessage);
                BaseMessage text = new BaseMessage();
                text.setContent("您发送的位置为:"+address);
                text.setToUserName(fromUserName);
                text.setFromUserName(toUserName);
                text.setCreateTime(new Date().getTime() + "");
                text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                responseMessage = MessageUtil.MessageToXml(text);
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                // 接收链接消息
                String Url = requestMap.get("Url");
                BaseMessage text = new BaseMessage();
                text.setContent("您发送的链接为:"+Url);
                text.setToUserName(fromUserName);
                text.setFromUserName(toUserName);
                text.setCreateTime(new Date().getTime() + "");
                text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                responseMessage = MessageUtil.MessageToXml(text);
            } else {
                System.out.println(requestMap);
            }
        } catch (Exception e) {
            LOGGER.error("error...........");
        }
        // 日志输出给微信的Xml
        System.out.println(responseMessage);
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");
        return responseMessage;
    }

    /**
      * @Description: 获取access_token
      * @Param:
      * @return: access_token
      * @Author: Zhaolc
      * @Date: 2018/12/19 9:51
      */
    public String httpGetAccessToken(){
        // 获取access_token请求地址
        String tokenUrl = ACCESS_TOKEN_URL;
        System.out.println("请求地址："+tokenUrl);
        String jsonString = WeixinUtil.getURLConnection(tokenUrl);
        System.out.println("返回数据："+jsonString);
        String access_token = "";
        // json数组转为List
        List<String> list = new ArrayList();
        list.add(jsonString);
        // list转JSONArray
        JSONArray jsonArray = JSONArray.fromObject(list);
        // JSONArray转为JSON对象
        for (int i=0;i<jsonArray.size();i++) {
            JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
            access_token = jsonObject.getString("access_token");
        }
        System.out.println("access_token:"+access_token);
        return access_token;
    }

    /**
      * @Description: 获取媒体MediaId
      * @Param: filePath fileType
      * @return: mediaId
      * @Author: Zhaolc
      * @Date: 2018/12/19 17:59
      */
    public String getMediaId(String filePath,String fileType){
        ACCESS_TOKEN = httpGetAccessToken();
        FILE_TYPE = fileType;
        System.out.println("access_token:"+ACCESS_TOKEN+";fileType:"+fileType);
        String mediaId = "";
        try {
            mediaId = WeixinUtil.uploadFile(filePath,ACCESS_TOKEN,FILE_TYPE);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(mediaId);
        return mediaId;
    }
}