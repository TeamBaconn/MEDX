package com.Tuong.Medicine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.Tuong.MedXMain.JSONHelper;
import com.Tuong.Trie.Trie;

public class MedicineManager {

	private final String med_trie_path = "Data/MedicineInfo.dat";
	private final String hoat_chat_trie_path = "Data/HoatChatInfo.dat";

	public Trie med_trie;
	public Trie hoat_chat_trie;

	public MedicineManager() {
		med_trie = new Trie(med_trie_path);
		hoat_chat_trie = new Trie(hoat_chat_trie_path);
		//crawlData();
	}

	public final String med_path_save = "Medicine/";

	public void crawlData() {
		long time = System.currentTimeMillis();
		for (int k = 'a'; k <= 'z'; k++) {
			int flag = 0;
			System.out.println("Start with "+(char)k);
			while (flag != -1) {
				try {
					URL url = new URL(
							"https://drugbank.vn/services/drugbank/api/public/thuoc?page="+flag+"&size=1000&tenThuoc="
									+ (char) k + "&sort=rate,desc&sort=+" + (char) k + ",asc");
					flag++;
					BufferedReader in = new BufferedReader(
							new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
					String line = in.readLine();
					if (line.length() > 2) {
						JSONParser parser = new JSONParser();
						JSONArray arr = (JSONArray) parser.parse(line);
						for (int i = 0; i < arr.size(); i++) {
							JSONObject obj = (JSONObject) arr.get(i);
							if(med_trie.contains((String)obj.get("soDangKy"))) continue;
							int id = med_trie.insert((String)obj.get("soDangKy"));
							med_trie.insert(((String)obj.get("tenThuoc")),id);
							String[] hoatChat = ((String)obj.get("hoatChat")).split(" ");
							if(hoatChat.length > 0) hoat_chat_trie.insert(hoatChat[0], id);
							JSONObject fin = new JSONObject();
							fin.put("tenThuoc", obj.get("tenThuoc"));
							fin.put("hoatChat", obj.get("hoatChat"));
							fin.put("nongDo", obj.get("nongDo"));
							fin.put("taDuoc", obj.get("taDuoc"));
							fin.put("tuoiTho", obj.get("tuoiTho"));
							fin.put("dongGoi", obj.get("dongGoi"));
							fin.put("giaKeKhai", obj.get("giaKeKhai"));
							fin.put("soLuong", 0);
							JSONHelper.writeFile(med_path_save + id + ".json", fin.toJSONString());
						}
					}else flag = -1;
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
		System.out.println("Finish with time "+(System.currentTimeMillis()-time));
		med_trie.save(med_trie_path);
		hoat_chat_trie.save(hoat_chat_trie_path);
	}
}
