package com.example.rentabike.database.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Class BikeEntity pour la création de la table bikes dans la base de données
 * Il y a également le constructeur pour pouvoir créer des bikes dans le programme
 * et les get/set afin de passer ou recevoir les paramètres des bikes
 */

@Entity(tableName = "bikes")
public class BikeEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String description;
    private String size;
    private byte[] bikePicture;

    public BikeEntity(String name, String description, String size,byte[]bikePicture) {
        this.name = name;
        this.description = description;
        this.size = size;
        this.bikePicture = bikePicture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSize() {
        return size;
    }

    public byte[] getBikePicture() {
        return bikePicture;
    }

}
