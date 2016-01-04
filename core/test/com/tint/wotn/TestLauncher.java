package com.tint.wotn;

public class TestLauncher {
	public static void main(String[] args) {
		String[] tests = {
			"com.tint.wotn.combat.CombatSystemTest"
		};
		org.junit.runner.JUnitCore.main(tests);
	}
}
