FROM ubuntu:14.04.4
#MAINTAINER Xiaomeng Yi "yixiaomeng@gmail.com"

RUN apt-get update -y
RUN apt-get -y install default-jdk maven vim zookeeper zookeeperd redis-server \
    python-software-properties python-pip python python-bs4 tree wget

RUN pip install flask redis

RUN mkdir -p /opt/storm
RUN wget http://www-us.apache.org/dist/storm/apache-storm-1.0.2/apache-storm-1.0.2.tar.gz
RUN tar xvzf apache-storm-1.0.2.tar.gz
RUN mv apache-storm-1.0.2 /opt/storm/
RUN chmod +x /opt/storm/apache-storm-1.0.2/bin/storm
RUN ln -s /opt/storm/apache-storm-1.0.2/bin/storm /usr/bin/storm
RUN rm apache-storm-1.0.2.tar.gz

