微信公众号开发：接口地址

辅助工具：
#微信公众号开发者文档：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1445241432
#微信公众平台接口调试工具：https://mp.weixin.qq.com/debug/


1、获取access_token
    https请求方式: GET
    https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
2、新增临时素材
    http请求方式：POST/FORM，使用https
    https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE
    调用示例（使用curl命令，用FORM表单方式上传一个多媒体文件）：
    curl -F media=@test.jpg "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE"
3、获取临时素材
    http请求方式: GET,https调用
    https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID
    请求示例（示例为通过curl命令获取多媒体文件）
    curl -I -G "https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID"
    获取素材的MediaId
    http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=fileType
4、新增永久图文素材
    http请求方式: POST，https协议
    https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=ACCESS_TOKEN
5、获取永久素材
    http请求方式: POST,https协议
    https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN
6、删除永久素材
    http请求方式: POST
    https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=ACCESS_TOKEN
7、自定义菜单创建接口
    http请求方式：POST（请使用https协议）https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN
8、
9、
10、
11、
12、
