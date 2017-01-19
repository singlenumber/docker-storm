package udacity.storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;

class TweetTopology
{
  public static void main(String[] args) throws Exception
  {
    // create the topology
    TopologyBuilder builder = new TopologyBuilder();

    /*
     * In order to create the spout, you need to get twitter credentials
     * If you need to use Twitter firehose/Tweet stream for your idea,
     * create a set of credentials by following the instructions at
     *
     * https://dev.twitter.com/discussions/631
     *
     */

    // now create the tweet spout with the credentials
    TweetSpout tweetSpout = new TweetSpout(
      "9XfmXMRPHpUOmmQ7NNi5TLLVi",
      "pMbWw2dEmgMf7OFohKypY3rKOAaMFQVgSrfcbGacUlG3OyYWVm",
      "1605273662-NNsIa5ozIApGNb1wSKQU1FqbLA38GYhMB5W5y16",
      "ngYGHxb2aqJNYUJumR551hbxP9fEDfZlVvUqPOITHEHxA"
    );

    // attach the tweet spout to the topology - parallelism of 1
    builder.setSpout("tweet-spout", tweetSpout, 1);

    // attach the parse tweet bolt using shuffle grouping
    //builder.setBolt("parse-tweet-bolt", new ParseTweetBolt(), 10).shuffleGrouping("tweet-spout");

    //************* replace with URLBolt emitting text using shuffle grouping
    builder.setBolt("python-URL-bolt", new URLBolt(), 10).shuffleGrouping("tweet-spout");


    //************* replace Java ParseTweetBolt with Java/Python SplitSentence
    builder.setBolt("python-split-sentence", new SplitSentence(), 10).shuffleGrouping("python-URL-bolt");

    // attach the count bolt using fields grouping - parallelism of 15
    //builder.setBolt("count-bolt", new CountBolt(), 15).fieldsGrouping("parse-tweet-bolt", new Fields("tweet-word"));

    //************* replace Java "parse-tweet-bolt" with Java/Python "python-split-sentence"
    builder.setBolt("count-bolt", new CountBolt(), 15).fieldsGrouping("python-split-sentence", new Fields("word"));

    // attach the report bolt using global grouping - parallelism of 1
    builder.setBolt("report-bolt", new ReportBolt(), 1).globalGrouping("count-bolt");

    // create the default config object
    Config conf = new Config();

    // set the config in debugging mode
    conf.setDebug(true);

    if (args != null && args.length > 0) {

      // run it in a live cluster

      // set the number of workers for running all spout and bolt tasks
      conf.setNumWorkers(3);

      // create the topology and submit with config
      StormSubmitter.submitTopology(args[0], conf, builder.createTopology());

    } else {

      // run it in a simulated local cluster

      // set the number of threads to run - similar to setting number of workers in live cluster
      conf.setMaxTaskParallelism(3);

      // create the local cluster instance
      LocalCluster cluster = new LocalCluster();

      // submit the topology to the local cluster
      cluster.submitTopology("tweet-word-count", conf, builder.createTopology());

      // let the topology run for 1000*30 seconds. note topologies never terminate!
      Utils.sleep(1000*30000);

      // now kill the topology
      cluster.killTopology("tweet-word-count");

      // we are done, so shutdown the local cluster
      cluster.shutdown();
    }
  }
}
