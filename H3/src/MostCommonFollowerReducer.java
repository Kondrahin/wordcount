import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

import java.io.IOException;
import java.util.*;

public class MostCommonFollowerReducer extends Reducer<Text, Text, Text, Text> {

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        Map<String, Integer> freqMap = new HashMap<>();

        for (Text val : values) {
            String nextWord = val.toString();
            freqMap.put(nextWord, freqMap.getOrDefault(nextWord, 0) + 1);
        }

        String mostCommon = null;
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : freqMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostCommon = entry.getKey();
            }
        }

        if (mostCommon != null) {
            context.write(key, new Text(mostCommon));
        }
    }
}

