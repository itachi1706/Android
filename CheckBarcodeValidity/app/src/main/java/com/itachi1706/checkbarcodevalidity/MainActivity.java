package com.itachi1706.checkbarcodevalidity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

    private Button scanBtn, scanBtn2;
    private TextView tv1,tv2,tvResults;
    private boolean scannedSecond = false;
    private String value1, value2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.value1 = "";
        this.value2 = "";
        this.scanBtn = (Button) this.findViewById(R.id.btnScan);
        this.scanBtn2 = (Button) this.findViewById(R.id.scanBtn2);
        this.scanBtn2.setVisibility(View.INVISIBLE);
        this.tv1 = (TextView) this.findViewById(R.id.bc1Stat);
        this.tv2 = (TextView) this.findViewById(R.id.bc2Stat);
        this.tvResults = (TextView) this.findViewById(R.id.chkResult);
        this.scanBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("Button:", "Launching scanner...");
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.initiateScan();
                Log.d("Button: ", "Scan 1 completed.");
            }
        });
        this.scanBtn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("Button:", "Launching scanner...");
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.initiateScan();
                Log.d("Button: ", "Scan 1 completed.");
            }
        });
    }

    private void changeBtn(){
        if (scannedSecond) {
            this.scanBtn.setVisibility(View.INVISIBLE);
            this.scanBtn2.setVisibility(View.VISIBLE);
        } else {
            this.scanBtn.setVisibility(View.VISIBLE);
            this.scanBtn2.setVisibility(View.INVISIBLE);
            if (!this.value1.equals("-1") && !this.value2.equals("-1")){
                determineMatch();
            }
            this.value1 = "";
            this.value2 = "";
        }
    }

    private void determineMatch(){
        if (this.value1.equals(this.value2)){
            this.tvResults.setTextColor(Color.GREEN);
            this.tvResults.setText("Barcode Matches!");
        } else {
            this.tvResults.setTextColor(Color.RED);
            this.tvResults.setText("Barcode Does Not Match!");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        Log.d("Result: ", "Parsing result...");
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null){
            Log.d("Result: ", "There is a scan result! Checking content...");
            String contents = scanResult.getContents();
            Log.d("Result: ", "Gotha Contents!");
            if (contents != null){
                Log.d("Result: ", "There is a content in it! Parsing content...");
                if (this.scannedSecond){
                    Log.d("Ver: ", "Barcode 2");
                    Log.d("Result: ", "Content found! Appending string...");
                    this.tv2.setText(scanResult.toString());
                    Log.d("Insert: ", "Obtaining values content...");
                    String con = scanResult.getContents();
                    Log.d("Parse: ", "Parsing Content String");
                    this.value2 = con;
                    this.scannedSecond = false;
                    this.changeBtn();
                }
                else {
                    Log.d("Ver: ", "Barcode 1");
                    Log.d("Result: ", "Content found! Appending string...");
                    this.tv2.setText("");
                    this.tvResults.setText("");
                    this.tv1.setText(scanResult.toString());
                    Log.d("Insert: ", "Obtaining values content...");
                    String con = scanResult.getContents();
                    Log.d("Parse: ", "Parsing Content String");
                    this.value1 = con;
                    this.scannedSecond = true;
                    this.changeBtn();
                }
            }
        }
    }
}
