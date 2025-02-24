<!DOCTYPE html>
<html><head><title>ALTA DE DEPARTAMENTOS</title></head>
<!--Formulario de entrada de datos e Inserción en el JavaBean -->
<jsp:useBean id = "depart" scope = "request" 
             class = "pantalla.Departamentos" />
<jsp:setProperty name = "depart" property = "*"/>

<%
 if(request.getParameter("deptno")!= null) { %>
<jsp:forward page="/Controlador?accion=insertar"/>
<% } %>
<body>
  <center><h2>ENTRADA DE DATOS DE DEPARTAMENTOS</h2>
  <form method="post">
        <p>N&uacute;mero de departamento:
        <input name="deptno" required 
               type="number" min="1" max="99" /> </p>
        <p>Nombre: 
        <input name="dnombre" 
               required type="text" size="15" maxlength="15" /> </p>
        <p>Localidad: 
        <input name="loc" required type="text" 
               size="15" maxlength="15" /> </p>          
        <input type="submit" name="insertar" 
                             value="Insertar departamento." />
        <input type="reset" name="cancelar" 
                            value="Cancelar entrada." />
  </form>   
  <a href="index.html">Inicio</a>
  </center>
</body>
</html>
