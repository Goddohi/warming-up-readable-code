package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.InputHandler;
import cleancode.studycafe.tobe.io.OutputHandler;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;

import java.util.List;

public class StudyCafePassMachine {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            outputHandler.showIntro();

            StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();

            List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();

            List<StudyCafePass> typePasses = studyCafePasses.stream()
                    .filter(studyCafePass -> studyCafePass.getPassType() == studyCafePassType)
                    .toList();

            outputHandler.showPassListForSelection(typePasses);
            StudyCafePass selectedPass = inputHandler.getSelectPass(typePasses);

            if (studyCafePassType == StudyCafePassType.FIXED) {

                StudyCafeLockerPass lockerPass = getStudyCafeLockerPass(selectedPass);

                boolean lockerSelection = false;
                if (lockerPass != null) {
                    outputHandler.askLockerPass(lockerPass);
                    lockerSelection = inputHandler.getLockerSelection();
                    if (lockerSelection) {
                        outputHandler.showPassOrderSummary(selectedPass, lockerPass);
                        return;
                    }

                }

            }

            outputHandler.showPassOrderSummary(selectedPass, null);
        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafeLockerPass getStudyCafeLockerPass(StudyCafePass selectedPass) {
        List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();
        StudyCafeLockerPass lockerPass = lockerPasses.stream()
            .filter(option ->
                option.getPassType() == selectedPass.getPassType()
                    && option.getDuration() == selectedPass.getDuration()
            )
            .findFirst()
            .orElse(null);
        return lockerPass;
    }

}
