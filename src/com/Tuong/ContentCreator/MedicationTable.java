package com.Tuong.ContentCreator;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.Tuong.Table.ButtonEditor;
import com.Tuong.Table.ButtonRenderer;
import com.Tuong.Table.MedicineModel;

public class MedicationTable extends JPanel{
	private static final long serialVersionUID = -1308483990338641187L;
	
	private MedicineModel model;
	
	public MedicationTable() {
		setLayout(new BoxLayout(this, 0));

		model = new MedicineModel();
        JTable table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.setRowHeight(100);
 
		table.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
		table.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(model, "remove"));
        
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
 
        //Resize
        for(int i = 0; i < MedicineModel.size.length; i++) table.getColumnModel().getColumn(i).setPreferredWidth((int) (scrollPane.getPreferredSize().getWidth()*MedicineModel.size[i]));
        
        
        //Add the scroll pane to this panel.
        setPreferredSize(new Dimension(400,500));
        add(scrollPane);
	}
	
	public MedicineModel getModel() {
		return this.model;
	}
}

