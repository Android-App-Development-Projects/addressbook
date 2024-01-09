package com.example.addressbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DisplayContact extends AppCompatActivity implements View.OnClickListener {

    //Change name of below variable to small and meaningful varible name
    int from_Where_I_Am_Coming = 0;
    private DatabaseHelper myDataBase;
    Button saveContactBtn;

    EditText nameET;
    EditText phoneET;
    EditText emailET;
    EditText addressET;
    EditText cityET;

    int id_To_Update = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        nameET = findViewById(R.id.editTextName);
        phoneET = findViewById(R.id.editTextPhoneNo);
        emailET = findViewById(R.id.editTextEmail);
        addressET = findViewById(R.id.editTextAddress);
        cityET = findViewById(R.id.editTextCity);

        saveContactBtn = findViewById(R.id.saveContactButton);
        saveContactBtn.setOnClickListener(this);

        myDataBase = new DatabaseHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            int value = extras.getInt(("id"));

            if(value>0){
                //Means veiw address case not not add address case
                Cursor rs = myDataBase.getData(value);
                id_To_Update = value;
                rs.moveToFirst();


                //Change below variable names to meaningful variable names.
                @SuppressLint("Range") String nameFromDB = rs.getString(rs.getColumnIndex(DatabaseHelper.ADDRESSBOOK_COLUMN_NAME));
                @SuppressLint("Range") String phoneFromDB = rs.getString(rs.getColumnIndex(DatabaseHelper.ADDRESSBOOK_COLUMN_PHONE));
                @SuppressLint("Range") String emailFromDB = rs.getString(rs.getColumnIndex(DatabaseHelper.ADDRESSBOOK_COLUMN_EMAIL));
                @SuppressLint("Range") String addressFromDB = rs.getString(rs.getColumnIndex(DatabaseHelper.ADDRESSBOOK_COLUMN_ADDRESS));
                @SuppressLint("Range") String cityFromDB = rs.getString(rs.getColumnIndex(DatabaseHelper.ADDRESSBOOK_COLUMN_CITY));

                if(!rs.isClosed()){
                    rs.close();
                }


                saveContactBtn.setVisibility(View.INVISIBLE);

                nameET.setText((CharSequence) nameFromDB);
                nameET.setFocusable(false);
                nameET.setClickable(false);

                phoneET.setText((CharSequence) phoneFromDB);
                phoneET.setFocusable(false);
                phoneET.setClickable(false);

                emailET.setText((CharSequence) emailFromDB);
                emailET.setFocusable(false);
                emailET.setClickable(false);

                addressET.setText((CharSequence) addressFromDB);
                addressET.setFocusable(false);
                addressET.setClickable(false);

                cityET.setText((CharSequence) cityFromDB);
                cityET.setFocusable(false);
                cityET.setClickable(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflates the menu
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            int value = extras.getInt("id");
            if(value>0){
                getMenuInflater().inflate(R.menu.display_contact, menu);
            }
            else{
                getMenuInflater().inflate(R.menu.main_menu, menu);
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.Edit_Contact){
            Button saveContactBtn = findViewById(R.id.saveContactButton);
            saveContactBtn.setVisibility(View.VISIBLE);

            nameET.setEnabled(true);
            nameET.setFocusableInTouchMode(true);
            nameET.setClickable(true);

            phoneET.setEnabled(true);
            phoneET.setFocusableInTouchMode(true);
            phoneET.setClickable(true);

            emailET.setEnabled(true);
            emailET.setFocusableInTouchMode(true);
            emailET.setClickable(true);

            addressET.setEnabled(true);
            addressET.setFocusableInTouchMode(true);
            addressET.setClickable(true);

            cityET.setEnabled(true);
            cityET.setFocusableInTouchMode(true);
            cityET.setClickable(true);
            return true;
        } else if (item.getItemId() == R.id.Delete_Contact) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.deleteContact)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            myDataBase.deleteAddress(id_To_Update);
                            Toast.makeText(getApplicationContext(), "DELETED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // User cancelled the dialog
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.setTitle("Are you sure?");
            alertDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
            Bundle extras = getIntent().getExtras();
            if(extras != null){
                int value = extras.getInt("id");
                if (value>0){
                    if(myDataBase.updateAddress(id_To_Update, nameET.getText().toString(),
                            phoneET.getText().toString(), emailET.getText().toString(),
                            addressET.getText().toString(), cityET.getText().toString())){
                        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Not Updated", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    if(myDataBase.insertAddress(nameET.getText().toString(),
                            phoneET.getText().toString(), emailET.getText().toString(),
                            addressET.getText().toString(), cityET.getText().toString())){
                        Toast.makeText(getApplicationContext(), "Address Added", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Address Not Added", Toast.LENGTH_SHORT).show();
                    }
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }
}