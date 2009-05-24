<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>

<jsp:include page="/WEB-INF/views/general/header/header.jsp" flush="true">
	<jsp:param name="title" value="Realizar reserva" />
	<jsp:param name="header" value="logged" />
</jsp:include>

<h1>Realizar reserva de <%= request.getParameter("name") %></h1>

<form action="" method="post">

	<label for="name">Fecha: </label>
	<input id="datepicker" type="text" name="fecha" size="10"/>
	<input id="id" type="hidden" name="id" value="<%= request.getParameter("id") %>"/>
	<select id="from" name="from"></select>
	<select id="to" name="to"></select>
	<input type="submit" value="Agregar" />
</form>

<script src="/tp-pod/js/jquery/js/jquery-1.3.2.min.js" type="text/javascript"/></script>
<script src="/tp-pod/js/jquery/js/jquery-ui-1.7.1.custom.min.js" type="text/javascript"/></script>
<script src="/tp-pod/js/util/calendar.js" type="text/javascript"/></script>

</body>
</html>