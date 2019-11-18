/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.mws2018039.service;

import it.mws2018039.amici.dto.AmiciDTO;
import it.mws2018039.amici.encoder.AmiciJsonEncored;
import it.mws2018039.amici.exception.AppAmiciException;
import it.mws2018039.storag.AppAmiciStorage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author er
 */
public class AmiciService extends HttpServlet {
    public static final String CMD                  = "CMD";
    
    public static final String CMD_GET_ELENCO_AMICI = "GET_ELENCO_AMICI";
    public static final String CMD_IMPOSTA_POSIZIONE= "IMPOSTA_POSIZIONE";
    public static final String CMD_SET_ONLINE       = "SET_ONLINE";
    public static final String CMD_CHECK_ACCOUNT    = "CHECK_ACCOUN";
    public static final String PAR_SE_ONLINE        = "SE_ONLINE";
    public static final String PAR_IP_ID            = "IP_ID";
    public static final String PAR_IP_LAT           = "IP_LAT";
    public static final String PAR_IP_LNG           = "IP_LNG";
    
    public static final String PAR_USR              = "USR";
    public static final String PAR_PSW              = "PSW";

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
        response.setContentType("application/json;charset=UTF-8");
        
        String cmd = request.getParameter( AmiciService.CMD );
        System.out.println("CMD: "+ cmd);
        
        if( cmd!=null && cmd.equals( AmiciService.CMD_GET_ELENCO_AMICI ) ) {
            processRequest_CMD_GET_ELENCO_AMICI(request, response);
        }
        
        if( cmd!=null && cmd.equals( AmiciService.CMD_IMPOSTA_POSIZIONE ) ) {
            processRequest_CMD_IMPOSTA_POSIZIONE(request, response);
        }
        
        if( cmd!=null && cmd.equals( AmiciService.CMD_SET_ONLINE ) ) {
            processRequest_CMD_SET_ONLINE(request, response);
        }
        if( cmd!=null && cmd.equals( AmiciService.CMD_CHECK_ACCOUNT ) ) {
            processRequest_CMD_CHECK_ACCOUNT(request, response);
        }
    }

    protected void processRequest_CMD_GET_ELENCO_AMICI(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String json = "ERROR";
        try {
            ArrayList<AmiciDTO> amiciList = AppAmiciStorage.getInstance().getElencoAmici();
            json = AmiciJsonEncored.toJson(amiciList);
        } catch (AppAmiciException ex) {
            ex.printStackTrace();
            return;
        }
        response.getWriter().print( json );
    }
    
    protected void processRequest_CMD_IMPOSTA_POSIZIONE(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String idStr  = request.getParameter( AmiciService.PAR_IP_ID );
            String latStr = request.getParameter( AmiciService.PAR_IP_LAT );
            String lngStr = request.getParameter( AmiciService.PAR_IP_LNG );
            
            System.out.println("idStr:  "+ idStr);
            System.out.println("latStr: "+ latStr);
            System.out.println("lngStr: "+ lngStr);
            
            String ret = "ERROR";
            try{
                int id      = new Integer(idStr);
                double lat  = new Double(latStr);
                double lng  = new Double(lngStr);
                int updVal = AppAmiciStorage.getInstance().impostaPosizione(id, lat, lng);
                if( updVal==1 ) ret = "OK";
            } catch( Exception e ){
                System.out.println("ERRORE----> "+ e.getMessage());
                e.printStackTrace();
            }
            
            response.getWriter().print( ret );
    }
    
    protected void processRequest_CMD_SET_ONLINE(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String idStr       = request.getParameter( AmiciService.PAR_IP_ID );
            String seOnlineStr = request.getParameter( AmiciService.PAR_SE_ONLINE );
            
            System.out.println("idStr:    "+ idStr);
            System.out.println("seOnline: "+ seOnlineStr);
            
            String ret = "ERROR";
            try{
                int id        = new Integer(idStr);
                int seOnline  = new Integer(seOnlineStr);
                int updVal = AppAmiciStorage.getInstance().impostaSeOnline(id, seOnline);
                if( updVal==1 ) ret = "OK";
            } catch( Exception e ){
                System.out.println("ERRORE----> "+ e.getMessage());
                e.printStackTrace();
            }
            
            response.getWriter().print( ret );
    }
    
    protected void processRequest_CMD_CHECK_ACCOUNT(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String usr = request.getParameter( AmiciService.PAR_USR );
            String psw = request.getParameter( AmiciService.PAR_PSW );
            
            System.out.println("usr: "+ usr);
            System.out.println("psw: "+ psw);
            
            String ret = "ERROR";
            try{
                int id = AppAmiciStorage.getInstance().verificaCredenziali(usr, psw);
                if(id>0) {
                    ret = ""+id;
                }
            } catch( Exception e ){
                System.out.println("ERRORE----> "+ e.getMessage());
                e.printStackTrace();
            }
            
            response.getWriter().print( ret );
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
