package com.Tuong.Medicine;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.Tuong.EventListener.EventListener;
import com.Tuong.EventListener.EventListenerManager;
import com.Tuong.MedXMain.JSONHelper;
import com.Tuong.Trie.Trie;
import com.Tuong.Trie.TrieResult;

public class MedicineManager implements EventListener {

	private final String med_trie_path = "Data/MedicineInfo.dat";
	private final String hoat_chat_trie_path = "Data/HoatChatInfo.dat";

	public Trie med_trie;
	public Trie hoat_chat_trie;

	public MedicineManager() {
		Register();
		med_trie = new Trie(med_trie_path);
		hoat_chat_trie = new Trie(hoat_chat_trie_path);
		// crawlData();
		crawlDataType("dongGoi");
	}

	public final String med_path_save = "Medicine/";

	@Override
	public void MedicineDeleteEvent(Medicine medicine) {
		med_trie.delete(medicine.getName(), medicine.getID());
		med_trie.save(med_trie_path);
		hoat_chat_trie.delete(medicine.getHoatChat().split(" ")[0], medicine.getID());
		hoat_chat_trie.save(hoat_chat_trie_path);
		new File(med_path_save + medicine.getName() + ".med").delete();
	}

	@Override
	public void MedicineQueryRequest(String query, String promoter) {
		ArrayList<TrieResult> res = med_trie.getRecommend(query, true);
		ArrayList<TrieResult> pro = hoat_chat_trie.getRecommend(promoter, true);
		if (promoter.length() != 0) {
			if (query.length() != 0) {
				for (TrieResult p : pro)
					for (TrieResult r : res)
						if (p.index == r.index)
							r.score += p.score;
				Collections.sort(res, Collections.reverseOrder());
			}else res = pro;
		}
		for (int i = 0; i < res.size(); i++) {
			Medicine med = (Medicine) JSONHelper.readObject(med_path_save + (res.get(i).index + 1) + ".med");
			EventListenerManager.current.activateEvent("MedicineLoadEvent", med);
		}
	}

	private void crawlData() {
		Trie duplicate = new Trie();
		long time = System.currentTimeMillis();
		for (int k = 'a'; k <= 'z'; k++) {
			int flag = 0;
			System.out.println("Start with " + (char) k);
			while (flag != -1) {
				try {
					URL url = new URL("https://drugbank.vn/services/drugbank/api/public/thuoc?page=" + flag
							+ "&size=1000&tenThuoc=" + (char) k + "&sort=rate,desc&sort=+" + (char) k + ",asc");
					flag++;
					BufferedReader in = new BufferedReader(
							new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
					String line = in.readLine();
					if (line.length() > 2) {
						JSONParser parser = new JSONParser();
						JSONArray arr = (JSONArray) parser.parse(line);
						for (int i = 0; i < arr.size(); i++) {
							JSONObject obj = (JSONObject) arr.get(i);
							if (duplicate.contains((String) obj.get("soDangKy")))
								continue;
							int id = duplicate.insert((String) obj.get("soDangKy"));
							med_trie.insert(((String) obj.get("tenThuoc")), id);
							String[] hoatChat = ((String) obj.get("hoatChat")).split(" ");
							if (hoatChat.length > 0)
								hoat_chat_trie.insert(hoatChat[0], id);
							Medicine med = new Medicine(id, (String) obj.get("tenThuoc"), 0,
									(String) obj.get("hoatChat"), (String) obj.get("giaKeKhai"),
									(String) obj.get("nongDo"));

							JSONHelper.writeObject(med_path_save + id + ".med", med);
						}
					} else
						flag = -1;
					in.close();
				} catch (MalformedURLException e) {
					System.out.println("Malformed URL: " + e.getMessage());
				} catch (IOException e) {
					System.out.println("I/O Error: " + e.getMessage());
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Finish with time " + (System.currentTimeMillis() - time));
		med_trie.save(med_trie_path);
		hoat_chat_trie.save(hoat_chat_trie_path);
	}
	
	private void crawlDataType(String query) {
		Trie duplicate = new Trie();
		long time = System.currentTimeMillis();
		for (int k = 'a'; k <= 'z'; k++) {
			int flag = 0;
			//System.out.println("Start with " + (char) k);
			while (flag != -1) {
				try {
					URL url = new URL("https://drugbank.vn/services/drugbank/api/public/thuoc?page=" + flag
							+ "&size=10000&tenThuoc=" + (char) k + "&sort=rate,desc&sort=+" + (char) k + ",asc");
					flag++;
					BufferedReader in = new BufferedReader(
							new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
					String line = in.readLine();
					if (line.length() > 2) {
						JSONParser parser = new JSONParser();
						JSONArray arr = (JSONArray) parser.parse(line);
						for (int i = 0; i < arr.size(); i++) {
							JSONObject obj = (JSONObject) arr.get(i);
							if (duplicate.contains((String) obj.get(query)))
								continue;
							int id = duplicate.insert((String) obj.get(query));
							System.out.println((String) obj.get(query));
						}
					} else
						flag = -1;
					in.close();
				} catch (MalformedURLException e) {
					System.out.println("Malformed URL: " + e.getMessage());
				} catch (IOException e) {
					System.out.println("I/O Error: " + e.getMessage());
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Finish with time " + (System.currentTimeMillis() - time));
		med_trie.save(med_trie_path);
		hoat_chat_trie.save(hoat_chat_trie_path);
	}
}
