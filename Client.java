import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import predicates.FindLocation;
import predicates.GetSongs;
import common.Artist;
import common.Classifier;
import common.InterfacePredicate;

/**
 * @author Vaikunth Sridharan
 * 
 */

@SuppressWarnings("unchecked")
public class Client {
	static JFrame frame;
	static JPanel panel1, panel2, panel3;
	static JLabel label2, label3, label1, label4, label5;
	static JButton button1, button2;
	static JTextField input;
	static JComboBox combo;
	static JTextArea textArea;
	String returnString = null;
	static JScrollPane scroll;
	Classifier serv;

	public Client() throws RemoteException, NotBoundException {
		frame = new JFrame("CLIENT \u00a9 Vaikunth Sridharan");
		panel1 = new JPanel();
		label1 = new JLabel("Introduction to RMI");
		label1.setHorizontalAlignment(JLabel.CENTER);
		label1.setVerticalAlignment(JLabel.CENTER);
		label2 = new JLabel("Enter Artist Name");
		label3 = new JLabel("Results");
		label3.setHorizontalAlignment(JLabel.CENTER);
		label3.setVerticalAlignment(JLabel.CENTER);
		label4 = new JLabel("Select a predicate option");
		input = new JTextField();
		button1 = new JButton("GO");
		button2 = new JButton("CLEAR");
		panel2 = new JPanel();
		panel3 = new JPanel();
		combo = new JComboBox();
		textArea = new JTextArea();
		serv = connectToRMIServer();
		scroll = new JScrollPane(textArea);
	}

	public void guiComponents() {
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel1.setLayout(new GridLayout(5, 1));
		panel1.add(label1);
		panel1.add(label2);
		panel1.add(input);
		panel1.add(label4);
		combo.addItem("FindLocation");
		combo.addItem("GetSongs");
		panel1.add(combo);
		panel3.setLayout(new GridLayout(1, 2));
		panel3.add(button1);
		panel3.add(button2);
		textArea.setEditable(false);
		panel2.add(panel3);
		frame.getContentPane().add(panel1, BorderLayout.NORTH);
		frame.getContentPane().add(label3, BorderLayout.CENTER);
		frame.getContentPane().add(scroll, BorderLayout.CENTER);
		frame.getContentPane().add(panel3, BorderLayout.SOUTH);
		frame.setSize(400, 400);
		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

				System.exit(0);
			}
		});

		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InterfacePredicate p = null;

				if (!input.getText().isEmpty())
					p = predicateInitialize(input.getText().toString(), combo
							.getSelectedItem().toString());
				else if (input.getText().isEmpty())
					JOptionPane.showMessageDialog(null,
							"Please do not leave the artist name blank");
				if (p != null) {
					try {
						displayResults(serv, p);
						JOptionPane
								.showMessageDialog(
										null,
										"Your results are now displayed, more than one song can be present please scroll to check");
					} catch (Exception e1) {
						JOptionPane
								.showMessageDialog(
										null,
										"Server side error, make sure you have Table2.in file near server if yes then it is some other exception");
					}
				}

			}
		});
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				input.setText("");
				textArea.setText("");
				combo.setSelectedIndex(0);
			}
		});

	}

	public static InterfacePredicate predicateInitialize(String artist_name,
			String predCond) {
		InterfacePredicate pred = null;
		if (predCond.equalsIgnoreCase("GetSongs"))
			pred = new GetSongs(artist_name);
		else
			pred = new FindLocation(artist_name);
		if (pred != null)
			return pred;
		else
			return null;

	}

	public static void displayResults(Classifier serv, InterfacePredicate pred)
			throws Exception {

		ArrayList<Artist> s = serv.classify(pred);
		for (Artist a : s) {

			if (pred.getClass().toString().contains("FindLocation"))
				textArea.setText(a.artist_location);
			else {
				String songResult = "";
				if (a.artist_songs[4].split("<I>") != null)
					for (String song : a.artist_songs[4].split("<I>")) {
						songResult += song + "\n";
					}
				textArea.setText(songResult.toString());
			}
		}

	}

	public static Classifier connectToRMIServer() throws RemoteException,
			NotBoundException {
		Registry registry = LocateRegistry.getRegistry("localhost", 2099);
		Classifier service = (Classifier) registry
				.lookup("sample/HelloService");

		return service;
	}

	@SuppressWarnings("rawtypes")
	public static void main(String... args) throws Exception {
		Client c = new Client();
		c.guiComponents();

	}
}
