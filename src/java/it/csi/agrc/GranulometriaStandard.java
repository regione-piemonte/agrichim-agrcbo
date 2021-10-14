package it.csi.agrc;

import it.csi.cuneo.*;
import java.sql.*;
//import it.csi.jsf.web.pool.*;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class GranulometriaStandard  extends ModelloInsEsitoAnalisi
{
  private long idRichiesta;
  private String argilla;
  private String limoTotale;
  private String sabbiaTotale;

  public GranulometriaStandard ()
  {
  }
  public GranulometriaStandard ( long idRichiesta, String argilla, String limoTotale, String sabbiaTotale )
  {
    this.idRichiesta=idRichiesta;
    this.argilla=argilla;
    this.limoTotale=limoTotale;
    this.sabbiaTotale=sabbiaTotale;
  }


  /**
   * Questo metodo va a leggere il record della tabella GRANULOMETRIA_STANDARD
   * con idRichiesta uguale a qullo memorizzato nelll'attributo idRichiesta.
   * Il contenuto del record viene memorizzato all'interno del bean
   * @return se trova un record restituisce true, altrimenti false
   * @throws Exception
   * @throws SQLException
   */
  public boolean select() throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    Statement stmt =null;
    StringBuffer query=new StringBuffer("");
    try
    {
      conn=getConnection();
      stmt = conn.createStatement();
      query = new StringBuffer("SELECT ARGILLA,LIMO_TOTALE,");
      query.append("SABBIA_TOTALE ");
      query.append("FROM GRANULOMETRIA_STANDARD ");
      query.append("WHERE ID_RICHIESTA = ");
      query.append(getIdRichiesta());
      //CuneoLogger.debug(this, query.toString());
      ResultSet rs=stmt.executeQuery(query.toString());
      String temp;
      if (rs.next())
      {
        temp=rs.getString("ARGILLA");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setArgilla(temp);

        temp=rs.getString("LIMO_TOTALE");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setLimoTotale(temp);

        temp=rs.getString("SABBIA_TOTALE");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setSabbiaTotale(temp);

        return true;
      }
      else return false;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe GranulometriaStandard");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe GranulometriaStandard"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      stmt.close();
      if (conn!=null) conn.close();
    }
  }

  /**
   * Questo metodo va ad inserire un nuovo record all'interno della tabella
   * GRANULOMETRIA_STANDARD. Alcuni dati sono quelli di input inseriti
   * dall'utente mentre altri sono calcolati
   * @throws Exception
   * @throws SQLException
   */
  public void insert() throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("INSERT INTO GRANULOMETRIA_STANDARD(");
      query.append("ID_RICHIESTA,ARGILLA,LIMO_TOTALE,");
      query.append("SABBIA_TOTALE) ");
      query.append("VALUES(");
      query.append(getIdRichiesta()).append(",");
      query.append(getArgilla()).append(",");
      query.append(getLimoTotale()).append(",");
      query.append(getSabbiaTotaleCalcoli());
      query.append(")");
      //CuneoLogger.debug(this, query.toString());
      stmt.executeUpdate(query.toString());
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("insert della classe GranulometriaStandard");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("insert della classe GranulometriaStandard"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      if (conn!=null) conn.close();
    }
  }

  /**
   * Questo metodo va a modificare un record all'interno della tabella
   * GRANULOMETRIA_STANDARD. Alcuni dati sono quelli di input inseriti
   * dall'utente mentre altri sono calcolati
   * @throws Exception
   * @throws SQLException
   */
  public void update() throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("UPDATE GRANULOMETRIA_STANDARD ");
      query.append("SET ARGILLA = ").append(getArgilla());
      query.append(",LIMO_TOTALE = ").append(getLimoTotale());
      query.append(",SABBIA_TOTALE = ").append(getSabbiaTotaleCalcoli());
      query.append(" WHERE ID_RICHIESTA = ");
      query.append(getIdRichiesta());
      //CuneoLogger.debug(this, query.toString());
      stmt.executeUpdate(query.toString());
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("update della classe GranulometriaStandard");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("update della classe GranulometriaStandard"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      if (conn!=null) conn.close();
    }
  }

  /**
   * Questo metodo va a cancellare un record della tabella
   * GRANULOMETRIA_STANDARD.
   * @throws Exception
   * @throws SQLException
   */
  public void delete() throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("DELETE FROM GRANULOMETRIA_STANDARD ");
      query.append("WHERE ID_RICHIESTA =").append(getIdRichiesta());
      //CuneoLogger.debug(this, query.toString());
      stmt.executeUpdate(query.toString());
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("delete della classe GranulometriaStandard");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("delete della classe GranulometriaStandard"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      if (conn!=null) conn.close();
    }
  }


  public double getSabbiaTotaleCalcoli()
  {
    double sabbiaTotale=0;
    try
    {
      double limoTotale=Double.parseDouble(getLimoTotale());
      double argilla=Double.parseDouble(getArgilla());
      sabbiaTotale= 100 -(limoTotale + argilla);
    }
    catch(Exception num)
    {
      sabbiaTotale=0;
    }
    return sabbiaTotale;
  }

  /**
   * Questa funzione controlla che i dati che verranno inseriti o modificati nel
   * database siano consistenti
   * */
  public String ControllaDati()
  {
    StringBuffer errore=new StringBuffer("");

    if (!Utili.isDouble(getArgilla(),0.0,999.99,2))
      errore.append(";1");
    if (!Utili.isDouble(getLimoTotale(),0.0,999.99,2))
      errore.append(";2");
    /**
    * Se tutti questi campi rispettano i vincoli effettuo l'ultimo controllo
    * sul campo calcolato. Se invece sono già stati trovati errore è innutile
    * andare a calcolare un campo che molto probabilmente non è valido in
    * partenza
    */
    if (errore.toString().equals(""))
    {
      try
      {
        if (Double.parseDouble(getArgilla())+
            Double.parseDouble(getLimoTotale())>100)
           errore.append(";1;2");
      }
      catch(Exception eNum)
      {
        errore.append(";1;2");
      }
    }
    /**
    * Se non sono stati trovati errori restituisce null
    */
    if (errore.toString().equals("")) return null;
    else return errore.toString();
  }

  public long getIdRichiesta()
  {
    return this.idRichiesta;
  }
  public void setIdRichiesta( long newIdRichiesta )
  {
    this.idRichiesta = newIdRichiesta;
  }


  public String getArgilla()
  {
    return this.argilla;
  }
  public String getArgillaPDF()
  {
    if (argilla==null) return "";
    else
    {
      argilla=argilla.replace(',','.');
      argilla=Utili.nf1.format(Double.parseDouble(argilla));
      argilla=argilla.replace('.',',');
      return argilla;
    }

  }
  public void setArgilla( String newArgilla )
  {
    if (newArgilla!=null) argilla=newArgilla.replace(',','.');
    else this.argilla = newArgilla;
  }

  public String getLimoTotale()
  {
    return this.limoTotale;
  }
  public String getLimoTotalePDF()
  {
    if (limoTotale==null) return "";
    else
    {
      limoTotale=limoTotale.replace(',','.');
      limoTotale=Utili.nf1.format(Double.parseDouble(limoTotale));
      limoTotale=limoTotale.replace('.',',');
      return limoTotale;
    }
  }
  public void setLimoTotale( String newLimoTotale )
  {
    if (newLimoTotale!=null) limoTotale=newLimoTotale.replace(',','.');
    else this.limoTotale = newLimoTotale;
  }

  public String getSabbiaTotale()
  {
    return this.sabbiaTotale;
  }
  public String getSabbiaTotalePDF()
  {
    if (sabbiaTotale==null) return "";
    else
    {
      sabbiaTotale=sabbiaTotale.replace(',','.');
      sabbiaTotale=Utili.nf1.format(Double.parseDouble(sabbiaTotale));
      sabbiaTotale=sabbiaTotale.replace('.',',');
      return sabbiaTotale;
    }
  }
  public void setSabbiaTotale( String newSabbiaTotale )
  {
    this.sabbiaTotale = newSabbiaTotale;
  }
}