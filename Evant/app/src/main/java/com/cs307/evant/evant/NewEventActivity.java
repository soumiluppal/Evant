package com.cs307.evant.evant;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;
import android.os.Environment;

import com.google.firebase.auth.FirebaseAuth;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import static com.cs307.evant.evant.MainActivity.db;

public class NewEventActivity extends AppCompatActivity {

    boolean priv = false;
    String title = "";
    String address = "";
    String description = "";
    String dttime;
    Uri image;
    ImageView imageView;
    Double lat;
    Double lng;
    File img;
    Bitmap bm;
    boolean latlngCheck = false;
    HashMap<String, Boolean> checkButtons = new HashMap<>();
    boolean first = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        final ImageView emage = (ImageView) findViewById(R.id.eventImage);
        final Calendar calendar = Calendar.getInstance();
        final Calendar clock = Calendar.getInstance();
        final EditText dText = (EditText) findViewById(R.id.dateText);
        final EditText tText = (EditText) findViewById(R.id.timeText);
        final EditText titleText = (EditText) findViewById(R.id.titleText);
        final EditText addrText = (EditText) findViewById(R.id.locationText);
        final EditText descText = (EditText) findViewById(R.id.descriptionText);
        final Switch prSwitch = (Switch) findViewById(R.id.privSwitch);
        final Button catButton = (Button) findViewById(R.id.catButton);


        //UNCOMMENT TO TEST IMAGE RETRIEVAL
        /*ImageView imageViewer = (ImageView) findViewById(R.id.testimage);
        ArrayList<Bitmap> images = db.getImage();
        imageViewer.setImageBitmap(images.get(15));
        */

        final Button setLocButton = (Button) findViewById(R.id.setLocButton);

        Button fab = (Button) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(latlngCheck == false){
                    Toast.makeText(getApplicationContext(), "Please specify the location", Toast.LENGTH_SHORT).show();
                }
                else {
                    title = titleText.getText().toString();
                    address = addrText.getText().toString();
                    description = descText.getText().toString();
                    dttime = dText.getText().toString() + "   " + tText.getText().toString();
                    priv = prSwitch.isChecked();
                    if (FirebaseAuth.getInstance().getUid() != null) {
                        if (title.length() > 0 && address.length() > 0 && description.length() > 0 && dText.getText().length() > 0 && tText.getText().length() > 0) {
                            Snackbar.make(view, "Event added", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            db.addEvent(title, address, description, dttime, FirebaseAuth.getInstance().getUid(), lat, lng, bm);
                            Intent intent = new Intent(NewEventActivity.this, MainActivity.class);
                            startActivity(intent);

                        } else {
                            Snackbar.make(view, "Please fill out all fields", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    } else {
                        Toast.makeText(NewEventActivity.this, "You must sign in.",
                                Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(dText, calendar);
            }
        };

        dText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(NewEventActivity.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int min) {
                clock.set(Calendar.HOUR, hour);
                clock.set(Calendar.MINUTE, min);
                updateLabelTime(tText, clock);
            }
        };

        tText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(NewEventActivity.this, time, clock
                        .get(clock.HOUR), clock.get(clock.MINUTE),
                        false).show();
            }
        });

        setLocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //moveTaskToBack(true);
                Intent intent = new Intent(NewEventActivity.this, MapSelectView.class);
                //startActivity(intent);
                startActivityForResult(intent, 101);
            }
        });

        catButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //moveTaskToBack(true);
                Intent intent = new Intent(NewEventActivity.this, CatActivity.class);
                //startActivity(intent);
                intent.putExtra("map", checkButtons);
                startActivityForResult(intent, 102);
                first = false;
            }
        });

        emage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(NewEventActivity.this);
                alertDialog.setTitle("Upload image");
                alertDialog.setMessage("Choose one of the following to upload a picture for the event.");
                alertDialog.setPositiveButton("From camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        imageView = emage;
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        //File file = Environment.getExternalStorageDirectory();
                        img = null;
                        try {
                            img = createImageFile();
                            if(img == null) {
                                Toast.makeText(NewEventActivity.this, "It is null", Toast.LENGTH_SHORT).show();
                            }
                            Uri photoURI = FileProvider.getUriForFile(NewEventActivity.this, "com.cs307.evant.evant.fileprovider", img);

                            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            startActivityForResult(intent, 0);
                        } catch (IOException e) {

                        }
                    }
                });
                alertDialog.setNegativeButton("From gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        imageView = emage;
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 1);
                    }
                });
                alertDialog.show();
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0) {
            Uri imageUri = Uri.parse(img.toString());
            image = imageUri;
            imageView.setImageURI(imageUri);
            bm = cameraToBitmap(img);

        } else if (resultCode == RESULT_OK && requestCode == 1) {
            Uri imageUri = data.getData();
            image = imageUri;
            imageView.setImageURI(imageUri);
            bm = galleryToBitmap(imageUri);
        }
        if(resultCode == RESULT_OK && requestCode == 101){
            lat = data.getDoubleExtra("LAT", 0.0);
            lng = data.getDoubleExtra("LNG", 0.0);
            latlngCheck = data.getBooleanExtra("LOCCHECK", false);
        }
        if(resultCode == RESULT_OK && requestCode == 102) {
            //Intent intent = getIntent();
            //Toast.makeText(NewEventActivity.this, "HELLO", Toast.LENGTH_SHORT);
            checkButtons = (HashMap<String, Boolean>)data.getSerializableExtra("map");
        }
    }

    private void updateLabel(EditText dText, Calendar calendar) {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dText.setText(sdf.format(calendar.getTime()));
    }

    private void updateLabelTime(EditText tText, Calendar clock) {
        String myFormat = "hh:mm aa"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        tText.setText(sdf.format(clock.getTime()));
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    Bitmap galleryToBitmap(Uri uri)
    {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            return bitmap;
        }

        catch(IOException e){}
        return null;
    }
    Bitmap cameraToBitmap(File imageFile){
        byte[] fileData = new byte[(int) imageFile.length()];
        try {
            DataInputStream dis = new DataInputStream(new FileInputStream(imageFile));
            dis.readFully(fileData);
            dis.close();
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inSampleSize = 2;  //you can also calculate your inSampleSize
            options.inJustDecodeBounds = false;
            options.inTempStorage = new byte[16 * 1024];
            Bitmap bitmap = BitmapFactory.decodeByteArray(fileData, 0, fileData.length, options);
            return bitmap;
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException ie){
            ie.printStackTrace();
        }
        return null;
    }
}

