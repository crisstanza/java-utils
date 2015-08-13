package utils;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

/**
 * @author cneves, 12-Ago-2015
 */
public final class JsonUtil {

	private JsonUtil() {
	}

	public static JsonObject getJsonObject(String jsonAsString) {
		JsonReader reader = Json.createReader(new StringReader(jsonAsString));
		JsonObject jsonObject = reader.readObject();
		Closer.close(reader);
		return jsonObject;
	}

	/**
	 * Transforma um valor Json em um objeto Java.<br />
	 * <br />
	 * Este m&eacute;todo suporta apenas valores que possam ser considerados como tipo primitivo, ou seja, strings,
	 * n&uacute;meros, booleanos ou null. O m&eacute;todo N&Atilde;O suporta valores Json que sejam do tipo ARRAY ou OBJECT.
	 * 
	 * @param value
	 *            Um objeto <code>JsonValue </code>
	 * 
	 * @return Um objeto Java com o mesmo valor do objeto Json recebido como par&acirc;metro.
	 * 
	 * @author cneves, 12-Ago-2015
	 */
	public static Object getObject(JsonValue jsonValue) {
		if (jsonValue.getValueType() == ValueType.STRING) {
			return getString((JsonString) jsonValue);
		} else if (jsonValue.getValueType() == ValueType.NUMBER) {
			return bigDecimalValue((JsonNumber) jsonValue);
		} else if (jsonValue.getValueType() == ValueType.TRUE) {
			return Boolean.TRUE;
		} else if (jsonValue.getValueType() == ValueType.FALSE) {
			return Boolean.FALSE;
		} else if (jsonValue.getValueType() == ValueType.NULL) {
			return null;
		} else {
			throw new IllegalArgumentException("Tipo inexperado: " + jsonValue.getValueType().name());
		}
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////

	public static Double doubleValue(JsonNumber jsonNumber) {
		return jsonNumber == null ? null : jsonNumber.doubleValue();
	}

	public static Integer intValue(JsonNumber jsonNumber) {
		return jsonNumber == null ? null : jsonNumber.intValue();
	}

	public static String getString(JsonString jsonString) {
		return jsonString == null ? null : jsonString.getString();
	}

	public static BigDecimal bigDecimalValue(JsonNumber jsonNumber) {
		return jsonNumber == null ? null : jsonNumber.bigDecimalValue();
	}

	public static Boolean to_Boolean(JsonValue jsonValue) {
		return jsonValue == null ? null : jsonValue.toString().equals(JsonValue.TRUE);
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////

	public static Double[][][] to_Double_array_array_array(JsonArray jsonArray) {
		if (jsonArray == null) {
			return null;
		}
		int size = jsonArray.size();
		Double[][][] array = new Double[size][][];
		for (int i = 0; i < size; i++) {
			array[i] = to_Double_array_array(jsonArray.getJsonArray(i));
		}
		return array;
	}

	private static Double[][] to_Double_array_array(JsonArray jsonArray) {
		int size = jsonArray.size();
		Double[][] array = new Double[size][];
		for (int i = 0; i < size; i++) {
			array[i] = to_Double_array(jsonArray.getJsonArray(i));
		}
		return array;
	}

	private static Double[] to_Double_array(JsonArray jsonArray) {
		int size = jsonArray.size();
		Double[] array = new Double[size];
		for (int i = 0; i < size; i++) {
			array[i] = JsonUtil.doubleValue(jsonArray.getJsonNumber(i));
		}
		return array;
	}

	public static Map<String, Object> to_MapStringObject(JsonObject jsonObject) {
		if (jsonObject == null) {
			return null;
		}
		Map<String, Object> json = new HashMap<String, Object>();
		Set<String> keys = jsonObject.keySet();
		for (String key : keys) {
			JsonValue value = jsonObject.get(key);
			json.put(key, JsonUtil.getObject(value));
		}
		return json;
	}

}
