package com.example.testapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.data.NewsItem

class HomeViewModel : ViewModel() {

    private val _newsList = MutableLiveData<List<NewsItem>>().apply {
        value = listOf(
            NewsItem(
                "First",
                "Jetpack compose test item!",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRXJN7QdGkyBvLa7Z6urQ_E5TaNnh0adLjWnQ&usqp=CAU"
            ),
            NewsItem(
                "Second",
                "Jetpack compose test item!Jetpack compose test item!",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRXJN7QdGkyBvLa7Z6urQ_E5TaNnh0adLjWnQ&usqp=CAU"
            ),
            NewsItem(
                "Third",
                "Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRXJN7QdGkyBvLa7Z6urQ_E5TaNnh0adLjWnQ&usqp=CAU"
            ),
            NewsItem(
                "Fourth",
                "Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRXJN7QdGkyBvLa7Z6urQ_E5TaNnh0adLjWnQ&usqp=CAU"
            ),
            NewsItem(
                "Fifth",
                "Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRXJN7QdGkyBvLa7Z6urQ_E5TaNnh0adLjWnQ&usqp=CAU"
            ),
            NewsItem(
                "Fifth",
                "Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRXJN7QdGkyBvLa7Z6urQ_E5TaNnh0adLjWnQ&usqp=CAU"
            ),
            NewsItem(
                "Fifth",
                "Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRXJN7QdGkyBvLa7Z6urQ_E5TaNnh0adLjWnQ&usqp=CAU"
            ),
            NewsItem(
                "Fifth",
                "Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRXJN7QdGkyBvLa7Z6urQ_E5TaNnh0adLjWnQ&usqp=CAU"
            ),
            NewsItem(
                "Fifth",
                "Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRXJN7QdGkyBvLa7Z6urQ_E5TaNnh0adLjWnQ&usqp=CAU"
            ),
            NewsItem(
                "Fifth",
                "Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRXJN7QdGkyBvLa7Z6urQ_E5TaNnh0adLjWnQ&usqp=CAU"
            ),
            NewsItem(
                "Fifth",
                "Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRXJN7QdGkyBvLa7Z6urQ_E5TaNnh0adLjWnQ&usqp=CAU"
            ),
            NewsItem(
                "Fifth",
                "Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRXJN7QdGkyBvLa7Z6urQ_E5TaNnh0adLjWnQ&usqp=CAU"
            ),
            NewsItem(
                "Fifth",
                "Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRXJN7QdGkyBvLa7Z6urQ_E5TaNnh0adLjWnQ&usqp=CAU"
            ),
            NewsItem(
                "Fifth",
                "Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!Jetpack compose test item!",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRXJN7QdGkyBvLa7Z6urQ_E5TaNnh0adLjWnQ&usqp=CAU"
            )
        )
    }
    val newsList: LiveData<List<NewsItem>> = _newsList
}