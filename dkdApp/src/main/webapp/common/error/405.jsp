<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>405 错误的请求方式</title>
		<meta http-equiv=content-type content="text/html; charset=UTF-8">

		<style type=text/css></style>
		<link type=text/css rel=stylesheet>
		<style type=text/css>
body {
	font-size: 9pt;
	color: #842b00;
	line-height: 16pt;
	font-family: "tahoma", "宋体";
	text-decoration: none
}

table {
	font-size: 9pt;
	color: #842b00;
	line-height: 16pt;
	font-family: "tahoma", "宋体";
	text-decoration: none
}

td {
	font-size: 9pt;
	color: #842b00;
	line-height: 16pt;
	font-family: "tahoma", "宋体";
	text-decoration: none
}

body {
	scrollbar-highlight-color: buttonface;
	scrollbar-shadow-color: buttonface;
	scrollbar-3dlight-color: buttonhighlight;
	scrollbar-track-color: #eeeeee;
	background-color: #ffffff
}

a {
	font-size: 9pt;
	color: #842b00;
	line-height: 16pt;
	font-family: "tahoma", "宋体";
	text-decoration: none
}

a:hover {
	font-size: 9pt;
	color: #0188d2;
	line-height: 16pt;
	font-family: "tahoma", "宋体";
	text-decoration: underline
}

h1 {
	font-size: 9pt;
	font-family: "tahoma", "宋体"
}
</style>
	<body topmargin=20>
		<table cellspacing=0 width=600 align=center border=0 cepadding="0">
			<tbody>
				<tr colspan="2">
					<td valign=top align=middle>
						<img height=100 src="../error/error.jpg" width=100 border=0>
					<td>
					<td>
						<!--------system return begin------------>
						<h1>
							<font color="#666666">错误的请求方式</font>
						</h1>
						<font color="#666666">http 错误 405：请求方式错误。
						</font>
						<hr noshade size=0>
						<p>
							<font color="#666666">☉ 请尝试以下操作：</font>
						</p>
						<ul>
							<li>
								<font color="#666666">单击</font><a
									 href="<c:url value="/"/>"><font color=#0000ff>返回首页</font>
								</a><font color="#666666">。 </font>
							</li>
						</ul>
						<p>
							<font color="#666666">
								如果您对本系统有任何疑问、意见、建议、咨询，请联系管理员 </font><font color="#c0c0c0">
								<br> &nbsp;&nbsp;&nbsp;</font>
							<br>
						</p>
						<!------------end this!--------------->
					</td>
				</tr>
			</tbody>
		</table>
	</body>
</html>