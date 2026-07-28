package com.example.swu_guru_19_workparttimeapp_

import android.content.Intent
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class KakaoAddressActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        webView = WebView(this)
        setContentView(webView)

        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(BridgeInterface(), "Android")

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                webView.loadUrl("javascript:execDaumPostcode();")
            }
        }

        val htmlData = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
                <script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
            </head>
            <body style="margin:0;padding:0;">
                <div id="layer" style="width:100%;height:100vh;"></div>
                <script>
                    function execDaumPostcode() {
                        new daum.Postcode({
                            oncomplete: function(data) {
                                var fullAddr = data.userSelectedType === 'R' ? data.roadAddress : data.jibunAddress;
                                window.Android.processDATA(fullAddr);
                            },
                            width : '100%',
                            height : '100%'
                        }).embed(document.getElementById('layer'));
                    }
                </script>
            </body>
            </html>
        """.trimIndent()

        webView.loadDataWithBaseURL("https://daum.net", htmlData, "text/html", "UTF-8", null)
    }

    private inner class BridgeInterface {
        @JavascriptInterface
        fun processDATA(data: String) {
            val intent = Intent().apply {
                putExtra("result_address", data)
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}