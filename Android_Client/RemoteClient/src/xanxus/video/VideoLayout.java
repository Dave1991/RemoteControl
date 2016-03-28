package xanxus.video;

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

public class VideoLayout extends AbstractLayout {

	private Button fullScreenButton;
	private Button muteButton;
	private Button prevButton;
	private Button nextButton;
	private Button pauseButton;

	public VideoLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		firstCommand = mainActivity.getPreferences().getString("video1",
				"ay ctrl enter");
		secondCommand = mainActivity.getPreferences().getString("video2",
				"ay ctrl m");
		thirdCommand = mainActivity.getPreferences().getString("video3",
				"ay pageup");
		fourthCommand = mainActivity.getPreferences().getString("video4",
				"ay space");
		fifthCommand = mainActivity.getPreferences().getString("video5",
				"ay pagedown");
		sixthCommand = mainActivity.getPreferences().getString("video6",
				"ay pageup");
		seventhCommand = mainActivity.getPreferences().getString("video7",
				"ay pagedown");
		eightCommand = mainActivity.getPreferences().getString("video8",
				"ay pagedown");
		LayoutInflater.from(context).inflate(R.layout.video_layout, this, true);
		leftButton = (Button) this.findViewById(R.id.vd_left_btn);
		rightButton = (Button) this.findViewById(R.id.vd_right_btn);
		prevButton = (Button) this.findViewById(R.id.prev_btn);
		nextButton = (Button) this.findViewById(R.id.next_btn);
		fullScreenButton = (Button) this.findViewById(R.id.full_screen_btn);
		muteButton = (Button) this.findViewById(R.id.mute_btn);
		pauseButton = (Button) this.findViewById(R.id.video_pause_btn);
		touchLayout = (LinearLayout) this.findViewById(R.id.video_touch_layout);
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
		((MainActivity) mContext).setButtonHeight(fullScreenButton);
		((MainActivity) mContext).setButtonHeight(muteButton);
		((MainActivity) mContext).setButtonHeight(pauseButton);
	}

	/**
	 * 绑定button监听器
	 */
	public void setButtonHandler() {
		// TODO Auto-generated method stub
		super.setButtonHandler();
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
		fullScreenButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new ClientRunnable(firstCommand, mainActivity
						.getHandler())).start();
			}
		});
		muteButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new ClientRunnable(secondCommand, mainActivity
						.getHandler())).start();
			}
		});
		pauseButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new ClientRunnable(fourthCommand, mainActivity
						.getHandler())).start();
			}
		});
	}
}
