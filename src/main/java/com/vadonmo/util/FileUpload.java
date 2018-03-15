package com.vadonmo.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

public class FileUpload {
	// 使用Apache文件上传组件处理文件上传步骤：
	// 1、创建一个DiskFileItemFactory工厂
	private static DiskFileItemFactory factory = new DiskFileItemFactory();
	// 2、创建一个文件上传解析器
	private static ServletFileUpload upload = new ServletFileUpload(factory);

	@SuppressWarnings({ "finally", "unchecked" })
	public static Map<String, List<String>> uploadFile(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String savePath = "D:/upload";// Global.ABSOLUTE_URL;
		List<String> contentList = new ArrayList<String>();// 普通文本
		List<String> mediaList = new ArrayList<String>();// 实验报告文件
		// 消息提示
		String message = "";
		Map<String, List<String>> resultMap = new HashMap<String, List<String>>();// 返回结果
		// 3、判断提交上来的数据是否是上传表单的数据
		try {
			if (!ServletFileUpload.isMultipartContent(request)) {
				// 按照传统方式获取数据
				return null;
			}
			// 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
			List<FileItem> list = upload.parseRequest(new ServletRequestContext(request));
			// 解决上传文件名的中文乱码
			upload.setHeaderEncoding("UTF-8");

			Map<String, Object> map = getFormFieldAndPath(list);
			contentList = (List<String>) map.get("contentList");

			for (FileItem item : list) {
				// 如果fileitem中封装的是普通输入项的数据
				if (!item.isFormField()) {
					// 如果fileitem中封装的是上传文件
					// 得到上传的文件名称，
					String filename = item.getName();
					System.out.println(filename);
					// 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如： c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
					// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					filename = filename.substring(filename.lastIndexOf("\\") + 1);

					if (filename != null && !"".equals(filename.trim())) {
						filename = makeFileName(filename);// 新文件名称，uuid+"_"+文件的原始名称
					} else {// 没有文件上传
						continue;
					}

					// 判断文件类型（视频flv，图片jpg、png）
					int dotIndex = filename.lastIndexOf(".");
					if (dotIndex > 0) {
						// String filetype = filename.substring(dotIndex + 1).toLowerCase();
						mediaList.add(savePath + "/" + filename);
					}

					File file = new File(savePath);
					// 判断上传文件的保存目录是否存在
					if (!file.exists() && !file.isDirectory()) {
						System.out.println(savePath + "目录不存在，需要创建");
						// 创建目录
						file.mkdirs();
					}

					// 获取item中的上传文件的输入流
					InputStream in = item.getInputStream();
					// 创建一个文件输出流
					FileOutputStream out = new FileOutputStream(savePath + "\\" + filename);
					// 创建一个缓冲区
					byte buffer[] = new byte[1024];
					// 判断输入流中的数据是否已经读完的标识
					int len = 0;
					// 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
					while ((len = in.read(buffer)) > 0) {
						// 使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
						out.write(buffer, 0, len);
					}
					// 关闭输入流
					in.close();
					// 关闭输出流
					out.close();
					// 删除处理文件上传时生成的临时文件
					item.delete();
				}
			}
			resultMap.put("content", contentList);
			resultMap.put("mediafile", mediaList);
			message = "true";
		} catch (FileUploadBase.FileSizeLimitExceededException e) {
			e.printStackTrace();
			message = "单个文件超出最大值！！！";
		} catch (FileUploadBase.SizeLimitExceededException e) {
			e.printStackTrace();
			message = "上传文件的总的大小超出限制的最大值！！！";
		} catch (Exception e) {
			message = "文件上传失败！";
			e.printStackTrace();
		} finally {
			List<String> msgList = new ArrayList<String>();// 提示信息
			msgList.add(message);
			resultMap.put("message", msgList);
//			PrintWriter out = response.getWriter();
//			out.flush();
//			out.println(message);
			return resultMap;
		}
	}

	/**
	 * 生成上传文件的文件名，文件名以：uuid+"_"+文件的原始名称
	 * 
	 * @param filename
	 *            文件的原始名称
	 * @return uuid+"_"+文件的原始名称
	 * @author xmx
	 * @Time 2015年7月6日 下午3:47:33
	 */
	private static String makeFileName(String filename) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String now = sdf.format(new Date());
		// 为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
		return now + UUID.randomUUID().toString() + "_" + filename;
	}

	/**
	 * 获取form表单中非上传文件的标签值
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 * @return Map<String,Object>
	 * @author jxc
	 * @Time 2015年7月15日 下午4:13:30
	 */
	private static Map<String, Object> getFormFieldAndPath(List<FileItem> list) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String savePath = "";

		// 解决上传文件名的中文乱码
		upload.setHeaderEncoding("UTF-8");
		List<String> contentList = new ArrayList<String>();// 普通文本

		for (FileItem item : list) {
			// 如果fileitem中封装的是普通输入项的数据
			String name = item.getFieldName();
			if (item.isFormField()) {
				// 解决普通输入项的数据的中文乱码问题
				String value = item.getString("UTF-8");
				contentList.add(value);
				if (name.length() > 4 && name.substring(0, 4).equals("path")) {
					savePath += "/" + value;
				}
			}
		}

		map.put("contentList", contentList);
		map.put("savePath", savePath);
		return map;
	}
}
