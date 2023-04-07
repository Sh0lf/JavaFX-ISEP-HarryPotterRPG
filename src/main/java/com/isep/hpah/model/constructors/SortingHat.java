package com.isep.hpah.model.constructors;

import lombok.*;

import java.util.HashMap;

public class SortingHat {

    //creating hashmap based on responses
    @Getter
    private final static HashMap <Integer, House> quesRes = new HashMap<>();
    static {
        //keys and values = (finalRes as String + House."house">
        quesRes.put(11, House.GRYFFINDOR);
        quesRes.put(12, House.HUFFLEPUFF);
        quesRes.put(21, House.RAVENCLAW);
        quesRes.put(22, House.SLYTHERIN);
    }


    public House getResHouse(int ind){
        return quesRes.get(ind);
    }
}
