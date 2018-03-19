<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/wechat/static/js/jweixin-1.2.0.js"></script>
<!-- head 中 -->
<link rel="stylesheet"
	href="https://cdn.bootcss.com/weui/1.1.2/style/weui.min.css">
<link rel="stylesheet"
	href="https://cdn.bootcss.com/jquery-weui/1.2.0/css/jquery-weui.min.css">
<title>微信图像接口</title>
</head>
<body>
	<a href="javascript: chooseImage();" class="weui-btn weui-btn_primary">选择图片</a>
	<a href="javascript: previewImage();" class="weui-btn weui-btn_primary">预览图片</a>
	<a href="javascript: uploadImage();" class="weui-btn weui-btn_primary">上传图片</a>
	<a href="javascript: downloadImage();" class="weui-btn weui-btn_primary">下载图片</a>
	<a href="javascript: getLocalImgData();" class="weui-btn weui-btn_primary">获取本地图片</a>
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
		'chooseImage',
		'previewImage',
		'uploadImage',
		'downloadImage',
		'getLocalImgData'
	]// 必填，需要使用的JS接口列表
});
wx.ready(function () {
	wx.checkJsApi({
		jsApiList: [
			'chooseImage',
			'previewImage',
			'uploadImage',
			'downloadImage',
			'getLocalImgData'
		],
		success: function (res) {
			alert(JSON.stringify(res));
		}
	});
	
});
var localIds;
var serverIds = new Array();
function chooseImage() {
	wx.chooseImage({
		count: 9, // 默认9
		sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
		sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
		success: function (res) {
			localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
			console.log(localIds);
		}
	});
}
function previewImage() {
	wx.previewImage({
		current: '', // 当前显示图片的http链接
		urls: localIds // 需要预览的图片http链接列表
	});
}
function uploadImage() {
	for(var i = 0; i < localIds.length; i++){
		wx.uploadImage({
			localId: localIds[i], // 需要上传的图片的本地ID，由chooseImage接口获得
			isShowProgressTips: 1, // 默认为1，显示进度提示
			success: function (res) {
				var serverId = res.serverId; // 返回图片的服务器端ID
				serverIds.push(serverId);
			}
		});
	}
}
function downloadImage() {
	wx.downloadImage({
		serverId: serverIds[0], // 需要下载的图片的服务器端ID，由uploadImage接口获得
		isShowProgressTips: 1, // 默认为1，显示进度提示
		success: function (res) {
			var localId = res.localId; // 返回图片下载后的本地ID
			localIds[0] = localId;
		}
	});
}
function getLocalImgData() {
	wx.getLocalImgData({
		localId: localIds[0], // 图片的localID
		success: function (res) {
			var localData = res.localData; // localData是图片的base64数据，可以用img标签显示
		}
	});
}
</script>
</html>