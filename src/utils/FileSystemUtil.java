package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Cris Stanza, 13-Ago-2015
 */
public final class FileSystemUtil {

	private FileSystemUtil() {
	}

	public static final String read(File path) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(path));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (IOException exc) {
			RuntimeException re = new RuntimeException(exc);
			re.setStackTrace(exc.getStackTrace());
			throw re;
		} finally {
			Closer.close(reader);
		}
	}

}
