import android.os.StrictMode;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class j {
    OkHttpClient okHttpClient = new OkHttpClient();
    Request request = new Request.Builder().url("http://10.1.164.225:5000/").build();

    /*okHttpClient.newCall(request).enqueue(new Callback() {
       @Override
       public void onFailure(@NotNull Call call, IOException e){

       }

       @Override
               public void onResponse(@NotNull Call call, @NotNULL Response response) throws IOException{

        }
    });*/
}

