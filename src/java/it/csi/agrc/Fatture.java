package it.csi.agrc;
import it.csi.cuneo.*;
import java.util.*;
import java.sql.*;

/**
 * Title:        Agrichim - Front Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class Fatture extends BeanDataSource
{
  private Vector fatture = new Vector();

  /**
   * indica il max numero di record che posso visualizzare in una pagina
   */
  private int passo=15;

  /**
   * indica la posizione del primo record che verrà visualizzato nella
   * pagina
   */
  private int baseElementi;

  /**
   * indica il numero di record totali che dovrei visualizzare
   */
  private int numRecord;

  private String cancella;

  public Fatture()
  {
  }

  /**
   * Questo metodo viene utilizzato per leggere i record che vengono ricercati
   * dall'utente
   * */
  public void fillFatture()
      throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    String query=this.getAut().getQueryRicerca();

    String queryCount=this.getAut().getQueryCountRicerca();
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
     // CuneoLogger.debug(this, "queryCount "+queryCount);
      ResultSet rset = stmt.executeQuery(queryCount);
      if (rset.next())
        this.numRecord=rset.getInt("NUM");
      rset.close();

      int inizio,fine;
      if ( this.baseElementi == 0) inizio=1;
        else inizio=this.getBaseElementi();
      if ( this.passo == 0 ) fine=size();
        else fine=inizio+this.getPasso()-1;
      query+=" WHERE NUM BETWEEN "+inizio+" AND "+fine;

      rset = stmt.executeQuery(query);
 
      //Creiamo un vettore momentaneo
      Vector fatture = new Vector();
     
      while (rset.next())
      {
      	fatture.add(new Fattura(
                                  rset.getString("ANNO"),
                                  rset.getString("NUMERO_FATTURA"),
                                  rset.getString("DATA_FATTURA"),
                                  rset.getString("PAGATA"),
                                  rset.getString("ANNULLATA"),
                                  rset.getString("PARTITA_IVA_O_CF"),
                                  rset.getString("RAGIONE_SOCIALE"),
                                  rset.getString("EE")
                                  )
                                  );
      }
      rset.close();
      stmt.close();
      //Metodo per 'compattare' le fatture con lo stesso anno e numeroFattura JIRA 100
      String numeroCampione = "";
      if(fatture.size()>0)
      {
   	 	int k=0;
   	 	do
   	 	{
      	 numeroCampione = ((Fattura) fatture.get(k)).getCampioni();
      	 for (int i = k+1; i <= fatture.size(); i++)
      	 {
		   		 k=k+1;
		   		 Fattura fp = (Fattura) fatture.get(i-1);
		   		 if(i!=fatture.size())
		   		 {
		   			 Fattura fs = (Fattura) fatture.get(i);
		   		 
		   			 String chiave1 = fp.getAnno()+fp.getNumeroFattura();
		   			 String chiave2 = fs.getAnno()+fs.getNumeroFattura();
		   		
		   			 if(chiave1.equals(chiave2)) // devo concatenare il numero campione
		   			 { 
		   				 numeroCampione= numeroCampione +"\n" + fs.getCampioni();
		   			 }
		   			 else
		   			 {
		   				 
		   				 Fattura fTOT = new Fattura(fp.getAnno(),fp.getNumeroFattura(),fp.getDataFattura(),
		   						 fp.getPagata(),fp.getAnnullata(), fp.getPartitaIvaOCf(),fp.getRagioneSociale(),numeroCampione);
		   				 add(fTOT);
		   				 break;
		   			 }
		   		 }
		   			 else{
		   		      Fattura fTOT = new Fattura(fp.getAnno(),fp.getNumeroFattura(),fp.getDataFattura(),
		   							 fp.getPagata(),fp.getAnnullata(), fp.getPartitaIvaOCf(),fp.getRagioneSociale(),numeroCampione);
		   					add(fTOT);
		   			 }
		   	 }
		   	 }
		   	while(k<fatture.size());
      }

    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("fillFatture della classe Fatture");
      this.getAut().setContenutoQuery(query);
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("fillFatture della classe Fatture"
                             +": non è una SQLException ma una Exception"
                             +" generica");
      this.getAut().setContenutoQuery(query);
      throw (e);
    }
    finally
    {
      if (conn!=null) conn.close();
    }
  }


  /**
   * Questo metodo viene utilizzato per estrarre l'elenco delle fatture con cui
   * costruire il pdf
   * */
  public void elencoFatture()
      throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    String query=this.getAut().getQueryRicerca();
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      //CuneoLogger.debug(this, "query "+query);
      ResultSet rset = stmt.executeQuery(query);

      while (rset.next())
      {
        add(new Fattura(
                                  rset.getString("ANNO"),
                                  rset.getString("NUMERO_FATTURA"),
                                  rset.getString("DATA_FATTURA"),
                                  rset.getString("PAGATA"),
                                  rset.getString("ANNULLATA"),
                                  rset.getString("PARTITA_IVA_O_CF"),
                                  rset.getString("RAGIONE_SOCIALE"),
                                  rset.getString("INDIRIZZO"),
                                  rset.getString("CAP"),
                                  rset.getString("COMUNE"),
                                  rset.getString("SIGLA_PROVINCIA"),
                                  rset.getString("IMPORTO_SPEDIZIONE"),
                                  rset.getString("IMPORTO_IMPONIBILE"),
                                  rset.getString("IMPORTO_IVA")
                                  )
                                  );
      }
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("elencoFatture della classe Fatture");
      this.getAut().setContenutoQuery(query);
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("elencoFatture della classe Fatture"
                             +": non è una SQLException ma una Exception"
                             +" generica");
      this.getAut().setContenutoQuery(query);
      throw (e);
    }
    finally
    {
      if (conn!=null) conn.close();
    }
  }

  public int size()
  {
    return fatture.size();
  }

  public Fattura get(int i)
  {
    return (Fattura)fatture.elementAt(i);
  }

  public void add(Fattura i)
  {
    fatture.addElement(i);
  }

  public String getCancella()
  {
      return cancella;
  }

  public void setCancella(String newCancella)
  {
      cancella = newCancella;
  }

  public int getPasso()
  {
    return passo;
  }

  public void setPasso(int newPasso)
  {
    passo = newPasso;
  }

  public int getBaseElementi()
  {
    return baseElementi;
  }

  public void setBaseElementi(int newBaseElementi)
  {
    baseElementi = newBaseElementi;
  }

  public int getNumRecord()
  {
    return numRecord;
  }

}