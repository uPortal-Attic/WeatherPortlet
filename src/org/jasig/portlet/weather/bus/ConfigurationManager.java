/* Copyright 2008 The JA-SIG Collaborative.  All rights reserved.
*  See license distributed with this file and
*  available online at http://www.uportal.org/license.html
*/

package org.jasig.portlet.weather.bus;

import java.io.Serializable;

public class ConfigurationManager implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8102411153509964268L;
	private Config config;

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}
}
