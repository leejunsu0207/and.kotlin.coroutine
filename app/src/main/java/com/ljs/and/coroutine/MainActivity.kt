package com.ljs.and.coroutine

import android.view.View
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.net.URL
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ljs.and.coroutine.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope

suspend fun loadImage(imageUrl: String): Bitmap{
    val url = URL(imageUrl)
    val stream = url.openStream()
    return BitmapFactory.decodeStream(stream)
}

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.run {
            buttonDownload.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    progress.visibility = View.VISIBLE
                    val url = editUrl.text.toString()

                    val bitmap = withContext(Dispatchers.IO){
                        loadImage(url)
                    }

                    imagePreview.setImageBitmap(bitmap)
                    progress.visibility = View.GONE

                }
            }
        }

    }
}