import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.*;
import java.util.*;

public class WordMapper extends Mapper<Object, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private Analyzer analyzer;

    @Override
    protected void setup(Context context) {
        analyzer = new StandardAnalyzer(); 
    }

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        try (TokenStream tokenStream = analyzer.tokenStream(null, new StringReader(value.toString()))) {
            CharTermAttribute attr = tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();

            while (tokenStream.incrementToken()) {
                String token = attr.toString();
                context.write(new Text(token), one);
            }

            tokenStream.end();
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException {
        analyzer.close();
    }
}

