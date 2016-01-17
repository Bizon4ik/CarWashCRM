<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="owner_header.jsp" />


<div class="container-fluid" id="body">

  <div class="row">
    <div class="col-xs-12 col-md-10 col-md-offset-1">
      <p class="userTitle">Статистика за сегодня: </p>
    </div>
  </div>

  <div class="row">
    <table class="table-bordered table-hover table-striped col-xs-12 table">
      <thead>
        <tr>
          <th>Имя мойки</th>
          <th>Количесво текущих заказов</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <c:forEach items="${adminMainPage.carWashesStatistic}" var="statistic" varStatus="loop">
            <tr>
                <th><a href="/owner/currentOrders/${statistic.key.id}">${statistic.key.name}</a></th>
                <th>${statistic.value.quantityCurrentOrders}</th>
            </tr>
          </c:forEach>

      </tbody>
    </table>

  </div>


</div>


</body>
</html>