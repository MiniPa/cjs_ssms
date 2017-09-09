package com.chengjs.cjsssmsweb.components.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * LuceneTest:
 * author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>, version:1.0.0, 2017/8/29
 */
public class LuceneTest {
  private static final Logger log = LoggerFactory.getLogger(LuceneTest.class);

  @Test
  public void luceneTest() throws Exception {

    // Store the index in memory:
    Directory directory = new RAMDirectory();
    // To store an index on disk, use this instead:
    // Directory directory = FSDirectory.open("/tmp/testindex");

    Analyzer analyzer = new StandardAnalyzer();
    IndexWriterConfig config = new IndexWriterConfig(analyzer);

    try {
      IndexWriter iwriter = new IndexWriter(directory, config);
      Document doc = new Document();
      String text = "This is the text to be indexed.";
      doc.add(new Field("fieldname", text, TextField.TYPE_STORED));
      iwriter.addDocument(doc);
      iwriter.close();

      // Now search the index:
      DirectoryReader ireader = DirectoryReader.open(directory);
      IndexSearcher isearcher = new IndexSearcher(ireader);

      // Parse a simple query that searches for "text":
      QueryParser parser = new QueryParser("fieldname", analyzer);
      Query query = parser.parse("text");
      ScoreDoc[] hits = isearcher.search(query, 1000, new Sort()).scoreDocs;
      assertEquals(1, hits.length);

      // Iterate through the results:
      for (int i = 0; i < hits.length; i++) {
        Document hitDoc = isearcher.doc(hits[i].doc);
        assertEquals("This is the text to be indexed.", hitDoc.get("fieldname"));
      }
      ireader.close();
      directory.close();
      log.debug("LuceneTest Passed");
    } catch (ParseException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}