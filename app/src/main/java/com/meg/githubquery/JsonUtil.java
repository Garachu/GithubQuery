package com.meg.githubquery;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by root on 9/23/17.
 */

public class JsonUtil {

    /*

     {
        "name":{
            "firstName": "John",
            "lastName": "Doe"
        },
        "title":"Missing Person"
     }

     */

    void processJSON(String json) throws JSONException {
        //Initialize JSOObject from JSON string
        JSONObject contact = new JSONObject(json);

        //Get the name into a JSONObject
        JSONObject name = contact.getJSONObject("name");

        //Get firstName and lastName
        String firstName = name.getString("firstName");
        String lastName = name.getString("lastName");

        //Get title from the main JSONObject
        String title = contact.getString("title");
    }

}
