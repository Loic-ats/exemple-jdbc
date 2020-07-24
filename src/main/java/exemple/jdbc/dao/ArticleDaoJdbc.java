package exemple.jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import java.util.List;

import exemple.jdbc.entity.Article;


public class ArticleDaoJdbc implements ArticleDao {
	
	public static void main (String [] a) {
		
		ArticleDaoJdbc oar = new ArticleDaoJdbc ();
		List <Article> listeArt = oar.extraire();
		for(Article ar : listeArt) {
			System.out.println(ar);
		}
		
	}
	
	
	@Override
	public List<Article> extraire() {
		
		Connection connection = null;
		List<Article> listeArt = new ArrayList<Article>();
		
		try {
			
			connection =  getConnection();
			Statement monCanal = connection.createStatement();
			ResultSet monResultat = monCanal.executeQuery("SELECT* FROM article;");
			
			while (monResultat.next()) {
				listeArt.add(new Article (monResultat.getInt("id"), 
						monResultat.getString("ref"),
						monResultat.getString("designation"),
						monResultat.getDouble("prix"),
						monResultat.getInt("id_fou")));	
			}
			
			monResultat.close();
			monCanal.close();
			
		}  catch (Exception e) {
			
			System.err.println("Erreur d'exécution : " + e.getMessage());
			
		}
		
		finally {
			try {
				
				if(connection != null) connection.close();
			}
			catch
				(SQLException e) {
				System.err.println("Probl de connection close: " + e.getMessage());
			}
		}
		return listeArt;
	}

	@Override
	public void insert(Article  article) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int update(String ancienNom, String nouveauNom) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean delete(Article article) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Connection getConnection () {
		//recupere le fichier properties
		ResourceBundle db = ResourceBundle.getBundle("database");
		
		try {
		//enregistre le pilote	
			
			Class.forName(db.getString("db.driver"));
			return DriverManager.getConnection(db.getString("db.url"), db.getString("db.user"),
					db.getString("db.pass"));
						
		} catch (ClassNotFoundException | SQLException e) {
			
			throw new RuntimeException ();
			
		}
		
	}

}
