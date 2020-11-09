package com.Tuong.DateUtils;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

public class DateUI extends JComboBox<Object>{
	private static final long serialVersionUID = -186921978087652461L;
	
	public DateUI() {
		super();
		setUI(new BasicComboBoxUI() {
			@Override
		    protected ComboPopup createPopup() {
		        BasicComboPopup basicComboPopup = new BasicComboPopup(comboBox);
		        basicComboPopup.setLayout(new BoxLayout(basicComboPopup,BoxLayout.Y_AXIS));
		        JPanel dateChooser = new JPanel(new FlowLayout());
		        JButton left = new JButton("<<");
		        JLabel label = new JLabel("");
		        JButton right = new JButton(">>");
		        dateChooser.add(left);
		        dateChooser.add(label);
		        dateChooser.add(right);
		        DatePicker datePicker = new DatePicker(comboBox,label, basicComboPopup.getPreferredSize().width,basicComboPopup.getPreferredSize().height);
		        
		        basicComboPopup.add(dateChooser);
		        basicComboPopup.add(datePicker);
		        
		        left.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						datePicker.changeMonthAction(-1);
					}
				});
		        right.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						datePicker.changeMonthAction(1);
					}
				});
		        comboBox.setRenderer(new DefaultListCellRenderer() {
					private static final long serialVersionUID = -969689250949408660L;

					@Override
			        public Component getListCellRendererComponent(final JList<?> list, Object value, final int index, final boolean isSelected,
			                final boolean cellHasFocus) {
			        	return new JLabel(datePicker.getDate().toReadable());
			        }
			    });
		        return basicComboPopup;
		    }
		});
	}
}
