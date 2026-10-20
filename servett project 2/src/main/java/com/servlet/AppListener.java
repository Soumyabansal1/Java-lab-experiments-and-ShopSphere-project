package com.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Application Lifecycle and Session Listener.
 * Tracks application startup/shutdown and active user sessions.
 */
@WebListener
public class AppListener implements ServletContextListener, HttpSessionListener {

    private final AtomicInteger activeSessions = new AtomicInteger(0);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        context.setAttribute("appName", "Servlet Demo Application");
        context.setAttribute("startTime", LocalDateTime.now());
        context.setAttribute("activeUserCount", activeSessions);
        System.out.println("[AppListener] Application context initialized at: " + LocalDateTime.now());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("[AppListener] Application context destroyed at: " + LocalDateTime.now());
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        int count = activeSessions.incrementAndGet();
        System.out.println("[AppListener] Session created: " + se.getSession().getId() + " | Active Sessions: " + count);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        int count = activeSessions.decrementAndGet();
        System.out.println("[AppListener] Session destroyed: " + se.getSession().getId() + " | Active Sessions: " + count);
    }
}
