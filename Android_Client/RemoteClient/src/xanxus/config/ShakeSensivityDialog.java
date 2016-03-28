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

public class ShakeSensivityDialog extends Dialog {

	private Context mContext = null;
	private MainActivity mainActivity = null;
	private SharedPreferences.Editor editor = null;
	private TextView sensivityTextView = null;
	private Button confirmButton = null;
	private Button cancelButton = null;
	private RadioGroup sensivityGroup = null;

	public ShakeSensivityDialog(Context context, TextView view,
			SharedPreferences.Editor editor) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		mainActivity = (MainActivity) mContext;
		this.setContentView(R.layout.sensivity_layout);
		this.setTitle("灵敏度设置");
		sensivityTextView = view;
		this.editor = editor;
		confirmButton = (Button) this.findViewById(R.id.button1);
		cancelButton = (Button) this.findViewById(R.id.button2);
		sensivityGroup = (RadioGroup) this.findViewById(R.id.sensivity_rg);
		if(sensivityTextView.getText().toString().equals("强")){
			((RadioButton)sensivityGroup.getChildAt(0)).setChecked(true);
		}else if(sensivityTextView.getText().toString().equals("中等")){
			((RadioButton)sensivityGroup.getChildAt(1)).setChecked(true);
		}else {
			((RadioButton)sensivityGroup.getChildAt(2)).setChecked(true);
		}
		confirmButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				RadioButton topButton = (RadioButton) sensivityGroup
						.getChildAt(0);
				RadioButton mediumButton = (RadioButton) sensivityGroup
						.getChildAt(1);
				if (topButton.isChecked()) {
					mainActivity.setSensitivity(1000);
					sensivityTextView.setText(topButton.getText());
				} else if (mediumButton.isChecked()) {
					mainActivity.setSensitivity(2000);
					sensivityTextView.setText("中等");
				} else {
					mainActivity.setSensitivity(3000);
					sensivityTextView.setText("弱");
				}

				// 保存用户设置
				ShakeSensivityDialog.this.editor.putInt("sensivity",
						mainActivity.getSensitivity());
				ShakeSensivityDialog.this.editor.commit();

				ShakeSensivityDialog.this.dismiss();
			}
		});
		cancelButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShakeSensivityDialog.this.dismiss();
			}
		});
	}

}
