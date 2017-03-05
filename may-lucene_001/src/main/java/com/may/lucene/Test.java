package com.may.lucene;

public class Test {
	
	public static void main(String[] args) {
		
		String indexDir = "D:\\Lucene\\index";
		String dataDir = "D:\\Lucene\\data";
		Indexer indexer = null;
		
		try {
			indexer = new Indexer(indexDir);
			int num = indexer.index(dataDir);
			
			System.out.println("index number:" + num);
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally {
			try {
				indexer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
