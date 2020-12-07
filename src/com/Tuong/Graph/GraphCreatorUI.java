package com.Tuong.Graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.Tuong.ContentHelper.BasicUI;
import com.Tuong.ContentHelper.CustomButton;
import com.Tuong.ContentHelper.FormCreator;
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
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(Color.decode("#f7f1e3"));
		int[] n = {100,200};
		FormCreator form = new FormCreator(panel, 2, n, 40);
		form.createLabel("Graph name");
		JTextField name = form.createTextField("");
		form.createLabel("Graph unit");
		JTextField unit = form.createTextField("");
		form.addComponent(null);
		CustomButton button = new CustomButton("Create graph");
		form.addComponent(button);
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
