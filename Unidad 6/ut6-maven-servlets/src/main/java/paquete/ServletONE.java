package paquete;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletONE
 */
@WebServlet("/ServletONE")
public class ServletONE extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletONE() {
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
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Soy el ServletONE!</h1>");
		out.println("</body>");
		out.println("</html>");
		
		String mensaje = request.getParameter("mensaje"); 
		if (mensaje != null) {
			out.println(mensaje);	
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
