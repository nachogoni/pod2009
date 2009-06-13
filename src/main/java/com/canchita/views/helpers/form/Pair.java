package com.canchita.views.helpers.form;

public class Pair<T, S> {
	private T first;
	private S second;

	public Pair(T f, S s) {
		first = f;
		second = s;
	}

	public T getFirst() {
		return first;
	}

	public S getSecond() {
		return second;
	}

	public String toString() {
		return "(" + first.toString() + ", " + second.toString() + ")";
	}
}
