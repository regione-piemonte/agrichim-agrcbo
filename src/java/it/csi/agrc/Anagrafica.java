package it.csi.agrc;

import java.math.BigDecimal;
import java.sql.*;
import it.csi.cuneo.*;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class Anagrafica extends BeanDataSource
{
  public String idAnagrafica;
  public String codiceIdentificativo;
  public String tipoPersona;
  public String cognomeRagioneSociale;
  public String nome;
  public String indirizzo;
  public String cap;
  public String comuneResidenza;
  public String telefono;
  public String cellulare;
  public String fax;
  public String email;
  public String idOrganizzazione;
  public String tipoUtente;
  public String idAnagrafica2;
  public String codDestinatario;
  public String pec;
  public String codContabilia;

  public static int ANAGRAFICA_AZIENDA = 0;
  public static int ANAGRAFICA_PRIVATO = 1;
  public static int ANAGRAFICA_TECNICO = 2;
  public static int ANAGRAFICA_TECNICO_INSERIMENTO = 3;
  public static int ANAGRAFICA_LAR = 4;

  public Anagrafica ()
  {
  }
  public Anagrafica ( String idAnagrafica, String codiceIdentificativo,
                      String tipoPersona, String cognomeRagioneSociale,
                      String nome, String indirizzo, String cap,
                      String comuneResidenza, String telefono, String cellulare,
                      String fax, String email, String idOrganizzazione,
                      String tipoUtente, String idAnagrafica2 )
  {
    this.idAnagrafica=idAnagrafica;
    this.codiceIdentificativo=codiceIdentificativo;
    this.tipoPersona=tipoPersona;
    this.cognomeRagioneSociale=cognomeRagioneSociale;
    this.nome=nome;
    this.indirizzo=indirizzo;
    this.cap=cap;
    this.comuneResidenza=comuneResidenza;
    this.telefono=telefono;
    this.cellulare=cellulare;
    this.fax=fax;
    this.email=email;
    this.idOrganizzazione=idOrganizzazione;
    this.tipoUtente=tipoUtente;
    this.idAnagrafica2=idAnagrafica2;
  }

  /**
   * Questo costruttore viene usato nella ricerca delle anagrafiche
   * */
  public Anagrafica ( String idAnagrafica, String cognomeRagioneSociale,
                      String nome, String codiceIdentificativo,
                      String tipoUtente, String comuneResidenza,
                      String idAnagrafica2 )
  {
    this.idAnagrafica=idAnagrafica;
    this.cognomeRagioneSociale=cognomeRagioneSociale;
    this.nome=nome;
    this.codiceIdentificativo=codiceIdentificativo;
    this.tipoUtente=tipoUtente;
    this.comuneResidenza=comuneResidenza;
    this.idAnagrafica2=idAnagrafica2;
  }

  /**
   * Questa funzione viene usata per visualizzare il dettaglio di un'anagrafica
   * @param idAnagrafica
   * @throws Exception
   * @throws SQLException
   */
  public void select(String idAnagrafica)
      throws Exception, SQLException
  {
    if (!isConnection())
           throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=this.getConnection();
    StringBuffer query=new StringBuffer("");
    Statement stmt = null;
    try
    {
        stmt = conn.createStatement();
        query.append("SELECT ");
        query.append("(CASE WHEN A.TIPO_PERSONA='F' THEN 'Persona fisica'");
        query.append("      WHEN A.TIPO_PERSONA='G' THEN 'Azienda' END) AS TIPO_PERSONA, ");
        query.append("(CASE WHEN A.TIPO_UTENTE='L' THEN 'Laboratorio' ");
        query.append("      WHEN A.TIPO_UTENTE='P' THEN 'Privato' ");
        query.append("      WHEN A.TIPO_UTENTE='T' THEN 'Tecnico' ELSE 'Nessuno' END) AS TIPO_UTENTE, ");
        query.append("O.RAGIONE_SOCIALE AS ORGANIZZAZIONE,A.CODICE_IDENTIFICATIVO,A.COGNOME_RAGIONE_SOCIALE,A.NOME,");
        query.append("A.INDIRIZZO,A.CAP,A.COMUNE_RESIDENZA AS ISTAT,A.EMAIL,A.TELEFONO,A.CELLULARE,A.FAX,A.ID_ANAGRAFICA_2, ");
        query.append("A.CODICE_DESTINATARIO, A.PEC, A.CODICE_CONTABILIA ");
        query.append(" FROM ANAGRAFICA A ");
        query.append("   LEFT OUTER JOIN ORGANIZZAZIONE_PROFESSIONALE O ON (A.ID_ORGANIZZAZIONE=O.ID_ORGANIZZAZIONE) ");
        query.append(" WHERE ID_ANAGRAFICA=").append(idAnagrafica);
        //CuneoLogger.debug(this, "\nQuery Anagrafiche.fill()\n"+query.toString());
        ResultSet rset = stmt.executeQuery(query.toString());

        if (rset.next())
        {
             setCodiceIdentificativo(rset.getString("CODICE_IDENTIFICATIVO"));
             setTipoPersona(rset.getString("TIPO_PERSONA"));
             setCognomeRagioneSociale(rset.getString("COGNOME_RAGIONE_SOCIALE"));
             setNome(rset.getString("NOME"));
             setIndirizzo(rset.getString("INDIRIZZO"));
             setCap(rset.getString("CAP"));
             setComuneResidenza(rset.getString("ISTAT"));
             setTelefono(rset.getString("TELEFONO"));
             setCellulare(rset.getString("CELLULARE"));
             setFax(rset.getString("FAX"));
             setEmail(rset.getString("EMAIL"));
             setIdOrganizzazione(rset.getString("ORGANIZZAZIONE"));
             setTipoUtente(rset.getString("TIPO_UTENTE"));
             setIdAnagrafica2(rset.getString("ID_ANAGRAFICA_2"));
             setCodDestinatario(rset.getString("CODICE_DESTINATARIO"));
             setPec(rset.getString("PEC"));
             setCodContabilia(rset.getString("CODICE_CONTABILIA"));
        }
        rset.close();
        stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe Anagrafica");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe Anagrafica"
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
   * Restituisce tutti i dati del record con anagrafica uguale a idAnagrafica
   * @param idAnagrafica
   * @throws Exception
   * @throws SQLException
   */
  public void getAnagrafica(String idAnagrafica)
      throws Exception, SQLException
  {
    if (!isConnection())
           throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=this.getConnection();
    StringBuffer query=new StringBuffer("");
    Statement stmt = null;
    try
    {
        stmt = conn.createStatement();
        query.append("SELECT * FROM ANAGRAFICA ");
        query.append("WHERE ID_ANAGRAFICA="+idAnagrafica+" ");
        //CuneoLogger.debug(this, "\nQuery Anagrafiche.fill()\n"+query.toString());
        ResultSet rset = stmt.executeQuery(query.toString());

        if (rset.next())
        {
             setIdAnagrafica(rset.getString("ID_ANAGRAFICA"));
             setCodiceIdentificativo(rset.getString("CODICE_IDENTIFICATIVO"));
             setTipoPersona(rset.getString("TIPO_PERSONA"));
             setCognomeRagioneSociale(rset.getString("COGNOME_RAGIONE_SOCIALE"));
             setNome(rset.getString("NOME"));
             setIndirizzo(rset.getString("INDIRIZZO"));
             setCap(rset.getString("CAP"));
             setComuneResidenza(rset.getString("COMUNE_RESIDENZA"));
             setTelefono(rset.getString("TELEFONO"));
             setCellulare(rset.getString("CELLULARE"));
             setFax(rset.getString("FAX"));
             setEmail(rset.getString("EMAIL"));
             setIdOrganizzazione(rset.getString("ID_ORGANIZZAZIONE"));
             setTipoUtente(rset.getString("TIPO_UTENTE"));
             setIdAnagrafica2(rset.getString("ID_ANAGRAFICA_2"));
        }
        rset.close();
        stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("getAnagrafica della classe Anagrafica");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("getAnagrafica della classe Anagrafica"
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
   * Questa funzione controlla che i dati che verranno inseriti o modificati nel
   * database siano consistenti
   * */
  public String ControllaDati()
  {
    StringBuffer errore=new StringBuffer("");
    /**
    *   elimino gli spazi in più
    */
    if (getIndirizzo()!=null) setIndirizzo(getIndirizzo().trim());
    if (getIndirizzo()!=null && getIndirizzo().length()>40)
    {
      errore.append(";1");
    }
    if (getCap()!=null && !Utili.controllaCap(getCap()))
    {
      errore.append(";2");
    }
    if (this.getComuneResidenza()==null
             ||
     "".equals(this.getComuneResidenza()))
    {
      errore.append(";3");
    }
    if (getEmail()!=null && !"".equals(getEmail()) && !Utili.controllaMail(getEmail(),50))
    {
      errore.append(";4");
    }
    if (getTelefono()!=null && getTelefono().length()>20)
    {
      errore.append(";5");
    }
    if (getFax()!=null && getFax().length()>20)
    {
      errore.append(";6");
    }
    if (getCodDestinatario()!=null && getCodDestinatario().length()>7)
    	errore.append(";10");
    if (getPec()!=null && !"".equals(getPec()) && !Utili.controllaMail(getPec(),50))
    	errore.append(";11");
    if (getCodContabilia()!=null && getCodContabilia().length()>7)
    	errore.append(";12");
    /**
    * Se non sono stati trovati errori restituisce null
    */
    if (errore.toString().equals("")) return null;
    else return errore.toString();
  }

  /**
   * Questa funzione controlla che i dati che modificati nel
   * database siano consistenti. Viene utilizata nella pagina di modifica
   * richiamata da "Ricerca anagrafica clienti e utenti"
  * */
  public String ControllaDatiUpdate()
  {
    StringBuffer errore=new StringBuffer("");
    /**
    *   elimino gli spazi in più
    */
    if (getCognomeRagioneSociale()!=null) setCognomeRagioneSociale(getCognomeRagioneSociale().trim());
    if (getNome()!=null) 
    	setNome(getNome().trim());

    if (getCognomeRagioneSociale() ==null
        || "".equals(getCognomeRagioneSociale())
        || getCognomeRagioneSociale().length()>60){
      errore.append(";1");
    }

    if ("Persona fisica".equals(tipoPersona)) {
      if (getNome() ==null
          || "".equals(getNome())
          || getNome().length()>40) {
        errore.append(";2");
      }
    }
    if (getIndirizzo()!=null && getIndirizzo().length()>40)
      errore.append(";3");
    if (getCap()!=null && !Utili.controllaCap(getCap()))
      errore.append(";4");
    if (this.getComuneResidenza()==null
             ||
     "".equals(this.getComuneResidenza())) {
      errore.append(";5");
    }
    if (getEmail()!=null && !"".equals(getEmail()) && !Utili.controllaMail(getEmail(),50))
      errore.append(";6");
    if (getTelefono()!=null && getTelefono().length()>20)
      errore.append(";7");
    if (getCellulare()!=null && getCellulare().length()>20)
      errore.append(";8");
    if (getFax()!=null && getFax().length()>20)
      errore.append(";9");
    if (getCodDestinatario()!=null && getCodDestinatario().length()>7)
    	errore.append(";10");
    if (getPec()!=null && !"".equals(getPec()) && !Utili.controllaMail(getPec(),50))
    	errore.append(";11");
    if (getCodContabilia()!=null && getCodContabilia().length()>30)
    	errore.append(";12");

    /**
    * Se non sono stati trovati errori restituisce null
    */
    if (errore.toString().equals("")) return null;
    else return errore.toString();
  }

  public int update() throws Exception, SQLException {
    if (!isConnection())
           throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=this.getConnection();
    StringBuffer query=null;
    PreparedStatement stmt = null;
    try {
    	query = new StringBuffer( "UPDATE ANAGRAFICA SET " );
        query.append("INDIRIZZO= ?, ");
        query.append("CAP= ?, ");
        query.append("COMUNE_RESIDENZA= ?, ");
        query.append("TELEFONO= ?, ");
        query.append("FAX= ?, ");
        query.append("EMAIL= ?, ");
		query.append("PEC= ?, ");
		query.append("CODICE_DESTINATARIO= ?, ");
		query.append("CODICE_CONTABILIA= ? ");
		query.append("WHERE ID_ANAGRAFICA= ?");
		CuneoLogger.debug(this, query.toString());
		stmt = conn.prepareStatement(query.toString());
		stmt.setString(1, getIndirizzo()!=null?Utili.validazioneSQLParam(getIndirizzo()):null);
		stmt.setString(2, getCap()!=null?Utili.validazioneSQLParam(getCap()):null);
		stmt.setString(3, this.getComuneResidenza()!=null?Utili.validazioneSQLParam(this.getComuneResidenza()):null);
		stmt.setString(4, this.getTelefono()!=null?Utili.validazioneSQLParam(this.getTelefono()):null);
		stmt.setString(5, this.getFax()!=null?Utili.validazioneSQLParam(this.getFax()):null);
		stmt.setString(6, this.getEmail()!=null?Utili.validazioneSQLParam(this.getEmail()):null);
		stmt.setString(7, this.getPec()!=null?Utili.validazioneSQLParam(this.getPec()):null);
		stmt.setString(8, this.getCodDestinatario()!=null?Utili.validazioneSQLParam(this.getCodDestinatario()):null);
		stmt.setString(9, this.getCodContabilia()!=null?Utili.validazioneSQLParam(this.getCodContabilia()):null);
		stmt.setBigDecimal(10, this.getIdAnagrafica()!=null?new BigDecimal(this.getIdAnagrafica()):null);
		int updated = stmt.executeUpdate();
		stmt.close();
		return updated;
    } catch(java.sql.SQLException ex) {
      this.getAut().setQuery("update della classe Anagrafica");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    } catch(Exception e) {
      this.getAut().setQuery("update della classe Anagrafica"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      e.printStackTrace();
      throw (e);
    } finally {
      if (conn!=null) conn.close();
    }
  }

  /**
   * Questa funzione viene usata per modificare i dati nel
   * database Viene utilizata nella pagina di modifica
   * richiamata da "Ricerca anagrafica clienti e utenti"
  * */
  public int updateAnag() throws Exception, SQLException{
    if (!isConnection())
           throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=this.getConnection();
    StringBuffer query=null;
    PreparedStatement stmt = null;
    try {
//      stmt = conn.createStatement();
      query = new StringBuffer( "UPDATE ANAGRAFICA SET " );
      query.append("COGNOME_RAGIONE_SOCIALE = ?, ");
      query.append("NOME= ?, ");
      query.append("INDIRIZZO= ?, ");
      query.append("CAP= ?, ");
      query.append("COMUNE_RESIDENZA= ?, ");
      query.append("TELEFONO= ?, ");
      query.append("FAX= ?, ");
      query.append("CELLULARE= ?, ");
      query.append("EMAIL= ?, ");
      query.append("PEC= ?, ");
      query.append("CODICE_DESTINATARIO= ?, ");
      query.append("CODICE_CONTABILIA= ? ");
      query.append("WHERE CODICE_IDENTIFICATIVO= ?");
      CuneoLogger.debug(this, query.toString());
      stmt = conn.prepareStatement(query.toString());
      stmt.setString(1, getCognomeRagioneSociale()!=null?Utili.validazioneSQLParam(getCognomeRagioneSociale()):"");
      stmt.setString(2, getNome()!=null?Utili.validazioneSQLParam(getNome()):"");
      stmt.setString(3, getIndirizzo()!=null?Utili.validazioneSQLParam(getIndirizzo()):"");
      stmt.setString(4, getCap()!=null?Utili.validazioneSQLParam(getCap()):"");
      stmt.setString(5, this.getComuneResidenza()!=null?Utili.validazioneSQLParam(this.getComuneResidenza()):"");
      stmt.setString(6, this.getTelefono()!=null?Utili.validazioneSQLParam(this.getTelefono()):"");
      stmt.setString(7, this.getFax()!=null?Utili.validazioneSQLParam(this.getFax()):"");
      stmt.setString(8, getCellulare()!=null?Utili.validazioneSQLParam(getCellulare()):"");
      stmt.setString(9, this.getEmail()!=null?Utili.validazioneSQLParam(this.getEmail()):"");
      stmt.setString(10, this.getPec()!=null?Utili.validazioneSQLParam(this.getPec()):"");
      stmt.setString(11, this.getCodDestinatario()!=null?Utili.validazioneSQLParam(this.getCodDestinatario()):"");
      stmt.setString(12, this.getCodContabilia()!=null?Utili.validazioneSQLParam(this.getCodContabilia()):"");
      stmt.setString(13, this.getCodiceIdentificativo()!=null?Utili.validazioneSQLParam(this.getCodiceIdentificativo()):"");
      int updated = stmt.executeUpdate();
      stmt.close();
      return updated;
    } catch(java.sql.SQLException ex) {
      this.getAut().setQuery("updateAnag della classe Anagrafica");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    } catch(Exception e) {
      this.getAut().setQuery("updateAnag della classe Anagrafica"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      e.printStackTrace();
      throw (e);
    } finally {
      if (conn!=null) conn.close();
    }
  }

  /**
   * Questo metodo viene utilizzato per inserire un anagrafica corrispondente
   * ad un nuovo tecnico
   **/
  public void insertTecnico() throws Exception, SQLException {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    try {
      conn=this.getConnection();
      // Si imposta a false l'autocommit perché le operazioni che vengono eseguite sul DB
      // devono essere effettuate in blocco
      // Si noti l'invocazione del metodo conn.commit() PRIMA di conn.close() e
      // l'invocazione di conn.rollback() nel blocco catch{}
      conn.setAutoCommit( false );
      // Per evitare ogni rischio di interferenza nel recuperare l'ultimo valore creato
      // nella sequence, imposto il massimo livello di isolamento di questa transazione
      conn.setTransactionIsolation( Connection.TRANSACTION_SERIALIZABLE );
      insert(conn);
      conn.commit();
    } catch(java.sql.SQLException ex) {
	     this.getAut().setQuery("insertTecnico della classe Anagraficampione");
	     this.getAut().setContenutoQuery("");
	     try {
	    	conn.rollback();
	     } catch( java.sql.SQLException ex2 ) {
			this.getAut().setQuery("insertTecnico della classe Anagrafica"
			                  +":problemi con il rollback");
			this.getAut().setContenutoQuery("");
			throw (ex2);
	     }
	     throw (ex);
   } catch(Exception e) {
	   this.getAut().setQuery("insertTecnico della classe Anagrafica"
                            +": non è una SQLException ma una Exception"
                            +" generica");
	   this.getAut().setContenutoQuery("");
	   throw (e);
   } finally {
	   if (conn!=null) {
		   conn.setAutoCommit(true);
		   conn.close();
	   }
   	}
  }

  public void insert(Connection conn) throws Exception, SQLException {
    // recupero il valore da utilizzare per ID_ANAGRAFICA
    //PreparedStatement stmt= conn.createStatement();
	PreparedStatement stmt = null;
    StringBuffer query= new StringBuffer("");
    //stmt = conn.createStatement();
    query = new StringBuffer( "INSERT INTO ANAGRAFICA " );
    query.append("(ID_ANAGRAFICA,CODICE_IDENTIFICATIVO, TIPO_PERSONA,");
    query.append("COGNOME_RAGIONE_SOCIALE, NOME, INDIRIZZO,");
    query.append("CAP, COMUNE_RESIDENZA, TELEFONO, CELLULARE, FAX, EMAIL, ");
    query.append("ID_ORGANIZZAZIONE, ");
    query.append("TIPO_UTENTE, ID_ANAGRAFICA_2) ");
    query.append("VALUES(nextval('ID_ANAGRAFICA'), ?,'F', ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,'T',? ) ");
    CuneoLogger.debug(this, "query "+query.toString());
    stmt = conn.prepareStatement(query.toString());
    stmt.setString(1, getCodiceIdentificativo()!=null?Utili.validazioneSQLParam(getCodiceIdentificativo()):"");
    //F
    stmt.setString(2, getCognomeRagioneSociale()!=null?Utili.validazioneSQLParam(getCognomeRagioneSociale()):"");
    stmt.setString(3, getNome()!=null?Utili.validazioneSQLParam(getNome()):"");
    stmt.setString(4, getIndirizzo()!=null?Utili.validazioneSQLParam(getIndirizzo()):"");
    stmt.setString(5, getCap()!=null?Utili.validazioneSQLParam(getCap()):"");
    stmt.setString(6, getComuneResidenza()!=null?Utili.validazioneSQLParam(getComuneResidenza()):"");
    stmt.setString(7, getTelefono()!=null?Utili.validazioneSQLParam(getTelefono()):"");
    stmt.setString(8, getCellulare()!=null?Utili.validazioneSQLParam(getCellulare()):"");
    stmt.setString(9, getFax()!=null?Utili.validazioneSQLParam(getFax()):"");
    stmt.setString(10, getEmail()!=null?Utili.validazioneSQLParam(getEmail()):"");
    stmt.setBigDecimal(11, this.getIdOrganizzazione()!=null && !this.getIdOrganizzazione().equals("")?new BigDecimal(this.getIdOrganizzazione()):null);
    //T
    stmt.setBigDecimal(12, this.getIdAnagrafica2()!=null && !this.getIdAnagrafica2().equals("")?new BigDecimal(this.getIdAnagrafica2()):null);
    stmt.executeUpdate();
    stmt.close();
  }

  /**
 * Questa funzione controlla che i dati che verranno inseriti o modificati nel
 * database siano consistenti
 * */
public String ControllaDati(int anagrafica)
{
  StringBuffer errore=new StringBuffer("");
  if (anagrafica==Anagrafica.ANAGRAFICA_TECNICO_INSERIMENTO)
  {
    int lung;
    if ("-1".equals(getIdOrganizzazione()))
      errore.append(";1");
    
    if (getCodiceIdentificativo()==null) 
    	lung = 0;
    else 
    	lung= getCodiceIdentificativo().length();
    if (lung!= 16)
      errore.append(";2");
    else {
       if (lung==16 && !Utili.controllaCodiceFiscale(this.getCodiceIdentificativo()))
          errore.append(";2");
    }
    if (getCognomeRagioneSociale()!=null) 
    	setCognomeRagioneSociale(Utili.validazioneSQLParam(getCognomeRagioneSociale()).trim());
    else
    	errore.append(";3");
    if (getCognomeRagioneSociale() ==null || "".equals(getCognomeRagioneSociale()) || getCognomeRagioneSociale().length()>60)
    	errore.append(";3");
    
    if (getNome()!=null) 
    	setNome(Utili.validazioneSQLParam(getNome()).trim());
    else
    	errore.append(";4");
    
    if (getNome() ==null || "".equals(getNome()) || getNome().length()>40)
    	errore.append(";4");

    if (getIndirizzo()!=null) 
    	setIndirizzo(Utili.validazioneSQLParam(getIndirizzo()).trim());
    else
    	errore.append(";5");
    
    if (getIndirizzo() ==null || "".equals(getIndirizzo()) || getIndirizzo().length()>40)
    	errore.append(";5");
    
    if (getCap()!=null && !"".equals(getCap()) && !Utili.controllaCap(getCap()))
    	errore.append(";6");
    
    if (this.getComuneResidenza()==null || "".equals(this.getComuneResidenza()))
    	errore.append(";7");
    
    if (getTelefono()!=null && !("").equals(getTelefono())) 
    	setTelefono(Utili.validazioneSQLParam(getTelefono()).trim());
    else
    	setTelefono(null);
    
    if (getCellulare()!=null && !("").equals(getCellulare())) 
    	setCellulare(Utili.validazioneSQLParam(getCellulare()).trim());
    else
    	setCellulare(null);
    
    if ((getTelefono()==null || "".equals(getTelefono()))
                        &&
        (getCellulare()==null || "".equals(getCellulare()))
       )
    {
      errore.append(";8");
      errore.append(";9");
    } else {
      if (getTelefono()!=null && (getTelefono().length()>20))
    	  errore.append(";8");
      if (getCellulare()!=null && (getCellulare().length()>20))
    	  errore.append(";9");
    }
    if (getEmail()!=null && !"".equals(getEmail()) && !Utili.controllaMail(getEmail(),50))
      errore.append(";10");
  }
  /**
  * Se non sono stati trovati errori restituisce null
  */
  if (errore.toString().equals("")) 
	  return null;
  else 
	  return errore.toString();
  }

  public String controllaAnagrafica()
    throws Exception, SQLException
  {
    if (getCodiceIdentificativo()==null ||
              "".equals(getCodiceIdentificativo()))
        return null;
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=this.getConnection();
    StringBuffer query=new StringBuffer("");
    Statement stmt = null;
    try
    {
      stmt = conn.createStatement();
      query.append("SELECT A.ID_ANAGRAFICA FROM ANAGRAFICA A ");
      query.append("WHERE A.CODICE_IDENTIFICATIVO = '");
      query.append(this.getCodiceIdentificativo());
      query.append("'");
      String queryStr=query.toString();
      ResultSet rset = stmt.executeQuery(queryStr);

      if (rset.next())
        return (rset.getString("ID_ANAGRAFICA"));
      else return null;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("controllaAnagrafica della classe Anagrafica");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("controllaAnagrafica della classe Anagrafica"
                             +": non è una SQLException ma una Exception"
                             +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      if (conn!=null)  conn.close();
    }
  }



  public String getCap()
  {
    return cap;
  }
  public void setCap(String cap)
  {
    this.cap = cap;
  }
  public String getCellulare()
  {
    return cellulare;
  }
  public void setCellulare(String cellulare)
  {
    this.cellulare = cellulare;
  }
  public String getCodiceIdentificativo()
  {
    if (codiceIdentificativo==null) return null;
    return codiceIdentificativo.toUpperCase();
  }
  public void setCodiceIdentificativo(String codiceIdentificativo)
  {
    this.codiceIdentificativo = codiceIdentificativo;
  }
  public String getCognomeRagioneSociale()
  {
    return cognomeRagioneSociale;
  }
  public void setCognomeRagioneSociale(String cognomeRagioneSociale)
  {
    this.cognomeRagioneSociale = cognomeRagioneSociale;
  }
  public String getComuneResidenza()
  {
    return comuneResidenza;
  }
  public void setComuneResidenza(String comuneResidenza)
  {
    this.comuneResidenza = comuneResidenza;
  }
  public String getEmail()
  {
    return email;
  }
  public void setEmail(String email)
  {
    this.email = email;
  }
  public String getFax()
  {
    return fax;
  }
  public void setFax(String fax)
  {
    this.fax = fax;
  }
  public String getIdAnagrafica()
  {
    return idAnagrafica;
  }
  public void setIdAnagrafica(String idAnagrafica)
  {
    this.idAnagrafica = idAnagrafica;
  }
  public String getIdOrganizzazione()
  {
    return idOrganizzazione;
  }
  public void setIdOrganizzazione(String idOrganizzazione)
  {
    this.idOrganizzazione = idOrganizzazione;
  }
  public String getIndirizzo()
  {
    return indirizzo;
  }
  public void setIndirizzo(String indirizzo)
  {
    this.indirizzo = indirizzo;
  }
  public String getNome()
  {
    return nome;
  }
  public void setNome(String nome)
  {
    this.nome = nome;
  }
  public String getTelefono()
  {
    return telefono;
  }
  public void setTelefono(String telefono)
  {
    this.telefono = telefono;
  }
  public String getTipoPersona()
  {
    return tipoPersona;
  }
  public void setTipoPersona(String tipoPersona)
  {
    this.tipoPersona = tipoPersona;
  }
  public String getTipoUtente()
  {
    return tipoUtente;
  }
  public void setTipoUtente(String tipoUtente)
  {
    this.tipoUtente = tipoUtente;
  }
  public String getIdAnagrafica2()
  {
    return idAnagrafica2;
  }
  public void setIdAnagrafica2(String idAnagrafica2)
  {
    this.idAnagrafica2 = idAnagrafica2;
  }
public String getCodDestinatario() {
	return codDestinatario;
}
public void setCodDestinatario(String codDestinatario) {
	this.codDestinatario = codDestinatario;
}
public String getPec() {
	return pec;
}
public void setPec(String pec) {
	this.pec = pec;
}
public String getCodContabilia() {
	return codContabilia;
}
public void setCodContabilia(String codContabilia) {
	this.codContabilia = codContabilia;
}
}