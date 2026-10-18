package com.floreantpos.extension;

import java.awt.Component;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JDialog;

import net.xeoh.plugins.base.Plugin;

/** Contract implemented by optional Floreant POS extensions. */
public interface FloreantPlugin extends Plugin {
	String getId();
	String getName();
	String getProductName();
	String getProductVersion();
	String getSecurityCode();
	void initUI();
	void initLicense();
	void initBackoffice();
	void initConfigurationView(JDialog dialog);
	List<AbstractAction> getSpecialFunctionActions();
	boolean requireLicense();
	boolean hasValidLicense();
	Component getParent();
}
