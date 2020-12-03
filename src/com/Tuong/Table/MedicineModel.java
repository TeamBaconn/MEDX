package com.Tuong.Table;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.Tuong.Medicine.MedicinePrescription;

public class MedicineModel extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	final String[] columnNames = {
			"No", 
			"Name",
       		"Dosage",
            "Delete"};
	public static final double[] size = {0.05,0.4,0.3,0.25};
	private ArrayList<MedicinePrescription> med_list = new ArrayList<MedicinePrescription>();
	
	public ArrayList<MedicinePrescription> getPrescription(){
		return med_list;
	}
	
	public void setPrescription(ArrayList<MedicinePrescription> med_list) {
		this.med_list = med_list;
	}
	
	@Override
	public boolean isCellEditable(int row, int column)
    {
      return true;
    }
	@Override
	public String getColumnName(int index) {
	    return columnNames[index];
	}
	
	@Override
	public int getRowCount() {
		return med_list.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	public void add(MedicinePrescription pre) {
		med_list.add(pre);
		fireTableDataChanged();
	}
	
	public void remove(int row) {
		med_list.remove(row);
		fireTableDataChanged();
	}
	
	public void setUnit(int i,int rowIndex, int columnIndex) {
		med_list.get(rowIndex).volume = i;
		fireTableDataChanged();
	}
	
	public void reset() {
		med_list.clear();
		fireTableDataChanged();
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		MedicinePrescription med = med_list.get(rowIndex);
		switch(columnIndex) {
		case 0:
			return rowIndex+1;
		case 1:
			return med.medicine.getName();
		case 2:
			return med.medicine.getName();
		case 3:
			return "Delete";
		}
		return null;
	}
	
}