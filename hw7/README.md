Homework 7
=====

#### Homework 7.1

Enron was an American corporation that engaged in a widespread accounting fraud and subsequently failed. 

In this dataset, each document is an email message. Like all Email messages, 
there is one sender but there can be multiple recipients. 

Construct a query to calculate the number of messages sent by Andrew Fastow, CFO, to Jeff Skilling, the president. Andrew Fastow's email addess was andrew.fastow@enron.com. Jeff Skilling's email was jeff.skilling@enron.com. 

For reference, the number of email messages from Andrew Fastow to John Lavorato (john.lavorato@enron.com) was 1. 
```
> use enron
> db.messages.aggregate([
    {$match : {"headers.From" : "andrew.fastow@enron.com"}},
    {$match : {"headers.To"   : "jeff.skilling@enron.com"}},
    {$group : {_id: "", count: {$sum : 1}}}
  ])
```

#### Homework 7.2

Please use the Enron dataset you imported for the previous problem. For this question you will use the aggregation framework to figure out pairs of people that tend to communicate a lot. To do this, you will need to unwind the To list for each message. 

This problem is a little tricky because a recipient may appear more than once in the To list for a message. You will need to fix that in a stage of the aggregation before doing your grouping and counting of (sender, recipient) pairs. 
```
> db.messages.aggregate([
    {$unwind : '$headers.To'},
    {$group  : {_id : {From:'$headers.From', To:'$headers.To', mID:'$headers.Message-ID'}, double:{$sum:1}}},
    {$group  : {_id : {From:'$_id.From', To:'$_id.To'}, count:{$sum:1}}},
    {$sort   : {count : -1}},
    {$limit  : 1}
  ])
```

#### Homework 7.3

In this problem you will update a document in the messages collection to illustrate your mastery of updating documents from the shell. In fact, we've created a collection with a very similar schema to the Enron dataset, but filled instead with randomly generated data. 

Please add the email address "mrpotatohead@10gen.com" to the list of addresses in the "headers.To" array for the document with "headers.Message-ID" of "<8147308.1075851042335.JavaMail.evans@thyme>" 
```
> db.messages.update(
		{"headers.Message-ID" : "<8147308.1075851042335.JavaMail.evans@thyme>"}, 
		{ $addToSet : { "headers.To": "mrpotatohead@10gen.com" }})
```

#### Homework 7.7

You have been tasked to cleanup a photosharing database. The database consists of two collections, albums, and images. Every image is supposed to be in an album, but there are orphan images that appear in no album. Here are some example documents (not from the collections you will be downloading). 
```
> db.albums.findOne()
{
	"_id" : 67
	"images" : [
		4745,
		7651,
		15247,
		17517,
		17853,
		20529,
		22640,
		27299,
		27997,
		32930,
		35591,
		48969,
		52901,
		57320,
		96342,
		99705
	]
}

> db.images.findOne()
{ "_id" : 99705, "height" : 480, "width" : 640, "tags" : [ "dogs", "kittens", "work" ] }
```

From the above, you can conclude that the image with _id = 99705 is in album 67. It is not an orphan.

Your task is to write a program to remove every image from the images collection that appears in no album. Or put another way, if an image does not appear in at least one album, it's an orphan and should be removed from the images collection. 

Start by using mongoimport to import your albums.json and images.json collections.

When you are done removing the orphan images from the collection, there should be 89,737 documents in the images collection. To prove you did it correctly, what are the total number of images with the tag 'sunrises" after the removal of orphans? As as a sanity check, there are 49, 887 images that are tagged 'sunrises' before you remove the images. 
```
$ mongoimport -d photosharing -c albums --drop < albums.json
$ mongoimport -d photosharing -c images --drop < images.json
$ mongo

> use photosharing
> db.images.count()
100000

> db.images.aggregate([{$match: {tags:"sunrises"}},{$group: {_id:"", total: {$sum: 1}}}])
{ "result" : [ { "_id" : "", "total" : 49887 } ], "ok" : 1 }

> db.albums.ensureIndex({images:1})
```
