package test.nohttp.android.hyr.com.hellonohttp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.SimpleResponseListener;

public class ImageActivity extends AppCompatActivity {

    private ImageView iv_post;
    private ImageView iv_get;
    private static final String IMAGE_URL = "http://api.nohttp.net/image";
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        iv_get = (ImageView) findViewById(R.id.iv_get);
        iv_post = (ImageView) findViewById(R.id.iv_post);

        // 1.创建请求队列
        requestQueue = NoHttp.newRequestQueue();

        //TODO GET请求图片
        // 2.创建请求对象
        Request<Bitmap> imageRequestGet = NoHttp.createImageRequest(IMAGE_URL, RequestMethod.GET);

        // 3.讲请求对象放入请求队列中
        requestQueue.add(1, imageRequestGet, new SimpleResponseListener<Bitmap>() {
            @Override
            public void onStart(int what) {
                super.onStart(what);
            }

            @Override
            public void onSucceed(int what, Response<Bitmap> response) {
                iv_get.setImageBitmap(response.get());
            }

            @Override
            public void onFailed(int what, Response<Bitmap> response) {
                Toast.makeText(ImageActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish(int what) {
                super.onFinish(what);
            }
        });

        //TODO POST请求图片
        // 2.创建请求对象
        Request<Bitmap> imageRequestPost = NoHttp.createImageRequest(IMAGE_URL, RequestMethod.POST);

        // 3.讲请求对象放入请求队列中
        requestQueue.add(1, imageRequestPost, new SimpleResponseListener<Bitmap>() {
            @Override
            public void onStart(int what) {
                super.onStart(what);
            }

            @Override
            public void onSucceed(int what, Response<Bitmap> response) {
                iv_post.setImageBitmap(response.get());
            }

            @Override
            public void onFailed(int what, Response<Bitmap> response) {
                Toast.makeText(ImageActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish(int what) {
                super.onFinish(what);
            }
        });


    }
}

