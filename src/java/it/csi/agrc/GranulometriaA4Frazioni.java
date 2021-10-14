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

public class GranulometriaA4Frazioni  extends ModelloInsEsitoAnalisi
{
  private long idRichiesta;
  private String argilla;
  private String limoTotale;
  private String limoFine;
  private String limoGrosso;
  private String sabbiaTotale;

  public GranulometriaA4Frazioni ()
  {
  }
  public GranulometriaA4Frazioni ( long idRichiesta, String argilla, String limoTotale, String limoFine, String limoGrosso, String sabbiaTotale )
  {
    this.idRichiesta=idRichiesta;
    this.argilla=argilla;
    this.limoTotale=limoTotale;
    this.limoFine=limoFine;
    this.limoGrosso=limoGrosso;
    this.sabbiaTotale=sabbiaTotale;
  }


  /**
   * Questo metodo va a leggere il record della tabella GRANULOMETRIA_A_4_FRAZIONI
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
      query = new StringBuffer("SELECT ARGILLA,LIMO_TOTALE,LIMO_FINE,LIMO_GROSSO,");
      query.append("SABBIA_TOTALE ");
      query.append("FROM GRANULOMETRIA_A_4_FRAZIONI ");
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

        temp=rs.getString("LIMO_FINE");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setLimoFine(temp);

        temp=rs.getString("LIMO_GROSSO");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setLimoGrosso(temp);

        temp=rs.getString("SABBIA_TOTALE");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setSabbiaTotale(temp);

        return true;
      }
      else return false;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe GranulometriaA4Frazioni");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe GranulometriaA4Frazioni"
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
   * GRANULOMETRIA_A_4_FRAZIONI. Alcuni dati sono quelli di input inseriti
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
      query = new StringBuffer("INSERT INTO GRANULOMETRIA_A_4_FRAZIONI(");
      query.append("ID_RICHIESTA,ARGILLA,LIMO_TOTALE,");
      query.append("LIMO_FINE,LIMO_GROSSO,SABBIA_TOTALE) ");
      query.append("VALUES(");
      query.append(getIdRichiesta()).append(",");
      query.append(getArgilla()).append(",");
      query.append(getLimoTotale()).append(",");
      query.append(getLimoFine()).append(",");
      query.append(getLimoGrossoCalcoli()).append(",");
      query.append(getSabbiaTotaleCalcoli());
      query.append(")");
      //CuneoLogger.debug(this, query.toString());
      stmt.executeUpdate(query.toString());
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("insert della classe GranulometriaA4Frazioni");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("insert della classe GranulometriaA4Frazioni"
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
   * GRANULOMETRIA_A_4_FRAZIONI. Alcuni dati sono quelli di input inseriti
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
      query = new StringBuffer("UPDATE GRANULOMETRIA_A_4_FRAZIONI ");
      query.append("SET ARGILLA = ").append(getArgilla());
      query.append(",LIMO_TOTALE = ").append(getLimoTotale());
      query.append(",LIMO_FINE = ").append(getLimoFine());
      query.append(",LIMO_GROSSO = ").append(getLimoGrossoCalcoli());
      query.append(",SABBIA_TOTALE = ").append(getSabbiaTotaleCalcoli());
      query.append(" WHERE ID_RICHIESTA = ");query.append(getIdRichiesta());
      //CuneoLogger.debug(this, query.toString());
      stmt.executeUpdate(query.toString());
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("update della classe GranulometriaA4Frazioni");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("update della classe GranulometriaA4Frazioni"
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
   * GRANULOMETRIA_A_4_FRAZIONI.
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
      query = new StringBuffer("DELETE FROM GRANULOMETRIA_A_4_FRAZIONI ");
      query.append("WHERE ID_RICHIESTA =").append(getIdRichiesta());
      //CuneoLogger.debug(this, query.toString());
      stmt.executeUpdate(query.toString());
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("delete della classe GranulometriaA4Frazioni");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("delete della classe GranulometriaA4Frazioni"
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


  public double getLimoGrossoCalcoli()
  {
    double limogrosso=0;
    try
    {
      double limoTotale=Double.parseDouble(getLimoTotale());
      double limoFine=Double.parseDouble(getLimoFine());
      limogrosso=limoTotale - limoFine;
    }
    catch(Exception num)
    {
      limogrosso=0;
    }
    return limogrosso;
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
    if (!Utili.isDouble(getLimoFine(),0.0,999.99,2))
      errore.append(";3");
    try
    {
      if (Double.parseDouble(getLimoFine()) > Double.parseDouble(this.getLimoTotale()))
         errore.append(";2;3");
      else
      {
        if ((Double.parseDouble(getArgilla()) + Double.parseDouble(getLimoTotale())>100))
         errore.append(";1;2");
      }
    }
    catch(Exception eNum)
    {
      errore.append(";2");
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

  public String getLimoFine()
  {
    return this.limoFine;
  }
  public String getLimoFinePDF()
  {
    if (limoFine==null) return "";
    else
    {
      limoFine=limoFine.replace(',','.');
      limoFine=Utili.nf1.format(Double.parseDouble(limoFine));
      limoFine=limoFine.replace('.',',');
      return limoFine;
    }
  }
  public void setLimoFine( String newLimoFine )
  {
    if (newLimoFine!=null) limoFine=newLimoFine.replace(',','.');
    else this.limoFine = newLimoFine;
  }

  public String getLimoGrosso()
  {
    return this.limoGrosso;
  }
  public String getLimoGrossoPDF()
  {
    if (limoGrosso==null) return "";
    else
    {
      limoGrosso=limoGrosso.replace(',','.');
      limoGrosso=Utili.nf1.format(Double.parseDouble(limoGrosso));
      limoGrosso=limoGrosso.replace('.',',');
      return limoGrosso;
    }
  }
  public void setLimoGrosso( String newLimoGrosso )
  {
    this.limoGrosso = newLimoGrosso;
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