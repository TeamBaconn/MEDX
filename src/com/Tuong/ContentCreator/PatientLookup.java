package com.Tuong.ContentCreator;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import com.Tuong.ContentHelper.BasicPanel;
import com.Tuong.ContentHelper.CustomButton;
import com.Tuong.ContentHelper.FormCreator;
import com.Tuong.ContentHelper.RoundTextfield;
import com.Tuong.EventListener.EventListenerManager;
import com.Tuong.MedXMain.MedXMain;
import com.Tuong.Patient.Patient;
import com.Tuong.Trie.TrieResult;

public class PatientLookup extends BasicPanel{

	private JList<Patient> p_list;
	private JTextField patient_name_search;
	private DefaultListModel<Patient> model;
	public PatientLookup() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		model = new DefaultListModel<Patient>();
		p_list = new JList<Patient>();
		p_list.setCellRenderer(new PatientListRenderer());
		JPanel patient_info = new JPanel(new GridBagLayout());
		patient_info.setBackground(Color.decode("#f7f1e3"));
		FormCreator form3 = new FormCreator(patient_info, 2, MedXMain.form_size_constant, 30);
		JButton create = new CustomButton("Create");

		form3.createLabel("Patient name");
		patient_name_search = new RoundTextfield();
		form3.addComponent(patient_name_search);
		form3.addComponent(null);
		form3.addComponent(create);

		refreshList("");
		JScrollPane scrollPne = new JScrollPane(p_list);
		scrollPne.setPreferredSize(new Dimension(400, 200));
		scrollPne.setMaximumSize(new Dimension(400, 200));
		add(scrollPne);
		add(patient_info);

		p_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		p_list.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// Load new patient
				if( p_list.getSelectedValue() == null) return;
				EventListenerManager.current.activateEvent("PatientSelectEvent", p_list.getSelectedValue());
			}
		});

		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(patient_name_search.getText().length() <= 0) return;
				EventListenerManager.current.activateEvent("PatientCreateEvent", patient_name_search.getText());
				refreshList(patient_name_search.getText());
			}
		});

		patient_name_search.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				refreshList(patient_name_search.getText());
			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});
	}
	
	public void refreshList(String text) {
		model.clear();
		EventListenerManager.current.activateEvent("PatientQueryRequest", patient_name_search.getText());
	}
	
	@Override
	public void PatientLoadEvent(Patient patient) {
		model.addElement(patient);
		p_list.setModel(model);
	}

	public String getSelectedName() {
		return patient_name_search.getName();
	}
}

class PatientListRenderer extends JLabel implements ListCellRenderer<Patient> {
	private ImageIcon vertify;
	private ImageIcon unvertify;

	public PatientListRenderer() {
		vertify = new ImageIcon(getScaledImage(new ImageIcon("Data/vertified_icon.png").getImage(),25,25));
		unvertify = new ImageIcon(getScaledImage(new ImageIcon("Data/un_vertified_icon.png").getImage(),25,25));
		setOpaque(true);
	}

	private Image getScaledImage(Image srcImg, int w, int h) {
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();

		return resizedImg;
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Patient> list, Patient value, int index,
			boolean isSelected, boolean cellHasFocus) {
		setText(value.id+" - "+value.toString());
		setIcon(value.isVertified() ? vertify : unvertify);
		setBackground(!isSelected ? Color.WHITE : Color.LIGHT_GRAY);
		return this;
	}

}
