package com.globussoft.readydoctors.patient.see_a_doctor_now;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.globussoft.readydoctors.patient.R;
import com.globussoft.readydoctors.patient.Utills.ConstantUrls;
import com.globussoft.readydoctors.patient.Utills.Singleton;
import com.globussoft.readydoctors.patient.model.MapPharmacyModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GLB-276 on 11/21/2015.
 */
public class SeeDoctorLocationActivity extends FragmentActivity implements
        com.google.android.gms.maps.GoogleMap.OnMarkerClickListener,
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
{

    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 1000 * 60 * 1; //1 minute
    private static final long FASTEST_INTERVAL = 1000 * 60 * 1; // 1 minute
    Button btnFusedLocation;
    TextView tvLocation;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    String mLastUpdateTime;
    GoogleMap googleMap;
    LatLngBounds curScreen;
    Boolean Added=true;
    public ArrayList<MapPharmacyModel> searchList=new ArrayList<MapPharmacyModel>();
    LatLng currentLatLng;
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate ...............................");
        //show error dialog if GoolglePlayServices not available
        if (!isGooglePlayServicesAvailable())
        {
            finish();
        }
        createLocationRequest();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        setContentView(R.layout.activity_location_google_map);
        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        googleMap = fm.getMap();
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setOnMarkerClickListener(this);


    }
    public void getCordinates()
    {
        curScreen = googleMap.getProjection().getVisibleRegion().latLngBounds;

        getNearByPharmacies();
    }
    public void getNearByPharmacies()
    {
        searchList.clear();
        System.out.println("northEast " + curScreen.northeast.latitude + " : " + curScreen.northeast.longitude);
        System.out.println("southWest " + curScreen.southwest.latitude + " : " + curScreen.southwest.longitude);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlNearByPharmacies,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {

                        System.out.println("searching near by pharmacy response" + response);
                        try {

                            JSONObject json = new JSONObject(response);


                            if (json.getString("code").equals("200"))
                            {
                                JSONArray array = json.getJSONArray("data");
                                for (int i=0;i<array.length();i++)
                                {
                                    MapPharmacyModel model = new MapPharmacyModel();
                                    JSONObject obj = array.getJSONObject(i);

                                    model.setPharmacyId(obj.getString("pharmacyId"));
                                    model.setPharmacyName(obj.getString("pharmacyName"));
                                    model.setCity(obj.getString("city"));
                                    model.setState(obj.getString("state"));
                                    model.setCountry(obj.getString("country"));
                                    model.setLatitude(obj.getString("lat"));
                                    model.setLongitude(obj.getString("lng"));
                                    model.setMailOrder(obj.getString("mailOrder"));
                                    model.setAvailabilty(obj.getString("availabilty"));
                                    model.setLongTermCare(obj.getString("longTermCare"));
                                    model.setPhoneNumber(obj.getString("phoneNumber"));
                                    model.setSpeciality(obj.getString("speciality"));
                                    model.setZipcode(obj.getString("zipcode"));

                                    searchList.add(model);

                                }
                                if(searchList.size()<=0)
                                {
                                    Toast.makeText(getApplicationContext(), "No Near by Pharmacies found ", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    for(int i=0;i<searchList.size();i++)
                                    {
                                        Double lat = Double.parseDouble(searchList.get(i).getLatitude());
                                        Double lng = Double.parseDouble(searchList.get(i).getLongitude());
                                        LatLng temp=new LatLng(lat, lng);
                                        googleMap.addMarker(new MarkerOptions()
                                                .position(temp)
                                                .title(searchList.get(i).getPharmacyName())
                                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                                                .snippet(searchList.get(i).getCity()+", "+searchList.get(i).getState()+", "+searchList.get(i).getCountry()));
                                    }

                                }
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "No Near by Pharmacies found ", Toast.LENGTH_SHORT).show();
                            }

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("NElat",""+curScreen.northeast.latitude);
                params.put("NElng",""+curScreen.northeast.longitude );
                params.put("SWlat",""+curScreen.southwest.latitude );
                params.put("SWlng", "" + curScreen.southwest.longitude);

                System.out.println("curScreen.northeast.latitude " + curScreen.northeast.latitude);
                System.out.println("curScreen.northeast.longitude "+curScreen.northeast.longitude);
                System.out.println("curScreen.southwest.latitude "+curScreen.southwest.latitude);
                System.out.println("curScreen.southwest.longitude "+curScreen.southwest.longitude);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }
    public static int convertToPixels(Context context, int nDP)
    {
        final float conversionScale = context.getResources().getDisplayMetrics().density;

        return (int) ((nDP * conversionScale) + 0.5f) ;

    }
    @Override
    public boolean onMarkerClick(Marker marker)
    {
        Log.i("GoogleMapActivity", "onMarkerClick");
        System.out.println("Clicked on marker");
        if(!marker.getTitle().equalsIgnoreCase("You"))
        {
            showDilog(marker.getTitle());
        }
        else
        {
            Toast.makeText(getApplicationContext()," You are here. ", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    public  void showDilog(final String title)
    {
        String message="Are you sure, you want to add "+title+" to your favorite pharmacy list.";
        AlertDialog.Builder alert = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        for(int i=0;i<searchList.size();i++)
                        {
                            if(searchList.get(i).getPharmacyName().equalsIgnoreCase(title))
                            {
                                System.out.println("its  there");
                                AddToFavoritePharmacy(searchList.get(i).getPharmacyId(),title, Singleton.PatientID);
                            }
                            else
                            {
                                System.out.println("Not there");
                            }
                        }
                    }
                });
        alert.show();
    }
    public void AddToFavoritePharmacy(final String pharmacyId,final String title,final String userId)
    {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlAddFavoritePharmacy,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("searching pharmacy response" + response);
                        try
                        {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("code").equals("200"))
                            {
//                                        Toast.makeText(LocationActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(),title+" added to your favorite pharmacy list. ", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(SeeDoctorLocationActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("pharmacyId", pharmacyId);
                params.put("userId", userId);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }
    @Override
    public void onStart()
    {
        super.onStart();
        Log.d(TAG, "onStart fired ..............");
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop fired ..............");
        mGoogleApiClient.disconnect();
        Log.d(TAG, "isConnected ...............: " + mGoogleApiClient.isConnected());
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected - isConnected ...............: " + mGoogleApiClient.isConnected());
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
        Log.d(TAG, "Location update started ..............: ");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed: " + connectionResult.toString());
    }

    @Override
    public void onLocationChanged(Location location)
    {
        Log.d(TAG, "Firing onLocationChanged..............................................");
        mCurrentLocation = location;
        System.out.println("mCurrentLocation " + mCurrentLocation.getLatitude() + " : " + mCurrentLocation.getLongitude());
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        if(Added)
        {
            currentLatLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
            addMarker(currentLatLng, "You");
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 13));
            Added=false;
        }

        getCordinates();

    }

    private void addMarker(final LatLng localcurrentLatLng,final String name)
    {
        MarkerOptions options = new MarkerOptions();

        // following four lines requires 'Google Maps Android API Utility Library'
        // https://developers.google.com/maps/documentation/android/utility/
        // I have used this to display the time as title for location markers
        // you can safely comment the following four lines but for this info

        /*IconGenerator iconFactory = new IconGenerator(this);
        iconFactory.setStyle(IconGenerator.STYLE_PURPLE);
        options.icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(mLastUpdateTime)));
        options.anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());*/


        /*LatLng currentLatLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());*/
        options.position(localcurrentLatLng);

        Marker mapMarker = googleMap.addMarker(options);
        long atTime = mCurrentLocation.getTime();
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date(atTime));

        mapMarker.setTitle(name);
        mapMarker.setSnippet(name);

        Log.d(TAG, name + " Marker added.............................");

        Log.d(TAG, "Zoom done.............................");
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        Log.d(TAG, "Location update stopped .......................");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
            Log.d(TAG, "Location update resumed .....................");
        }
    }
}