package com.vadonmo.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vadonmo.service.WeixinGateway;
import com.vadonmo.util.Global;
import com.vadonmo.util.SignUtil;

@Controller
@RequestMapping("jssdkShop")
public class JSSDKShopController {
	@RequestMapping("")
	protected String getShop(HttpServletResponse response, HttpServletRequest request, Model model) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String param = request.getQueryString();
		String url = "http://" + request.getServerName() // 服务器地址
				+ request.getContextPath() // 项目名称
				+ request.getServletPath() // 请求页面或其他地址
				+ (param == null ? "" : "?" + param); // 参数
		// String url = request.getRequestURL().toString();
		Map<String, String> map = SignUtil.sign(WeixinGateway.getStaticJsapiTiket(), url);
		model.addAttribute("map", map);
		model.addAttribute("appId", Global.APPID);
		return "jssdkShop";
	}

}
