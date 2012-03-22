<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Create Blog</title>
        <style type="text/css">
            textarea, input{width: 600px;}
            textarea{height: 500px;}
        </style>
    </head>
    <body>
        <h1><c:out value="${blog.title}" /></h1>
        <p><c:out value="${blog.content}" /></p>
    </body>
</html>