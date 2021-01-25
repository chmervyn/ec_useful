package com.sap.mervyn;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ClearBuildAndTargetFoldersForModules {
	private static final String ROOT = "C:\\SuccessFactors\\ec\\";
	
	private static final String BUILD_SYSTEM = "build-system";
	
	private static final List<String> backendModules = Arrays.asList("au-employeecentral");
	
	private static final List<String> UIModules = Arrays.asList("au-peopleprofile");
	
	public static void main(String[] args) {
		clearBuildAndTargetFolders();
	}
	
	private static void clearBuildAndTargetFolders() {
		
		new Thread(ClearBuildAndTargetFoldersForModules::clearTomcatRelatedFiles).start();
		new Thread(ClearBuildAndTargetFoldersForModules::clearBuildSystem).start();
		new Thread(ClearBuildAndTargetFoldersForModules::clearBackendModules).start();
		new Thread(ClearBuildAndTargetFoldersForModules::clearUIModules).start();
	}
	
	private static void clearTomcatRelatedFiles() {
		File[] files = new File(ROOT).listFiles();
		for (File file : files) {
			if (!file.getName().equals(".idea") && !file.getName().equals(BUILD_SYSTEM) 
					&& !backendModules.contains(file.getName()) && !UIModules.contains(file.getName())) {
				deleteDirectory(file.getPath());
			}
		}
	}
	
	private static void clearBuildSystem() {
		File[] files = new File(ROOT + BUILD_SYSTEM).listFiles();
		for (File file : files) {
			if (file.isDirectory() && (".gradle".equalsIgnoreCase(file.getName()) || "build".equalsIgnoreCase(file.getName()))) {
				deleteDirectory(file.getPath());
			} 
		}
	}
	
	private static void clearBackendModules() {
		backendModules.forEach(module -> {
			File[] files = new File(ROOT + module).listFiles();
			for (File file : files) {
				if (file.isDirectory() && file.getName().startsWith(module + "-")) {
					deleteDirectory(file.getPath() + "\\" + "build");
					deleteDirectory(file.getPath() + "\\" + "output");
				}
			}
		});
		
	}
	
	private static void clearUIModules() {
		UIModules.forEach(module -> {
			File[] files = new File(ROOT + module).listFiles();
			for (File file : files) {
				if (file.isDirectory() && file.getName().startsWith(module + "-")) {
					deleteDirectory(file.getPath() + "\\" + "build");
					deleteDirectory(file.getPath() + "\\" + "target");
				}
			}
		});		
	}
	
	private static boolean deleteDirectory(String dir) {
		File file = new File(dir);
		
		boolean delete = true;
		if (file.exists()) {
			if (file.isDirectory()) {
				String[] children = file.list();
				if (children != null && children.length > 0) {
					for (String child : children) {
						boolean success = deleteDirectory(file.getPath() + "\\" + child);
						if (!success) {
							return false;
						}
					}
				}
				
				delete = file.delete();
			} else {
				delete = file.delete();
			}
		}
		
		return delete;
	}
	
}
