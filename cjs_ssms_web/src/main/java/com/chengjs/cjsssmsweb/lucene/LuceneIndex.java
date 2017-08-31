package com.chengjs.cjsssmsweb.lucene;

import com.chengjs.cjsssmsweb.enums.EnvEnum;
import com.chengjs.cjsssmsweb.pojo.WebUser;
import com.chengjs.cjsssmsweb.util.PropertiesUtil;
import com.chengjs.cjsssmsweb.util.StringUtil;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.StringReader;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * LuceneIndex: lucene索引类
 * author: <a href="mailto:chengjs_minipa@outlook.com">chengjs</a>, version:1.0.0, 2017/8/24
 */
public class LuceneIndex {

	private Directory dir=null;

	/**
	 * 获取IndexWriter实例
	 * @return
	 * @throws Exception
	 */
	private IndexWriter getWriter()throws Exception{
		/*
		 * 生成的索引位置在env-config.properties里配置
		 */
		dir= FSDirectory.open(Paths.get(PropertiesUtil.getValue(EnvEnum.LUCENE_INDEX_PATH.val())));
		SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();
		IndexWriterConfig iwc=new IndexWriterConfig(analyzer);
		IndexWriter writer=new IndexWriter(dir, iwc);
		return writer;
	}

	/*
	 * 添加 WebUser 数据
	 * @param webUser
	 */
	public void addIndex(WebUser webUser)throws Exception{
		IndexWriter writer=getWriter();
		Document doc=new Document();
		/*
		 * yes是会将数据存进索引，如果查询结果中需要将记录显示出来就要存进去，如果查询结果
		 * 只是显示标题之类的就可以不用存，而且内容过长不建议存进去
		 * 使用TextField类是可以用于查询的。
		 */
		doc.add(new StringField("userid",String.valueOf(webUser.getUserid()), Field.Store.YES));
		doc.add(new TextField("username", webUser.getUsername(), Field.Store.YES));
		doc.add(new TextField("description", webUser.getDescription(), Field.Store.YES));

		writer.addDocument(doc);
		writer.close();
	}

	/**
	 * 更新博客索引
	 * @param webUser
	 * @throws Exception
	 */
	public void updateIndex(WebUser webUser)throws Exception{
		IndexWriter writer=getWriter();
		Document doc=new Document();
		doc.add(new StringField("userid",String.valueOf(webUser.getUserid()), Field.Store.YES));
		doc.add(new TextField("username", webUser.getUsername(), Field.Store.YES));
		doc.add(new TextField("description", webUser.getDescription(), Field.Store.YES));

		writer.updateDocument(new Term("userid", String.valueOf(webUser.getUserid())), doc);
		writer.close();
	}

	/**
	 * 删除指定博客的索引
	 * @param userid
	 * @throws Exception
	 */
	public void deleteIndex(String userid)throws Exception{
		IndexWriter writer=getWriter();
		writer.deleteDocuments(new Term("userid", userid));

		writer.forceMergeDeletes(); // 强制删除
		writer.commit();
		writer.close();
	}

	/**
	 * 查询用户
	 * @param query_key 查询关键字
	 * @return
	 * @throws Exception
	 */
	public List<WebUser> searchBlog(String query_key)throws Exception{
		/**
     * 1.解析器parser获取
		 * 注意的是查询索引的位置得是存放索引的位置，不然会找不到。
		 */
		dir = FSDirectory.open(Paths.get(PropertiesUtil.getValue(EnvEnum.LUCENE_INDEX_PATH.val())));
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher is=new IndexSearcher(reader);
		BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
		SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();

		/*
		 * 2.username和description就是我们需要进行查找的两个字段
		 * 同时在存放索引的时候要使用TextField类进行存放。
		 */
		QueryParser parser=new QueryParser("username",analyzer);
		Query query=parser.parse(query_key);
		QueryParser parser2=new QueryParser("description",analyzer);
		Query query2=parser2.parse(query_key);
		booleanQuery.add(query, BooleanClause.Occur.SHOULD);
		booleanQuery.add(query2, BooleanClause.Occur.SHOULD);

		TopDocs hits=is.search(booleanQuery.build(), 100);
		QueryScorer scorer=new QueryScorer(query);
		Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);

		/*
		 * 这里可以根据自己的需要来自定义查找关键字高亮时的样式。
		 */
		SimpleHTMLFormatter simpleHTMLFormatter=new SimpleHTMLFormatter("<b><font color='red'>","</font></b>");
		Highlighter highlighter=new Highlighter(simpleHTMLFormatter, scorer);
		highlighter.setTextFragmenter(fragmenter);
		List<WebUser> userList=new LinkedList<WebUser>();
		for(ScoreDoc scoreDoc:hits.scoreDocs){
			Document doc=is.doc(scoreDoc.doc);
			WebUser webUser=new WebUser();
			webUser.setUserid(doc.get("userid"));
			webUser.setDescription(doc.get("description"));

			String username=doc.get("username");
			String description=doc.get("description");

			if(username!=null){
				TokenStream tokenStream = analyzer.tokenStream("username", new StringReader(username));
				String husername=highlighter.getBestFragment(tokenStream, username);
				if(StringUtil.isEmpty(husername)){
					webUser.setUsername(username);
				}else{
					webUser.setUsername(husername);
				}
			}

			if(description!=null){
				TokenStream tokenStream = analyzer.tokenStream("description", new StringReader(description));
				String hContent=highlighter.getBestFragment(tokenStream, description);
				if(StringUtil.isEmpty(hContent)){
					if(description.length()<=200){
						webUser.setDescription(description);
					}else{
						webUser.setDescription(description.substring(0, 200));
					}
				}else{
					webUser.setDescription(hContent);
				}
			}
			userList.add(webUser);
		}
		return userList;
	}
}
