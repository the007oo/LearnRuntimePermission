package com.splanet.learnruntimepermission;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phattarapong on 9/22/2017.
 */

abstract class RequestPermission extends AppCompatActivity {


    protected static final int REQUEST_PERMISSION = 1234;

    protected static final int REQUEST_MULTI_PERMISSION = 12351254;

    protected void requestPermissionCamera() {

        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(RequestPermission.this,
                Manifest.permission.CAMERA);

        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {

            if (!ActivityCompat.shouldShowRequestPermissionRationale(RequestPermission.this,
                    Manifest.permission.CAMERA)) {

                showMessageOKCancel("You need to allow access to Camera", RequestPermission.this,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(RequestPermission.this,
                                        new String[]{Manifest.permission.CAMERA},
                                        REQUEST_PERMISSION);
                            }
                        });

                return;
        }

            ActivityCompat.requestPermissions(RequestPermission.this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_PERMISSION);
            return;
        }
        //DO SOME THINGS
    }

    protected void requestMultiPermission() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add("GPS");
        if (!addPermission(permissionsList, Manifest.permission.READ_CONTACTS))
            permissionsNeeded.add("Read Contacts");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_CONTACTS))
            permissionsNeeded.add("Write Contacts");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message, RequestPermission.this,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_MULTI_PERMISSION);
                            }
                        });
                return;
            }
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_MULTI_PERMISSION);
            return;
        }

    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
//            if (!shouldShowRequestPermissionRationale(permission))
//                return false;
        }
        return true;
    }


    private void showMessageOKCancel(String message, AppCompatActivity appCompatActivity, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(appCompatActivity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


}
