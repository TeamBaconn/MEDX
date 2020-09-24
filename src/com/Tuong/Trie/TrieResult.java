package com.Tuong.Trie;

public class TrieResult implements Comparable<TrieResult>{
	public int score, index;

	public TrieResult(int score, int index) {
		this.score = score;
		this.index = index;
	}

	@Override
	public int compareTo(TrieResult res) {
		return (this.score < res.score ? -1 : (this.score == res.score ? 0 : 1));
	}
}
