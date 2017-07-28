package com.bw.zhongenguang.entity.util;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
//工具类运行在Action类中，所以下面代码是通过获取服务器上传路径后将文件和文件名上传。
public class FileUpload2 {
   /*1上传2下载*/
	public static String upload(String fileName,File myfile) throws Exception{
		if(fileName!=null){
			//用UUID来解决同名文件
			fileName = UUID.randomUUID().toString().replace("-", "")+fileName.substring(fileName.lastIndexOf("."));
			System.out.println("fileName2====="+fileName);
			//得到服务器上传的路径
			HttpServletRequest request = ServletActionContext.getRequest();
			String path = request.getSession().getServletContext().getRealPath("")+"\\upload\\";
			System.out.println("path==="+path);
			
			//创建一个新文件
			File newFile = new File(path+fileName);
			
			//上传
			FileUtils.copyFile(myfile, newFile);
		}else{
			 fileName = "";
		}
		
		return fileName;
	}
	public static String download(String filepath,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		OutputStream fos = null;
		InputStream fis = null;

		try {
		// 如果是从服务器上取就用这个获得系统的绝对路径方法。
		//String filepath = request.getRealPath(filepatha);//方法过时了
		String filepathall = request.getSession().getServletContext().getRealPath(filepath);

		File uploadFile = new File(filepathall);

		fis = new FileInputStream(uploadFile);
		bis = new BufferedInputStream(fis);
		fos = response.getOutputStream();
		bos = new BufferedOutputStream(fos);
		System.out.println("Download---filepath"+filepath);
		//得到文件名
		String filename = filepath.substring(filepath.lastIndexOf("\\")+1);

		// 这个就就是弹出下载对话框的关键代码
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(filename, "utf-8"));
		
		int bytesRead = 0;
		// 用输出流去写，缓冲输入输出流
		byte[] buffer = new byte[8192];
		while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) {
		bos.write(buffer, 0, bytesRead);
		}

		} catch (FileNotFoundException e) {
		e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
		} catch (NumberFormatException e) {
		e.printStackTrace();
		} finally {
		try {
		if (bos != null) {
		bos.flush();
		}
		if (fis != null) {
		fis.close();
		}
		if (bis != null) {
		bis.close();
		}
		if (fos != null) {
		fos.close();
		}
		if (bos != null) {
		bos.close();
		}
		} catch (IOException e) {
		e.printStackTrace();
		}
		}
		return null;
	}
	
	
}
