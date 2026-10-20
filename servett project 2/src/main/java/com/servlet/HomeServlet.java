package com.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Protected Home / Dashboard Servlet.
 * Accessible only when authenticated (protected by AuthFilter).
 */
@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("user") : "Guest";
        LocalDateTime loginTime = (session != null) ? (LocalDateTime) session.getAttribute("loginTime") : null;
        String formattedLoginTime = (loginTime != null) 
                ? loginTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) 
                : "N/A";

        ServletContext context = getServletContext();
        AtomicInteger activeCount = (AtomicInteger) context.getAttribute("activeUserCount");
        int totalActive = (activeCount != null) ? activeCount.get() : 1;
        String appName = (String) context.getAttribute("appName");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang='en'>");
            out.println("<head>");
            out.println("  <meta charset='UTF-8'>");
            out.println("  <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("  <title>Dashboard - " + escapeHtml(username) + "</title>");
            out.println("  <style>");
            out.println("    * { box-sizing: border-box; margin: 0; padding: 0; }");
            out.println("    body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #0b0f19; color: #f1f5f9; min-height: 100vh; display: flex; flex-direction: column; }");
            out.println("    .navbar { background: #1e293b; border-bottom: 1px solid #334155; padding: 16px 32px; display: flex; justify-content: space-between; align-items: center; }");
            out.println("    .navbar h2 { font-size: 1.25rem; color: #38bdf8; }");
            out.println("    .logout-link { background: #ef4444; color: #ffffff; text-decoration: none; padding: 8px 16px; border-radius: 6px; font-weight: 600; font-size: 0.9rem; transition: background 0.2s; }");
            out.println("    .logout-link:hover { background: #dc2626; }");
            out.println("    .container { max-width: 900px; margin: 40px auto; padding: 0 20px; width: 100%; flex: 1; }");
            out.println("    .welcome-card { background: linear-gradient(135deg, #1e293b 0%, #0f172a 100%); border: 1px solid #334155; border-radius: 12px; padding: 32px; margin-bottom: 24px; box-shadow: 0 10px 25px rgba(0,0,0,0.4); }");
            out.println("    .welcome-card h1 { color: #f8fafc; font-size: 1.8rem; margin-bottom: 8px; }");
            out.println("    .welcome-card p { color: #94a3b8; font-size: 1rem; }");
            out.println("    .grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(260px, 1fr)); gap: 20px; }");
            out.println("    .stat-card { background: #1e293b; border: 1px solid #334155; border-radius: 10px; padding: 20px; }");
            out.println("    .stat-card .label { font-size: 0.85rem; text-transform: uppercase; letter-spacing: 0.05em; color: #64748b; margin-bottom: 8px; font-weight: 600; }");
            out.println("    .stat-card .value { font-size: 1.2rem; font-weight: bold; color: #38bdf8; word-break: break-all; }");
            out.println("    .actions { margin-top: 30px; display: flex; gap: 12px; }");
            out.println("    .btn-secondary { background: #334155; color: #f8fafc; text-decoration: none; padding: 10px 20px; border-radius: 8px; font-weight: 600; transition: background 0.2s; }");
            out.println("    .btn-secondary:hover { background: #475569; }");
            out.println("  </style>");
            out.println("</head>");
            out.println("<body>");
            out.println("  <nav class='navbar'>");
            out.println("    <h2>⚡ " + (appName != null ? escapeHtml(appName) : "Servlet App") + "</h2>");
            out.println("    <a class='logout-link' href='" + request.getContextPath() + "/logout'>Sign Out</a>");
            out.println("  </nav>");
            out.println("  <div class='container'>");
            out.println("    <div class='welcome-card'>");
            out.println("      <h1>Welcome, " + escapeHtml(username) + "! 🎉</h1>");
            out.println("      <p>You have successfully authenticated through <code>LoginServlet</code> and passed through <code>AuthFilter</code>.</p>");
            out.println("    </div>");
            out.println("    <div class='grid'>");
            out.println("      <div class='stat-card'>");
            out.println("        <div class='label'>Session ID</div>");
            out.println("        <div class='value' style='font-size: 0.95rem;'>" + (session != null ? session.getId() : "N/A") + "</div>");
            out.println("      </div>");
            out.println("      <div class='stat-card'>");
            out.println("        <div class='label'>Logged In Since</div>");
            out.println("        <div class='value'>" + formattedLoginTime + "</div>");
            out.println("      </div>");
            out.println("      <div class='stat-card'>");
            out.println("        <div class='label'>Active Sessions (AppListener)</div>");
            out.println("        <div class='value'>" + totalActive + "</div>");
            out.println("      </div>");
            out.println("    </div>");
            out.println("    <div class='actions'>");
            out.println("      <a class='btn-secondary' href='" + request.getContextPath() + "/hello?name=" + escapeHtml(username) + "'>Test HelloServlet (with name)</a>");
            out.println("      <a class='btn-secondary' href='" + request.getContextPath() + "/index.html'>Landing Page</a>");
            out.println("    </div>");
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
