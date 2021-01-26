package com.mattg.aztownhall.ui.wildapricotusersignsignin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mattg.aztownhall.R
import com.mattg.aztownhall.ui.home.HomeViewModel


class SignInUserFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_sign_in_user, container, false)
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            SignInUserFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}