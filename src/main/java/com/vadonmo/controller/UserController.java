package com.vadonmo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vadonmo.service.WeixinGateway;

@Controller
@RequestMapping("user")
public class UserController {

	/**
	 * 获取某位粉丝的信息
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("info")
	protected String getUserInfo(HttpServletResponse response, HttpServletRequest request, Model model)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String user = WeixinGateway.getUserInfo("o95c7uLi7DAvQQvIZHf3ql8vI8c8");
		model.addAttribute("user", user);
		return "user";
	}

	/**
	 * 获取粉丝列表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	protected String getUserList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String user = WeixinGateway.getUserList("");
		model.addAttribute("user", user);
		return "user";
	}

	/**
	 * 获取黑名单列表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getBlackList")
	protected String getUserBlackList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String user = WeixinGateway.getUserBlackList("");
		model.addAttribute("user", user);
		return "user";
	}
}
