package com.sap.mervyn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;




public class EncodeOrDecodeRecordHashWithZip {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);		
		String recordHash = scanner.nextLine();
		decodeRecordHash(removeDoubleQuote(recordHash));
		scanner.close();
	}
	
	private static void decodeRecordHash(String hash) {

	    String decompressed = null;

	    byte[] compressed = Base64.getUrlDecoder().decode(hash);
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ByteArrayInputStream bain = new ByteArrayInputStream(compressed);

	    try {
	      GZIPInputStream gzin = new GZIPInputStream(bain);

	      int offset = -1;
	      byte[] buffer = new byte[1024];
	      while ((offset = gzin.read(buffer)) != -1) {
	        baos.write(buffer, 0, offset);
	      }

	      decompressed = baos.toString();
	      JsonObject decompressedObject = new JsonParser().parse(decompressed).getAsJsonObject();
	      Gson gson = new GsonBuilder().setPrettyPrinting().create();
	      decompressed = gson.toJson(decompressedObject);
	      System.out.println(decompressed);

	      gzin.close();
	      baos.close();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}
	
	private static String removeDoubleQuote(String compressedString) {
		return compressedString.replace("\"", "");
	}
	
	
}
