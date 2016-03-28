package xanxus.config;

import com.xanxus.MainActivity;
import com.xanxus.R;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

@SuppressLint("ParserError")
public class IPDialog extends Dialog {

	private TextView ipValueView = null;
	private SharedPreferences.Editor editor = null;

	public IPDialog(Context context, TextView ipValueView,
			SharedPreferences.Editor editor) {
		super(context);
		// TODO Auto-generated constructor stub
		this.ipValueView = ipValueView;
		this.editor = editor;
		this.setTitle("设置服务器IP地址");
		initView();
	}

	/**
	 * 初始化对话框view
	 */
	private void initView() {
		View configView = getLayoutInflater().inflate(R.layout.ipconfig, null);
		Button okButton = (Button) configView.findViewById(R.id.ok_btn);
		Button cancelButton = (Button) configView.findViewById(R.id.cancel_btn);
		final EditText ipText = (EditText) configView
				.findViewById(R.id.target_ip);
		ipText.setText(ipValueView.getText());
		okButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				String ipString = ipText.getText().toString();
				MainActivity.serverIP = ipString;

				// 保存用户设置
				editor.putString("serverIP", ipString);
				editor.commit();
				if (ipValueView != null)
					// 设置程序对应服务器的ip
					ipValueView.setText(MainActivity.serverIP);
				IPDialog.this.dismiss();
			}
		});
		cancelButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				IPDialog.this.dismiss();
			}
		});
		this.setContentView(configView);
	}
}