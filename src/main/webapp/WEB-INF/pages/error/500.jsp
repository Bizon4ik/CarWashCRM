<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:choose>
    <c:when test="${carWashId == null}">
        <c:set var="url" value="/admin"/>
        <jsp:include page="../admin/admin_header.jsp" />
    </c:when>
    <c:otherwise>
        <c:set var="url" value="/owner"/>
        <jsp:include page="../owner/owner_header.jsp" />
    </c:otherwise>
</c:choose>


<div class="container-fluid" id="body">
    <img src="/resources/img/sorry.jpg" />


    <blockquote>
        <p>Господа, мы тысячикратно извиняемся, но похоже мы где-то плотно наложали. Пришлите пожалуйста нам ниже указанное сообщение и мы все исправим. Еще раз тысячу извинений. </p>
        <footer>С Уважением нерадивые разработчики.</footer>
    </blockquote>


        <div class="row">
            <div class="col-xs-12" >
                <div class="bg-danger globarError">
                    <c:if test="${not empty exception.msg}">
                        ${exception.msg}
                    </c:if>
                </div>
            </div>
        </div>

</div>

</body>
</html>