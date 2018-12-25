package org.cs.weixin.message.controller;

/**
 * Created by zlc on 2018/5/31.
 * 微信接口控制器
 */

import org.cs.weixin.message.service.impl.WechatService;
import org.cs.weixin.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/wechat")
public class WechatController {

    private static final String Token ="zhaolinchao";
    private static final Logger LOGGER = LoggerFactory.getLogger(WechatController.class);

    @Resource
    WechatService wechatService;

    /**
     * 微信接入
     * @param
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/connect", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void connect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        //微信服务器POST消息时用的是UTF-8编码，在接收时也要用同样的编码，否则中文会乱码；
        response.setCharacterEncoding("UTF-8");
        //在响应消息（回复消息给用户）时，也将编码方式设置为UTF-8，原理同上；
        PrintWriter out = response.getWriter();
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        try {
            if (isGet) {
                String signature = request.getParameter("signature");// 微信加密签名
                String timestamp = request.getParameter("timestamp");// 时间戳
                String nonce = request.getParameter("nonce");// 随机数
                String echostr = request.getParameter("echostr");//随机字符串
                // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
                if (SignUtil.checkSignature(Token, signature, timestamp, nonce)) {
                    LOGGER.info("微信服务器连接成功！");
                    response.getWriter().write(echostr);
                } else {
                    LOGGER.error("验证签名(signature)失败！");
                }
            } else {
                String respMessage = "异常消息！";
                try {
                    respMessage = wechatService.weixinPost(request);
                    out.write(respMessage);
                    LOGGER.info("请求成功！");
                    LOGGER.info("微信服务器" + respMessage);
                } catch (Exception e) {
                    LOGGER.error("未能将信息从微信转换！");
                }
            }
        } catch (Exception e) {
            LOGGER.error("微信服务器连接失败!");
        } finally {
            out.close();
        }
    }

    /**
      * @Description: 获取access_token
      * @Param:
      * @return: access_token
      * @Author: Zhaolc
      * @Date: 2018/12/19 15:09
      */
    @RequestMapping(value = "token")
    @ResponseBody
    public String token(){
        String Token = "";
        Token = wechatService.httpGetAccessToken();
        return Token;
    }

    /**
      * @Description: 获取媒体MediaId
      * @Param: filePath,fileType
      * @return: mediaId
      * @Author: Zhaolc
      * @Date: 2018/12/20 9:04
      */
    @RequestMapping(value = "mediaId")
    @ResponseBody
    public String mediaId(String filePath,String fileType){
        String mediaId = "";
        mediaId = wechatService.getMediaId(filePath,fileType);
        return mediaId;
    }
}
