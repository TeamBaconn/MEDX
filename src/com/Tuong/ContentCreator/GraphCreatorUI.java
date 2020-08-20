package com.Tuong.ContentCreator;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JTextField;

public class GraphCreatorUI extends BasicUI{
	private PatientUI pUI;
	public GraphCreatorUI(PatientUI pUI) {
		super("Create new graph", new Dimension(100,100), false, null);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		this.pUI = pUI;
	}
	@Override
	public void setupUI() {
		JTextField name = createTextField("Graph name", this);
		JTextField unit = createTextField("Graph unit", this);
		createButton("Create graph", this, new ButtonAction() {
			@Override
			public void click() {
				if(pUI.createNewGraph(name.getText(), unit.getText())) setVisible(false);
			}
		});
	}
}
