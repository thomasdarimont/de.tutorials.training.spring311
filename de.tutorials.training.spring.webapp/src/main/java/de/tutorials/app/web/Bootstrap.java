package de.tutorials.app.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class Bootstrap implements WebApplicationInitializer {

    AnnotationConfigWebApplicationContext rootContext;

    @Override
    public void onStartup(ServletContext servletContext)
	    throws ServletException {
	setupApplicationContext();
	registerServlets(servletContext);
	registerListeners(servletContext);
	registerFilters(servletContext);
    }

    private void registerServlets(ServletContext servletContext) {
	// Create the dispatcher servlet's Spring application context
	AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
	dispatcherContext.register(DispatcherConfig.class);

	// Register and map the dispatcher servlet
	ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
		"dispatcher", new DispatcherServlet(dispatcherContext));
	dispatcher.setLoadOnStartup(1);
	dispatcher.addMapping("/dispatch/*");
    }

    private void setupApplicationContext() {
	this.rootContext = new AnnotationConfigWebApplicationContext();
	this.rootContext.scan("de.tutorials.app");
	// this.rootContext.register(annotatedClasses)
    }

    private void registerListeners(ServletContext servletContext) {
	servletContext.addListener(new ContextLoaderListener(this.rootContext));
    }

    private void registerFilters(ServletContext servletContext) {
	servletContext.addFilter("hiddenHttpMethodFilter",
		HiddenHttpMethodFilter.class).addMappingForUrlPatterns(null,
		false, "/*");
    }
}
