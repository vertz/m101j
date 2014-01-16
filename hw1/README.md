Homework 1
=====

#### Homework 1.1

Use mongorestore to restore the dump into your running mongod. Do this by opening a terminal window (mac) or cmd window (windows) and navigating to the directory so that the dump directory is directly beneath you. Now type
```
mongorestore dump
```

Now, using the Mongo shell, perform a findone on the collection called hw1 in the database m101. That will return one document. Please provide the value corresponding to the "answer" key from the document returned.
```
> use m101 
> var j = db.hw1.findOne() 
> j["answer"]
```

#### Homework 1.3

We want to test that you have a working JDK, that maven is installed and that you can run maven-based projects. Please install JDK 1.6 or above and maven if they are not already installed. This week's video lessons show how to install maven. 

Download hw1-3.zip from Download Handout link, uncompress it, cd into the hw1-3 directory (there should be a pom.xml file in there), and run Maven as follows:
```
mvn compile exec:java -Dexec.mainClass=com.tengen.Week1Homework3
```

#### Homework 1.4

We are now going to test that you can run a Maven-based project that depends on the MongoDB Java driver, Spark, and Freemarker. Download hw1-4.zip, uncompress it, cd to the hw-1.4 directory, and run Maven as follows:
```
mvn compile exec:java -Dexec.mainClass=com.tengen.Week1Homework4
```

Like the previous homework, it requires Maven to be installed correctly, your mongod server to be running, and that you have run mongorestore properly from HW 1.1.

If all is working correctly, there should be two lines towards the bottom that say:
```
== Spark has ignited ...
>> Listening on 0.0.0.0:4567
```

Next, open a tab in your web browser and navigate to http://localhost:4567.
```
$ curl http://localhost:4567
```

