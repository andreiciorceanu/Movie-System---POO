package main;

import actor.ActorsAwards;
import fileio.ActorInputData;
import fileio.Input;

import java.util.*;

public class Query {

    public static String awards(Input input, String sortare, List<String> cuvinte) {
        Map<String, Integer> abc = new LinkedHashMap<>();
        int verif;
        int sum = 0;
        for (ActorInputData actors : input.getActors()) {
            verif = 0;
            sum = 0;
            for (String j : cuvinte) {
                for (ActorsAwards s : actors.getAwards().keySet()) {
                    if (!(s.toString().equals(j))) {
                        verif++;
                    }
                }
            }
            if (verif == cuvinte.size()) {
                for (ActorsAwards a : actors.getAwards().keySet()) {
                    sum += actors.getAwards().get(a);
                }
                abc.put(actors.getName(), sum);
            }
        }
        Map<String, Integer> mapf = new LinkedHashMap<>();
        if (sortare.equals("asc")) {
            abc.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue((i1, i2) -> i1.compareTo(i2))
                    .thenComparing(Map.Entry.comparingByKey((s1, s2) -> s1.compareTo(s2))))
                    .forEach(element -> mapf.put(element.getKey(), element.getValue()));
        }
        if (sortare.equals("desc")) {
            abc.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                    .thenComparing(Map.Entry.comparingByKey(Comparator.reverseOrder())))
                    .forEach(element -> mapf.put(element.getKey(), element.getValue()));
        }
        String str = "Query result: []";
        for (Map.Entry<String, Integer> altmap : mapf.entrySet()) {
            str = str + altmap.getKey() + ", ";
        }
        return str;
    }

}
