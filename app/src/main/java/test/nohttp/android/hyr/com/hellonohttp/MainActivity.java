package test.nohttp.android.hyr.com.hellonohttp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.SimpleResponseListener;

public class MainActivity extends AppCompatActivity {

    private Button get;
    private Button post;
    private TextView tv;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        get = (Button) findViewById(R.id.get1);
        post = (Button) findViewById(R.id.post1);
        tv = (TextView) findViewById(R.id.textView);
        requestQueue = NoHttp.newRequestQueue();

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Request<String> request = NoHttp.createStringRequest("http://api.nohttp.net/method", RequestMethod.GET);
                // 如果要指定并发值，传入数字即可：NoHttp.newRequestQueue(3);
                request.add("name", "yanzhenjie");// String类型
                request.add("pwd", "123");
//                request.add("userAge", 20);// int类型
//                request.add("userSex", '1');// char类型，还支持其它类型
                requestQueue.add(0, request, new SimpleResponseListener<String>() {
                    @Override
                    public void onStart(int what) {
                        super.onStart(what);
                    }

                    @Override
                    public void onSucceed(int what, Response<String> response) {
                        super.onSucceed(what, response);
                        // 请求成功
                        tv.setText("成功" + response.get());
                    }

                    @Override
                    public void onFailed(int what, Response<String> response) {
                        super.onFailed(what, response);
                        // 请求失败
                        tv.setText("失败" + response.get());
                    }
                });
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Request<String> request = NoHttp.createStringRequest("http://api.nohttp.net/method", RequestMethod.POST);
                // 如果要指定并发值，传入数字即可：NoHttp.newRequestQueue(3);
                request.add("name", "yanzhenjie");// String类型
                request.add("pwd", "123");
                requestQueue.add(0, request, new SimpleResponseListener<String>() {
                    @Override
                    public void onStart(int what) {
                        super.onStart(what);
                    }

                    @Override
                    public void onSucceed(int what, Response<String> response) {
                        super.onSucceed(what, response);
                        // 请求成功
                        tv.setText("成功" + response.get());
                    }

                    @Override
                    public void onFailed(int what, Response<String> response) {
                        super.onFailed(what, response);
                        // 请求失败
                        tv.setText("失败" + response.get());
                    }
                });
            }
        });
    }

    public void startImageActivity(View view) {
        Intent intent = new Intent(this, ImageActivity.class);
        startActivity(intent);
    }

    public void startCacheActivity(View view) {
        Intent intent = new Intent(this, CacheActivity.class);
        startActivity(intent);
    }
}

