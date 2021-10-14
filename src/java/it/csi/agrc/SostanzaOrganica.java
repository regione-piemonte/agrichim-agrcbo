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

public class SostanzaOrganica  extends ModelloInsEsitoAnalisi
{
  private long idRichiesta;
  private String letturaSostanzaOrganica;
  private String pesoCampione;
  private String sostanzaOrganica;
  private String carbonioOrganico;
  private String carbonioOrganicoMetodoAna;
  private String sostanzaOrganicaMetodoAna;

  public SostanzaOrganica ()
  {
  }
  public SostanzaOrganica ( long idRichiesta, String letturaSostanzaOrganica, String pesoCampione, String sostanzaOrganica, String carbonioOrganico, String carbonioOrganicoMetodoAna, String sostanzaOrganicaMetodoAna )
  {
    this.idRichiesta=idRichiesta;
    this.letturaSostanzaOrganica=letturaSostanzaOrganica;
    this.pesoCampione=pesoCampione;
    this.sostanzaOrganica=sostanzaOrganica;
    this.carbonioOrganico=carbonioOrganico;
    this.carbonioOrganicoMetodoAna=carbonioOrganicoMetodoAna;
    this.sostanzaOrganicaMetodoAna=sostanzaOrganicaMetodoAna;
  }

  /**
   * Questo metodo va a leggere il record della tabella SOSTANZA_ORGANICA
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
      query = new StringBuffer("SELECT LETTURA_SOSTANZA_ORGANICA,PESO_CAMPIONE,");
      query.append("SOSTANZA_ORGANICA,CARBONIO_ORGANICO,");
      query.append("CARBONIO_ORGANICO_METODO_ANA,SOSTANZA_ORGANICA_METODO_ANA ");
      query.append("FROM SOSTANZA_ORGANICA ");
      query.append("WHERE ID_RICHIESTA = ");
      query.append(getIdRichiesta());
      //CuneoLogger.debug(this, query.toString());
      ResultSet rs=stmt.executeQuery(query.toString());
      String temp;
      if (rs.next())
      {
        temp=rs.getString("LETTURA_SOSTANZA_ORGANICA");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaSostanzaOrganica(temp);

        temp=rs.getString("PESO_CAMPIONE");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setPesoCampione(temp);

        temp=rs.getString("SOSTANZA_ORGANICA");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setSostanzaOrganica(temp);

        temp=rs.getString("CARBONIO_ORGANICO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setCarbonioOrganico(temp);

        temp=rs.getString("CARBONIO_ORGANICO_METODO_ANA");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setCarbonioOrganicoMetodoAna(temp);

        temp=rs.getString("SOSTANZA_ORGANICA_METODO_ANA");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setSostanzaOrganicaMetodoAna(temp);

        return true;
      }
      else return false;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe SostanzaOrganica");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe SostanzaOrganica"
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
   * SOSTANZA_ORGANICA. Alcuni dati sono quelli di input inseriti
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
      query = new StringBuffer("INSERT INTO SOSTANZA_ORGANICA(");
      query.append("ID_RICHIESTA,LETTURA_SOSTANZA_ORGANICA,PESO_CAMPIONE,");
      query.append("SOSTANZA_ORGANICA,CARBONIO_ORGANICO,");
      query.append("CARBONIO_ORGANICO_METODO_ANA,SOSTANZA_ORGANICA_METODO_ANA) ");
      query.append("VALUES(");
      query.append(getIdRichiesta()).append(",");
      query.append(getLetturaSostanzaOrganica()).append(",");
      query.append(getPesoCampione()).append(",");
      query.append(getSostanzaOrganicaCalcoli()).append(",");
      query.append(getCarbonioOrganicoCalcoli()).append(",");
      query.append(getCarbonioOrganicoMetodoAna()).append(",");
      query.append(getSostanzaOrganicaMetodoAnaCalcoli());
      query.append(")");
      //CuneoLogger.debug(this, query.toString());
      stmt.executeUpdate(query.toString());
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("insert della classe SostanzaOrganica");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("insert della classe SostanzaOrganica"
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
   * SOSTANZA_ORGANICA. Alcuni dati sono quelli di input inseriti
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
      query = new StringBuffer("UPDATE SOSTANZA_ORGANICA ");
      query.append("SET LETTURA_SOSTANZA_ORGANICA = ").append(getLetturaSostanzaOrganica());
      query.append(",PESO_CAMPIONE = ").append(getPesoCampione());
      query.append(",SOSTANZA_ORGANICA = ").append(getSostanzaOrganicaCalcoli());
      query.append(",CARBONIO_ORGANICO = ").append(getCarbonioOrganicoCalcoli());
      query.append(",CARBONIO_ORGANICO_METODO_ANA = ").append(getCarbonioOrganicoMetodoAna());
      query.append(",SOSTANZA_ORGANICA_METODO_ANA = ").append(getSostanzaOrganicaMetodoAnaCalcoli());
      query.append(" WHERE ID_RICHIESTA = ");query.append(getIdRichiesta());
      //CuneoLogger.debug(this, query.toString());
      stmt.executeUpdate(query.toString());
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("update della classe SostanzaOrganica");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("update della classe SostanzaOrganica"
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
   * SOSTANZA_ORGANICA.
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
      query = new StringBuffer("DELETE FROM SOSTANZA_ORGANICA ");
      query.append("WHERE ID_RICHIESTA =").append(getIdRichiesta());
      //CuneoLogger.debug(this, query.toString());
      stmt.executeUpdate(query.toString());
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("delete della classe SostanzaOrganica");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("delete della classe SostanzaOrganica"
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

  public Double getSostanzaOrganicaCalcoli()
  {
    double sostanzaOrganica=0;
    try
    {
      double letturaSostanzaOrganica=Double.parseDouble(getLetturaSostanzaOrganica());
      double pesoCampione=Double.parseDouble(getPesoCampione());
      if (letturaSostanzaOrganica>0 && pesoCampione>0)
      {
        sostanzaOrganica=letturaSostanzaOrganica/pesoCampione;
      }
    }
    catch(Exception num)
    {
      sostanzaOrganica=0;
    }
    if (sostanzaOrganica==0) return null;
    else return new Double(sostanzaOrganica);
  }

  public Double getCarbonioOrganicoCalcoli()
  {
    double carbonioOrganico=0;
    try
    {
      double sostanzaOrganica=0;
      if (getSostanzaOrganicaCalcoli()!=null)
        sostanzaOrganica=getSostanzaOrganicaCalcoli().doubleValue();
      if (sostanzaOrganica >0)
      {
        carbonioOrganico=sostanzaOrganica/1.72;
      }
    }
    catch(Exception num)
    {
      carbonioOrganico=0;
    }
    if (carbonioOrganico==0) return null;
    else return new Double(carbonioOrganico);
  }

  public Double getSostanzaOrganicaMetodoAnaCalcoli()
  {
    double sostanzaOrganicaMetodoAna=0;
    try
    {
      double carbonioOrganicoMetodoAna=Double.parseDouble(getCarbonioOrganicoMetodoAna());
      if (carbonioOrganicoMetodoAna>0)
      {
        sostanzaOrganicaMetodoAna=carbonioOrganicoMetodoAna*1.72;
      }
    }
    catch(Exception num)
    {
      sostanzaOrganicaMetodoAna=0;
    }
    if (sostanzaOrganicaMetodoAna==0) return null;
    else return new Double(sostanzaOrganicaMetodoAna);
  }

  /**
   * Questa funzione controlla che i dati che verranno inseriti o modificati nel
   * database siano consistenti
   * */
  public String ControllaDati()
  {
    StringBuffer errore=new StringBuffer("");

      if (getLetturaSostanzaOrganica()!=null &&
            !Utili.isDouble(getLetturaSostanzaOrganica(),0.001,999999.999,3))
        errore.append(";1");
      if (getPesoCampione()!=null &&
            !Utili.isDouble(getPesoCampione(),0.001,999999.999,3))
        errore.append(";2");
      if (getCarbonioOrganicoMetodoAna()!=null &&
            !Utili.isDouble(getCarbonioOrganicoMetodoAna(),0.001,999999.999,3))
        errore.append(";3");

    try
    {
      if (!((getLetturaSostanzaOrganica()!=null ^ getCarbonioOrganicoMetodoAna()!=null) &&
           (getPesoCampione()!=null ^ getCarbonioOrganicoMetodoAna()!=null)))
      {
        errore.append(";1;2;3");
      }
    }
    catch(Exception e) {}

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

  public String getLetturaSostanzaOrganica()
  {
    return this.letturaSostanzaOrganica;
  }
  public void setLetturaSostanzaOrganica( String newLetturaSostanzaOrganica )
  {
    if (newLetturaSostanzaOrganica!=null)
    {
      letturaSostanzaOrganica=newLetturaSostanzaOrganica.replace(',','.');
    }
    else letturaSostanzaOrganica = newLetturaSostanzaOrganica;
  }

  public String getPesoCampione()
  {
    return this.pesoCampione;
  }
  public void setPesoCampione( String newPesoCampione )
  {
    if (newPesoCampione!=null) pesoCampione=newPesoCampione.replace(',','.');
    else pesoCampione = newPesoCampione;
  }

  public String getSostanzaOrganicaPDF()
  {
    if (sostanzaOrganica==null) return "";
    else
    {
      sostanzaOrganica=sostanzaOrganica.replace(',','.');
      sostanzaOrganica=Utili.nf2.format(Double.parseDouble(sostanzaOrganica));
      sostanzaOrganica=sostanzaOrganica.replace('.',',');
      return sostanzaOrganica;
    }
  }

  public String getSostanzaOrganica()
  {
    return this.sostanzaOrganica;
  }
  public void setSostanzaOrganica( String newSostanzaOrganica )
  {
    if (newSostanzaOrganica!=null)
      sostanzaOrganica=newSostanzaOrganica.replace(',','.');
    else this.sostanzaOrganica = newSostanzaOrganica;
  }

  public String getCarbonioOrganicoPDF()
  {
    if (carbonioOrganico==null) return "";
    else
    {
      carbonioOrganico=carbonioOrganico.replace(',','.');
      carbonioOrganico=Utili.nf2.format(Double.parseDouble(carbonioOrganico));
      carbonioOrganico=carbonioOrganico.replace('.',',');
      return carbonioOrganico;
    }
  }

  public String getCarbonioOrganico()
  {
    return this.carbonioOrganico;
  }
  public void setCarbonioOrganico( String newCarbonioOrganico )
  {
    if (newCarbonioOrganico!=null)
      carbonioOrganico=newCarbonioOrganico.replace(',','.');
    else this.carbonioOrganico = newCarbonioOrganico;
  }

  public String getCarbonioOrganicoMetodoAnaPDF()
  {
    if (carbonioOrganicoMetodoAna==null) return "";
    else
    {
      carbonioOrganicoMetodoAna=carbonioOrganicoMetodoAna.replace(',','.');
      carbonioOrganicoMetodoAna=Utili.nf2.format(Double.parseDouble(carbonioOrganicoMetodoAna));
      carbonioOrganicoMetodoAna=carbonioOrganicoMetodoAna.replace('.',',');
      return carbonioOrganicoMetodoAna;
    }
  }

  public String getCarbonioOrganicoMetodoAna()
  {
    return this.carbonioOrganicoMetodoAna;
  }
  public void setCarbonioOrganicoMetodoAna( String newCarbonioOrganicoMetodoAna )
  {
    if (newCarbonioOrganicoMetodoAna!=null)
      carbonioOrganicoMetodoAna=newCarbonioOrganicoMetodoAna.replace(',','.');
    else this.carbonioOrganicoMetodoAna = newCarbonioOrganicoMetodoAna;
  }

  public String getSostanzaOrganicaMetodoAnaPDF()
  {
    if (sostanzaOrganicaMetodoAna==null) return "";
    else
    {
      sostanzaOrganicaMetodoAna=sostanzaOrganicaMetodoAna.replace(',','.');
      sostanzaOrganicaMetodoAna=Utili.nf2.format(Double.parseDouble(sostanzaOrganicaMetodoAna));
      sostanzaOrganicaMetodoAna=sostanzaOrganicaMetodoAna.replace('.',',');
      return sostanzaOrganicaMetodoAna;
    }
  }

  public String getSostanzaOrganicaMetodoAna()
  {
    return this.sostanzaOrganicaMetodoAna;
  }
  public void setSostanzaOrganicaMetodoAna( String newSostanzaOrganicaMetodoAna )
  {
    if (newSostanzaOrganicaMetodoAna!=null)
      sostanzaOrganicaMetodoAna=newSostanzaOrganicaMetodoAna.replace(',','.');
    else this.sostanzaOrganicaMetodoAna = newSostanzaOrganicaMetodoAna;
  }
}