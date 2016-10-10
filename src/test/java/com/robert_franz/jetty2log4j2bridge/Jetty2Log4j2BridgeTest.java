package com.robert_franz.jetty2log4j2bridge;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.eclipse.jetty.util.log.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

/**
 * User: Robert Franz
 * Date: 2015-08-24
 * Time: 18:44
 */
public class Jetty2Log4j2BridgeTest
{
	private Jetty2Log4j2Bridge testable;

	@Before
	public void setUp()
	{
		testable = new Jetty2Log4j2Bridge("logger");
	}

	@Test
	public void getName()
	{
		assertEquals("logger", testable.getName());
	}

	@Test
	public void newLogger()
	{
		Logger logger = testable.newLogger("newLogger");
		assertEquals("newLogger", logger.getName());
	}

	@Test
	public void log()
	{
		testable.warn("warn");
		assertLog(Level.WARN, "warn", null);

		Throwable tw = new Throwable("warn");
		testable.warn(tw);
		assertLog(Level.WARN, "catching", tw);

		testable.warn("warn:{}", "text");
		assertLog(Level.WARN, "warn:text", null);

		testable.info("info");
		assertLog(Level.INFO, "info", null);

		Throwable ti = new Throwable("info");
		testable.info(ti);
		assertLog(Level.INFO, "catching", ti);

		testable.info("info:{}", "text");
		assertLog(Level.INFO, "info:text", null);

		testable.debug("debug");
		assertLog(Level.DEBUG, "debug", null);

		Throwable td = new Throwable("debug");
		testable.debug(td);
		assertLog(Level.DEBUG, "catching", td);

		testable.debug("debug:{}", "text");
		assertLog(Level.DEBUG, "debug:text", null);

		Throwable tig = new Throwable("ignore");
		testable.ignore(tig);
		assertLog(Level.TRACE, "catching", tig);
	}

	private void assertLog(Level level, String message, Throwable throwable)
	{
		LinkedList<LogEvent> events = TestAppender.events;
		assertEquals(1, events.size());
		LogEvent event = events.getFirst();
		assertEquals(level, event.getLevel());
		assertEquals(message, event.getMessage().getFormattedMessage());
		assertEquals(throwable, event.getThrown());
		events.clear();
	}

}


