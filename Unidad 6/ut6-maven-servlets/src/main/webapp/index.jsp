<html>
<header>
	<title>Servlets</title>
</header>
<body>
	<h1>Servlets</h1>
	<p></p>
	<p></p>

	<h2>Método GET sin parámetros</h2>
	<form method="GET" action="/ut6-maven-servlets/ServletONE">
		<input type="submit">
	</form>
	<h2>Llama a ServletONE con parámetros</h2>
	<a href="/ut6-maven-servlets/ServletONE?mensaje=holaMundo"> Ir a
		/ut6-maven-servlets/ServletONE?mensaje=HolaMundo </a>

	<h2>Método POST sin parámetros</h2>
	<form method="POST" action="/ut6-maven-servlets/ServletTWO">
		<input type="submit">
	</form>

	<h2>Llama a ServletTWO con parámetros</h2>
	<a href="/ut6-maven-servlets/ServletTWO"> Ir a
		/ut6-maven-servlets/servlet2 </a>

</body>
</html>
