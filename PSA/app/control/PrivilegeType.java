
package app.control;

public class PrivilegeType {
    public static final int ADMIN = 0;
    public static final int USER = 1;
    public static final int EVALUATOR = 2;
    public static final int NOTLOGGED = 3;
    public static final int BOARD = 4;
    
    public static class Admin{
        public static final int EVENT = 0;
        public static final int CALENDAR = 1;
        public static final int BOARD = 2;
        public static final int SPEAKER = 3;
        public static final int CATEGORY = 4;
    }
    
    public static class User{
        public static final int EVENT = 0;
        public static final int CALENDAR = 1;
    }
    
    public static class Evaluator{
        public static final int EVENT = 0;
        public static final int CALENDAR = 1;
        public static final int EVALUATION = 2;  
    }
    
    public static class notLogged{
        public static final int EVENT = 0;
        public static final int CALENDAR = 1;
    }
    
    public static class Board{
        public static final int EVENT = 0;
        public static final int CALENDAR = 1;
        public static final int EVALUATORS = 2;
    }
}
