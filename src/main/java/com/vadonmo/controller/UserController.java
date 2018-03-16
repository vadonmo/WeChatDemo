package com.vadonmo.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

	/**
	 * 获取用户增减数据
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("getSummary")
	protected void getUserSummary(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		String endDate = sdf.format(calendar.getTime());
		calendar.add(Calendar.DAY_OF_MONTH, -6);
		String startDate = sdf.format(calendar.getTime());
		String user = WeixinGateway.getUserSummary(startDate, endDate);
		PrintWriter out = response.getWriter();
		out.print(user);
		out.close();
	}

	/**
	 * 获取累计用户数据
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("getCumulate")
	protected void getUserCumulate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		String endDate = sdf.format(calendar.getTime());
		calendar.add(Calendar.DAY_OF_MONTH, -6);
		String startDate = sdf.format(calendar.getTime());
		String user = WeixinGateway.getUserCumulate(startDate, endDate);
		PrintWriter out = response.getWriter();
		out.print(user);
		out.close();
	}
}
