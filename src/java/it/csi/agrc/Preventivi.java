package it.csi.agrc;
import it.csi.cuneo.*;

import java.util.*;
import java.math.BigDecimal;
import java.sql.*;

public class Preventivi extends BeanDataSource implements Modello {
	private long idRichiesta;
	private BigDecimal idPreventivo;
	private String idPreventivoStr;
	private String note_aggiuntive;
	private String note;
	private String numero_preventivo;
	private String importo;
	private String codice_fiscale;
	private ArrayList<Preventivi> preventivi = new ArrayList<Preventivi>();
	
	public Preventivi(){}
	public Preventivi(Object dataSource, Autenticazione aut){
		this.setDataSource(dataSource);
		this.setAut(aut);
	}
  /**
   *  Legge tutti i dati dalla tabella PREVENTIVI
   **/
	public void select() throws Exception, SQLException {
		select(null);
	}
	
	public void select(String id_preventivo) throws Exception, SQLException {
		if (!isConnection())
			throw new Exception("Riferimento a DataSource non inizializzato");
		Connection conn=null;
		StringBuffer query=null;
		try {
			conn=getConnection();
			Statement stmt = conn.createStatement();
			query=new StringBuffer("SELECT id_preventivi, codice_fiscale, ");
			query.append("numero_preventivo,"); 
			query.append("importo, "); 
			query.append("note_aggiuntive ");
			query.append("FROM PREVENTIVI ");
			if(id_preventivo!=null && !id_preventivo.equals(""))
				query.append("WHERE id_preventivi = ").append(id_preventivo).append(" ");
			query.append("ORDER BY codice_fiscale, numero_preventivo");
			CuneoLogger.debug(this, "query.toString() "+query.toString());
		    ResultSet rset = stmt.executeQuery(query.toString());
		    Preventivi preventivo; 
		    while (rset.next()){
		    	preventivo = new Preventivi();
		    	preventivo.setIdPreventivo(rset.getBigDecimal("id_preventivi"));
		    	preventivo.setCodice_fiscale(rset.getString("codice_fiscale"));
		    	preventivo.setNumero_preventivo(rset.getString("numero_preventivo"));
		    	preventivo.setNote_aggiuntive(rset.getString("note_aggiuntive"));
		    	preventivo.setImporto(rset.getString("importo"));
		    	this.preventivi.add(preventivo);
		    }
		    rset.close();
		    stmt.close();
		} catch(java.sql.SQLException ex) {
			this.getAut().setQuery("select della classe Preventivi");
			this.getAut().setContenutoQuery(query.toString());
			throw (ex);
		} catch(Exception e) {
			this.getAut().setQuery("select della classe Preventivi"
                            +": non è una SQLException ma una Exception"
                            +" generica");
			this.getAut().setContenutoQuery(query.toString());
			throw (e);
		} finally {
			if (conn!=null) conn.close();
		}
	}
	
	public void selectPreventivo(String id_richiesta) throws Exception, SQLException {
		if (!isConnection())
			throw new Exception("Riferimento a DataSource non inizializzato");
		Connection conn=null;
		StringBuffer query=null;
		try {
			conn=getConnection();
			Statement stmt = conn.createStatement();
			query=new StringBuffer("SELECT p.numero_preventivo, p.codice_fiscale, p.importo, p.note_aggiuntive, ad.note ");
			query.append("FROM preventivi p ");
			query.append("left join etichetta_campione ec on p.id_preventivi=ec.id_preventivi ");
			query.append("left join analisi_dati ad on ec.id_richiesta=ad.id_richiesta  ");
			query.append("WHERE ec.id_richiesta='").append(id_richiesta).append("' ");
			CuneoLogger.debug(this, "query.toString() "+query.toString());
			ResultSet rset = stmt.executeQuery(query.toString());
			Preventivi preventivo; 
			while (rset.next()){
				preventivo = new Preventivi();
				preventivo.setCodice_fiscale(rset.getString("codice_fiscale"));
				preventivo.setNumero_preventivo(rset.getString("numero_preventivo"));
				preventivo.setNote_aggiuntive(rset.getString("note_aggiuntive"));
				preventivo.setImporto(rset.getString("importo"));
				preventivo.setNote(rset.getString("note"));
				this.preventivi.add(preventivo);
			}
			rset.close();
			stmt.close();
		} catch(java.sql.SQLException ex) {
			this.getAut().setQuery("select della classe Preventivi");
			this.getAut().setContenutoQuery(query.toString());
			throw (ex);
		} catch(Exception e) {
			this.getAut().setQuery("select della classe Preventivi"
					+": non è una SQLException ma una Exception"
					+" generica");
			this.getAut().setContenutoQuery(query.toString());
			throw (e);
		} finally {
			if (conn!=null) conn.close();
		}
	}
	
	public boolean checkUtilizzoPreventivo(BigDecimal id_preventivo) throws Exception, SQLException {
		if (!isConnection())
			throw new Exception("Riferimento a DataSource non inizializzato");
		Connection conn=null;
		StringBuffer query=null;
		boolean ret = false;
		try {
			conn=getConnection();
			Statement stmt = conn.createStatement();
			query=new StringBuffer("SELECT id_richiesta ");
			query.append("FROM etichetta_campione ");
			query.append("WHERE id_preventivi = ").append(id_preventivo).append(" ");
			CuneoLogger.debug(this, "query.toString() "+query.toString());
			ResultSet rset = stmt.executeQuery(query.toString());
			if(rset.next()){
				ret = true;
			}
			rset.close();
			stmt.close();
		} catch(java.sql.SQLException ex) {
			this.getAut().setQuery("select della classe Preventivi");
			this.getAut().setContenutoQuery(query.toString());
			throw (ex);
		} catch(Exception e) {
			this.getAut().setQuery("select della classe Preventivi"
					+": non è una SQLException ma una Exception"
					+" generica");
			this.getAut().setContenutoQuery(query.toString());
			throw (e);
		} finally {
			if (conn!=null) 
				conn.close();
			return ret;
		}
	}
	
	public int selectPreventivoCF(String cf, String preventivo) throws Exception, SQLException {
		  if (!isConnection())
			  throw new Exception("Riferimento a DataSource non inizializzato");
		  Connection conn=null;
		  StringBuffer query=null;
		  int ret = 0;
		  try {
			  conn=getConnection();
			  Statement stmt = conn.createStatement();
			  query=new StringBuffer("SELECT p.codice_fiscale, p.numero_preventivo, p.importo, p.note_aggiuntive ");
			  query.append("FROM preventivi p ");
			  query.append("left join etichetta_campione ec on p.id_preventivi=ec.id_preventivi ");
			  query.append("WHERE p.numero_preventivo='").append(preventivo).append("' ");
			  query.append("AND p.codice_fiscale=UPPER('").append(cf).append("')");
			  CuneoLogger.debug(this,"query.toString() "+query.toString());
			  ResultSet rset = stmt.executeQuery(query.toString());
			  while (rset.next()) {
				  ret++;
				  importo=rset.getString("IMPORTO");
				  note_aggiuntive=rset.getString("NOTE_AGGIUNTIVE");
			  }
			  rset.close();
			  stmt.close();
		  } catch(java.sql.SQLException ex) {
			  this.getAut().setQuery("selectPreventivoCF della classe Analisi");
			  this.getAut().setContenutoQuery(query.toString());
			  throw (ex);
		  } catch(Exception e) {
			  this.getAut().setQuery("selectPreventivoCF della classe Analisi"
					  +": non è una SQLException ma una Exception"
					  +" generica");
			  this.getAut().setContenutoQuery(query.toString());
			  throw (e);
		  } finally {
			  if (conn!=null) 
				  conn.close();
			  return ret;
		  }
	  }
	  
	@Override
	public int update() throws Exception, SQLException {
		if (!isConnection())
	           throw new Exception("Riferimento a DataSource non inizializzato");
	    Connection conn = null;
	    StringBuffer query = new StringBuffer();
	    Statement stmt = null;
	    try {
	    	conn = getConnection();
	    	stmt = conn.createStatement();
		    query = new StringBuffer( "UPDATE PREVENTIVI SET " );
		    query.append("CODICE_FISCALE = '").append(this.getCodice_fiscale()).append("', ");
		    query.append("IMPORTO = ").append(this.getImporto_BD()).append(", ");
		    query.append("NOTE_AGGIUNTIVE = '").append(Utili.toVarchar(this.getNote_aggiuntive())).append("' ");
		    query.append("WHERE ID_PREVENTIVI = ");
		    query.append(this.getIdPreventivo()).append(" ");
		    
		    CuneoLogger.debug(this, "query "+query.toString());
		    stmt.executeUpdate(query.toString());
		    stmt.close();
	    } catch(java.sql.SQLException ex) {
	        this.getAut().setQuery("UPDATE della classe Preventivo");
	        this.getAut().setContenutoQuery(query.toString());
	        try {
	          conn.rollback();
	        } catch( java.sql.SQLException ex2 ) {
	          this.getAut().setQuery("UPDATE della classe Preventivo: problemi con il rollback");
	          this.getAut().setContenutoQuery(query.toString());
	          throw (ex2);
	        }
	        throw (ex);
	    } catch(Exception e) {
	        this.getAut().setQuery("UPDATE della classe Preventivo: non è una SQLException ma una Exception generica");
	        this.getAut().setContenutoQuery(query.toString());
	        throw (e);
	    } finally {
	        if (conn!=null) {
	          conn.setAutoCommit(true);
	          conn.close();
	        }
	      }
		return 0;
	}
	
	public void insert() throws Exception, SQLException {
		if (!isConnection())
	           throw new Exception("Riferimento a DataSource non inizializzato");
	    Connection conn = null;
	    StringBuffer query = new StringBuffer();
	    Statement stmt = null;
	    try {
	    	conn = getConnection();
	    	stmt = conn.createStatement();
			query = new StringBuffer( "SELECT nextval('ID_PREVENTIVI') as NEXTVAL" );
		    ResultSet rset = stmt.executeQuery( query.toString() );
		    if ( rset.next() )
		      this.setIdPreventivo(rset.getBigDecimal( "NEXTVAL" ));
		    rset.close();
	
		    query.setLength( 0 );
		    //stmt = conn.createStatement();
		    query = new StringBuffer( "INSERT INTO PREVENTIVI " );
		    query.append("(ID_PREVENTIVI,NUMERO_PREVENTIVO, CODICE_FISCALE,");
		    query.append("IMPORTO, NOTE_AGGIUNTIVE) ");
		    query.append("VALUES( ");
		    query.append(this.getIdPreventivo()).append(", '");
		    query.append(this.getNumero_preventivo()).append("', '");
		    query.append(this.getCodice_fiscale()).append("', ");
		    query.append(this.getImporto_BD()).append(", '");
		    query.append(Utili.toVarchar(this.getNote_aggiuntive())).append("') ");
		    
		    CuneoLogger.debug(this, "query "+query.toString());
		    stmt.executeUpdate(query.toString());
		    stmt.close();
	    } catch(java.sql.SQLException ex) {
	        this.getAut().setQuery("insert della classe Preventivo");
	        this.getAut().setContenutoQuery(query.toString());
	        try {
	          conn.rollback();
	        } catch( java.sql.SQLException ex2 ) {
	          this.getAut().setQuery("insert della classe Preventivo: problemi con il rollback");
	          this.getAut().setContenutoQuery(query.toString());
	          throw (ex2);
	        }
	        throw (ex);
	    } catch(Exception e) {
	        this.getAut().setQuery("insert della classe Preventivo: non è una SQLException ma una Exception generica");
	        this.getAut().setContenutoQuery(query.toString());
	        throw (e);
	    } finally {
	        if (conn!=null) {
	          conn.setAutoCommit(true);
	          conn.close();
	        }
	      }
	}
	
	public int delete() throws Exception, SQLException {
		if (!isConnection())
	           throw new Exception("Riferimento a DataSource non inizializzato");
	    Connection conn = null;
	    StringBuffer query = new StringBuffer();
	    Statement stmt = null;
	    try {
	    	conn = getConnection();
	    	stmt = conn.createStatement();
		    query = new StringBuffer( "DELETE FROM PREVENTIVI " );
		    query.append("WHERE ID_PREVENTIVI = ");
		    query.append(this.getIdPreventivo()).append(" ");
		    CuneoLogger.debug(this, "query "+query.toString());
		    stmt.executeUpdate(query.toString());
		    stmt.close();
	    } catch(java.sql.SQLException ex) {
	        this.getAut().setQuery("DELETE della classe Preventivo");
	        this.getAut().setContenutoQuery(query.toString());
	        try {
	          conn.rollback();
	        } catch( java.sql.SQLException ex2 ) {
	          this.getAut().setQuery("DELETE della classe Preventivo: problemi con il rollback");
	          this.getAut().setContenutoQuery(query.toString());
	          throw (ex2);
	        }
	        throw (ex);
	    } catch(Exception e) {
	        this.getAut().setQuery("DELETE della classe Preventivo: non è una SQLException ma una Exception generica");
	        this.getAut().setContenutoQuery(query.toString());
	        throw (e);
	    } finally {
	        if (conn!=null) {
	          conn.setAutoCommit(true);
	          conn.close();
	        }
	      }
	    return 0;
	}
	
	/**
	   * Questa funzione controlla che i dati che verranno inseriti o modificati nel
	   * database siano consistenti
	 * @throws Exception 
	 * @throws SQLException 
	   * */
	  public String ControllaDati(boolean update) throws SQLException, Exception  {
	    StringBuffer errore=new StringBuffer("");
	    int lung;
	    
	    if (getNumero_preventivo() ==null
		        || "".equals(getNumero_preventivo()))
	    	errore.append(";1");
	    else if (getNumero_preventivo() !=null && !Utili.isNumber(getNumero_preventivo())) 
	      errore.append(";1");
	    
	    if (getCodice_fiscale()==null) 
	    	lung = 0;
	    else 
	    	lung= getCodice_fiscale().length();
	    if (!(lung==16)) {
	      errore.append(";2");
	    } else {
	      if (lung==16 && !Utili.controllaCodiceFiscale(getCodice_fiscale()))
	    	  errore.append(";2");
	    }

	    if (getNote_aggiuntive() !=null && getNote_aggiuntive().length()>4000) {
		      errore.append(";4");
	    }
	    
	    if (getImporto()!=null && !Utili.isNumber(getImporto())){
	    	errore.append(";3");
	    }
	    
	    if(!update && selectPreventivoCF(getCodice_fiscale(),getNumero_preventivo())>0) {
	    	errore.append(";1");
	    	errore.append(";2");
	    }
	    /**
	    * Se non sono stati trovati errori restituisce null
	    */
	    if (errore.toString().equals("")) 
	    	return null;
	    else 
	    	return errore.toString();
	  }

	public void setIdRichiesta(long idRichiesta){
		this.idRichiesta = idRichiesta;
	}
	
	public long getIdRichiesta() {
		return idRichiesta;
	}
	
	public String getNote_aggiuntive() {
		if(note_aggiuntive == null)
			return "";
		else
			return note_aggiuntive;
	}
	
	public void setNote_aggiuntive(String note_aggiuntive) {
		this.note_aggiuntive = note_aggiuntive;
	}
	
	public String getNumero_preventivo() {
		return numero_preventivo;
	}
	
	public void setNumero_preventivo(String numero_preventivo) {
		this.numero_preventivo = numero_preventivo;
	}
	
	public String getImporto() {
		return importo;
	}
	
	public void setImporto(String importo) {
		this.importo = importo;
	}
	
	public BigDecimal getImporto_BD() {
		if(this.importo==null || this.importo.equals("") || !Utili.isNumber(this.importo))
			return null;
		else
			return new BigDecimal(this.importo);
	}
	
	public String getCodice_fiscale() {
		return codice_fiscale;
	}
	
	public void setCodice_fiscale(String codice_fiscale) {
		this.codice_fiscale = codice_fiscale;
	}

	public ArrayList<Preventivi> getPreventivi() {
		return preventivi;
	}

	public void setPreventivi(ArrayList<Preventivi> preventivi) {
		this.preventivi = preventivi;
	}
	public BigDecimal getIdPreventivo() {
		if(idPreventivo==null)
			if(this.getIdPreventivoStr()!=null)
				idPreventivo = new BigDecimal(this.getIdPreventivoStr());
		return idPreventivo;
	}
	public void setIdPreventivo(BigDecimal idPreventivo) {
		this.idPreventivo = idPreventivo;
	}
	public String getIdPreventivoStr() {
		return idPreventivoStr;
	}
	public void setIdPreventivoStr(String idPreventivoStr) {
		this.idPreventivoStr = idPreventivoStr;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
}