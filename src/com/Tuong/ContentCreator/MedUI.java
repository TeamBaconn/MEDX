package com.Tuong.ContentCreator;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.Tuong.Authenication.AuthManager;
import com.Tuong.ContentHelper.BasicUI;
import com.Tuong.ContentHelper.ButtonAction;
import com.Tuong.ContentHelper.FormCreator;
import com.Tuong.DateUtils.Date;
import com.Tuong.DateUtils.DatePicker;
import com.Tuong.Graph.Graph;
import com.Tuong.Graph.GraphCreatorUI;
import com.Tuong.Graph.GraphType;
import com.Tuong.Graph.GraphValue;
import com.Tuong.MedXMain.JSONHelper;
import com.Tuong.MedXMain.MedXMain;
import com.Tuong.Medicine.MedicineCategory;
import com.Tuong.Medicine.MedicinePrescription;
import com.Tuong.Patient.Patient;

public class MedUI extends BasicUI{
	
	private MedicineList listMed;
	public PatientLookup patient_lookup;
	public PatientInfo patientInfo;
	
	public MedUI(AuthManager auth_manager) {
		super("Medicine Manager", Toolkit.getDefaultToolkit().getScreenSize(),true,auth_manager);
	}
	public void addMedicine(MedicinePrescription medicine) {
		medTable.model.add(medicine);
	}

	JTabbedPane patientTab;
	@Override
	public void setupUI() {
		addCloseAction(new ButtonAction() {
			@Override
			public void click() {
				auth_manager.getMedicineManager().saveData();
				patientInfo.savePatient();
				auth_manager.setMedUI(null);
			}
		});
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		/*
		 * Med information
		 * Name - Name of the medicine
		 * Category - Combo box
		 * Date - EXP of the med
		 * Unit - Unit of the medicine
		 */
		JPanel medication = new JPanel();
		medication.setLayout(new BoxLayout(medication, BoxLayout.LINE_AXIS));
		
		listMed = new MedicineList(auth_manager);
		
		JPanel listCategory = new JPanel();
		listCategory.setBorder(new CompoundBorder(new TitledBorder("Categories"), new EmptyBorder(12, 0, 0, 0)));
		listCategory.setLayout(new BoxLayout(listCategory, BoxLayout.Y_AXIS));
		listCategory.add(Box.createVerticalGlue());
		JList<MedicineCategory> listCate = new JList<MedicineCategory>();
		updateCategory(listCate);
		listCate.setMaximumSize(new Dimension(400, 200));
		listCate.setPreferredSize(new Dimension(400, 200));
		JPanel cateInfo = new JPanel();
		cateInfo.setLayout(new GridBagLayout());
		FormCreator formCate = new FormCreator(cateInfo, 2, MedXMain.form_size_constant, 30);
		formCate.createLabel("Category");
		JTextField cateName = formCate.createTextField("");
		JButton addCate = new JButton("Create");
		JButton removeCate = new JButton("Remove");
		formCate.createLabel("Description");
		JTextField hint = formCate.createTextField("");
		formCate.addComponent(addCate);
		formCate.addComponent(removeCate);
		removeCate.setVisible(false);
		removeCate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(showConfirmDialog("MedX", "Do you want to delete "+cateName.getText()) == 1) return; 
				showDialog("MedX", "Delete category ("+cateName.getText()+") successful", 1);
				auth_manager.getMedicineManager().removeMedicineCategory(cateName.getText());
				cateName.setText("");
				hint.setText("");
				updateCategory(listCate);
				listMed.refresh();
			}
		});
		addCate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				auth_manager.getMedicineManager().addMedicineCategory(cateName.getText(), hint.getText());
				updateCategory(listCate);
				listMed.refresh();
				showDialog("MedX", "Create new category ("+cateName.getText()+") successful", 1);
			}
		});
		listCate.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				//Check selection in Jlist
				if(!e.getValueIsAdjusting()) return;
				addCate.setText("Fix");
				removeCate.setVisible(true);
				cateName.setText(listCate.getSelectedValue().getName());
				hint.setText(listCate.getSelectedValue().getHint());
			}
		});
		cateName.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				addCate.setText("Create");
				listCate.clearSelection();
				removeCate.setVisible(false);
			}
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			@Override
			public void keyPressed(KeyEvent e) {
				
			}
		});
		
		listCategory.add(listCate);
		listCategory.add(Box.createRigidArea(new Dimension(0,20)));
		listCategory.add(cateInfo);
		
		JPanel patientManager = new JPanel();
		patientManager.setLayout(new BoxLayout(patientManager, BoxLayout.LINE_AXIS));
		
		patientInfo = new PatientInfo(auth_manager);
		
		
		
		JPanel prescription = getPrescriptionUI();
		
		patient_lookup = new PatientLookup(auth_manager);
		patientTab = new JTabbedPane();
		patientTab.addTab("Patient Lookup", patient_lookup);
		patientTab.addTab("Patient Info", patientInfo);
		//patientTab.addTab("Prescription", prescription);
		
		medication.add(listMed);
		medication.add(listCategory);
		patientManager.add(patientTab);
		patientManager.add(prescription);
		patientTab.setEnabledAt(1, false);
		//patientTab.setEnabledAt(2, false);
		
		add(patientManager);
		add(medication);
	}
	public void updatePatient(int id) {
		auth_manager.getMedUI().patientTab.setEnabledAt(1, true);
		if(patientInfo.getPatient() != null) patientInfo.savePatient();
		patientInfo.loadPatient(id);
		patientTab.setSelectedIndex(1);
		
		prescription_patient_name.setText(patientInfo.getPatient().getName());
	}
	public void removePatient() {
		patient_lookup.refreshList("");
		auth_manager.getMedUI().patientTab.setEnabledAt(1, false);
		patientTab.setSelectedIndex(0);
		
		prescription_patient_name.setText("");
	}
	private DatePicker start_date,end_date;
	private JTextField note;
	private JList<MedicinePrescription> prescriptions;
	private JTextField prescription_patient_name;
	private MedicationTable medTable;
	public JPanel getPrescriptionUI() {
		JPanel pre = new JPanel();
		pre.setLayout(new BoxLayout(pre, BoxLayout.Y_AXIS));
		medTable = new MedicationTable();
		pre.add(medTable);
		prescriptions = new JList<MedicinePrescription>();
		prescriptions.setPreferredSize(new Dimension(400,200));
		prescriptions.setMaximumSize(new Dimension(400,200));
		//pre.add(prescriptions);
		
		JPanel f = new JPanel(new GridBagLayout());
		int[] n = {140,240};
		FormCreator form = new FormCreator(f, 2, n, 30);
		form.createLabel("Patient name: ");
		prescription_patient_name = new JTextField();
		prescription_patient_name.setEditable(false);
		form.addComponent(prescription_patient_name);
		form.createLabel("Note");
		note = form.createTextField("");
	
		form.createLabel("Start date:");
		start_date = new DatePicker(new Date(), true);
		form.addComponent(start_date);
		form.createLabel("End date:");
		end_date = new DatePicker(new Date(), true);
		form.addComponent(end_date);
		
		pre.add(f);
		
		JButton print = new JButton("Print");
		print.setMaximumSize(new Dimension(400,40));
		print.setAlignmentX(CENTER_ALIGNMENT);
		//pre.add(print);
		return pre;
	}
	
	private void updateCategory(JList<MedicineCategory> list) {
		ArrayList<MedicineCategory> n = auth_manager.getMedicineManager().getCategories();
		DefaultListModel model = new DefaultListModel();
		for(int i = 0; i < n.size(); i++) {
			model.addElement(n.get(i));
		}
		list.clearSelection();
		list.setModel(model);
	}
}
