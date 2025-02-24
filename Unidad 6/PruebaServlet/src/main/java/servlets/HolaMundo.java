package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HolaMundo
 */
@WebServlet("/HolaMundo")
public class HolaMundo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HolaMundo() {
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

		// Se establece el tipo MIME del mensaje de respuesta.
		response.setContentType("text/html");

		// Se crea un flujo de salida para escribir la respuesta a la petición del
		// cliente.
		PrintWriter out = response.getWriter();

		// Se escribe el mensaje de respuesta en una página html
		out.println("<html>");
		out.println("<head>");
		out.println("<title>HOLA MUNDO!</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Hola Mundo!</h1>");
		out.println("<h1>Probando. Primer servlet</h1>");
		out.println("</body>");
		out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>HOLA MUNDO!</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Hola Mundo! HOLAAAA MUNDO DOPOST!</h1>");
		out.println("<h1>Probando. Primer servlet. Metodo Post</h1>");
		out.println("</body>");
		out.println("</html>");
	}

}
