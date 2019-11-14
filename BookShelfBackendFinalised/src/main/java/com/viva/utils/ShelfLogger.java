package com.viva.utils;

/*********************************************************************
 * COPYRIGHT: Comviva Technologies Limited
 *
 * This software is the sole property of Comviva and is protected
 * by copyright law and international treaty provisions. Unauthorized
 * reproduction or redistribution of this program, or any portion of
 * it may result in severe civil and criminal penalties and will be
 * prosecuted to the maximum extent possible under the law.
 *
 * Comviva reserves all rights not expressly granted. You may not
 * reverse engineer, decompile, or disassemble the software, except
 * and only to the extent that such activity is expressly permitted
 * by applicable law notwithstanding this limitation.
 *
 * THIS SOFTWARE IS PROVIDED TO YOU "AS IS" WITHOUT WARRANTY OF ANY
 * KIND, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
 * PARTICULAR PURPOSE. YOU ASSUME THE ENTIRE RISK AS TO THE ACCURACY
 * AND THE USE OF THIS SOFTWARE. COMVIVA SHALL NOT BE LIABLE FOR
 * ANY DAMAGES WHATSOEVER ARISING OUT OF THE USE OF OR INABILITY TO
 * USE THIS SOFTWARE, EVEN IF COMVIVA HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *********************************************************************/

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.util.ContextInitializer;
import ch.qos.logback.core.joran.spi.JoranException;

public class ShelfLogger {

	String msisdn = "msisdn";

	/* The logger. */

	private Logger logger;

	/* The Constant SPACE. */

	private static final String SPACE = " ";
	private static String[] testMsisdn;

	/*
	 * 
	 * Constructor to get the Logger instance.
	 * 
	 * @param className the name of the logger to retrieve. If the named logger
	 * already exists, then the existing instance will be returned.
	 * 
	 */

	public ShelfLogger(final Class<?> className) {
		logger = LoggerFactory.getLogger(className);
	}

	/*
	 * 
	 * Instantiates a new logger.
	 *
	 * @param aStrVal the a str val
	 * 
	 */

	public ShelfLogger(String aStrVal) {
		logger = LoggerFactory.getLogger(aStrVal);
	}

	/*
	 * 
	 * This method is used to reload the logger properties.
	 * 
	 */

	public void reloadLogBackupConfiguration() throws JoranException {
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		ContextInitializer contextInitiliazer = new ContextInitializer(loggerContext);
		URL url = contextInitiliazer.findURLOfDefaultConfigurationFile(true);
		loggerContext.reset();
		contextInitiliazer.configureByResource(url);
	}

	/*
	 * 
	 * Method to load test Msisdn
	 * 
	 */

	public static void loadTestMsisdn(String[] msisdn) {
		testMsisdn = msisdn;
	}

	/*
	 * 
	 * Method to check msisdn belongs to test MSISDN
	 * 
	 */

	private boolean checkMsisdn(String msisdn) {
		for (int i = 0; i < testMsisdn.length; i++)
			if (msisdn.equals(testMsisdn[i]))
				return true;
		return false;
	}

	/*
	 * Checks if is debug enabled.
	 *
	 * @return If Debug is enabled
	 * 
	 */

	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	/*
	 * 
	 * Checks if is info enabled.
	 *
	 * @return If Info is enabled
	 * 
	 */

	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	/**
	 * 
	 * This method logs the statement with the DEBUG level (It designates
	 * fine-grained informational events that are most useful to debug an
	 * application). If this category is DEBUG enabled, then it proceeds to call all
	 * the registered appenders in this category and also higher in the hierarchy
	 * depending on the value of the additivity flag.
	 * 
	 * @param statement the statement to be logged
	 * 
	 **/

	public void debug(final String statement) {
		if (!isDebugEnabled() && MDC.get(msisdn) != null && checkMsisdn(MDC.get(msisdn)))
			logger.info(statement);
		else
			logger.debug(statement);

	}

	/*
	 * 
	 * Debug.
	 *
	 * @param statement the statement
	 * 
	 * @param e the e
	 * 
	 */

	public void debug(final String statement, final Exception e) {
		if (!isDebugEnabled() && MDC.get(msisdn) != null && checkMsisdn(MDC.get(msisdn)))
			logger.info(append(statement, e));
		else
			logger.debug(append(statement, e));
	}

	/**
	 * 
	 * This method logs the statement with the INFO level (It designates
	 * informational messages that highlight the progress of the application at
	 * coarse-grained level). If this category is INFO enabled, then it proceeds to
	 * call all the registered appenders in this category and also higher in the
	 * hierarchy depending on the value of the additivity flag.
	 * 
	 * @param statement the statement to be logged
	 * 
	 **/

	public void info(final String statement) {
		logger.info(statement);
	}

	/*
	 * 
	 * Info.
	 *
	 * @param statement the statement
	 * 
	 * @param e the e
	 * 
	 */

	public void info(final String statement, final Exception e) {
		logger.info(append(statement, e));
	}

	/**
	 * 
	 * This method logs the statement with the WARN level (It designates potentially
	 * harmful situations).
	 * 
	 * @param statement the statement to be logged
	 * 
	 **/

	public void warn(final String statement) {
		logger.warn(statement);
	}

	/*
	 * 
	 * Warn.
	 *
	 * @param statement the statement
	 * 
	 * @param e the e
	 * 
	 */

	public void warn(final String statement, final Exception e) {
		logger.warn(append(statement, e));
	}

	/**
	 * 
	 * This method logs the statement with the ERROR level (It designates error
	 * events that might still allow the application to continue running).
	 * 
	 * @param statement the statement to be logged
	 * 
	 **/

	public void error(final String statement) {
		logger.error(statement);
	}

	/*
	 * 
	 * Error.
	 *
	 * @param statement the statement
	 * 
	 * @param e the e
	 * 
	 */

	public void error(final String statement, final Exception e) {
		logger.error(append(statement, e));
	}

	/**
	 * 
	 * This method logs the statement with the FATAL level (It designates very
	 * severe error events that will presumably lead the application to abort).
	 * 
	 * @param statement the statement to be logged
	 * 
	 **/

	public void fatal(final String statement) {
		logger.error(statement);
	}

	/*
	 * Fatal.
	 *
	 * @param statement the statement
	 * 
	 * @param e the e
	 */

	public void fatal(final String statement, final Exception e) {
		logger.error(append(statement, e));
	}

	/*
	 * Append.
	 *
	 * @param statement the statement
	 * 
	 * @param e the e
	 * 
	 * @return the string
	 */

	private String append(final String statement, final Exception e) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(statement);
		stringBuilder.append(SPACE);
		stringBuilder.append(getExceptionDetails(e));
		return stringBuilder.toString();
	}

	/*
	 * Gets the exception details.
	 *
	 * @param e the e
	 * 
	 * @return the exception details
	 */

	private String getExceptionDetails(Exception e) {
		StringBuilder stringBuilder = new StringBuilder();
		if (e != null) {
			StackTraceElement[] traces = e.getStackTrace();
			int length = traces.length;
			if (length > 0) {
				if (length > 3 && logger.isInfoEnabled()) {
					length = 3;
				}
				stringBuilder.append("Exception : " + e + ": " + e.getMessage());
				getClassDetailsAndMethodDetails(e, length, stringBuilder);
			}
		}
		return stringBuilder.toString();
	}

	private void getClassDetailsAndMethodDetails(Exception e, int length, StringBuilder stringBuilder) {
		StackTraceElement[] traces = e.getStackTrace();
		for (int i = 0; i < length; i++) {
			StackTraceElement ste = traces[i];
			if (ste != null) {
				String s = ste.getClassName();
				if (s != null) {
					stringBuilder.append("[Class : " + s + "]");
				}

				s = ste.getMethodName();
				if (s != null) {
					stringBuilder.append("[Method : " + s + "]");
				}
				stringBuilder.append("[Line : " + ste.getLineNumber() + "]");
			}
		}
	}
}
