package com.example.navigation3tryout

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.navigation3tryout.databinding.FragmentSimpleNavTestBinding
import com.mapbox.dash.sdk.Dash

class SimpleNavTest : Fragment() {
    private lateinit var binding: FragmentSimpleNavTestBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSimpleNavTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        Dash.controller.startTripSession().onFailure {

        }
    }

    private companion object {
        const val TAG = "SimpleNavTest"
    }
}