package com.Tuong.Medicine;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class MedicineModel extends AbstractTableModel{
	private static final long serialVersionUID = -6427474011442687982L;
	final String[] columnNames = {
			"No", 
			"ID",
       		"Name",
            "Category",
            "Unit",
            "Volume",
            "Price"};
	private ArrayList<MedicinePrescription> med_list = new ArrayList<MedicinePrescription>();
	
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
	public void setUnit(int i,int rowIndex, int columnIndex) {
		med_list.get(rowIndex).volume = i;
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
			return med.medicine.getCategory();
		case 4:
			return med.volume;
		case 5:
			return med.volume;
		case 6:
			return "200.000d";
		}
		return null;
	}
	
}