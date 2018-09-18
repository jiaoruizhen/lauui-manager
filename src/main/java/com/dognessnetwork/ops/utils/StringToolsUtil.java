package com.dognessnetwork.ops.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dognessnetwork.ops.domain.SIMCardInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import sun.misc.BASE64Encoder;

/**
 * 字符串工具类
 *
 */
@SuppressWarnings("restriction")
public class StringToolsUtil {

	private static Logger log = LoggerFactory.getLogger(StringToolsUtil.class);

	/**
	 * 获取当前容器的根目录，如jboss则结果为/data/jboss4.2.3GA/，如tomcat则为/data/tomcat7/
	 * 
	 * @return
	 */
	public static String getContainerPath() {
		// jboss结果为其bin目录如/data/jboss.4.2.3.GA/bin，tomcat则结果为其根目录如/data/tomcat7
		String containerBinPath = System.getProperty("user.dir");
		System.out.println("system property [user.dir] = " + containerBinPath);
		// 把windows路径中的\替换为linux路径的/
		containerBinPath = containerBinPath.replaceAll("\\\\", "/");
		if (containerBinPath.contains("/bin")) {
			return containerBinPath.substring(0, containerBinPath.indexOf("/bin"));
		} else {
			return containerBinPath;
		}
	}

	/**
	 * 字符串首字母大写转换
	 * 
	 * @return
	 */
	public static String firstCharUp(String str) {
		if (isNull(str)) {
			return "";
		}
		try {
			char[] cs = str.toCharArray();
			// 首字母大写，并且首字母是小写才转。
			if (cs[0] > 96) {
				cs[0] -= 32;
			}
			return String.valueOf(cs);
		} catch (Exception e) {
			log.error("首字母大写转换出错", e);
		}
		return "";
	}

	/**
	 * obj转换为json字符串，同时忽略空属性，即只输出非空属性。
	 * 
	 * @param obj
	 * @return
	 */
	public static String obj2Json(final Object obj) {
		if (obj == null) {
			return "";
		}
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return "";
	}

	/**
	 * json字符串转换为对象，需要指定class。
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T json2Obj(String json, Class<T> clazz) {
		if (isNull(json)) {
			return null;
		}
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(json, clazz);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 取得本应用所在机器的ip
	 * 
	 * @return
	 */
	public static String getCurrentIp() {
		String ip = "";
		try {
			InetAddress addr = InetAddress.getLocalHost();
			ip = addr.getHostAddress();// 获得本机IP
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ip;
	}

	/**
	 * 判断字符串是否为空或空白串。
	 * 
	 * @param str
	 * @return 若字符串为null或空白串返回true
	 */
	public static boolean isNull(String str) {
		return str == null || str.trim().length() < 1;
	}

	/**
	 * 生成length位的随机字符串
	 * 
	 * @return
	 */
	public static String createNoncestr(int length) {
		String chars = "0123456789";
		String res = "";
		for (int i = 0; i < length; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	/**
	 * string TO List
	 * 
	 * @param str
	 *            ; 以（隔开符）隔开的一串字符串
	 * @param delimit
	 *            隔开符
	 * @return
	 */
	public static List<String> StringToList(String str, String delimit) {

		if (null != str && !"".equals(str)) {

			String[] strArray = str.split(delimit);
			List<String> strList = Arrays.asList(strArray);
			return strList;
		}

		return null;
	}

	/**
	 * 判断字符串是否为空或者空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return null == str || str.trim().isEmpty();
	}

	/**
	 * 判断数组中的字符串是否全部为空。true全部为空，false至少有一个不为空。
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmptyAll(String... str) {
		if (str == null || str.length < 1) {
			return true;
		}
		for (String one : str) {
			if (!isEmpty(one)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断数组中的字符串是否有一个为空。true有一个为空，false全部非空。
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmptyOne(String... str) {
		if (str == null || str.length < 1) {
			return true;
		}
		for (String one : str) {
			if (isEmpty(one)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 连续空白字符替换为一个空格
	 * 
	 * @param str
	 * @return
	 */
	public static String getStringOneBlank(String str) {
		if (!isEmpty(str)) {
			Pattern p = Pattern.compile("\\s+");
			Matcher m = p.matcher(str);
			String strNoBlank = m.replaceAll(" ");
			return strNoBlank;
		} else {
			return str;
		}
	}

	public static String getClassAndMethod(String clazz) {
		if (isEmpty(clazz) || !clazz.contains(".")) {
			return "";
		}
		try {
			int index = clazz.lastIndexOf(".");// 最后一个.
			String tmp = clazz.substring(0, index - 1);// 倒数第二个.
			index = tmp.lastIndexOf(".");
			return clazz.substring(index + 1);// 取类名.方法名的字符串，如“EmployeeMapper.selectByPrimaryKey”
		} catch (Exception e) {
			log.error("", e);
		}
		return "";
	}

	/**
	 * 判断字符串是否是整数
	 *
	 * @author freddy
	 * @param number
	 * @return
	 */
	public static boolean isInteger(String number) {
		boolean isNumber = false;
		if (number == null) {
			return false;
		}
		if (!StringToolsUtil.isEmpty(number.trim())) {
			isNumber = number.matches("^([1-9]\\d*)|(0)$");
		}
		return isNumber;
	}

	/**
	 * 将一个 Map 对象转化为一个 JavaBean
	 * 
	 * @param type
	 *            要转化的类型
	 * @param map
	 *            包含属性值的 map
	 * @return 转化出来的 JavaBean 对象
	 * @throws IntrospectionException
	 *             如果分析类属性失败
	 * @throws IllegalAccessException
	 *             如果实例化 JavaBean 失败
	 * @throws InstantiationException
	 *             如果实例化 JavaBean 失败
	 * @throws InvocationTargetException
	 *             如果调用属性的 setter 方法失败
	 */
	public static Object mapToBean(Class<?> type, Map<?, ?> map)
			throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
		Object obj = type.newInstance(); // 创建 JavaBean 对象

		// 给 JavaBean 对象的属性赋值
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();

			if (map.containsKey(propertyName)) {
				// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
				Object value = map.get(propertyName);

				Object[] args = new Object[1];
				args[0] = value;

				descriptor.getWriteMethod().invoke(obj, args);
			}
		}
		return obj;
	}

	/**
	 * 将一个 JavaBean 对象转化为一个 Map
	 * 
	 * @param bean
	 *            要转化的JavaBean 对象
	 * @return 转化出来的 Map 对象
	 * @throws IntrospectionException
	 *             如果分析类属性失败
	 * @throws IllegalAccessException
	 *             如果实例化 JavaBean 失败
	 * @throws InvocationTargetException
	 *             如果调用属性的 setter 方法失败
	 */
	public static Map<String, Object> beanToMap(Object bean)
			throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		Class<? extends Object> type = bean.getClass();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}

	/*
	 * 判断是否是大于0的整数
	 */
	public static boolean numIsGT0(String number) {
		if (isInteger(number) && Integer.valueOf(number.trim()) >= 0) {
			return true;
		}
		return false;
	}

	public static boolean numIsGT1(String number) {
		if (isInteger(number) && Integer.valueOf(number.trim()) > 0) {
			return true;
		}
		return false;
	}

	public static boolean numIsGT0Short(String number) {
		if (isInteger(number) && Integer.valueOf(number.trim()) >= 0
				&& Integer.valueOf(number.trim()) <= Short.MAX_VALUE) {
			return true;
		}
		return false;
	}

	public static boolean numIsGT0Byte(String number) {
		if (isInteger(number) && Integer.valueOf(number.trim()) >= 0
				&& Integer.valueOf(number.trim()) <= Byte.MAX_VALUE) {
			return true;
		}
		return false;
	}

	/**
	 * @return
	 * @Description: 根据图片地址转换为base64编码字符串
	 * @Author:
	 * @CreateTime:
	 */
	public static String getImageStr(String imgFile) {
		InputStream inputStream = null;
		byte[] data = null;
		try {
			inputStream = new FileInputStream(imgFile);
			data = new byte[inputStream.available()];
			inputStream.read(data);
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 加密
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}

	public static void downloadImg(String urlStr, String fileName) {
		try {
			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			InputStream is = con.getInputStream();
			byte[] b = new byte[2048];
			int len;
			OutputStream os = new FileOutputStream(fileName);
			while ((len = is.read(b)) != -1) {
				os.write(b, 0, len);
			}
			os.close();
			is.close();
		} catch (Exception e) {

		}
	}

	/**
	 * 在字符串中添加空字符串
	 * 
	 * @param count
	 *            添加的个数
	 * @return
	 */
	public static String getCount(int count) {
		String st = "";
		if (count < 0) {
			count = 0;
		}
		for (int i = 0; i < count; i++) {
			st = st + " ";
		}
		return st;
	}

	public static String getRandomString(int length) {
		// 随机字符串的随机字符库
		String KeyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuffer sb = new StringBuffer();
		int len = KeyString.length();
		for (int i = 0; i < length; i++) {
			sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
		}
		return sb.toString();
	}

	// public static void main(String[] args) throws Exception {
	// System.out.println("89860117750002669506865940020350345".substring(0,
	// 20));
	//
	// }

	public static List<SIMCardInfo> insert() {
		List<SIMCardInfo> list = new ArrayList<>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			File file = new File("D:/Downloads/dis/1.rsp");// 文件路径及名称
			Reader r = new FileReader(file);// 读文件
			BufferedReader br = new BufferedReader(r);// 缓冲机制
			String str = "";
			while ((str = br.readLine()) != null) {// (str=br.readLine())很重要，不能写在上面，否则死循环
				System.out.println(str);
				System.out.println("卡号：" + str.substring(0, 13));
				System.out.println("iccid:" + str.substring(14, 34));
				System.out.println("开户时间:" + str.substring(35, 43));
				System.out.println("套餐编号:" + str.substring(98, 110));
				System.out.println("套餐名称:" + str.substring(111, 121));
				System.out.println("套餐名称:" + str.substring(122, 136));
				System.out.println("套餐状态：" + str.substring(137, 139));
				System.out.println("套餐生效时间：" + str.substring(140, 148));
				System.out.println("套餐失效时间：" + str.substring(149, 157));
				System.out.println("sim卡状态：" + str.substring(158, 161));
				System.out.println("sim卡状态变化时间：" + str.substring(162, 176));
				String pac = "[{\"pkgCode\":\"" + str.substring(98, 110) + "\",\"pkgEfftTime\":\""
						+ str.substring(140, 148) + "\",\"pkgExpireTime\":\"" + str.substring(149, 157)
						+ "\",\"pkgName\":\"" + str.substring(111, 121) + "\",\"pkgStatus\":\""
						+ str.substring(137, 139) + "\",\"subSprodId\":\"" + str.substring(122, 136);
				System.out.println(pac);
				str = br.readLine();
				System.out.println(str);
				System.out.println("卡号：" + str.substring(0, 13));
				System.out.println("iccid:" + str.substring(14, 34));
				System.out.println("开户时间:" + str.substring(35, 43));
				System.out.println("套餐编号:" + str.substring(98, 110));
				System.out.println("套餐名称:" + str.substring(111, 124));
				System.out.println("套餐名称:" + str.substring(125, 139));
				System.out.println("套餐状态：" + str.substring(140, 142));
				System.out.println("套餐生效时间：" + str.substring(143, 151));
				System.out.println("套餐失效时间：" + str.substring(152, 160));
				System.out.println("sim卡状态：" + str.substring(161, 163));
				System.out.println("sim卡状态变化时间：" + str.substring(165, 179));
				String ac = "\"},{\"pkgCode\":\"" + str.substring(98, 110) + "\",\"pkgEfftTime\":\""
						+ str.substring(143, 151) + "\",\"pkgExpireTime\":\"" + str.substring(152, 160)
						+ "\",\"pkgName\":\"" + str.substring(111, 124) + "\",\"pkgStatus\":\""
						+ str.substring(140, 142) + "\",\"subSprodId\":\"" + str.substring(125, 139) + "\"}]";
				System.out.println(ac);
				SIMCardInfo sim = new SIMCardInfo();
				sim.setIccid(str.substring(14, 34));
				sim.setMsisdn(str.substring(0, 13));
				if ("测试期".equals(str.substring(161, 164))) {
					sim.setStatus(1);
				}
				if ("沉默期".equals(str.substring(161, 164))) {
					sim.setStatus(2);
				}
				if ("库存期".equals(str.substring(161, 164))) {
					sim.setStatus(3);
				}
				if ("正使用".equals(str.substring(161, 164))) {
					sim.setStatus(4);
				}
				if ("停机".equals(str.substring(161, 164))) {
					sim.setStatus(5);
				}
				if ("预约销户".equals(str.substring(161, 164))) {
					sim.setStatus(6);
				}
				sim.setStatusTime(sdf3.format(sdf2.parse(str.substring(165, 179).toString())));
				sim.setOpenTime(sdf1.format(sdf.parse(str.substring(35, 43).toString())));
				sim.setPackageInfo(pac + ac);
				sim.setOperator(Long.parseLong("2"));
				list.add(sim);
				System.out.println(sim);
			}
			br.close();// 关闭流
			r.close();// 关闭流

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static <T> T setNullValue(T source)
			throws IllegalArgumentException, IllegalAccessException, SecurityException {
		Field[] fields = source.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.getGenericType().toString().equals("class java.lang.String")) {
				field.setAccessible(true);
				Object obj = field.get(source);
				if (obj != null && obj.equals("")) {
					field.set(source, null);
				} else if (obj != null) {
					String str = obj.toString();
					field.set(source, str.trim());
				}
			}
		}
		return source;
	}

	public static <T> T setNullValueAndReplace(T source)
			throws IllegalArgumentException, IllegalAccessException, SecurityException {
		Field[] fields = source.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.getGenericType().toString().equals("class java.lang.String")) {
				field.setAccessible(true);
				Object obj = field.get(source);
				if (obj != null && obj.equals("")) {
					field.set(source, null);
				} else if (obj != null) {
					String str = obj.toString();
					str = escapeSql(str);
					field.set(source,
							str.replace("\\", "\\" + "\\").replace("(", "\\(").replace(")", "\\)").replace("%", "\\%")
									.replace("[", "\\[").replace("]", "\\]").replace("|", "\\|").replace("$", "\\$")
									.replace("+", "\\+").trim());
				}
			}
		}
		return source;
	}

	private static String escapeSql(String str) {
		if (str == null) {
			return null;
		}
		return StringUtils.replace(str, "'", "''");
	}

	public static String[] concatArray(String[] a, String[] b) {
		String[] c = new String[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}

	public static List<Long> splitStrId(String ids) {
		String charStr = ",";
		if (ids.lastIndexOf(charStr) == -1) {
			return Arrays.asList(Long.valueOf(ids));
		}
		String[] split = ids.split(charStr);
		Long[] str2 = new Long[split.length];
		for (int i = 0; i < split.length; i++) {
			str2[i] = Long.valueOf(split[i]);
		}
		return Arrays.asList(str2);
	}
}
