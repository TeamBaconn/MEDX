package com.Tuong.ContentCreator;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.Tuong.Medicine.MedicineModel;

public class MedicationTable extends JPanel{
	private static final long serialVersionUID = -1308483990338641187L;
	
	public MedicineModel model;
	
	public MedicationTable() {
		setLayout(new BoxLayout(this, 0));

		model = new MedicineModel();
        JTable table = new JTable(model);
        table.getColumnModel().getColumn(4).setCellEditor(new SpinnerEditor(model));
        table.getColumnModel().getColumn(5).setCellEditor(new SpinnerEditor(model));
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.setRowHeight(100);
 
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
 
        //Add the scroll pane to this panel.
        setPreferredSize(new Dimension(400,500));
        add(scrollPane);
        
        table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}

