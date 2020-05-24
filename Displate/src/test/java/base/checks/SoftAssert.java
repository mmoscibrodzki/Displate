package base.checks;

import java.util.ArrayList;
import java.util.List;

public class SoftAssert {

    private static final List<String> ERRORS = new ArrayList<>();

    private SoftAssert(){
    }

    private static void addError(AssertionError ae){
        final StackTraceElement ste = findRoutCause(ae.getStackTrace());
        final StringBuilder msg = new StringBuilder(ae.getMessage().replace("<","{").replace(">","}"));

        msg.append("\n\tat ").append(ste.getClassName()).append(".").append(ste.getMethodName()).append("(").append(ste.getFileName())
                .append(":").append(ste.getLineNumber()).append(")");
        ERRORS.add(msg.toString());
    }

    private static StackTraceElement findRoutCause(StackTraceElement[] stackTrace){
        boolean softAssert = false;
        for (StackTraceElement stackTraceElement: stackTrace) {
            if (SoftAssert.class.getCanonicalName().equals(stackTraceElement.getClassName())){
                softAssert = true;
            }else{
                if (softAssert){
                    return stackTraceElement;
                }
            }
        }
        return null;
    }

}
