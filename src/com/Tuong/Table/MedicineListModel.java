package com.Tuong.Table;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.Tuong.Authenication.AuthManager;
import com.Tuong.Medicine.Medicine;

public class MedicineListModel extends AbstractTableModel {
	private final String[] column = { "Name", "Hoat chat", "Nong do", "Ta duoc", "Stock", "giaKeKhai","" };
	private ArrayList<Medicine> medicines;
	private AuthManager auth_manager;
	public MedicineListModel(AuthManager auth_manager) {
		medicines = new ArrayList<Medicine>();
		this.auth_manager = auth_manager;
	}

	@Override
	public int getRowCount() {
		return medicines.size();
	}
	
	public void deleteMedicine(int row) {
		auth_manager.getMedicineManager().deleteMedicine(medicines.get(row));
		medicines.remove(row);
		fireTableDataChanged();
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
			return medicines.get(rowIndex).getTaDuoc();
		case 4:
			return medicines.get(rowIndex).getStock();
		case 5:
			return medicines.get(rowIndex).giaKeKhai();
		case 6:
			return "Delete";
		}
		return null;
	}

}