package xanxus.config;

import com.xanxus.MainActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;

public class OSDialog extends Dialog {

	private TextView osValueView = null;
	private Context mContext = null;
	private Spinner osSpinner = null;
	private LinearLayout contentLayout = null;
	private SharedPreferences.Editor editor = null;
	private final String[] osArray = { "windows", "ubuntu", "mac" };

	public OSDialog(Context context, TextView osTextView,
			SharedPreferences.Editor editor) {
		super(context);
		// TODO Auto-generated constructor stub
		this.osValueView = osTextView;
		this.mContext = context;
		this.editor = editor;
		this.setTitle("设置服务器的操作系统");
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		contentLayout = new LinearLayout(mContext);
		contentLayout.setOrientation(LinearLayout.VERTICAL);
		osSpinner = new Spinner(mContext);
		ArrayAdapter<String> osAdapter = new ArrayAdapter<String>(mContext,
				android.R.layout.simple_expandable_list_item_1, osArray);
		osSpinner.setAdapter(osAdapter);
		contentLayout.addView(osSpinner);
		LinearLayout buttonLayout = new LinearLayout(mContext);
		Button confirmButton = new Button(mContext);
		confirmButton.setText("确定");
		confirmButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//获取用户选中的操作系统
				String osString = osArray[osSpinner.getSelectedItemPosition()];
				MainActivity.serverOS = osString;
				
				//保存该次用户的修改，下次启动仍然生效
				editor.putString("serverOS", osString);
				editor.commit();
				osValueView.setText(osString);
				OSDialog.this.dismiss();
			}
		});
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1);
		buttonLayout.addView(confirmButton, params);
		Button cancelButton = new Button(mContext);
		cancelButton.setText("取消");
		cancelButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				OSDialog.this.dismiss();
			}
		});
		buttonLayout.addView(cancelButton, params);
		contentLayout.addView(buttonLayout);
		this.setContentView(contentLayout);
	}

}
