/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seta.servlet;

import static com.seta.util.Utility.redirect;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.seta.activity.Action;
import com.seta.ejb.EmailManagerEJB;
import com.seta.util.Utility;
import java.util.logging.*;
/**
 *
 * @author agodino
 */
public class OperazioniGeneral extends HttpServlet {
	private static final Logger LOGGER = Logger.getLogger( OperazioniGeneral.class.getName() );
	private static final long serialVersionUID = 3135660729916827579L;
	
	@EJB
	private EmailManagerEJB emailManagerEJB;


    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void changePasswordProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id_operatore = request.getSession().getAttribute("id_operatore").toString();
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String email = request.getParameter("email");
        if (newPassword.length() > 7 && checkPassword(newPassword)) {
            String ctrlPagina = Action.changePasswordProfile(id_operatore, oldPassword, newPassword);
            if (ctrlPagina.equals("OK")) {
                String[] to = {email};
                emailManagerEJB.sendEmail(to, "cambio password", Action.getPath("userchangepwd").replace("@utente", request.getSession().getAttribute("nome").toString() + " " + request.getSession().getAttribute("cognome").toString()));
                response.sendRedirect("profile.jsp?esitopass=" + ctrlPagina);
            } else if (ctrlPagina.equals("updateFail")) {
                response.sendRedirect("profile.jsp?esitopass=" + ctrlPagina);
            } else if (ctrlPagina.equals("passErr")) {
                response.sendRedirect("profile.jsp?esitopass=" + ctrlPagina);
            }
        } else {
            response.sendRedirect("profile.jsp?esitopass=passErr");
        }
    }

    protected void changeOperatorProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone_number");

        if (Action.updateOperatore((int) request.getSession().getAttribute("id_operatore"), name, surname, email, phone)) {
            request.getSession().setAttribute("nome", name);
            request.getSession().setAttribute("cognome", surname);
            String[] to = {email};
            emailManagerEJB.sendEmail(to, "cambio info personali", Action.getPath("userchangeinfo").replace("@utente", name + " " + surname));
            response.sendRedirect("profile.jsp?esitoedit=OK");
        } else {
            response.sendRedirect("profile.jsp?esitoedit=KO");
        }

    }

    protected void checkPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pwd = request.getParameter("pwd");
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        if (checkPassword(pwd)) {
            response.getWriter().write("KO");
        } else {
            response.getWriter().write("OK");
        }
    }

    private boolean checkPassword(String pass) {
        Pattern upperletter = Pattern.compile("[A-Z]");
        Pattern lowerletter = Pattern.compile("[a-z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

        Matcher hasUpperLetter = upperletter.matcher(pass);
        Matcher hasLowerLetter = lowerletter.matcher(pass);
        Matcher hasDigit = digit.matcher(pass);
        Matcher hasSpecial = special.matcher(pass);

        if (!hasUpperLetter.find()) {
            return false;
        }
        if (!hasLowerLetter.find()) {
            return false;
        }
        if (!hasDigit.find()) {
            return false;
        }
        if (!hasSpecial.find()) {
            return false;
        }

        return true;

    }

    protected void showDoc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getParameter("path");
        File downloadFile = new File(path);

        if (downloadFile.exists()) {
            FileInputStream inStream = new FileInputStream(downloadFile);
            String mimeType = Files.probeContentType(downloadFile.toPath());
            if (mimeType == null) {
                mimeType = "application/pdf";
            }
            response.setContentType(mimeType);
            String headerKey = "Content-Disposition";
            String headerValue = String.format("inline; filename=\"%s\"", downloadFile.getName());
            response.setHeader(headerKey, headerValue);
            OutputStream outStream = response.getOutputStream();
            byte[] buffer = new byte[4096 * 4096];
            int bytesRead = -1;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            inStream.close();
            outStream.close();
        } else {
            response.sendRedirect("");
        }
    }

    private void downloadDoc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getParameter("path");

        File downloadFile = new File(path);

        if (downloadFile.exists()) {
            FileInputStream inStream = new FileInputStream(downloadFile);
            String mimeType = Files.probeContentType(downloadFile.toPath());
            if (mimeType == null) {
                mimeType = "application/pdf";
            }
            response.setContentType(mimeType);
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attach; filename=\"%s\"", downloadFile.getName());
            response.setHeader(headerKey, headerValue);
            OutputStream outStream = response.getOutputStream();
            byte[] buffer = new byte[4096 * 4096];
            int bytesRead = -1;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            inStream.close();
            outStream.close();
//            
        } else {
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String link_value = Utility.verifySession(request);
        if (link_value != null) {
            redirect(request, response, link_value);
        } else {
            try {
                response.setContentType("text/html;charset=UTF-8");
                String type = request.getParameter("type");

                if ((String) request.getSession().getAttribute("username") == null) {
                    response.sendRedirect("login.jsp");
                } else {
                    String user = (String) request.getSession().getAttribute("username");
                    int stato = (int) request.getSession().getAttribute("status");
                    int tipo = (int) request.getSession().getAttribute("tipo");
                    if (user == null && stato != 3) {
                        redirect(request, response, "Login?type=2");
                    } else {
                        if (type.equals("2")) {
                            changePasswordProfile(request, response);
                        }
                        if (type.equals("3")) {
                            changeOperatorProfile(request, response);
                        }
                        if (type.equals("4")) {
                            checkPassword(request, response);
                        }
                        if (type.equals("5")) {
                            showDoc(request, response);
                        }
                        if (type.equals("6")) {
                            downloadDoc(request, response);
                        }
                    }
                }
            } catch (ServletException | IOException ex) {
               LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                Action.insertTracking("Rendicontazione Service", "ProcessRequest  " + ex.getMessage());
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
