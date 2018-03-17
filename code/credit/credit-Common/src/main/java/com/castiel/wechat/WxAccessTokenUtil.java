package com.castiel.wechat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;


public class WxAccessTokenUtil {
	/**得到网页授权access_token**/
	public static JSONObject getAccessToken(HttpServletRequest request,String code){
		HttpSession session = request.getSession();
		JSONObject accessToken_jsonObject = null;
		if(session.getAttribute("access_token_jsonObject")!=null){
			accessToken_jsonObject = (JSONObject)session.getAttribute("access_token_jsonObject");
			/**验证授权凭证是否有效**/							
			String checkAccessTokenUrl = "https://api.weixin.qq.com/sns/auth?access_token="+accessToken_jsonObject.getString("access_token")+"&openid="+accessToken_jsonObject.getString("openid");
			JSONObject old_accessToken_jsonObject = JSONObject.fromObject(loadJson(checkAccessTokenUrl));
			//如果票据失效,重新获取票据,0为有效
			if(old_accessToken_jsonObject.getInt("errcode")!=0){
				String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+WxConfig.APPID+"&secret="+WxConfig.APPSECRET+"&code="+code+"&grant_type=authorization_code";
				accessToken_jsonObject = JSONObject.fromObject(loadJson(accessTokenUrl));
				session.setAttribute("access_token_jsonObject",accessToken_jsonObject);
			}
		}else{
			String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+WxConfig.APPID+"&secret="+WxConfig.APPSECRET+"&code="+code+"&grant_type=authorization_code";
			accessToken_jsonObject = JSONObject.fromObject(loadJson(accessTokenUrl));
			session.setAttribute("access_token_jsonObject",accessToken_jsonObject);
		}
		return accessToken_jsonObject;
	}
	
	/**得到普通access_token**/
	public static JSONObject getBaseAccessToken(HttpServletRequest request){
		HttpSession session = request.getSession();
		JSONObject base_access_token_jsonObject = null;
		if(session.getAttribute("base_access_token_jsonObject")!=null){
			base_access_token_jsonObject = (JSONObject)session.getAttribute("base_access_token_jsonObject");
			String checkAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+base_access_token_jsonObject.getString("access_token")+"&type=jsapi";
			JSONObject old_base_accessToken_jsonObject = JSONObject.fromObject(loadJson(checkAccessTokenUrl));
			//如果票据失效,重新获取票据
			if(old_base_accessToken_jsonObject.getInt("errcode")!=0){
				String baseAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+WxConfig.APPID+"&secret="+WxConfig.APPSECRET+"";
				base_access_token_jsonObject = JSONObject.fromObject(loadJson(baseAccessTokenUrl));
				session.setAttribute("base_access_token_jsonObject",base_access_token_jsonObject);
			}
		}else{
			String baseAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+WxConfig.APPID+"&secret="+WxConfig.APPSECRET+"";
			base_access_token_jsonObject = JSONObject.fromObject(loadJson(baseAccessTokenUrl));
			session.setAttribute("base_access_token_jsonObject",base_access_token_jsonObject);
		}
		return base_access_token_jsonObject;
	}
	
	public static JSONObject getUserInfo(String accesstoken,String openid){
		String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+accesstoken+"&openid="+openid+"&lang=zh_CN";
		JSONObject userInfo_jsonObject = JSONObject.fromObject(loadJson(userInfoUrl));
		return userInfo_jsonObject;
	}
	
	public static JSONObject getUserInfoDetail(String accesstoken,String openid){
		String userInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+accesstoken+"&openid="+openid+"&lang=zh_CN";
		JSONObject userInfo_detail_jsonObject = JSONObject.fromObject(loadJson(userInfoUrl));
		return userInfo_detail_jsonObject;
	}
	
	public static JSONObject getJsapiTicket(String accesstoken){
		String jsapiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accesstoken+"&type=jsapi";
		JSONObject jsapiTicket_jsonObject = JSONObject.fromObject(loadJson(jsapiTicketUrl));
		return jsapiTicket_jsonObject;
	}
	
	/**java通过网址获取json数据**/
	public static String loadJson (String url) {  
        StringBuilder json = new StringBuilder();  
        try {
            URL urlObject = new URL(url);  
            URLConnection uc = urlObject.openConnection();  
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(), "utf-8"));
            String inputLine = null;  
            while ( (inputLine = in.readLine()) != null) {  
                json.append(inputLine);  
            }  
            in.close();  
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return json.toString(); 
    } 
	
	
	/**java通过网址获取json数据、带参数**/
	public static String loadJson (String url,String parameter) {  
        StringBuilder json = new StringBuilder();  
        try {
            URL urlObject = new URL(url);  
            URLConnection uc = urlObject.openConnection(); 
            uc.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(uc.getOutputStream(), "utf-8"); 
            //write parameters
            writer.write(parameter);
            writer.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(), "utf-8"));
            String inputLine = null;  
            while ( (inputLine = in.readLine()) != null) {  
                json.append(inputLine);  
            }  
            writer.close();
            in.close();  
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return json.toString(); 
    } 
}
