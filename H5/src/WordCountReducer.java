import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class WordCountReducer extends Reducer<WordWithFrequency, Text, Text, WordWithFrequency> {

    @Override
    public void reduce(WordWithFrequency key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        int sum = 0;

        for (Text val : values) {
            sum += 1;
        }

        key.setFrequency(sum);
        context.write(new Text(key.getWord()), key);
    }
}

