package com.tint.wotn.utils;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class HexCoordinates {
	public static final Vector2[] AXIAL_DIRS = {
		new Vector2(1, 0), new Vector2(1, -1), new Vector2(0, -1),
		new Vector2(-1, 0), new Vector2(-1, 1), new Vector2(0, 1)
	};
	public static final Vector3[] CUBE_DIRS = {
		new Vector3(1, -1, 0), new Vector3(1, 0, -1), new Vector3(0, 1, -1),
		new Vector3(-1, 1, 0), new Vector3(-1, 0, 1), new Vector3(0, -1, 1)
	};
	public static final Vector2[] AXIAL_DIAGS = {
		new Vector2(2, -1), new Vector2(1, -2), new Vector2(-1, -1),
		new Vector2(-2, 1), new Vector2(-1, 2), new Vector2(1, 1)
	};
	public static final Vector3[] CUBE_DIAGS = {
		new Vector3(2, -1, -1), new Vector3(1, 1, -2), new Vector3(-1, 2, -1),
		new Vector3(-2, 1, 1), new Vector3(-1, -1, 2), new Vector3(1, -2, 1)
	};
	
	public static Vector2 transform(Vector3 cube) {
		return new Vector2(cube.x, cube.z);
	}
	
	public static Vector3 transform(Vector2 axial) {
		return new Vector3(axial.x, -axial.x - axial.y, axial.y);
	}
	
	public static Vector2 getAxialNeighbor(Vector2 axial, int direction) {
		Vector2 dir = AXIAL_DIRS[direction % AXIAL_DIRS.length];
		Vector2 result = new Vector2(axial.x + dir.x, axial.y + dir.y);
		return result;
	}
	
	public static Vector3 getCubeNeighbor(Vector3 cube, int direction) {
		Vector3 dir = CUBE_DIRS[direction % CUBE_DIRS.length];
		Vector3 result = new Vector3(cube.x + dir.x, cube.y + dir.y, cube.z + dir.z);
		return result;
	}

	public static Vector2 getAxialDiagonal(Vector2 axial, int direction) {
		Vector2 dir = AXIAL_DIAGS[direction % AXIAL_DIAGS.length];
		Vector2 result = new Vector2(axial.x + dir.x, axial.y + dir.y);
		return result;
	}
	
	public static Vector3 getCubeDiagonal(Vector3 cube, int direction) {
		Vector3 dir = CUBE_DIAGS[direction % CUBE_DIAGS.length];
		Vector3 result = new Vector3(cube.x + dir.x, cube.y + dir.y, cube.z + dir.z);
		return result;
	}
	
	public static float getCubeDistance(Vector3 a, Vector3 b) {
		return (Math.abs(a.x - b.x) + Math.abs(a.y - b.y) + Math.abs(a.z - b.z)) / 2;
	}
	
	public static float getAxialDistance(Vector2 a, Vector2 b) {
		Vector3 ac = transform(a);
		Vector3 bc = transform(b);
		return getCubeDistance(ac, bc);
	}
	
	public static List<Vector3> getAllInRing(Vector3 center, int radius) {
		List<Vector3> result = new ArrayList<Vector3>();
		Vector3 cube = new Vector3(
				center.x + CUBE_DIRS[4].x * radius,
				center.y + CUBE_DIRS[4].y * radius,
				center.z + CUBE_DIRS[4].z * radius
				);
		
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < radius; j++) {
				result.add(cube);
				cube = getCubeNeighbor(cube, i);
			}
		}
		return result;
	}
	
	public static List<Vector3> getAllInRange(
			float xmin, float xmax,
			float ymin, float ymax,
			float zmin, float zmax)
	{
		List<Vector3> result = new ArrayList<Vector3>();
		for(float x = xmin; x <= xmax; x++)
			for(float y = Math.max(ymin, -x -zmax); y <= Math.min(ymax, -x -zmin); y++)
				result.add(new Vector3(x, y, -x - y));
		
		return result;
	}
	
	public static List<Vector3> getAllInIntersectingRanges(List<Vector3> centers, int n) {
		List<Vector3> result = new ArrayList<Vector3>();
		if(centers.size() < 2) return result;
		Vector3 c0 = centers.get(0);
		Vector3 c1 = centers.get(1);
		float xmin = Math.max(c0.x - n, c1.x - n);
		float xmax = Math.min(c0.x + n, c1.x + n);
		float ymin = Math.max(c0.y - n, c1.y - n);
		float ymax = Math.min(c0.y + n, c1.y + n);
		float zmin = Math.max(c0.z - n, c1.z - n);
		float zmax = Math.min(c0.z + n, c1.z + n);
		
		for(int i = 2; i < centers.size(); i++) {
			Vector3 cn = centers.get(i);
			float xmin_temp = Math.max(xmin, cn.x - n);
			float xmax_temp = Math.min(xmax, cn.x + n);
			float ymin_temp = Math.max(ymin, cn.y - n);
			float ymax_temp = Math.min(ymax, cn.y + n);
			float zmin_temp = Math.max(zmin, cn.z - n);
			float zmax_temp = Math.min(zmax, cn.z + n);
			
			if(xmin_temp < xmin) xmin = xmin_temp;
			if(xmax_temp > xmax) xmax = xmax_temp;
			if(ymin_temp < ymin) ymin = ymin_temp;
			if(ymax_temp > ymax) ymax = ymax_temp;
			if(zmin_temp < zmin) zmin = zmin_temp;
			if(zmax_temp > zmax) zmax = zmax_temp;
		}
		
		result = getAllInRange(xmin, xmax, ymin, ymax, zmin, zmax);
		return result;
	}
	
	public static Vector3 cubeRound(Vector3 cube) {
		float rx = Math.round(cube.x);
		float ry = Math.round(cube.y);
		float rz = Math.round(cube.z);
		
		float x_diff = (rx - cube.x) * (rx - cube.x);
		float y_diff = (ry - cube.y) * (ry - cube.y);
		float z_diff = (rz - cube.z) * (rz - cube.z);
		
		if(x_diff > y_diff && x_diff > z_diff)
			rx = -ry - rz;
		else if(y_diff > z_diff)
			ry = -rx - rz;
		else
			rz = -rx - ry;
		
		return new Vector3(rx, ry, rz);
	}
}
