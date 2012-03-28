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
        <form action="${ctx}/blog/save.htm" method="post">
            <fieldset>
                <legend>Create Blog</legend>
                <p>
	                <label for="title">Title</label><br />
	                <input type="text" name="title" required="required" maxlength="255" />
	            </p>
	            <p>
	               <label for="content">Content</label><br />
	               <textarea name="content" required="required"></textarea>
	            </p>
	            <p>
	               <label for="tags">Tags(comma separated)</label><br />
	               <input type="text" name="tags" />
	            </p>
	            <p>
	               <button type="submit">Create</button>
	            </p>
            </fieldset>
        </form>
    </body>
</html>