package com.mattg.arizonatownhall.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mattg.arizonatownhall.R
import com.mattg.arizonatownhall.utils.BaseFragment
import com.mattg.arizonatownhall.utils.ClickListener
import com.mattg.arizonatownhall.utils.NewsClickListener
import io.swagger.client.models.Event
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var clickListener: ClickListener
    private lateinit var clickListenerNews: NewsClickListener


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        homeViewModel.onAuthenticate((homeViewModel.requestBody), "events")

        homeViewModel.getNews()

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        navBar.visibility = View.VISIBLE



        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.news.observe(viewLifecycleOwner, {
            addNewsToRecycler(it)
        })
        homeViewModel.events.observe(viewLifecycleOwner, {
            setupEventsRecycler(it.reversed())
            progressBar.visibility = View.INVISIBLE
        })
        homeViewModel.itemToShare.observe(viewLifecycleOwner, {
            postToFaceBook(it, null, null)
        })


    }

    private fun addNewsToRecycler(news: List<NewsItem>) {
        val recyclerNews = rv_home_news
        clickListenerNews = NewsClickListener { _, type, url ->
            when (type) {
                0 -> startCustomTab(url, requireContext())
                1 -> homeViewModel.itemToShare.value = url
            }

        }
        val adapterNews = HomeNewsAdapter(requireContext(), news, clickListenerNews)
        val layoutManagerNews =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerNews.adapter = adapterNews
        recyclerNews.layoutManager = layoutManagerNews
    }

    private fun setupEventsRecycler(list: List<Event>) {
        val recyclerEvents = rv_homeEvents
        clickListener = ClickListener { _, press, url ->

            when (press) {

                0 -> {
                    startCustomTab("https://aztownhall.org/event-$url", requireContext())
                }

                1 -> {
                    homeViewModel.itemToShare.value = "https://aztownhall.org/event-$url"
                }

            }

        }
        val adapterEvents = HomeEventsAdapter(requireContext(), list, clickListener)
        val layoutManagerEvents =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerEvents.adapter = adapterEvents
        recyclerEvents.layoutManager = layoutManagerEvents
    }


}