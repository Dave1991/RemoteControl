package com.xanxus;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class AbstractLayout extends RelativeLayout {

	protected Context mContext;
	protected String firstCommand;
	protected String secondCommand;
	protected String thirdCommand;
	protected String fourthCommand;
	protected String fifthCommand;
	protected String sixthCommand;
	protected String seventhCommand;
	protected String eightCommand;
	protected LinearLayout touchLayout;
	protected Button leftButton = null;
	protected Button rightButton = null;
	protected MainActivity mainActivity = null;

	public AbstractLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		mainActivity = (MainActivity) context;
	}

	protected void initView() {
	}

	protected void setButtonHandler() {
		leftButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new ClientRunnable("mc",mainActivity.getHandler())).start();
			}
		});
		leftButton.setOnLongClickListener(new View.OnLongClickListener() {

			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new ClientRunnable("lp",mainActivity.getHandler())).start();
				return true;
			}
		});
		rightButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new ClientRunnable("rc",mainActivity.getHandler())).start();
			}
		});
	}

	public String getFirstCommand() {
		return firstCommand;
	}

	public void setFirstCommand(String firstCommand) {
		this.firstCommand = firstCommand;
	}

	public String getSecondCommand() {
		return secondCommand;
	}

	public void setSecondCommand(String secondCommand) {
		this.secondCommand = secondCommand;
	}

	public String getThirdCommand() {
		return thirdCommand;
	}

	public void setThirdCommand(String thirdCommand) {
		this.thirdCommand = thirdCommand;
	}

	public String getFourthCommand() {
		return fourthCommand;
	}

	public void setFourthCommand(String fourthCommand) {
		this.fourthCommand = fourthCommand;
	}

	public String getFifthCommand() {
		return fifthCommand;
	}

	public void setFifthCommand(String fifthCommand) {
		this.fifthCommand = fifthCommand;
	}

	public String getSixthCommand() {
		return sixthCommand;
	}

	public void setSixthCommand(String sixthCommand) {
		this.sixthCommand = sixthCommand;
	}

	public String getSeventhCommand() {
		return seventhCommand;
	}

	public void setSeventhCommand(String seventhCommand) {
		this.seventhCommand = seventhCommand;
	}

	public void setCommand(int position, String command) {
		switch (position) {
		case 1:
			firstCommand = command;
			break;
		case 2:
			secondCommand = command;
			break;
		case 3:
			thirdCommand = command;
			break;
		case 4:
			fourthCommand = command;
			break;
		case 5:
			fifthCommand = command;
			break;
		case 6:
			sixthCommand = command;
			break;
		case 7:
			seventhCommand = command;
			break;
		}
	}

	public String getEightCommand() {
		return eightCommand;
	}

	public void setEightCommand(String eightCommand) {
		this.eightCommand = eightCommand;
	}
}
