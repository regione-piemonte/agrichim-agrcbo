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

public class GranulometriaMetodoBojoucos  extends ModelloInsEsitoAnalisi
{
  private long idRichiesta;
  private String letturaDensita115;
  private String letturaDensita130;
  private String letturaDensita145;
  private String densitaSoluzFondo1;
  private String temperatura1;
  private String fattoreTempGranulare1;
  private String diametro115;
  private String diametro130;
  private String diametro145;
  private String densitaLorda5012;
  private String densitaLorda5013;
  private String densitaLorda5023;
  private String densitaLorda50;
  private String sabbia;
  private String letturaDensita930;
  private String letturaDensita10;
  private String letturaDensita1045;
  private String densitaSoluzFondo2;
  private String temperatura2;
  private String fattoreTempGranulare2;
  private String diametro930;
  private String diametro10;
  private String diametro1045;
  private String densitaLorda2045;
  private String densitaLorda2046;
  private String densitaLorda2056;
  private String densitaLorda20;
  private String limoGrosso;
  private String letturaDensita17;
  private String letturaDensita1830;
  private String letturaDensita20;
  private String densitaBianco17;
  private String densitaBianco1830;
  private String densitaBianco20;
  private String densitaMediaBianco3;
  private String temperatura17;
  private String temperatura1830;
  private String temperatura20;
  private String temperaturaMedia3;
  private String fattoreTempGranulare3;
  private String diametro17;
  private String diametro1830;
  private String diametro20;
  private String densitaLorda278;
  private String densitaLorda279;
  private String densitaLorda289;
  private String densitaLorda2;
  private String limoFine;
  private String argilla1;
  private String limo;
  private String argilla2;

  public GranulometriaMetodoBojoucos ()
  {
  }
  public GranulometriaMetodoBojoucos ( long idRichiesta, String letturaDensita115, String letturaDensita130, String letturaDensita145, String densitaSoluzFondo1, String temperatura1, String fattoreTempGranulare1, String diametro115, String diametro130, String diametro145, String densitaLorda5012, String densitaLorda5013, String densitaLorda5023, String densitaLorda50, String sabbia, String letturaDensita930, String letturaDensita10, String letturaDensita1045, String densitaSoluzFondo2, String temperatura2, String fattoreTempGranulare2, String diametro930, String diametro10, String diametro1045, String densitaLorda2045, String densitaLorda2046, String densitaLorda2056, String densitaLorda20, String limoGrosso, String letturaDensita17, String letturaDensita1830, String letturaDensita20, String densitaBianco17, String densitaBianco1830, String densitaBianco20, String densitaMediaBianco3, String temperatura17, String temperatura1830, String temperatura20, String temperaturaMedia3, String fattoreTempGranulare3, String diametro17, String diametro1830, String diametro20, String densitaLorda278, String densitaLorda279, String densitaLorda289, String densitaLorda2, String limoFine, String argilla1, String limo, String argilla2 )
  {
    this.idRichiesta=idRichiesta;
    this.letturaDensita115=letturaDensita115;
    this.letturaDensita130=letturaDensita130;
    this.letturaDensita145=letturaDensita145;
    this.densitaSoluzFondo1=densitaSoluzFondo1;
    this.temperatura1=temperatura1;
    this.fattoreTempGranulare1=fattoreTempGranulare1;
    this.diametro115=diametro115;
    this.diametro130=diametro130;
    this.diametro145=diametro145;
    this.densitaLorda5012=densitaLorda5012;
    this.densitaLorda5013=densitaLorda5013;
    this.densitaLorda5023=densitaLorda5023;
    this.densitaLorda50=densitaLorda50;
    this.sabbia=sabbia;
    this.letturaDensita930=letturaDensita930;
    this.letturaDensita10=letturaDensita10;
    this.letturaDensita1045=letturaDensita1045;
    this.densitaSoluzFondo2=densitaSoluzFondo2;
    this.temperatura2=temperatura2;
    this.fattoreTempGranulare2=fattoreTempGranulare2;
    this.diametro930=diametro930;
    this.diametro10=diametro10;
    this.diametro1045=diametro1045;
    this.densitaLorda2045=densitaLorda2045;
    this.densitaLorda2046=densitaLorda2046;
    this.densitaLorda2056=densitaLorda2056;
    this.densitaLorda20=densitaLorda20;
    this.limoGrosso=limoGrosso;
    this.letturaDensita17=letturaDensita17;
    this.letturaDensita1830=letturaDensita1830;
    this.letturaDensita20=letturaDensita20;
    this.densitaBianco17=densitaBianco17;
    this.densitaBianco1830=densitaBianco1830;
    this.densitaBianco20=densitaBianco20;
    this.densitaMediaBianco3=densitaMediaBianco3;
    this.temperatura17=temperatura17;
    this.temperatura1830=temperatura1830;
    this.temperatura20=temperatura20;
    this.temperaturaMedia3=temperaturaMedia3;
    this.fattoreTempGranulare3=fattoreTempGranulare3;
    this.diametro17=diametro17;
    this.diametro1830=diametro1830;
    this.diametro20=diametro20;
    this.densitaLorda278=densitaLorda278;
    this.densitaLorda279=densitaLorda279;
    this.densitaLorda289=densitaLorda289;
    this.densitaLorda2=densitaLorda2;
    this.limoFine=limoFine;
    this.argilla1=argilla1;
    this.limo=limo;
    this.argilla2=argilla2;
  }


  /**
   * Questo metodo va a leggere il record della tabella GRANULOMETRIA_METODO_BOJOUCOS
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
      query = new StringBuffer("");
      query.append("SELECT * FROM GRANULOMETRIA_METODO_BOJOUCOS ");
      query.append("WHERE ID_RICHIESTA = ");
      query.append(getIdRichiesta());
      //CuneoLogger.debug(this, query.toString());
      ResultSet rs=stmt.executeQuery(query.toString());
      String temp;
      if (rs.next())
      {
        temp=rs.getString("LETTURA_DENSITA_1_15");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaDensita115(temp);

        temp=rs.getString("LETTURA_DENSITA_1_30");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaDensita130(temp);

        temp=rs.getString("LETTURA_DENSITA_1_45");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaDensita145(temp);

        temp=rs.getString("DENSITA_SOLUZ_FONDO_1");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaSoluzFondo1(temp);

        temp=rs.getString("TEMPERATURA_1");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setTemperatura1(temp);

        temp=rs.getString("FATTORE_TEMP_GRANULARE_1");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setFattoreTempGranulare1(temp);

        temp=rs.getString("DIAMETRO_1_15");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiametro115(temp);

        temp=rs.getString("DIAMETRO_1_30");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiametro130(temp);

        temp=rs.getString("DIAMETRO_1_45");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiametro145(temp);

        temp=rs.getString("DENSITA_LORDA_50_1_2");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaLorda5012(temp);

        temp=rs.getString("DENSITA_LORDA_50_1_3");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaLorda5013(temp);

        temp=rs.getString("DENSITA_LORDA_50_2_3");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaLorda5023(temp);

        temp=rs.getString("DENSITA_LORDA_50");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaLorda50(temp);

        temp=rs.getString("SABBIA");
        if (temp!=null) temp=Utili.nf1.format(Double.parseDouble(temp));
        this.setSabbia(temp);

        temp=rs.getString("LETTURA_DENSITA_9_30");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaDensita930(temp);

        temp=rs.getString("LETTURA_DENSITA_10");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaDensita10(temp);

        temp=rs.getString("LETTURA_DENSITA_10_45");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaDensita1045(temp);

        temp=rs.getString("DENSITA_SOLUZ_FONDO_2");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaSoluzFondo2(temp);

        temp=rs.getString("TEMPERATURA_2");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setTemperatura2(temp);

        temp=rs.getString("FATTORE_TEMP_GRANULARE_2");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setFattoreTempGranulare2(temp);

        temp=rs.getString("DIAMETRO_9_30");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiametro930(temp);

        temp=rs.getString("DIAMETRO_10");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiametro10(temp);

        temp=rs.getString("DIAMETRO_10_45");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiametro1045(temp);

        temp=rs.getString("DENSITA_LORDA_20_4_5");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaLorda2045(temp);


        temp=rs.getString("DENSITA_LORDA_20_4_6");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaLorda2046(temp);

        temp=rs.getString("DENSITA_LORDA_20_5_6");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaLorda2056(temp);

        temp=rs.getString("DENSITA_LORDA_20");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaLorda20(temp);

        temp=rs.getString("LIMO_GROSSO");
        if (temp!=null) temp=Utili.nf1.format(Double.parseDouble(temp));
        this.setLimoGrosso(temp);

        temp=rs.getString("LETTURA_DENSITA_17");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaDensita17(temp);

        temp=rs.getString("LETTURA_DENSITA_18_30");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaDensita1830(temp);

        temp=rs.getString("LETTURA_DENSITA_20");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaDensita20(temp);

        temp=rs.getString("DENSITA_BIANCO_17");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaBianco17(temp);

        temp=rs.getString("DENSITA_BIANCO_18_30");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaBianco1830(temp);

        temp=rs.getString("DENSITA_BIANCO_20");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaBianco20(temp);

        temp=rs.getString("DENSITA_MEDIA_BIANCO_3");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaMediaBianco3(temp);

        temp=rs.getString("TEMPERATURA_17");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setTemperatura17(temp);

        temp=rs.getString("TEMPERATURA_18_30");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setTemperatura1830(temp);

        temp=rs.getString("TEMPERATURA_20");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setTemperatura20(temp);

        temp=rs.getString("TEMPERATURA_MEDIA_3");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setTemperaturaMedia3(temp);

        temp=rs.getString("FATTORE_TEMP_GRANULARE_3");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setFattoreTempGranulare3(temp);

        temp=rs.getString("DIAMETRO_17");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiametro17(temp);

        temp=rs.getString("DIAMETRO_18_30");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiametro1830(temp);

        temp=rs.getString("DIAMETRO_20");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiametro20(temp);

        temp=rs.getString("DENSITA_LORDA_2_7_8");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaLorda278(temp);

        temp=rs.getString("DENSITA_LORDA_2_7_9");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaLorda279(temp);

        temp=rs.getString("DENSITA_LORDA_2_8_9");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaLorda289(temp);

        temp=rs.getString("DENSITA_LORDA_2");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaLorda2(temp);

        temp=rs.getString("LIMO_FINE");
        if (temp!=null) temp=Utili.nf1.format(Double.parseDouble(temp));
        this.setLimoFine(temp);

        temp=rs.getString("ARGILLA_1");
        if (temp!=null) temp=Utili.nf1.format(Double.parseDouble(temp));
        this.setArgilla1(temp);

        temp=rs.getString("LIMO");
        if (temp!=null) temp=Utili.nf1.format(Double.parseDouble(temp));
        this.setLimo(temp);

        temp=rs.getString("ARGILLA_2");
        if (temp!=null) temp=Utili.nf1.format(Double.parseDouble(temp));
        this.setArgilla2(temp);

        return true;
      }
      else return false;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe GranulometriaMetodoBojoucos");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe GranulometriaMetodoBojoucos"
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
   * GRANULOMETRIA_METODO_BOJOUCOS. Alcuni dati sono quelli di input inseriti
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
      query = new StringBuffer("INSERT INTO GRANULOMETRIA_METODO_BOJOUCOS(");
      query.append("ID_RICHIESTA, LETTURA_DENSITA_1_15, LETTURA_DENSITA_1_30,");
      query.append("LETTURA_DENSITA_1_45, DENSITA_SOLUZ_FONDO_1, TEMPERATURA_1,");
      query.append("FATTORE_TEMP_GRANULARE_1, DIAMETRO_1_15, DIAMETRO_1_30, DIAMETRO_1_45,");
      query.append("DENSITA_LORDA_50_1_2, DENSITA_LORDA_50_1_3, DENSITA_LORDA_50_2_3,");
      query.append("DENSITA_LORDA_50, SABBIA, LETTURA_DENSITA_9_30, LETTURA_DENSITA_10,");
      query.append("LETTURA_DENSITA_10_45, DENSITA_SOLUZ_FONDO_2, TEMPERATURA_2,");
      query.append("FATTORE_TEMP_GRANULARE_2, DIAMETRO_9_30, DIAMETRO_10, DIAMETRO_10_45,");
      query.append("DENSITA_LORDA_20_4_5, DENSITA_LORDA_20_4_6, DENSITA_LORDA_20_5_6,");
      query.append("DENSITA_LORDA_20, LIMO_GROSSO, LETTURA_DENSITA_17, LETTURA_DENSITA_18_30,");
      query.append("LETTURA_DENSITA_20, DENSITA_BIANCO_17, DENSITA_BIANCO_18_30,");
      query.append("DENSITA_BIANCO_20, DENSITA_MEDIA_BIANCO_3, TEMPERATURA_17,");
      query.append("TEMPERATURA_18_30, TEMPERATURA_20, TEMPERATURA_MEDIA_3,");
      query.append("FATTORE_TEMP_GRANULARE_3, DIAMETRO_17, DIAMETRO_18_30, DIAMETRO_20,");
      query.append("DENSITA_LORDA_2_7_8, DENSITA_LORDA_2_7_9, DENSITA_LORDA_2_8_9,");
      query.append("DENSITA_LORDA_2, LIMO_FINE, ARGILLA_1, LIMO, ARGILLA_2");
      query.append(") ");
      query.append("VALUES(");
      query.append(getIdRichiesta()).append(",");
      query.append(getLetturaDensita115()).append(",");
      query.append(getLetturaDensita130()).append(",");
      query.append(getLetturaDensita145()).append(",");
      query.append(getDensitaSoluzFondo1()).append(",");
      query.append(getTemperatura1()).append(",");
      query.append(getFattoreTempGranulare1Calcoli()).append(",");
      query.append(getDiametro115Calcoli()).append(",");
      query.append(getDiametro130Calcoli()).append(",");
      query.append(getDiametro145Calcoli()).append(",");
      query.append(getDensitaLorda5012Calcoli()).append(",");
      query.append(getDensitaLorda5013Calcoli()).append(",");
      query.append(getDensitaLorda5023Calcoli()).append(",");
      query.append(getDensitaLorda50Calcoli()).append(",");
      query.append(getSabbiaCalcoli()).append(",");
      query.append(getLetturaDensita930()).append(",");
      query.append(getLetturaDensita10()).append(",");
      query.append(getLetturaDensita1045()).append(",");
      query.append(getDensitaSoluzFondo2()).append(",");
      query.append(getTemperatura2()).append(",");
      query.append(getFattoreTempGranulare2Calcoli()).append(",");
      query.append(getDiametro930Calcoli()).append(",");
      query.append(getDiametro10Calcoli()).append(",");
      query.append(getDiametro1045Calcoli()).append(",");
      query.append(getDensitaLorda2045Calcoli()).append(",");
      query.append(getDensitaLorda2046Calcoli()).append(",");
      query.append(getDensitaLorda2056Calcoli()).append(",");
      query.append(getDensitaLorda20Calcoli()).append(",");
      query.append(getLimoGrossoCalcoli()).append(",");
      query.append(getLetturaDensita17()).append(",");
      query.append(getLetturaDensita1830()).append(",");
      query.append(getLetturaDensita20()).append(",");
      query.append(getDensitaBianco17()).append(",");
      query.append(getDensitaBianco1830()).append(",");
      query.append(getDensitaBianco20()).append(",");
      query.append(getDensitaMediaBianco3()).append(",");
      query.append(getTemperatura17()).append(",");
      query.append(getTemperatura1830()).append(",");
      query.append(getTemperatura20()).append(",");
      query.append(getTemperaturaMedia3Calcoli()).append(",");
      query.append(getFattoreTempGranulare3Calcoli()).append(",");
      query.append(getDiametro17Calcoli()).append(",");
      query.append(getDiametro1830Calcoli()).append(",");
      query.append(getDiametro20Calcoli()).append(",");
      query.append(getDensitaLorda278Calcoli()).append(",");
      query.append(getDensitaLorda279Calcoli()).append(",");
      query.append(getDensitaLorda289Calcoli()).append(",");
      query.append(getDensitaLorda2Calcoli()).append(",");
      query.append(getLimoFineCalcoli()).append(",");
      query.append(getArgilla1Calcoli()).append(",");
      query.append(getLimoCalcoli()).append(",");
      query.append(getArgilla2Calcoli());
      query.append(")");
      //CuneoLogger.debug(this, query.toString());
      stmt.executeUpdate(query.toString());
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("insert della classe GranulometriaMetodoBojoucos");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("insert della classe GranulometriaMetodoBojoucos"
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
   * GRANULOMETRIA_METODO_BOJOUCOS. Alcuni dati sono quelli di input inseriti
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
      query = new StringBuffer("UPDATE GRANULOMETRIA_METODO_BOJOUCOS ");
      query.append("SET LETTURA_DENSITA_1_15 = ").append(getLetturaDensita115());
      query.append(", LETTURA_DENSITA_1_30 = ").append(getLetturaDensita130());
      query.append(",LETTURA_DENSITA_1_45 = ").append(getLetturaDensita145());
      query.append(", DENSITA_SOLUZ_FONDO_1 = ").append(getDensitaSoluzFondo1());
      query.append(", TEMPERATURA_1 = ").append(getTemperatura1());
      query.append(", FATTORE_TEMP_GRANULARE_1 = ").append(getFattoreTempGranulare1Calcoli());
      query.append(", DIAMETRO_1_15 = ").append(getDiametro115Calcoli());
      query.append(", DIAMETRO_1_30 = ").append(getDiametro130Calcoli());
      query.append(", DIAMETRO_1_45 = ").append(getDiametro145Calcoli());
      query.append(", DENSITA_LORDA_50_1_2 = ").append(getDensitaLorda5012Calcoli());
      query.append(", DENSITA_LORDA_50_1_3 = ").append(getDensitaLorda5013Calcoli());
      query.append(", DENSITA_LORDA_50_2_3 = ").append(getDensitaLorda5023Calcoli());
      query.append(", DENSITA_LORDA_50 = ").append(getDensitaLorda50Calcoli());
      query.append(", SABBIA = ").append(getSabbiaCalcoli());
      query.append(", LETTURA_DENSITA_9_30 = ").append(getLetturaDensita930());
      query.append(", LETTURA_DENSITA_10 = ").append(getLetturaDensita10());
      query.append(", LETTURA_DENSITA_10_45 = ").append(getLetturaDensita1045());
      query.append(", DENSITA_SOLUZ_FONDO_2 = ").append(getDensitaSoluzFondo2());
      query.append(", TEMPERATURA_2 = ").append(getTemperatura2());
      query.append(", FATTORE_TEMP_GRANULARE_2 = ").append(getFattoreTempGranulare2Calcoli());
      query.append(", DIAMETRO_9_30 = ").append(getDiametro930Calcoli());
      query.append(", DIAMETRO_10 = ").append(getDiametro10Calcoli());
      query.append(", DIAMETRO_10_45 = ").append(getDiametro1045Calcoli());
      query.append(", DENSITA_LORDA_20_4_5 = ").append(getDensitaLorda2045Calcoli());
      query.append(", DENSITA_LORDA_20_4_6 = ").append(getDensitaLorda2046Calcoli());
      query.append(", DENSITA_LORDA_20_5_6 = ").append(getDensitaLorda2056Calcoli());
      query.append(", DENSITA_LORDA_20 = ").append(getDensitaLorda20Calcoli());
      query.append(", LIMO_GROSSO = ").append(getLimoGrossoCalcoli());
      query.append(", LETTURA_DENSITA_17 = ").append(getLetturaDensita17());
      query.append(", LETTURA_DENSITA_18_30 = ").append(getLetturaDensita1830());
      query.append(", LETTURA_DENSITA_20 = ").append(getLetturaDensita20());
      query.append(", DENSITA_BIANCO_17 = ").append(getDensitaBianco17());
      query.append(", DENSITA_BIANCO_18_30 = ").append(getDensitaBianco1830());
      query.append(", DENSITA_BIANCO_20 = ").append(getDensitaBianco20());
      query.append(", DENSITA_MEDIA_BIANCO_3 = ").append(getDensitaMediaBianco3());
      query.append(", TEMPERATURA_17 = ").append(getTemperatura17());
      query.append(", TEMPERATURA_18_30 = ").append(getTemperatura1830());
      query.append(", TEMPERATURA_20 = ").append(getTemperatura20());
      query.append(", TEMPERATURA_MEDIA_3 = ").append(getTemperaturaMedia3Calcoli());
      query.append(", FATTORE_TEMP_GRANULARE_3 = ").append(getFattoreTempGranulare3Calcoli());
      query.append(", DIAMETRO_17 = ").append(getDiametro17Calcoli());
      query.append(", DIAMETRO_18_30 = ").append(getDiametro1830Calcoli());
      query.append(", DIAMETRO_20 = ").append(getDiametro20Calcoli());
      query.append(", DENSITA_LORDA_2_7_8 = ").append(getDensitaLorda278Calcoli());
      query.append(", DENSITA_LORDA_2_7_9 = ").append(getDensitaLorda279Calcoli());
      query.append(", DENSITA_LORDA_2_8_9 = ").append(getDensitaLorda289Calcoli());
      query.append(", DENSITA_LORDA_2 = ").append(getDensitaLorda2Calcoli());
      query.append(", LIMO_FINE = ").append(getLimoFineCalcoli());
      query.append(", ARGILLA_1 = ").append(getArgilla1Calcoli());
      query.append(", LIMO = ").append(getLimoCalcoli());
      query.append(", ARGILLA_2 = ").append(getArgilla2Calcoli());
      query.append(" WHERE ID_RICHIESTA = ");query.append(getIdRichiesta());
      //CuneoLogger.debug(this, query.toString());
      stmt.executeUpdate(query.toString());
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("update della classe GranulometriaMetodoBojoucos");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("update della classe GranulometriaMetodoBojoucos"
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
   * GRANULOMETRIA_METODO_BOJOUCOS.
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
      query = new StringBuffer("DELETE FROM GRANULOMETRIA_METODO_BOJOUCOS ");
      query.append("WHERE ID_RICHIESTA =").append(getIdRichiesta());
      //CuneoLogger.debug(this, query.toString());
      stmt.executeUpdate(query.toString());
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("delete della classe GranulometriaMetodoBojoucos");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("delete della classe GranulometriaMetodoBojoucos"
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


  public double getFattoreTempGranulare1Calcoli()
  {
    double fattoreTempGranulare1=0;
    try
    {
      double temperatura1=Double.parseDouble(this.getTemperatura1());
      fattoreTempGranulare1=((55.104*temperatura1)+18550) / ((1+(0.02078*temperatura1))*1000);
    }
    catch(Exception num)
    {
      fattoreTempGranulare1=0;
    }
    return fattoreTempGranulare1;
  }

  public Double getDiametro115Calcoli()
  {
    double diametro115=0;
    try
    {
       double letturaDensita115=Double.parseDouble(getLetturaDensita115());
       double fattoreTempGranulare1=getFattoreTempGranulare1Calcoli();
       if (letturaDensita115>0)
       {
         diametro115=fattoreTempGranulare1*(Math.sqrt((24.5-(0.13*letturaDensita115))/1.25));
       }
    }
    catch(Exception num)
    {
      diametro115=0;
    }
    if (diametro115==0) return null;
    else return new Double(diametro115);
  }

  public Double getDiametro130Calcoli()
  {
    double diametro130=0;
    try
    {
      double letturaDensita130=Double.parseDouble(getLetturaDensita130());
      double fattoreTempGranulare1=getFattoreTempGranulare1Calcoli();
      if (letturaDensita130>0)
      {
        diametro130=fattoreTempGranulare1*(Math.sqrt((24.5-(0.13*letturaDensita130))/1.50));
      }
    }
    catch(Exception num)
    {
      diametro130=0;
    }
    if (diametro130==0) return null;
    else return new Double(diametro130);
  }

  public Double getDiametro145Calcoli()
  {
    double diametro145=0;
    try
    {
      double letturaDensita145=Double.parseDouble(getLetturaDensita145());
      double fattoreTempGranulare1=getFattoreTempGranulare1Calcoli();
      if (letturaDensita145>0)
      {
        diametro145=fattoreTempGranulare1*(Math.sqrt((24.5-(0.13*letturaDensita145))/1.75));
      }
    }
    catch(Exception num)
    {
      diametro145=0;
    }
    if (diametro145==0) return null;
    else return new Double(diametro145);
  }

  public Double getDensitaLorda5012Calcoli()
  {
    double densitaLorda5012=0;
    try
    {
      double diametro115=-1,diametro130=-1,diametro145=-1;
      if (getDiametro115Calcoli()!=null)
        diametro115=getDiametro115Calcoli().doubleValue();
      if (getDiametro130Calcoli()!=null)
        diametro130=getDiametro130Calcoli().doubleValue();
      if (getDiametro145Calcoli()!=null)
        diametro145=getDiametro145Calcoli().doubleValue();
      double letturaDensita115=Double.parseDouble(getLetturaDensita115());
      double letturaDensita130=Double.parseDouble(getLetturaDensita130());
      if (diametro130>0 && diametro115>diametro130 && diametro145==0 || diametro130<50)
      {
        densitaLorda5012=letturaDensita115-(((diametro115-50)/(diametro115-diametro130))*(letturaDensita115-letturaDensita130));
      }
    }
    catch(Exception num)
    {
      densitaLorda5012=0;
    }
    if (densitaLorda5012==0) return null;
    else return new Double(densitaLorda5012);
  }

  public Double getDensitaLorda5013Calcoli()
  {
    double densitaLorda5013=0;
    try
    {
      double diametro115=-1,diametro130=-1,diametro145=-1;
      if (getDiametro115Calcoli()!=null)
        diametro115=getDiametro115Calcoli().doubleValue();
      if (getDiametro130Calcoli()!=null)
        diametro130=getDiametro130Calcoli().doubleValue();
      if (getDiametro145Calcoli()!=null)
        diametro145=getDiametro145Calcoli().doubleValue();
      double letturaDensita115=Double.parseDouble(getLetturaDensita115());
      double letturaDensita145=Double.parseDouble(getLetturaDensita145());
      if (diametro145>0 && diametro115>diametro145 && diametro130==0)
      {
        densitaLorda5013=letturaDensita115-(((diametro115-50)/(diametro115-diametro145))*(letturaDensita115-letturaDensita145));
      }
    }
    catch(Exception num)
    {
      densitaLorda5013=0;
    }
    if (densitaLorda5013==0) return null;
    else return new Double(densitaLorda5013);
  }

  public Double getDensitaLorda5023Calcoli()
  {
    double densitaLorda5023=0;
    try
    {
      double diametro115=-1,diametro130=-1,diametro145=-1;
      if (getDiametro115Calcoli()!=null)
        diametro115=getDiametro115Calcoli().doubleValue();
      if (getDiametro130Calcoli()!=null)
        diametro130=getDiametro130Calcoli().doubleValue();
      if (getDiametro145Calcoli()!=null)
        diametro145=getDiametro145Calcoli().doubleValue();
      double letturaDensita130=Double.parseDouble(getLetturaDensita130());
      double letturaDensita145=Double.parseDouble(getLetturaDensita145());
      if (diametro145>0 && diametro130>diametro145 && diametro115==0 || diametro130>=50)
      {
        densitaLorda5023=letturaDensita130-(((diametro130-50)/(diametro130-diametro145))*(letturaDensita130-letturaDensita145));
      }
    }
    catch(Exception num)
    {
      densitaLorda5023=0;
    }
    if (densitaLorda5023==0) return null;
    else return new Double(densitaLorda5023);
  }

  public Double getDensitaLorda50Calcoli()
  {
    return null;
  }

  public Double getSabbiaCalcoli()
  {
    double sabbia=0;
    try
    {
      double densitaSoluzFondo1=Double.parseDouble(getDensitaSoluzFondo1());
      double densitaLorda5012=-1,densitaLorda5013=-1,densitaLorda5023=-1;
      if (getDensitaLorda5012Calcoli()!=null)
        densitaLorda5012=getDensitaLorda5012Calcoli().doubleValue();
      if (getDensitaLorda5013Calcoli()!=null)
        densitaLorda5013=getDensitaLorda5013Calcoli().doubleValue();
      if (getDensitaLorda5023Calcoli()!=null)
        densitaLorda5023=getDensitaLorda5023Calcoli().doubleValue();
      if ((densitaLorda5012 >0 || densitaLorda5013>0 || densitaLorda5023>0) && densitaSoluzFondo1>0)
      {
        sabbia=100-((densitaLorda5012+densitaLorda5013+densitaLorda5023-densitaSoluzFondo1)*2);
      }
    }
    catch(Exception num)
    {
      sabbia=0;
    }
    if (sabbia==0) return null;
    else return new Double(sabbia);
  }

  public Double getFattoreTempGranulare2Calcoli()
  {
    double fattoreTempGranulare2=0;
    try
    {
      double temperatura2=Double.parseDouble(getTemperatura2());
      if (temperatura2>0)
        fattoreTempGranulare2=((55.104*temperatura2)+18550) / ((1+(0.02078*temperatura2))*1000);
    }
    catch(Exception num)
    {
      fattoreTempGranulare2=0;
    }
    if (fattoreTempGranulare2==0) return null;
    else return new Double(fattoreTempGranulare2);
  }

  public Double getDiametro930Calcoli()
  {
    double diametro930=0;
    try
    {
      double letturaDensita930=Double.parseDouble(getLetturaDensita930());
      double fattoreTempGranulare2=-1;
      if (getFattoreTempGranulare2Calcoli()!=null)
        fattoreTempGranulare2=getFattoreTempGranulare2Calcoli().doubleValue();
      if (letturaDensita930>0)
      {
        diametro930=fattoreTempGranulare2*(Math.sqrt((24.5-(0.13*letturaDensita930))/9.5));
      }
    }
    catch(Exception num)
    {
      diametro930=0;
    }
    if (diametro930==0) return null;
    else return new Double(diametro930);
  }

  public Double getDiametro10Calcoli()
  {
    double diametro10=0;
    try
    {
      double letturaDensita10=Double.parseDouble(getLetturaDensita10());
      double fattoreTempGranulare2=-1;
      if (getFattoreTempGranulare2Calcoli()!=null)
        fattoreTempGranulare2=getFattoreTempGranulare2Calcoli().doubleValue();
      if (letturaDensita10>0)
      {
        diametro10=fattoreTempGranulare2*(Math.sqrt((24.5-(0.13*letturaDensita10))/10));
      }
    }
    catch(Exception num)
    {
      diametro10=0;
    }
    if (diametro10==0) return null;
    else return new Double(diametro10);
  }

  public Double getDiametro1045Calcoli()
  {
    double diametro1045=0;
    try
    {
      double letturaDensita1045=Double.parseDouble(getLetturaDensita1045());
      double fattoreTempGranulare2=-1;
      if (getFattoreTempGranulare2Calcoli()!=null)
        fattoreTempGranulare2=getFattoreTempGranulare2Calcoli().doubleValue();
      if (letturaDensita1045>0)
      {
        diametro1045=fattoreTempGranulare2*(Math.sqrt((24.5-(0.13*letturaDensita1045))/10.75));
      }
    }
    catch(Exception num)
    {
      diametro1045=0;
    }
    if (diametro1045==0) return null;
    else return new Double(diametro1045);
  }

  public Double getDensitaLorda2045Calcoli()
  {
    double densitaLorda2045=0;
    try
    {
      double diametro1045=-1,diametro10=-1,diametro930=-1;
      if (getDiametro1045Calcoli()!=null)
        diametro1045=getDiametro1045Calcoli().doubleValue();
      if (getDiametro10Calcoli()!=null)
        diametro10=getDiametro10Calcoli().doubleValue();
      if (getDiametro930Calcoli()!=null)
        diametro930=getDiametro930Calcoli().doubleValue();
      double letturaDensita930=Double.parseDouble(getLetturaDensita930());
      double letturaDensita10=Double.parseDouble(getLetturaDensita10());
      if (diametro10>0 && diametro930>diametro10 && (diametro1045==0 || diametro10<20))
      {
        densitaLorda2045=letturaDensita930-(((diametro930-20)/(diametro930-diametro10))*(letturaDensita930-letturaDensita10));
      }
    }
    catch(Exception num)
    {
      densitaLorda2045=0;
    }
    if (densitaLorda2045==0) return null;
    else return new Double(densitaLorda2045);
  }

  public Double getDensitaLorda2046Calcoli()
  {
    double densitaLorda2046=0;
    try
    {
      double diametro1045=-1,diametro10=-1,diametro930=-1;
      if (getDiametro1045Calcoli()!=null)
        diametro1045=getDiametro1045Calcoli().doubleValue();
      if (getDiametro10Calcoli()!=null)
        diametro10=getDiametro10Calcoli().doubleValue();
      if (getDiametro930Calcoli()!=null)
        diametro930=getDiametro930Calcoli().doubleValue();
      double letturaDensita930=Double.parseDouble(getLetturaDensita930());
      double letturaDensita1045=Double.parseDouble(getLetturaDensita1045());
      if (diametro1045>0 && diametro930>diametro1045 && diametro10==0)
      {
        densitaLorda2046=letturaDensita930-(((diametro930-20)/(diametro930-diametro1045))*(letturaDensita930-letturaDensita1045));
       }
    }
    catch(Exception num)
    {
      densitaLorda2046=0;
    }
    if (densitaLorda2046==0) return null;
    else return new Double(densitaLorda2046);
  }

  public Double getDensitaLorda2056Calcoli()
  {
    double densitaLorda2056=0;
    try
    {
      double diametro1045=-1,diametro10=-1,diametro930=-1;
      if (getDiametro1045Calcoli()!=null)
        diametro1045=getDiametro1045Calcoli().doubleValue();
      if (getDiametro10Calcoli()!=null)
        diametro10=getDiametro10Calcoli().doubleValue();
      if (getDiametro930Calcoli()!=null)
        diametro930=getDiametro930Calcoli().doubleValue();
      double letturaDensita10=Double.parseDouble(getLetturaDensita10());
      double letturaDensita1045=Double.parseDouble(getLetturaDensita1045());
      if (diametro1045>0 && diametro10>diametro1045 && (diametro930==0 || diametro10>=20))
      {
        densitaLorda2056=letturaDensita10-(((diametro10-20)/(diametro10-diametro1045))*(letturaDensita10-letturaDensita1045));
       }
    }
    catch(Exception num)
    {
      densitaLorda2056=0;
    }
    if (densitaLorda2056==0) return null;
    else return new Double(densitaLorda2056);
  }

  /**
   * Al momento non viene calcolata
   * @return
   */
  public Double getDensitaLorda20Calcoli()
  {
    return null;
  }

  public Double getLimoGrossoCalcoli()
  {
    double limoGrosso=0;
    try
    {
      double densitaLorda5012=-1,densitaLorda5013=-1,densitaLorda5023=-1;
      double densitaLorda2045=-1,densitaLorda2046=-1,densitaLorda2056=-1;
      double densitaSoluzFondo1=Double.parseDouble(getDensitaSoluzFondo1());
      double densitaSoluzFondo2=Double.parseDouble(getDensitaSoluzFondo2());
      if (getDensitaLorda5012Calcoli()!=null)
        densitaLorda5012=getDensitaLorda5012Calcoli().doubleValue();
      if (getDensitaLorda5013Calcoli()!=null)
        densitaLorda5013=getDensitaLorda5013Calcoli().doubleValue();
      if (getDensitaLorda5023Calcoli()!=null)
        densitaLorda5023=getDensitaLorda5023Calcoli().doubleValue();
      if (getDensitaLorda2045Calcoli()!=null)
        densitaLorda2045=getDensitaLorda2045Calcoli().doubleValue();
      if (getDensitaLorda2046Calcoli()!=null)
        densitaLorda2046=getDensitaLorda2046Calcoli().doubleValue();
      if (getDensitaLorda2056Calcoli()!=null)
        densitaLorda2056=getDensitaLorda2056Calcoli().doubleValue();
      if ((densitaLorda5012>0 || densitaLorda5013>0 || densitaLorda5023>0)
                                     &&
          (densitaLorda2045>0 || densitaLorda2046>0 || densitaLorda2056>0)
                                     &&
               (densitaSoluzFondo1>0 && densitaSoluzFondo2>0)
          )
      {
        limoGrosso=((densitaLorda5012+densitaLorda5013+densitaLorda5023 - densitaSoluzFondo1) - (densitaLorda2045+densitaLorda2046+densitaLorda2056-densitaSoluzFondo2))*2;
      }
    }
    catch(Exception num)
    {
      limoGrosso=0;
    }
    if (limoGrosso==0) return null;
    else return new Double(limoGrosso);
  }

  public double getDensitaMediaBianco3Calcoli()
  {
    double densitaMediaBianco3=0;
    try
    {
      double densitaBianco17=Double.parseDouble(getDensitaBianco17());
      double densitaBianco1830=Double.parseDouble(getDensitaBianco1830());
      double densitaBianco20=Double.parseDouble(getDensitaBianco20());
      densitaMediaBianco3=(densitaBianco17+densitaBianco1830+densitaBianco20)/
      (Math.round(densitaBianco17/(densitaBianco17+1))+
       Math.round(densitaBianco1830/(densitaBianco1830+1))+
       Math.round(densitaBianco20/(densitaBianco20+1)));
    }
    catch(Exception num)
    {
      densitaMediaBianco3=0;
    }
    return densitaMediaBianco3;
  }

  public double getTemperaturaMedia3Calcoli()
  {
    double temperaturaMedia3=0;
    try
    {
      double temperatura17=Double.parseDouble(getTemperatura17());
      double temperatura1830=Double.parseDouble(getTemperatura1830());
      double temperatura20=Double.parseDouble(getTemperatura20());
      temperaturaMedia3=(temperatura17+temperatura1830+temperatura20)/
      (Math.round(temperatura17/(temperatura17+1))+
       Math.round(temperatura1830/(temperatura1830+1))+
       Math.round(temperatura20/(temperatura20+1)));
    }
    catch(Exception num)
    {
      temperaturaMedia3=0;
    }
    return temperaturaMedia3;
  }

  public double getFattoreTempGranulare3Calcoli()
  {
    double fattoreTempGranulare3=0;
    try
    {
      double temperaturaMedia3= getTemperaturaMedia3Calcoli();
      fattoreTempGranulare3=((55.104*temperaturaMedia3)+18550)/((1+(0.02078*temperaturaMedia3))*1000);
    }
    catch(Exception num)
    {
      fattoreTempGranulare3=0;
    }
    return fattoreTempGranulare3;
  }

  public Double getDiametro17Calcoli()
  {
    double diametro17=0;
    try
    {
      double letturaDensita17=Double.parseDouble(getLetturaDensita17());
      double fattoreTempGranulare3=getFattoreTempGranulare3Calcoli();
      if (letturaDensita17>0)
      {
        diametro17=fattoreTempGranulare3*(Math.sqrt((24.5-(0.13*letturaDensita17))/1020));
      }
    }
    catch(Exception num)
    {
      diametro17=0;
    }
    if (diametro17==0) return null;
    else return new Double(diametro17);
  }

  public Double getDiametro1830Calcoli()
  {
    double diametro1830=0;
    try
    {
      double letturaDensita1830=Double.parseDouble(getLetturaDensita1830());
      double fattoreTempGranulare3=getFattoreTempGranulare3Calcoli();
      if (letturaDensita1830>0)
      {
        diametro1830=fattoreTempGranulare3*(Math.sqrt((24.5-(0.13*letturaDensita1830))/1110));
      }
    }
    catch(Exception num)
    {
      diametro1830=0;
    }
    if (diametro1830==0) return null;
    else return new Double(diametro1830);
  }

  public Double getDiametro20Calcoli()
  {
    double diametro20=0;
    try
    {
      double letturaDensita20=Double.parseDouble(getLetturaDensita20());
      double fattoreTempGranulare3=getFattoreTempGranulare3Calcoli();
      if (letturaDensita20>0)
      {
        diametro20=fattoreTempGranulare3*(Math.sqrt((24.5-(0.13*letturaDensita20))/1200));
      }
    }
    catch(Exception num)
    {
      diametro20=0;
    }
    if (diametro20==0) return null;
    else return new Double(diametro20);
  }

  public Double getDensitaLorda278Calcoli()
  {
    double densitaLorda278=0;
    try
    {
      double diametro1830=-1,diametro17=-1,diametro20=-1;
      if (getDiametro1830Calcoli()!=null)
        diametro1830=getDiametro1830Calcoli().doubleValue();
      if (getDiametro17Calcoli()!=null)
        diametro17=getDiametro17Calcoli().doubleValue();
      if (getDiametro20Calcoli()!=null)
        diametro20=getDiametro20Calcoli().doubleValue();
      double letturaDensita1830=Double.parseDouble(getLetturaDensita1830());
      double letturaDensita17=Double.parseDouble(getLetturaDensita17());
      if (diametro1830>0 && diametro17>diametro1830 && (diametro20==0 || diametro1830<2))
      {
        densitaLorda278=letturaDensita17-(((diametro17-2)/(diametro17-diametro1830))*(letturaDensita17-letturaDensita1830));
      }
    }
    catch(Exception num)
    {
      densitaLorda278=0;
    }
    if (densitaLorda278==0) return null;
    else return new Double(densitaLorda278);
  }

  public Double getDensitaLorda279Calcoli()
  {
    double densitaLorda279=0;
    try
    {
      double diametro1830=-1,diametro17=-1,diametro20=-1;
      if (getDiametro1830Calcoli()!=null)
        diametro1830=getDiametro1830Calcoli().doubleValue();
      if (getDiametro17Calcoli()!=null)
        diametro17=getDiametro17Calcoli().doubleValue();
      if (getDiametro20Calcoli()!=null)
        diametro20=getDiametro20Calcoli().doubleValue();
      double letturaDensita20=Double.parseDouble(getLetturaDensita20());
      double letturaDensita17=Double.parseDouble(getLetturaDensita17());
      if (diametro20>0 && diametro17>diametro20 && diametro1830==0)
      {
        densitaLorda279=letturaDensita17-(((diametro17-2)/(diametro17-diametro20))*(letturaDensita17-letturaDensita20));
       }
    }
    catch(Exception num)
    {
      densitaLorda279=0;
    }
    if (densitaLorda279==0) return null;
    else return new Double(densitaLorda279);
  }

  public Double getDensitaLorda289Calcoli()
  {
    double densitaLorda289=0;
    try
    {
      double diametro1830=-1,diametro17=-1,diametro20=-1;
      if (getDiametro1830Calcoli()!=null)
        diametro1830=getDiametro1830Calcoli().doubleValue();
      if (getDiametro17Calcoli()!=null)
        diametro17=getDiametro17Calcoli().doubleValue();
      if (getDiametro20Calcoli()!=null)
        diametro20=getDiametro20Calcoli().doubleValue();
      double letturaDensita1830=Double.parseDouble(getLetturaDensita1830());
      double letturaDensita20=Double.parseDouble(getLetturaDensita20());
      if (diametro20>0 && diametro1830>diametro20 && (diametro17==0 || diametro1830>=2))
      {
        densitaLorda289=letturaDensita1830-(((diametro1830-2)/(diametro1830-diametro20))*(letturaDensita1830-letturaDensita20));
       }
    }
    catch(Exception num)
    {
      densitaLorda289=0;
    }
    if (densitaLorda289==0) return null;
    else return new Double(densitaLorda289);
  }

  /**
   * Al momento non viene calcolata
   * @return
   */
  public Double getDensitaLorda2Calcoli()
  {
    return null;
  }

  public Double getLimoFineCalcoli()
  {
    double limoFine=0;
    try
    {
      double densitaLorda2045=-1,densitaLorda2046=-1,densitaLorda2056=-1;
      double densitaLorda278=-1,densitaLorda279=-1,densitaLorda289=-1;
      if (getDensitaLorda2045Calcoli()!=null)
        densitaLorda2045=getDensitaLorda2045Calcoli().doubleValue();
      if (getDensitaLorda2046Calcoli()!=null)
        densitaLorda2046=getDensitaLorda2046Calcoli().doubleValue();
      if (getDensitaLorda2056Calcoli()!=null)
        densitaLorda2056=getDensitaLorda2056Calcoli().doubleValue();
      if (getDensitaLorda278Calcoli()!=null)
        densitaLorda278=getDensitaLorda278Calcoli().doubleValue();
      if (getDensitaLorda279Calcoli()!=null)
        densitaLorda279=getDensitaLorda279Calcoli().doubleValue();
      if (getDensitaLorda289Calcoli()!=null)
        densitaLorda289=getDensitaLorda289Calcoli().doubleValue();
      double densitaSoluzFondo2=Double.parseDouble(getDensitaSoluzFondo2());
      double densitaMediaBianco3=getDensitaMediaBianco3Calcoli();
      if ((densitaLorda2045>0 || densitaLorda2046>0 || densitaLorda2056>0)
                                   &&
          (densitaLorda278>0 || densitaLorda279>0 || densitaLorda289>0)
            && densitaSoluzFondo2>0 && densitaMediaBianco3>0
         )
      {
        limoFine=((densitaLorda2045+densitaLorda2046+densitaLorda2056-densitaSoluzFondo2)-(densitaLorda278+densitaLorda279+densitaLorda289-densitaMediaBianco3))*2;
      }
    }
    catch(Exception num)
    {
      limoFine=0;
    }
    if (limoFine==0) return null;
    else return new Double(limoFine);
  }

  public Double getArgilla1Calcoli()
  {
    double argilla1=0;
    try
    {
      double limoGrosso=0,limoFine=0,sabbia=0;
      if (getLimoGrossoCalcoli()!=null)
        limoGrosso=getLimoGrossoCalcoli().doubleValue();
      if (getLimoFineCalcoli()!=null)
        limoFine=getLimoFineCalcoli().doubleValue();
      if (getSabbiaCalcoli()!=null)
        sabbia=getSabbiaCalcoli().doubleValue();
      if (limoGrosso>0 || limoFine>0)
      {
        argilla1=100-sabbia-limoGrosso-limoFine;
      }
    }
    catch(Exception num)
    {
      argilla1=0;
    }
    if (argilla1==0) return null;
    else return new Double(argilla1);
  }

  public Double getLimoCalcoli()
  {
    double limo=0;
    try
    {
      double sabbia=0,limoGrosso=0,argilla1=0;
      double densitaLorda278=0,densitaLorda279=0,densitaLorda289=0;
      double densitaLorda5012=0,densitaLorda5013=0,densitaLorda5023=0;
      if (getSabbiaCalcoli()!=null)
        sabbia=getSabbiaCalcoli().doubleValue();
      if (getLimoGrossoCalcoli()!=null)
        limoGrosso=getLimoGrossoCalcoli().doubleValue();
      if (getArgilla1Calcoli()!=null)
        argilla1=getArgilla1Calcoli().doubleValue();
      if (getDensitaLorda278Calcoli()!=null)
        densitaLorda278=getDensitaLorda278Calcoli().doubleValue();
      if (getDensitaLorda279Calcoli()!=null)
        densitaLorda279=getDensitaLorda279Calcoli().doubleValue();
      if (getDensitaLorda289Calcoli()!=null)
        densitaLorda289=getDensitaLorda289Calcoli().doubleValue();
      if (getDensitaLorda5012Calcoli()!=null)
        densitaLorda5012=getDensitaLorda5012Calcoli().doubleValue();
      if (getDensitaLorda5013Calcoli()!=null)
        densitaLorda5013=getDensitaLorda5013Calcoli().doubleValue();
      if (getDensitaLorda5023Calcoli()!=null)
        densitaLorda5023=getDensitaLorda5023Calcoli().doubleValue();
      double densitaMediaBianco3=getDensitaMediaBianco3Calcoli();
      double densitaSoluzFondo1=Double.parseDouble(getDensitaSoluzFondo1());
      if (sabbia>0 && (densitaLorda278>0 || densitaLorda279>0 || densitaLorda289>0)
             && limo==0 && argilla1==0)
      {
        limo=((densitaLorda5012+densitaLorda5013+densitaLorda5023-densitaSoluzFondo1)-(densitaLorda278+densitaLorda279+densitaLorda289-densitaMediaBianco3))*2;
      }
    }
    catch(Exception num)
    {
      limo=0;
    }
    if (limo==0) return null;
    else return new Double(limo);
  }

  public Double getArgilla2Calcoli()
  {
    double argilla2=0;
    try
    {
      double limoGrosso=-1,limoFine=-1,sabbia=0,argilla1=-1,limo=0;
      if (getLimoGrossoCalcoli()!=null)
        limoGrosso=getLimoGrossoCalcoli().doubleValue();
      if (getLimoFineCalcoli()!=null)
        limoFine=getLimoFineCalcoli().doubleValue();
      if (getSabbiaCalcoli()!=null)
        sabbia=getSabbiaCalcoli().doubleValue();
      if (getArgilla1Calcoli()!=null)
        argilla1=getArgilla1Calcoli().doubleValue();
      if (getLimoCalcoli()!=null)
        limo=getLimoCalcoli().doubleValue();
      if (limoGrosso==0 && limoFine==0 && argilla1==0 && sabbia>0 && limo>0)
      {
        argilla2=100-sabbia-limo;
      }
    }
    catch(Exception num)
    {
      argilla2=0;
    }
    if (argilla2==0) return null;
    else return new Double(argilla2);
  }


  /**
   * Questa funzione controlla che i dati che verranno inseriti o modificati nel
   * database siano consistenti
   * */
  public String ControllaDati()
  {

    /**
    *   Da rivedere !!!!!!!!!!!!
    * */

    StringBuffer errore=new StringBuffer("");
    double d115=0,d130=0,d145=0,d930=0,d10=0,d1045=0,denSolfondo2=0;
    double d17=0,d1830=0,d20=0,t17=-1,t1830=-1,t20=-1;
    if (getLetturaDensita115()!=null &&
          !Utili.isDouble(getLetturaDensita115(),0,999999.999,3))
      errore.append(";1");
    if (getLetturaDensita130()!=null &&
          !Utili.isDouble(getLetturaDensita130(),0,999999.999,3))
      errore.append(";2");
    if (getLetturaDensita145()!=null &&
          !Utili.isDouble(getLetturaDensita145(),0,999999.999,3))
      errore.append(";3");
    try
    {
      d115=Double.parseDouble(getLetturaDensita115());
    }
    catch(Exception e) {}
    try
    {
      d130=Double.parseDouble(getLetturaDensita130());
    }
    catch(Exception e) {}
    try
    {
      d145=Double.parseDouble(getLetturaDensita145());
    }
    catch(Exception e) {}
    if (!((d115>0 && d115>=d130) || d130>0)) errore.append(";2");
    if (!( (d130>0 && d115>=d130)
                         ||
          (d145>0 && d115>d145)
                         ||
          (d145>0 && d130>=d145)
       )
    )
    errore.append(";3");


    if (!Utili.isDouble(getDensitaSoluzFondo1(),0.001,999999.999,3))
      errore.append(";4");
    if (!Utili.isDouble(getTemperatura1(),0.0,999999.999,3))
      errore.append(";5");
    else
    {
      double t1=Double.parseDouble(getTemperatura1());
      if (t1<10 || t1>30) errore.append(";5");
    }

    if (getLetturaDensita930()!=null &&
          !Utili.isDouble(getLetturaDensita930(),0,999999.999,3))
      errore.append(";6");
    if (getLetturaDensita10()!=null &&
          !Utili.isDouble(getLetturaDensita10(),0,999999.999,3))
      errore.append(";7");
    if (getLetturaDensita1045()!=null &&
          !Utili.isDouble(getLetturaDensita1045(),0,999999.999,3))
      errore.append(";8");
    try
    {
      d930=Double.parseDouble(getLetturaDensita930());
    }
    catch(Exception e) {}
    try
    {
      d10=Double.parseDouble(getLetturaDensita10());
    }
    catch(Exception e) {}
    try
    {
      d1045=Double.parseDouble(getLetturaDensita1045());
    }
    catch(Exception e) {}
    if (!((d145==0 || d145>=d930) && (d130==0 || d130>d930))       )
      errore.append(";6");
    if (!((d930>0 && d930>=d10) || d10>=0))
      errore.append(";7");
    if (!( (d10>0 && d930>=d10)
                         ||
          (d1045>=0 && d930>=d1045)
                         ||
          (d1045>=0 && d10>=d1045)
       )
    )
    errore.append(";8");

    if (getDensitaSoluzFondo2()!=null &&
          !Utili.isDouble(getDensitaSoluzFondo2(),0,999999.999,3))
      errore.append(";9");
    try
    {
      denSolfondo2=Double.parseDouble(getDensitaSoluzFondo2());
    }
    catch(Exception e) {}

    if (!(((d930>0 || (d10>0 && d1045>0)) && denSolfondo2 >0) || (d930==0 && d10==0 && denSolfondo2==0)))
      errore.append(";9");

    if (!Utili.isDouble(getTemperatura2(),0.0,999999.999,3))
      errore.append(";10");
    else
    {
      double t2=Double.parseDouble(getTemperatura2());
      if (!((denSolfondo2 == 0 && t2==0) || (denSolfondo2 > 0 && t2>=10 && t2<=30))) errore.append(";10");
    }

    if (getLetturaDensita17()!=null &&
          !Utili.isDouble(getLetturaDensita17(),0,999999.999,3))
      errore.append(";11");
    if (getLetturaDensita1830()!=null &&
          !Utili.isDouble(getLetturaDensita1830(),0,999999.999,3))
      errore.append(";12");
    if (getLetturaDensita20()!=null &&
          !Utili.isDouble(getLetturaDensita20(),0,999999.999,3))
      errore.append(";13");
    try
    {
      d17=Double.parseDouble(getLetturaDensita17());
    }
    catch(Exception e) {}
    try
    {
      d1830=Double.parseDouble(getLetturaDensita1830());
    }
    catch(Exception e) {}
    try
    {
      d20=Double.parseDouble(getLetturaDensita20());
    }
    catch(Exception e) {}
    if (!((d1045==0 || d1045>=d17) && (d10==0 || d10>=d17) && (d145 == 0 || d145>=d17)))
      errore.append(";11");
    if (!( (d17>0 && d17>=d1830) || d1830>0)  )
      errore.append(";12");
    if (!( (d1830>0 &&d17>=d1830) || (d20>0 && d17>d20) || (d20>0 &&d1830>=d20)
         )
       )
      errore.append(";13");


    if (getDensitaBianco17()==null && getDensitaBianco1830()==null && getDensitaBianco20()==null)
    {
      errore.append(";14;15;16");
    }
    else
    {
      if (getDensitaBianco17()!=null &&
            !Utili.isDouble(getDensitaBianco17(),0,999999.999,3))
        errore.append(";14");
      if (getDensitaBianco1830()!=null &&
            !Utili.isDouble(getDensitaBianco1830(),0,999999.999,3))
        errore.append(";15");
      if (getDensitaBianco20()!=null &&
            !Utili.isDouble(getDensitaBianco20(),0,999999.999,3))
        errore.append(";16");
    }
    try
    {
      if ( (getDensitaBianco17()==null || Double.parseDouble(getDensitaBianco17())==0)
                               &&
          (getDensitaBianco1830()==null || Double.parseDouble(getDensitaBianco1830())==0)
                               &&
          (getDensitaBianco20()==null || Double.parseDouble(getDensitaBianco20())==0 )
          )
      {
       errore.append(";14;15;16");
      }
    }
    catch(Exception e) {}

    if (getTemperatura17()!=null &&
          !Utili.isDouble(getTemperatura17(),0,999999.999,3))
      errore.append(";17");
    if (getTemperatura1830()!=null &&
          !Utili.isDouble(getTemperatura1830(),0,999999.999,3))
      errore.append(";18");
    if (getTemperatura20()!=null &&
          !Utili.isDouble(getTemperatura20(),0,999999.999,3))
      errore.append(";19");
    try
    {
      t17=Double.parseDouble(getTemperatura17());
    }
    catch(Exception e) {}
    try
    {
      t1830=Double.parseDouble(getTemperatura1830());
    }
    catch(Exception e) {}
    try
    {
      t20=Double.parseDouble(getTemperatura20());
    }
    catch(Exception e) {}
    if (!((t17>=10 && t17<=30) || t17==0))
      errore.append(";17");
    if (!((t1830>=10 && t1830<=30) || t1830==0))
      errore.append(";18");
    if (!(t17>0 || t1830>0 || (t20>=10 && t20<=30)))
      errore.append(";19");

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

  public String getLetturaDensita115()
  {
    return this.letturaDensita115;
  }
  public void setLetturaDensita115( String newLetturaDensita115 )
  {
    if (newLetturaDensita115!=null) letturaDensita115=newLetturaDensita115.replace(',','.');
    else this.letturaDensita115 = newLetturaDensita115;
  }

  public String getLetturaDensita130()
  {
    return this.letturaDensita130;
  }
  public void setLetturaDensita130( String newLetturaDensita130 )
  {
    if (newLetturaDensita130!=null) letturaDensita130=newLetturaDensita130.replace(',','.');
    else  this.letturaDensita130 = newLetturaDensita130;
  }

  public String getLetturaDensita145()
  {
    return this.letturaDensita145;
  }
  public void setLetturaDensita145( String newLetturaDensita145 )
  {
    if (newLetturaDensita145!=null) letturaDensita145=newLetturaDensita145.replace(',','.');
    else this.letturaDensita145 = newLetturaDensita145;
  }

  public String getDensitaSoluzFondo1()
  {
    return this.densitaSoluzFondo1;
  }
  public void setDensitaSoluzFondo1( String newDensitaSoluzFondo1 )
  {
    if (newDensitaSoluzFondo1!=null) densitaSoluzFondo1=newDensitaSoluzFondo1.replace(',','.');
    else this.densitaSoluzFondo1 = newDensitaSoluzFondo1;
  }

  public String getTemperatura1()
  {
    return this.temperatura1;
  }
  public void setTemperatura1( String newTemperatura1 )
  {
    if (newTemperatura1!=null) temperatura1=newTemperatura1.replace(',','.');
    else this.temperatura1 = newTemperatura1;
  }

  public String getFattoreTempGranulare1()
  {
    return this.fattoreTempGranulare1;
  }
  public void setFattoreTempGranulare1( String newFattoreTempGranulare1 )
  {
    if (newFattoreTempGranulare1!=null) fattoreTempGranulare1=newFattoreTempGranulare1.replace(',','.');
    else this.fattoreTempGranulare1 = newFattoreTempGranulare1;
  }

  public String getDiametro115()
  {
    return this.diametro115;
  }
  public void setDiametro115( String newDiametro115 )
  {
    if (newDiametro115!=null) diametro115=newDiametro115.replace(',','.');
    else this.diametro115 = newDiametro115;
  }

  public String getDiametro130()
  {
    return this.diametro130;
  }
  public void setDiametro130( String newDiametro130 )
  {
    if (newDiametro130!=null) diametro130=newDiametro130.replace(',','.');
    else this.diametro130 = newDiametro130;
  }

  public String getDiametro145()
  {
    return this.diametro145;
  }
  public void setDiametro145( String newDiametro145 )
  {
    if (newDiametro145!=null) diametro145=newDiametro145.replace(',','.');
    else this.diametro145 = newDiametro145;
  }

  public String getDensitaLorda5012()
  {
    return this.densitaLorda5012;
  }
  public void setDensitaLorda5012( String newDensitaLorda5012 )
  {
    if (newDensitaLorda5012!=null) densitaLorda5012=newDensitaLorda5012.replace(',','.');
    else this.densitaLorda5012 = newDensitaLorda5012;
  }

  public String getDensitaLorda5013()
  {
    return this.densitaLorda5013;
  }
  public void setDensitaLorda5013( String newDensitaLorda5013 )
  {
    if (newDensitaLorda5013!=null) densitaLorda5013=newDensitaLorda5013.replace(',','.');
    else this.densitaLorda5013 = newDensitaLorda5013;
  }

  public String getDensitaLorda5023()
  {
    return this.densitaLorda5023;
  }
  public void setDensitaLorda5023( String newDensitaLorda5023 )
  {
    if (newDensitaLorda5023!=null) densitaLorda5023=newDensitaLorda5023.replace(',','.');
    else this.densitaLorda5023 = newDensitaLorda5023;
  }

  public String getDensitaLorda50()
  {
    return this.densitaLorda50;
  }
  public void setDensitaLorda50( String newDensitaLorda50 )
  {
    if (newDensitaLorda50!=null) densitaLorda50=newDensitaLorda50.replace(',','.');
    else this.densitaLorda50 = newDensitaLorda50;
  }

  public String getSabbia()
  {
    return sabbia;
  }

  public String getSabbiaTotalePDF()
  {
    if (sabbia==null) return "";
    else
    {
      sabbia=sabbia.replace(',','.');
      sabbia=Utili.nf1.format(Double.parseDouble(sabbia));
      sabbia=sabbia.replace('.',',');
      return sabbia;
    }
  }
  public void setSabbia( String newSabbia )
  {
    if (newSabbia!=null) sabbia=newSabbia.replace(',','.');
    else this.sabbia = newSabbia;
  }

  public String getLetturaDensita930()
  {
    return this.letturaDensita930;
  }
  public void setLetturaDensita930( String newLetturaDensita930 )
  {
    if (newLetturaDensita930!=null) letturaDensita930=newLetturaDensita930.replace(',','.');
    else this.letturaDensita930 = newLetturaDensita930;
  }

  public String getLetturaDensita10()
  {
    return this.letturaDensita10;
  }
  public void setLetturaDensita10( String newLetturaDensita10 )
  {
    if (newLetturaDensita10!=null) letturaDensita10=newLetturaDensita10.replace(',','.');
    else this.letturaDensita10 = newLetturaDensita10;
  }

  public String getLetturaDensita1045()
  {
    return this.letturaDensita1045;
  }
  public void setLetturaDensita1045( String newLetturaDensita1045 )
  {
    if (newLetturaDensita1045!=null) letturaDensita1045=newLetturaDensita1045.replace(',','.');
    else this.letturaDensita1045 = newLetturaDensita1045;
  }

  public String getDensitaSoluzFondo2()
  {
    return this.densitaSoluzFondo2;
  }
  public void setDensitaSoluzFondo2( String newDensitaSoluzFondo2 )
  {
    if (newDensitaSoluzFondo2!=null) densitaSoluzFondo2=newDensitaSoluzFondo2.replace(',','.');
    else this.densitaSoluzFondo2 = newDensitaSoluzFondo2;
  }

  public String getTemperatura2()
  {
    return this.temperatura2;
  }
  public void setTemperatura2( String newTemperatura2 )
  {
    if (newTemperatura2!=null) temperatura2=newTemperatura2.replace(',','.');
    else this.temperatura2 = newTemperatura2;
  }

  public String getFattoreTempGranulare2()
  {
    return this.fattoreTempGranulare2;
  }
  public void setFattoreTempGranulare2( String newFattoreTempGranulare2 )
  {
    if (newFattoreTempGranulare2!=null) fattoreTempGranulare2=newFattoreTempGranulare2.replace(',','.');
    else this.fattoreTempGranulare2 = newFattoreTempGranulare2;
  }

  public String getDiametro930()
  {
    return this.diametro930;
  }
  public void setDiametro930( String newDiametro930 )
  {
    if (newDiametro930!=null) diametro930=newDiametro930.replace(',','.');
    else this.diametro930 = newDiametro930;
  }

  public String getDiametro10()
  {
    return this.diametro10;
  }
  public void setDiametro10( String newDiametro10 )
  {
    if (newDiametro10!=null) diametro10=newDiametro10.replace(',','.');
    else this.diametro10 = newDiametro10;
  }

  public String getDiametro1045()
  {
    return this.diametro1045;
  }
  public void setDiametro1045( String newDiametro1045 )
  {
    if (newDiametro1045!=null) diametro1045=newDiametro1045.replace(',','.');
    else this.diametro1045 = newDiametro1045;
  }

  public String getDensitaLorda2045()
  {
    return this.densitaLorda2045;
  }
  public void setDensitaLorda2045( String newDensitaLorda2045 )
  {
    if (newDensitaLorda2045!=null) densitaLorda2045=newDensitaLorda2045.replace(',','.');
    else this.densitaLorda2045 = newDensitaLorda2045;
  }

  public String getDensitaLorda2046()
  {
    return this.densitaLorda2046;
  }
  public void setDensitaLorda2046( String newDensitaLorda2046 )
  {
    if (newDensitaLorda2046!=null) densitaLorda2046=newDensitaLorda2046.replace(',','.');
    else this.densitaLorda2046 = newDensitaLorda2046;
  }

  public String getDensitaLorda2056()
  {
    return this.densitaLorda2056;
  }
  public void setDensitaLorda2056( String newDensitaLorda2056 )
  {
    if (newDensitaLorda2056!=null) densitaLorda2056=newDensitaLorda2056.replace(',','.');
    else this.densitaLorda2056 = newDensitaLorda2056;
  }

  public String getDensitaLorda20()
  {
    return this.densitaLorda20;
  }
  public void setDensitaLorda20( String newDensitaLorda20 )
  {
    if (newDensitaLorda20!=null) densitaLorda20=newDensitaLorda20.replace(',','.');
    else this.densitaLorda20 = newDensitaLorda20;
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
  public String getLimoGrosso()
  {
    return this.limoGrosso;
  }
  public void setLimoGrosso( String newLimoGrosso )
  {
    if (newLimoGrosso!=null) limoGrosso=newLimoGrosso.replace(',','.');
    else this.limoGrosso = newLimoGrosso;
  }

  public String getLetturaDensita17()
  {
    return this.letturaDensita17;
  }
  public void setLetturaDensita17( String newLetturaDensita17 )
  {
    if (newLetturaDensita17!=null) letturaDensita17=newLetturaDensita17.replace(',','.');
    else this.letturaDensita17 = newLetturaDensita17;
  }

  public String getLetturaDensita1830()
  {
    return this.letturaDensita1830;
  }
  public void setLetturaDensita1830( String newLetturaDensita1830 )
  {
    if (newLetturaDensita1830!=null) letturaDensita1830=newLetturaDensita1830.replace(',','.');
    else this.letturaDensita1830 = newLetturaDensita1830;
  }

  public String getLetturaDensita20()
  {
    return this.letturaDensita20;
  }
  public void setLetturaDensita20( String newLetturaDensita20 )
  {
    if (newLetturaDensita20!=null) letturaDensita20=newLetturaDensita20.replace(',','.');
    else this.letturaDensita20 = newLetturaDensita20;
  }

  public String getDensitaBianco17()
  {
    return this.densitaBianco17;
  }
  public void setDensitaBianco17( String newDensitaBianco17 )
  {
    if (newDensitaBianco17!=null) densitaBianco17=newDensitaBianco17.replace(',','.');
    else this.densitaBianco17 = newDensitaBianco17;
  }

  public String getDensitaBianco1830()
  {
    return this.densitaBianco1830;
  }
  public void setDensitaBianco1830( String newDensitaBianco1830 )
  {
    if (newDensitaBianco1830!=null) densitaBianco1830=newDensitaBianco1830.replace(',','.');
    else this.densitaBianco1830 = newDensitaBianco1830;
  }

  public String getDensitaBianco20()
  {
    return this.densitaBianco20;
  }
  public void setDensitaBianco20( String newDensitaBianco20 )
  {
    if (newDensitaBianco20!=null) densitaBianco20=newDensitaBianco20.replace(',','.');
    else this.densitaBianco20 = newDensitaBianco20;
  }

  public String getDensitaMediaBianco3()
  {
    return this.densitaMediaBianco3;
  }
  public void setDensitaMediaBianco3( String newDensitaMediaBianco3 )
  {
    if (newDensitaMediaBianco3!=null) densitaMediaBianco3=newDensitaMediaBianco3.replace(',','.');
    else this.densitaMediaBianco3 = newDensitaMediaBianco3;
  }

  public String getTemperatura17()
  {
    return this.temperatura17;
  }
  public void setTemperatura17( String newTemperatura17 )
  {
    if (newTemperatura17!=null) temperatura17=newTemperatura17.replace(',','.');
    else this.temperatura17 = newTemperatura17;
  }

  public String getTemperatura1830()
  {
    return this.temperatura1830;
  }
  public void setTemperatura1830( String newTemperatura1830 )
  {
    if (newTemperatura1830!=null) temperatura1830=newTemperatura1830.replace(',','.');
    else this.temperatura1830 = newTemperatura1830;
  }

  public String getTemperatura20()
  {
    return this.temperatura20;
  }
  public void setTemperatura20( String newTemperatura20 )
  {
    if (newTemperatura20!=null) temperatura20=newTemperatura20.replace(',','.');
    else this.temperatura20 = newTemperatura20;
  }

  public String getTemperaturaMedia3()
  {
    return this.temperaturaMedia3;
  }
  public void setTemperaturaMedia3( String newTemperaturaMedia3 )
  {
    if (newTemperaturaMedia3!=null) temperaturaMedia3=newTemperaturaMedia3.replace(',','.');
    else this.temperaturaMedia3 = newTemperaturaMedia3;
  }

  public String getFattoreTempGranulare3()
  {
    return this.fattoreTempGranulare3;
  }
  public void setFattoreTempGranulare3( String newFattoreTempGranulare3 )
  {
    if (newFattoreTempGranulare3!=null) fattoreTempGranulare3=newFattoreTempGranulare3.replace(',','.');
    else this.fattoreTempGranulare3 = newFattoreTempGranulare3;
  }

  public String getDiametro17()
  {
    return this.diametro17;
  }
  public void setDiametro17( String newDiametro17 )
  {
    if (newDiametro17!=null) diametro17=newDiametro17.replace(',','.');
    else this.diametro17 = newDiametro17;
  }

  public String getDiametro1830()
  {
    return this.diametro1830;
  }
  public void setDiametro1830( String newDiametro1830 )
  {
    if (newDiametro1830!=null) diametro1830=newDiametro1830.replace(',','.');
    else this.diametro1830 = newDiametro1830;
  }

  public String getDiametro20()
  {
    return this.diametro20;
  }
  public void setDiametro20( String newDiametro20 )
  {
    if (newDiametro20!=null) diametro20=newDiametro20.replace(',','.');
    else this.diametro20 = newDiametro20;
  }

  public String getDensitaLorda278()
  {
    return this.densitaLorda278;
  }
  public void setDensitaLorda278( String newDensitaLorda278 )
  {
    if (newDensitaLorda278!=null) densitaLorda278=newDensitaLorda278.replace(',','.');
    else this.densitaLorda278 = newDensitaLorda278;
  }

  public String getDensitaLorda279()
  {
    return this.densitaLorda279;
  }
  public void setDensitaLorda279( String newDensitaLorda279 )
  {
    if (newDensitaLorda279!=null) densitaLorda279=newDensitaLorda279.replace(',','.');
    else this.densitaLorda279 = newDensitaLorda279;
  }

  public String getDensitaLorda289()
  {
    return this.densitaLorda289;
  }
  public void setDensitaLorda289( String newDensitaLorda289 )
  {
    if (newDensitaLorda289!=null) densitaLorda289=newDensitaLorda289.replace(',','.');
    else this.densitaLorda289 = newDensitaLorda289;
  }

  public String getDensitaLorda2()
  {
    return this.densitaLorda2;
  }
  public void setDensitaLorda2( String newDensitaLorda2 )
  {
    if (newDensitaLorda2!=null) densitaLorda2=newDensitaLorda2.replace(',','.');
    else this.densitaLorda2 = newDensitaLorda2;
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

  public String getLimoFine()
  {
    return this.limoFine;
  }
  public void setLimoFine( String newLimoFine )
  {
    if (newLimoFine!=null) limoFine=newLimoFine.replace(',','.');
    else this.limoFine = newLimoFine;
  }

  public String getArgilla1()
  {
    return this.argilla1;
  }
  public void setArgilla1( String newArgilla1 )
  {
    if (newArgilla1!=null) argilla1=newArgilla1.replace(',','.');
    else this.argilla1 = newArgilla1;
  }


  public String getLimo()
  {
    return this.limo;
  }
  public String getLimoTotalePDF()
  {
    if (limo==null) return "";
    else
    {
      limo=limo.replace(',','.');
      limo=Utili.nf1.format(Double.parseDouble(limo));
      limo=limo.replace('.',',');
      return limo;
    }
  }
  public void setLimo( String newLimo )
  {
    if (newLimo!=null) limo=newLimo.replace(',','.');
    else this.limo = newLimo;
  }

  public String getArgilla2()
  {
    return this.argilla2;
  }
  public void setArgilla2( String newArgilla2 )
  {
    if (newArgilla2!=null) argilla2=newArgilla2.replace(',','.');
    else this.argilla2 = newArgilla2;
  }

  public String getArgillaPDF()
  {
    if (argilla1==null && argilla2==null) return "";
    else
    {
      String argilla;
      if (argilla1==null) argilla=argilla2;
      else argilla=argilla1;
      argilla=argilla.replace(',','.');
      argilla=Utili.nf1.format(Double.parseDouble(argilla));
      argilla=argilla.replace('.',',');
      return argilla;
    }
  }

}