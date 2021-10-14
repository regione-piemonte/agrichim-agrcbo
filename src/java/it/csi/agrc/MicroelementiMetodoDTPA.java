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

public class MicroelementiMetodoDTPA  extends ModelloInsEsitoAnalisi
{
  private long idRichiesta;
  private String letturaFerro;
  private String diluizioneFerro;
  private String ferroAssimilabile;
  private String letturaManganese;
  private String diluizioneManganese;
  private String manganeseAssimilabile;
  private String letturaZinco;
  private String diluizioneZinco;
  private String zincoAssimilabile;
  private String letturaRame;
  private String diluizioneRame;
  private String rameAssimilabile;
  private String letturaBoro;
  private String diluizioneBoro;
  private String boroAssimilabile;

  public MicroelementiMetodoDTPA ()
  {
  }
  public MicroelementiMetodoDTPA ( long idRichiesta, String letturaFerro, String diluizioneFerro, String ferroAssimilabile, String letturaManganese, String diluizioneManganese, String manganeseAssimilabile, String letturaZinco, String diluizioneZinco, String zincoAssimilabile, String letturaRame, String diluizioneRame, String rameAssimilabile, String letturaBoro, String diluizioneBoro, String boroAssimilabile )
  {
    this.idRichiesta=idRichiesta;
    this.letturaFerro=letturaFerro;
    this.diluizioneFerro=diluizioneFerro;
    this.ferroAssimilabile=ferroAssimilabile;
    this.letturaManganese=letturaManganese;
    this.diluizioneManganese=diluizioneManganese;
    this.manganeseAssimilabile=manganeseAssimilabile;
    this.letturaZinco=letturaZinco;
    this.diluizioneZinco=diluizioneZinco;
    this.zincoAssimilabile=zincoAssimilabile;
    this.letturaRame=letturaRame;
    this.diluizioneRame=diluizioneRame;
    this.rameAssimilabile=rameAssimilabile;
    this.letturaBoro=letturaBoro;
    this.diluizioneBoro=diluizioneBoro;
    this.boroAssimilabile=boroAssimilabile;
  }

  /**
   * Questo metodo va a leggere il record della tabella MICROELEMENTI_METODO_DTPA
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
      query = new StringBuffer("SELECT LETTURA_FERRO, DILUIZIONE_FERRO,");
      query.append("FERRO_ASSIMILABILE, LETTURA_MANGANESE, DILUIZIONE_MANGANESE,");
      query.append("MANGANESE_ASSIMILABILE, LETTURA_ZINCO, DILUIZIONE_ZINCO,");
      query.append("ZINCO_ASSIMILABILE, LETTURA_RAME, DILUIZIONE_RAME,");
      query.append("RAME_ASSIMILABILE, LETTURA_BORO,");
      query.append("DILUIZIONE_BORO, BORO_ASSIMILABILE ");
      query.append("FROM MICROELEMENTI_METODO_DTPA ");
      query.append("WHERE ID_RICHIESTA = ");
      query.append(getIdRichiesta());
      //CuneoLogger.debug(this, query.toString());
      ResultSet rs=stmt.executeQuery(query.toString());
      String temp;
      if (rs.next())
      {
        temp=rs.getString("LETTURA_FERRO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaFerro(temp);

        temp=rs.getString("DILUIZIONE_FERRO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiluizioneFerro(temp);

        temp=rs.getString("FERRO_ASSIMILABILE");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setFerroAssimilabile(temp);

        temp=rs.getString("LETTURA_MANGANESE");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaManganese(temp);

        temp=rs.getString("DILUIZIONE_MANGANESE");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiluizioneManganese(temp);

        temp=rs.getString("MANGANESE_ASSIMILABILE");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setManganeseAssimilabile(temp);

        temp=rs.getString("LETTURA_ZINCO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaZinco(temp);

        temp=rs.getString("DILUIZIONE_ZINCO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiluizioneZinco(temp);

        temp=rs.getString("ZINCO_ASSIMILABILE");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setZincoAssimilabile(temp);

        temp=rs.getString("LETTURA_RAME");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaRame(temp);

        temp=rs.getString("DILUIZIONE_RAME");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiluizioneRame(temp);

        temp=rs.getString("RAME_ASSIMILABILE");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setRameAssimilabile(temp);

        temp=rs.getString("LETTURA_BORO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaBoro(temp);

        temp=rs.getString("DILUIZIONE_BORO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiluizioneBoro(temp);

        temp=rs.getString("BORO_ASSIMILABILE");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setBoroAssimilabile(temp);

        return true;
      }
      else return false;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe MicroelementiMetodoDTPA");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe MicroelementiMetodoDTPA"
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
   * MICROELEMENTI_METODO_DTPA. Alcuni dati sono quelli di input inseriti
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
      /*

      */
      query = new StringBuffer("INSERT INTO MICROELEMENTI_METODO_DTPA(");
      query.append("ID_RICHIESTA,LETTURA_FERRO, DILUIZIONE_FERRO,");
      query.append("FERRO_ASSIMILABILE, LETTURA_MANGANESE, DILUIZIONE_MANGANESE,");
      query.append("MANGANESE_ASSIMILABILE, LETTURA_ZINCO, DILUIZIONE_ZINCO,");
      query.append("ZINCO_ASSIMILABILE, LETTURA_RAME, DILUIZIONE_RAME,");
      query.append("RAME_ASSIMILABILE, LETTURA_BORO,");
      query.append("DILUIZIONE_BORO, BORO_ASSIMILABILE");
      query.append(") ");
      query.append("VALUES(");
      query.append(getIdRichiesta()).append(",");
      query.append(getLetturaFerro()).append(",");
      query.append(getDiluizioneFerro()).append(",");
      query.append(getFerroAssimilabileCalcoli()).append(",");
      query.append(getLetturaManganese()).append(",");
      query.append(getDiluizioneManganese()).append(",");
      query.append(getManganeseAssimilabileCalcoli()).append(",");
      query.append(getLetturaZinco()).append(",");
      query.append(getDiluizioneZinco()).append(",");
      query.append(getZincoAssimilabileCalcoli()).append(",");
      query.append(getLetturaRame()).append(",");
      query.append(getDiluizioneRame()).append(",");
      query.append(getRameAssimilabileCalcoli()).append(",");
      query.append(getLetturaBoro()).append(",");
      query.append(getDiluizioneBoro()).append(",");
      query.append(getBoroAssimilabileCalcoli());
      query.append(")");
      //CuneoLogger.debug(this, query.toString());
      stmt.executeUpdate(query.toString());
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("insert della classe MicroelementiMetodoDTPA");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("insert della classe MicroelementiMetodoDTPA"
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
   * MICROELEMENTI_METODO_DTPA. Alcuni dati sono quelli di input inseriti
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
      query = new StringBuffer("UPDATE MICROELEMENTI_METODO_DTPA ");
      query.append("SET LETTURA_FERRO= ").append(getLetturaFerro());
      query.append(",DILUIZIONE_FERRO = ").append(getDiluizioneFerro());
      query.append(",FERRO_ASSIMILABILE = ").append(getFerroAssimilabileCalcoli());
      query.append(",LETTURA_MANGANESE = ").append(getLetturaManganese());
      query.append(",DILUIZIONE_MANGANESE = ").append(getDiluizioneManganese());
      query.append(",MANGANESE_ASSIMILABILE = ").append(getManganeseAssimilabileCalcoli());
      query.append(",LETTURA_ZINCO = ").append(getLetturaZinco());
      query.append(",DILUIZIONE_ZINCO = ").append(getDiluizioneZinco());
      query.append(",ZINCO_ASSIMILABILE = ").append(getZincoAssimilabileCalcoli());
      query.append(",LETTURA_RAME = ").append(getLetturaRame());
      query.append(",DILUIZIONE_RAME = ").append(getDiluizioneRame());
      query.append(",RAME_ASSIMILABILE = ").append(getRameAssimilabileCalcoli());
      query.append(",LETTURA_BORO = ").append(getLetturaBoro());
      query.append(",DILUIZIONE_BORO = ").append(getDiluizioneBoro());
      query.append(",BORO_ASSIMILABILE = ").append(getBoroAssimilabileCalcoli());
      query.append(" WHERE ID_RICHIESTA = ");query.append(getIdRichiesta());
      //CuneoLogger.debug(this, query.toString());
      stmt.executeUpdate(query.toString());
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("update della classe MicroelementiMetodoDTPA");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("update della classe MicroelementiMetodoDTPA"
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
   * MICROELEMENTI_METODO_DTPA.
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
      query = new StringBuffer("DELETE FROM MICROELEMENTI_METODO_DTPA ");
      query.append("WHERE ID_RICHIESTA =").append(getIdRichiesta());
      //CuneoLogger.debug(this, query.toString());
      stmt.executeUpdate(query.toString());
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("delete della classe MicroelementiMetodoDTPA");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("delete della classe MicroelementiMetodoDTPA"
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

  public Double getFerroAssimilabileCalcoli()
  {
    double ferroAssimilabile=0;
    try
    {
      double letturaFerro=Double.parseDouble(getLetturaFerro());
      double diluizioneFerro=Double.parseDouble(getDiluizioneFerro());
      if (letturaFerro>0 && diluizioneFerro>0)
        ferroAssimilabile=letturaFerro*diluizioneFerro*2;
    }
    catch(Exception num)
    {
      ferroAssimilabile=0;
    }
    if (ferroAssimilabile==0) return null;
    else return new Double(ferroAssimilabile);
  }

  public Double getManganeseAssimilabileCalcoli()
  {
    double manganeseAssimilabile=0;
    try
    {
      double letturaManganese=Double.parseDouble(getLetturaManganese());
      double diluizioneManganese=Double.parseDouble(getDiluizioneManganese());
      if (letturaManganese>0 && diluizioneManganese>0)
        manganeseAssimilabile=letturaManganese*diluizioneManganese*2;
    }
    catch(Exception num)
    {
      manganeseAssimilabile=0;
    }
    if (manganeseAssimilabile==0) return null;
    else return new Double(manganeseAssimilabile);
  }

  public Double getBoroAssimilabileCalcoli()
  {
    double boroAssimilabile=0;
    try
    {
      double letturaBoro=Double.parseDouble(getLetturaBoro());
      double diluizioneBoro=Double.parseDouble(getDiluizioneBoro());
      if (letturaBoro>0 && diluizioneBoro>0)
        boroAssimilabile=letturaBoro*diluizioneBoro;
    }
    catch(Exception num)
    {
      boroAssimilabile=0;
    }
    if (boroAssimilabile==0) return null;
    else return new Double(boroAssimilabile);
  }

  public Double getZincoAssimilabileCalcoli()
  {
    double zincoAssimilabile=0;
    try
    {
      double letturaZinco=Double.parseDouble(getLetturaZinco());
      double diluizioneZinco=Double.parseDouble(getDiluizioneZinco());
      if (letturaZinco>0 && diluizioneZinco>0)
        zincoAssimilabile=letturaZinco*diluizioneZinco*2;
    }
    catch(Exception num)
    {
      zincoAssimilabile=0;
    }
    if (zincoAssimilabile==0) return null;
    else return new Double(zincoAssimilabile);
  }

  public Double getRameAssimilabileCalcoli()
  {
    double rameAssimilabile=0;
    try
    {
      double letturaRame=Double.parseDouble(getLetturaRame());
      double diluizioneRame=Double.parseDouble(getDiluizioneRame());
      if (letturaRame>0 && diluizioneRame>0)
        rameAssimilabile=letturaRame*diluizioneRame*2;
    }
    catch(Exception num)
    {
      rameAssimilabile=0;
    }
    if (rameAssimilabile==0) return null;
    else return new Double(rameAssimilabile);
  }

  /**
   * Questa funzione controlla che i dati che verranno inseriti o modificati nel
   * database siano consistenti
   * */
  public String ControllaDati()
  {
    return ControllaDati(false,false,false,false,false);
  }
  public String ControllaDati(boolean ferro,boolean manganese,
                              boolean zinco,boolean rame,boolean boro)
  {
    StringBuffer errore=new StringBuffer("");
    if (ferro)
    {
      if (!Utili.isDouble(getLetturaFerro(),0,999999.999,3))
        errore.append(";1");
      if (!Utili.isDouble(getDiluizioneFerro(),0,999999.999,3))
        errore.append(";2");
    }

    if (manganese)
    {
      if (!Utili.isDouble(getLetturaManganese(),0,999999.999,3))
        errore.append(";3");
      if (!Utili.isDouble(getDiluizioneManganese(),0,999999.999,3))
        errore.append(";4");
    }

    if (zinco)
    {
      if (!Utili.isDouble(getLetturaZinco(),0,999999.999,3))
        errore.append(";5");
      if (!Utili.isDouble(getDiluizioneZinco(),0,999999.999,3))
        errore.append(";6");
    }

    if (rame)
    {
      if (!Utili.isDouble(getLetturaRame(),0,999999.999,3))
        errore.append(";7");
      if (!Utili.isDouble(getDiluizioneRame(),0,999999.999,3))
        errore.append(";8");
    }

    if (boro)
    {
      if (!Utili.isDouble(getLetturaBoro(),0,999999.999,3))
        errore.append(";9");
      if (!Utili.isDouble(getDiluizioneBoro(),0,999999.999,3))
        errore.append(";10");
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

  public String getLetturaFerro()
  {
    return this.letturaFerro;
  }
  public void setLetturaFerro( String newLetturaFerro )
  {
    if (newLetturaFerro!=null) letturaFerro=newLetturaFerro.replace(',','.');
    else this.letturaFerro = newLetturaFerro;
  }

  public String getDiluizioneFerro()
  {
    return this.diluizioneFerro;
  }
  public void setDiluizioneFerro( String newDiluizioneFerro )
  {
    if (newDiluizioneFerro!=null) diluizioneFerro=newDiluizioneFerro.replace(',','.');
    else this.diluizioneFerro = newDiluizioneFerro;
  }

  public String getFerroAssimilabilePDF()
  {
    if (ferroAssimilabile==null) return "";
    else
    {
      ferroAssimilabile=ferroAssimilabile.replace(',','.');
      ferroAssimilabile=Utili.nf1.format(Double.parseDouble(ferroAssimilabile));
      ferroAssimilabile=ferroAssimilabile.replace('.',',');
      return ferroAssimilabile;
    }
  }

  public String getFerroAssimilabile()
  {
    return this.ferroAssimilabile;
  }
  public void setFerroAssimilabile( String newFerroAssimilabile )
  {
    if (newFerroAssimilabile!=null) ferroAssimilabile=newFerroAssimilabile.replace(',','.');
    else this.ferroAssimilabile = newFerroAssimilabile;
  }

  public String getLetturaManganese()
  {
    return this.letturaManganese;
  }
  public void setLetturaManganese( String newLetturaManganese )
  {
    if (newLetturaManganese!=null) letturaManganese=newLetturaManganese.replace(',','.');
    else this.letturaManganese = newLetturaManganese;
  }

  public String getDiluizioneManganese()
  {
    return this.diluizioneManganese;
  }
  public void setDiluizioneManganese( String newDiluizioneManganese )
  {
    if (newDiluizioneManganese!=null) diluizioneManganese=newDiluizioneManganese.replace(',','.');
    else this.diluizioneManganese = newDiluizioneManganese;
  }

  public String getManganeseAssimilabilePDF()
  {
    if (manganeseAssimilabile==null) return "";
    else
    {
      manganeseAssimilabile=manganeseAssimilabile.replace(',','.');
      manganeseAssimilabile=Utili.nf1.format(Double.parseDouble(manganeseAssimilabile));
      manganeseAssimilabile=manganeseAssimilabile.replace('.',',');
      return manganeseAssimilabile;
    }
  }

  public String getManganeseAssimilabile()
  {
    return this.manganeseAssimilabile;
  }
  public void setManganeseAssimilabile( String newManganeseAssimilabile )
  {
    if (newManganeseAssimilabile!=null) manganeseAssimilabile=newManganeseAssimilabile.replace(',','.');
    else this.manganeseAssimilabile = newManganeseAssimilabile;
  }

  public String getLetturaZinco()
  {
    return this.letturaZinco;
  }
  public void setLetturaZinco( String newLetturaZinco )
  {
    if (newLetturaZinco!=null) letturaZinco=newLetturaZinco.replace(',','.');
    else this.letturaZinco = newLetturaZinco;
  }

  public String getDiluizioneZinco()
  {
    return this.diluizioneZinco;
  }
  public void setDiluizioneZinco( String newDiluizioneZinco )
  {
    if (newDiluizioneZinco!=null) diluizioneZinco=newDiluizioneZinco.replace(',','.');
    else this.diluizioneZinco = newDiluizioneZinco;
  }

  public String getZincoAssimilabilePDF()
  {
    if (zincoAssimilabile==null) return "";
    else
    {
      zincoAssimilabile=zincoAssimilabile.replace(',','.');
      zincoAssimilabile=Utili.nf1.format(Double.parseDouble(zincoAssimilabile));
      zincoAssimilabile=zincoAssimilabile.replace('.',',');
      return zincoAssimilabile;
    }
  }

  public String getZincoAssimilabile()
  {
    return this.zincoAssimilabile;
  }
  public void setZincoAssimilabile( String newZincoAssimilabile )
  {
    if (newZincoAssimilabile!=null) zincoAssimilabile=newZincoAssimilabile.replace(',','.');
    else this.zincoAssimilabile = newZincoAssimilabile;
  }

  public String getLetturaRame()
  {
    return this.letturaRame;
  }
  public void setLetturaRame( String newLetturaRame )
  {
    if (newLetturaRame!=null) letturaRame=newLetturaRame.replace(',','.');
    else this.letturaRame = newLetturaRame;
  }

  public String getDiluizioneRame()
  {
    return this.diluizioneRame;
  }
  public void setDiluizioneRame( String newDiluizioneRame )
  {
    if (newDiluizioneRame!=null) diluizioneRame=newDiluizioneRame.replace(',','.');
    else this.diluizioneRame = newDiluizioneRame;
  }

  public String getRameAssimilabilePDF()
  {
    if (rameAssimilabile==null) return "";
    else
    {
      rameAssimilabile=rameAssimilabile.replace(',','.');
      rameAssimilabile=Utili.nf1.format(Double.parseDouble(rameAssimilabile));
      rameAssimilabile=rameAssimilabile.replace('.',',');
      return rameAssimilabile;
    }
  }

  public String getRameAssimilabile()
  {
    return this.rameAssimilabile;
  }
  public void setRameAssimilabile( String newRameAssimilabile )
  {
    if (newRameAssimilabile!=null) rameAssimilabile=newRameAssimilabile.replace(',','.');
    else this.rameAssimilabile = newRameAssimilabile;
  }

  public String getLetturaBoro()
  {
    return this.letturaBoro;
  }
  public void setLetturaBoro( String newLetturaBoro )
  {
    if (newLetturaBoro!=null) letturaBoro=newLetturaBoro.replace(',','.');
    else this.letturaBoro = newLetturaBoro;
  }

  public String getDiluizioneBoro()
  {
    return this.diluizioneBoro;
  }
  public void setDiluizioneBoro( String newDiluizioneBoro )
  {
    if (newDiluizioneBoro!=null) diluizioneBoro=newDiluizioneBoro.replace(',','.');
    else this.diluizioneBoro = newDiluizioneBoro;
  }

  public String getBoroAssimilabilePDF()
  {
    if (boroAssimilabile==null) return "";
    else
    {
      boroAssimilabile=boroAssimilabile.replace(',','.');
      boroAssimilabile=Utili.nf2.format(Double.parseDouble(boroAssimilabile));
      boroAssimilabile=boroAssimilabile.replace('.',',');
      return boroAssimilabile;
    }
  }

  public String getBoroAssimilabile()
  {
    return this.boroAssimilabile;
  }
  public void setBoroAssimilabile( String newBoroAssimilabile )
  {
    if (newBoroAssimilabile!=null) boroAssimilabile=newBoroAssimilabile.replace(',','.');
    else this.boroAssimilabile = newBoroAssimilabile;
  }
}