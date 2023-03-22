package com.certeixeira.netflixremake.util

import android.util.Log
import java.io.IOException
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

                //forma 1:
                val stream = urlConnection.inputStream //sequencia de bytes
                val jsonAsString = stream.bufferedReader().use { it.readText() }
                Log.i("Teste", jsonAsString)

                // forma 2:

            } catch (e: java.lang.Exception) {
                Log.e("Teste", e.message ?: "erro deconhecido", e)
            }
        }
    }

}