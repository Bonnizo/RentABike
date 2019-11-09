package com.example.rentabike.ui.Bike;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.rentabike.R;
import com.example.rentabike.ui.Rent.AddRentActivity;


/**
 * Class ShowBikeActivity qui permet d'afficher un bike.
 * Reçoit les données de la part de BikeActivity, les affiche et donne la possibilité de rajouter une réservation
 *
 */
public class ShowBikeActivity extends AppCompatActivity  {

    public static final String SHOW_EXTRA_ID =
            "com.example.rentabike.ui.Bike.EDIT_EXTRA_ID";
    public static final String SHOW_EXTRA_NAME =
            "com.example.rentabike.ui.Bike.EDIT_EXTRA_NAME";
    public static final String SHOW_EXTRA_DESCRIPTION =
            "com.example.rentabike.ui.Bike.EDIT_EXTRA_DESCRIPTION";
    public static final String SHOW_EXTRA_SIZE =
            "com.example.rentabike.ui.Bike.EDIT_EXTRA_SIZE";
    public static final String SHOW_EXTRA_PICTURE =
            "com.example.rentabike.ui.Bike.EDIT_EXTRA_PRICE";


    private TextView showTextName;
    private TextView showTextDescription;
    private TextView showTextSize;
    private ImageView imageViewPicture;
    private int idBike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bike);

        showTextName = findViewById(R.id.show_bike_name);
        showTextDescription = findViewById(R.id.show_bike_description);
        showTextSize = findViewById(R.id.show_bike_size);
        imageViewPicture = findViewById(R.id.imageViewEdit);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();

        setTitle("Bike details");

        //passer les infos du bike
        showTextName.setText(intent.getStringExtra(SHOW_EXTRA_NAME));
        showTextDescription.setText(intent.getStringExtra(SHOW_EXTRA_DESCRIPTION));
        showTextSize.setText(intent.getStringExtra(SHOW_EXTRA_SIZE));
        idBike = intent.getIntExtra(SHOW_EXTRA_ID,0);
        byte[] tempArray = intent.getByteArrayExtra(SHOW_EXTRA_PICTURE);

        if (tempArray != null) {
            Bitmap compressedBitmap = BitmapFactory.decodeByteArray(tempArray, 0, tempArray.length);
            imageViewPicture.setImageBitmap(compressedBitmap);
        }

        Button buttonAddRent = findViewById(R.id.button_new_rent);
        buttonAddRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ShowBikeActivity.this, AddRentActivity.class);
                intent.putExtra(AddRentActivity.RENT_EXTRA_ID_BIKE, idBike);
                startActivity(intent);
            }
        });
    }
}