package cleancode.studycafe.tobe.model.pass.locker;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class StudyCafeSeatPassesTest {
    @Test
    @DisplayName("좌석 이용권과 락커 이용권이 타입과 기간이 같아야 사용")
    void seatPassCanUseLockerPassMatchedDurationAndPassType() throws Exception {
        //given
        StudyCafeLockerPasses lockerPasses = StudyCafeLockerPasses.of(
            List.of(
                StudyCafeLockerPass.of(StudyCafePassType.FIXED, 4, 250000),
                StudyCafeLockerPass.of(StudyCafePassType.FIXED, 12, 700000)
            )
        );
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 4, 10000, 0);

        //when
        Optional<StudyCafeLockerPass> lockerPassOptional = lockerPasses.findLockerPassBy(seatPass);

        //then
        assertThat(lockerPassOptional).isPresent();
    }

    @Test
    @DisplayName("좌석 이용권과 락커 이용권의 타입과 기간이 일치하지 않으면 사용불가")
    void seatPassCanNotUseLockerPassNoneMatchedDurationOrType() throws Exception {
        seatPassCanNotUseLockerPassNoneMatchedDuration();
        seatPassCanNotUseLockerPassNoneMatchedPassType();
    }
    @Test
    @DisplayName("좌석 이용권과 락커 이용권의 기간이 일치하지 않으면 사용불가")
    void seatPassCanNotUseLockerPassNoneMatchedDuration() throws Exception {
        //given
        StudyCafeLockerPasses lockerPasses = StudyCafeLockerPasses.of(
            List.of(
                StudyCafeLockerPass.of(StudyCafePassType.FIXED, 4, 250000),
                StudyCafeLockerPass.of(StudyCafePassType.FIXED, 12, 700000)
            )
        );
        StudyCafeSeatPass durationNonMatchedSeatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 11, 10000, 0);

        //when
        Optional<StudyCafeLockerPass> durationNonMatchedSeatPassOptional = lockerPasses.findLockerPassBy(durationNonMatchedSeatPass);

        //then
        assertThat(durationNonMatchedSeatPassOptional).isEmpty();
    }

    @Test
    @DisplayName("좌석 이용권과 락커 이용권의 타입이 일치하지 않으면 사용불가")
    void seatPassCanNotUseLockerPassNoneMatchedPassType() throws Exception {
        //given
        StudyCafeLockerPasses lockerPasses = StudyCafeLockerPasses.of(
            List.of(
                StudyCafeLockerPass.of(StudyCafePassType.FIXED, 4, 250000),
                StudyCafeLockerPass.of(StudyCafePassType.FIXED, 12, 700000)
            )
        );
        StudyCafeSeatPass typeNonMatchedSeatPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 4, 10000, 0);

        //when
        Optional<StudyCafeLockerPass> typeNonMatchedSeatPassOptional = lockerPasses.findLockerPassBy(typeNonMatchedSeatPass);

        //then
       assertThat(typeNonMatchedSeatPassOptional).isEmpty();
    }
}
