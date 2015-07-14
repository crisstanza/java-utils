package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author Cris Stanza, 13-Jul-2015
 */
public final class DateUtils {

	private static final String DD_MM_YYYY = "dd/MM/yyyy";
	private static final String DD_MM_YYYY__HHMMSS = "dd/MM/yyyy HH:mm:ss";

	private DateUtils() {
	}

	public static XMLGregorianCalendar xmlData() throws Exception {
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar());
	}

	public static XMLGregorianCalendar xmlData(String dataHora) throws Exception {
		DateFormat df = new SimpleDateFormat(DD_MM_YYYY__HHMMSS);
		Date date = df.parse(dataHora);
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
	}

	public static XMLGregorianCalendar xmlData(Date date) throws Exception {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
	}

	public static Date data(String data) throws Exception {
		DateFormat df = new SimpleDateFormat(DD_MM_YYYY);
		Date date = df.parse(data);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTime();
	}

	public static String data(Date data) throws Exception {
		DateFormat df = new SimpleDateFormat(DD_MM_YYYY);
		return df.format(data);
	}

	public static String dataHora() throws Exception {
		DateFormat df = new SimpleDateFormat(DD_MM_YYYY__HHMMSS);
		return df.format(new Date());
	}

	public static String dataHora(XMLGregorianCalendar data) {
		DateFormat df = new SimpleDateFormat(DD_MM_YYYY__HHMMSS);
		return df.format(data.toGregorianCalendar().getTime());
	}

}
