package com.example.appwebserviceconretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appwebserviceconretrofit.Interface.JsonApi;
import com.example.appwebserviceconretrofit.Modelo.banco;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.appwebserviceconretrofit.R.*;

public class MainActivity extends AppCompatActivity {

    Spinner txtRetrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        txtRetrofit=(Spinner) findViewById(id.txtRetrofit);
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