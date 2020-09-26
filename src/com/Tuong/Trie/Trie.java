package com.Tuong.Trie;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.Tuong.MedXMain.VNCharacterUtils;

public class Trie {

	static final int ALPHABET_SIZE = 37;
	private TrieNode root;
	public int size = 0;

	private int[] score;

	public Trie() {
		root = new TrieNode();
	}

	public Trie(String filepath) {
		root = new TrieNode();
		read(filepath);
	}
	int last;
	public void runLast(int max_result, int[] score, TrieNode root) {
		if(root == null || last > max_result) return;
		if(root.id != null) {
			for(int i = 0; i < root.id.size(); i++) {
				if(last > max_result) return;
				score[root.id.get(i)-1]++;
				last++;
			}	
		}
		for(int i = 0; i < root.children.length; i++) runLast(max_result, score, root.children[i]);
	}
	public ArrayList<TrieResult> getRecommend(String key, boolean run_blind) {
		key = getTrieString(key);
		ArrayList<TrieResult> result = new ArrayList<TrieResult>();
		score = new int[size];
		Arrays.fill(score, 0);
		String[] s = key.split(" ");
		for (int i = 0; i < s.length; i++) {
			TrieNode node = search(s[i]);
			if (node == null) continue;
			if (node.id == null) {
				last = 0;
				if(run_blind) runLast(10, score, node);
				continue;
			}
			node.id.forEach(t -> score[t-1]++);
		}
		for (int i = 0; i < score.length; i++) {
			if(!(score[i] != 0 && score[i] >= s.length)) continue;
			result.add(new TrieResult(score[i], i));
		}
		Collections.sort(result,Collections.reverseOrder()); 
		return result;
	}

	public int insertString(String key) {
		key = getTrieString(key);
		if(key.length() < 0) return -1;
		String[] s = key.split(" ");
		size++;
		for (int i = 0; i < s.length; i++)
			insert(s[i], size);
		return size;
	}
	private int getIndex(char c) {
		if(c >= 'a' && c <= 'z') return c-'a';
		if(c >= '0' && c <= '9') return c-'0';
		return 36; //Wierd symbol
	}
	private char getChar(int c) {
		if(c <= 26) return (char)(c + 'a');
		if(c < 36) return (char)(c + '0');
		return '-';
	}
	public int insert(String key) {
		key = getTrieString(key);
		if(key.length() < 0) return -1;
		size++;
		insert(key, size);
		return size;
	}
	public void insert(String key, int id) {
		key = getTrieString(key);
		int level;
		int length = key.length();
		int index;

		TrieNode pCrawl = root;

		for (level = 0; level < length; level++) {
			index = getIndex(key.charAt(level));
			if (pCrawl.children[index] == null)
				pCrawl.children[index] = new TrieNode();

			pCrawl = pCrawl.children[index];
		}
		if (pCrawl.id == null)
			pCrawl.id = new ArrayList<Integer>();
		if (!pCrawl.id.contains(id))
			pCrawl.id.add(id);
	}

	public TrieNode search(String key) {
		int level;
		int length = key.length();
		int index;
		TrieNode pCrawl = root;

		for (level = 0; level < length; level++) {
			index = getIndex(key.charAt(level));

			if (pCrawl.children[index] == null)
				return pCrawl;

			pCrawl = pCrawl.children[index];
		}

		return pCrawl;
	}
	
	public boolean contains(String key) {
		key = getTrieString(key);
		int level;
		int length = key.length();
		int index;
		TrieNode pCrawl = root;

		for (level = 0; level < length; level++) {
			index = getIndex(key.charAt(level));

			if (pCrawl.children[index] == null)
				return false;

			pCrawl = pCrawl.children[index];
		}

		return pCrawl.children != null;
	}

	public String getTrieString(String s) {
		return VNCharacterUtils.removeAccent(s.toLowerCase());
	}
	public void save(String filepath) {
		try {
			long time = System.currentTimeMillis();
			FileOutputStream fos = new FileOutputStream(filepath);
			BufferedOutputStream writer = new BufferedOutputStream(fos);
			DataOutputStream dos = new DataOutputStream(writer);
			saveRun(root, dos);
			writer.flush();
			writer.close();
			time = System.currentTimeMillis() - time;
			System.out.println("Finish saving with " + size+ " components in "+time+" ms");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	private void saveRun(TrieNode root, DataOutputStream fos) throws IOException {
		if (root.id == null)
			fos.writeInt(0);
		else {
			fos.writeInt(root.id.size());
			for (int i = 0; i < root.id.size(); i++)
				fos.writeInt(root.id.get(i));
		}
		for (int i = 0; i < root.children.length; i++) {
			fos.writeBoolean(root.children[i] != null);
			if (root.children[i] != null)
				saveRun(root.children[i], fos);
		}
	}

	public void read(String filepath) {
		try {
			long time = System.currentTimeMillis();
			FileInputStream fis = new FileInputStream(filepath);
			BufferedInputStream reader = new BufferedInputStream(fis);
			DataInputStream din = new DataInputStream(reader);
			readRun(root, din,"");
			time = System.currentTimeMillis() - time;
			System.out.println("Finish reading with " + size + " components in "+time+" ms");
		} catch (Exception ex) {
			System.out.println("Can't read trie from " + filepath);
		}
	}
	
	private void readRun(TrieNode root, DataInputStream reader, String ans) throws IOException {
		int arraysize = reader.readInt();
		if (arraysize > 0) {
			if (root.id == null)
				root.id = new ArrayList<Integer>();
			for (int i = 0; i < arraysize; i++) {
				int input = reader.readInt();
				root.id.add(input);
				if (input > size)
					size = input;
			}
			//System.out.println(ans +" " + root.id);
		}
		for (int i = 0; i < root.children.length; i++) {
			if (reader.readBoolean()) {
				root.children[i] = new TrieNode();
				readRun(root.children[i], reader, ans+getChar(i));
			}
		}
	}
	public void delete(String key, int id) {
		String[] s = getTrieString(key).split(" ");
		for(int i = 0; i < s.length; i++) remove(root, s[i], id, 0);
		if(id >= size) size--;
	}
	public boolean isEmpty(TrieNode root) {
		if(root == null) return true;
        for(int i = 0; i < root.children.length; i++) if(root.children[i] != null) return false;
        return true;
	}
	public TrieNode remove(TrieNode root, String key, int id, int depth) 
	{ 
	    if (root == null) 
	        return null; 
	    if (depth == key.length()) { 
	        if (root.id != null) {
	        	for(int i = 0; i < root.id.size(); i++) if(root.id.get(i) == id) {
	        		root.id.remove(i);
	        	}
	        	if(root.id.size() <= 0) root.id = null;
	        }
	        if (isEmpty(root) && root.id == null) root = null; 
	        return root; 
	    } 
	    int index = getIndex(key.charAt(depth));
	    root.children[index] = remove(root.children[index], key, id, depth + 1); 
	    if (isEmpty(root) && root.id == null) {  
	        root = null; 
	    } 
	    return root; 
	} 
}