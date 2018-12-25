package org.cs.weixin.menu.service.impl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.cs.weixin.menu.model.Button;
import org.cs.weixin.menu.model.ClickButton;
import org.cs.weixin.menu.model.Menu;
import org.cs.weixin.menu.model.ViewButton;
import org.cs.weixin.menu.service.IMenuService;
import org.cs.weixin.message.service.impl.WechatService;
import org.cs.weixin.util.HttpUtils;

import javax.annotation.Resource;

/**
 * Created by zlc on 2018/12/24.
 * Descriptor: TODO 自定义菜单业务
 */
public class MenuService implements IMenuService {

    @Resource
    WechatService wechatService;

    private static String menuUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    public static void main(String[] args){

        // 一级菜单
        Menu menu = new Menu();
        ClickButton clickOne = new ClickButton();
        clickOne.setType("click");
        clickOne.setName("今日歌曲");
        clickOne.setKey("V1001_TODAY_MUSIC");
        ClickButton clickTwo = new ClickButton();
        clickTwo.setName("菜单");
        // 二级菜单
        ViewButton vbtn = new ViewButton();
        vbtn.setType("view");
        vbtn.setName("搜索");
        vbtn.setUrl("http://www.soso.com/");
        ViewButton vbt1 = new ViewButton();
        vbt1.setType("miniprogram");
        vbt1.setName("wxa");
        vbt1.setUrl("http://mp.weixin.qq.com");
        ClickButton cbt = new ClickButton();
        cbt.setType("click");
        cbt.setName("赞一下我们");
        cbt.setKey("V1001_GOOD");
        // 将二级菜单加入一级菜单
        clickTwo.setSub_button(new Button[]{vbtn,vbt1,cbt});
        // 组装一级菜单
        menu.setButton(new Button[]{clickOne,clickTwo});
        // 将Java对象转换为json字符串
        JSONObject jsonObject =JSONObject.fromObject(menu);
        System.out.println(jsonObject);
    }

    /**
      * @Description: 创建菜单按钮
      * @Param: 需要在微信公众平台配置js安全域名,暂停开发
      * @return:
      * @Author: Zhaolc
      * @Date: 2018/12/24 15:57
      */
    @Override
    public String createMenu() {
        ClickButton clickButton = new ClickButton();
        clickButton.setKey("search");
        clickButton.setName("搜索");
        clickButton.setType("view");

        ViewButton viewButton = new ViewButton();
        viewButton.setUrl("www.baidu.com");
        viewButton.setName("百度一下");
        viewButton.setType("view");

        JSONArray sub_button = new JSONArray();
        sub_button.add(clickButton);
        sub_button.add(viewButton);

        JSONObject buttonObj = new JSONObject();
        buttonObj.put("name","菜单");
        buttonObj.put("sub_button",sub_button);

        JSONArray button = new JSONArray();
        button.add(viewButton);
        button.add(buttonObj);
        button.add(clickButton);

        JSONObject menuJson = new JSONObject();
        menuJson.put("button",button);

        String access_token = wechatService.httpGetAccessToken();
        if (access_token != ""){
            menuUrl = menuUrl.replace("ACCESS_TOKEN",access_token);
        }

        String rs = HttpUtils.sendPost(menuUrl,menuJson);
        System.out.println(rs);
        return null;
    }


}
