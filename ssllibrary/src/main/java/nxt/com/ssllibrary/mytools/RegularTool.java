package nxt.com.ssllibrary.mytools;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegularTool {
	public static boolean isPhoneNumberValid(String phoneNumber) {
		boolean flag = false;
		String expression = "((^(13|14|15|17|18)[0-9]{9}$)|(^0[1,2]{1}d{1}-?d{8}$)|"
				+ "(^0[3-9] {1}d{2}-?d{7,8}$)|"
				+ "(^0[1,2]{1}d{1}-?d{8}-(d{1,4})$)|"
				+ "(^0[3-9]{1}d{2}-? d{7,8}-(d{1,4})$))";
		CharSequence phone = phoneNumber;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(phone);
		if (matcher.matches()) {
			flag = true;
		}
		return flag;
	}
	
	public static boolean isEmail(String email){
		boolean flag2 = false;
		String expression2 ="^[a-zA-Z0-9]+([\\_|\\-|\\.]?[a-zA-Z0-9])*\\@[a-zA-Z0-9]+([\\_|\\-|\\.]?[a-zA-Z0-9])*\\.[a-zA-Z]{2,3}$";
		CharSequence email2 = email;
		Pattern pattern = Pattern.compile(expression2);
		Matcher matcher = pattern.matcher(email2);
		if (matcher.matches()) {
			flag2 = true;
		}
		return flag2;
	}
	
	
	/**
	 * 验证手机格式
	 */
	public static boolean isMobileNO(String mobiles) {
		/*
		移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		联通：130、131、132、152、155、156、185、186
		电信：133、153、180、189、（1349卫通）
		总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		*/
		String telRegex = "[1][34578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(mobiles)) return false;
		else return mobiles.matches(telRegex);
    }
	
	/**
	 * 验证qq号格式
	 * @param qqNo (验证6-12位的qq号)
	 * @return
	 */
	public static boolean isQQNO(String qqNo){
		String qqRegex = "^[1-9][0-9]{5,11}$";
		CharSequence qq = qqNo;
		Pattern pattern = Pattern.compile(qqRegex);
		Matcher matcher = pattern.matcher(qq);
		return matcher.matches();
	}



	// 手机号以158****3443�?形式表现
	public static String subString(String dataMobile) {
		String dataPhone = "";
		if(dataMobile.length() == 11){
		try {
			dataPhone = dataMobile.substring(0, 3) + "****"
					+ dataMobile.substring(7, 11);
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		return dataPhone;
	}

	
}
