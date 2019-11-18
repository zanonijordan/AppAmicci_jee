/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.mws2018039.amici.test;

import it.mws2018039.amici.encoder.AmiciJsonEncored;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.mws2018039.amici.dto.AmiciDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author er
 */
public class TestJSON {
    public static void main(String[] args) {
        
        ArrayList<AmiciDTO> aList = new ArrayList<>();
        for(int i=0; i<3; i++){
            AmiciDTO a = new AmiciDTO();
            a.setId(1);
            a.setCognome("Rizzo_"+i);
            a.setNome("Emanuele");
            a.setNickname("lele");
            a.setLat(25.123123D);
            a.setLng(22.321321D);
            a.setSeOnline(i);
            aList.add( a );
        }
        
        String json = null;
        try {
            json = AmiciJsonEncored.toJson( aList );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("json: "+ json);
        
        ArrayList<AmiciDTO> bList = null;
        try {
           bList = AmiciJsonEncored.toAmiciDTOList( json );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        System.out.println("size: "+ bList.size());
        AmiciDTO b1 = bList.get(0);
        System.out.println("cognome: "+ b1.getCognome());
    }
}
