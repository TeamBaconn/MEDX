package com.Tuong.DateUtils;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
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

public class DateUI extends JComboBox<Object> {
	private static final long serialVersionUID = -186921978087652461L;

	private DatePicker _datePicker;

	public DateUI(int width) {
		super();
		setupUI(width <= 200 ? 200 : width);
	}

	private void setupUI(int width) {
		setUI(new BasicComboBoxUI() {
			@Override
			protected JButton createArrowButton() {
				JButton button = super.createArrowButton();
				button.setContentAreaFilled(false);
				button.setBorder(BorderFactory.createEmptyBorder());
				return button;
			}

			@Override
			protected ComboPopup createPopup() {
				BasicComboPopup basicComboPopup = new BasicComboPopup(comboBox);
				basicComboPopup.setLayout(null);
				
				_datePicker = new DatePicker(comboBox, width);
				basicComboPopup.add(_datePicker);
				comboBox.setRenderer(new DefaultListCellRenderer() {
					private static final long serialVersionUID = -969689250949408660L;

					@Override
					public Component getListCellRendererComponent(final JList<?> list, Object value, final int index,
							final boolean isSelected, final boolean cellHasFocus) {
						return new JLabel(_datePicker.getDate().toReadable());
					}
				});
				return basicComboPopup;
			}
		});
	}

	public Date getDate() {
		return _datePicker.getDate();
	}

	public void setDate(Date date) {
		_datePicker.setDate(date);
	}
}
