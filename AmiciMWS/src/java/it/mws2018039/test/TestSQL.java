/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.mws2018039.test;

import it.mws2018039.amici.dto.AmiciDTO;
import it.mws2018039.storag.AppAmiciStorage;
import java.util.ArrayList;

/**
 *
 * @author er
 */
public class TestSQL {
    public static void main(String[] args) {
        //ArrayList<AmiciDTO> ret = AppAmiciStorage.getInstance().getElencoAmici();
        int ret = AppAmiciStorage.getInstance().impostaPosizione(2, 1.23D, 4.6540D);
        System.out.println("ret: "+ ret);
        
    }
}
