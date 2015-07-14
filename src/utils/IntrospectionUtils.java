package utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cris Stanza, 10-Jul-2015.
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

	public static Object setRandomValues(Object obj) {
		try {
			Class<?> clazz = obj.getClass();
			for (Field field : clazz.getDeclaredFields()) {
				field.setAccessible(true);
				if (!Modifier.isFinal(field.getModifiers())) {
					field.set(obj, randomValue(field));
				}
				field.setAccessible(false);
			}
			return obj;
		} catch (Exception exc) {
			throw new RuntimeException(exc);
		}
	}

	private static Object randomValue(Field field) throws Exception {
		Class<?> type = field.getType();
		if (String.class.getName().equals(type.getName())) {
			return RandomUtils.randomCaptalizedString();
		} else if (Integer.class.getName().equals(type.getName())) {
			return RandomUtils.randomInt();
		} else if (Double.class.getName().equals(type.getName())) {
			return RandomUtils.randomDouble();
		} else if (List.class.getName().equals(type.getName())) {
			return randomValue(new ArrayList<>(), field);
		} else {
			return null;
		}
	}

	private static Object randomValue(ArrayList<Object> list, Field field) throws Exception {
		ParameterizedType genericType = (ParameterizedType) field.getGenericType();
		Type[] genericTypeArguments = genericType.getActualTypeArguments();
		Class<?> genericClass = (Class<?>) genericTypeArguments[0];
		Object newObject = genericClass.newInstance();
		list.add(setRandomValues(newObject));
		return list;
	}

}
