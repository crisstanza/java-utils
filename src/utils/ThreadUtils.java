package utils;

/**
 * Classe auxiliar para executar opera&ccedil;&otilde;es de "sleep".
 *
 * @author Cris Stanza, 19-Jun-2015
 */
public final class ThreadUtils {

	/**
	 * Retorna um objeto capaz de efetuar um "sleep" durante <code>intervaloInSeconds</code> segundos.<br />
	 * <br />
	 * Exemplo de uso (sleep de 1 minuto):<br />
	 * &nbsp; - <code>ThreadUtils.sleep(60).seconds()</code><br />
	 *
	 * @param intervaloInSeconds
	 *            N&uacute;mero em segundos para o "sleep".
	 * @return Um objeto que efetuar&aacute; um "sleep" assim que o seu m&acute;todo <code>seconds()</code> for chamado.
	 */
	public static Sleeper sleep(int intervaloInSeconds) {
		return new Sleeper(intervaloInSeconds);
	}

	private ThreadUtils() {
	}

	public static final class Sleeper {

		final int intervaloInSeconds;
		final int intervaloInMiliSeconds;

		private Sleeper(int intervaloInSeconds) {
			this.intervaloInSeconds = intervaloInSeconds;
			this.intervaloInMiliSeconds = 1000 * intervaloInSeconds;
		}

		public void seconds() {
			try {
				Thread.sleep(intervaloInMiliSeconds);
			} catch (InterruptedException exc) {
				// nada a fazer!
			}
		}

	}

}
