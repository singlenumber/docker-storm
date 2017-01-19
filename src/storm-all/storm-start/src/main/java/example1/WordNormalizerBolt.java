package main.java.example1;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * Created by etude on 8/28/16.
 */
public class WordNormalizerBolt extends BaseRichBolt{
    private OutputCollector collector;
    @Override
    public void cleanup() {}

    /**
     * The bolt will receive the line from the
     * words file and process it to Normalize this line
     *
     * The normalize will be put the words in lower case
     * and split the line to get all words in this
     */
    @Override
    public void execute(Tuple input) {
        String word = input.getString(0);
        word = word.trim();
        word = word.toLowerCase();
        String[] words = word.split(" ");
        for(String str : words){
            str = str.trim();
            if(!"".equals(str)){
                //Emit the word
                collector.emit(new Values(str));
            }
        }
        //Set the tuple as Acknowledge
        collector.ack(input);
    }
    @Override
    public void prepare(Map stormConf, TopologyContext context,
                        OutputCollector collector) {
        this.collector = collector;
    }

    /**
     * The bolt will only emit the field "word"
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }
}
