package exemple.jdbc.dao;

import java.util.List;

import exemple.jdbc.entity.Fournisseur;

public interface FournisseurDao {
	
	//Create : insert
	//Read : extraire
	//Update : mise à jour
	//Delete :supprimer
	
	List <Fournisseur> extraire();
	void insert (Fournisseur fournisseur);
	int update (String ancienNom, String nouveauNom);
	boolean delete(Fournisseur fournisseur);
};
