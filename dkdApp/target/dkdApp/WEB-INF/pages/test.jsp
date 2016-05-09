<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@ include file="/common/taglib/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <title>test</title>
  <%@ include file="/common/taglib/meta.jsp" %>
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
  <tr>
    <td>
      编号
    </td>
  </tr>
  <c:forEach var="nonobank" items="${list}" >
  <tr>
    <td>
      ${nonobank.boExtno}
    </td>
  </tr>
  </c:forEach>

  </tbody>
</table>
</body>
</html>