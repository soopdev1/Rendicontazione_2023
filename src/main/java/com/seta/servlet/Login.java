/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seta.servlet;

import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.seta.activity.Action;
import com.seta.ejb.EmailManagerEJB;
import com.seta.entity.Operator;
import com.seta.util.Utility;
import java.util.logging.*;

/**
 *
 * @author agodino
 */
public class Login extends HttpServlet {
	private static final Logger LOGGER = Logger.getLogger( Login.class.getName() );
	private static final long serialVersionUID = 8247572363420013664L;

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
    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("username");
        String pass = request.getParameter("password");
        if (String.valueOf(user) != null && pass != null) {
            if (!String.valueOf(user).trim().equals("") && !pass.trim().equals("")) {
                String password = Utility.convMd5(pass);

                Operator ub = Action.getOperatore(user, password);

                if (ub != null) {
                    request.getSession().setAttribute("id_operatore", ub.getIdoperatore());
                    request.getSession().setAttribute("tipo", ub.getTipo());
                    request.getSession().setAttribute("nome", ub.getNome());
                    request.getSession().setAttribute("cognome", ub.getCognome());
                    request.getSession().setAttribute("status", ub.getStato());
                    //request.getSession().setAttribute("OBJente", ub.getEnte());
                    request.getSession().setAttribute("idente", ub.getEnte());
                    if(ub.getEnte()==0){
                        request.getSession().setAttribute("ente", "Regione Calabria");
                    }else{
                        request.getSession().setAttribute("cf_ente", ub.getEntePromotore().getCf());
                        request.getSession().setAttribute("ente", ub.getEntePromotore().getRagioneSociale());
                    }
                    
                    if (ub.getStato() == 0 || ub.getStato() == 2) {
                        request.getSession().setAttribute("username", ub.getUsername());
                        request.getSession().setAttribute("id", ub.getIdoperatore());
                        Action.insertTracking((String) request.getSession().getAttribute("username"), "First Access");
                        response.sendRedirect("login.jsp?esito=FIRSTACCESS&state=" + ub.getStato());
                    } else if (ub.getTipo() == 1) {
                        if (ub.getStato() != 3) {
                            request.getSession().setAttribute("username", ub.getUsername());
                            request.getSession().setAttribute("id", ub.getIdoperatore());
                            //request.getSession().setAttribute("status", ub.getType());
                            Action.insertTracking((String) request.getSession().getAttribute("username"), "Log In");
                            Utility.redirect(request, response, "index_Regional.jsp");
                        } else {
                            response.sendRedirect("login.jsp?esito=banned");
                        }
                    } else if (ub.getTipo() == 2) {
                        if (ub.getStato() != 3) {
                            request.getSession().setAttribute("username", ub.getUsername());
                            request.getSession().setAttribute("id", ub.getIdoperatore());
                            //request.getSession().setAttribute("status", ub.getType());
                            Action.insertTracking((String) request.getSession().getAttribute("username"), "Log In");
                            Utility.redirect(request, response, "index_Ente.jsp");
                        } else {
                            response.sendRedirect("login.jsp?esito=banned");
                        }
                    } else if (ub.getTipo() == 3) {
                        if (ub.getStato() != 3) {
                            request.getSession().setAttribute("username", ub.getUsername());
                            request.getSession().setAttribute("id", ub.getIdoperatore());
                            //request.getSession().setAttribute("status", ub.getType());
                            Action.insertTracking((String) request.getSession().getAttribute("username"), "Log In");
                            Utility.redirect(request, response, "index_Reviser.jsp");
                        } else {
                            response.sendRedirect("login.jsp?esito=banned");
                        }
                    } else {
                        response.sendRedirect("login.jsp?esito=banned");
                    }
                } else {
                    Action.insertTracking(user, "LoginErr");
                    response.sendRedirect("login.jsp?esito=KO1");
                }
            }
        }
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //String user = request.getParameter("id_operatore");
        Action.insertTracking((String) request.getSession().getAttribute("username"), "log_out");
        request.getSession().setAttribute("username", null);
        request.getSession().invalidate();
        response.sendRedirect("login.jsp");
    }

    protected void resetpsw(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mail = request.getParameter("email");
        String user = request.getParameter("username");
        Operator infos = Action.getDataReset(mail, user);
        Action.insertTracking(user, "Reset Password");
        if (infos != null) {
            if (infos.getStato() != 5 && infos.getEmail() != null && infos.getUsername() != null) {
                //CREATE A RANDOM PASSWORD START//
                String SALTCHARS = "abcdefghilmnopqrsrtuvzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_.";
                StringBuilder pass = new StringBuilder();
                Random rnd = new Random();
                while (pass.length() < 8) { // length of the random string.
                    int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                    pass.append(SALTCHARS.charAt(index));
                }
                String newpsw = pass.toString();
                //END
                String to[] = {mail};
                String testoMail = Action.getPath("userrecoverspassword");
                testoMail = StringUtils.replace(testoMail, "@Username", infos.getUsername());
                testoMail = StringUtils.replace(testoMail, "@Password", newpsw);
                testoMail = StringUtils.replace(testoMail, "@utente", infos.getNome() + " " + infos.getCognome());
                if (emailManagerEJB.sendEmail(to, Action.getPath("userresetpswObject"), testoMail)) {
                    if (Action.resetPassword(newpsw, user)) {
                        response.sendRedirect("login.jsp?esito=OK");
                    }
                } else {
                    Action.insertTracking(user, "Reset Password, Impossibile inviare mail");
                    response.sendRedirect("login.jsp?esito=KO3");
                }
            } else {
                Action.insertTracking(user, "Reset Password, Username and Email don't match");
                response.sendRedirect("login.jsp?esito=KO3");
            }
        } else {
            Action.insertTracking(user, "Reset Password, Username and Email don't match");
            response.sendRedirect("login.jsp?esito=KO3");
        }
    }

    protected void changePsw(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actualpsw = request.getParameter("actualpsw");
        String newpsw = request.getParameter("newpsw");
        String newpsw1 = request.getParameter("newpsw1");
        String user = request.getSession().getAttribute("username").toString();
        String actualpswindb = Action.getPasswordFromUser(user);
        Action.insertTracking(user, "Cambio Password ");
        if (newpsw.length() > 7 && checkPassword(newpsw)) {
            if (actualpswindb.equals(Utility.convMd5(actualpsw)) && newpsw.equals(newpsw1)) {
                boolean esito = Action.changePasswordFA(user, actualpsw, newpsw);
                if (esito) {
                    Action.setStatusFA(user);
                    response.sendRedirect("login.jsp?esito=OK&state=NEWCRED");
                } else {
                    Action.insertTracking(user, "Cambio Password Errore");
                    response.sendRedirect("login.jsp?esito=FIRSTACCESS&state=EPASS");
                }
            } else {
                Action.insertTracking(user, "Cambio Password  l e 2 password non coincidono");
                response.sendRedirect("login.jsp?esito=FIRSTACCESS&state=2PASS");
            }
        } else {
            Action.insertTracking(user, "Cambio Password Errore password non conforme ");
            response.sendRedirect("login.jsp?esito=FIRSTACCESS&state=PASS");
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

    }//checkPassword
    
     protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            response.setContentType("text/html;charset=UTF-8");
            String type = request.getParameter("type");

            if (type.equals("1")) {
                login(request, response);
            }
            if (type.equals("2")) {
                logout(request, response);
            }
            if (type.equals("3")) {
                resetpsw(request, response);
            }
            if (type.equals("4")) {
                changePsw(request, response);
            }
        } catch (ServletException | IOException ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
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
