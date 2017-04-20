package me.kowo.opencity.models;

import java.util.HashMap;
import java.util.List;


public class Info {
    /**
     * parameters : {"1":"Майстрова Ірина Анатоліївна","2":"(0522) 32-25-31","3":"NVO-1@ukr.net","4":"http://shcool1.klasna.com"}
     * accessibilities : [1,2]
     */

    private HashMap<Integer, String> parameters;
    private List<Integer> accessibilities;

    public HashMap<Integer, String> getParameters() {
        return parameters;
    }

    public Info setParameters(HashMap<Integer, String> parameters) {
        this.parameters = parameters;
        return this;
    }

    public List<Integer> getAccessibilities() {
        return accessibilities;
    }

    public Info setAccessibilities(List<Integer> accessibilities) {
        this.accessibilities = accessibilities;
        return this;
    }


}
