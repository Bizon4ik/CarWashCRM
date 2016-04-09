<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../owner_header.jsp" />

<div class="container-fluid" id="body">

  <div class="row">
    <div class="col-xs-12 col-md-10 col-md-offset-1">
      <p class="userTitle">Все клиенты: </p>
    </div>
  </div>

  <c:if test="${delete != null}">
  <form action="/owner/category/all" method="post">
    </c:if>

    <table class="table-bordered table-hover table-striped col-xs-12 table" >
      <thead>
      <tr>
        <th>№</th>
        <th>Название</th>
        <th>Телефон</th>
        <th>Тип оплаты</th>
        <th>Коефициент</th>
        <th>Действия</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach items="${clientList}" var="row" varStatus="loop">

        <tr>
          <td>${loop.count}</td>
          <td>${row.name}</td>
          <td>${row.phoneNumber}</td>
          <c:choose>
            <c:when test="${row.isPayByCash == true}" >
              <td>Наличные</td>
            </c:when>
            <c:otherwise>
              <td>Безналичные</td>
            </c:otherwise>
          </c:choose>
          <td>${row.priceMultiplicator}</td>
          <td>
            <a href="/owner/clients/delete/${row.id}"><input type="button" class="btn btn-danger defaultBtnSize" value="delete"></a>
            <a href="/owner/clients/update/${row.id}"><input type="button" class="btn btn-success defaultBtnSize" value="update"></a>
          </td>
          <c:if test="${delete != null}">
            <td><input type="checkbox" name="listIdCategory" value="${row.id}"/></td>
          </c:if>

        </tr>

      </c:forEach>
      </tbody>

    </table>

    <c:if test="${delete != null}" >
    <input type="submit" class="btn btn-danger col-xs-5 col-xs-offset-7 col-md-2 col-md-offset-10 deleteButton" value="delete">
  </form>
  </c:if>

</div>





</div>


</body>
</html>




