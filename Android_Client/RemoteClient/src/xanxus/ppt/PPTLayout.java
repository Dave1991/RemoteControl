package xanxus.ppt;

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

public class PPTLayout extends AbstractLayout {

	private Button playButton = null;
	private Button firstPlayButton = null;
	private Button escButton = null;
	private Button pageUpButton = null;
	private Button pageDownButton = null;

	public PPTLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mainActivity = (MainActivity) mContext;
		firstCommand = mainActivity.getPreferences().getString("ppt1", "pu");
		secondCommand = mainActivity.getPreferences().getString("ppt2", "pd");
		thirdCommand = mainActivity.getPreferences().getString("ppt3", "es");
		fourthCommand = mainActivity.getPreferences().getString("ppt4", "pl");
		fifthCommand = mainActivity.getPreferences().getString("ppt5", "fp");
		sixthCommand = mainActivity.getPreferences().getString("ppt6", "pu");
		seventhCommand = mainActivity.getPreferences().getString("ppt7", "pd");
		eightCommand = mainActivity.getPreferences().getString("ppt8", "pd");
		LayoutInflater.from(mContext).inflate(R.layout.ppt_layout, this, true);
		pageUpButton = (Button) this.findViewById(R.id.page_up_btn);
		pageDownButton = (Button) this.findViewById(R.id.page_down_btn);
		playButton = (Button) this.findViewById(R.id.play_btn);
		firstPlayButton = (Button) this.findViewById(R.id.first_play_btn);
		escButton = (Button) this.findViewById(R.id.esc_btn);
		touchLayout = (LinearLayout) this.findViewById(R.id.linear3);
		leftButton = (Button) this.findViewById(R.id.left_btn);
		rightButton = (Button) this.findViewById(R.id.right_btn);
		((MainActivity) mContext).addTouchView(touchLayout);
		setButtonHeight();
		setButtonHandler();
	}

	/**
	 * 设置按钮高度，适应不同设备的屏幕
	 */
	private void setButtonHeight() {
		((MainActivity) mContext).setButtonHeight(pageDownButton);
		((MainActivity) mContext).setButtonHeight(pageUpButton);
		((MainActivity) mContext).setButtonHeight(escButton);
		((MainActivity) mContext).setButtonHeight(playButton);
		((MainActivity) mContext).setButtonHeight(firstPlayButton);
	}

	/**
	 * 绑定button监听器
	 */
	public void setButtonHandler() {
		super.setButtonHandler();
		playButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new ClientRunnable(fourthCommand, mainActivity
						.getHandler())).start();
			}
		});
		firstPlayButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new ClientRunnable(fifthCommand, mainActivity
						.getHandler())).start();
			}
		});
		escButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new ClientRunnable(thirdCommand, mainActivity
						.getHandler())).start();
			}
		});
		pageUpButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new ClientRunnable(firstCommand, mainActivity
						.getHandler())).start();
			}
		});
		pageDownButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new ClientRunnable(secondCommand, mainActivity
						.getHandler())).start();
			}
		});
	}

}
