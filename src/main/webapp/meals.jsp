<%--
  Created by IntelliJ IDEA.
  Date: 13.12.2016
  Time: 3:59
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals List</title>
    <style type="text/css">
        .tg {
            border-collapse: collapse;
            border-spacing: 0;
            border-color: #ccc;
        }

        .tg td {
            font-family: Arial, sans-serif;
            font-size: 12px;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            text-align: center;
        }

        .tg th {
            font-family: Arial, sans-serif;
            font-size: 14px;
            font-weight: normal;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #f0f0f0;
        }
    </style>
</head>
<body>
<h1>Meals List</h1>
<table class="tg">
    <tr>
        <th>ID</th>
        <th width="120">Время</th>
        <th width="120">Описание</th>
        <th width="120">Калории</th>
        <th colspan=2>Action</th>
    </tr>
    <c:forEach items="${mealsList}" var="meal">
        <tr scope="row" bgcolor=${meal.isExceed() ? "#FA8072" : "#90EE90"}>
            <td>${meal.getId()}</td>
            <td>${TimeUtil.formatLocalDateTime(meal.getDateTime())}</td>
            <td>${meal.getDescription()}</td>
            <td>${meal.getCalories()}</td>
            <td><a href="meals?action=edit&id=<c:out value="${meal.getId()}"/>">Update</a></td>
            <td><a href="meals?action=delete&id=<c:out value="${meal.getId()}"/>">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<p><a href="meals?action=add">Add Meal</a></p>
</body>
</html>
