<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" />
		<meta content="yes" name="apple-mobile-web-app-capable" />
		<meta content="black" name="apple-mobile-web-app-status-bar-style" />
		<meta content="telephone=no" name="format-detection" />
        <meta name="robots" content="none" />
        <title>$404</title>
        <link rel="icon" type="image/png" href="/favicon.png" />
        <style type="text/css">
        	element.style {
				font-family: Arial, 'Microsoft Yahei', Simsun, sans-serif;
			}
			body {
				background-color: #F6F6F6;
				color: #585858;
				font-size: 125%;
			}
         	.wrapper{
         		width:100%
         	}
         	.logobg{
         		margin: 0 auto;
				width: 356px;
				padding-top: 22%;
         	}
         	.return{
         		font-size: 18px;
				text-decoration: none;
				height: 30px;
				background: rgb(253, 8, 27);
				padding: 5px 100px 5px 100px;
				color: white;
				font-family: "微软雅黑";
         	}
         	.return:hover{
				background: rgb(253, 185, 8);
				color: white;
         	}
         	button {
				background: rgb(76, 142, 250);
				border: 0;
				border-radius: 2px;
				box-sizing: border-box;
				color: #fff;
				cursor: pointer;
				font-size: .875em;
				height: 36px;
				margin: -6px 0 0;
				padding: 8px 55px;
				transition: box-shadow 200ms cubic-bezier(0.4, 0, 0.2, 1);
			}
         </style>
    </head>
    <body>
        <div class="wrapper">
            <div class="logobg">
        		<img style="width:100%" src="/common/error/404.jpg">
        	</div>
        	<div class="mt20" style="width: 186px;margin: 0px auto;"><a href="javascript:void(0)" onclick="location.href='/?date='+new Date().getTime()" target="_top" ><button class="" >返回首页</button></a></div>
        </div>
        
    </body>
</html>
