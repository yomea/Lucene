package com.may.lucene;

import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Seacher {
	
	public static void query(String indexDir, String q) throws Exception {
		//获取索引目录
		Directory dir = FSDirectory.open(Paths.get(indexDir));
		//读取索引目录
		IndexReader reader = DirectoryReader.open(dir);
		//建立搜索对象
		IndexSearcher searcher = new IndexSearcher(reader);
		//实例一个标准分词器
		Analyzer analyzer = new StandardAnalyzer();
		//查询解析器，对哪个field进行查询
		QueryParser parser = new QueryParser("contents", analyzer);
		//创建查询对象
		Query query = parser.parse(q);
		//搜索，取前十条,搜索出来的有点类似代理对象
		TopDocs hits = searcher.search(query, 10);
		long start = System.currentTimeMillis();
		for(ScoreDoc scoreDoc : hits.scoreDocs) {
			//搜索出来的类似代理对象，所以要使用docID再用searcher查一遍
			Document doc = searcher.doc(scoreDoc.doc);
			System.out.println(doc.get("fullName"));
		}
		long end = System.currentTimeMillis();
		System.out.println("查询总共花费时间为：" + (-start + end) + "ms");
		System.out.println("总共查到" + hits.scoreDocs.length + "条记录！");
		reader.close();
		
	}

}
