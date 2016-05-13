package com.flatironschool.javacs;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

/**
 * Encapsulates a map from search term to set of TermCounter.
 * 
 * @author downey
 *
 */
public class Index {

	private Map<String, Set<TermCounter>> index = new HashMap<String, Set<TermCounter>>();
	
	/**
	 * Adds a TermCounter to the set associated with `term`.
	 * 
	 * @param term
	 * @param tc
	 */
	public void add(String term, TermCounter tc) {
		Set<TermCounter> set = get(term);

		// if we're seeing a term for the first time, make a new Set
		if (set == null) {
			set = new HashSet<TermCounter>();
			index.put(term, set);
		}
		// otherwise we can modify an existing Set
		set.add(tc);
	}

	/**
	 * Looks up a search term and returns a set of TermCounters.
	 * 
	 * @param term
	 * @return
	 */
	public Set<TermCounter> get(String term) {
		return index.get(term);
	}

	/**
	 * Add a page to the index.
	 * 
	 * @param url         URL of the page.
	 * @param paragraphs  Collection of elements that should be indexed.
	 */
	public void indexPage(String url, Elements paragraphs) {
		// make a TermCounter and count the terms in the paragraphs
        // TODO: fill this in
		TermCounter newCounter = new TermCounter(url);
		newCounter.processElements(paragraphs);
		// for each term in the TermCounter, add the TermCounter to the index
        // TODO: fill this in
        for (String curr : newCounter.keySet()) {
        	add(curr, newCounter);
        }
	}

	/**
	 * Prints the contents of the index.
	 */
	public void printIndex() {
		// loop through the search terms
		for (String term: keySet()) {
			System.out.println(term);
			
			// for each term, print the pages where it appears
			Set<TermCounter> tcs = get(term);
			for (TermCounter tc: tcs) {
				Integer count = tc.get(term);
				System.out.println("    " + tc.getLabel() + " " + count);
			}
		}
	}

	/**
	 * Returns the set of terms that have been indexed.
	 * 
	 * @return
	 */
	public Set<String> keySet() {
		return index.keySet();
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		WikiFetcher wf = new WikiFetcher();
		Index indexer = new Index();

		String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
		Elements paragraphs = wf.fetchWikipedia(url);
		indexer.indexPage(url, paragraphs);
		
		url = "https://en.wikipedia.org/wiki/Programming_language";
		paragraphs = wf.fetchWikipedia(url);
		indexer.indexPage(url, paragraphs);
		
		indexer.printIndex();
	}


}
