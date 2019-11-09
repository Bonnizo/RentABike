package com.example.rentabike.database;


import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.rentabike.database.dao.BikeDao;
import com.example.rentabike.database.dao.RentDao;
import com.example.rentabike.database.entity.BikeEntity;
import com.example.rentabike.database.entity.RentEntity;

/**
 * Création de la base de données "database" avec nos 2 tables bikes et rents
 * Il y a la possibilité d'insérer des données à la création avec la méthode populate
 *
 */
@Database(entities = {BikeEntity.class, RentEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    private static final String DB_NAME = "database.db";
    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DB_NAME)
                            .allowMainThreadQueries()
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    new PopulateDbAsync(INSTANCE).execute();
                                }
                            })
                            .build();
                }
            }
        }return INSTANCE;
    }

    public void clearDb() {
        if (INSTANCE != null) {
            new PopulateDbAsync(INSTANCE).execute();
        }
    }

    public abstract BikeDao bikeDao();
    public abstract RentDao rentDao();

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final BikeDao bikeDao;
        private final RentDao rentDao;

        public PopulateDbAsync(AppDatabase instance) {
            bikeDao = instance.bikeDao();
            rentDao = instance.rentDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            //rentDao.insert(new RentEntity("Yev", "Zaychenko", "12.03.2019", "04478475", 1 ));
            //rentDao.insert(new RentEntity("Victor", "Bonny", "12.05.5274", "0789547898", 2 ));
            return null;
        }
    }
}