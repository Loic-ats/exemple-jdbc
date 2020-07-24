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
import exemple.jdbc.entity.Fournisseur;


public class ArticleDaoJdbc implements ArticleDao {
	
	public static void main (String [] a) {
		
		ArticleDaoJdbc oar = new ArticleDaoJdbc ();
		List <Article> listeArt = oar.extraire();
		for(Article ar : listeArt) {
			System.out.println(ar);
		}
		
		oar.insert(new Article (14, "A02", "Tournevis", 7.2, 1));
		listeArt = oar.extraire();
		for(Article ar : listeArt) {
			System.out.println(ar);
		}
		
		oar.update("Tournevis", "Pince");
		listeArt = oar.extraire();
		for (Article ar : listeArt) {
			System.out.println(ar);
		}
		
		if(oar.delete(new Article (14, "A02", "Pince", 7.2, 1)) ) System.out.println("Fournisseur supprimé ! ");
		listeArt = oar.extraire();
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
		Connection connection = null;

		try {
			connection = getConnection();
			Statement monCanal = connection.createStatement();
			
			int nb = monCanal.executeUpdate("INSERT INTO article (id,ref,designation,prix,id_fou) " + " values (" 
			+ article.getId() + " , '"
			+ article.getRef() + "','"
			+ article.getDesignation() + "',"  
			+article.getPrix() + " , " 
			+ article.getid_fou() + ")");

			if (nb == 1) {
				System.out.println("Article ajouté!");
			}

		} catch (Exception e) {

			System.err.println("Erreur d'éxécution : " + e.getMessage());

		}

		finally {
			try {

				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				System.err.println("Probl de connection close: " + e.getMessage());
			}
		}
		
	}

	@Override
	public int update(String ancienNom, String nouveauNom) {
		
		Connection connection = null;
		int nb = 0;

		try {
			
		connection = getConnection();
		Statement monCanal = connection.createStatement();
		
		 nb = monCanal.executeUpdate("UPDATE article SET designation = '"+ nouveauNom + "' WHERE designation = '"  + ancienNom + "';");
				
				monCanal.close();				
		}
		
		catch (Exception e){
			
			System.err.println("Erreur d'éxécution :  " + e.getMessage());
		}
		
		finally {
			try {
				if (connection != null) connection.close();
			}
			catch(SQLException e) {
				
				System.err.println("Probl de connection close :" + e.getMessage());
			}
		}
		return 0;
	}

	@Override
	public boolean delete(Article article) {
	
		Connection connection = null;
		boolean nb = false;
		
		try {
			
			connection = getConnection();
			Statement monCanal = connection.createStatement();
			nb = monCanal.executeUpdate (
					"DELETE FROM article where id = " + article.getId() + ";") 
					==1;
					
			monCanal.close();
			
				
					
		} catch (Exception e) {
			System.out.println("erreur d'exécution" + e.getMessage());
			
		}
		
		finally {
			try {
				if (connection != null) connection.close();
			}
			catch(SQLException e) {
				
				System.err.println("Probl de connection close :" + e.getMessage());
			}
		}
		
		
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
