package com.apih5.utils;

import java.io.File;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apkinfo.api.util.AXmlResourceParser;
import org.apkinfo.api.util.TypedValue;
import org.apkinfo.api.util.XmlPullParser;

public class ApkInfoUtil {
	private static final float RADIX_MULTS[] = { 0.00390625F, 3.051758E-005F, 1.192093E-007F, 4.656613E-010F };
	private static final String DIMENSION_UNITS[] = { "px", "dip", "sp", "pt", "in", "mm", "", "" };
	private static final String FRACTION_UNITS[] = { "%", "%p", "", "", "", "", "", "" };

	public static void main(String[] args) {
		String apkPath = ApkInfoUtil.getFilePath("D:/www/htmlProject/apk/");
		String versionCode = ApkInfoUtil.readAPK(apkPath);
//		System.out.println(versionCode);
	}
	/**
	 * 读取APK版本
	 * 
	 * @param apkUrl
	 * @return
	 */
	public static String readAPK(String apkUrl) {
		ZipFile zipFile;
		String versionCode = "";
		try {
			zipFile = new ZipFile(apkUrl);
			Enumeration<?> enumeration = zipFile.entries();
			ZipEntry zipEntry = null;
			while (enumeration.hasMoreElements()) {
				zipEntry = (ZipEntry) enumeration.nextElement();
				if (!zipEntry.isDirectory()) {
					if ("androidmanifest.xml".equals(zipEntry.getName().toLowerCase())) {
						AXmlResourceParser parser = new AXmlResourceParser();
						parser.open(zipFile.getInputStream(zipEntry));
						while (true) {
							int type = parser.next();
							if (type == XmlPullParser.END_DOCUMENT) {
								break;
							}
							String name = parser.getName();
							if (null != name && name.toLowerCase().equals("manifest")) {
								for (int i = 0; i != parser.getAttributeCount(); i++) {
									if ("versionName".equals(parser.getAttributeName(i))) {
										String versionName = getAttributeValue(parser, i);
										if (null == versionName) {
											versionName = "";
										}
										versionCode = versionName;
									}
								}
								break;
							}
						}
					}
				}
			}
			zipFile.close();
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return versionCode;
	}

	private static String getAttributeValue(AXmlResourceParser parser, int index) {
		int type = parser.getAttributeValueType(index);
		int data = parser.getAttributeValueData(index);
		if (type == TypedValue.TYPE_STRING) {
			return parser.getAttributeValue(index);
		}
		if (type == TypedValue.TYPE_ATTRIBUTE) {
			return String.format("?%s%08X", getPackage(data), data);
		}
		if (type == TypedValue.TYPE_REFERENCE) {
			return String.format("@%s%08X", getPackage(data), data);
		}
		if (type == TypedValue.TYPE_FLOAT) {
			return String.valueOf(Float.intBitsToFloat(data));
		}
		if (type == TypedValue.TYPE_INT_HEX) {
			return String.format("0x%08X", data);
		}
		if (type == TypedValue.TYPE_INT_BOOLEAN) {
			return data != 0 ? "true" : "false";
		}
		if (type == TypedValue.TYPE_DIMENSION) {
			return Float.toString(complexToFloat(data)) + DIMENSION_UNITS[data & TypedValue.COMPLEX_UNIT_MASK];
		}
		if (type == TypedValue.TYPE_FRACTION) {
			return Float.toString(complexToFloat(data)) + FRACTION_UNITS[data & TypedValue.COMPLEX_UNIT_MASK];
		}
		if (type >= TypedValue.TYPE_FIRST_COLOR_INT && type <= TypedValue.TYPE_LAST_COLOR_INT) {
			return String.format("#%08X", data);
		}
		if (type >= TypedValue.TYPE_FIRST_INT && type <= TypedValue.TYPE_LAST_INT) {
			return String.valueOf(data);
		}
		return String.format("<0x%X, type 0x%02X>", data, type);
	}

	private static String getPackage(int id) {
		if (id >>> 24 == 1) {
			return "android:";
		}
		return "";
	}

	public static float complexToFloat(int complex) {
		return (float) (complex & 0xFFFFFF00) * RADIX_MULTS[(complex >> 4) & 3];
	}

	/**
	 * 获取文件夹下文件名称
	 * 
	 * @param path
	 * @return
	 */
	public static String getFilePath(String path) {
		String filePath = "";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}

		File[] array = file.listFiles();
		int length = array.length;
		for (int i = 0; i < length; i++) {
			if (array[i].isFile()) {
				if (array[i].getPath().contains(".apk")) {
					filePath = array[i].getPath();
					break;
				}
			} else if (array[i].isDirectory()) {
				getFilePath(array[i].getPath());
			}
		}
		return filePath;
	}

	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 * 
	 * @param dir
	 *            将要删除的文件目录
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}
}
