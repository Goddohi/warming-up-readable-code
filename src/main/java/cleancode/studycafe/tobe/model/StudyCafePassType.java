package cleancode.studycafe.tobe.model;

import java.util.Set;

public enum StudyCafePassType {

    HOURLY("시간 단위 이용권") {
        @Override
        public String formatDisplay(int duration, int price) {
            return String.format("%d시간권 - %d원", duration, price);
        }
    },
    WEEKLY("주 단위 이용권") {
        @Override
        public String formatDisplay(int duration, int price) {
            return String.format("%d주권 - %d원", duration, price);
        }
    },
    FIXED("1인 고정석") {
        @Override
        public String formatDisplay(int duration, int price) {
            return String.format("%d주권 - %d원", duration, price);
        }
    };

    private final String description;

    StudyCafePassType(String description) {
        this.description = description;
    }
    public static final Set<StudyCafePassType> LOCK_TYPES = Set.of(StudyCafePassType.FIXED);

    public boolean canUseLocker() {
        return LOCK_TYPES.contains(this);
    }

    public abstract String formatDisplay(int duration, int price);
}
