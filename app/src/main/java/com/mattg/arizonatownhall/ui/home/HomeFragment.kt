package com.mattg.arizonatownhall.ui.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mattg.arizonatownhall.utils.ClickListener
import com.mattg.arizonatownhall.utils.NewsClickListener
import com.mattg.arizonatownhall.R
import com.mattg.arizonatownhall.utils.BaseFragment
import io.swagger.client.models.Event
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.login_web_portal_dialog.*


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

        val isFirst = requireActivity().getSharedPreferences("PREFERENCE", AppCompatActivity.MODE_PRIVATE).getBoolean("isFirst", true)

        if(isFirst){
            showLoginDialog()
        }

        requireActivity().getSharedPreferences("PREFERENCE", AppCompatActivity.MODE_PRIVATE).edit().putBoolean("isFirst", false).apply()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.news.observe(viewLifecycleOwner, {
            addNewsToRecycler(it)
        })
        homeViewModel.events.observe(viewLifecycleOwner,  {
            setupEventsRecycler(it.reversed())
            progressBar.visibility = View.INVISIBLE
        })
        homeViewModel.itemToShare.observe(viewLifecycleOwner,  {
            postToFaceBook(it, null, null)
        })



    }

    private fun showLoginDialog() {

        Dialog(requireContext()).apply {
            setContentView(R.layout.login_web_portal_dialog)
            btn_dialog_yes.setOnClickListener {
                startCustomTab("https://aztownhall.org/Sys/Login", requireContext())
                dismiss()
            }
            btn_dialog_no.setOnClickListener {
                dismiss()
            }
            btn_dialog_member.setOnClickListener {
                startCustomTab("https://aztownhall.org/Support", requireContext())
                dismiss()
            }

        }.show()

    }

    private fun addNewsToRecycler(news: List<NewsItem>){
        val recyclerNews = rv_home_news
        clickListenerNews = NewsClickListener{ _, type, url ->
            when(type){
                0-> startCustomTab(url, requireContext())
                1-> homeViewModel.itemToShare.value = url
            }

        }
        val adapterNews = HomeNewsAdapter(requireContext(), news, clickListenerNews)
        val layoutManagerNews = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerNews.adapter = adapterNews
        recyclerNews.layoutManager = layoutManagerNews
    }

    private fun setupEventsRecycler(list: List<Event>) {
        val recyclerEvents = rv_homeEvents
        clickListener = ClickListener { _, press, url->

            when(press){

            0-> {
                startCustomTab("https://aztownhall.org/event-$url", requireContext())
            }

            1-> {
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