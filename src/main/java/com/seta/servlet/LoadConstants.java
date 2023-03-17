package com.seta.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.seta.util.ConstantsSingleton;

/**
 * Servlet implementation class LoadConstants
 */
@WebServlet(urlPatterns = "/LoadConstants", loadOnStartup = 1)
public class LoadConstants extends HttpServlet {

	private static final long serialVersionUID = -6124826149192544774L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		ConstantsSingleton.getInstance();
	}
}
