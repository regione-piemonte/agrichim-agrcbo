package it.csi.agrc;
import it.csi.cuneo.*;
import java.util.*;
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

public class BeanTipoCampione extends BeanDataSource implements Serializable
{
	private static final long serialVersionUID = -3204050307870341590L;

	private String codMateriale[];
    private String descMateriale[];

    private String codLaboratorio[];
    private String descLaboratorio[];

    private String codModalita[];
    private String descModalita[];

    private String codStatoCampione[];
    private String descStatoCampione[];

    private CodiciMisuraPsr codiciMisuraPsr;

    public void setCodMateriale(Vector codVector)
    {
      codMateriale = (String[]) codVector.toArray(new String[0]);
    }
    public void setDescMateriale(Vector descVector)
    {
      descMateriale = (String[]) descVector.toArray(new String[0]);
    }

    public String [] getCodMateriale()
    {
      return codMateriale;
    }
    public String [] getDescMateriale()
    {
      return descMateriale;
    }


    public void setCodLaboratorio(Vector codVector)
    {
      codLaboratorio = (String[]) codVector.toArray(new String[0]);
    }
    public void setDescLaboratorio(Vector descVector)
    {
      descLaboratorio = (String[]) descVector.toArray(new String[0]);
    }

    public String [] getCodLaboratorio()
    {
      return codLaboratorio;
    }
    public String [] getDescLaboratorio()
    {
      return descLaboratorio;
    }


    public void setCodModalita(Vector codVector)
    {
      codModalita = (String[]) codVector.toArray(new String[0]);
    }
    public void setDescModalita(Vector descVector)
    {
      descModalita = (String[]) descVector.toArray(new String[0]);
    }

    public String [] getCodModalita()
    {
      return codModalita;
    }
    public String [] getDescModalita()
    {
      return descModalita;
    }

    public void setCodStatoCampione(Vector codVector)
    {
      codStatoCampione = (String[]) codVector.toArray(new String[0]);
    }
    public void setDescStatoCampione(Vector descVector)
    {
      descStatoCampione = (String[]) descVector.toArray(new String[0]);
    }

    public String [] getCodStatoCampione()
    {
      return codStatoCampione;
    }
    public String [] getDescStatoCampione()
    {
      return descStatoCampione;
    }
		public CodiciMisuraPsr getCodiciMisuraPsr()
		{
			return codiciMisuraPsr;
		}
		public void setCodiciMisuraPsr(CodiciMisuraPsr codiciMisuraPsr)
		{
			this.codiciMisuraPsr = codiciMisuraPsr;
		}    
}