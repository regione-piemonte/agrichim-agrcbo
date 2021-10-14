package it.csi.agrc;
import it.csi.cuneo.*;
import java.util.*;
import java.sql.*;
//import it.csi.jsf.web.pool.*;

/**
 * Title:        Agrichim - Front Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class OrganizzazioneProfessionali extends BeanDataSource
{
  private Vector organizzazioneProfessionali = new Vector();

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

  public OrganizzazioneProfessionali()
  {
  }

  /**
   * Questo metodo viene utilizzato per leggere i record che vengono ricercati
   * dall'utente
   * */
  public void fillOrganizzazioneProfessionali()
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
      //CuneoLogger.debug(this, query);
      rset = stmt.executeQuery(query);

      while (rset.next())
      {
        add(new OrganizzazioneProfessionale(
                                  rset.getString("ID_ORGANIZZAZIONE"),
                                  rset.getString("CF_PARTITA_IVA"),
                                  rset.getString("RAGIONE_SOCIALE"),
                                  rset.getString("SEDE_TERRITORIALE"),
                                  rset.getString("INDIRIZZO"),
                                  rset.getString("COMUNE")
                                  )
                                  );
      }
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("fillOrganizzazioneProfessionali della classe OrganizzazioneProfessionali");
      this.getAut().setContenutoQuery(query);
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("fillOrganizzazioneProfessionali della classe OrganizzazioneProfessionali"
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
    return organizzazioneProfessionali.size();
  }

  public OrganizzazioneProfessionale get(int i)
  {
    return (OrganizzazioneProfessionale)organizzazioneProfessionali.elementAt(i);
  }

  public void add(OrganizzazioneProfessionale i)
  {
    organizzazioneProfessionali.addElement(i);
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