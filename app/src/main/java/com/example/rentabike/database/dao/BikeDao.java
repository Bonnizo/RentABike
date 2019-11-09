package com.example.rentabike.database.dao;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Query;
import com.example.rentabike.database.entity.BikeEntity;
import java.util.List;

/**
 * Interface avec les méthodes nécessaire pour enregistrer et/ou trouver des infos sur un bike dans la base de données
 */
@Dao
public interface BikeDao {


    @Insert
    void insert(BikeEntity bike);

    @Update
    void update(BikeEntity bike);

    @Delete()
    void delete(BikeEntity bike);

    @Query("DELETE FROM bikes")
    void deleteAllBikes();

    @Query("SELECT * FROM bikes")
    LiveData<List<BikeEntity>> getAllBikes();

    @Query("SELECT * FROM bikes WHERE id = :id LIMIT 1")
    BikeEntity findBikeById(int id);










}
