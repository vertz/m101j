package com.tengen;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.BasicDBList;

import java.util.ArrayList;
import java.net.UnknownHostException;

/*
 * remove the lowest homework score for each student.
 */
public class Week3Homework1 
{
    public static void main(String[] args) throws UnknownHostException 
    {
        MongoClient client = new MongoClient();
        DB db = client.getDB("school");
        DBCollection collection = db.getCollection("students");
        DBCursor cursor = collection.find();
        
        try 
        {
            while (cursor.hasNext()) 
            {
                DBObject cur = cursor.next();
                ArrayList<BasicDBObject> scores = (ArrayList) cur.get("scores");
                
                double min = Double.POSITIVE_INFINITY;
                BasicDBObject toRemove = null;
                
                for(BasicDBObject score : scores)
                {
                    if(!score.get("type").equals("homework"))
                        continue;
                    
                    double scoreVal = score.getDouble("score");
                    if( min > scoreVal )
                    {
                        min = scoreVal;
                        toRemove = score; 
                    }  
                }
                
                scores.remove(toRemove);
                BasicDBObject updateQuery = new BasicDBObject("$set", new BasicDBObject("scores", scores));
                
                collection.update(new BasicDBObject("_id", cur.get("_id")), updateQuery);
            }
        } 
        finally 
        {
            cursor.close();
        }

    }
}
