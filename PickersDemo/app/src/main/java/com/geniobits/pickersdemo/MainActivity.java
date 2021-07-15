package com.geniobits.pickersdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.geniobits.mediachooserdialog.MediaChooser.MediaChooserDialog;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                /* ... */
                token.continuePermissionRequest();
            }
        }).check();
        Button time_button = findViewById(R.id.time_button);
        time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                        Toast.makeText(MainActivity.this, "hr:"+hourOfDay+" min:"+minute+" sec:"+second, Toast.LENGTH_SHORT).show();
                    }
                }, false);
                timePickerDialog.show(getSupportFragmentManager(),"TimePickerDialog");
            }
        });


        Button date_button = findViewById(R.id.date_button);
        date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                Toast.makeText(MainActivity.this, "year:"+year+" month:"+monthOfYear
                                        +" day:"+dayOfMonth, Toast.LENGTH_SHORT).show();

                            }
                        },
                        now.get(Calendar.YEAR), // Initial year selection
                        now.get(Calendar.MONTH), // Initial month selection
                        now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );
// If you're calling this from a support Fragment
                dpd.show(getSupportFragmentManager(), "Datepickerdialog");
            }
        });
        Button image_button = findViewById(R.id.image_button);
        ImageView imageView = findViewById(R.id.imageView);
        image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaChooserDialog mediaChooserDialog = new MediaChooserDialog(MainActivity.this, new MediaChooserDialog.MediaSelectionListener() {
                    @Override
                    public void onMediaSelected(Uri uri, String path) {
                        //uri is file uri
                        //path is file path
                        Picasso.get().load(uri).into(imageView);
                    }
                });
                mediaChooserDialog.showPictureDialog();
            }
        });
        Button video_button = findViewById(R.id.video_button);
        video_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaChooserDialog mediaChooserDialog = new MediaChooserDialog(MainActivity.this, new MediaChooserDialog.MediaSelectionListener() {
                    @Override
                    public void onMediaSelected(Uri uri, String path) {
                        //uri is file uri
                        //path is file path
                        Toast.makeText(MainActivity.this, path, Toast.LENGTH_SHORT).show();
                    }
                });
                mediaChooserDialog.showVideoDialog();
            }
        });
        Button file_button = findViewById(R.id.file_button);
        file_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, 102);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode==102) {
            // Get the Uri of the selected file
            Uri uri = data.getData();
            Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();
            File directoryMain = getExternalFilesDir("Media");
            if (!directoryMain.exists()) {
                boolean success = directoryMain.mkdirs();
                Log.e("makedir",String.valueOf(success));
            }

            String fileName = "Filename.jpg";
            File end_file = new File(directoryMain + "/" + fileName);
            try {
                copy(uri, end_file, getApplicationContext());
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("copy error", e.toString());
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public static void copy(Uri src, File dst, Context context) throws IOException {
        try (InputStream in = context.getContentResolver().openInputStream(src);  ) {
            try (OutputStream out = new FileOutputStream(dst)) {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
        }
    }
}