package com.Tuong.Graph;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.Tuong.ContentHelper.BasicUI;
import com.Tuong.ContentHelper.CustomButton;
import com.Tuong.ContentHelper.Form;
import com.Tuong.EventListener.ConditionalFlag;
import com.Tuong.EventListener.EventListenerManager;

public class GraphCreatorUI extends BasicUI{
	private static final long serialVersionUID = 4972121492576858217L;
	public GraphCreatorUI() {
		super("Create new graph", new Dimension(300,200), false);
	}
	
	@Override
	public void UIOpenEvent(String name) {
		if(name == getName()) close();
	}
	
	@Override
	public void setupUI() {
		Form panel = new Form(new int[] {100,200}, 40);
		panel.setBackground(getBackground());
		panel.createLabel("Graph name");
		JTextField name = panel.createTextField("");
		panel.createLabel("Graph unit");
		JTextField unit = panel.createTextField("");
		panel.add((Component)null);
		CustomButton button = new CustomButton("Create graph");
		panel.add(button);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(EventListenerManager.current.activateEvent
						("CreateGraphEvent", name.getText(), unit.getText(), new ConditionalFlag())){
					showDialog("Success", "You just created "+name.getText()+" graph", 1);
					close();
					return;
				}
				showDialog("Fail", name.getText()+" exists", 0);
			}
		});
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(panel);
		pack();
	}
}
