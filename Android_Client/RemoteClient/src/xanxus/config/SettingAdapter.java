package xanxus.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xanxus.music.MusicLayout;
import xanxus.ppt.PPTLayout;
import xanxus.video.VideoLayout;

import com.xanxus.MainActivity;
import com.xanxus.R;
import com.xanxus.R.id;
import com.xanxus.R.layout;

import android.content.Context;
import android.provider.MediaStore.Video;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SettingAdapter extends BaseAdapter {

	private List<Map<String, String>> settingItems = null;// 所有列表项
	private List<Map<String, String>> groupLists = null;// 所有分组项
	private LayoutInflater layoutInflater = null;
	private Context mContext = null;
	private MainActivity mainActivity = null;
	private PPTLayout pptLayout = null;
	private MusicLayout musicLayout = null;
	private VideoLayout videoLayout = null;

	public SettingAdapter(Context context) {
		mContext = context;
		mainActivity = (MainActivity) mContext;
		pptLayout = (PPTLayout) mainActivity.getPptLayout();
		musicLayout = (MusicLayout) mainActivity.getMusicLayout();
		videoLayout = (VideoLayout) mainActivity.getVideoLayout();
		layoutInflater = LayoutInflater.from(mContext);
		settingItems = new ArrayList<Map<String, String>>();
		groupLists = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		// 通用设置分组
		map.put("itemTitle", "服务器设置");
		groupLists.add(map);
		settingItems.add(map);
		map = new HashMap<String, String>();
		map.put("itemTitle", "IP地址");
		map.put("itemValue", MainActivity.serverIP);
		settingItems.add(map);
		map = new HashMap<String, String>();
		map.put("itemTitle", "操作系统");
		map.put("itemValue", MainActivity.serverOS);
		settingItems.add(map);
		// 幻灯片分组
		map = new HashMap<String, String>();
		map.put("itemTitle", "幻灯片设置");
		groupLists.add(map);
		settingItems.add(map);
		map = new HashMap<String, String>();
		map.put("itemTitle", "上一页");
		if (pptLayout.getFirstCommand().equals("pu"))
			map.put("itemValue", "pageup");
		else {
			map.put("itemValue", pptLayout.getFirstCommand().substring(3)
					.replace(" ", "+"));
		}
		settingItems.add(map);
		map = new HashMap<String, String>();
		map.put("itemTitle", "下一页");
		if (pptLayout.getSecondCommand().equals("pd"))
			map.put("itemValue", "pagedown");
		else {
			map.put("itemValue", pptLayout.getSecondCommand().substring(3)
					.replace(" ", "+"));
		}
		settingItems.add(map);
		map = new HashMap<String, String>();
		map.put("itemTitle", "退出放映");
		if (pptLayout.getThirdCommand().equals("es"))
			map.put("itemValue", "esc");
		else {
			map.put("itemValue", pptLayout.getThirdCommand().substring(3)
					.replace(" ", "+"));
		}
		settingItems.add(map);
		map = new HashMap<String, String>();
		map.put("itemTitle", "从该页放映");
		if (pptLayout.getFourthCommand().equals("pl"))
			map.put("itemValue", "alt+f5");
		else {
			map.put("itemValue", pptLayout.getFourthCommand().substring(3)
					.replace(" ", "+"));
		}
		settingItems.add(map);
		map = new HashMap<String, String>();
		map.put("itemTitle", "从头放映");
		if (pptLayout.getFifthCommand().equals("fp"))
			map.put("itemValue", "f5");
		else {
			map.put("itemValue", pptLayout.getFifthCommand().substring(3)
					.replace(" ", "+"));
		}
		settingItems.add(map);
		map = new HashMap<String, String>();
		map.put("itemTitle", "音量上");
		if (pptLayout.getSixthCommand().equals("pu"))
			map.put("itemValue", "pageup");
		else {
			map.put("itemValue", pptLayout.getSixthCommand().substring(3)
					.replace(" ", "+"));
		}
		settingItems.add(map);
		map = new HashMap<String, String>();
		map.put("itemTitle", "音量下");
		if (pptLayout.getSeventhCommand().equals("pd"))
			map.put("itemValue", "pagedown");
		else {
			map.put("itemValue", pptLayout.getSeventhCommand().substring(3)
					.replace(" ", "+"));
		}
		settingItems.add(map);
		map = new HashMap<String, String>();
		map.put("itemTitle", "摇一摇");
		if (pptLayout.getEightCommand().equals("pd"))
			map.put("itemValue", "pagedown");
		else {
			map.put("itemValue", pptLayout.getEightCommand().substring(3)
					.replace(" ", "+"));
		}
		settingItems.add(map);
		// 音乐分组
		map = new HashMap<String, String>();
		map.put("itemTitle", "音乐设置");
		groupLists.add(map);
		settingItems.add(map);
		map = new HashMap<String, String>();
		map.put("itemTitle", "音量减少");
		if (musicLayout.getFirstCommand().equals("vd"))
			map.put("itemValue", "ctrl+alt+down");
		else {
			map.put("itemValue", musicLayout.getFirstCommand().substring(3)
					.replace(" ", "+"));
		}
		settingItems.add(map);
		map = new HashMap<String, String>();
		map.put("itemTitle", "音量增大");
		if (musicLayout.getSecondCommand().equals("vu"))
			map.put("itemValue", "ctrl+alt+up");
		else {
			map.put("itemValue", musicLayout.getSecondCommand().substring(3)
					.replace(" ", "+"));
		}
		settingItems.add(map);
		map = new HashMap<String, String>();
		map.put("itemTitle", "上一首");
		if (musicLayout.getThirdCommand().equals("ps"))
			map.put("itemValue", "ctrl+alt+left");
		else {
			map.put("itemValue", musicLayout.getThirdCommand().substring(3)
					.replace(" ", "+"));
		}
		settingItems.add(map);
		map = new HashMap<String, String>();
		map.put("itemTitle", "暂停");
		if (musicLayout.getFourthCommand().equals("pa"))
			map.put("itemValue", "ctrl+alt+f5");
		else {
			map.put("itemValue", musicLayout.getFourthCommand().substring(3)
					.replace(" ", "+"));
		}
		settingItems.add(map);
		map = new HashMap<String, String>();
		map.put("itemTitle", "下一首");
		if (musicLayout.getFifthCommand().equals("ns"))
			map.put("itemValue", "ctrl+alt+right");
		else {
			map.put("itemValue", musicLayout.getFifthCommand().substring(3)
					.replace(" ", "+"));
		}
		settingItems.add(map);
		map = new HashMap<String, String>();
		map.put("itemTitle", "音量上");
		if (musicLayout.getSixthCommand().equals("ps"))
			map.put("itemValue", "ctrl+alt+left");
		else {
			map.put("itemValue", musicLayout.getSixthCommand().substring(3)
					.replace(" ", "+"));
		}
		settingItems.add(map);
		map = new HashMap<String, String>();
		map.put("itemTitle", "音量下");
		if (musicLayout.getSeventhCommand().equals("ns"))
			map.put("itemValue", "ctrl+alt+right");
		else {
			map.put("itemValue", musicLayout.getSeventhCommand().substring(3)
					.replace(" ", "+"));
		}
		settingItems.add(map);
		map = new HashMap<String, String>();
		map.put("itemTitle", "摇一摇");
		if (musicLayout.getEightCommand().equals("ns"))
			map.put("itemValue", "ctrl+alt+right");
		else {
			map.put("itemValue", musicLayout.getEightCommand().substring(3)
					.replace(" ", "+"));
		}
		settingItems.add(map);
		// 视频分组
		map = new HashMap<String, String>();
		map.put("itemTitle", "视频设置");
		groupLists.add(map);
		settingItems.add(map);
		map = new HashMap<String, String>();
		map.put("itemTitle", "全屏");
		map.put("itemValue", videoLayout.getFirstCommand().substring(3)
				.replace(" ", "+"));
		settingItems.add(map);
		map = new HashMap<String, String>();
		map.put("itemTitle", "静音");
		map.put("itemValue", videoLayout.getSecondCommand().substring(3)
				.replace(" ", "+"));
		settingItems.add(map);
		map = new HashMap<String, String>();
		map.put("itemTitle", "上一集");
		map.put("itemValue", videoLayout.getThirdCommand().substring(3)
				.replace(" ", "+"));
		settingItems.add(map);
		map = new HashMap<String, String>();
		map.put("itemTitle", "暂停");
		map.put("itemValue", videoLayout.getFourthCommand().substring(3)
				.replace(" ", "+"));
		settingItems.add(map);
		map = new HashMap<String, String>();
		map.put("itemTitle", "下一集");
		map.put("itemValue", videoLayout.getFifthCommand().substring(3)
				.replace(" ", "+"));
		settingItems.add(map);
		map = new HashMap<String, String>();
		map.put("itemTitle", "音量上");
		map.put("itemValue", videoLayout.getSixthCommand().substring(3)
				.replace(" ", "+"));
		settingItems.add(map);
		map = new HashMap<String, String>();
		map.put("itemTitle", "音量下");
		map.put("itemValue", videoLayout.getSeventhCommand().substring(3)
				.replace(" ", "+"));
		settingItems.add(map);
		map = new HashMap<String, String>();
		map.put("itemTitle", "摇一摇");
		map.put("itemValue", videoLayout.getEightCommand().substring(3)
				.replace(" ", "+"));
		settingItems.add(map);
		// 设置是否要摇一摇
		map = new HashMap<String, String>();
		map.put("itemTitle", "摇一摇设置");
		groupLists.add(map);
		settingItems.add(map);
		map = new HashMap<String, String>();
		map.put("itemTitle", "开关");
		map.put("itemValue", mainActivity.isShake() ? "开" : "关");
		settingItems.add(map);
		map = new HashMap<String, String>();
		map.put("itemTitle", "灵敏度");
		switch (mainActivity.getSensitivity()) {
		case 1000:
			map.put("itemValue", "强");
			break;
		case 2000:
			map.put("itemValue", "中等");
			break;
		case 3000:
			map.put("itemValue", "弱");
			break;
		}
		settingItems.add(map);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return settingItems.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return settingItems.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (groupLists.contains(settingItems.get(position))) {
			convertView = layoutInflater.inflate(R.layout.list_item_tag, null);
			TextView itemTitle = (TextView) convertView
					.findViewById(R.id.group_title);
			itemTitle.setText(settingItems.get(position).get("itemTitle"));
		} else {
			convertView = layoutInflater.inflate(R.layout.list_item, null);
			TextView itemTitle = (TextView) convertView
					.findViewById(R.id.item_title);
			itemTitle.setText(settingItems.get(position).get("itemTitle"));
			((TextView) convertView.findViewById(R.id.item_value))
					.setText(settingItems.get(position).get("itemValue"));
		}
		return convertView;
	}
}
