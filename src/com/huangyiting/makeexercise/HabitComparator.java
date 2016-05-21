package com.huangyiting.makeexercise;

import java.util.Comparator;

public class HabitComparator implements Comparator<HabitBean>  {

	@Override
	public int compare(HabitBean left, HabitBean right) {
		if (left.userName.equals(right.userName)) {
			return 0;
		} else {
			return -1;
		}
	}

	

}
