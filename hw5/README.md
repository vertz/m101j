Homework 5
=====

#### Homework 5.1

In this assignment you will use the aggregation framework to find the most frequent author of comments on your blog. We will be using the same dataset as last week. 

Start by downloading the posts.json dataset from last week's homework. Then import into your blog database as follows:
```
mongoimport -d blog -c posts --drop < posts.json
```

Now use the aggregation framework to calculate the author with the greatest number of comments. 

To help you verify your work before submitting, the author with the least comments is Mariela Sherer and she commented 387 times. 

```
> db.posts.aggregate([{$unwind: "$comments"},
                      {$group : { _id: "$comments.author", "n_comments": {$sum:1}}},
                      {$sort  : { n_comments: -1 }},
                      {$limit : 1 }])
```

#### Homework 5.2

```
mongoimport -d test -c zips --drop < zips.json
```

Please calculate the average population of cities in California (abbreviation CA) and New York (NY) (taken together) with populations over 25,000. 

For this problem, assume that a city name that appears in more than one state represents two separate cities. 

Please round the answer to a whole number. 
Hint: The answer for CT and NJ is 49749. 

Please note:
<ul>
<li>One zip code may cover cities in different states.</li>
<li>Different states might have the same city name.</li>
<li>A city might have multiple zip codes.</li>
</ul>

```                
> db.zips.aggregate([
	{$match: {$or : [{state:'CA'},{state:'NY'}]}},
	{$group: {_id : {city:'$city', state:'$state'}, pop:{$sum:'$pop'}}},
	{$match: {pop : {$gt : 25000}}},
	{$group: {_id :null, avg_pop:{$avg:'$pop'}}}
  ])                      
```

#### Homework 5.3

In this problem you will be analyzing a dataset of student grades. Please import grades_5-3.js into a database and collection of your choice. 
```
mongoimport -d test -c grades --drop < grades_5-3.js
```

The documents look like this:
```
{
	"_id" : ObjectId("50b59cd75bed76f46522c392"),
	"student_id" : 10,
	"class_id" : 5,
	"scores" : [
		{
			"type" : "exam",
			"score" : 69.17634380939022
		},
		{
			"type" : "quiz",
			"score" : 61.20182926719762
		},
		{
			"type" : "homework",
			"score" : 73.3293624199466
		},
		{
			"type" : "homework",
			"score" : 15.206314042622903
		},
		{
			"type" : "homework",
			"score" : 36.75297723087603
		},
		{
			"type" : "homework",
			"score" : 64.42913107330241
		}
	]
}
```

There are documents for each student (student_id) across a variety of classes (class_id). Note that not all students in the same class have the same exact number of assessments. Some students have three homework assignments, etc. 

Your task is to calculate the class with the best average student performance. This involves calculating an average for each student in each class of all non-quiz assessments and then averaging those numbers to get a class average. To be clear, each student's average includes only exams and homework grades. <b>Don't include their quiz scores in the calculation.</b> 

What is the class_id which has the highest average student perfomance? 

Hint/Strategy: You need to group twice to solve this problem. You must figure out the GPA that each student has achieved in a class and then average those numbers to get a class average. After that, you just need to sort. The hardest class is class_id=2. Those students achieved a class average of 37.6 

What is the class_id with the highest average student average.
```
> db.grades.aggregate([
	{$unwind: '$scores'},
	{$match : {'scores.type':{$ne:'quiz'}}},
	{$group : {_id : {student:'$student_id', class:'$class_id'}, gpa:{$avg:'$scores.score'}}},
	{$group : {_id:'$_id.class', class_gpa:{$avg:'$gpa'}}},
	{$sort  : {class_gpa : -1}},
	{$limit : 1}
  ])  
```

#### Homework 5.4

In this problem you will calculate the number of people who live in a zip code in the US where the city starts with a digit. We will take that to mean they don't really live in a city. Once again, you will be using the zip code collection you imported earlier. 

The project operator can extract the first digit from any field. For example, to extract the first digit from the city field, you could write this query:
```
> db.zips.aggregate([
    {$project: 
        { 
            first_char: {$substr : ["$city",0,1]},
        }
    }
])
```

Using the aggregation framework, calculate the sum total of people who are living in a zip code where the city starts with a digit. Choose the answer below. 

Note that you will need to probably change your projection to send more info through than just that first character. Also, you will need a filtering step to get rid of all documents where the city does not start with a digital (0-9).

```
> db.zips.aggregate([
	{$project: {first_char: {$substr : ["$city",0,1]}, pop: 1}},
	{$match  : {first_char: {$regex : /[0-9]/}}},
	{$group  : {_id: null, sum: {$sum : '$pop'}}}
])
```
