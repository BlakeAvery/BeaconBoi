package com.ufoproducts.hackholyoke.beaconboi
import android.util.Log
import java.net.*
import java.io.*
val CRLF = "\r\n"
class Connector constructor(d: LocationData): Runnable {
    val data: LocationData
    init {
        data = d
    }
    override fun run() {
        val socc = Socket("localhost", 5000);
        val out = OutputStreamWriter(socc.getOutputStream())
        val input = InputStreamReader(socc.getInputStream())
        val http = "PUT /location/${data.ID}.json FireWatch/0.1${CRLF}Content-Type: application/json$CRLF"
        val json = """$http{"id":"${data.ID}","lat":"${data.latitude}","long":"${data.longitude}}$CRLF"
        """.trimIndent()
        Log.i("Info", "BeaconServ request: $json")
        out.write(json)
        val response = input.readLines()
        if(response[0].contains("200")) {
            Log.i("Info", "File accepted")
        } else {
            Log.e("Oops", "FireWatch server denied data!")
        }
    }
}