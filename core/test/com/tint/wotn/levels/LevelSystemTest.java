package com.tint.wotn.levels;

import static org.junit.Assert.*;

import org.junit.*;

import com.tint.wotn.Core;

public class LevelSystemTest {

	@Before
	public void setUp() {
		Core.INSTANCE.initializeLevelSystem();
	}
	
	@After
	public void tearDown() {
		Core.INSTANCE.dispose();
	}
	
	@Test
	public void testInitialization() {
		for(int i = 0; i < 1; i++)
			assertTrue(Core.INSTANCE.levelSystem.isValidID(i));
	}
	
	@Test
	public void testGetLevel() {
		Level nullLevel = Core.INSTANCE.levelSystem.getCurrentLevel();
		assertNotEquals(nullLevel, null);
		Core.INSTANCE.levelSystem.enterLevel(0);
		Level firstLevel = Core.INSTANCE.levelSystem.getCurrentLevel();
		assertNotEquals(nullLevel, firstLevel);
	}
	
	@Test
	public void testEnterLevel() {
		Core.INSTANCE.levelSystem.enterLevel(-2);
		assertEquals(Core.INSTANCE.levelSystem.getCurrentLevelID(), -1);
		Core.INSTANCE.levelSystem.enterLevel(5);
		assertEquals(Core.INSTANCE.levelSystem.getCurrentLevelID(), -1);
		Core.INSTANCE.levelSystem.enterLevel(0);
		assertEquals(Core.INSTANCE.levelSystem.getCurrentLevelID(), 0);
	}
}
