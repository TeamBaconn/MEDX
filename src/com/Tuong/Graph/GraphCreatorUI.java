package com.Tuong.Graph;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JTextField;

import com.Tuong.Authenication.AuthManager;
import com.Tuong.ContentCreator.MedUI;
import com.Tuong.ContentHelper.BasicUI;
import com.Tuong.ContentHelper.ButtonAction;

public class GraphCreatorUI extends BasicUI{
	private static final long serialVersionUID = 4972121492576858217L;
	
	private MedUI pUI;
	public GraphCreatorUI(AuthManager pUI) {
		super("Create new graph", new Dimension(300,100), false, null);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		this.pUI = pUI.getMedUI();
	}
	@Override
	public void setupUI() {
		JTextField name = createTextField("Graph name", this);
		JTextField unit = createTextField("Graph unit", this);
		createButton("Create graph", this, new ButtonAction() {
			@Override
			public void click() {
				if(pUI.createNewGraph(name.getText(), unit.getText())) {
					showDialog("Success", "You just created "+name.getText()+" graph", 1);
					setVisible(false);
				}else 
					showDialog("Fail", name.getText()+" exists", 0);
			}
		});
	}
}
