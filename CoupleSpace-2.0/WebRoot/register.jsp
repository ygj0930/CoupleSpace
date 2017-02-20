<%@page language="java" import="java.sql.*,java.util.*" pageEncoding="utf-8"%>
<%@page isELIgnored="false"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>CoupleSpace-注册</title>
    
        	<script>
function show(o){     
	//单击图片，重新获得src，random作用？      
	o.src = "kaptcha.do?"+Math.random();
}
</script>
    <script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
    <script src="js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="js/easyui-lang-zh_CN.js"></script>
    <link rel="stylesheet" href="js/jquery-easyui/themes/default/easyui.css" type="text/css"></link>
    <link rel="stylesheet" href="js/jquery-easyui/themes/icon.css" type="text/css"></link>

    
    <style type="text/css">
		body
		{
			font: normal 14px/1.4 "Helvetica Neue","HelveticaNeue",Helvetica,Arial,sans-serif;
		}
		div
		{
			display:block;
		}
		a
		{
			text-decoration:none;
			opacity: 1;
			color: #fff;
		 }
		input,button{ outline:none; }
		::-moz-focus-inner{border:0px;}   
        .login_bg
        {
			position: fixed;
			top: 0;
			right: 0;
			bottom: 0;
			left: 0;
			background-size: cover;
        }
		.login_header {
			position:absolute;
			top:0;
			left:0;
			right:0;
			}
		.container {
			position:relative;
			top:50%;
			margin: 0 auto;
			width: 260px;
			
			}
		#logo
		{
			display: block;
			text-align: center;
			font-weight: bold;
			font-size: 50px;
			color: white;
			height: 100%;
		}
		#subheading 
		{
		  position: relative;
		  width: 517px;
		  left: 50%;
		  margin: 10px 0 16px -258px;
		  font-size: 15px;
		  font-weight: normal;
		  color: #fff;
		  text-align: center;
		}
		.signup_forms
		{
			width:260px;	
		}
		.signup_forms_container
		{
			overflow: hidden;
			background: #fff;
			border-radius:3px;	
		}
		.form_user,.form_password,.form_username,.checkCode
		{
			width:260px;
			height:45px;
			margin:0px;
        	padding:0px;
			border:0px;	
		}
		.checkCode,.form_password,.form_username,.form_confirm_password{border-top: 1px solid #e3e3e3;}
		.signup_forms input
		{
			padding: 11px 10px 11px 13px;
			width: 100%;
			margin:0px;
        	background: 0;
			font: 16px/1.4 "Helvetica Neue","HelveticaNeue",Helvetica,Arial,sans-serif;
			border:0px;	
		}
		#signup_forms_submit{
		  margin-top:8px;		
		  width:260px;
		  height:45px;
		  background:#529ECC;
		  border:0px;
		  border-radius:3px;
		  cursor:pointer;             
		 }
		 #signup_forms_submit span{
			 color: #fff;
			 font: "Helvetica Neue",Arial,Helvetica,sans-serif;
			 font-size: 16px;}
		.footer
		 {
			position: fixed;
			top: auto;
			right: 0;
			bottom: 0;
			left: 0;
		 }
		 .footer_signup_link
		 {
			 position: absolute;
			 width:141px;
			 height:78px;
			 bottom: 0;
			 padding: 0 20px;
			 margin: 0 0 13px 0;
			 line-height: 25px;
			 text-align: center;
			 opacity: 1;
			 color: #fff;
		 }
		 .signup_link
		 {
			  display: block;
			  height: 45px;
			  padding: 0 10px;
			  margin: 0 0 8px 0;
			  font-size: 16px;
			  font-weight: bold;
			  line-height: 45px;
			  border: 0;
			  border-radius: 2px;
			  color: #fff;
              background: rgba(255,255,255,0.33);
		}
		.link
		{
			font-size: 14px;
			padding-right: 5px;
			margin-right: 12px;
			color: #fff;
		}
		.design_show
		{
			position: fixed;
			bottom: 0;
			right: 0;
			padding: 0 12px;
			margin: 0 0 13px 0;
			line-height: 25px;
		}
		.designer_info
		{
			position: relative;
			color: #FFFFFF;
		}
		#face
		{
			margin: 0 0 0 10px;
			border-radius:20px;
			padding: 0;
			vertical-align: middle;
			height: 24px;
			width: 24px;
		}
    </style>
    <script type="text/javascript">
    	
			function doCheck(){
			if(document.getElementById("signup_email").value==""||document.getElementById("signup_password").value==""||document.getElementById("signup_confirm_password").value==""||document.getElementById("signup_username").value=="")
			{
				alert("注册项均不能为空！");
				return false;
			}
			else
			{
				if(document.getElementById("signup_password").value!=document.getElementById("signup_confirm_password").value)
				{
						alert('两次密码输入不一!');
						document.getElementById("signup_password").value="";
						document.getElementById("signup_confirm_password").value="";
						return false;
				}
				else
				{
					return true;
				}
			}
		};
    </script>
</head>
<body>
    <div id="login_bg" class ="login_bg" style="background-image:url(images/goushouzhi.jpg);"></div>
    <div class="login_header">
    	<span></span>
    </div>
    
    <div class="container">
    	<div class="form_header">
        	<h1 id="logo">CoupleSpace</h1>
			<h2 id="subheading">Register</h2>
        </div>
        	<div class="signup_forms" class="signup_forms">
            	<div id="signup_forms_container" class="signup_forms_container">
                    	<form class="signup_form_form" id="sign_form" method="post" action="UserServlet.do" onsubmit="return doCheck();">
                        	<div class="signup_account" id="signup_account">
                            	<div class="form_user">	
        							<input type="text" name="email" placeholder="邮箱" id="signup_email">
                                </div>
                                <div class="form_username">
           							<input type="text" name="username" placeholder="用户名" id="signup_username">
        						</div>
                                <div class="form_password">
           							<input type="password" name="password" placeholder="密码" id="signup_password">
        						</div>
        						<div class="form_confirm_password">
           							<input type="password"  placeholder="确认密码" id="signup_confirm_password">
        						</div>
        						<div class="checkCode">
           							<input type="text"  placeholder="验证码" id="checkCode" name="checkCode">
        						</div>
                            </div>
                             </div>
                             	<div>
									<img src="kaptcha.do" onclick="show(this)" />
								</div>
								<input type="hidden" name="op" value="register"/>
                				<button type="submit" id="signup_forms_submit" ><span style="font-size:16px;"><strong>注册</strong></span></button>
           	   				 </div>
                        </form> 
               
    </div>
    
    <div class="footer">
    	<div class="footer_signup_link">
        	<a class="signup_link" href="login.jsp">登录</a>
        </div>
        <div class="design_show">
        	<div class="designer_info">
            	<a href="#">Designed by <strong>ygj</strong></a>

            </div>
        </div>
    </div>
</body>
</html>
