Homework 6
=====

#### Homework 6.5

In this homework you will build a small replica set on your own computer. 
We will check that it works with validate.py, which you should download from the Download Handout link.

Create three directories for the three mongod processes. On unix, this could be done as follows:
```
$ mkdir -p data/rs1 data/rs2 data/rs3
```

Now start three mongo instances as follows. 
```
$ sudo service mongodb stop

$ mongod --replSet m101 --logpath "1.log" --dbpath /data/rs1 --port 27017 --smallfiles --oplogSize 64 --fork
$ mongod --replSet m101 --logpath "2.log" --dbpath /data/rs2 --port 27018 --smallfiles --oplogSize 64 --fork
$ mongod --replSet m101 --logpath "3.log" --dbpath /data/rs3 --port 27019 --smallfiles --oplogSize 64 --fork         
```

Now you will create the replica set. Type the following commands into the mongo shell:
```
> config = { _id: "m101", members:[
           { _id : 0, host : "localhost:27017"},
           { _id : 1, host : "localhost:27018"},
           { _id : 2, host : "localhost:27019"}]};
           
> rs.initiate(config);
```

At this point, the replica set should be coming up. You can type
```
rs.status()
```
to see the state of replication. 

Now run validate.py to confirm that it works.
```
python validate.py
```
