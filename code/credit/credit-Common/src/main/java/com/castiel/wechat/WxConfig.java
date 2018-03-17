package com.castiel.wechat;

public class WxConfig {
	// 公众号ID
	public static String APPID = "wx5bda8c260561016d";

	// 公众号应用密钥
	public static String APPSECRET = "3ea649d421a949d32ee3558c2bf58fd8";

	// 商户号
	public static String MCHID = "1315768801";

	// 用户支付成功后为微信调用的action 异步回调
	public static String NOTIFY_URL = "www.binpai.net.cn:8080/front/productInfo/paySuccess.action";

	// 商户支付密钥Key。审核通过后，在微信发送的邮件中查看
	public static String KEY = "BinPaiYunShang36b02536e98607227c";

}
