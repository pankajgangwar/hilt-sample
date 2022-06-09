package com.pkumar7.daggerhilt.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.pkumar7.daggerhilt.R
import com.pkumar7.daggerhilt.di.ApplicationTag
import com.pkumar7.daggerhilt.di.DelimeterTag
import com.pkumar7.daggerhilt.model.Blog
import com.pkumar7.daggerhilt.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel : MainViewModel by viewModels()

    @ApplicationTag
    @Inject
    lateinit var appTag: String

    @DelimeterTag
    @Inject
    lateinit var delimeterTag: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(appTag, "onCreate $delimeterTag" )
        subscribeObservers()
        viewModel.setStateEvent(MainStateEvent.GetBlogEvent)
        viewModel.printMessage()
    }

    private fun subscribeObservers(){
        viewModel.dataState.observe(this, Observer {
            dataState ->
            when(dataState){
                is DataState.Success<List<Blog>> -> {
                    displayProgressBar(false)
                    appendBlogsTitle(dataState.data)
                }
                is DataState.Error -> {
                    displayErrorMessage(dataState.exception.message)
                    displayProgressBar(false)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }
    private fun displayErrorMessage(message: String?){
        text.text = message ?: "Uknown error"
    }

    private fun displayProgressBar(display : Boolean){
        progress_bar.visibility = if(display) View.VISIBLE else View.GONE
    }

    private fun appendBlogsTitle(blogs: List<Blog>){
        var sb = StringBuilder()
        for(blog in blogs){
            sb.append(blog.title + "\n")
        }
        text.text = sb.toString()
    }
}