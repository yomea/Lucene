package com.may.lucene;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexer {
	
	private IndexWriter writer = null;//写索引
	
	public Indexer(String indexDir) throws Exception {
		//创建索引目录
		Directory dir = FSDirectory.open(Paths.get(indexDir));
		
		Analyzer analyzer = new StandardAnalyzer();//标准分词器,不适用于中文
		
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		
		writer = new IndexWriter(dir, config);
		
		
	}
	
	public int index(String dataDir) throws Exception {
		
		File[] files = new File(dataDir).listFiles();
		
		for (File file : files) {
			indexFile(file);
		}
		return writer.numDocs();//返回索引的个数.
		
	}

	/**
	 * 建立索引文件
	 * @param file
	 * @throws Exception 
	 */
	private void indexFile(File file) throws Exception {
		System.out.println("file 的路径名：" + file.getCanonicalPath());
		
		Document doc =  getDocument(file);
		
		writer.addDocument(doc);
		
	}

	private Document getDocument(File file) throws Exception {
		
		Document document = new Document();
		document.add(new TextField("contents", new FileReader(file)));
		document.add(new TextField("fileName", file.getName(), Field.Store.YES));
		document.add(new TextField("fullName", file.getCanonicalPath(), Field.Store.YES));
		
		return document;
	}

	
	/**
	 * 关闭写索引
	 * @throws ExceptionAO
	 */
	public void close() throws Exception {
		writer.close();
	}
	
	
}
