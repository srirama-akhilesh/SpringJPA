package com.loya.onetomanybidirectionaldemo.dao;

import com.loya.onetomanybidirectionaldemo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDao extends JpaRepository<Book, Long> {

	@Query(value= "select u from Book u where u.genre = ?1")
	public ResponseEntity<Book> findByGenre(String genre);
}
