package com.example.swu_guru_19_workparttimeapp_.Boss

import android.content.Intent
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class KakaoAddressActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        webView = WebView(this)
        setContentView(webView)

        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
        }

        webView.addJavascriptInterface(BridgeInterface(), "Android")

        webView.webViewClient = object : WebViewClient() {}

        val htmlData = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta
                    name="viewport"
                    content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"
                >
                <script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
            </head>

            <body style="margin:0; padding:0;">
                <div id="layer" style="width:100%; height:100vh;"></div>

                <script>
                    function execDaumPostcode() {
                        new daum.Postcode({
                            oncomplete: function(data) {
                                var fullAddress =
                                    data.userSelectedType === 'R'
                                    ? data.roadAddress
                                    : data.jibunAddress;

                                window.Android.processDATA(fullAddress);
                            },
                            width: '100%',
                            height: '100%'
                        }).embed(document.getElementById('layer'));
                    }

                    window.onload = execDaumPostcode;
                </script>
            </body>
            </html>
        """.trimIndent()

        webView.loadDataWithBaseURL(
            "https://postcode.map.daum.net",
            htmlData,
            "text/html",
            "UTF-8",
            null
        )
    }

    private inner class BridgeInterface {

        @JavascriptInterface
        fun processDATA(address: String) {
            runOnUiThread {
                val resultIntent = Intent().apply {
                    putExtra("result_address", address)
                }

                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    override fun onDestroy() {
        webView.destroy()
        super.onDestroy()
    }
}