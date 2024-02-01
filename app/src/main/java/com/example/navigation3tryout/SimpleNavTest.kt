package com.example.navigation3tryout

import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.navigation3tryout.databinding.FragmentSimpleNavTestBinding
import com.mapbox.common.location.Location
import com.mapbox.dash.sdk.Dash
import com.mapbox.dash.sdk.DashNavigationFragment
import com.mapbox.dash.sdk.config.api.DashConfig
import com.mapbox.dash.sdk.coordination.DashDestination
import com.mapbox.dash.sdk.coordination.PointDestination
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

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

        val config = DashConfig.create(
            applicationContext = requireActivity().applicationContext,
            accessToken = getString(R.string.mapbox_access_token),
        ) {
            locationSimulation {
                defaultLocation = Location.Builder()
                    .source(LocationManager.PASSIVE_PROVIDER)
                    .latitude(38.899929)
                    .longitude(-77.03394)
                    .build()
            }
        }

        Dash.init(config)

        Dash.controller.startTripSession().onFailure {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.showButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, DashNavigationFragment.newInstance())
                .commitNow()
        }

        binding.startButton.setOnClickListener {
            MainScope().launch {
                val result = Dash.controller.setDestination(
                    PointDestination(longitude = -77.03394+0.01, latitude = 38.899929+0.01)
                )

                result.onSuccess {
                    Dash.controller.startNavigation(it.routes.first())
                }.onFailure {
                    Log.e(TAG, "setDestination failed: $it")
                }
            }
        }
    }

    private companion object {
        const val TAG = "SimpleNavTest"
    }
}