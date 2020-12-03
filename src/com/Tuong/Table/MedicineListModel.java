package com.Tuong.Table;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.Tuong.Authenication.AuthManager;
import com.Tuong.EventListener.EventListener;
import com.Tuong.EventListener.EventListenerManager;
import com.Tuong.EventSystem.EventManager;
import com.Tuong.Medicine.Medicine;

public class MedicineListModel extends AbstractTableModel {
	private final String[] column = { "Name", "Hoat chat", "Nong do", "Stock", "","" };
	public static final double[] size = {0.2,0.4,0.1,0.1,0.1,0.1};
	private ArrayList<Medicine> medicines;
	
	public MedicineListModel() {
		medicines = new ArrayList<Medicine>();
	}

	@Override
	public int getRowCount() {
		return medicines.size();
	}
	
	public void deleteMedicine(int row) {
		EventListenerManager.current.activateEvent("MedicineDeleteEvent", medicines.get(row));
		medicines.remove(row);
		fireTableDataChanged();
	}
	
	public void addMedicine(int row) {
		EventListenerManager.current.activateEvent("MedicineAddEvent", medicines.get(row));
	}
	
	public void setUnit(int i, int rowIndex, int columnIndex) {
		medicines.get(rowIndex).stock = i;
		fireTableDataChanged();
	}

	public void addElement(Medicine medicine) {
		medicines.add(medicine);
		fireTableDataChanged();
	}

	public void clear() {
		//Save data here
		medicines.clear();
		fireTableDataChanged();
	}

	@Override
	public int getColumnCount() {
		return column.length;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return true;
	}

	@Override
	public String getColumnName(int index) {
		return column[index];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return medicines.get(rowIndex).getName();
		case 1:
			return medicines.get(rowIndex).getHoatChat();
		case 2:
			return medicines.get(rowIndex).getNongDo();
		case 3:
			return medicines.get(rowIndex).getStock();
		case 4:
			return "Add";
		case 5:
			return "Delete";
		}
		return null;
	}

}