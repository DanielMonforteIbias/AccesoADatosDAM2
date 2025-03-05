<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="danielmonforteibias.*, java.util.* " %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Listado vuelos</title>
</head>
<body>
<%
ArrayList<Vuelo> lista = (ArrayList) request.getAttribute("lista");
HashMap<String,Integer>mapaPasajes=(HashMap)request.getAttribute("mapaPasajes");
String mensaje= (String) request.getAttribute("mensajeborrar");
%>

	<div align="center">
	<h1 align="center">OPERACIONES CON VUELOS</h1>
	<h2 align="center">EXAMEN REALIZADO POR: Daniel Monforte Ibias</h2>
	<hr>
	  <h2>LISTADO DE LOS VUELOS</h2>
	  
	  	  <%
	
	  	    if (mensaje!=null) {out.println("<h3>"+mensaje+"</h3>");}
	  	  %>
      
	  <table border=1><tr><th>Identificador</th><th>Aeropuerto <br>de origen</th>
	  <th>Nombre<br>Aeropuerto</th><th>País de<br>origen</th><th>Aeropuerto <br>de destino</th>
	  <th>Nombre<br>Aeropuerto</th><th>País de<br>destino</th><th>Tipo Vuelo</th>
	  <th>Número <br>Pasajeros</th>
	  <th>Borrar<br>vuelo</th>
	  </tr>
	  <%
	 
	   for (int i=0; i<lista.size();i++){
		   Vuelo vu=lista.get(i);
		   out.println("<tr>");
		   out.println("<td>"+vu.getIdentificador()+"</td>");
		   out.println("<td>"+vu.getAeropuertoOrigen().getCodAeropuerto()+"</td>");
		   out.println("<td>"+vu.getAeropuertoOrigen().getNombre()+"</td>");
		   out.println("<td>"+vu.getAeropuertoOrigen().getPais()+"</td>");
		   out.println("<td>"+vu.getAeropuertoDestino().getCodAeropuerto()+"</td>");
		   out.println("<td>"+vu.getAeropuertoDestino().getNombre()+"</td>");
		   out.println("<td>"+vu.getAeropuertoDestino().getPais()+"</td>");
		   out.println("<td>"+vu.getTipoVuelo()+"</td>");
		   out.println("<td>"+mapaPasajes.get(vu.getIdentificador())+"</td>");
		   if (+mapaPasajes.get(vu.getIdentificador()) == 0 )
		   out.println("<td><a href='/danielmonforteibiasWeb/Controlador?accion=borrarvuelo&id="+ vu.getIdentificador()+"'>Borrar</a></td>");
		   else
			  out.println("<td>Borrar</td>");
		    
		   out.println("</tr>");
		   
	   }
	  
	  %>


<p align='center'>
            <a href = "/danielmonforteibiasWeb/index.jsp">Volver al inicio</a> |
		  <a href="/danielmonforteibiasWeb/Controlador?accion=verpasajevuelo"> Ver el pasaje de un vuelo</a>
	</p>
</body>
</html>