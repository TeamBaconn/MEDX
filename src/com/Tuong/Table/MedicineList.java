package com.Tuong.Table;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.json.simple.JSONObject;

import com.Tuong.Authenication.AuthManager;
import com.Tuong.ContentHelper.BasicPanel;
import com.Tuong.EventListener.EventListenerManager;
import com.Tuong.MedXMain.JSONHelper;
import com.Tuong.Medicine.Medicine;
import com.Tuong.Table.ButtonEditor;
import com.Tuong.Table.ButtonRenderer;
import com.Tuong.Table.MedicineListModel;
import com.Tuong.Table.SpinnerEditor;
import com.Tuong.Trie.TrieResult;

public class MedicineList extends BasicPanel {
	private static final long serialVersionUID = 7598135574795319656L;

	private JTable list;

	private JTextField medName;
	
	private MedicineListModel model;

	public MedicineList() {
		setBorder(new CompoundBorder(new TitledBorder("Medication Information"), new EmptyBorder(0, 0, 0, 0)));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		model = new MedicineListModel();
		list = new JTable(model);
		
		list.getColumnModel().getColumn(3).setCellEditor(new SpinnerEditor(model, "setUnit"));
		list.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
		list.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(model, "deleteMedicine"));
		list.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
		list.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(model, "addMedicine"));
		
		list.setRowHeight(40);
		list.setPreferredScrollableViewportSize(new Dimension(1000, 70));
		JScrollPane scrollPne = new JScrollPane(list);
		scrollPne.setPreferredSize(new Dimension(1000, 400));
		scrollPne.setMaximumSize(new Dimension(1000, 400));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//Resize column
		for(int i = 0; i < MedicineListModel.size.length; i++) list.getColumnModel().getColumn(i).setPreferredWidth((int) (scrollPne.getPreferredSize().getWidth()*MedicineListModel.size[i]));
		
		medName = new JTextField();
		medName.addKeyListener(new KeyAdapter() {
			@Override 
			public void keyReleased (KeyEvent e) {
				refreshSearch(medName.getText());
			}
		});
		refreshSearch("");
		add(medName);
		add(scrollPne);
	}

	private void refreshSearch(String query) {
		model.clear();
		EventListenerManager.current.activateEvent("MedicineQueryRequest", query);
		list.clearSelection();
	}
	
	@Override
	public void MedicineLoadEvent(Medicine med) {
		model.addElement(med);
	}
}
