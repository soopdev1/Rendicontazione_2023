
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seta.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.util.logging.*;
/**
 *
 * @author raffaele
 */
public class Utility {
	private static final Logger LOGGER = Logger.getLogger( Utility.class.getName() );

	public static String setDecalPoint(String s) {
        String out = "";
        String tmp;
        String cents = s.substring(s.lastIndexOf(".") + 1);
        if (cents.equals("0")) {
            cents = "00";
        }
        s = s.substring(0, s.lastIndexOf("."));
        char[] c = s.toCharArray();
        int cont = 1;
        for (int i = c.length; i > 0; i--) {
            tmp = out;
            out = "";
            out += c[i - 1];
            out += tmp;
            if (cont % 3 == 0) {
                tmp = out;
                out = ".";
                out += tmp;
            }
            cont++;
        }
        if (out.startsWith(".")) {
            out = out.substring(1, out.length());
        }
        out = out + "," + cents;
        return out;
    }

    public static String ctrlString(String a) {
        if (a == null) {
            return "-";
        }
        return a;
    }

    public static String ctrlInt(String a) {
        if (a == null) {
            return "0";
        }
        return a;
    }

    public static String convMd5(String psw) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(psw.getBytes());
            byte byteData[] = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString().trim();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "-";
        }
    }

    public static String randomP() {
        final SecureRandom random = new SecureRandom();
        String r = new BigInteger(130, random).toString(32);
        r = r.substring(0, 6);
        r = r + "!1";
        return r;
    }

    public static String generaId() {
        String random = RandomStringUtils.randomAlphanumeric(5).trim();
        return new DateTime().toString("yyMMddHHmmssSSS") + random;
    }

    public static String generaTokenSito() {
        String random = RandomStringUtils.randomAlphanumeric(10).trim();
        return random;
    }

    public static boolean isNumeric(String val) {
        return StringUtils.isNumeric(val);
    }

    public static String encodeToString(String path, String type) throws IOException {

        BufferedImage image = ImageIO.read(new File(path));
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();

            imageString = Base64.encodeBase64String(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }

    public static void redirect(HttpServletRequest request, HttpServletResponse response, String destination) throws ServletException, IOException {
        if (response.isCommitted()) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(destination);
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(destination);
        }
    }

    public static String formatStringtoStringDate(String dat, String pattern1, String pattern2) {
        try {
            return new SimpleDateFormat(pattern2).format(new SimpleDateFormat(pattern1).parse(dat));
        } catch (Exception e) {
        }
        return "No correct date";
    }

    public static int parseIntR(String value) {
        value = value.replaceAll("-", "").trim();
        if (value.contains(".")) {
            StringTokenizer st = new StringTokenizer(value, ".");
            value = st.nextToken();
        }
        int d1 = 0;
        try {
            d1 = Integer.parseInt(value);
        } catch (Exception e) {
            e.printStackTrace();
            d1 = 0;
        }
        return d1;
    }

    public static void printRequest(HttpServletRequest request) throws ServletException, IOException {
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            for (int i = 0; i < paramValues.length; i++) {
                LOGGER.log(Level.INFO,paramName + ": " + paramValues[i]);
            }
        }

    }

    public static String verifySession(HttpServletRequest req) {
        if (req.getSession() != null) {
            String tip = (String) req.getSession().getAttribute("username");
//            String cod = (String) req.getSession().getAttribute("us_cod");
//            if (tip == null || cod == null) {
            if (tip == null) {
                return "login.jsp";
            }
        } else {
            return "login.jsp";
        }
        return null;
    }

    public static String correggi(String ing) {
        if (ing != null) {
            ing = ing.replaceAll("\\\\", "/");
            ing = ing.replaceAll("\n", "");
            ing = ing.replaceAll("\r", "");
            ing = ing.replaceAll("\t", "");
            ing = ing.replaceAll("'", "\'");
            ing = ing.replaceAll("“", "\'");
            ing = ing.replaceAll("‘", "\'");
            ing = ing.replaceAll("”", "\'");
            ing = ing.replaceAll("\"", "/");
            return ing.replaceAll("\"", "\'");
        } else {
            return "-";
        }
    }

    public static String CaratteriSpeciali(String ing) {
        if (ing != null) {
            ing = StringUtils.replace(ing, "Ã©", "è");
            ing = StringUtils.replace(ing, "Ã¨", "é");
            ing = StringUtils.replace(ing, "Ã¬", "ì");
            ing = StringUtils.replace(ing, "Ã²", "ò");
            ing = StringUtils.replace(ing, "Ã¹", "ù");
            ing = StringUtils.replace(ing, "Ã", "à");
            return ing;
        } else {
            return "-";
        }
    }

    public static String CaratteriSpecialifromDB(String ing) {
        if (ing != null) {
            ing = StringUtils.replace(ing, "è", "Ã©");
            ing = StringUtils.replace(ing, "é", "Ã¨");
            ing = StringUtils.replace(ing, "ì", "Ã¬");
            ing = StringUtils.replace(ing, "ò", "Ã²");
            ing = StringUtils.replace(ing, "ù", "Ã¹");
            ing = StringUtils.replace(ing, "à", "Ã");
            return ing;
        } else {
            return "-";
        }
    }

    public static String passaLink(String ing) {
        if (ing != null) {
            ing = StringUtils.replace(ing, "&", "%26");
            ing = StringUtils.replace(ing, "?", "%3F");
            ing = StringUtils.replace(ing, "%", "%25");
            ing = StringUtils.replace(ing, "<", "%3C");
            ing = StringUtils.replace(ing, ">", "%3E");
            ing = StringUtils.replace(ing, "=", "%3D");
            return ing;
        } else {
            return "-";
        }
    }

    public static String passaLinkDecodifica(String ing) {
        if (ing != null) {
            ing = StringUtils.replace(ing, "%26", "&");
            ing = StringUtils.replace(ing, "%3F", "?");
            ing = StringUtils.replace(ing, "%25", "%");
            ing = StringUtils.replace(ing, "%3C", "<");
            ing = StringUtils.replace(ing, "%3E", ">");
            ing = StringUtils.replace(ing, "%3D", "=");
            ing = StringUtils.replace(ing, "%22", "\"");
            ing = StringUtils.replace(ing, "%23", "#");
            ing = StringUtils.replace(ing, "%24", "$");
            ing = StringUtils.replace(ing, "%27", "\'");
            ing = StringUtils.replace(ing, "%28", "(");
            ing = StringUtils.replace(ing, "%29", ")");
            ing = StringUtils.replace(ing, "%2A", "*");
            ing = StringUtils.replace(ing, "%2B", "+");
            ing = StringUtils.replace(ing, "%2C", ",");
            ing = StringUtils.replace(ing, "%2D", "-");
            ing = StringUtils.replace(ing, "%2E", ".");
            ing = StringUtils.replace(ing, "%2F", "/");
            ing = StringUtils.replace(ing, "%5B", "[");
            ing = StringUtils.replace(ing, "%5D", "]");
            ing = StringUtils.replace(ing, "%5C", "\\");
            ing = StringUtils.replace(ing, "%5E", "^");
            ing = StringUtils.replace(ing, "%5F", "_");
            ing = StringUtils.replace(ing, "%60", "`");
            ing = StringUtils.replace(ing, "%7C", "|");
            ing = StringUtils.replace(ing, "%80", "€");
            ing = StringUtils.replace(ing, "%BD", "½");
            ing = StringUtils.replace(ing, "%7E", "~");
            ing = StringUtils.replace(ing, "%7B", "{");
            ing = StringUtils.replace(ing, "%7D", "}");
            ing = StringUtils.replace(ing, "%89", "‰");
            ing = StringUtils.replace(ing, "%C2%A3", "£");
            ing = StringUtils.replace(ing, "%C2%A7", "§");
            ing = StringUtils.replace(ing, "%C2%B0", "°");
            ing = StringUtils.replace(ing, "%C3%A7", "ç");
            ing = StringUtils.replace(ing, "%E9", "é");
            return ing;
        } else {
            return "-";
        }
    }

    public static int getDayDfference(Date d1, Date d2) {
        return (int) TimeUnit.DAYS.convert(d2.getTime() - d1.getTime(), TimeUnit.MILLISECONDS);
    }

    public static int getDayDfference(String d1, String d2, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return getDayDfference(sdf.parse(d1), sdf.parse(d2));
        } catch (ParseException ex) {

        }
        return 0;
    }
}
