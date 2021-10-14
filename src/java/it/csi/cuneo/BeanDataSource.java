package it.csi.cuneo;
//import javax.sql.DataSource;
import it.csi.agrc.Autenticazione;
import it.csi.jsf.web.pool.BeanDAO;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

/**
 * Title:        Agrichim - Back Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class BeanDataSource extends BeanDAO
{
  private Autenticazione aut;
  private javax.sql.DataSource dataSource;
  
  private final int PASSO = 500;

  public BeanDataSource()
  {
  }

  public Autenticazione getAut()
  {
    return aut;
  }

  public void setAut(Autenticazione aut)
  {
    this.aut = aut;
  }

  private void setLog(String fileName)
  {
    try
    {
        DriverManager.setLogWriter(
            new PrintWriter(new FileOutputStream(fileName)));
    }
    catch (FileNotFoundException e)
    {
        System.err.println(e.getMessage());
    }
  }

  public void setDataSource(Object obj)
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
      //Sono in ambiente TomCat quindi restuituisco una connessione fornita
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
  }

  protected InputStream getStream(String resource)
  {
    return getClass().getClassLoader().getResourceAsStream(resource);
  }

  /**
   * Controlla la Date e la converte in Timestamp
   *
   * @param val
   * @return
   */
  protected java.sql.Timestamp convertDateToTimestamp(java.util.Date val)
  {
    if (val == null) return null;
    return new Timestamp(val.getTime());
  }

  /**
   * Restituizione di una stringa dal vettore di Id
   * Al momento il vector puo' essere di Long, BigDecimal o String
   * @param vId
   * @param idx
   * @return
   */
    protected String getIdFromvId(Vector vId, int idx)
    {
      Object o = vId.get(idx);

      if (o instanceof String)
      {
        return "'" + (String) o + "'";
      }
      else if (o instanceof Long)
      {
        return ((Long) o).toString();
      }
      else if (o instanceof BigDecimal)
      {
        return ((BigDecimal) o).toString();
      }
      else
      {
        return o.toString();
      }
    }
 
	  public String getInCondition(String campo,Vector vId)
	  {
	    int cicli = vId.size() / PASSO;
	    if (vId.size() % PASSO != 0)
    	{
	    	cicli++;
    	}
	
	    StringBuffer condition = new StringBuffer(" AND ( ");
	    for (int j = 0; j < cicli; j++)
	    {
	      if (j != 0)
	      {
	         condition.append(" OR ");
	      }
				boolean primo = true;
				for (int i = j * PASSO; i < ((j + 1) * PASSO) && i < vId.size(); i++)
				{
					if (primo) 
					{
						condition.append(" " + campo + " IN (" + getIdFromvId(vId, i));
						primo = false;
					}
					else
					{
						condition.append("," + getIdFromvId(vId, i));
					}
				}
				condition.append(")");
	    }
	    condition.append(")");
	
	    return condition.toString();
	  }
}