package src.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

/* Abstract config reader for reading the configuration files */
public abstract class ConfigReader {

	private static BufferedReader input;

	/*
	 * Parse the file at the given location using the given actions to process
	 * each entry.
	 * @param configActions The actions to perform for each entry.
	 * @param filename The location of the file to parse.
	 * @throws ConfigReaderException Thrown for all errors.
	 */
	public static void parseFile(ConfigEntryAction[] configActions,
			String filename) throws ConfigReaderException {
		final HashMap<String, ConfigEntryAction> actions = new HashMap<String, ConfigEntryAction>(
				configActions.length);

		for (int i = 0; i < configActions.length; i++) {
			ConfigEntryAction action = configActions[i];
			actions.put(action.getActionId(), action);
		}
		try {
			input = new BufferedReader(new FileReader(filename));

			String strLine;
			while ((strLine = input.readLine()) != null) {
				if (strLine.length() == 0 || strLine.startsWith("#")) {
					continue;
				}
				String[] params = strLine.split("=", 2);
				ConfigEntryAction action = actions.get(params[0]);

				if (action == null) {
					System.out.println("Unknown config entry:" + params[0]);
				}

				action.performAction(params[1]);
			}

		} catch (Exception e) {
			throw new ConfigReaderException(e);
		}
	}

	/*
	 * An abstract action to perform for a config entry.
	 */
	public static abstract class ConfigEntryAction {
		
		private final String actionId;
		
		public String getActionId() {
			return actionId;
		}

		public ConfigEntryAction(String id) {
			actionId = id;
		}

		/*
		 * Peform the action for a given entry in the config file. */
		public abstract void performAction(String value)
				throws ConfigReaderException;
	}

	@SuppressWarnings("serial")
	public static class ConfigReaderException extends Exception {
		public ConfigReaderException(Exception e) {
			super(e);
		}
	}
}