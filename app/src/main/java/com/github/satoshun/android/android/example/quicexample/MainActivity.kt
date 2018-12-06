package com.github.satoshun.android.android.example.quicexample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.chromium.net.CronetEngine
import org.chromium.net.CronetException
import org.chromium.net.UrlRequest
import org.chromium.net.UrlResponseInfo
import java.nio.ByteBuffer
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_act)

    val engine = CronetEngine
      .Builder(this)
      .enableHttp2(true)
      .enableQuic(true)
      .build()

    val request = engine
      .newUrlRequestBuilder(
        "https://quic.clemente.io",
        object : UrlRequest.Callback() {
          override fun onResponseStarted(request: UrlRequest?, info: UrlResponseInfo?) {
            Log.d("onResponseStarted", info.toString())

            val buffer = ByteBuffer.allocateDirect(1000)
            request?.read(buffer)
            Log.d("buffer", buffer.toString())
          }

          override fun onReadCompleted(request: UrlRequest?, info: UrlResponseInfo?, byteBuffer: ByteBuffer?) {
            Log.d("onReadCompleted", byteBuffer.toString())
          }

          override fun onFailed(request: UrlRequest?, info: UrlResponseInfo?, error: CronetException?) {
            Log.d("onFailed", error.toString())
          }

          override fun onSucceeded(request: UrlRequest?, info: UrlResponseInfo?) {
            TODO("not implemented")
          }

          override fun onRedirectReceived(request: UrlRequest?, info: UrlResponseInfo?, newLocationUrl: String?) {
            TODO("not implemented")
          }
        },
        Executors.newSingleThreadExecutor()
      )
      .setHttpMethod("GET")
      .addHeader("hoge", "fuga")
      .build()

    request.start()
  }
}
