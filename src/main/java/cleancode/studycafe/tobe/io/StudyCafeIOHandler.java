package cleancode.studycafe.tobe.io;

import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;

import java.util.List;

public class StudyCafeIOHandler {
    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();

    public StudyCafePassType getPassTypeSelectingUserAction(){
        return inputHandler.getPassTypeSelectingUserAction();
    }

    public void showSimpleMessage(String message){
        outputHandler.showSimpleMessage(message);
    }
    public void showPassOrderSummary(StudyCafePass studyCafePass){
        outputHandler.showPassOrderSummary(studyCafePass);
    }
    public void showPassOrderSummary(StudyCafePass studyCafePass,StudyCafeLockerPass lockerPass){
        outputHandler.showPassOrderSummary(studyCafePass, lockerPass);
    }
    public void showIntro(){
        outputHandler.showIntro();
    }
    public void showPassListForSelection(List<StudyCafePass> studyCafePass){
        outputHandler.showPassListForSelection(studyCafePass);
    }
    public StudyCafePass getSelectPass(List<StudyCafePass> passes){
       return inputHandler.getSelectPass(passes);
    }
    public boolean isLockerSelection(StudyCafeLockerPass lockerPass){
        outputHandler.askLockerPass(lockerPass);
        return  inputHandler.getLockerSelection();
    }
}
