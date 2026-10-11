package org.example.main;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.ToLongFunction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecursivePatternMatcher<T>{
    private final T[] input;
    private final Consumer<Integer> printCallback;
    private final BiConsumer<int[], Integer> repeatCallback;
    private final ToLongFunction<T> hashFunction;
    private final BiPredicate<T, T> equalsFunction;
    private final int[] symbols;
    private final boolean[] duplicates;

    public RecursivePatternMatcher(T[] input, Consumer<Integer> printCallback, BiConsumer<int[], Integer> repeatCallback, ToLongFunction<T> hashFunction, BiPredicate<T, T> equalsFunction){
        this.input = input;
        this.printCallback = printCallback;
        this.repeatCallback = repeatCallback;
        this.hashFunction = hashFunction;
        this.equalsFunction = equalsFunction;
        this.symbols = new int[input.length];
        this.duplicates = this.buildSymbols();
    }

    public void process(){
        this.scanCycle(0, this.input.length);
    }

    private boolean[] buildSymbols(){
        Map<Long, List<Integer>> buckets = new HashMap<>(this.input.length*4/3 + 1);
        List<T> representatives = new ArrayList<>();
        int[] representativeIndices = new int[this.input.length];
        boolean[] duplicates = new boolean[this.input.length];

        for(int i=0; i < this.input.length; i++){
            T value = this.input[i];

            long hash = value == null ? 0L : this.hashFunction.applyAsLong(value);
            List<Integer> bucket = buckets.computeIfAbsent(hash, k -> new ArrayList<>());
            int symbol = -1;

            for(int candidate : bucket){
                if(this.equalsFunction.test(value, representatives.get(candidate))){
                    duplicates[i] = true;
                    duplicates[representativeIndices[candidate]] = true;
                    symbol = candidate;
                    break;
                }
            }

            if(symbol == -1){
                symbol = representatives.size();
                representatives.add(value);
                representativeIndices[symbol] = i;
                bucket.add(symbol);
            }

            this.symbols[i] = symbol;
        }

        return duplicates;
    }

    private void scanCycle(int from, int to){
        int pos = from;

        while(pos < to){
            if(!this.duplicates[pos]){
                this.printCallback.accept(pos);
                pos++;
                continue;
            }

            int[] repetition = this.findCycle(pos, to);
            if(repetition == null){
                this.printCallback.accept(pos);
                pos++;
                continue;
            }

            this.scanCycle(pos, pos + repetition[0]);
            this.repeatCallback.accept(new int[]{pos, pos + repetition[0] - 1}, repetition[1]);

            pos += repetition[0]*repetition[1];
        }
    }

    private int[] findCycle(int from, int to){
        int n = to - from;

        if(n < 2){
            return null;
        }

        int left = 0;
        int right = 0;
        int bestSize = 0;
        int bestCount = 0;
        int bestCovered = 0;
        int[] z = new int[n];
        z[0] = n;

        for(int i=1; i <= n/2; i++){
            if(!this.duplicates[from + i - 1]){
                break;
            }

            if(i <= right){
                z[i] = Math.min(right - i + 1, z[i - left]);
            }

            while(i + z[i] < n && this.symbols[from + z[i]] == this.symbols[from + i + z[i]]){
                z[i]++;
            }

            if(z[i] >= i){
                int count = 1 + z[i]/i;
                int covered = i*count;

                if(covered > bestCovered || (covered == bestCovered && i < bestSize)){
                    bestSize = i;
                    bestCount = count;
                    bestCovered = covered;

                    if(bestCovered == n){
                        break;
                    }
                }
            }

            if(i + z[i] - 1 > right){
                left = i;
                right = i + z[i] - 1;
            }
        }

        if(bestCount >= 2){
            return new int[]{bestSize, bestCount};
        }

        return null;
    }
}