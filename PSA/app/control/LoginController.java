package app.control;

import app.control.interfaces.PrivilegeTypeInterface;
import app.data.ConnectionMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LoginController {
    
    public boolean exists(String email, String password, PrivilegeTypeInterface test){
        
        Connection conexao = ConnectionMySQL.openConnection();
        
        try{
            PreparedStatement preparedStatement = conexao.prepareStatement("select privilegio from usuario"
                    + " where email = ? and senha = ?;");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            if(rs.next()){
                
                switch (rs.getInt(1)) {
                    case PrivilegiesTest.ADMIN:
                        test.setPrivilegeType(0);
                        break;
                    case PrivilegiesTest.BOARD:
                        test.setPrivilegeType(4);
                        break;
                    case PrivilegiesTest.EVALUATOR:
                        test.setPrivilegeType(2);
                        break;
                    case PrivilegiesTest.USER:
                        test.setPrivilegeType(1);
                        break;
                    default:
                        break;
                }
              return true;  
              }
              ConnectionMySQL.closeConnection(conexao);
              return false;  
        }
     
        catch(SQLException ex){
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
