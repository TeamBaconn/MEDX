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

public class Trie {

	static final int ALPHABET_SIZE = 26;
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

	public int[] getRecommend(String key, int max_result) {
		key = getTrieString(key);
		int[] result = new int[max_result];
		int[] result_max = new int[max_result];
		Arrays.fill(result_max, 0);
		Arrays.fill(result, -1);
		if(key.length() <= 0) return result;
		score = new int[size];
		Arrays.fill(score, 0);
		String[] s = key.split(" ");
		for (int i = 0; i < s.length; i++) {
			TrieNode node = search(s[i]);
			if (node == null || node.id == null)
				continue;
			node.id.forEach(t -> score[t-1]++);
		}
		for (int i = 0; i < score.length; i++) {
			if(score[i] == 0) continue;
			for (int k = 0; k < max_result; k++) {
				if (score[i] >= result_max[k]) {
					for (int j = max_result - 2; j >= k; j--) {
						result_max[j + 1] = result_max[j];
						result[j + 1] = result[j];
					}
					result_max[k] = score[i];
					result[k] = i;
					break;
				}
			}
		}
		//System.out.println(key);
		//for(int i = 0; i < max_result; i++) System.out.println(result_max[i]+" "+(result[i]+1));
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

	public void insert(String key, int size) {
		int level;
		int length = key.length();
		int index;

		TrieNode pCrawl = root;

		for (level = 0; level < length; level++) {
			index = key.charAt(level) - 'a';
			if (pCrawl.children[index] == null)
				pCrawl.children[index] = new TrieNode();

			pCrawl = pCrawl.children[index];
		}
		if (pCrawl.id == null)
			pCrawl.id = new ArrayList<Integer>();
		if (!pCrawl.id.contains(size))
			pCrawl.id.add(size);
	}

	public TrieNode search(String key) {
		key = getTrieString(key);
		int level;
		int length = key.length();
		int index;
		TrieNode pCrawl = root;

		for (level = 0; level < length; level++) {
			index = key.charAt(level) - 'a';

			if (pCrawl.children[index] == null)
				return pCrawl;

			pCrawl = pCrawl.children[index];
		}

		return pCrawl;
	}

	public String getTrieString(String input) {
		return input.toLowerCase();
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
			time -= System.currentTimeMillis();
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
			time -= System.currentTimeMillis();
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
				readRun(root.children[i], reader, ans+(char)((int)'a'+i));
			}
		}
	}
}