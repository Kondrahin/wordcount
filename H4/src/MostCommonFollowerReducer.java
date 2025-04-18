import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

import java.io.IOException;
import java.util.*;

public class MostCommonFollowerReducer extends Reducer<Text, Text, Text, WordWithFrequency> {

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        Map<String, Integer> frequencyMap = new HashMap<>();

        for (Text value : values) {
            String nextWord = value.toString();
            frequencyMap.put(nextWord, frequencyMap.getOrDefault(nextWord, 0) + 1);
        }

        String mostCommon = null;
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostCommon = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        if (mostCommon != null) {
            WordWithFrequency result = new WordWithFrequency(mostCommon, maxCount);
            context.write(key, result);
        }
    }
}

