package cleancode.studycafe.tobe.model;

public class StudyCafePass implements Pass{

    private final StudyCafePassType passType;
    private final int duration;
    private final int price;
    private final double discountRate;

    private StudyCafePass(StudyCafePassType passType, int duration, int price, double discountRate) {
        this.passType = passType;
        this.duration = duration;
        this.price = price;
        this.discountRate = discountRate;
    }

    public static StudyCafePass of(StudyCafePassType passType, int duration, int price, double discountRate) {
        return new StudyCafePass(passType, duration, price, discountRate);
    }

    public StudyCafePassType getPassType() {
        return passType;
    }

    public int getDuration() {
        return duration;
    }

    public int getPrice() {
        return price;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public String display() {
        return passType.formatDisplay(duration, price);
    }

    public boolean isSamePassType(StudyCafePassType passType) {
        return this.passType == passType;
    }

    public boolean isSamePassType(Pass pass) {
        return this.isSamePassType(pass.getPassType());
    }

    public boolean isNotSamePassType(StudyCafePassType passType) {
        return this.passType != passType;
    }

    public boolean isSameDuration(int duration) {
        return this.duration == duration;
    }

    public boolean isSameDuration(Pass pass) {
        return this.isSameDuration(pass.getDuration());
    }
    public boolean canUseLocker() {
        return passType.canUseLocker();
    }
    public boolean cannotUseLocker() {
        return !canUseLocker();
    }



}
