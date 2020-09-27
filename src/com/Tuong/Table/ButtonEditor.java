package com.Tuong.Table;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
	private Object object;
	private String invoke;

	public ButtonEditor(Object object, String invoke) {
		this.object = object;
		this.invoke = invoke;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		if (!isSelected)
			return null;
		Method field;
		try {
			field = object.getClass().getMethod(invoke, int.class);
			field.invoke(object, row);
			fireEditingStopped();
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object getCellEditorValue() {
		return null;
	}
}