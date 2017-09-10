package app.control.interfaces;

public interface PrivilegeTypeInterface {
    public int getPrivilegeType();
    public void setPrivilegeType(int type);
    
    public static final int ADMIN = 0;
    public static final int USER = 1;
    public static final int EVALUATOR = 2;
    public static final int NOTLOGGED = 3;
    public static final int BOARD = 4;
    
}
