package xanxus.explorer;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.xanxus.AbstractLayout;
import com.xanxus.ClientRunnable;
import com.xanxus.R;
import com.xanxus.RemoteFile;

public class ExplorerLayout extends AbstractLayout {

	private LinearLayout buttonsLayout = null;
	private ListView fileListView = null;
	private String currentPath = "/";
	private LinearLayout currentPathLayout = null;
	private ExplorerAdapter adapter = null;
	private String openFilePath = "";

	public ExplorerLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.explorer_layout, this,
				true);
		currentPathLayout = (LinearLayout) this
				.findViewById(R.id.current_path_ll);
		buttonsLayout = (LinearLayout) this
				.findViewById(R.id.current_path_buttons_ll);
		fileListView = (ListView) this.findViewById(R.id.files_lv);
		sendRequestFileList();
		setListener();
	}

	private void sendRequestFileList() {
		new Thread(new ClientRunnable("fe " + currentPath,
				mainActivity.getHandler())).start();
	}
	private void sendOpenFile(){
		new Thread(new ClientRunnable("of " + openFilePath,
				mainActivity.getHandler())).start();
	}

	private void setListener() {
		// TODO Auto-generated method stub
		fileListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				RemoteFile file = (RemoteFile) adapter.getItem(arg2);
				if (file != null) {
					if (file.isDirectory()) {
						currentPath = file.getPath();
						//防止路径中带有空格，预先把空格变为问号
						currentPath = currentPath.replace(" ", "?");
						sendRequestFileList();
					} else {
						//防止路径中带有空格，预先把空格变为问号
						openFilePath = file.getPath().replace(" ", "?");
						sendOpenFile();
					}
				} else {
					// 上一级目录
					String upperPath = currentPath.substring(0,
							currentPath.lastIndexOf("\\"));
					// 上一级目录为根目录
					if (upperPath.endsWith(":") && currentPath.endsWith("\\"))
						currentPath = "/";
					else if (upperPath.endsWith(":"))
						currentPath = upperPath + "\\";
					else
						currentPath = upperPath;
					sendRequestFileList();
				}
			}
		});
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
	}

	public void notifyFileList(JSONObject jsonObject) throws JSONException {
		JSONArray array = jsonObject.getJSONArray("files");
		boolean hup = jsonObject.getBoolean("hup");
		RemoteFile[] files = new RemoteFile[array.length()];
		if (hup) {
			for (int i = 0; i < array.length(); i++) {
				JSONObject json = array.getJSONObject(i);
				RemoteFile file = new RemoteFile(json.getString("filePath"));
				file.setDirectory(json.getBoolean("isDirectory"));
				file.setLastModifiedTime(json.getLong("lastModified"));
				file.setName(json.getString("fileName"));
				files[i] = file;
			}

		} else {
			for (int i = 0; i < array.length(); i++) {
				JSONObject json = array.getJSONObject(i);
				RemoteFile file = new RemoteFile(
						json.getString("partitionName"));
				file.setName(json.getString("partitionName"));
				file.setDirectory(true);
				files[i] = file;
			}
		}
		adapter = new ExplorerAdapter(files, mainActivity, hup);
		adapter.notifyDataSetChanged();
		fileListView.setAdapter(adapter);

	}

}
