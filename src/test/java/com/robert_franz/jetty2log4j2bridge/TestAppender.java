package com.robert_franz.jetty2log4j2bridge;

/**
 * User: Robert Franz
 * Date: 2015-08-27
 * Time: 09:18
 */

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.Serializable;
import java.util.LinkedList;

// note: class name need not match the @Plugin name.
@Plugin(name = "TestAppender", category = "Core", elementType = "appender", printObject = true)
public final class TestAppender extends AbstractAppender
{
	public static final LinkedList<LogEvent> events = new LinkedList<>();

	protected TestAppender(String name, Filter filter,
		Layout<? extends Serializable> layout, final boolean ignoreExceptions)
	{
		super(name, filter, layout, ignoreExceptions);
	}

	public void append(LogEvent event)
	{
		events.add(event);
	}

	// Your custom appender needs to declare a factory method
	// annotated with `@PluginFactory`. Log4j will parse the configuration
	// and call this factory method to construct an appender instance with
	// the configured attributes.
	@PluginFactory
	public static TestAppender createAppender(
		@PluginAttribute("name") String name,
		@PluginElement("Layout") Layout<? extends Serializable> layout,
		@PluginElement("Filter") final Filter filter,
		@PluginAttribute("otherAttribute") String otherAttribute)
	{
		if(name == null)
		{
			LOGGER.error("No name provided for TestAppender");
			return null;
		}
		if(layout == null)
		{
			layout = PatternLayout.createDefaultLayout();
		}
		return new TestAppender(name, filter, layout, true);
	}
}