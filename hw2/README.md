Homework 2
=====

#### Homework 2.1

In this problem, you will be using a collection of student scores that is similar to what we used in the lessons. Please download the grades.js file and import it into your local mongo database as follows:
```
mongoimport -d students -c grades < grades.js
```

The dataset contains 4 scores for 200 students.
First, let’s confirm your data is intact; the number of documents should be 800.

```
> use students
> db.grades.count()
800
```

This next query, which uses the aggregation framework that we have not taught yet, will tell you the student_id with the highest average score:
```
> db.grades.aggregate({'$group':{'_id':'$student_id', 'average':{$avg:'$score'}}},
{'$sort':{'average':-1}}, {'$limit':1})
```

Note: Aggregation requires mongodb 2.2 or above.
The answer, deep in the resulting document, should be student_id 164 with an average of approximately 89.3.

Now it’s your turn to analyze the data set. Find all exam scores greater than or equal to 65. and sort those scores from lowest to highest.

```
> db.grades.find({score : {$gte : 65}, type : "exam"}).sort({score : 1})
```

#### Homework 2.2

Write a program in the language of your choice that will remove the grade of type "homework" with the lowest score for each student from the dataset that you imported in HW 2.1. Since each document is one grade, it should remove one document per student.

Hint/spoiler: If you select homework grade-documents, sort by student and then by score, you can iterate through and find the lowest score for each student by noticing a change in student id. As you notice that change of student_id, remove the document.

To confirm you are on the right track, here are some queries to run after you process the data with the correct answer shown:

Let us count the number of grades we have:
```
> db.grades.count() 
600
```

Now let us find the student who holds the 101st best grade across all grades:
```
> db.grades.find().sort({'score':-1}).skip(100).limit(1)
{ "_id" : ObjectId("50906d7fa3c412bb040eb709"), "student_id" : 100, "type" : "homework", "score" : 88.50425479139126 }
```

Now let us sort the students by student_id, score and see what the top five docs are:
```
> db.grades.find({},{'student_id':1, 'type':1, 'score':1, '_id':0}).sort({'student_id':1, 'score':1, }).limit(5)
{ "student_id" : 0, "type" : "quiz", "score" : 31.95004496742112 }
{ "student_id" : 0, "type" : "exam", "score" : 54.6535436362647 }
{ "student_id" : 0, "type" : "homework", "score" : 63.98402553675503 }
{ "student_id" : 1, "type" : "homework", "score" : 44.31667452616328 }
{ "student_id" : 1, "type" : "exam", "score" : 74.20010837299897 }
```

To verify that you have completed this task correctly, provide the identity of the student with the highest average in the class with following query that uses the aggregation framework. The answer will appear in the _id field of the resulting document.

```
> db.grades.aggregate({'$group':{'_id':'$student_id', 'average':{$avg:'$score'}}}, 
{'$sort':{'average':-1}}, {'$limit':1})
```

#### <a href="http://stackoverflow.com/questions/19527564/mongo-couldnt-connect-to-server-127-0-0-127017-at-src-mongo-shell-mongo-js14">Error: couldn't connect to server 127.0.0.1:27017 src/mongo/shell/mongo.js</a>

Most probably a problem with mongo lock. solved with the following commands:
```
sudo rm /var/lib/mongodb/mongod.lock
sudo service mongodb restart
```
