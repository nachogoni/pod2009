
<%@page import="com.canchita.model.location.Place"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%><html>
<body>
<h2>HelloWorld!</h2>

<%
	List<String> tels = new ArrayList<String>();
	tels.add("4444-5555");
	Place place = new Place("Madero 339", tels,"1233", "Pto Madero", "Buenos Aires",
			"CABA", "Argentina", "-34.030303", "+54.3434334");
	place.addTelephone("5555-5555");
	
%>

<%= place.toString() %>

</body>
</html>