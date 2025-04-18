import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class WordCountMapper extends Mapper<Object, Text, WordWithFrequency, Text> {

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        
        String[] words = value.toString().split("\\s+");
        
        for (String word : words) {
            if (word != null && !word.isEmpty()) {
                WordWithFrequency wordWithFrequency = new WordWithFrequency(word, 1);
                context.write(wordWithFrequency, new Text(word));
            }
        }
    }
}

