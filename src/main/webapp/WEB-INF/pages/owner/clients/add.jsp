<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../owner_header.jsp" />

<c:choose>
  <c:when test="${title == 'update'}">
    <c:set var="showTitle" value="Обновить клиента #${client.id}"/>
    <c:set var="formPath" value="/owner/clients/update/${client.id}"/>
  </c:when>
  <c:otherwise>
    <c:set var="showTitle" value="Добавить клиента:"/>
    <c:set var="formPath" value="/owner/clients/add"/>
  </c:otherwise>
</c:choose>


<div class="container-fluid" id="body">

  <div class="row">
    <div class="col-xs-12 col-md-10 col-md-offset-1">
      <p class="userTitle">${showTitle}</p>
    </div>
  </div>

  <div class="row">
    <div class="col-xs-12 col-md-10 col-md-offset-1" >
      <form:form method="post" action="${formPath}" cssClass="form-horizontal" commandName="client">
        <div class="form-group">
          <label for="name" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Название<sup>*</sup>:</label>
          <div class="col-xs-8 col-md-5 col-lg-4">
            <form:input path="name" id="name" cssClass="form-control" placeholder="Укажите имя" value="${client.name}"></form:input>
            <form:errors path="name" cssClass="errorMessage"></form:errors>
          </div>
        </div>

        <div class="form-group">
          <label for="phoneNumber" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">№ телефона:</label>
          <div class="col-xs-8 col-md-5 col-lg-4">
            <form:input path="phoneNumber" id="phoneNumber" cssClass="form-control" placeholder="Номер телефона" value="${client.phoneNumber}"/>
            <form:errors path="phoneNumber" cssClass="errorMessage"></form:errors>
          </div>
        </div>

        <div class="form-group">
          <label for="IsPayByCash" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Наличные<sup>*</sup>:</label>
          <div class="col-xs-8 col-md-5 col-lg-4">
            <form:checkbox path="isPayByCash" id="IsPayByCash" cssClass="form-control" value="${client.isPayByCash}"></form:checkbox>
            <form:errors path="isPayByCash" cssClass="errorMessage"></form:errors>
          </div>
        </div>

        <div class="form-group">
          <label for="priceMultiplicator" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Корректировка цены:</label>
          <div class="col-xs-8 col-md-5 col-lg-4">
            <form:select size="1" multiple="false" path="priceMultiplicator" id="priceMultiplicator" cssClass="form-control" itemLabel="${client.priceMultiplicator}">
              <c:forEach step="10" begin="50" end="150" varStatus="loop">
                <form:option value="${loop.current}">${loop.current}</form:option>
              </c:forEach>
            </form:select>
            <form:errors path="priceMultiplicator" cssClass="errorMessage"></form:errors>
          </div>
        </div>
        

        <div class="form-group">
          <div class="col-xs-12 col-md-offset-1 col-md-7 col-lg-offset-1 col-lg-6">
            <p class="asterisk"><sup>*</sup>Эти поля обязательны к заполнению</p>
          </div>
        </div>

        <div class="form-group">
          <div class="col-xs-12 col-md-offset-1 col-lg-offset-1 ">
            <button type="submit" class="btn btn-primary">&nbsp;&nbsp;Сохранить&nbsp;&nbsp;</button>
          </div>
        </div>

      </form:form>
    </div>
  </div>




</div>


</body>
</html>



