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

}
