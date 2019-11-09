package com.example.rentabike.database.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Class RentEntity pour la création de la table rents dans la base de données
 * Il y a également le constructeur pour pouvoir créer des réservations dans le programme
 * et les get/set afin de passer ou recevoir les paramètres des réservations
 */


@Entity(tableName = "rents",
        foreignKeys =
        @ForeignKey(
                entity = BikeEntity.class,
                 parentColumns = "id",
                    childColumns = "bikeRented_id",
                    onDelete = ForeignKey.CASCADE

        ),indices = @Index(value = {"bikeRented_id"})


)

public class RentEntity {

    @PrimaryKey(autoGenerate = true)
    private int rentId;
    private String firstNameClient;
    private String lastNameClient;
    private String clientNumber;
    private String dateRent;

  @ColumnInfo(name = "bikeRented_id")
    private int bikeRentedId;


    public RentEntity(String firstNameClient, String lastNameClient, String dateRent, String clientNumber,  int bikeRentedId) {
        this.firstNameClient = firstNameClient;
        this.lastNameClient = lastNameClient;
        this.dateRent = dateRent;
        this.clientNumber = clientNumber;
        this.bikeRentedId = bikeRentedId;
    }

    public int getRentId() {
        return rentId;
    }

    public void setRentId(int rentId) {
        this.rentId = rentId;
    }

    public String getFirstNameClient() {
        return firstNameClient;
    }

    public String getLastNameClient() {
        return lastNameClient;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public String getDateRent() {
        return dateRent;
    }

    public int getBikeRentedId() {
        return bikeRentedId;
    }

    public void setbikeRentedId(int bikeRentedId) {
        this.bikeRentedId = bikeRentedId;
    }
}