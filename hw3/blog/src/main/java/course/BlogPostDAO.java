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

package course;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBList;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.sun.org.apache.bcel.internal.generic.ACONST_NULL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BlogPostDAO {
    DBCollection postsCollection;

    public BlogPostDAO(final DB blogDatabase) {
        postsCollection = blogDatabase.getCollection("posts");
    }

    // Return a single post corresponding to a permalink
    public DBObject findByPermalink(String permalink) {
    
        DBObject post = postsCollection.findOne(new BasicDBObject("permalink", permalink));
        return post;
    }

    // Return a list of posts in descending order. Limit determines
    // how many posts are returned.
    public List<DBObject> findByDateDescending(int limit) {

        List<DBObject> posts = new ArrayList<DBObject>();
        DBCursor cursor = postsCollection.find().sort(new BasicDBObject("date", -1)).limit(limit);
        
        try 
        {
            while (cursor.hasNext()) 
            {
                posts.add(cursor.next());
            }
        } 
        finally 
        {
            cursor.close();
        }

        return posts;
    }


    public String addPost(String title, String body, List tags, String username) {

        System.out.println("inserting blog entry " + title + " " + body);

        String permalink = title.replaceAll("\\s", "_"); // whitespace becomes _
        permalink = permalink.replaceAll("\\W", ""); // get rid of non alphanumeric
        permalink = permalink.toLowerCase();

        BasicDBObject post = new BasicDBObject();
        post.put("title", title);
        post.put("author", username);
        post.put("body", body);
        post.put("permalink", permalink);
        post.put("tags", tags);
        post.put("comments", new ArrayList<String>());
        post.put("date", new Date());

        postsCollection.insert(post);

        return permalink;
    }

    // Append a comment to a blog post
    public void addPostComment(final String name, final String email, final String body,
                               final String permalink) {

        DBObject post = findByPermalink(permalink);
        BasicDBList comments = (BasicDBList) post.get("comments");
        
        BasicDBObject comment = new BasicDBObject();
        comment.put("author", name);
        comment.put("body", body);
        
        if (email != null && !email.equals("")) 
        {
            comment.put("email", email);
        } 
        
        comments.add(comment);
        
        // If set to true, creates a new document when no document matches the query criteria
        boolean upsert = true;
        
        // If set to true, updates multiple documents that meet the query criteria. 
        // If set to false, updates one document. The default value is false.
        boolean multi  = false;
        
        postsCollection.update(new BasicDBObject("permalink", permalink), post, upsert, multi);
    }


}
