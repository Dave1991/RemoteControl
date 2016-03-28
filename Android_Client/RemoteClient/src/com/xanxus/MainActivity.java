package com.xanxus;

import org.json.JSONException;
import org.json.JSONObject;

import xanxus.config.ConfigLayout;
import xanxus.explorer.ExplorerLayout;
import xanxus.music.MusicLayout;
import xanxus.ppt.PPTLayout;
import xanxus.shake.OnShakeListener;
import xanxus.shake.ShakeListener;
import xanxus.touch.SlideView;
import xanxus.touch.TouchView;
import xanxus.video.VideoLayout;

import com.xanxus.mytabview.TabView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.R.bool;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	// 保存用户设置的sharepreference
	private SharedPreferences preferences = null;
	private SharedPreferences.Editor editor = null;
	public static final int PORT = 43026;
	public static String serverIP = "0.0.0.0";
	public static String serverOS = "windows";
	private View configView = null;
	private TabView myTabView = null;
	private RelativeLayout mainLayout = null;
	private AbstractLayout pptLayout = null;
	private AbstractLayout musicLayout = null;
	private AbstractLayout videoLayout = null;
	private AbstractLayout explorerLayout = null;

	// 检测摇晃传感器
	private ShakeListener mShakeListener = null;
	private OnShakeListener onShakeListener = null;

	// 检测出摇晃时震动
	private Vibrator vibrator = null;
	private boolean isShake = true;
	private int sensitivity = 2000;

	private Handler handler = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mainLayout = new RelativeLayout(this);

		mShakeListener = new ShakeListener(this);
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		onShakeListener = new OnShakeListener() {

			public void onShake() {
				// TODO Auto-generated method stub
				vibrator.vibrate(100);
				if (isShake) {
					if (myTabView.getCurrentTag().equals("ppt"))
						new Thread(new ClientRunnable(
								pptLayout.getEightCommand(), handler)).start();
					else if (myTabView.getCurrentTag().equals("music")) {
						new Thread(new ClientRunnable(
								musicLayout.getEightCommand(), handler))
								.start();
					} else if (myTabView.getCurrentTag().equals("video")) {
						new Thread(new ClientRunnable(
								videoLayout.getEightCommand(), handler))
								.start();
					}
				}
			}
		};
		mShakeListener.setOnShakeListener(onShakeListener);
		// 保存用户设置的sharedpreference
		preferences = getSharedPreferences("RemoteControl.conf", MODE_PRIVATE);
		editor = preferences.edit();

		// 从上一次用户设置的ip和os中读取
		serverIP = preferences.getString("serverIP", "0.0.0.0");
		serverOS = preferences.getString("serverOS", "windows");
		isShake = preferences.getBoolean("isShake", true);
		sensitivity = preferences.getInt("sensitivity", 2000);

		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == 0x110) {
					String jsonString = msg.obj.toString();
					try {
						JSONObject jsonObject = new JSONObject(jsonString);
						String function = jsonObject.getString("fun");
						if (function.equals("explorer")) {
							((ExplorerLayout) explorerLayout)
									.notifyFileList(jsonObject);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				super.handleMessage(msg);
			}
		};
		addTabView();
		this.setContentView(mainLayout);
	}

	public void addTabView() {
		myTabView = new TabView(this, LinearLayout.HORIZONTAL);
		myTabView.setUpInAnimation(R.anim.push_left_in);
		myTabView.setUpOutAnimation(R.anim.push_left_out);
		myTabView.setDownInAnimation(R.anim.push_right_in);
		myTabView.setDownOutAnimation(R.anim.push_right_out);
		if (pptLayout == null)
			// 初始化ppt控制布局
			pptLayout = new PPTLayout(this);
		myTabView.addTab("ppt", "幻灯片", pptLayout,
				getResources().getDrawable(R.drawable.ppt));
		if (musicLayout == null)
			// 初始化音乐控制布局
			musicLayout = new MusicLayout(this);
		myTabView.addTab("music", "音乐", musicLayout, getResources()
				.getDrawable(R.drawable.music));
		if (videoLayout == null)
			// 初始化视频控制布局
			videoLayout = new VideoLayout(this);
		myTabView.addTab("video", "视频", videoLayout, getResources()
				.getDrawable(R.drawable.video));
		if (explorerLayout == null)
			explorerLayout = new ExplorerLayout(this);
		myTabView.addTab("explorer", "文件浏览", explorerLayout, getResources()
				.getDrawable(R.drawable.folder));
		if (configView == null)
			// 初始化设置界面布局
			configView = new ConfigLayout(this);
		myTabView.addTab("setting", "设置", configView, getResources()
				.getDrawable(R.drawable.setting));
		mainLayout.addView(myTabView);
	}

	/**
	 * 给当前layout添加触摸板
	 * 
	 * @param touchLayout
	 */
	public void addTouchView(LinearLayout touchLayout) {
		TouchView touchView = new TouchView(this);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, 1);
		params.rightMargin = 5;
		touchLayout.addView(touchView, params);

		params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, 4);
		SlideView slideView = new SlideView(this);
		touchLayout.addView(slideView, params);
	}

	/**
	 * 设置按钮高度，为屏幕高度的1/6
	 * 
	 * @param button
	 */
	public void setButtonHeight(Button button) {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		button.setHeight(metrics.heightPixels / 6);
		button.setHeight(metrics.heightPixels / 6);
		button.setHeight(metrics.heightPixels / 6);
		button.setHeight(metrics.heightPixels / 6);
		button.setHeight(metrics.heightPixels / 6);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_VOLUME_UP:
			if (myTabView.getCurrentTag().equals("ppt")) {
				new Thread(new ClientRunnable(pptLayout.getSixthCommand(),
						handler)).start();
			} else if (myTabView.getCurrentTag().equals("music")) {
				new Thread(new ClientRunnable(musicLayout.getSixthCommand(),
						handler)).start();
			} else if (myTabView.getCurrentTag().equals("video")) {
				new Thread(new ClientRunnable(videoLayout.getSixthCommand(),
						handler)).start();
			}
			return true;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			if (myTabView.getCurrentTag().equals("ppt"))
				new Thread(new ClientRunnable(pptLayout.getSeventhCommand(),
						handler)).start();
			else if (myTabView.getCurrentTag().equals("music")) {
				new Thread(new ClientRunnable(musicLayout.getSeventhCommand(),
						handler)).start();
			} else if (myTabView.getCurrentTag().equals("video")) {
				new Thread(new ClientRunnable(videoLayout.getSeventhCommand(),
						handler)).start();
			}
			return true;
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	public AbstractLayout getPptLayout() {
		return pptLayout;
	}

	public void setPptLayout(AbstractLayout pptLayout) {
		this.pptLayout = pptLayout;
	}

	public AbstractLayout getMusicLayout() {
		return musicLayout;
	}

	public void setMusicLayout(AbstractLayout musicLayout) {
		this.musicLayout = musicLayout;
	}

	public AbstractLayout getVideoLayout() {
		return videoLayout;
	}

	public void setVideoLayout(AbstractLayout videoLayout) {
		this.videoLayout = videoLayout;
	}

	public SharedPreferences getPreferences() {
		return preferences;
	}

	public SharedPreferences.Editor getEditor() {
		return editor;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mShakeListener.stop();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mShakeListener.stop();
	}

	public boolean isShake() {
		return isShake;
	}

	public void setShake(boolean isShake) {
		this.isShake = isShake;
	}

	public int getSensitivity() {
		return sensitivity;
	}

	public void setSensitivity(int sensitivity) {
		this.sensitivity = sensitivity;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation != Configuration.ORIENTATION_PORTRAIT)
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

}
