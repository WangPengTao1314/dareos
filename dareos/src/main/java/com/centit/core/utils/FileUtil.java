package com.centit.core.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.centit.commons.model.Consts;

/**
 * @ClassName: FileUtil.java
 * @Description: 文件上传下载工具类
 * @author: zhu_hj
 * @date: 2018年5月2日 下午2:55:16
 */
public class FileUtil {
	
	/**
	 * 上传附件
	 * @param ftpPath 附件上传路径
	 * @param folder 上传目录
	 * @param file 上传文件
	 */
	public static String uploadFile(String ftpPath, MultipartFile file){
		return uploadFile(ftpPath, "", file);
	}
	public static String uploadFile(String ftpPath, String folder, MultipartFile file) {
		folder += "/" + TimeUtil.getTime("yyyyMMdd") + "/";
		String path = ftpPath + folder;
		String OriginalFilename = file.getOriginalFilename();
		String ext = OriginalFilename.substring(OriginalFilename.lastIndexOf("."), OriginalFilename.length());
		String fileName=OriginalFilename.replace(ext, "");
		Random random = new Random();
		int ends = random.nextInt(99);
		String ran = String.format("%02d",ends);//如果不足两位，前面补0
		String filename = fileName + "_" + TimeUtil.getTime("HHmmss") + ran + ext;
		
		File f=new File(path);
		if(!f.exists())
			f.mkdirs();
		try{
			File destFile = new File(path, filename);  
			file.transferTo(destFile); 
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return folder + filename;
		
		
		
		
		
	}
		
	/**
	 * 删除附件
	 */
	public static void removeFile(String ftpPath, String path){
		File f1=new File(ftpPath + path);
		if(f1.exists()){
			f1.delete();
		}
	}
	
	/**
	 * 检查文件大小和格式
	 */
	public static String checkFile(MultipartFile file,boolean exists,int maxSize,String allowExts){
		if(exists&&(file==null||file.getOriginalFilename().equals("")))
			return "文件不存在！";
		if(file!=null&&!file.getOriginalFilename().equals("")){	
			if(maxSize>0){
				double size=Math.ceil(file.getSize()/(1024*1024));
				if(size>maxSize)
					return "文件大小不能超过"+maxSize+"M！";
			}
					
			if(allowExts!=null&&!allowExts.equals("")){
				String[] exts=allowExts.split(",");
				int c=0;
				String filename=file.getOriginalFilename();
				String ext=filename.substring(filename.lastIndexOf(".")+1,filename.length());
				for(int i=0;i<exts.length;i++){
					if(ext.toUpperCase().equals(exts[i].toUpperCase())){
						c=1;
						break;
					}
				}
				if(c==0)
					return "只能上传指定格式"+allowExts+"的文件！";
			}
		}
		return "";
	}
	
	/**
	 *  二进制转文件
	 */
	public static void base64StringToFile(String base64String, String filepath) {
		try {
			byte[] bytes1 = Base64.decodeBase64(base64String.getBytes());
			OutputStream bos = new FileOutputStream(filepath);
			bos.write(bytes1);
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 文件转二进制
	 */
	public static String getFileToBase64String(File file) {
		byte[] by = new byte[(int) file.length()];
		try {
			InputStream is = new FileInputStream(file);
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			byte[] bb = new byte[2048];
			int ch;
			ch = is.read(bb);
			while (ch != -1) {
				bytestream.write(bb, 0, ch);
				ch = is.read(bb);
			}
			by = bytestream.toByteArray();
			is.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new String(Base64.encodeBase64(by));
	}
	
	/**
	 * 下载附件
	 * @param response
	 * @param path 文件路径
	 * @param filename 默认保存名称
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */
	public static void downloadFile(HttpServletResponse response, String path, String filename) 
			throws FileNotFoundException, UnsupportedEncodingException{
		File file = new File(path+filename);
        InputStream inStream = new FileInputStream(file);
        if(StringUtils.isEmpty(filename)) {
        	filename = file.getName();
        }
        // 截取过滤前面的日期路径
        String name=filename.substring(filename.lastIndexOf("/")+1,filename.length());
        response.reset();
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + 
        		new String(name.getBytes("gb2312"), "iso8859-1") + "\"");
        byte[] b = new byte[1024];
        int len;
        try {
            while ((len = inStream.read(b)) > 0)
                response.getOutputStream().write(b, 0, len);
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
