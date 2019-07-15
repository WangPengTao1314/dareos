package com.centit.common.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.centit.commons.model.Consts;
import com.centit.core.exception.BizException;
import com.centit.core.utils.CustomizedProperties;
import com.centit.core.utils.FileUtil;
import com.centit.core.utils.SpringContextHolder;

/**
 * @ClassName: FileUploadController 
 * @Description: 文件上传
 */
@RestController
@RequestMapping("/sys/uploadFile")
public class FileUploadController extends BaseController{

	/**
	 * @Title: upload 
	 * @Description: 上传单个附件，并返回文件路径
	 * @author: zhu_hj
	 * @date: 2018年6月12日 上午11:24:46 
	 * @param request
	 * @param response
	 * @param file
	 * @return
	 * @throws BizException
	 * @return: ResponseBean
	 */
	@SuppressWarnings("deprecation")
	@ResponseBody
	@PostMapping("/upload2")
	public void upload(HttpServletRequest request, HttpServletResponse response) throws BizException{
		PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	    MultipartFile file = multipartRequest.getFile("Filedata");
		String uploadPath = CustomizedProperties.getContextProperty(Consts.PROPERTIES_KEY_UPLOADPATH);
		if(StringUtils.isEmpty(uploadPath)) {
			throw new BizException(SpringContextHolder.getMessage("sys.upload.err.path"));
		}
		String path = request.getRealPath(uploadPath);
		try{
			String url = FileUtil.uploadFile(path, file);
//			return ResponseBean.Success(url);
			jsonResult = jsonResult(true, url);
		}catch(Exception e){
			e.printStackTrace();
			 jsonResult = jsonResult(false, "上传失败");
//			throw new BizException(SpringContextHolder.getMessage("sys.upload.err", new String[]{e.getMessage()}));
		}
		if (null != writer) {
            writer.write(jsonResult);
            writer.close();
        }
	}

	@ResponseBody
	@PostMapping("/upload")
	public Map<String,Object> uploadFile(@RequestParam(value = "Filedata", required = false)MultipartFile file, HttpServletRequest request) throws Exception {
//        String schoolId = request.getParameter("schoolId");//获取参数
        Map<String,Object> result = new HashMap<String, Object>();
//        String uploadPath = CustomizedProperties.getContextProperty(Consts.PROPERTIES_KEY_UPLOADPATH);
//		if(StringUtils.isEmpty(uploadPath)) {
//			throw new BizException(SpringContextHolder.getMessage("sys.upload.err.path"));
//		}
//		String path = request.getRealPath(uploadPath);
		
		String path=Consts.UPLOADPATH_ROOT;
		
		//public final static  String UPLOAD_ROOT_DIR=Properties.getString("upload.root.dir");
        try{
            //上传文件方法，这里需要改成自己项目里上传文件方法
        	String filePath = FileUtil.uploadFile(path, file);
            result.put("code", 0);
            result.put("msg", "上传成功");
            result.put("fileName", file.getOriginalFilename());
            result.put("filePath", filePath);
            return result;
        }catch(Exception e){
            result.put("code", 1);
            result.put("msg", "上传失败");
            result.put("fileName", "");
            result.put("filePath", "");
            return result;
        }
    }

	@RequestMapping("/uploads")
	public void uploadImg(Long classesId,HttpServletRequest request, HttpServletResponse response) throws Exception{
	    PrintWriter writer = getWriter(response);
		String jsonResult = jsonResult();
		try {  
	            // Create a factory for disk-based file items  
	            DiskFileItemFactory factory = new DiskFileItemFactory();  
	  
	            // Set factory constraints  
//	            factory.setSizeThreshold(4096); // 设置缓冲区大小，这里是4kb  
//	            factory.setRepository(tempPathFile);// 设置缓冲区目录  
	  
	            // Create a new file upload handler  
	            ServletFileUpload upload = new ServletFileUpload(factory);  
	  
	            // Set overall request size constraint  
//	            upload.setSizeMax(4194304); // 设置最大文件尺寸，这里是4MB  
	  
	            List<FileItem> items = upload.parseRequest(request);// 得到所有的文件  
	            String uploadPath = CustomizedProperties.getContextProperty(Consts.PROPERTIES_KEY_UPLOADPATH);
	    		if(StringUtils.isEmpty(uploadPath)) {
	    			throw new BizException(SpringContextHolder.getMessage("sys.upload.err.path"));
	    		}
	            System.out.println(items.size());
	            Iterator<FileItem> i = items.iterator();  
	            while (i.hasNext()) {  
	                FileItem fi = (FileItem) i.next();  
	                String fileName = fi.getName();  
	                if (fileName != null) {  
	                	File fullFile = new File(new String(fi.getName().getBytes(), "utf-8")); // 解决文件名乱码问题  
	                    File savedFile = new File(uploadPath, fullFile.getName());  
	                    fi.write(savedFile);  
//	                    String url = FileUtil.uploadFile(uploadPath, fullFile);
	        			
	                }  
	            }  
	            System.out.print("上传成功！");  
	            jsonResult = jsonResult(true, "上传成功");
	        } catch (Exception e) {  
	        	e.printStackTrace();
				jsonResult = jsonResult(false, "上传失败");
	        }  
			if (null != writer) {
	            writer.write(jsonResult);
	            writer.close();
	        }
	}
	/**
	 * @Title: downloadFile 
	 * @Description: 下载附件，参数filePath为路径，fileName为下载时显示的文件名称(可为空)
	 * @author: zhu_hj
	 * @date: 2018年6月12日 上午11:28:30 
	 * @param request
	 * @param response
	 * @throws BizException
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 * @return: void
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = { "/download"}, method = { RequestMethod.GET, RequestMethod.POST })
	public void downloadFile(HttpServletRequest request, HttpServletResponse response) 
			throws BizException, FileNotFoundException, UnsupportedEncodingException {
		String fileName = request.getParameter("fileName"); //下载时显示的名称
		String path=Consts.UPLOADPATH_ROOT;
		FileUtil.downloadFile(response, path, fileName);
	}
	
	/**
	 * @Title: deleteFile 
	 * @Description: 删除附件
	 * @author: zhu_hj
	 * @date: 2018年6月12日 下午1:25:23 
	 * @param request
	 * @param response
	 * @throws BizException
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 * @return: void
	 */
	@SuppressWarnings("deprecation")
	@DeleteMapping("/delFile")
	public void deleteFile(HttpServletRequest request, HttpServletResponse response) 
			throws BizException, FileNotFoundException, UnsupportedEncodingException { 
		String filePath = request.getParameter("filePath"); //附件路径
		FileUtil.removeFile(request.getRealPath(CustomizedProperties.
				getContextProperty(Consts.PROPERTIES_KEY_UPLOADPATH)), filePath);
	}
}
