package it.csi.agrc;
import it.csi.cuneo.*;
//import java.util.*;
//import java.sql.*;
//import it.csi.jsf.web.pool.*;
import java.io.*;

/**
 * Title:        Agrichim - Front Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class BeanAnalisi extends BeanDataSource implements Serializable
{
  private static final long serialVersionUID = -8059975067662335723L;
	
  private java.util.Hashtable analisi;
  private java.util.Hashtable costoAnalisi;
  private String importoSpedizione;

  public java.util.Hashtable getAnalisi()
  {
    return analisi;
  }
  public void setAnalisi(java.util.Hashtable analisi)
  {
    this.analisi = analisi;
  }

  public void setCostoAnalisi(java.util.Hashtable costoAnalisi) {
    this.costoAnalisi = costoAnalisi;
  }
  public java.util.Hashtable getCostoAnalisi() {
    return costoAnalisi;
  }
  public void setImportoSpedizione(String importoSpedizione)
  {
    this.importoSpedizione = importoSpedizione;
  }
  public String getImportoSpedizione()
  {
    return importoSpedizione;
  }
}