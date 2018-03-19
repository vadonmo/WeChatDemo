<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/wechat/static/js/jweixin-1.2.0.js"></script>
<!-- head 中 -->
<link rel="stylesheet"
	href="https://cdn.bootcss.com/weui/1.1.2/style/weui.min.css">
<link rel="stylesheet"
	href="https://cdn.bootcss.com/jquery-weui/1.2.0/css/jquery-weui.min.css">
<title>微信设备信息</title>
</head>
<body>
	<a href="javascript: getNetworkType();"
		class="weui-btn weui-btn_primary">获取网络状态接口</a>
	<!-- body 最后 -->
	<script src="https://cdn.bootcss.com/jquery/1.11.0/jquery.min.js"></script>
	<script
		src="https://cdn.bootcss.com/jquery-weui/1.2.0/js/jquery-weui.min.js"></script>
</body>
<script type="text/javascript">
wx.config({
	debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	appId: '${appId}', // 必填，公众号的唯一标识
	timestamp: ${map.timestamp}, // 必填，生成签名的时间戳
	nonceStr: '${map.nonceStr}', // 必填，生成签名的随机串
	signature: '${map.signature}', // 必填，签名
	jsApiList: [
		// 所有要调用的 API 都要加到这个列表中
		'checkJsApi',
		'getNetworkType'
	]// 必填，需要使用的JS接口列表
});
var localId;
var serverId;
wx.ready(function () {
	wx.checkJsApi({
		jsApiList: [
			'getNetworkType'
		],
		success: function (res) {
			alert(JSON.stringify(res));
		}
	});
});

function getNetworkType() {
	wx.getNetworkType({
		success: function (res) {
			var networkType = res.networkType; // 返回网络类型2g，3g，4g，wifi
			alert(networkType);
		}
	});
}
</script>
</html>