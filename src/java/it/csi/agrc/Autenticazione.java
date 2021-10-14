package it.csi.agrc;

import it.csi.cuneo.*;
import it.csi.jsf.web.pool.*;
import java.io.*;
import java.sql.*;
import javax.sql.*;

/**
 * Title:        Agrichim - Back Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @ver

/**
 * La classe Autenticazione viene utilizzata come classe base per memorizzare le
 * informazioni relative all'utente collegato al sistema (proprietà utente) e
 * gli eventuali problemi che possono verificarsi durante lesecuzione delle
 * query (query, contenutoQuery, errorCode)
 * E' un Java Bean che possiede le proprietà
 * -
 * - query: qui vengono memorizzate informazioni circa il metodo e la classe
 *   contenenti una query che ha generato l'errore
 * - contenutoQuery: viene memorizzata la query che ha causato un'errore
 * - errorCode: codice di errore
 * - dataSource
 * - utenteEsperto: se è vero siginifica che l'utente LAR collegato è
 *   un utente esperto
 * - idAnagrafica: anagrafica dell'utente LAr collegato
 * - nome: nome dell'utente LAR collegato
 * - cognome: cognome dell'utente LAR collegato
 * - idRichiestaCorrente: contiene l'id della richiesta attualmente in uso
 * - codMateriale: codice materiale della richiesta in bozza corrente.
 *   Inizialmente è null.
 * - coordinateGeografiche: se vale true significa che il materiale (TER, FOG,
 *   FRU) di cui si vuole fare l'analisi necessita dell'indicazione delle
 *   coordinate geografiche.
 */

public class Autenticazione extends BeanDAO implements Serializable
{
  private static final long serialVersionUID = 8555643485876740813L;
	
  private String contenutoQuery;
  private String errorCode;
  private String query;
  //private DataSource dataSource;
  private Throwable eccezione;
  private boolean utenteEsperto=false;
  private long idAnagrafica=-1;
  private String nome;
  private String cognome;
  private String queryRicerca;
  private String queryCountRicerca;
  private long idRichiestaCorrente=0;
  private String codMateriale;
  private boolean coordinateGeografiche;
  private String idRichieste;
  private String idRichiestaChecked;
  private Responsabile responsabile;

  public static final int RICERCA_CAMPIONI=1;
  public static final int RICERCA_ANALISI_RICHIESTE=2;
  public static final int RICERCA_CAMPIONI_LABORATORIO=3;
  public static final int RICERCA_REFERTI_DA_EMETTERE=4;
  public static final int RICERCA_REFERTI_EMESSI=5;







  public Autenticazione()
  { }

  /**
   * Caricamento dei dati dell'utente necessari all'applicazione
   * @param idAnagrafica viene passato da IRIDE
   * @param utenteEsperto viene passato da IRIDE
   * @return true se ha torvato un'anagrafica corrispondente
   * @throws Exception
   * @throws SQLException
   */
  /*public boolean caricaDati(String codiceFiscale,
                         boolean utenteEsperto)
  throws Exception, SQLException
  {
    Connection conn=this.getConnection();
    StringBuffer query=new StringBuffer("");
    Statement stmt = null;
    boolean trovato = false;
    this.utenteEsperto=utenteEsperto;
    try
    {
        stmt = conn.createStatement();
        query.append("SELECT NOME, COGNOME_RAGIONE_SOCIALE,");
        query.append("ID_ANAGRAFICA ");
        query.append("FROM ANAGRAFICA WHERE CODICE_IDENTIFICATIVO = '");
        query.append(codiceFiscale).append("'");
        query.append(" AND TIPO_UTENTE='L'");
        //CuneoLogger.debug(this, query.toString());
        ResultSet rset = stmt.executeQuery(query.toString());
        if (rset.next())
        {
          this.setCognome(rset.getString("COGNOME_RAGIONE_SOCIALE"));
          this.setNome(rset.getString("NOME"));
          this.idAnagrafica=rset.getLong("ID_ANAGRAFICA");
          trovato = true;
        }
        rset.close();
        if (trovato)
        {
          //  Se ho trovato un record e questo contiene un codice fiscale
          // vado a cercare il responsabile della persona che si è collegata.
          // Se trovo il responsabile memorizzo i suoi dati all'interno
          // dell'oggetto responsabile di autenticazione
          
          query.setLength(0);
          query.append("SELECT  F.* ");
          query.append("FROM UTENTE_INTERNO U, FIRMA F ");
          query.append("WHERE  U.CODICE_FISCALE = '");
          query.append(codiceFiscale);
          query.append("' AND F.ID_FIRMA=U.ID_FIRMA");
          rset = stmt.executeQuery(query.toString());
          if (rset.next())
          {
            Responsabile resp=new Responsabile();
            resp.setNome(rset.getString("NOME"));
            resp.setCognome(rset.getString("COGNOME"));
            resp.setTitoloOnorifico(rset.getString("TITOLO_ONORIFICO"));
            resp.setIdFirma(rset.getString("ID_FIRMA"));
            this.setResponsabile(resp);
          }
          rset.close();
        }
        return trovato;
    }
    catch(java.sql.SQLException ex)
    {
      ex.printStackTrace();
      setQuery("caricaDati della classe Autenticazione");
      setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      e.printStackTrace();
      setQuery("caricaDati della classe Autenticazione"
                              +": non è una SQLException ma una Exception"
                              +" generica");
      setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      if (stmt!=null) stmt.close();
      if (conn!=null) conn.close();
    }
  }*/

  public boolean isUtenteEsperto()
  {
    return utenteEsperto;
  }
  public void setUtenteEsperto(boolean utenteEsperto)
  {
    this.utenteEsperto = utenteEsperto;
  }
  public long getIdAnagrafica() {
    return idAnagrafica;
  }
  public void setIdAnagrafica(long idAnagrafica) {
    this.idAnagrafica = idAnagrafica;
  }
  public Throwable getEccezione()
  {
    return eccezione;
  }
  public void setEccezione(Throwable eccezione)
  {
    this.eccezione = eccezione;
  }
  public void setContenutoQuery(String newContenutoQuery)
  {
    contenutoQuery = newContenutoQuery;
  }
  public String getContenutoQuery()
  {
    return contenutoQuery;
  }
  public void setErrorCode(String errorCode)
  {
    this.errorCode = errorCode;
  }
  public String getErrorCode()
  {
    return errorCode;
  }
  public void setQuery(String query)
  {
    this.query = query;
  }
  public String getQuery()
  {
    return query;
  }

  /*public void setDataSource(Object obj)
  {
    if (Utili.POOLMAN)
      this.setGenericPool((it.csi.jsf.web.pool.GenericPool)obj);
    else
      this.dataSource = (javax.sql.DataSource)obj;
  }

  public void setDataSource(javax.sql.DataSource dataSource)
  {
    this.dataSource = dataSource;
  }

  public javax.sql.DataSource getDataSource()
  {
    return dataSource;
  }

  public Connection getConnection()
      throws Exception, SQLException
  {
    if (Utili.POOLMAN)
    {
      //Sono in ambiente TomCat quindi restuituisco una connessione fornita
      //da PoolMan
      return this.getGenericPool().getConnection();
    }
    else
    {
      //Sono in ambiente WebLogic quindi restuituisco una connessione fornita
      //da DataSource
      return dataSource.getConnection();
    }
  }

  public boolean isConnection()
      throws Exception, SQLException
  {
    if (Utili.POOLMAN)
    {
      //Sono in ambiente TomCat quindi controllo se Poolman è inizializzato
      if (this.getGenericPool() == null) return false;
      else return true;
    }
    else
    {
      //Sono in ambiente WebLogic quindi controllo se il DataSource è
      // inizializzato
      if (dataSource == null) return false;
      else return true;
    }
  }*/



  /**
   * Questo metodo viene chiamato per eseguire una ricerca dei dati relativi
   * alle anagrafiche senza utilizzare nessun filtro. Infatti viene chiamato il
   * metodo ricercaAnagrafiche passando il parametro filtri a false.
   */
  public void ricercaAnagrafiche()
  {
    this.ricercaAnagrafiche(false,null,null,null,null,null);
  }

  /**
   * Questo metodo viene chiamato per eseguire una ricerca dei dati relativi
   * alle organizzazioni senza utilizzare nessun filtro. Infatti viene chiamato
   * il metodo ricercaOrganizzazione passando il parametro filtri a false.
   */
  public void ricercaOrganizzazione()
  {
    this.ricercaOrganizzazione(false,null,null,null);
  }

  /**
   * Questo metodo viene chiamato per eseguire una ricerca delle fatture
   * senza utilizzare nessun filtro. Infatti viene chiamato il
   * metodo ricercaFatture passando il parametro filtri a false.
   */
  public void ricercaFatture()
  {
    this.ricercaFatture(false,null,null,null,null,null,null,null,null,null,null);
  }

  /**
   * Questo metodo viene chiamato per eseguire una ricerca per l'elenco
   * delle fatture senza utilizzare nessun filtro. Infatti viene chiamato il
   * metodo ricercaFatture passando il parametro filtri a false.
   */
  public void elencoFatture()
  {
    this.elencoFatture(false,null,null,null,null,null,null,null,null,null);
  }



  /**
   * Questo metodo viene chiamato per eseguire una ricerca dei dati relativi
   * alle anagrafiche
   * @param filtri: se vale true significa  che verrà richiamato il metodo che
   * imposta il filtro di ricerca
   * @param tipoUtente: tecnico, privato, LAR
   * @param tipoOrganizzazione:  Organizzazione professionale, Liberi
   * professionisti, Università ecc.
   * @param organizzazione: organizzazione a cui appartiene l'utente
   * @param ragSociale: ragione sociale o cognome dell'utente
   * @param codFoPIAVA: codice fiscale o partita iva dell'utente
   */
  public void ricercaAnagrafiche( boolean filtri,
                                  String tipoUtente,
                                  String tipoOrganizzazione,
                                  String organizzazione,
                                  String ragSociale,
                                  String codFoPIAVA
                                  )
  {
    String filtro="";
    if (filtri) filtro=impostaCriteriRicerca(tipoUtente,
                                             tipoOrganizzazione,organizzazione,
                                             ragSociale,codFoPIAVA);
    /*
     Questa query mi informa sul numero di record restituiti per organizzare
     la visualizzazione della pagina
    */
    StringBuffer queryCount = new StringBuffer();
    StringBuffer query = new StringBuffer();

    queryCount.append("SELECT COUNT(*) AS NUM ");
    queryCount.append("FROM ANAGRAFICA A,COMUNE C ");
    if (tipoOrganizzazione!=null)
      queryCount.append(",ORGANIZZAZIONE_PROFESSIONALE OP ");
    queryCount.append("WHERE C.CODICE_ISTAT=A.COMUNE_RESIDENZA ");
    queryCount.append(filtro);
    CuneoLogger.debug(this, "Autenticazione::ricercaAnagrafiche() - queryCount: "+queryCount.toString());
    this.setQueryCountRicerca(queryCount.toString());

    query.append("SELECT   * FROM (");
    query.append("SELECT ROW_NUMBER() ");
    query.append("OVER (ORDER BY A.COGNOME_RAGIONE_SOCIALE,A.NOME) AS NUM,");
    query.append("A.ID_ANAGRAFICA, ");
    query.append("A.COGNOME_RAGIONE_SOCIALE,A.NOME,A.CODICE_IDENTIFICATIVO, ");
    query.append("(CASE WHEN A.TIPO_UTENTE='L' THEN 'LABORATORIO' ");
    query.append("      WHEN A.TIPO_UTENTE='P' THEN 'PRIVATO' ");
    query.append("      WHEN A.TIPO_UTENTE='T' THEN 'TECNICO' END) AS TIPO_UTENTE,");
    query.append("C.DESCRIZIONE AS COMUNE, ");
    query.append("A.ID_ANAGRAFICA_2 ");
    query.append("FROM ANAGRAFICA A,COMUNE C ");
    if (tipoOrganizzazione!=null)
      query.append(",ORGANIZZAZIONE_PROFESSIONALE OP ");
    query.append("WHERE C.CODICE_ISTAT=A.COMUNE_RESIDENZA ");
    query.append(filtro);
    query.append(") AS LISTA_ORGANIZZAZIONI ");    
    CuneoLogger.debug(this, "Autenticazione::ricercaAnagrafiche() - query: "+query.toString());
    this.setQueryRicerca(query.toString());
  }

  /**
   * Questo metodo viene chiamato per eseguire una ricerca dei dati relativi
   * alle organizzazioni
   * @param filtri: se vale true significa  che verrà richiamato il metodo che
   * imposta il filtro di ricerca
   * @param tipoOrganizzazione:  Organizzazione professionale, Liberi
   * professionisti, Università ecc.
   * @param ragSociale: ragione sociale
   * @param istat: istat del comune sede dell'organizzazione
   */
  public void ricercaOrganizzazione( boolean filtri,
                                     String tipoOrganizzazione,
                                     String ragSociale,
                                     String istat
                                   )
  {
    String filtro="";
    if (filtri) filtro=impostaCriteriRicerca(tipoOrganizzazione,
                                             ragSociale,istat);
    /*
     Questa query mi informa sul numero di record restituiti per organizzare
     la visualizzazione della pagina
    */
    StringBuffer queryCount = new StringBuffer();
    StringBuffer query = new StringBuffer();

    queryCount.append("SELECT COUNT(*) AS NUM ");
    queryCount.append("FROM ORGANIZZAZIONE_PROFESSIONALE OP, COMUNE C ");
    queryCount.append("WHERE C.CODICE_ISTAT=OP.COMUNE_RESIDENZA ");
    queryCount.append(filtro);
    CuneoLogger.debug(this, "Autenticazione::ricercaOrganizzazione() - queryCount: "+queryCount.toString());
    this.setQueryCountRicerca(queryCount.toString());

    query.append("SELECT   * FROM (");
    query.append("SELECT ROW_NUMBER()  OVER (ORDER BY OP.RAGIONE_SOCIALE) AS NUM,");
    query.append("OP.ID_ORGANIZZAZIONE,");
    query.append("OP.CF_PARTITA_IVA,OP.RAGIONE_SOCIALE,OP.SEDE_TERRITORIALE,");
    query.append("OP.INDIRIZZO,C.DESCRIZIONE AS COMUNE ");
    query.append("FROM ORGANIZZAZIONE_PROFESSIONALE OP, COMUNE C ");
    query.append("WHERE C.CODICE_ISTAT=OP.COMUNE_RESIDENZA ");
    query.append(filtro);
    query.append(") AS LISTA_ORGANIZZAZIONI ");
    CuneoLogger.debug(this, "Autenticazione::ricercaOrganizzazione() - query: "+query.toString());
    this.setQueryRicerca(query.toString());
  }

  /**
   * Questo metodo viene chiamato per eseguire una ricerca delle fatture
   * @param filtri: se vale true significa  che verrà richiamato il metodo che
   * imposta il filtro di ricerca
   * @param annoFattura: anno di rilascio della fattura
   * @param numeroFattura: numero della fattura
   * @param dataDa: data (di emissione) minima che deve avere la fattura
   * per soddisfare la ricerca
   * @param dataA: data (di emissione) massima che deve avere la fattura
   * per soddisfare la ricerca
   * @param numeroRichiesta: numero della richiesta
   * @param annoCampione: anno del campione
   * @param numeroCampione: numero del campione
   * @param codFisPIVA:codice fiscale o partita iva della tabella fattura
   * @param statoPagamento: indica se la fattura è stata pagata (S), se  è
   * stata pagata parzialmente (P) o se è ancora da pagare (N)
   * @param statoFattura: indica se la fattura è stata annullata (S)
   */
  public void ricercaFatture( boolean filtri,
                              String annoFattura,
                              String numeroFattura,
                              String dataDa,
                              String dataA,
                              String numeroRichiesta,
                              String annoCampione,
                              String numeroCampione,
                              String codFisPIVA,
                              String statoPagamento,
                              String statoFattura
                              )
  {
    String filtro="";
    if (filtri) filtro=impostaCriteriRicerca(annoFattura,numeroFattura,dataDa,
                                             dataA,numeroRichiesta,annoCampione,
                                             numeroCampione,codFisPIVA,
                                             statoPagamento,statoFattura);
    /*
     Questa query mi informa sul numero di record restituiti per organizzare
     la visualizzazione della pagina
    */
    StringBuffer queryCount = new StringBuffer();
    StringBuffer query = new StringBuffer();



    queryCount.append("SELECT COUNT(*) AS NUM FROM (");
    queryCount.append("SELECT F.ANNO, F.NUMERO_FATTURA ");
    //queryCount.append("FROM FATTURA F,CAMPIONE_FATTURATO CF,ETICHETTA_CAMPIONE E ");
    queryCount.append("FROM FATTURA F, CAMPIONE_FATTURATO CF LEFT OUTER JOIN ETICHETTA_CAMPIONE E ON (E.ID_RICHIESTA = CF.ID_RICHIESTA) ");
    queryCount.append("WHERE F.ANNO=CF.ANNO ");
    queryCount.append("AND F.NUMERO_FATTURA=CF.NUMERO_FATTURA ");
    //queryCount.append("AND E.ID_RICHIESTA=CF.ID_RICHIESTA ");
    queryCount.append(filtro);
    queryCount.append(" GROUP BY F.ANNO, F.NUMERO_FATTURA) AS LISTA_FATTURE ");
    CuneoLogger.debug(this, "Autenticazione::ricercaFatture() - queryCount: "+queryCount.toString());
    this.setQueryCountRicerca(queryCount.toString());

    query.append("SELECT   * FROM (");
    //query.append("SELECT ROW_NUMBER() OVER (ORDER BY DATA_FATTURA) AS NUM, ANNO, NUMERO_FATTURA,DATA_FATTURA,");
    query.append("SELECT ROW_NUMBER() OVER (ORDER BY ANNO DESC, NUMERO_FATTURA DESC, numero_campione desc) AS NUM, ANNO, NUMERO_FATTURA,DATA_FATTURA,");
    query.append("PAGATA,ANNULLATA,PARTITA_IVA_O_CF,RAGIONE_SOCIALE, numero_campione, anno_E, (COALESCE(numero_campione, '') || '/' ||COALESCE(ANNO_E, '')) as EE ");
    query.append("FROM (SELECT F.ANNO ANNO ,F.NUMERO_FATTURA  NUMERO_FATTURA,");
    query.append("TO_CHAR (F.DATA_FATTURA, 'DD/MM/YYYY') DATA_FATTURA,");
    query.append("F.PAGATA PAGATA, F.ANNULLATA ANNULLATA, ");
    query.append("F.PARTITA_IVA_O_CF PARTITA_IVA_O_CF, F.RAGIONE_SOCIALE RAGIONE_SOCIALE, E.NUMERO_CAMPIONE, E.ANNO as ANNO_E ");
    //query.append("FROM FATTURA F, CAMPIONE_FATTURATO CF, ETICHETTA_CAMPIONE E ");
    query.append("FROM FATTURA F, CAMPIONE_FATTURATO CF LEFT OUTER JOIN ETICHETTA_CAMPIONE E ON (E.ID_RICHIESTA = CF.ID_RICHIESTA) ");
    query.append("WHERE F.ANNO = CF.ANNO ");
    query.append("AND F.NUMERO_FATTURA = CF.NUMERO_FATTURA ");
    //query.append("AND E.ID_RICHIESTA = CF.ID_RICHIESTA ");
    query.append(filtro);
    query.append(" GROUP BY F.ANNO, F.NUMERO_FATTURA, F.DATA_FATTURA, F.PAGATA,");
    query.append("F.ANNULLATA,F.PARTITA_IVA_O_CF, F.RAGIONE_SOCIALE, E.NUMERO_CAMPIONE, E.ANNO  order by anno_e, E.NUMERO_CAMPIONE desc ");
    query.append(") AS LISTA_FATTURE ) AS LISTA_FATTURE_2 ");
    CuneoLogger.debug(this, "Autenticazione::ricercaFatture() - query: "+query.toString());
    this.setQueryRicerca(query.toString());
  }

  /**
   * Questo metodo viene chiamato per eseguire una ricerca dell'elenco delle fatture
   * @param filtri: se vale true significa  che verrà richiamato il metodo che
   * imposta il filtro di ricerca
   * @param dataDa: data (di emissione) minima che deve avere la fattura
   * per soddisfare la ricerca
   * @param dataA: data (di emissione) massima che deve avere la fattura
   * per soddisfare la ricerca
   * @param annoFatturaDa: anno di rilascio della fattura
   * @param numeroFatturaDa: numero della fattura
   * @param annoFatturaA: anno di rilascio della fattura
   * @param numeroFatturaA: numero della fattura
   * @param codFisPIVA:codice fiscale o partita iva della tabella fattura
   * @param statoPagamento: indica se la fattura è stata pagata (S), se  è
   * stata pagata parzialmente (P) o se è ancora da pagare (N)
   * @param statoFattura: indica se la fattura è stata annullata (S)
   */
  public void elencoFatture( boolean filtri,
                              String dataDa,
                              String dataA,
                              String annoFatturaDa,
                              String numeroFatturaDa,
                              String annoFatturaA,
                              String numeroFatturaA,
                              String codFisPIVA,
                              String statoPagamento,
                              String statoFattura
                              )
  {
    String filtro="";
    if (filtri) filtro=impostaCriteriRicerca(dataDa,dataA,annoFatturaDa,
                                             numeroFatturaDa,annoFatturaA,
                                             numeroFatturaA,codFisPIVA,
                                             statoPagamento,statoFattura);
    /*
     Questa query mi informa sul numero di record restituiti per organizzare
     la visualizzazione della pagina
    */
    StringBuffer query = new StringBuffer();
    query.append("SELECT F.ANNO,");
    query.append("TO_CHAR (F.DATA_FATTURA, 'DD/MM/YYYY') DATA_FATTURA,");
    query.append("F.NUMERO_FATTURA,F.RAGIONE_SOCIALE,F.PARTITA_IVA_O_CF,");
    query.append("F.IMPORTO_SPEDIZIONE,F.PAGATA,F.ANNULLATA,");
    query.append("F.INDIRIZZO,F.CAP,F.COMUNE,F.SIGLA_PROVINCIA,");
    query.append("SUM(CF.IMPORTO_IMPONIBILE) IMPORTO_IMPONIBILE,");
    query.append("SUM(CF.IMPORTO_IVA) IMPORTO_IVA ");
    query.append("FROM FATTURA F,CAMPIONE_FATTURATO CF ");
    query.append("WHERE F.ANNO=CF.ANNO ");
    query.append("AND F.NUMERO_FATTURA=CF.NUMERO_FATTURA ");
    query.append(filtro);
    query.append("GROUP BY F.ANNO, F.NUMERO_FATTURA,F.RAGIONE_SOCIALE,");
    query.append("F.PARTITA_IVA_O_CF,F.IMPORTO_SPEDIZIONE,F.PAGATA,F.ANNULLATA,");
    query.append("F.INDIRIZZO,F.CAP,F.COMUNE,F.SIGLA_PROVINCIA,F.DATA_FATTURA ");
    query.append("ORDER BY ANNO DESC, NUMERO_FATTURA DESC");
    CuneoLogger.debug(this, "Autenticazione::elencoFatture() - query: " + query.toString());
    this.setQueryRicerca(query.toString());
  }

  /**
   * Questo metodo viene chiamato per eseguire una ricerca di tutti i campioni
   * senza utilizzare nessun filtro. Infatti viene chiamato il metodo
   * ricercaElencoCampioni passando il parametro filtri a false.
   * @param ricerca: indica che ricerca effettuare, può assumere uno dei seguenti
   * valori:
   * <br>- RICERCA_CAMPIONI: ricerca tutti i campioni
   * <br>- RICERCA_ANALISI_RICHIESTE: ricerca i campioni delle analisi richieste
   * <br>- RICERCA_CAMPIONI_LABORATORIO: ricerca i campioni in laboratorio
   * <br>- RICERCA_REFERTI_DA_EMETTERE: ricerca i campioni che si trovano nello
   * stato "Referto da emettere"
   * <br>- RICERCA_REFERTI_EMESSI: ricerca i campioni che si trovano nello
   * stato "Referto da emettere"
   */
  public void ricercaElencoCampioni(int ricerca)
  {
    this.ricercaElencoCampioni(ricerca,false,null,null,null,null,null,null,null,null,
                               null,null,null,null,null,null,null,null,null,null,null,
                               null,null,null,null,null,true,false,null);
  }

  /**
   * Questo metodo viene utilizzato per effettuare le ricerche brevi (quelle
   * per cui non si apre la finestra di ricerca)
   * @param ricerca: indica che ricerca effettuare, può assumere uno dei seguenti
   * valori:
   * <br>- RICERCA_CAMPIONI: ricerca tutti i campioni
   * <br>- RICERCA_ANALISI_RICHIESTE: ricerca i campioni delle analisi richieste
   * <br>- RICERCA_CAMPIONI_LABORATORIO: ricerca i campioni in laboratorio
   * <br>- RICERCA_REFERTI_DA_EMETTERE: ricerca i campioni che si trovano nello
   * stato "Referto da emettere"
   * <br>- RICERCA_REFERTI_EMESSI: ricerca i campioni che si trovano nello
   * stato "Referto da emettere"
   * @param anno : Anno di riferimento del campione
   * @param numCampione: numero progressivo attribuito automaticamente al
   * campione
   * @param idRichiesta: identificativo univoco del campione
   * @param tipoMateriale: materiale di cui è composto il campione
   */
  public void ricercaElencoCampioni(int ricerca,
                                    String anno,
                                    String numCampione,
                                    String idRichiesta,
                                    String tipoMateriale)
  {
    this.ricercaElencoCampioni(ricerca,true,anno,numCampione,null,idRichiesta,
                               null,null,null,null,null,null,null,null,null,null,null,tipoMateriale,
                               null,null,null,null,null,null,null,null,true,true,null);
  }

  /**
   * Questo metodo viene chiamato per eseguire una ricerca dei campioni
   * @param filtri: se vale true significa  che verrà richiamato il metodo che
   * imposta il filtro di ricerca
   * @param ricerca: indica che ricerca effettuare, può assumere uno dei seguenti
   * valori:
   * <br>- RICERCA_CAMPIONI: ricerca tutti i campioni
   * <br>- RICERCA_ANALISI_RICHIESTE: ricerca i campioni delle analisi richieste
   * <br>- RICERCA_CAMPIONI_LABORATORIO: ricerca i campioni in laboratorio
   * <br>- RICERCA_REFERTI_DA_EMETTERE: ricerca i campioni che si trovano nello
   * stato "Referto da emettere"
   * <br>- RICERCA_REFERTI_EMESSI: ricerca i campioni che si trovano nello
   * stato "Referto da emettere"
   * @param anno: Anno di riferimento del campione
   * @param numCampioneDa: numero progressivo (attribuito automaticamente al
   * campione) minimo che deve avere il campione per soddisfare la ricerca
   * @param numCampioneA: numero progressivo (attribuito automaticamente al
   * campione) massimo che deve avere il campione per soddisfare la ricerca
   * @param idRichiestaDa: identificativo (univoco del campione) minimo che deve
   * avere il campione per soddisfare la ricerca
   * @param idRichiestaA: identificativo (univoco del campione) minimo che deve
   * avere il campione per soddisfare la ricerca
   * @param dataDa: data (in cui è stata inserita la richeista) minima che deve
   * avere il campione per soddisfare la ricerca
   * @param dataA: data (in cui è stata inserita la richeista) massima che deve
   * avere il campione per soddisfare la ricerca
   * @param cognomeProprietario: contiene i caratteri (almeno 2) che devono
   * essere contenuti nel cognome del proprietario del campione perché il
   * campione soddisfi la ricerca
   * @param nomeProprietario:contiene i caratteri che devono
   * essere contenuti nel nome del proprietario del campione perché il
   * campione soddisfi la ricerca
   * @param cognomeTecnico:contiene i caratteri (almeno 2) che devono
   * essere contenuti nel cognome del tecnico del campione perché il
   * campione soddisfi la ricerca
   * @param nomeTecnico:contiene i caratteri che devono
   * essere contenuti nel nome del tecnico del campione perché il
   * campione soddisfi la ricerca
   * @param tipoMateriale: materiale di cui è composto il campione
   * @param etichetta: contiene i caratteri (almeno 4) che devono
   * essere contenuti nell'etichetta del campione perché il
   * campione soddisfi la ricerca
   * @param labConsegna: codice del laboratorio di consegna
   * @param stato: codice dello stato della richiesta (Bozza, Analisi richiesta,
   * ecc...)
   * @param statoPagamento: codice dello stato di pagamento relativo al campione
   * (pagata,da pagare,gratuita)
   * @param organizzazione: seleziono tutte le etichette inserite da tecnici
   * che appartengono a questa organizzazione
   * @param labAnalisi: codice del laboratorio di analisi
   * @param analisiFattura: serve per sapere se la fattura è richiesta, e se si
   * se è stata emessa
   * @param spedizioneFattura: serve per sapere se la fattura è da spedire o
   * o consegnare a mano
   * @param tutti: se vale true lascai inalterato il filtro, se vale false
   * aggiunge una condizione al filtro che è diversa a seconda del campione
   * @param filtroBreve: indica se il filtro da utilizzare va applicato solo
   * a ualche parametro
   */
  public void ricercaElencoCampioni(int ricerca,
                                    boolean filtri,
                                    String anno,
                                    String numCampioneDa,
                                    String numCampioneA,
                                    
                                    String idRichiestaDa,
                                    String idRichiestaA,
                                    String dataDa,
                                    String dataA,
                                    String dataDaPag,
                                    String dataAPag,
                                    String dataEmissioneRefertoDa,
                                    String dataEmissioneRefertoA,   
                                    
                                    String cognomeProprietario,
                                    String nomeProprietario,
                                    
                                    String cognomeTecnico,
                                    String nomeTecnico,
                                    
                                    String tipoMateriale,
                                    String etichetta,
                                    String labConsegna,
                                    String stato,
                                    
                                    String statoPagamento,
                                    String organizzazione,
                                    String labAnalisi,
                                    String analisiFattura,
                                    String spedizioneFattura,
                                    boolean tutti,
                                    boolean filtroBreve,
                                    String iuv)
  {
    String filtro="";
    boolean normale=true;
    if (filtri) filtro=impostaCriteriRicerca(ricerca,anno,numCampioneDa,numCampioneA,
                                      idRichiestaDa,idRichiestaA,dataDa,dataA,dataDaPag,dataAPag,
                                      dataEmissioneRefertoDa, dataEmissioneRefertoA,
                                      cognomeProprietario,nomeProprietario,
                                      cognomeTecnico,nomeTecnico,
                                      tipoMateriale,etichetta,labConsegna,
                                      stato,statoPagamento,organizzazione,
                                      labAnalisi,analisiFattura,spedizioneFattura,
                                      tutti,filtroBreve);

    if (filtro.length()>0 && filtro.charAt(0) == '1'){
      normale=false;
      filtro=filtro.substring(1);
    }

    /*
     Questa query mi informa sul numero di record restituiti per organizzare
     la visualizzazione della pagina
    */
    StringBuffer queryCount = new StringBuffer();
    if (normale)
     queryCount.append("SELECT COUNT(EC.ID_RICHIESTA) AS NUM ");
    else
     queryCount.append("SELECT COUNT(DISTINCT(EC.ID_RICHIESTA)) AS NUM ");
    queryCount.append("FROM MATERIALE M,");
    if (normale)
     queryCount.append("CODIFICA_TRACCIABILITA CT, ");
    else
     queryCount.append("CODIFICA_TRACCIABILITA CT, ANAGRAFICA A, ");
    
    /* CONCLUSIONE CLAUSOLA FROM - INIZIO */
    if (ricerca == RICERCA_CAMPIONI){
    	queryCount.append("ETICHETTA_CAMPIONE EC ");
    }else{
    	queryCount.append("ETICHETTA_CAMPIONE EC LEFT OUTER JOIN TRACCIATURA T ON (EC.ID_RICHIESTA=T.ID_RICHIESTA) ");
    }
    
    if (ricerca == RICERCA_REFERTI_EMESSI || analisiFattura!=null) {
    	queryCount.append(" LEFT JOIN DATI_FATTURA DF ON EC.ID_RICHIESTA=DF.ID_RICHIESTA ");    	
    }
    if((dataDaPag!=null && !dataDaPag.equals("")) || (dataAPag!=null && !dataAPag.equals("")) || (iuv!=null && !iuv.equals("")))
    	queryCount.append(" LEFT JOIN PAGAMENTI P on EC.ID_RICHIESTA = P.ID_RICHIESTA ");
    if(iuv!=null && !iuv.equals("")) {
    	if(iuv.startsWith("3")) 
	    	  iuv = iuv.substring(1);
		queryCount.append("WHERE UPPER(IUV) like UPPER('").append(iuv).append("') AND ");
    }else
    	queryCount.append("WHERE ");
    /* CONCLUSIONE CLAUSOLA FROM - FINE */

    queryCount.append(" EC.CODICE_MATERIALE= M.CODICE_MATERIALE ");
    queryCount.append("AND EC.STATO_ATTUALE = CT.CODICE ");
    
    switch (ricerca){
      case RICERCA_CAMPIONI:queryCount.append("AND EC.STATO_ATTUALE<>'00' ");
                            break;
      case RICERCA_ANALISI_RICHIESTE:queryCount.append("AND EC.STATO_ATTUALE='10' ");
                                     break;
      case RICERCA_CAMPIONI_LABORATORIO:queryCount.append("AND EC.STATO_ATTUALE='20' ");
                                        break;
      case RICERCA_REFERTI_DA_EMETTERE:queryCount.append("AND EC.STATO_ATTUALE='30' ");
                                       break;
      case RICERCA_REFERTI_EMESSI:
      	queryCount.append("AND EC.STATO_ATTUALE='40' ");
      	
      	//AGRICHIM-22
      	//Filtro di ricerca avanzato sulla data di emissione del referto nell'elenco dei referti emessi
      	if (dataEmissioneRefertoDa != null || dataEmissioneRefertoA != null){
        	queryCount.append("AND EXISTS (SELECT ID_RICHIESTA ");
        	queryCount.append("FROM TRACCIATURA ");
        	queryCount.append("WHERE ID_RICHIESTA = EC.ID_RICHIESTA ");
        	queryCount.append("AND CODICE = '" + Constants.CODICE_TRACCIATURA.REFERTO_EMESSO + "' ");
        	if (dataEmissioneRefertoDa != null){
          	queryCount.append("AND DATA >= to_timestamp('");
          	queryCount.append(dataEmissioneRefertoDa).append("','DD/MM/YYYY') ");        		
        	}
        	if (dataEmissioneRefertoA != null) {
        		queryCount.append("AND DATA <= to_timestamp('");
        		queryCount.append(dataEmissioneRefertoA).append("','DD/MM/YYYY') ");
        	}
        	queryCount.append(") ");
      	}

        break;
    }

    /* CONCLUSIONE CLAUSOLA WHERE - INIZIO */
    if (ricerca != RICERCA_CAMPIONI){
    	queryCount.append("AND T.PROGRESSIVO = ");
    	queryCount.append("(SELECT MAX(PROGRESSIVO) FROM TRACCIATURA WHERE EC.ID_RICHIESTA=ID_RICHIESTA) ");
    }

    if(dataDaPag!=null && !dataDaPag.equals(""))
    	queryCount.append("AND P.DATA_PAGAMENTO >= to_timestamp('").append(dataDaPag).append("','DD/MM/YYYY') ");    
    if(dataAPag!=null && !dataAPag.equals(""))
    	queryCount.append("AND P.DATA_PAGAMENTO <= to_timestamp('").append(dataAPag).append("','DD/MM/YYYY') "); 
    /* CONCLUSIONE CLAUSOLA WHERE - FINE */

    queryCount.append(filtro);
    CuneoLogger.debug(this, "Autenticazione::ricercaElencoCampioni - queryCount: "+queryCount.toString());
    this.setQueryCountRicerca(queryCount.toString());
    
    /*
     Questa query invece mi restituisce i record
    */
    StringBuffer query = new StringBuffer();
    query.append("SELECT * FROM ( ");
    if (normale)
     query.append("SELECT EC.ID_RICHIESTA,");
    else
     query.append("SELECT DISTINCT EC.ID_RICHIESTA,");

    query.append("ROW_NUMBER()  OVER ");
    switch (ricerca){
      case RICERCA_CAMPIONI:
    	  query.append("(ORDER BY EC.ID_RICHIESTA DESC) AS NUM,");
          break;
      case RICERCA_ANALISI_RICHIESTE:
    	  query.append("(ORDER BY EC.ID_RICHIESTA DESC) AS NUM,");
          break;
      case RICERCA_CAMPIONI_LABORATORIO:
    	  query.append("(ORDER BY EC.ANNO ASC, EC.NUMERO_CAMPIONE ASC, EC.ID_RICHIESTA ASC) AS NUM,");
          break;
      case RICERCA_REFERTI_DA_EMETTERE:
      	query.append("(ORDER BY EC.ANNO DESC, EC.NUMERO_CAMPIONE DESC, EC.ID_RICHIESTA DESC) AS NUM,");
          break;
      case RICERCA_REFERTI_EMESSI:
      	query.append("(ORDER BY EC.ANNO DESC, EC.NUMERO_CAMPIONE DESC, EC.ID_RICHIESTA DESC) AS NUM,");
          break;
    }

    query.append("TO_CHAR(EC.DATA_INSERIMENTO_RICHIESTA,'DD/MM/YYYY')");
    query.append(" AS DATA,EC.ANNO,EC.NUMERO_CAMPIONE,EC.STATO_ATTUALE,");
    query.append("EC.CODICE_MATERIALE AS CODICE_MATERIALE,");
    query.append("M.DESCRIZIONE AS MATERIALE,");
    query.append("EC.DESCRIZIONE_ETICHETTA,");
    query.append("CT.DESCRIZIONE AS DESC_STATO_ATTUALE,");
    query.append("EC.ANAGRAFICA_UTENTE,");
    query.append("EC.ANAGRAFICA_TECNICO,");
    query.append("EC.ANAGRAFICA_PROPRIETARIO, ");
    query.append("EC.PAGAMENTO, ");
    query.append("(with elenco_iuv as (select distinct FIRST_VALUE(iuv) " + 
			"    OVER( PARTITION BY ID_RICHIESTA ORDER BY data_annullamento desc) iuv,ID_RICHIESTA from PAGAMENTI) " + 
			"select case when p.data_annullamento is null then p.iuv else '' end iuv " + 
			"from pagamenti p " + 
			"  JOIN elenco_iuv ei ON p.id_richiesta = ei.id_richiesta and p.iuv = ei.iuv " + 
			"where p.id_richiesta = ec.id_richiesta),");
	query.append("(with elenco_iuv as ( select distinct FIRST_VALUE(iuv)  " + 
			"    OVER( PARTITION BY ID_RICHIESTA ORDER BY data_annullamento desc) iuv,ID_RICHIESTA from PAGAMENTI) " + 
			"select  TP.COD_TIPO_PAGAMENTO " + 
			"from pagamenti p " + 
			"  JOIN elenco_iuv ei ON p.id_richiesta = ei.id_richiesta and p.iuv = ei.iuv " + 
			"  LEFT JOIN tipo_pagamento TP ON P.ID_TIPO_PAGAMENTO = TP.ID_TIPO_PAGAMENTO " + 
			"where p.id_richiesta = ec.id_richiesta),");
	query.append("(with elenco_iuv as (select distinct FIRST_VALUE(iuv) " + 
			"    OVER( PARTITION BY ID_RICHIESTA ORDER BY data_annullamento desc) iuv,ID_RICHIESTA from PAGAMENTI) " + 
			"select p.data_annullamento " + 
			"from pagamenti p " + 
			"  JOIN elenco_iuv ei ON p.id_richiesta = ei.id_richiesta and p.iuv = ei.iuv " + 
			"where p.id_richiesta = ec.id_richiesta)");
    switch (ricerca){
      case RICERCA_CAMPIONI:query.append(",EC.NOTE_CLIENTE AS NOTE ");
                            break;
      case RICERCA_ANALISI_RICHIESTE:query.append(",T.NOTE ");
                                     break;
      case RICERCA_CAMPIONI_LABORATORIO:query.append(",T.NOTE ");
                                        break;
      case RICERCA_REFERTI_DA_EMETTERE:query.append(",T.NOTE ");
                                       break;
      case RICERCA_REFERTI_EMESSI:query.append(",T.NOTE ");
                                  break;
    }
    query.append("FROM MATERIALE M, ");
    if (normale)
      query.append("CODIFICA_TRACCIABILITA CT, ");
    else
      query.append("CODIFICA_TRACCIABILITA CT, ANAGRAFICA A, ");
    
    /* CONCLUSIONE CLAUSOLA FROM - INIZIO */
    if (ricerca == RICERCA_CAMPIONI){
    	query.append("ETICHETTA_CAMPIONE EC ");
    }else{
    	query.append("ETICHETTA_CAMPIONE EC LEFT OUTER JOIN TRACCIATURA T ON (EC.ID_RICHIESTA=T.ID_RICHIESTA) ");
    }
    
    if (ricerca == RICERCA_REFERTI_EMESSI || analisiFattura!=null){
    	query.append(" LEFT JOIN DATI_FATTURA DF ON EC.ID_RICHIESTA=DF.ID_RICHIESTA ");    	
    }
    if((dataDaPag!=null && !dataDaPag.equals("")) || (dataAPag!=null && !dataAPag.equals("")))
    	query.append(" LEFT JOIN PAGAMENTI P on EC.ID_RICHIESTA = P.ID_RICHIESTA ");
    /* CONCLUSIONE CLAUSOLA FROM - FINE */

    query.append("WHERE EC.CODICE_MATERIALE= M.CODICE_MATERIALE ");
    query.append("AND EC.STATO_ATTUALE = CT.CODICE ");

    switch (ricerca){
      case RICERCA_CAMPIONI:query.append("AND EC.STATO_ATTUALE<>'00' ");
                            break;
      case RICERCA_ANALISI_RICHIESTE:query.append("AND EC.STATO_ATTUALE='10' ");
                                     break;
      case RICERCA_CAMPIONI_LABORATORIO:query.append("AND EC.STATO_ATTUALE='20' ");
                                        break;
      case RICERCA_REFERTI_DA_EMETTERE:query.append("AND EC.STATO_ATTUALE='30' ");
                                       break;
      case RICERCA_REFERTI_EMESSI:
      	query.append("AND EC.STATO_ATTUALE='40' ");
      	
      	//AGRICHIM-22
      	//Filtro di ricerca avanzato sulla data di emissione del referto nell'elenco dei referti emessi
      	if (dataEmissioneRefertoDa != null || dataEmissioneRefertoA != null){
      		query.append("AND EXISTS (SELECT ID_RICHIESTA ");
      		query.append("FROM TRACCIATURA ");
      		query.append("WHERE ID_RICHIESTA = EC.ID_RICHIESTA ");
      		query.append("AND CODICE = '" + Constants.CODICE_TRACCIATURA.REFERTO_EMESSO + "' ");
        	if (dataEmissioneRefertoDa != null){
        		query.append("AND DATA >= to_timestamp('");
        		query.append(dataEmissioneRefertoDa).append("','DD/MM/YYYY') ");        		
        	}
        	if (dataEmissioneRefertoA != null){
        		query.append("AND DATA <= to_timestamp('");
        		query.append(dataEmissioneRefertoA).append("','DD/MM/YYYY') ");
        	}
        	query.append(") ");
      	}
        break;
    }
    /* CONCLUSIONE CLAUSOLA WHERE - INIZIO */
    if (ricerca != RICERCA_CAMPIONI){
    	query.append("AND T.PROGRESSIVO = ");
    	query.append("(SELECT MAX(PROGRESSIVO) FROM TRACCIATURA WHERE EC.ID_RICHIESTA=ID_RICHIESTA) ");
    }

    if (dataDaPag!=null && !dataDaPag.equals(""))
    	query.append("AND P.DATA_PAGAMENTO >= to_timestamp('").append(dataDaPag).append("','DD/MM/YYYY') ");
    if (dataAPag!=null && !dataAPag.equals("")) 
    	query.append("AND P.DATA_PAGAMENTO <= to_timestamp('").append(dataAPag).append("','DD/MM/YYYY') ");
    /* CONCLUSIONE CLAUSOLA WHERE - FINE */
    
    query.append(filtro);
    if(iuv!=null && !iuv.equals("")) {
		query.append(" IUV:'").append(iuv).append("'");
	}else
		query.append(") AS LISTA_CAMPIONI ");
    CuneoLogger.debug(this, "Autenticazione::ricercaElencoCampioni - query: "+query.toString());
    this.setQueryRicerca(query.toString());
  }

  /**
   * Questo metodo viene utilizzato per andare costruire quella parte di query
   * che permetterà di fare un filtro per la ricerca
   * @param ricerca: indica che ricerca effettuare, può assumere uno dei seguenti
   * valori:
   * <br>- RICERCA_CAMPIONI: ricerca tutti i campioni
   * <br>- RICERCA_ANALISI_RICHIESTE: ricerca i campioni delle analisi richieste
   * <br>- RICERCA_CAMPIONI_LABORATORIO: ricerca i campioni in laboratorio
   * <br>- RICERCA_REFERTI_DA_EMETTERE: ricerca i campioni che si trovano nello
   * stato "Referto da emettere"
   * <br>- RICERCA_REFERTI_EMESSI: ricerca i campioni che si trovano nello
   * stato "Referto da emettere"
   * @param anno: Anno di riferimento del campione
   * @param numCampioneDa: numero progressivo (attribuito automaticamente al
   * campione) minimo che deve avere il campione per soddisfare la ricerca
   * @param numCampioneA: numero progressivo (attribuito automaticamente al
   * campione) massimo che deve avere il campione per soddisfare la ricerca
   * @param idRichiestaDa: identificativo (univoco del campione) minimo che deve
   * avere il campione per soddisfare la ricerca
   * @param idRichiestaA: identificativo (univoco del campione) minimo che deve
   * avere il campione per soddisfare la ricerca
   * @param dataDa: data (in cui è stata inserita la richeista) minima che deve
   * avere il campione per soddisfare la ricerca
   * @param dataA: data (in cui è stata inserita la richeista) massima che deve
   * avere il campione per soddisfare la ricerca
   * @param cognomeProprietario: contiene i caratteri (almeno 2) che devono
   * essere contenuti nel cognome del proprietario del campione perché il
   * campione soddisfi la ricerca
   * @param nomeProprietario:contiene i caratteri che devono
   * essere contenuti nel nome del proprietario del campione perché il
   * campione soddisfi la ricerca
   * @param cognomeTecnico:contiene i caratteri (almeno 2) che devono
   * essere contenuti nel cognome del tecnico del campione perché il
   * campione soddisfi la ricerca
   * @param nomeTecnico:contiene i caratteri che devono
   * essere contenuti nel nome del tecnico del campione perché il
   * campione soddisfi la ricerca
   * @param tipoMateriale: materiale di cui è composto il campione
   * @param etichetta: contiene i caratteri (almeno 4) che devono
   * essere contenuti nell'etichetta del campione perché il
   * campione soddisfi la ricerca
   * @param labConsegna: codice del laboratorio di consegna
   * @param stato: codice dello stato della richiesta (Bozza, Analisi richiesta,
   * ecc...)
   * @param statoPagamento: codice dello stato di pagamento relativo al campione
   * (pagata,da pagare,gratuita)
   * @param organizzazione: selezione tutte le etichette inserite da tecnici
   * che appartengono a questa organizzazione
   * @param labAnalisi: codice del laboratorio di analisi
   * @param analisiFattura: serve per sapere se la fattura è richiesta, e se si
   * se è stata emessa
   * @param spedizioneFattura: serve per sapere se la fattura è da spedire o
   * o consegnare a mano
   * @param tutti: se vale true lascai inalterato il filtro, se vale false
   * aggiunge una condizione al filtro che è diversa a seconda del campione
   * @param filtroBreve: indica se il filtro da utilizzare va applicato solo
   * a ualche parametro
   */
  private String impostaCriteriRicerca(int ricerca,
                                       String anno,
                                       String numCampioneDa,
                                       String numCampioneA,
                                       String idRichiestaDa,
                                       String idRichiestaA,
                                       String dataDa,
                                       String dataA,
                                       String dataDaPag,
                                       String dataAPag,
                                       String dataEmissioneRefertoDa,
                                       String dataEmissioneRefertoA,
                                       String cognomeProprietario,
                                       String nomeProprietario,
                                       String cognomeTecnico,
                                       String nomeTecnico,
                                       String tipoMateriale,
                                       String etichetta,
                                       String labConsegna,
                                       String stato,
                                       String statoPagamento,
                                       String organizzazione,
                                       String labAnalisi,
                                       String analisiFattura,
                                       String spedizioneFattura,
                                       boolean tutti,
                                       boolean filtroBreve)
  {
    StringBuffer filtro=new StringBuffer(" ");
    if (anno!=null)
      filtro.append(" AND EC.ANNO = ").append(anno);
    if (numCampioneDa!=null)
    {
      if (filtroBreve)
        filtro.append(" AND EC.NUMERO_CAMPIONE = ").append(numCampioneDa);
      else
        filtro.append(" AND EC.NUMERO_CAMPIONE >= ").append(numCampioneDa);
    }
    if (numCampioneA!=null)
      filtro.append(" AND EC.NUMERO_CAMPIONE <= ").append(numCampioneA);
    if (idRichiestaDa!=null)
    {
      if (filtroBreve)
        filtro.append(" AND EC.ID_RICHIESTA = ").append(idRichiestaDa);
      else
        filtro.append(" AND EC.ID_RICHIESTA >= ").append(idRichiestaDa);
    }
    if (idRichiestaA!=null)
      filtro.append(" AND EC.ID_RICHIESTA <= ").append(idRichiestaA);
    
    if (dataDa!=null)
    {
      filtro.append(" AND EC.DATA_INSERIMENTO_RICHIESTA >= to_timestamp('");
      filtro.append(dataDa).append("','DD/MM/YYYY') ");
    }
    if (dataA!=null)
    {
      filtro.append(" AND EC.DATA_INSERIMENTO_RICHIESTA <= to_timestamp('");
      filtro.append(dataA).append("','DD/MM/YYYY') ");
    }
    
    if (dataDaPag!=null)
    {
    	filtro.append(" AND EC.DATA_INCASSO >= to_timestamp('");
    	filtro.append(dataDaPag).append("','DD/MM/YYYY') ");
    }
    if (dataAPag!=null)
    {
    	filtro.append(" AND EC.DATA_INCASSO <= to_timestamp('");
    	filtro.append(dataAPag).append("','DD/MM/YYYY') ");
    }
    
    if (tipoMateriale!=null)
    {
      filtro.append(" AND EC.CODICE_MATERIALE = '").append(tipoMateriale);
      filtro.append("' ");
    }
    if (etichetta!=null)
    {
      filtro.append(" AND UPPER(EC.DESCRIZIONE_ETICHETTA) LIKE UPPER('%");
      filtro.append(Utili.toVarchar(etichetta.trim())).append("%') ");
    }
    if (labConsegna!=null)
    {
      filtro.append(" AND EC.LABORATORIO_CONSEGNA ='");
      filtro.append(labConsegna).append("' ");
    }
    if (stato!=null)
    {
      filtro.append(" AND EC.STATO_ATTUALE ='");
      filtro.append(stato).append("' ");
    }
    if (statoPagamento!=null)
    {
      filtro.append(" AND EC.PAGAMENTO ='");
      filtro.append(statoPagamento).append("' ");
    }
    if (cognomeProprietario!=null)
    {
      filtro.append(" AND UPPER(A.COGNOME_RAGIONE_SOCIALE) LIKE UPPER('%");
      filtro.append(Utili.toVarchar(cognomeProprietario.trim())).append("%') ");
    }
    if (nomeProprietario!=null)
    {
      filtro.append(" AND UPPER(A.NOME) LIKE UPPER('%");
      filtro.append(Utili.toVarchar(nomeProprietario.trim())).append("%') ");
    }
    if (cognomeTecnico!=null)
    {
      filtro.append(" AND UPPER(A.COGNOME_RAGIONE_SOCIALE) LIKE UPPER('%");
      filtro.append(Utili.toVarchar(cognomeTecnico.trim())).append("%') ");
    }
    if (nomeTecnico!=null)
    {
      filtro.append(" AND UPPER(A.NOME) LIKE UPPER('%");
      filtro.append(Utili.toVarchar(nomeTecnico.trim())).append("%') ");
    }

    if (nomeTecnico!=null || cognomeTecnico!=null || nomeProprietario!=null ||
             cognomeProprietario!=null)
    {
      filtro.setCharAt(0,'1');
      //filtro.append(" AND A.ID_ANAGRAFICA=COALESCE(EC.ANAGRAFICA_UTENTE ");
      filtro.append(" AND (  A.ID_ANAGRAFICA=EC.ANAGRAFICA_UTENTE ");
      
      if (nomeTecnico!=null || cognomeTecnico!=null)
    	  filtro.append(" OR  A.ID_ANAGRAFICA = EC.ANAGRAFICA_TECNICO ");
      if (nomeProprietario!=null || cognomeProprietario!=null)
    	  filtro.append(" OR  A.ID_ANAGRAFICA = EC.ANAGRAFICA_PROPRIETARIO ");
      filtro.append(") ");
      
      
    }
    if (organizzazione!=null && !"-1".equals(organizzazione)) {
      filtro.setCharAt(0,'1');
      filtro.append(" AND A.ID_ORGANIZZAZIONE=").append(organizzazione);
      filtro.append(" AND ( A.ID_ANAGRAFICA= EC.ANAGRAFICA_TECNICO OR  A.ID_ANAGRAFICA=EC.ANAGRAFICA_UTENTE) ") ;
    }
    if (labAnalisi!=null)
    {
      filtro.append(" AND EC.LABORATORIO_ANALISI ='");
      filtro.append(labAnalisi).append("' ");
    }
    if (analisiFattura!=null)
    {
      if ("N".equals(analisiFattura))
        filtro.append(" AND DF.FATTURA_SN ='N' ");
      if ("D".equals(analisiFattura))
        filtro.append(" AND DF.FATTURA_SN ='S' AND EC.ID_RICHIESTA NOT IN (SELECT ID_RICHIESTA FROM CAMPIONE_FATTURATO WHERE ID_RICHIESTA IS NOT NULL) ");
      if ("E".equals(analisiFattura))
        filtro.append(" AND DF.FATTURA_SN ='S' AND EC.ID_RICHIESTA IN (SELECT ID_RICHIESTA FROM CAMPIONE_FATTURATO WHERE ID_RICHIESTA IS NOT NULL) ");
    }
    if (spedizioneFattura!=null)
    {
      filtro.append(" AND DF.SPEDIZIONE ='");
      filtro.append(spedizioneFattura).append("' ");
    }
    if (!tutti)
    {
      switch (ricerca)
      {
        case RICERCA_CAMPIONI:
                              break;
        case RICERCA_ANALISI_RICHIESTE:filtro.append("AND EC.STATO_ANOMALIA='B' ");
                                       break;
        case RICERCA_CAMPIONI_LABORATORIO:
                                          break;
        case RICERCA_REFERTI_DA_EMETTERE:filtro.append("AND EC.STATO_ANOMALIA='B' ");
                                         break;
        case RICERCA_REFERTI_EMESSI:
                                    break;
      }
    }
    filtro.append(" ");
    return filtro.toString();
  }


  /**
   * Questo metodo viene chiamato per eseguire una ricerca delle anagrafiche
   * @param tipoUtente: tecnico, privato, LAR
   * @param tipoOrganizzazione:  Organizzazione professionale, Liberi
   * professionisti, Università ecc.
   * @param organizzazione: organizzazione a cui appartiene l'utente
   * @param ragSociale: ragione sociale o cognome dell'utente
   * @param codFoPIAVA: codice fiscale o partita iva dell'utente
   */
  private String impostaCriteriRicerca(String tipoUtente,
                                       String tipoOrganizzazione,
                                       String organizzazione,
                                       String ragSociale,
                                       String codFoPIAVA
                                       )
  {
    StringBuffer filtro=new StringBuffer(" ");
    filtro.append(" ");
    if (tipoUtente!=null)
    {
      filtro.append(" AND A.TIPO_UTENTE = '");
      filtro.append(tipoUtente).append("'");
    }
    if (tipoOrganizzazione!=null)
    {
      filtro.append(" AND A.ID_ORGANIZZAZIONE=OP.ID_ORGANIZZAZIONE");
      filtro.append(" AND OP.ID_TIPO_ORGANIZZAZIONE = ");
      filtro.append(tipoOrganizzazione);
    }
    if (organizzazione!=null)
    {
      filtro.append(" AND A.ID_ORGANIZZAZIONE = ");
      filtro.append(organizzazione);
    }
    if (ragSociale!=null)
    {
      filtro.append(" AND UPPER(A.COGNOME_RAGIONE_SOCIALE) LIKE UPPER('%");
      filtro.append(Utili.toVarchar(ragSociale.trim())).append("%') ");
    }
    if (codFoPIAVA!=null)
    {
      filtro.append(" AND A.CODICE_IDENTIFICATIVO = '");
      filtro.append(codFoPIAVA).append("'");
    }

/*
    if (tipoUtente!=null)
    {
      filtro.append(" AND UPPER(A.COGNOME_RAGIONE_SOCIALE) LIKE UPPER('%");
      filtro.append(Utili.toVarchar(tipoUtente.trim())).append("%') ");
    }*/

    return filtro.toString();
  }

  /**
   * Questo metodo viene chiamato per eseguire una ricerca delle organizzazioni
   * @param tipoOrganizzazione:  Organizzazione professionale, Liberi
   * professionisti, Università ecc.
   * @param ragSociale: ragione sociale o cognome dell'utente
   * @param istat: istat del comune sede dell'organizzazione
   */
  private String impostaCriteriRicerca(String tipoOrganizzazione,
                                       String ragSociale,
                                       String istat)
  {
    StringBuffer filtro=new StringBuffer(" ");
    if (tipoOrganizzazione!=null && Utili.isNumber(tipoOrganizzazione))
    {
      filtro.append(" AND OP.ID_TIPO_ORGANIZZAZIONE = ");
      filtro.append(tipoOrganizzazione);
    }
    if (ragSociale!=null)
    {
      filtro.append(" AND UPPER(OP.RAGIONE_SOCIALE) LIKE UPPER('%");
      filtro.append(Utili.toVarchar(Utili.validazioneSQLParam(ragSociale).trim())).append("%') ");
    }
    if (istat!=null)
    {
      filtro.append(" AND OP.COMUNE_RESIDENZA = '");
      filtro.append(Utili.validazioneSQLParam(istat)).append("'");
    }
    filtro.append(" ");
    return filtro.toString();
  }


  /**
   * Questo metodo viene chiamato per eseguire una ricerca delle anagrafiche
   * @param annoFattura: anno di rilascio della fattura
   * @param numeroFattura: numero della fattura
   * @param dataDa: data (di emissione) minima che deve avere la fattura
   * per soddisfare la ricerca
   * @param dataA: data (di emissione) massima che deve avere la fattura
   * per soddisfare la ricerca
   * @param numeroRichiesta: numero della richiesta
   * @param annoCampione: anno del campione
   * @param numeroCampione: numero del campione
   * @param codFisPIVA:codice fiscale o partita iva della tabella fattura
   * @param statoPagamento: indica se la fattura è stata pagata (S), se  è
   * stata pagata parzialmente (P) o se è ancora da pagare (N)
   * @param statoFattura: indica se la fattura è stata annullata (S)
   */
  private String impostaCriteriRicerca(String annoFattura,
                                        String numeroFattura,
                                        String dataDa,
                                        String dataA,
                                        String numeroRichiesta,
                                        String annoCampione,
                                        String numeroCampione,
                                        String codFisPIVA,
                                        String statoPagamento,
                                        String statoFattura
                                       )
  {
    StringBuffer filtro=new StringBuffer(" ");
    if (annoFattura!=null)
    {
      filtro.append(" AND F.ANNO = ");
      filtro.append(annoFattura);
    }
    if (numeroFattura!=null)
    {
      filtro.append(" AND F.NUMERO_FATTURA = ");
      filtro.append(numeroFattura);
    }
    if (dataDa!=null)
    {
      filtro.append(" AND F.DATA_FATTURA >= to_timestamp('");
      filtro.append(dataDa).append("','DD/MM/YYYY') ");
    }
    if (dataA!=null)
    {
      filtro.append(" AND F.DATA_FATTURA <= to_timestamp('");
      filtro.append(dataA).append("','DD/MM/YYYY') ");
    }
    if (numeroRichiesta!=null)
    {
      filtro.append(" AND CF.ID_RICHIESTA = ");
      filtro.append(numeroRichiesta);
    }
    if (annoCampione!=null)
    {
      filtro.append(" AND E.ANNO = ");
      filtro.append(annoCampione);
    }
    if (numeroCampione!=null)
    {
      filtro.append(" AND E.NUMERO_CAMPIONE = ");
      filtro.append(numeroCampione);
    }
    if (codFisPIVA!=null)
    {
      filtro.append(" AND F.PARTITA_IVA_O_CF = '");
      filtro.append(codFisPIVA).append("'");
    }
    if ("S".equals(statoPagamento))
    {
      filtro.append(" AND F.PAGATA = 'S'");
    }
    if ("N".equals(statoPagamento))
    {
      filtro.append(" AND (F.PAGATA = 'N' OR F.PAGATA = 'P')");
    }
    if ("S".equals(statoFattura))
    {
      filtro.append(" AND F.ANNULLATA = 'S'");
    }
    if ("N".equals(statoFattura))
    {
      filtro.append(" AND F.ANNULLATA IS NULL");
    }
    filtro.append(" ");
    return filtro.toString();
  }

  /**
   * Questo metodo viene chiamato per eseguire una ricerca delle anagrafiche
   * @param dataDa: data (di emissione) minima che deve avere la fattura
   * per soddisfare la ricerca
   * @param dataA: data (di emissione) massima che deve avere la fattura
   * per soddisfare la ricerca
   * @param annoFatturaDa: anno di rilascio della fattura
   * @param numeroFatturaDa: numero della fattura
   * @param annoFatturaA: anno di rilascio della fattura
   * @param numeroFatturaA: numero della fattura
   * @param codFisPIVA:codice fiscale o partita iva della tabella fattura
   * @param statoPagamento: indica se la fattura è stata pagata (S), se  è
   * stata pagata parzialmente (P) o se è ancora da pagare (N)
   * @param statoFattura: indica se la fattura è stata annullata (S)
   */
  private String impostaCriteriRicerca( String dataDa,
                                        String dataA,
                                        String annoFatturaDa,
                                        String numeroFatturaDa,
                                        String annoFatturaA,
                                        String numeroFatturaA,
                                        String codFisPIVA,
                                        String statoPagamento,
                                        String statoFattura
                                       )
  {
    StringBuffer filtro=new StringBuffer();
    if (dataDa!=null)
    {
      filtro.append(" AND F.DATA_FATTURA >= to_timestamp('");
      filtro.append(dataDa).append("','DD/MM/YYYY') ");
    }
    if (dataA!=null)
    {
      filtro.append(" AND F.DATA_FATTURA <= to_timestamp('");
      filtro.append(dataA).append("','DD/MM/YYYY') ");
    }
    if (annoFatturaDa!=null && numeroFatturaDa!=null &&
        annoFatturaA!=null && numeroFatturaA!=null)
    {

      if (annoFatturaA.equals(annoFatturaDa))
      {
        filtro.append(" AND (");
        filtro.append(" F.ANNO = ");
        filtro.append(annoFatturaDa);
        filtro.append(" AND F.NUMERO_FATTURA >= ");
        filtro.append(numeroFatturaDa);
        filtro.append(" AND F.NUMERO_FATTURA <= ");
        filtro.append(numeroFatturaA).append(") ");
      }
      else
      {
        int annoDa = Integer.parseInt(annoFatturaDa) + 1;
        int annoA = Integer.parseInt(annoFatturaA) - 1;

        filtro.append(" AND (");
        filtro.append(" (F.ANNO = ");
        filtro.append(annoFatturaDa);
        filtro.append(" AND F.NUMERO_FATTURA >= ");
        filtro.append(numeroFatturaDa);
        filtro.append(") OR ( F.ANNO = ");
        filtro.append(annoFatturaA);
        filtro.append(" AND F.NUMERO_FATTURA <= ");
        filtro.append(numeroFatturaA);
        filtro.append(") OR F.ANNO BETWEEN ");
        filtro.append(annoDa).append(" AND ").append(annoA).append(") ");
      }
    }
    else
    {
      if (annoFatturaDa != null && numeroFatturaDa != null)
      {
        filtro.append(" AND F.ANNO >= ");
        filtro.append(annoFatturaDa);
        filtro.append(" AND F.NUMERO_FATTURA >= ");
        filtro.append(numeroFatturaDa);
      }
      if (annoFatturaA != null && numeroFatturaA != null)
      {
        filtro.append(" AND F.ANNO <= ");
        filtro.append(annoFatturaA);
        filtro.append(" AND F.NUMERO_FATTURA <= ");
        filtro.append(numeroFatturaA);
      }
    }
    if (codFisPIVA!=null)
    {
      filtro.append(" AND F.PARTITA_IVA_O_CF = '");
      filtro.append(codFisPIVA).append("'");
    }
    if ("S".equals(statoPagamento))
    {
      filtro.append(" AND F.PAGATA = 'S'");
    }
    if ("N".equals(statoPagamento))
    {
      filtro.append(" AND (F.PAGATA = 'N' OR F.PAGATA = 'P')");
    }
    if ("S".equals(statoFattura))
    {
      filtro.append(" AND F.ANNULLATA = 'S'");
    }
    if ("N".equals(statoFattura))
    {
      filtro.append(" AND F.ANNULLATA IS NULL");
    }
    filtro.append(" ");
    return filtro.toString();
  }

  public void setNome(String nome) {
    this.nome = nome;
  }
  public String getNome() {
    return nome;
  }
  public void setCognome(String cognome) {
    this.cognome = cognome;
  }
  public String getCognome() {
    return cognome;
  }
  public void setIdRichiestaCorrente(long idRichiestaCorrente)
  {
    this.idRichiestaCorrente = idRichiestaCorrente;
  }
  public long getIdRichiestaCorrente()
  {
    return idRichiestaCorrente;
  }
  public void setCodMateriale(String codMateriale)
  {
    this.codMateriale = codMateriale;
  }
  public String getCodMateriale()
  {
    return codMateriale;
  }
  public void setCoordinateGeografiche(boolean coordinateGeografiche)
  {
    this.coordinateGeografiche = coordinateGeografiche;
  }
  public boolean isCoordinateGeografiche()
  {
    return coordinateGeografiche;
  }
  public void setIdRichieste(String idRichieste)
  {
    this.idRichieste = idRichieste;
  }
  public String getIdRichieste()
  {
    return idRichieste;
  }
  public void setResponsabile(Responsabile responsabile)
  {
    this.responsabile = responsabile;
  }
  public Responsabile getResponsabile()
  {
    return responsabile;
  }

  public void setQueryRicerca(String queryRicerca)
  {
    this.queryRicerca = queryRicerca;
  }
  public String getQueryRicerca()
  {
    return queryRicerca;
  }
  public void setQueryCountRicerca(String queryCountRicerca)
  {
    this.queryCountRicerca = queryCountRicerca;
  }
  public String getQueryCountRicerca()
  {
    return queryCountRicerca;
  }

public String getIdRichiestaChecked() {
	return idRichiestaChecked;
}

public void setIdRichiestaChecked(String idRichiestaChecked) {
	this.idRichiestaChecked = idRichiestaChecked;
}

}
