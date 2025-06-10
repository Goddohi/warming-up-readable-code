package cleancode.studycafe.tobe.model.order;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("이용권 주문 계산로직 테스트")
public class StudyCafePassOrderTest {
    @DisplayName("시간권만 주문 (할인 없음)")
    @Test
    void orderHourlyPassOnly12Hours() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 12, 13000, 0.0);

        // when
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, null);

        // then
        assertThat(order.getLockerPass()).isEmpty(); //미포함
        assertThat(order.getTotalPrice()).isEqualTo(13000);//할인없는가격
    }

    @DisplayName("주간 2주권만 주문 (10% 할인)")
    @Test
    void orderWeeklyPassOnly2Weeks() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 2, 100000, 0.1);

        // when
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, null);

        // then
        assertThat(order.getLockerPass()).isEmpty(); //2주권만
        assertThat(order.getTotalPrice()).isEqualTo((int)(100000 - (100000 * 0.1))); //할인10%가격과 동일
    }

    @DisplayName("주간 12주권만 주문 (15% 할인)")
    @Test
    void orderWeeklyPassOnly12Weeks() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 12, 400000, 0.15);

        // when
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, null);

        // then
        assertThat(order.getLockerPass()).isEmpty();
        assertThat(order.getTotalPrice()).isEqualTo((int)(400000 - (400000 * 0.15)));
    }

    @DisplayName("고정석만 4주권 주문 (10% 할인)")
    @Test
    void orderFixedSeatPassOnly4Weeks() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 4, 250000, 0.1);

        // when
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, null);

        // then
        assertThat(order.getLockerPass()).isEmpty();
        assertThat(order.getTotalPrice()).isEqualTo((int)(250000 - (250000 * 0.1)));
    }

    @DisplayName("고정석과 락커 4주권 주문(고정석만 10% 할인) ")
    @Test
    void orderFixedSeatPass4WeeksAndLockerPass() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 4, 250000, 0.1);
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 4, 10000);

        // when
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, lockerPass);

        // then
        assertThat(order.getLockerPass()).isPresent();
        assertThat(order.getTotalPrice()).isEqualTo((int)(250000 + 10000 - (250000 * 0.1)));
    }

    @DisplayName("고정석와 락커 12주권 주문: (고정석만 15% 할인)")
    @Test
    void orderUseLockerFixedPass_12Weeks() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 12, 700000, 0.15);
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 12, 30000);

        // when
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, lockerPass);

        // then
        assertThat(order.getLockerPass()).isPresent();
        assertThat(order.getTotalPrice()).isEqualTo((int)(700000 + 30000 - (700000 * 0.15)));
    }
}
