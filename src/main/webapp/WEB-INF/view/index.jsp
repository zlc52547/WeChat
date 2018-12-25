<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
</head>
<style>
    .WeChat{
        text-align: center;
        color: #fff;
        -webkit-border-radius: 3px;
        font-size: 14px;
        border: 1px solid #3d810c;
        cursor: pointer;
        background-color: #56a447;
    }
</style>
<body>
<h2>Hello WeChat!</h2>
    <input type="button" class="WeChat" id="btn" value="获取Token"><span>：</span>
    <input type="text" value="" id="token" style="width: 1300px">
<hr>
    <input type="button" class="WeChat" id="btn1" value="获取mediaId"><span>：</span>
    <input type="text" value="" id="mediaId" style="width: 1300px">
<hr>
<h3>新增自定义菜单按钮</h3>
    <form>
        <input type="text" value="">

    </form>
<script type="text/javascript">
    // 获取access_token
    $("#btn").click(function () {
        $.ajax({
            url:'/wechat/token',
            dataType:'text',
            success:function (result) {
                if (result != ''){
                    $("#token").val(result);
                }else {
                    alert('请求失败!')
                }
            }
        })
    })
    // 获取mediaId
    $("#btn1").click(function () {
        // 本地文件路径
        var file = "C:\\Users\\Administrator\\Desktop\\文档\\个人\\HDHeadImage.jpg";
        $.ajax({
            url:'/wechat/mediaId',
            dataType:'text',
            data:{"filePath":file,"fileType":"image"},
            success:function (result) {
                if (result != ''){
                    $("#mediaId").val(result);
                }else {
                    alert('请求失败!')
                }
            }
        })
    })

</script>


</body>
</html>
