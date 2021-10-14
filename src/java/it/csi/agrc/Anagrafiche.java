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

public class Anagrafiche extends BeanDataSource
{
  private Vector anagrafiche = new Vector();

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

  public Anagrafiche()
  {
  }
  public Anagrafiche(Object dataSource, Autenticazione aut)
  {
    this.setDataSource(dataSource);
    this.setAut(aut);
  }

  public String getNomeCognome(Connection conn,String idAnagrafica)
  throws Exception, SQLException
  {
    String nome=null,cognome=null;
    StringBuffer query=new StringBuffer("");
    Statement stmt = conn.createStatement();
    query=new StringBuffer("SELECT A.NOME,A.COGNOME_RAGIONE_SOCIALE ");
    query.append("FROM ANAGRAFICA A ");
    query.append("WHERE a.ID_ANAGRAFICA = ").append(idAnagrafica);
    ResultSet rset = stmt.executeQuery(query.toString());
    if (rset.next())
    {
      nome = rset.getString("NOME");
      cognome = rset.getString("COGNOME_RAGIONE_SOCIALE");
    }
    rset.close();
    stmt.close();
    if (nome == null) nome="";
    if (cognome == null) cognome="";
    return nome +" "+cognome;
  }

  public Anagrafica getAnagrafica(String idAnagraficaSearch)
      throws Exception, SQLException
  {
    Anagrafica a=null;
    fill(idAnagraficaSearch,false);
    if ( size()==1 )
      a = get(0);
    return a;
  }
  public void fill(String stringSearch, boolean isCodiceIdentificativo)
      throws Exception, SQLException
  {
    if (!isConnection())
           throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=this.getConnection();
    StringBuffer query=new StringBuffer("");
    Statement stmt = null;
    try
    {
        stmt = conn.createStatement();

        query.append("SELECT * FROM ANAGRAFICA ");
        if ( null != stringSearch )
          if ( !"".equals(stringSearch) )
            if (isCodiceIdentificativo)
              query.append("WHERE CODICE_IDENTIFICATIVO='"+stringSearch+"' ");
            else
              query.append("WHERE ID_ANAGRAFICA="+stringSearch+" ");
        //query.append("ORDER BY COGNOME_RAGIONE_SOCIALE");
        //CuneoLogger.debug(this, "\nQuery Anagrafiche.fill()\n"+query.toString());
        ResultSet rset = stmt.executeQuery(query.toString());

        anagrafiche.clear();
        while (rset.next()) {
          add( new Anagrafica( rset.getString("ID_ANAGRAFICA"),
                               rset.getString("CODICE_IDENTIFICATIVO"),
                               rset.getString("TIPO_PERSONA"),
                               rset.getString("COGNOME_RAGIONE_SOCIALE"),
                               rset.getString("NOME"),
                               rset.getString("INDIRIZZO"),
                               rset.getString("CAP"),
                               rset.getString("COMUNE_RESIDENZA"),
                               rset.getString("TELEFONO"),
                               rset.getString("CELLULARE"),
                               rset.getString("FAX"),
                               rset.getString("EMAIL"),
                               rset.getString("ID_ORGANIZZAZIONE"),
                               rset.getString("TIPO_UTENTE"),
                               rset.getString("ID_ANAGRAFICA_2"))
              );
        }
        rset.close();
        stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("fill della classe Anagrafiche");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("fill della classe Anagrafiche"
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

  /**
   * Questo metodo viene utilizzato per leggere i record che vengono ricercati
   * dall'utente
   * */
  public void fillAnagrafiche()
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
        add(new Anagrafica(
                                  rset.getString("ID_ANAGRAFICA"),
                                  rset.getString("COGNOME_RAGIONE_SOCIALE"),
                                  rset.getString("NOME"),
                                  rset.getString("CODICE_IDENTIFICATIVO"),
                                  rset.getString("TIPO_UTENTE"),
                                  rset.getString("COMUNE"),
                                  rset.getString("ID_ANAGRAFICA_2")
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
    return anagrafiche.size();
  }

  public Anagrafica get(int i)
  {
    return (Anagrafica)anagrafiche.elementAt(i);
  }

  public void add(Anagrafica i)
  {
    anagrafiche.addElement(i);
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