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

public class OrganizzazioneProfessionale  extends BeanDataSource
{
  private String idOrganizzazione;
  private String idTipoOrganizzazione;
  private String tipoOrganizzazione;
  private String cfPartitaIva;
  private String ragioneSociale;
  private String sedeTerritoriale;
  private String indirizzo;
  private String cap;
  private String comune;
  private String siglaProvincia;
  private String telefono;
  private String fax;
  private String email;
  private String codDestinatario;
  private String pec;
  private String codContabilia;

  public OrganizzazioneProfessionale()
  {
  }

  public OrganizzazioneProfessionale(Object dataSource, Autenticazione aut)
  {
    this.setDataSource(dataSource);
    this.setAut(aut);
  }

  /*public OrganizzazioneProfessionale ( String idOrganizzazione,
    String idTipoOrganizzazione, String cfPartitaIva, String ragioneSociale,
    String sedeTerritoriale, String indirizzo, String cap, String comuneResidenza,
    String telefono, String fax, String email )
  {
    this.idOrganizzazione=idOrganizzazione;
    this.idTipoOrganizzazione=idTipoOrganizzazione;
    this.cfPartitaIva=cfPartitaIva;
    this.ragioneSociale=ragioneSociale;
    this.sedeTerritoriale=sedeTerritoriale;
    this.indirizzo=indirizzo;
    this.cap=cap;
    this.comuneResidenza=comuneResidenza;
    this.telefono=telefono;
    this.fax=fax;
    this.email=email;
  }*/

  public OrganizzazioneProfessionale ( String idOrganizzazione, String cfPartitaIva,
    String ragioneSociale,String sedeTerritoriale, String indirizzo, String comune)
  {
    this.idOrganizzazione=idOrganizzazione;
    this.cfPartitaIva=cfPartitaIva;
    this.ragioneSociale=ragioneSociale;
    this.sedeTerritoriale=sedeTerritoriale;
    this.indirizzo=indirizzo;
    this.comune=comune;
  }

  public void select(String idOrganizzazione)
		  throws Exception, SQLException
		  {
		    if (idOrganizzazione==null)
		      return;
		    if (!isConnection())
		      throw new Exception("Riferimento a DataSource non inizializzato");
		    Connection conn=null;
		    StringBuffer query=new StringBuffer("");
		    try
		    {
		      conn=getConnection();
		      Statement stmt = conn.createStatement();

		      query = new StringBuffer("SELECT ID_ORGANIZZAZIONE, OP.ID_TIPO_ORGANIZZAZIONE, ");
		      query.append("CF_PARTITA_IVA, RAGIONE_SOCIALE, SEDE_TERRITORIALE, INDIRIZZO,");
		      query.append("OP.CAP, C.DESCRIZIONE AS COMUNE, P.SIGLA_PROVINCIA, ");
		      query.append("TELEFONO, FAX, EMAIL, T.DESCRIZIONE AS TIPO_ORGANIZZAZIONE ");
		      query.append("FROM TIPO_ORGANIZZAZIONE T, PROVINCIA P, COMUNE C, ");
		      query.append("ORGANIZZAZIONE_PROFESSIONALE OP ");
		      query.append("WHERE ID_ORGANIZZAZIONE=").append(idOrganizzazione);
		      query.append(" AND OP.COMUNE_RESIDENZA=C.CODICE_ISTAT");
		      query.append(" AND C.PROVINCIA=P.ID_PROVINCIA");
		      query.append(" AND OP.ID_TIPO_ORGANIZZAZIONE=T.ID_TIPO_ORGANIZZAZIONE");
		      //CuneoLogger.debug(this,query.toString());
		      ResultSet rset = stmt.executeQuery(query.toString());
		      if (rset.next())
		      {
		        this.setIdOrganizzazione(rset.getString("ID_ORGANIZZAZIONE"));
		        this.setIdTipoOrganizzazione(rset.getString("ID_TIPO_ORGANIZZAZIONE"));
		        this.setTipoOrganizzazione(rset.getString("TIPO_ORGANIZZAZIONE"));
		        this.setRagioneSociale(rset.getString("RAGIONE_SOCIALE"));
		        this.setSedeTerritoriale(rset.getString("SEDE_TERRITORIALE"));
		        this.setIndirizzo(rset.getString("INDIRIZZO"));
		        this.setCap(rset.getString("CAP"));
		        this.setComune(rset.getString("COMUNE"));
		        this.setSiglaProvincia(rset.getString("SIGLA_PROVINCIA"));
		        this.setTelefono(rset.getString("TELEFONO"));
		        this.setFax(rset.getString("FAX"));
		        this.setEmail(rset.getString("EMAIL"));
		        this.setCfPartitaIva(rset.getString("CF_PARTITA_IVA"));
		      }
		      rset.close();
		      stmt.close();
		    }
		    catch(java.sql.SQLException ex)
		    {
		      this.getAut().setQuery("select della classe OrganizzazioneProfessionale");
		      this.getAut().setContenutoQuery(query.toString());
		      throw (ex);
		    }
		    catch(Exception e)
		    {
		      this.getAut().setQuery("select della classe OrganizzazioneProfessionale"
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

  /***
   * Utilizzata per recuperare i dati del record per visualizzarli prima di
   * procedere con la modifica
   */
  public void select()
  throws Exception, SQLException
  {
    if (idOrganizzazione==null)
      return;
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();

      query = new StringBuffer("SELECT ID_TIPO_ORGANIZZAZIONE, CF_PARTITA_IVA,");
      query.append("RAGIONE_SOCIALE,SEDE_TERRITORIALE,");
      query.append("INDIRIZZO, CAP, COMUNE_RESIDENZA,");
      query.append("TELEFONO, FAX, EMAIL,CODICE_DESTINATARIO,PEC,CODICE_CONTABILIA ");
      query.append("FROM ORGANIZZAZIONE_PROFESSIONALE ");
      query.append("WHERE ID_ORGANIZZAZIONE=").append(Utili.validazioneSQLParam(idOrganizzazione));
      //CuneoLogger.debug(this, query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        this.setIdTipoOrganizzazione(rset.getString("ID_TIPO_ORGANIZZAZIONE"));
        this.setCfPartitaIva(rset.getString("CF_PARTITA_IVA"));
        this.setRagioneSociale(rset.getString("RAGIONE_SOCIALE"));
        this.setSedeTerritoriale(rset.getString("SEDE_TERRITORIALE"));
        this.setIndirizzo(rset.getString("INDIRIZZO"));
        this.setCap(rset.getString("CAP"));
        this.setComune(rset.getString("COMUNE_RESIDENZA"));
        this.setTelefono(rset.getString("TELEFONO"));
        this.setFax(rset.getString("FAX"));
        this.setEmail(rset.getString("EMAIL"));
        this.setCodDestinatario(rset.getString("CODICE_DESTINATARIO"));
        this.setPec(rset.getString("PEC"));
        this.setCodContabilia(rset.getString("CODICE_CONTABILIA"));
        
      }
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe OrganizzazioneProfessionale");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe OrganizzazioneProfessionale"
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
   * Questo metodo va ad inserire un nuovo record all'interno della tabella
   * ORGANIZZAZIONE_PROFESSIONALE.
   * @throws Exception
   * @throws SQLException
   */
  public void insert() throws Exception, SQLException {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    PreparedStatement stmt = null;
    try {
      conn=getConnection();
      //Statement stmt = conn.createStatement();
      query = new StringBuffer("INSERT INTO ORGANIZZAZIONE_PROFESSIONALE (");
      query.append("ID_ORGANIZZAZIONE, ID_TIPO_ORGANIZZAZIONE, CF_PARTITA_IVA,");
      query.append("RAGIONE_SOCIALE,SEDE_TERRITORIALE,");
      query.append("INDIRIZZO, CAP, COMUNE_RESIDENZA,");
      query.append("TELEFONO, FAX, EMAIL) ");
      query.append("VALUES (nextval('ID_ORGANIZZAZIONE'), ?,?,?,?,?,?,?,?,?,?) ");
      stmt = conn.prepareStatement(query.toString());
      CuneoLogger.debug(this, query.toString());
      stmt.setBigDecimal(1, getIdTipoOrganizzazione()!=null?Utili.parseBigDecimal(getIdTipoOrganizzazione()):null);
      stmt.setString(2, getCfPartitaIva()!=null?Utili.validazioneSQLParam(getCfPartitaIva()):"");
      stmt.setString(3, getRagioneSociale()!=null?Utili.validazioneSQLParam(getRagioneSociale()):"");
      stmt.setString(4, getSedeTerritoriale()!=null?Utili.validazioneSQLParam(getSedeTerritoriale()):"");
      stmt.setString(5, getIndirizzo()!=null?Utili.validazioneSQLParam(getIndirizzo()):"");
      stmt.setString(6, getCap()!=null?Utili.validazioneSQLParam(getCap()):"");
      stmt.setString(7, getComune()!=null?Utili.validazioneSQLParam(getComune()):"");
      stmt.setString(8, getTelefono()!=null?Utili.validazioneSQLParam(getTelefono()):"");
      stmt.setString(9, getFax()!=null?Utili.validazioneSQLParam(getFax()):"");
      stmt.setString(10, getEmail()!=null?Utili.validazioneSQLParam(getEmail()):"");
      stmt.executeUpdate();
      stmt.close();
    } catch(java.sql.SQLException ex) {
      this.getAut().setQuery("insert della classe OrganizzazioneProfessionale");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    } catch(Exception e) {
      this.getAut().setQuery("insert della classe OrganizzazioneProfessionale"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    } finally {
      if (conn!=null) conn.close();
    }
  }

  /**
   * Questo metodo va a modificare un nuovo record della tabella
   * ORGANIZZAZIONE_PROFESSIONALE.
   * @throws Exception
   * @throws SQLException
   */
  public void update() throws Exception, SQLException {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    PreparedStatement stmt = null;
    try {
      conn=getConnection();
      query = new StringBuffer("UPDATE ORGANIZZAZIONE_PROFESSIONALE ");
      query.append(" SET ID_TIPO_ORGANIZZAZIONE = ? ");
      query.append(",CF_PARTITA_IVA = ? ");
      query.append(",RAGIONE_SOCIALE = ? ");
      query.append(",SEDE_TERRITORIALE = ? ");
      query.append(",INDIRIZZO = ? ");
      query.append(",CAP = ? ");
      query.append(",COMUNE_RESIDENZA = ? ");
      query.append(",TELEFONO = ? ");
      query.append(",FAX = ? ");
      query.append(",EMAIL = ? ");
      query.append(",CODICE_DESTINATARIO = ? ");
      query.append(",PEC = ? ");
      query.append(",CODICE_CONTABILIA	= ? ");
      query.append(" WHERE ID_ORGANIZZAZIONE = ?");
      stmt = conn.prepareStatement(query.toString());
      CuneoLogger.debug(this, query.toString());
      stmt.setBigDecimal(1, getIdTipoOrganizzazione()!=null?Utili.parseBigDecimal(getIdTipoOrganizzazione()):null);
      stmt.setString(2, getCfPartitaIva()!=null?Utili.validazioneSQLParam(getCfPartitaIva()):"");
      stmt.setString(3, getRagioneSociale()!=null?Utili.validazioneSQLParam(getRagioneSociale()):"");
      stmt.setString(4, getSedeTerritoriale()!=null?Utili.validazioneSQLParam(getSedeTerritoriale()):"");
      stmt.setString(5, getIndirizzo()!=null?Utili.validazioneSQLParam(getIndirizzo()):"");
      stmt.setString(6, getCap()!=null?Utili.validazioneSQLParam(getCap()):"");
      stmt.setString(7, getComune()!=null?Utili.validazioneSQLParam(getComune()):"");
      stmt.setString(8, getTelefono()!=null?Utili.validazioneSQLParam(getTelefono()):"");
      stmt.setString(9, getFax()!=null?Utili.validazioneSQLParam(getFax()):"");
      stmt.setString(10, getEmail()!=null?Utili.validazioneSQLParam(getEmail()):"");
      stmt.setString(11, getCodDestinatario()!=null?Utili.validazioneSQLParam(getCodDestinatario()):"");
      stmt.setString(12, getPec()!=null?Utili.validazioneSQLParam(getPec()):"");
      stmt.setString(13, getCodContabilia()!=null?Utili.validazioneSQLParam(getCodContabilia()):"");
      stmt.setBigDecimal(14, getIdOrganizzazione()!=null?Utili.parseBigDecimal(getIdOrganizzazione()):null);
      stmt.executeUpdate();
      stmt.close();
    } catch(java.sql.SQLException ex) {
      this.getAut().setQuery("update della classe OrganizzazioneProfessionale");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    } catch(Exception e) {
      this.getAut().setQuery("update della classe OrganizzazioneProfessionale"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    } finally {
      if (conn!=null) conn.close();
    }
  }

  /**
   * Questo metodo va a cancellare un record dalla tabella
   * ORGANIZZAZIONE_PROFESSIONALE.
   * @throws Exception
   * @throws SQLException
   */
  public void delete() throws Exception, SQLException {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    PreparedStatement stmt = null;
    try {
      conn=getConnection();
      query = new StringBuffer("DELETE FROM ORGANIZZAZIONE_PROFESSIONALE ");
      query.append("WHERE ID_ORGANIZZAZIONE = ?");
      stmt = conn.prepareStatement(query.toString());
      stmt.setBigDecimal(1, Utili.parseBigDecimal(getIdOrganizzazione()));
      CuneoLogger.debug(this, query.toString());
      stmt.executeUpdate(query.toString());
      stmt.close();
    } catch(java.sql.SQLException ex) {
      this.getAut().setQuery("delete della classe OrganizzazioneProfessionale");
      this.getAut().setContenutoQuery(query.toString());
      if (ex.getErrorCode()==2292) throw new Exception("2292 Non è "
        +"stato possibile cancellare questa Organizzazione in quanto "
        +"esistono dei tecnici che ne fanno parte.");
      else {
        ex.printStackTrace();
        throw (ex);
      }
    } catch(Exception e) {
      this.getAut().setQuery("delete della classe OrganizzazioneProfessionale"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    } finally {
      if (conn!=null) conn.close();
    }
  }

  /**
   * Questa funzione controlla che i dati che verranno inseriti o modificati nel
   * database siano consistenti
   * */
  public String ControllaDati() {
    StringBuffer errore=new StringBuffer("");
    int lung;
    
    if ("-1".equals(getIdTipoOrganizzazione())) 
      errore.append(";1");

    if (getCfPartitaIva()==null) lung = 0;
    else lung= getCfPartitaIva().length();
    if (!(lung==11 || lung==16 || lung==0))
      errore.append(";2");
    else {
      if (lung==11 && !Utili.controllaPartitaIVA(getCfPartitaIva()))
        errore.append(";2");
      if (lung==16 && !Utili.controllaCodiceFiscale(getCfPartitaIva()))
        errore.append(";2");
    }

    if (getRagioneSociale() !=null) 
    	setRagioneSociale(Utili.validazioneSQLParam(getRagioneSociale()).trim());
    else
    	errore.append(";3");
    if (getRagioneSociale() ==null
        || "".equals(getRagioneSociale())
        || getRagioneSociale().length()>60){
      errore.append(";3");
    }

    if (getIndirizzo()!=null) 
    	setIndirizzo(Utili.validazioneSQLParam(getIndirizzo()).trim());
    else
    	errore.append(";4");
    if (getIndirizzo() ==null || "".equals(getIndirizzo()) || getIndirizzo().length()>40) 
      errore.append(";4");

    if (getSedeTerritoriale() !=null) {
      setSedeTerritoriale(Utili.validazioneSQLParam(getSedeTerritoriale()).trim());
      if (getSedeTerritoriale().length()>30)
        errore.append(";5");
    }    
    
    if (getCap()!=null && !"".equals(getCap()) && !Utili.controllaCap(getCap()))
    	errore.append(";6");
    
    if (this.getComune()==null || "".equals(this.getComune())) 
    	errore.append(";7");
    else if(this.getComune()!=null && !Utili.isNumber(this.getComune()))
    	errore.append(";7");
    
    if (getTelefono() !=null){
      setTelefono(Utili.validazioneSQLParam(getTelefono()).trim());
      if (getTelefono().length()>20)
        errore.append(";8");
    }else
    	errore.append(";8");
    
    if (getFax() !=null){
      setFax(Utili.validazioneSQLParam(getFax()).trim());
      if (getFax().length()>20)
        errore.append(";9");
    }
    
    if (getEmail()!=null && !"".equals(getEmail()) && !Utili.controllaMail(getEmail(),50))
      errore.append(";10");
    
    if (getCodDestinatario()!=null && (getCodDestinatario().length()>7))
    	errore.append(";11");
    if (getPec()!=null && !"".equals(getPec()) && !Utili.controllaMail(getPec(),50))
    	errore.append(";12");
    if (getCodContabilia()!=null && (getCodContabilia().length()>7))
    	errore.append(";13");
    /**
    * Se non sono stati trovati errori restituisce null
    */
    if (errore.toString().equals("")) 
    	return null;
    else 
    	return errore.toString();
  }


  public String getIdOrganizzazione()
  {
    return this.idOrganizzazione;
  }
  public void setIdOrganizzazione( String newIdOrganizzazione )
  {
    this.idOrganizzazione = newIdOrganizzazione;
  }

  public String getIdTipoOrganizzazione()
  {
    return this.idTipoOrganizzazione;
  }
  public void setIdTipoOrganizzazione( String newIdTipoOrganizzazione )
  {
    this.idTipoOrganizzazione = newIdTipoOrganizzazione;
  }

  public String getCfPartitaIva()
  {
    return this.cfPartitaIva;
  }
  public void setCfPartitaIva( String newCfPartitaIva )
  {
    this.cfPartitaIva = newCfPartitaIva;
  }

  public String getRagioneSociale()
  {
    return this.ragioneSociale;
  }
  public void setRagioneSociale( String newRagioneSociale )
  {
    this.ragioneSociale = newRagioneSociale;
  }

  public String getSedeTerritoriale()
  {
    return this.sedeTerritoriale;
  }
  public void setSedeTerritoriale( String newSedeTerritoriale )
  {
    this.sedeTerritoriale = newSedeTerritoriale;
  }

  public String getIndirizzo()
  {
    return this.indirizzo;
  }
  public void setIndirizzo( String newIndirizzo )
  {
    this.indirizzo = newIndirizzo;
  }

  public String getCap()
  {
    return this.cap;
  }
  public void setCap( String newCap )
  {
    this.cap = newCap;
  }


  public String getTelefono()
  {
    return this.telefono;
  }
  public void setTelefono( String newTelefono )
  {
    this.telefono = newTelefono;
  }

  public String getFax()
  {
    return this.fax;
  }
  public void setFax( String newFax )
  {
    this.fax = newFax;
  }

  public String getEmail()
  {
    return this.email;
  }
  public void setEmail( String newEmail )
  {
    this.email = newEmail;
  }
  public String getComune()
  {
    return comune;
  }
  public void setComune(String comune)
  {
    this.comune = comune;
  }
  public void setSiglaProvincia(String siglaProvincia)
  {
    this.siglaProvincia = siglaProvincia;
  }
  public String getSiglaProvincia()
  {
    return siglaProvincia;
  }
  public String getTipoOrganizzazione()
  {
    return tipoOrganizzazione;
  }
  public void setTipoOrganizzazione(String tipoOrganizzazione)
  {
    this.tipoOrganizzazione = tipoOrganizzazione;
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