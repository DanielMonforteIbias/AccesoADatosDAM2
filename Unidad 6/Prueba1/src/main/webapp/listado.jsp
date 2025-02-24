<%@page import = " componenteDept.*, java.util.*"%>
<html><head><title>LISTADO DE DEPARTAMENTOS</title></head>
<body>
<center> 
<h2>LISTADO DE DEPARTAMENTOS</h2>
<table border = '1'>
<tr><th>Departamento</th><th>Nombre</th><th>Localidad</th></tr>
<%
ArrayList listadep = (ArrayList)request.getAttribute("departamentos");
if(listadep != null)
  for(int i = 0; i<listadep.size(); i++){
   Departamento d = (Departamento)listadep.get(i); %>  
   <tr><td><%=d.getDeptno()%></td>
   <td><%=d.getDnombre()%></td>
   <td><%=d.getLoc()%></td>
   </tr>
   <%}%>
</table><br/><br/>
<a href="index.html">Inicio</a>
</center>
</body>
</html>
