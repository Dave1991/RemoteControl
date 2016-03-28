package xanxus.explorer;

import java.io.File;
import java.util.Date;

import com.xanxus.R;
import com.xanxus.RemoteFile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ExplorerAdapter extends BaseAdapter {

	private RemoteFile[] currrentFiles = null;
	private Context mContext = null;
	private boolean hasUpperPath = true;// 判断是否有上一层目录

	public ExplorerAdapter(RemoteFile[] files, Context context, boolean hup) {
		currrentFiles = files;
		mContext = context;
		hasUpperPath = hup;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		if (hasUpperPath)
			return currrentFiles.length + 1;
		else
			return currrentFiles.length;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (hasUpperPath) {
			if (position > 0)
				return currrentFiles[position - 1];
			else
				return null;
		} else
			return currrentFiles[position];
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = LayoutInflater.from(mContext).inflate(
				R.layout.explorer_item_layout, null);
		ImageView iconView = (ImageView) convertView
				.findViewById(R.id.imageView1);
		TextView fileNameView = (TextView) convertView
				.findViewById(R.id.file_name_tv);
		TextView lastModifiedView = (TextView) convertView
				.findViewById(R.id.last_modified_time_tv);
		if (hasUpperPath) {
			// 添加上级目录
			if (position == 0) {
				fileNameView.setText("..");
				lastModifiedView.setText("上级目录");
			} else {
				RemoteFile file = currrentFiles[position - 1];
				fileNameView.setText(file.getName());
				Date date = new Date(file.getLastModifiedTime());
				lastModifiedView.setText(date.toLocaleString());
				if (!file.isDirectory()) {
					iconView.setImageResource(R.drawable.file);
				}
			}
		} else {
			// 没有上级目录，说明这是根目录
			RemoteFile file = currrentFiles[position];
			fileNameView.setText(file.getName());
			lastModifiedView.setText("");
			iconView.setImageResource(R.drawable.harddisk);
		}
		return convertView;
	}

	public RemoteFile[] getCurrrentFiles() {
		return currrentFiles;
	}

	public void setCurrrentFiles(RemoteFile[] currrentFiles) {
		this.currrentFiles = currrentFiles;
	}

	public boolean isHasUpperPath() {
		return hasUpperPath;
	}

	public void setHasUpperPath(boolean hasUpperPath) {
		this.hasUpperPath = hasUpperPath;
	}
}
