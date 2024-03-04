package com.example.newsread

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsVm @Inject constructor(private val apiService: ApiService): ViewModel() {


    var newsList : List<Article> by mutableStateOf(listOf())



    fun fetchNews(){
        viewModelScope.launch {
            try {
               val news = apiService.getNews()
                if (news.isSuccessful) {
                    newsList = news.body()!!.articles
                } else {
                    print("MESSAGE -> ${news.message()}")
                }
            }
            catch (e:Exception){
                println(e.message)
            }

        }
    }
}