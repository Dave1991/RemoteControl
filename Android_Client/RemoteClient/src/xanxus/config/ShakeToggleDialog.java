package xanxus.config;

import com.xanxus.MainActivity;
import com.xanxus.R;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ShakeToggleDialog extends Dialog {

	private TextView toggleValueView = null;
	private SharedPreferences.Editor editor = null;
	private Context mContext = null;
	private Button confirmButton = null;
	private Button cancelButton = null;
	private RadioGroup toggleGroup = null;

	public ShakeToggleDialog(Context context, TextView view,
			SharedPreferences.Editor editor) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		toggleValueView = view;
		this.setTitle("摇一摇设置");
		this.editor = editor;
		setContentView(R.layout.shake_toggle_layout);
		confirmButton = (Button) this.findViewById(R.id.button1);
		cancelButton = (Button) this.findViewById(R.id.button2);
		toggleGroup = (RadioGroup) this.findViewById(R.id.shake_toggle);
		if (toggleValueView.getText().toString().equals("开")) {
			((RadioButton) toggleGroup.getChildAt(0)).setChecked(true);
		} else
			((RadioButton) toggleGroup.getChildAt(1)).setChecked(true);
		confirmButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				RadioButton openButton = (RadioButton) toggleGroup
						.getChildAt(0);
				if (openButton.isChecked())
					toggleValueView.setText(openButton.getText());
				else
					toggleValueView.setText("关");
				((MainActivity) mContext).setShake(openButton.isChecked());
				// 保存用户设置
				ShakeToggleDialog.this.editor.putBoolean("isShake",
						((MainActivity) mContext).isShake());
				ShakeToggleDialog.this.editor.commit();
				ShakeToggleDialog.this.dismiss();
			}
		});
		cancelButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShakeToggleDialog.this.dismiss();
			}
		});
	}

}
