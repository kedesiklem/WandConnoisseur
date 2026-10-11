package org.example.GUI;

public class RelativeLayoutConstraints extends LayoutConstraints{
    private int margin;

    public RelativeLayoutConstraints(Fraction startX, Fraction startY, Fraction startWidth, Fraction startHeight, int minimumWidth, int minimumHeight, Fraction xGrowthRate, Fraction yGrowthRate, Fraction widthGrowthRate, Fraction heightGrowthRate){
        super(startX.clone(), startY.clone(), startWidth.clone().max(minimumWidth).max(0), startHeight.clone().max(minimumHeight).max(0), Math.max(minimumWidth, 0), Math.max(minimumHeight, 0), xGrowthRate, yGrowthRate, widthGrowthRate, heightGrowthRate);
    }

    public RelativeLayoutConstraints(Fraction startX, Fraction startY, Fraction startWidth, Fraction startHeight, Fraction xGrowthRate, Fraction yGrowthRate, Fraction widthGrowthRate, Fraction heightGrowthRate){
        super(startX.clone(), startY.clone(), startWidth.clone().max(0), startHeight.clone().max(0), 0, 0, xGrowthRate, yGrowthRate, widthGrowthRate, heightGrowthRate);
    }

    public RelativeLayoutConstraints(Fraction xGrowthRate, Fraction yGrowthRate, Fraction widthGrowthRate, Fraction heightGrowthRate){
        super(new Fraction(0), new Fraction(0), new Fraction(0), new Fraction(0), 0, 0, xGrowthRate, yGrowthRate, widthGrowthRate, heightGrowthRate);
    }

    public int getMargin(){
        return this.margin;
    }

    public RelativeLayoutConstraints setMargin(int margin){
        this.margin = margin;
        return this;
    }

    @Override
    public boolean update(Fraction width, Fraction height, Fraction startWidth, Fraction startHeight){
        // TODO only return true if update
        this.x.set(width).subtract((long)this.margin).multiply(this.xGrowthRate).add(this.startX);
        this.y.set(height).subtract((long)this.margin).multiply(this.yGrowthRate).add(this.startY);
        this.width.set(width).subtract((long)this.margin).multiply(this.widthGrowthRate).add(this.startWidth).max(this.minimumWidth);
        this.height.set(height).subtract((long)this.margin).multiply(this.heightGrowthRate).add(this.startHeight).max(this.minimumHeight);

        return true;
    }
}