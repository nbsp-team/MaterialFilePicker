package com.nbsp.materialfilepicker.sample

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat.checkSelfPermission
import com.nbsp.materialfilepicker.MaterialFilePicker
import com.nbsp.materialfilepicker.ui.FilePickerActivity
import java.io.File
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    private val pickButton: Button by lazy { findViewById<Button>(R.id.pick_from_activity) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pickButton.setOnClickListener { checkPermissionsAndOpenFilePicker() }
    }

    private fun checkPermissionsAndOpenFilePicker() {
        val permissionGranted = checkSelfPermission(this, READ_EXTERNAL_STORAGE) == PERMISSION_GRANTED

        if (permissionGranted) {
            openFilePicker()
        } else {
            if (shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE)) {
                showError()
            } else {
                requestPermissions(
                        this,
                        arrayOf(READ_EXTERNAL_STORAGE),
                        PERMISSIONS_REQUEST_CODE
                )
            }
        }
    }

    private fun showError() {
        Toast.makeText(this, "Allow external storage reading", Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults.first() == PERMISSION_GRANTED) {
                openFilePicker()
            } else {
                showError()
            }
        }
    }

    private fun openFilePicker() {
        val externalStorage = Environment.getExternalStorageDirectory()
        val alarmsFolder = File(externalStorage, ALARMS_EXTERNAL_STORAGE_FOLDER)

        MaterialFilePicker()
                // Pass a source of context. Can be:
                //    .withActivity(Activity activity)
                //    .withFragment(Fragment fragment)
                //    .withSupportFragment(androidx.fragment.app.Fragment fragment)
                .withActivity(this)
                // With cross icon on the right side of toolbar for closing picker straight away
                .withCloseMenu(true)
                // Entry point path (user will start from it)
                .withPath(alarmsFolder.absolutePath)
                // Root path (user won't be able to come higher than it)
                .withRootPath(externalStorage.absolutePath)
                // Showing hidden files
                .withHiddenFiles(true)
                // Want to choose only jpg images
                .withFilter(Pattern.compile(".*\\.(jpg|jpeg)$"))
                // Don't apply filter to directories names
                .withFilterDirectories(false)
                .withTitle("Sample title")
                .withRequestCode(FILE_PICKER_REQUEST_CODE)
                .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data ?: throw IllegalArgumentException("data must not be null")

            val path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH)

            if (path != null) {
                Log.d("Path: ", path)
                Toast.makeText(this, "Picked file: $path", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        private const val PERMISSIONS_REQUEST_CODE = 0
        private const val FILE_PICKER_REQUEST_CODE = 1

        private const val ALARMS_EXTERNAL_STORAGE_FOLDER = "Alarms"
    }
}
