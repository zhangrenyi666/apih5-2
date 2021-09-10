package com.apih5.utils;

import java.math.BigDecimal;

import com.apih5.framework.utils.CalcUtils;

import cn.hutool.core.util.StrUtil;

public class DigitalConversionUtil {
	private static char[] cnNum = new char[] { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' };
	private static char[] unitArr = new char[] { '厘', '分', '角', '圆', '拾', '佰', '仟', '万', '亿' };

	/**
	 * 数字金额大写转换，思想先写个完整的然后将如零拾替换成零 要用到正则表达式
	 * @param n
	 * @return
	 */
	public static String digitUppercase(BigDecimal bigDecimal) {
		if (bigDecimal == null) {
			bigDecimal = new BigDecimal(0);
		}
		
		if (CalcUtils.compareToZero(bigDecimal) == 0) {
			return "零元整";
		}
		String fraction[] = { "角", "分" };
		String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String unit[][] = { { "元", "万", "亿" }, { "", "拾", "佰", "仟"} };
		String unit2[][] = { { "元", "万", "亿" }, { "", "拾", "佰", "仟", "万"} };
		String head = CalcUtils.compareToZero(bigDecimal) < 0 ? "负" : "";
		String s = "";
		String str = "";
		// 因n*10可能会出现四舍五入的可能，所以这里对小数部分要特别处理一下
		String strValue = digitToStr(bigDecimal, null);
		if (strValue.indexOf(".") > 0) {
			String afterdot = strValue.substring(strValue.lastIndexOf(".") + 1, strValue.length());
			for (int i = 0; i < fraction.length; i++) {
				if (afterdot.length() >= 1) {
					int m = Integer.parseInt(afterdot.substring(0, 1));
					s += (digit[m] + fraction[i]).replaceAll("(零.)+", "");
					afterdot = afterdot.substring(1);
				}
			}
			if (s.length() < 1) {
				s = "整";
			}
		} else {
			s = "整";
		}
		// 整数部分处理
		long integerPart =  bigDecimal.longValue();
		for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
			String p = "";
			if(integerPart > 9999L && i > 1) {
				for (int j = 0; j < unit2[1].length && integerPart > 0; j++) {
					p = digit[(int) (integerPart % 10)] + unit2[1][j] + p;
					integerPart = integerPart / 10;
				}
			} else {
				for (int j = 0; j < unit[1].length && integerPart > 0; j++) {
					p = digit[(int) (integerPart % 10)] + unit[1][j] + p;
					integerPart = integerPart / 10;
				}
			}
			s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
		}
		str = s.replaceAll("(零.)*零元", "元").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
		if (str.substring(0, 1).equals("零")) {
			str = str.substring(1, str.length());
		}
		return head + str;
	}
	
	/**
	 * 数字转换为字符并去掉小数点后面的零
	 */
	public static String digitToStr(BigDecimal bigDecimal, String fmtter) {
		String digitstr = "0";
		if (CalcUtils.compareToZero(bigDecimal) != 0) {
			if (StrUtil.isNotEmpty(fmtter)) {
				digitstr = bigDecimal + "";
			} else {
				String nstr = bigDecimal.divide(new BigDecimal(1), 6) + "";
				if (nstr.indexOf(".") > 0) {
					String beforedot = nstr.substring(0, nstr.indexOf("."));
					String afterdot = nstr.substring(nstr.indexOf(".") + 1, nstr.length());
					boolean flag = beforedot.startsWith("-");
					BigDecimal beforedotnum = new BigDecimal(beforedot);
					BigDecimal aferdotnum = new BigDecimal(afterdot);
					if (CalcUtils.compareToZero(aferdotnum) == 0) {
						digitstr = "" + beforedotnum;
					} else {
						while (afterdot.endsWith("0")) {
							afterdot = afterdot.substring(0, afterdot.length() - 1);
						}
						if (CalcUtils.compareToZero(beforedotnum) == 0 && flag) {
							digitstr = "-" + beforedotnum + "." + afterdot;
						} else {
							digitstr = beforedotnum + "." + afterdot;
						}
					}
				} else {
					digitstr = nstr;
				}
			}
		}
		return digitstr;
	}

	 /**
     * 中文金额转数字
     * @param chineseNumber 中文金额
     * @return
     */
    public static BigDecimal upperCaseToLowerCase(String chineseNumber) {
        BigDecimal result = new BigDecimal(0);
        int lastUnitIndex = 0, num = 0;
        chineseNumber = chineseNumber.replace("元", "圆");
        for (int i = 0; i < chineseNumber.length(); i++) {
            boolean isUnit = true;
            char c = chineseNumber.charAt(i);
            for (int j = 0; j < cnNum.length; j++) {
                // 是数字
                if (c == cnNum[j]) {
                    // 数字值 = 索引
                    num = j;
                    isUnit = false;
                    break;
                }
            }
            if (isUnit) {
                // 第一个就是单位，如：拾伍万圆整
                if (i == 0) {
                    num = 1;
                }
                int unitIndex = getUnitIndex(c);
                BigDecimal unit = getUnit(c);
                if (unitIndex > lastUnitIndex) {
                    result = result.add(new BigDecimal(num)).multiply(unit);
                } else {
                    result = result.add(new BigDecimal(num).multiply(unit));
                }
                lastUnitIndex = unitIndex;
                num = 0;
            }
        }
        
        if (StrUtil.contains(chineseNumber, "负")) {
        	result = CalcUtils.calcMultiply(result, new BigDecimal(-1));
		}
        
        return result.setScale(2,BigDecimal.ROUND_DOWN);
    }
 
    private static int getUnitIndex(char c) {
        for (int j = 0; j < unitArr.length; j++) {
            if (c == unitArr[j]) {
                return j;
            }
        }
        return 0;
    }
 
    private static BigDecimal getUnit(char c) {
        double num = 0;
        int unitIndex = getUnitIndex(c);
        switch (unitIndex) {
            // '厘', '分', '角', '圆', '拾', '佰', '仟', '万', '亿'
            case 4:
                num = 10;
                break;
            case 5:
                num = 100;
                break;
            case 6:
                num = 1000;
                break;
            case 7:
                num = 10000;
                break;
            case 8:
                num = 100000000;
                break;
            case 3:
                num = 1;
                break;
            case 2:
                num = 0.1;
                break;
            case 1:
                num = 0.01;
                break;
            case 0:
                num = 0.001;
                break;
            default:
                break;
        }
        return new BigDecimal(num);
    }
}
