package com.certeixeira.netflixremake.util

import android.util.Log
import com.certeixeira.netflixremake.model.Category
import com.certeixeira.netflixremake.model.Movie
import org.json.JSONObject
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
            var urlConnection: HttpsURLConnection? = null
            var buffer: BufferedInputStream? = null
            var stream: InputStream? = null
            try {
                //nova thread (processo paralelo)
                val requestURL = URL(url)
                urlConnection =
                    requestURL.openConnection() as HttpsURLConnection
                urlConnection.readTimeout = 2000 //tempo de leitura em milissegundos
                urlConnection.connectTimeout = 2000 //tempo de conexão

                val statusCode = urlConnection.responseCode
                if (statusCode > 400) {
                    throw IOException("Erro na comunicação com o servidor!")
                }

                stream = urlConnection.inputStream //sequencia de bytes

                //forma 1:
//                val jsonAsString = stream.bufferedReader().use { it.readText() }


                // forma 2:
                buffer = BufferedInputStream(stream)
                val jasonAsString = toString(buffer)

                val categories = toCategories(jasonAsString)

                Log.i("Teste", categories.toString())

            } catch (e: java.lang.Exception) {
                Log.e("Teste", e.message ?: "erro deconhecido", e)
            } finally {
                urlConnection?.disconnect()
                stream?.close()
                buffer?.close()

            }


        }
    }
    private fun toCategories(jsonAsString: String) : List<Category> {
        val categories = mutableListOf<Category>()

        val jsonRoot = JSONObject(jsonAsString)
        val jsonCategories = jsonRoot.getJSONArray("category")
        for ( i in 0 until jsonCategories.length()) {
            val jsonCategory = jsonCategories.getJSONObject(i)

            val title = jsonCategory.getString("title")
            val jsonMovies = jsonCategory.getJSONArray("movie")

            val movies = mutableListOf<Movie>()
            for (j in 0 until jsonMovies.length()) {
                val jsonMovie = jsonMovies.getJSONObject(j)
                val id = jsonMovie.getInt("id")
                val coverUrl = jsonMovie.getString("cover_url")

                movies.add(Movie(id, coverUrl))
            }

            categories.add(Category(title, movies))
        }

        return categories
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