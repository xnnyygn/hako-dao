<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Blog List</title>
    </head>
    <body>
        <a href="${pageContext.request.contextPath}/blog/create.htm">Create Blog</a>
        <c:forEach items="${blogs}" var="blog">
            <div class="blog">
                <h2><c:out value="${blog.title}" /></h2>
                <p><c:out value="${blog.content}" /></p>
            </div>
        </c:forEach>
    </body>
</html>