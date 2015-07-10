package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipInputStream;

/**
 * Classe auxiliar para executar requisi&ccedil;&otilde;es HTTP.
 *
 * @author Cris Stanza, 06-Jul-2015
 */
public final class UrlUtils {

	private final static String METHOD_POST = "POST";

	private static final String ENCODING_UTF_8 = "UTF-8";

	private static final String CONTENT_TYPE_APPLICATION_XML = "application/xml";
	private static final String CONTENT_TYPE = "Content-Type";

	private UrlUtils() {
	}

	public static String post(URL url, String body) throws Exception {
		HttpURLConnection con = null;
		try {
			con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod(METHOD_POST);
			con.setRequestProperty(CONTENT_TYPE, CONTENT_TYPE_APPLICATION_XML);
			OutputStream destiny = con.getOutputStream();
			destiny.write(body.getBytes());
			destiny.flush();
			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				StringBuilder result = new StringBuilder();
				ZipInputStream zip = new ZipInputStream(con.getInputStream());
				Object nextEntryResult = zip.getNextEntry();
				if (nextEntryResult == null) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), ENCODING_UTF_8));
					String aux = "";
					while ((aux = reader.readLine()) != null) {
						result.append(aux);
					}
					result.replace(0, 8, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
					zip.close();
				} else {
					BufferedReader readerZip = new BufferedReader(new InputStreamReader(zip, ENCODING_UTF_8));
					String auxZip = "";
					while ((auxZip = readerZip.readLine()) != null) {
						result.append(auxZip);
					}
					zip.close();
				}
				return result.toString();
			} else {
				throw new Exception("[" + con.getResponseMessage() + "] " + con.getResponseMessage());
			}
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
	}

}
