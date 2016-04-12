package com.tint.wotn.gfx;

import com.badlogic.gdx.graphics.Color;

public class ColourTransition {

	private final Color startColor;
	private final Color endColor;
	private Color currentColor;
	private Color deltaColor;
	private final int steps;
	private int currentStep;
	
	public ColourTransition(Color startColor, Color endColor, int steps) {
		this.startColor = startColor;
		this.endColor = endColor;
		this.steps = steps;
		currentColor = startColor;
		currentStep = 0;
		
		float dr = Math.abs((float) (endColor.r - startColor.r) / (float) steps);
		float dg = Math.abs((float) (endColor.g - startColor.g) / (float) steps);
		float db = Math.abs((float) (endColor.b - startColor.b) / (float) steps);
		float da = Math.abs((float) (endColor.a - startColor.a) / (float) steps);
		
		deltaColor = new Color(dr, dg, db, da);
	}
	
	public void reset() {
		currentStep = 0;
		currentColor = startColor;
	}
	
	public void step() {
		if(currentStep < steps)
			currentStep++;
		
		currentColor.add(-deltaColor.r, -deltaColor.g, -deltaColor.b, -deltaColor.a);
	}
	
	public Color getCurrentColor() {
		return currentColor;
	}
	
	public Color getDeltaColor() {
		return deltaColor;
	}
	
	public Color getStartColor() {
		return startColor;
	}
	
	public Color getEndColor() {
		return endColor;
	}
	
	public int getSteps() {
		return steps;
	}
	
	public int getCurrentStep() {
		return currentStep;
	}
	
	public boolean isFinished() {
		return currentStep == steps;
	}
	
	public float getProgress() {
		return ((float) currentStep / steps); 
	}
}
