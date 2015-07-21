package utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;

/**
 * @author Cris Stanza, 10-Jul-2015.
 */
public final class IntrospectionUtils {

	private static final DefaultInterfaceImplResolver defaultImplResolver = new DefaultInterfaceImplResolver();

	private static final String PRIMITIVE_DOUBLE = "double";
	private static final String PRIMITIVE_FLOAT = "float";
	private static final String PRIMITIVE_INT = "int";
	private static final String PRIMITIVE_LONG = "long";

	private static final int MIN_RANDOM_LIST_SIZE = 2;
	private static final int MAX_RANDOM_LIST_SIZE = 4;

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private IntrospectionUtils() {
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Extrai os valores de todas as propriedades deste objeto, recursivamente.
	 * 
	 * @param obj
	 *            Um objeto qualquer, de qualquer tipo.
	 * @return Retorna uma String informando nome, valor e tipo (para listas) de todas as propriedades do objeto
	 *         <code>obj</code>.
	 */
	public static String toString(Object obj) {
		boolean omitClassName = false;
		return toString(obj, omitClassName);
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private static String toString(Object obj, boolean omitClassName) {
		try {
			StringBuilder sb = new StringBuilder();
			Class<?> clazz = obj.getClass();
			sb.append(omitClassName ? "" : clazz.getName());
			sb.append("[");
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				Class<?> type = field.getType();
				field.setAccessible(true);
				List<String> modifiers = modifiers(field);
				if (!modifiers.isEmpty()) {
					sb.append("(").append(StringUtils.join(modifiers)).append(")");
				}
				sb.append(field.getName());
				sb.append("=");
				if (canBeConsideredPrimitive(type)) {
					sb.append(field.get(obj));
				} else if (isJavaUtilList(type)) {
					sb.append(type.getName());
					Class<?> genericType = genericType(field);
					if (genericType != null) {
						sb.append("<").append(genericType.getName()).append(">");
					}
					sb.append("[");
					List<?> list = (List<?>) field.get(obj);
					if (list != null) {
						for (int j = 0; j < list.size(); j++) {
							Object object = list.get(j);
							sb.append(toString(object, true));
							sb.append(j < list.size() - 1 ? ", " : "");
						}
					}
					sb.append("]");
				} else {
					sb.append(toString(field.get(obj)));
				}
				sb.append(i < fields.length - 1 ? " " : "");
				field.setAccessible(false);
			}
			sb.append("]");
			return sb.toString();
		} catch (Exception exc) {
			throw new RuntimeException(exc);
		}
	}

	private static List<String> modifiers(Field field) {
		List<String> modifiers = new ArrayList<String>();
		if (Modifier.isFinal(field.getModifiers())) {
			modifiers.add("final");
		}
		if (Modifier.isStatic(field.getModifiers())) {
			modifiers.add("static");
		}
		return modifiers;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static boolean isJavaLangDoubleOrPrimitive(Class<?> type) {
		return java.lang.Double.class.getName().equals(type.getName()) || PRIMITIVE_DOUBLE.equals(type.getName());
	}

	public static boolean isJavaLangFloatOrPrimitive(Class<?> type) {
		return java.lang.Float.class.getName().equals(type.getName()) || PRIMITIVE_FLOAT.equals(type.getName());
	}

	public static boolean isJavaLangIntegerOrPrimitive(Class<?> type) {
		return java.lang.Integer.class.getName().equals(type.getName()) || PRIMITIVE_INT.equals(type.getName());
	}

	public static boolean isJavaLangLongOrPrimitive(Class<?> type) {
		return java.lang.Long.class.getName().equals(type.getName()) || PRIMITIVE_LONG.equals(type.getName());
	}

	public static boolean isJavaLangString(Class<?> type) {
		return java.lang.String.class.getName().equals(type.getName());
	}

	public static boolean canBeConsideredPrimitive(Class<?> type) {
		return isJavaLangDoubleOrPrimitive(type) || isJavaLangFloatOrPrimitive(type) || isJavaLangIntegerOrPrimitive(type) || isJavaLangLongOrPrimitive(type) || isJavaLangString(type) || isDate(type);
	}

	private static boolean isDate(Class<?> type) {
		return isJavaUtilDate(type) || isJavaxXmlDatatypeXMLGregorianCalendar(type);
	}

	public static boolean isJavaxXmlDatatypeXMLGregorianCalendar(Class<?> type) {
		return javax.xml.datatype.XMLGregorianCalendar.class.getName().equals(type.getName());
	}

	public static boolean isJavaUtilList(Class<?> type) {
		return java.util.List.class.getName().equals(type.getName());
	}

	public static boolean isJavaUtilDate(Class<?> type) {
		return java.util.Date.class.getName().equals(type.getName());
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Seta valores aleat&oacute;rios para todas as propriedades deste objeto, recursivamente.
	 * 
	 * @param obj
	 *            Um objeto qualquer, de qualquer tipo.
	 * @return Retorna o mesmo objeto com todas as suas propriedades populadas com valores aleat&oacute;rios.
	 */
	public static Object setRandomValues(Object obj) {
		return setRandomValues(obj, null);
	}

	/**
	 * Seta valores aleat&oacute;rios para todas as propriedades deste objeto, recursivamente, utilizando um resolvedor de
	 * implementa&ccedil;&otilde;es personalizado.
	 * 
	 * @param obj
	 *            Um objeto qualquer, de qualquer tipo.
	 * @param customImplResolver
	 *            Um resolvedor personalizado de implementa&ccedil;&otilde;es de interfaces.
	 * @return Retorna o mesmo objeto com todas as suas propriedades populadas com valores aleat&oacute;rios, sendo que as
	 *         interfaces encontradas foram populadas com classes concretas resolvidas atrav&eacute;s do
	 *         <code>customImplResolver</code>.
	 */
	public static Object setRandomValues(Object obj, InterfaceImplResolver customImplResolver) {
		try {
			Class<?> clazz = obj.getClass();
			for (Field field : clazz.getDeclaredFields()) {
				field.setAccessible(true);
				if (!Modifier.isFinal(field.getModifiers())) {
					field.set(obj, randomValue(field, customImplResolver));
				}
				field.setAccessible(false);
			}
			return obj;
		} catch (Exception exc) {
			exc.printStackTrace();
			throw new RuntimeException(exc);
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	private static Object randomValue(Field field, InterfaceImplResolver customImplResolver) throws Exception {
		Class<?> type = field.getType();
		if (isJavaLangString(type)) {
			return RandomUtils.randomCaptalizedString();
		} else if (isJavaLangDoubleOrPrimitive(type)) {
			return RandomUtils.randomDouble();
		} else if (isJavaLangFloatOrPrimitive(type)) {
			return RandomUtils.randomFloat();
		} else if (isJavaLangIntegerOrPrimitive(type)) {
			return RandomUtils.randomInt();
		} else if (isJavaLangLongOrPrimitive(type)) {
			return RandomUtils.randomLong();
		} else {
			if (type.isInterface()) {
				Class<?> implClass = defaultImplResolver.resolve(type, customImplResolver);
				Object newObject = implClass.newInstance();
				if (isJavaUtilList(type)) {
					return randomValue((List<Object>) newObject, field, customImplResolver);
				} else {
					return setRandomValues(implClass, customImplResolver);
				}
			} else if (Modifier.isAbstract(type.getModifiers())) {
				Class<?> implClass = defaultImplResolver.resolve(type, customImplResolver);
				if (isJavaxXmlDatatypeXMLGregorianCalendar(type)) {
					return DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar());
				} else {
					return setRandomValues(implClass, customImplResolver);
				}
			} else if (isJavaUtilDate(type)) {
				return type.newInstance();
			} else {
				Object newObject = type.newInstance();
				return setRandomValues(newObject, customImplResolver);
			}
		}
	}

	private static Object randomValue(List<Object> list, Field field, InterfaceImplResolver customImplResolver) throws Exception {
		Class<?> genericClass = genericType(field);
		int max = RandomUtils.randomInt(MIN_RANDOM_LIST_SIZE, MAX_RANDOM_LIST_SIZE);
		for (int i = 0; i < max; i++) {
			Object newObject = genericClass.newInstance();
			list.add(setRandomValues(newObject, customImplResolver));
		}
		return list;
	}

	private static Class<?> genericType(Field field) {
		ParameterizedType genericType = (ParameterizedType) field.getGenericType();
		Type[] genericTypeArguments = genericType.getActualTypeArguments();
		return genericTypeArguments.length > 0 ? (Class<?>) genericTypeArguments[0] : null;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Resolvedor de implementa&ccedil;&atilde;o para as classes/interfaces nativas do JDK.
	 * 
	 * Ao resolver um tipo, este resolvedor sempre tenta primeiro resolv&ecirc;-lo atrav&eacute;s do resolvedor personalizado
	 * informado no par&acirc;metro <code>customImplResolver</code> (ou seja, o comportamento deste resolvedor por ser
	 * sobrescrito).
	 * 
	 * @see br.com.agrolog.integracao.util.IntrospectionUtils
	 * @see br.com.agrolog.integracao.util.IntrospectionUtils.InterfaceImplResolver
	 * 
	 * @author cneves, 15-Jul-2015.
	 */
	private static class DefaultInterfaceImplResolver {

		private Class<?> resolve(Class<?> interfaceType, InterfaceImplResolver customImplResolver) {
			Class<?> implClass = null;
			if (customImplResolver != null) {
				implClass = customImplResolver.resolve(interfaceType);
			}
			if (implClass == null) {
				if (isJavaUtilList(interfaceType)) {
					implClass = ArrayList.class;
				}
			}
			return implClass;
		}

	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Interface a ser implementada para defini&ccedil;&atilde;o da classe concreta que implementa uma determinada interface.
	 * 
	 * @author cneves, 15-Jul-2015.
	 */
	public static interface InterfaceImplResolver {

		/**
		 * Este m&eacute;todo deve retornar uma classe que implementa a interface definida no par&acirc;metro <code>type</code>.
		 * 
		 * @param interfaceType
		 *            Deve representar um tipo <code>interface</code>.
		 * @return Uma classe concreta compat&iacute;vel com a interface informada no par&acirc;metro <code>type</code>.
		 */
		public Class<?> resolve(Class<?> interfaceType);

	}

}
