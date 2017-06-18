package test.nohttp.android.hyr.com.hellonohttp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.CacheMode;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.SimpleResponseListener;

public class CacheActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv;
    private ImageView iv;
    private Button image;
    private Button data;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 1.图片缓存
        // 2.数据缓存
        setContentView(R.layout.activity_cache);
        initView();

        // 创建请求队列
        requestQueue = NoHttp.newRequestQueue();
    }

    private void initView() {
        tv = (TextView) findViewById(R.id.tv);
        iv = (ImageView) findViewById(R.id.iv);
        image = (Button) findViewById(R.id.image);
        data = (Button) findViewById(R.id.data);

        image.setOnClickListener(this);
        data.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image:
                // 请求图片
                requestImage();
                break;
            case R.id.data:
                // 请求数据
                requestString();
                break;
        }
    }

    /**
     * 请求图片 使用缓存
     */
    private void requestImage() {
        Request<Bitmap> imageRequest = NoHttp.createImageRequest("http://api.nohttp.net/image", RequestMethod.GET);
        // 设置缓存模式 通常使用第二种
        imageRequest.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        requestQueue.add(2, imageRequest, new SimpleResponseListener<Bitmap>() {
            @Override
            public void onSucceed(int what, Response<Bitmap> response) {
                iv.setImageBitmap(response.get());
            }

            @Override
            public void onFailed(int what, Response<Bitmap> response) {
                Toast.makeText(CacheActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 请求数据 使用缓存
     */
    private void requestString() {
        // 创建请求对象
        Request<String> stringRequestGet = NoHttp.createStringRequest("http://api.nohttp.net/cache", RequestMethod.GET);
        stringRequestGet.add("name", "yanzhenjie");// String类型
        stringRequestGet.add("pwd", "123");
        // 设置缓存模式 通常使用第二种
        //1、Default模式，实现http 304重定向缓存 NoHttp本身是实现了RFC2616，所以这里不用设置或者设置为DEFAULT。
        //2、REQUEST_NETWORK_FAILED_READ_CACHE 当请求服务器失败的时候，读取缓存 请求服务器成功则返回服务器数据，如果请求服务器失败，读取缓存数据返回。
        //3、IF_NONE_CACHE_REQUEST_NETWORK 如果发现有缓存直接成功，没有缓存才请求服务器 我们知道ImageLoader的核心除了内存优化外，
        //剩下一个就是发现把内地有图片则直接使用，没有则请求服务器，所以NoHttp这一点非常使用做一个ImageLoader。
        //4、ONLY_REQUEST_NETWORK 仅仅请求网络 这里不会读取缓存，也不支持Http304。
        //5、ONLY_READ_CACHE 仅仅读取缓存 仅仅读取缓存，不会请求网络和其它操作。
        stringRequestGet.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);

        requestQueue.add(1, stringRequestGet, new SimpleResponseListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                tv.setText(what + ":请求成功:" + response.get());
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                tv.setText(what + ":请求失败:" + response.get());
            }
        });
    }
}
