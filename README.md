MongoDB for Java Developers
=====

Week 1: Introduction & Overview

Week 2: CRUD (Creating, Reading and Updating Data)

Week 3: Schema Design

Week 4: Performance

Week 5: Aggregation Framework 

Week 6: Application Engineering

Week 7: Case Studies 

### <a href = "http://docs.mongodb.org/manual/tutorial/install-mongodb-on-ubuntu/">Install MongoDB on Ubuntu</a>

The Ubuntu package management tool (i.e. dpkg and apt) ensure package consistency and authenticity by requiring that distributors sign packages with GPG keys. Issue the following command to import the MongoDB public GPG Key:

```
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 7F0CEB10
```
Create a /etc/apt/sources.list.d/mongodb.list file using the following command.
```
echo 'deb http://downloads-distro.mongodb.org/repo/ubuntu-upstart dist 10gen' | sudo tee /etc/apt/sources.list.d/mongodb.list
```
Now issue the following command to reload your repository:
```
sudo apt-get update
```

#### Install Packages

Issue the following command to install the latest stable version of MongoDB:
```
sudo apt-get install mongodb-10gen
```
When this command completes, you have successfully installed MongoDB! Continue for configuration and start-up suggestions.

#### Manage Installed Versions

You can use the mongodb-10gen package to install previous versions of MongoDB. To install a specific release, append the version number to the package name, as in the following example:
```
apt-get install mongodb-10gen=2.2.3
```
This will install the 2.2.3 release of MongoDB. You can specify any available version of MongoDB; however apt-get will upgrade the mongodb-10gen package when a newer version becomes available. Use the following pinning procedure to prevent unintended upgrades.

To pin a package, issue the following command at the system prompt to pin the version of MongoDB at the currently installed version:
```
echo "mongodb-10gen hold" | sudo dpkg --set-selections
```

#### Run MongoDB

The MongoDB instance stores its data files in the /var/lib/mongo and its log files in /var/log/mongo, and run using the mongod user account. If you change the user that runs the MongoDB process, you must modify the access control rights to the /var/lib/mongo and /var/log/mongo directories.

##### Start MongoDB
You can start the mongod process by issuing the following command:
```
sudo service mongodb start
```
You can verify that mongod has started successfully by checking the contents of the log file at /var/log/mongodb/mongodb.log.
##### Stop MongoDB
As needed, you may stop the mongod process by issuing the following command:
```
sudo service mongodb stop
```
##### Restart MongoDB
You may restart the mongod process by issuing the following command:
```
sudo service mongodb restart
```
