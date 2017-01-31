import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.JToggleButton;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JFormattedTextField;
import javax.swing.JCheckBox;




/*
 * IHM du serveur
 */

public class MyJFrameServeur extends JFrame {

	private JPanel contentPane;
	private JTextField tf_ip;
	private JLabel lblPort;
	private JTextField tf_port;
	private JButton btn_stop;
	private JButton btn_listen;
	private JButton btn_refresh;
	private JLabel label_status;
	private JCheckBox chckbxIpv;
	
	private boolean portIsOk = false;
	private boolean ipIsOk = false;
	private boolean portClientIsOk = false;
	private boolean ipClientIsOk = false;
	private boolean isIpV6 = false;
	
	private Server s;
	private Thread t;
	private JTextField tf_ip_client;
	private JTextField tf_port_client;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyJFrameServeur frame = new MyJFrameServeur();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructeur de la frame
	 */
	public MyJFrameServeur() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		setTitle("Peer 2 Peer EISC (Serveur)");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		
		Server s = new Server();
		
		
		
		/*
		 * Ajout de tout les composants
		 */
		JLabel lblip = new JLabel("@IP : ");
		lblip.setBounds(10, 11, 46, 14);
		contentPane.add(lblip);
		
		tf_ip = new JTextField();
		tf_ip.setBounds(51, 8, 143, 20);
		contentPane.add(tf_ip);
		tf_ip.setColumns(10);
		
		lblPort = new JLabel("Port : ");
		lblPort.setBounds(93, 39, 41, 14);
		contentPane.add(lblPort);
		
		tf_port = new JTextField();
		tf_port.setBounds(134, 36, 60, 20);
		contentPane.add(tf_port);
		tf_port.setColumns(10);
		
		btn_listen = new JButton("LISTEN");
		//btn_listen.set
		btn_listen.setBackground(new Color(0, 255, 102));
		btn_listen.setBounds(200, 11, 125, 33);
		contentPane.add(btn_listen);
		
		btn_stop = new JButton("STOP");
		btn_stop.setBackground(new Color(255, 0, 0));
		btn_stop.setBounds(335, 11, 81, 33);
		contentPane.add(btn_stop);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 120, 414, 2);
		contentPane.add(separator);
		
		JLabel lblTank = new JLabel("Tank");
		lblTank.setBounds(10, 133, 46, 14);
		contentPane.add(lblTank);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 232, 414, 2);
		contentPane.add(separator_1);
		
		JLabel lblDownload = new JLabel("Download");
		lblDownload.setBounds(10, 245, 67, 14);
		contentPane.add(lblDownload);
		
		btn_refresh = new JButton("Refresh");
		btn_refresh.setBounds(335, 129, 89, 23);
		contentPane.add(btn_refresh);
		
		JLabel lblStatut = new JLabel("Status : ");
		lblStatut.setBounds(221, 45, 46, 27);
		contentPane.add(lblStatut);
		
		label_status = new JLabel("Paused");
		label_status.setBounds(280, 42, 136, 33);
		contentPane.add(label_status);
		
		chckbxIpv = new JCheckBox("IPv6");
		chckbxIpv.setBounds(6, 35, 71, 23);
		contentPane.add(chckbxIpv);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(76, 70, 271, 2);
		contentPane.add(separator_2);
		
		JLabel lblClient = new JLabel("Client : ");
		lblClient.setBounds(10, 88, 46, 14);
		contentPane.add(lblClient);
		
		JLabel lblip_1 = new JLabel("@IP :");
		lblip_1.setBounds(76, 88, 46, 14);
		contentPane.add(lblip_1);
		
		tf_ip_client = new JTextField();
		tf_ip_client.setBounds(108, 83, 136, 20);
		contentPane.add(tf_ip_client);
		tf_ip_client.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Port :");
		lblNewLabel.setBounds(269, 86, 46, 14);
		contentPane.add(lblNewLabel);
		
		tf_port_client = new JTextField();
		tf_port_client.setBounds(305, 83, 86, 20);
		contentPane.add(tf_port_client);
		tf_port_client.setColumns(10);
		
		setVisible(true);
		
		
		// Listener du field PORT (verifie que l'entre est correcte)
		tf_port.getDocument().addDocumentListener(new DocumentListener() {
		  public void changedUpdate(DocumentEvent e) {
		    //warn();
		  }
		  public void removeUpdate(DocumentEvent e) {
		    warn();
		  }
		  public void insertUpdate(DocumentEvent e) {
		    warn();
		  }

		  public void warn() {
			  if ((!tf_port.getText().equals(""))&&(tf_port.getText().matches("[0-9]+"))) {
			     if ((Integer.parseInt(tf_port.getText())<=0) | (Integer.parseInt(tf_port.getText())>63000)){
			    	 tf_port.setBackground(Color.RED);
			    	 portIsOk = false;
			     } else {
			    	 tf_port.setBackground(Color.WHITE);
			    	 portIsOk = true;
			     }
			  } else {
				  tf_port.setBackground(Color.RED);
				  portIsOk = false;
			  }
		  }
		});
		
		//Listener du field IP (verifie que la saisie est bien de type @ip)
		tf_ip.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
			    //warn();
			  }
			  public void removeUpdate(DocumentEvent e) {
			    warn();
			  }
			  public void insertUpdate(DocumentEvent e) {
			    warn();
			  }

			  public void warn() {
				  try {
					  if ((tf_ip.getText().length()>6) && isIpV6 && (InetAddress.getByName(tf_ip.getText()) instanceof Inet6Address)) {
						  ipIsOk = true;
						  tf_ip.setBackground(Color.WHITE);
					  } else if ((tf_ip.getText().length()>6) && (!isIpV6) && (InetAddress.getByName(tf_ip.getText()) instanceof Inet4Address)) {
						  ipIsOk = true;
						  tf_ip.setBackground(Color.WHITE);
					  } else {
						  ipIsOk = false;
						  tf_ip.setBackground(Color.RED);
					  }
				  } catch (UnknownHostException e) {
					  e.printStackTrace();
					  ipIsOk = false;
					  tf_ip.setBackground(Color.RED);
				  }
			  }
		});
		
		//Listener du checkBox IPvX
		chckbxIpv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				isIpV6 = chckbxIpv.isSelected();
			}
		});
		
		
		// Listener du field PORT CLIENT (verifie que l'entre est correcte)
		tf_port_client.getDocument().addDocumentListener(new DocumentListener() {
		  public void changedUpdate(DocumentEvent e) {
		    //warn();
		  }
		  public void removeUpdate(DocumentEvent e) {
		    warn();
		  }
		  public void insertUpdate(DocumentEvent e) {
		    warn();
		  }

		  public void warn() {
			  if ((!tf_port_client.getText().equals(""))&&(tf_port_client.getText().matches("[0-9]+"))) {
			     if ((Integer.parseInt(tf_port_client.getText())<=0) | (Integer.parseInt(tf_port_client.getText())>63000)){
			    	 tf_port_client.setBackground(Color.RED);
			    	 portClientIsOk = false;
			     } else {
			    	 tf_port_client.setBackground(Color.WHITE);
			    	 portClientIsOk = true;
			     }
			  } else {
				  tf_port_client.setBackground(Color.RED);
				  portClientIsOk = false;
			  }
		  }
		});
		
		
		//Listener du field IP CLIENT (verifie que la saisie est bien de type @ip)
		tf_ip_client.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
			    //warn();
			  }
			  public void removeUpdate(DocumentEvent e) {
			    warn();
			  }
			  public void insertUpdate(DocumentEvent e) {
			    warn();
			  }

			  public void warn() {
				  try {
					  if ((tf_ip_client.getText().length()>6) && isIpV6 && (InetAddress.getByName(tf_ip_client.getText()) instanceof Inet6Address)) {
						  ipClientIsOk = true;
						  tf_ip_client.setBackground(Color.WHITE);
					  } else if ((tf_ip_client.getText().length()>6) && (!isIpV6) && (InetAddress.getByName(tf_ip_client.getText()) instanceof Inet4Address)) {
						  ipClientIsOk = true;
						  tf_ip_client.setBackground(Color.WHITE);
					  } else {
						  ipClientIsOk = false;
						  tf_ip_client.setBackground(Color.RED);
					  }
				  } catch (UnknownHostException e) {
					  e.printStackTrace();
					  ipClientIsOk = false;
					  tf_ip_client.setBackground(Color.RED);
				  }
			  }
		});
		
		
		
		
		//Listener du bouton LISTEN
		btn_listen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (portIsOk && ipIsOk && portClientIsOk && ipClientIsOk) {
					System.out.println("Config OK");
					
					
					// lancement de l'ecoute serveur
					InetAddress ia;
					InetAddress ia2;
					try {
						ia = InetAddress.getByName(tf_ip.getText());
						ia2 = InetAddress.getByName(tf_ip_client.getText());
						
						if (t!=null) {
							t.stop();
						}
						//Thread pour ne pas bloquer l'ihm pendant l'atente de reception
						t = new Thread(new Runnable() {
							
							@Override
							public void run() {
								
								s.udpServer(Integer.parseInt(tf_port.getText()), ia, Integer.parseInt(tf_port_client.getText()), ia2);
							}
						});
						t.start();
						label_status.setText("Now Listening...");
						
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					
				} else {
					System.out.print("Wrong config");
				}
			}
		});
		
		btn_stop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (t!=null) {
					t.stop();
					label_status.setText("Paused");
				}
			}
		});
		
		
		
		
	}
}
