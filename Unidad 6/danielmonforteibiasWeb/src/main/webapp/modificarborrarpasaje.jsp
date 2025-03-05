<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="danielmonforteibias.*, java.util.* "%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%
	ArrayList<Pasaje> listapasaje = (ArrayList) request.getAttribute("lista");
	String mensaje = (String) request.getAttribute("mensaje");
	%>
	<div align="center">
		<h2>LISTADO DE PASAJE</h2>
		<%
		if (mensaje != null)
			out.println("<h2>" + mensaje + "</h2>");
		%>

		<table>
			<tr>
				<th>Id de pasaje</th>
				<th>Código pasajero</th>
				<th>nombre pasajero</th>
				<th>País pasajero</th>
				<th>Número de asiento</th>
				<th>clase</th>
				<th>PVP</th>
				<th>BORRAR/MODIFICAR</th>

			</tr>
			<%
			for (int i = 0; i < listapasaje.size(); i++) {
				Pasaje pas = listapasaje.get(i);
				out.println("<tr>");
				out.println("<td>" + pas.getIdPasaje() + "</td>");
				out.println("<td>" + pas.getPasajero().getPasajeroCod() + "</td>");
				out.println("<td>" + pas.getPasajero().getNombre() + "</td>");
				out.println("<td>" + pas.getPasajero().getPais() + "</td>");
				out.println("<td>" + pas.getNumAsiento() + "</td>");
				out.println("<td>" + pas.getClase() + "</td>");
				out.println("<td>" + pas.getPvp() + "</td>");
			%>
			<td><a
				href="/AAvuelos/Controlador?accion=borrar&id=<%=pas.getIdPasaje()%>">
					Borrar </a> <a
				href="/AAvuelos/Controlador?accion=modificar&id=<%=pas.getIdPasaje()%>">|
					Modificar </a></td>
			<%
			out.println("</tr>");

			}
			%>
		</table>
		<p align='center'>
			<a href="/danielmonforteibiasWeb/index.jsp">Volver al inicio</a>
		</p>
</body>
</html>