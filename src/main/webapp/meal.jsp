<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="POST" action="meals" name="frmAddUser">
    ID: <input type="text" readonly="readonly" name="id" value="<c:out value="${meal.getId()}" />" /> <br />
    Время : <input
        type="text" name="dateTime"
        value="<c:out value="${TimeUtil.formatLocalDateTime(meal.getDateTime())}" />" /> <br />
    Описание: <input type="text" name="description" value="<c:out value="${meal.getDescription()}" />" /> <br />
    Калории: <input type="text" name="calories" value="<c:out value="${meal.getCalories()}" />" /> <br />
    <br />
    <input type="submit" value="Submit" />
</form>
</body>
</html>
