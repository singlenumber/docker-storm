Real-Time Analytics with Apache Storm from ud381
=====
storm version: 1.0.2

How to run:
1. redis-server &
2. src/viz python app.py &
3. mvn clean && mvn package
4. storm jar target/*.jar udacity.storm.classname
(ex9 not working)