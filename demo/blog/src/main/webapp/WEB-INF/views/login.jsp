<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Login</title>
        <style type="text/css">
            .login-error{color: red;}
        </style>
    </head>
    <body>
        <c:if test="${incorrect}">
            <p class="login-error">Incorrect username or password.</p>
        </c:if>
        <form action="${ctx}/handle_login.htm" method="post">
            <fieldset>
                <legend>Login</legend>
                <p>
	                <label for="username">Username</label><br />
	                <input type="text" name="username" required="required" />
	            </p>
	            <p>
	               <label for="password">Password</label><br />
	               <input type="password" name="password" required="required" />
	            </p>
	            <p>
	               <button type="submit">Login</button>
	            </p>
            </fieldset>
        </form>
    </body>
</html>