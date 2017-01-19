import storm

class SplitSentenceBolt(storm.BasicBolt):
  def process(self, tup):
    if len(tup.values) != 0:
      words = tup.values[0].split(" ")
      if len(words) != 0:
        for word in words:
          storm.emit([word])

SplitSentenceBolt().run()