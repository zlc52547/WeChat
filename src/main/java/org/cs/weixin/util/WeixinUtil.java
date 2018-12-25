package org.cs.weixin.util;

import net.sf.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zlc on 2018/6/3.
 * 微信工具类
 */
public class WeixinUtil {
    private static final String APPID="wx6edd4c71d8777f31";   //在基础配置中可查看自己APPID
    private static final String APPSECRET="050f5b650b0992755ad067fa4d4dc644";   //在基础配置中可查看自己APPSECRET
    // 获取ACCESS_TOKEN的Url
    private static final String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+APPSECRET;
    // 上传文件Url
    private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
    // 获取永久素材MediaId的Url
    private static final String getMediaId = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN";


    public static String getURLConnection(String requsetUrl){
        try {
            URL url = new URL(requsetUrl);
            // 建立连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() == 200) {
                // 获取输入流
                InputStream in = conn.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = in.read(bytes)) != -1) {
                    baos.write(bytes, 0, len);
                    baos.flush();
                }
                return baos.toString("UTF-8");
            }
        }catch (IOException i){
            i.printStackTrace();
        }
        return "";
    }

    // 上传素材文件
    public static String uploadFile(String filePath, String accessToken, String type) throws Exception{
        File file = new File(filePath);
        if(!file.exists() || !file.isFile()) {
            throw new IOException("文件不存在！");
        }

        String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
        URL urlObj = new URL(url);

        // 建立连接
        HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
        // 设置连接属性
        conn.setDoInput(true);// 使用 URL 连接进行输入
        conn.setDoOutput(true);// 使用 URL 连接进行输出
        conn.setUseCaches(false);// 不允许缓存
        conn.setRequestMethod("POST");// 设置URL请求方式

        // 设置请求属性
        conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
        conn.setRequestProperty("Charset", "UTF-8");
//        conn.setRequestProperty("Content-Type", "application/json");
        //conn.setRequestProperty("Content-Type","multipart/form-data;");

        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        conn.setRequestProperty("Content-Type","multipart/form-data;boundary="+BOUNDARY);

        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition:form-data;name=\"file\";filename=\""+file.getName()+"\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");

        byte[] head = sb.toString().getBytes("utf-8");

        // 输出流
        OutputStream out = new DataOutputStream(conn.getOutputStream());

        out.write(head);

        // 文件正文部分
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while((bytes = in.read(bufferOut))!=-1) {
            out.write(bufferOut,0,bytes);
        }
        in.close();

        // 结尾
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");
        out.write(foot);
        out.flush();
        out.close();

        // 获取响应
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        String result = null;

        reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = null;
        while((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        if(result == null) {
            result = buffer.toString();
        }
        reader.close();

        // 需要添加json-lib  jar包
        JSONObject json = JSONObject.fromObject(result);
        System.out.println(json);
        String mediaId = json.getString("media_id");

        return mediaId;
    }
}
