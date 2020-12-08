package com.mattg.arizonatownhall.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mattg.arizonatownhall.R
import kotlinx.android.synthetic.main.fragment_links.*


class LinksFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_links, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        tv_faq.setOnClickListener {
            val url = "https://aztownhall.org/FAQ"
            startCustomTab(url, requireContext())
        }
        tv_town_hall_faq.setOnClickListener {
            val url = "https://aztownhall.org/Community-Town-Halls-FAQs"
            startCustomTab(url, requireContext())
        }
        tv_zoom_help.setOnClickListener {
            val url =
                "https://aztownhall.org/resources/Documents/113%20Creating%20Vibrant%20Communities/Welcome%20to%20Arizona%20Town%20Hall%20on%20Zoom.pdf"
            startCustomTab(url, requireContext())
        }

        tv_events_calender.setOnClickListener {
            val url = "https://aztownhall.org/Events_Calendar"
            startCustomTab(url, requireContext())
        }
        ib_facebook.setOnClickListener {
            startCustomTab("https://www.facebook.com/ArizonaTownHall", requireContext())
        }
        ib_instagram.setOnClickListener {
            startCustomTab("https://www.instagram.com/aztownhall", requireContext())
        }
        ib_twitter.setOnClickListener {
            startCustomTab("https://twitter.com/AZTownHall", requireContext())
        }
    }


}