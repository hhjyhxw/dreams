package com.zhumeng.dream.util.encode;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

	public static String DEFALUT_KEY = "ABCDEFGHIJKLMN";

	public static String encrypt(String strKey, String strIn) throws Exception {
		SecretKeySpec skeySpec = getKey(strKey);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(strIn.getBytes());

		//return new BASE64Encoder().encode(encrypted);
		return parseByte2HexStr(encrypted);
	}

	public static String decrypt(String strKey, String strIn) throws Exception {
		SecretKeySpec skeySpec = getKey(strKey);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
		//byte[] encrypted1 = new BASE64Decoder().decodeBuffer(strIn);
		byte[] encrypted1 = parseHexStr2Byte(strIn);

		byte[] original = cipher.doFinal(encrypted1);
		String originalString = new String(original);
		return originalString;
	}

	private static SecretKeySpec getKey(String strKey) throws Exception {
		if(strKey == null || "".equals(strKey))
		{
			strKey = DEFALUT_KEY;
		}
		byte[] arrBTmp = strKey.getBytes();
		byte[] arrB = new byte[16]; // 创建一个空的16位字节数组（默认值为0）

		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}
		SecretKeySpec skeySpec = new SecretKeySpec(arrB, "AES");
		return skeySpec;
	}

	/**将16进制转换为二进制

	 * @param hexStr

	 * @return

	 */

	public static byte[] parseHexStr2Byte(String hexStr) {

		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}

		return result;

	}

	/**将二进制转换成16进制

	 * @param buf

	 * @return

	 */

	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}
	
//测试	
//	public static void main(String[] args) {
//		try {
//			String s = encrypt("0002000200020002","xxxxx");
//			System.out.println("加密后："+s);
//			
//			String j = decrypt("0002000200020002",s);
//			System.out.println("解密后："+j);
//			
//			String ori = decrypt("0002000200020002","0803D8BA899B8E0E52E5BD3C98DFAFBA");
//			System.out.println("解密后："+ori);
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	
}
