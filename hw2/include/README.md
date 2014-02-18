Include 
=====

##### Java

```
/src/main/java/com/tengen/DocumentRepresentationTest.java  
/src/main/java/com/tengen/DotNotationTest.java  
/src/main/java/com/tengen/FieldSelectionTest.java  
/src/main/java/com/tengen/FindAndModifyTest.java  
/src/main/java/com/tengen/FindCriteriaTest.java  
/src/main/java/com/tengen/FindTest.java  
/src/main/java/com/tengen/InsertTest.java  
/src/main/java/com/tengen/SortSkipLimitTest.java  
/src/main/java/com/tengen/UpdateRemoveTest.java
```
to run any of them just replace the XXXX with the file name (without '.java')
  
```
mvn compile exec:java -Dexec.mainClass=com.tengen.XXXX
```

##### Shell

Insert a document into the fruit collection with the attributes of "name" being "apple", "color" being "red", and "shape" being "round". Use the "insert" method.
```
> db.fruit.insert( { "name" : "apple", "color" : "red", "shape" : "round" } )
```

Use findOne on the collection users to find one document where the key username is "dwight", and retrieve only the key named email.
```
> db.users.findOne( { "username" : "dwight" } , { "_id" : false, "email" : true } )
```

Supposing a scores collection similar to the one presented, how would you find all documents with an essay score equal to 50 and only retrieve the student field?
```
> db.scores.find( { "type" : "essay", "score" : 50} , { "_id" : false, "student" : true } )
```
```
$gt - great than 
$lt - less than 
$lte / gte - same as above but with equal
```

find documents with a score between 50 and 60, inclusive?
```
> db.scores.find({ score : { $gte : 50 , $lte : 60 } } )
```

Which of the following will find all users with name between "F" and "Q"?
```
> db.users.find( { name : { $gte : "F" , $lte : "Q" } } );
> db.users.find( { name : { $lte : "Q" , $gte : "F" } } );
```

Write a query that retrieves documents from a users collection where the name has a "q" in it, and the document has an email field.
```
> db.users.find( { name : {$regex : "q"} , email : {$exists : true}} )
```

How would you find all documents in the scores collection where the score is less than 50 or greater than 90?
```
> db.scores.find( { $or : [ {score : {$lt : 50}} , {score : {$gt : 90}} ] } )
```

Suppose a simple e-commerce product catalog called catalog with documents that look like this:
```
{ product : "Super Duper-o-phonic", 
  price : 100000000000,
  reviews : [ { user : "fred", comment : "Great!" , rating : 5 },
              { user : "tom" , comment : "I agree with Fred, somewhat!" , rating : 4 } ],
  ... }
```

Write a query that finds all products that cost more than 10,000 and that have a rating of 5 or better.
```
> db.catalog.find( { price : { $gt : 10000 } , "reviews.rating" : { $gte : 5 }} )
```

Recall the documents in the scores collection:
```
{
	"_id" : ObjectId("50844162cb4cf4564b4694f8"),
	"student" : 0,
	"type" : "exam",
	"score" : 75
}
```

Write a query that retrieves exam documents, sorted by score in descending order, skipping the first 50 and showing only the next 20.
```
> db.scores.find( { type : "exam" } ).sort( { score : -1 } ).skip(50).limit(20)
```

Give every document with a score less than 70 an extra 20 points. 
```
> db.scores.update({score : {$lt : 70}},{$inc : {score : 20}},{multi : true})
```

Delete every document with a score of less than 60.
```
> db.scores.remove({score : {$lt : 60}})
```

How would you count the documents in the scores collection where the type was "essay" and the score was greater than 90?
```
> db.scores.count({ type:"essay", score:{$gt:90}});
```

For the users collection, the documents are of the form
```
{
	"_id" : "myrnarackham",
	"phone" : "301-512-7434",
	"country" : "US"
}
```

Please set myrnarackham's country code to "RU" but leave the rest of the document (and the rest of the collection) unchanged. 
```
> db.users.update({_id : "myrnarackham"},{$set : {country : "RU"}})
```
