<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="/wechat/static/js/jquery-3.3.1.min.js"></script>
<title>素材管理</title>
</head>
<body>
	<form id="form" action="/wechat/media/upload"
		enctype="multipart/form-data" method="post">
		<input name="media" type="file"><br> <select name="type">
			<option value="image/jpeg">图片</option>
			<option value="voice">语音</option>
			<option value="video">视频</option>
			<option value="thumb">缩略图</option>
		</select> <input type="button" onclick="upload()" value="上传">
	</form>
</body>
<script type="text/javascript">
	function upload() {
		$.ajax({
			type : "POST",//方法类型
			dataType : "html",//预期服务器返回的数据类型
			url : "/wechat/media/upload",//url
			data : new FormData($('#form')[0]),
			cache : false,
			processData : false,
			contentType : false,
			success : function(result) {
				console.log(result);//打印服务端返回的数据(调试用)
				if (result.resultCode == 200) {
					alert(result);
				}
			},
			error : function() {
				alert("异常！");
			}
		});
	}
</script>
</html>