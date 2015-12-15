package edu.uclm.esi.iso3.llamadas.procesador.gui;

import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.Rectangle;

import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JProgressBar;
import javax.swing.JLabel;

import edu.uclm.esi.iso3.llamadas.procesador.dominio.Constantes;
import edu.uclm.esi.iso3.llamadas.procesador.dominio.ProcesadorDeLlamadas;

public class JFProcesadorDeLlamadas extends JFrame implements IVentana {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton jbDirectorioDeTrabajo = null;
	private JTextField jtfDirectorioDeTrabajo = null;
	private JButton jbProcesarLlamadas = null;
	private JProgressBar jpbPorcentaje = null;
	private JLabel jlInfo = null;
	/**
	 * This method initializes jbDirectorioDeTrabajo	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbDirectorioDeTrabajo() {
		if (jbDirectorioDeTrabajo == null) {
			jbDirectorioDeTrabajo = new JButton();
			jbDirectorioDeTrabajo.setBounds(new Rectangle(21, 26, 216, 43));
			jbDirectorioDeTrabajo.setText("Directorio de trabajo");
			jbDirectorioDeTrabajo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cargarDirectorioDeTrabajo();
				}
			});
		}
		return jbDirectorioDeTrabajo;
	}

	/**
	 * This method initializes jtfDirectorioDeTrabajo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtfDirectorioDeTrabajo() {
		if (jtfDirectorioDeTrabajo == null) {
			jtfDirectorioDeTrabajo = new JTextField();
			jtfDirectorioDeTrabajo.setBounds(new Rectangle(245, 34, 580, 28));
			jtfDirectorioDeTrabajo.setText("/Users/Maco/Documents/workspaceAspectJ/LlamadasGenerador/resources/");
		}
		return jtfDirectorioDeTrabajo;
	}
	
	protected void cargarDirectorioDeTrabajo() {
		JFileChooser chooser;
		if (this.jtfDirectorioDeTrabajo.getText().length()!=0)
			chooser = new JFileChooser(this.jtfDirectorioDeTrabajo.getText());
		else
			chooser= new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = chooser.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
		   this.jtfDirectorioDeTrabajo.setText(chooser.getSelectedFile().getAbsolutePath());
		}
	}

	/**
	 * This method initializes jbProcesarLlamadas	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbProcesarLlamadas() {
		if (jbProcesarLlamadas == null) {
			jbProcesarLlamadas = new JButton();
			jbProcesarLlamadas.setBounds(new Rectangle(295, 80, 215, 38));
			jbProcesarLlamadas.setText("Procesar llamadas");
			jbProcesarLlamadas.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					procesarLlamadas(jtfDirectorioDeTrabajo.getText());
				}
			});
		}
		return jbProcesarLlamadas;
	}

	public void procesarLlamadas(String directorio) {
		try {
			ProcesadorDeLlamadas d=new ProcesadorDeLlamadas(this.jtfDirectorioDeTrabajo.getText(), this);
			Thread t=new Thread(d);
			t.start();
		}
		catch (Exception e) {
			showError(e.toString());
		}
	}
	
	public void showInfo(String msg) {
		this.jlInfo.setForeground(Color.BLUE);
		this.jlInfo.setText(msg);
	}

	public void showError(String msg) {
		this.jlInfo.setForeground(Color.RED);
		this.jlInfo.setText(msg);
		this.jlInfo.setForeground(Color.BLUE);
	}

	public void showProgreso(int valor) {
		this.jpbPorcentaje.setValue(valor);
	}
	
	public void setProgresoMaximo(int total) {
		this.jpbPorcentaje.setMaximum(total);
	}
	
	/**
	 * This method initializes jpbPorcentaje	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	private JProgressBar getJpbPorcentaje() {
		if (jpbPorcentaje == null) {
			jpbPorcentaje = new JProgressBar();
			jpbPorcentaje.setBounds(new Rectangle(28, 182, 796, 19));
		}
		return jpbPorcentaje;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFProcesadorDeLlamadas thisClass = new JFProcesadorDeLlamadas();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
	}

	/**
	 * This is the default constructor
	 */
	public JFProcesadorDeLlamadas() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(850, 327);
		this.setContentPane(getJContentPane());
		this.setTitle("Procesador de llamadas telef—nicas");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jlInfo = new JLabel();
			jlInfo.setBounds(new Rectangle(28, 133, 797, 34));
			jlInfo.setText("");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJbDirectorioDeTrabajo(), null);
			jContentPane.add(getJtfDirectorioDeTrabajo(), null);
			jContentPane.add(getJbProcesarLlamadas(), null);
			jContentPane.add(getJpbPorcentaje(), null);
			jContentPane.add(jlInfo, null);
		}
		return jContentPane;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
