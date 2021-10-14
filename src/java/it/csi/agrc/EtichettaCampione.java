package it.csi.agrc;

import it.csi.cuneo.BeanDataSource;
import it.csi.cuneo.Constants;
import it.csi.cuneo.CuneoLogger;
import it.csi.cuneo.Utili;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import it.csi.jsf.web.pool.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

//import java.awt.*;
import javax.swing.ImageIcon;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Title:        Agrichim - Front Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class EtichettaCampione extends BeanDataSource implements Serializable{
  private static final long serialVersionUID = 3079455905144093134L;
  private final String this_class = "[EtichettaCampione::";
  private String idRichiesta;
  private String dataInserimentoRichiesta;
  private String codiceMateriale;
  private String descMateriale;
  private String descrizioneEtichetta;
  private String descStatoAttuale;
  private String proprietario;
  private String richiedente;
  private String statoAnomalia;
  private String numeroCampione;
  private String annoCampione;
  private String statoAttuale;
  private String note;
  private String descLabConsegna;
  private String pagamento;
  private String codLabConsegna;
  private String codLabAnalisi;
  private String descComune;
  private String descSpecieColtura;
  private String descLabAnalisi;
  private String costo;
  private String fatturareA;

  private String anagraficaUtente;
  private String anagraficaProprietario;
  private String anagraficaTecnico;
  private String anagraficaRichiedente;
  
  private String dataAccettazioneCampione;
  
  private String data_incasso;
  private String iuv;
  private String cf;
  private String importo;
  private String messaggioErrore;
  private String tipoPagamento;
  private String dataAnnullamento;
private Date dataPagamento;
  

  public static final int UTENTE = 0;
  public static final int TECNICO = 1;
  public static final int PROPRIETARIO = 2;
  public static final int MULTIPLO_ANALISI_RICHIESTE=3;
  public static final int MULTIPLO_CAMPIONI_LABORATORIO=4;


  public EtichettaCampione()
  {
  }

  public EtichettaCampione(Object dataSource, Autenticazione aut)
  {
    this.setDataSource(dataSource);
    this.setAut(aut);
  }



  public EtichettaCampione(String idRichiesta,
                           String dataInserimentoRichiesta,
                           String codiceMateriale,
                           String descMateriale,
                           String descrizioneEtichetta,
                           String descStatoAttuale,
                           String proprietario,
                           String richiedente,
                           String numeroCampione,
                           String annoCampione,
                           String statoAttuale,
                           String note,
                           String pagamento,
                           String tipo_pagamento,
                           String iuv,
                           String data_annullamento)
  {
    this.idRichiesta =idRichiesta;
    this.dataInserimentoRichiesta =dataInserimentoRichiesta;
    this.codiceMateriale =codiceMateriale;
    this.descMateriale =descMateriale;
    this.descrizioneEtichetta = descrizioneEtichetta;
    this.descStatoAttuale =descStatoAttuale;
    this.proprietario=proprietario;
    this.richiedente=richiedente;
    this.numeroCampione=numeroCampione;
    this.annoCampione=annoCampione;
    this.statoAttuale=statoAttuale;
    this.note=note;
    this.iuv=iuv;
    this.pagamento=pagamento;
    this.tipoPagamento=tipo_pagamento;
    this.dataAnnullamento = data_annullamento;
  }
  public EtichettaCampione(String idRichiesta,
		  String dataInserimentoRichiesta,
		  String codiceMateriale,
		  String descMateriale,
		  String descrizioneEtichetta,
		  String descStatoAttuale,
		  String proprietario,
		  String richiedente,
		  String numeroCampione,
		  String annoCampione,
		  String statoAttuale,
		  String note)
  {
	  this.idRichiesta =idRichiesta;
	  this.dataInserimentoRichiesta =dataInserimentoRichiesta;
	  this.codiceMateriale =codiceMateriale;
	  this.descMateriale =descMateriale;
	  this.descrizioneEtichetta = descrizioneEtichetta;
	  this.descStatoAttuale =descStatoAttuale;
	  this.proprietario=proprietario;
	  this.richiedente=richiedente;
	  this.numeroCampione=numeroCampione;
	  this.annoCampione=annoCampione;
	  this.statoAttuale=statoAttuale;
	  this.note=note;
  }

  public EtichettaCampione(String idRichiesta,
                           String codiceMateriale,
                           String descMateriale,
                           String descrizioneEtichetta,
                           String descLabConsegna,
                           String pagamento,
                           String statoAnomalia,
                           String codLabAnalisi,
                           String codLabConsegna,
                           String iuv,
                           String note,
                           String dataIncasso,
                           int x)
  {
    this.idRichiesta =idRichiesta;
    this.codiceMateriale =codiceMateriale;
    this.descMateriale =descMateriale;
    this.descrizioneEtichetta = descrizioneEtichetta;
    this.descLabConsegna=descLabConsegna;
    this.pagamento=pagamento;
    this.statoAnomalia=statoAnomalia;
    this.codLabAnalisi=codLabAnalisi;
    this.codLabConsegna=codLabConsegna;
    this.iuv=iuv;
    this.note=note;
    this.data_incasso=dataIncasso; 
  }

  public EtichettaCampione(String idRichiesta,
                           String annoCampione,
                           String numeroCampione)
  {
    this.idRichiesta =idRichiesta;
    this.annoCampione=annoCampione;
    this.numeroCampione=numeroCampione;
  }

  public EtichettaCampione(String idRichiesta,
                           String descMateriale,
                           String descrizioneEtichetta,
                           String proprietario,
                           String pagamento,
                           String costo,
                           String fatturareA
                           )
  {
    this.idRichiesta =idRichiesta;
    this.descMateriale =descMateriale;
    this.descrizioneEtichetta = descrizioneEtichetta;
    this.proprietario=proprietario;
    this.pagamento=pagamento;
    this.costo=costo;
    this.fatturareA=fatturareA;
  }



  public void select(String idRichiesta)
  throws Exception, SQLException
  {
    Anagrafiche anagrafiche=new Anagrafiche();
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    String proprietario;
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("SELECT EC.ID_RICHIESTA,");
      query.append("M.DESCRIZIONE AS MATERIALE,");
      query.append("EC.DESCRIZIONE_ETICHETTA,");
      query.append("EC.ANAGRAFICA_PROPRIETARIO, ");
      query.append("EC.ANAGRAFICA_UTENTE, ");
      query.append("TO_CHAR(EC.DATA_INSERIMENTO_RICHIESTA,'DD/MM/YYYY') AS DATA_INSERIMENTO_RICHIESTA, ");
      
      query.append("EC.LABORATORIO_ANALISI, ");
      query.append("EC.ANNO, ");
      query.append("EC.NUMERO_CAMPIONE, ");
      query.append("EC.STATO_ANOMALIA, ");
      query.append("TO_CHAR(EC.DATA_INCASSO,'DD/MM/YYYY') AS DATA_INCASSO ");
      query.append("FROM ETICHETTA_CAMPIONE EC, MATERIALE M ");
      query.append("WHERE EC.ID_RICHIESTA = ");
      query.append(idRichiesta);
      query.append(" AND EC.CODICE_MATERIALE= M.CODICE_MATERIALE ");
      //CuneoLogger.debug(this, query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        String anagraficaProprietario=rset.getString("ANAGRAFICA_PROPRIETARIO");
        String anagraficaUtente=rset.getString("ANAGRAFICA_UTENTE");
        if (anagraficaProprietario==null)
        {
          proprietario=anagrafiche.getNomeCognome(
                     conn,
                     anagraficaUtente
                                                );
        }
        else
        {
          proprietario=anagrafiche.getNomeCognome(
              conn,
              anagraficaProprietario
              );
        }
        this.setIdRichiesta(rset.getString("ID_RICHIESTA"));
        this.setDescMateriale(rset.getString("MATERIALE"));
        this.setDescrizioneEtichetta(
             rset.getString("DESCRIZIONE_ETICHETTA"));
        this.setProprietario(proprietario);
        this.setCodLabAnalisi(rset.getString("LABORATORIO_ANALISI"));
        this.setAnnoCampione(rset.getString("ANNO"));
        this.setNumeroCampione(rset.getString("NUMERO_CAMPIONE"));
        this.setStatoAnomalia(rset.getString("STATO_ANOMALIA"));
        this.setData_incasso(rset.getString("DATA_INCASSO"));
        this.setDataInserimentoRichiesta(rset.getString("DATA_INSERIMENTO_RICHIESTA"));
      }
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe EtichettaCampione"
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
   * Questo metodo viene utilizzato in campioni laboratorio per popolare la
   * riga del campione con idRichiesta uguale a quella che si trova
   * nel bean di autenticazione
   * @throws Exception
   * @throws SQLException
   */
  public void selectForCampioniLab()
  throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    //String proprietario;
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("SELECT EC.ID_RICHIESTA, M.DESCRIZIONE AS DESC_MATERIALE, EC.DESCRIZIONE_ETICHETTA, ");
      query.append(" L.DESCRIZIONE AS DESC_LAB, CT.DESCRIZIONE STATO_ATTUALE, EC.CODICE_MATERIALE ");
      query.append(" FROM ETICHETTA_CAMPIONE EC LEFT OUTER JOIN LABORATORIO L ON EC.LABORATORIO_ANALISI = L.CODICE_LABORATORIO, ");
      query.append(" MATERIALE M, TRACCIATURA T, CODIFICA_TRACCIABILITA CT ");
      query.append(" WHERE EC.ID_RICHIESTA = ");
      query.append(getAut().getIdRichiestaCorrente());
      query.append(" AND EC.CODICE_MATERIALE = M.CODICE_MATERIALE ");
      query.append(" AND EC.ID_RICHIESTA = T.ID_RICHIESTA ");
      query.append(" AND T.CODICE = CT.CODICE ");
      query.append(" ORDER BY DATA DESC ");//I record sono ordinati per data decrescente, leggendo solo il primo, si ottiene lo stato attuale della richiesta
      
      //CuneoLogger.debug(this, query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        setIdRichiesta(rset.getString("ID_RICHIESTA"));
        setDescMateriale(rset.getString("DESC_MATERIALE"));
        setDescrizioneEtichetta(rset.getString("DESCRIZIONE_ETICHETTA"));
        setDescLabAnalisi(rset.getString("DESC_LAB"));
        setStatoAttuale(rset.getString("STATO_ATTUALE"));
        setCodiceMateriale(rset.getString("CODICE_MATERIALE"));
      }
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("selectForCampioniLab della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("selectForCampioniLab della classe EtichettaCampione"
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
   * Restituisce un vettore composto da 3 stringhe:
   * - anagrafica utente
   * - anagrafica tecnico
   * - anagrafica proprietario
   * */
  public String[] selectUtenti(long idRichiesta)
  throws Exception, SQLException
  {
    String[] utenti=new String[3];
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("SELECT ANAGRAFICA_PROPRIETARIO,");
      query.append("ANAGRAFICA_TECNICO,ANAGRAFICA_UTENTE, PAGAMENTO ");
      query.append("FROM ETICHETTA_CAMPIONE ");
      query.append("WHERE ID_RICHIESTA = ");
      query.append(idRichiesta);
      CuneoLogger.debug(this, query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next()) {
        utenti[EtichettaCampione.PROPRIETARIO]=rset.getString("ANAGRAFICA_PROPRIETARIO");
        utenti[EtichettaCampione.TECNICO]=rset.getString("ANAGRAFICA_TECNICO");
        utenti[EtichettaCampione.UTENTE]=rset.getString("ANAGRAFICA_UTENTE");
        this.setPagamento(rset.getString("PAGAMENTO"));
      }
      rset.close();
      stmt.close();
      return utenti;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("selectUtenti della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("selectUtenti della classe EtichettaCampione"
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
   *
   * @param idRichiesta contiene le idRichiesta (separate da una virgola) che
   * devo controllare
   * @param scelta può assumere i seguenti valori:
   * - MULTIPLO_ANALISI_RICHIESTE: campioni con stato analisi richiesta
   * - MULTIPLO_CAMPIONI_LABORATORIO: campioni con stato campioni in laboratorio
   * @return restituisce true se i campioni hanno soddisfatto i parametri di
   * uguaglianza, false altrimenti
   * @throws Exception
   * @throws SQLException
   */
  public boolean controllaAccettazioneScartoMultiplo(String idRichiesta,int scelta) throws Exception, SQLException{
  String this_method = this_class+"::controllaAccettazioneScartoMultiplo] ";
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    Statement stmt=null;
    StringBuffer query=new StringBuffer("");
    try{
      conn=getConnection();
      stmt = conn.createStatement();
      ResultSet rset;
      int num=0;
      switch(scelta){
        case EtichettaCampione.MULTIPLO_ANALISI_RICHIESTE:
          boolean unico=true;
          query = new StringBuffer("SELECT DISTINCT ANAGRAFICA_TECNICO,");
          query.append("ANAGRAFICA_UTENTE,LABORATORIO_CONSEGNA,STATO_ANOMALIA,");
          query.append("PAGAMENTO, (with elenco_iuv as ( " + 
          		"select distinct FIRST_VALUE(iuv) OVER(PARTITION BY ID_RICHIESTA " + 
          		"        ORDER BY data_annullamento desc) iuv,ID_RICHIESTA from PAGAMENTI) " + 
          		"select case when p.data_annullamento is null then p.iuv else '' end iuv " + 
          		"from pagamenti p " + 
          		"  JOIN elenco_iuv ei ON p.id_richiesta = ei.id_richiesta and p.iuv = ei.iuv " + 
          		"where p.id_richiesta = ec.id_richiesta) FROM ETICHETTA_CAMPIONE ec ");
          query.append("WHERE ID_RICHIESTA IN (");
          query.append(idRichiesta).append(")");
          CuneoLogger.debug(this_method, query.toString());
          rset = stmt.executeQuery(query.toString());
          while (rset.next() && unico){
            num++;
            if (num>1) unico=false;
            if (rset.getString("STATO_ANOMALIA")!=null) return false;
          }
          rset.close();
          if (!unico){
        	CuneoLogger.debug(this_method, "unico = false");
            unico=true;
            query.setLength(0);
            query.append("SELECT DISTINCT ANAGRAFICA_TECNICO,");
            query.append("LABORATORIO_CONSEGNA,STATO_ANOMALIA,");
            query.append("PAGAMENTO, (with elenco_iuv as ( " + 
            		"select distinct FIRST_VALUE(iuv) OVER( " + 
            		"      PARTITION BY ID_RICHIESTA ORDER BY data_annullamento desc) iuv,ID_RICHIESTA " + 
            		" from PAGAMENTI) " + 
            		"select case when p.data_annullamento is null then p.iuv else '' end iuv " + 
            		"from pagamenti p " + 
            		"  JOIN elenco_iuv ei ON p.id_richiesta = ei.id_richiesta and p.iuv = ei.iuv " + 
            		"where p.id_richiesta = ec.id_richiesta) FROM ETICHETTA_CAMPIONE ec ");
            query.append("WHERE ID_RICHIESTA IN (");
            query.append(idRichiesta).append(")");
            CuneoLogger.debug(this_method, query.toString());
            rset = stmt.executeQuery(query.toString());
            while (rset.next() && unico){
              num++;
              if (num>1) return false;
            }
            rset.close();
          }
        break;

        case EtichettaCampione.MULTIPLO_CAMPIONI_LABORATORIO:
          query = new StringBuffer("SELECT DISTINCT(CODICE_MATERIALE) ");
          query.append("FROM ETICHETTA_CAMPIONE ");
          query.append("WHERE ID_RICHIESTA IN (");
          query.append(idRichiesta).append(")");
          CuneoLogger.debug(this_method, query.toString());
          rset = stmt.executeQuery(query.toString());
          while (rset.next()){
            num++;
            if (num>1) return false;
          }
          rset.close();
        break;
      }
      return true;
    }catch(java.sql.SQLException ex){
      this.getAut().setQuery("controllaAccettazioneScartoMultiplo della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      CuneoLogger.error(this_method, ex);
      throw (ex);
    }catch(Exception e){
      this.getAut().setQuery("controllaAccettazioneScartoMultiplo della classe EtichettaCampione: non è una SQLException ma una Exception generica");
      this.getAut().setContenutoQuery(query.toString());
      CuneoLogger.error(this_method, e);
      throw (e);
    }finally{
      stmt.close();
      if (conn!=null) conn.close();
    }
  }

  public void verificaPagamento(String idRichiesta) throws Exception, SQLException{
	  String method = this_class+"::verificaPagamento] ";
	    if (!isConnection())
	    	throw new Exception("Riferimento a DataSource non inizializzato");
	    Connection conn=null;
	    StringBuffer query=new StringBuffer("");
	    try{
	      conn=getConnection();
	      Statement stmt = conn.createStatement();
	      query = new StringBuffer("SELECT P.ID_RICHIESTA as NUMERO_RICHIESTA,P.DATA_PAGAMENTO,TP.COD_TIPO_PAGAMENTO, ");
		  query.append("P.IUV, P.CF_PARTITA_IVA, SUM(AR.COSTO_ANALISI) as IMPORTO ");
		  query.append("FROM PAGAMENTI P ");
		  query.append(" join analisi_richieste AR on P.ID_RICHIESTA=AR.ID_RICHIESTA ");
		  query.append(" join tipo_pagamento TP on P.ID_TIPO_PAGAMENTO = TP.ID_TIPO_PAGAMENTO ");
		  query.append("WHERE 1=1 ");
	      if (idRichiesta != null && !idRichiesta.equals(""))
	    	  query.append("and P.ID_RICHIESTA = "+idRichiesta);
	      query.append(" and p.data_annullamento is null group by NUMERO_RICHIESTA,DATA_PAGAMENTO,COD_TIPO_PAGAMENTO,IUV,CF_PARTITA_IVA ");
	      CuneoLogger.debug(method,query.toString());
	      ResultSet rset = stmt.executeQuery(query.toString());
	      if (rset.next()){
	    	 this.setIdRichiesta(rset.getString("NUMERO_RICHIESTA"));
	    	 CuneoLogger.debug(method, this.getIdRichiesta());
	    	 this.setIuv(rset.getString("IUV"));
	    	 CuneoLogger.debug(method, this.getIuv());
	    	 this.setCf(rset.getString("CF_PARTITA_IVA"));
	    	 CuneoLogger.debug(method, this.getCf());
	    	 this.setImporto(rset.getString("IMPORTO"));
	    	 CuneoLogger.debug(method, this.getImporto());
	    	 this.setTipoPagamento(rset.getString("COD_TIPO_PAGAMENTO"));
	    	 CuneoLogger.debug(method, this.getTipoPagamento());
	      }
	      rset.close();
	      stmt.close();
	    }catch(java.sql.SQLException ex) {
	    	CuneoLogger.error(method,"SQLException - "+ex);
	    	this.getAut().setQuery("verificaPagamento della classe EtichettaCampione");
	    	this.getAut().setContenutoQuery(query.toString());
	    	throw (ex);
	    }catch(Exception e){
	    	CuneoLogger.error(method,"Exception - "+e);
	    	this.getAut().setQuery("verificaPagamento della classe EtichettaCampione"
	                            +": non è una SQLException ma una Exception"
	                            +" generica");
	    	this.getAut().setContenutoQuery(query.toString());
	    	throw (e);
	    }finally{
	    	if (conn!=null) conn.close();
	    }
  }
	    public EtichettaCampione ricevutaPagamento(String idRichiesta) throws Exception, SQLException{
	    	String method = this_class+"::ricevutaPagamento] ";
	    	if (!isConnection())
	    		throw new Exception("Riferimento a DataSource non inizializzato");
	    	Connection conn=null;
	    	StringBuffer query=new StringBuffer("");
	    	EtichettaCampione ec = new EtichettaCampione();
	    	try{
	    		conn=getConnection();
	    		Statement stmt = conn.createStatement();
	    		query = new StringBuffer("SELECT P.ID_RICHIESTA as NUMERO_RICHIESTA,P.DATA_PAGAMENTO,TP.COD_TIPO_PAGAMENTO, ");
	    		query.append("P.IUV, P.CF_PARTITA_IVA, SUM(AR.COSTO_ANALISI) as IMPORTO ");
	    		query.append("FROM PAGAMENTI P ");
	    		query.append(" join analisi_richieste AR on P.ID_RICHIESTA=AR.ID_RICHIESTA ");
	    		query.append(" join tipo_pagamento TP on P.ID_TIPO_PAGAMENTO = TP.ID_TIPO_PAGAMENTO ");
	    		query.append("WHERE 1=1 ");
	    		if (idRichiesta != null && !idRichiesta.equals(""))
	    			query.append("and P.ID_RICHIESTA = "+idRichiesta);
	    		query.append(" and p.data_annullamento is null group by NUMERO_RICHIESTA,DATA_PAGAMENTO,COD_TIPO_PAGAMENTO,IUV,CF_PARTITA_IVA ");
	    		CuneoLogger.debug(method,query.toString());
	    		ResultSet rset = stmt.executeQuery(query.toString());
	    		if (rset.next()){
	    			if(rset.getString("IUV")!=null && !rset.getString("IUV").equals("")) {
	    				if(rset.getString("DATA_PAGAMENTO")!=null && !rset.getString("DATA_PAGAMENTO").equals("")) {
	    					ec.setIdRichiesta(rset.getString("NUMERO_RICHIESTA"));
	    					CuneoLogger.debug(method, this.getIdRichiesta());
	    					ec.setIuv(rset.getString("IUV"));
	    					CuneoLogger.debug(method, this.getIuv());
	    					ec.setCf(rset.getString("CF_PARTITA_IVA"));
	    					CuneoLogger.debug(method, this.getCf());
	    					ec.setImporto(rset.getString("IMPORTO"));
	    					CuneoLogger.debug(method, this.getImporto());
	    					ec.setTipoPagamento(rset.getString("COD_TIPO_PAGAMENTO"));
	    					CuneoLogger.debug(method, this.getTipoPagamento());
	    				}else
	    					ec.setMessaggioErrore("Il pagamento e' in attesa, non e' ancora pervenuto; utilizza la funzione Verifica il Pagamento per aggiornare lo stato del pagamento");
	    			}else
	    				ec.setMessaggioErrore("Non ci sono transazioni di pagamento valide, utilizza la funzione Paga");
	    		}
	    		rset.close();
	    		stmt.close();
	    	}catch(java.sql.SQLException ex) {
	    		CuneoLogger.error(method,"SQLException - "+ex);
	    		this.getAut().setQuery("ricevutaPagamento della classe EtichettaCampione");
	    		this.getAut().setContenutoQuery(query.toString());
	    		throw (ex);
	    	}catch(Exception e){
	    		CuneoLogger.error(method,"Exception - "+e);
	    		this.getAut().setQuery("ricevutaPagamento della classe EtichettaCampione"
	    				+": non è una SQLException ma una Exception"
	    				+" generica");
	    		this.getAut().setContenutoQuery(query.toString());
	    		throw (e);
	    	}finally{
	    		if (conn!=null) conn.close();
	    		return ec;
	    	}
  }
  public boolean updateFlagPagamento(String idRichiesta, Date dataPagamento) throws Exception, SQLException {
	  String method = this_class+"::updateFlagPagamento] ";
	  if (!isConnection())
	    	throw new Exception("Riferimento a DataSource non inizializzato");
	    Connection conn=null;
	    StringBuffer query=new StringBuffer("");
	    int update_flag = 0;
	    boolean update_data = false;
	    try{
			conn=getConnection();
			Statement stmt = conn.createStatement();
			stmt = conn.createStatement();
			query = new StringBuffer("UPDATE ETICHETTA_CAMPIONE SET PAGAMENTO = 'S' WHERE ID_RICHIESTA = "+idRichiesta);
			CuneoLogger.debug(method,query.toString());
			update_flag = stmt.executeUpdate( query.toString());
			CuneoLogger.debug(method,"ESITO UPDATE ETICHETTA_CAMPIONE : "+(update_flag>0?"positivo":"negativo"));
			if(update_flag>0) {
				update_data = updateDataPagamento(idRichiesta,dataPagamento);
			}
			stmt.close();
	    }catch(java.sql.SQLException ex) {
	    	CuneoLogger.error(method,"SQLException - "+ex);
	    	this.getAut().setQuery("updateFlagPagamento della classe EtichettaCampione per id richiesta "+idRichiesta);
	    	this.getAut().setContenutoQuery(query.toString());
	    	throw (ex);
	    }catch(Exception e){
	    	CuneoLogger.error(method,"Exception - "+e);
	    	this.getAut().setQuery("updateFlagPagamento della classe EtichettaCampione per id richiesta "+idRichiesta
	                            +": non è una SQLException ma una Exception"
	                            +" generica");
	    	this.getAut().setContenutoQuery(query.toString());
	    	throw (e);
	    }finally{
	    	if (conn!=null) conn.close();
	    		return (update_flag>0 && update_data)?true:false;
	    }
  }
  
  public boolean updateDataPagamento(String idRichiesta, Date dataPagamento) throws Exception, SQLException {
	  String method = this_class+"::updateDataPagamento] ";
	  if (!isConnection())
	    	throw new Exception("Riferimento a DataSource non inizializzato");
	    Connection conn=null;
	    StringBuffer query=new StringBuffer("");
	    int update_data = 0;
	    PreparedStatement stmt = null;
	    try{
			conn=getConnection();
			//update sulla Pagamenti
			query = new StringBuffer("UPDATE PAGAMENTI SET DATA_PAGAMENTO = ? ");
			query.append("WHERE ID_RICHIESTA = ? and data_annullamento is null ");
			CuneoLogger.debug(method,query.toString());
			stmt = conn.prepareStatement(query.toString());
			Timestamp ts = null;
			if(dataPagamento==null) 
				ts = new Timestamp(new Date().getTime());
			else
				ts = new Timestamp(dataPagamento.getTime());
				
			stmt.setTimestamp(1, ts);
			stmt.setBigDecimal(2, new BigDecimal(idRichiesta));
			update_data = stmt.executeUpdate();
			CuneoLogger.debug(method,"ESITO UPDATE PAGAMENTI : "+(update_data>0?"positivo":"negativo"));
			
			//update sulla etichetta_campione
			query = new StringBuffer("UPDATE ETICHETTA_CAMPIONE SET DATA_INCASSO = ? ");
			query.append("WHERE ID_RICHIESTA = ? ");
			CuneoLogger.debug(method,query.toString());
			stmt = conn.prepareStatement(query.toString());
			stmt.setTimestamp(1, ts);
			stmt.setBigDecimal(2, new BigDecimal(idRichiesta));
			update_data = stmt.executeUpdate();
			CuneoLogger.debug(method,"ESITO UPDATE ETICHETTA_CAMPIONE : "+(update_data>0?"positivo":"negativo"));
			stmt.close();
	    }catch(java.sql.SQLException ex) {
	    	CuneoLogger.error(method,"SQLException - "+ex);
	    	this.getAut().setQuery("updateDataPagamento della classe EtichettaCampione per id richiesta "+idRichiesta);
	    	this.getAut().setContenutoQuery(query.toString());
	    	throw (ex);
	    }catch(Exception e){
	    	CuneoLogger.error(method,"Exception - "+e);
	    	this.getAut().setQuery("updateDataPagamento della classe EtichettaCampione per id richiesta "+idRichiesta
	                            +": non è una SQLException ma una Exception"
	                            +" generica");
	    	this.getAut().setContenutoQuery(query.toString());
	    	throw (e);
	    }finally{
	    	if (conn!=null) conn.close();
	    		return update_data>0?true:false;
	    }
  }
  /**
   *
   * @param idRichiesta contiene le idRichiesta (separate da una virgola) che
   * devo controllare
   * @return restituisce true se i campioni hanno soddisfatto i parametri di
   * uguaglianza, false altrimenti
   * @throws Exception
   * @throws SQLException
   */
  public boolean controllaFatturaMultipla(String idRichiesta, boolean verificaIntestatarioFattura)
  throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    Statement stmt=null;
    StringBuffer query=new StringBuffer("");
    String fatturare=null; // questa stringa contiene un flag che mi dice a chi è
                      //intestata la fattura
    try
    {
      conn=getConnection();
      stmt = conn.createStatement();
      ResultSet rset;

      /**
       * CONTROLLA CHE: nessun campione deve avere una fattura emessa attiva
       */
      query = new StringBuffer("SELECT F.ANNULLATA ");
      query.append("FROM CAMPIONE_FATTURATO CF, FATTURA F ");
      query.append("WHERE CF.NUMERO_FATTURA=F.NUMERO_FATTURA AND CF.ANNO=F.ANNO ");
      query.append("AND CF.ID_RICHIESTA IN (");
      query.append(idRichiesta).append(") AND F.ANNULLATA IS NULL");
      //CuneoLogger.debug(this, query.toString());
      rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        return false;
      }
      rset.close();
      
      
      //Controllo che lo stato sia diverso da Richiesta in Bozza (00) o analisi richiesta (10) o Richiesta annullata (50)
      query.setLength(0);
      query.append("SELECT STATO_ATTUALE ");
      query.append("FROM ETICHETTA_CAMPIONE ");
      query.append("WHERE ID_RICHIESTA IN (");
      query.append(idRichiesta).append(")");
      //CuneoLogger.debug(this, query.toString());
      rset = stmt.executeQuery(query.toString());
      
      while(rset.next())
      {
        String stato = rset.getString("STATO_ATTUALE");
        if ("00".equals(stato) || "10".equals(stato) || "50".equals(stato))
        	return false;
      }
      rset.close();
      
      

      /**
       * CONTROLLA CHE: - tutti i campioni devono avere gli estremi di
       *                  fatturazione uguali
       *                - deve essere stata richiesta la fattura per tutti i
       *                  campioni
       */
      query.setLength(0);
      int num=0;
      query.append("SELECT DISTINCT FATTURA_SN,SPEDIZIONE,");
      query.append("FATTURARE,CF_PARTITA_IVA, ");
      query.append("RAGIONE_SOCIALE,INDIRIZZO,CAP,COMUNE ");
      query.append("FROM DATI_FATTURA ");
      query.append("WHERE ID_RICHIESTA IN (");
      query.append(idRichiesta).append(")");
      //CuneoLogger.debug(this, query.toString());
      rset = stmt.executeQuery(query.toString());
      while (rset.next())
      {
      	fatturare = rset.getString("FATTURARE");

      	if (verificaIntestatarioFattura)
      	{
      		num++;
      	}
      	else if ("N".equals(fatturare))
      	{
      		num++;
      	}
        if (num>1)
      	{
      		return false;
      	}
        
        if ("N".equals(rset.getString("FATTURA_SN")))
      	{
      		return false;
      	}
      }
      rset.close();

      /**
       * CONTROLLA CHE: non devono esserci analisi gratuite
       */
      query.setLength(0);
      query.append("SELECT PAGAMENTO FROM ETICHETTA_CAMPIONE ");
      query.append("WHERE ID_RICHIESTA IN (");
      query.append(idRichiesta).append(")");
      query.append("AND PAGAMENTO='G'");
      //CuneoLogger.debug(this, query.toString());
      rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        return false;
      }
      rset.close();

      /**
       * Quest'ultimo controllo devo farlo solo nel caso di selezione multipla
       */
      if (idRichiesta.indexOf(",")!=-1 && verificaIntestatarioFattura)
      {
        /**
         * CONTROLLA CHE: l'intestatario della fattura sia lo stesso.
         */
        query.setLength(0);
        if ("U".equals(fatturare))
        {
          query.append("SELECT DISTINCT E.ANAGRAFICA_UTENTE ");
          query.append("FROM ETICHETTA_CAMPIONE E ");
          query.append("WHERE E.ID_RICHIESTA IN (");
          query.append(idRichiesta).append(")");
        }
        if ("T".equals(fatturare))
        {
          query.append("SELECT DISTINCT E.ANAGRAFICA_TECNICO ");
          query.append("FROM ETICHETTA_CAMPIONE E ");
          query.append("WHERE E.ID_RICHIESTA IN (");
          query.append(idRichiesta).append(")");
        }
        if ("P".equals(fatturare))
        {
          query.append("SELECT DISTINCT E.ANAGRAFICA_PROPRIETARIO ");
          query.append("FROM ETICHETTA_CAMPIONE E ");
          query.append("WHERE E.ID_RICHIESTA IN (");
          query.append(idRichiesta).append(")");
        }
        if ("O".equals(fatturare))
        {
          String anagrafiche="",temp;
          query.append("SELECT E.ANAGRAFICA_TECNICO,E.ANAGRAFICA_UTENTE ");
          query.append("FROM ETICHETTA_CAMPIONE E ");
          query.append("WHERE E.ID_RICHIESTA IN (");
          query.append(idRichiesta).append(")");
          //CuneoLogger.debug(this, query.toString());
          rset = stmt.executeQuery(query.toString());
          while (rset.next())
          {
            temp=rset.getString("ANAGRAFICA_TECNICO");
            if (temp==null || "".equals(temp))
            {
              temp=rset.getString("ANAGRAFICA_UTENTE");
              if (temp==null || "".equals(temp)) temp="0";
            }
            anagrafiche=anagrafiche+temp+",";
          }
          anagrafiche=anagrafiche.substring(0,anagrafiche.length()-1);
          rset.close();
          query.setLength(0);
          query.append("SELECT DISTINCT ID_ORGANIZZAZIONE FROM ANAGRAFICA ");
          query.append("WHERE ID_ANAGRAFICA IN (");
          query.append(anagrafiche).append(")");
          //CuneoLogger.debug(this, query.toString());
        }
        if ("A".equals(fatturare))
        {
          query.append("SELECT DISTINCT CF_PARTITA_IVA, RAGIONE_SOCIALE, INDIRIZZO, CAP, COMUNE ");
          query.append("FROM DATI_FATTURA ");
          query.append("WHERE ID_RICHIESTA IN (");
          query.append(idRichiesta).append(")");
        }
        num=0;
        CuneoLogger.debug(this, query.toString());
        //System.out.println("\nQuery EtichettaCampione.controllaFatturaMultipla\n"
        //                   + query.toString());
        rset = stmt.executeQuery(query.toString());
        while (rset.next())
        {
          num++;
          if (num>1) return false;
        }
        rset.close();
      }
      return true;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("controllaFatturaMultipla della "
                            +"classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("controllaFatturaMultipla "
                            +"della classe EtichettaCampione"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
        if (stmt!=null) stmt.close();
        if (conn!=null) conn.close();
    }
  }

  /**
   * Metodo utilizzato per passare far passare i campione dallo stato di
   * Analisi Richiesta a quello di Campioni in laboratorio
   * @param idRichiesta: codici dei campioni che devo far cambiare di stato
   * @param pagamento: mi indica come devo impostare il pagamento dei campioni
   * @param labAnalisi: codice del laboratorio in cui verranno effettuate
   * le analisi
   * @param scarto: indica se il campione è in uno stato bloccante o meno
   * @param note: eventuali note da inserire nella tracciatura
   * @throws Exception
   * @throws SQLException
   */
  public void accettazioneScartoAnalRic(String idRichiesta,
                                        String pagamento,
                                        String labAnalisi,
                                        String scarto,
                                        String note,
                                        String data_incasso
                                        ) throws Exception, SQLException {
	  if (!isConnection())
		  throw new Exception("Riferimento a DataSource non inizializzato");
	    Connection conn=null;
	    StringBuffer query=new StringBuffer("");
	    String codiceMaterialeCampione = "";
	    try {
	      conn=getConnection();
	      // Si imposta a false l'autocommit perché le operazioni che vengono eseguite sul DB
	      // devono essere effettuate in blocco
	      // Si noti l'invocazione del metodo conn.commit() PRIMA di conn.close() e
	      // l'invocazione di conn.rollback() nel blocco catch{}
	//      conn.setAutoCommit( false );
	      // Per evitare ogni rischio di interferenza nel recuperare l'ultimo valore creato
	      // nella sequence, imposto il massimo livello di isolamento di questa transazione
	//      conn.setTransactionIsolation( Connection.TRANSACTION_SERIALIZABLE );
	      
	      //ELISA 22/05/2017
	      //Provo a invertire le due righe 
	      conn.setTransactionIsolation( Connection.TRANSACTION_SERIALIZABLE );
	      conn.setAutoCommit( false );
	      
	      CuneoLogger.debug(this, "idRichiesta "+ idRichiesta);
	      long idRichieste[]=Utili.longIdTokenize(idRichiesta,",");
	      Statement stmt = conn.createStatement();
	      ResultSet rset;
	      int numCampione=0,progressivo=0;
	      //for(int i=0;i<idRichieste.length;i++) 
	      for(int i=idRichieste.length-1;i>=0;i--) {//per riordinare in modo decrescente i valori idRichiesta
	        if (!"B".equals(scarto)) {
	          query.setLength(0);
	          query.append("SELECT COALESCE(MAX( NUMERO_CAMPIONE), 0) AS NUM ");
	          query.append("FROM ETICHETTA_CAMPIONE ");
	          query.append("WHERE ANNO= ").append(Utili.getYearDate());
	          query.append(" AND CODICE_MATERIALE =  ");
	          query.append("(");
	          query.append("SELECT CODICE_MATERIALE FROM ETICHETTA_CAMPIONE ");
	          query.append("WHERE ID_RICHIESTA = ").append(idRichieste[i]);
	          query.append(")");
	          CuneoLogger.debug(this, query.toString());
	          rset = stmt.executeQuery(query.toString());
	          if (rset.next()){
		            try{
		              numCampione=Integer.parseInt(rset.getString("NUM"));
		            }catch(Exception eNum){
		              numCampione=0;
		            }
	          }else{
	        	  numCampione=0;
	          }
	          numCampione++;
	          rset.close();
	          query.setLength(0);
	          query.append("SELECT CODICE_MATERIALE FROM ETICHETTA_CAMPIONE ");
	          query.append("WHERE ID_RICHIESTA = ").append(idRichieste[i]);
	          CuneoLogger.debug(this, query.toString());
	          rset = stmt.executeQuery(query.toString());
	          if (rset.next()){
	        	  codiceMaterialeCampione = rset.getString("CODICE_MATERIALE");
	          }
	          rset.close();
	        }
	        query.setLength(0);
	        query.append("UPDATE ETICHETTA_CAMPIONE ");
	        query.append("SET LABORATORIO_ANALISI = ");
	        if (labAnalisi!=null)
	        	query.append("'").append(labAnalisi).append("',");
	        else 
	        	query.append("null,");
	        query.append("PAGAMENTO = ");
	        if (pagamento!=null)
	        	query.append("'").append(pagamento).append("',");
	        else 
	        	query.append("null,");
	        
	        if(!Utili.isEmpty(data_incasso) && Utili.isDate(data_incasso)) {
	  		  	query.append(" DATA_INCASSO = TO_DATE('").append(data_incasso).append("', 'dd/MM/yyyy'), ");
	        }else
	        	query.append(" DATA_INCASSO = null, ");
	        
	        if ("B".equals(scarto) || "A".equals(scarto)){
				query.append("STATO_ANOMALIA = ");
				query.append("'").append(scarto).append("'");
	        }else
	        	query.append("STATO_ANOMALIA = null ");
	
	        if (!"B".equals(scarto)){
				query.append(", STATO_ATTUALE = '20' ");
				if(!codiceMaterialeCampione.equals("ZMA")) {
					query.append(", ANNO = ").append(Utili.getYearDate());
					query.append(", NUMERO_CAMPIONE = ").append(numCampione);
				}
	        }
	        
	        query.append(" WHERE ID_RICHIESTA = ").append(idRichieste[i]);
	        query.append(" AND NUMERO_CAMPIONE IS NULL ");
	        //CuneoLogger.debug(this, query.toString());
	        int rowAgg = stmt.executeUpdate(query.toString());
	        /**
	         * Leggo il progressivo della tabella tracciatura
	         * */
	        //solo se è funzionata la query di prima
	        if(rowAgg > 0){
		          query.setLength(0);
		          query.append("SELECT MAX(PROGRESSIVO) AS PROGRESSIVO ");
		          query.append("FROM TRACCIATURA ");
		          query.append("WHERE ID_RICHIESTA = ").append(idRichieste[i]);
		          //CuneoLogger.debug(this, query.toString());
		          rset = stmt.executeQuery(query.toString());
		          if (rset.next()){
		        	  try{
		        		  progressivo=Integer.parseInt(rset.getString("PROGRESSIVO"));
		        	  }catch(Exception eNum2){
						  progressivo=0;
		        	  }
		          }else{
		        	  progressivo=0;
		          }
		          progressivo++;
		          rset.close();
		          query.setLength(0);
		          if (!"B".equals(scarto)){
					/**
					 * Inserisco il record corrispondente a questo campione sulla tabella
					 * TRACCIATURA con stato uguale a Campione Laborarotio
					 * */
					query.append("INSERT INTO TRACCIATURA(ID_RICHIESTA, PROGRESSIVO,");
					query.append("CODICE,DATA,NOTE) ");
					query.append("VALUES(");
					query.append(idRichieste[i]);
					query.append(",").append(progressivo);
					query.append(",'20'");
					query.append(",to_timestamp('").append(Utili.getSystemDate());
					query.append("','DD-MM-YYYY')");
					if (note!=null && !"".equals(note))
						query.append(",'").append(Utili.toVarchar(note)).append("')");
					else
						query.append(",null)");
	          }else{
	            /**
	             * Inserisco il record corrispondente a questo campione sulla tabella
	             * TRACCIATURA con stato uguale a analisi richiesta
	             * */
	            query.append("INSERT INTO TRACCIATURA(ID_RICHIESTA, PROGRESSIVO,");
	            query.append("CODICE,DATA,NOTE) ");
	            query.append("VALUES(");
	            query.append(idRichieste[i]);
	            query.append(",").append(progressivo);
	            query.append(",'10'");
	            query.append(",to_timestamp('").append(Utili.getSystemDate());
	            query.append("','DD-MM-YYYY')");
	            if (note!=null && !"".equals(note))
	            	query.append(",'").append(Utili.toVarchar(note)).append("')");
	            else
	            	query.append(",null)");
	          }
	          //CuneoLogger.debug(this, query.toString());
	          stmt.executeUpdate(query.toString());
	        }
	      }
	      stmt.close();
	      conn.commit();
	    }catch(java.sql.SQLException ex){
	      this.getAut().setQuery("accettazioneScartoAnalRic della classe EtichettaCampione");
	      this.getAut().setContenutoQuery(query.toString());
	      try{
	    	  conn.rollback();
	      }catch( java.sql.SQLException ex2 ){
	        this.getAut().setQuery("accettazioneScartoAnalRic della classe EtichettaCampione"
	                               +":problemi con il rollback");
	        this.getAut().setContenutoQuery(query.toString());
	        throw (ex2);
	      }
	      throw (ex);
	    }catch(Exception e){
	      this.getAut().setQuery("accettazioneScartoAnalRic della classe EtichettaCampione"
	                             +": non è una SQLException ma una Exception"
	                             +" generica");
	      this.getAut().setContenutoQuery(query.toString());
	      throw (e);
	    }finally{
	      if (conn!=null){
	        conn.setAutoCommit(true);
	        conn.close();
	      }
	    }
  }

  /**
   * Metodo utilizzato per far passare un campione dallo stato di campione
   * in laboratorio a quello di referto da emettere
   * @param pagamento: mi indica lo stato di pagamento del campione
   * @param note: eventuali note da inserire nella tracciatura
   * @throws Exception
   * @throws SQLException
   */
  public void accettazioneScartoCampLab(String pagamento,
                                        String note,
                                        String data_incasso
                                       )
  throws Exception, SQLException
  {
  if (!isConnection())
     throw new Exception("Riferimento a DataSource non inizializzato");
   Connection conn=null;
   StringBuffer query=new StringBuffer("");
   long progressivo=0;
   try
   {
     conn=getConnection();
     // Si imposta a false l'autocommit perché le operazioni che vengono eseguite sul DB
     // devono essere effettuate in blocco
     // Si noti l'invocazione del metodo conn.commit() PRIMA di conn.close() e
     // l'invocazione di conn.rollback() nel blocco catch{}
     conn.setAutoCommit( false );
     // Per evitare ogni rischio di interferenza nel recuperare l'ultimo valore creato
     // nella sequence, imposto il massimo livello di isolamento di questa transazione
     conn.setTransactionIsolation( Connection.TRANSACTION_SERIALIZABLE );

     Statement stmt = conn.createStatement();
     ResultSet rset;


     query.append("UPDATE ETICHETTA_CAMPIONE ");
     query.append("SET PAGAMENTO = ");
     if (pagamento!=null)
       query.append("'").append(pagamento).append("',");
     else query.append("null,");
     query.append(" DATA_INCASSO = ");
     if(data_incasso!=null && !data_incasso.equals(""))
    	 query.append("TO_DATE('").append(data_incasso).append("','dd/MM/yyyy'), ");
     else
    	 query.append("null, ");
     query.append("STATO_ATTUALE = '30'");
     query.append(" WHERE ID_RICHIESTA = ").append(getAut().getIdRichiestaCorrente());
     //CuneoLogger.debug(this, query.toString());
     stmt.executeUpdate(query.toString());
     /**
      * Leggo il progressivo della tabella tracciatura
      * */
     query.setLength(0);
     query.append("SELECT MAX(PROGRESSIVO) AS PROGRESSIVO ");
     query.append("FROM TRACCIATURA ");
     query.append("WHERE ID_RICHIESTA = ").append(getAut().getIdRichiestaCorrente());
     //CuneoLogger.debug(this, query.toString());
     rset = stmt.executeQuery(query.toString());
     if (rset.next()){
       try{
         progressivo=Integer.parseInt(rset.getString("PROGRESSIVO"));
       }catch(Exception eNum2){
         progressivo=0;
       }
     }else{
       progressivo=0;
     }
     progressivo++;
     rset.close();


     query.setLength(0);
     /**
      * Inserisco il record corrispondente a questo campione sulla tabella
      * TRACCIATURA con stato uguale a Campione Laborarotio
      * */
     query.append("INSERT INTO TRACCIATURA(ID_RICHIESTA, PROGRESSIVO,");
     query.append("CODICE,DATA,NOTE) ");
     query.append("VALUES(");
     query.append(this.getAut().getIdRichiestaCorrente());
     query.append(",").append(progressivo);
     query.append(",'30'");
     query.append(",to_timestamp('").append(Utili.getSystemDate());
     query.append("','DD-MM-YYYY')");
     if (note!=null && !"".equals(note))
       query.append(",'").append(Utili.toVarchar(note)).append("')");
     else
       query.append(",null)");
     //CuneoLogger.debug(this, query.toString());
     stmt.executeUpdate(query.toString());


     stmt.close();
     conn.commit();
   }
   catch(java.sql.SQLException ex)
   {
     this.getAut().setQuery("accettazioneScartoCampLab della classe EtichettaCampione");
     this.getAut().setContenutoQuery(query.toString());
     try
     {
       conn.rollback();
     }
     catch( java.sql.SQLException ex2 )
     {
       this.getAut().setQuery("accettazioneScartoCampLab della classe EtichettaCampione"
                              +":problemi con il rollback");
       this.getAut().setContenutoQuery(query.toString());
       throw (ex2);
     }
     throw (ex);
   }
   catch(Exception e)
   {
     this.getAut().setQuery("accettazioneScartoCampLab della classe EtichettaCampione"
                            +": non è una SQLException ma una Exception"
                            +" generica");
     this.getAut().setContenutoQuery(query.toString());
     throw (e);
   }
   finally
   {
     if (conn!=null)
     {
       conn.setAutoCommit(true);
       conn.close();
     }
   }
  }

  public void modificaAnalisi(String pagamento, String note, String data_incasso) throws Exception, SQLException
  {
  	if (! isConnection())
  	{
      throw new Exception("Riferimento a DataSource non inizializzato");
  	}

    Connection conn = null;
    StringBuffer query = new StringBuffer("");
    Statement stmt = null;

    try
    {
      conn = getConnection();
      stmt = conn.createStatement();

      // Si imposta a false l'autocommit perché le operazioni che vengono eseguite sul DB
      // devono essere effettuate in blocco
      // Si noti l'invocazione del metodo conn.commit() PRIMA di conn.close() e
      // l'invocazione di conn.rollback() nel blocco catch{}
      conn.setAutoCommit(false);
      // Per evitare ogni rischio di interferenza nel recuperare l'ultimo valore creato
      // nella sequence, imposto il massimo livello di isolamento di questa transazione
      conn.setTransactionIsolation( Connection.TRANSACTION_SERIALIZABLE);
      note = note.replaceAll("'", "''");
      //Inserimento record corrispondente a questo campione sulla tabella TRACCIATURA con stato uguale a Richiesta annullata
      query.setLength(0);
      query.append(" UPDATE TRACCIATURA SET ");
      query.append(" NOTE = '" + note + "'");
      query.append(" WHERE ID_RICHIESTA = ").append(getAut().getIdRichiestaCorrente());
      query.append(" AND CODICE = '" + Constants.CODICE_TRACCIATURA.CAMPIONE_IN_LABORATORIO + "' ");

      stmt.executeUpdate(query.toString());
      
      if(pagamento!=null && !pagamento.equals("")) {
	      //Aggiornamento record della tabella ETICHETTA_CAMPIONE impostando il campo STATO_ATTUALE con stato uguale a Richiesta annullata
	      query.setLength(0);
	      query.append(" UPDATE ETICHETTA_CAMPIONE SET ");
	      query.append(" PAGAMENTO = '" + pagamento + "' ");
	      query.append(", DATA_INCASSO = ");
	      if(data_incasso!=null && !data_incasso.equals(""))
	     	 query.append("TO_DATE('").append(data_incasso).append("','dd/MM/yyyy') ");
	      else
	     	 query.append("null ");
	      query.append(" WHERE ID_RICHIESTA = ").append(getAut().getIdRichiestaCorrente());
	
	      stmt.executeUpdate(query.toString());
      }
      stmt.close();
      conn.commit();
    }catch (java.sql.SQLException ex){
      this.getAut().setQuery("modificaAnalisi della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      try{
        conn.rollback();
      }catch( java.sql.SQLException ex2 ){
        this.getAut().setQuery("modificaAnalisi della classe EtichettaCampione"
                               +":problemi con il rollback");
        this.getAut().setContenutoQuery(query.toString());
        throw (ex2);
      }
      throw (ex);
    }catch(Exception e){
      this.getAut().setQuery("modificaAnalisi della classe EtichettaCampione"
                             +": non è una SQLException ma una Exception"
                             +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }finally{
      if (conn!=null){
        conn.setAutoCommit(true);
        conn.close();
      }
    }
  }

  /**
   * Metodo utilizzato per passare far passare i campione dallo stato di
   * Referto da emettere a quello di Referto emesso
   * @param idRichiesta: codici dei campioni che devo far cambiare di stato
   * @param pagamento: mi indica come devo impostare il pagamento dei campioni
   * @param scarto: indica se il campione è in uno stato bloccante o meno
   * @param note: eventuali note da inserire nella tracciatura
   * @param firmaReferto: indica se l'esito deve essere emesso con firma o no
   * @param noteFirma: eventuali note relative alla firma dell'esito
   * @throws Exception
   * @throws SQLException
   */
  public void firmaScartoRefEmettere(String idRichieste,
                                     String pagamento,
                                     String scarto,
                                     String note,
                                     String firmaReferto,
                                     String noteFirma,
                                     String data_incasso
                                     )
  throws Exception, SQLException
  {
 if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    try
    {
      conn=getConnection();
      // Si imposta a false l'autocommit perché le operazioni che vengono eseguite sul DB
      // devono essere effettuate in blocco
      // Si noti l'invocazione del metodo conn.commit() PRIMA di conn.close() e
      // l'invocazione di conn.rollback() nel blocco catch{}
      conn.setAutoCommit( false );
      // Per evitare ogni rischio di interferenza nel recuperare l'ultimo valore creato
      // nella sequence, imposto il massimo livello di isolamento di questa transazione
      conn.setTransactionIsolation( Connection.TRANSACTION_SERIALIZABLE );
      Statement stmt = conn.createStatement();
      ResultSet rset;
      
      String[] aIdRichiesta = null;
	  	if (idRichieste.contains(","))
	  	{
	  		//Più richieste
	  		aIdRichiesta = idRichieste.split(",");
	  	}
	  	else
	  	{
				//Una sola richiesta
	  		aIdRichiesta = new String[1];
	  		aIdRichiesta[0] = idRichieste;  	
	  	}
      
      for (String idRichiesta : aIdRichiesta)
      {
        int progressivo = 0;

        query.setLength(0);
        query.append("UPDATE ETICHETTA_CAMPIONE ");
        query.append("SET PAGAMENTO = ");
        if (pagamento!=null)
          query.append("'").append(pagamento).append("',");
        else query.append("null,");
        if(!Utili.isEmpty(data_incasso) && Utili.isDate(data_incasso)) {
  		  	query.append(" DATA_INCASSO = TO_DATE('").append(data_incasso).append("', 'dd/MM/yyyy'), ");
        }else
        	query.append(" DATA_INCASSO = null, ");
        if ("B".equals(scarto) || "A".equals(scarto))
        {
          query.append("STATO_ANOMALIA = ");
          query.append("'").append(scarto).append("'");
        }
        else query.append("STATO_ANOMALIA = null ");

        if (!"B".equals(scarto))
        {
          query.append(",STATO_ATTUALE = '40'");
        }
        query.append(" WHERE ID_RICHIESTA = ").append(idRichiesta);
        //CuneoLogger.debug(this, query.toString());
        stmt.executeUpdate(query.toString());

        /**
         * Leggo il progressivo della tabella tracciatura
         * */
        query.setLength(0);
        query.append("SELECT MAX(PROGRESSIVO) AS PROGRESSIVO ");
        query.append("FROM TRACCIATURA ");
        query.append("WHERE ID_RICHIESTA = ").append(idRichiesta);
        //CuneoLogger.debug(this, query.toString());
        rset = stmt.executeQuery(query.toString());
        if (rset.next())
        {
          try
          {
            progressivo=Integer.parseInt(rset.getString("PROGRESSIVO"));
          }
          catch(Exception eNum2)
          {
            progressivo=0;
          }
        }
        else
        {
          progressivo=0;
        }
        progressivo++;
        rset.close();

        query.setLength(0);
        if (!"B".equals(scarto))
        {
          /**
           * Inserisco il record corrispondente a questo campione sulla tabella
           * TRACCIATURA con stato uguale a Campione Laborarotio
           * */
          query.append("INSERT INTO TRACCIATURA(ID_RICHIESTA, PROGRESSIVO,");
          query.append("CODICE,DATA,NOTE) ");
          query.append("VALUES(");
          query.append(idRichiesta);
          query.append(",").append(progressivo);
          query.append(",'40'");
          query.append(",to_timestamp('").append(Utili.getSystemDate());
          query.append("','DD-MM-YYYY')");
          if (note!=null && !"".equals(note))
            query.append(",'").append(Utili.toVarchar(note)).append("')");
          else
            query.append(",null)");
        }
        else
        {
          /**
           * Inserisco il record corrispondente a questo campione sulla tabella
           * TRACCIATURA con stato uguale a analisi richiesta
           * */
          query.append("INSERT INTO TRACCIATURA(ID_RICHIESTA, PROGRESSIVO,");
          query.append("CODICE,DATA,NOTE) ");
          query.append("VALUES(");
          query.append(idRichiesta);
          query.append(",").append(progressivo);
          query.append(",'30'");
          query.append(",to_timestamp('").append(Utili.getSystemDate());
          query.append("','DD-MM-YYYY')");
          if (note!=null && !"".equals(note))
            query.append(",'").append(Utili.toVarchar(note)).append("')");
          else
            query.append(",null)");
        }
        //CuneoLogger.debug(this, query.toString());
        stmt.executeUpdate(query.toString());

        /**
         * Infine nel caso il campione non sia stato bloccato bisogna inserire
         * un record sulla tabella Firma referto.
         * */
        if (!"B".equals(scarto))
        {
          query.setLength(0);
          query.append("INSERT INTO FIRMA_REFERTO(ID_RICHIESTA, ID_FIRMA, NOTE)");
          query.append("VALUES (");
          query.append(idRichiesta);
          if ("S".equals(firmaReferto))
            query.append(",").append(getAut().getResponsabile().getIdFirma());
          else
            query.append(",null");
          if (noteFirma!=null && !"".equals(noteFirma))
            query.append(",'").append(Utili.toVarchar(noteFirma)).append("')");
          else
            query.append(",null)");
          //CuneoLogger.debug(this, query.toString());
          stmt.executeUpdate(query.toString());
        }      	
      }

      stmt.close();
      conn.commit();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("firmaScartoRefEmettere della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      try
      {
        conn.rollback();
      }
      catch( java.sql.SQLException ex2 )
      {
        this.getAut().setQuery("firmaScartoRefEmettere della classe EtichettaCampione"
                               +":problemi con il rollback");
        this.getAut().setContenutoQuery(query.toString());
        throw (ex2);
      }
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("firmaScartoRefEmettere della classe EtichettaCampione"
                             +": non è una SQLException ma una Exception"
                             +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      if (conn!=null)
      {
        conn.setAutoCommit(true);
        conn.close();
      }
    }
  }


  /**
   * Metodo utilizzato per far tornare un campione dallo referto da emettere
   * a quello di stato di campione in laboratorio
   * @param note: eventuali note da inserire nella tracciatura
   * @throws Exception
   * @throws SQLException
   */
  public void ritornoCampLab(String idRichieste, String note)
  throws Exception, SQLException
  {
  if (!isConnection())
     throw new Exception("Riferimento a DataSource non inizializzato");
   Connection conn=null;
   StringBuffer query=new StringBuffer("");
   long progressivo=0;
   try
   {
     conn=getConnection();
     // Si imposta a false l'autocommit perché le operazioni che vengono eseguite sul DB
     // devono essere effettuate in blocco
     // Si noti l'invocazione del metodo conn.commit() PRIMA di conn.close() e
     // l'invocazione di conn.rollback() nel blocco catch{}
     conn.setAutoCommit( false );
     // Per evitare ogni rischio di interferenza nel recuperare l'ultimo valore creato
     // nella sequence, imposto il massimo livello di isolamento di questa transazione
     conn.setTransactionIsolation( Connection.TRANSACTION_SERIALIZABLE );

     Statement stmt = conn.createStatement();
     ResultSet rset;

     String[] aIdRichiesta = null;
		 if (idRichieste.contains(","))
		 {
			 //Più richieste
			 aIdRichiesta = idRichieste.split(",");
		 }
		 else
		 {
			 //Una sola richiesta
			 aIdRichiesta = new String[1];
			 aIdRichiesta[0] = idRichieste;  	
		 }

     for (String idRichiesta : aIdRichiesta)
     {
    	 query.setLength(0);
	     query.append("UPDATE ETICHETTA_CAMPIONE ");
	     query.append("SET STATO_ATTUALE = '20'");
	     query.append(" WHERE ID_RICHIESTA = ").append(idRichiesta);
	     //CuneoLogger.debug(this, query.toString());
	     stmt.executeUpdate(query.toString());
	     /**
	      * Leggo il progressivo della tabella tracciatura
	      * */
	     query.setLength(0);
	     query.append("SELECT MAX(PROGRESSIVO) AS PROGRESSIVO ");
	     query.append("FROM TRACCIATURA ");
	     query.append("WHERE ID_RICHIESTA = ").append(idRichiesta);
	     //CuneoLogger.debug(this, query.toString());
	     rset = stmt.executeQuery(query.toString());
	     if (rset.next())
	     {
	       try
	       {
	         progressivo=Integer.parseInt(rset.getString("PROGRESSIVO"));
	       }
	       catch(Exception eNum2)
	       {
	         progressivo=0;
	       }
	     }
	     else
	     {
	       progressivo=0;
	     }
	     progressivo++;
	     rset.close();
	
	
	     query.setLength(0);
	     /**
	      * Inserisco il record corrispondente a questo campione sulla tabella
	      * TRACCIATURA con stato uguale a Campione Laborarotio
	      * */
	     query.append("INSERT INTO TRACCIATURA(ID_RICHIESTA, PROGRESSIVO,");
	     query.append("CODICE,DATA,NOTE) ");
	     query.append("VALUES(");
	     query.append(idRichiesta);
	     query.append(",").append(progressivo);
	     query.append(",'20'");
	     query.append(",to_timestamp('").append(Utili.getSystemDate());
	     query.append("','DD-MM-YYYY')");
	     if (note!=null && !"".equals(note))
	       query.append(",'").append(Utili.toVarchar(note)).append("')");
	     else
	       query.append(",null)");
	     //CuneoLogger.debug(this, query.toString());
	     stmt.executeUpdate(query.toString());
     }

     stmt.close();
     conn.commit();
   }
   catch(java.sql.SQLException ex)
   {
     this.getAut().setQuery("ritornoCampLab della classe EtichettaCampione");
     this.getAut().setContenutoQuery(query.toString());
     try
     {
       conn.rollback();
     }
     catch( java.sql.SQLException ex2 )
     {
       this.getAut().setQuery("ritornoCampLab della classe EtichettaCampione"
                              +":problemi con il rollback");
       this.getAut().setContenutoQuery(query.toString());
       throw (ex2);
     }
     throw (ex);
   }
   catch(Exception e)
   {
     this.getAut().setQuery("accettazioneScartoCampLab della classe EtichettaCampione"
                            +": non è una SQLException ma una Exception"
                            +" generica");
     this.getAut().setContenutoQuery(query.toString());
     throw (e);
   }
   finally
   {
     if (conn!=null)
     {
       conn.setAutoCommit(true);
       conn.close();
     }
   }
  }

  /**
   * Viene utilizzato per leggere i dati necessari alla creazione del PDF
   * anagraficaCampione.pdf
   * @param idRichiesta
   * @throws Exception
   * @throws SQLException
   */
  public void selectPdf(String idRichiesta)
  throws Exception, SQLException
  {
    Anagrafiche anagrafiche=new Anagrafiche();
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    String anagraficaUtente;
    String proprietario;
    String richiedente;
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("SELECT EC.ID_RICHIESTA, ");
      //query.append("TRUNC(EC.DATA_INSERIMENTO_RICHIESTA) AS DATA_INSERIMENTO_RICHIESTA, ");
      query.append("TO_CHAR(EC.DATA_INSERIMENTO_RICHIESTA,'DD/MM/YYYY') AS DATA_INSERIMENTO_RICHIESTA, ");
      query.append("to_char((select min(data) from tracciatura tr where tr.id_richiesta =");
      query.append(idRichiesta);
      query.append(" and codice = '20'),'DD/MM/YYYY') AS DATA_ACCETTAZIONE_CAMPIONE, ");
      query.append("EC.ANNO, ");
      query.append("EC.NUMERO_CAMPIONE, ");
      query.append("EC.CODICE_MATERIALE, ");
      query.append("M.DESCRIZIONE AS MATERIALE, ");
      query.append("EC.LABORATORIO_CONSEGNA, ");
      query.append("EC.LABORATORIO_ANALISI, ");
      query.append("EC.DESCRIZIONE_ETICHETTA, ");
      query.append("EC.ANAGRAFICA_UTENTE, ");
      query.append("EC.ANAGRAFICA_TECNICO, ");
      query.append("EC.ANAGRAFICA_PROPRIETARIO, ");
      query.append("EC.STATO_ANOMALIA, ");
      query.append("EC.NOTE_CLIENTE ");
      query.append("FROM ETICHETTA_CAMPIONE EC, MATERIALE M ");
      query.append("WHERE EC.ID_RICHIESTA = ");
      query.append(idRichiesta);
      query.append(" AND EC.CODICE_MATERIALE=M.CODICE_MATERIALE ");
      CuneoLogger.debug(this,query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        anagraficaUtente=rset.getString("ANAGRAFICA_UTENTE");
        this.setAnagraficaUtente(anagraficaUtente);
        this.setAnagraficaProprietario(rset.getString("ANAGRAFICA_PROPRIETARIO"));
        if (this.getAnagraficaProprietario()==null)
          this.setAnagraficaProprietario(anagraficaUtente);
        proprietario=anagrafiche.getNomeCognome(conn, this.getAnagraficaProprietario());

        this.setAnagraficaTecnico(rset.getString("ANAGRAFICA_TECNICO"));
        if (this.getAnagraficaTecnico()==null)
          this.setAnagraficaRichiedente(anagraficaUtente);
        else
          this.setAnagraficaRichiedente(this.getAnagraficaTecnico());
        richiedente=anagrafiche.getNomeCognome(conn, this.getAnagraficaRichiedente());

        this.setIdRichiesta(rset.getString("ID_RICHIESTA"));
        this.setDataInserimentoRichiesta(rset.getString("DATA_INSERIMENTO_RICHIESTA"));
        this.setDataAccettazioneCampione(rset.getString("DATA_ACCETTAZIONE_CAMPIONE"));
        this.setAnnoCampione(rset.getString("ANNO"));
        this.setNumeroCampione(rset.getString("NUMERO_CAMPIONE"));
        this.setCodiceMateriale(rset.getString("CODICE_MATERIALE"));
        this.setDescMateriale(rset.getString("MATERIALE"));
        this.setCodLabConsegna(rset.getString("LABORATORIO_CONSEGNA"));
        this.setCodLabAnalisi(rset.getString("LABORATORIO_ANALISI"));
        this.setDescrizioneEtichetta(rset.getString("DESCRIZIONE_ETICHETTA"));
        this.setNote(rset.getString("NOTE_CLIENTE"));
        this.setProprietario(proprietario);
        this.setRichiedente(richiedente);
        this.setStatoAnomalia(rset.getString("STATO_ANOMALIA"));
      }
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("selectPdf della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("selectPdf della classe EtichettaCampione"
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
   * Questo metodo legge i dati necessari alla pagina esitoterminato
   * @param idRichiesta: indica il campione del quale devono essere caricati i
   * dati
   * @throws Exception
   * @throws SQLException
   */
  public void selectEsitoTerminato(String idRichiesta)
  throws Exception, SQLException
  {
    //Anagrafiche anagrafiche=new Anagrafiche();
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("SELECT EC.ID_RICHIESTA, ");
      query.append("EC.CODICE_MATERIALE,EC.ANNO, ");
      query.append("EC.NUMERO_CAMPIONE, ");
      query.append("M.DESCRIZIONE AS MATERIALE, ");
      query.append("L.DESCRIZIONE AS LABORATORIO_ANALISI, ");
      query.append("EC.DESCRIZIONE_ETICHETTA,EC.PAGAMENTO,T.NOTE, TO_CHAR(EC.DATA_INCASSO,'DD/MM/YYYY') AS DATA_INCASSO, ");
      query.append("(with elenco_iuv as ( select distinct FIRST_VALUE(iuv)  " + 
      		"    OVER( PARTITION BY ID_RICHIESTA ORDER BY data_annullamento desc) iuv,ID_RICHIESTA " + 
      		" from PAGAMENTI) " + 
      		"select case when p.data_annullamento is null then p.iuv else '' end iuv " + 
      		"from pagamenti p " + 
      		"  JOIN elenco_iuv ei ON p.id_richiesta = ei.id_richiesta and p.iuv = ei.iuv " + 
      		"where p.id_richiesta = ec.id_richiesta) ");
      query.append("FROM ETICHETTA_CAMPIONE EC LEFT OUTER JOIN TRACCIATURA T ON(EC.ID_RICHIESTA=T.ID_RICHIESTA), ");
      query.append("MATERIALE M, LABORATORIO L ");
      query.append("WHERE EC.ID_RICHIESTA = ");
      query.append(idRichiesta);
      query.append(" AND EC.CODICE_MATERIALE=M.CODICE_MATERIALE ");
      query.append(" AND EC.LABORATORIO_ANALISI=L.CODICE_LABORATORIO ");
      query.append(" AND T.PROGRESSIVO = ");
      query.append("(SELECT MAX(PROGRESSIVO) FROM TRACCIATURA WHERE EC.ID_RICHIESTA=ID_RICHIESTA) ");
      //CuneoLogger.debug(this, query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        this.setCodiceMateriale(rset.getString("CODICE_MATERIALE"));
        this.setIdRichiesta(rset.getString("ID_RICHIESTA"));
        this.setAnnoCampione(rset.getString("ANNO"));
        this.setNumeroCampione(rset.getString("NUMERO_CAMPIONE"));
        this.setDescMateriale(rset.getString("MATERIALE"));
        this.setDescLabAnalisi(rset.getString("LABORATORIO_ANALISI"));
        this.setDescrizioneEtichetta(rset.getString("DESCRIZIONE_ETICHETTA"));
        this.setPagamento(rset.getString("PAGAMENTO"));
        this.setNote(rset.getString("NOTE"));
        this.setData_incasso(rset.getString("DATA_INCASSO"));
        this.setIuv(rset.getString("IUV"));
      }
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("selectEsitoTerminato della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("selectEsitoTerminato della classe EtichettaCampione"
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
   * Il metodo getCostoAnalisi() equivale ad un attributo calcolato
   * della richiesta corrispondente ad un'istanza di questa classe.
   *
   * @return Una stringa contenente il costo dell'analisi
   * @throws Exception
   * @throws SQLException
   */
  public String getCostoAnalisi()
  throws Exception, SQLException
  {
    return getCostoAnalisi(null);
  }

  /**
   * Il metodo getCostoAnalisi() equivale ad un attributo calcolato
   * della richiesta corrispondente ad un'istanza di questa classe.
   *
   * @param costoSpedizione Se <b>null</b> viene ignorato, altrimenti deve contenere il costo della spedizione, che viene aggiunto al totale
   * @return Una stringa contenente il costo dell'analisi
   * @throws Exception
   * @throws SQLException
   */
  public String getCostoAnalisi(String costoSpedizione)
  throws Exception, SQLException
  {
    //Anagrafiche anagrafiche=new Anagrafiche();
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    double importoAnalisi=0;
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("SELECT SUM(AR.COSTO_ANALISI) AS COSTO_ANALISI ");
      query.append("FROM ANALISI_RICHIESTE AR ");
      query.append("WHERE AR.ID_RICHIESTA = ");
      query.append(this.idRichiesta);
      //CuneoLogger.debug(this, query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
        importoAnalisi=rset.getDouble("COSTO_ANALISI");
      rset.close();
      stmt.close();
      importoAnalisi+=Double.parseDouble(costoSpedizione);
      return Utili.valuta(importoAnalisi);
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("getCostoAnalisi della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("getCostoAnalisi della classe EtichettaCampione"
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

  public String getDataRicezione()
      throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    String dataRicezione="";
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("SELECT TO_CHAR(DATA,'DD/MM/YYYY') AS DATA ");
      query.append("FROM TRACCIATURA ");
      query.append("WHERE ID_RICHIESTA=");
      query.append(this.getIdRichiesta());
      query.append("AND CODICE='20' "); // codice "ricezione"
      query.append("ORDER BY DATA"); // nel dubbio ne esistano diversi, prendo sempre il primo
      //CuneoLogger.debug(this, query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
        dataRicezione=rset.getString("DATA");
      rset.close();
      stmt.close();
      return dataRicezione;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("getDataRicezione della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("getDataRicezione della classe EtichettaCampione"
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
 
  public String getRiferCatCoorGeoPDF()
	      throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query = new StringBuffer("");
    StringBuffer riferCatCoorGeo=new StringBuffer("");
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query.append("SELECT C.DESCRIZIONE, P.SIGLA_PROVINCIA, D.COORDINATA_NORD_UTM, D.COORDINATA_EST_UTM, ");
      query.append("D.SEZIONE, D.FOGLIO, D.PARTICELLA_CATASTALE ");
      query.append("FROM DATI_APPEZZAMENTO D ");
      query.append("	LEFT JOIN COMUNE C ON D.COMUNE_APPEZZAMENTO=C.CODICE_ISTAT ");
      query.append("	LEFT JOIN PROVINCIA P ON C.PROVINCIA=P.ID_PROVINCIA ");
      query.append("WHERE ID_RICHIESTA= ").append(idRichiesta);
      
      //CuneoLogger.debug(this, query.toString());
      /*
	      Per le coordinate vengono visualizzati  campi COORDINATA_NORD_UTM e COORDINATA_EST_UTM della tabella DATI_APPEZAMENTO 
	      (le coordinate sono visualizzate solamente se valorizzate).
	      Per i riferimenti catastali (visualizzati se campo PARTICELLA di DAT_APPEZZAMENTO è valorizzato) 
	      vengono visualizzati la decodifica del campo COMUNE_APPEZZAMENTO (descrizione del comune e sigla provincia), 
	      il campo SEZIONE, il campo FOGLIO e il campo PARTICELLA_CATASTALE.
	      
	      UTM Nord 123456  - UTM Est 123456
		  Caluso (TO) - Sez. 1 - Fo 12 - Part. 33			

      */
      
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
    	String comune=rset.getString("DESCRIZIONE");
    	String siglaProv=rset.getString("SIGLA_PROVINCIA");
    	String nordUtm=rset.getString("COORDINATA_NORD_UTM");
    	String estUtm=rset.getString("COORDINATA_EST_UTM");
    	String particella=rset.getString("PARTICELLA_CATASTALE");
    	String sezione=rset.getString("SEZIONE");
    	String foglio=rset.getString("FOGLIO");
    	
    
    	
    	if (!(Utili.isEmpty(nordUtm) || Utili.isEmpty(estUtm)))
    	{
    		riferCatCoorGeo.append("UTM Nord ").append(nordUtm);
    		riferCatCoorGeo.append(" - UTM Est ").append(estUtm).append("\n");
    	}
    	riferCatCoorGeo.append(comune);
    	if (!Utili.isEmpty(siglaProv))
    		riferCatCoorGeo.append(" (").append(siglaProv).append(")");
		if (!Utili.isEmpty(sezione))
			riferCatCoorGeo.append(" - Sez. ").append(sezione);
		if (!Utili.isEmpty(foglio))	
			riferCatCoorGeo.append(" - Fo ").append(foglio);
		if (!Utili.isEmpty(particella))
			riferCatCoorGeo.append(" - Part. ").append(particella);
    	
      }
      rset.close();
      stmt.close();
      return riferCatCoorGeo.toString();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("getRiferCatCoorGeoPDF della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("getRiferCatCoorGeoPDF della classe EtichettaCampione"
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
   * Questo metodo viene utilizzato per creare il pdf del risultato delle
   * analisi
   * */
  public boolean selectPerPDFRisultatoAnalisi(boolean terreno,String riga1[],String riga2[],String riga3[],String codMateriale)
      throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    String anagraficaTecnico="";
    String anagUtente="",anagProprietario="";
    boolean ris=false;
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();

      query = new StringBuffer("SELECT E.ANAGRAFICA_UTENTE, ");
      query.append("E.ANAGRAFICA_TECNICO,A.TIPO_UTENTE");
      query.append(",E.ANAGRAFICA_PROPRIETARIO ");
      query.append("FROM ETICHETTA_CAMPIONE E LEFT OUTER JOIN ANAGRAFICA A ON (E.ANAGRAFICA_UTENTE=A.ID_ANAGRAFICA) ");
      query.append("WHERE E.ID_RICHIESTA=");
      query.append(this.getIdRichiesta());
      //CuneoLogger.debug(this, query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        anagraficaTecnico=rset.getString("ANAGRAFICA_TECNICO");
        anagUtente=rset.getString("ANAGRAFICA_UTENTE");
        anagProprietario=rset.getString("ANAGRAFICA_PROPRIETARIO");
        if (anagProprietario==null) anagProprietario=anagUtente;
        if (anagraficaTecnico==null)
        {
          anagraficaTecnico=anagUtente;
          String tipo=rset.getString("TIPO_UTENTE");
          if ("T".equals(tipo)) ris=true;
        }
        else ris=true;
      }
      rset.close();
      query.setLength(0);
      if (ris)
      {
        query.append("SELECT O.RAGIONE_SOCIALE,");
        query.append("C2.DESCRIZIONE AS COMUNE_ORG,A.COGNOME_RAGIONE_SOCIALE,");
        query.append("A.NOME,A.INDIRIZZO,C1.DESCRIZIONE AS COMUNE_IND,");
        query.append("P.SIGLA_PROVINCIA AS PROVINCIA,");
        query.append("A.TELEFONO,A.CELLULARE ");
        query.append("FROM ANAGRAFICA A, COMUNE C1,COMUNE C2, PROVINCIA P,");
        query.append("ORGANIZZAZIONE_PROFESSIONALE O ");
        query.append("WHERE A.ID_ANAGRAFICA=").append(anagraficaTecnico);
        query.append(" AND C1.CODICE_ISTAT=A.COMUNE_RESIDENZA");
        query.append(" AND P.ID_PROVINCIA=C1.PROVINCIA");
        query.append(" AND O.ID_ORGANIZZAZIONE=A.ID_ORGANIZZAZIONE");
        query.append(" AND C2.CODICE_ISTAT=O.COMUNE_RESIDENZA");
        //CuneoLogger.debug(this, query.toString());
        rset = stmt.executeQuery(query.toString());
        String temp1=null,temp2=null,temp3=null;
        if (rset.next())
        {
          temp1=rset.getString("RAGIONE_SOCIALE");
          temp2=rset.getString("COMUNE_ORG");
          if (temp1==null) temp1="";
          if (temp2==null) temp2="";
          if ("".equals(temp2)) riga1[0]=temp1;
          else
          {
            if ("".equals(temp1)) riga1[0]=temp2;
            else riga1[0]=temp1+ " - "+temp2;
          }

          temp1=rset.getString("COGNOME_RAGIONE_SOCIALE");
          temp2=rset.getString("NOME");
          if (temp1==null) temp1="";
          if (temp2==null) temp2="";
          riga1[1]=temp1+ " " +temp2;

          temp1=rset.getString("INDIRIZZO");
          temp2=rset.getString("COMUNE_IND");
          temp3=rset.getString("PROVINCIA");
          if (temp1==null) temp1="";
          if (temp2==null) temp2="";
          if (temp3==null) temp3="";
          if ("".equals(temp2)) riga1[2]=temp1;
          else
          {
            if ("".equals(temp1)) riga1[2]=temp2;
            else riga1[2]=temp1+ " - "+temp2;
          }

          riga1[2]+=" ("+temp3+")";

          temp1=rset.getString("TELEFONO");

          if (temp1==null)
          {
            temp1=rset.getString("CELLULARE");
            if (temp1==null) temp1="";
          }
          riga1[3]=temp1;
        }
        else
        {
          ris=false;
        }
        rset.close();
      }
      query.setLength(0);
      if (terreno)
      {
        query.append("SELECT PROP.COGNOME_RAGIONE_SOCIALE,PROP.NOME,");
        query.append("PROP.INDIRIZZO,C1.DESCRIZIONE AS COMUNE_IND,");
        query.append("P.SIGLA_PROVINCIA AS PROVINCIA,PROP.TELEFONO,PROP.CELLULARE,");
        query.append("UTENTE.COGNOME_RAGIONE_SOCIALE COG_TITOLARE,");
        query.append("UTENTE.NOME AS NOME_TITOLARE,");
        query.append(" C.DESCRIZIONE || ' - ' || S.DESCRIZIONE AS SPECIE_COLTURA,");
        query.append(" C2.DESCRIZIONE || ' - ' || S2.DESCRIZIONE AS SPECIE_COLTURA_ATTUALE ");
        query.append("FROM ANAGRAFICA PROP,ANAGRAFICA UTENTE,COMUNE C1,PROVINCIA P");
        query.append(",DATI_CAMPIONE_TERRENO D ");
        query.append("left join SPECIE_COLTURA S2 on s2.ID_SPECIE = D.COLTURA_ATTUALE ");
        query.append("left join CLASSE_COLTURA C2 on C2.ID_COLTURA = S2.ID_COLTURA ");
        query.append(",CLASSE_COLTURA C,SPECIE_COLTURA S");
        query.append(" WHERE PROP.ID_ANAGRAFICA=").append(anagProprietario);
        query.append(" AND UTENTE.ID_ANAGRAFICA=").append(anagUtente);
        query.append(" AND D.ID_RICHIESTA=").append(getIdRichiesta());
        query.append(" AND C1.CODICE_ISTAT=PROP.COMUNE_RESIDENZA ");
        query.append(" AND P.ID_PROVINCIA=C1.PROVINCIA");
        query.append(" AND S.ID_SPECIE = D.COLTURA_PREVISTA");
        query.append(" AND C.ID_COLTURA=S.ID_COLTURA");
      }
      else
      {
        query.append("SELECT PROP.COGNOME_RAGIONE_SOCIALE,PROP.NOME,");
        query.append("PROP.INDIRIZZO,C1.DESCRIZIONE AS COMUNE_IND,");
        query.append("P.SIGLA_PROVINCIA AS PROVINCIA,PROP.TELEFONO,PROP.CELLULARE,");
        query.append("UTENTE.COGNOME_RAGIONE_SOCIALE COG_TITOLARE,");
        query.append("UTENTE.NOME AS NOME_TITOLARE ");
        query.append("FROM ANAGRAFICA PROP,ANAGRAFICA UTENTE,COMUNE C1,PROVINCIA P");
        query.append(" WHERE PROP.ID_ANAGRAFICA=").append(anagProprietario);
        query.append(" AND UTENTE.ID_ANAGRAFICA=").append(anagUtente);
        query.append(" AND C1.CODICE_ISTAT=PROP.COMUNE_RESIDENZA ");
        query.append(" AND P.ID_PROVINCIA=C1.PROVINCIA");
      }
      //CuneoLogger.debug(this, query.toString());
      rset = stmt.executeQuery(query.toString());
      String temp1=null,temp2=null,temp3=null;
      if (rset.next())
      {
        temp1=rset.getString("COGNOME_RAGIONE_SOCIALE");
        temp2=rset.getString("NOME");
        if (temp1==null) temp1="";
        if (temp2==null) temp2="";
        if ("".equals(temp2)) riga2[0]=temp1;
        else
        {
          if ("".equals(temp1)) riga2[0]=temp2;
          else riga2[0]=temp1+ " "+temp2;
        }

        temp1=rset.getString("COG_TITOLARE");
        temp2=rset.getString("NOME_TITOLARE");
        if (temp1==null) temp1="";
        if (temp2==null) temp2="";
        riga2[1]=temp1+ " " +temp2;

        temp1=rset.getString("INDIRIZZO");
        temp2=rset.getString("COMUNE_IND");
        temp3=rset.getString("PROVINCIA");
        if (temp1==null) temp1="";
        if (temp2==null) temp2="";
        if (temp3==null) temp3="";
        if ("".equals(temp2)) riga2[2]=temp1;
        else
        {
          if ("".equals(temp1)) riga2[2]=temp2;
          else riga2[2]=temp1+ " - "+temp2;
        }

          riga2[2]+=" ("+temp3+")";

        temp1=rset.getString("TELEFONO");

        if (temp1==null)
        {
          temp1=rset.getString("CELLULARE");
          if (temp1==null) temp1="";
        }
        riga2[3]=temp1;
        if (terreno)
        {
          riga3[0]=rset.getString("SPECIE_COLTURA_ATTUALE");
          riga3[1]=rset.getString("SPECIE_COLTURA");
        }
      }
      rset.close();

      if (!terreno)
      {
        query.setLength(0);
        if ("ERB".equals(codMateriale))
        {
          query.append("SELECT SO.DESCRIZIONE AS SPECIE,");
          query.append("CC.DESCRIZIONE AS COLTURA ");
          query.append("FROM CAMPIONE_VEGETALI_ERBACEE CVE, ");
          query.append("SPECIE_COLTURA SO LEFT OUTER JOIN CLASSE_COLTURA CC ON (SO.ID_COLTURA = CC.ID_COLTURA) ");
          query.append("WHERE CVE.ID_RICHIESTA=");
          query.append(this.getIdRichiesta());
          query.append(" AND CVE.ID_SPECIE=SO.ID_SPECIE");
        }
        if ("FOG".equals(codMateriale) || "FRU".equals(codMateriale))
        {
          query.append("SELECT SO.DESCRIZIONE AS SPECIE, CVF.ALTRA_SPECIE,");
          query.append("CC.DESCRIZIONE AS COLTURA ");
          query.append("FROM CAMPIONE_VEGETALI_FOGLIEFRUTTA CVF ");
          query.append("LEFT OUTER JOIN SPECIE_COLTURA SO ON (CVF.ID_SPECIE=SO.ID_SPECIE) ");
          query.append("LEFT OUTER JOIN CLASSE_COLTURA CC ON (CVF.ID_COLTURA = CC.ID_COLTURA) ");
          query.append("WHERE CVF.ID_RICHIESTA=");
          query.append(this.getIdRichiesta());
        }
        //CuneoLogger.debug(this,query.toString());
        rset = stmt.executeQuery(query.toString());
        if (rset.next())
        {
          riga3[0]=rset.getString("COLTURA");
          riga3[1]=rset.getString("SPECIE");
          if (riga3[1]==null || "".equals(riga3[1]))
          {
            if ("FOG".equals(getCodiceMateriale()) || "FRU".equals(getCodiceMateriale()))
            {
              riga3[1]=rset.getString("ALTRA_SPECIE");
            }
          }
        }
        rset.close();
      }

      stmt.close();
      return ris;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("selectPerPDFRisultatoAnalisi della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("selectPerPDFRisultatoAnalisi della classe EtichettaCampione"
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


  public void selectPerPDFAnalisiRichieste()
      throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    //String dataRicezione="";
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("SELECT TO_CHAR(E.DATA_INSERIMENTO_RICHIESTA,'DD/MM/YYYY')");
      query.append(" AS DATA_INSERIMENTO_RICHIESTA,E.CODICE_MATERIALE ");
      query.append(",E.NUMERO_CAMPIONE,C.DESCRIZIONE AS COMUNE ");
      query.append(", M.DESCRIZIONE AS DESC_MATERIALE ");
      query.append("FROM ETICHETTA_CAMPIONE E,DATI_APPEZZAMENTO DA, COMUNE C ");
      query.append(", MATERIALE M ");
      query.append("WHERE E.ID_RICHIESTA=");
      query.append(this.getIdRichiesta());
      query.append(" AND DA.ID_RICHIESTA = E.ID_RICHIESTA");
      query.append(" AND DA.COMUNE_APPEZZAMENTO  = C.CODICE_ISTAT ");
      query.append(" AND M.CODICE_MATERIALE= E.CODICE_MATERIALE ");
      //CuneoLogger.debug(this, query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        this.setDataInserimentoRichiesta(rset.getString("DATA_INSERIMENTO_RICHIESTA"));
        this.setNumeroCampione(rset.getString("NUMERO_CAMPIONE"));
        this.setDescComune(rset.getString("COMUNE"));
        this.setCodiceMateriale(rset.getString("CODICE_MATERIALE"));
        this.setDescMateriale(rset.getString("DESC_MATERIALE"));
      }
      rset.close();
      query.setLength(0);
      if ("TER".equals(getCodiceMateriale()))
      {
        query.append("SELECT SO.DESCRIZIONE AS SPECIE ");
        query.append("FROM DATI_CAMPIONE_TERRENO DCT,SPECIE_COLTURA SO ");
        query.append("WHERE DCT.ID_RICHIESTA=");
        query.append(this.getIdRichiesta());
        query.append(" AND DCT.COLTURA_PREVISTA=SO.ID_SPECIE");
      }
      if ("ERB".equals(getCodiceMateriale()))
      {
        query.append("SELECT SO.DESCRIZIONE AS SPECIE ");
        query.append("FROM CAMPIONE_VEGETALI_ERBACEE CVE,SPECIE_COLTURA SO ");
        query.append("WHERE CVE.ID_RICHIESTA=");
        query.append(this.getIdRichiesta());
        query.append(" AND CVE.ID_SPECIE=SO.ID_SPECIE");
      }
      if ("FOG".equals(getCodiceMateriale()) || "FRU".equals(getCodiceMateriale()))
      {
        query.append("SELECT SO.DESCRIZIONE AS SPECIE, CVF.ALTRA_SPECIE ");
        query.append("FROM CAMPIONE_VEGETALI_FOGLIEFRUTTA CVF LEFT OUTER JOIN SPECIE_COLTURA SO ON(CVF.ID_SPECIE=SO.ID_SPECIE) ");
        query.append("WHERE CVF.ID_RICHIESTA=");
        query.append(this.getIdRichiesta());
      }
      //CuneoLogger.debug(this, query.toString());
      rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        this.setDescSpecieColtura(rset.getString("SPECIE"));
        if (this.getDescSpecieColtura()==null || "".equals(getDescSpecieColtura()))
        {
          if ("FOG".equals(getCodiceMateriale()) || "FRU".equals(getCodiceMateriale()))
          {
            this.setDescSpecieColtura(rset.getString("ALTRA_SPECIE"));
          }
        }
      }
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("selectPerPDFAnalisiRichieste della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("selectPerPDFAnalisiRichieste della classe EtichettaCampione"
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
   * Questo metodo viene utilizzato per popolare i dati della form che si trova
   * nella pagina firmaScarto
   * */
  public String selectForFirmaScarto(String idRichiesta)
  throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    String email=null;
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("SELECT M.DESCRIZIONE AS DESC_MATERIALE,");
      query.append("EC.CODICE_MATERIALE,EC.ANNO,");
      query.append("EC.NUMERO_CAMPIONE,EC.DESCRIZIONE_ETICHETTA,");
      query.append("L.DESCRIZIONE AS DESC_LABANALISI,");
      query.append("A.EMAIL,EC.STATO_ANOMALIA,EC.PAGAMENTO,T.NOTE,TO_CHAR(EC.DATA_INCASSO, 'DD/MM/YYYY') as DATA_INCASSO, ");
      query.append("(with elenco_iuv as (select distinct FIRST_VALUE(iuv) " + 
  			"    OVER( PARTITION BY ID_RICHIESTA ORDER BY data_annullamento desc) iuv,ID_RICHIESTA from PAGAMENTI) " + 
  			"select case when p.data_annullamento is null then p.iuv else '' end iuv " + 
  			"from pagamenti p " + 
  			"  JOIN elenco_iuv ei ON p.id_richiesta = ei.id_richiesta and p.iuv = ei.iuv " + 
  			"where p.id_richiesta = ec.id_richiesta)");
      query.append("FROM ETICHETTA_CAMPIONE EC LEFT OUTER JOIN TRACCIATURA T ON (EC.ID_RICHIESTA=T.ID_RICHIESTA), ");
      query.append("MATERIALE M,ANAGRAFICA A,LABORATORIO L ");
      query.append("WHERE EC.ID_RICHIESTA = ");
      query.append(idRichiesta);
      query.append(" AND EC.CODICE_MATERIALE= M.CODICE_MATERIALE ");
      query.append("AND A.ID_ANAGRAFICA=EC.ANAGRAFICA_UTENTE ");
      query.append("AND L.CODICE_LABORATORIO=EC.LABORATORIO_ANALISI ");
      query.append("AND T.PROGRESSIVO = ");
      query.append("(SELECT MAX(PROGRESSIVO) FROM TRACCIATURA WHERE EC.ID_RICHIESTA=ID_RICHIESTA) ");
      query.append("ORDER BY EC.ID_RICHIESTA");
      //CuneoLogger.debug(this, query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        this.setCodiceMateriale(rset.getString("CODICE_MATERIALE"));
        this.setDescMateriale(rset.getString("DESC_MATERIALE"));
        this.setAnnoCampione(rset.getString("ANNO"));
        this.setNumeroCampione(rset.getString("NUMERO_CAMPIONE"));
        this.setDescrizioneEtichetta(rset.getString("DESCRIZIONE_ETICHETTA"));
        this.setDescLabAnalisi(rset.getString("DESC_LABANALISI"));
        this.setStatoAnomalia(rset.getString("STATO_ANOMALIA"));
        this.setPagamento(rset.getString("PAGAMENTO"));
        this.setNote(rset.getString("NOTE"));
        this.setIuv(rset.getString("IUV"));
        this.setData_incasso(rset.getString("DATA_INCASSO"));
        email=rset.getString("EMAIL");
      }
      rset.close();
      stmt.close();
      return email;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("selectForFirmaScarto della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("selectForFirmaScarto della classe EtichettaCampione"
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
   * Questo metodo viene utilizzato per ottenere gli indirizzi email legati alla richiesta o richieste a cui viene inviata la fattura via email
   * nella pagina creaFattura.jsp
   * */
  public String[] selectEmailForCreaFatturaByIdRichiestaRange(String idRichieste) throws Exception, SQLException
  {
    if (!isConnection())
    {
      throw new Exception("Riferimento a DataSource non inizializzato");
    }

    Connection conn = null;
    StringBuffer query = new StringBuffer("");
    Vector<String> elencoEmail = new Vector<String>();

    try
    {
      conn = getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer(" SELECT EC.ID_RICHIESTA, AT.EMAIL EMAIL_TECNICO, AP.EMAIL EMAIL_PROPRIETARIO, ");
      query.append(" AU.EMAIL EMAIL_UTENTE ");
      query.append(" FROM ETICHETTA_CAMPIONE EC ");
      query.append(" LEFT JOIN ANAGRAFICA AT ON EC.ANAGRAFICA_TECNICO = AT.ID_ANAGRAFICA ");
      query.append(" LEFT JOIN ANAGRAFICA AP ON EC.ANAGRAFICA_PROPRIETARIO = AP.ID_ANAGRAFICA ");
      query.append(" LEFT JOIN ANAGRAFICA AU ON EC.ANAGRAFICA_UTENTE = AU.ID_ANAGRAFICA ");
      query.append(" WHERE EC.ID_RICHIESTA IN (");
      query.append(idRichieste).append(")");
      CuneoLogger.debug(this, query.toString());

      ResultSet rset = stmt.executeQuery(query.toString());
      while (rset.next())
      {
      	String emailAnagrafica = rset.getString("EMAIL_TECNICO");
      	emailAnagrafica = emailAnagrafica != null && emailAnagrafica != ""  ? emailAnagrafica : rset.getString("EMAIL_PROPRIETARIO");
      	emailAnagrafica = emailAnagrafica != null && emailAnagrafica != ""  ? emailAnagrafica : rset.getString("EMAIL_UTENTE");
      	if (emailAnagrafica != null && emailAnagrafica != "")
      	{
      		if(!elencoEmail.contains(emailAnagrafica)) //per evitare di avere destinatari duplicati Elisa 02/03/2017
      		{
      			 CuneoLogger.debug(this, "emailAnagrafica " + emailAnagrafica );
      		     elencoEmail.add(emailAnagrafica);
      		}
      	}
      }
      rset.close();
      stmt.close();

      return (elencoEmail != null && elencoEmail.size() > 0) ? elencoEmail.toArray(new String[elencoEmail.size()]) : null;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("selectEmailForCreaFatturaByIdRichiestaRange della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("selectEmailForCreaFatturaByIdRichiestaRange della classe EtichettaCampione"
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
   * Metodo utilizzato per emettere la fattura
   * @param richieste:id dei campioni a cui si sta lavorando
   * @param valori: indica lo stato di pagamento di ogni campione
   * @param cfPartitaIva: tutti i parametri che seguono sono relativi
   * all'intestatario della fattura
   * @param ragioneSociale
   * @param indirizzo
   * @param cap
   * @param comuneDesc
   * @param siglaProvincia
   * @param importoSpedizione:importo della spedizione della fattura
   * @param pagata: indica se la fattura è stata pagata(S), non pagata (N)
   * o pagata parzialmente (P)
   * @param imponibili: vettore che contiene l'imponibile per ogni campione
   * @param iva: vettore che contiene l'IVA per ogni campione
   * @throws Exception
   * @throws SQLException
   */
  public int[] creaFattura(long idRichieste[],
                          boolean val[],
                          String cfPartitaIva,
                          String ragioneSociale,
                          String indirizzo,
                          String cap,
                          String comuneDesc,
                          String siglaProvincia,
                          String importoSpedizione,
                          double imponibili[],
                          double iva[],
                          String data_incasso
                         )
  throws Exception, SQLException
  {
    String pagata=null;
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer();

    try
    {
      conn=getConnection();
      // Si imposta a false l'autocommit perché le operazioni che vengono eseguite sul DB
      // devono essere effettuate in blocco
      // Si noti l'invocazione del metodo conn.commit() PRIMA di conn.close() e
      // l'invocazione di conn.rollback() nel blocco catch{}
      conn.setAutoCommit( false );
      // Per evitare ogni rischio di interferenza nel recuperare l'ultimo valore creato
      // nella sequence, imposto il massimo livello di isolamento di questa transazione
      //conn.setTransactionIsolation( Connection.TRANSACTION_SERIALIZABLE );
      PreparedStatement pstmt;
      int numFattura[]=new int[2];
      numFattura[1]=Integer.parseInt(Utili.getYearDate());
      /**
       * Leggo il numero dell'ultima fattura fatta
       * */
      query.setLength(0);

      query.append("SELECT COALESCE(ULTIMA_FATTURA_AGRICHIM,0) AS NUM ");
      query.append("FROM PARAMETRI_FATTURE ");
      query.append("WHERE ANNO_FATTURA_AGRICHIM = ?");
      //CuneoLogger.debug(this, query.toString());
      pstmt = conn.prepareStatement(query.toString());
      pstmt.setInt(1,numFattura[1]);
      ResultSet rset = pstmt.executeQuery();
      if (rset.next())
      {
        try
        {
          numFattura[0]=Integer.parseInt(rset.getString("NUM"));
        }
        catch(Exception eNum)
        {
          numFattura[0]=0;
        }
      }
      else
      {
        numFattura[0]=0;
      }
      numFattura[0]++;
      //CuneoLogger.debug(this, "numFattura "+numFattura);
      rset.close();

      /**
       * Se non ho trovato l'anno devo aggiornare il record con l'anno corrente
       * */
      if (numFattura[0]==1)
      {
        query.setLength(0);
        query.append("UPDATE PARAMETRI_FATTURE SET " );
        query.append("ULTIMA_FATTURA_AGRICHIM = 1, ");
        query.append("ANNO_FATTURA_AGRICHIM = ? ");
        //CuneoLogger.debug(this, query.toString());
        pstmt.close();
        pstmt = conn.prepareStatement(query.toString());
        pstmt.setInt(1,numFattura[1]);
        pstmt.executeUpdate();
      }
      else
      {
        query.setLength(0);
        query.append("UPDATE PARAMETRI_FATTURE SET " );
        query.append("ULTIMA_FATTURA_AGRICHIM = ? ");
        //CuneoLogger.debug(this, query.toString());
        pstmt.close();
        pstmt = conn.prepareStatement(query.toString());
        pstmt.setInt(1,numFattura[0]);
        pstmt.executeUpdate();
      }


      /**
       * Vado a vedere se tutte le analisi sono state pagate ("S"),
       * nessua analisi è stata pagata ("N")
       * qualche analisi è stata pagata e qualche analisi non è stata pagata ("P")
       */
      boolean pagS=false,pagN=false;
      for(int i=0;i<idRichieste.length;i++)
      {
          if (val[i]) pagS=true;
          else pagN=true;
      }
      if (pagS && pagN) pagata="P";
      else
      {
       if (pagS)  pagata="S";
       else pagata="N";
      }


      /**
       * Inserisco il record sulla tabella fattura
       * */
      query.setLength(0);
      query.append("INSERT INTO FATTURA(" );
      query.append("ANNO, NUMERO_FATTURA, DATA_FATTURA, PAGATA, ");
      query.append("PARTITA_IVA_O_CF, RAGIONE_SOCIALE, INDIRIZZO,");
      query.append("CAP, COMUNE, SIGLA_PROVINCIA, IMPORTO_SPEDIZIONE) ");
      query.append("VALUES(?,?,?,?,?,?,?,?,?,?,?) ");
      pstmt.close();
      pstmt = conn.prepareStatement(query.toString());

      pstmt.setInt(1,numFattura[1]);
      pstmt.setInt (2,numFattura[0]);
      pstmt.setDate(3,new java.sql.Date(new java.util.Date().getTime()));
      pstmt.setString(4,pagata);
      pstmt.setString(5,cfPartitaIva);
      pstmt.setString(6,ragioneSociale);
      pstmt.setString(7,indirizzo);
      pstmt.setString(8,cap);
      pstmt.setString(9,comuneDesc);
      pstmt.setString(10,siglaProvincia);
      pstmt.setDouble(11,Double.parseDouble(importoSpedizione.replace(',','.')));
      pstmt.executeUpdate();



      /**
       * Aggiorno lo stato pagamento dei vari campioni
       * */
      query.setLength(0);
      query.append("UPDATE ETICHETTA_CAMPIONE SET " );
      query.append("PAGAMENTO = ? ");
      if(!Utili.isEmpty(data_incasso) && Utili.isDate(data_incasso))
    		  query.append(", DATA_INCASSO = ? ");
      query.append("WHERE ID_RICHIESTA = ?");
      pstmt.close();
      pstmt = conn.prepareStatement(query.toString());
      for(int i=0;i<idRichieste.length;i++) {
          if (val[i]) pstmt.setString(1,"S");
          else pstmt.setString(1,"N");
          if(!Utili.isEmpty(data_incasso) && Utili.isDate(data_incasso)) {
	          //ricostruzione data per sqlDate yyyy-MM-dd
	          String data = data_incasso.substring(6)+"-"+data_incasso.substring(3, 5)+"-"+data_incasso.substring(0, 2);
	          pstmt.setDate(2, java.sql.Date.valueOf(data));
	          pstmt.setLong (3,idRichieste[i]);//cambia la numerazione
          }else {
        	  pstmt.setLong (2,idRichieste[i]);
          }
          pstmt.executeUpdate();
      }
      pstmt.close();

      /**
       * inserisco un record per ogni campione sulla tabella Campione Fatturato
       * */
      query.setLength(0);

      query.append("INSERT INTO CAMPIONE_FATTURATO " );
      query.append("(ID_RICHIESTA, ANNO, NUMERO_FATTURA,");
      query.append("IMPORTO_IMPONIBILE, IMPORTO_IVA, ID_CAMPIONE_FATTURATO) ");
      query.append("VALUES(?,?,?,?,?,?) ");
      pstmt.close();
      pstmt = conn.prepareStatement(query.toString());
      for(int i=0;i<idRichieste.length;i++)
      {
          pstmt.setLong (1,idRichieste[i]);
          pstmt.setInt(2,numFattura[1]);
          pstmt.setInt (3,numFattura[0]);
          pstmt.setDouble(4,imponibili[i]);
          pstmt.setDouble(5,iva[i]);
          pstmt.setLong(6, getNewIdCampioneFatturato(conn, rset));
          pstmt.executeUpdate();
      }
      pstmt.close();

      conn.commit();
      return numFattura;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("creaFattura della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      try
      {
        conn.rollback();
      }
      catch( java.sql.SQLException ex2 )
      {
        this.getAut().setQuery("creaFattura della classe EtichettaCampione"
                               +":problemi con il rollback");
        this.getAut().setContenutoQuery(query.toString());
        throw (ex2);
      }
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("creaFattura della classe EtichettaCampione"
                             +": non è una SQLException ma una Exception"
                             +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      if (conn!=null)
      {
        conn.setAutoCommit(true);
        conn.close();
      }
    }
  }

  private Long getNewIdCampioneFatturato(Connection conn, ResultSet rset) throws SQLException
  {
    //Nuova primary key per il campo ID_CAMPIONE_FATTURATO
    Statement stmt = conn.createStatement();

    rset = stmt.executeQuery("SELECT NEXTVAL ('ID_CAMPIONE_FATTURATO') AS NEXTVAL");
    if (rset.next())
    {
    	return rset.getLong("NEXTVAL");
    }
    rset.close();
    stmt.close();

    return null;
  }

  /**
   * Questo metodo mi restituisce l'immagine della firma del firmatario
   * dell'esito dell'analisi
   * */
  public boolean getFirma(Responsabile resp)
  throws Exception, SQLException
  {
    Connection conn=this.getConnection();
    StringBuffer query=new StringBuffer("");
    Statement stmt = null;
    InputStream resource=null;
    boolean trovato=false;
    try
    {
      stmt = conn.createStatement();
      query.append("SELECT F.NOME,F.COGNOME,F.TITOLO_ONORIFICO,F.FIRMA,FR.NOTE,");
      query.append("FR.ID_FIRMA ");
      query.append("FROM FIRMA_REFERTO FR LEFT OUTER JOIN FIRMA F ON (FR.ID_FIRMA = F.ID_FIRMA) ");
      query.append("WHERE FR.ID_RICHIESTA = ").append(getIdRichiesta());
      //CuneoLogger.debug(this, "query.toString() "+query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        if (rset.getString("ID_FIRMA")!=null)
        {
          Blob bTemp=null;
          try
          {
            bTemp=rset.getBlob("FIRMA");
            resource=bTemp.getBinaryStream();
            resp.setNome(rset.getString("NOME"));
            resp.setCognome(rset.getString("COGNOME"));
            resp.setTitoloOnorifico(rset.getString("TITOLO_ONORIFICO"));
          }
          catch(Exception e) {}
          trovato=true;
        }
        resp.setNote(rset.getString("NOTE"));
      }
      rset.close();
    }
    catch(java.sql.SQLException ex)
    {
      ex.printStackTrace();
      this.getAut().setQuery("getFirma della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      e.printStackTrace();
      this.getAut().setQuery("getFirma della classe EtichettaCampione"
                              +": non è una SQLException ma una Exception"
                              +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      try
      {
        if (stmt!=null) stmt.close();
        if (conn!=null) conn.close();
      }
      catch(Exception e) {}
      if (resource!=null)
      {
        byte[] buffer = new byte[1024];
        try
        {
          BufferedInputStream in = new BufferedInputStream(resource);
          ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
          int n;
          while ((n = in.read(buffer)) > 0)
            out.write(buffer, 0, n);
          in.close();
          out.flush();
          buffer = out.toByteArray();
        }
        catch (IOException ioe)
        {
          System.err.println(ioe.toString());
        }

        if (buffer != null)
          resp.setFirma(new ImageIcon(buffer).getImage());
       }
    }
    return trovato;
  }

  /**
   * Metodo utilizzato per passare far passare i campione dallo stato di
   * Analisi Richiesta a quello di Campioni in laboratorio
   * @param note: eventuali note da inserire nella tracciatura
   * @throws Exception
   * @throws SQLException
   */
  public void annullaRichiesta(String note) throws Exception, SQLException
  {
  	if (! isConnection())
  	{
      throw new Exception("Riferimento a DataSource non inizializzato");
  	}

    Connection conn = null;
    StringBuffer query = new StringBuffer("");
    Statement stmt = null;
    ResultSet rset;
    int progressivo = 0;

    try
    {
      conn = getConnection();
      stmt = conn.createStatement();

      // Si imposta a false l'autocommit perché le operazioni che vengono eseguite sul DB
      // devono essere effettuate in blocco
      // Si noti l'invocazione del metodo conn.commit() PRIMA di conn.close() e
      // l'invocazione di conn.rollback() nel blocco catch{}
      conn.setAutoCommit(false);
      // Per evitare ogni rischio di interferenza nel recuperare l'ultimo valore creato
      // nella sequence, imposto il massimo livello di isolamento di questa transazione
      conn.setTransactionIsolation( Connection.TRANSACTION_SERIALIZABLE);

      //Lettura campo progressivo della tabella tracciatura
      query.setLength(0);
      query.append("SELECT MAX(PROGRESSIVO) AS PROGRESSIVO ");
      query.append("FROM TRACCIATURA ");
      query.append("WHERE ID_RICHIESTA = ").append(getAut().getIdRichiestaCorrente());

      rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        try
        {
          progressivo = Integer.parseInt(rset.getString("PROGRESSIVO"));
        }
        catch (Exception eNum2)
        {
          progressivo = 0;
        }
      }
      else
      {
        progressivo = 0;
      }
      progressivo++;
      rset.close();

      //Inserimento record corrispondente a questo campione sulla tabella TRACCIATURA con stato uguale a Richiesta annullata
      query.setLength(0);
      query.append("INSERT INTO TRACCIATURA(ID_RICHIESTA, PROGRESSIVO,");
      query.append("CODICE,DATA,NOTE) ");
      query.append("VALUES(");
      query.append(getAut().getIdRichiestaCorrente());
      query.append(",").append(progressivo);
      query.append(",'" + Constants.CODICE_TRACCIATURA.RICHIESTA_ANNULLATA + "'");
      query.append(",to_timestamp('").append(Utili.getSystemDate());
      query.append("','DD-MM-YYYY')");
      query.append(",'").append(Utili.toVarchar(note)).append("')");

      stmt.executeUpdate(query.toString());
      
      //Aggiornamento record della tabella ETICHETTA_CAMPIONE impostando il campo STATO_ATTUALE con stato uguale a Richiesta annullata
      query.setLength(0);
      query.append(" UPDATE ETICHETTA_CAMPIONE ");
      query.append(" SET STATO_ATTUALE = '" + Constants.CODICE_TRACCIATURA.RICHIESTA_ANNULLATA + "'");
      query.append(" WHERE ID_RICHIESTA = ").append(getAut().getIdRichiestaCorrente());
      stmt.executeUpdate(query.toString());

      stmt.close();
      conn.commit();
    }
    catch (java.sql.SQLException ex)
    {
      this.getAut().setQuery("accettazioneScartoAnalRic della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      try
      {
        conn.rollback();
      }
      catch( java.sql.SQLException ex2 )
      {
        this.getAut().setQuery("accettazioneScartoAnalRic della classe EtichettaCampione"
                               +":problemi con il rollback");
        this.getAut().setContenutoQuery(query.toString());
        throw (ex2);
      }
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("accettazioneScartoAnalRic della classe EtichettaCampione"
                             +": non è una SQLException ma una Exception"
                             +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      if (conn!=null)
      {
        conn.setAutoCommit(true);
        conn.close();
      }
    }
  }

  public void setIdRichiesta (String idRichiesta)
  {
    this.idRichiesta =idRichiesta ;
  }

  public void setDataInserimentoRichiesta (String dataInserimentoRichiesta)
  {
    this.dataInserimentoRichiesta = dataInserimentoRichiesta;
  }

  public void setDescMateriale (String descMateriale)
  {
    this.descMateriale = descMateriale;
  }

  public void setDescrizioneEtichetta (String descrizioneEtichetta)
  {
    this.descrizioneEtichetta = descrizioneEtichetta;
  }

  public String getIdRichiesta()
  {
    return idRichiesta;
  }
  public String getDataInserimentoRichiesta()
  {
    return dataInserimentoRichiesta;
  }
  public String getDescMateriale()
  {
    return descMateriale;
  }
  public String getDescrizioneEtichetta()
  {
    return descrizioneEtichetta;
  }
  public void setProprietario(String proprietario)
  {
    this.proprietario = proprietario;
  }
  public String getProprietario()
  {
    return proprietario;
  }
  public void setRichiedente(String richiedente)
  {
    this.richiedente = richiedente;
  }
  public String getRichiedente()
  {
    return richiedente;
  }
  public void setDescStatoAttuale(String descStatoAttuale)
  {
    this.descStatoAttuale = descStatoAttuale;
  }
  public String getDescStatoAttuale()
  {
    return descStatoAttuale;
  }
  public void setStatoAnomalia(String statoAnomalia)
  {
    this.statoAnomalia = statoAnomalia;
  }
  public String getStatoAnomalia()
  {
    return statoAnomalia;
  }
  public String getCodiceMateriale()
  {
    return codiceMateriale;
  }
  public void setCodiceMateriale(String codiceMateriale)
  {
    this.codiceMateriale = codiceMateriale;
  }
  public void setNumeroCampione(String numeroCampione)
  {
    this.numeroCampione = numeroCampione;
  }
  public String getNumeroCampione()
  {
    return numeroCampione;
  }
  public void setAnnoCampione(String annoCampione)
  {
    this.annoCampione = annoCampione;
  }
  public String getAnnoCampione()
  {
    return annoCampione;
  }
  public void setStatoAttuale(String statoAttuale)
  {
    this.statoAttuale = statoAttuale;
  }
  public String getStatoAttuale()
  {
    return statoAttuale;
  }
  public void setNote(String note)
  {
    this.note = note;
  }
  public String getNote()
  {
    return note;
  }
  public void setDescLabConsegna(String descLabConsegna) {
    this.descLabConsegna = descLabConsegna;
  }
  public String getDescLabConsegna() {
    return descLabConsegna;
  }
  public void setPagamento(String pagamento) {
    this.pagamento = pagamento;
  }
  public String getPagamento() {
    return pagamento;
  }
  public void setCodLabConsegna(String codLabConsegna) {
    this.codLabConsegna = codLabConsegna;
  }
  public String getCodLabConsegna() {
    return codLabConsegna;
  }
  public String getAnagraficaProprietario()
  {
    return anagraficaProprietario;
  }
  public void setAnagraficaProprietario(String anagraficaProprietario)
  {
    this.anagraficaProprietario = anagraficaProprietario;
  }
  public String getAnagraficaTecnico()
  {
    return anagraficaTecnico;
  }
  public void setAnagraficaRichiedente(String anagraficaRichiedente)
  {
    this.anagraficaRichiedente = anagraficaRichiedente;
  }
  public void setAnagraficaTecnico(String anagraficaTecnico)
  {
    this.anagraficaTecnico = anagraficaTecnico;
  }
  public String getAnagraficaRichiedente()
  {
    return anagraficaRichiedente;
  }
  public String getAnagraficaUtente()
  {
    return anagraficaUtente;
  }
  public void setAnagraficaUtente(String anagraficaUtente)
  {
    this.anagraficaUtente = anagraficaUtente;
  }
  public void setCodLabAnalisi(String codLabAnalisi) {
    this.codLabAnalisi = codLabAnalisi;
  }
  public String getCodLabAnalisi() {
    return codLabAnalisi;
  }
  public void setDescComune(String descComune)
  {
    this.descComune = descComune;
  }
  public String getDescComune()
  {
    return descComune;
  }
  public void setDescSpecieColtura(String descSpecieColtura)
  {
    this.descSpecieColtura = descSpecieColtura;
  }
  public String getDescSpecieColtura()
  {
    return descSpecieColtura;
  }
  public void setDescLabAnalisi(String descLabAnalisi) {
    this.descLabAnalisi = descLabAnalisi;
  }
  public String getDescLabAnalisi() {
    return descLabAnalisi;
  }
  public void setCosto(String costo)
  {
    this.costo = costo;
  }
  public String getCosto()
  {
    return costo;
  }
  private void writeObject(ObjectOutputStream oos) throws IOException
  {
    oos.defaultWriteObject();
  }
  private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException
  {
    ois.defaultReadObject();
  }
  public void setFatturareA(String fatturareA)
  {
    this.fatturareA = fatturareA;
  }
  public String getFatturareA()
  {
    return fatturareA;
  }

	public String getDataAccettazioneCampione()
	{
		return dataAccettazioneCampione;
	}

	public void setDataAccettazioneCampione(String dataAccettazioneCampione)
	{
		this.dataAccettazioneCampione = dataAccettazioneCampione;
	}

	public String getData_incasso() {
		return data_incasso;
	}

	public void setData_incasso(String data_incasso) {
		this.data_incasso = data_incasso;
	}

	public String getIuv() {
		return iuv;
	}

	public void setIuv(String iuv) {
		this.iuv = iuv;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getImporto() {
		return importo;
	}

	public void setImporto(String importo) {
		this.importo = importo;
	}

	public String getMessaggioErrore() {
		return messaggioErrore;
	}

	public void setMessaggioErrore(String messaggioErrore) {
		this.messaggioErrore = messaggioErrore;
	}
	public String getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public void setDataPagamento(XMLGregorianCalendar calendar) {
		if(calendar != null) {
			this.dataPagamento = getDate(calendar);
		}
	}
	public static Date getDate(XMLGregorianCalendar obj){
	    Date date= obj.toGregorianCalendar().getTime();
	    CuneoLogger.debug("getDate", "inside date parse method");
	    return date;
	}

	public String getDataAnnullamento() {
		return dataAnnullamento;
	}

	public void setDataAnnullamento(String dataAnnullamento) {
		this.dataAnnullamento = dataAnnullamento;
	}
}