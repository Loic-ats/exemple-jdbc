package exemple.jdbc.dao;

import java.util.List;

import exemple.jdbc.entity.Article;

public interface ArticleDao {

	List<Article> extraire();

	void insert(Article article);

	int update(String ancienNom, String nouveauNom);

	boolean delete(Article article);
};
