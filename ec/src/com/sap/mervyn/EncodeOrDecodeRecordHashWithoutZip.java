package com.sap.mervyn;

import java.util.Base64;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class EncodeOrDecodeRecordHashWithoutZip {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);		
		String recordHash = scanner.nextLine();
		decodeRecordHash(recordHash);
		scanner.close();
	}
	
	private static void decodeRecordHash(String hash) {

	    String decompressed = null;

	    byte[] compressed = Base64.getUrlDecoder().decode(hash);

	    decompressed = String.valueOf(compressed);
	    JsonObject decompressedObject = new JsonParser().parse(decompressed).getAsJsonObject();
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    decompressed = gson.toJson(decompressedObject);
	    System.out.println(decompressed);
	}
	
	private static String removeDoubleQuote(String compressedString) {
		return compressedString.replace("\"", "");
	}
	
	
}
