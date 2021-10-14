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

public class UmiditaCampione  extends ModelloInsEsitoAnalisi
{
  private long idRichiesta;
  private String tara;
  private String pesoNettoUmido;
  private String pesoLordoSecco;
  private String umiditaCampione;
  private String sostanzaSecca;

  public UmiditaCampione ()
  {
  }
  public UmiditaCampione ( long idRichiesta, String tara, String pesoNettoUmido, String pesoLordoSecco, String umiditaCampione, String sostanzaSecca )
  {
    this.idRichiesta=idRichiesta;
    this.tara=tara;
    this.pesoNettoUmido=pesoNettoUmido;
    this.pesoLordoSecco=pesoLordoSecco;
    this.umiditaCampione=umiditaCampione;
    this.sostanzaSecca=sostanzaSecca;
  }


  /**
   * Questo metodo va a leggere il record della tabella UMIDITA_CAMPIONE
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
      String temp;
      conn=getConnection();
      stmt = conn.createStatement();
      query = new StringBuffer("SELECT TARA,PESO_NETTO_UMIDO,PESO_LORDO_SECCO,");
      query.append("UMIDITA_CAMPIONE,SOSTANZA_SECCA ");
      query.append("FROM UMIDITA_CAMPIONE ");
      query.append("WHERE ID_RICHIESTA = ");
      query.append(getIdRichiesta());
      //CuneoLogger.debug(this, query.toString());
      ResultSet rs=stmt.executeQuery(query.toString());
      if (rs.next())
      {
        temp=rs.getString("TARA");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setTara(temp);

        temp=rs.getString("PESO_NETTO_UMIDO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setPesoNettoUmido(temp);

        temp=rs.getString("PESO_LORDO_SECCO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setPesoLordoSecco(temp);

        temp=rs.getString("UMIDITA_CAMPIONE");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setUmiditaCampione(temp);

        temp=rs.getString("SOSTANZA_SECCA");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setSostanzaSecca(temp);

        return true;
      }
      else return false;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe UmiditaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe UmiditaCampione"
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
   * UMIDITA_CAMPIONE. Alcuni dati sono quelli di input inseriti dall'utente
   * mentre altri sono calcolati
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
      double umiditaCampione=getUmiditaCampioneCalcoli();
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("INSERT INTO UMIDITA_CAMPIONE(");
      query.append("ID_RICHIESTA,TARA,PESO_NETTO_UMIDO,");
      query.append("PESO_LORDO_SECCO,UMIDITA_CAMPIONE,SOSTANZA_SECCA) ");
      query.append("VALUES(");
      query.append(getIdRichiesta()).append(",");
      query.append(getTara()).append(",");
      query.append(getPesoNettoUmido()).append(",");
      query.append(getPesoLordoSecco()).append(",");
      query.append(umiditaCampione).append(",");
      query.append(100-umiditaCampione);
      query.append(")");
      //CuneoLogger.debug(this, query.toString());
      stmt.executeUpdate(query.toString());
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("insert della classe UmiditaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("insert della classe UmiditaCampione"
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
   * UMIDITA_CAMPIONE. Alcuni dati sono quelli di input inseriti dall'utente
   * mentre altri sono calcolati
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
      double umiditaCampione=getUmiditaCampioneCalcoli();
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("UPDATE UMIDITA_CAMPIONE ");
      query.append("SET TARA = ").append(getTara());
      query.append(",PESO_NETTO_UMIDO = ").append(getPesoNettoUmido());
      query.append(",PESO_LORDO_SECCO = ").append(getPesoLordoSecco());
      query.append(",UMIDITA_CAMPIONE = ").append(umiditaCampione);
      query.append(",SOSTANZA_SECCA = ").append(100-umiditaCampione);
      query.append(" WHERE ID_RICHIESTA = ");query.append(getIdRichiesta());
      //CuneoLogger.debug(this, query.toString());
      stmt.executeUpdate(query.toString());
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("update della classe UmiditaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("update della classe UmiditaCampione"
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
   * UMIDITA_CAMPIONE.
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
      query = new StringBuffer("DELETE FROM UMIDITA_CAMPIONE ");
      query.append("WHERE ID_RICHIESTA =").append(getIdRichiesta());
      //CuneoLogger.debug(this, query.toString());
      stmt.executeUpdate(query.toString());
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("delete della classe UmiditaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("delete della classe UmiditaCampione"
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

  public long getIdRichiesta()
  {
    return this.idRichiesta;
  }
  public void setIdRichiesta( long newIdRichiesta )
  {
    this.idRichiesta = newIdRichiesta;
  }

  public String getTara()
  {
    return this.tara;
  }
  public void setTara( String newTara )
  {
    if (newTara!=null) tara=newTara.replace(',','.');
    else tara = newTara;
  }

  public String getPesoNettoUmido()
  {
    return this.pesoNettoUmido;
  }
  public void setPesoNettoUmido( String newPesoNettoUmido )
  {
    if (newPesoNettoUmido!=null) pesoNettoUmido=newPesoNettoUmido.replace(',','.');
    else pesoNettoUmido = newPesoNettoUmido;
  }

  public String getPesoLordoSecco()
  {
    return this.pesoLordoSecco;
  }
  public void setPesoLordoSecco( String newPesoLordoSecco )
  {
    if (newPesoLordoSecco!=null) pesoLordoSecco=newPesoLordoSecco.replace(',','.');
    else pesoLordoSecco = newPesoLordoSecco;
  }

  public String getUmiditaCampione()
  {
    return this.umiditaCampione;
  }
  public String getUmiditaCampionePDF()
  {
    if (umiditaCampione==null) return "";
    else
    {
      umiditaCampione=umiditaCampione.replace(',','.');
      umiditaCampione=Utili.nf1.format(Double.parseDouble(umiditaCampione));
      umiditaCampione=umiditaCampione.replace('.',',');
      return umiditaCampione;
    }
  }
  public void setUmiditaCampione( String newUmiditaCampione )
  {
    if (newUmiditaCampione!=null) umiditaCampione=newUmiditaCampione.replace(',','.');
    else this.umiditaCampione = newUmiditaCampione;
  }

  public String getSostanzaSecca()
  {
    return this.sostanzaSecca;
  }
  public String getSostanzaSeccaPDF()
  {
    if (sostanzaSecca==null) return "";
    else
    {
      sostanzaSecca=sostanzaSecca.replace(',','.');
      sostanzaSecca=Utili.nf1.format(Double.parseDouble(sostanzaSecca));
      sostanzaSecca=sostanzaSecca.replace('.',',');
      return sostanzaSecca;
    }
  }
  public void setSostanzaSecca( String newSostanzaSecca )
  {
    if (newSostanzaSecca!=null) sostanzaSecca=newSostanzaSecca.replace(',','.');
    else this.sostanzaSecca = newSostanzaSecca;
  }

  public double getUmiditaCampioneCalcoli()
  {
    double umiditaCampione=0;
    try
    {
      double tara=Double.parseDouble(getTara());
      double pesoNetto=Double.parseDouble(getPesoNettoUmido());
      double pesoLordo=Double.parseDouble(getPesoLordoSecco());
      umiditaCampione=((pesoNetto-(pesoLordo-tara))/pesoNetto)*100;
    }
    catch(Exception num)
    {
      umiditaCampione=0;
    }
    return umiditaCampione;
  }

  /**
   * Questa funzione controlla che i dati che verranno inseriti o modificati nel
   * database siano consistenti
   * */
  public String ControllaDati()
  {
    StringBuffer errore=new StringBuffer("");

    if (!Utili.isDouble(getTara(),0.0,999999.999,3))
      errore.append(";1");
    if (!Utili.isDouble(getPesoNettoUmido(),0.0,999999.999,3))
      errore.append(";2");
    try
    {
      if (!Utili.isDouble(getPesoLordoSecco(),0.0,999999.999,3)
       || Double.parseDouble(getPesoLordoSecco()) > Double.parseDouble(getPesoNettoUmido())+Double.parseDouble(getTara())
       || Double.parseDouble(getTara()) >= Double.parseDouble(getPesoLordoSecco()))
         errore.append(";3");
    }
    catch(Exception eNum)
    {
      errore.append(";3");
    }
    /**
    * Se non sono stati trovati errori restituisce null
    */
    if (errore.toString().equals("")) return null;
    else return errore.toString();
  }
}