import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.*;
import java.util.*;

public class WordPairMapper extends Mapper<Object, Text, Text, Text> {

    private Analyzer analyzer;

    @Override
    protected void setup(Context context) {
        analyzer = new StandardAnalyzer();
    }

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {

        String[] sentences = value.toString().split("[.!?]+");

        for (String sentence : sentences) {
            List<String> words = tokenize(sentence);
            for (int i = 0; i < words.size() - 1; i++) {
                String current = words.get(i);
                String next = words.get(i + 1);
                context.write(new Text(current), new Text(next));
            }
        }
    }

    private List<String> tokenize(String sentence) throws IOException {
        List<String> result = new ArrayList<>();
        try (TokenStream ts = analyzer.tokenStream(null, new StringReader(sentence))) {
            CharTermAttribute attr = ts.addAttribute(CharTermAttribute.class);
            ts.reset();
            while (ts.incrementToken()) {
                result.add(attr.toString());
            }
            ts.end();
        }
        return result;
    }

    @Override
    protected void cleanup(Context context) throws IOException {
        analyzer.close();
    }
}

