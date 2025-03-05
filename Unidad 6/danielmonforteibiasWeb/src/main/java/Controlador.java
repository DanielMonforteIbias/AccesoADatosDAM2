
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import danielmonforteibias.*;

@WebServlet("/Controlador")
public class Controlador extends HttpServlet {
	private static final long serialVersionUID = 1L;

	VueloDAO vuelos = new VueloImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controlador() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());

		String accion = request.getParameter("accion");
		
		
		if (accion!=null && accion.equals("borrarvuelo")) {
			// sólo se borrarán los vuelos con 0 pasajeros.
			String idvuelo = request.getParameter("id");
			System.out.println("borarr " + idvuelo);
			String mensaje = vuelos.eliminarVuelo(idvuelo);
			System.out.println("mensaje " + mensaje);
			ArrayList<Vuelo> lista = vuelos.obtenerVuelos();
			HashMap<String, Integer> mapaPasajes = new HashMap<>();
			for(Vuelo v:lista) {
				mapaPasajes.put(v.getIdentificador(), vuelos.obtenerPasajesVuelo(v.getIdentificador()).size());
			}
			request.setAttribute("mensajeborrar", mensaje);
			request.setAttribute("lista", lista);
			request.setAttribute("mapaPasajes", mapaPasajes);
			RequestDispatcher rd = request.getRequestDispatcher("/listadovuelos.jsp");
			rd.forward(request, response);
			
			
		}
		
		
		
		if (accion!=null && accion.equals("verpasajevuelo")) {
			ArrayList<Vuelo> lista = vuelos.obtenerVuelos();
			request.setAttribute("lista", lista);
			RequestDispatcher rd = request.getRequestDispatcher("/verpasajevuelo.jsp");
			rd.forward(request, response);
			
		}
		
		if (accion!=null && accion.equals("listarvuelos")) {
			// Cargamos los datos de los vuelos

			System.out.println("entro");
			ArrayList<Vuelo> lista = vuelos.obtenerVuelos();
			HashMap<String, Integer> mapaPasajes = new HashMap<>();
			for(Vuelo v:lista) {
				mapaPasajes.put(v.getIdentificador(), vuelos.obtenerPasajesVuelo(v.getIdentificador()).size());
			}
			request.setAttribute("lista", lista);
			request.setAttribute("mapaPasajes", mapaPasajes);
			RequestDispatcher rd = request.getRequestDispatcher("/listadovuelos.jsp");
			rd.forward(request, response);

		}

		if (accion!=null && accion.equals("iradetalle")) {

			ArrayList<Vuelo> lista = vuelos.obtenerVuelos();
			request.setAttribute("lista", lista);
			RequestDispatcher rd = request.getRequestDispatcher("/verdetallesvuelo.jsp");
			rd.forward(request, response);

		}

		if ( accion!=null && accion.equals("modificarborrar")) {

			ArrayList<Pasaje> lista = vuelos.getPasajes();
			request.setAttribute("lista", lista);
			RequestDispatcher rd = request.getRequestDispatcher("/modificarborrarpasaje.jsp");
			rd.forward(request, response);

		}

		if (accion!=null && accion.equals("borrar")) {

			String id = request.getParameter("id");
			System.out.println("borarr " + id);
			String mensaje = vuelos.borrarPasaje(Integer.parseInt(id));

			ArrayList<Pasaje> lista = vuelos.getPasajes();
			request.setAttribute("lista", lista);
			request.setAttribute("mensaje", mensaje);
			RequestDispatcher rd = request.getRequestDispatcher("/modificarborrarpasaje.jsp");
			rd.forward(request, response);

		}

		if (accion!=null && accion.equals("modificar")) {

			String id = request.getParameter("id");
			System.out.println("modificar " + id);

			Pasaje pasaje = vuelos.getPasaje(Integer.parseInt(id));

			ArrayList<Vuelo> lista = vuelos.obtenerVuelos();
			ArrayList<Pasajero> listapas = vuelos.getPasajeros();

			request.setAttribute("titulo", "MODIFICAR PASAJE: " + id);
			request.setAttribute("listavuelos", lista);
			request.setAttribute("listapasajeros", listapas);
			request.setAttribute("pasaje", pasaje);

			RequestDispatcher rd = request.getRequestDispatcher("/insertarmodificar.jsp");
			rd.forward(request, response);

		}

		if (accion!=null && accion.equals("insertarpasaje")) {

			ArrayList<Vuelo> lista = vuelos.obtenerVuelos();
			ArrayList<Pasajero> listapas = vuelos.getPasajeros();

			request.setAttribute("titulo", "INSERTAR PASAJE");
			request.setAttribute("listavuelos", lista);
			request.setAttribute("listapasajeros", listapas);
			RequestDispatcher rd = request.getRequestDispatcher("/insertarmodificar.jsp");
			rd.forward(request, response);

		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);

		// name="verdetallevuelo"

		String accion = request.getParameter("accion");

		if (accion != null && accion.equals("verpasv")) {

			String identificador = request.getParameter("identificador");

			ArrayList<Pasaje> listapasaje = vuelos.obtenerPasajesVuelo(identificador);
			Vuelo vuelo = vuelos.consultarVuelo(identificador);
			ArrayList<Vuelo> lista = vuelos.obtenerVuelos();

			request.setAttribute("vu", vuelo);
			request.setAttribute("ident", identificador);
			request.setAttribute("listapasaje", listapasaje);
			request.setAttribute("lista", lista);
			RequestDispatcher rd = request.getRequestDispatcher("/verpasajevuelo.jsp");
			rd.forward(request, response);
		}

		String verdetallevuelo = request.getParameter("verdetallevuelo");

		if (verdetallevuelo != null) {

			System.out.println("entro  verdetallevuelo");

			String identificador = request.getParameter("identificador");

			Vuelo vuelo = vuelos.consultarVuelo(identificador);

			ArrayList<Vuelo> lista = vuelos.obtenerVuelos();
			ArrayList<Pasaje> listapasaje = vuelos.obtenerPasajesVuelo(identificador);
			request.setAttribute("listapasaje", listapasaje);
			request.setAttribute("ident", identificador);
			request.setAttribute("vuelo", vuelo);
			request.setAttribute("lista", lista);
			RequestDispatcher rd = request.getRequestDispatcher("/verdetallesvuelo.jsp");
			rd.forward(request, response);

		}

		String verpasajevuelo = request.getParameter("verpasajevuelo");

		if (verpasajevuelo != null) {

			System.out.println("entro  verpasajevuelo");
			String identificador = request.getParameter("identificador");

			ArrayList<Pasaje> listapasaje = vuelos.obtenerPasajesVuelo(identificador);

			ArrayList<Vuelo> lista = vuelos.obtenerVuelos();

			request.setAttribute("ident", identificador);
			request.setAttribute("listapasaje", listapasaje);
			request.setAttribute("lista", lista);
			RequestDispatcher rd = request.getRequestDispatcher("/verdetallesvuelo.jsp");
			rd.forward(request, response);

		}
		
		String insertar=request.getParameter("insertar");
		if(insertar!=null) {
			int pasajeroCod = Integer.parseInt(request.getParameter("pasajerocod"));
	        String identificador = request.getParameter("identificador");
	        int numAsiento = Integer.parseInt(request.getParameter("numasiento"));
	        String clase = request.getParameter("clase");
	        int pvp = Integer.parseInt(request.getParameter("pvp"));

	        Pasaje pasaje = new Pasaje();
	        int pasajeCod = vuelos.getPasajes().stream().mapToInt(Pasaje::getIdPasaje).max().orElse(1)+1; //OBTENEMOS EL MAXIMO+1 

	        pasaje.setIdPasaje(pasajeCod);
	        pasaje.setIdentificador(identificador);
	        pasaje.setPasajero(vuelos.getPasajero(pasajeroCod));
	        pasaje.setNumAsiento(numAsiento);
	        pasaje.setClase(clase);
	        pasaje.setPvp(pvp);
	        System.out.println(pasaje.getIdentificador());
	        String mensaje=vuelos.insertarPasaje(pasaje);
	        System.out.println(mensaje);
	        ArrayList<Vuelo> lista = vuelos.obtenerVuelos();
			ArrayList<Pasajero> listapas = vuelos.getPasajeros();

			request.setAttribute("titulo", mensaje);
			request.setAttribute("listavuelos", lista);
			request.setAttribute("listapasajeros", listapas);
			RequestDispatcher rd = request.getRequestDispatcher("/insertarmodificar.jsp");
			rd.forward(request, response);
		}

	}

}