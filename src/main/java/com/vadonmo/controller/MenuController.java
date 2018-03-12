package com.vadonmo.controller;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vadonmo.service.WeixinGateway;

@Controller
@RequestMapping("/menu")
public class MenuController {

	public static void main(String args[]) {
		System.out.println(new Date().getTime());
	}

	/**
	 * 创建菜单
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("createMenu")
	public void createMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String jsonStr = "{ \"button\": [{ \"name\": \"扫码\", \"sub_button\": [{ \"type\": "
				+ "\"scancode_waitmsg\", \"name\": \"扫码带提示\", \"key\": \"rselfmenu_0_0\", "
				+ "\"sub_button\": [] }, { \"type\": \"scancode_push\", \"name\": \"扫码推事件\", "
				+ "\"key\": \"rselfmenu_0_1\", \"sub_button\": [] }] }, { \"name\": \"发图\", "
				+ "\"sub_button\": [{ \"type\": \"pic_sysphoto\", \"name\": \"系统拍照发图\", "
				+ "\"key\": \"rselfmenu_1_0\", \"sub_button\": [] }, { \"type\": \"pic_photo_or_album\", "
				+ "\"name\": \"拍照或者相册发图\", \"key\": \"rselfmenu_1_1\", \"sub_button\": [] }, "
				+ "{ \"type\": \"pic_weixin\", \"name\": \"微信相册发图\", \"key\": \"rselfmenu_1_2\", "
				+ "\"sub_button\": [] }, { \"type\": \"click\", \"name\": \"今日歌曲\", \"key\": \"V1001_TODAY_MUSIC\" } ] }, { \"name\": \"发送位置\", \"type\": \"location_select\", \"key\": "
				+ "\"rselfmenu_2_0\" }] }";
		String result = WeixinGateway.createMenu(jsonStr);
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}

	/**
	 * 查询菜单
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("getMenu")
	public void getMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String result = WeixinGateway.getMenu();
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}

	/**
	 * 删除菜单
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("deleteMenu")
	public void deleteMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String result = WeixinGateway.deleteMenu();
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}
}
