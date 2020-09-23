package com.Tuong.Trie;

public class TrieSearchTask {
	private int[] score;
	private int max_result;
	public TrieSearchTask(Trie trie, String key, int max_result) {
		score = new int[trie.size];
		this.max_result = max_result;
		String[] s = trie.getTrieString(key).split(" ");
		for(int i = 0; i < s.length; i++) {
			TrieNode node = trie.search(s[i]);
			if(node==null) continue;
			node.id.forEach(t -> score[t]++);
		}
	}
	
}
