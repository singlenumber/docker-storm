package udacity.storm;

import org.apache.storm.task.ShellBolt;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
//import storm.starter.spout.RandomSentenceSpout;

import java.util.Map;

/**
 * A bolt that parses the tweet into words
 */

 // https://github.com/apache/storm/blob/master/examples/storm-starter/src/jvm/storm/starter/WordCountTopology.java

  public class URLBolt extends ShellBolt implements IRichBolt {

    public URLBolt() {
      super("python", "urltext.py");
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
      declarer.declare(new Fields("text"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
      return null;
    }
  }
