package com.nbsp.materialfilepicker.sample

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.nbsp.materialfilepicker.MaterialFilePicker
import com.nbsp.materialfilepicker.ui.FilePickerActivity

class SampleFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_sample, container, false)

        val pickButton = view.findViewById<Button>(R.id.pick_from_fragment)
        pickButton.setOnClickListener { checkPermissionsAndOpenFilePicker() }

        return view
    }

    private fun checkPermissionsAndOpenFilePicker() {
        val permissionGranted = checkSelfPermission(requireContext(), READ_EXTERNAL_STORAGE) == PERMISSION_GRANTED

        if (permissionGranted) {
            openFilePicker()
        } else {
            if (shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {
                showError()
            } else {
                requestPermissions(arrayOf(READ_EXTERNAL_STORAGE), PERMISSIONS_REQUEST_CODE)
            }
        }
    }

    private fun showError() {
        Toast.makeText(
                context,
                "Allow external storage reading",
                Toast.LENGTH_SHORT
        ).show()
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults.first() == PERMISSION_GRANTED) {
                openFilePicker()
            } else {
                showError()
            }
        }
    }

    private fun openFilePicker() {
        MaterialFilePicker()
                .withSupportFragment(this)
                .withRequestCode(FILE_PICKER_REQUEST_CODE)
                .withHiddenFiles(true)
                .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data ?: throw IllegalArgumentException("data must not be null")

            val path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH)

            if (path != null) {
                Log.d("Path (fragment): ", path)
                Toast.makeText(
                        context,
                        "Picked file in fragment: $path", Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    companion object {
        private const val PERMISSIONS_REQUEST_CODE = 0
        private const val FILE_PICKER_REQUEST_CODE = 1
    }
}