package com.example.learningleo.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.learningleo.R
import com.example.learningleo.repository.Marker
import com.example.learningleo.viewmodel.RoomViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private val roomViewModel : RoomViewModel by lazy{
        ViewModelProvider(this).get(RoomViewModel::class.java)
    }
    val MAPS_REQUEST_CODE = 111
    private lateinit var mMap: GoogleMap
    private lateinit var userEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        userEmail = intent.getStringExtra("Email")

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),MAPS_REQUEST_CODE)
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnMapLongClickListener { loc ->
            val intent = Intent(this, NewMarkerActivity::class.java)
            intent.putExtra("Loc",loc.toString())
            startActivityForResult(intent,1)
        }
        getLocation(mMap)
        getUserMarkers()
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray) {
        when (requestCode) {
            MAPS_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    finish()
                }
            }
        }}

    fun getLocation(googleMap: GoogleMap) {
        var currentLoc : LatLng? = null
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()
        val bestProvider = locationManager.getBestProvider(criteria, true)

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            locationManager.requestLocationUpdates(bestProvider,2000,0f,object : LocationListener {
                override fun onLocationChanged(location: Location?) {
                    if(location!=null){
                        val loc = LatLng(location!!.latitude, location!!.longitude)
                        if(loc != currentLoc)
                        {
                            googleMap.addMarker(MarkerOptions().position(loc).title("Você está aqui"))
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,15f))
                            currentLoc = loc
                        }
                    }
                }
                override fun onStatusChanged(provider: String?, p1: Int, p2: Bundle?) {
                    TODO("Not yet implemented")
                }

                override fun onProviderEnabled(provider: String?) {
                    TODO("Not yet implemented")
                }

                override fun onProviderDisabled(provider: String?) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    override fun onActivityResult(request: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(request, resultCode, data)
        if(request ==  1)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                val markerName = data?.getStringExtra("Name").toString()
                val loc = data!!.getStringExtra("Loc").toString().split(" ")[1]
                val lat = loc.split(",")[0].removePrefix("(")
                val long = loc.split(",")[1].removeSuffix(")")
                val new_loc = LatLng(lat.toDouble(),long.toDouble())

                roomViewModel.insert(Marker(0,userEmail,markerName,lat,long))
                mMap.addMarker(MarkerOptions().position(new_loc).title(markerName))
            }
        }
    }

    fun getUserMarkers()
    {
        roomViewModel.getByUser(userEmail){markerArray: Array<Marker>? ->
            markerArray?.forEach { marker ->
                mMap.addMarker(MarkerOptions().position(LatLng(marker.latitude.toDouble(),marker.longitude.toDouble())).title(marker.markerName))
            }
        }
    }
}






