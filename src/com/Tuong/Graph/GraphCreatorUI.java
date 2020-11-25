package com.Tuong.Graph;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JTextField;

import com.Tuong.Authenication.AuthManager;
import com.Tuong.ContentCreator.MedUI;
import com.Tuong.ContentHelper.BasicUI;
import com.Tuong.ContentHelper.ButtonAction;
import com.Tuong.EventListener.ConditionalFlag;
import com.Tuong.EventListener.EventListener;
import com.Tuong.EventListener.EventListenerManager;

public class GraphCreatorUI extends BasicUI{
	private static final long serialVersionUID = 4972121492576858217L;
	public GraphCreatorUI() {
		super("Create new graph", new Dimension(300,100), false);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
	}
	
	@Override
	public void UIOpenEvent(String name) {
		if(name == getName()) close();
	}
	
	@Override
	public void setupUI() {
		JTextField name = createTextField("Graph name", this);
		JTextField unit = createTextField("Graph unit", this);
		createButton("Create graph", this, new ButtonAction() {
			@Override
			public boolean click() {
				if(EventListenerManager.current.activateEvent
						("CreateGraphEvent", name.getText(), unit.getText(), new ConditionalFlag())){
					showDialog("Success", "You just created "+name.getText()+" graph", 1);
					close();
					return true;
				}
				showDialog("Fail", name.getText()+" exists", 0);
				return false;
			}
		});
	}
}
