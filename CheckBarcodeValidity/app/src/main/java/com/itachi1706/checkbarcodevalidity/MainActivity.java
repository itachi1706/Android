package com.itachi1706.checkbarcodevalidity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;


public class MainActivity extends AppCompatActivity {

    private Button scanBtn, scanBtn2;
    private TextView tv1,tv2,tvResults;
    private boolean scannedSecond = false;
    private String value1, value2;
    boolean visionApi = true;
    private static String TAG = "vision";

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

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        visionApi = sp.getBoolean("google_vision", true);
        this.scanBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("Button:", "Launching scanner...");
                if (!visionApi) {
                    IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                    integrator.initiateScan();
                } else {
                    Intent intent = new Intent(MainActivity.this, BarcodeCaptureActivity.class);
                    intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
                    intent.putExtra(BarcodeCaptureActivity.UseFlash, false);
                    startActivityForResult(intent, RC_BARCODE_CAPTURE);
                }
                Log.d("Button: ", "Scan 1 completed.");
            }
        });
        this.scanBtn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("Button:", "Launching scanner...");
                if (!visionApi) {
                    IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                    integrator.initiateScan();
                } else {
                    Intent intent = new Intent(MainActivity.this, BarcodeCaptureActivity.class);
                    intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
                    intent.putExtra(BarcodeCaptureActivity.UseFlash, false);
                    startActivityForResult(intent, RC_BARCODE_CAPTURE);
                }
                Log.d("Button: ", "Scan 1 completed.");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        visionApi = sp.getBoolean("google_vision", true);
    }

    private static final int RC_BARCODE_CAPTURE = 9001;

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
            startActivity(new Intent(this, GeneralSettings.class));
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
        } else if (requestCode == RC_BARCODE_CAPTURE) {

            // Format, Content, Orientation, EC level
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (intent != null) {
                    Barcode barcode = intent.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    StringBuilder result = new StringBuilder();
                    result.append("Format: ").append(getFormatName(barcode.format)).append("\n");
                    result.append("Content: ").append(barcode.displayValue).append("\n");
                    result.append("Raw Value: ").append(barcode.rawValue).append("\n");
                    if (this.scannedSecond) {
                        this.value2 = barcode.rawValue;
                        this.scannedSecond = false;
                        this.tv2.setText(result);
                        this.changeBtn();
                    } else {
                        this.tv2.setText("");
                        this.tvResults.setText("");
                        this.value1 = barcode.rawValue;
                        this.scannedSecond = true;
                        this.tv1.setText(result);
                        this.changeBtn();
                    }
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                } else {
                    /*statusMessage.setText(R.string.barcode_failure);*/
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                /*statusMessage.setText(String.format(getString(R.string.barcode_error),
                        CommonStatusCodes.getStatusCodeString(resultCode)));*/
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }

    private String getFormatName(int format) {
        switch (format) {
            case Barcode.AZTEC: return "AZTEC";
            case Barcode.CODABAR: return "CODABAR";
            case Barcode.CODE_128: return "CODE_128";
            case Barcode.CODE_39: return "CODE_39";
            case Barcode.CODE_93: return "CODE_93";
            case Barcode.DATA_MATRIX: return "DATA_MATRIX";
            case Barcode.EAN_13: return "EAN_13";
            case Barcode.EAN_8: return "EAN_8";
            case Barcode.ITF: return "ITF";
            case Barcode.PDF417: return "PDF417";
            case Barcode.QR_CODE: return "QR_CODE";
            case Barcode.UPC_A: return "UPC_A";
            case Barcode.UPC_E: return "UPC_E";
            default: return "Unknown (" + format + ")";
        }
    }
}
