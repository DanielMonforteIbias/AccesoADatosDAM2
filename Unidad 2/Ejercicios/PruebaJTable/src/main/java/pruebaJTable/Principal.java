package pruebaJTable;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.sql.*;
import java.util.List;
import java.util.Vector;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Principal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modelo;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		setTitle("PruebaJTable");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 435);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitulo = new JLabel("Pruebas JTable y ResultSetMetadata");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setForeground(new Color(0, 0, 255));
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitulo.setBounds(139, 11, 334, 28);
		contentPane.add(lblTitulo);
		
		JButton btnEmpleados = new JButton("Ver empleados");
		btnEmpleados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				llenarTablaBD("empleados");
			}
		});
		btnEmpleados.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnEmpleados.setBounds(36, 50, 162, 23);
		contentPane.add(btnEmpleados);
		
		JButton btnDepartamentos = new JButton("Ver departamentos");
		btnDepartamentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				llenarTablaBD("departamentos");
			}
		});
		btnDepartamentos.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDepartamentos.setBounds(224, 50, 162, 23);
		contentPane.add(btnDepartamentos);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(36, 108, 516, 277);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Se ha pulsado la fila "+table.getSelectedRow());
				// Creamos un vector para recuperar la fila, partiendo del modelo
				Vector datostabla = modelo.getDataVector();
				// Los pasamos a una lista
				List datosFila = (List) datostabla.get(table.getSelectedRow());
				//Visualizamos los datos de la fila, si no está vacía
				if (datosFila.size() > 0) {
					 for (int i = 0; i < datosFila.size(); i++) {
					   System.out.println("Dato " + i + " : " + datosFila.get(i));
					}
				}
				//Tambien se puede hacer System.out.println(datosFila);
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"", null, null, null, null},
				{null, "", null, null, null},
				{"", null, null, null, null},
				{null, null, "", "", null},
				{null, null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
				"Columna 1", "Columna 2", "Columna 3", "Columna 4", "Columna 5"
			}
		));
		scrollPane.setViewportView(table);
		
		JButton btnOtrosDatos = new JButton("Ver otros datos");
		btnOtrosDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				llenarJTable();
			}
		});
		btnOtrosDatos.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnOtrosDatos.setBounds(405, 50, 147, 23);
		contentPane.add(btnOtrosDatos);
	}
	private void llenarJTable() {
		String[] etiquetas = {"Nombre", "Apellido", "Tlf"};
		Object[][] datos = { {"Ali",   "Ramos", "1233"},{"Dori",   "Gil", "125533"},{"Juan",   "Sánchez", "333"}};
			
		//Para llenar el JTABLE se necesita un DefaultTableModel
		//Si lo cargo con mis datos lo hago así
		modelo = new DefaultTableModel(datos, etiquetas);
		modelo.setColumnIdentifiers(etiquetas);
		modelo.setDataVector(datos, etiquetas);

		//Asignamos el modelo a la tabla
		table.setModel(modelo);
		Color fg = Color.PINK;
		table.setBackground(fg);
		table.setForeground(Color.BLUE);	

	}
	private void llenarTablaBD(String nombreTabla) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "ejemplo", "dam"); 
			Statement sentencia = conexion.createStatement();
			String consulta = "SELECT *  FROM "+nombreTabla;
			ResultSet resul = sentencia.executeQuery(consulta);
			ResultSetMetaData rsmd = resul.getMetaData();
			int nColumnas = rsmd.getColumnCount(); // Número de columna
			
			String consulta2 = "SELECT COUNT(*)  FROM "+nombreTabla; //Consulta para obtener el numero de filas
			Statement sentencia2 = conexion.createStatement();
			ResultSet resul2 = sentencia2.executeQuery(consulta2); 
			resul2.next(); //Si devuelve un solo registro, hacemos esto
			int filas=resul2.getInt(1);
			sentencia2.close();
			resul2.close();
			
			//Creamos los Arrays
			String[] etiquetas = new String[nColumnas];
			Object[][] datos = new Object[filas][nColumnas];
			
			//Llenamos los Arrays
			
			//Lleno el array de etiquetas
			for (int i = 1; i <= nColumnas; i++) {
				rsmd.getColumnName(i);
				System.out.println("Añado la columna " + rsmd.getColumnName(i).toUpperCase());
				etiquetas[i - 1] = rsmd.getColumnName(i).toUpperCase();
			}
			//Lleno el array de datos
			int numeroFila = 0;
			resul = sentencia.executeQuery(consulta);
			while (resul.next()) {
			   //Bucle para cada fila, añadir las columnas 
		         for (int i = 0; i < nColumnas; i++) {
					datos[numeroFila][i] = resul.getObject(i + 1);
					System.out.println("Añado la columna " + i + ", datos " + resul.getString(i + 1));
				}
			   numeroFila++;
			}

			modelo = new DefaultTableModel(datos, etiquetas);
			modelo.setColumnIdentifiers(etiquetas); //esto puede sobrar
			modelo.setDataVector(datos, etiquetas); //esto puede sobrar
			
		       // Asignamos el modelo a la tabla
			table.setModel(modelo);
			Color fg = Color.PINK;
			table.setBackground(fg);
			table.setForeground(Color.BLUE);

			resul.close();
			conexion.close();

			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
