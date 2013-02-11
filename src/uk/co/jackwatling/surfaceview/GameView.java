package uk.co.jackwatling.surfaceview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable, SurfaceHolder.Callback{

	private SurfaceHolder holder;
	private Thread thread;
	private boolean running;
	
	private Bitmap icon;
	private Point2D pos;
	private Point2D vel;
	
	public GameView(Context context, AttributeSet attrs){
		super(context, attrs);		
		icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		holder = getHolder();
		holder.addCallback(this);
		thread = new Thread(this);
		running = false;
		pos = new Point2D();
		vel = new Point2D(3);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread.start();
		running = true;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		canvas.drawColor(Color.GRAY);
		canvas.drawBitmap(icon, pos.getX(), pos.getY(), null);
	}

	@Override
	public void run() {
		while (running){
			onUpdate();
			Canvas canvas = holder.lockCanvas();
			onDraw(canvas);
			holder.unlockCanvasAndPost(canvas);
		}
	}

	private void onUpdate() {
		pos.add(vel);
		if (pos.getX() > getWidth() - icon.getWidth() || pos.getX() < 0)
			vel.setX(vel.getX() * -1);
		if (pos.getY() > getHeight() - icon.getHeight() || pos.getY() < 0)
			vel.setY(vel.getY() * -1);
	}
	
}
