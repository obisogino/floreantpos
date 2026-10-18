package com.floreantpos.extension;

import java.awt.Component;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JDialog;

/** Default no-op implementation for optional extensions. */
public class AbstractFloreantPlugin implements FloreantPlugin {
	@Override
	public String getId() {
		return getClass().getName();
	}

	@Override
	public String getName() {
		return getProductName();
	}

	@Override
	public String getProductName() {
		return getClass().getSimpleName();
	}

	@Override
	public String getProductVersion() {
		return null;
	}

	@Override
	public String getSecurityCode() {
		return null;
	}

	@Override
	public void initUI() {
	}

	@Override
	public void initLicense() {
	}

	@Override
	public void initBackoffice() {
	}

	@Override
	public void initConfigurationView(JDialog dialog) {
	}

	@Override
	public List<AbstractAction> getSpecialFunctionActions() {
		return Collections.emptyList();
	}

	@Override
	public boolean requireLicense() {
		return false;
	}

	@Override
	public boolean hasValidLicense() {
		return true;
	}

	@Override
	public Component getParent() {
		return null;
	}
}
