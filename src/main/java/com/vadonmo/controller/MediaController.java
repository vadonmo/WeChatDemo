package com.vadonmo.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vadonmo.service.WeixinGateway;
import com.vadonmo.util.FileUpload;

@Controller
@RequestMapping("media")
public class MediaController {

	@RequestMapping("")
	protected String showMedia(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		return "media";

	}

	/**
	 * 获取素材
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("getMaterial/{type}")
	protected void getMaterial(@PathVariable("type") String type, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String result = WeixinGateway.getMaterialList(type, 0, 20);
		PrintWriter out = response.getWriter();
		out.print(result);
		out.close();
	}

	@RequestMapping("upload")
	protected void uploadMedia(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Map<String, List<String>> resultMap = FileUpload.uploadFile(request, response);
		int count = resultMap.size();
		String result = "保存失败";
		String mediaType = "image/jpeg";
		if (count > 0) {
			List<String> messageList = resultMap.get("message");
			if (!"true".equals(messageList.get(0))) {

			} else {
				// 保存信息
				for (String key : resultMap.keySet()) {
					List<String> list = resultMap.get(key);
					if (key.equals("mediafile")) {
						result = list.get(0);
						// WeixinGateway.uploadPermanentMedia2(new File(result), "标题", "描述");
					} else if (key.equals("contentList")) {
						mediaType = list.get(1);
					}
				}
				result = WeixinGateway.uploadPermanentMedia2(new File(result), "标题", "描述", mediaType);
			}
		}
		PrintWriter out = response.getWriter();
		out.print(result);
		out.close();
	}

	/**
	 * 删除素材
	 * 
	 * @param mediaId
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("delete/{mediaId}")
	protected void deleteMaterial(@PathVariable("mediaId") String mediaId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String result = WeixinGateway.deleteMaterial(mediaId);
		PrintWriter out = response.getWriter();
		out.print(result);
		out.close();
	}
}
