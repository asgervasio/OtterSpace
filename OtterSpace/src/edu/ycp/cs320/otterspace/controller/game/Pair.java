package edu.ycp.cs320.otterspace.controller.game;

public class Pair<LeftType, RightType> {
	private LeftType left;
	private RightType right;
	
	public Pair() {
	}
	
	public void setLeft(LeftType left) {
		this.left = left;
	}
	
	public LeftType getLeft() {
		return left;
	}
	
	public void setRight(RightType right) {
		this.right = right;
	}
	
	public RightType getRight() {
		return right;
	}
}
