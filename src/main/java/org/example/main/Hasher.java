package org.example.main;

import java.nio.charset.StandardCharsets;

final class Hasher{
    private long hash;

    Hasher(long seed){
        this.hash = seed;
    }

    Hasher(){
        this(0);
    }

    public long getValue(){
        return this.hash;
    }

    public void reset(long seed){
        this.hash = seed;
    }

    public void reset(){
        this.reset(0);
    }

    public long add(int value){
        this.mix(3L);
        this.mix(value);
        return this.hash;
    }

    public long add(int[] values){
        this.mix(5L);
        if(values == null){
            return this.hash;
        }
        this.mix(values.length);
        for(int value : values){
            this.mix(value);
        }
        return this.hash;
    }

    public long add(long value){
        this.mix(7L);
        this.mix(value);
        return this.hash;
    }

    public long add(long[] values){
        this.mix(11L);
        if(values == null){
            return this.hash;
        }
        this.mix(values.length);
        for(long value : values){
            this.mix(value);
        }
        return this.hash;
    }

    public long add(float value){
        this.mix(13L);
        this.mix(Float.floatToRawIntBits(value));
        return this.hash;
    }

    public long add(float[] values){
        this.mix(17L);
        if(values == null){
            return this.hash;
        }
        this.mix(values.length);
        for(float value : values){
            this.mix(Float.floatToRawIntBits(value));
        }
        return this.hash;
    }

    public long add(double value){
        this.mix(19L);
        this.mix(Double.doubleToRawLongBits(value));
        return this.hash;
    }

    public long add(double[] values){
        this.mix(23L);
        if(values == null){
            return this.hash;
        }
        this.mix(values.length);
        for(double value : values){
            this.mix(Double.doubleToRawLongBits(value));
        }
        return this.hash;
    }

    public long add(String value){
        this.mix(29L);
        if(value == null){
            return this.hash;
        }
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);

        this.mix(bytes.length);
        for(byte b : bytes){
            this.mix(b & 0xffL);
        }
        return this.hash;
    }

    public long add(String[] values){
        this.mix(31L);
        if(values == null){
            return this.hash;
        }
        this.mix(values.length);
        for(String value : values){
            this.add(value);
        }
        return this.hash;
    }

    public long add(Class<?> value){
        this.mix(37L);
        return add(value.getName());
    }

    public long add(Class<?>[] values){
        this.mix(41L);
        this.mix(values.length);
        for(Class<?> value : values){
            this.add(value);
        }
        return this.hash;
    }

    public long add(boolean value){
        this.mix(43L);
        this.mix(value ? 1L : 0L);
        return this.hash;
    }

    public long add(boolean[] values){
        this.mix(47L);
        if(values == null){
            return this.hash;
        }
        this.mix(values.length);
        for(boolean value : values){
            this.add(value);
        }
        return this.hash;
    }

    private void mix(long value){
        this.hash ^= value + 0x9e3779b97f4a7c15L;
        this.hash *= 0xbf58476d1ce4e5b9L;
        this.hash ^= this.hash >>> 27;
    }
}