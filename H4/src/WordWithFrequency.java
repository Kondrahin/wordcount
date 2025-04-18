import org.apache.hadoop.io.*;

import java.io.*;

public class WordWithFrequency implements Writable {

    private Text word;
    private IntWritable frequency;

    public WordWithFrequency() {
        this.word = new Text();
        this.frequency = new IntWritable();
    }

    public WordWithFrequency(String word, int frequency) {
        this.word = new Text(word);
        this.frequency = new IntWritable(frequency);
    }

    public Text getWord() {
        return word;
    }

    public IntWritable getFrequency() {
        return frequency;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        word.write(out);
        frequency.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        word.readFields(in);
        frequency.readFields(in);
    }

    @Override
    public String toString() {
        return word.toString() + "\t" + frequency.get();
    }
}

