package com.mattg.arizonatownhall.ui.home


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mattg.arizonatownhall.model.AuthToken
import com.mattg.arizonatownhall.model.WAApiCallService
import com.mattg.arizonatownhall.utils.Constants
import io.swagger.client.models.Event
import io.swagger.client.models.EventsResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val currentRefreshToken = Constants.refreshToken
    private val body = "grant_type=client_credentials&scope=auto&obtain_refresh_token=true"
    private val refreshBody = "grant_type=refresh_token&refresh_token=$currentRefreshToken"
    val requestBody = body.toRequestBody("text/plain".toMediaTypeOrNull())
    val requestBodyRefresh = refreshBody.toRequestBody("text/plain".toMediaTypeOrNull())
    private var mFirebaseDatabaseInstance : FirebaseFirestore? = null


    val events = MutableLiveData<List<Event>>()
    val news = MutableLiveData<List<NewsItem>>()
    var itemToShare = MutableLiveData<String>()

    val currentEvent = MutableLiveData<Event>()


    private fun setEvents(list: MutableList<Event>){
        events.value = list
    }
    fun setCurrentEvent(event: Event){
        currentEvent.postValue(event)
    }


    fun getNews(){
        mFirebaseDatabaseInstance = FirebaseFirestore.getInstance()
        val docReference = mFirebaseDatabaseInstance?.collection("InTheNews")?.orderBy("sortDate", Query.Direction.DESCENDING)

        docReference?.addSnapshotListener{
        documents, e ->
            if(e!=null){
                Log.e("error", "error loading news documents")
            } else {
                val newsList = ArrayList<NewsItem>()
                val items = documents!!.documents
                for(item in items){
                    val url = item.getString("Url")
                    val date = item.getString("Date")
                    val source = item.getString("Source")
                    val title = item.getString("title")
                    val itemToAdd = NewsItem(url, source, date, title)
                    newsList.add(itemToAdd)
                }
                news.value = newsList
            }
       }
    }

    private fun addEventsToRecycler(events: Array<Event>){
        val eventsList = mutableListOf(*events)
       setEvents(eventsList)
    }

    fun singleEventRequest(tokenToUse: String, eventId: String){
        val callGetEvent = WAApiCallService.get()
        callGetEvent.getSingleEvent(Constants.accountIdAdmin.toString().trim(), eventId.trim(), "Bearer $tokenToUse")
            .enqueue(object: Callback<Event>{
                override fun onResponse(call: Call<Event>, response: Response<Event>) {
                    val eventToPost = response.body()!!
                    setCurrentEvent(eventToPost)
                    eventToPost.Details?.RegistrationTypes?.get(0)

                }
                override fun onFailure(call: Call<Event>, t: Throwable) {
                   Log.i("FAILURE", "CALL FAILED throwable = ${t.localizedMessage}")
                }
            })

    }

    fun testEventsRequest(tokenToUse: String){
        val callGet = WAApiCallService.get()
        callGet.getEvents(Constants.accountIdAdmin.toString().trim(), "Bearer $tokenToUse")
            .enqueue(object: Callback<EventsResponse> {
                override fun onResponse(
                    call: Call<EventsResponse>,
                    response: Response<EventsResponse>
                ) {
                    val events = response.body()!!.Events!!
                    addEventsToRecycler(events)

                }
                override fun onFailure(call: Call<EventsResponse>, t: Throwable) {
                    Log.i("FAILURE", t.localizedMessage!!)
                }
            })
    }

    fun onAuthenticate(bodyParam: RequestBody, callType: String?){
        val call = WAApiCallService.call()
        call.callPost(bodyParam,"application/x-www-form-urlencoded")
            .enqueue(object: Callback<AuthToken> {
                override fun onFailure(call: Call<AuthToken>, t: Throwable) {
                    Log.i("FAILURE", t.localizedMessage!!)
                }
                override fun onResponse(call: Call<AuthToken>, response: Response<AuthToken>) {
                    val refreshToken = response.body()?.refreshToken
                    val token = response.body()?.accessToken
                    if (refreshToken != null) {
                        Constants.refreshToken = refreshToken
                    }
                    if (token != null) {
                        Constants.token = token.toString()
                        when (callType) {
                            "events" -> testEventsRequest(token)
                            "event" -> singleEventRequest(token, currentEvent.value?.Id.toString())
                        }
                    }
                    if(response.code() == 401){
                        onAuthenticate(requestBodyRefresh, null)
                    }

                }
            })
    }

}