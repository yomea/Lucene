package com.java1234.lucene;

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

	private IndexWriter writer; // д����ʵ��
	
	/**
	 * ���췽�� ʵ����IndexWriter
	 * @param indexDir
	 * @throws Exception
	 */
	public Indexer(String indexDir)throws Exception{
		Directory dir=FSDirectory.open(Paths.get(indexDir));
		Analyzer analyzer=new StandardAnalyzer(); // ��׼�ִ���
		IndexWriterConfig iwc=new IndexWriterConfig(analyzer);
		writer=new IndexWriter(dir, iwc);
	}
	
	/**
	 * �ر�д����
	 * @throws Exception
	 */
	public void close()throws Exception{
		writer.close();
	}
	
	/**
	 * ����ָ��Ŀ¼�������ļ�
	 * @param dataDir
	 * @throws Exception
	 */
	public int index(String dataDir)throws Exception{
		File []files=new File(dataDir).listFiles();
		for(File f:files){
			indexFile(f);
		}
		return writer.numDocs();
	}

	/**
	 * ����ָ���ļ�
	 * @param f
	 */
	private void indexFile(File f) throws Exception{
		System.out.println("�����ļ���"+f.getCanonicalPath());
		Document doc=getDocument(f);
		writer.addDocument(doc);
	}

	/**
	 * ��ȡ�ĵ����ĵ���������ÿ���ֶ�
	 * @param f
	 */
	private Document getDocument(File f)throws Exception {
		Document doc=new Document();
		doc.add(new TextField("contents",new FileReader(f)));
		doc.add(new TextField("fileName", f.getName(),Field.Store.YES));
		doc.add(new TextField("fullPath",f.getCanonicalPath(),Field.Store.YES));
		return doc;
	}
	
	public static void main(String[] args) {
		String indexDir="D:\\lucene4";
		String dataDir="D:\\lucene4\\data";
		Indexer indexer=null;
		int numIndexed=0;
		long start=System.currentTimeMillis();
		try {
			indexer = new Indexer(indexDir);
			numIndexed=indexer.index(dataDir);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				indexer.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		long end=System.currentTimeMillis();
		System.out.println("������"+numIndexed+" ���ļ� ������"+(end-start)+" ����");
	}
}