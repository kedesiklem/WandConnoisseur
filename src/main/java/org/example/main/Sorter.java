// taken from https://www.lua.org/source/5.1/ltablib.c.html
package org.example.main;

import java.util.function.ToIntFunction;
import java.util.IdentityHashMap;
import java.util.List;

class Sorter{
    private static <T> int compare(T a, T b, ToIntFunction<T> getId){
        return Integer.compare(getId.applyAsInt(a), getId.applyAsInt(b));
    }

    private static <T> void swap(List<T> list, int i, int j){
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    public static <T> void sort(List<T> list, int start, int end, ToIntFunction<T> getId){
        while(start < end){
            if(compare(list.get(end), list.get(start), getId) < 0){
                swap(list, start, end);
            }
            if(end - start == 1){
                break;
            }

            int i = (start + end)/2;

            if(compare(list.get(i), list.get(start), getId) < 0){
                swap(list, i, start);
            }else if(compare(list.get(end), list.get(i), getId) < 0){ // a[end] < a[i]?
                swap(list, i, end);
            }
            if(end - start == 2){
                break;
            }

            T pivot = list.get(i);
            swap(list, i, end - 1);
            i = start;
            int j = end - 1;

            while(true){
                while(compare(list.get(++i), pivot, getId) < 0){}
                while(compare(pivot, list.get(--j), getId) < 0){}

                if(j < i){
                    break;
                }

                swap(list, i, j);
            }

            swap(list, end - 1, i);

            if(i - start < end - i){
                j = start;
                start = i + 1;
                i--;
            }else{
                j = i + 1;
                i = end;
                end = j - 2;
            }

            sort(list, j, i, getId);
        }
    }

    public static <T> void sort(List<T> list, ToIntFunction<T> getId){
        sort(list, 0, list.size() - 1, getId);
    }

    public static <T> void sort(List<T> list, List<T> target){
        IdentityHashMap<T, Integer> ids = new IdentityHashMap<>(target.size());

        for(int i=0; i < target.size(); i++){
            ids.put(target.get(i), i);
        }

        sort(list, card -> ids.getOrDefault(card, 0));
    }
}