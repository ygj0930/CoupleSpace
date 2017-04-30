<%@page language="java" import="java.sql.*,java.util.*" pageEncoding="utf-8"%>
<%@page isELIgnored="false"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="description" content="">
	
    <title>修改日志</title>
	
    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
	
	<!-- Custom CSS -->
    <link href="css/style.css" rel="stylesheet">
	<link rel="stylesheet" href="fonts/stylesheet.css">
	<link href="css/mb-comingsoon-iceberg.css" rel="stylesheet" />
	
	<!-- Custom Fonts -->
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	
	<!-- jQuery and Modernizr-->
	<script src="js/jquery-2.1.1.js"></script>
	<script src="js/modernizr.custom.97074.js"></script>
		<link href="ue/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
		<script type="text/javascript" src="ue/third-party/jquery.min.js"></script>
		<script type="text/javascript" charset="utf-8" src="ue/umeditor.config.js"></script>
		<script type="text/javascript" charset="utf-8" src="ue/umeditor.min.js"></script>
		<script type="text/javascript" src="ue/lang/zh-cn/zh-cn.js"></script>
	<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="js/html5shiv.js"></script>
        <script src="js/respond.min.js"></script>
    <![endif]-->
</head>

<body class="sub-page">
	
	<!-- /////////////////////////////////////////Navigation -->
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header page-scroll">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand page-scroll" href="#page-top">修改日志</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav nav-justified ">
                    <li class="hidden">
                        <a href="#page-top"></a>
                    </li>
					<li>
                        <a class="page-scroll" href="index.jsp">主页</a>
                    </li>
                    <li  class="active">
                        <a class="page-scroll" href="ArticleServlet.do?op=articles&pages=1">日志列表</a>
                    </li>
                    <li>
                        <a class="page-scroll" href="addarticle.jsp">新增日志</a>
                    </li>
                      <li>
                        <a class="page-scroll" href="UserServlet.do?op=exit">注销</a>
                    </li>
                </ul>
				<ul class="nav nav-justified ">
                     <li>
                      在线人数：${Count}
                    </li>               
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>
	<!-- Navigation -->
	
	<header>
		<div>
			<br/>
			<br/>
			<br/>
		</div>
		<script type="text/javascript">		
		function doCheck(){
			if(document.getElementById('title').value==''||document.getElementById('summarize').value==''||document.getElementById('content').value=='')
			{
				alert("各项不能为空!");	
				return false;			 
			}
			return true;
		};
    </script>
	</header>
	<!-- /////////////////////////////////////////Content -->
		<div id="page-content">
		<div class="container">
			<div class="row">
				<div id="main-content">
				<form class="signup_form_form" id="sign_form" method="post" action="ArticleServlet.do?op=update" onsubmit="return doCheck();" enctype="multipart/form-data">
					<article>	
						<div>
							<div> 
								标题：<textarea  rows="5" cols="97" style="margin-left:2px;width:500px;height:50px;" name="title" id="title">${article.ATitle}</textarea>
							</div>
						</div>
						<div class="art-content">
							<div>概述：<textarea  rows="5" cols="97" style="margin-left:2px;width:760px;height:100px;" name="summarize" id="summarize">${article.ASummarize}</textarea>
							</div>
							<div>图片：<input type="file" id="photo" name="photo"/>
							</div>
						<div>
							内容：<textarea  rows="1" cols="30" style="margin-left:2px;width:800px;height:300px;" name="content" id="myEditor">${article.AContent}</textarea>
							<script>
							UM.getEditor('myEditor', {});
							</script>
						</div>
						</div>
						<div>
						<input type="hidden" name="a_id" value="${article.AId}"/>
					      <button type="submit" id="signup_forms_submit"><span><strong>提交</strong></span></button>
						</div>
					</article>					
				</form> 
				</div>

			</div>
		</div>
	</div>

    <!-- Core JavaScript Files -->
   	 
    <script src="js/bootstrap.min.js"></script>
	
	<!-- Custom Theme JavaScript -->
	<script src="js/agency.js"></script>

	<!-- Plugin JavaScript -->
	<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>
	<script src="js/classie.js"></script>
	<script src="js/cbpAnimatedHeader.js"></script>
	
	<!-- Countdown -->
    <script src="js/jquery.mb-comingsoon.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $('#myCounter').mbComingsoon({ expiryDate: new Date(2016, 0, 1, 9, 30), speed:100 });
            setTimeout(function () {
                $(window).resize();
            }, 200);
        });
    </script>
	
	<!-- Img Hover -->
	<script type="text/javascript" src="js/jquery.hoverdir.js"></script>	
	<script type="text/javascript">
		$(function() {
		
			$(' #da-thumbs > li ').each( function() { $(this).hoverdir(); } );

		});
	</script>
	
</body>

</html>
