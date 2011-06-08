/*
 * Copyright 2010 Sewatech
 *
 * This file is part of LogWEx.
 *
 * LogWEx is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published bythe Free Software Foundation, in version 3 of the
 * License.
 *
 * LogWEx is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with LogWEx.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package org.sewatech.logwex.utils;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Load log4j configuration and watch it to provide hot reconfiguration
 * Should be configured as the first Listener ni server.xml
 * <pre>
 *   <Listener className="org.sewatech.logwex.utils.ConfigureAndWatchListener"
 *       configurationFile="/opt/java/apache-tomcat-6.0.32/conf/log4j.xml"
 *       delay="20000"/>
 * </pre>
 *
 * @author Alexis Hassler
 */
public class ConfigureAndWatchListener implements LifecycleListener {

    private String configurationFile;
    private Long delay;

    /**
     * Set the log4j configuration file in XML format.
     */
    public void setConfigurationFile(String file) {
        this.configurationFile = file;
    }

    /**
     * Set the delay in milliseconds to wait between each check
     */
    public void setDelay(Long delay) {
        this.delay = delay;
    }

    public void lifecycleEvent(LifecycleEvent event) {
        if (Lifecycle.START_EVENT.equals(event.getType())) {
            DOMConfigurator.configureAndWatch(configurationFile, delay);
            Logger.getLogger(this.getClass()).debug("Log4J configured and configuration file beeing watched");
        } else if (Lifecycle.STOP_EVENT.equals(event.getType())) {
        }
    }
}
