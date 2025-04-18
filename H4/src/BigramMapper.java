import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class BigramMapper extends Mapper<Object, Text, Text, Text> {

    private Analyzer analyzer;

    @Override
    protected void setup(Context context) {
        analyzer = new StandardAnalyzer();
    }

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {

        String[] sentences = value.toString().split("[.!?]+");

        for (String sentence : sentences) {
            List<String> tokens = new ArrayList<>();

            try (TokenStream tokenStream = analyzer.tokenStream(null, new StringReader(sentence))) {
                CharTermAttribute attr = tokenStream.addAttribute(CharTermAttribute.class);
                tokenStream.reset();

                while (tokenStream.incrementToken()) {
                    tokens.add(attr.toString());
                }

                tokenStream.end();
            }

            for (int i = 0; i < tokens.size() - 1; i++) {
                context.write(new Text(tokens.get(i)), new Text(tokens.get(i + 1)));
            }
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException {
        analyzer.close();
    }
}

