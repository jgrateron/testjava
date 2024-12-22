package com.fresco;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class PelotaRebotando extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panel; // Panel donde se dibujan los componentes
	private int pelotaX, pelotaY; // Posición de la pelota en coordenadas X e Y
	private int velocidadVertical; // Velocidad vertical de la pelota
	private final int DIAMETRO_PELOTA = 20; // Diámetro de la pelota
	private final int ANCHO_EDIFICIO = 100; // Ancho del edificio
	private int ALTURA_EDIFICIO; // Altura del edificio, que se calcula dinámicamente
	private final int GRAVEDAD = 1; // Valor de la gravedad
	private double PERDIDA_ENERGIA = 0.2; // Factor de pérdida de energía en cada rebote (modificable)
	private boolean estaCayendo = false; // Indica si la pelota está cayendo o no
	private final int POSICION_X_EDIFICIO = 100; // Posición X del edificio
	private final int ANCHO_VENTANA = 20; // Ancho de las ventanas
	private final int ALTO_VENTANA = 20; // Alto de las ventanas
	private final int ESPACIO_VENTANAS = 10; // Espacio entre ventanas y bordes del edificio
	private final int FILAS_VENTANAS = 15; // Número de filas de ventanas
	private final int ANCHO_PUERTA = 30; // Ancho de la puerta
	private final int ALTO_PUERTA = 50; // Alto de la puerta
	private final int DESPLAZAMIENTO_Y_PUERTA = 0; // Desplazamiento vertical de la puerta
	private int posicionInicialPelotaX; // Posición X inicial de la pelota
	private int posicionInicialPelotaY; // Posición Y inicial de la pelota
	private Timer temporizador; // Temporizador para la animación
	private int ALTO_PANTALLA = 1000; // Alto de la pantalla
	private final int ANCHO_PANTALLA = 600; // Ancho de la pantalla
	private int POSICION_Y_SUELO; // Posición Y del suelo, que se calcula dinámicamente
	private double PORC_POSICION_Y_SUELO = 0.9;
	private int contadorRebotes = 0; // Contador de rebotes
	private JSpinner spinnerPerdidaEnergia; // Spinner para modificar la pérdida de energía
	private JButton botonReiniciar; // Botón para reiniciar la pelota
	private JButton botonIniciarPausar; // Botón para iniciar/pausar la pelota

	public PelotaRebotando() {
		setTitle("Pelota Rebotando"); // Establece el título de la ventana
		setSize(ANCHO_PANTALLA, ALTO_PANTALLA); // Establece el tamaño de la ventana
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra la aplicación al cerrar la ventana
		setLocationRelativeTo(null); // Centra la ventana en la pantalla

		// Creación del Spinner
		SpinnerNumberModel modeloSpinner = new SpinnerNumberModel(0.2, -1.0, 1.0, 0.02);
		spinnerPerdidaEnergia = new JSpinner(modeloSpinner);
		spinnerPerdidaEnergia.setPreferredSize(new Dimension(80, 30)); // Establece el tamaño del JSpinner

		// Agrega el spinner a un panel para situarlo en la parte superior de la ventana
		JPanel panelSpinner = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel etiqueta = new JLabel("Pérdida de Energía: ");
		etiqueta.setFont(new Font("Arial", Font.PLAIN, 14)); // Establece el tamaño de la fuente de la etiqueta
		panelSpinner.add(etiqueta);
		panelSpinner.add(spinnerPerdidaEnergia);

		// Crea el botón de reinicio
		botonReiniciar = new JButton("Reiniciar");
		botonReiniciar.addActionListener((e) -> {
			reiniciarPelota(); // Llama al método para reiniciar la pelota al pulsar el botón
		});
		// Agrega el botón de reinicio al panel junto al spinner
		panelSpinner.add(botonReiniciar);

		// Crea el botón de inicio/pausa
		botonIniciarPausar = new JButton("Iniciar/Pausar");
		botonIniciarPausar.addActionListener((e) -> {
			if (!estaCayendo) {
				estaCayendo = true;
			} else {
				estaCayendo = false;
			}
		});
		// Agrega el botón de inicio/pausa al panel junto al spinner y el botón de //
		// reinicio
		panelSpinner.add(botonIniciarPausar);
		add(panelSpinner, BorderLayout.NORTH); // Agrega el panel del spinner y botones al norte de la ventana
		// Listener para el spinner
		spinnerPerdidaEnergia.addChangeListener((e) -> {
			// Actualiza el valor de la pérdida de energía
			PERDIDA_ENERGIA = (double) spinnerPerdidaEnergia.getValue();
		});

		panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g; // Convierte el objeto Graphics a Graphics2D
				// Activa el anti-aliasing
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				// Calcula la posición del suelo y del edificio
				POSICION_Y_SUELO = (int) (getHeight() * PORC_POSICION_Y_SUELO);
				ALTURA_EDIFICIO = (int) (getHeight() * 0.57);
				g2d.setColor(Color.RED); // Establece el color de la pelota
				g2d.fillOval(pelotaX, pelotaY, DIAMETRO_PELOTA, DIAMETRO_PELOTA); // Dibuja la pelota
				// Dibuja el edificio sobre el suelo
				int posicionYEdificio = POSICION_Y_SUELO - ALTURA_EDIFICIO;
				g2d.setColor(Color.GRAY); // Establece el color del edificio
				g2d.fillRect(POSICION_X_EDIFICIO, posicionYEdificio, ANCHO_EDIFICIO, ALTURA_EDIFICIO);
				// Dibuja el // edificio
				g2d.setColor(Color.WHITE); // Establece el color de las ventanas
				for (int fila = 0; fila < FILAS_VENTANAS; fila++) {
					// Calcula la posición Y de cada ventana
					int y = posicionYEdificio + ESPACIO_VENTANAS + (fila * (ALTO_VENTANA + ESPACIO_VENTANAS));
					// Dibuja la ventana izquierda
					g2d.fillRect(POSICION_X_EDIFICIO + ESPACIO_VENTANAS, y, ANCHO_VENTANA, ALTO_VENTANA);
					// Dibuja la ventana derecha
					g2d.fillRect(POSICION_X_EDIFICIO + ANCHO_EDIFICIO - ANCHO_VENTANA - ESPACIO_VENTANAS, y,
							ANCHO_VENTANA, ALTO_VENTANA);
				}
				g2d.setColor(Color.DARK_GRAY); // Establece el color de la puerta
				// Calcula la posición X de la puerta
				int posicionXPuerta = POSICION_X_EDIFICIO + (ANCHO_EDIFICIO / 2) - (ANCHO_PUERTA / 2);
				// Calcula la posición Y de la puerta
				int posicionYPuerta = posicionYEdificio + ALTURA_EDIFICIO - ALTO_PUERTA - DESPLAZAMIENTO_Y_PUERTA;

				g2d.fillRect(posicionXPuerta, posicionYPuerta, ANCHO_PUERTA, ALTO_PUERTA); // Dibuja la puerta
				g2d.setColor(Color.BLACK); // Establece el color del suelo
				g2d.fillRect(0, POSICION_Y_SUELO, getWidth(), getHeight() - POSICION_Y_SUELO); // Dibuja el suelo
				// Dibuja el contador de rebotes
				g2d.setColor(Color.BLACK); // Establece el color del texto del contador
				g2d.setFont(new Font("Arial", Font.BOLD, 16)); // Establece la fuente del texto
				String textoContador = "Rebotes: " + contadorRebotes; // Crea el texto del contador
				FontMetrics fm = g2d.getFontMetrics(); // Obtiene las métricas de la fuente
				int anchoTexto = fm.stringWidth(textoContador); // Obtiene el ancho del texto
				int altoTexto = fm.getHeight(); // Obtiene el alto del texto
				int posicionXTexto = getWidth() - anchoTexto - 10; // Calcula la posición X del texto
				int posicionYTexto = 10 + altoTexto; // Calcula la posición Y del texto
				g2d.drawString(textoContador, posicionXTexto, posicionYTexto); // Dibuja el texto del contador
			}
		};
		panel.setDoubleBuffered(true); // Activa el doble buffer para evitar parpadeos
		add(panel); // Agrega el panel a la ventana

		// Calcula la posición X inicial de la pelota
		posicionInicialPelotaX = POSICION_X_EDIFICIO + ANCHO_EDIFICIO + 20;
		POSICION_Y_SUELO = (int) (getHeight() * PORC_POSICION_Y_SUELO); // Calcula la posición Y del suelo
		ALTURA_EDIFICIO = (int) (getHeight() * 0.57); // Calcula la altura del edificio
		posicionInicialPelotaY = POSICION_Y_SUELO - ALTURA_EDIFICIO; // Calcula la posición Y inicial de la pelota
		pelotaX = posicionInicialPelotaX; // Establece la posición X de la pelota
		pelotaY = posicionInicialPelotaY; // Establece la posición Y de la pelota
		velocidadVertical = 0; // Establece la velocidad vertical de la pelota a cero

		// Crea el temporizador para la animación
		temporizador = new Timer(20, (e) -> {
			if (estaCayendo) {
				actualizarAnimacion(); // Llama al método para actualizar la animación
				// Repinta el panel después de actualizar la animación
				SwingUtilities.invokeLater(() -> panel.repaint());
			}
		});
		temporizador.start();
		setFocusable(true); // Permite que la ventana obtenga el foco
		requestFocusInWindow(); // Solicita el foco para la ventana
	}

	private void actualizarAnimacion() {
		velocidadVertical += GRAVEDAD; // Incrementa la velocidad vertical por la gravedad
		pelotaY += velocidadVertical; // Actualiza la posición Y de la pelota
		
		if (pelotaY + DIAMETRO_PELOTA > POSICION_Y_SUELO) { // Si la pelota toca el suelo
			pelotaY = POSICION_Y_SUELO - DIAMETRO_PELOTA; // Ajusta la posición de la pelota
			if (Math.abs(velocidadVertical) < 2) { // Si la velocidad vertical es muy baja
				velocidadVertical = 0; // Detiene la velocidad vertical
				estaCayendo = false; // Detiene el movimiento de la pelota
			} else {
				// Invierte la velocidad con pérdida de energía
				velocidadVertical = (int) (-velocidadVertical * (1 - PERDIDA_ENERGIA));
				contadorRebotes++; // Incrementa el contador de rebotes
			}
		}
	}

	private void reiniciarPelota() {
		pelotaX = posicionInicialPelotaX; // Restablece la posición X de la pelota
		POSICION_Y_SUELO = (int) (getHeight() * PORC_POSICION_Y_SUELO);
		ALTURA_EDIFICIO = (int) (getHeight() * 0.57);
		pelotaY = POSICION_Y_SUELO - ALTURA_EDIFICIO; // Restablece la posición Y de la pelota
		velocidadVertical = 0; // Restablece la velocidad vertical de la pelota
		estaCayendo = false; // Detiene la caída de la pelota
		contadorRebotes = 0; // Restablece el contador de rebotes
		panel.repaint(); // Repinta el panel para mostrar la pelota en la posición inicial
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> { // Ejecuta la aplicación en el hilo de eventos de Swing
			PelotaRebotando app = new PelotaRebotando(); // Crea una instancia de la aplicación
			app.setVisible(true); // Hace visible la ventana
			app.setSize(600, 1000); // Establece el tamaño inicial de la ventana
		});
	}
}