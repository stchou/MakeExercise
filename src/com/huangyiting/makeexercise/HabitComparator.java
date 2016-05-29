package com.huangyiting.makeexercise;

import java.util.Comparator;

public class HabitComparator implements Comparator<String> {

	@Override
	public int compare(String left, String right) {

		String leftnumber = left.split("-")[0];
		String rightnumber = right.split("-")[0];
		int leftValue;
		try {
			leftValue = Integer.valueOf(leftnumber);
		} catch (Exception e) {
			leftValue = 0;
		}

		int rightValue;
		try {
			rightValue = Integer.valueOf(rightnumber);
		} catch (Exception e) {
			rightValue = 0;
		}
		return leftValue - rightValue;

	}

}
