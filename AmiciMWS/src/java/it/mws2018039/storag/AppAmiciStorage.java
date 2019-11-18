/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.mws2018039.storag;

import it.mws2018039.amici.dto.AmiciDTO;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author er
 */
public class AppAmiciStorage {
    private static AppAmiciStorage INSTANCE = null;
    private Connection conn = null;
    private static final String conn_str = "jdbc:mysql://192.168.1.159/db_amici";
    private static final String username = "app_amici";
    private static final String password = "java";

    private AppAmiciStorage(){
        try {
            System.out.print("\nAvvio connesione al DB...");
            this.conn = DriverManager.getConnection(conn_str, username, password);
            System.out.print("OK\n");
        } catch (SQLException ex) {
            System.out.print("ERROR DB NON ACCESSIBILE: "+ex+"\n");
        }
    }
    public static AppAmiciStorage getInstance(){
        if( INSTANCE==null ) INSTANCE = new AppAmiciStorage();
        return INSTANCE;
    }
    
    public int verificaCredenziali( String u, String p ){
        int ret = -1;
        
        try{    // poi gestiremo il rilancio dell'eccezione
            String query = "select * from utenti where username = '"+u+"' and password = '"+p+"'";
            
            Statement s = conn.createStatement();
            System.out.println("executing (non dorvrei stamparla nei log): "+ query);   
            ResultSet r = s.executeQuery(query);

            AmiciDTO currS;
            if( r.next() ){
                ret=r.getInt("id");
            }
        } catch( Exception e ){
            System.out.println("ERRORE: "+ e);
        }
        return ret;
    }
    
    public ArrayList<AmiciDTO> getElencoAmici(){
        ArrayList<AmiciDTO> lst = new ArrayList<>();
        
        try{    // poi gestiremo il rilancio dell'eccezione
            String query = "SELECT * FROM amici";
            
            Statement s = conn.createStatement();
            System.out.println("executing: "+ query);   
            ResultSet r = s.executeQuery(query);

            AmiciDTO currS;
            while( r.next() ){
                currS = new AmiciDTO();
                currS.setId(        r.getInt(   "id"         ) );
                currS.setNome(      r.getString("nome"       ) );
                currS.setCognome(   r.getString("cognome"    ) );
                currS.setNickname(  r.getString("nickname"   ) );
                currS.setTel(       r.getString("tel"        ) );
                currS.setLat(       r.getDouble("lat"        ) );
                currS.setLng(       r.getDouble("lng"        ) );
                currS.setSeOnline(  r.getInt(   "se_online"  ) );
                currS.setUrlFoto(   r.getString("url_foto"   ) );

                lst.add( currS );
            }
        } catch( Exception e ){
            System.out.println("ERRORE: "+ e);
        }
        return lst;
    }
    
    public int impostaPosizione( int id, double lat, double lng ){
        int ret = 0;
        try{
            // update amici set lat=12.345, lng=32.432 where id=1
            String query = "UPDATE amici set lat="+lat+", lng="+lng+" WHERE id="+id;
            
            Statement st = conn.createStatement();
            System.out.println("executing: "+ query);
            ret = st.executeUpdate(query);

            System.out.println("record modificati: "+ ret);
            
        } catch (Exception e){
            System.out.println("Errore: "+e);
        } 
        return ret;
    }
    
    public int impostaSeOnline( int id, int seOnline ){
        int ret = 0;
        try{
            String query = "UPDATE amici set se_online="+seOnline+" WHERE id="+id;
            
            Statement st = conn.createStatement();
            System.out.println("executing: "+ query);
            ret = st.executeUpdate(query);

            System.out.println("record modificati: "+ ret);
            
        } catch (Exception e){
            System.out.println("Errore: "+e);
        } 
        return ret;
    }
}
