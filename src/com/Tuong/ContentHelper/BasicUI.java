package com.Tuong.ContentHelper;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.Tuong.EventListener.EventListener;
import com.Tuong.EventListener.EventListenerManager;

public class BasicUI extends JFrame implements EventListener{
	private static final long serialVersionUID = -8323627314065426705L;
	
	public BasicUI(String name, Dimension d, boolean exit_on_close) {
		// Setting up the UI
		super(name);
		EventListenerManager.current.activateEvent("UIOpenEvent", name);
		Register();
		setSize(d);
		setupUI();
		setIconImage(new ImageIcon("Data/logo_size_invert.png").getImage());
		if(exit_on_close) setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	protected void close() {
		setVisible(false);
		UnRegister();
	}
	
	protected void setupUI() {
		// Blank function for overriding with custom UI setup in extends classes
	}
	
	protected void addCloseAction(ButtonAction action) {
		addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
				
			}
			@Override
			public void windowIconified(WindowEvent e) {
				
			}
			@Override
			public void windowDeiconified(WindowEvent e) {
				
			}
			@Override
			public void windowDeactivated(WindowEvent e) {
				
			}
			@Override
			public void windowClosing(WindowEvent e) {
				action.click();
			}
			@Override
			public void windowClosed(WindowEvent e) {
				
			}
			@Override
			public void windowActivated(WindowEvent e) {
				
			}
		});
	}
	
	protected JLabel createLabel(String displayText, Container cont) {
		JLabel label = new JLabel(displayText);
		cont.add(label);
		return label;
	}
	
	protected JPasswordField createPasswordField(Container cont) {
		JPasswordField t = new RoundPasswordField();
		t.setBackground(Color.decode("#f7f1e3"));
		cont.add(t);
		return t;
	}
	
	protected JTextField createTextField(String displayText, Container cont) {
		JTextField t = new RoundTextfield();
		t.setBackground(Color.decode("#f7f1e3"));
		t.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (t.getText().length() <= 0)
					t.setText(displayText);
			}

			@Override
			public void focusGained(FocusEvent e) {
				if (t.getText().contentEquals(displayText))
					t.setText("");
			}
		});
		cont.add(t);
		return t;
	}

	protected JButton createButton(String displayText, Container comp, ButtonAction action) {
		JButton button = new CustomButton(displayText);
		button.setText(displayText);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setAlignmentY(Component.CENTER_ALIGNMENT);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				action.click();
			}
		});
		if(comp != null) comp.add(button);
		return button;
	}

	public void showDialog(String title, String content, int option) {
		JOptionPane.showMessageDialog(this, content, title, option);
	}
	
	public int showConfirmDialog(String title, String content) {
		return JOptionPane.showConfirmDialog(this, content, title, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
	}
}
