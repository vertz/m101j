Homework 4.3
=====

In this homework assignment you will be adding some indexes to the post collection to make the blog fast.

We have provided the full code for the blog application and you don't need to make any changes, or even run the blog. But you can, for fun.

We are also providing a patriotic (if you are an American) data set for the blog. There are 1000 entries with lots of comments and tags. You must load this dataset to complete the problem.
```
# from the mongo shell
use blog
db.posts.drop()
# from the a mac or PC terminal window
mongoimport -d blog -c posts < posts.json
```

The blog has been enhanced so that it can also display the top 10 most recent posts by tag. There are hyperlinks from the post tags to the page that displays the 10 most recent blog entries for that tag. (run the blog and it will be obvious)

Your assignment is to make the following blog pages fast:
<ul>
<li> The blog home page </li>
<li> The page that displays blog posts by tag (http://localhost:8082/tag/whatever)</li>
<li> The page that displays a blog entry by permalink (http://localhost:8082/post/permalink)</li>
</ul>

By fast, we mean that indexes should be in place to satisfy these queries such that we only need to scan the number of documents we are going to return.
To figure out what queries you need to optimize, you can read the blog.py code and see what it does to display those pages. Isolate those queries and use explain to explore.

```
# The blog home page
> db.posts.ensureIndex({date:-1})

# The page that displays blog posts by tag
> db.posts.ensureIndex({tags:1,date:-1})

# The page that displays a blog entry by permalink
> db.posts.ensureIndex({permalink:1})
```

Once you have added the indexes to make those pages fast run the following.
```
python validate.py
```
