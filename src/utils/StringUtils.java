package utils;

import java.nio.charset.Charset;

/**
 * @author Cris Stanza, 13-Jul-2015
 */
public final class StringUtils {

	private static final String DEFAULT_ENCODING = "ISO-8859-1";
	private static final String MSDOS_ENCODING = "CP850";

	private static boolean windows = System.getProperty("os.name").startsWith("Windows");

	private StringUtils() {
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

}
