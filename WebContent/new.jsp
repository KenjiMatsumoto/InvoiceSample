<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>請求書新規作成</title>
</head>
<body>
    <%
        request.setCharacterEncoding("UTF-8");
    %>
	<h1>新規</h1>
	<c:if test="${requestScope.errors != null}">
		<p id="errors">エラー</p>
		<ul>
			<c:forEach var="error" items="${requestScope.errors}">
				<li>${error}</li>
			</c:forEach>
		</ul>
	</c:if>
	<form method="POST" action="NewAction">
        請求書ID : <input type="text" readonly="readonly" name="invoiceId" value="" /> 
        <br /> 
        タイトル : <input type="text" name="title" value="" />
        <br /> 
        詳細 : <input type="text" name="detail" value="" /> 
        <br /> 
        請求金額 : <input type="text" name="totalFee" value="" /> 
        <br /> 
        <input type="submit" value="追加" /> <input type="hidden" name="token" value="<%=session.getAttribute("token")%>">
<%--         請求書ID : <input type="text" readonly="readonly" name="invoiceId" value="<c:out value="${invoice.invoiceId}" />" /> 
        <br /> 
        タイトル : <input type="text" name="title" value="<c:out value="${invoice.title}" />" />
        <br /> 
        詳細 : <input type="text" name="detail" value="<c:out value="${invoice.detail}" />" /> 
        <br /> 
        請求金額 : <input type="text" name="totalFee" value="<c:out value="${invoice.totalFee}" />" /> 
        <br /> 
        <input type="submit" value="追加" /> <input type="hidden" name="token" value="<%=session.getAttribute("token")%>">
 --%>
	</form>

	<a href="ListAction">一覧へ戻る</a>
</body>
</html>