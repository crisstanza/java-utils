package utils;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author Cris Stanza, 13-Jul-2015
 */
public final class StringUtils {

	private static final String DEFAULT_ENCODING = "ISO-8859-1";
	private static final String MSDOS_ENCODING = "CP850";

	private static final String SPACE = " ";
	private static final int TAB_SIZE = 4;

	private static final char COMMA = ',';

	private static boolean windows = System.getProperty("os.name").startsWith("Windows");

	private StringUtils() {
	}

	public static String join(List<?> list) {
		StringBuilder sb = new StringBuilder();
		if (list != null) {
			int size = list.size();
			if (size > 0) {
				sb.append(list.get(0));
			}
			for (int i = 1; i < size; i++) {
				sb.append(COMMA).append(list.get(i));
			}
		}
		return sb.toString();
	}

	public static String fixCharSet(String str) {
		try {
			if (windows) {
				return new String(str.getBytes(DEFAULT_ENCODING), Charset.forName(MSDOS_ENCODING));
			} else {
				return str;
			}
		} catch (Exception exc) {
			throw new RuntimeException(exc);
		}
	}

	public static String fill(int level) {
		StringBuilder sb = new StringBuilder();
		int spaces = level * TAB_SIZE;
		for (int i = 0; i < spaces; i++) {
			sb.append(SPACE);
		}
		return sb.toString();
	}

	/**
	 * Corrige o separador decimal de uma <code>String</code>. Exemplo:<br />
	 * - entrada: -97,06 32,83, -97,06 32,83<br />
	 * - sa&iacute;da: -97.06 32.83, -97.06 32.83
	 * 
	 * @param str
	 *            <code>String</code> utilizando v&iacute;rgula como separador decimal.
	 * @return <code>String</code> utilizando ponto como separador decimal.
	 */
	public static String fixComma(String str) {
		int strLength = str.length();
		char[] result = new char[strLength];
		int iComma = 0;
		for (int i = 0; i < strLength; i++) {
			char charAt = str.charAt(i);
			if (charAt == ',') {
				result[i] = iComma % 3 == 2 ? ',' : '.';
				iComma++;
			} else {
				result[i] = charAt;
			}
		}
		return new String(result);
	}

	public static String fixQuote(String str) {
		return str.replace('\'', '"');
	}

	/**
	 * Um <code>java.lang.StringBuilder</code> que sempre adiciona uma quebra de linha a cada chamada ao seu método
	 * <code>.append()</code>.
	 * 
	 * @author cneves, 24-Jul-2015
	 */
	public static class StringLinesBuilder {
		private final StringBuilder sb = new StringBuilder();

		public void append(Object obj) {
			sb.append(obj).append(System.lineSeparator());
		}

		public String toString() {
			return sb.toString().trim();
		}

		public int length() {
			return sb.length();
		}

	}

}
