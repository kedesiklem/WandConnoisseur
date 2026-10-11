// taken from https://github.com/love2d/love/blob/main/src/modules/math/RandomGenerator.cpp to simulate similar RNG
package org.example.main;

public class RandomGenerator{
    private long state;
    private double lastRandomNormal = Double.POSITIVE_INFINITY;

    public RandomGenerator(){
        long seed = 0x0139408D_CBBF7A44L;
        setSeed(seed);
    }

    public RandomGenerator(long seed){
        setSeed(seed);
    }

    public RandomGenerator(long low, long high){
        setSeed(low, high);
    }

    private static long wangHash64(long key) {
        key = (~key) + (key << 21);
        key = key ^ (key >>> 24);
        key = (key + (key << 3)) + (key << 8);
        key = key ^ (key >>> 14);
        key = (key + (key << 2)) + (key << 4);
        key = key ^ (key >>> 28);
        key = key + (key << 31);
        return key;
    }

    public void setSeed(long seed){
        do{
            seed = wangHash64(seed);
        }while(seed == 0);

        this.state = seed;
        this.lastRandomNormal = Double.POSITIVE_INFINITY;
    }

    public void setSeed(long low, long high){
        setSeed(((high & 0xFFFFFFFFL) << 32) | (low  & 0xFFFFFFFFL));
    }

    public long rand(){
        long x = this.state;

        x ^= (x >>> 12);
        x ^= (x << 25);
        x ^= (x >>> 27);

        this.state = x;

        return x*2685821657736338717L;
    }

    public double random(){
        return (rand() >>> 11)*(1.0/(1L << 53));
    }

    public int random(int min, int max){ // max inclusive
        if(min > max){
            int tmp = min;
            min = max;
            max = tmp;
        }
        return min + (int)(random()*(max - min + 1));
    }

    public double randomNormal(double stddev){
        if(this.lastRandomNormal != Double.POSITIVE_INFINITY){
            double r = this.lastRandomNormal;
            this.lastRandomNormal = Double.POSITIVE_INFINITY;
            return r*stddev;
        }

        double r = Math.sqrt(-2.0*Math.log(1.0 - random()));
        double phi = 2.0*Math.PI*(1.0 - random());

        this.lastRandomNormal = r*Math.cos(phi);
        return r*Math.sin(phi)*stddev;
    }

    public long getState(){
        return this.state;
    }

    public void setState(long state){
        this.state = state;
        this.lastRandomNormal = Double.POSITIVE_INFINITY;
    }
}