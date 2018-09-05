package com.dognessnetwork.ops.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	private static String ALGORITHM_MD5 = "MD5";

	public static String getMD5(String srcTxt) throws NoSuchAlgorithmException,
			NullPointerException {
		if (srcTxt == null) {
			throw new NullPointerException("输入参数为 NULL!");
		}

		String result = "";

		MessageDigest digest = MessageDigest.getInstance(ALGORITHM_MD5);
		digest.update(srcTxt.getBytes());
		byte[] byteRes = digest.digest();

		int length = byteRes.length;

		for (int i = 0; i < length; ++i) {
			result = result + byteHEX(byteRes[i]);
		}

		return result;
	}

	private static String byteHEX(byte ib) {
		char[] DigitNormal = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9', 'a', 'b', 'c', 'd', 'e', 'f' };

		char[] ob = new char[2];

		ob[0] = DigitNormal[(ib >>> 4 & 0xF)];

		ob[1] = DigitNormal[(ib & 0xF)];

		return new String(ob);
	}
//
//	public static String getMD5byBase64(String input) throws Exception {
//		String result = null;
//		byte[] byteRes = (byte[]) null;
//		try {
//			MessageDigest digest = MessageDigest.getInstance(ALGORITHM_MD5);
//			digest.update(input.getBytes());
//			byteRes = digest.digest();
//		} catch (NoSuchAlgorithmException ex) {
//			System.err.println(ex.getMessage());
//		}
//
//		result = Base64Util.encodeBase64(byteRes);
//
//		return result;
//	}
	public static String MD5Lower(String plain) {
		String re_md5 = new String();
		try {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(plain.getBytes());
		byte b[] = md.digest();

		int i;

		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
		i = b[offset];
		if (i < 0)
		i += 256;
		if (i < 16)
		buf.append("0");
		buf.append(Integer.toHexString(i));
		}

		re_md5 = buf.toString();

		} catch (NoSuchAlgorithmException e) {
		e.printStackTrace();
		}
		return re_md5;
}

public static void main(String[] args) {
		try {
			String s = MD5Util.getMD5("123456");
			System.out.println(s);
		}catch (Exception e){
			e.printStackTrace();
		}
}
}

