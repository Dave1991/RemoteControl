package xanxus.music;

import com.xanxus.AbstractLayout;
import com.xanxus.ClientRunnable;
import com.xanxus.MainActivity;
import com.xanxus.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * 控制音乐页面的布局
 * 
 * @author xanxus
 * 
 */
public class MusicLayout extends AbstractLayout {

	private Button prevButton;
	private Button nextButton;
	private Button volumeUpButton;
	private Button volumeDownButton;
	private Button pauseButton;

	public MusicLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		firstCommand = mainActivity.getPreferences().getString("music1", "vd");
		secondCommand = mainActivity.getPreferences().getString("music2", "vu");
		thirdCommand = mainActivity.getPreferences().getString("music3", "ps");
		fourthCommand = mainActivity.getPreferences().getString("music4", "pa");
		fifthCommand = mainActivity.getPreferences().getString("music5", "ns");
		sixthCommand = mainActivity.getPreferences().getString("music6", "ps");
		seventhCommand = mainActivity.getPreferences()
				.getString("music7", "ns");
		eightCommand = mainActivity.getPreferences().getString("music8", "ns");
		LayoutInflater.from(context).inflate(R.layout.music_layout, this, true);
		leftButton = (Button) this.findViewById(R.id.ms_left_btn);
		rightButton = (Button) this.findViewById(R.id.ms_right_btn);
		prevButton = (Button) this.findViewById(R.id.prev_song_btn);
		nextButton = (Button) this.findViewById(R.id.next_song_btn);
		volumeUpButton = (Button) this.findViewById(R.id.volume_up_btn);
		volumeDownButton = (Button) this.findViewById(R.id.volume_down_btn);
		pauseButton = (Button) this.findViewById(R.id.music_pause_btn);
		touchLayout = (LinearLayout) this.findViewById(R.id.music_touch_layout);
		((MainActivity) context).addTouchView(touchLayout);
		setButtonHeight();
		setButtonHandler();
	}

	/**
	 * 设置按钮高度，适应不同设备的屏幕
	 */
	private void setButtonHeight() {
		((MainActivity) mContext).setButtonHeight(prevButton);
		((MainActivity) mContext).setButtonHeight(nextButton);
		((MainActivity) mContext).setButtonHeight(volumeDownButton);
		((MainActivity) mContext).setButtonHeight(volumeUpButton);
		((MainActivity) mContext).setButtonHeight(pauseButton);
	}

	/**
	 * 绑定button监听器
	 */
	public void setButtonHandler() {
		// TODO Auto-generated method stub
		super.setButtonHandler();
		volumeUpButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new ClientRunnable(secondCommand, mainActivity
						.getHandler())).start();
			}
		});
		volumeDownButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new ClientRunnable(firstCommand, mainActivity
						.getHandler())).start();
			}
		});
		prevButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new ClientRunnable(thirdCommand, mainActivity
						.getHandler())).start();
			}
		});
		nextButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new ClientRunnable(fifthCommand, mainActivity
						.getHandler())).start();
			}
		});
		pauseButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new ClientRunnable(fourthCommand, mainActivity
						.getHandler())).start();
				if (pauseButton.getText().toString().equals("暂停"))
					pauseButton.setText("播放");
				else {
					pauseButton.setText("暂停");
				}
			}
		});

	}

}
