package org.example.GUI;

public class LayoutConstraints{
    protected final Fraction startX;
    protected final Fraction startY;
    protected final Fraction startWidth;
    protected final Fraction startHeight;
    protected int minimumWidth;
    protected int minimumHeight;
    protected final Fraction xGrowthRate;
    protected final Fraction yGrowthRate;
    protected final Fraction widthGrowthRate;
    protected final Fraction heightGrowthRate;
    protected final Fraction x;
    protected final Fraction y;
    protected final Fraction width;
    protected final Fraction height;

    public LayoutConstraints(Fraction x, Fraction y, Fraction width, Fraction height, int minimumWidth, int minimumHeight, Fraction xGrowthRate, Fraction yGrowthRate, Fraction widthGrowthRate, Fraction heightGrowthRate){
        this.startX = x;
        this.startY = y;
        this.x = x.clone();
        this.y = y.clone();
        this.minimumWidth = Math.max(minimumWidth, 0);
        this.minimumHeight = Math.max(minimumHeight, 0);
        this.startWidth = Fraction.max(width, this.minimumWidth);
        this.startHeight = Fraction.max(height, this.minimumHeight);
        this.xGrowthRate = xGrowthRate;
        this.yGrowthRate = yGrowthRate;
        this.widthGrowthRate = widthGrowthRate;
        this.heightGrowthRate = heightGrowthRate;
        this.width = this.startWidth.clone();
        this.height = this.startHeight.clone();
    }

    public LayoutConstraints(Fraction x, Fraction y, Fraction width, Fraction height, Fraction xGrowthRate, Fraction yGrowthRate, Fraction widthGrowthRate, Fraction heightGrowthRate){
        this.startX = x;
        this.startY = y;
        this.x = x.clone();
        this.y = y.clone();
        this.minimumWidth = 0;
        this.minimumHeight = 0;
        this.startWidth = Fraction.max(width, this.minimumWidth);
        this.startHeight = Fraction.max(height, this.minimumHeight);
        this.xGrowthRate = xGrowthRate;
        this.yGrowthRate = yGrowthRate;
        this.widthGrowthRate = widthGrowthRate;
        this.heightGrowthRate = heightGrowthRate;
        this.width = this.startWidth.clone();
        this.height = this.startHeight.clone();
    }

    public LayoutConstraints(Fraction x, Fraction y, Fraction width, Fraction height){
        this.startX = x;
        this.startY = y;
        this.x = x.clone();
        this.y = y.clone();
        this.minimumWidth = 0;
        this.minimumHeight = 0;
        this.startWidth = Fraction.max(width, this.minimumWidth);
        this.startHeight = Fraction.max(height, this.minimumHeight);
        this.xGrowthRate = new Fraction(0);
        this.yGrowthRate = new Fraction(0);
        this.widthGrowthRate = new Fraction(0);
        this.heightGrowthRate = new Fraction(0);
        this.width = this.startWidth.clone();
        this.height = this.startHeight.clone();
    }

    // getters
    public Fraction getX(){
        return this.x;
    }

    public int getXInt(){
        return (int)Math.round(this.x.toDouble());
    }

    public Fraction getY(){
        return this.y;
    }

    public int getYInt(){
        return (int)Math.round(this.y.toDouble());
    }

    public Fraction getWidth(){
        return this.width;
    }

    public int getWidthInt(){
        return (int)Math.round(this.width.toDouble());
    }

    public Fraction getHeight(){
        return this.height;
    }

    public int getHeightInt(){
        return (int)Math.round(this.height.toDouble());
    }

    public int getMinimumWidth(){
        return this.minimumWidth;
    }

    public int getMinimumHeight(){
        return this.minimumHeight;
    }

    public Fraction getXGrowthRate(){
        return this.xGrowthRate;
    }

    public Fraction getYGrowthRate(){
        return this.yGrowthRate;
    }

    public Fraction getWidthGrowthRate(){
        return this.widthGrowthRate;
    }

    public Fraction getHeightGrowthRate(){
        return this.heightGrowthRate;
    }

    public Fraction getStartX(){
        return this.startX;
    }

    public Fraction getStartY(){
        return this.startY;
    }

    public Fraction getStartWidth(){
        return this.startWidth;
    }

    public Fraction getStartHeight(){
        return this.startHeight;
    }

    // setters
    public void setX(Fraction x){
        this.x.set(x);
    }

    public void setX(long numerator, long denominator){
        this.x.set(numerator, denominator);
    }

    public void setY(Fraction y){
        this.y.set(y);
    }

    public void setY(long numerator, long denominator){
        this.y.set(numerator, denominator);
    }

    public void setMinimumWidth(int minimumWidth){
        this.minimumWidth = minimumWidth;
    }

    public void setMinimumHeight(int minimumHeight){
        this.minimumHeight = minimumHeight;
    }

    public void setXGrowthRate(Fraction xGrowthRate){
        this.xGrowthRate.set(xGrowthRate);
    }

    public void setXGrowthRate(long numerator, long denominator){
        this.xGrowthRate.set(numerator, denominator);
    }

    public void setYGrowthRate(Fraction yGrowthRate){
        this.yGrowthRate.set(yGrowthRate);
    }

    public void setYGrowthRate(long numerator, long denominator){
        this.yGrowthRate.set(numerator, denominator);
    }

    public void setWidthGrowthRate(Fraction widthGrowthRate){
        this.widthGrowthRate.set(widthGrowthRate);
    }

    public void setWidthGrowthRate(long numerator, long denominator){
        this.widthGrowthRate.set(numerator, denominator);
    }

    public void setHeightGrowthRate(Fraction heightGrowthRate){
        this.heightGrowthRate.set(heightGrowthRate);
    }

    public void setHeightGrowthRate(long numerator, long denominator){
        this.heightGrowthRate.set(numerator, denominator);
    }

    public void setStartX(Fraction startX){
        this.startX.set(startX);
    }

    public void setStartX(long numerator, long denominator){
        this.startX.set(numerator, denominator);
    }

    public void setStartX(long value){
        this.startX.set(value);
    }

    public void setStartY(Fraction startY){
        this.startY.set(startY);
    }

    public void setStartY(long startY){
        this.startY.set(startY);
    }

    public void setStartY(long numerator, long denominator){
        this.startY.set(numerator, denominator);
    }

    public void setStartWidth(Fraction startWidth){
        this.startWidth.set(startWidth);
    }

    public void setStartWidth(long startWidth){
        this.startWidth.set(startWidth);
    }

    public void setStartHeight(Fraction startWidth){
        this.startHeight.set(startWidth);
    }

    public void setStartHeight(long startWidth){
        this.startHeight.set(startWidth);
    }

    private static Fraction fractionReset(Fraction fraction, long value){
        if(fraction == null){
            return new Fraction(value);
        }else{
            fraction.set(value);
            return fraction;
        }
    }

    protected static Fraction fractionReset(Fraction fraction, Fraction value){
        if(fraction == null){
            return value.clone();
        }else{
            fraction.set(value);
            return fraction;
        }
    }

    public boolean update(LayoutConstraints parent){
        if(parent == null){
            return false;
        }

        return this.update(parent.width, parent.height, parent.startWidth, parent.startHeight);
    }

    public boolean update(Fraction width, Fraction height, Fraction startWidth, Fraction startHeight){
        Fraction currentValue = null;
        boolean changed = false;
        boolean updateX = !this.xGrowthRate.equals(0);
        boolean updateY = !this.yGrowthRate.equals(0);
        boolean updateWidth = !this.widthGrowthRate.equals(0);
        boolean updateHeight = !this.heightGrowthRate.equals(0);

        if(updateX || updateWidth){
            Fraction widthGrowth = Fraction.subtract(width, startWidth);

            if(updateX){
                currentValue = fractionReset(currentValue, this.xGrowthRate).multiply(widthGrowth).add(this.startX);
                changed = !this.x.equals(currentValue);
                this.x.set(currentValue);
            }

            if(updateWidth){
                currentValue = fractionReset(currentValue, this.widthGrowthRate).multiply(widthGrowth).add(this.startWidth).max(this.minimumWidth);
                changed = changed || !this.width.equals(currentValue);
                this.width.set(currentValue);
            }
        }

        if(updateY || updateHeight){
            Fraction heightGrowth = Fraction.subtract(height, startHeight);

            if(updateY){
                currentValue = fractionReset(currentValue, this.yGrowthRate).multiply(heightGrowth).add(this.startY);
                changed = changed || !this.y.equals(currentValue);
                this.y.set(currentValue);
            }

            if(updateHeight){
                currentValue = fractionReset(currentValue, this.heightGrowthRate).multiply(heightGrowth).add(this.startHeight).max(this.minimumHeight);
                changed = changed || !this.height.equals(currentValue);
                this.height.set(currentValue);
            }
        }

        return changed;
    }

    public boolean isInside(long x, long y){
        return x >= this.x.toDouble() && x <= this.x.toDouble() + this.width.toDouble() && y >= this.y.toDouble() && y <= this.y.toDouble() + this.height.toDouble();
    }

    public static void createLayoutHorizontal(LayoutConstraints[] layoutConstraints, Fraction margin, Fraction startX, Fraction startY, Fraction totalWidth, Fraction totalHeight, Fraction baseXGrowthRate, Fraction baseYGrowthRate, boolean xGrowth, boolean widthGrowth, boolean heightGrowth){
        Fraction width = totalWidth.clone().subtract(margin.clone().multiply(1L + layoutConstraints.length)).divide((long)layoutConstraints.length);
        Fraction widthGrowthRate = widthGrowth ? new Fraction(1).subtract(baseXGrowthRate).divide((long)layoutConstraints.length) : new Fraction(0);
        Fraction heightGrowthRate = heightGrowth ? new Fraction(1).subtract(baseYGrowthRate) : new Fraction(0);
        Fraction currentX = margin.clone().add(startX);

        for(int i=0; i < layoutConstraints.length; i++){
            layoutConstraints[i] = new LayoutConstraints(currentX.clone(), margin.clone().add(startY), width.clone(), margin.clone().multiply(2L).invertSubtract(totalHeight), xGrowth || widthGrowth ? new Fraction(1).subtract(baseXGrowthRate).multiply(i, layoutConstraints.length).add(baseXGrowthRate) : baseXGrowthRate.clone(), baseYGrowthRate.clone(), widthGrowthRate.clone(), heightGrowthRate.clone());
            currentX.add(width).add(margin);
        }
    }

    public static void createLayoutHorizontalUnbound(LayoutConstraints[] layoutConstraints, Fraction margin, Fraction startX, Fraction startY, Fraction width, Fraction totalHeight, Fraction baseXGrowthRate, Fraction baseYGrowthRate, boolean xGrowth, boolean widthGrowth, boolean heightGrowth){
        Fraction widthGrowthRate = widthGrowth ? new Fraction(1).subtract(baseXGrowthRate).divide((long)layoutConstraints.length) : new Fraction(0);
        Fraction heightGrowthRate = heightGrowth ? new Fraction(1).subtract(baseYGrowthRate) : new Fraction(0);
        Fraction currentX = margin.clone().add(startX);

        for(int i=0; i < layoutConstraints.length; i++){
            layoutConstraints[i] = new LayoutConstraints(currentX.clone(), margin.clone().add(startY), width.clone(), margin.clone().multiply(2L).invertSubtract(totalHeight), xGrowth || widthGrowth ? new Fraction(1).subtract(baseXGrowthRate).multiply(i, layoutConstraints.length).add(baseXGrowthRate) : baseXGrowthRate.clone(), baseYGrowthRate.clone(), widthGrowthRate.clone(), heightGrowthRate.clone());
            currentX.add(width).add(margin);
        }
    }

    public static void createLayoutVertical(LayoutConstraints[] layoutConstraints, Fraction margin, Fraction startX, Fraction startY, Fraction totalWidth, Fraction totalHeight, Fraction baseXGrowthRate, Fraction baseYGrowthRate, boolean yGrowth, boolean widthGrowth, boolean heightGrowth){
        Fraction height = totalHeight.clone().subtract(margin.clone().multiply(1L + layoutConstraints.length)).divide((long)layoutConstraints.length);
        Fraction widthGrowthRate = widthGrowth ? new Fraction(1).subtract(baseXGrowthRate) : new Fraction(0);
        Fraction heightGrowthRate = heightGrowth ? new Fraction(1).subtract(baseYGrowthRate).divide((long)layoutConstraints.length) : new Fraction(0);
        Fraction currentY = margin.clone().add(startY);

        for(int i=0; i < layoutConstraints.length; i++){
            layoutConstraints[i] = new LayoutConstraints(margin.clone().add(startX), currentY.clone(), margin.clone().multiply(2L).invertSubtract(totalWidth), height.clone(), baseXGrowthRate.clone(), yGrowth || heightGrowth ? new Fraction(1).subtract(baseYGrowthRate).multiply(i, layoutConstraints.length).add(baseYGrowthRate) : baseYGrowthRate.clone(), widthGrowthRate.clone(), heightGrowthRate.clone());
            currentY.add(height).add(margin);
        }
    }

    public static void createLayoutVerticalUnbound(LayoutConstraints[] layoutConstraints, Fraction margin, Fraction startX, Fraction startY, Fraction totalWidth, Fraction height, Fraction baseXGrowthRate, Fraction baseYGrowthRate, boolean yGrowth, boolean widthGrowth, boolean heightGrowth){
        Fraction widthGrowthRate = widthGrowth ? new Fraction(1).subtract(baseXGrowthRate) : new Fraction(0);
        Fraction heightGrowthRate = heightGrowth ? new Fraction(1).subtract(baseYGrowthRate).divide((long)layoutConstraints.length) : new Fraction(0);
        Fraction currentY = margin.clone().add(startY);

        for(int i=0; i < layoutConstraints.length; i++){
            layoutConstraints[i] = new LayoutConstraints(margin.clone().add(startX), currentY.clone(), margin.clone().multiply(2L).invertSubtract(totalWidth), height.clone(), baseXGrowthRate.clone(), yGrowth || heightGrowth ? new Fraction(1).subtract(baseYGrowthRate).multiply(i, layoutConstraints.length).add(baseYGrowthRate) : baseYGrowthRate.clone(), widthGrowthRate.clone(), heightGrowthRate.clone());
            currentY.add(height).add(margin);
        }
    }
}