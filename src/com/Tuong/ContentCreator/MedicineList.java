package com.Tuong.ContentCreator;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.simple.JSONObject;

import com.Tuong.Authenication.AuthManager;
import com.Tuong.ContentHelper.FormCreator;
import com.Tuong.DateUtils.Date;
import com.Tuong.DateUtils.DatePicker;
import com.Tuong.MedXMain.JSONHelper;
import com.Tuong.MedXMain.MedXMain;
import com.Tuong.Medicine.Medicine;
import com.Tuong.Patient.Patient;
import com.Tuong.Trie.TrieResult;

public class MedicineList extends JPanel {
	private static final long serialVersionUID = 7598135574795319656L;

	private JList<Medicine> list;

	private JTextField medName;
	private DatePicker dPicker;
	private JSpinner unit;
	private JButton addPat;

	private AuthManager auth_manager;

	public MedicineList(AuthManager auth_manager) {
		this.auth_manager = auth_manager;

		setBorder(new CompoundBorder(new TitledBorder("Medication Information"), new EmptyBorder(12, 0, 0, 0)));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(Box.createVerticalGlue());
		list = new JList<Medicine>();
		JScrollPane scrollPne = new JScrollPane(list);
		scrollPne.setPreferredSize(new Dimension(400, 200));
		scrollPne.setMaximumSize(new Dimension(400, 200));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(scrollPne);
		add(Box.createRigidArea(new Dimension(0, 20)));
		JPanel medInfo = new JPanel();
		medInfo.setLayout(new GridBagLayout());
		add(medInfo);

		FormCreator form = new FormCreator(medInfo, 2, MedXMain.form_size_constant, 30);
		dPicker = new DatePicker(new Date(), false);
		form.createLabel("Name");
		medName = form.createTextField("");
		searchAction(medName, dPicker);
		form.createLabel("Category");
		form.addComponent(null);
		form.createLabel("EXP Date");
		form.addComponent(dPicker);
		form.createLabel("Units");
		unit = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
		form.addComponent(unit);
		JButton addMed = new JButton("Create");
		JButton delMed = new JButton("Delete");
		addPat = new JButton();
		addPat.setVisible(false);

		form.addComponent(null);
		form.addComponent(addMed);
		form.addComponent(null);
		form.addComponent(delMed);
		form.addComponent(null);
		form.addComponent(addPat);
		unit.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (list.getSelectedValue() == null)
					return;
				list.getSelectedValue().setStock((int) unit.getValue());
			}
		});
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// Check selection in Jlist
				openAddMedButton();
				if (!e.getValueIsAdjusting())
					return;
				medName.setText(list.getSelectedValue().getName());
				unit.setValue(list.getSelectedValue().getStock());
			}
		});
		addMed.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				refresh();
				auth_manager.getMedUI().showDialog("MedX", "Add medicine successfully",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		addPat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openMedAdd();
			}
		});

		refresh();
	}

	public void openAddMedButton() {
		if (list.getSelectedValue() == null) {
			addPat.setVisible(false);
			return;
		}
		addPat.setText("Add " + list.getSelectedValue().getName() + " to ");
		addPat.setVisible(true);
	}

	private void searchAction(JTextField medName, DatePicker date) {
		medName.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				ArrayList<TrieResult> res = auth_manager.getMedicineManager().med_trie.getRecommend(medName.getText(),
						true);
				DefaultListModel<Medicine> model = new DefaultListModel<Medicine>();
				for (int i = 0; i < res.size(); i++) {
					//System.out.println((res.get(i).index + 1) + " " + res.get(i).score);
					JSONObject object = (JSONObject) JSONHelper.readFile(auth_manager.getMedicineManager().med_path_save+(res.get(i).index + 1)+".json");
					model.addElement(new Medicine((String) object.get("tenThuoc"), 
							"Unit", 
							((Long) object.get("soLuong")).intValue()));
				}
				list.clearSelection();
				list.setModel(model);
				dPicker.setDateSetter(null, "");
			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});
	}

	public void refresh() {
		dPicker.setDateSetter(null, "");
		dPicker.setDate(new Date());
		medName.setText("");
	}
	private void openMedAdd() {
		// if(auth_manager.getMedUI().getPatient() == null || list.getSelectedValue() ==
		// null) return;
		// auth_manager.getMedUI().addMedicine(new
		// MedicinePrescription(list.getSelectedValue().med));
	}
}


class MedicineRenderer extends JLabel implements ListCellRenderer<Patient> {
	private static final long serialVersionUID = 3702676130889055807L;
	
	public MedicineRenderer() {
		setOpaque(true);
	}
	@Override
	public Component getListCellRendererComponent(JList<? extends Patient> list, Patient value, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		return null;
	}
}
