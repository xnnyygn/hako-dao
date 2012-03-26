<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title><c:out value="${blog.title}" /></title>
        <style type="text/css">
            textarea, input{width: 600px;}
            textarea{height: 500px;}
        </style>
    </head>
    <body>
    	<div>
	        <h1><c:out value="${blog.title}" /></h1>
	        <p><c:out value="${blog.content}" /></p>
        </div>
        <form action="${ctx}/blog/save_comment.htm" method="post">
            <p>
                <label for="username">Username</label><br />
                <input type="text" name="username" />
            </p>
            <p>
                <label for="content">Content</label><br />
                <textarea name="content" rows="5" cols="10"></textarea>
            </p>
            <p>
                <input type="hidden" name="blogId" value="${blog.id}" />
                <button type="submit">Comment</button>
            </p>
        </form>
        <div id="comments">
            <c:forEach items="${blog.comments}" var="comment">
                <div class="comment">
                    <p>
                        <span class="username">
                            <c:out value="${comment.username}" />
                        </span>
                        <span class="date">
                            ${comment.dateCommented}
                        </span>
                    </p>
                    <p><c:out value="${comment.content}" /></p>
	        	</div>
        	</c:forEach>
        </div>
    </body>
</html>