<%@ page language="java" contentType="text/html; charset=UTF-8"
 	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</head>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
     <h2 align="center"> Hello ${name}!</h2>
     <h2> ${test}</h2>
     <div>
         <table>
             <thead>
                 <td>
                 <th>BrandName</th>
                 <th>TotalProfiles</th>
                 <th>TotalFans</th>
                 <th>TotalEngagements</th>
                 </td>
             </thead>
             <tbody>
             <c:forEach items="${brands}" var="name">
                 <tr>
                     <td>${name.brandName}</td>
                     <td>${name.totalProfiles}</td>
                     <td>${name.totalFans}</td>
                     <td>${name.totalEngagements}</td>
                 </tr>
             </c:forEach>
             </tbody>

         </table>
     </div>
<script>
    console.log("${brands}");
</script>
</body>
</html>