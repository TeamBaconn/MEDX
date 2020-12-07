package com.Tuong.DateUtils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

public class DateUI extends JComboBox<Object> {
	private static final long serialVersionUID = -186921978087652461L;

	private DatePicker _datePicker;
	private boolean center;
	
	public DateUI(int width, boolean center) {
		super();
		this.center = center;
		setOpaque(false);
		setBackground(Color.WHITE);
		setupUI(width <= 200 ? 200 : width);
	}
	
	private void setupUI(int width) {
		setUI(new BasicComboBoxUI() {
			@Override
            protected JButton createArrowButton() {
                return null;
            }
			@Override
		    public void paint( Graphics g, JComponent c ) {
				String s = _datePicker.getDate().toReadable();
		        Graphics2D g2 = (Graphics2D) g.create();
		        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(getBackground());
		        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getRadius(), getRadius());

		        g2.setColor(new Color(102, 102, 102));
		        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getRadius(), getRadius());
		        
				g.drawString(s, center? (getWidth()-g.getFontMetrics().stringWidth(s))/2 : 10, 
						(getHeight()+g.getFontMetrics().getHeight()-5)/2);
			}
			private int getRadius() {
				return 10;
			}
			@Override
			protected ComboPopup createPopup() {
				BasicComboPopup basicComboPopup = new BasicComboPopup(comboBox);
				basicComboPopup.setLayout(null);

				_datePicker = new DatePicker(comboBox, width);
				basicComboPopup.add(_datePicker);
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
