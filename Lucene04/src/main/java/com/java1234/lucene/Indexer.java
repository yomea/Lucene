package com.java1234.lucene;

import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexer {

	private Integer ids[]={1,2,3};
	private String citys[]={"aingdao","banjing","changhai"};
	private String descs[]={
			"Qingdao is b beautiful city.",
			"Nanjing is c city of culture.",
			"Shanghai is d bustling city."
	};
	
	private Directory dir;
	
	/**
	 * ��ȡIndexWriterʵ��
	 * @return
	 * @throws Exception
	 */
	private IndexWriter getWriter()throws Exception{
		Analyzer analyzer=new StandardAnalyzer(); // ��׼�ִ���
		IndexWriterConfig iwc=new IndexWriterConfig(analyzer);
		IndexWriter writer=new IndexWriter(dir, iwc);
		return writer;
	}
	
	/**
	 * ��������
	 * @param indexDir
	 * @throws Exception
	 */
	private void index(String indexDir)throws Exception{
		dir=FSDirectory.open(Paths.get(indexDir));
		IndexWriter writer=getWriter();
		for(int i=0;i<ids.length;i++){
			Document doc=new Document();
			doc.add(new IntField("id", ids[i], Field.Store.YES));
			doc.add(new StringField("city",citys[i],Field.Store.YES));
			doc.add(new TextField("desc", descs[i], Field.Store.YES));
			writer.addDocument(doc); // ����ĵ�
		}
		writer.close();
	}
	
	
	public static void main(String[] args) throws Exception {
		new Indexer().index("D:\\lucene5");
	}
	
}
