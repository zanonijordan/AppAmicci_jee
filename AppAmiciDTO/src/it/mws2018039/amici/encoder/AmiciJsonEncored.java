/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.mws2018039.amici.encoder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import it.mws2018039.amici.dto.AmiciDTO;
import it.mws2018039.amici.exception.AppAmiciException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author er
 */
public class AmiciJsonEncored {
    public static String toJson( AmiciDTO dto ) throws AppAmiciException{
        String json = null;
        try {
            json = new ObjectMapper().writeValueAsString(dto);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            throw new AppAmiciException("Conversione da DTO a JSON non riuscita");
        }
        return json;
    }
    
    public static String toJson( ArrayList<AmiciDTO> dtoList ) throws AppAmiciException{
        String json = null;
        try {
            json = new ObjectMapper().writeValueAsString(dtoList);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            throw new AppAmiciException("Conversione da ArrayList<DTO> a JSON non riuscita");
        }
        return json;
    }
    
    public static AmiciDTO toAmiciDTO( String json ) throws AppAmiciException{
        AmiciDTO dto = null;
        try {
            dto = new ObjectMapper().readValue(json, AmiciDTO.class);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new AppAmiciException("Conversione da JSON a DTO non riuscita");
        }
        return dto;
    }
    
    public static ArrayList<AmiciDTO> toAmiciDTOList( String json ) throws AppAmiciException{
        ArrayList<AmiciDTO> lst = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            CollectionType javaType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, AmiciDTO.class);
            
            lst = mapper.readValue(json, javaType);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AppAmiciException("Conversione da JSON a DTO non riuscita");
        }
        return lst;
    }
            
}
