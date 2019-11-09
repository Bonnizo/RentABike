package com.example.rentabike.database.repository;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.example.rentabike.database.AppDatabase;
import com.example.rentabike.database.dao.BikeDao;
import com.example.rentabike.database.entity.BikeEntity;
import java.util.List;

/**
 * Class BikeRepository permet d'effectuer les actions
 * On peut faire les méthodes update/delete/insert sans devoir mettre en pause le thread principal
 * Avec un appl de méthode insert/delete/insert, on lance une méthode AsyncTask qui exécutera en background la méthode rattachée.
 * Cela ne ralenti pas le programme et effectue les fonctions appelées.
 *
 */
public class BikeRepository {

    private BikeDao bikeDao;
    private LiveData<List<BikeEntity>> allBikes;

    public BikeRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        bikeDao = database.bikeDao();
        allBikes = bikeDao.getAllBikes();
    }


    public void insert(BikeEntity bike) {
        new InsertBikeAsyncTask(bikeDao).execute(bike);
    }

    public void update(BikeEntity bike) {
        new UpdateBikeAsyncTask(bikeDao).execute(bike);
    }

    public void delete(BikeEntity bike) {
        new DeleteBikeAsyncTask(bikeDao).execute(bike);
    }

    public void deleteAllBikes() {
        new DeleteAllBikesAsyncTask(bikeDao).execute();
    }

    public LiveData<List<BikeEntity>> getAllBikes() {
        return allBikes;
    }

    private static class InsertBikeAsyncTask extends AsyncTask<BikeEntity, Void, Void> {
        private BikeDao bikeDao;
        private InsertBikeAsyncTask(BikeDao bikeDao) {
            this.bikeDao = bikeDao;
        }
        protected Void doInBackground(BikeEntity... bikeEntities) {

            bikeDao.insert(bikeEntities[0]);
            return null;
        }
    }

    private static class UpdateBikeAsyncTask extends AsyncTask<BikeEntity, Void, Void> {
        private BikeDao bikeDao;
        private UpdateBikeAsyncTask(BikeDao bikeDao) {
            this.bikeDao = bikeDao;
        }
        protected Void doInBackground(BikeEntity... bikeEntities) {

            bikeDao.update(bikeEntities[0]);
            return null;
        }
    }

    private static class DeleteBikeAsyncTask extends AsyncTask<BikeEntity, Void, Void> {
        private BikeDao bikeDao;
        private DeleteBikeAsyncTask(BikeDao bikeDao) {
            this.bikeDao = bikeDao;
        }
        protected Void doInBackground(BikeEntity... bikeEntities) {

            bikeDao.delete(bikeEntities[0]);
            return null;
        }
    }

    private static class DeleteAllBikesAsyncTask extends AsyncTask<Void, Void, Void> {
        private BikeDao bikeDao;
        private DeleteAllBikesAsyncTask(BikeDao bikeDao) {
            this.bikeDao = bikeDao;
        }
        protected Void doInBackground(Void... voids) {

            bikeDao.deleteAllBikes();
            return null;
        }
    }
}
