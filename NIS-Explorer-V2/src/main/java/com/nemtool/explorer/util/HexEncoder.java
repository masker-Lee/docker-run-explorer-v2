package com.nemtool.explorer.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * @Description: Static class that contains utility functions for converting hex strings to and from bytes.
 * @author Masker
 * @date 2020.06.28
 */
public class HexEncoder {

	/**
	 * decode payload message (for type 1)
	 * @param message
	 * @return
	 */
	public static String decodeMessage(String message){
		if(message==null){
			return "";
		}
		String result = "";
        byte[] bytes = getBytes(message);
        try {
        	result = new String(bytes, "UTF-8");
        } catch(Exception e) {
        	e.printStackTrace();
        }
        return result;
	}
	
	/**
	 * Converts a hex string to a byte array.
	 *
	 * @param hexString
	 *            The input hex string.
	 * @return The output byte array.
	 */
	public static byte[] getBytes(final String hexString) {
		try {
			return getBytesInternal(hexString);
		} catch (final DecoderException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Tries to convert a hex string to a byte array.
	 *
	 * @param hexString
	 *            The input hex string.
	 * @return The output byte array or null if the input string is malformed.
	 */
	public static byte[] tryGetBytes(final String hexString) {
		try {
			return getBytesInternal(hexString);
		} catch (final DecoderException e) {
			return null;
		}
	}

	private static byte[] getBytesInternal(final String hexString) throws DecoderException {
		final Hex codec = new Hex();
		final String paddedHexString = 0 == hexString.length() % 2 ? hexString : "0" + hexString;
		final byte[] encodedBytes = StringEncoder.getBytes(paddedHexString);
		return codec.decode(encodedBytes);
	}

	/**
	 * Converts a byte array to a hex string.
	 *
	 * @param bytes
	 *            The input byte array.
	 * @return The output hex string.
	 */
	public static String getString(final byte[] bytes) {
		final Hex codec = new Hex();
		final byte[] decodedBytes = codec.encode(bytes);
		return StringEncoder.getString(decodedBytes);
	}
	
	/**
	 * Converts hex to utf-8, for message payload
	 * @param message
	 * @return
	 */
	public static String hexToUtf8(String message) {
		if (message.indexOf("fe") == 0) {
			return "HEX: " + message.substring(2);
		} else {
			return decodeMessage(message);
		}
	}
}