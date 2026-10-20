package com.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Handles User Login and Logout operations.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login", "/logout"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        // Handle Logout request
        if ("/logout".equals(path) || "logout".equalsIgnoreCase(request.getParameter("action"))) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.removeAttribute("user");
                session.invalidate();
                System.out.println("[LoginServlet] Session invalidated successfully.");
            }
            response.sendRedirect(request.getContextPath() + "/index.html?message=logged_out");
            return;
        }

        // If already logged in, redirect to home
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/index.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Basic validation demo (accept admin / admin123 or any non-empty username with password length >= 4)
        boolean isValid = username != null && !username.trim().isEmpty() 
                && password != null && !password.trim().isEmpty()
                && (("admin".equalsIgnoreCase(username.trim()) && "admin123".equals(password))
                    || password.length() >= 4);

        if (isValid) {
            // Create or retrieve session
            HttpSession session = request.getSession(true);
            session.setAttribute("user", username.trim());
            session.setAttribute("loginTime", LocalDateTime.now());

            System.out.println("[LoginServlet] User '" + username + "' logged in. Session ID: " + session.getId());
            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            System.out.println("[LoginServlet] Failed login attempt for user: " + username);
            response.sendRedirect(request.getContextPath() + "/index.html?error=invalid_credentials");
        }
    }
}
