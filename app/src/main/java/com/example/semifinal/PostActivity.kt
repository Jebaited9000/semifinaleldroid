package com.example.semifinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.semifinal.constants.Constants
import com.example.semifinal.databinding.ActivityPostBinding
import com.example.semifinal.models.Post
import com.example.semifinal.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.delete.setOnClickListener(){
            Toast.makeText( this, "Tweet deleted successfully", Toast.LENGTH_SHORT).show()
            deletePost()
        }
        binding.update.setOnClickListener(){
            Toast.makeText( this, "Tweet updated successfully", Toast.LENGTH_SHORT).show()
            //updatePost()
        }
    }

    override fun onResume() {
        super.onResume()
        getPost()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getPost() {
        val id = intent.getStringExtra(Constants.PARAM_ID) ?: return
        RetrofitClient.apiService.getPostById(id).enqueue(object: Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    val data: Post? = response.body()
                    if(data != null) {
                        binding.description.text = data.description
                        binding.name.text = data.name
                        binding.progress.visibility = View.GONE
                    }
                } else {
                    showError()
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                showError()
            }
        })
    }

    private fun showError() {
        Toast.makeText(this, "Failed to load data.", Toast.LENGTH_SHORT).show()
    }

    private fun deletePost(){
        val id = intent.getStringExtra(Constants.PARAM_ID) ?: return
        RetrofitClient.apiService.deletePost(id).enqueue(object: Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    finish()
                } else {
                    showError()
                }
            }
            override fun onFailure(call: Call<Post>, t: Throwable) {
                showError()
            }
        })
    }
}