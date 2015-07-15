package utils;

import java.util.Random;

/**
 * Classe auxiliar para gerar dados aleat&oacute;rios.
 *
 * @author Cris Stanza, 14-Jul-2015
 */
public final class RandomUtils {

	private static final Random random = new Random();

	private static final int MIN_RANDOM_CAPTALIZED_STRING_SIZE = 4;
	private static final int MAX_RANDOM_CAPTALIZED_STRING_SIZE = 10;

	private static final int ONE = 1;

	private static final int MIN_LOWER_CHAR = 97;
	private static final int MAX_LOWER_CHAR = 122;

	private static final int MIN_UPPER_CHAR = 65;
	private static final int MAX_UPPER_CHAR = 90;

	private RandomUtils() {
	}

	public static String randomCaptalizedString() {
		int size = randomInt(MIN_RANDOM_CAPTALIZED_STRING_SIZE, MAX_RANDOM_CAPTALIZED_STRING_SIZE);
		StringBuilder sb = new StringBuilder();
		sb.append(randomUpperChar());
		for (int i = 0; i < size; i++) {
			sb.append(randomLowerChar());
		}
		return sb.toString();
	}

	public static char randomUpperChar() {
		return (char) randomInt(MIN_UPPER_CHAR, MAX_UPPER_CHAR);
	}

	public static char randomLowerChar() {
		return (char) randomInt(MIN_LOWER_CHAR, MAX_LOWER_CHAR);
	}

	public static int randomInt(int min, int max) {
		return random.nextInt(max - min + ONE) + min;
	}

	public static int randomInt() {
		return random.nextInt();
	}

	public static double randomDouble() {
		return random.nextDouble();
	}

	public static float randomFloat() {
		return random.nextFloat();
	}

	public static long randomLong() {
		return random.nextLong();
	}

}
