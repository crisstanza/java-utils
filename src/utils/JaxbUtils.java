package utils;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;

/**
 * Classe auxiliar para executar opera&ccedil;&otilde;es de serializa&ccedil;&atilde;o e de-serializa&ccedil;&atilde;o de
 * objetos.
 *
 * @author Cris Stanza, 06-Jul-2015
 */
public final class JaxbUtils {

	public static final boolean SILENT = true;

	private JaxbUtils() {
	}

	/**
	 * Transforma um objeto em um XML.
	 * 
	 * @param obj
	 *            Objeto a ser serializado.
	 * @return Uma <code>String</code> representando o XML do objeto serializado.
	 * @throws Exception
	 *             Lança exce&ccedil;&atilde;o no caso de qualquer tipo de erro.
	 */
	public static String marshal(Object obj) throws Exception {
		JAXBContext jaxb = JAXBContext.newInstance(obj.getClass());
		Marshaller marshaller = jaxb.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		StringWriter result = new StringWriter();
		marshaller.marshal(obj, result);
		return result.toString();
	}

	/**
	 * Transforma um XML em um objeto. Este m&eacute;todo lança exce&ccedil;&atilde;o no caso de qualquer tipo de erro.
	 * 
	 * @param xml
	 *            A <code>String</code> representando o XML do objeto a ser de-serializado.
	 * @param clazz
	 *            A classe do objeto representado no XML.
	 * @return
	 * @throws Exception
	 *             Lança exce&ccedil;&atilde;o no caso de qualquer tipo de erro.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T unmarshal(String xml, Class<T> clazz) throws Exception {
		JAXBContext jaxb = JAXBContext.newInstance(clazz);
		Unmarshaller unmarshaller = jaxb.createUnmarshaller();
		return (T) unmarshaller.unmarshal(new StringReader(xml));
	}

	/**
	 * Transforma um XML em um objeto com a op&ccedil;&atilde;o de n&atilde;o lançar exce&ccedil;&atilde;o no caso erros do tipo
	 * <code>javax.xml.bind.UnmarshalException</code>.
	 * 
	 * @param xml
	 *            A <code>String</code> representando o XML do objeto a ser de-serializado.
	 * @param clazz
	 *            A classe do objeto representado no XML.
	 * @param silent
	 *            Se for passado o valor <code>true</code>, o m&eacute;todo ter&aacute; o comportamento "silencioso" para o caso
	 *            de exce&ccedil;&otilde;es do tipo <code>javax.xml.bind.UnmarshalException</code> e retornar&aacute; o valor
	 *            <code>null</code>. Caso ocorra algum outro tipo de exce&ccedil;&atilde;o, a mesma ser&aacute; lançada.
	 * @return
	 * @throws Exception
	 *             Lança exce&ccedil;&atilde;o apenas se o par&acirc;metro de entrada <code>silent</code> tiver o valor
	 *             <code>false</code> ou se a exce&ccedil;&atilde;o ocorrida n&atilde;o for do tipo
	 *             <code>javax.xml.bind.UnmarshalException</code>.
	 */
	public static <T> T unmarshal(String xml, Class<T> clazz, boolean silent) throws Exception {
		try {
			return JaxbUtils.unmarshal(xml, clazz);
		} catch (UnmarshalException exc) {
			if (silent) {
				return null;
			} else {
				throw exc;
			}
		}
	}

}
