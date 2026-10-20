package com.servlet;

import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

/**
 * Main Application Entry Point.
 * Starts an embedded Apache Tomcat 10 server on port 8080.
 * You can run this class directly like a standard Java application.
 */
public class App {

    public static void main(String[] args) {
        try {
            int port = 8080;
            Tomcat tomcat = new Tomcat();
            tomcat.setPort(port);
            tomcat.getConnector(); // Initialize the default HTTP connector

            // Path to webapp static resources (index.html, etc.)
            String webAppMount = new File("src/main/webapp").getAbsolutePath();
            Context context = tomcat.addWebapp("", webAppMount);
            context.addWelcomeFile("index.html");

            // Register compiled classes for annotations (@WebServlet, @WebFilter, @WebListener)
            File additionWebInfClasses = new File("target/classes");
            if (additionWebInfClasses.exists()) {
                WebResourceRoot resources = new StandardRoot(context);
                resources.addPreResources(new DirResourceSet(
                        resources,
                        "/WEB-INF/classes",
                        additionWebInfClasses.getAbsolutePath(),
                        "/"
                ));
                context.setResources(resources);
            }

            // 1. Start Tomcat Server
            tomcat.start();

            System.out.println("===============================================================");
            System.out.println("  🚀 Java Servlet Application Started Successfully!");
            System.out.println("  🌐 Open in your browser: http://localhost:" + port + "/");
            System.out.println("===============================================================");

            // 2. Open browser AFTER the server has started
            openBrowser("http://localhost:" + port + "/");

            // 3. Keep the server running
            tomcat.getServer().await();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void openBrowser(String url) {
        try {
            if (java.awt.Desktop.isDesktopSupported() && java.awt.Desktop.getDesktop().isSupported(java.awt.Desktop.Action.BROWSE)) {
                java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
            } else {
                // Fallback for Windows
                
                Runtime.getRuntime().exec(new String[]{"rundll32", "url.dll,FileProtocolHandler", url});
            }
        } catch (Exception ignored) {
            // If automated open fails, user can open the URL manually
        }
    }
}
