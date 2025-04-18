import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.Writable;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class WordWithFrequency implements WritableComparable<WordWithFrequency> {
    private String word;
    private int frequency;

    
    public WordWithFrequency() {}

    
    public WordWithFrequency(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }


    @Override
    public void write(DataOutput out) throws IOException {
        if (word != null) {
            out.writeUTF(word);
        } else {
            out.writeUTF(""); 
        }
        out.writeInt(frequency);
    }

    
    @Override
    public void readFields(DataInput in) throws IOException {
        word = in.readUTF();
        frequency = in.readInt();
    }

    
    @Override
    public int compareTo(WordWithFrequency other) {
        if (other == null) {
            return 1;
        }
        return Integer.compare(other.frequency, this.frequency);
    }

    @Override
    public String toString() {
        return word + ": " + frequency;
    }
}

