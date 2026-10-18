package com.orocube.common.util;

import java.net.InetAddress;
import java.nio.charset.Charset;
import java.util.UUID;

/**
 * Provides the terminal identifier formerly supplied by the unavailable
 * orocube-common-classes dependency.
 */
public final class TerminalUtil {
	private static final String TERMINAL_KEY_PROPERTY = "floreantpos.terminalKey";
	private static final Charset UTF_8 = Charset.forName("UTF-8");
	private static String systemUID;

	private TerminalUtil() {
	}

	public static synchronized String getSystemUID() {
		if (systemUID != null) {
			return systemUID;
		}

		String configuredKey = System.getProperty(TERMINAL_KEY_PROPERTY);
		if (configuredKey != null && configuredKey.trim().length() > 0) {
			systemUID = configuredKey.trim();
			return systemUID;
		}

		String hostName = "unknown-host";
		try {
			hostName = InetAddress.getLocalHost().getHostName();
		}
		catch (Exception ignored) {
			// A deterministic fallback is used when the host name cannot be resolved.
		}

		String fingerprint = hostName + "|" + System.getProperty("os.name", "unknown-os") + "|" + System.getProperty("user.name", "unknown-user");
		systemUID = UUID.nameUUIDFromBytes(fingerprint.getBytes(UTF_8)).toString();
		return systemUID;
	}
}
