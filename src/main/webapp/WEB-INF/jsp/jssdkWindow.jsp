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
<title>微信界面设置</title>
</head>
<body>
	<a href="javascript: closeWindow();" class="weui-btn weui-btn_primary">关闭当前网页窗口接口</a>
	<a href="javascript: hideMenuItems();"
		class="weui-btn weui-btn_primary">批量隐藏功能按钮接口</a>
	<a href="javascript: showMenuItems();"
		class="weui-btn weui-btn_primary">批量显示功能按钮接口</a>
	<a href="javascript: hideAllNonBaseMenuItem();"
		class="weui-btn weui-btn_primary">隐藏所有非基础按钮接口</a>
	<a href="javascript: showAllNonBaseMenuItem();"
		class="weui-btn weui-btn_primary">显示所有功能按钮接口</a>
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
		'closeWindow',
		'hideMenuItems',
		'showMenuItems',
		'hideAllNonBaseMenuItem',
		'showAllNonBaseMenuItem'
	]// 必填，需要使用的JS接口列表
});
var localId;
var serverId;
wx.ready(function () {
	wx.checkJsApi({
		jsApiList: [
			'closeWindow',
			'hideMenuItems',
			'showMenuItems',
			'hideAllNonBaseMenuItem',
			'showAllNonBaseMenuItem'
		],
		success: function (res) {
			alert(JSON.stringify(res));
		}
	});
});

function closeWindow() {
	wx.closeWindow();
}
function hideMenuItems() {
	wx.hideMenuItems({
		menuList: [
			'menuItem:readMode', // 阅读模式
	        'menuItem:share:timeline', // 分享到朋友圈
	        'menuItem:copyUrl' // 复制链接
		], // 要隐藏的菜单项，只能隐藏“传播类”和“保护类”按钮，所有menu项见附录3
		success: function (res) {
			alert('已隐藏“阅读模式”，“分享到朋友圈”，“复制链接”等按钮');
		},
		fail: function (res) {
			alert(JSON.stringify(res));
		}
	});
}
function showMenuItems() {
	wx.showMenuItems({
		menuList: [
			'menuItem:readMode', // 阅读模式
	        'menuItem:share:timeline', // 分享到朋友圈
	        'menuItem:copyUrl' // 复制链接
		], // 要隐藏的菜单项，只能隐藏“传播类”和“保护类”按钮，所有menu项见附录3
		success: function (res) {
			alert('已显示“阅读模式”，“分享到朋友圈”，“复制链接”等按钮');
		},
		fail: function (res) {
			alert(JSON.stringify(res));
		}
	});
}
function hideAllNonBaseMenuItem() {
	wx.hideAllNonBaseMenuItem();
}
function showAllNonBaseMenuItem() {
	wx.showAllNonBaseMenuItem();
}
</script>
</html>