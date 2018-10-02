/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.control;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author kevin
 */
public class calculadora extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        DecimalFormat df = new DecimalFormat("#.##");
        Gson gSon = new Gson();
        String operadores = request.getParameter("operadores");
        Double resultado = .0;
        String exp = "^-?[0-9]+([.][0-9]+)?";
        String soperadorx = request.getParameter("operadorx").trim();
        String soperadory = request.getParameter("operadory").trim();

        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            if (soperadorx.matches(exp) && soperadory.matches(exp)) {
                Double operadorx = Double.parseDouble(request.getParameter("operadorx"));
                Double operadory = Double.parseDouble(request.getParameter("operadory"));
                if (operadores.equals("")) {
                    throw new IllegalArgumentException();
                } else {

                    switch (operadores) {
                        case "sumar":
                            resultado = (operadorx + operadory);
                            break;
                        case "restar":
                            resultado = (operadorx - operadory);
                            break;
                        case "multiplicar":
                            resultado = (operadorx * operadory);
                            break;
                        case "dividir":
                            if (operadory == 0) {
                                throw new NumberFormatException();
                            }
                            resultado = (operadorx / operadory);
                            break;
                        default:
                            throw new IllegalArgumentException();
                    }
                }
                String strJson = gSon.toJson(df.format(resultado));
                out.print(strJson);

            } else {
                throw new IllegalArgumentException();
            }
        } catch (NumberFormatException nfe) {
            response.setStatus(500);
            String error = "El divisor de una division no puedes ser 0.";
            String strJson = gSon.toJson(error);
            out.print(strJson);
        } catch (IllegalArgumentException iae) {
            response.setStatus(500);
            String error;
            if (soperadorx.equals("") || soperadory.equals("")) {
                error = "No puede dejar campos vacios.";
            } else if (!(operadores.equals("sumar")) || !(operadores.equals("restar")) || !(operadores.equals("multiplicar")) || !(operadores.equals("dividir")) ) {
                error = "Debes seleccionar un operador";
            } else {
                error = "Debes introducir solo numeros.";
            }
            String strJson = gSon.toJson(error);
            out.print(strJson);
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
