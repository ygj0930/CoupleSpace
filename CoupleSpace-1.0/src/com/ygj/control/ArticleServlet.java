package com.ygj.control;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.ygj.dao.PagecountDAO;
import com.ygj.service.ArticleService;
import com.ygj.vo.Article;

public class ArticleServlet extends HttpServlet {

	public ArticleServlet() {
		super();
	}


	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		
	}


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doPost(request, response);
	
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op=request.getParameter("op");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session=request.getSession();
		int uid;
		if(session.getAttribute("userid")==null){
			uid=0;
		}else{
			uid=(Integer) session.getAttribute("userid");
		}
		
		
		if("add".equals(op)){
			String title = null;
			String summarize = null;
			String content = null;
			String upload_path = "upload/photo/";
			String save_path = upload_path + "moren.png";

			int kb = 1024;
			final long MAX_SIZE = kb * 1024;
			final long Max_Cache = 2048 * 1024;
			final String[] allowedExt = new String[] { "jpg", "gif", "png", "bmp",
					"jpeg" };
			if (ServletFileUpload.isMultipartContent(request)) {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(4096 * 1024);
				ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
				servletFileUpload.setHeaderEncoding("UTF-8");
				servletFileUpload.setSizeMax(MAX_SIZE);
				List<FileItem> fileItemList = null;
				File temp_file = new File(request.getSession().getServletContext()
						.getRealPath("/")
						+ "upload/UploadTemp");
				if (!temp_file.exists()) {
					temp_file.mkdirs();
				} else {
					long tempsize = 0;
					File[] files = temp_file.listFiles();
					for (File fi : files) {
						tempsize += fi.length();
					}
					if (tempsize > Max_Cache) {
						for (File fi : files) {
							fi.delete();
						}
						temp_file.delete();
						temp_file.mkdirs();
					}
				}
				factory.setRepository(temp_file);

				try {
					fileItemList = servletFileUpload.parseRequest(request);

				} catch (FileUploadException e) {
					if (e instanceof SizeLimitExceededException) {
						out.println("<script>alert('Photo size exceeded!Please retry!');window.location='addarticle.jsp';</script>");
						return;
					}
					e.printStackTrace();
				}

				if (fileItemList != null & fileItemList.size() != 0) {
					Iterator fileItr = fileItemList.iterator();
					FileItem item_file = null;

					while (fileItr.hasNext()) {
						FileItem item = (FileItem) fileItr.next();

						if (item == null || item.isFormField()) {

							String name = item.getFieldName();
							String value = item.getString("utf-8");
							if (name.equals("title") && value != null
									&& !value.equals("")) {
								title = value;
							}
							if (name.equals("summarize") && value != null
									&& !value.equals("")) {
								summarize = value;
							}
							if (name.equals("content") && value != null
									&& !value.equals("")) {
								content = value;
							}
						}

						if (item != null && !item.isFormField()) {
							item_file = item;
						}
					}

					if (item_file != null) {
						String fileName = item_file.getName();
						long size = item_file.getSize();
						if (!"".equals(fileName) && size != 0) {
							String t_name = fileName.substring(fileName
									.lastIndexOf("\\") + 1);
							String t_ext = t_name
									.substring(t_name.lastIndexOf(".") + 1);
							int allowFlag = 0;
							int allowedExtCount = allowedExt.length;
							for (; allowFlag < allowedExtCount; allowFlag++) {
								if (allowedExt[allowFlag].equals(t_ext))
									break;
							}
							if (allowFlag == allowedExtCount) {
								out.print("<script>alert('Incorrect photo type,please retry!');window.location='addarticle.jsp';</script>");
								return;
							}
							fileName = "file" + System.currentTimeMillis()
									+ fileName.substring(fileName.lastIndexOf("."));

							save_path = upload_path + fileName;
							File file = new File(request.getSession()
									.getServletContext().getRealPath(save_path));
							if (!file.getParentFile().exists()) {
								file.getParentFile().mkdirs();
							}
							try {
								item_file.write(file);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}

					String a_id = UUID.randomUUID().toString();
					Date dt=new Date();
					SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String a_time=format.format(dt);
					
					Article article=new Article(a_id, title, summarize, content, a_time, save_path,uid);
					
					if(new ArticleService().addArticle(article)){
						out.print("<script>alert('Add successfully!');window.location='ArticleServlet.do?op=articles';</script>");
					}else{
						out.print("<script>alert('Add failed,please retry!');window.location='addarticle.jsp';</script>");
					}
				}
			}
		}
		else if("updateshow".equals(op)){
			String a_id=request.getParameter("a_id");
			Article article=new ArticleService().doChaxun(a_id);
			request.setAttribute("article", article);
			RequestDispatcher dispatcher = request.getRequestDispatcher("updatearticle.jsp");
			dispatcher.forward(request, response);
		}
		else if("update".equals(op)){
			String a_id=null;
			String title = null;
			String summarize = null;
			String content = null;
			String upload_path = "upload/photo/";
			String save_path = null;

			int kb = 1024;
			final long MAX_SIZE = kb * 1024;
			final long Max_Cache = 2048 * 1024;
			final String[] allowedExt = new String[] { "jpg", "gif", "png", "bmp",
					"jpeg" };
			if (ServletFileUpload.isMultipartContent(request)) {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(4096 * 1024);
				ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
				servletFileUpload.setHeaderEncoding("UTF-8");
				servletFileUpload.setSizeMax(MAX_SIZE);
				List<FileItem> fileItemList = null;
				File temp_file = new File(request.getSession().getServletContext()
						.getRealPath("/")
						+ "upload/UploadTemp");
				if (!temp_file.exists()) {
					temp_file.mkdirs();
				} else {
					long tempsize = 0;
					File[] files = temp_file.listFiles();
					for (File fi : files) {
						tempsize += fi.length();
					}
					if (tempsize > Max_Cache) {
						for (File fi : files) {
							fi.delete();
						}
						temp_file.delete();
						temp_file.mkdirs();
					}
				}
				factory.setRepository(temp_file);

				try {
					fileItemList = servletFileUpload.parseRequest(request);

				} catch (FileUploadException e) {
					if (e instanceof SizeLimitExceededException) {
						out.println("<script>alert('Photo size exceeded!Please retry!');window.location='updatearticle.jsp';</script>");
						return;
					}
					e.printStackTrace();
				}

				if (fileItemList != null & fileItemList.size() != 0) {
					Iterator fileItr = fileItemList.iterator();
					FileItem item_file = null;

					while (fileItr.hasNext()) {
						FileItem item = (FileItem) fileItr.next();

						if (item == null || item.isFormField()) {

							String name = item.getFieldName();
							String value = item.getString("utf-8");
							if (name.equals("title") && value != null
									&& !value.equals("")) {
								title = value;
							}
							if (name.equals("summarize") && value != null
									&& !value.equals("")) {
								summarize = value;
							}
							if (name.equals("content") && value != null
									&& !value.equals("")) {
								content = value;
							}
							if (name.equals("a_id") && value != null
									&& !value.equals("")) {
								a_id = value;
							}
						}

						if (item != null && !item.isFormField()) {
							item_file = item;
						}
					}

					if (item_file != null) {
						String fileName = item_file.getName();
						long size = item_file.getSize();
						if (!"".equals(fileName) && size != 0) {
							String t_name = fileName.substring(fileName
									.lastIndexOf("\\") + 1);
							String t_ext = t_name
									.substring(t_name.lastIndexOf(".") + 1);
							int allowFlag = 0;
							int allowedExtCount = allowedExt.length;
							for (; allowFlag < allowedExtCount; allowFlag++) {
								if (allowedExt[allowFlag].equals(t_ext))
									break;
							}
							if (allowFlag == allowedExtCount) {
								out.print("<script>alert('Incorrect photo type,please retry!');window.location='updatearticle.jsp';</script>");
								return;
							}
							fileName = "file" + System.currentTimeMillis()
									+ fileName.substring(fileName.lastIndexOf("."));

							save_path = upload_path + fileName;
							File file = new File(request.getSession()
									.getServletContext().getRealPath(save_path));
							if (!file.getParentFile().exists()) {
								file.getParentFile().mkdirs();
							}
							try {
								item_file.write(file);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					Date dt=new Date();
					SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String a_time=format.format(dt);
					
					Article article=new Article(a_id, title, summarize, content, a_time, save_path,uid);
					
					if(new ArticleService().UpArticle(article)){
						out.print("<script>alert('Update successfully!');window.location='ArticleServlet.do?op=show&a_id="+a_id+"';</script>");
					}else{
						out.print("<script>alert('Update failed,please retry!');window.location='ArticleServlet.do?op=updateshow&a_id="+a_id+"';</script>");
					}
				}
			}
		}
		else if("show".equals(op)){
			String a_id=request.getParameter("a_id");
			Article article=new ArticleService().doChaxun(a_id);
			request.setAttribute("article", article);
			RequestDispatcher dispatcher = request.getRequestDispatcher("showarticle.jsp");
			dispatcher.forward(request, response);
		}
		else if("delete".equals(op)){
			String a_id=request.getParameter("a_id");
			if(new ArticleService().doDel(a_id)){
				out.print("<script>alert('Delete successfully!');window.location='ArticleServlet.do?op=articles';</script>");
			}else{
				out.print("<script>alert('Delete failed,please retry!');window.location='ArticleServlet.do?op=show&a_id="+a_id+"';</script>");
			}
		}
		else if("articles".equals(op)){
			int pages=0;
			int count=new PagecountDAO().getCount();
			int totalpages=0;
			int limit=5;
			
			totalpages=(int)Math.ceil(count/(limit*1.0));
			String strPage=request.getParameter("pages");
			if(strPage==null){
				pages=1;
			}else{
				try{
					pages=java.lang.Integer.parseInt(strPage);
				}catch(Exception e){
					pages=1;
				}
				if(pages<1){
					pages=1;
				}
				if(pages>totalpages){
					pages=totalpages;
				}
			}
			
			Vector<Article> articles=new ArticleService().getFenye(uid,pages, limit);
			request.setAttribute("articles", articles);
			request.setAttribute("total", totalpages);
			request.setAttribute("pages", pages);
			RequestDispatcher dispatcher = request.getRequestDispatcher("articles.jsp");
			dispatcher.forward(request, response);	
		}

	
	}


	public void init() throws ServletException {
		// Put your code here
	}

}
