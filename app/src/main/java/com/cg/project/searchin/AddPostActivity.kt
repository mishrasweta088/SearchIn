package com.cg.project.searchin

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.cg.project.searchin.AddPostActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class AddPostActivity : AppCompatActivity() {
    var firebaseAuth: FirebaseAuth? = null
    var userDbRef: DatabaseReference? = null
    var actionBar: ActionBar? = null

    //permissions array
    lateinit var cameraPermissions: Array<String>
    lateinit var storagePermissions: Array<String>

    //views
    var titleEt: EditText? = null
    var descriptionEt: EditText? = null
    var imageIv: ImageView? = null
    var uploadBtn: Button? = null

    //user info
    var name: String? = null
    var email: String? = null
    var uid: String? = null
    var dp: String? = null
    var image_rui: Uri? = null

    //progress bar
    var pd: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)
        val actionBar = supportActionBar
        actionBar!!.setTitle("Add new post")
        actionBar.setDisplayShowHomeEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        //init permissions arrays
        cameraPermissions = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        storagePermissions =
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        pd = ProgressDialog(this)
        firebaseAuth = FirebaseAuth.getInstance()
        checkUserStatus()
        actionBar.setSubtitle(email)
        userDbRef = FirebaseDatabase.getInstance().getReference("Users")
        val query =
            userDbRef!!.orderByChild("email").equalTo(email)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    name = "" + ds.child("name").value
                    email = "" + ds.child("email").value
                    dp = "" + ds.child("image").value
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

        //init views
        titleEt = findViewById(R.id.pTitleEt)
        descriptionEt = findViewById(R.id.pDescriptionEt)
        imageIv = findViewById(R.id.pImageIv)
        uploadBtn = findViewById(R.id.pUploadBtn)

        //get image from camera/gallery on click
        imageIv!!.setOnClickListener(View.OnClickListener { showImagePickDialog() })

        //upload button click listener
        uploadBtn!!.setOnClickListener(View.OnClickListener {
            val title = titleEt!!.getText().toString().trim { it <= ' ' }
            val description =
                descriptionEt!!.getText().toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(title)) {
                Toast.makeText(this@AddPostActivity, "Enter title..", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(description)) {
                Toast.makeText(this@AddPostActivity, "Enter description..", Toast.LENGTH_SHORT)
                    .show()
                return@OnClickListener
            }
            if (image_rui == null) {
                uploadData(title, description, "no image")
            } else {
                uploadData(title, description, image_rui.toString())
            }
        })
    }

    private fun uploadData(
        title: String,
        description: String,
        uri: String
    ) {
        pd!!.setMessage("Publishing post..")
        pd!!.show()
        val timeStamp = System.currentTimeMillis().toString()
        val filePathAndName = "Posts/post_$timeStamp"
        if (uri != "noImage") {
            val ref =
                FirebaseStorage.getInstance().reference.child(filePathAndName)
            ref.putFile(Uri.parse(uri))
                .addOnSuccessListener { taskSnapshot ->
                    val uriTask =
                        taskSnapshot.storage.downloadUrl
                    while (!uriTask.isSuccessful);
                    val downloadUri = uriTask.result.toString()
                    if (uriTask.isSuccessful) {
                        val hashMap =
                            HashMap<Any, String?>()
                        hashMap["uid"] = uid
                        hashMap["uName"] = name
                        hashMap["uEmail"] = email
                        hashMap["uDp"] = dp
                        hashMap["pId"] = timeStamp
                        hashMap["pTitle"] = title
                        hashMap["pDescr"] = description
                        hashMap["pImage"] = downloadUri
                        hashMap["pTime"] = timeStamp

                        //path to store post data
                        val ref =
                            FirebaseDatabase.getInstance().getReference("Posts")
                        //put data in this ref
                        ref.child(timeStamp).setValue(hashMap)
                            .addOnSuccessListener {
                                pd!!.dismiss()
                                Toast.makeText(
                                    this@AddPostActivity,
                                    "Post published",
                                    Toast.LENGTH_SHORT
                                ).show()
                                //reset views
                                titleEt!!.setText("")
                                descriptionEt!!.setText("")
                                imageIv!!.setImageURI(null)
                                image_rui = null
                            }
                            .addOnFailureListener { e ->
                                pd!!.dismiss()
                                Toast.makeText(
                                    this@AddPostActivity,
                                    "" + e.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                }
                .addOnFailureListener { e ->
                    pd!!.dismiss()
                    Toast.makeText(this@AddPostActivity, "" + e.message, Toast.LENGTH_SHORT)
                        .show()
                }
        } else {
            val hashMap =
                HashMap<Any, String?>()
            hashMap["uid"] = uid
            hashMap["uName"] = name
            hashMap["uEmail"] = email
            hashMap["uDp"] = dp
            hashMap["pId"] = timeStamp
            hashMap["pTitle"] = title
            hashMap["pDescr"] = description
            hashMap["pImage"] = "noImage"
            hashMap["pTime"] = timeStamp

            //path to store post data
            val ref = FirebaseDatabase.getInstance().getReference("Posts")
            //put data in this ref
            ref.child(timeStamp).setValue(hashMap)
                .addOnSuccessListener {
                    pd!!.dismiss()
                    Toast.makeText(this@AddPostActivity, "Post published", Toast.LENGTH_SHORT)
                        .show()
                    titleEt!!.setText("")
                    descriptionEt!!.setText("")
                    imageIv!!.setImageURI(null)
                    image_rui = null
                }
                .addOnFailureListener { e ->
                    pd!!.dismiss()
                    Toast.makeText(this@AddPostActivity, "" + e.message, Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }

    private fun showImagePickDialog() {
        val options = arrayOf("Camera", "Gallery")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose Image FROM")
        builder.setItems(options) { dialog, which ->
            if (which == 0) {
                if (!checkCameraPermission()) {
                    requestCameraPermission()
                } else {
                    pickFromCamera()
                }
            }
            if (which == 1) {
                if (!checkStoragePermission()) {
                    requestStoragePermission()
                } else {
                    pickFromGallery()
                }
            }
        }
        builder.create().show()
    }

    private fun pickFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE)
    }

    private fun pickFromCamera() {
        val cv = ContentValues()
        cv.put(MediaStore.Images.Media.TITLE, "Temp Pick")
        cv.put(MediaStore.Images.Media.DESCRIPTION, "Temp Descr")
        image_rui = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_rui)
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE)
    }

    private fun checkStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            this,
            storagePermissions,
            STORAGE_REQUEST_CODE
        )
    }

    private fun checkCameraPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        val result1 = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        return result && result1
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            cameraPermissions,
            CAMERA_REQUEST_CODE
        )
    }

    override fun onStart() {
        super.onStart()
        checkUserStatus()
    }

    override fun onResume() {
        super.onResume()
        checkUserStatus()
    }

    private fun checkUserStatus() {
        val user = firebaseAuth!!.currentUser
        if (user != null) {
            email = user.email
            uid = user.uid
        } else {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    fun onSupportNvigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        menu.findItem(R.id.action_add_post).isVisible = false
        menu.findItem(R.id.action_search).isVisible = false
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.size > 0) {
                    val cameraAccepted =
                        grantResults[0] == PackageManager.PERMISSION_GRANTED
                    val storageAccepted =
                        grantResults[0] == PackageManager.PERMISSION_GRANTED
                    if (cameraAccepted && storageAccepted) {
                        pickFromCamera()
                    } else {
                        Toast.makeText(
                            this,
                            "Camera & Stoge both permissions are neccessary",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                }
            }
            STORAGE_REQUEST_CODE -> {
                if (grantResults.size > 0) {
                    val storageAccepted =
                        grantResults[0] == PackageManager.PERMISSION_GRANTED
                    if (storageAccepted) {
                        pickFromGallery()
                    } else {
                        Toast.makeText(this, " Stoge  permission is neccessary", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                }
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                image_rui = data!!.data
                imageIv!!.setImageURI(image_rui)
            } else if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                imageIv!!.setImageURI(image_rui)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        //permissions constants
        private const val CAMERA_REQUEST_CODE = 100
        private const val STORAGE_REQUEST_CODE = 200
        private const val IMAGE_PICK_CAMERA_CODE = 300
        private const val IMAGE_PICK_GALLERY_CODE = 400
    }
}