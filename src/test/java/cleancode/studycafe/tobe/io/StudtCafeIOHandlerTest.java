package cleancode.studycafe.tobe.io;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StudtCafeIOHandlerTest {
    private void provideUserInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    @DisplayName("반환값 1 -> Hourly")
    @Test
    void inputOneReturnHourly() {
        // given
        provideUserInput("1");
        StudyCafeIOHandler studyCafeIOHandler = new StudyCafeIOHandler();

        // when
        StudyCafePassType result = studyCafeIOHandler.askPassTypeSelecting();

        // then
        assertThat(result).isEqualTo(StudyCafePassType.HOURLY);
    }

    @DisplayName("잘못된 입력시 AppException")
    @Test
    void returnException() {
        // given
        provideUserInput("9");
        StudyCafeIOHandler studyCafeIOHandler = new StudyCafeIOHandler();

        // when & then
        assertThatThrownBy(studyCafeIOHandler::askPassTypeSelecting)
            .isInstanceOf(AppException.class)
            .hasMessage("잘못된 입력입니다.");
    }
}
