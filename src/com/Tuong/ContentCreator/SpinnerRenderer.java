package com.Tuong.ContentCreator;

import java.awt.Component;

import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellRenderer;

import com.Tuong.Medicine.MedicineModel;

public class SpinnerRenderer implements TableCellRenderer
{
    private JSpinner spinner;
    private int x,y;
    public SpinnerRenderer(MedicineModel model)
    {
        spinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 5));
        spinner.setBorder(null);
        spinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				model.setValueAt(spinner.getValue(), x, y);
			}
		});
    }
    
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		System.out.println(x+" "+y);
		this.x = row; this.y = column;
		return spinner;
	}
}
