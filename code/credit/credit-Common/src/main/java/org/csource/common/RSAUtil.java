package org.csource.common;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

import com.alibaba.fastjson.JSON;
import com.sun.jersey.core.util.Base64;


public class RSAUtil {
	private static final String ALGORITHM = "RSA";
	private static final String SIGN_ALGORITHM = "SHA1withRSA";


	/**
	 * 签名
	 * 
	 * @param text
	 * @param privateKeys
	 * @param algorithm
	 * @return
	 * @throws Exception
	 */
	public static byte[] sign(byte[] text, String privateKey) throws Exception {
		PrivateKey priKey = getPrivateKey(privateKey);
		Signature signatureChecker = Signature.getInstance(SIGN_ALGORITHM);
		signatureChecker.initSign(priKey);
		signatureChecker.update(text);
		return signatureChecker.sign();
	}

	/**
	 * 得到私钥
	 * 
	 * @param key 密钥字符串（经过base64编码）
	 * @return 私钥对象
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(final String key) throws Exception {
		byte[] keyBytes = null;
		keyBytes = Base64.decode(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	public static void main(String[] args) throws Exception {
		String begindate = "20170621";
		String enddate = "20170707";
		String param = "begindate=" + begindate + "&enddate=" + enddate;
		System.err.println(param);
		String ret = HttpUtil.sendPost("http://222.197.164.93/yktpre/services/activity/list", param);
		ResultBean resultBean = JSON.parseObject(ret, ResultBean.class);
		System.err.println(resultBean.getRetData().size());
		System.err.println(ret);
	}
}
