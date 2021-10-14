package it.csi.agrc;

import it.csi.cuneo.*;

import java.util.*;
import java.sql.*;


/**
 * <p>Title: Agrichim - Back Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class Parametro extends BeanDataSource
{
  private final String BOBA="BOBA";
  private final String VBCC="VBCC";
  private final String TXAS="TXAS";
  private final String TXDI="TXDI";
  private final String TXLA="TXLA";
  private final String TXSF="TXSF";
  private final String TXLC="TXLC";
  private final String TXT2="TXT2";
  //private final String TXT3="TXT3";
  private final String TXT5="TXT5";
  private final String TXT6="TXT6";
  private final String HBTO="HBTO";
  private final String HBAL="HBAL";
  private final String HBCE="HBCE";
  
  private final String HBCA="HBCA";
  
  private final String TXPI="TXPI";
  private final String GFEP="GFEP";
  private final String IVA1="IVA1";
  private final String IVA0="IVA0";
  private final String NUMR = "NUMR";
  private final String NUMC = "NUMC";
  private final String SIUS = "SIUS";
  private final String SIPW = "SIPW";
  private final String METALLI_PESANTI_SCONTO_NUMERO = "MTNM";
  private final String METALLI_PESANTI_SCONTO_PERCENTUALE = "MTPR";

  public void leggiParametri(BeanParametri parametri)
      throws Exception, SQLException
  {
    if (!isConnection())
           throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=this.getConnection();
    StringBuffer query=new StringBuffer("");
    Statement stmt = null;
    String idParametro=null;
    try
    {
    	
        stmt = conn.createStatement();
    
        query.append("SELECT ID_PARAMETRO, VALORE ");
        query.append("FROM PARAMETRO ");
      
        ResultSet rset = stmt.executeQuery(query.toString());
       
        HashMap hash=new HashMap();
      
        
        while (rset.next())
        {
          
          idParametro=rset.getString("ID_PARAMETRO");
         
          if (!idParametro.equals(SIPW)){	
        	
        	  hash.put(idParametro,rset.getString("VALORE"));
        	
          }
        }
        
        parametri.setBOBA((String)hash.get(BOBA));
        parametri.setVBCC((String)hash.get(VBCC));
        parametri.setAssessorato((String)hash.get(TXAS));
        parametri.setDirezione((String)hash.get(TXDI));
        parametri.setLabAgr((String)hash.get(TXLA));
        parametri.setSettore((String)hash.get(TXSF));
        parametri.setLabConsegna2((String)hash.get(TXLC));
        parametri.setTXT2((String)hash.get(TXT2));
        //parametri.setTXT3((String)hash.get(TXT3));
        parametri.setTXT5((String)hash.get(TXT5));
        parametri.setTXT6((String)hash.get(TXT6));
        parametri.setHBTO((String)hash.get(HBTO));
        parametri.setHBAL((String)hash.get(HBAL));
        parametri.setHBCE((String)hash.get(HBCE));
        parametri.setPartitaIVALab((String)hash.get(TXPI));
        parametri.setGFEP((String)hash.get(GFEP));
        parametri.setIVA0((String)hash.get(IVA0));
        parametri.setIVA1((String)hash.get(IVA1));
        parametri.setNUMR((String)hash.get(NUMR));
        parametri.setNUMC((String)hash.get(NUMC));
        parametri.setMetalliPesantiScontoNumero((String) hash.get(METALLI_PESANTI_SCONTO_NUMERO));
        parametri.setMetalliPesantiScontoPercentuale((String) hash.get(METALLI_PESANTI_SCONTO_PERCENTUALE));     
        parametri.setSIUS((String)hash.get(SIUS));
        parametri.setHBCA((String)hash.get(HBCA));
       
        
        rset.close();
       
        //Leggo la password per accedere a sigmater
        query=new StringBuffer("select pgp_sym_decrypt(valore_b,  '##agrc_sigmater++')");
        query.append("from parametro ");
        query.append("where id_parametro = 'SIPW' ");
        
        rset = stmt.executeQuery(query.toString());
        
        if (rset.next()){
        	
        	parametri.setSIPW(rset.getString(1));
        	
        }
        
        rset.close();
        stmt.close();
        
    }
    catch(java.sql.SQLException ex)
    {
    	
      this.getAut().setQuery("leggiParametri della classe Parametro");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    }
    catch(Exception e)
    {
    	
      this.getAut().setQuery("leggiParametri della classe Parametro"
                            +": non è una SQLException ma una Exception"
                            +" generica");
     
      e.printStackTrace();
      throw (e);
    }
    finally
    {
      if (conn!=null) conn.close();
    }
  }
}