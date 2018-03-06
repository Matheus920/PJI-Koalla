package app.control.interfaces;

import java.util.List;

public interface PrivilegiesButtonsInterface extends PrivilegeTypeInterface{
    public List<String> getButtons();
    public void setEvaluatorButtons();
    public void setNotLoggedButtons();
    public void setAdminButtons();
    public void setBoardButtons();
    public void setUserButtons();
    public int getAmountButtons();
    
    public static class AdminButtons {
        public static final int EVENT = 0;
       // public static final int CALENDAR = 1;
        public static final int SPEAKER = 1;
        public static final int EVALUATORS = 2;
        public static final int BOARD = 3;
        public static final int CATEGORY = 4;
    }
    
    public static class UserButtons{
        public static final int EVENT = 0;
        //public static final int CALENDAR = 1;
        public static final int SPEAKER = 1;
        public static final int EVALUATORS = 2;
        public static final int BOARD = 3;
    }
    
    public static class EvaluatorButtons{
        public static final int EVENT = 0;
        //public static final int CALENDAR = 1;
        public static final int SPEAKER = 1;
        public static final int EVALUATORS = 2;
        public static final int BOARD = 3;
        public static final int EVALUATION = 4;
    }
    
    public static class notLoggedButtons{
        public static final int EVENT = 0;
        //public static final int CALENDAR = 1;
        public static final int SPEAKER = 1;
        public static final int EVALUATORS = 2;
        public static final int BOARD = 3;
    }
    
    public static class BoardButtons{
        public static final int EVENT = 0;
        //public static final int CALENDAR = 1;
        public static final int SPEAKER = 1;
        public static final int EVALUATORS = 2;
        public static final int BOARD = 3;
        public static final int CRITERIA = 4;
    }
}
