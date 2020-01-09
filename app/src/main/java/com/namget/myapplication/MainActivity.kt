package com.namget.myapplication

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log.e
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    lateinit var inentIntegrator: IntentIntegrator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        initZxing()
    }

    private fun initZxing() {
        // this is the current Activity
        inentIntegrator = IntentIntegrator(this).apply {
            setDesiredBarcodeFormats()
            setPrompt("바코드 스캔")
            //이미지를 받을지의 대한 여부
            setBarcodeImageEnabled(true)
            setOrientationLocked(true)
            setContinuous(true)
            // 삐소리나는 바코드
            setBeepEnabled(false)
            //카메라 방향
            setCameraId(0)
            initiateScan()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult? =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                //취소
            } else {
                //성공
                result.contents
                e("onActivityResult", "result.barcodeImagePath : ${result.barcodeImagePath}")
                makePathToBitmap(result.barcodeImagePath)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private fun makePathToBitmap(path: String) {
        val imageFile = File(path)
        val bmOption: BitmapFactory.Options = BitmapFactory.Options()
        val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath, bmOption)
        testImage.setImageBitmap(bitmap)

    }

}
