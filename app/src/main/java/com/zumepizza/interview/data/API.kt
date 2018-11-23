package com.zumepizza.interview.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.zumepizza.interview.model.Pizza
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber

/**
 * Created by Joe Wang on 2018/11/22.
 */
object API {
    fun getMenu(context: Context): MutableLiveData<List<Pizza>> {
        val queue: RequestQueue = Volley.newRequestQueue(context)

        val url = "https://api.myjson.com/bins/snyji"
        Timber.d("getMenu")

        var liveData: MutableLiveData<List<Pizza>> = MutableLiveData()

        val gson = Gson()

        val request: StringRequest = StringRequest(Request.Method.GET, url, Response.Listener<String> {
            response ->
      //      Timber.d("response: " + response)
            try {
                val result: JSONObject = JSONArray(response).getJSONObject(0)
          //        Timber.d("chef = " + result.getString("Chef's Choice"))
                val chefList: List<Pizza> = gson.fromJson(result.getString("Chef's Choice"), Array<Pizza>::class.java).asList()
    //              Timber.d("len = " + chefList[1])

                val chefTitle = Pizza(true, "CHEF'S CHOICE")
                val pizzaList = mutableListOf<Pizza>(chefTitle)
                pizzaList.addAll(chefList)
                pizzaList.add(Pizza(true, "CLASSICS"))
                val classicList: List<Pizza> = gson.fromJson(result.getString("Classics"), Array<Pizza>::class.java).asList()
                pizzaList.addAll(classicList)
                pizzaList.add(Pizza(true, "SIGNATURE"))
                val signatureList: List<Pizza> = gson.fromJson(result.getString("Signature"), Array<Pizza>::class.java).asList()
                pizzaList.addAll(signatureList)
                pizzaList.add(Pizza(true, "VEGETARIAN"))
                val vegeList: List<Pizza> = gson.fromJson(result.getString("Vegetarian"), Array<Pizza>::class.java).asList()
                pizzaList.addAll(vegeList)
                Timber.d("list : " + pizzaList.size)

                liveData.value = pizzaList
            } catch (e: Exception) {
                Timber.d("JSONArray wrong: " + e.localizedMessage)
            }

        }, Response.ErrorListener {
            error ->
            Timber.e("get Menu error: " + error.localizedMessage)
        })
        queue.add(request)

        return liveData
    }
}