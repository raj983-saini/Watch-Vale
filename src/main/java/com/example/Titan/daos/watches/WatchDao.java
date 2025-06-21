package com.example.Titan.daos.watches;

import com.example.Titan.entity.watches.Watches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WatchDao extends JpaRepository<Watches ,Long> {
    @Query(value = """
  SELECT * FROM watches 
  WHERE :firstWord IS NULL 
     OR TRIM(:firstWord) = '' 
     OR LOWER(REPLACE(watch_name, ' ', '')) LIKE LOWER(CONCAT(:firstWord, '%'))
""", nativeQuery = true)
    List<Watches> searchByFirstWord(@Param("firstWord") String firstWord);

    @Query("SELECT DISTINCT w FROM Watches w JOIN w.tags t WHERE t.tag IN :tags")
    List<Watches> findByTagNames(@Param("tags") List<String> tags);

    @Query("SELECT w.price FROM Watches w WHERE w.id = :watchId")
    Double findPriceByWatchId(@Param("watchId") Long watchId);


}
