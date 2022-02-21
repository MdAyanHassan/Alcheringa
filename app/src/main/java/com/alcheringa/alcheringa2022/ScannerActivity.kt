package com.alcheringa.alcheringa2022

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.alcheringa.alcheringa2022.R
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.scottyab.aescrypt.AESCrypt
import org.json.JSONObject
import java.lang.Exception
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.regex.Pattern

@androidx.camera.core.ExperimentalGetImage
class ScannerActivity : AppCompatActivity() {

    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService

    private lateinit var previewView: PreviewView

    private lateinit var myToast: Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        myToast = Toast.makeText(this, null, Toast.LENGTH_SHORT)

        previewView = findViewById(R.id.viewFinder)

        cameraExecutor = Executors.newSingleThreadExecutor()

        if(
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ){
            startCamera()
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CODE_PERMISSIONS)
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

            imageCapture = ImageCapture.Builder()
                    .build()

            val imageAnalyzer = ImageAnalysis.Builder()
                    .build()
                    .also {
                        it.setAnalyzer(cameraExecutor, YourImageAnalyzer(this))
                    }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                        this, cameraSelector, preview, imageCapture, imageAnalyzer)

            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //toast("starting camera")
                startCamera()
                //requireActivity().recreate()
            } else {
                Toast.makeText(this, "Camera Permission not granted", Toast.LENGTH_LONG).show()
                //finish()
            }
        }
    }
    private fun toast(s: String){
        myToast.setText(s)
        myToast.show()
        //Toast.makeText(requireActivity(),s,Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val TAG = "ScannerActivity"
        private const val REQUEST_CODE_PERMISSIONS = 100
    }

    class YourImageAnalyzer(context: Context): ImageAnalysis.Analyzer {

        private var c = context
        private val myToast: Toast = Toast.makeText(c, null, Toast.LENGTH_SHORT)
        private var status = "Scanning"

        override fun analyze(imageProxy: ImageProxy) {
            val mediaImage = imageProxy.image
            if (mediaImage != null) {
                val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                // Pass image to an ML Kit Vision API
                getCodeValue(image)
            }
            imageProxy.close()
        }

        private fun getCodeValue(image: InputImage){
            val options = BarcodeScannerOptions.Builder()
                    .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                    .build()

            val scanner = BarcodeScanning.getClient(options)

            scanner.process(image)
                    .addOnSuccessListener { barcodes ->
                        Log.d(TAG, "scanned")
                        if(barcodes.size == 1){
                            if (status=="Scanning"){
                                status="Processing"
                                val rawValue = barcodes[0].rawValue
                                Log.d(TAG, "getCodeValue: $rawValue")
                                //rawValue = decodeMessage(rawValue)
                                //if(rawValue != ""){ }else{status = "Scanning"}
                                //val regexPattern = "^(.+)@(\\S+)$"
                                //if(Pattern.compile(regexPattern)
                                //                .matcher(rawValue.toString())
                                //                .matches()){
                                //    getUserInfo(rawValue!!)
                                //}
                                //else{
                                //    Log.d(TAG, "Invalid QR Code")
                                //    toast("Invalid QR Code")
                                //    status = "Scanning"
                                //}
                                toast("Text from QR Code: "+rawValue!!)
                            } else {
                                Log.d(TAG,"Another scan while processing")
                            }
                        }else if(barcodes.size > 1){
                            toast("More than one QR code detected")
                        }

                    }
                    .addOnFailureListener {
                        Log.d(TAG, "scanned but failed")
                    }
        }

        private fun getUserInfo(email: String) {
            (c as Activity).findViewById<LoaderView>(R.id.dots_progress).visibility = View.VISIBLE
            val v = (c.getSystemService(VIBRATOR_SERVICE) as Vibrator)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(150,
                        VibrationEffect.DEFAULT_AMPLITUDE))
            } else { v.vibrate(150) }

            val firebaseFirestore = FirebaseFirestore.getInstance()
            firebaseFirestore.collection("USERS").document(email).get()
                    .addOnCompleteListener { task: Task<DocumentSnapshot> ->
                        if (task.isSuccessful && task.result.exists()) {

                            val name: String = task.result.getString("Name").toString()
                            //val intent = Intent(c, AddUserActivity::class.java)
                            //intent.putExtra("name",name)
                            //intent.putExtra("email",email)
                            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            //status = "Scanning"
                            //(c as Activity).findViewById<LoaderView>(androidx.camera.core.R.id.dots_progress).visibility = View.GONE
                            //c.startActivity(intent)
                            (c as Activity).finish()
                        }else{
                            toast("User does not exist")
                            status = "Scanning"
                            (c as Activity).findViewById<LoaderView>(R.id.dots_progress).visibility = View.GONE
                        }
                    }
        }

        private fun toast(s: String){
            myToast.setText(s)
            myToast.show()
            //Toast.makeText(c,s,Toast.LENGTH_SHORT).show()
        }


        private fun decodeMessage(message: String?): String {
            val password = "2ew1O7DDCKjIImXryfwM"
            return try {
                val messageAfterDecrypt = AESCrypt.decrypt(password, message)
                Log.d(TAG, "decodeMessage: $messageAfterDecrypt")
                messageAfterDecrypt

            } catch (e: Exception) {
                Log.d(TAG, "Could not decrypt QR Code")
                ""
            }
        }
    }
}