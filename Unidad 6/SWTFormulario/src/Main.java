import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class Main {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Hola");
		shell.setSize(500, 300);
		// Crear un botón
		Button button = new Button(shell, SWT.PUSH);
		// Botón de tipo PUSH
		button.setText("¡Haz clic aquí!"); // Texto del botón
		button.setBounds(50, 50, 150, 30); // Posición y tamaño (x, y, ancho, alto)
		// Agregar evento al botón 
		button.addListener(SWT.Selection, event -> { 
		MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION | SWT.OK); messageBox.setText("¡Mensaje!");
		messageBox.setMessage("Le diste clic crack"); messageBox.open(); });
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
