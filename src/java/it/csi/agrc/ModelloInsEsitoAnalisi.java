package it.csi.agrc;

import it.csi.cuneo.*;
import java.sql.*;
//import it.csi.jsf.web.pool.*;
//import java.util.*;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */


/**
 * Questa interfaccia viene usata da tutte le classi che permettono l'inserimento
 * delle analisi di un campione in laboratorio
 * */
public abstract class ModelloInsEsitoAnalisi extends BeanDataSource
{
  public abstract void insert()
      throws Exception, SQLException;

  public abstract void update()
      throws Exception, SQLException;

  public abstract boolean select()
      throws Exception, SQLException;

  public abstract void delete()
      throws Exception, SQLException;

  public abstract String ControllaDati();
}