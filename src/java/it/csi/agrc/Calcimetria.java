package it.csi.agrc;

import it.csi.cuneo.Utili;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
//import it.csi.jsf.web.pool.*;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class Calcimetria  extends ModelloInsEsitoAnalisi
{
  private long idRichiesta;
  private String letturaCalcimetro;
  private String pressioneAtmosferica;
  private String temperatura;
  private String tensioneDiVapore;
  private String carbonatoCalcioTotale;
  private String calcareAttivo;
  private String letturaFerroOssalato;
  private String diluizioneDeterminaFerro;
  private String ferroOssalato;
  private String indicePotereClorosante;

  public Calcimetria ()
  {
  }
  public Calcimetria ( long idRichiesta, String letturaCalcimetro, String pressioneAtmosferica, String temperatura, String tensioneDiVapore, String carbonatoCalcioTotale, String calcareAttivo, String letturaFerroOssalato, String diluizioneDeterminaFerro, String ferroOssalato, String indicePotereClorosante )
  {
    this.idRichiesta=idRichiesta;
    this.letturaCalcimetro=letturaCalcimetro;
    this.pressioneAtmosferica=pressioneAtmosferica;
    this.temperatura=temperatura;
    this.tensioneDiVapore=tensioneDiVapore;
    this.carbonatoCalcioTotale=carbonatoCalcioTotale;
    this.calcareAttivo=calcareAttivo;
    this.letturaFerroOssalato=letturaFerroOssalato;
    this.diluizioneDeterminaFerro=diluizioneDeterminaFerro;
    this.ferroOssalato=ferroOssalato;
    this.indicePotereClorosante=indicePotereClorosante;
  }

  /**
   * Questo metodo va a leggere il record della tabella CALCIMETRIA
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
      query = new StringBuffer("SELECT LETTURA_CALCIMETRO,PRESSIONE_ATMOSFERICA,");
      query.append("TEMPERATURA,TENSIONE_DI_VAPORE,CARBONATO_CALCIO_TOTALE,");
      query.append("CALCARE_ATTIVO,LETTURA_FERRO_OSSALATO,");
      query.append("DILUIZIONE_DETERMINA_FERRO,FERRO_OSSALATO,");
      query.append("INDICE_POTERE_CLOROSANTE ");
      query.append("FROM CALCIMETRIA ");
      query.append("WHERE ID_RICHIESTA = ");
      query.append(getIdRichiesta());
      //CuneoLogger.debug(this, query.toString());
      ResultSet rs=stmt.executeQuery(query.toString());
      String temp;
      if (rs.next())
      {
        temp=rs.getString("LETTURA_CALCIMETRO");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setLetturaCalcimetro(temp);

        temp=rs.getString("PRESSIONE_ATMOSFERICA");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setPressioneAtmosferica(temp);

        temp=rs.getString("TEMPERATURA");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setTemperatura(temp);

        temp=rs.getString("TENSIONE_DI_VAPORE");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setTensioneDiVapore(temp);

        temp=rs.getString("CARBONATO_CALCIO_TOTALE");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setCarbonatoCalcioTotale(temp);

        temp=rs.getString("CALCARE_ATTIVO");
        if (temp!=null) temp=Utili.nf1.format(Double.parseDouble(temp));
        this.setCalcareAttivo(temp);

        temp=rs.getString("LETTURA_FERRO_OSSALATO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaFerroOssalato(temp);

        temp=rs.getString("DILUIZIONE_DETERMINA_FERRO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiluizioneDeterminaFerro(temp);

        temp=rs.getString("FERRO_OSSALATO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setFerroOssalato(temp);

        temp=rs.getString("INDICE_POTERE_CLOROSANTE");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setIndicePotereClorosante(temp);

        return true;
      }
      else return false;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe Calcimetria");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe Calcimetria"
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
   * CALCIMETRIA. Alcuni dati sono quelli di input inseriti
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
      query = new StringBuffer("INSERT INTO CALCIMETRIA(");
      query.append("ID_RICHIESTA,LETTURA_CALCIMETRO,PRESSIONE_ATMOSFERICA,");
      query.append("TEMPERATURA,TENSIONE_DI_VAPORE,CARBONATO_CALCIO_TOTALE,");
      query.append("CALCARE_ATTIVO,LETTURA_FERRO_OSSALATO,");
      query.append("DILUIZIONE_DETERMINA_FERRO,FERRO_OSSALATO,");
      query.append("INDICE_POTERE_CLOROSANTE) ");
      query.append("VALUES(");
      query.append(getIdRichiesta()).append(",");
      query.append(getLetturaCalcimetro()).append(",");
      query.append(getPressioneAtmosferica()).append(",");
      query.append(getTemperatura()).append(",");
      query.append(getTensioneDiVaporeCalcoli()).append(",");
      query.append(getCarbonatoCalcioTotaleCalcoli()).append(",");
      query.append(getCalcareAttivo()).append(",");
      query.append(getLetturaFerroOssalato()).append(",");
      query.append(getDiluizioneDeterminaFerro()).append(",");
      query.append(getFerroOssalatoCalcoli()).append(",");
      query.append(getIndicePotereClorosanteCalcoli());
      query.append(")");
      //CuneoLogger.debug(this, query.toString());
      stmt.executeUpdate(query.toString());
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("insert della classe Calcimetria");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("insert della classe Calcimetria"
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
   * CALCIMETRIA. Alcuni dati sono quelli di input inseriti
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
      query = new StringBuffer("UPDATE CALCIMETRIA ");
      query.append("SET LETTURA_CALCIMETRO = ").append(getLetturaCalcimetro());
      query.append(",PRESSIONE_ATMOSFERICA = ").append(getPressioneAtmosferica());
      query.append(",TEMPERATURA = ").append(getTemperatura());
      query.append(",TENSIONE_DI_VAPORE = ").append(getTensioneDiVaporeCalcoli());
      query.append(",CARBONATO_CALCIO_TOTALE = ").append(getCarbonatoCalcioTotaleCalcoli());
      query.append(",CALCARE_ATTIVO = ").append(getCalcareAttivo());
      query.append(",LETTURA_FERRO_OSSALATO = ").append(getLetturaFerroOssalato());
      query.append(",DILUIZIONE_DETERMINA_FERRO = ").append(getDiluizioneDeterminaFerro());
      query.append(",FERRO_OSSALATO = ").append(getFerroOssalatoCalcoli());
      query.append(",INDICE_POTERE_CLOROSANTE = ").append(getIndicePotereClorosanteCalcoli());
      query.append(" WHERE ID_RICHIESTA = ");query.append(getIdRichiesta());
      //CuneoLogger.debug(this, query.toString());
      stmt.executeUpdate(query.toString());
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("update della classe Calcimetria");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("update della classe Calcimetria"
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
   * Questo metodo verifica che siano impostati valori per i tre dati del calcare totale
   * Serve per definire il colore della voce "calcimetria" nelle pagine di inserimento analisi
   *
   * CONDIZIONI PER IL COMPLETAMENTO ATTUALI (20/12/2011)
   * - lettura calcimetro valorizzata
   * - temperatura e/o pressione valorizzata
   * - calcare attivo valorizzato
   *
   * @return se le condizioni per il completamento sono rispettate restituisce true, altrimenti false
   * @throws Exception
   */
  public boolean isCompletata() throws Exception
  {
	Analisi analisi = new Analisi(this.getDataSource(), this.getAut());
	analisi.select(new Long(this.idRichiesta).toString());
	
	Vector codiciAnalisi = analisi.getCodiciAnalisi();
    Iterator iter = codiciAnalisi.iterator();

    boolean calcareAttivoRichiesto = false;
    boolean calcareTotaleRichiesto = false;
	
    while (iter.hasNext())
    {
        String item = (String)iter.next();
    	if (Analisi.ANA_CALCAREATTIVO.equals(item))
    		calcareAttivoRichiesto = true;
    	else if (Analisi.ANA_CALCARETOTALE.equals(item) || Analisi.ANA_PACCHETTO_TIPO.equals(item))
    		calcareTotaleRichiesto = true;
    }
	
	boolean completata = true; 

	if ( calcareAttivoRichiesto )
		completata = completata &&
    		this.calcareAttivo != null && !"".equals(this.calcareAttivo);
	if ( calcareTotaleRichiesto )
		completata = completata &&
    		this.letturaCalcimetro != null && !"".equals(this.letturaCalcimetro) &&
    		( ( this.pressioneAtmosferica != null && !"".equals(this.pressioneAtmosferica) ) ||
    		( this.temperatura != null && !"".equals(this.temperatura) ) );
	
    return completata;
  }

  /**
   * Questo metodo va a cancellare un record della tabella
   * CALCIMETRIA.
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
      query = new StringBuffer("DELETE FROM CALCIMETRIA ");
      query.append("WHERE ID_RICHIESTA =").append(getIdRichiesta());
      //CuneoLogger.debug(this, query.toString());
      stmt.executeUpdate(query.toString());
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("delete della classe Calcimetria");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("delete della classe Calcimetria"
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

  public Double getTensioneDiVaporeCalcoli()
  {
    double tensioneDiVapore=0;
    try
    {
      double temperatura=Double.parseDouble(getTemperatura());
      if (temperatura>=10 && temperatura<=30)
      {
        tensioneDiVapore=(((385.366*temperatura)+3648.78)/(1-(0.0176151*temperatura)))/1000;
      }
    }
    catch(Exception num)
    {
      tensioneDiVapore=0;
    }
    if (tensioneDiVapore==0) return null;
    else return new Double(tensioneDiVapore);
  }

  public Double getCarbonatoCalcioTotaleCalcoli()
  {
    double carbonatoCalcioTotale=0;
    try
    {
      double temperatura=Double.parseDouble(getTemperatura());
      double letturaCalcimetro=Double.parseDouble(getLetturaCalcimetro());
      double pressioneAtmosferica=Double.parseDouble(getPressioneAtmosferica());
      double tensioneDiVapore=0;
      if (getTensioneDiVaporeCalcoli()!=null)
        tensioneDiVapore=getTensioneDiVaporeCalcoli().doubleValue();
      if (letturaCalcimetro>0 && pressioneAtmosferica>0 && tensioneDiVapore>0 && temperatura>0)
      {
        carbonatoCalcioTotale=(letturaCalcimetro*(pressioneAtmosferica-tensioneDiVapore)*273*0.44655)/(760*(273+temperatura));
      }
    }
    catch(Exception num)
    {
      carbonatoCalcioTotale=0;
    }
    if (carbonatoCalcioTotale==0) return null;
    else return new Double(carbonatoCalcioTotale);
  }

  public Double getFerroOssalatoCalcoli()
  {
    double ferroOssalato=0;
    try
    {
      double letturaFerroOssalato=Double.parseDouble(getLetturaFerroOssalato());
      double diluizioneDeterminaFerro=Double.parseDouble(getDiluizioneDeterminaFerro());
      if (letturaFerroOssalato>0 && diluizioneDeterminaFerro>0)
      {
        ferroOssalato=letturaFerroOssalato*diluizioneDeterminaFerro;
      }
    }
    catch(Exception num)
    {
      ferroOssalato=0;
    }
    if (ferroOssalato==0) return null;
    else return new Double(ferroOssalato);
  }

  public Double getIndicePotereClorosanteCalcoli()
  {
    double indicePotereClorosante=0;
    try
    {
      double calcareAttivo=Double.parseDouble(getCalcareAttivo());
      double ferroOssalato=0;
      if (getFerroOssalatoCalcoli()!=null)
        ferroOssalato=getFerroOssalatoCalcoli().doubleValue();
      if (calcareAttivo>0 && ferroOssalato>0)
      {
        indicePotereClorosante=(calcareAttivo*10000)/(ferroOssalato*ferroOssalato);
      }
    }
    catch(Exception num)
    {
      indicePotereClorosante=0;
    }
    if (indicePotereClorosante==0) return null;
    else return new Double(indicePotereClorosante);
  }

  /**
   * Questa funzione controlla che i dati che verranno inseriti o modificati nel
   * database siano consistenti
   * */
  public String ControllaDati()
  {
    StringBuffer errore=new StringBuffer("");

    if (getLetturaCalcimetro()!=null && !Utili.isDouble(getLetturaCalcimetro(),-9999999.99,9999999.99,2))
      errore.append(";1");
    if (getPressioneAtmosferica()!=null && !Utili.isDouble(getPressioneAtmosferica(),0.001,999999.999,3))
      errore.append(";2");
    if (getTemperatura()!=null && !Utili.isDouble(getTemperatura(),10,30,3))
      errore.append(";3");
    if (getCalcareAttivo()!=null && !Utili.isDouble(getCalcareAttivo(),0.0,999999.9,1))
      errore.append(";4");
    if (getLetturaFerroOssalato()!=null && !Utili.isDouble(getLetturaFerroOssalato(),0.0,999999.999,3))
      errore.append(";5");
    if (getDiluizioneDeterminaFerro()!=null && !Utili.isDouble(getDiluizioneDeterminaFerro(),0.0,999999.999,3))
      errore.append(";6");
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

  public String getLetturaCalcimetro()
  {
    return this.letturaCalcimetro;
  }
  public void setLetturaCalcimetro( String newLetturaCalcimetro )
  {
    if (newLetturaCalcimetro!=null) letturaCalcimetro=newLetturaCalcimetro.replace(',','.');
    else this.letturaCalcimetro = newLetturaCalcimetro;
  }

  public String getPressioneAtmosferica()
  {
    return this.pressioneAtmosferica;
  }
  public void setPressioneAtmosferica( String newPressioneAtmosferica )
  {
    if (newPressioneAtmosferica!=null) pressioneAtmosferica=newPressioneAtmosferica.replace(',','.');
    else this.pressioneAtmosferica = newPressioneAtmosferica;
  }

  public String getTemperatura()
  {
    return this.temperatura;
  }
  public void setTemperatura( String newTemperatura )
  {
    if (newTemperatura!=null) temperatura=newTemperatura.replace(',','.');
    else temperatura = newTemperatura;
  }

  public String getTensioneDiVapore()
  {
    return this.tensioneDiVapore;
  }
  public void setTensioneDiVapore( String newTensioneDiVapore )
  {
    if (newTensioneDiVapore!=null) tensioneDiVapore=newTensioneDiVapore.replace(',','.');
    else tensioneDiVapore = newTensioneDiVapore;
  }

  public String getCarbonatoCalcioTotalePDF()
  {
    if (carbonatoCalcioTotale==null) return "";
    else
    {
      carbonatoCalcioTotale=carbonatoCalcioTotale.replace(',','.');
      carbonatoCalcioTotale=Utili.nf1.format(Double.parseDouble(carbonatoCalcioTotale));
      carbonatoCalcioTotale=carbonatoCalcioTotale.replace('.',',');
      return carbonatoCalcioTotale;
    }
  }

  public String getCarbonatoCalcioTotale()
  {
    return this.carbonatoCalcioTotale;
  }
  public void setCarbonatoCalcioTotale( String newCarbonatoCalcioTotale )
  {
    if (newCarbonatoCalcioTotale!=null) carbonatoCalcioTotale=newCarbonatoCalcioTotale.replace(',','.');
    else carbonatoCalcioTotale = newCarbonatoCalcioTotale;
  }

  public String getCalcareAttivoPDF()
  {
    if (calcareAttivo==null) return "";
    else
    {
      calcareAttivo=calcareAttivo.replace(',','.');
      calcareAttivo=Utili.nf2.format(Double.parseDouble(calcareAttivo));
      calcareAttivo=calcareAttivo.replace('.',',');
      return calcareAttivo;
    }
  }

  public String getCalcareAttivo()
  {
    return this.calcareAttivo;
  }
  public void setCalcareAttivo( String newCalcareAttivo )
  {
    if (newCalcareAttivo!=null) calcareAttivo=newCalcareAttivo.replace(',','.');
    else calcareAttivo = newCalcareAttivo;
  }

  public String getLetturaFerroOssalato()
  {
    return this.letturaFerroOssalato;
  }
  public void setLetturaFerroOssalato( String newLetturaFerroOssalato )
  {
    if (newLetturaFerroOssalato!=null) letturaFerroOssalato=newLetturaFerroOssalato.replace(',','.');
    else letturaFerroOssalato = newLetturaFerroOssalato;
  }

  public String getDiluizioneDeterminaFerro()
  {
    return this.diluizioneDeterminaFerro;
  }
  public void setDiluizioneDeterminaFerro( String newDiluizioneDeterminaFerro )
  {
    if (newDiluizioneDeterminaFerro!=null) diluizioneDeterminaFerro=newDiluizioneDeterminaFerro.replace(',','.');
    else this.diluizioneDeterminaFerro = newDiluizioneDeterminaFerro;
  }

  public String getFerroOssalato()
  {
    return this.ferroOssalato;
  }
  public void setFerroOssalato( String newFerroOssalato )
  {
    if (newFerroOssalato!=null) ferroOssalato=newFerroOssalato.replace(',','.');
    else this.ferroOssalato = newFerroOssalato;
  }

  public String getIndicePotereClorosante()
  {
    return this.indicePotereClorosante;
  }
  public void setIndicePotereClorosante( String newIndicePotereClorosante )
  {
    if (newIndicePotereClorosante!=null) indicePotereClorosante=newIndicePotereClorosante.replace(',','.');
    else this.indicePotereClorosante = newIndicePotereClorosante;
  }
}