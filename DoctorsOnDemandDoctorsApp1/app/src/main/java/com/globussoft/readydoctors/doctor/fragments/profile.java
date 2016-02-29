package com.globussoft.readydoctors.doctor.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.globussoft.readydoctors.doctor.Activity.MainActivity;
import com.globussoft.readydoctors.doctor.R;
import com.globussoft.readydoctors.doctor.lazylist.ImageLoader;
import com.globussoft.readydoctors.doctor.uttils.ConnectionDetector;
import com.globussoft.readydoctors.doctor.uttils.ConstantTag;
import com.globussoft.readydoctors.doctor.uttils.ConstantUrls;
import com.globussoft.readydoctors.doctor.uttils.FileUtils;
import com.globussoft.readydoctors.doctor.uttils.MainSingleton;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class profile extends Fragment {
    View rootView;
    TextView dName, editName, editDepartment, editExperiance;
    RelativeLayout edit_shedule, saveprofileRlt;
    EditText editContactNo, editAboutUs, editAddress;
    ProgressBar progress;
    ImageView profile_pic;
    ImageLoader imageLoader;
    private static int RESULT_LOAD_IMG = 1;
    String imagePath = "";
    int serverResponseCode;
   String serverResponseMessage;
    public String response = "", str;
    ProgressDialog progressDialog;
    String availbleTo="10:30";
    String availbleFrom="09:50";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.profile_fragment, container, false);
        InitUI();
        if(new ConnectionDetector(getActivity()).isConnectingToInternet())
        {
            FetchProfile(MainSingleton.doctor_id);
        }
        else
        {
            Toast.makeText(getActivity(), "You dont have Internet...!", Toast.LENGTH_SHORT).show();
        }

        return rootView;

    }

    void InitUI() {
        edit_shedule = (RelativeLayout) rootView.findViewById(R.id.EditSheduleRlt);
        editName = (TextView) rootView.findViewById(R.id.editName);
        editDepartment = (TextView) rootView.findViewById(R.id.editDepartment);
        editExperiance = (TextView) rootView.findViewById(R.id.editExperiance);
        progress = (ProgressBar) rootView.findViewById(R.id.progress);
        dName = (TextView) rootView.findViewById(R.id.dName);
        editContactNo = (EditText) rootView.findViewById(R.id.editContactNo);
        editAboutUs = (EditText) rootView.findViewById(R.id.aboutus);
        editAddress = (EditText) rootView.findViewById(R.id.editAddress);
        profile_pic = (ImageView) rootView.findViewById(R.id.profile_pic);
        saveprofileRlt= (RelativeLayout) rootView.findViewById(R.id.saveprofileRlt);
        editName.setText(MainSingleton.name);
        dName.setText(MainSingleton.name);
        System.out.print("MainSingleton.nam=" + MainSingleton.name);
        imageLoader = new ImageLoader(getActivity());
        edit_shedule.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(new ConnectionDetector(getActivity()).isConnectingToInternet())
                {
                    FragmentTransaction ftran = MainActivity.fragmentmanager.beginTransaction();
                    ftran.replace(R.id.container, new EditSchedule()).commit();
                }
                else
                {
                    Toast.makeText(getActivity(), "You dont have Internet...!", Toast.LENGTH_SHORT).show();
                }


            }
        });
        saveprofileRlt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(new ConnectionDetector(getActivity()).isConnectingToInternet())
                {
                    UpdateProfile(MainSingleton.doctor_id,editAddress.getText().toString(),editAboutUs.getText().toString(),editContactNo.getText().toString(),availbleTo,availbleFrom);
                }
                else
                {
                    Toast.makeText(getActivity(), "You dont have Internet...!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        profile_pic.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(new ConnectionDetector(getActivity()).isConnectingToInternet())
                {
                    picImage();
                }
                else
                {
                    Toast.makeText(getActivity(), "You dont have Internet...!", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RESULT_LOAD_IMG && resultCode == getActivity().RESULT_OK && data != null) {
            Uri pickedImageuri = data.getData();


            int columnIndex;

            Bitmap bitmap;
            imagePath=FileUtils.getPath(getActivity(), pickedImageuri);
            bitmap =getBitmapFromPath(FileUtils.getPath(getActivity(), pickedImageuri));
            profile_pic.setImageBitmap(bitmap);
            if(new ConnectionDetector(getActivity()).isConnectingToInternet())
            {
                new UploadImagetask().execute(imagePath);
            }
            else
            {
                Toast.makeText(getActivity(), "You dont have Internet...!", Toast.LENGTH_SHORT).show();
            }



        }
    }

    public class UploadImagetask extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            String imagepath = params[0];

            uploadFile(imagepath);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait....");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();

        }


        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
        }
    }


    public void FetchProfile(final String doctor_id) {
        progress.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlProfile,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("profile fetch response" + response);
                        try {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("code").equals("200")) {

                                JSONObject obj = json.getJSONObject(ConstantTag.tag_Data);
                                editDepartment.setText(obj.getString("departmentId"));
                                editExperiance.setText(obj.getString("Experience"));
                                editContactNo.setText(obj.getString("doctorContactNumber"));
                                editAboutUs.setText(obj.getString("doctorAbout"));
                                editAddress.setText(obj.getString("doctorAddress"));
                                String profilepic_url = obj.getString("doctorProfilePicUrl");
                                imageLoader.DisplayImage(profilepic_url, profile_pic);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        progress.setVisibility(View.INVISIBLE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.setVisibility(View.INVISIBLE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("doctorId", doctor_id);


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


    protected void picImage()
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);


    }
    public Bitmap getBitmapFromPath(String pathName)
    {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(pathName, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize1(options, 640, 640);

        // Decode bitmap with inSampleSize set

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(pathName, options);

    }

    public static int calculateInSampleSize1 (BitmapFactory.Options options,
                                              int reqWidth, int reqHeight)
    {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }


    public int uploadFile(String sourceFile_A) {

        String fileNameA = sourceFile_A;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesReadA, bytesAvailableA, bufferSizeA;
        int bytesReadB, bytesAvailableB, bufferSizeB;
        byte[] bufferA;
        byte[] bufferB;
        int maxBufferSize = 1 * 1024 * 1024;

        System.out.println("sourceFileUri A=" + sourceFile_A);


        try {
            byte[] image;
            URL url = new URL(ConstantUrls.UrlMain+ConstantUrls.UrlUpdateProfilePic);

            // Open a HTTP connection to the URL
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);

            dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"doctorId\""
                    + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(MainSingleton.doctor_id); // mobile_no
            // is
            // String
            // variable
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);





            // upload image 1
            if (sourceFile_A.length() == 0) {

            } else {
                File sourceFileA = new File(sourceFile_A);
                FileInputStream fileInputStreamA = new FileInputStream(
                        sourceFileA);
                if (!sourceFileA.isFile()) {

                } else {
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"profilepic\";filename="
                            + fileNameA + "" + lineEnd);
                    dos.writeBytes(lineEnd);
                    bytesAvailableA = fileInputStreamA.available();
                    bufferSizeA = Math.min(bytesAvailableA, maxBufferSize);
                    bufferA = new byte[bufferSizeA];
                    bytesReadA = fileInputStreamA.read(bufferA, 0, bufferSizeA);

                    while (bytesReadA > 0) {

                        dos.write(bufferA, 0, bufferSizeA);
                        bytesAvailableA = fileInputStreamA.available();
                        bufferSizeA = Math.min(bytesAvailableA, maxBufferSize);
                        bytesReadA = fileInputStreamA.read(bufferA, 0,
                                bufferSizeA);

                    }
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                }
                // close the streams //
                fileInputStreamA.close();
            }


            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();
            serverResponseMessage = conn.getResponseMessage();



            Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage
                    + ": " + serverResponseCode);

            String serverResponseMessage = conn.getResponseMessage();

            try {
                DataInputStream inStream = new DataInputStream(
                        conn.getInputStream());
                while ((str = inStream.readLine()) != null) {
                    if (str != null) {
                        response = response + str;
                        // System.out.println("My Server Response "+response);
                        Log.e("Debug", "Server Response " + response);
                    }
                }
                inStream.close();
            } catch (IOException ioex) {
                Log.e("Debug", "error: " + ioex.getMessage(), ioex);
            }
            // System.out.println("******************    "+serverResponseMessage);
            Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage
                    + ": " + serverResponseCode);

            dos.flush();
            dos.close();

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            getActivity().runOnUiThread(new Runnable() {
                public void run() {

                    Toast.makeText(getActivity(), "MalformedURLException",
                            Toast.LENGTH_SHORT).show();
                }
            });

            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
        } catch (Exception e) {
            e.printStackTrace();

            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(),
                            "Got Exception : see logcat ", Toast.LENGTH_SHORT)
                            .show();
                }
            });
            Log.e("Upload file to serve", "Exception : " + e.getMessage(), e);
        }

        conn.disconnect();
        return serverResponseCode;
    }



    public void UpdateProfile(final String doctor_id,final String doctorAddress,final String doctorAbout,
                              final  String doctorContactNumber,final String doctorAvailableTo,final String doctorAvailableFrom) {
        progress.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest sr = new StringRequest(Request.Method.POST,
                ConstantUrls.UrlMain + ConstantUrls.UrlUpdateprofile,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("Profile update  response" + response);
                        try {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("code").equals("200")) {

                               Toast.makeText(getActivity(),"Success",Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        progress.setVisibility(View.INVISIBLE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.setVisibility(View.INVISIBLE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("doctorid", doctor_id);
                params.put("doctorAddress", doctorAddress);
                params.put("doctorAbout", doctorAbout);
                params.put("doctorContactNumber", doctorContactNumber);
                params.put("doctorAvailableTo", doctorAvailableTo);
                params.put("doctorAvailableFrom", doctorAvailableFrom);



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

}