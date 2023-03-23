package com.certeixeira.netflixremake.util

import android.util.Log
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class CategoryTask {

    fun execute(url: String) {
        //fornece um executor que vai abrir um processo paralelo
        val executer = Executors.newSingleThreadExecutor()

        executer.execute {
            try {
                //nova thread (processo paralelo)
                val requestURL = URL(url)
                val urlConnection =
                    requestURL.openConnection() as HttpsURLConnection
                urlConnection.readTimeout = 2000 //tempo de leitura em milissegundos
                urlConnection.connectTimeout = 2000 //tempo de conexão

                val statusCode = urlConnection.responseCode
                if (statusCode > 400) {
                    throw IOException("Erro na comunicação com o servidor!")
                }

                val stream = urlConnection.inputStream //sequencia de bytes

                //forma 1:
//                val jsonAsString = stream.bufferedReader().use { it.readText() }


                // forma 2:
                val buffer = BufferedInputStream(stream)
                val jasonAsString = toString(buffer)
                Log.i("Teste", jasonAsString)

            } catch (e: java.lang.Exception) {
                Log.e("Teste", e.message ?: "erro deconhecido", e)
            }
        }
    }


    private fun toString(stream: InputStream): String {
        val bytes = ByteArray(1024)
        val baos = ByteArrayOutputStream()
        var read: Int
        while (true) {
            read = stream.read(bytes)
            if (read <= 0) {
                break
            }
            baos.write(bytes, 0, read)
        }

        return String(baos.toByteArray())
    }

}