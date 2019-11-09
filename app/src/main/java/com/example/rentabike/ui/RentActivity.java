package com.example.rentabike.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;
import com.example.rentabike.R;
import com.example.rentabike.adapter.RentAdapter;
import com.example.rentabike.database.AppDatabase;
import com.example.rentabike.database.entity.BikeEntity;
import com.example.rentabike.database.entity.RentEntity;
import com.example.rentabike.ui.Bike.ShowBikeActivity;
import com.example.rentabike.ui.Rent.EditRentActivity;
import com.example.rentabike.viewmodel.RentViewModel;
import java.util.List;


/**
 * Class RentActivity qui affiche la liste de réservation et sert de base pour effectuer les différents modifications
 * de réservation dans le programme.
 * Permet de naviguer sur les autres actions disponibles pour la réservation depuis chaque réservation item dans la liste
 * La liste update si on a un onchange. Possibilité d'edit une réservation également en récupérant les données de EditRentActivity
 *
 *
 */
public class RentActivity extends AppCompatActivity {


    public static final int EDIT_RENT_REQUEST = 2;
    private  RentViewModel rentListViewModel;
    RentAdapter rentAdapter = new RentAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);
        setTitle("My rentals");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(rentAdapter);

        AppDatabase appDb = AppDatabase.getInstance(this);

        rentListViewModel = ViewModelProviders.of(this).get(RentViewModel.class);
        rentListViewModel.getAllrents().observe(this, new Observer<List<RentEntity>>() {
            @Override
            public void onChanged(@Nullable List<RentEntity> rentEntities) {
                rentAdapter.setrentEntities(rentEntities);
            }
        });


        rentAdapter.setOnItemClickListener(new RentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RentEntity rent) {
            }

            public void onEditClick(RentEntity rent) {

                Intent intent = new Intent(RentActivity.this, EditRentActivity.class);

                intent.putExtra(EditRentActivity.EDIT_EXTRA_FIRSTNAME, rent.getFirstNameClient());
                intent.putExtra(EditRentActivity.EDIT_EXTRA_LASTNAME, rent.getLastNameClient());
                intent.putExtra(EditRentActivity.EDIT_EXTRA_DATE, rent.getDateRent());
                intent.putExtra(EditRentActivity.EDIT_EXTRA_NUMBER, rent.getClientNumber());
                intent.putExtra(EditRentActivity.EDIT_EXTRA_RENTID, rent.getRentId());
                intent.putExtra(EditRentActivity.EDIT_EXTRA_RENTBIKEID, rent.getBikeRentedId());

                startActivityForResult(intent, EDIT_RENT_REQUEST);
            }

            public void onDeleteClick(RentEntity rent) {
                AlertDialog diaBox = AskOption(rent);
                diaBox.show();
            }

            @Override
            public void showBikeRented(RentEntity rent) {

                int id = rent.getBikeRentedId();

                BikeEntity bike= appDb.bikeDao().findBikeById(id);

                if (bike == null) {
                    Toast.makeText(RentActivity.this, "The bike is no longer for rent, you can cancel the reservation", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(RentActivity.this, ShowBikeActivity.class);

                intent.putExtra(ShowBikeActivity.SHOW_EXTRA_NAME, bike.getName());
                intent.putExtra(ShowBikeActivity.SHOW_EXTRA_DESCRIPTION,bike.getDescription());
                intent.putExtra(ShowBikeActivity.SHOW_EXTRA_SIZE, bike.getSize());
                intent.putExtra(ShowBikeActivity.SHOW_EXTRA_PICTURE, bike.getBikePicture());
                intent.putExtra(ShowBikeActivity.SHOW_EXTRA_ID, bike.getId());

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == EDIT_RENT_REQUEST && resultCode == RESULT_OK) {
            int idRent = data.getIntExtra(EditRentActivity.EDIT_EXTRA_RENTID, -1);
            int idBikeRented = data.getIntExtra(EditRentActivity.EDIT_EXTRA_RENTBIKEID, -1);

            if (idRent == -1 ) {
                Toast.makeText(this, "There is a problem, rent not updated", Toast.LENGTH_SHORT).show();
                return;
            }
            if (idBikeRented == -1 ) {
                Toast.makeText(this, "There is no bike rented", Toast.LENGTH_SHORT).show();
                return;
            }

            String firstname = data.getStringExtra(EditRentActivity.EDIT_EXTRA_FIRSTNAME);
            String lastname = data.getStringExtra(EditRentActivity.EDIT_EXTRA_LASTNAME);
            String date = data.getStringExtra(EditRentActivity.EDIT_EXTRA_DATE);
            String number = data.getStringExtra(EditRentActivity.EDIT_EXTRA_NUMBER);

            RentEntity rentEntity = new RentEntity(firstname, lastname, date, number, idBikeRented);
            rentEntity.setRentId(idRent);

            if (rentEntity.getFirstNameClient() == null && rentEntity.getLastNameClient() == null && rentEntity.getDateRent() == null && rentEntity.getClientNumber() == null) {
                rentListViewModel.delete(rentEntity);
            } else {
                rentListViewModel.update(rentEntity);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main2, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                rentAdapter.getFilter().filter(newText);;
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_help:
                Intent intentHelp = new Intent(this, AboutActivity.class);
                this.startActivity(intentHelp);
                break;
            case R.id.action_settings:
                Intent intentSettings = new Intent(this, SettingsActivity.class);
                this.startActivity(intentSettings);
                break;
            case R.id.action_bike:
                Intent intentRent = new Intent(this, BikeActivity.class);
                this.startActivity(intentRent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private AlertDialog AskOption(RentEntity rent)
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete ?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        dialog.dismiss();
                        rentListViewModel.delete(rent);
                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }


}
