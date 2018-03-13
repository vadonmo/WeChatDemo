package com.vadonmo.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vadonmo.model.KfAccount;
import com.vadonmo.service.WeixinGateway;

/**
 * 客服相关操作
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("custom")
public class CustomController {

	public static void main(String args[]) throws Exception {
		KfAccount kfAccount = new KfAccount();
		kfAccount.setKf_account("test1@test");
		kfAccount.setNickname("客服1");
		kfAccount.setPassword("pswmd5");
		String result = WeixinGateway.getKfList();
		///String result = WeixinGateway.addKfAccount(kfAccount);
		System.out.println(result);
	}

	/**
	 * 获取客服基本信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("getkflist")
	protected void getKfList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String result = WeixinGateway.getKfList();
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}

	/**
	 * 添加客服帐号
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("kfaccount/add")
	protected void addKfAccount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		KfAccount kfAccount = new KfAccount();
		kfAccount.setKf_account("test1@test");
		kfAccount.setNickname("客服1");
		kfAccount.setPassword("pswmd5");
		String result = WeixinGateway.addKfAccount(kfAccount);
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}

}
