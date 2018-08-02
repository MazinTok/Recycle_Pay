package com.mazinaltokhais.hajjhackathon;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    CodeScannerView scannerView;
    TextView BottleCountText ;//= (TextView) findViewById(R.id.BottleCount);
    TextView WalletText ;//= (TextView) findViewById(R.id.BottleCount);
    private ImageButton ScannerBtn;
    private ImageButton MapBtn;

    private static final String TAG ="testFB" ;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        InitioalViewas();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
        ReadFromFB_Bottles();
        ReadFromFB_Wallet();

        ScannerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CAMERA}, 100);
                    }
                    else {
                        // Already grant Camera permission. Now call your QR scan Activity
                        Intent myIntent = new Intent(MainActivity.this, ScannerActivity.class);
                        //  myIntent.putExtra("key", value); //Optional parameters
                      //  MainActivity.this.startActivity(myIntent);
                        startActivityForResult(myIntent, 1);
                        //Scanner();
                    }
                }else {
//                    // call your QR scan Activity
//                    Intent myIntent = new Intent(MainActivity.this, ScannerActivity.class);
//                    //  myIntent.putExtra("key", value); //Optional parameters
//                    MainActivity.this.startActivity(myIntent);
//                    Scanner();
                    Intent myIntent = new Intent(MainActivity.this, ScannerActivity.class);
                  //  MainActivity.this.startActivity(myIntent);
                    startActivityForResult(myIntent, 1);
                }

            }
        });

        MapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CAMERA}, 100);
                    }
                    else {
                        // Already grant Camera permission. Now call your QR scan Activity
                        Intent myIntent = new Intent(MainActivity.this, MapsActivity.class);
                        //  myIntent.putExtra("key", value); //Optional parameters
                        //  MainActivity.this.startActivity(myIntent);
                        startActivityForResult(myIntent, 1);
                        //Scanner();
                    }
                }else {
//                    // call your QR scan Activity
//                    Intent myIntent = new Intent(MainActivity.this, ScannerActivity.class);
//                    //  myIntent.putExtra("key", value); //Optional parameters
//                    MainActivity.this.startActivity(myIntent);
//                    Scanner();
                    Intent myIntent = new Intent(MainActivity.this, MapsActivity.class);
                    //  MainActivity.this.startActivity(myIntent);
                    startActivityForResult(myIntent, 1);
                }

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
             //   Toast.makeText(MainActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
                String Bottles = BottleCountText.getText().toString();
                int temp = Integer.parseInt(Bottles)+Integer.parseInt(result.toString());

                BottleCountText.setText(valueOf(temp));
                WriteTOFBBottles(temp);
                double tempWallet = temp * 0.05;
                tempWallet = Math.floor(tempWallet * 100) / 100;
                WalletText.setText(String.valueOf(tempWallet));
                WriteTOFBWallet(tempWallet);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult
    public void InitioalViewas()
    {
         BottleCountText = (TextView) findViewById(R.id.BottleCount);
        BottleCountText.setText("0");

        WalletText = (TextView) findViewById(R.id.WalletText);
        WalletText.setText("0");

        ScannerBtn = (ImageButton)findViewById(R.id.ScannerBtn);
        MapBtn = (ImageButton)findViewById(R.id.mapBtn);

         scannerView = (CodeScannerView)findViewById(R.id.scanner_view_main);

        TextView BottleCountText ;//= (TextView) findViewById(R.id.BottleCount);
    }
    public void ReadFromFB_Bottles()
    {

        // Read from the database
        Query myTopPostsQuery = myRef.child("Users").child(Build.SERIAL).child("Bottles");//.equalTo(mTree.getmCountryName());
        myTopPostsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
        //myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()) {
                    String value = dataSnapshot.getValue().toString();
                    Log.d(TAG, "Value is: " + value);
                    BottleCountText.setText(value);
                }
                else {
                    WriteTOFB();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }

//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//             
//            }
        });
    }

    public void ReadFromFB_Wallet()
    {

        // Read from the database
        Query myTopPostsQuery = myRef.child("Users").child(Build.SERIAL).child("Wallet");//.equalTo(mTree.getmCountryName());
        myTopPostsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            //myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()) {
                    String value = dataSnapshot.getValue().toString();
                    Log.d(TAG, "Value is: " + value);
                    WalletText.setText(value);
                }
                else {
                    WriteTOFB();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }

//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//
//            }
        });
    }

    public void WriteTOFBBottles(int val)
    {

        myRef.child("Users").child(Build.SERIAL).child("Bottles").setValue(val);

    }
    public void WriteTOFBWallet(double val)
    {

        myRef.child("Users").child(Build.SERIAL).child("Wallet").setValue(val);

    }
    public void WriteTOFB()
    {
        //myRef.child("Users").setValue(Build.SERIAL);
        myRef.child("Users").child(Build.SERIAL).child("Bottles").setValue(0);
        myRef.child("Users").child(Build.SERIAL).child("Wallet").setValue(0.0);
        myRef.child("Users").child(Build.SERIAL).child("Name").setValue("Mazin");

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
    public void Scanner()
    {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) this, android.Manifest.permission.CAMERA)) {

                //Show permission dialog
            } else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((Activity)this, new String[]{android.Manifest.permission.CAMERA}, 100);
            }
        }
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

    }
}
