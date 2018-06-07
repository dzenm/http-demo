package com.din.testhttp.express;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.din.testhttp.R;
import com.din.testhttp.databinding.ActivityExpressBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ExpressActivity extends AppCompatActivity{

    private ExpressAdapter expressAdapter;
    private ActivityExpressBinding bind;
    private List<Express> list;
    private static final String EXPRESS = "http://www.kuaidi100.com/query?type=yuantong&postid=11111111111";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_express);

        list = new ArrayList<>();
        expressAdapter = new ExpressAdapter(list);
        //  设置recyclerView方向
        bind.recyclerView.setLayoutManager(new LinearLayoutManager(ExpressActivity.this, LinearLayoutManager.VERTICAL, false));
        // 设置适配器
        bind.recyclerView.setAdapter(expressAdapter);
        bind.showData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getHTTPConnection();
                bind.recyclerView.setAdapter(expressAdapter);
            }
        });
    }

    private void getHTTPConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(EXPRESS)
                            .build();
                    Response response = client.newCall(request).execute();
                    String data = response.body().string();
                    Express(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }




    public void Express(String jsonData) {
        String time = null;
        String context = null;
        try {
            //  创建JSONObject对象
            JSONObject jsonObject = new JSONObject(jsonData);
            //  创建JSONArray数组
            JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
            for (int i = 0; i < jsonArray.length(); i++) {
                //  通过JSONObject对象获取数组中的内容
                JSONObject result = jsonArray.getJSONObject(i);
                //  通过getString获取对应的对象内容
                time = result.getString("time");
                context = result.getString("context");
                list.add(new Express(time, context));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

