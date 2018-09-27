package com.example.sherath.areadescription;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.google.atap.tangoservice.Tango;

public class MainActivity extends Activity {

    // The unique key string for storing the user's input.
    public static final String USE_AREA_LEARNING =
            "com.example.sherath.areadescription.usearealearning";
    public static final String LOAD_ADF =
            "com.example.sherath.areadescription.loadadf";

    // Permission request action.
    public static final int REQUEST_CODE_TANGO_PERMISSION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);

        startActivityForResult(
                Tango.getRequestPermissionIntent(Tango.PERMISSIONTYPE_ADF_LOAD_SAVE), 0);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    /**
     * The "ADF List View" button has been clicked.
     * Defined in {@code activity_main.xml}
     */
    public void adfListViewClicked(View v) {
        startAdfListView();
    }

    /**
     * Start the ADF list activity.
     */
    private void startAdfListView() {
        Intent startAdfListViewIntent = new Intent(this, AdfUuidListViewActivity.class);
        startActivity(startAdfListViewIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // The result of the permission activity.
        //
        // Note that when the permission activity is dismissed, the HelloAreaDescriptionActivity's
        // onResume() callback is called. Because the Tango Service is connected in the onResume()
        // function, we do not call connect here.
        //
        // Check which request we're responding to.
        if (requestCode == REQUEST_CODE_TANGO_PERMISSION) {
            // Make sure the request was successful.
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, R.string.arealearning_permission, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
