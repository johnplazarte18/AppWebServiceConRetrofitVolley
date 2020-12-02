package com.example.appwebserviceconretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appwebserviceconretrofit.Interface.JsonApi;
import com.example.appwebserviceconretrofit.Modelo.banco;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;

import static com.example.appwebserviceconretrofit.R.*;

public class MainActivity extends AppCompatActivity {

    Spinner txtRetrofit,txtVolley;

    RequestQueue requestQueue;

    String url="https://api-uat.kushkipagos.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        txtRetrofit=(Spinner) findViewById(id.txtRetrofit);
        txtVolley=(Spinner) findViewById(id.txtVolley);
        requestQueue= Volley.newRequestQueue(this);
    }
    private void jsonArrayRequest(){
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(
                Request.Method.GET, url,
                null,
                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int size=response.length();
                        for (int i=0;i<size;i++){
                            try {
                                JSONObject jsonObject=new JSONObject(response.get(i).toString());
                                ArrayList<String> lista=new ArrayList<String>();
                                lista.add(jsonObject.getString("code")+" : "+jsonObject.getString("name"));

                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
                , new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){@Override
        public String getBodyContentType() {
            return "transfer/v1/bankList; charset=utf-8";
        }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();

                params.put("Public-Merchant-Id:", "d6d229453ee34affa819cd9014cac51b");

                return params;
            }
        };
        requestQueue.add(jsonArrayRequest);
    }
    public void click_consultar2(View view){
        jsonArrayRequest();
    }









    public void click_consultar(View view){
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://api-uat.kushkipagos.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonApi ap=retrofit.create(JsonApi.class);
        Call<List<banco>> call=ap.getbanco();
        call.enqueue(new Callback<List<banco>>() {
            @Override
            public void onResponse(Call<List<banco>> call, Response<List<banco>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                    return;
                }
                List<banco> ltbanco=response.body();
                ArrayList<String> lista=new ArrayList<String>();
                for(banco b:ltbanco){
                    lista.add(b.getCode()+" : "+b.getName());
                }
                txtRetrofit.setAdapter(new ArrayAdapter<String>(getApplicationContext(), layout.support_simple_spinner_dropdown_item,lista));
            }

            @Override
            public void onFailure(Call<List<banco>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}