package com.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Basic Demonstration Servlet.
 * Demonstrates request handling, parameter extraction, and HTML response generation.
 */
@WebServlet(name = "HelloServlet", urlPatterns = {"/hello"})
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        String name = request.getParameter("name");
        if (name == null || name.trim().isEmpty()) {
            name = "World";
        }

        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang='en'>");
            out.println("<head>");
            out.println("  <meta charset='UTF-8'>");
            out.println("  <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("  <title>Hello Servlet Response</title>");
            out.println("  <style>");
            out.println("    body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #0f172a; color: #f8fafc; margin: 0; padding: 40px 20px; display: flex; justify-content: center; }");
            out.println("    .card { background: #1e293b; border: 1px solid #334155; border-radius: 12px; padding: 32px; max-width: 600px; width: 100%; box-shadow: 0 10px 25px rgba(0,0,0,0.5); }");
            out.println("    h1 { color: #38bdf8; margin-top: 0; }");
            out.println("    .info-box { background: #0f172a; border-left: 4px solid #38bdf8; padding: 14px; border-radius: 6px; margin: 20px 0; }");
            out.println("    .info-item { margin: 8px 0; font-size: 0.95rem; color: #94a3b8; }");
            out.println("    .info-item strong { color: #f1f5f9; }");
            out.println("    a.btn { display: inline-block; background: #38bdf8; color: #0f172a; font-weight: 600; text-decoration: none; padding: 10px 20px; border-radius: 8px; margin-top: 15px; transition: background 0.2s; }");
            out.println("    a.btn:hover { background: #7dd3fc; }");
            out.println("  </style>");
            out.println("</head>");
            out.println("<body>");
            out.println("  <div class='card'>");
            out.println("    <h1>👋 Hello, " + escapeHtml(name) + "!</h1>");
            out.println("    <p>This dynamic response is served directly by <code>HelloServlet.java</code>.</p>");
            out.println("    <div class='info-box'>");
            out.println("      <div class='info-item'><strong>Method:</strong> " + request.getMethod() + "</div>");
            out.println("      <div class='info-item'><strong>Remote IP:</strong> " + request.getRemoteAddr() + "</div>");
            out.println("      <div class='info-item'><strong>Context Path:</strong> " + request.getContextPath() + "</div>");
            out.println("      <div class='info-item'><strong>Servlet Path:</strong> " + request.getServletPath() + "</div>");
            out.println("      <div class='info-item'><strong>Server Time:</strong> " + currentTime + "</div>");
            out.println("    </div>");
            out.println("    <a class='btn' href='" + request.getContextPath() + "/index.html'>← Back to Home</a>");
            out.println("  </div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private String escapeHtml(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#x27;");
    }
}
