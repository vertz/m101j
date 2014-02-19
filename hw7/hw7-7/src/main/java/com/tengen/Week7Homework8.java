/*
 * Copyright (c) 2008 - 2013 10gen, Inc. <http://10gen.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.tengen;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.net.UnknownHostException;

public class Week7Homework8 
{
    public static void main(String[] args) throws UnknownHostException 
    {
        MongoClient client =  new MongoClient(new MongoClientURI("mongodb://localhost"));
        DB db = client.getDB("test");
        DBCollection animals = db.getCollection("animals");

        BasicDBObject animal = new BasicDBObject("animal", "monkey");

        animals.insert(animal);
        animal.removeField("animal");
        animal.append("animal", "cat");
        animals.insert(animal);
        animal.removeField("animal");
        animal.append("animal", "lion");
        animals.insert(animal);
        
        long count = animals.count();
        System.out.println("number of animals: " + count);    
    }
}

