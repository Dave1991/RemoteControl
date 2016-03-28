package xanxus.config;

import com.xanxus.MainActivity;
import com.xanxus.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class ConfigLayout extends RelativeLayout {

	private Context mContext = null;
	private ListView listView = null;
	private IPDialog ipDialog = null;
	private MainActivity mainActivity = null;
	private SharedPreferences.Editor editor = null;
	private ShakeToggleDialog toggleDialog = null;
	private ShakeSensivityDialog sensivityDialog = null;

	public ConfigLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		mainActivity = (MainActivity) mContext;
		editor = mainActivity.getEditor();
		LayoutInflater.from(mContext).inflate(R.layout.setting, this, true);
		listView = (ListView) this.findViewById(R.id.setting_list);
		SettingAdapter settingAdapter = new SettingAdapter(mContext);
		listView.setAdapter(settingAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				TextView textView = (TextView) arg1
						.findViewById(R.id.item_title);
				if (textView != null) {
					String title = textView.getText().toString();
					TextView itemValueView = (TextView) arg1
							.findViewById(R.id.item_value);
					if (title.equals("IP地址")) {
						showIPDialog(itemValueView);
					} else if (title.equals("操作系统")) {
						showOSDialog(itemValueView);
					} else {
						if (arg3 > 3 && arg3 < 12)
							showKeyDialog(itemValueView, 0, (int) (arg3 - 3));
						else if (arg3 > 12 && arg3 < 21) {
							showKeyDialog(itemValueView, 1, (int) (arg3 - 11));
						} else if (arg3 > 21 && arg3 < 30) {
							showKeyDialog(itemValueView, 2, (int) (arg3 - 19));
						} else if (arg3 == 31)
							showShakeToggleDialog(itemValueView);
						else if (arg3 == 32)
							showShakeSensivityDialog(itemValueView);
					}
				}
			}
		});
	}

	/**
	 * 显示设置ip对话框
	 * 
	 * @param ipValueView
	 */
	public void showIPDialog(final TextView ipValueView) {
		if (ipDialog == null) {
			ipDialog = new IPDialog(mContext, ipValueView, editor);
		}
		ipDialog.show();
	}

	/**
	 * 显示设置服务器操作系统对话框
	 * 
	 * @param osTextView
	 */
	public void showOSDialog(final TextView osTextView) {
		OSDialog osDialog = new OSDialog(mContext, osTextView, editor);
		osDialog.show();
	}

	/**
	 * 显示设置快捷键的对话框
	 * 
	 * @param keyTextView
	 */
	public void showKeyDialog(final TextView keyTextView, int group,
			int position) {
		KeyDialog keyDialog = new KeyDialog(mContext, keyTextView, group,
				position, editor);
		keyDialog.show();
	}

	/**
	 * 显示设置摇一摇开关对话框
	 * 
	 * @param ipValueView
	 */
	public void showShakeToggleDialog(final TextView valueView) {
		if (toggleDialog == null) {
			toggleDialog = new ShakeToggleDialog(mContext, valueView, editor);
		}
		toggleDialog.show();
	}

	/**
	 * 显示设置摇一摇开关对话框
	 * 
	 * @param ipValueView
	 */
	public void showShakeSensivityDialog(final TextView valueView) {
		if (sensivityDialog == null) {
			sensivityDialog = new ShakeSensivityDialog(mContext, valueView,
					editor);
		}
		sensivityDialog.show();
	}
}
