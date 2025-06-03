package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.StudyCafeIOHandler;
import cleancode.studycafe.tobe.io.InputHandler;
import cleancode.studycafe.tobe.io.OutputHandler;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    private final StudyCafeIOHandler studyCafeIOHandler = new StudyCafeIOHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            studyCafeIOHandler.showIntro();

            StudyCafePass selectedPass = getStudyCafePass();

            Optional<StudyCafeLockerPass> optionalLockerPass = getStudyCafeLockerPass(selectedPass);
            optionalLockerPass.ifPresentOrElse(
                    lockerPass -> studyCafeIOHandler.showPassOrderSummary(selectedPass, lockerPass),
                    ()->studyCafeIOHandler.showPassOrderSummary(selectedPass));
        } catch (AppException e) {
            studyCafeIOHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            studyCafeIOHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafePass getStudyCafePass() {
        StudyCafePassType studyCafePassType = studyCafeIOHandler.getPassTypeSelectingUserAction();

        List<StudyCafePass> typePasses = getTypePasses(studyCafePassType);

        studyCafeIOHandler.showPassListForSelection(typePasses);
        return studyCafeIOHandler.getSelectPass(typePasses);
    }

    private List<StudyCafePass> getTypePasses(StudyCafePassType studyCafePassType) {
        List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();

        List<StudyCafePass> typePasses = studyCafePasses.stream()
                .filter(studyCafePass -> studyCafePass.isSamePassType(studyCafePassType))
                .toList();
        return typePasses;
    }

    private Optional<StudyCafeLockerPass> getStudyCafeLockerPass(StudyCafePass selectedPass) {
        if (selectedPass.cannotUseLocker()) return Optional.empty();

        List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();

        StudyCafeLockerPass lockerPass = lockerPasses.stream()
            .filter(option ->
                selectedPass.isSamePassType(option) && selectedPass.isSameDuration(option)
            )
            .findFirst()
            .orElse(null);

        if (lockerPass != null) {
            boolean isLockerSelected = studyCafeIOHandler.isLockerSelection(lockerPass);
            if (isLockerSelected) {
                return Optional.of(lockerPass);
            }
        }

        return Optional.empty();
    }

}
