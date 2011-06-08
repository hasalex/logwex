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
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * User: Alexis Hassler
 */
public class ConfigureAndWatchListenerTest {

    private ConfigureAndWatchListener listener;

    @Test
    public void testRelativePath() throws Exception {
        //testLifecycleEvent("log4j-test.xml");
    }
    @Test
    public void testAbsolutePath() throws Exception {
        testLifecycleEvent("/Users/alexis/Projet/sw-tct/idea-project/logwex-tc/src/test/resources/log4j-test.xml");
    }

    private void testLifecycleEvent(String file) throws Exception {
        //assertTrue("File should exist", new File(file).exists());

        listener = new ConfigureAndWatchListener();
        listener.setConfigurationFile(file);
        LifecycleEvent event = new LifecycleEvent(mock(Lifecycle.class), Lifecycle.START_EVENT);
//        listener.lifecycleEvent(event);

        DOMConfigurator.configureAndWatch(file, 20);

        Logger logger = Logger.getRootLogger(); //getRootLogger();

        logger.info("OK ?");

        assertNotNull("Null appenders enumeration", logger.getAllAppenders());
        assertTrue("No appender for logger", logger.getAllAppenders().hasMoreElements());
    }
}
