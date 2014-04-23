package com.boredream.http;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.boredream.eshop.R;

public class VolleyDemo extends Activity {

	private TextView tv_1;
	private ImageView iv_1;
	private NetworkImageView iv_netiv1, iv_netiv2, iv_netiv3, iv_netiv4,
			iv_netiv5;
	private static final String CITY_CODE_URL = "http://api.map.baidu.com/telematics/v3/weather?location=北京&output=json&ak=640f3985a6437dad8135dae98d775a09";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_volley);
		
		tv_1 = (TextView) findViewById(R.id.tv_1);

		iv_1 = (ImageView) findViewById(R.id.iv_1);

		iv_netiv1 = (NetworkImageView) findViewById(R.id.iv_netiv1);
		iv_netiv2 = (NetworkImageView) findViewById(R.id.iv_netiv2);
		iv_netiv3 = (NetworkImageView) findViewById(R.id.iv_netiv3);
		iv_netiv4 = (NetworkImageView) findViewById(R.id.iv_netiv4);
		iv_netiv5 = (NetworkImageView) findViewById(R.id.iv_netiv5);

		getJSONByVolley();
		loadImageByVolley();
		showImageByNetworkImageView();
		getStringVolley();
	}

	private void getStringVolley() {
		RequestQueue requestQueue = Volley.newRequestQueue(this);
		StringRequest sRequest = new StringRequest(Request.Method.GET,
				CITY_CODE_URL, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.i("DDD", response);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						Log.i("DDD", "sorry,Error");
					}
				});
		sRequest.setShouldCache(false);
		requestQueue.add(sRequest);
	}

	/**
	 * 利用Volley获取JSON数据
	 */
	private void getJSONByVolley() {
		RequestQueue requestQueue = Volley.newRequestQueue(this);
		final ProgressDialog progressDialog = ProgressDialog.show(this,
				"This is title", "...Loading...");

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, "http://42.96.153.196:8080/AndroidService.svc/GetGroupInformationList?groupID=549&userID=2416&page=1", null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.i("DDD", "response=" + response);
						tv_1.setText(response.toString());
						if (progressDialog.isShowing()
								&& progressDialog != null) {
							progressDialog.dismiss();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						tv_1.setText(arg0.toString());
						Log.i("DDD", "sorry,Error");
						if (progressDialog.isShowing()
								&& progressDialog != null) {
							progressDialog.dismiss();
						}
					}
				});
		requestQueue.add(jsonObjectRequest);
	}

	/**
	 * 利用Volley异步加载图片
	 * 
	 * 注意方法参数: getImageListener(ImageView view, int defaultImageResId, int
	 * errorImageResId) 第一个参数:显示图片的ImageView 第二个参数:默认显示的图片资源 第三个参数:加载错误时显示的图片资源
	 */
	private void loadImageByVolley() {
		String imageUrl = "http://www.eoeandroid.com/static/image/common/eoe_logo.png";
		RequestQueue requestQueue = Volley.newRequestQueue(this);
		final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(
				20);
		ImageCache imageCache = new ImageCache() {
			@Override
			public void putBitmap(String key, Bitmap value) {
				lruCache.put(key, value);
			}

			@Override
			public Bitmap getBitmap(String key) {
				return lruCache.get(key);
			}
		};
		ImageLoader imageLoader = new ImageLoader(requestQueue, imageCache);
		ImageListener listener = ImageLoader.getImageListener(iv_1,
				R.drawable.ic_launcher, R.drawable.ic_launcher);
		imageLoader.get(imageUrl, listener);
	}

	private void showImageByNetworkImageView() {
		String imageUrl1 = "http://avatar.csdn.net/6/6/D/1_lfdfhl.jpg";
		String imageUrl2 = "http://t12.baidu.com/it/u=3581266748,3370024907&fm=58";
		String imageUrl3 = "http://t2.baidu.com/it/u=2746688282,2286805359&fm=21&gp=0.jpg";
		String imageUrl4 = "http://t10.baidu.com/it/u=2344869391,1964162964&fm=21&gp=0.jpg";
		String imageUrl5 = "http://t12.baidu.com/it/u=2783524012,4149039143&fm=23&gp=0.jpg";
		RequestQueue requestQueue = Volley.newRequestQueue(this);
		final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(
				20);
		ImageCache imageCache = new ImageCache() {
			@Override
			public void putBitmap(String key, Bitmap value) {
				lruCache.put(key, value);
			}

			@Override
			public Bitmap getBitmap(String key) {
				return lruCache.get(key);
			}
		};
		ImageLoader imageLoader = new ImageLoader(requestQueue, imageCache);
		iv_netiv1.setTag("url_1");
		iv_netiv2.setTag("url_2");
		iv_netiv3.setTag("url_3");
		iv_netiv4.setTag("url_4");
		iv_netiv5.setTag("url_5");

		iv_netiv1.setImageUrl(imageUrl1, imageLoader);
		iv_netiv2.setImageUrl(imageUrl2, imageLoader);
		iv_netiv3.setImageUrl(imageUrl3, imageLoader);
		iv_netiv4.setImageUrl(imageUrl4, imageLoader);
		iv_netiv5.setImageUrl(imageUrl5, imageLoader);
	}

}
