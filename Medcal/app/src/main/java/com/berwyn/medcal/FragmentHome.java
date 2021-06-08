package com.berwyn.medcal;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class FragmentHome extends Fragment {

    ImageView imgview;
    Button btnTP;
    Button btnScn;
    Button btnSetAl;
    Uri imageUri;
    static int PreqCode = 1;
    final int galery = 100;
    String selectedImagePath;

    public static final int RESULT_OK = -1;

    public FragmentHome() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getActivity().getApplication(), NewMedsActivity.class);
                startActivity(intent);

                /*
                Fragment frag = new FragmentNewMeds();
                FragmentTransaction Ft = getFragmentManager().beginTransaction();

                Ft.replace(R.id.nav_home, frag);
                Ft.addToBackStack(null);

                Ft.commit();

                 */

            }
        });

        btnScn = (Button) view.findViewById(R.id.btnscan);
        btnTP = (Button) view.findViewById(R.id.btntakepic);
        imgview = (ImageView) view.findViewById(R.id.hasilfoto);

        btnTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tp = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(tp, galery);
            }
        });



        btnScn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String img = imageUri.getPath();
                final String backgroundImageName = String.valueOf(imgview.getTag());



                OkHttpClient okHttpClient = new OkHttpClient();

                RequestBody formBody = new FormBody.Builder().add("image",img).build();

                //http://35.239.15.89:5000/image
                Request req = new Request.Builder()
                        .url("http://35.239.15.89:5000/image")
                        .post(formBody)
                        .build();

                okHttpClient.newCall(req).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull final IOException e) {
                        call.cancel();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(),"network not found!",Toast.LENGTH_SHORT).show();
                            }
                        });


                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                Toast.makeText(getActivity(),"Connected to The Server",Toast.LENGTH_SHORT).show();

                                /*
                                try {
                                    tv.setText(response.body().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                 */

                                String str="http://35.239.15.89:5000/image2";
                                String JSON_STRING = "obat";

                                String medicineName;
                                URLConnection urlConn = null;
                                BufferedReader bufferedReader = null;

                                //Gson gson = new Gson();

                                try{
                                    AssetManager assetManager = getActivity().getAssets();
                                    InputStream ims = assetManager.open("http://35.239.15.89:5000/image/myData.json");

                                    Gson gson = new Gson();
                                    Reader reader = new InputStreamReader(ims);

                                    Obat obatObj = gson.fromJson(reader, Obat.class);
                                    TextView tv = (TextView) view.findViewById(R.id.medsname);
                                    tv.setText(obatObj.Obat);

                                }catch(IOException e) {
                                    e.printStackTrace();

                                    /*
                                    Reader reader = new FileReader("http://35.239.15.89:5000/image/.json"
                                    // Convert JSON File to Java Object
                                    Obat o = gson.fromJson(reader, Obat.class);

                                    // print staff
                                    tv.setText(o.Obat);

                                } catch (IOException e) {
                                    e.printStackTrace();

                                     */
                                }

                                /*
                                try {
                                    URL url = new URL(str);
                                    urlConn = url.openConnection();
                                    bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

                                    StringBuffer stringBuffer = new StringBuffer();
                                    String line;
                                    while ((line = bufferedReader.readLine()) != null)
                                    {
                                        stringBuffer.append(line);
                                    }

                                    //return new JSONObject(stringBuffer.toString());
                                    return
                                }
                                catch(Exception ex)
                                {
                                    Log.e("App", "yourDataTask", ex);
                                    return null;
                                }
                                finally
                                {
                                    if(bufferedReader != null)
                                    {
                                        try {
                                            bufferedReader.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }


                                try {
                                    // get JSONObject from JSON file
                                    JSONObject obj = new JSONObject(JSON_STRING);

                                    // fetch JSONObject named employee
                                    JSONObject employee = obj.getJSONObject("obat");

                                    // get employee name and salary
                                    medicineName = employee.getString("obat");

                                    // set employee name and salary in TextView's
                                    tv.setText(medicineName);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                 */

                            }
                        });
                    }

                });
            }
        });






        /*
        imgview = view.findViewById(R.id.hasilfoto);

        btnScn = view.findViewById(R.id.btnscan);
        btnSetAl = view.findViewById(R.id.btnset);

        if (ContextCompat.checkSelfPermission(FragmentHome.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(FragmentHome.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },
                    100);
        }

         */
        // return v;


        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Home");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == galery && resultCode == RESULT_OK){
            imageUri = data.getData();
            imgview.setImageURI(imageUri);
        }
    }

}