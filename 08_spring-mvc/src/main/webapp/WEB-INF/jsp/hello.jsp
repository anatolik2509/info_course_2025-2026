<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<c:url value="/css/main.css"/> ">
</head>
<body>
<a href="${s:mvcUrl('MC#index').build()}">Default page</a>,
<a href="${s:mvcUrl('MC#simple').build()}">Simple action</a>,
<a href="${s:mvcUrl('MC#simpleMultiple').build()}">Simple action accessible by few URLs</a>,
<a href="${s:mvcUrl('MC#responseBody').build()}">Response returned by controller</a>,
<a href="${s:mvcUrl('MC#writer').build()}">Using of java.io.Writer</a>,
<a href="${s:mvcUrl('MC#responseStatus').build()}">Response status code setting</a>,
<a href="${s:mvcUrl('MC#requestMappingBunch').build()}">Additional request mapping options</a>,
<a href="${s:mvcUrl('MC#requestParam').arg(0, 'someValue').build()}">Receiving request parameters</a>,
<a href="${s:mvcUrl('MC#requestParamBanch').arg(0, 'someValue').build()}">Additional request parameters receiving options</a>,
<a href="${s:mvcUrl('MC#pathVariable').arg(0, 'someValue').build()}">Path variable receiving</a>,
<a href="${s:mvcUrl('MC#pathVariableBunch').arg(0, 'someValue').arg(1, 'someValue2').build()}">Additional path variable receiving options</a>,
<a href="${s:mvcUrl('MC#requestHeader').build()}">Request header receiving</a>

<div style="border:3px double black;margin:100px; padding:30px">
    ${viewVariable}
</div>

</body>
</html>