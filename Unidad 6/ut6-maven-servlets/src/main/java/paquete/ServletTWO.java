package paquete;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletTWO
 */
@WebServlet("/ServletTWO")
public class ServletTWO extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletTWO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Servido en: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Servido en: ").append(request.getContextPath());
		// Se establece el tipo MIME del mensaje de respuesta.
		response.setContentType("text/html");
		// Se crea un flujo de salida para escribir la respuesta a la petición del
		// cliente.
		PrintWriter out = response.getWriter();
		// Se escribe el mensaje de respuesta en una página html
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Soy ServletTWO</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h2>ServletTWO!</h2>");
		out.println("</body>");
		out.println("</html>");

		String mensaje = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		;
		if (mensaje != null) {
			out.println(mensaje);
		}
	}

}
