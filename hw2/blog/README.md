Homework 2.3
=====

The project roughly follows the model/view/controller paradigm. 
BlogController.java is the controller and the model. The templates comprise the view. 
Here is a description of the directories and files.
```
/src/main/java/course - contains source code
/src/main/resources/freemarker - contains templates
run.sh - the mvn command that starts it running if you don't want to use an IDE
validate.py - an obfuscated python script that you will run in order to validate that your blog is working.
```
If everything is working properly, you should be able to start the blog by typing either:
```
mvn compile exec:java -Dexec.mainClass=course.BlogController
```
or
```
bash run.sh
```
If you goto http://localhost:8082 you should see a message “this is a placeholder for the blog” 

Here are some URLs that must work when you are done. 
```
http://localhost:8082/signup
http://localhost:8082/login
http://localhost:8082/logout
```

When you login or sign-up, the blog will redirect to 
```
http://localhost:8082/welcome
```
and that must work properly, welcoming the user by username.

We have removed four Java statements from UserDAO and marked the areas where you need to work with XXX. 
You should not need to touch any other code. 
The java statements that you are going to add will add a new user upon sign-up and validate a login by retrieving the right user document. 

The blog stores its data in the blog database in two collections, users and sessions. 
Here are two example docs for a username ‘erlichson’ with password ‘fubar’. 
You can insert these if you like, but you don’t need to. 
```
> db.users.find()
{ "_id" : "erlichson", "password" : "VH9IFu+/vUNSKTzZsFZEOsK1,-1924261330" }

> db.sessions.find()
{ "_id" : "AN4M7warH+fdKOszU8qnd2Hmfn8JZFFZ9sff4zcPRpw=", "username" : "erlichson" }
 
```
