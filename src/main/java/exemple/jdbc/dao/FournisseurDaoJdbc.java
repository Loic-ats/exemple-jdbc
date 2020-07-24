package exemple.jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import exemple.jdbc.entity.Fournisseur;

public class FournisseurDaoJdbc implements FournisseurDao {

	public static void main(String[] a) {

		FournisseurDaoJdbc ofo = new FournisseurDaoJdbc();
		List<Fournisseur> listeFour = ofo.extraire();
		for (Fournisseur fo : listeFour) {
			System.out.println(fo);
		}
		
		//Rajout du fournisseur Lessieurs avec un index 9
		ofo.insert(new Fournisseur (9, "Lesieurs"));
		listeFour = ofo.extraire();
		for (Fournisseur fo : listeFour) {
			System.out.println(fo);
		}
		//Update ==> Remplacement du fournisseur Lesieurs par Leclerc
		ofo.update("Lesieurs", "Leclerc");
		listeFour = ofo.extraire();
		for (Fournisseur fo : listeFour) {
			System.out.println(fo);
		}
		
		//Suppresion du fournisseur Leclerc
		if(ofo.delete(new Fournisseur (9, "Leclerc")) ) System.out.println("Fournisseur supprimé ! ");
		listeFour = ofo.extraire();
		for(Fournisseur fo : listeFour) {
			System.out.println(fo);
		}
		

		
		
	}

	@Override
	public List<Fournisseur> extraire() {
		Connection connection = null;
		List<Fournisseur> listeFour = new ArrayList<Fournisseur>();

		try {

			connection = getConnection();

			// Récupérer un Statement = accés aux données à partir de l'objet connection

			// Récupérer un canal/tuyaux de communication pour échanger avec la BDD pour
			// faire des requêtes
			Statement monCanal = connection.createStatement();
			// Récupérer le résultat de la requête
			ResultSet monResultat = monCanal.executeQuery("SELECT* FROM fournisseur;");

			// Ajouter ligne par ligne dans la liste des Fournisseurs
			// Tant qu'il ya un valeur a monResultat la boucle tourne acar on ne sais pas
			// combien il y a d'element quand on fait un SELECT

			while (monResultat.next()) {

				listeFour.add(new Fournisseur(monResultat.getInt("id"), monResultat.getString("nom")));

			}
			// On ferme chaqu'un des éléments => connection est fermé dans le finally donc
			// on ne le rajoute pas ici
			monResultat.close();
			monCanal.close();

		}

		catch (Exception e) {

			System.err.println("Erreur d'exécution : " + e.getMessage());
		}

		finally {
			try {

				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				System.err.println("Probl de connection close: " + e.getMessage());
			}
		}
		return listeFour;
	}

	// fait un insert dans la base compta sur la table fournisseur
	public void insert(Fournisseur fournisseur) {
		Connection connection = null;

		try {
			connection = getConnection();
			Statement monCanal = connection.createStatement();
			
			int nb = monCanal.executeUpdate("INSERT INTO fournisseur (id,nom) values (" + fournisseur.getId() + " , '"
					+ fournisseur.getNom() + "');");

			if (nb == 1) {
				System.out.println("Fournisseur ajouté!");
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

	// fait un update dans la table fournisseur en chargeant le nom ancienNom par
	// nouveauNom
	public int update(String ancienNom, String nouveauNom) {
		
		Connection connection = null;
		int nb = 0;

		try {
			
		connection = getConnection();
		Statement monCanal = connection.createStatement();
		
		 nb = monCanal.executeUpdate("UPDATE fournisseur SET nom = '"+ nouveauNom + "' WHERE nom = '"  + ancienNom + "';");
				
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

	// supprime le fournisseur specifie dans la table fournisseur
	public boolean delete(Fournisseur fournisseur) {
		
		Connection connection = null;
		boolean nb = false;
		
		try {
			
			connection = getConnection();
			Statement monCanal = connection.createStatement();
			nb = monCanal.executeUpdate (
					"DELETE FROM fournisseur where id = " + fournisseur.getId() + ";") 
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

	// méthode pour se connecter au serveur elle vient notament récupérer les
	// informations dans le database.properties

	public Connection getConnection() {
		// recupere le fichier properties
		ResourceBundle db = ResourceBundle.getBundle("database");

		try {
			// enregistre le pilote

			Class.forName(db.getString("db.driver"));
			return DriverManager.getConnection(db.getString("db.url"), db.getString("db.user"),
					db.getString("db.pass"));

		} catch (ClassNotFoundException | SQLException e) {

			throw new RuntimeException();

		}

	}

}
