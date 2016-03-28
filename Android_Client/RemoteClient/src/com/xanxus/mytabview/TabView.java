package com.xanxus.mytabview;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

/**
 * 自定义tabview
 * 
 * @author xanxus
 * 
 */
public class TabView extends RelativeLayout {

	public static final int BUTTON_LAYOUT_ID = 100;
	private Context mContext = null;
	private ViewFlipper viewFlipper = null;
	private LinearLayout buttonLayout = null;
	private LayoutParams layoutParams = null;
	private HashMap<String, Button> buttonMap = null;
	private HashMap<String, View> subViewMap = null;
	private Animation[] animations = null;
	/**
	 * 当前tab页的tag
	 */
	private String currentTag = null;

	public TabView(Context context, int orientation) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		buttonMap = new HashMap<String, Button>();
		subViewMap = new HashMap<String, View>();
		viewFlipper = new ViewFlipper(mContext);
		buttonLayout = new LinearLayout(mContext);
		buttonLayout.setOrientation(orientation);
		if (orientation == LinearLayout.VERTICAL)
			layoutParams = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
					android.view.ViewGroup.LayoutParams.FILL_PARENT);
		else
			layoutParams = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		buttonLayout.setId(BUTTON_LAYOUT_ID);
		this.addView(buttonLayout, layoutParams);
		layoutParams = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.FILL_PARENT);
		animations = new Animation[4];
		if (orientation == LinearLayout.VERTICAL)
			layoutParams.addRule(RelativeLayout.RIGHT_OF, BUTTON_LAYOUT_ID);
		else if (orientation == LinearLayout.HORIZONTAL) {
			layoutParams.addRule(RelativeLayout.BELOW, BUTTON_LAYOUT_ID);
		}
		this.addView(viewFlipper, layoutParams);
	}

	public void setOrientation(int orientation) {
		buttonLayout.setOrientation(orientation);
		layoutParams = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.FILL_PARENT);
		if (orientation == LinearLayout.VERTICAL)
			layoutParams.addRule(RelativeLayout.RIGHT_OF, BUTTON_LAYOUT_ID);
		else if (orientation == LinearLayout.HORIZONTAL) {
			layoutParams.addRule(RelativeLayout.BELOW, BUTTON_LAYOUT_ID);
		}
		viewFlipper.setLayoutParams(layoutParams);
	}

	/**
	 * 添加一个新的tab
	 * 
	 * @param tag
	 *            新的tab的唯一标识
	 * @param title
	 *            新的tab的title
	 * @param subView
	 *            新的tab的view
	 * @return false表示已存在该tag，true表示成功添加
	 */
	public boolean addTab(final String tag, String title, View subView,Drawable topDrawable) {
		Button tabButton = new Button(mContext);
		tabButton.setText(title);
		tabButton.setCompoundDrawablesWithIntrinsicBounds(null, topDrawable, null, null);
		Set<String> tagSet = buttonMap.keySet();
		if (tagSet.contains(tag)) {
			return false;
		}
		int i = 0;
		for (; i < buttonLayout.getChildCount(); i++) {
			buttonLayout.getChildAt(i).setBackgroundColor(Color.WHITE);
		}
		buttonMap.put(tag, tabButton);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
		buttonLayout.addView(tabButton, layoutParams);
		tabButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				showTab(tag);
			}
		});
		tabButton.setBackgroundColor(Color.GRAY);
		subViewMap.put(tag, subView);
		viewFlipper.addView(subView);
		viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(subView));
		currentTag = tag;
		// viewFlipper.setLayoutParams(layoutParams);
		return true;
	}

	public void setUpInAnimation(Animation animation) {
		animations[0] = animation;
	}

	public void setUpOutAnimation(Animation animation) {
		animations[1] = animation;
	}

	public void setUpInAnimation(int resourceID) {
		animations[0] = AnimationUtils.loadAnimation(mContext, resourceID);
	}

	public void setUpOutAnimation(int resourceID) {
		animations[1] = AnimationUtils.loadAnimation(mContext, resourceID);
	}

	public void setDownInAnimation(Animation animation) {
		animations[2] = animation;
	}

	public void setDownOutAnimation(Animation animation) {
		animations[3] = animation;
	}

	public void setDownInAnimation(int resourceID) {
		animations[2] = AnimationUtils.loadAnimation(mContext, resourceID);
	}

	public void setDownOutAnimation(int resourceID) {
		animations[3] = AnimationUtils.loadAnimation(mContext, resourceID);
	}

	/**
	 * 展示指定的tab
	 * 
	 * @param tag
	 *            要展示的tab的tag
	 * @return false表示不存在指定的tag的tab或者当前展示的tab的tag等于参数tag
	 */
	public boolean showTab(String tag) {
		// tag不存在
		if (!buttonMap.containsKey(tag))
			return false;
		// 当前tag就是指定的tag
		if (currentTag.equals(tag))
			return false;
		currentTag = tag;
		Button selectButton = buttonMap.get(tag);
		// 更新buttonlayout
		for (int i = 0; i < buttonLayout.getChildCount(); i++) {
			Button button = (Button) buttonLayout.getChildAt(i);
			if (button != selectButton)
				button.setBackgroundColor(Color.WHITE);
			else
				button.setBackgroundColor(Color.GRAY);
		}
		// 更新viewFlipper
		int currentIndex = viewFlipper.getDisplayedChild();
		int displayIndex = viewFlipper.indexOfChild(subViewMap.get(tag));
		if (currentIndex > displayIndex) {
			// 当前tab在即将显示的tab的下方，从下面离开
			viewFlipper.setInAnimation(animations[2]);
			viewFlipper.setOutAnimation(animations[3]);
		} else {
			// 当前tab在即将显示的tab的上方，从上面离开
			viewFlipper.setInAnimation(animations[0]);
			viewFlipper.setOutAnimation(animations[1]);
		}
		viewFlipper.setDisplayedChild(displayIndex);
		return true;
	}

	/**
	 * 获取指定tab的标题
	 * 
	 * @param tag
	 *            指定tab的tag
	 * @return null表示tag不存在
	 */
	public String getTabTitle(String tag) {
		if (buttonMap.containsKey(tag))
			return buttonMap.get(tag).getText().toString();
		else
			return null;
	}

	/**
	 * 获取指定tab的view
	 * 
	 * @param tag
	 *            指定tab的tag
	 * @return null表示tag不存在
	 */
	public View getTabView(String tag) {
		if (subViewMap.containsKey(tag))
			return subViewMap.get(tag);
		else
			return null;
	}

	public String getCurrentTag() {
		return currentTag;
	}

	public String getCurrentTitle() {
		return buttonMap.get(currentTag).getText().toString();
	}

	public View getCurrentTabView() {
		return viewFlipper.getCurrentView();
	}

	/**
	 * 删除指定的tabview，显示下一个tabview
	 * 
	 * @param tag
	 *            指定tab的tag
	 * @return false表示tag不存在
	 */
	public boolean removeTabView(String tag) {
		if (!buttonMap.containsKey(tag))
			return false;
		// 如果不是当前显示的tab，直接删除
		if (!tag.equals(currentTag)) {
			buttonLayout.removeView(buttonMap.get(tag));
			viewFlipper.removeView(subViewMap.get(tag));
			subViewMap.remove(tag);
			buttonMap.remove(tag);
			return true;
		} else {
			// 显示下一个tabview
			viewFlipper.showNext();
			View currentView = viewFlipper.getCurrentView();
			Iterator<Map.Entry<String, View>> iterator = subViewMap.entrySet()
					.iterator();
			// 遍历subViewMap,获取当前tab的tag
			while (iterator.hasNext()) {
				Map.Entry<String, View> entry = iterator.next();
				if (entry.getValue().equals(currentView)) {
					currentTag = entry.getKey();
					Button currentButton = buttonMap.get(currentTag);
					// buttonlayout更新
					for (int i = 0; i < buttonLayout.getChildCount(); i++) {
						Button tmpButton = (Button) buttonLayout.getChildAt(i);
						if (currentButton == tmpButton)
							tmpButton.setBackgroundColor(Color.GRAY);
						else
							tmpButton.setBackgroundColor(Color.WHITE);
					}
				}
			}
			// 当前tab已不是指定tag的tab，可以直接删除
			buttonLayout.removeView(buttonMap.get(tag));
			viewFlipper.removeView(subViewMap.get(tag));
			subViewMap.remove(tag);
			buttonMap.remove(tag);
			return true;
		}

	}

}
