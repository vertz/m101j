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
 * remove every image from the images collection that appears in no album.
 */
public class Week7Homework7 
{
    public static void main(String[] args) throws UnknownHostException 
    {
        // establish a connection to the database
        MongoClient client = new MongoClient();
        
        // get a handle to the photosharing database
        DB db = client.getDB("photosharing");
        DBCollection albums = db.getCollection("albums");
        DBCollection images = db.getCollection("images");
        
        BasicDBObject query  = new BasicDBObject();
        BasicDBObject fields = new BasicDBObject("_id", 1);
        
        DBCursor cursor = images.find(query, fields);
        
        long image_id;
        long n_albums;
        
        try 
        {
            while (cursor.hasNext()) 
            {
                DBObject doc = cursor.next();
                
                image_id = ((Integer) doc.get("_id")).longValue();
                n_albums = albums.find(new BasicDBObject("images", image_id)).count();
                
                if(n_albums < 1)
                {
                    images.remove(new BasicDBObject("_id", image_id));    
                }
            }
        } 
        finally 
        {
            cursor.close();
        }

    }
}
