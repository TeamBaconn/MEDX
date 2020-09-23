package com.Tuong.Trie;

import java.util.ArrayList;

public class TrieNode {
	TrieNode[] children = new TrieNode[Trie.ALPHABET_SIZE];
	public ArrayList<Integer> id;
};