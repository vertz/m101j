Homework 4
=====

#### Homework 4.0

Please provide the mongo shell command to add an index to a collection named students, having the index key be class, student_name.
```
> db.students.ensureIndex({class:1,student_name:1})
```

```
db.system.indexes.find()    # indexes inside db
db.collection.getIndexes()  # indexes inside collection
```

Please provide the mongo shell command to add a unique index to the collection students on the keys student_id, class_id.
```
> db.students.ensureIndex({student_id:1,class_id:1},{unique:true})
```

```
db.collection.stats()  # information about the collection
db.collection.totalIndexSize()  # size of index in memory
```

Suppose you have a 2D geospatial index defined on the key location in the collection places. Write a query that will find the closest three places (the closest three documents) to the location 74, 140.
```
> db.places.find({location : {$near : [74,140]}}).limit(3)
```

```
db.system.profile
db.setProfilingLevel(level, slowms)
db.getProfilingStatus()
```

Write the query to look in the system profile collection for all queries that took longer than one second, ordered by timestamp descending.
```
> db.system.profile.find({millis:{$gt:1000}}).sort({ts:-1})
```

#### Homework 4.1

Suppose you have a collection with the following indexes:
```
> db.products.getIndexes()
[
	{
		"v" : 1,
		"key" : {
			"_id" : 1
		},
		"ns" : "store.products",
		"name" : "_id_"
	},
	{
		"v" : 1,
		"key" : {
			"sku" : 1
		},
                "unique" : true,
		"ns" : "store.products",
		"name" : "sku_1"
	},
	{
		"v" : 1,
		"key" : {
			"price" : -1
		},
		"ns" : "store.products",
		"name" : "price_-1"
	},
	{
		"v" : 1,
		"key" : {
			"description" : 1
		},
		"ns" : "store.products",
		"name" : "description_1"
	},
	{
		"v" : 1,
		"key" : {
			"category" : 1,
			"brand" : 1
		},
		"ns" : "store.products",
		"name" : "category_1_brand_1"
	},
	{
		"v" : 1,
		"key" : {
			"reviews.author" : 1
		},
		"ns" : "store.products",
		"name" : "reviews.author_1"
	}
```

The following queries can utilize an index.
```
> db.products.find({'brand':"GE"}).sort({price:1})
> db.products.find({$and:[{price:{$gt:30}},{price:{$lt:50}}]}).sort({brand:1})
```

#### Homework 4.2

Suppose you have a collection called tweets whose documents contain information about the created_at time of the tweet and the user's followers_count at the time they issued the tweet. What can you infer from the following explain output?
```
db.tweets.find({"user.followers_count":{$gt:1000}}).sort({"created_at" : 1 }).limit(10).skip(5000).explain()
{
        "cursor" : "BtreeCursor created_at_-1 reverse",
        "isMultiKey" : false,
        "n" : 10,
        "nscannedObjects" : 46462,
        "nscanned" : 46462,
        "nscannedObjectsAllPlans" : 49763,
        "nscannedAllPlans" : 49763,
        "scanAndOrder" : false,
        "indexOnly" : false,
        "nYields" : 0,
        "nChunkSkips" : 0,
        "millis" : 205,
        "indexBounds" : {
                "created_at" : [
                        [
                                {
                                        "$minElement" : 1
                                },
                                {
                                        "$maxElement" : 1
                                }
                        ]
                ]
        },
        "server" : "localhost.localdomain:27017"
}
```

```
> This query performs a collection scan.
> The query uses an index to determine the order in which to return result documents.
> The query visits 46462 documents.
```

#### Homework 4.4

In this problem you will analyze a profile log taken from a mongoDB instance. To start, please download sysprofile.json and import it with the following command:
```
mongoimport -d m101 -c profile < sysprofile.json
```

Now query the profile data, looking for all queries to the students collection in the database school2, sorted in order of decreasing latency. What is the latency of the longest running operation to the collection, in milliseconds?
```
> db.profile.find({'ns':'school2.students'}).sort({millis: -1}).limit(1)
```

#### <a href="http://stackoverflow.com/questions/19527564/mongo-couldnt-connect-to-server-127-0-0-127017-at-src-mongo-shell-mongo-js14">Error: couldn't connect to server 127.0.0.1:27017 src/mongo/shell/mongo.js</a>

Most probably a problem with mongo lock. solved with the following commands:
```
sudo rm /var/lib/mongodb/mongod.lock
sudo service mongodb restart
```
