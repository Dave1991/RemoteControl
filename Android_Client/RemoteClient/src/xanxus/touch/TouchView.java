package xanxus.touch;

import java.io.Closeable;

import com.xanxus.ClientRunnable;
import com.xanxus.MainActivity;

import android.content.Context;
import android.gesture.Gesture;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class TouchView extends View {

	private Context mContext = null;
	private int lastX;
	private int lastY;
	private int downX;
	private int downY;
	private long lastTouchTime;
	private float zoomValue;
	private float mtDistanceStart = 0;
	private float mtDistanceEnd = 0;
	private GestureDetector gestureDetector = null;
	private boolean mtZoomActive = false;
	private float mutiDistance = 0f;
	private boolean mutiActive = false;
	private MainActivity mainActivity = null;

	public TouchView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		mainActivity = (MainActivity) mContext;
		this.setBackgroundColor(Color.DKGRAY);
		gestureDetector = new GestureDetector(
				new GestureDetector.OnGestureListener() {

					public boolean onSingleTapUp(MotionEvent e) {
						// TODO Auto-generated method stub
						mouseClick();
						return true;
					}

					public void onShowPress(MotionEvent e) {
						// TODO Auto-generated method stub

					}

					public boolean onScroll(MotionEvent e1, MotionEvent e2,
							float distanceX, float distanceY) {
						// TODO Auto-generated method stub
						return false;
					}

					public void onLongPress(MotionEvent e) {
						// TODO Auto-generated method stub
					}

					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						// TODO Auto-generated method stub
						return false;
					}

					public boolean onDown(MotionEvent e) {
						// TODO Auto-generated method stub
						return false;
					}
				});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int x = (int) event.getX(), y = (int) event.getY();
		if (!gestureDetector.onTouchEvent(event)) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				lastX = x;
				lastY = y;
				downX = x;
				downY = y;
				lastTouchTime = System.currentTimeMillis();
			} else if (MotionEvent.ACTION_POINTER_2_DOWN == event.getAction()
					&& event.getPointerCount() == 2) {
				float d = distance(event);
				if (d > 20f) {
					this.mtZoomActive = true;
					this.zoomValue = 1f;
					this.mtDistanceStart = d;
					this.mtDistanceEnd = this.mtDistanceStart;
				}
			} else if (MotionEvent.ACTION_POINTER_3_DOWN == event.getAction()
					&& event.getPointerCount() >= 3) {
				mutiActive = true;
				mutiDistance = mutiDistance(event);
			} else if (MotionEvent.ACTION_MOVE == event.getAction()) {
				if (mtZoomActive && event.getPointerCount() == 2) {
					float d = distance(event);
					if (d > 20f) {
						d = .6f * mtDistanceEnd + .4f * d;
						this.zoomValue = d / this.mtDistanceStart;
						mtDistanceEnd = d;
						zoom(zoomValue);
					}
				} else if (event.getPointerCount() == 1) {
					int distanceX = x - lastX;
					int distanceY = y - lastY;
					mouseMove(distanceX, distanceY);
					lastX = x;
					lastY = y;
				}
				// else if (mutiActive&&event.getPointerCount() >= 3) {
				//
				// }
			} else if (MotionEvent.ACTION_UP == event.getAction()
					|| MotionEvent.ACTION_POINTER_2_UP == event.getAction()
					|| MotionEvent.ACTION_POINTER_3_UP == event.getAction()) {
				if (mtZoomActive) {
					mtZoomActive = false;
				}
				if (mutiActive && event.getPointerCount() >= 3) {
					mutiActive = false;
					float d = mutiDistance(event);
					if ((mutiDistance - d) > 20f)
						close();
				}
				lastX = x;
				lastY = y;
			}
		}
		return true;
	}

	private void close() {
		// TODO Auto-generated method stub
		new Thread(new ClientRunnable("f4", mainActivity.getHandler())).start();
	}

	// 计算事件中两个点的距离
	public static float distance(MotionEvent event) {
		double dx = event.getX(0) - event.getX(1);
		double dy = event.getY(0) - event.getY(1);
		return distance(dx, dy);
	}

	public static float distance(double dx, double dy) {
		return (float) Math.sqrt(dx * dx + dy * dy);
	}

	public static float mutiDistance(MotionEvent event) {
		float totalDistance = 0f;
		for (int i = 0; i < event.getPointerCount() - 1; i++) {
			double dx = event.getX(i) - event.getX(i + 1);
			double dy = event.getY(i) - event.getY(i + 1);
			totalDistance += distance(dx, dy);
		}
		return totalDistance;
	}

	public void zoom(float zoomvalue) {
		new Thread(new ClientRunnable("zm " + zoomvalue,
				mainActivity.getHandler())).start();
	}

	public void mouseMove(int dx, int dy) {
		new Thread(new ClientRunnable("mm " + dx + " " + dy,
				mainActivity.getHandler())).start();
	}

	public void mouseClick() {
		new Thread(new ClientRunnable("mc", mainActivity.getHandler())).start();
	}

	public void MouseRightClick() {
		new Thread(new ClientRunnable("mr", mainActivity.getHandler())).start();
	}

}
