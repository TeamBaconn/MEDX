package com.Tuong.ContentCreator;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.json.simple.JSONObject;

import com.Tuong.Authenication.AuthManager;
import com.Tuong.MedXMain.JSONHelper;
import com.Tuong.Medicine.Medicine;
import com.Tuong.Table.ButtonEditor;
import com.Tuong.Table.ButtonRenderer;
import com.Tuong.Table.MedicineListModel;
import com.Tuong.Table.SpinnerEditor;
import com.Tuong.Trie.TrieResult;

public class MedicineList extends JPanel {
	private static final long serialVersionUID = 7598135574795319656L;

	private JTable list;

	private JTextField medName;

	private AuthManager auth_manager;

	private MedicineListModel model;

	public MedicineList(AuthManager auth_manager) {
		this.auth_manager = auth_manager;

		setBorder(new CompoundBorder(new TitledBorder("Medication Information"), new EmptyBorder(0, 0, 0, 0)));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		model = new MedicineListModel(auth_manager);
		list = new JTable(model);
		list.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		list.getColumnModel().getColumn(4).setCellEditor(new SpinnerEditor(model, "setUnit"));
		list.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
		list.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(model, "deleteMedicine"));
		
		list.setRowHeight(40);
		list.setPreferredScrollableViewportSize(new Dimension(500, 70));
		JScrollPane scrollPne = new JScrollPane(list);
		scrollPne.setPreferredSize(new Dimension(500, 400));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		medName = new JTextField();
		medName.addKeyListener(new KeyAdapter() {
			@Override 
			public void keyReleased (KeyEvent e) {
				refreshSearch(medName.getText());
			}
		});
		refreshSearch("");
		add(medName);
		add(scrollPne);
	}

	private void refreshSearch(String query) {
		ArrayList<TrieResult> res = auth_manager.getMedicineManager().med_trie.getRecommend(query, true);
		model.clear();
		for (int i = 0; i < res.size(); i++) {
			// System.out.println((res.get(i).index + 1) + " " + res.get(i).score);
			JSONObject object = (JSONObject) JSONHelper
					.readFile(auth_manager.getMedicineManager().med_path_save + (res.get(i).index + 1) + ".json");
			model.addElement(new Medicine(((Long) object.get("ID")).intValue(),
					(String) object.get("tenThuoc"), "Unit",
					((Long) object.get("soLuong")).intValue(), (String) object.get("hoatChat"),
					(String) object.get("tuoiTho"), (String) object.get("giaKeKhai"), (String) object.get("nongDo"),
					(String) object.get("taDuoc")));
		}
		list.clearSelection();
	}
}
