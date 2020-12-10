package com.Tuong.Table;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.Tuong.ContentHelper.BasicPanel;
import com.Tuong.ContentHelper.FormCreator;
import com.Tuong.ContentHelper.RoundTextfield;
import com.Tuong.EventListener.EventListenerManager;
import com.Tuong.Medicine.Medicine;

public class MedicineList extends BasicPanel {
	private static final long serialVersionUID = 7598135574795319656L;

	private JTable list;
	
	private MedicineListModel model;

	public MedicineList() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new EmptyBorder(new Insets(20, 10, 10, 10)));
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
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//Resize column
		for(int i = 0; i < MedicineListModel.size.length; i++) list.getColumnModel().getColumn(i).setPreferredWidth((int) (scrollPne.getPreferredSize().getWidth()*MedicineListModel.size[i]));
		
		JPanel search = new JPanel(new GridBagLayout());
		search.setMaximumSize(new Dimension(1000,40));
		search.setOpaque(false);
		int[] n = {100,200,100,200};
		FormCreator form = new FormCreator(search, 4, n, 30);
		form.createLabel("Medicine Name").setHorizontalAlignment(SwingConstants.RIGHT);
		JTextField medName = form.createTextField("");
		form.createLabel("Promoter").setHorizontalAlignment(SwingConstants.RIGHT);
		JTextField promoterName = form.createTextField("");
		medName.addKeyListener(new KeyAdapter() {
			@Override 
			public void keyReleased (KeyEvent e) {
				refreshSearch(medName.getText(),promoterName.getText());
			}
		});
		promoterName.addKeyListener(new KeyAdapter() {
			@Override 
			public void keyReleased (KeyEvent e) {
				refreshSearch(medName.getText(),promoterName.getText());
			}
		});
		refreshSearch("","");
		add(search);
		add(Box.createVerticalStrut(10));
		add(scrollPne);
	}

	private void refreshSearch(String query, String promoter) {
		model.clear();
		EventListenerManager.current.activateEvent("MedicineQueryRequest", query,promoter);
		list.clearSelection();
	}
	
	@Override
	public void MedicineLoadEvent(Medicine med) {
		model.addElement(med);
	}
}
