<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/wechat/static/js/jweixin-1.2.0.js"></script>
<title>微信分享测试</title>
</head>
<body>用户信息${map}
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
		'openLocation',
		'getLocation',
		'onMenuShareTimeline',
		'onMenuShareAppMessage',
		'onMenuShareQQ',
		'onMenuShareWeibo',
		'onMenuShareQZone'
	]// 必填，需要使用的JS接口列表
});
wx.ready(function () {
	wx.checkJsApi({
		jsApiList: [
			'getLocation',
			'onMenuShareTimeline',
			'onMenuShareAppMessage',
			'onMenuShareQQ',
			'onMenuShareWeibo',
			'onMenuShareQZone'
		],
		success: function (res) {
			alert(JSON.stringify(res));
		}
	});
	wx.onMenuShareQZone({
		title: '小幸运-QQ空间',
		desc: '电影《我的少女时代》主题曲',
		link: 'http://wechat.qiandaoba.cn/upload/xxy.mp3',
		imgUrl: 'http://wechat.qiandaoba.cn/upload/2.jpg',
		type: 'music',
		dataUrl: 'http://wechat.qiandaoba.cn/upload/xxy.mp3',
		success: function () {
			// 用户确认分享后执行的回调函数
		},
		cancel: function () {
			// 用户取消分享后执行的回调函数
		}
	});
	wx.onMenuShareAppMessage({
		title: '小幸运-好友',
		desc: '电影《我的少女时代》主题曲',
		link: 'http://wechat.qiandaoba.cn/upload/xxy.mp3',
		imgUrl: 'http://wechat.qiandaoba.cn/upload/2.jpg',
		type: 'music',
		dataUrl: 'http://wechat.qiandaoba.cn/upload/xxy.mp3',
		trigger: function (res) {
			// 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
			// alert('用户点击发送给朋友');
		},
		success: function (res) {
			// alert('已分享');
		},
		cancel: function (res) {
			// alert('已取消');
		},
		fail: function (res) {
			// alert(JSON.stringify(res));
		}
	});

	wx.onMenuShareTimeline({
		title: '小幸运-朋友圈',
		desc: '电影《我的少女时代》主题曲',
		link: 'http://wechat.qiandaoba.cn/upload/xxy.mp3',
		imgUrl: 'http://wechat.qiandaoba.cn/upload/2.jpg',
		type: 'music',
		dataUrl: 'http://wechat.qiandaoba.cn/upload/xxy.mp3',
		trigger: function (res) {
			// 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
			// alert('用户点击分享到朋友圈');
		},
		success: function (res) {
			// alert('已分享');
		},
		cancel: function (res) {
			// alert('已取消');
		},
		fail: function (res) {
			// alert(JSON.stringify(res));
		}
	});
	wx.onMenuShareQQ({
		title: '小幸运-QQ',
		desc: '电影《我的少女时代》主题曲',
		link: 'http://wechat.qiandaoba.cn/upload/xxy.mp3',
		imgUrl: 'http://wechat.qiandaoba.cn/upload/2.jpg',
		type: 'music',
		dataUrl: 'http://wechat.qiandaoba.cn/upload/xxy.mp3',
		success: function () {
			// 用户确认分享后执行的回调函数
		},
		cancel: function () {
			// 用户取消分享后执行的回调函数
		}
	});
	wx.onMenuShareWeibo({
		title: '小幸运-微博',
		desc: '电影《我的少女时代》主题曲',
		link: 'http://wechat.qiandaoba.cn/upload/xxy.mp3',
		imgUrl: 'http://wechat.qiandaoba.cn/upload/2.jpg',
		type: 'music',
		dataUrl: 'http://wechat.qiandaoba.cn/upload/xxy.mp3',
		success: function () {
			// 用户确认分享后执行的回调函数
		},
		cancel: function () {
			// 用户取消分享后执行的回调函数
		}
	});
});

</script>
</html>