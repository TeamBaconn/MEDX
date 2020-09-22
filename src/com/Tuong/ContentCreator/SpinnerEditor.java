package com.Tuong.ContentCreator;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;

import com.Tuong.Medicine.MedicineModel;

public class SpinnerEditor extends AbstractCellEditor implements TableCellEditor {
	private static final long serialVersionUID = 451428870952031517L;
	
	final JSpinner spinner = new JSpinner();
	private int x, y;

	public SpinnerEditor(MedicineModel model) {
		spinner.setModel(new SpinnerNumberModel(0, 0, 100, 5));
		spinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				model.setUnit((int) spinner.getValue(), x, y);
			}
		});
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		spinner.setValue(value);
		this.x = row; this.y = column;
		return spinner;
	}

	public boolean isCellEditable(EventObject evt) {
		if (evt instanceof MouseEvent) {
			return ((MouseEvent) evt).getClickCount() >= 2;
		}
		return true;
	}

	public Object getCellEditorValue() {
		return spinner.getValue();
	}
}