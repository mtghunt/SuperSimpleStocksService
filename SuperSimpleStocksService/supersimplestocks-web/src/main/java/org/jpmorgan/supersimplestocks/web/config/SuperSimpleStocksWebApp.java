package org.jpmorgan.supersimplestocks.web.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * 
 * 
 *
 */
public class SuperSimpleStocksWebApp extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { WebApp.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebApp.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/app/*" };
	}

}
