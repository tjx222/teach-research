/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.test;

import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.net.URL;

import org.junit.runners.model.InitializationError;
import org.slf4j.impl.StaticLoggerBinder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.SystemPropertyUtils;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.util.ContextInitializer;
import ch.qos.logback.core.joran.spi.JoranException;
/**
* <pre>
*	单元测试日志初始化
* </pre>
*
* @author tmser
* @version $Id: LogBackJUnit4ClassRunner.java, v 1.0 2016-07-27 tmser Exp $
*/
public class LogBackJUnit4ClassRunner extends SpringJUnit4ClassRunner {

	static {
			initLogging("classpath:config/init/logback.xml");
	}
	
	public LogBackJUnit4ClassRunner(Class<?> clazz) throws InitializationError{
		super(clazz);
	}
	
    static void init(String location) throws FileNotFoundException, JoranException {
	        String resolvedLocation = SystemPropertyUtils.resolvePlaceholders(location);
	        URL url = ResourceUtils.getURL(resolvedLocation);
	        LoggerContext loggerContext = (LoggerContext)StaticLoggerBinder.getSingleton().getLoggerFactory();

	        // in the current version logback automatically configures at startup the context, so we have to reset it
	        loggerContext.reset();

	        // reinitialize the logger context.  calling this method allows configuration through groovy or xml
	        new ContextInitializer(loggerContext).configureByResource(url);
	 }
	  
	 public static void initLogging(String location) {

	        // Only perform custom Logback initialization in case of a config file.
	        if (location != null) {
	            // Perform actual Logback initialization; else rely on Logback's default initialization.
	            try {
	                // Initialize
	                init(location);
	            } catch (Exception ex) {
	            	ex.printStackTrace();
	            }
	        }

	        //If SLF4J's java.util.logging bridge is available in the classpath, install it. This will direct any messages
	        //from the Java Logging framework into SLF4J. When logging is terminated, the bridge will need to be uninstalled
	        try {
	            Class<?> julBridge = ClassUtils.forName("org.slf4j.bridge.SLF4JBridgeHandler", ClassUtils.getDefaultClassLoader());
	            
	            Method removeHandlers = ReflectionUtils.findMethod(julBridge, "removeHandlersForRootLogger");
	            if (removeHandlers != null) {
	                ReflectionUtils.invokeMethod(removeHandlers, null);
	            }
	            
	            Method install = ReflectionUtils.findMethod(julBridge, "install");
	            if (install != null) {
	                ReflectionUtils.invokeMethod(install, null);
	            }
	        } catch (ClassNotFoundException ignored) {
	        }
	    }
}
