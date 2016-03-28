package com.xanxus;

import java.awt.event.KeyEvent;

/**
 * 将键的内容转换成keyevent的枚举值
 * 
 * @author xanxus
 * 
 */
public class KeyConverter {

	public static int convert(String keyString) {
		switch (keyString) {
		case "a":
			return KeyEvent.VK_A;
		case "b":
			return KeyEvent.VK_B;
		case "c":
			return KeyEvent.VK_C;
		case "d":
			return KeyEvent.VK_D;
		case "e":
			return KeyEvent.VK_E;
		case "f":
			return KeyEvent.VK_F;
		case "g":
			return KeyEvent.VK_G;
		case "h":
			return KeyEvent.VK_H;
		case "i":
			return KeyEvent.VK_I;
		case "j":
			return KeyEvent.VK_J;
		case "k":
			return KeyEvent.VK_K;
		case "l":
			return KeyEvent.VK_L;
		case "m":
			return KeyEvent.VK_M;
		case "n":
			return KeyEvent.VK_N;
		case "o":
			return KeyEvent.VK_O;
		case "p":
			return KeyEvent.VK_P;
		case "q":
			return KeyEvent.VK_Q;
		case "r":
			return KeyEvent.VK_R;
		case "s":
			return KeyEvent.VK_S;
		case "t":
			return KeyEvent.VK_T;
		case "u":
			return KeyEvent.VK_U;
		case "v":
			return KeyEvent.VK_V;
		case "w":
			return KeyEvent.VK_W;
		case "x":
			return KeyEvent.VK_X;
		case "y":
			return KeyEvent.VK_Y;
		case "z":
			return KeyEvent.VK_Z;
		case "f1":
			return KeyEvent.VK_F1;
		case "f2":
			return KeyEvent.VK_F2;
		case "f3":
			return KeyEvent.VK_F3;
		case "f4":
			return KeyEvent.VK_F4;
		case "f5":
			return KeyEvent.VK_F5;
		case "f6":
			return KeyEvent.VK_F6;
		case "f7":
			return KeyEvent.VK_F7;
		case "f8":
			return KeyEvent.VK_F8;
		case "f9":
			return KeyEvent.VK_F9;
		case "f10":
			return KeyEvent.VK_F10;
		case "f11":
			return KeyEvent.VK_F11;
		case "f12":
			return KeyEvent.VK_F12;
		case "0":
			return KeyEvent.VK_0;
		case "1":
			return KeyEvent.VK_1;
		case "2":
			return KeyEvent.VK_2;
		case "3":
			return KeyEvent.VK_3;
		case "4":
			return KeyEvent.VK_4;
		case "5":
			return KeyEvent.VK_5;
		case "6":
			return KeyEvent.VK_6;
		case "7":
			return KeyEvent.VK_7;
		case "8":
			return KeyEvent.VK_8;
		case "9":
			return KeyEvent.VK_9;
		case "-":
			return KeyEvent.VK_MINUS;
		case "=":
			return KeyEvent.VK_EQUALS;
		case "left":
			return KeyEvent.VK_LEFT;
		case "right":
			return KeyEvent.VK_RIGHT;
		case "up":
			return KeyEvent.VK_UP;
		case "down":
			return KeyEvent.VK_DOWN;
		case "pageup":
			return KeyEvent.VK_PAGE_UP;
		case "pagedown":
			return KeyEvent.VK_PAGE_DOWN;
		case "space":
			return KeyEvent.VK_SPACE;
		case "enter":
			return KeyEvent.VK_ENTER;
		case "home":
			return KeyEvent.VK_HOME;
		case "end":
			return KeyEvent.VK_END;
		default:
			return -1;
		}
	}
}
