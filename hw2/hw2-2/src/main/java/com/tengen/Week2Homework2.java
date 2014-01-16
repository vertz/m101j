package com.tengen;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

/*
 * remove the grade of type "homework" with the lowest score for each student from the dataset
 */
public class Week2Homework2 
{
    public static void main(String[] args) throws UnknownHostException 
    {
        MongoClient client = new MongoClient();
        DB db = client.getDB("students");
        DBCollection collection = db.getCollection("grades");

        DBCursor cursor = collection.find(new BasicDBObject("type", "homework"));
        cursor.sort(new BasicDBObject("student_id", 1).append("score", 1));

        Integer currId = null;
        
        try 
        {
            while (cursor.hasNext()) 
            {
                DBObject cur = cursor.next();
                Integer sid  = (Integer)cur.get("student_id");
                
                if(currId == null ||  !currId.equals(sid))
                {
                    currId = sid;
                    collection.remove(new BasicDBObject("_id", cur.get("_id")));
                }
            }
        } 
        finally 
        {
            cursor.close();
        }

    }
}
