package org.example.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StringList{
    private final List<String> list = new ArrayList<>();

    public StringList clone(){
        StringList copy = new StringList();
        copy.list.addAll(this.list);
        return copy;
    }

    public boolean equals(StringList stringList){
        if(this.list.size() != stringList.list.size()){
            return false;
        }

        for(int i=0; i < this.list.size(); i++){
            if(!this.list.get(i).equals(stringList.list.get(i))){
                return false;
            }
        }

        return true;
    }

    public boolean add(String value){
        int index = Collections.binarySearch(this.list, value);

        if(index >= 0){
            return false;
        }

        this.list.add(-index - 1, value);

        return true;
    }

    public boolean contains(String value){
        return Collections.binarySearch(this.list, value) >= 0;
    }

    public int size(){
        return this.list.size();
    }

    public String get(int index){
        return this.list.get(index);
    }

    public String[] getArray(){
        return this.list.toArray(new String[0]);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("[");

        for(int i=0; i < this.list.size(); i++){
            if(i > 0){
                sb.append(", ");
            }
            sb.append('"').append(this.list.get(i)).append('"');
        }
        sb.append(']');

        return sb.toString();
    }
}