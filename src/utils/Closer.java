package utils;

import java.io.Closeable;

/**
 * @author Cris Stanza, 13-Ago-2015
 */
public final class Closer {

	private Closer() {
	}

	public static void close(Closeable closeMe) {
		try {
			if (closeMe != null) {
				closeMe.close();
			}
		} catch (Exception exc) {
			// nada a fazer!
		}
	}

}
