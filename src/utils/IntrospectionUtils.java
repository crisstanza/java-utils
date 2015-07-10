package utils;

import java.lang.reflect.Field;

/**
 * @author Cris Stanza, 10-Jul-2015
 */
public class IntrospectionUtils {

	public static String toString(Object obj) {
		try {
			StringBuilder sb = new StringBuilder();
			Class<?> clazz = obj.getClass();
			sb.append(clazz.getName());
			sb.append(" [");
			for (Field field : clazz.getDeclaredFields()) {
				field.setAccessible(true);
				sb.append(" ").append(field.getName()).append("=").append(field.get(obj));
				field.setAccessible(false);
			}
			sb.append(" ]");
			return sb.toString();
		} catch (Exception exc) {
			throw new RuntimeException(exc);
		}
	}

}
