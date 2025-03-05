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
<div align="center">
		<%
			ArrayList<Pasajero> listapas = (ArrayList) request.getAttribute("listapasajeros");
		ArrayList<Vuelo> listavu = (ArrayList) request.getAttribute("listavuelos");

		Pasaje pasaje = (Pasaje) request.getAttribute("pasaje");

		String ident = "";
		
		int idetpas = 0, numa=0;
		int pvp=0;
		String chekt="checked", chekb="", chekp="";
  
		if (pasaje!=null){
			ident = pasaje.getIdentificador();
			idetpas=pasaje.getPasajero().getPasajeroCod();
			numa=pasaje.getNumAsiento();
			pvp=pasaje.getPvp();
			if (pasaje.getClase().equals("TURISTA")) chekt="checked";
			if (pasaje.getClase().equals("BUSINES")) chekb="checked";
			if (pasaje.getClase().equals("PRIMERA")) chekp="checked";
			
		}
		
		
		String titulo = (String) request.getAttribute("titulo");

		out.println("<h2>" + titulo + "</h2>");
		%>


		<form method="post" action="/danielmonforteibiasWeb/Controlador">

			<p>
				Selecciona viajero: <select name='pasajerocod'>
					<%
						String selec = "";
					for (int i = 0; i < listapas.size(); i++) {
						Pasajero dep = (Pasajero) listapas.get(i);
						selec = "";
						if (idetpas== dep.getPasajeroCod()) {
							selec = "selected";
						}
						out.println("<option " + selec + " value=" + dep.getPasajeroCod() + ">" + dep.getPasajeroCod() + " - "
						+ dep.getNombre() + "</option>");
					}
					%>

				</select>
			</p>

			<p>
				Selecciona un vuelo: <select name='identificador'>
					<%
						selec = "";
					for (int i = 0; i < listavu.size(); i++) {
						Vuelo dep = (Vuelo) listavu.get(i);
						selec = "";
						if (ident.equals(dep.getIdentificador())) {
							selec = "selected";
						}
						out.println("<option " + selec + " value='" + dep.getIdentificador() + "'>" + dep.getIdentificador() + "</option>");
					}
					%>

				</select>
			</p>
			<p>
				Num asiento: <input name="numasiento" required type="number"
				size="15" maxlength="15" value="<%=numa%>" />
			</p>
			<p>
				Selecciona la clase: 
				
				<input type="radio" name="clase"
					value="TURISTA" <%=chekt %> > TURISTA <input type="radio"
					name="clase" value="PRIMERA" <%=chekp %> > PRIMERA <input type="radio"
					name="clase" value="BUSINES" <%=chekb %> > BUSINES
			</p>

			<p>
				PVP: <input name="pvp" required type="number" value="<%=pvp%>" size="15"
					maxlength="15" />
			</p>
			<p>
				<input type="submit" name="insertar" value="Insertar Pasaje" />
			</p>

		</form>

<p align='center'>
			<a href="/danielmonforteibiasWeb/index.jsp">Volver al inicio</a>
		</p>
</body>
</html>