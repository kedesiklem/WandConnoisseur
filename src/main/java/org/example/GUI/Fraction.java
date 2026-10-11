package org.example.GUI;

public class Fraction{
    private long numerator;
    private long denominator;

    public Fraction(long numerator, long denominator){
        this.numerator = numerator;
        this.denominator = denominator;
        this.simplify();
    }

    public Fraction(int numerator, int denominator){
        this((long)numerator, (long)denominator);
    }

    public Fraction(long value){
        this.numerator = value;
        this.denominator = 1;
    }

    public Fraction(int value){
        this((long)value);
    }

    public Fraction clone(){
        return new Fraction(this.numerator, this.denominator);
    }

    public static Fraction clone(Fraction fraction){
        return new Fraction(fraction.numerator, fraction.denominator);
    }

    // getters
    public long getNumerator(){
        return numerator;
    }

    public long getDenominator(){
        return denominator;
    }

    public int getNumeratorInt(){
        return (int)numerator;
    }

    public int getDenominatorInt(){
        return (int)denominator;
    }

    public Fraction set(Fraction fraction){
        this.numerator = fraction.numerator;
        this.denominator = fraction.denominator;
        return this;
    }

    public Fraction set(long numerator, long denominator){
        this.numerator = numerator;
        this.denominator = denominator;
        return this;
    }

    public Fraction set(long value){
        this.numerator = value;
        this.denominator = 1;
        return this;
    }

    public Fraction add(Fraction other){
        this.numerator = this.numerator*other.denominator + other.numerator*this.denominator;
        this.denominator *= other.denominator;
        this.simplify();
        return this;
    }

    public Fraction subtract(Fraction other){
        this.numerator = this.numerator*other.denominator - other.numerator*this.denominator;
        this.denominator *= other.denominator;
        this.simplify();
        return this;
    }

    public Fraction invertSubtract(Fraction other){
        this.numerator = other.numerator*this.denominator - this.numerator*other.denominator;
        this.denominator *= other.denominator;
        this.simplify();
        return this;
    }

    public Fraction multiply(Fraction other){
        this.numerator *= other.numerator;
        this.denominator *= other.denominator;
        this.simplify();
        return this;
    }

    public Fraction divide(Fraction other){
        this.numerator *= other.denominator;
        this.denominator *= other.numerator;
        this.simplify();
        return this;
    }

    public Fraction add(long numerator, long denominator){
        this.numerator = this.numerator*numerator + numerator*this.denominator;
        this.denominator *= denominator;
        this.simplify();
        return this;
    }

    public Fraction subtract(long numerator, long denominator){
        this.numerator = this.numerator*denominator - numerator*this.denominator;
        this.denominator *= denominator;
        this.simplify();
        return this;
    }

    public Fraction invertSubtract(long numerator, long denominator){
        this.numerator = numerator*this.denominator - this.numerator*denominator;
        this.denominator *= denominator;
        this.simplify();
        return this;
    }

    public Fraction multiply(long numerator, long denominator){
        this.numerator *= numerator;
        this.denominator *= denominator;
        this.simplify();
        return this;
    }

    public Fraction divide(long numerator, long denominator){
        this.numerator *= denominator;
        this.denominator *= numerator;
        this.simplify();
        return this;
    }

    public Fraction add(Long value){
        this.numerator += value*this.denominator;
        this.simplify();
        return this;
    }

    public Fraction subtract(Long value){
        this.numerator -= value*this.denominator;
        this.simplify();
        return this;
    }

    public Fraction invertSubtract(Long value){
        this.numerator = value*this.denominator - this.numerator;
        this.simplify();
        return this;
    }

    public Fraction multiply(Long value){
        this.numerator *= value;
        this.simplify();
        return this;
    }

    public Fraction divide(Long value){
        this.denominator *= value;
        this.simplify();
        return this;
    }

    public static double add(Fraction fraction, long value){
        return (double)(fraction.numerator + value*fraction.denominator)/fraction.denominator;
    }

    public static double subtract(Fraction fraction, long value){
        return (double)(fraction.numerator - value*fraction.denominator)/fraction.denominator;
    }

    public static double subtract(long value, Fraction fraction){
        return (double)(value*fraction.denominator - fraction.numerator)/fraction.denominator;
    }

    public static double multiply(Fraction fraction, long value){
        return (double)(fraction.numerator*value)/fraction.denominator;
    }

    public static double divide(Fraction fraction, long value){
        return (double)fraction.numerator/(fraction.denominator*value);
    }

    public static double add(Fraction fraction, double value){
        return (fraction.numerator + value*fraction.denominator)/fraction.denominator;
    }

    public static double subtract(Fraction fraction, double value){
        return (fraction.numerator - value*fraction.denominator)/fraction.denominator;
    }

    public static double subtract(double value, Fraction fraction){
        return (value*fraction.denominator - fraction.numerator)/fraction.denominator;
    }

    public static double multiply(Fraction fraction, double value){
        return fraction.numerator*value/fraction.denominator;
    }

    public static double divide(Fraction fraction, double value){
        return fraction.numerator/(fraction.denominator*value);
    }

    public static Fraction add(Fraction f1, Fraction f2){
        return new Fraction(
            f1.numerator*f2.denominator + f2.numerator*f1.denominator,
            f1.denominator*f2.denominator
        );
    }

    public static Fraction subtract(Fraction f1, Fraction f2){
        return new Fraction(
            f1.numerator*f2.denominator - f2.numerator*f1.denominator,
            f1.denominator*f2.denominator
        );
    }

    public static Fraction multiply(Fraction f1, Fraction f2){
        return new Fraction(
            f1.numerator*f2.numerator,
            f1.denominator*f2.denominator
        );
    }

    public static Fraction divide(Fraction f1, Fraction f2){
        return new Fraction(
            f1.numerator*f2.denominator,
            f1.denominator*f2.numerator
        );
    }

    public static Fraction max(Fraction f1, Fraction f2){
        if(f1.toDouble() >= f2.toDouble()){
            return f1.clone();
        }else{
            return f2.clone();
        }
    }

    public static Fraction max(Fraction fraction, long value){
        if(fraction.toDouble() >= value){
            return fraction.clone();
        }else{
            return new Fraction(value);
        }
    }

    public Fraction max(Fraction fraction){
        if(this.toDouble() < fraction.toDouble()){
            this.set(fraction);
        }
        return this;
    }

    public Fraction max(long value){
        if(this.toDouble() < value){
            this.numerator = value;
            this.denominator = 1;
        }
        return this;
    }

    public double toDouble(){
        if(denominator == 0){
            if(numerator > 0){
                return Double.MAX_VALUE;
            }
            if(numerator < 0){
                return -Double.MAX_VALUE;
            }
            return 0.0;
        }

        return (double)numerator/denominator;
    }

    public long toLong(){
        if(denominator == 0){
            if(numerator > 0){
                return Long.MAX_VALUE;
            }
            if(numerator < 0){
                return -Long.MAX_VALUE;
            }
            return 0;
        }

        return numerator/denominator;
    }

    @Override
    public String toString(){
        if(this.denominator == 1){
            return Long.toString(numerator);
        }else if(this.denominator == 0){
            if(this.numerator > 0){
                return "infinity";
            }else if(this.numerator < 0){
                return "-infinity";
            }else{
                return "0";
            }
        }

        return numerator + "/" + denominator;
    }

    public boolean equals(Fraction other){
        return this.numerator == other.numerator && this.denominator == other.denominator;
    }

    public boolean equals(long value){
        return this.denominator == 1 && this.numerator == value;
    }

    private static long gcd(long a, long b){
        while(b != 0){
            long t = a%b;
            a = b;
            b = t;
        }
        return a == 0 ? 1 : a;
    }

    private void simplify(){
        if(this.denominator == 0 || this.denominator == 1){
            return;
        }

        if(this.denominator < 0){
            this.numerator = -this.numerator;
            this.denominator = -this.denominator;
        }

        long gcd = gcd(Math.abs(this.numerator), this.denominator);

        this.numerator /= gcd;
        this.denominator /= gcd;
    }
}