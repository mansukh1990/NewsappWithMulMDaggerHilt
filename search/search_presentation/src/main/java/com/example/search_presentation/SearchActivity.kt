package com.example.search_presentation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.common_utils.Navigator
import com.example.search_presentation.databinding.ActivitySearchBinding
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    companion object {
        fun launchActivity(activity: Activity) {
            val intent = Intent(activity, SearchActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private var _binding: ActivitySearchBinding? = null
    val binding: ActivitySearchBinding
        get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()

    private val newsAdapter = NewsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        setObserver()
    }

    private fun setObserver() {
        lifecycleScope.launch {
            viewModel.searchArticle.collectLatest {
                if (it.isLoading) {
                    binding.progressBar.visibility = View.VISIBLE

                }
                if (it.error.isNotBlank()) {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this@SearchActivity, it.error, Toast.LENGTH_LONG).show()

                }
                it.data?.let {
                    binding.progressBar.visibility = View.GONE
                    newsAdapter.setData(it)

                }

            }
        }

    }

    private fun initView() {

        binding.rvSearch.adapter = newsAdapter

        binding.searchTitle.doAfterTextChanged {
            val map = mutableMapOf<String, String>()
            map[Constant.API_KEY] = Constant.API_KEY
            map[Constant.QUERY] = Constant.QUERY
            viewModel.getSearchArticles(map)

        }
        binding.ivRnage.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.dateRangePicker().build()
            datePicker.show(this.supportFragmentManager, "range picker")

            datePicker.addOnPositiveButtonClickListener {
                val start = changeDateFormat(it.first)
                val end = changeDateFormat(it.second)

                val map = mutableMapOf<String, String>()
                map[Constant.API_KEY] = Constant.API_KEY
                map[Constant.QUERY] = binding.searchTitle.text.toString()
                map[Constant.START_DATE] = start
                map[Constant.END_DATE] = end

                viewModel.getSearchArticles(map)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun changeDateFormat(long: Long?): String {
        return try {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            simpleDateFormat.format(long)

        } catch (e: Exception) {
            ""

        }
    }
}

object GoToSearchActivity : Navigator {
    override fun navigate(activity: Activity) {
        SearchActivity.launchActivity(activity)
    }

}