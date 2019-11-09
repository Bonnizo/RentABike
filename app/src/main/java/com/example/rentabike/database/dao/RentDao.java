package com.example.rentabike.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Query;
import com.example.rentabike.database.entity.RentEntity;

import java.util.List;

/**
 * Interface avec les méthodes nécessaire pour enregistrer et/ou trouver des infos sur une réservation dans la base de données
 */
@Dao
public interface RentDao {

    @Insert
    void insert(RentEntity rent);

    @Update
    void update(RentEntity rent);

    @Delete()
    void delete(RentEntity rent);

    @Query("DELETE FROM rents")
    void deleteAllRents();

    @Query("SELECT * FROM rents")
    LiveData<List<RentEntity>> getAllRents();

    @Query("SELECT * FROM rents WHERE rentId = :id LIMIT 1")
    RentEntity findRenteById(int id);

    @Query("SELECT * FROM rents WHERE dateRent = :dateRent AND bikeRented_id = :bikeRentedId LIMIT 1")
    RentEntity findRentExist(String dateRent, int bikeRentedId);


}
