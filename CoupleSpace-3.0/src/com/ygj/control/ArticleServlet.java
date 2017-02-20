package com.ygj.control;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.Vector;
import java.util.logging.Logger;

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

import com.ygj.bo.ArticleBO;
import com.ygj.dao.ArticleDAO;
import com.ygj.dao.PagecountDAO;
import com.ygj.error.MyError;
import com.ygj.error.MyException;
import com.ygj.service.ArticleService;
import com.ygj.service.UploadService;

public class ArticleServlet extends HttpServlet {

	Properties properties;
	org.apache.log4j.Logger logger=org.apache.log4j.Logger.getLogger(ArticleServlet.class.getName());
	
	public ArticleServlet() {
		super();
		properties=new Properties();
		InputStream in=ArticleDAO.class.getClassLoader().getResourceAsStream("config.properties");
		try {
			properties.load(in);
		} catch (Exception e) {
			
			logger.error("Exception in ArticleServlet!", e);
			throw new MyError("加载配置文件出错!", e);
		}
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
		request.setCharacterEncoding(properties.getProperty("Encoding"));
		response.setContentType(properties.getProperty("contentType"));
		response.setCharacterEncoding(properties.getProperty("Encoding"));
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
			String upload_path = properties.getProperty("uploadPath");
			String save_path = null;			
			final String allowedExt[] ={"gif" , "png" , "jpg" , "jpeg" , "bmp"};
			String realwebbase = request.getSession().getServletContext().getRealPath("/");
			String temp_file = realwebbase+properties.getProperty("uploadTemp");
			UploadService us=new UploadService(allowedExt,upload_path,temp_file);
			int flag=us.upload(request);		
			if (flag == 2) {
				out.print("<script>alert('Incorrect photo type,please retry!');window.location='addarticle.jsp';</script>");
				return;
			}
			String a_id = UUID.randomUUID().toString();
			Date dt = new Date();
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String a_time = format.format(dt);
			
			Map map=us.getTextcontent();
			
			if(map==null){
				out.print("<script>alert('Please chose a photo!');window.location='ArticleServlet.do?op=articles';</script>");
				return;				
			}
			
			title=(String) map.get("title");
			summarize=(String) map.get("summarize");
			content=(String) map.get("content");		
			save_path=us.getSavePath();
			ArticleBO article = new ArticleBO(a_id, title, summarize, content,
					a_time, save_path, uid);
			if (new ArticleService().addArticle(article)) {
				out.print("<script>alert('Add successfully!');window.location='ArticleServlet.do?op=articles';</script>");
			} else {
				out.print("<script>alert('Add failed,please retry!');window.location='addarticle.jsp';</script>");
			}		
		}
		else if("updateshow".equals(op)){
			String a_id=request.getParameter("a_id");
			ArticleBO article=new ArticleService().doChaxun(a_id);
			request.setAttribute("article", article);
			RequestDispatcher dispatcher = request.getRequestDispatcher("updatearticle.jsp");
			dispatcher.forward(request, response);
		}
		else if("update".equals(op)){
			String a_id = null;
			String title = null;
			String summarize = null;
			String content = null;
			String upload_path = properties.getProperty("uploadPath");
			String save_path = null;

			final String allowedExt[] = { "gif", "png", "jpg", "jpeg", "bmp"};
			String realwebbase = request.getSession().getServletContext()
					.getRealPath("/");
			String temp_file = realwebbase +properties.getProperty("uploadTemp");
			UploadService us = new UploadService(allowedExt, upload_path,
					temp_file);
			int flag = us.upload(request);
			if (flag == 2) {
				out.print("<script>alert('Incorrect photo type,please retry!');window.location='ArticleServlet.do?op=articles';</script>");
				return;
			}
			Date dt = new Date();
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String a_time = format.format(dt);
			
			Map map = us.getTextcontent();
			if(map==null){
				out.print("<script>alert('Please chose a photo!');window.location='ArticleServlet.do?op=articles';</script>");
				return;				
			}
			title = (String) map.get("title");
			summarize = (String) map.get("summarize");
			content = (String) map.get("content");
			a_id = (String) map.get("a_id");
			save_path = us.getSavePath();

			ArticleBO article = new ArticleBO(a_id, title, summarize, content,
					a_time, save_path, uid);

			if (new ArticleService().UpArticle(article)) {
				out.print("<script>alert('Update successfully!');window.location='ArticleServlet.do?op=show&a_id="
						+ a_id + "';</script>");
			} else {
				out.print("<script>alert('Update failed,please retry!');window.location='ArticleServlet.do?op=updateshow&a_id="
						+ a_id + "';</script>");
			}
		}
		else if("show".equals(op)){
			String a_id=request.getParameter("a_id");
			ArticleBO article=new ArticleService().doChaxun(a_id);
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
					logger.warn("pages limited warning!");
					pages=1;
				}
				if(pages<1){
					pages=1;
				}
				if(pages>totalpages){
					pages=totalpages;
				}
			}
			
			Vector<ArticleBO> articles=new ArticleService().getFenye(uid,pages, limit);
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
