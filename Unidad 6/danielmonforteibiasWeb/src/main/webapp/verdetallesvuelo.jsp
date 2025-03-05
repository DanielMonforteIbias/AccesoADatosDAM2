<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="danielmonforteibias.*, java.util.* "%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ver detalles vuelo</title>
</head>
<body>
<%
		ArrayList<Vuelo> lista = (ArrayList) request.getAttribute("lista");
		String ident = (String) request.getAttribute("ident");
		if (ident == null)
			ident = "";

		Vuelo vu = (Vuelo) request.getAttribute("vuelo");

		ArrayList<Pasaje> listapasaje = (ArrayList) request.getAttribute("listapasaje");
	%>

	<div align="center">
		<h2>Detalle de los vuelos</h2>
		<br>
		<br>
		<form action="/danielmonforteibiasWeb/Controlador" method="post">

			Selecciona un vuelo: <select name='identificador'>
				<%
					String selec = "";
				for (int i = 0; i < lista.size(); i++) {
					Vuelo dep = (Vuelo) lista.get(i);
					selec = "";
					if (ident.equals(dep.getIdentificador())) {
						selec = "selected";
					}
					out.println("<option " + selec + " value='" + dep.getIdentificador() + "'>" + dep.getIdentificador() + "</option>");
				}
				%>
			</select> <input type="submit" name="verdetallevuelo"
				value="Ver detalle del vuelo" /> <input type="submit"
				name="verpasajevuelo" value="Ver pasaje del vuelo" />
		</form>
		<br>
		<br>
		<hr>
		<%
			if (vu != null) {
			out.println("<h2>DETALLE DEL VUELO CON IDENTIFICADOR: " + ident + "</h2>");

			out.println("<br>Aeropuerto de origen: " + vu.getAeropuertoOrigen().getCodAeropuerto());
			out.println("<br>Nombre aeropuerto de origen: " + vu.getAeropuertoOrigen().getNombre());
			out.println("<br>Pais aeropuerto de origen:" + vu.getAeropuertoOrigen().getPais());
			out.println("<br>Aeropuerto de destino: " + vu.getAeropuertoDestino().getCodAeropuerto());
			out.println("<br>Nombre Aeropuerto de destino: " + vu.getAeropuertoDestino().getNombre());
			out.println("<br>Nombre pais de destino: " + vu.getAeropuertoDestino().getPais());
			out.println("<br>Tipo de vuelo: " + vu.getTipoVuelo());
			if(listapasaje!=null)out.println("<br>Número de pasajeros: " + listapasaje.size());
			else out.println("<br>Número de pasajeros: " + 0);
		}
		%>


		<%
			if (listapasaje != null) {
		%>
		<h2>LISTADO DEL PASAJE DELVUELO: <%=ident%></h2>
		<%
			if (listapasaje.size() == 0) {
				out.println("<h3>VUELO SIN PASAJE</h3>");
            
		} else {
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
				out.println("</tr>");

			}
			%>
		</table>

		<%
			}
		%>

		<%
			}
		%>
		<hr>


<p align='center'>
            <a href = "/danielmonforteibiasWeb/index.jsp">Volver al inicio</a>
        </p>
</body>
</html>