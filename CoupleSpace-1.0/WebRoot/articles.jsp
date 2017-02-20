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
	
    <title>日志列表</title>
	
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
                <a class="navbar-brand page-scroll" href="#page-top">日志列表</a>
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
					<c:if test='${!empty userid}'>
                    <li>
                        <a class="page-scroll" href="addarticle.jsp">新增日志</a>
                    </li>
                      <li>
                        <a class="page-scroll" href="UserServlet.do?op=exit">注销</a>
                    </li>
                    </c:if>
                    <c:if test="${empty userid}">
                    <li>
                        <a class="page-scroll" href="login.jsp">登录</a>
                    </li>
                     <li>
                        <a class="page-scroll" href="register.jsp">注册</a>
                    </li>                 
                    </c:if>

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
	</header>
	<!-- Header -->
	<div id="page-content">
		<div class="container">
		<div id="main-content">
	<!--用一个数组封装查询结果，set到request属性，转发过来。然后jstl遍历数组，一个元素一个div显示一篇文章，EL获取数组元素的属性们显示。分页查询，一页5条。 -->
	<table>
		<c:forEach var="arti" items="${articles}" step="1">				
					<article>
						<div class="art-header">
							<h2>${arti.a_title}</h2>
							<div class="info">${arti.a_time}</div>
						</div>
						<div class="art-content">
							<img src="${arti.a_photo}"/>
							<p>${arti.a_summarize}</p>

							<a href="ArticleServlet.do?a_id=${arti.a_id}&op=show"><button  class="btn btn-skin f-right">More</button></a>

						</div>
					</article>	
		</c:forEach>			
				</div>	
			</div>
	</div>

	<div width="100%" style="margin-top:5px;text-align:right;">
		<form name="f1" method="POST" action="ArticleServlet.do?op=articles">
		第${pages}页 &nbsp;&nbsp;
		共${total}页 &nbsp;&nbsp;
		<a href="ArticleServlet.do?op=articles&pages=1">首页</a> &nbsp;&nbsp;
		<a href="ArticleServlet.do?op=articles&pages=${pages<=1?pages:pages-1}">上一页</a> &nbsp;&nbsp;
		<a href="ArticleServlet.do?op=articles&pages=${pages==total?total:pages+1}">下一页</a> &nbsp;&nbsp;
		<a href="ArticleServlet.do?op=articles&pages=${total}">最后一页</a> &nbsp;&nbsp;
		转到第:<input type="text" id="pages" name="pages" size="8">页 &nbsp;&nbsp;
		<input type="submit" value="GO" name="cndok">
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
