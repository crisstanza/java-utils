package utils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.ResourceBundle;

/**
 * @author Cris Stanza, 19-Jun-2015
 */
public final class Config {

	private static final String LIST_SEPARATOR = "\\s*,\\s*";
	private static final String PAIR_SEPARATOR = "\\s*/\\s*";

	private static final Config instance = new Config();

	// se o arquivo properties estiver no mesmo pacote desta classe:
	// private final ResourceBundle rb = ResourceBundle.getBundle(this.getClass().getPackage().getName() +
	// ".config");
	//
	// se o arquivo properties estiver na ra\u00a1z do classpath:
	private final ResourceBundle rb = ResourceBundle.getBundle("config");

	private Config() {
	}

	// //////////////////////////////////////////////////////////////////////////

	public static final Config getInstance() {
		return instance;
	}

	// //////////////////////////////////////////////////////////////////////////

	private String getString(final String name) {
		return rb.getString(name);
	}

	private int getInt(final String name) {
		return NumberUtils.parseInt(getString(name));
	}

	private long getLong(final String name) {
		return NumberUtils.parseLong(getString(name));
	}

	private boolean getBool(final String name) {
		return NumberUtils.parseBool(getString(name));
	}

	private String[] getStringArray(final String name) {
		return parseStringArray(getString(name));
	}

	private String[] parseStringArray(final String value) {
		return value == null ? null : value.split(LIST_SEPARATOR);
	}

	private long[] getLongArray(final String name) {
		return NumberUtils.parseLongArray(getStringArray(name));
	}

	private int[] getIntArray(final String name) {
		return NumberUtils.parseIntArray(getStringArray(name));
	}

	// //////////////////////////////////////////////////////////////////////////

	public final int getSomeInt() {
		return getInt("SOME_INT");
	}

	public final String getSomeString() {
		return getString("SOME_STRING");
	}

	public final long getSomeLong() {
		return getLong("SOME_LONG");
	}

	public final boolean getSomeBool() {
		return getBool("SOME_BOOL");
	}

	public final long[] getLongArray() {
		return getLongArray("LONG_ARRAY");
	}

	public final int[] getIntArray() {
		return getIntArray("INT_ARRAY");
	}

	// //////////////////////////////////////////////////////////////////////////

	public boolean containsIntA(Integer intA) {
		for (IntAIntB intAIntB : getIntAIntB()) {
			if (intAIntB.intA.equals(intA)) {
				return true;
			}
		}
		return false;
	}

	public IntAIntB getIntA(Integer intB) {
		for (IntAIntB intAIntB : getIntAIntB()) {
			if (intAIntB.intB.equals(intB)) {
				return intAIntB;
			}
		}
		return null;
	}

	public final IntAIntB[] getIntAIntB() {
		return getIntAIntBArray("INT_A/INT_B");
	}

	private IntAIntB[] getIntAIntBArray(final String name) {
		return parseIntAIntBArray(getStringArray(name));
	}

	private IntAIntB[] parseIntAIntBArray(String[] stringArray) {
		if (stringArray == null) {
			return null;
		} else {
			IntAIntB[] intAIntBArray = new IntAIntB[stringArray.length];
			for (int i = 0; i < stringArray.length; i++) {
				intAIntBArray[i] = parseIntAIntB(stringArray[i]);
			}
			return intAIntBArray;
		}
	}

	private IntAIntB parseIntAIntB(String intAIntB) {
		return new IntAIntB(intAIntB.split(PAIR_SEPARATOR));
	}

	// //////////////////////////////////////////////////////////////////////////

	public Int1Int2 getInt1Int2(Integer int1) {
		for (Int1Int2 int1Int2 : getInt1Int2()) {
			if (int1.longValue() <= int1Int2.int1.longValue()) {
				return int1Int2;
			}
		}
		return null;
	}

	public final Int1Int2[] getInt1Int2() {
		return Int1Int2.sort(getInt1Int2Array("INT_1/INT_2"));
	}

	private Int1Int2[] getInt1Int2Array(final String name) {
		return parseInt1Int2Array(getStringArray(name));
	}

	private Int1Int2[] parseInt1Int2Array(String[] stringArray) {
		if (stringArray == null) {
			return null;
		} else {
			Int1Int2[] int1Int2Array = new Int1Int2[stringArray.length];
			for (int i = 0; i < stringArray.length; i++) {
				int1Int2Array[i] = parseInt1Int2(stringArray[i]);
			}
			return int1Int2Array;
		}
	}

	private Int1Int2 parseInt1Int2(String int1Int2) {
		return new Int1Int2(int1Int2.split(PAIR_SEPARATOR));
	}

	// //////////////////////////////////////////////////////////////////////////

	public static class IntAIntB {
		public Integer intA, intB;

		public IntAIntB(String[] intAintB) {
			this.intA = NumberUtils.parseInt(intAintB[0]);
			this.intB = NumberUtils.parseInt(intAintB[1]);
		}

		public String toString() {
			return IntrospectionUtils.toString(this);
		}

	}

	public static class Int1Int2 {
		public Integer int1, int2;

		public Int1Int2(String[] int1Int2) {
			this.int1 = NumberUtils.parseInt(int1Int2[0]);
			this.int2 = NumberUtils.parseInt(int1Int2[1]);
		}

		public static Int1Int2[] sort(Int1Int2[] int1Int2) {
			Arrays.sort(int1Int2, new Comparator<Int1Int2>() {
				@Override
				public int compare(Int1Int2 o1, Int1Int2 o2) {
					return o1.int1.compareTo(o2.int1);
				}
			});
			return int1Int2;
		}

		public String toString() {
			return IntrospectionUtils.toString(this);
		}

	}

}
