/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.util.common;

import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 *
 * @author rjanumpally
 */
@Component
public class JsonUtil {

    /**
     * Produce a JSONArray containing the names of the elements of this
     * JSONObject.
     *
     * @return A JSONArray containing the key strings, or null if the JSONObject
     * is empty.
     */
    public JSONArray names(JSONObject json) {
        JSONArray ja = new JSONArray();
        Iterator<String> keys = json.keys();
        while (keys.hasNext()) {
            ja.put(keys.next());
        }
        return ja.length() == 0 ? null : ja;
    }

    public List<String> getValuesForGivenKey(String jsonArrayStr, String key) {
        JSONArray jsonArray = new JSONArray(jsonArrayStr);
        return null;/*IntStream.range(0, jsonArray.length())
                .mapToObj(index -> ((JSONObject) jsonArray.get(index)).optString(key))
                .collect(Collectors.toList());*/
    }

    public String getValueForGivenKey(JSONObject record, String key) {
        String result = "";
        try {
            Iterator<String> keys = record.keys();
            while (keys.hasNext()) {
                if (key.equalsIgnoreCase(keys.next())) {
                    result = record.getString(key);
                    break;
                }
            }
        } catch (Exception e) {
            return "";
        }
        return result;
    }
}
