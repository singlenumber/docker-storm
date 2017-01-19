package main.java.example1;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by etude on 8/28/16.
 */
public class TopologyMain {
    public static void main(String[] args) throws InterruptedException {

        //Topology definition
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("word-reader", new WordReaderSpout());
        builder.setBolt("word-normalizer", new WordNormalizerBolt()).shuffleGrouping("word-reader");
        builder.setBolt("word-counter", new WordCounterBolt(),2).fieldsGrouping("word-normalizer", new Fields("word"));
        //Configuration
        Config conf = new Config();
        conf.put("wordsFile", "/Users/Etude/workspace/cluster-work/docker-storm/src/storm-all/storm-start/src/main/resources/words.txt");
        conf.setDebug(false);
        //Topology run
        conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("Getting-Started-Topology", conf, builder.createTopology());
        Thread.sleep(5000);
        cluster.killTopology("Getting-Started-Topology");
        cluster.shutdown();
    }


}
