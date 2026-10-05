package com.tutort.assignments.Enzo;

import java.util.HashMap;

public class InternalWorkingOfHashMap {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("enzo", 100);
        map.put("ab", 200);
        System.out.println(map.hashCode());
        System.out.println(map.size());
    }
}
