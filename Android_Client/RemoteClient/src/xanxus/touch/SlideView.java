package xanxus.touch;

import com.xanxus.ClientRunnable;
import com.xanxus.MainActivity;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

public class SlideView extends View {

	private Context mContext = null;
	private int lastY;
	private MainActivity mainActivity = null;

	public SlideView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		mainActivity = (MainActivity) mContext;
		this.setBackgroundColor(Color.LTGRAY);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int x = (int) event.getX(), y = (int) event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			int dy = y - lastY;
			mouseSlide(dy);
			lastY = y;
			break;
		case MotionEvent.ACTION_UP:
			lastY = y;
			break;
		}
		return true;
	}

	public void mouseSlide(int dy) {
		new Thread(new ClientRunnable("ms " + dy, mainActivity.getHandler()))
				.start();
	}

}
