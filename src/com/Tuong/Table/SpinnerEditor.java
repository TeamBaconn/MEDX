package com.Tuong.Table;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;

public class SpinnerEditor extends AbstractCellEditor implements TableCellEditor {
	private static final long serialVersionUID = 451428870952031517L;
	
	final JSpinner spinner = new JSpinner();
	private int x, y;

	public SpinnerEditor(Object object, String field) {
		spinner.setModel(new SpinnerNumberModel(0, 0, 100, 5));
		spinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				try {
					object.getClass().getMethod(field, int.class,int.class,int.class).invoke(object, (int) spinner.getValue(), x, y);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException e1) {
					e1.printStackTrace();
				}
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