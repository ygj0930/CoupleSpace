package com.ygj.service;

import java.util.*;
import java.lang.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.ygj.dao.ArticleDAO;
import com.ygj.error.MyException;

public class UploadService {
	private String[] fileTypes;
	private String dstPath;
	private long fileSizeMax=10*1024*1024;
	private String cachePath;
	private int cacheSizeMax=5*1024*1024;
	private Map<String,String> textcontent;
	private String save_path;
	Properties properties;
	Logger logger=Logger.getLogger(getClass().getName());
	
	
	public UploadService() {
		super();
	}
	
	public String getSavePath(){
		return save_path;
	}
	
	private ServletFileUpload sfupload;
	private DiskFileItemFactory factory=new DiskFileItemFactory();
	public String[] getFileTypes() {
		return fileTypes;
	}
	public void setFileTypes(String[] fileTypes) {
		this.fileTypes = fileTypes;
	}
	public String getDstPath() {
		return dstPath;
	}
	public void setDstPath(String dstPath) {
		this.dstPath = dstPath;
	}
	public long getFileSizeMax() {
		return fileSizeMax;
	}
	public void setFileSizeMax(long fileSizeMax) {
		this.fileSizeMax = fileSizeMax;
	}
	public String getCachePath() {
		return cachePath;
	}
	public void setCachePath(String cachePath) {
		this.cachePath = cachePath;
	}
	public int getCacheSizeMax() {
		return cacheSizeMax;
	}
	public void setCacheSizeMax(int cacheSizeMax) {
		this.cacheSizeMax = cacheSizeMax;
	}
	public Map<String, String> getTextcontent() {
		return textcontent;
	}
	public void setTextcontent(Map<String, String> textcontent) {
		this.textcontent = textcontent;
	}
	
	public UploadService(String[] fileTypes, String dstPath, String cachePath) {
		super();
		this.fileTypes = fileTypes;
		this.dstPath = dstPath;
		this.cachePath = cachePath;
	}
	public UploadService(String[] fileTypes, String dstPath, long fileSizeMax,
			String cachePath, int cacheSizeMax) {
		super();
		this.fileTypes = fileTypes;
		this.dstPath = dstPath;
		this.fileSizeMax = fileSizeMax;
		this.cachePath = cachePath;
		this.cacheSizeMax = cacheSizeMax;
	}
	
	public String getFileExt(File file){
		String path=file.getName();
		return path.substring(path.lastIndexOf(".")+1);
	}
	public ServletFileUpload getServletFileUpload(){
		if(sfupload==null){
			sfupload=new ServletFileUpload(factory);
			return sfupload;
		}else{
			return sfupload;
		}
	}
	public boolean isFileValidate(String fileName,long size){		
		if (!"".equals(fileName) && size != 0) {
			String t_name = fileName.substring(fileName
					.lastIndexOf("\\") + 1);
			String t_ext = t_name
					.substring(t_name.lastIndexOf(".") + 1);
			int allowFlag = 0;
			int allowedExtCount =fileTypes.length;
			for (; allowFlag < allowedExtCount; allowFlag++) {
				if (fileTypes[allowFlag].equals(t_ext))
					break;
			}
			if (allowFlag == allowedExtCount) {
				return false;
			}
		}
		return true;
	}
	public void makeDir(String url){		
		File file=new File(url);
		if(!file.exists()){
			if(!file.mkdirs()){
				System.out.println("fail to create dir!");
			}
		}
}
public int upload(HttpServletRequest request){
		
		try{
		if(!ServletFileUpload.isMultipartContent(request)){
			return -1;
		}
		makeDir(dstPath);
		makeDir(cachePath);	
		factory.setRepository(new File(cachePath));
		factory.setSizeThreshold(cacheSizeMax);
		ServletFileUpload sfu=getServletFileUpload();
		sfu.setFileSizeMax(fileSizeMax);
		
	
		List<FileItem> items=sfu.parseRequest(request);
		Map<String,String> map =new HashMap<String,String>();
		
        
		Iterator it=items.iterator();
		FileItem item_file = null;
			while (it.hasNext()) {
				FileItem fileItem = (FileItem) it.next();
				if (fileItem.isFormField()) {
					map.put(fileItem.getFieldName(),
							fileItem.getString("UTF-8"));
				}
				if (fileItem != null && !fileItem.isFormField()) {
					item_file = fileItem;
				}
			}
			if (item_file != null) {
				String fileName = item_file.getName();
				long size = item_file.getSize();
				if (!isFileValidate(fileName,size)) {
					return 2;
				} else {
					fileName = "file" + System.currentTimeMillis()
							+ fileName.substring(fileName.lastIndexOf("."));
					save_path = dstPath + fileName;
					File file2 = new File(request.getSession()
							.getServletContext().getRealPath(save_path));
					if (!file2.getParentFile().exists()) {
						file2.getParentFile().mkdirs();
					}
					item_file.write(file2);
				}
        }
		setTextcontent(map);
		}catch(Exception ex){
			logger.error("Exception in UploadService!", ex);
			throw new MyException("Exception inUploadService!", ex);
		}	
		
		return 0;
	}
}
