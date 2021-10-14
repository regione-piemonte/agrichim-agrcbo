package it.csi.agrc;
import it.csi.cuneo.*;
import java.util.*;
import java.sql.*;
//import it.csi.jsf.web.pool.*;

/**
 * Title:        Agrichim - Front Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class TipoCampione extends BeanDataSource
{
  private String codMateriale;
  private String descMateriale;
  private long idRichiesta;
  private String etichettaCampione;
  private String codLaboratorio;
  private String descLaboratorio;
  private String codModalitaConsegna;
  private String descModalitaConsegna;
  private String note;
  private String giornoInserimentoRic;
  private String meseInserimentoRic;
  private String annoInserimentoRic;
  private String codiceMisuraPsr;
  private String noteMisuraPsr;
  private String iuv;
  private String tipoPagamento;
  private String statoPagamento;

  private static final String QUERY_MATERIALE=
   "SELECT CODICE_MATERIALE,DESCRIZIONE FROM MATERIALE ORDER BY DESCRIZIONE";
  private static final String QUERY_LABORATORIO=
   "SELECT CODICE_LABORATORIO,DESCRIZIONE FROM LABORATORIO WHERE DATA_FINE_VALIDITA IS NULL ORDER BY DESCRIZIONE";
  private static final String QUERY_MODALITA=
   "SELECT CODICE_MODALITA,DESCRIZIONE FROM MODALITA_DI_CONSEGNA ORDER BY DESCRIZIONE";
  private static final String QUERY_STATO=
   "SELECT CODICE,DESCRIZIONE FROM CODIFICA_TRACCIABILITA ORDER BY CODICE";

  public static final int  MATERIALE=1;
  public static final int  LABORATORIO=2;
  public static final int  MODALITA=3;
  public static final int  STATO=4;

  public TipoCampione()
  {
  }

  /**
   * Se scelta è uguale a:
   * - MATERIALE esegue la query che estrae tutti i materiali
   * - LABORATORIO esegue la query che estrae tutti i laboratori
   * - MODALITA esegue la query che estrae tutte le modalità di consegna
   * - STATO esegue la query che estrae tutti gli stati in cui può trovarsi
   *   un campione
   * Viene utilizzata per restituire i codici e le descrizioni dalle
   * tabelle di transcodifica
   **/
  public void selectCodDesc(int scelta,Vector cod,Vector desc)
    throws Exception, SQLException
  {
    cod.clear();
    desc.clear();
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    String query=null;
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      switch(scelta)
      {
        case MATERIALE:query=QUERY_MATERIALE;
                       break;
        case LABORATORIO:query=QUERY_LABORATORIO;
                         break;
        case MODALITA:query=QUERY_MODALITA;
                      break;
        case STATO:query=QUERY_STATO;
                      break;
      }
      ResultSet rset = stmt.executeQuery(query);
      String temp;
      while (rset.next())
      {
        temp=rset.getString(1);
        if (temp==null) temp="";
        cod.add(temp);

        temp=rset.getString(2);
        if (temp==null) temp="";
        desc.add(temp);
      }
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("selectCodDesc della classe TipoCampione");
      this.getAut().setContenutoQuery(query);
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("selectCodDesc della classe TipoCampione"
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


  public void select()
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
      query = new StringBuffer("SELECT TO_CHAR(EC.DATA_INSERIMENTO_RICHIESTA,'DD MM YYYY') ");
      query.append("AS DATA, EC.CODICE_MATERIALE AS COD_MATERIALE,");
      query.append("M.DESCRIZIONE AS DESC_MATERIALE,");
      query.append("EC.DESCRIZIONE_ETICHETTA,");
      query.append("L.CODICE_LABORATORIO AS COD_LABORATORIO,");
      query.append("L.DESCRIZIONE AS DESC_LABORATORIO,");
      query.append("MC.CODICE_MODALITA AS COD_MODALITA,");
      query.append("MC.DESCRIZIONE AS DESC_MODALITA,");
      query.append("EC.NOTE_CLIENTE, ");
      query.append("EC.CODICE_MISURA_PSR, EC.NOTE_MISURA_PSR,EC.PAGAMENTO, ");      
      query.append("(with elenco_iuv as (select distinct FIRST_VALUE(iuv) " + 
    			"    OVER( PARTITION BY ID_RICHIESTA ORDER BY data_annullamento desc) iuv,ID_RICHIESTA from PAGAMENTI) " + 
    			"select case when p.data_annullamento is null then p.iuv else '' end iuv " + 
    			"from pagamenti p " + 
    			"  JOIN elenco_iuv ei ON p.id_richiesta = ei.id_richiesta and p.iuv = ei.iuv " + 
    			"where p.id_richiesta = ec.id_richiesta), ");
      query.append("(with elenco_iuv as (select distinct FIRST_VALUE(iuv) " + 
    			"    OVER( PARTITION BY ID_RICHIESTA ORDER BY data_annullamento desc) iuv,ID_RICHIESTA from PAGAMENTI) " + 
    			"select tp.cod_tipo_pagamento " + 
    			"from pagamenti p " + 
    			"  JOIN elenco_iuv ei ON p.id_richiesta = ei.id_richiesta and p.iuv = ei.iuv " + 
    			"  JOIN tipo_pagamento tp on p.id_tipo_pagamento = tp.id_tipo_pagamento "+
    			"where p.id_richiesta = ec.id_richiesta) ");
      query.append("FROM ETICHETTA_CAMPIONE EC,MATERIALE M,");
      query.append("LABORATORIO L, MODALITA_DI_CONSEGNA MC ");
      query.append("WHERE EC.ID_RICHIESTA= ").append(this.getIdRichiesta());
      query.append(" AND EC.CODICE_MATERIALE=M.CODICE_MATERIALE ");
      query.append("AND EC.LABORATORIO_CONSEGNA=L.CODICE_LABORATORIO ");
      query.append("AND EC.CODICE_MODALITA = MC.CODICE_MODALITA ");
      //CuneoLogger.debug(this, "query.toString() "+query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        setDataInserimentoRic(rset.getString("DATA"));
        setCodMateriale(rset.getString("COD_MATERIALE"));
        setDescMateriale(rset.getString("DESC_MATERIALE"));
        setEtichettaCampione(rset.getString("DESCRIZIONE_ETICHETTA"));
        setCodLaboratorio(rset.getString("COD_LABORATORIO"));
        setDescLaboratorio(rset.getString("DESC_LABORATORIO"));
        setCodModalitaConsegna(rset.getString("COD_MODALITA"));
        setDescModalitaConsegna(rset.getString("DESC_MODALITA"));
        setNote(rset.getString("NOTE_CLIENTE"));
        setCodiceMisuraPsr(rset.getString("CODICE_MISURA_PSR"));
        setNoteMisuraPsr(rset.getString("NOTE_MISURA_PSR"));        
        setIuv(rset.getString("IUV"));
        setTipoPagamento(rset.getString("COD_TIPO_PAGAMENTO"));
        setStatoPagamento(rset.getString("PAGAMENTO"));
      }
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe TipoCampione");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe TipoCampione"
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


  public String getCodMateriale()
  {
    return codMateriale;
  }
  public void setCodMateriale(String codMateriale)
  {
    this.codMateriale = codMateriale;
  }
  public void setDescMateriale(String descMateriale)
  {
    this.descMateriale = descMateriale;
  }
  public String getDescMateriale()
  {
    return descMateriale;
  }
  public void setIdRichiesta(long idRichiesta)
  {
    this.idRichiesta = idRichiesta;
  }
  public long getIdRichiesta()
  {
    return idRichiesta;
  }
  public void setEtichettaCampione(String etichettaCampione)
  {
    this.etichettaCampione = etichettaCampione;
  }
  public String getEtichettaCampione()
  {
    return etichettaCampione;
  }
  public void setCodLaboratorio(String codLaboratorio)
  {
    this.codLaboratorio = codLaboratorio;
  }
  public String getCodLaboratorio()
  {
    return codLaboratorio;
  }
  public void setDescLaboratorio(String descLaboratorio)
  {
    this.descLaboratorio = descLaboratorio;
  }
  public String getDescLaboratorio()
  {
    return descLaboratorio;
  }
  public void setCodModalitaConsegna(String codModalitaConsegna)
  {
    this.codModalitaConsegna = codModalitaConsegna;
  }
  public String getCodModalitaConsegna()
  {
    return codModalitaConsegna;
  }
  public void setDescModalitaConsegna(String descModalitaConsegna)
  {
    this.descModalitaConsegna = descModalitaConsegna;
  }
  public String getDescModalitaConsegna()
  {
    return descModalitaConsegna;
  }
  public void setNote(String note)
  {
    this.note = note;
  }
  public String getNote()
  {
    return note;
  }

  public void setGiornoInserimentoRic(String giornoInserimentoRic)
  {
    this.giornoInserimentoRic = giornoInserimentoRic;
  }
  public String getGiornoInserimentoRic()
  {
    return giornoInserimentoRic;
  }
  public void setMeseInserimentoRic(String meseInserimentoRic)
  {
    this.meseInserimentoRic = meseInserimentoRic;
  }
  public String getMeseInserimentoRic()
  {
    return meseInserimentoRic;
  }
  public void setAnnoInserimentoRic(String annoInserimentoRic)
  {
    this.annoInserimentoRic = annoInserimentoRic;
  }
  public String getAnnoInserimentoRic()
  {
    return annoInserimentoRic;
  }
  public String getCodiceMisuraPsr()
	{
		return codiceMisuraPsr;
	}
	public void setCodiceMisuraPsr(String codiceMisuraPsr)
	{
		this.codiceMisuraPsr = codiceMisuraPsr;
	}
	public String getNoteMisuraPsr()
	{
		return noteMisuraPsr;
	}
	public void setNoteMisuraPsr(String noteMisuraPsr)
	{
		this.noteMisuraPsr = noteMisuraPsr;
	}
  public void setDataInserimentoRic(String dataInserimentoRic)
  {
    StringTokenizer st=new StringTokenizer(dataInserimentoRic);
    if (st.hasMoreElements())
      this.setGiornoInserimentoRic(st.nextToken());
    if (st.hasMoreElements())
      this.setMeseInserimentoRic(st.nextToken());
    if (st.hasMoreElements())
      this.setAnnoInserimentoRic(st.nextToken());
  }

  /**
   * Questa funzione controlla che i dati che verranno modificati nel
   * database siano consistenti
   * */
  public String ControllaDati()
  {
    StringBuffer errore=new StringBuffer("");


    if (this.getEtichettaCampione()!=null && this.getEtichettaCampione().length()>40)
    {
      errore.append(";1");
    }


    if (this.getNote()!=null && this.getNote().length()>512)
    {
      errore.append(";2");
    }


    /**
    * Se non sono stati trovati errori restituisce null
    */
    if (errore.toString().equals("")) return null;
    else return errore.toString();
  }

  /**
   * Modifica i dati del campione
   *
   * */
  public int update()
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
      query = new StringBuffer( "UPDATE ETICHETTA_CAMPIONE SET " );
      if (this.getEtichettaCampione()==null)
      {
        query.append("DESCRIZIONE_ETICHETTA = null,");
      }
      else
      {
        query.append("DESCRIZIONE_ETICHETTA = '");
        query.append(Utili.toVarchar(getEtichettaCampione())).append("',");
      }
      query.append("LABORATORIO_CONSEGNA = '").append(Utili.toVarchar(getCodLaboratorio()));
      query.append("',CODICE_MODALITA = '").append(Utili.toVarchar(getCodModalitaConsegna()));
      if (this.getNote()==null)
      {
        query.append("',NOTE_CLIENTE = null");
      }
      else
      {
        query.append("',NOTE_CLIENTE = '").append(Utili.toVarchar(getNote())).append("'");
      }
      query.append(", CODICE_MISURA_PSR = ").append(getCodiceMisuraPsr());
      if (this.getNoteMisuraPsr() == null)
      {
        query.append(", NOTE_MISURA_PSR = null");
      }
      else
      {
        query.append(",NOTE_MISURA_PSR = '").append(Utili.toVarchar(getNoteMisuraPsr())).append("'");
      }      
      query.append(" WHERE ID_RICHIESTA = ").append(this.getIdRichiesta());
      /**
      * Modifico i dati della tabella ETICHETTA_CAMPIONE
      *
      */
      int updated = stmt.executeUpdate( query.toString() );
      stmt.close();
      return updated;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("update della classe TipoCampione");
      this.getAut().setContenutoQuery(query.toString());
      try
      {
        conn.rollback();
      }
      catch( java.sql.SQLException ex2 )
      {
        this.getAut().setQuery("update della classe TipoCampione"
                               +":problemi con il rollback");
        this.getAut().setContenutoQuery(query.toString());
        throw (ex2);
      }
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("update della classe TipoCampione"
                             +": non è una SQLException ma una Exception"
                             +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      if (conn!=null)
      {
        conn.close();
      }
    }
  }

public String getIuv() {
	return iuv;
}

public void setIuv(String iuv) {
	this.iuv = iuv;
}

public String getTipoPagamento() {
	return tipoPagamento;
}

public void setTipoPagamento(String tipoPagamento) {
	this.tipoPagamento = tipoPagamento;
}

public String getStatoPagamento() {
	return statoPagamento;
}

public void setStatoPagamento(String statoPagamento) {
	this.statoPagamento = statoPagamento;
}

}