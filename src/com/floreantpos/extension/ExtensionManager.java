package com.floreantpos.extension;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.xeoh.plugins.base.Plugin;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.util.PluginManagerUtil;

import com.floreantpos.util.JarUtil;

/** Discovers built-in and externally supplied Floreant POS extensions. */
public final class ExtensionManager {
	private static final ExtensionManager INSTANCE = new ExtensionManager();
	private List<FloreantPlugin> plugins = Collections.emptyList();
	private boolean initialized;

	private ExtensionManager() {
	}

	public static ExtensionManager getInstance() {
		return INSTANCE;
	}

	public synchronized void initialize(Class<?> applicationClass) {
		if (initialized) {
			return;
		}

		PluginManager pluginManager = PluginManagerFactory.createPluginManager();
		addPlugins(pluginManager, new File(JarUtil.getJarLocation(applicationClass)));

		String pluginPaths = System.getProperty("pluginsPath");
		if (pluginPaths == null || pluginPaths.trim().length() == 0) {
			addPlugins(pluginManager, new File("plugins"));
		}
		else {
			String[] paths = pluginPaths.split(",");
			for (int i = 0; i < paths.length; i++) {
				addPlugins(pluginManager, new File(paths[i].trim()));
			}
		}

		List<FloreantPlugin> discovered = new ArrayList<FloreantPlugin>();
		for (Plugin plugin : new PluginManagerUtil(pluginManager).getPlugins()) {
			if (plugin instanceof FloreantPlugin) {
				FloreantPlugin extension = (FloreantPlugin) plugin;
				if (!extension.requireLicense() || isLicensed(extension)) {
					discovered.add(extension);
				}
			}
		}
		Collections.sort(discovered, new Comparator<FloreantPlugin>() {
			@Override
			public int compare(FloreantPlugin first, FloreantPlugin second) {
				return first.getClass().getName().compareTo(second.getClass().getName());
			}
		});
		plugins = Collections.unmodifiableList(discovered);
		initialized = true;
	}

	private static void addPlugins(PluginManager pluginManager, File directory) {
		if (directory.exists()) {
			pluginManager.addPluginsFrom(directory.toURI());
		}
	}

	private static boolean isLicensed(FloreantPlugin extension) {
		extension.initLicense();
		return extension.hasValidLicense();
	}

	public static List<FloreantPlugin> getPlugins() {
		return INSTANCE.plugins;
	}

	public static List<FloreantPlugin> getPlugins(Class<?> pluginClass) {
		List<FloreantPlugin> result = new ArrayList<FloreantPlugin>();
		for (FloreantPlugin extension : INSTANCE.plugins) {
			if (pluginClass.isAssignableFrom(extension.getClass())) {
				result.add(extension);
			}
		}
		return result;
	}

	public static FloreantPlugin getPlugin(Class<?> pluginClass) {
		for (FloreantPlugin extension : INSTANCE.plugins) {
			if (pluginClass.isAssignableFrom(extension.getClass())) {
				return extension;
			}
		}
		return null;
	}
}
