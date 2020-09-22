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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
import com.Tuong.Medicine.MedicineSet;
import com.Tuong.Patient.Patient;
import com.Tuong.Patient.PatientSet;

public class MedUI extends BasicUI{
	
	private JList<MedicineSet> list;
	
	private JTextField medName;
	private JComboBox<MedicineCategory> category;
	private DatePicker dPicker;
	private JSpinner unit;
	private JButton addPat;
	
	private JTextField patient_name;
	
	private PatientLookup patient_lookup;
	
	public MedUI(AuthManager auth_manager) {
		super("Medicine Manager", Toolkit.getDefaultToolkit().getScreenSize(),true,auth_manager);
	}
	
	public void setPatient(PatientSet p) {
		this.set = p;
		openAddMedButton();
	}
	public void addMedicine(MedicinePrescription medicine) {
		/*DefaultListModel<MedicinePrescription> model = new DefaultListModel<MedicinePrescription>();
		for(int i = 0; i < prescriptions.getModel().getSize(); i++) model.addElement(prescriptions.getModel().getElementAt(i));
		model.addElement(medicine);
		prescriptions.clearSelection();
		prescriptions.setModel(model);*/
		medTable.model.add(medicine);
	}
	private void openAddMedButton() {
		if(list.getSelectedValue() == null || set == null) {
			addPat.setVisible(false);
			return;
		}
		addPat.setText("Add "+list.getSelectedValue().med.getName()+" to "+set.name);
		addPat.setVisible(true);
	}

	JTabbedPane patientTab;
	@Override
	public void setupUI() {
		addCloseAction(new ButtonAction() {
			@Override
			public void click() {
				auth_manager.getMedicineManager().saveData();
				savePatient();
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
		JPanel listMed = new JPanel();
		listMed.setBorder(new CompoundBorder(new TitledBorder("Medication Information"), new EmptyBorder(12, 0, 0, 0)));
		listMed.setLayout(new BoxLayout(listMed, BoxLayout.Y_AXIS));
		listMed.add(Box.createVerticalGlue());
		list = new JList<MedicineSet>();
		list.setMaximumSize(new Dimension(400, 200));
		list.setPreferredSize(new Dimension(400, 200));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listMed.add(list);
		listMed.add(Box.createRigidArea(new Dimension(0,20)));
		JPanel medInfo = new JPanel();
		medInfo.setLayout(new GridBagLayout());
		listMed.add(medInfo);
		
		FormCreator form = new FormCreator(medInfo, 2, MedXMain.form_size_constant, 30);
		dPicker = new DatePicker(new Date(), false);
		form.createLabel("Name");
		medName = form.createTextField("");
		searchAction(medName,dPicker);
		form.createLabel("Category");
		category = new JComboBox<MedicineCategory>();
		form.addComponent(category);
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
		
		category.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(category.getSelectedIndex() == category.getModel().getSize()-1) {
					System.out.println("Hello");
					category.setSelectedIndex(category.getSelectedIndex());
					return;
				}
				if(list.getSelectedValue() == null) return;
				list.getSelectedValue().med.setCategory((MedicineCategory)category.getSelectedItem());
			}
		});
		unit.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(list.getSelectedValue() == null) return;
				list.getSelectedValue().med.setStock((int)unit.getValue());
			}
		});
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				//Check selection in Jlist
				openAddMedButton();
				if(!e.getValueIsAdjusting()) return;
				medName.setText(list.getSelectedValue().med.getName());
				dPicker.setDateSetter(list.getSelectedValue().med,"expDate");
				dPicker.setDate(list.getSelectedValue().med.getEXP());
				unit.setValue(list.getSelectedValue().med.getStock());
				category.setSelectedItem(list.getSelectedValue().med.getCategory());
			}
		});
		addMed.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				auth_manager.getMedicineManager().addMedicine(medName.getText(),"Unit name", (int)unit.getValue(), dPicker.getDate(), (MedicineCategory)category.getSelectedItem());
				refresh();
				showDialog("MedX", "Add medicine successfully", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		refresh();
		
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
				refresh();
			}
		});
		addCate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				auth_manager.getMedicineManager().addMedicineCategory(cateName.getText(), hint.getText());
				updateCategory(listCate);
				refresh();
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
		addPat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openMedAdd();
			}
		});
		
		listCategory.add(listCate);
		listCategory.add(Box.createRigidArea(new Dimension(0,20)));
		listCategory.add(cateInfo);
		
		JPanel patientManager = new JPanel();
		patientManager.setLayout(new BoxLayout(patientManager, BoxLayout.LINE_AXIS));
		
		JPanel patientInfoPanel = setupPatientInfo();
		
		
		
		JPanel prescription = getPrescriptionUI();
		
		patient_lookup = new PatientLookup(auth_manager);
		patientTab = new JTabbedPane();
		patientTab.addTab("Patient Lookup", patient_lookup);
		patientTab.addTab("Patient Info", patientInfoPanel);
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
	public void updatePatient(PatientSet patientSet) {
		auth_manager.getMedUI().patientTab.setEnabledAt(1, true);
		//patientTab.setEnabledAt(2, true);
		if(patient != null) savePatient();
		loadPatient(patientSet);
		patientTab.setSelectedIndex(1);
		
		prescription_patient_name.setText(patient.getName());
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
	
	private DatePicker DOB;
	private JTextField diagnosis;
	private PatientSet set;
	private JComboBox<GraphType> graphList;
	private Graph graph;
	private Patient patient;
	
	public void loadPatient(PatientSet set) {
		if(set == null) return;
		if(auth_manager.getMedUI() != null) auth_manager.getMedUI().setPatient(set);
		JSONObject object = (JSONObject) JSONHelper.readFile(set.path);
		this.set=set;
		this.patient = new Patient(set.name, (String) object.get("Diagnosis"), 
				Date.parse((String) object.get("DOB")));
		graphList.setModel(new DefaultComboBoxModel<GraphType>());
		JSONArray graphs = (JSONArray)object.get("Graphs");
		if(graphs != null) for(int i = 0; i < graphs.size(); i++) {
			JSONObject graph = (JSONObject) graphs.get(i);
			GraphType g = new GraphType((String)graph.get("Name"), (String)graph.get("Unit"));
			String[] v = ((String)graph.get("Value")).split(",");
			for(int j = 0; j < v.length; j++) if(v[j].length()>1){
				g.value.add(new GraphValue(Date.parse(v[j].split("/")[1]),Double.valueOf(v[j].split("/")[0])));
			}
			graphList.addItem(g);
		}
		graph.setGraph((GraphType) graphList.getSelectedItem());
		System.out.println("Open patient record " +patient.getName());
		reloadUI();
	}
	
	public void savePatient() {
		if(patient == null) return;
		System.out.println("Save patient "+patient.getName());
		JSONObject obj = new JSONObject();
		obj.put("DOB", DOB.getDate().toString());
		obj.put("Diagnosis", diagnosis.getText());
		JSONArray graph = new JSONArray();
		for(int i = 0; i < graphList.getModel().getSize(); i++) {
			GraphType g = graphList.getModel().getElementAt(i);
			JSONObject d = new JSONObject();
			d.put("Name",g.name);
			d.put("Unit",g.unit);
			String s = "";
			for(GraphValue v : g.value) s+=v.value+"/"+v.date.toString()+",";
			d.put("Value",s);
			graph.add(d);
		}
		obj.put("Graphs",graph);
		JSONHelper.writeFile(set.path, obj.toJSONString());
	
	}
	public boolean createNewGraph(String graphName, String graphUnit) {
		for(int i = 0; i < graphList.getModel().getSize(); i++) if(graphList.getItemAt(i).name.equals(graphName)) return false;
		graphList.addItem(new GraphType(graphName, graphUnit));
		return true;
	}
	private void reloadUI() {
		patient_name.setText(patient.getName());
		diagnosis.setText(patient.getDiagnosis());
		DOB.setDate(patient.getDOB());
	}
	
	public JPanel setupPatientInfo() {
		JPanel patientInfo = new JPanel(new GridBagLayout());
		int[] n = {100,500};
		FormCreator patientForm = new FormCreator(patientInfo, 2, n, 30);
		patientForm.createLabel("Name");
		patient_name = patientForm.createTextField("");
		patientForm.createLabel("DOB");
		DOB = new DatePicker(new Date(), false);
		patientForm.addComponent(DOB);
		
		patientForm.createLabel("Diagnosis");
		diagnosis = new JTextField("");
		patientForm.addComponent(diagnosis);
		
		patientForm.createLabel("Graph");
		graphList = new JComboBox<GraphType>();
		patientForm.addComponent(graphList);
		patientForm.addComponent(null);
		JButton newGraph = new JButton("Create new graph");
		patientForm.addComponent(newGraph);
		patientForm.setSize(n);
		graph = new Graph((GraphType)graphList.getSelectedItem());
		JPanel bGraph = new JPanel();
		bGraph.setLayout(new BoxLayout(bGraph, BoxLayout.Y_AXIS));
		JPanel adjust = new JPanel(new GridLayout(2,2));
		JButton up = new JButton("Up");
		JButton down = new JButton("Down");
		adjust.add(up);
		adjust.add(down);
		JTextField value = new JTextField();
		JButton insert = new JButton(">>");
		DatePicker dP = new DatePicker(new Date(), false);
		dP.setMaximumSize(new Dimension(100,30));
		adjust.add(value);
		adjust.add(insert);
		
		bGraph.add(adjust);
		bGraph.add(dP);
		patientForm.addComponent(bGraph);
		bGraph.setPreferredSize(new Dimension(n[0],150));
		bGraph.setMinimumSize(new Dimension(n[0],150));

		patientForm.addComponent(graph);
		graph.setPreferredSize(new Dimension(n[1],150));
		graph.setMinimumSize(new Dimension(n[1],150));
		
		insert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graph.addValue(dP.getDate(),Double.parseDouble(value.getText()));
			}
		});
		newGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new GraphCreatorUI(auth_manager);
			}
		});
		graphList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graph.setGraph((GraphType) graphList.getSelectedItem());
			}
		});
		return patientInfo;
	}
	
	private void openMedAdd() {
		if(set == null || list.getSelectedValue() == null) return;
		new MedAddUI(auth_manager, set, this, list.getSelectedValue().med);
	}
	
	public PatientSet getPatient() {
		return this.set;
	}
	
	public void refresh() {
		dPicker.setDateSetter(null,"");
		dPicker.setDate(new Date());
		medName.setText("");
		DefaultComboBoxModel<MedicineCategory> cat = new DefaultComboBoxModel<MedicineCategory>();
		auth_manager.getMedicineManager().getCategories().forEach(a -> cat.addElement(a));
		cat.addElement(new MedicineCategory("Add new category...", ""));
		category.setModel(cat);
		category.setSelectedIndex(0);
		updateMedicine("");
	}
	
	private void searchAction(JTextField medName, DatePicker date) {
		updateMedicine(medName.getText());
		medName.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				updateMedicine(medName.getText());
			}			
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			@Override
			public void keyPressed(KeyEvent e) {
				
			}
		});
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

	private void updateMedicine(String medName) {
		ArrayList<MedicineSet> n = auth_manager.getMedicineManager().getMed(medName);
		DefaultListModel model = new DefaultListModel();
		for(int i = 0; i < n.size(); i++) {
			if(medName.length() != 0 && n.get(0).w - n.get(i).w > 10) break;
			model.addElement(n.get(i));
		}
		list.clearSelection();
		list.setModel(model);
		dPicker.setDateSetter(null, "");
	}
}
