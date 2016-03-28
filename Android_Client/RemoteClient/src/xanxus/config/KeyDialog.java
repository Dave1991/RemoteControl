package xanxus.config;

import com.xanxus.MainActivity;
import com.xanxus.R;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class KeyDialog extends Dialog {

	private Context mContext = null;
	private TextView itemValueView = null;
	private Button confirmButton = null;
	private Button cancelButton = null;
	private int group = 0;// 分组
	private int position = 0;// 组内位置
	private MainActivity mainActivity = null;
	private CheckBox ctrlCheckBox = null;
	private CheckBox altCheckBox = null;
	private CheckBox shiftCheckBox = null;
	private EditText keyEditText = null;
	private SharedPreferences.Editor editor = null;

	public KeyDialog(Context context, TextView itemValueView, int group,
			int position, SharedPreferences.Editor editor) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		this.editor = editor;
		mainActivity = (MainActivity) mContext;
		this.itemValueView = itemValueView;
		this.group = group;
		this.position = position;
		initView();
		setListener();
	}

	private void setListener() {
		// TODO Auto-generated method stub
		confirmButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				String newCommand = "ay";
				String showCommand = "";
				if (ctrlCheckBox.isChecked()) {
					newCommand += " ctrl";
					showCommand += "ctrl";
				}
				if (altCheckBox.isChecked()) {
					newCommand += " alt";
					showCommand += "+alt";
				}
				if (shiftCheckBox.isChecked()) {
					newCommand += " shift";
					showCommand += "+shift";
				}
				String inputKey = keyEditText.getText().toString();
				if (!inputKey.trim().equals("")) {
					newCommand += " " + inputKey.trim();
					showCommand += "+" + inputKey.trim();
				}
				switch (group) {
				case 0:
					mainActivity.getPptLayout()
							.setCommand(position, newCommand);
					
					//保存该次用户的修改，下次启动仍然生效
					editor.putString("ppt"+position, newCommand);
					break;
				case 1:
					mainActivity.getMusicLayout().setCommand(position,
							newCommand);
					
					//保存该次用户的修改，下次启动仍然生效
					editor.putString("music"+position, newCommand);
					break;
				case 2:
					mainActivity.getVideoLayout().setCommand(position,
							newCommand);
					
					//保存该次用户的修改，下次启动仍然生效
					editor.putString("video"+position, newCommand);
					break;
				}
				editor.commit();
				itemValueView.setText(showCommand);
				KeyDialog.this.dismiss();
			}
		});
		cancelButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				KeyDialog.this.dismiss();
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		this.setTitle("设置快捷键");
		this.setContentView(R.layout.keyconfig);
		confirmButton = (Button) this.findViewById(R.id.button1);
		cancelButton = (Button) this.findViewById(R.id.button2);
		ctrlCheckBox = (CheckBox) this.findViewById(R.id.checkBox1);
		altCheckBox = (CheckBox) this.findViewById(R.id.checkBox2);
		shiftCheckBox = (CheckBox) this.findViewById(R.id.checkBox3);
		keyEditText = (EditText) this.findViewById(R.id.editText1);
	}

}
