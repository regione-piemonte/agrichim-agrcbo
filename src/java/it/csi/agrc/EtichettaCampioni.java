package it.csi.agrc;
import it.csi.cuneo.BeanDataSource;
import it.csi.cuneo.CuneoLogger;
import it.csi.cuneo.Utili;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
//import it.csi.jsf.web.pool.*;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.ServletContext;

/**
 * Title:        Agrichim - Front Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class EtichettaCampioni extends BeanDataSource
{
  private Vector etichettaCampioni = new Vector();
  private String cancella;
  ServletContext context;

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

  public EtichettaCampioni()
  {
  }


  public void fillElencoCampioni(Boolean leggiTuttiRecord)
      throws Exception, SQLException
  {
	  fillElencoCampioni(-1, leggiTuttiRecord);
  }


  public void fillElencoCampioni(int tipoRicerca, Boolean leggiTuttiRecord)
      throws Exception, SQLException
  {
    Anagrafiche anagrafiche=new Anagrafiche();
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    String anagraficaTecnico;
    String anagraficaProprietario;
    String anagraficaUtente;
    String proprietario;
    String richiedente;
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
      String [] arrOfStr = null;
      if (leggiTuttiRecord == null || ! leggiTuttiRecord || query.indexOf("IUV:")>0) {
    	  if(query.indexOf("IUV:")>0) {
	    	  arrOfStr = query.split("(IUV:)");
	    	  query = arrOfStr[0];
		      query+=") AS ELENCO_CAMPIONI WHERE "; 
		      String iuv;
		      if(arrOfStr[1].startsWith("'3")) {
		    	  iuv = "'"+arrOfStr[1].substring(2);
		      }else
		    	  iuv = arrOfStr[1];
		      query+=" UPPER(IUV) like ("+iuv+") ";
	      }else
	    	  query += " WHERE NUM BETWEEN " + inizio + " AND " + fine;
      }
      
      switch (tipoRicerca) {
      	case Autenticazione.RICERCA_ANALISI_RICHIESTE:
      		query += " ORDER BY ID_RICHIESTA DESC ";
      		break;
      	case Autenticazione.RICERCA_CAMPIONI_LABORATORIO:
      		query += "  ORDER BY ANNO ASC, NUMERO_CAMPIONE ASC, ID_RICHIESTA ASC";
      		break;
      	default:
      		query += " ORDER BY ANNO DESC, NUMERO_CAMPIONE DESC, ID_RICHIESTA DESC ";
      }
      
      CuneoLogger.debug(this, query);
      rset = stmt.executeQuery(query);

      while (rset.next()) {
        anagraficaProprietario=rset.getString("ANAGRAFICA_PROPRIETARIO");
        anagraficaUtente=rset.getString("ANAGRAFICA_UTENTE");
        if (anagraficaProprietario==null){
          proprietario=anagrafiche.getNomeCognome(conn,anagraficaUtente);
        }else{
          proprietario=anagrafiche.getNomeCognome(conn,anagraficaProprietario);
        }
        anagraficaTecnico=rset.getString("ANAGRAFICA_TECNICO");
        if (anagraficaTecnico==null){
          richiedente=anagrafiche.getNomeCognome(conn,anagraficaUtente);
        }else{
          richiedente=anagrafiche.getNomeCognome(conn,anagraficaTecnico);
        }

        add(new EtichettaCampione(
                                  rset.getString("ID_RICHIESTA"),
                                  rset.getString("DATA"),
                                  rset.getString("CODICE_MATERIALE"),
                                  rset.getString("MATERIALE"),
                                  rset.getString("DESCRIZIONE_ETICHETTA"),
                                  rset.getString("DESC_STATO_ATTUALE"),
                                  proprietario,
                                  richiedente,
                                  rset.getString("NUMERO_CAMPIONE"),
                                  rset.getString("ANNO"),
                                  rset.getString("STATO_ATTUALE"),
                                  rset.getString("NOTE"),
                                  rset.getString("PAGAMENTO"),
                                  rset.getString("COD_TIPO_PAGAMENTO"),
                                  rset.getString("IUV"),
                                  rset.getString("DATA_ANNULLAMENTO"))
                                  );
      }
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("fillElencoCampioni della classe EtichettaCampioni");
      this.getAut().setContenutoQuery(query);
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("fillElencoCampioni della classe EtichettaCampioni"
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

  public Vector<Object[]> esportaRichieste(Vector<Long> idRichieste) throws Exception, SQLException
  {
    if (! isConnection())
    {
      throw new Exception("Riferimento a DataSource non inizializzato");
    }
    Connection conn = null;
    StringBuffer query = new StringBuffer("");
    Vector<Object[]> elenco = new Vector<Object[]>();

    try
    {
      conn = getConnection();
      Statement stmt = conn.createStatement();

      query = new StringBuffer("SELECT EC.ID_RICHIESTA NUMERO_RICHIESTA ");
      query.append(", EC.ANNO ANNO ");
      query.append(", EC.NUMERO_CAMPIONE NUM_CAMPIONE ");  
      query.append(", EC.DESCRIZIONE_ETICHETTA ETICHETTA ");
      query.append(", TO_CHAR(EC.DATA_INSERIMENTO_RICHIESTA,'DD/MM/YYYY') AS DATA_INSERIMENTO_RICHIESTA ");
      query.append(", TO_CHAR((SELECT MIN(DATA) FROM TRACCIATURA TR WHERE TR.ID_RICHIESTA = EC.ID_RICHIESTA AND CODICE = '20'),'DD/MM/YYYY') DATA_ACCETTAZIONE ");
      query.append(", TO_CHAR((SELECT MAX(DATA) FROM TRACCIATURA TR WHERE TR.ID_RICHIESTA = EC.ID_RICHIESTA AND CODICE = '40'),'DD/MM/YYYY') DATA_EMISSIONE_REFERTO ");
      query.append(", CT.DESCRIZIONE STATO_RICHIESTA ");
      query.append(", (CASE WHEN EC.STATO_ATTUALE = '00' THEN '' ");
      query.append("        ELSE (SELECT TR.NOTE ");
      query.append("              FROM TRACCIATURA TR ");
      query.append("              WHERE TR.ID_RICHIESTA = EC.ID_RICHIESTA ");
      query.append("              AND TR.PROGRESSIVO = (SELECT MAX(TR2.PROGRESSIVO) ");
      query.append("                                    FROM TRACCIATURA TR2 ");
      query.append("                                    WHERE TR2.ID_RICHIESTA = TR.ID_RICHIESTA ");
      query.append("		                                AND TR2.CODICE = EC.STATO_ATTUALE)) END) NOTE_STATO_RICHIESTA ");
      query.append(", COM_CAMP.DESCRIZIONE COMUNE_CAMPIONE ");
      query.append(", PR.SIGLA_PROVINCIA SIGLA_PROVINCIA_CAMPIONE ");
      query.append(", SC_ATT.DESCRIZIONE COLTURA_ATTUALE ");
      query.append(", SC_PREV.DESCRIZIONE COLTURA_PREVISTA ");
      query.append(", AD.NOTE NOTE_AGGIUNTIVE ");
      query.append(", COM_CONS.DESCRIZIONE LABORATORIO_CONSEGNA ");
      query.append(", MDC.DESCRIZIONE TIPO_CONSEGNA ");
      query.append(", CMP.DESCRIZIONE_MISURA_PSR ANALISI_SVOLTA_ADEMP_PSR ");
      query.append(", EC.NOTE_MISURA_PSR MOTIVAZ_ADEMP_PSR ");
      query.append(", EC.NOTE_CLIENTE AS NOTE_RICHIEDENTE ");
      query.append(", (CASE WHEN AN1.TIPO_UTENTE = 'T' THEN OP1.RAGIONE_SOCIALE ELSE OP2.RAGIONE_SOCIALE END) ORGANIZZAZIONE ");
      query.append(", (CASE WHEN AN1.TIPO_UTENTE = 'T' THEN AN1.COGNOME_RAGIONE_SOCIALE || ' ' || AN1.NOME ELSE AN2.COGNOME_RAGIONE_SOCIALE || ' ' || AN2.NOME END) TECNICO ");
      query.append(", AN1.COGNOME_RAGIONE_SOCIALE || ' ' || AN1.NOME UTENTE_INSERIM_DATI  ");
      query.append(", EC.PAGAMENTO PAGAMENTO ");
      query.append(", EC.STATO_ANOMALIA ANOMALIA_SCARTO_SOSP ");
      query.append(", DF.FATTURA_SN FATTURA_RICHIESTA ");
      query.append(", DF.SPEDIZIONE SPEDIZIONE_FATTURA ");
      query.append(", DF.FATTURARE INTESTAZ_FATTURA ");
      //verifica se è stata emessa almeno una fattura per la richiesta di analisi in esame
      query.append(", (CASE WHEN (SELECT MAX(COALESCE(CF.NUMERO_FATTURA, 0)) FROM CAMPIONE_FATTURATO CF WHERE CF.ID_RICHIESTA = EC.ID_RICHIESTA) > 0 THEN 'Si' ELSE 'No' END) FATTURA_EMESSA ");
      //aggregazione delle fatture emesse per la richiesta di analisi in esame
      query.append(", (SELECT STRING_AGG(CF2.ANNO || '/' || CF2.NUMERO_FATTURA, ', ') ");
      query.append("FROM CAMPIONE_FATTURATO CF2 ");
      query.append("WHERE CF2.ID_RICHIESTA = EC.ID_RICHIESTA ");
      query.append("GROUP BY CF2.ID_RICHIESTA) NUMERO_FATTURA ");
      query.append(", DA.SEZIONE SEZIONE ");
      query.append(", DA.FOGLIO FOGLIO ");
      query.append(", DA.PARTICELLA_CATASTALE PARTICELLA_CATASTALE_PRINCIPALE ");
      query.append(", DA.SUBPARTICELLA ALTRE_PARTICELLE_SECONDARIE ");
      query.append(", DA.COORDINATA_NORD_UTM COORD_NORD_UTM ");
      query.append(", DA.COORDINATA_EST_UTM COORD_EST_UTM ");
      query.append(", DA.COORDINATA_NORD_BOAGA COORD_NORD_BOAGA ");
      query.append(", DA.COORDINATA_NORD_BOAGA COORD_EST_BOAGA ");
      query.append(", DA.CODICE_COORDINATE_GRADI TIPO_COORD_GEOGRAFICHE ");
      query.append(", DA.COORDINATA_NORD_GRADI COORD_GEOGR_NORD_GRADI ");
      query.append(", DA.COORDINATA_NORD_MINUTI COORD_GEOGR_NORD_MINUTI ");
      query.append(", DA.COORDINATA_NORD_SECONDI COORD_GEOGR_NORD_SECONDI ");
      query.append(", DA.COORDINATA_EST_GRADI COORD_GEOGR_EST_GRADI ");
      query.append(", DA.COORDINATA_EST_MINUTI COORD_GEOGR_EST_MINUTI ");
      query.append(", DA.COORDINATA_EST_SECONDI COORD_GEOGR_EST_SECONDI ");
      query.append(", AN3.COGNOME_RAGIONE_SOCIALE || ' ' || COALESCE(AN3.NOME,'') PROPRIETARIO ");
      query.append(", PP.DESCRIZIONE PROFONDITA_PRELIEVO ");
      query.append(", DCT.GIACITURA GIACITURA ");
      query.append(", DCT.SCHELETRO PRESENZA_PIETRE_GHIAIE ");
      query.append(", DCT.STOPPIE INTERRAMENTO_STOPPIE ");
      query.append(", CO.DESCRIZIONE CONCIMAZIONE_ORGANICA ");
      query.append(", CONC.DESCRIZIONE CONCIME_UTILIZZATO ");
      query.append(", LT.DESCRIZIONE STATO_TERRENO ");
      query.append(", IRR.DESCRIZIONE IRRIGAZIONE ");
      query.append(", MC.DESCRIZIONE MODALITA_COLTIVAZIONE ");
      
//      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'TIPO' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR "); 
//      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'TIPO') ANALISI_TIPO ");
//      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'CO' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
//      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'CO') COMPLESSO_SCAMBIO ");  
      
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'pH' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'TIPO' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("    WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI in ('pH', 'TIPO')) ANALISI_PH ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'Ca' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'TIPO' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'CO' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("    WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI in ('Ca','TIPO','CO')) ANALISI_CA ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'Mg' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'TIPO' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'CO' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("    WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI in ('Mg', 'TIPO', 'CO')) ANALISI_MG ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'K' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'TIPO' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'CO' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI in ('K','TIPO','CO')) ANALISI_K ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'N' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'TIPO' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI in ('N','TIPO')) ANALISI_N ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'P' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'TIPO' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR "); 
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI in ('P', 'TIPO')) ANALISI_P ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'CSC' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'TIPO' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'CO' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI in ('CSC', 'TIPO', 'CO')) ANALISI_CSC ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'SO' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'TIPO' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI in ('SO','TIPO')) ANALISI_SOST_ORG ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'CaCO3' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'TIPO' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI in ('CaCO3', 'TIPO')) ANALISI_CACO3 ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'CaAtt' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'CaAtt') ANALISI_CAATT ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'Std' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'Std') ANALISI_STD "); 
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = '4Fra' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");  
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = '4Fra') ANALISI_4FRA ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = '5Fra' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = '5Fra') ANALISI_5FRA ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'Sal' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");  
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'Sal') ANALISI_SAL ");
      
//      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'MICRO' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR "); 
//      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'MICRO') MICROELEMENTI ");   
      
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'Fe' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'MICRO' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR  ");
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI in ('Fe','MICRO')) ANALISI_FE ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'Mn' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'MICRO' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI in ('Mn', 'MICRO')) ANALISI_MN ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'Zn' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'MICRO' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI in ('Zn', 'MICRO')) ANALISI_ZN ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'Cu' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'MICRO' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR "); 
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI in ('Cu', 'MICRO')) ANALISI_CU "); 
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'B' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'B') ANALISI_B ");
      
//      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'UM' THEN 'SI' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
//      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'UM') ANALISI_UMIDITA ");
      
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = ('FeTot') THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("    WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'FeTot') FERRO_TOTALE ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = ('CuTot') THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("    WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'CuTot') RAME_TOTALE ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = ('ZnTot') THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("    WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'ZnTot') ZINCO_TOTALE ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = ('MnTot') THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("    WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'MnTot') MANGANESE_TOTALE ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = ('CdTot') THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("    WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'CdTot') CADMIO_TOTALE ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = ('CrTot') THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("    WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'CrTot') CROMO_TOTALE ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = ('NiTot') THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("    WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'NiTot') NICHEL_TOTALE ");      
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = ('PbTot') THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("    WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'PbTot') PIOMBO_TOTALE ");
      query.append(", UM.UMIDITA_CAMPIONE UMIDITA_P ");
      query.append(", UM.SOSTANZA_SECCA SOSTANZA_SECCA_P ");
      query.append(", COALESCE(GS.ARGILLA, COALESCE(G4F.ARGILLA,G5F.ARGILLA)) ARGILLA_P ");
      query.append(", COALESCE(GS.LIMO_TOTALE, COALESCE(G4F.LIMO_TOTALE,G5F.LIMO_TOTALE)) LIMO_TOTALE_P ");
      query.append(", COALESCE(G4F.LIMO_GROSSO, G5F.LIMO_GROSSO) LIMO_GROSSO_P ");
      query.append(", COALESCE(G4F.LIMO_FINE, G5F.LIMO_FINE) LIMO_FINE_P ");
      query.append(", COALESCE(GS.SABBIA_TOTALE, COALESCE(G4F.SABBIA_TOTALE,G5F.SABBIA_TOTALE)) SABBIA_TOTALE_P ");
      query.append(", G5F.SABBIA_GROSSA SABBIA_GROSSA_P ");
      query.append(", G5F.SABBIA_FINE SABBIA_FINE_P ");
      query.append(", RS.PH_ACQUA PH_ACQUA ");
      query.append(", RS.PH_CLORURO_POTASSIO PH_CLORURO_POTASSIO ");
      query.append(", RS.PH_TAMPONE PH_TAMPONE ");
      query.append(", CON.CONDUCIBILITA CONDUC_SALINITA ");
      query.append(", CA.CARBONATO_CALCIO_TOTALE CALCARE_TOTALE_P ");
      query.append(", CA.CALCARE_ATTIVO CALCARE_ATTIVO_P ");
      query.append(", COALESCE(SO.SOSTANZA_ORGANICA, SO.SOSTANZA_ORGANICA_METODO_ANA) SOSTANZA_ORG_P ");
      query.append(", COALESCE(SO.CARBONIO_ORGANICO, SO.CARBONIO_ORGANICO_METODO_ANA) CARBONIO_ORG_P ");
      query.append(", AZ.AZOTO_TOTALE_METODO_ANA AZOTO_TOTALE_P ");
      query.append(", AZ.RAPPORTO_C_N RAPPORTO_C_N ");
      query.append(", CS.CAPACITA_SCAMBIO_CATIONICO CSC_MEQ_100G ");
      query.append(", CS.CALCIO_SCAMBIABILE CALCIO_SCAMB_PPM ");
      query.append(", CS.CALCIO_SCAMBIABILE_MEQ_100 CALCIO_SCAMB_MEQ_100 ");
      query.append(", CS.CALCIO_SCAMBIABILE_CSC CALCIO_SCAMB_SU_CSC_P ");
      query.append(", CS.MAGNESIO_SCAMBIABILE MAGNESIO_SCAMB_PPM ");
      query.append(", CS.MAGNESIO_SCAMBIABILE_MEQ_100 MAGNESIO_SCAMB_MEQ_100G ");
      query.append(", CS.MAGNESIO_SCAMBIABILE_CSC MAGNESIO_SCAMB_SU_CSC_P ");
      query.append(", CS.POTASSIO_SCAMBIABILE POTASSIO_SCAMB_PPM ");
      query.append(", CS.POTASSIO_SCAMBIABILE_MEQ_100 POTASSIO_SCAMB_MEQ_100G ");
      query.append(", CS.POTASSIO_SCAMBIABILE_CSC POTASSIO_SCAMB_SU_CSC_P ");
      query.append(", CS.SODIO_SCAMBIABILE SODIO_SCAMB_PPM ");
      query.append(", CS.SODIO_SCAMBIABILE_MEQ_100 SODIO_SCAMB_MEQ_100G ");
      query.append(", CS.SODIO_SCAMBIABILE_CSC SODIO_SCAMB_SU_CSC ");
      query.append(", CS.CA_MG RAPPORTO_CA_MG ");
      query.append(", CS.CA_K RAPPORTO_CA_K ");
      query.append(", CS.MG_K RAPPORTO_MG_K ");
      query.append(", CS.SATURAZIONE_BASICA SATURAZ_BASICA ");
      query.append(", FO.FOSFORO_ASSIMILABILE FOSFORO_ASSIM_PPM ");
      query.append(", FO.ANIDRIDE_FOSFORICA ANIDRIDE_FOSFORICA_ASSIM_PPM ");
      query.append(", MEL.FERRO_ASSIMILABILE FERRO_ASSIM_PPM ");
      query.append(", MEL.MANGANESE_ASSIMILABILE MANGANESE_ASSIM_PPM ");
      query.append(", MEL.ZINCO_ASSIMILABILE ZINCO_ASSIM_PPM ");
      query.append(", MEL.RAME_ASSIMILABILE RAME_ASSIM_PPM ");
      query.append(", MEL.BORO_ASSIMILABILE BORO_ASSIM_PPM ");
      query.append(", MP.FERRO_TOTALE MP_FERRO_TOTALE ");
      query.append(", MP.MANGANESE_TOTALE MP_MANGANESE_TOTALE ");
      query.append(", MP.ZINCO_TOTALE MP_ZINCO_TOTALE ");
      query.append(", MP.RAME_TOTALE MP_RAME_TOTALE ");
      query.append(", MP.PIOMBO_TOTALE MP_PIOMBO_TOTALE ");
      query.append(", MP.CROMO_TOTALE MP_CROMO_TOTALE ");
      query.append(", MP.NICHEL_TOTALE MP_NICHEL_TOTALE ");
      query.append(", MP.CADMIO_TOTALE MP_CADMIO_TOTALE ");
      query.append(", FR.NOTE NOTE_LABORATORIO ");
      query.append("FROM ETICHETTA_CAMPIONE EC ");
      query.append("LEFT JOIN ANAGRAFICA AN1 ON AN1.ID_ANAGRAFICA = EC.ANAGRAFICA_UTENTE ");
      query.append("LEFT JOIN ORGANIZZAZIONE_PROFESSIONALE OP1 ON OP1.ID_ORGANIZZAZIONE = AN1.ID_ORGANIZZAZIONE ");
      query.append("LEFT JOIN TIPO_ORGANIZZAZIONE TO1 ON TO1.ID_TIPO_ORGANIZZAZIONE = OP1.ID_TIPO_ORGANIZZAZIONE ");
      query.append("LEFT JOIN ANAGRAFICA AN2 ON AN2.ID_ANAGRAFICA = EC.ANAGRAFICA_TECNICO ");
      query.append("LEFT JOIN ORGANIZZAZIONE_PROFESSIONALE OP2 ON OP2.ID_ORGANIZZAZIONE = AN2.ID_ORGANIZZAZIONE ");
      query.append("LEFT JOIN TIPO_ORGANIZZAZIONE TO2 ON TO2.ID_TIPO_ORGANIZZAZIONE = OP2.ID_TIPO_ORGANIZZAZIONE ");
      query.append("LEFT JOIN ANAGRAFICA AN3 ON AN3.ID_ANAGRAFICA = EC.ANAGRAFICA_PROPRIETARIO ");
      query.append("LEFT JOIN MATERIALE MA ON MA.CODICE_MATERIALE = EC.CODICE_MATERIALE ");
      query.append("LEFT JOIN CODIFICA_TRACCIABILITA CT ON CT.CODICE = EC.STATO_ATTUALE ");
      query.append("LEFT JOIN LABORATORIO LAB_CONS ON LAB_CONS.CODICE_LABORATORIO = EC.LABORATORIO_CONSEGNA ");
      query.append("LEFT JOIN COMUNE COM_CONS ON COM_CONS.CODICE_ISTAT = LAB_CONS.COMUNE ");
      query.append("LEFT JOIN LABORATORIO LAB_ANA ON LAB_ANA.CODICE_LABORATORIO = EC.LABORATORIO_ANALISI ");
      query.append("LEFT JOIN COMUNE COM_ANA ON COM_ANA.CODICE_ISTAT = LAB_ANA.COMUNE ");
      query.append("LEFT JOIN DATI_FATTURA DF ON DF.ID_RICHIESTA = EC.ID_RICHIESTA ");
      query.append("LEFT JOIN MODALITA_DI_CONSEGNA MDC ON MDC.CODICE_MODALITA = EC.CODICE_MODALITA ");
      query.append("LEFT JOIN ANALISI_DATI AD ON AD.ID_RICHIESTA = EC.ID_RICHIESTA ");
      query.append("LEFT JOIN DATI_APPEZZAMENTO DA ON DA.ID_RICHIESTA = EC.ID_RICHIESTA ");
      query.append("LEFT JOIN COMUNE COM_CAMP ON COM_CAMP.CODICE_ISTAT = DA.COMUNE_APPEZZAMENTO ");
      query.append("LEFT JOIN PROVINCIA PR ON PR.ID_PROVINCIA = COM_CAMP.PROVINCIA ");
      query.append("LEFT JOIN DATI_CAMPIONE_TERRENO DCT ON DCT.ID_RICHIESTA = EC.ID_RICHIESTA ");
      query.append("LEFT JOIN CONCIMAZIONE_ORGANICA CO ON (DCT.TIPO_CONCIMAZIONE = CO.TIPO_CONCIMAZIONE) ");
      query.append("LEFT JOIN CONCIME CONC ON (DCT.ID_CONCIME = CONC.ID_CONCIME) ");
      query.append("LEFT JOIN LAVORAZIONE_TERRENO LT ON (DCT.ID_LAVORAZIONE_TERRENO = LT.ID_LAVORAZIONE_TERRENO) ");
      query.append("LEFT JOIN IRRIGAZIONE IRR ON (DCT.ID_IRRIGAZIONE = IRR.ID_IRRIGAZIONE) ");
      query.append("LEFT JOIN MODALITA_COLTIVAZIONE MC ON (DCT.CODICE_MODALITA_COLTIVAZIONE = MC.CODICE_MODALITA_COLTIVAZIONE) ");
      query.append("LEFT JOIN PROFONDITA_PRELIEVO PP ON DCT.ID_PROFONDITA = PP.ID_PROFONDITA ");
      query.append("LEFT JOIN SPECIE_COLTURA SC_ATT ON SC_ATT.ID_SPECIE = DCT.COLTURA_ATTUALE ");
      query.append("LEFT JOIN SPECIE_COLTURA SC_PREV ON SC_PREV.ID_SPECIE = DCT.COLTURA_PREVISTA ");
      query.append("LEFT JOIN FIRMA_REFERTO FR ON FR.ID_RICHIESTA = EC.ID_RICHIESTA ");
      query.append("LEFT JOIN CODICE_MISURA_PSR CMP ON CMP.CODICE_MISURA_PSR = EC.CODICE_MISURA_PSR ");
      query.append("LEFT JOIN UMIDITA_CAMPIONE UM ON EC.ID_RICHIESTA = UM.ID_RICHIESTA ");
      query.append("LEFT JOIN GRANULOMETRIA_STANDARD GS ON EC.ID_RICHIESTA = GS.ID_RICHIESTA ");
      query.append("LEFT JOIN GRANULOMETRIA_A_4_FRAZIONI G4F ON EC.ID_RICHIESTA = G4F.ID_RICHIESTA ");
      query.append("LEFT JOIN GRANULOMETRIA_A_5_FRAZIONI G5F ON EC.ID_RICHIESTA = G5F.ID_RICHIESTA ");
      query.append("LEFT JOIN REAZIONE_SUOLO RS ON EC.ID_RICHIESTA = RS.ID_RICHIESTA ");
      query.append("LEFT JOIN CONDUCIBILITA_SALINITA CON ON EC.ID_RICHIESTA = CON.ID_RICHIESTA ");
      query.append("LEFT JOIN CALCIMETRIA CA ON EC.ID_RICHIESTA = CA.ID_RICHIESTA ");
      query.append("LEFT JOIN SOSTANZA_ORGANICA SO ON EC.ID_RICHIESTA = SO.ID_RICHIESTA ");
      query.append("LEFT JOIN AZOTO AZ ON EC.ID_RICHIESTA = AZ.ID_RICHIESTA ");
      query.append("LEFT JOIN COMPLESSO_SCAMBIO CS ON EC.ID_RICHIESTA = CS.ID_RICHIESTA ");
      query.append("LEFT JOIN FOSFORO_METODO_OLSEN FO ON EC.ID_RICHIESTA = FO.ID_RICHIESTA ");
      query.append("LEFT JOIN MICROELEMENTI_METODO_DTPA MEL ON EC.ID_RICHIESTA = MEL.ID_RICHIESTA ");
      query.append("LEFT JOIN METALLI_PESANTI MP ON EC.ID_RICHIESTA = MP.ID_RICHIESTA ");
      //jira 232 esportazione solo dei TERRENI
      query.append("WHERE EC.CODICE_MATERIALE IN ('TER') ");
      if (idRichieste != null && idRichieste.size() > 0) {
      	query.append(getInCondition("EC.ID_RICHIESTA", idRichieste));
      }
      query.append(" ORDER BY EC.ID_RICHIESTA DESC ");

      CuneoLogger.debug(this, query.toString());

      ResultSet rset = stmt.executeQuery(query.toString());

      while (rset.next())
      {
      	Object[] rigaEsportaRichieste = new Object[]
      	{
      			rset.getString("NUMERO_RICHIESTA"), rset.getString("ANNO"), rset.getString("NUM_CAMPIONE"),
      			rset.getString("ETICHETTA"), rset.getString("LABORATORIO_CONSEGNA"), rset.getString("TIPO_CONSEGNA"),
      			rset.getString("ANALISI_SVOLTA_ADEMP_PSR"), rset.getString("MOTIVAZ_ADEMP_PSR"), rset.getString("NOTE_RICHIEDENTE"),
      			rset.getString("DATA_ACCETTAZIONE"), rset.getString("DATA_EMISSIONE_REFERTO"), rset.getString("STATO_RICHIESTA"),
      			rset.getString("NOTE_STATO_RICHIESTA"), rset.getString("ORGANIZZAZIONE"), rset.getString("TECNICO"),
      			rset.getString("UTENTE_INSERIM_DATI"), Utili.getDecodificaPagamento(rset.getString("PAGAMENTO")), Utili.getDecodificaStatoAnomalia(rset.getString("ANOMALIA_SCARTO_SOSP")),
      			Utili.getDecodificaFlagSiNo(rset.getString("FATTURA_RICHIESTA")), Utili.getDecodificaFlagSiNo(rset.getString("SPEDIZIONE_FATTURA")), Utili.getDecodificaIntestazioneFattura(rset.getString("INTESTAZ_FATTURA")),
      			rset.getString("FATTURA_EMESSA"), rset.getString("NUMERO_FATTURA"), rset.getString("COMUNE_CAMPIONE"),
      			rset.getString("SIGLA_PROVINCIA_CAMPIONE"), rset.getString("SEZIONE"), rset.getString("FOGLIO"),
      			rset.getString("PARTICELLA_CATASTALE_PRINCIPALE"), rset.getString("ALTRE_PARTICELLE_SECONDARIE"), rset.getString("COORD_NORD_UTM"),
      			rset.getString("COORD_EST_UTM"), rset.getString("COORD_NORD_BOAGA"), rset.getString("COORD_EST_BOAGA"),
      			rset.getString("TIPO_COORD_GEOGRAFICHE"), rset.getString("COORD_GEOGR_NORD_GRADI"), rset.getString("COORD_GEOGR_NORD_MINUTI"),
      			rset.getString("COORD_GEOGR_NORD_SECONDI"), rset.getString("COORD_GEOGR_EST_GRADI"), rset.getString("COORD_GEOGR_EST_MINUTI"),
      			rset.getString("COORD_GEOGR_EST_SECONDI"), rset.getString("PROPRIETARIO"), rset.getString("PROFONDITA_PRELIEVO"),
      			rset.getString("GIACITURA"), rset.getString("COLTURA_ATTUALE"), rset.getString("COLTURA_PREVISTA"),
      			rset.getString("PRESENZA_PIETRE_GHIAIE"), rset.getString("INTERRAMENTO_STOPPIE"), rset.getString("CONCIMAZIONE_ORGANICA"),
      			rset.getString("CONCIME_UTILIZZATO"), rset.getString("STATO_TERRENO"), rset.getString("IRRIGAZIONE"),
      			rset.getString("MODALITA_COLTIVAZIONE"), 
      			//rset.getString("ANALISI_TIPO"), rset.getString("COMPLESSO_SCAMBIO"),
      			rset.getString("ANALISI_PH"), rset.getString("ANALISI_CA"), rset.getString("ANALISI_MG"),
      			rset.getString("ANALISI_K"), rset.getString("ANALISI_N"), rset.getString("ANALISI_P"),
      			rset.getString("ANALISI_CSC"), rset.getString("ANALISI_SOST_ORG"), rset.getString("ANALISI_CACO3"),
      			rset.getString("ANALISI_CAATT"), rset.getString("ANALISI_STD"), rset.getString("ANALISI_4FRA"),
      			rset.getString("ANALISI_5FRA"), rset.getString("ANALISI_SAL"), 
      			//rset.getString("MICROELEMENTI"),
      			rset.getString("ANALISI_FE"), rset.getString("ANALISI_MN"), rset.getString("ANALISI_ZN"),
      			rset.getString("ANALISI_CU"), rset.getString("ANALISI_B"), 
      			//rset.getString("ANALISI_UMIDITA"),
      			rset.getString("FERRO_TOTALE"), rset.getString("RAME_TOTALE"), rset.getString("ZINCO_TOTALE"),
      			rset.getString("MANGANESE_TOTALE"), rset.getString("CADMIO_TOTALE"), rset.getString("CROMO_TOTALE"),
      			rset.getString("NICHEL_TOTALE"), rset.getString("PIOMBO_TOTALE"), rset.getString("NOTE_AGGIUNTIVE"),
      			//rset.getBigDecimal("UMIDITA_P"), rset.getBigDecimal("SOSTANZA_SECCA_P"), 
      			rset.getBigDecimal("ARGILLA_P"),
      			rset.getBigDecimal("LIMO_TOTALE_P"), rset.getBigDecimal("LIMO_GROSSO_P"), rset.getBigDecimal("LIMO_FINE_P"),
      			rset.getBigDecimal("SABBIA_TOTALE_P"), rset.getBigDecimal("SABBIA_GROSSA_P"), rset.getBigDecimal("SABBIA_FINE_P"),
      			rset.getBigDecimal("PH_ACQUA"), rset.getBigDecimal("PH_CLORURO_POTASSIO"), rset.getBigDecimal("PH_TAMPONE"),
      			rset.getBigDecimal("CONDUC_SALINITA"), rset.getBigDecimal("CALCARE_TOTALE_P"), rset.getBigDecimal("CALCARE_ATTIVO_P"),
      			rset.getBigDecimal("SOSTANZA_ORG_P"), rset.getBigDecimal("CARBONIO_ORG_P"), rset.getBigDecimal("AZOTO_TOTALE_P"),
      			rset.getBigDecimal("RAPPORTO_C_N"), rset.getBigDecimal("CSC_MEQ_100G"), rset.getBigDecimal("CALCIO_SCAMB_PPM"),
      			rset.getBigDecimal("CALCIO_SCAMB_MEQ_100"), rset.getBigDecimal("CALCIO_SCAMB_SU_CSC_P"), rset.getBigDecimal("MAGNESIO_SCAMB_PPM"),
      			rset.getBigDecimal("MAGNESIO_SCAMB_MEQ_100G"), rset.getBigDecimal("MAGNESIO_SCAMB_SU_CSC_P"), rset.getBigDecimal("POTASSIO_SCAMB_PPM"),
      			rset.getBigDecimal("POTASSIO_SCAMB_MEQ_100G"), rset.getBigDecimal("POTASSIO_SCAMB_SU_CSC_P"), rset.getBigDecimal("SODIO_SCAMB_PPM"),
      			rset.getBigDecimal("SODIO_SCAMB_MEQ_100G"), rset.getBigDecimal("SODIO_SCAMB_SU_CSC"), rset.getBigDecimal("RAPPORTO_CA_MG"),
      			rset.getBigDecimal("RAPPORTO_CA_K"), rset.getBigDecimal("RAPPORTO_MG_K"), rset.getBigDecimal("SATURAZ_BASICA"),
      			rset.getBigDecimal("FOSFORO_ASSIM_PPM"), rset.getBigDecimal("ANIDRIDE_FOSFORICA_ASSIM_PPM"), rset.getBigDecimal("FERRO_ASSIM_PPM"),
      			rset.getBigDecimal("MANGANESE_ASSIM_PPM"), rset.getBigDecimal("ZINCO_ASSIM_PPM"), rset.getBigDecimal("RAME_ASSIM_PPM"),
      			rset.getBigDecimal("BORO_ASSIM_PPM"), rset.getBigDecimal("MP_FERRO_TOTALE"), rset.getBigDecimal("MP_MANGANESE_TOTALE"),
      			rset.getBigDecimal("MP_ZINCO_TOTALE"), rset.getBigDecimal("MP_RAME_TOTALE"), rset.getBigDecimal("MP_PIOMBO_TOTALE"),
      			rset.getBigDecimal("MP_CROMO_TOTALE"), rset.getBigDecimal("MP_NICHEL_TOTALE"), rset.getBigDecimal("MP_CADMIO_TOTALE"),
      			rset.getString("NOTE_LABORATORIO")		
      	};

      	elenco.add(rigaEsportaRichieste);
      }
      rset.close();
      stmt.close();
    }
    catch (java.sql.SQLException ex)
    {
      this.getAut().setQuery("esportaRichieste della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch (Exception e)
    {
      this.getAut().setQuery("esportaRichieste della classe EtichettaCampione"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      if (conn!=null) conn.close();
    }
  	
  	return elenco;
  }
  
  
  
  public Vector<Object[]> esportaContabile(BeanParametri beanParametriApplication, Vector<Long> idRichieste) throws Exception, SQLException{
    if (! isConnection()) 
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn = null;
    StringBuffer query = new StringBuffer("");
    Vector<Object[]> elenco = new Vector<Object[]>();
    try{
      conn = getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("SELECT MA.DESCRIZIONE, EC.ID_RICHIESTA NUMERO_RICHIESTA ");
      query.append(", EC.ANNO ANNO ");
      query.append(", TO_CHAR(EC.DATA_INSERIMENTO_RICHIESTA,'DD/MM/YYYY') AS DATA_INSERIMENTO_RICHIESTA ");
      query.append(", EC.NUMERO_CAMPIONE NUM_CAMPIONE ");  
      query.append(", EC.DESCRIZIONE_ETICHETTA ETICHETTA ");
      query.append(", COM_CONS.DESCRIZIONE LABORATORIO_CONSEGNA ");
      query.append(", TO_CHAR((SELECT MIN(DATA) FROM TRACCIATURA TR WHERE TR.ID_RICHIESTA = EC.ID_RICHIESTA AND CODICE = '20'),'DD/MM/YYYY') DATA_ACCETTAZIONE ");
      query.append(", TO_CHAR((SELECT MAX(DATA) FROM TRACCIATURA TR WHERE TR.ID_RICHIESTA = EC.ID_RICHIESTA AND CODICE = '40'),'DD/MM/YYYY') DATA_EMISSIONE_REFERTO ");
      query.append(", CT.DESCRIZIONE STATO_RICHIESTA ");
      query.append(", (CASE WHEN AN1.TIPO_UTENTE = 'T' THEN OP1.RAGIONE_SOCIALE ELSE OP2.RAGIONE_SOCIALE END) ORGANIZZAZIONE ");
      query.append(", (CASE WHEN AN1.TIPO_UTENTE = 'T' THEN AN1.COGNOME_RAGIONE_SOCIALE || ' ' || AN1.NOME ELSE AN2.COGNOME_RAGIONE_SOCIALE || ' ' || AN2.NOME END) TECNICO ");
      query.append(", AN1.COGNOME_RAGIONE_SOCIALE || ' ' || AN1.NOME UTENTE_INSERIM_DATI  ");
      query.append(", EC.PAGAMENTO AS PAGAMENTO_ANALISI ");
      query.append(", TO_CHAR(EC.DATA_INCASSO,'DD/MM/YYYY') AS DATA_INCASSO ");
      query.append(", EC.STATO_ANOMALIA ANOMALIA_SCARTO_SOSP ");
      query.append(", DF.FATTURA_SN FATTURA_RICHIESTA ");
      query.append(", (CASE WHEN (SELECT MAX(COALESCE(CF.NUMERO_FATTURA, 0)) FROM CAMPIONE_FATTURATO CF WHERE CF.ID_RICHIESTA = EC.ID_RICHIESTA) > 0 THEN 'Si' ELSE 'No' END) FATTURA_EMESSA ");
      //aggregazione delle fatture emesse per la richiesta di analisi in esame
      query.append(", (SELECT STRING_AGG(CF2.ANNO || '/' || CF2.NUMERO_FATTURA, ', ') ");
      query.append("FROM CAMPIONE_FATTURATO CF2 ");
      query.append("WHERE CF2.ID_RICHIESTA = EC.ID_RICHIESTA ");
      query.append("GROUP BY CF2.ID_RICHIESTA) NUMERO_FATTURA ");
      query.append(", (CASE WHEN DF.FATTURARE='U' THEN 'Utente' "+
    		  			" 	WHEN DF.FATTURARE='P' THEN 'Proprietario' "+
    		  			"   WHEN DF.FATTURARE='T' THEN 'Tecnico' "+
			  			"   WHEN DF.FATTURARE='O' THEN 'Organizzazione'  "+
			  			"   WHEN DF.FATTURARE='A' THEN 'Altri Estremi'  "+
			  			"   ELSE ''  "+
			  			"   END) as INTESTAZ_FATTURA ");
      query.append(",(CASE WHEN DF.FATTURARE = 'U' THEN (SELECT codice_destinatario FROM anagrafica WHERE id_anagrafica = EC.anagrafica_utente)  " + 
      				"     WHEN DF.FATTURARE = 'P' THEN (SELECT codice_destinatario FROM anagrafica WHERE id_anagrafica = EC.anagrafica_proprietario)  " + 
		      		"     WHEN DF.FATTURARE = 'T' THEN (SELECT codice_destinatario FROM anagrafica WHERE id_anagrafica = EC.anagrafica_tecnico)  " + 
		      		"     WHEN DF.FATTURARE = 'O' THEN (CASE WHEN EC.anagrafica_tecnico IS NULL " + 
		      		"                                      THEN (SELECT op.codice_destinatario FROM anagrafica a LEFT JOIN organizzazione_professionale op ON a.id_organizzazione = op.id_organizzazione WHERE a.id_anagrafica = ec.anagrafica_utente) " + 
		      		"                                      ELSE (SELECT op.codice_destinatario FROM anagrafica a LEFT JOIN organizzazione_professionale op ON a.id_organizzazione = op.id_organizzazione WHERE a.id_anagrafica = ec.anagrafica_tecnico) END)   " + 
		      		"     WHEN DF.FATTURARE = 'A' THEN '' END) AS COD_DEST ");
      query.append(",(CASE WHEN DF.FATTURARE = 'U' THEN (SELECT codice_contabilia FROM anagrafica WHERE id_anagrafica = EC.anagrafica_utente)  " + 
		      		"     WHEN DF.FATTURARE = 'P' THEN (SELECT codice_contabilia FROM anagrafica WHERE id_anagrafica = EC.anagrafica_proprietario)  " + 
		      		"     WHEN DF.FATTURARE = 'T' THEN (SELECT codice_contabilia FROM anagrafica WHERE id_anagrafica = EC.anagrafica_tecnico)  " + 
		      		"     WHEN DF.FATTURARE = 'O' THEN (CASE WHEN EC.anagrafica_tecnico IS NULL " + 
		      		"                                      THEN (SELECT op.codice_contabilia FROM anagrafica a LEFT JOIN organizzazione_professionale op ON a.id_organizzazione = op.id_organizzazione WHERE a.id_anagrafica = ec.anagrafica_utente) " + 
		      		"                                      ELSE (SELECT op.codice_contabilia FROM anagrafica a LEFT JOIN organizzazione_professionale op ON a.id_organizzazione = op.id_organizzazione WHERE a.id_anagrafica = ec.anagrafica_tecnico) END)   " + 
		      		"     WHEN DF.FATTURARE = 'A' THEN '' END) AS COD_CONTABILIA ");
      query.append(",(CASE WHEN DF.FATTURARE = 'U' THEN (SELECT pec FROM anagrafica WHERE id_anagrafica = EC.anagrafica_utente)  " + 
		      		"     WHEN DF.FATTURARE = 'P' THEN (SELECT pec FROM anagrafica WHERE id_anagrafica = EC.anagrafica_proprietario)  " + 
		      		"     WHEN DF.FATTURARE = 'T' THEN (SELECT pec FROM anagrafica WHERE id_anagrafica = EC.anagrafica_tecnico)  " + 
		      		"     WHEN DF.FATTURARE = 'O' THEN (CASE WHEN EC.anagrafica_tecnico IS NULL " + 
		      		"                                      THEN (SELECT op.pec FROM anagrafica a LEFT JOIN organizzazione_professionale op ON a.id_organizzazione = op.id_organizzazione WHERE a.id_anagrafica = ec.anagrafica_utente) " + 
		      		"                                      ELSE (SELECT op.pec FROM anagrafica a LEFT JOIN organizzazione_professionale op ON a.id_organizzazione = op.id_organizzazione WHERE a.id_anagrafica = ec.anagrafica_tecnico) END)   " + 
		      		"     WHEN DF.FATTURARE = 'A' THEN '' END) AS PEC_AN ");
      query.append(", (CASE WHEN DF.FATTURARE='U' THEN (select cognome_ragione_sociale from anagrafica where id_anagrafica=EC.anagrafica_utente) "+
  			 			  " WHEN DF.FATTURARE='P' THEN (select cognome_ragione_sociale from anagrafica where id_anagrafica=EC.anagrafica_proprietario)  "+
  			 			  " WHEN DF.FATTURARE='T' THEN (select cognome_ragione_sociale||' '||nome from anagrafica where id_anagrafica=EC.anagrafica_tecnico)  "+
  			 			  " WHEN DF.FATTURARE='O' THEN (  "+
  		                    "CASE WHEN EC.anagrafica_tecnico IS NULL "+
  		                          "THEN (select op.ragione_sociale from anagrafica a  "+
              				            	"left join organizzazione_professionale op on a.id_organizzazione = op.id_organizzazione "+
  		              				        "where a.id_anagrafica=ec.anagrafica_utente) "+
  		                    "ELSE (select op.ragione_sociale from anagrafica a "+
	      				            "left join organizzazione_professionale op on a.id_organizzazione = op.id_organizzazione "+
  		      				        "where a.id_anagrafica=ec.anagrafica_tecnico)    "+
  		                    "END) "+
    		  			  " WHEN DF.FATTURARE='A' THEN DF.ragione_sociale END) as DENOMINAZIONE ");
      
      query.append(", (CASE WHEN DF.FATTURARE='U' THEN (select indirizzo from anagrafica where id_anagrafica=ec.anagrafica_utente) "+
			  			  " WHEN DF.FATTURARE='P' THEN (select indirizzo from anagrafica where id_anagrafica=ec.anagrafica_proprietario)  "+
			  			  " WHEN DF.FATTURARE='T' THEN (select indirizzo from anagrafica where id_anagrafica=ec.anagrafica_tecnico)  "+
			  			  " WHEN DF.FATTURARE='O' THEN (" + 
			  			  " 	CASE WHEN EC.anagrafica_tecnico IS NULL " + 
			  			  "      		THEN (select op.indirizzo " + 
			  			  "			            from anagrafica a " + 
			  			  "			            left join organizzazione_professionale op on a.id_organizzazione = op.id_organizzazione " + 
			  			  "			            where a.id_anagrafica=ec.anagrafica_utente) " + 
			  			  "      	ELSE (select op.indirizzo " + 
			  			  "      			from anagrafica a " + 
			  			  "      			left join organizzazione_professionale op on a.id_organizzazione = op.id_organizzazione " + 
			  			  "      			where a.id_anagrafica=ec.anagrafica_tecnico) " + 
			  			  " 	END) "+
			          	  " WHEN DF.FATTURARE='A' THEN DF.indirizzo END) as INDIRI ");
      
      query.append(", (select descrizione from comune where codice_istat in (CASE WHEN DF.FATTURARE='U' THEN (select comune_residenza from anagrafica where id_anagrafica=ec.anagrafica_utente) "+
  			 			"   WHEN DF.FATTURARE='P' THEN (select comune_residenza from anagrafica where id_anagrafica=ec.anagrafica_proprietario)  "+
		  				"   WHEN DF.FATTURARE='T' THEN (select comune_residenza from anagrafica where id_anagrafica=ec.anagrafica_tecnico)  "+
		  				"   WHEN DF.FATTURARE='O' THEN ( " + 
		  				"      	CASE WHEN ec.anagrafica_tecnico IS NULL " + 
		  				"      			THEN (select op.comune_residenza from anagrafica a  " + 
		  				"      				  left join organizzazione_professionale op on a.id_organizzazione = op.id_organizzazione " + 
		  				"      				  where a.id_anagrafica=ec.anagrafica_utente) " + 
		  				"      		ELSE (select op.comune_residenza from anagrafica a  " + 
		  				"      				left join organizzazione_professionale op on a.id_organizzazione = op.id_organizzazione " + 
		  				"      				where a.id_anagrafica=ec.anagrafica_tecnico) " + 
		  				"   	END) "+
		          		"   WHEN DF.FATTURARE='A' THEN DF.comune END)) as COMU ");
      
      query.append(", (select cap from comune where codice_istat in (CASE WHEN DF.FATTURARE='U' THEN (select comune_residenza from anagrafica where id_anagrafica=ec.anagrafica_utente) "+
			 			"   WHEN DF.FATTURARE='P' THEN (select comune_residenza from anagrafica where id_anagrafica=ec.anagrafica_proprietario)  "+
						"   WHEN DF.FATTURARE='T' THEN (select comune_residenza from anagrafica where id_anagrafica=ec.anagrafica_tecnico)  "+
						"   WHEN DF.FATTURARE='O' THEN ( " + 
						"      	CASE WHEN ec.anagrafica_tecnico IS NULL " + 
						"      			THEN (select op.cap from anagrafica a  " + 
						"      				  left join organizzazione_professionale op on a.id_organizzazione = op.id_organizzazione " + 
						"      				  where a.id_anagrafica=ec.anagrafica_utente) " + 
						"      		ELSE (select op.cap from anagrafica a  " + 
						"      				left join organizzazione_professionale op on a.id_organizzazione = op.id_organizzazione " + 
						"      				where a.id_anagrafica=ec.anagrafica_tecnico) " + 
						"   	END) "+
		        		"   WHEN DF.FATTURARE='A' THEN DF.comune END)) as DESCR_CAP ");
      
      query.append(", (select descrizione from provincia where id_provincia = (select provincia from comune where codice_istat in (CASE WHEN DF.FATTURARE='U' THEN (select comune_residenza from anagrafica where id_anagrafica=ec.anagrafica_utente) "+
			 			"   WHEN DF.FATTURARE='P' THEN (select comune_residenza from anagrafica where id_anagrafica=ec.anagrafica_proprietario)  "+
						"   WHEN DF.FATTURARE='T' THEN (select comune_residenza from anagrafica where id_anagrafica=ec.anagrafica_tecnico)  "+
						"   WHEN DF.FATTURARE='O' THEN ( " + 
						"      	CASE WHEN ec.anagrafica_tecnico IS NULL " + 
						"      			THEN (select op.cap from anagrafica a  " + 
						"      				  left join organizzazione_professionale op on a.id_organizzazione = op.id_organizzazione " + 
						"      				  where a.id_anagrafica=ec.anagrafica_utente) " + 
						"      		ELSE (select op.cap from anagrafica a  " + 
						"      				left join organizzazione_professionale op on a.id_organizzazione = op.id_organizzazione " + 
						"      				where a.id_anagrafica=ec.anagrafica_tecnico) " + 
						"   	END) "+
		      			"   WHEN DF.FATTURARE='A' THEN DF.comune END))) as PROVI ");
      
      query.append(", (CASE WHEN DF.FATTURARE='U' THEN (select codice_identificativo from anagrafica where id_anagrafica=ec.anagrafica_utente) "+
			  			  " WHEN DF.FATTURARE='P' THEN (select codice_identificativo from anagrafica where id_anagrafica=ec.anagrafica_proprietario)  "+
			  			  " WHEN DF.FATTURARE='T' THEN (select codice_identificativo from anagrafica where id_anagrafica=ec.anagrafica_tecnico)  "+
			  			  " WHEN DF.FATTURARE='O' THEN ( " + 
			  			  " 	CASE WHEN ec.anagrafica_tecnico IS NULL " + 
			  			  "      		THEN (select op.cf_partita_iva from anagrafica a  " + 
			  			  "      				left join organizzazione_professionale op on a.id_organizzazione = op.id_organizzazione " + 
			  			  "      				where a.id_anagrafica=ec.anagrafica_utente) " + 
			  			  "    		ELSE (select op.cf_partita_iva from anagrafica a  " + 
			  			  "      			left join organizzazione_professionale op on a.id_organizzazione = op.id_organizzazione " + 
			  			  "      			where a.id_anagrafica=ec.anagrafica_tecnico) " + 
			  			  "		END) "+
			          	  " WHEN DF.FATTURARE='A' THEN DF.cf_partita_iva END) as CFPIVA ");
      //cuaa siap, pec siap,denominazione siap,indirizzo siap,partita iva siap,codice fiscale siap DA WS
      query.append(", DF.CODICE_DESTINATARIO AS CODDEST_DF, DF.PEC AS PEC_DF ");
      query.append(", EC.NOTE_CLIENTE AS NOTE_RICHIEDENTE ");
      query.append(", (CASE WHEN EC.STATO_ATTUALE = '00' THEN '' ");
      query.append("        ELSE (SELECT TR.NOTE ");
      query.append("              FROM TRACCIATURA TR ");
      query.append("              WHERE TR.ID_RICHIESTA = EC.ID_RICHIESTA ");
      query.append("              AND TR.PROGRESSIVO = (SELECT MAX(TR2.PROGRESSIVO) ");
      query.append("                                    FROM TRACCIATURA TR2 ");
      query.append("                                    WHERE TR2.ID_RICHIESTA = TR.ID_RICHIESTA ");
      query.append("		                                AND TR2.CODICE = EC.STATO_ATTUALE)) END) NOTE_STATO_RICHIESTA ");
      query.append(", FR.NOTE NOTE_LABORATORIO ");
      query.append(", P.IUV,P.ESITO,TP.COD_TIPO_PAGAMENTO,P.DATA_RICHIESTA_PAGAMENTO,P.DATA_PAGAMENTO,P.DATA_ANNULLAMENTO,P.CF_PARTITA_IVA ");
      query.append(", SUM(AR.COSTO_ANALISI) as COSTO_ANALISI ");// DA QUI -> importo da pagare \ imponibile \iva
      query.append(", PRE.numero_preventivo ");
      query.append(", PRE.codice_fiscale as CF_PREVENTIVO ");
      query.append(", PRE.importo AS IMPORTO_PREVENTIVO ");
      query.append(", PRE.note_aggiuntive AS NOTE_LAB_PR ");
      //FROM
      query.append("FROM ETICHETTA_CAMPIONE EC ");
      query.append("LEFT JOIN ANAGRAFICA AN1 ON AN1.ID_ANAGRAFICA = EC.ANAGRAFICA_UTENTE ");
      query.append("LEFT JOIN ORGANIZZAZIONE_PROFESSIONALE OP1 ON OP1.ID_ORGANIZZAZIONE = AN1.ID_ORGANIZZAZIONE ");
      //query.append("LEFT JOIN TIPO_ORGANIZZAZIONE TO1 ON TO1.ID_TIPO_ORGANIZZAZIONE = OP1.ID_TIPO_ORGANIZZAZIONE ");
      query.append("LEFT JOIN ANAGRAFICA AN2 ON AN2.ID_ANAGRAFICA = EC.ANAGRAFICA_TECNICO ");
      query.append("LEFT JOIN ORGANIZZAZIONE_PROFESSIONALE OP2 ON OP2.ID_ORGANIZZAZIONE = AN2.ID_ORGANIZZAZIONE ");
      //query.append("LEFT JOIN TIPO_ORGANIZZAZIONE TO2 ON TO2.ID_TIPO_ORGANIZZAZIONE = OP2.ID_TIPO_ORGANIZZAZIONE ");
      query.append("LEFT JOIN ANAGRAFICA AN3 ON AN3.ID_ANAGRAFICA = EC.ANAGRAFICA_PROPRIETARIO ");
      query.append("LEFT JOIN MATERIALE MA ON MA.CODICE_MATERIALE = EC.CODICE_MATERIALE ");
      query.append("LEFT JOIN CODIFICA_TRACCIABILITA CT ON CT.CODICE = EC.STATO_ATTUALE ");
      query.append("LEFT JOIN LABORATORIO LAB_CONS ON LAB_CONS.CODICE_LABORATORIO = EC.LABORATORIO_CONSEGNA ");
      query.append("LEFT JOIN COMUNE COM_CONS ON COM_CONS.CODICE_ISTAT = LAB_CONS.COMUNE ");
      query.append("LEFT JOIN LABORATORIO LAB_ANA ON LAB_ANA.CODICE_LABORATORIO = EC.LABORATORIO_ANALISI ");
      query.append("LEFT JOIN COMUNE COM_ANA ON COM_ANA.CODICE_ISTAT = LAB_ANA.COMUNE ");
      query.append("LEFT JOIN DATI_FATTURA DF ON DF.ID_RICHIESTA = EC.ID_RICHIESTA ");
      query.append("left join campione_fatturato CF on DF.id_richiesta = CF.id_richiesta ");
      query.append("left join analisi_richieste AR on DF.id_richiesta = AR.id_richiesta ");
      query.append("LEFT JOIN MODALITA_DI_CONSEGNA MDC ON MDC.CODICE_MODALITA = EC.CODICE_MODALITA ");
      query.append("LEFT JOIN ANALISI_DATI AD ON AD.ID_RICHIESTA = EC.ID_RICHIESTA ");
      query.append("LEFT JOIN DATI_APPEZZAMENTO DA ON DA.ID_RICHIESTA = EC.ID_RICHIESTA ");
      query.append("LEFT JOIN COMUNE COM_CAMP ON COM_CAMP.CODICE_ISTAT = DA.COMUNE_APPEZZAMENTO ");
      query.append("LEFT JOIN PROVINCIA PR ON PR.ID_PROVINCIA = COM_CAMP.PROVINCIA ");
      query.append("LEFT JOIN FIRMA_REFERTO FR ON FR.ID_RICHIESTA = EC.ID_RICHIESTA ");
      query.append("LEFT JOIN PAGAMENTI P ON EC.ID_RICHIESTA = P.ID_RICHIESTA ");
      query.append("LEFT JOIN TIPO_PAGAMENTO TP ON P.ID_TIPO_PAGAMENTO = TP.ID_TIPO_PAGAMENTO ");
      query.append("LEFT JOIN PREVENTIVI PRE ON EC.ID_PREVENTIVI = PRE.ID_PREVENTIVI ");
      query.append("WHERE 1 = 1 ");
      if (idRichieste != null && idRichieste.size() > 0)
      	query.append(getInCondition("EC.ID_RICHIESTA", idRichieste));
      query.append(" group by 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,43,44,45,46 ");//il 42 -> COSTO_ANALISI
      query.append(" ORDER BY EC.ID_RICHIESTA DESC ");
      
      CuneoLogger.debug(this, query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      while (rset.next()){
    	  it.csi.solmr.dto.anag.AnagAziendaVO anag = null;
    	  String Cf_P_Iva = rset.getString("CFPIVA");
    	  if(Cf_P_Iva!=null && !Cf_P_Iva.equals("")) {
    		  anag = beanParametriApplication.getAnagServiceCSIInterface().serviceGetAziendaByCuaa(Cf_P_Iva);
    		  if(anag==null) 
    			  anag = beanParametriApplication.getAnagServiceCSIInterface().serviceGetAziendaByPartitaIva(Cf_P_Iva);
    	  }
    	  DecimalFormat df = new DecimalFormat("#0.00");
      	Object[] rigaEsportaRichieste = new Object[]{
      			rset.getString("DESCRIZIONE"),
				//da tab PAGAMENTI e TIPO_PAGAMENTO
      			rset.getString("DATA_PAGAMENTO"),
				rset.getBigDecimal("COSTO_ANALISI"), 
      			//rset.getString("COSTO_ANALISI").replace(".","/").replace(",", ".").replace("/", ","), //importo da pagare
      			Utili.getImponibileoIVA(rset.getString("COSTO_ANALISI"),false),//imponibile
      			Utili.getImponibileoIVA(rset.getString("COSTO_ANALISI"),true),//iva
      			rset.getString("NUMERO_RICHIESTA"), 
				rset.getString("IUV"),
				rset.getString("CF_PARTITA_IVA"),
				Utili.getDecodificaFlagSiNo(rset.getString("FATTURA_RICHIESTA")), 
				rset.getString("DENOMINAZIONE"), 
				rset.getString("CFPIVA"),
				anag!=null?anag.getCUAA()!=null?anag.getCUAA():"":"",
				anag!=null?anag.getPartitaIVA()!=null?anag.getPartitaIVA():"":"",
				anag!=null?anag.getCodFiscale()!=null?anag.getCodFiscale():"":"",
				rset.getString("COD_CONTABILIA"),
				anag!=null?anag.getDenominazione()!=null?anag.getDenominazione():"":"",
				rset.getString("CODDEST_DF"),
				rset.getString("COD_DEST"),
				rset.getString("PEC_DF"),
				rset.getString("INDIRI"), 
				rset.getString("COMU"), 
      			rset.getString("DESCR_CAP"), 
      			rset.getString("PROVI"), 
      			anag!=null?anag.getSedelegIndirizzo()!=null?anag.getSedelegIndirizzo():"":"",
				anag!=null?anag.getDescComune()!=null?anag.getDescComune():"":"",
				anag!=null?anag.getSedelegCAP()!=null?anag.getSedelegCAP():"":"",
				anag!=null?anag.getSedelegProv()!=null?anag.getSedelegProv():"":"",
				rset.getString("ORGANIZZAZIONE"), 
				rset.getString("TECNICO"),
				rset.getString("UTENTE_INSERIM_DATI"),
				Utili.getDecodificaPagamento(rset.getString("PAGAMENTO_ANALISI")), 
				rset.getString("DATA_INCASSO"),
				rset.getString("ESITO"),
				rset.getString("COD_TIPO_PAGAMENTO"),
				rset.getString("DATA_RICHIESTA_PAGAMENTO"),
				rset.getString("DATA_ANNULLAMENTO"),
				rset.getString("NUM_CAMPIONE"),
				rset.getString("ANNO"), 
				rset.getString("STATO_RICHIESTA"),
				rset.getString("DATA_INSERIMENTO_RICHIESTA"),
				rset.getString("LABORATORIO_CONSEGNA"), 
				rset.getString("DATA_ACCETTAZIONE"), 
      			rset.getString("ETICHETTA"), 
      			rset.getString("DATA_EMISSIONE_REFERTO"), 
      			Utili.getDecodificaStatoAnomalia(rset.getString("ANOMALIA_SCARTO_SOSP")),
      			rset.getString("FATTURA_EMESSA"), 
      			rset.getString("NUMERO_FATTURA"), 
      			rset.getString("INTESTAZ_FATTURA"),
      			rset.getString("PEC_AN"),
				anag!=null?anag.getPec()!=null?anag.getPec():"":"",
      			rset.getString("NOTE_RICHIEDENTE"),
      			rset.getString("NOTE_STATO_RICHIESTA"), 
      			rset.getString("NOTE_LABORATORIO"),
      			rset.getString("numero_preventivo"),
      			rset.getString("CF_PREVENTIVO"),
      			rset.getString("IMPORTO_PREVENTIVO"),
      			rset.getString("NOTE_LAB_PR")
      	};
      	elenco.add(rigaEsportaRichieste);
      }
      rset.close();
      stmt.close();
    }catch (java.sql.SQLException ex){
    	this.getAut().setQuery("esportaContabile della classe EtichettaCampione");
    	this.getAut().setContenutoQuery(query.toString());
    	throw (ex);
    }catch (Exception e){      
    	this.getAut().setQuery("esportaContabile della classe EtichettaCampione: non è una SQLException ma una Exception generica");
    	this.getAut().setContenutoQuery(query.toString());
    	throw (e);
    }finally{
      if (conn!=null) conn.close();
    }
  	return elenco;
  }
  
  
  

  public Vector<Object[]> esportaAnalisi(Vector<Long> idRichieste) throws Exception, SQLException
  {
    if (! isConnection())
    {
      throw new Exception("Riferimento a DataSource non inizializzato");
    }
    Connection conn = null;
    StringBuffer query = new StringBuffer("");
    Vector<Object[]> elenco = new Vector<Object[]>();

    try
    {
      conn = getConnection();
      Statement stmt = conn.createStatement();

      query = new StringBuffer("SELECT EC.ID_RICHIESTA NUMERO_RICHIESTA ");
      query.append("     , EC.ANNO ANNO ");
      query.append("     , EC.NUMERO_CAMPIONE NUM_CAMPIONE ");
      query.append("     , TO_CHAR((SELECT MAX(DATA) FROM TRACCIATURA TR WHERE TR.ID_RICHIESTA = EC.ID_RICHIESTA AND CODICE = '40'),'DD/MM/YYYY') DATA_EMISSIONE_REFERTO ");
      query.append("     , EC.DESCRIZIONE_ETICHETTA ETICHETTA ");
      query.append("     , COM_CAMP.DESCRIZIONE COMUNE_CAMPIONE ");
      query.append("     , PR.SIGLA_PROVINCIA SIGLA_PROVINCIA_CAMPIONE ");
      query.append("     , SC_ATT.DESCRIZIONE COLTURA_ATTUALE ");
      query.append("     , SC_PREV.DESCRIZIONE COLTURA_PREVISTA ");
      query.append("     , FR.NOTE NOTE_LABORATORIO ");
      //query.append("     -- Umidità campione ");
      query.append("     , UM.UMIDITA_CAMPIONE UMIDITA_P ");
      query.append("     , UM.SOSTANZA_SECCA SOSTANZA_SECCA_P ");
      //query.append("     -- Granulometria (standard oppure a 4 frazioni oppure a 5 frazioni) ");
      query.append("     , COALESCE(GS.ARGILLA,COALESCE(G4F.ARGILLA,G5F.ARGILLA)) ARGILLA_P ");
      query.append("     , COALESCE(GS.LIMO_TOTALE,COALESCE(G4F.LIMO_TOTALE,G5F.LIMO_TOTALE)) LIMO_TOTALE_P ");
      query.append("     , COALESCE(G4F.LIMO_GROSSO,G5F.LIMO_GROSSO) LIMO_GROSSO_P ");
      query.append("     , COALESCE(G4F.LIMO_FINE,G5F.LIMO_FINE) LIMO_FINE_P ");
      query.append("     , COALESCE(GS.SABBIA_TOTALE,COALESCE(G4F.SABBIA_TOTALE,G5F.SABBIA_TOTALE)) SABBIA_TOTALE_P ");
      query.append("     , G5F.SABBIA_GROSSA SABBIA_GROSSA_P ");
      query.append("     , G5F.SABBIA_FINE SABBIA_FINE_P ");
      //query.append("     -- Analisi pH ");
      query.append("     , RS.PH_ACQUA PH_ACQUA ");
      query.append("     , RS.PH_CLORURO_POTASSIO PH_CLORURO_POTASSIO ");
      query.append("     , RS.PH_TAMPONE PH_TAMPONE ");
      //query.append("     -- Conducibilità (salinità), unità di misura -> micro S/cm ");
      query.append("     , CON.CONDUCIBILITA CONDUC_SALINITA ");
      //query.append("     -- Calcimetria ");
      query.append("     , CA.CARBONATO_CALCIO_TOTALE CALCARE_TOTALE_P ");
      query.append("     , CA.CALCARE_ATTIVO CALCARE_ATTIVO_P ");
      //query.append("     -- Sostanza organica ");
      query.append("     , COALESCE(SO.SOSTANZA_ORGANICA, SO.SOSTANZA_ORGANICA_METODO_ANA) SOSTANZA_ORG_P ");
      query.append("     , COALESCE(SO.CARBONIO_ORGANICO, SO.CARBONIO_ORGANICO_METODO_ANA) CARBONIO_ORG_P ");
      //query.append("     -- Azoto ");
      query.append("     , AZ.AZOTO_TOTALE_METODO_ANA AZOTO_TOTALE_P ");
      query.append("     , AZ.RAPPORTO_C_N RAPPORTO_C_N ");
      //query.append("     -- Capacità di scambio cationito (CSC) ");
      query.append("     , CS.CAPACITA_SCAMBIO_CATIONICO CSC_MEQ_100G ");
      query.append("     , CS.CALCIO_SCAMBIABILE CALCIO_SCAMB_PPM ");
      query.append("     , CS.CALCIO_SCAMBIABILE_MEQ_100 CALCIO_SCAMB_MEQ_100 ");
      query.append("     , CS.CALCIO_SCAMBIABILE_CSC CALCIO_SCAMB_SU_CSC_P ");
      query.append("     , CS.MAGNESIO_SCAMBIABILE MAGNESIO_SCAMB_PPM ");
      query.append("     , CS.MAGNESIO_SCAMBIABILE_MEQ_100 MAGNESIO_SCAMB_MEQ_100G ");
      query.append("     , CS.MAGNESIO_SCAMBIABILE_CSC MAGNESIO_SCAMB_SU_CSC_P ");
      query.append("     , CS.POTASSIO_SCAMBIABILE POTASSIO_SCAMB_PPM ");
      query.append("     , CS.POTASSIO_SCAMBIABILE_MEQ_100 POTASSIO_SCAMB_MEQ_100G ");
      query.append("     , CS.POTASSIO_SCAMBIABILE_CSC POTASSIO_SCAMB_SU_CSC_P ");
      query.append("     , CS.SODIO_SCAMBIABILE SODIO_SCAMB_PPM ");
      query.append("     , CS.SODIO_SCAMBIABILE_MEQ_100 SODIO_SCAMB_MEQ_100G ");
      query.append("     , CS.SODIO_SCAMBIABILE_CSC SODIO_SCAMB_SU_CSC ");
      query.append("     , CS.CA_MG RAPPORTO_CA_MG ");
      query.append("     , CS.CA_K RAPPORTO_CA_K ");
      query.append("     , CS.MG_K RAPPORTO_MG_K ");
      query.append("     , CS.SATURAZIONE_BASICA SATURAZ_BASICA ");
      query.append("     , FO.FOSFORO_ASSIMILABILE FOSFORO_ASSIM_PPM ");
      query.append("     , FO.ANIDRIDE_FOSFORICA ANIDRIDE_FOSFORICA_ASSIM_PPM ");
      query.append("     , MEL.FERRO_ASSIMILABILE FERRO_ASSIM_PPM ");
      query.append("     , MEL.MANGANESE_ASSIMILABILE MANGANESE_ASSIM_PPM ");
      query.append("     , MEL.ZINCO_ASSIMILABILE ZINCO_ASSIM_PPM ");
      query.append("     , MEL.RAME_ASSIMILABILE RAME_ASSIM_PPM ");
      query.append("     , MEL.BORO_ASSIMILABILE BORO_ASSIM_PPM ");
      query.append("     , MP.FERRO_TOTALE FERRO_TOTALE ");
      query.append("     , MP.MANGANESE_TOTALE MANGANESE_TOTALE ");
      query.append("     , MP.ZINCO_TOTALE ZINCO_TOTALE ");
      query.append("     , MP.RAME_TOTALE RAME_TOTALE ");
      query.append("     , MP.PIOMBO_TOTALE PIOMBO_TOTALE ");
      query.append("     , MP.CROMO_TOTALE CROMO_TOTALE ");
      query.append("     , MP.BORO_TOTALE BORO_TOTALE ");
      query.append("     , MP.NICHEL_TOTALE NICHEL_TOTALE ");
      query.append("     , MP.CADMIO_TOTALE CADMIO_TOTALE ");
      query.append("     , MP.STRONZIO_TOTALE STRONZIO_TOTALE ");
      //query.append("     , MP.ALTRO_METALLO_TOTALE ALTRO_METALLO_TOTALE ");      
      query.append("  FROM ETICHETTA_CAMPIONE EC ");
      query.append("       JOIN CODIFICA_TRACCIABILITA CT ON EC.STATO_ATTUALE = CT.CODICE ");
      query.append("       JOIN MATERIALE MA USING (CODICE_MATERIALE) ");
      query.append("       LEFT JOIN REAZIONE_SUOLO RS USING (ID_RICHIESTA) ");
      query.append("       LEFT JOIN COMPLESSO_SCAMBIO CS USING (ID_RICHIESTA) ");
      query.append("       LEFT JOIN SOSTANZA_ORGANICA SO USING (ID_RICHIESTA) ");
      query.append("       LEFT JOIN CALCIMETRIA CA USING (ID_RICHIESTA) ");
      query.append("       LEFT JOIN GRANULOMETRIA_STANDARD GS USING (ID_RICHIESTA) ");
      query.append("       LEFT JOIN GRANULOMETRIA_A_4_FRAZIONI G4F USING (ID_RICHIESTA) ");
      query.append("       LEFT JOIN GRANULOMETRIA_A_5_FRAZIONI G5F USING (ID_RICHIESTA) ");
      query.append("       LEFT JOIN AZOTO AZ USING (ID_RICHIESTA) ");
      query.append("       LEFT JOIN FOSFORO_METODO_OLSEN FO USING (ID_RICHIESTA) ");
      query.append("       LEFT JOIN CONDUCIBILITA_SALINITA CON USING (ID_RICHIESTA) ");
      query.append("       LEFT JOIN MICROELEMENTI_METODO_DTPA MEL USING (ID_RICHIESTA) ");
      query.append("       LEFT JOIN UMIDITA_CAMPIONE UM USING (ID_RICHIESTA) ");
      query.append("       LEFT JOIN DATI_APPEZZAMENTO DA ON DA.ID_RICHIESTA = EC.ID_RICHIESTA ");
      query.append("       LEFT JOIN COMUNE COM_CAMP ON COM_CAMP.CODICE_ISTAT = DA.COMUNE_APPEZZAMENTO ");
      query.append("       LEFT JOIN PROVINCIA PR ON PR.ID_PROVINCIA = COM_CAMP.PROVINCIA ");
      query.append("       LEFT JOIN DATI_CAMPIONE_TERRENO DCT ON DCT.ID_RICHIESTA = EC.ID_RICHIESTA ");
      query.append("       LEFT JOIN SPECIE_COLTURA SC_ATT ON SC_ATT.ID_SPECIE = DCT.COLTURA_ATTUALE ");
      query.append("       LEFT JOIN SPECIE_COLTURA SC_PREV ON SC_PREV.ID_SPECIE = DCT.COLTURA_PREVISTA ");
      query.append("       LEFT JOIN FIRMA_REFERTO FR ON FR.ID_RICHIESTA = EC.ID_RICHIESTA ");
      query.append("       LEFT JOIN METALLI_PESANTI MP ON MP.ID_RICHIESTA = EC.ID_RICHIESTA ");
      query.append(" WHERE EC.STATO_ATTUALE = '40' ");//Referto emesso
      if (idRichieste != null && idRichieste.size() > 0)
      {
      	query.append(getInCondition("EC.ID_RICHIESTA", idRichieste));
      }
      query.append(" ORDER BY EC.ID_RICHIESTA DESC ");

      //CuneoLogger.debug(this, query.toString());

      ResultSet rset = stmt.executeQuery(query.toString());
      while (rset.next())
      {
      	Object[] rigaEsportaRichieste = new Object[]
      	{
      			rset.getString("NUMERO_RICHIESTA"), rset.getString("ANNO"), rset.getString("NUM_CAMPIONE"),
      			rset.getString("DATA_EMISSIONE_REFERTO"), rset.getString("ETICHETTA"), rset.getString("COMUNE_CAMPIONE"),
      			rset.getString("SIGLA_PROVINCIA_CAMPIONE"), rset.getString("COLTURA_ATTUALE"), rset.getString("COLTURA_PREVISTA"),
      			rset.getString("NOTE_LABORATORIO"), rset.getBigDecimal("UMIDITA_P"), rset.getBigDecimal("SOSTANZA_SECCA_P"),
      			rset.getBigDecimal("ARGILLA_P"), rset.getBigDecimal("LIMO_TOTALE_P"), rset.getBigDecimal("LIMO_GROSSO_P"),
      			rset.getBigDecimal("LIMO_FINE_P"), rset.getBigDecimal("SABBIA_TOTALE_P"), rset.getBigDecimal("SABBIA_GROSSA_P"),
      			rset.getBigDecimal("SABBIA_FINE_P"), rset.getBigDecimal("PH_ACQUA"), rset.getBigDecimal("PH_CLORURO_POTASSIO"),
      			rset.getBigDecimal("PH_TAMPONE"), rset.getBigDecimal("CONDUC_SALINITA"), rset.getBigDecimal("CALCARE_TOTALE_P"),
      			rset.getBigDecimal("CALCARE_ATTIVO_P"), rset.getBigDecimal("SOSTANZA_ORG_P"), rset.getBigDecimal("CARBONIO_ORG_P"),
      			rset.getBigDecimal("AZOTO_TOTALE_P"), rset.getBigDecimal("RAPPORTO_C_N"), rset.getBigDecimal("CSC_MEQ_100G"),
      			rset.getBigDecimal("CALCIO_SCAMB_PPM"), rset.getBigDecimal("CALCIO_SCAMB_MEQ_100"), rset.getBigDecimal("CALCIO_SCAMB_SU_CSC_P"),
      			rset.getBigDecimal("MAGNESIO_SCAMB_PPM"), rset.getBigDecimal("MAGNESIO_SCAMB_MEQ_100G"), rset.getBigDecimal("MAGNESIO_SCAMB_SU_CSC_P"),
      			rset.getBigDecimal("POTASSIO_SCAMB_PPM"), rset.getBigDecimal("POTASSIO_SCAMB_MEQ_100G"), rset.getBigDecimal("POTASSIO_SCAMB_SU_CSC_P"),
      			rset.getBigDecimal("SODIO_SCAMB_PPM"), rset.getBigDecimal("SODIO_SCAMB_MEQ_100G"), rset.getBigDecimal("SODIO_SCAMB_SU_CSC"),
      			rset.getBigDecimal("RAPPORTO_CA_MG"), rset.getBigDecimal("RAPPORTO_CA_K"), rset.getBigDecimal("RAPPORTO_MG_K"),
      			rset.getBigDecimal("SATURAZ_BASICA"), rset.getBigDecimal("FOSFORO_ASSIM_PPM"), rset.getBigDecimal("ANIDRIDE_FOSFORICA_ASSIM_PPM"),
      			rset.getBigDecimal("FERRO_ASSIM_PPM"), rset.getBigDecimal("MANGANESE_ASSIM_PPM"), rset.getBigDecimal("ZINCO_ASSIM_PPM"),
      			rset.getBigDecimal("RAME_ASSIM_PPM"), rset.getBigDecimal("BORO_ASSIM_PPM"), rset.getBigDecimal("FERRO_TOTALE"),
      			rset.getBigDecimal("MANGANESE_TOTALE"), rset.getBigDecimal("ZINCO_TOTALE"), rset.getBigDecimal("RAME_TOTALE"),
      			rset.getBigDecimal("PIOMBO_TOTALE"), rset.getBigDecimal("CROMO_TOTALE"), rset.getBigDecimal("BORO_TOTALE"),
      			rset.getBigDecimal("NICHEL_TOTALE"), rset.getBigDecimal("CADMIO_TOTALE"), rset.getBigDecimal("STRONZIO_TOTALE")
      	};

      	elenco.add(rigaEsportaRichieste);
      }
      rset.close();
      stmt.close();
    }
    catch (java.sql.SQLException ex)
    {
      this.getAut().setQuery("esportaAnalisi della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch (Exception e)
    {
      this.getAut().setQuery("esportaAnalisi della classe EtichettaCampione"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      if (conn!=null) conn.close();
    }
  	
  	return elenco;
  }

  public Vector<Object[]> esportaElencoAnalisiRichieste(Vector<Long> idRichieste) throws Exception, SQLException
  {
    if (! isConnection())
    {
      throw new Exception("Riferimento a DataSource non inizializzato");
    }
    Connection conn = null;
    StringBuffer query = new StringBuffer("");
    Vector<Object[]> elenco = new Vector<Object[]>();

    try
    {
      conn = getConnection();
      Statement stmt = conn.createStatement();

      query = new StringBuffer("SELECT ");
     // query.append(" EC.ID_RICHIESTA NUMERO_RICHIESTA ,");
      query.append(" EC.ANNO ANNO ");
      query.append(", EC.NUMERO_CAMPIONE NUM_CAMPIONE ");
      query.append(", EC.DESCRIZIONE_ETICHETTA ETICHETTA_CAMPIONE ");
      //query.append(", TO_CHAR(EC.DATA_INSERIMENTO_RICHIESTA,'DD/MM/YYYY') DATA_RICHIESTA ");
      query.append(", TO_CHAR((SELECT MIN(DATA) FROM TRACCIATURA TR WHERE TR.ID_RICHIESTA = EC.ID_RICHIESTA AND CODICE = '20'),'DD/MM/YYYY') DATA_ACCETTAZIONE ");
     // query.append(", COM_CAMP.DESCRIZIONE COMUNE_CAMPIONE ");
     // query.append(", PR.SIGLA_PROVINCIA SIGLA_PROVINCIA_CAMPIONE ");
      query.append(", COM_CONS.DESCRIZIONE LABORATORIO_CONSEGNA ");      
      query.append(", SC_ATT.DESCRIZIONE COLTURA_ATTUALE ");
    //  query.append(", SC_PREV.DESCRIZIONE COLTURA_PREVISTA ");
      query.append(", CO.DESCRIZIONE CONCIMAZIONE_ORGANICA ");
      query.append(", AD.NOTE NOTE_AGGIUNTIVE ");
      query.append(", EC.NOTE_CLIENTE ");
      query.append(", (CASE WHEN EC.STATO_ATTUALE = '00' THEN '' ");
      query.append("        ELSE (SELECT TR.NOTE ");
      query.append("              FROM TRACCIATURA TR ");
      query.append("              WHERE TR.ID_RICHIESTA = EC.ID_RICHIESTA ");
      query.append("              AND TR.PROGRESSIVO = (SELECT MAX(TR2.PROGRESSIVO) ");
      query.append("                                    FROM TRACCIATURA TR2 ");
      query.append("                                    WHERE TR2.ID_RICHIESTA = TR.ID_RICHIESTA ");
      query.append("		                                AND TR2.CODICE = EC.STATO_ATTUALE)) END) NOTE_STATO_RICHESTA ");      
     // query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'TIPO' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR "); 
     // query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'TIPO') ANALISI_TIPO ");      
     // query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'CO' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
     // query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'CO') COMPLESSO_SCAMBIO ");      
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'pH' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'TIPO' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("    WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI in ('pH', 'TIPO')) ANALISI_PH ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'N' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'TIPO' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI in('N', 'TIPO')) ANALISI_N_TOT ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'SO' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'TIPO' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("    WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI in ('SO', 'TIPO')) ANALISI_SO ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'CSC' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'TIPO' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'CO' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI in  ('CSC', 'TIPO','CO')) ANALISI_CSC ");      
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'Ca' THEN 'X'  WHEN COALESCE(AR.CODICE_ANALISI,'') = 'TIPO' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'CO' THEN 'X'  ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("    WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI in ('Ca', 'TIPO', 'CO')) ANALISI_CA ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'Mg' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'TIPO' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'CO' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("    WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI in ('Mg', 'TIPO','CO')) ANALISI_MG ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'K' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'TIPO' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'CO' THEN 'X'  ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI in ('K','TIPO', 'CO')) ANALISI_K ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'P' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'TIPO' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR "); 
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI in ('P', 'TIPO')) ANALISI_P ");      
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'CaCO3' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'TIPO' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI in ('CaCO3', 'TIPO')) ANALISI_CACO3 ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'CaAtt' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'CaAtt') ANALISI_CAATT ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'Std' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'Std') ANALISI_STD ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = '4Fra' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");  
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = '4Fra') ANALISI_4FRA ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = '5Fra' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = '5Fra') ANALISI_5FRA ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'Sal' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");  
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'Sal') ANALISI_SAL ");
     // query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'MICRO' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR "); 
     // query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'MICRO') MICROELEMENTI ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'Fe' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'MICRO' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR  ");
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI in ('Fe', 'MICRO')) ANALISI_FE ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'Mn' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'MICRO' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI in ('Mn', 'MICRO')) ANALISI_MN ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'Zn' THEN 'X'  WHEN COALESCE(AR.CODICE_ANALISI,'') = 'MICRO' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI in ('Zn', 'MICRO')) ANALISI_ZN ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'Cu' THEN 'X' WHEN COALESCE(AR.CODICE_ANALISI,'') = 'MICRO' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR "); 
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI in ('Cu', 'MICRO')) ANALISI_CU "); 
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'B' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'B') ANALISI_B ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = ('FeTot') THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("    WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'FeTot') FERRO_TOTALE ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = ('CuTot') THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("    WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'CuTot') RAME_TOTALE ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = ('ZnTot') THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("    WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'ZnTot') ZINCO_TOTALE ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = ('MnTot') THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("    WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'MnTot') MANGANESE_TOTALE ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = ('CdTot') THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("    WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'CdTot') CADMIO_TOTALE ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = ('CrTot') THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("    WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'CrTot') CROMO_TOTALE ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = ('NiTot') THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("    WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'NiTot') NICHEL_TOTALE ");
      query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = ('PbTot') THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      query.append("    WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'PbTot') PIOMBO_TOTALE ");
      //query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'METAL' THEN 'SI' ELSE '' END) FROM ANALISI_RICHIESTE AR");
      //query.append("    WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'METAL') METALLI_PESANTI ");
      //query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = 'UM' THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      //query.append("WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'UM') ANALISI_UMIDITA ");
      //query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = ('BTot') THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      //query.append("    WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'BTot') BORO_TOTALE ");
      //query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = ('SrTot') THEN 'X' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      //query.append("    WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'SrTot') STRONZIO_TOTALE ");
      //query.append(", (SELECT (CASE WHEN COALESCE(AR.CODICE_ANALISI,'') = ('MetTot') THEN 'SI' ELSE '' END) FROM ANALISI_RICHIESTE AR ");
      //query.append("    WHERE AR.ID_RICHIESTA = EC.ID_RICHIESTA AND AR.CODICE_ANALISI = 'MetTot') ALTRO_METALLO_TOTALE ");      
      query.append("FROM ETICHETTA_CAMPIONE EC ");
      query.append("LEFT JOIN ANALISI_DATI AD ON AD.ID_RICHIESTA = EC.ID_RICHIESTA ");
      query.append("LEFT JOIN DATI_APPEZZAMENTO DA ON DA.ID_RICHIESTA = EC.ID_RICHIESTA ");
     // query.append("LEFT JOIN COMUNE COM_CAMP ON COM_CAMP.CODICE_ISTAT = DA.COMUNE_APPEZZAMENTO ");
      //query.append("LEFT JOIN PROVINCIA PR ON PR.ID_PROVINCIA = COM_CAMP.PROVINCIA ");
      query.append("LEFT JOIN DATI_CAMPIONE_TERRENO DCT ON DCT.ID_RICHIESTA = EC.ID_RICHIESTA ");
      query.append("LEFT JOIN SPECIE_COLTURA SC_ATT ON SC_ATT.ID_SPECIE = DCT.COLTURA_ATTUALE ");
     // query.append("LEFT JOIN SPECIE_COLTURA SC_PREV ON SC_PREV.ID_SPECIE = DCT.COLTURA_PREVISTA ");
      query.append("LEFT JOIN FIRMA_REFERTO FR ON FR.ID_RICHIESTA = EC.ID_RICHIESTA ");
      query.append("LEFT JOIN LABORATORIO LAB_CONS ON LAB_CONS.CODICE_LABORATORIO = EC.LABORATORIO_CONSEGNA ");
      query.append("LEFT JOIN COMUNE COM_CONS ON COM_CONS.CODICE_ISTAT = LAB_CONS.COMUNE ");
      query.append("LEFT JOIN CONCIMAZIONE_ORGANICA CO ON (DCT.TIPO_CONCIMAZIONE = CO.TIPO_CONCIMAZIONE) ");

      query.append("WHERE EC.CODICE_MATERIALE IN ('TER') ");
      if (idRichieste != null && idRichieste.size() > 0)
      {
      	query.append(getInCondition("EC.ID_RICHIESTA", idRichieste));
      }
      //query.append(" ORDER BY EC.ID_RICHIESTA DESC ");
      query.append(" ORDER BY EC.ANNO DESC, EC.NUMERO_CAMPIONE DESC ");

      CuneoLogger.debug(this, query.toString());

      ResultSet rset = stmt.executeQuery(query.toString());
      while (rset.next())
      {
      	Object[] rigaEsportaRichieste = new Object[]
      	{
      			//rset.getString("NUMERO_RICHIESTA"), 
      			rset.getString("ANNO"), rset.getBigDecimal("NUM_CAMPIONE"),
      			rset.getString("ETICHETTA_CAMPIONE"), 
      			//rset.getString("DATA_RICHIESTA"), 
      			rset.getString("DATA_ACCETTAZIONE"),
      			//rset.getString("COMUNE_CAMPIONE"), rset.getString("SIGLA_PROVINCIA_CAMPIONE"), 
      			rset.getString("LABORATORIO_CONSEGNA"),
      			rset.getString("COLTURA_ATTUALE"), 
      			//rset.getString("COLTURA_PREVISTA"), 
      			//rset.getString("ANALISI_TIPO"),
      			//rset.getString("COMPLESSO_SCAMBIO"), 
      			rset.getString("ANALISI_PH"), rset.getString("ANALISI_N_TOT"),
      			rset.getString("ANALISI_SO"), rset.getString("ANALISI_CSC"), rset.getString("ANALISI_CA"),
      			rset.getString("ANALISI_MG"), rset.getString("ANALISI_K"), rset.getString("ANALISI_P"), 
      			rset.getString("ANALISI_CACO3"), rset.getString("ANALISI_CAATT"), rset.getString("ANALISI_STD"),
      			rset.getString("ANALISI_4FRA"), rset.getString("ANALISI_5FRA"), rset.getString("ANALISI_SAL"),
      			//rset.getString("MICROELEMENTI"), 
      			rset.getString("ANALISI_FE"), rset.getString("ANALISI_MN"),
      			rset.getString("ANALISI_ZN"), rset.getString("ANALISI_CU"), rset.getString("ANALISI_B"),
      			rset.getString("FERRO_TOTALE"), rset.getString("RAME_TOTALE"), rset.getString("ZINCO_TOTALE"),
      			rset.getString("MANGANESE_TOTALE"), rset.getString("CADMIO_TOTALE"), rset.getString("CROMO_TOTALE"),
      			rset.getString("NICHEL_TOTALE"), rset.getString("PIOMBO_TOTALE"), rset.getString("CONCIMAZIONE_ORGANICA"),
      			rset.getString("NOTE_AGGIUNTIVE"), rset.getString("NOTE_CLIENTE"), rset.getString("NOTE_STATO_RICHESTA")
      	};

      	elenco.add(rigaEsportaRichieste);
      }
      rset.close();
      stmt.close();
   
    }
    catch (java.sql.SQLException ex)
    {
      this.getAut().setQuery("esportaRichieste della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch (Exception e)
    {
      this.getAut().setQuery("esportaRichieste della classe EtichettaCampione"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      if (conn!=null) conn.close();
    }
  	
  	return elenco;
  }

  /**
   * Questo metodo viene utilizzato per popolare i dati della form che si trova
   * nella pagina della fattura
   * @param idRichiesta: contiene gli id delle etichette di cui si vuole costruire
   * la fattura
   * @return restituisce quanto bisogna pagare per la spedizione della fatura
   * @throws Exception
   * @throws SQLException
   */
  public double selectForFattura(String idRichiesta)
      throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    Anagrafiche anagrafiche=new Anagrafiche();
    StringBuffer query=new StringBuffer("");
    String anagraficaProprietario;
    String anagraficaUtente;
    String proprietario;
    double importoSpedizione=0;
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query.append("SELECT EC.ID_RICHIESTA,M.DESCRIZIONE AS MATERIALE,EC.DESCRIZIONE_ETICHETTA, ");
      query.append("EC.ANAGRAFICA_UTENTE,EC.ANAGRAFICA_PROPRIETARIO,");
      query.append("EC.PAGAMENTO,SUM (AR.COSTO_ANALISI) AS COSTO, ");
      query.append("COALESCE(DF.IMPORTO_SPEDIZIONE,0) AS IMPORTO_SPEDIZIONE,DF.FATTURARE ");
      query.append("FROM ETICHETTA_CAMPIONE EC,MATERIALE M,");
      query.append("ANALISI_RICHIESTE AR,DATI_FATTURA DF ");
      query.append("WHERE EC.CODICE_MATERIALE= M.CODICE_MATERIALE ");
      //query.append("AND EC.STATO_ATTUALE='40' "); ora permettiamo la richiesta fattura dalla ricerca campione
      query.append("AND EC.ID_RICHIESTA=AR.ID_RICHIESTA ");
      query.append("AND EC.ID_RICHIESTA=DF.ID_RICHIESTA ");
      query.append("AND EC.ID_RICHIESTA IN (").append(idRichiesta);
      query.append(") GROUP BY EC.ID_RICHIESTA,M.DESCRIZIONE,EC.DESCRIZIONE_ETICHETTA,");
      query.append("EC.ANAGRAFICA_UTENTE,EC.ANAGRAFICA_PROPRIETARIO,");
      query.append("EC.PAGAMENTO,DF.IMPORTO_SPEDIZIONE,DF.FATTURARE");
      //CuneoLogger.debug(this, query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      while (rset.next())
      {
        anagraficaProprietario=rset.getString("ANAGRAFICA_PROPRIETARIO");
        anagraficaUtente=rset.getString("ANAGRAFICA_UTENTE");
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
        add(new EtichettaCampione(
                                    rset.getString("ID_RICHIESTA"),
                                    rset.getString("MATERIALE"),
                                    rset.getString("DESCRIZIONE_ETICHETTA"),
                                    proprietario,
                                    rset.getString("PAGAMENTO"),
                                    rset.getString("COSTO"),
                                    rset.getString("FATTURARE")
                                    )
                                    );
       importoSpedizione+=rset.getDouble("IMPORTO_SPEDIZIONE");
      }
      return importoSpedizione;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("selectForFattura della classe EtichettaCampioni");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("selectForFattura della classe EtichettaCampioni"
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
   * Questo metodo viene utilizzato per popolare i dati che vengono visualizzati
   * nel PDF della fattura
   * @param idFattura: contiene l'id della fattura
   * @param anno: contiene l'anno di emissione della fattura
   * @return restituisce quanto bisogna pagare per la spedizione della fatura
   * @throws Exception
   * @throws SQLException
   */
  public void selectForFatturaPDF(String idFattura,String anno)
      throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    Anagrafiche anagrafiche=new Anagrafiche();
    StringBuffer query=new StringBuffer("");
    String anagraficaProprietario;
    String anagraficaUtente;
    String proprietario;
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query.append("SELECT M.DESCRIZIONE AS MATERIALE,");
      query.append("EC.DESCRIZIONE_ETICHETTA,");
      query.append("EC.ANAGRAFICA_UTENTE,EC.ANAGRAFICA_PROPRIETARIO ");
      query.append("FROM ETICHETTA_CAMPIONE EC,CAMPIONE_FATTURATO CF");
      query.append(",MATERIALE M ");
      query.append("WHERE CF.NUMERO_FATTURA = ").append(idFattura);
      query.append(" AND CF.ANNO=").append(anno);
      query.append(" AND EC.ID_RICHIESTA = CF.ID_RICHIESTA ");
      query.append("AND EC.CODICE_MATERIALE= M.CODICE_MATERIALE ");
      query.append("ORDER BY EC.ID_RICHIESTA ");
      //CuneoLogger.debug(this, query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      while (rset.next())
      {
        anagraficaProprietario=rset.getString("ANAGRAFICA_PROPRIETARIO");
        anagraficaUtente=rset.getString("ANAGRAFICA_UTENTE");
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
        add(new EtichettaCampione(
                                    null,
                                    rset.getString("MATERIALE"),
                                    rset.getString("DESCRIZIONE_ETICHETTA"),
                                    proprietario,
                                    null,
                                    null,
                                    null
                                    )
                                    );
      }
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("selectForFatturaPDF della classe EtichettaCampioni");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("selectForFatturaPDF della classe EtichettaCampioni"
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


  public void caricoListaCampioni(String idRichiesta)
  throws Exception, SQLException
  {
    caricoListaCampioni(idRichiesta,null);
  }

  public void caricoListaCampioni(String idRichiesta,String note)
  throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    Anagrafiche anagrafiche=new Anagrafiche();
    StringBuffer query=new StringBuffer("");
    String anagraficaTecnico;
    String anagraficaProprietario;
    String anagraficaUtente;
    String proprietario;
    String richiedente;
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query.append("SELECT EC.ID_RICHIESTA,M.DESCRIZIONE AS DESC_MATERIALE,");
      query.append("EC.ANNO,EC.NUMERO_CAMPIONE,EC.DESCRIZIONE_ETICHETTA,");
      query.append("EC.ANAGRAFICA_UTENTE,EC.ANAGRAFICA_PROPRIETARIO,");
      query.append("EC.ANAGRAFICA_TECNICO ");
      if (note==null)
        query.append(", T.NOTE ");
      else
        query.append(", EC.NOTE_CLIENTE AS NOTE ");
      query.append("FROM ETICHETTA_CAMPIONE EC, MATERIALE M ");
      if (note==null)
        query.append(", TRACCIATURA T ");
      query.append("WHERE EC.CODICE_MATERIALE= M.CODICE_MATERIALE ");
      query.append("AND EC.ID_RICHIESTA IN (").append(idRichiesta);
      if (note==null)
        query.append(") AND T.ID_RICHIESTA= EC.ID_RICHIESTA AND T.CODICE='20'");
      else
        query.append(") ");
      query.append(" ORDER BY EC.ANNO,EC.NUMERO_CAMPIONE");
      //CuneoLogger.debug(this, query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      while (rset.next())
      {
        anagraficaProprietario=rset.getString("ANAGRAFICA_PROPRIETARIO");
        anagraficaUtente=rset.getString("ANAGRAFICA_UTENTE");
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
        anagraficaTecnico=rset.getString("ANAGRAFICA_TECNICO");
        if (anagraficaTecnico==null)
        {
          richiedente=anagrafiche.getNomeCognome(
                     conn,
                     anagraficaUtente
                                                );
        }
        else
        {
          richiedente=anagrafiche.getNomeCognome(
              conn,
              anagraficaTecnico
              );
        }
        add(new EtichettaCampione(
                                    rset.getString("ID_RICHIESTA"),
                                    "",
                                    "",
                                    rset.getString("DESC_MATERIALE"),
                                    rset.getString("DESCRIZIONE_ETICHETTA"),
                                    "",
                                    proprietario,
                                    richiedente,
                                    rset.getString("NUMERO_CAMPIONE"),
                                    rset.getString("ANNO"),
                                    "",
                                    rset.getString("NOTE")
                                    )
                                    );
      }
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("listaCampioniPdfeMail della classe EtichettaCampioni");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("listaCampioniPdfeMail della classe EtichettaCampioni"
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

  public String select(String idRichieste)
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
      query = new StringBuffer("SELECT EC.ID_RICHIESTA,");
      query.append("M.DESCRIZIONE AS MATERIALE,EC.CODICE_MATERIALE,");
      query.append("EC.DESCRIZIONE_ETICHETTA,");
      query.append("EC.ANAGRAFICA_PROPRIETARIO, ");
      query.append("EC.ANAGRAFICA_TECNICO, ");
      query.append("EC.ANAGRAFICA_UTENTE,AT.EMAIL AS EMAILTECNICO,");
      query.append("A.EMAIL, L.DESCRIZIONE,EC.STATO_ANOMALIA,EC.PAGAMENTO,");
      query.append("T.NOTE,EC.LABORATORIO_ANALISI,EC.LABORATORIO_CONSEGNA,TO_CHAR(EC.DATA_INCASSO, 'DD/MM/YYYY') as DATA_INCASSO, ");
      query.append("(with elenco_iuv as (select distinct FIRST_VALUE(iuv) " + 
  			"    OVER( PARTITION BY ID_RICHIESTA ORDER BY data_annullamento desc) iuv,ID_RICHIESTA from PAGAMENTI) " + 
  			"select case when p.data_annullamento is null then p.iuv else '' end iuv " + 
  			"from pagamenti p " + 
  			"  JOIN elenco_iuv ei ON p.id_richiesta = ei.id_richiesta and p.iuv = ei.iuv " + 
  			"where p.id_richiesta = ec.id_richiesta)");
      query.append("FROM ETICHETTA_CAMPIONE EC ");
      query.append("  LEFT OUTER JOIN TRACCIATURA T ON(EC.ID_RICHIESTA=T.ID_RICHIESTA) ");
      query.append("  LEFT OUTER JOIN ANAGRAFICA A ON(EC.ANAGRAFICA_UTENTE=A.ID_ANAGRAFICA) ");
      query.append("  LEFT OUTER JOIN ANAGRAFICA AT ON(EC.ANAGRAFICA_TECNICO=AT.ID_ANAGRAFICA), ");
      query.append("MATERIALE M, LABORATORIO L ");
      if (idRichieste!=null){
        query.append("WHERE EC.ID_RICHIESTA IN (");
        query.append(idRichieste).append(")");
      }else{
        query.append("WHERE EC.ID_RICHIESTA = ");
        query.append(getAut().getIdRichiestaCorrente());
      }
      query.append(" AND EC.CODICE_MATERIALE= M.CODICE_MATERIALE ");
      query.append("AND L.CODICE_LABORATORIO=EC.LABORATORIO_CONSEGNA ");
      query.append("AND T.PROGRESSIVO = ");
      query.append("(SELECT MAX(PROGRESSIVO) FROM TRACCIATURA WHERE EC.ID_RICHIESTA=ID_RICHIESTA) ");
      query.append("ORDER BY EC.ID_RICHIESTA");
      CuneoLogger.debug(this, query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      int x = 0; //mi serve solo per definire il costruttore giusto di Etichetta Campione
      while (rset.next()){
        add(new EtichettaCampione(
                                    rset.getString("ID_RICHIESTA"),
                                    rset.getString("CODICE_MATERIALE"),
                                    rset.getString("MATERIALE"),
                                    rset.getString("DESCRIZIONE_ETICHETTA"),
                                    rset.getString("DESCRIZIONE"),
                                    rset.getString("PAGAMENTO"),
                                    rset.getString("STATO_ANOMALIA"),
                                    rset.getString("LABORATORIO_ANALISI"),
                                    rset.getString("LABORATORIO_CONSEGNA"),
                                    rset.getString("IUV"),
                                    rset.getString("NOTE"),
                                    rset.getString("DATA_INCASSO"),
                                    x
                                  )
                                );
        if (rset.getString("ANAGRAFICA_TECNICO") != null)
          email=rset.getString("EMAILTECNICO");
        else
          email=rset.getString("EMAIL");
      }
      rset.close();
      stmt.close();
      return email;
    }catch(java.sql.SQLException ex){
      this.getAut().setQuery("select della classe EtichettaCampioni");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }catch(Exception e){
      this.getAut().setQuery("select della classe EtichettaCampioni"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }finally{
      if (conn!=null) conn.close();
    }
  }

  /**
   * Qusto metodo viene utilizzato in campioni laboratorio per popolare la
   * combo che appare nei vari form di inserimento esito analisi   *
   * @param idRichieste string che contiene i codici dei campioni su cui si
   * sta lavorando
   * @throws Exception
   * @throws SQLException
   */
  public void selectAnnoNumero(String idRichieste)
  throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    //String email=null;
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("SELECT ID_RICHIESTA,ANNO,NUMERO_CAMPIONE ");
      query.append("FROM ETICHETTA_CAMPIONE ");
      query.append("WHERE ID_RICHIESTA IN (");
      query.append(idRichieste);
      query.append(") ORDER BY ANNO,NUMERO_CAMPIONE");
      //CuneoLogger.debug(this, query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      while (rset.next())
      {
        add(new EtichettaCampione(
                                    rset.getString("ID_RICHIESTA"),
                                    rset.getString("ANNO"),
                                    rset.getString("NUMERO_CAMPIONE")
                                    )
                                    );
      }
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("selectAnnoNumero della classe EtichettaCampioni");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("selectAnnoNumero della classe EtichettaCampioni"
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
   * Viene utilizzato per leggere i dati relativi a più richieste
   * anagraficaCampione.pdf
   * @param idRichiesta
   * @throws Exception
   * @throws SQLException
   */
  public void selectRichiesteByIdRange(String idRichieste) throws Exception, SQLException
  {
    Anagrafiche anagrafiche = new Anagrafiche();
    if (!isConnection())
    {
      throw new Exception("Riferimento a DataSource non inizializzato");
    }
    Connection conn = null;
    StringBuffer query = new StringBuffer("");
    String anagraficaUtente;
    String proprietario;
    String richiedente;

    try
    {
      conn = getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("SELECT EC.ID_RICHIESTA, ");
      query.append("TO_CHAR(EC.DATA_INSERIMENTO_RICHIESTA,'DD/MM/YYYY') AS DATA_INSERIMENTO_RICHIESTA, ");
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
      query.append("EC.NOTE_CLIENTE,TO_CHAR(EC.DATA_INCASSO, 'DD/MM/YYYY') as DATA_INCASSO, ");
      query.append("(with elenco_iuv as (select distinct FIRST_VALUE(iuv) " + 
    			"    OVER( PARTITION BY ID_RICHIESTA ORDER BY data_annullamento desc) iuv,ID_RICHIESTA from PAGAMENTI) " + 
    			"select case when p.data_annullamento is null then p.iuv else '' end iuv " + 
    			"from pagamenti p " + 
    			"  JOIN elenco_iuv ei ON p.id_richiesta = ei.id_richiesta and p.iuv = ei.iuv " + 
    			"where p.id_richiesta = ec.id_richiesta) ");
      query.append("FROM ETICHETTA_CAMPIONE EC, MATERIALE M ");
      query.append("WHERE ID_RICHIESTA IN (");
      query.append(idRichieste);
      query.append(") ");
      query.append(" AND EC.CODICE_MATERIALE = M.CODICE_MATERIALE ");
      query.append(" ORDER BY EC.ID_RICHIESTA DESC ");
      //CuneoLogger.debug(this, query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      while (rset.next())
      {
      	EtichettaCampione etichettaCampione = new EtichettaCampione();

        anagraficaUtente=rset.getString("ANAGRAFICA_UTENTE");
        etichettaCampione.setAnagraficaUtente(anagraficaUtente);
        etichettaCampione.setAnagraficaProprietario(rset.getString("ANAGRAFICA_PROPRIETARIO"));
        if (etichettaCampione.getAnagraficaProprietario() == null)
        {
        	etichettaCampione.setAnagraficaProprietario(anagraficaUtente);
        }
        proprietario = anagrafiche.getNomeCognome(conn, etichettaCampione.getAnagraficaProprietario());

        etichettaCampione.setAnagraficaTecnico(rset.getString("ANAGRAFICA_TECNICO"));
        if (etichettaCampione.getAnagraficaTecnico() == null)
        {
        	etichettaCampione.setAnagraficaRichiedente(anagraficaUtente);
        }
        else
        {
        	etichettaCampione.setAnagraficaRichiedente(etichettaCampione.getAnagraficaTecnico());
        }
        richiedente = anagrafiche.getNomeCognome(conn, etichettaCampione.getAnagraficaRichiedente());

        etichettaCampione.setIdRichiesta(rset.getString("ID_RICHIESTA"));
        etichettaCampione.setDataInserimentoRichiesta(rset.getString("DATA_INSERIMENTO_RICHIESTA"));
        etichettaCampione.setAnnoCampione(rset.getString("ANNO"));
        etichettaCampione.setNumeroCampione(rset.getString("NUMERO_CAMPIONE"));
        etichettaCampione.setCodiceMateriale(rset.getString("CODICE_MATERIALE"));
        etichettaCampione.setDescMateriale(rset.getString("MATERIALE"));
        etichettaCampione.setCodLabConsegna(rset.getString("LABORATORIO_CONSEGNA"));
        etichettaCampione.setCodLabAnalisi(rset.getString("LABORATORIO_ANALISI"));
        etichettaCampione.setDescrizioneEtichetta(rset.getString("DESCRIZIONE_ETICHETTA"));
        etichettaCampione.setNote(rset.getString("NOTE_CLIENTE"));
        etichettaCampione.setProprietario(proprietario);
        etichettaCampione.setRichiedente(richiedente);
        etichettaCampione.setStatoAnomalia(rset.getString("STATO_ANOMALIA"));
        etichettaCampione.setData_incasso(rset.getString("DATA_INCASSO"));
        etichettaCampione.setIuv(rset.getString("IUV"));
        
        add(etichettaCampione);
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
   * Questo metodo viene utilizzato per popolare i dati della form che si trova
   * nella pagina firmaScarto
   * */
  public HashMap<String, String> selectEmailForFirmaScartoByIdRichiestaRange(String idRichieste) throws Exception, SQLException
  {
    if (!isConnection())
    {
      throw new Exception("Riferimento a DataSource non inizializzato");
    }

    Connection conn = null;
    StringBuffer query = new StringBuffer("");

    HashMap<String, String> hmEmail = new HashMap<String, String>();
    
    try
    {
      conn = getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer(" SELECT EC.ID_RICHIESTA, A.EMAIL ");
      query.append(" FROM ETICHETTA_CAMPIONE EC, ANAGRAFICA A ");
      query.append(" WHERE EC.ID_RICHIESTA IN (");
      query.append(idRichieste).append(")");
      query.append(" AND A.ID_ANAGRAFICA = EC.ANAGRAFICA_UTENTE ");
      query.append(" ORDER BY EC.ID_RICHIESTA DESC ");
      //CuneoLogger.debug(this, query.toString());

      ResultSet rset = stmt.executeQuery(query.toString());
      while (rset.next())
      {
      	hmEmail.put(rset.getString("ID_RICHIESTA"), rset.getString("EMAIL"));
      }
      rset.close();
      stmt.close();

      return hmEmail;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("selectEmailForFirmaScartoByIdRichiestaRange della classe EtichettaCampioni");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("selectEmailForFirmaScartoByIdRichiestaRange della classe EtichettaCampioni"
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

  public int size()
  {
    return etichettaCampioni.size();
  }

  public EtichettaCampione get(int i)
  {
    return (EtichettaCampione)etichettaCampioni.elementAt(i);
  }

  public void add(EtichettaCampione i)
  {
    etichettaCampioni.addElement(i);
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