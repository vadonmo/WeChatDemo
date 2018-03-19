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
<title>微信音频接口</title>
</head>
<body>
	<a href="javascript: startRecord();" class="weui-btn weui-btn_primary">开始录音</a>
	<a href="javascript: stopRecord();" class="weui-btn weui-btn_primary">停止录音</a>
	<a href="javascript: playVoice();" class="weui-btn weui-btn_primary">播放语音</a>
	<a href="javascript: pauseVoice();" class="weui-btn weui-btn_primary">暂停播放</a>
	<a href="javascript: stopVoice();" class="weui-btn weui-btn_primary">停止播放</a>
	<a href="javascript: uploadVoice();" class="weui-btn weui-btn_primary">上传语音</a>
	<a href="javascript: downloadVoice();" class="weui-btn weui-btn_primary">下载语音</a>
	<a href="javascript: translateVoice();" class="weui-btn weui-btn_primary">识别语音</a>
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
		'startRecord',
		'stopRecord',
		'onVoiceRecordEnd',
		'playVoice',
		'pauseVoice',
		'stopVoice',
		'onVoicePlayEnd',
		'uploadVoice',
		'downloadVoice',
		'translateVoice'
	]// 必填，需要使用的JS接口列表
});
var localId;
var serverId;
wx.ready(function () {
	wx.checkJsApi({
		jsApiList: [
			'startRecord',
			'stopRecord',
			'onVoiceRecordEnd',
			'playVoice',
			'pauseVoice',
			'stopVoice',
			'onVoicePlayEnd',
			'uploadVoice',
			'downloadVoice',
			'translateVoice'
		],
		success: function (res) {
			alert(JSON.stringify(res));
		}
	});
	wx.onVoiceRecordEnd({
		// 录音时间超过一分钟没有停止的时候会执行 complete 回调
		complete: function (res) {
			localId = res.localId;
		}
	});
	wx.onVoicePlayEnd({
		success: function (res) {
			localId = res.localId; // 返回音频的本地ID
		}
	});
	
});

function startRecord() {
	wx.startRecord();
}
function stopRecord() {
	wx.stopRecord({
		success: function (res) {
			localId = res.localId;
		}
	});
}

function playVoice() {
	wx.playVoice({
		localId: localId // 需要播放的音频的本地ID，由stopRecord接口获得
	});
}
function pauseVoice() {
	wx.pauseVoice({
		localId: localId // 需要暂停的音频的本地ID，由stopRecord接口获得
	});
}
function stopVoice() {
	wx.pauseVoice({
		localId: localId // 需要暂停的音频的本地ID，由stopRecord接口获得
	});
}
function uploadVoice() {
	wx.uploadVoice({
		localId: localId, // 需要上传的音频的本地ID，由stopRecord接口获得
		isShowProgressTips: 1, // 默认为1，显示进度提示
		success: function (res) {
			serverId = res.serverId; // 返回音频的服务器端ID
		}
	});
}
function downloadVoice() {
	wx.downloadVoice({
		serverId: serverId, // 需要下载的音频的服务器端ID，由uploadVoice接口获得
		isShowProgressTips: 1, // 默认为1，显示进度提示
		success: function (res) {
			localId = res.localId; // 返回音频的本地ID
		}
	});
}
function translateVoice() {
	wx.translateVoice({
		localId: localId, // 需要识别的音频的本地Id，由录音相关接口获得
		isShowProgressTips: 1, // 默认为1，显示进度提示
		success: function (res) {
			alert(res.translateResult); // 语音识别的结果
		}
	});
}
</script>
</html>