<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../owner_header.jsp" />

<c:choose>
  <c:when test="${title == 'update'}">
    <c:set var="showTitle" value="Обновить категорию #${category.id}"/>
    <c:set var="formPath" value="/owner/category/update/${category.id}"/>
  </c:when>
  <c:otherwise>
    <c:set var="showTitle" value="Добавить категорию:"/>
    <c:set var="formPath" value="/owner/category/add"/>
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
      <form:form method="post" action="${formPath}" cssClass="form-horizontal" commandName="category">

        <div class="form-group">
          <label for="name" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Название<sup>*</sup>:</label>
          <div class="col-xs-8 col-md-5 col-lg-4">
            <form:input path="name" id="name" cssClass="form-control" placeholder="Название категории" value="${category.name}"/>
            <form:errors path="name" cssClass="errorMessage"/>
          </div>
        </div>

        <div class="form-group">
          <label for="description" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Описание<sup>*</sup>:</label>
          <div class="col-xs-8 col-md-5 col-lg-4">
            <form:input path="description" id="description" cssClass="form-control" placeholder="Описание категории" value="${category.description}"/>
            <form:errors path="description" cssClass="errorMessage"/>
          </div>
        </div>

        <div class="form-group">
          <div class="col-xs-12 col-md-offset-1 col-md-7 col-lg-offset-1 col-lg-6">
            <p class="asterisk"><sup>*</sup>Эти поля обязательны к заполнению</p>
          </div>
        </div>

        <div class="form-group">
          <div class="col-xs-12 col-md-offset-1 col-md-7 col-lg-offset-1 col-lg-6">
            <button type="submit" class="btn btn-primary">Добавить</button>
          </div>
        </div>

      </form:form>


    </div>
  </div>


</div>


</body>
</html>