package exemple.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class TestConnectionJdbc {

	public static void main(String[] args) {

        // recupère le fichier properties
        ResourceBundle db = ResourceBundle.getBundle("database2");
        Connection connection = null;     
        // creer la connection
        try {	
        	//enregistre le pilote
        	Class.forName(db.getString("db.driver"));    	    	
        	//Connection connection = null
        	//Créer la connection	   	
        	connection = DriverManager.getConnection(
        			db.getString("db.url"), 
        			db.getString("db.user"),
                    db.getString("db.pass")); 	
       
        	 // affiche la connexion
            boolean valid = connection.isValid(500);
            if (valid) {
                // SEVERE = Erreur
                // INFO = info
                // WARNING = Avertissement
                // FINEST = Debug
                System.out.println("La connection est ok");
            } else {
                System.out.println("Il y a une erreur de connection");             
            }
        }    
            catch (SQLException | ClassNotFoundException e) {
        // Handle errors for JDBC
        System.out.println("Erreur de connection : " + e.getMessage());
    }
            
            finally {
            	try {
            		
            		if(connection != null) connection.close();
                      	
                } catch(SQLException e) {
            	
      
                	 System.out.println("Erreur de fermeture : " + e.getMessage());
                }
            	
            	}
            System.out.println("Base déconnectée ! "); 
        }
	}






