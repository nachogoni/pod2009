
<%@page import="com.canchita.model.user.CommonUser"%><html>
<body>
<h2>Hello World!</h2>
<% CommonUser lal = new CommonUser();%>
<% System.out.println(lal.getIsGuest()); %>
</body>
</html>
