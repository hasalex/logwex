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

package org.sewatech.logwex.example;

import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.WriterAppender;
import org.sewatech.logwex.utils.LogRedirector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created : 16 août 2010
 *
 * @author Alexis Hassler
 * @since 1.0
 */
public class SimpleLogWExServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(SimpleLogWExServlet.class);
    private static final String APPENDER_NAME = "logwex";
    
    private ByteArrayOutputStream stream;

    public void init() throws ServletException {
        super.init();

        Logger rootLogger = Logger.getRootLogger();

        rootLogger.setLevel(Level.OFF);
        logger.setLevel(Level.ALL);

        rootLogger.removeAllAppenders();

        stream = new ByteArrayOutputStream();
        WriterAppender appender = new WriterAppender(new SimpleLayout(), stream);
        appender.setName(APPENDER_NAME);
        rootLogger.addAppender(appender);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.action(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.action(request, response);
    }

    private void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String pattern = request.getParameter("pattern");
        if ( (pattern != null) && !"".equals(pattern)) {
            changePattern(pattern);
        }
        
        logger.info("action");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
                "Transitional//EN\">\n" +
                "<HTML>\n" +
                "<HEAD><TITLE>");
        out.print("LogWEx Examples - logs");
        out.print("</TITLE></HEAD>\n<BODY>\n" +
                "<h1>LogWEx Examples</h1><p>Here are the logs with pattern \"" + pattern + "\" :</p>\n");
        out.print("<HR>");
        out.print(stream.toString().replace("\n", "<br>"));
        out.print("<HR>\n<p><a href='" + request.getContextPath() + "'>Back</a></p>\n");
        out.println("</BODY>\n</HTML>\n");
        out.close();
        stream.reset();
    }

    public void changePattern(String pattern) {

        Logger rootLogger = Logger.getRootLogger();
        Layout layout = new PatternLayout(pattern);
        rootLogger.getAppender(APPENDER_NAME).setLayout(layout);

    }

}
