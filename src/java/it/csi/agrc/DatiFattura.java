package it.csi.agrc;

import it.csi.cuneo.BeanDataSource;
import it.csi.cuneo.Constants;
import it.csi.cuneo.CuneoLogger;
import it.csi.cuneo.Utili;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
//import it.csi.jsf.web.pool.*;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class DatiFattura  extends BeanDataSource
{
  private String idRichiesta;
  private String fatturaSN;
  private String spedizione;
  private String importoSpedizione;
  private String fatturare;
  private String cfPartitaIva;
  private String ragioneSociale;
  private String indirizzo;
  private String cap;
  private String comune;
  private String comuneDesc;
  private String idRichiestaCorrente;
  private String fatturareCorrente;
  private String siglaProvincia;
  private String descrizione;
  private String pagata;
  private String cod_destinatario;
  private String pec;
  private String dataIncasso;
  private BigDecimal importoBD;
  
  
  public DatiFattura()
  {
  }
  public DatiFattura(Object dataSource, Autenticazione aut)
  {
    this.setDataSource(dataSource);
    this.setAut(aut);
  }

  public void select() throws Exception, SQLException
  {
    select(null);
  }
  public void select(String idRichiestaSearch) throws Exception, SQLException
  {
    if (!isConnection())
           throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn = null;
    StringBuffer query = null;
    Statement stmt = null;

    try
    {
      conn = getConnection();
      stmt = conn.createStatement();
      query = new StringBuffer("SELECT DF.*, C.DESCRIZIONE, TO_CHAR(EC.DATA_INCASSO,'DD/MM/YYYY') AS DATA_INCASSO ");
      query.append("FROM DATI_FATTURA DF LEFT OUTER JOIN COMUNE C ON (DF.COMUNE=C.CODICE_ISTAT) "
      				+ " LEFT JOIN ETICHETTA_CAMPIONE EC ON DF.ID_RICHIESTA = EC.ID_RICHIESTA ");
      query.append("WHERE DF.ID_RICHIESTA = ");
      if ( idRichiestaSearch == null)
        query.append(this.getAut().getIdRichiestaCorrente());
      else
        query.append(idRichiestaSearch);
      //CuneoLogger.debug(this, "\nQuery DatiFattura.select()\n"+query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        this.setIdRichiesta(rset.getString("ID_RICHIESTA"));
        this.setFatturaSN(rset.getString("FATTURA_SN"));
        this.setSpedizione(rset.getString("SPEDIZIONE"));
        this.setFatturare(rset.getString("FATTURARE"));
        this.setCfPartitaIva(rset.getString("CF_PARTITA_IVA"));
        this.setRagioneSociale(rset.getString("RAGIONE_SOCIALE"));
        this.setIndirizzo(rset.getString("INDIRIZZO"));
        this.setCap(rset.getString("CAP"));
        this.setComune(rset.getString("COMUNE"));
        this.setComuneDesc(rset.getString("DESCRIZIONE"));
        this.setImportoSpedizione(rset.getString("IMPORTO_SPEDIZIONE"));
        this.setPec(rset.getString("PEC"));
        this.setCod_destinatario(rset.getString("CODICE_DESTINATARIO"));
        this.setDataIncasso(rset.getString("DATA_INCASSO"));
      }
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe DatiFattura");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe DatiFattura"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      e.printStackTrace();
      throw (e);
    }
    finally
    {
      if (conn!=null) conn.close();
    }
  }

  public void selectIntestatarioFattura(String idRichiesta,String fatturareA)
      throws Exception, SQLException
  {
    if (!isConnection())
           throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn = null;
    StringBuffer query = null;
    Statement stmt = null;

    try
    {
      conn = getConnection();
      stmt = conn.createStatement();
      if ("U".equals(fatturareA) || "T".equals(fatturareA) || "P".equals(fatturareA))
      {
        query = new StringBuffer("SELECT A.CODICE_IDENTIFICATIVO,");
        query.append("A.COGNOME_RAGIONE_SOCIALE,A.NOME,");
        query.append("A.INDIRIZZO,A.CAP,C.DESCRIZIONE, P.SIGLA_PROVINCIA, TO_CHAR(E.DATA_INCASSO,'DD/MM/YYYY') AS DATA_INCASSO ");
        query.append("FROM ANAGRAFICA A,ETICHETTA_CAMPIONE E,COMUNE C ");
        query.append(", PROVINCIA P ");

        if ("U".equals(fatturareA)){
          query.append("WHERE A.ID_ANAGRAFICA=E.ANAGRAFICA_UTENTE ");
        }
        if ("T".equals(fatturareA)){
          query.append("WHERE A.ID_ANAGRAFICA=E.ANAGRAFICA_TECNICO ");
        }
        if ("P".equals(fatturareA)){
          query.append("WHERE A.ID_ANAGRAFICA=E.ANAGRAFICA_PROPRIETARIO ");
        }

        query.append("AND A.COMUNE_RESIDENZA=C.CODICE_ISTAT ");
        query.append("AND C.PROVINCIA =P.ID_PROVINCIA ");
        query.append("AND E.ID_RICHIESTA = ").append(idRichiesta);
        //CuneoLogger.debug(this, "\nQuery DatiFattura.select()\n"+query.toString());
        ResultSet rset = stmt.executeQuery(query.toString());
        if (rset.next())
        {
          String nome,cognome;
          this.setCfPartitaIva(rset.getString("CODICE_IDENTIFICATIVO"));
          cognome=rset.getString("COGNOME_RAGIONE_SOCIALE");
          if (cognome==null) cognome="";
          nome=rset.getString("NOME");
          if (nome==null) nome="";
          this.setRagioneSociale(cognome+" "+nome);
          this.setIndirizzo(rset.getString("INDIRIZZO"));
          this.setCap(rset.getString("CAP"));
          this.setComuneDesc(rset.getString("DESCRIZIONE"));
          this.setSiglaProvincia(rset.getString("SIGLA_PROVINCIA"));
          this.setDataIncasso(rset.getString("DATA_INCASSO"));
        }
        rset.close();
      }
      else
      {
        if ("O".equals(fatturareA))
        {
          String idAnagrafica=null;
          query = new StringBuffer("SELECT EC.ANAGRAFICA_TECNICO,EC.ANAGRAFICA_UTENTE, TO_CHAR(EC.DATA_INCASSO,'DD/MM/YYYY') AS DATA_INCASSO ");
          query.append("FROM ETICHETTA_CAMPIONE EC ");
          query.append("WHERE EC.ID_RICHIESTA = ").append(idRichiesta);
          //CuneoLogger.debug(this, "\nQuery DatiFattura.select()\n"+query.toString());
          ResultSet rset = stmt.executeQuery(query.toString());
          if (rset.next())
          {
            idAnagrafica=rset.getString("ANAGRAFICA_TECNICO");
            if (idAnagrafica==null) idAnagrafica=rset.getString("ANAGRAFICA_UTENTE");
            this.setDataIncasso(rset.getString("DATA_INCASSO"));
          }
          rset.close();

          query.setLength(0);
          query.append("SELECT O.CF_PARTITA_IVA,O.RAGIONE_SOCIALE,");
          query.append("O.INDIRIZZO,O.CAP,C.DESCRIZIONE, P.SIGLA_PROVINCIA ");
          query.append("FROM ANAGRAFICA A,ORGANIZZAZIONE_PROFESSIONALE O,");
          query.append("COMUNE C, PROVINCIA P ");
          query.append("WHERE A.ID_ANAGRAFICA = ").append(idAnagrafica);
          query.append(" AND O.ID_ORGANIZZAZIONE=A.ID_ORGANIZZAZIONE");
          query.append(" AND C.CODICE_ISTAT=O.COMUNE_RESIDENZA");
          query.append(" AND C.PROVINCIA =P.ID_PROVINCIA ");
          //CuneoLogger.debug(this, "\nQuery DatiFattura.select()\n"+query.toString());
          rset = stmt.executeQuery(query.toString());
          if (rset.next())
          {
            //String nome,cognome;
            this.setCfPartitaIva(rset.getString("CF_PARTITA_IVA"));
            this.setRagioneSociale(rset.getString("RAGIONE_SOCIALE"));
            this.setIndirizzo(rset.getString("INDIRIZZO"));
            this.setCap(rset.getString("CAP"));
            this.setComuneDesc(rset.getString("DESCRIZIONE"));
            this.setSiglaProvincia(rset.getString("SIGLA_PROVINCIA"));
          }
          rset.close();
        }
        else
        {
          query = new StringBuffer("SELECT DF.CF_PARTITA_IVA,DF.RAGIONE_SOCIALE,");
          query.append("DF.INDIRIZZO,DF.CAP,C.DESCRIZIONE, P.SIGLA_PROVINCIA , TO_CHAR(EC.DATA_INCASSO,'DD/MM/YYYY') AS DATA_INCASSO ");
          query.append("FROM DATI_FATTURA DF, COMUNE C, ETICHETTA_CAMPIONE EC ");
          query.append(", PROVINCIA P ");
          query.append("WHERE DF.ID_RICHIESTA = ").append(idRichiesta);
          query.append(" AND C.CODICE_ISTAT=DF.COMUNE ");
          query.append("AND C.PROVINCIA =P.ID_PROVINCIA ");
          query.append("AND DF.ID_RICHIESTA = EC.ID_RICHIESTA ");
          //CuneoLogger.debug(this, "\nQuery DatiFattura.select()\n"+query.toString());
          ResultSet rset = stmt.executeQuery(query.toString());
          if (rset.next())
          {
            //String nome,cognome;
            this.setCfPartitaIva(rset.getString("CF_PARTITA_IVA"));
            this.setRagioneSociale(rset.getString("RAGIONE_SOCIALE"));
            this.setIndirizzo(rset.getString("INDIRIZZO"));
            this.setCap(rset.getString("CAP"));
            this.setComuneDesc(rset.getString("DESCRIZIONE"));
            this.setSiglaProvincia(rset.getString("SIGLA_PROVINCIA"));
            this.setDataIncasso(rset.getString("DATA_INCASSO"));
          }
          rset.close();
        }
        stmt.close();
      }
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("selectIntestatarioFattura della classe DatiFattura");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("selectIntestatarioFattura della classe DatiFattura"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      e.printStackTrace();
      throw (e);
    }
    finally
    {
      if (conn!=null) conn.close();
    }
  }

  /**
   * Metodo insertUpdate()
   * Per il momento cancelliamo sempre il record relativo a ID_RICHIESTA
   * e lo inseriamo subito dopo, in modo da non dover scrivere anche la
   * UPDATE.
   * @return Numero di record inseriti (1 oppure 0)
   * @throws Exception
   * @throws SQLException
   */
  public int insertUpdate()
      throws Exception, SQLException
  {
    if (!isConnection())
           throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn = null;
    StringBuffer query = null;
    Statement stmt = null;

    try
    {
      conn = getConnection();
      stmt = conn.createStatement();
      this.setIdRichiesta(String.valueOf(this.getAut().getIdRichiestaCorrente()));
      idRichiestaCorrente = this.getIdRichiesta();
      fatturareCorrente = this.getFatturare();
      query = new StringBuffer("DELETE FROM DATI_FATTURA ");
      query.append("WHERE ID_RICHIESTA = ").append(idRichiestaCorrente);
      //CuneoLogger.debug(this, "\nQuery DatiFattura.insert() - DELETE\n"+query.toString());
      stmt.executeUpdate(query.toString());
      stmt.close();

      stmt = conn.createStatement();
      query = _costruisciQuery();
      //CuneoLogger.debug(this, "\nQuery DatiFattura.insert() - INSERT\n"+query.toString());
      int inserted = 0;
      inserted = stmt.executeUpdate(query.toString());
      stmt.close();

      return inserted;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("insert della classe DatiFattura");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("insert della classe DatiFattura"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      e.printStackTrace();
      throw (e);
    }
    finally
    {
      if (conn!=null) conn.close();
    }
  }

  private StringBuffer _costruisciQuery()
  {
    String fatturaSNCorrente = this.getFatturaSN();
    String spedizioneCorrente = this.getSpedizione();
    StringBuffer query = new StringBuffer("INSERT INTO DATI_FATTURA ");
    query.append("( ID_RICHIESTA, FATTURA_SN, SPEDIZIONE, IMPORTO_SPEDIZIONE, FATTURARE, ");
    query.append("CF_PARTITA_IVA, RAGIONE_SOCIALE, INDIRIZZO, CAP, COMUNE, PEC , CODICE_DESTINATARIO ) ");
    query.append("SELECT " + idRichiestaCorrente + " AS ID_RICHIESTA, '"
                 + fatturaSNCorrente + "' AS FATTURA_SN, " );

    if ("S".equals(fatturaSNCorrente))
    {
      if ("S".equals(spedizioneCorrente))
        query.append("'S' AS SPEDIZIONE, "
                     + this.getImportoSpedizione() + " AS IMPORTO_SPEDIZIONE, '"
                     + fatturareCorrente + "' AS FATTURARE, " );
      else
        query.append("'N' AS SPEDIZIONE, "
                     + "NULL AS IMPORTO_SPEDIZIONE, '"
                     + fatturareCorrente + "' AS FATTURARE, " );

      /*
       * Per il momento queste cose non le cancello perché un giorno potrebbero servire,
       * ma per adesso non servono più

      if ("U".equals(fatturare)) // Utente
      {
        query.append("A.CODICE_IDENTIFICATIVO AS CF_PARTITA_IVA, ");
        query.append("A.COGNOME_RAGIONE_SOCIALE AS RAGIONE_SOCIALE, ");
        query.append("A.INDIRIZZO AS INDIRIZZO, ");
        query.append("A.CAP AS CAP, ");
        query.append("A.COMUNE_RESIDENZA AS COMUNE ");
        query.append("FROM ANAGRAFICA A, ETICHETTA_CAMPIONE EC ");
        query.append("WHERE EC.ID_RICHIESTA = " + idRichiestaCorrente);
        query.append(" AND A.ID_ANAGRAFICA=EC.ANAGRAFICA_UTENTE");
      }
      else if ("T".equals(fatturare)) // Tecnico
      {
        query.append("A.CODICE_IDENTIFICATIVO AS CF_PARTITA_IVA, ");
        query.append("A.COGNOME_RAGIONE_SOCIALE AS RAGIONE_SOCIALE, ");
        query.append("A.INDIRIZZO AS INDIRIZZO, ");
        query.append("A.CAP AS CAP, ");
        query.append("A.COMUNE_RESIDENZA AS COMUNE ");
        query.append("FROM ANAGRAFICA A, ETICHETTA_CAMPIONE EC ");
        query.append("WHERE EC.ID_RICHIESTA = " + idRichiestaCorrente);
        query.append(" AND A.ID_ANAGRAFICA=EC.ANAGRAFICA_TECNICO");
      }
      else if ("P".equals(fatturare)) // Proprietario
      {
        query.append("A.CODICE_IDENTIFICATIVO AS CF_PARTITA_IVA, ");
        query.append("A.COGNOME_RAGIONE_SOCIALE AS RAGIONE_SOCIALE, ");
        query.append("A.INDIRIZZO AS INDIRIZZO, ");
        query.append("A.CAP AS CAP, ");
        query.append("A.COMUNE_RESIDENZA AS COMUNE ");
        query.append("FROM ANAGRAFICA A, ETICHETTA_CAMPIONE EC ");
        query.append("WHERE EC.ID_RICHIESTA = " + idRichiestaCorrente);
        query.append(" AND A.ID_ANAGRAFICA=EC.ANAGRAFICA_PROPRIETARIO");
      }
      else if ("O".equals(fatturare)) // Organizzazione del tecnico
      {
        query.append("OP.CF_PARTITA_IVA AS CF_PARTITA_IVA, ");
        query.append("OP.RAGIONE_SOCIALE AS RAGIONE_SOCIALE, ");
        query.append("OP.INDIRIZZO AS INDIRIZZO, ");
        query.append("OP.CAP AS CAP, ");
        query.append("OP.COMUNE_RESIDENZA AS COMUNE ");
        query.append("FROM ORGANIZZAZIONE_PROFESSIONALE OP, ANAGRAFICA A, ETICHETTA_CAMPIONE EC ");
        query.append("WHERE EC.ID_RICHIESTA = " + idRichiestaCorrente);
        query.append(" AND A.ID_ANAGRAFICA=EC.ANAGRAFICA_TECNICO ");
        query.append("AND OP.ID_ORGANIZZAZIONE=A.ID_ORGANIZZAZIONE ");
      }
      */

      if ("A".equals(fatturare)) // Altro indirizzo
        query.append("'" + this.getCfPartitaIva() + "' AS CF_PARTITA_IVA, '"
                     + this.getRagioneSociale() + "' AS RAGIONE_SOCIALE, '"
                     + this.getIndirizzo() + "' AS INDIRIZZO, '"
                     + this.getCap() +"' AS CAP, '"
                     + this.getComune()+"' AS COMUNE, " );
      else // Per gli altri valori, si recuperano quelli aggiornati
           // da ANAGRAFICA o da ORGANIZZAZIONE_PROFESSIONALE
        query.append("NULL AS CF_PARTITA_IVA, "
                     + "NULL AS RAGIONE_SOCIALE, "
                     + "NULL AS INDIRIZZO, "
                     + "NULL AS CAP, "
                     + "NULL AS COMUNE, " );
      query.append((this.getPec()!=null?"'"+this.getPec()+"'":"NULL")+" AS PEC, ");
      query.append((this.getCod_destinatario()!=null?"'"+this.getCod_destinatario()+"'":"NULL")+" AS CODICE_DESTINATARIO ");
    }
    else
    {
        query.append("'N' AS SPEDIZIONE, NULL AS IMPORTO_SPEDIZIONE, "
                    + "NULL AS FATTURARE, NULL AS CF_PARTITA_IVA, "
                    + "NULL AS RAGIONE_SOCIALE, NULL AS INDIRIZZO, "
                    + "NULL AS CAP, NULL AS COMUNE, NULL AS PEC, NULL AS CODICE_DESTINATARIO" );
    }
    return query;
  }


  public String ControllaDati()
  {
    StringBuffer errore=new StringBuffer("");

    if ("A".equals(this.getFatturare()))
    {
      int lung;
      if (getCfPartitaIva()==null) lung = 0;
      else lung=getCfPartitaIva().length();
      if (lung!=11 && lung!=16)
        errore.append(";1");
      else
      {
         if (lung==11 && !Utili.controllaPartitaIVA(this.getCfPartitaIva()))
         {
            errore.append(";1");
         }
         if (lung==16 && !Utili.controllaCodiceFiscale(this.getCfPartitaIva()))
         {
            errore.append(";1");
         }
      }

      if (getRagioneSociale()!=null)
        setRagioneSociale(getRagioneSociale().trim());
      if (getRagioneSociale()==null || "".equals(getRagioneSociale()) || getRagioneSociale().length()>60)
      {
        errore.append(";2");
      }
      if (getIndirizzo()!=null)
        setIndirizzo(getIndirizzo().trim());
      if (getIndirizzo()==null || "".equals(getIndirizzo()) || getIndirizzo().length()>40)
      {
        errore.append(";3");
      }
      if (!Utili.controllaCap(getCap()))
      {
        errore.append(";4");
      }
      if (this.getComune()==null
               ||
       "".equals(this.getComune()))
      {
        errore.append(";5");
      }
    }

    if ((this.getFatturare()==null || "".equals(this.getFatturare()))
              &&
        ("S".equals(this.getFatturaSN())))
    {
      errore.append(";6");
    }

    /**
    * Se non sono stati trovati errori restituisce null
    */
    if (errore.toString().equals("")) return null;
    else return errore.toString();
  }

  /**
   * Viene utilizzato per determinare se tutte le richieste hanno lo stesso intestatario
   * anagraficaCampione.pdf
   * @param idRichiesta
   * @throws Exception
   * @throws SQLException
   */
  public HashMap<String, String> selectIntestatarioFatturaByIdRichiesteRange(String idRichieste) throws Exception, SQLException
  {
    if (! isConnection())
    {
      throw new Exception("Riferimento a DataSource non inizializzato");
    }
    Connection conn = null;
    StringBuffer query = new StringBuffer("");
    HashMap<String, String> hmFatturare = new HashMap<String, String>();

    try
    {
      conn = getConnection();
      Statement stmt = conn.createStatement();

      query = new StringBuffer("SELECT DISTINCT FATTURARE ");
      query.append("FROM DATI_FATTURA ");
      query.append("WHERE ID_RICHIESTA IN (");
      query.append(idRichieste);
      query.append(") ");
      //CuneoLogger.debug(this, query.toString());

      ResultSet rset = stmt.executeQuery(query.toString());
      while (rset.next())
      {
      	String fatturare = rset.getString("FATTURARE");
      	hmFatturare.put(fatturare, fatturare);
      }
      rset.close();
      stmt.close();
    }
    catch (java.sql.SQLException ex)
    {
      this.getAut().setQuery("selectIntestatarioFatturaByIdRichiesteRange della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch (Exception e)
    {
      this.getAut().setQuery("selectIntestatarioFatturaByIdRichiesteRange della classe EtichettaCampione"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      if (conn!=null) conn.close();
    }
    
    return hmFatturare;
  }

  /**
   * Viene utilizzato per conteggiare le richieste che hanno la tipologia di anagrafica passata in input a null
   * anagraficaCampione.pdf
   * @param idRichiesta
   * @throws Exception
   * @throws SQLException
   */
  public int selectConteggioIntestatarioFatturaNonValorizzatoByIdRichiesteRange(String idRichieste, String tipoAnagrafica) throws Exception, SQLException
  {
    if (! isConnection())
    {
      throw new Exception("Riferimento a DataSource non inizializzato");
    }
    Connection conn = null;
    StringBuffer query = new StringBuffer("");

    try
    {
      conn = getConnection();
      Statement stmt = conn.createStatement();

      query = new StringBuffer("SELECT COUNT(ID_RICHIESTA) CONTEGGIO ");
      query.append("FROM ETICHETTA_CAMPIONE ");
      query.append("WHERE ID_RICHIESTA IN (");
      query.append(idRichieste);
      query.append(") ");
      
      if ("T".equals(tipoAnagrafica))
      {
      	query.append("AND ANAGRAFICA_TECNICO IS NULL ");
      }

      if ("P".equals(tipoAnagrafica))
      {
      	query.append("AND ANAGRAFICA_PROPRIETARIO IS NULL ");
      }
      //CuneoLogger.debug(this, query.toString());

      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
      	return rset.getInt("CONTEGGIO");
      }
      rset.close();
      stmt.close();
    }
    catch (java.sql.SQLException ex)
    {
      this.getAut().setQuery("selectIntestatarioFatturaByIdRichiesteRange della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch (Exception e)
    {
      this.getAut().setQuery("selectIntestatarioFatturaByIdRichiesteRange della classe EtichettaCampione"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      if (conn!=null) conn.close();
    }
    
    return 0;
  }

  public int updateDatiFatturaByIdRichiesteRange(String idRichieste) throws Exception, SQLException{
    if (! isConnection())
    	throw new Exception("Riferimento a DataSource non inizializzato");

    Connection conn=this.getConnection();
    StringBuffer query=null;
    Statement stmt = null;

    try{
      stmt = conn.createStatement();
      query = new StringBuffer(" UPDATE DATI_FATTURA SET " );
      query.append(" FATTURARE = '").append(Utili.toVarchar(getFatturare())).append("', ");
      
      if (Utili.isEmpty(getCfPartitaIva()))
    	  query.append("CF_PARTITA_IVA = null, ");
      else
    	  query.append("CF_PARTITA_IVA = '").append(Utili.toVarchar(getCfPartitaIva())).append("', ");
      
      if (Utili.isEmpty(getRagioneSociale()))
    	  query.append("RAGIONE_SOCIALE = null, ");
      else
    	  query.append("RAGIONE_SOCIALE = '").append(Utili.toVarchar(getRagioneSociale())).append("', ");
      
      if (Utili.isEmpty(getIndirizzo()))
    	  query.append("INDIRIZZO = null, ");
      else
    	  query.append("INDIRIZZO = '").append(Utili.toVarchar(getIndirizzo())).append("', ");
      
      if (Utili.isEmpty(getCap()))
    	  query.append("CAP = null, ");
      else
    	  query.append("CAP = '").append(Utili.toVarchar(getCap())).append("', ");
      
      if (Utili.isEmpty(getComune()))
    	  query.append("COMUNE = null, ");
      else
    	  query.append("COMUNE = '").append(Utili.toVarchar(getComune())).append("', ");
      
      if (Utili.isEmpty(getPec()))
    	  query.append("PEC = null, ");
      else
    	  query.append("PEC = '").append(Utili.toVarchar(getPec())).append("', ");
      
      if (Utili.isEmpty(getCod_destinatario()))
          query.append("CODICE_DESTINATARIO = null ");
      else
          query.append("CODICE_DESTINATARIO = '").append(Utili.toVarchar(getCod_destinatario())).append("' ");
        
      query.append("WHERE ID_RICHIESTA IN (");
      query.append(idRichieste);
      query.append(") ");
      //CuneoLogger.debug(this, query.toString());

      int updated = stmt.executeUpdate(query.toString());
      stmt.close();
      return updated;
    }catch(java.sql.SQLException ex){
      this.getAut().setQuery("update della classe Anagrafica");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    }catch(Exception e){
      this.getAut().setQuery("update della classe Anagrafica: non è una SQLException ma una Exception generica");
      e.printStackTrace();
      throw (e);
    }finally{
      if (conn!=null) conn.close();
    }
  }

  /**
   * Metodo utilizzato per emettere la fattura in bianco
   * @param cfPartitaIva: tutti i parametri che seguono sono relativi
   * all'intestatario della fattura
   * @param ragioneSociale
   * @param indirizzo
   * @param cap
   * @param comuneDesc
   * @param siglaProvincia
   * @param importoSpedizione:importo della spedizione della fattura
   * @param pagata: indica se la fattura è stata pagata(S), non pagata (N)
   * @param importoImponibile: importo imponibile
   * @param importoIva: importo iva
   * @throws Exception
   * @throws SQLException
   */
  public int[] creaFatturaInBianco(String cfPartitaIva,
                          String ragioneSociale,
                          String indirizzo,
                          String cap,
                          String comuneDesc,
                          String siglaProvincia,
                          String pagata,
                          String descrizione,
                          String importoSpedizione,
                          String importoImponibile,
                          String importoIva
                         )
  throws Exception, SQLException
  {
    if (! isConnection())
    {
      throw new Exception("Riferimento a DataSource non inizializzato");
    }

    Connection conn = null;
    StringBuffer query = new StringBuffer();

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
      int numFattura[] = new int[2];
      numFattura[1] = Integer.parseInt(Utili.getYearDate());
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
          numFattura[0] = Integer.parseInt(rset.getString("NUM"));
        }
        catch(Exception eNum)
        {
          numFattura[0] = 0;
        }
      }
      else
      {
        numFattura[0] = 0;
      }
      numFattura[0]++;
      //CuneoLogger.debug(this, "numFattura "+numFattura);
      rset.close();

      /**
       * Se non ho trovato l'anno devo aggiornare il record con l'anno corrente
       * */
      if (numFattura[0] == 1)
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
       * Inserisco il record sulla tabella fattura
       * */
      query.setLength(0);
      query.append("INSERT INTO FATTURA(" );
      query.append("ANNO, NUMERO_FATTURA, DATA_FATTURA, PAGATA, ");
      query.append("PARTITA_IVA_O_CF, RAGIONE_SOCIALE, INDIRIZZO,");
      query.append("CAP, COMUNE, SIGLA_PROVINCIA, IMPORTO_SPEDIZIONE, DESCRIZIONE) ");
      query.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?) ");
      pstmt.close();
      pstmt = conn.prepareStatement(query.toString());

      pstmt.setInt(1, numFattura[1]);
      pstmt.setInt (2, numFattura[0]);
      pstmt.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
      pstmt.setString(4, pagata);
      pstmt.setString(5, cfPartitaIva);
      pstmt.setString(6, ragioneSociale);
      pstmt.setString(7, indirizzo);
      pstmt.setString(8, cap);
      pstmt.setString(9, comuneDesc);
      //Nel caso di stato estero devo cambiare la sigla provincia
      if (Constants.DESC_PROVINCIA_STATO_ESTERO.equals(siglaProvincia))
    	  siglaProvincia=Constants.SIGLA_PROVINCIA_STATO_ESTERO;
      pstmt.setString(10, siglaProvincia);
      pstmt.setDouble(11, Double.parseDouble(importoSpedizione.replace(',','.')));
      pstmt.setString(12, descrizione);
      pstmt.executeUpdate();

      // recupero il valore da utilizzare per ID_CAMPIONE_FATTURATO
      Long idCampioneFatturato = null;
      Statement stmt = conn.createStatement();
      query.setLength(0);
      
      query.append("SELECT NEXTVAL ('ID_CAMPIONE_FATTURATO') AS NEXTVAL" );
      rset = stmt.executeQuery( query.toString() );
      if (rset.next())
      {
      	idCampioneFatturato = rset.getLong("NEXTVAL");
      }
      rset.close();
      stmt.close();

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

      pstmt.setNull(1, java.sql.Types.NUMERIC);
      pstmt.setInt(2, numFattura[1]);
      pstmt.setInt (3, numFattura[0]);
      pstmt.setDouble(4, Double.parseDouble(importoImponibile.replace(',','.')));
      pstmt.setDouble(5, Double.parseDouble(importoIva.replace(',','.')));
      pstmt.setLong(6, idCampioneFatturato);
      pstmt.executeUpdate();
          
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
  
  
  public DatiFattura selectImportoPagamento(String iuv) throws Exception, SQLException {
	  String method = "DatiFattura - selectImportoPagamento - ";
    if (!isConnection())
    	throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    DatiFattura p = null;
    try{
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer(    
    		    " SELECT                            \n"
    		  + " SUM(COSTO_ANALISI) as IMPORTO    \n"
    		  + " FROM                              \n"
    		  + " PAGAMENTI P, ANALISI_RICHIESTE AR \n"
    		  + " WHERE                             \n"
    		  + " P.ID_RICHIESTA = AR.ID_RICHIESTA  \n"
    		  + " AND                               \n"
    		  + " IUV = '"+iuv+"'         \n"
    		  + " GROUP BY IUV      \n");
      CuneoLogger.debug(method,query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next()){
    	 p = new DatiFattura();
    	 p.setImportoBD(rset.getBigDecimal("IMPORTO"));
    	 CuneoLogger.debug(method, p.getImportoBD());
      }
      rset.close();
      stmt.close();
    }catch(java.sql.SQLException ex) {
    	CuneoLogger.error(method,"SQLException - "+ex);
    	this.getAut().setQuery("selectPagamento della classe Pagamento");
    	this.getAut().setContenutoQuery(query.toString());
    	throw (ex);
    }catch(Exception e){
    	CuneoLogger.error(method,"Exception - "+e);
    	this.getAut().setQuery("selectPagamento della classe Pagamento"
                            +": non è una SQLException ma una Exception"
                            +" generica");
    	this.getAut().setContenutoQuery(query.toString());
    	throw (e);
    }finally{
    	if (conn!=null) conn.close();
    		return p;
    }
  }

  public String getIdRichiesta()
  {
    return this.idRichiesta;
  }
  public void setIdRichiesta( String newIdRichiesta )
  {
    this.idRichiesta = newIdRichiesta;
  }

  public String getFatturaSN()
  {
    return fatturaSN;
  }
  public void setFatturaSN(String fatturaSN)
  {
    this.fatturaSN = fatturaSN;
  }

  public String getSpedizione()
  {
    return this.spedizione;
  }
  public void setSpedizione( String newSpedizione )
  {
    this.spedizione = newSpedizione;
  }

  public String getImportoSpedizione()
  {
    return this.importoSpedizione;
  }
  public void setImportoSpedizione( String newImportoSpedizione )
  {
    this.importoSpedizione = newImportoSpedizione;
  }

  public String getFatturare()
  {
    return this.fatturare;
  }
  public void setFatturare( String newFatturare )
  {
    this.fatturare = newFatturare;
  }

  public String getCfPartitaIva()
  {
    if (null == this.cfPartitaIva) this.cfPartitaIva="";
    return this.cfPartitaIva;
  }
  public void setCfPartitaIva( String newCfPartitaIva )
  {
    if (newCfPartitaIva!=null)
      this.cfPartitaIva=newCfPartitaIva.toUpperCase();
    else
      this.cfPartitaIva = newCfPartitaIva;
  }

  public String getRagioneSociale()
  {
    if (null == this.ragioneSociale) this.ragioneSociale="";
    return this.ragioneSociale;
  }
  public void setRagioneSociale( String newRagioneSociale )
  {
    this.ragioneSociale = newRagioneSociale;
  }

  public String getIndirizzo()
  {
    if (null == this.indirizzo) this.indirizzo="";
    return this.indirizzo;
  }
  public void setIndirizzo( String newIndirizzo )
  {
    this.indirizzo = newIndirizzo;
  }

  public String getCap()
  {
    if (null == this.cap) this.cap="";
    return this.cap;
  }
  public void setCap( String newCap )
  {
    this.cap = newCap;
  }

  public String getComune()
  {
    if (null == this.comune) this.comune="";
    return this.comune;
  }
  public void setComune( String newComune )
  {
    this.comune = newComune;
  }
  public void setComuneDesc(String comuneDesc)
  {
    this.comuneDesc = comuneDesc;
  }
  public String getComuneDesc()
  {
    if (null == this.comuneDesc) this.comuneDesc="";
    return comuneDesc;
  }
  public void setSiglaProvincia(String siglaProvincia)
  {
    this.siglaProvincia = siglaProvincia;
  }
  public String getSiglaProvincia()
  {
    if (null == this.siglaProvincia) this.siglaProvincia="";
    return siglaProvincia;
  }
	public String getDescrizione()
	{
		if (null == this.descrizione) this.descrizione = "";
		return descrizione;
	}
	public void setDescrizione(String descrizione)
	{
		this.descrizione = descrizione;
	}
	public String getPagata()
	{
		return pagata;
	}
	public void setPagata(String pagata)
	{
		if (null == this.pagata) this.pagata = "";
		this.pagata = pagata;
	}
	public String getCod_destinatario() {
		return cod_destinatario;
	}
	public void setCod_destinatario(String cod_destinatario) {
		this.cod_destinatario = cod_destinatario;
	}
	public String getPec() {
		return pec;
	}
	public void setPec(String pec) {
		this.pec = pec;
	}
	public String getDataIncasso() {
		return dataIncasso;
	}
	public void setDataIncasso(String dataIncasso) {
		this.dataIncasso = dataIncasso;
	}
	

public BigDecimal getImportoBD() {
	return importoBD;
}

public void setImportoBD(BigDecimal importoBD) {
	this.importoBD = importoBD;
}
}