package com.example.rentabike.database.repository;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.example.rentabike.database.AppDatabase;
import com.example.rentabike.database.dao.RentDao;
import com.example.rentabike.database.entity.RentEntity;
import java.util.List;

/**
 * Class RentRepository permet d'effectuer les actions
 * On peut faire les méthodes update/delete/insert sans devoir mettre en pause le thread principal
 * Avec un appl de méthode insert/delete/insert, on lance une méthode AsyncTask qui exécutera en background la méthode rattachée.
 * Cela ne ralenti pas le programme et effectue les fonctions appelées.
 *
 */
public class RentRepository {

    private RentDao rentDao;
    private LiveData<List<RentEntity>> allRents;

    public RentRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        rentDao = database.rentDao();
        allRents = rentDao.getAllRents();
    }



    public void insert(RentEntity rent) {
        new InsertRentAsyncTask(rentDao).execute(rent);
    }

    public void update(RentEntity rent) {
        new UpdateRentAsyncTask(rentDao).execute(rent);
    }

    public void delete(RentEntity rent) {new DeleteRentAsyncTask(rentDao).execute(rent); }

    public void deleteAllBikes() {
        new DeleteAllRentsAsyncTask(rentDao).execute();
    }

    public LiveData<List<RentEntity>> getAllRents() {
        return allRents;
    }


    private static class InsertRentAsyncTask extends AsyncTask<RentEntity, Void, Void> {
        private RentDao rentDao;
        private InsertRentAsyncTask(RentDao rentDao) {
            this.rentDao = rentDao;
        }
        protected Void doInBackground(RentEntity... rentEntities) {

            rentDao.insert(rentEntities[0]);
            return null;
        }
    }

    private static class UpdateRentAsyncTask extends AsyncTask<RentEntity, Void, Void> {
        private RentDao rentDao;
        private UpdateRentAsyncTask(RentDao rentDao) {
            this.rentDao = rentDao;
        }
        protected Void doInBackground(RentEntity... rentEntities) {

            rentDao.update(rentEntities[0]);
            return null;
        }
    }

    private static class DeleteRentAsyncTask extends AsyncTask<RentEntity, Void, Void> {
        private RentDao rentDao;
        private DeleteRentAsyncTask(RentDao rentDao) {
            this.rentDao = rentDao;
        }
        protected Void doInBackground(RentEntity... rentEntities) {

            rentDao.delete(rentEntities[0]);
            return null;
        }
    }

    private static class DeleteAllRentsAsyncTask extends AsyncTask<Void, Void, Void> {
        private RentDao rentDao;
        private DeleteAllRentsAsyncTask(RentDao rentDao) {
            this.rentDao = rentDao;
        }
        protected Void doInBackground(Void... voids) {

            rentDao.deleteAllRents();
            return null;
        }
    }
}
