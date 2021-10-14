package it.csi.agrc;

import it.csi.cuneo.CuneoLogger;
import it.csi.solmr.interfaceCSI.anag.services.AnagServiceCSIInterface;

import java.util.StringTokenizer;

/**
 * Title:        Agrichim - Back Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

 /**
 * Questa classe viene utilizzata per memorizzare (tramite le proprietà
 * dei bean) i seguenti parametri:
 * - passPhrase
 * - mailMittente
 * - mailDestinatario []
 * - hostServerPosta
 * - nomeServerWebApplication
 * - maxRecordXPagina
 * - maxRecordXPaginaCampLaboratorio
 * - urlStartApplication
 * - mailMittenteAgrichim
 */

public class BeanParametri implements java.io.Serializable
{
  private static final long serialVersionUID = 5004451546462107476L;

  private String mailMittente;
  private String mailDestinatario[];
  private String hostServerPosta;
  private String nomeServerWebApplication;
  private int maxRecordXPagina;
  private int maxRecordXPaginaCampLaboratorio;
  private String urlStartApplication;
  private String passPhrase;
  private String urlNuovaAnalisi;
  private String mailMittenteAgrichim;
  private String irideLogoutURL;
  private String irideLogoutData;
  private String VBCC;
  private String BOBA;
  private String assessorato;
  private String direzione;
  private String settore;
  private String labAgr;
  private String labConsegna2;
  private String TXT2;
  private String TXT5;
  private String TXT6;
  private String HBTO;
  private String HBAL;
  private String HBCE;
  
  private String HBCA;
  
  private String partitaIVALab;
  private String GFEP;
  private String IVA0;
  private String IVA1;
  private String NUMR;
  private String NUMC;
  private String SIPW;
  private String SIUS;
  private String metalliPesantiScontoNumero;
  private String metalliPesantiScontoPercentuale;

  private AnagServiceCSIInterface anagServiceCSIInterface;
  //private SigwgssrvSrv sigwgssrvSrv;
  
  
  private String tokenApiManagerEndpoint;
  private String tokenApiManagerKey;
  private String tokenApiManagerSecret;
  private String tokenApiManagerXAuth;
  private String sigwgssrvSigwgssrvEndpoint;
  private String sigwgssrvSigwgssrvWsdl;
  //siapPagoPA
  private String agripagopaWSDL;
  
  public BeanParametri() {  }

  public void setMailMittente(String newMailMittente)
  {
    mailMittente = newMailMittente;
    CuneoLogger.debug(this, "BO mailMittente "+mailMittente);
  }

  public String getMailMittente()
  {
    return mailMittente;
  }

  public String[] getMailDestinatario()
  {
    return mailDestinatario;
  }

  public void setMailDestinatario1(String mailDestinatari)
  {
    CuneoLogger.debug(this, "BO mailDestinatari "+mailDestinatari);
    StringTokenizer sToken = new StringTokenizer(mailDestinatari,";");
    int numToken=sToken.countTokens();
    mailDestinatario = new String[numToken];
    for (int i=0;i< mailDestinatario.length; i++ )
    {
       mailDestinatario[i]=sToken.nextToken();
       CuneoLogger.debug(this, "BO mailDestinatario["+i+"] "+mailDestinatario[i]);
    }
  }

  public void setHostServerPosta(String newHostServerPosta)
  {
    hostServerPosta = newHostServerPosta;
    CuneoLogger.debug(this, "BO hostServerPosta "+hostServerPosta);
  }

  public String getHostServerPosta()
  {
    return hostServerPosta;
  }

  public void setNomeServerWebApplication(String newNomeServerWebApplication)
  {
    nomeServerWebApplication = newNomeServerWebApplication;
    CuneoLogger.debug(this, "BO nomeServerWebApplication "+nomeServerWebApplication);
  }

  public String getNomeServerWebApplication()
  {
    return nomeServerWebApplication;
  }

  public void setMaxRecordXPagina(String newMaxRecordXPagina)
  {
    try
    {
      maxRecordXPagina = Integer.parseInt(newMaxRecordXPagina);
      CuneoLogger.debug(this, "BO maxRecordXPagina "+maxRecordXPagina);
    }
    catch(Exception e)
    {
      maxRecordXPagina=15;
      CuneoLogger.debug(this, "BO maxRecordXPagina Eccezione: 15");
    }
  }

  public void setMaxRecordXPaginaCampLaboratorio(String newMaxRecordXPaginaCampLaboratorio)
  {
    try
    {
      maxRecordXPaginaCampLaboratorio = Integer.parseInt(newMaxRecordXPaginaCampLaboratorio);
      CuneoLogger.debug(this, "BO maxRecordXPaginaCampLaboratorio "+maxRecordXPaginaCampLaboratorio);
    }
    catch(Exception e)
    {
      maxRecordXPaginaCampLaboratorio=50;
      CuneoLogger.debug(this, "BO maxRecordXPaginaCampLaboratorio Eccezione: 50");
    }
  }

  public int getMaxRecordXPaginaCampLaboratorio()
  {
    return maxRecordXPaginaCampLaboratorio;
  }

  public int getMaxRecordXPagina()
  {
    return maxRecordXPagina;
  }

  public void setUrlStartApplication(String newUrlStartApplication)
  {
    urlStartApplication = newUrlStartApplication;
    CuneoLogger.debug(this, "BO urlStartApplication "+urlStartApplication);
  }

  public String getUrlStartApplication()
  {
    return urlStartApplication;
  }
  public void setPassPhrase(String passPhrase)
  {
    this.passPhrase = passPhrase;
    CuneoLogger.debug(this, "BO passPhrase "+passPhrase);
  }
  public String getPassPhrase()
  {
    return passPhrase;
  }
  public void setUrlNuovaAnalisi(String urlNuovaAnalisi)
  {
    this.urlNuovaAnalisi = urlNuovaAnalisi;
    CuneoLogger.debug(this, "BO urlNuovaAnalisi "+urlNuovaAnalisi);
  }
  public String getUrlNuovaAnalisi()
  {
    return urlNuovaAnalisi;
  }
  public void setMailMittenteAgrichim(String mailMittenteAgrichim)
  {
    this.mailMittenteAgrichim = mailMittenteAgrichim;
    CuneoLogger.debug(this, "BO mailMittenteAgrichim "+mailMittenteAgrichim);
  }
  public String getMailMittenteAgrichim()
  {
    return mailMittenteAgrichim;
  }
  public void setIrideLogoutURL(String irideLogoutURL)
  {
    this.irideLogoutURL = irideLogoutURL;
    CuneoLogger.debug(this, "BO irideLogoutURL "+irideLogoutURL);
  }
  public String getIrideLogoutURL()
  {
    return irideLogoutURL;
  }
  public void setIrideLogoutData(String irideLogoutData)
  {
    this.irideLogoutData = irideLogoutData;
    CuneoLogger.debug(this, "BO irideLogoutData "+irideLogoutData);
  }
  public String getIrideLogoutData()
  {
    return irideLogoutData;
  }

  public String getVBCC()
  {
    return VBCC;
  }
  public void setVBCC(String VBCC)
  {
    this.VBCC = VBCC;
  }
  public String getBOBA()
  {
    return BOBA;
  }
  public void setBOBA(String BOBA)
  {
    this.BOBA = BOBA;
  }
  public String getAssessorato()
  {
    return assessorato;
  }
  public void setAssessorato(String assessorato)
  {
    this.assessorato = assessorato;
  }
  public String getDirezione()
  {
    return direzione;
  }
  public void setDirezione(String direzione)
  {
    this.direzione = direzione;
  }
  public String getSettore()
  {
    return settore;
  }
  public void setSettore(String settore)
  {
    this.settore = settore;
  }
  public String getLabAgr()
  {
    return labAgr;
  }
  public void setLabAgr(String labAgr)
  {
    this.labAgr = labAgr;
  }
  public String getLabConsegna2()
  {
    return labConsegna2;
  }
  public void setLabConsegna2(String labConsegna2)
  {
    this.labConsegna2 = labConsegna2;
  }
  public String getTXT2()
  {
    return TXT2;
  }
  public void setTXT2(String TXT2)
  {
    this.TXT2 = TXT2;
  }
  public String getTXT5()
  {
    return TXT5;
  }
  public void setTXT5(String TXT5)
  {
    this.TXT5 = TXT5;
  }
  public String getTXT6()
  {
    return TXT6;
  }
  public void setTXT6(String TXT6)
  {
    this.TXT6 = TXT6;
  }
  public String getHBTO()
  {
    return HBTO;
  }
  public void setHBTO(String HBTO)
  {
    this.HBTO = HBTO;
  }
  public String getHBAL()
  {
    return HBAL;
  }
  public void setHBAL(String HBAL)
  {
    this.HBAL = HBAL;
  }
  public String getHBCE()
  {
    return HBCE;
  }
  public void setHBCE(String HBCE)
  {
    this.HBCE = HBCE;
  }


/**
 * @return the hBCA
 */
public final String getHBCA() {
	return HBCA;
}

/**
 * @param hBCA the hBCA to set
 */
public final void setHBCA(String hBCA) {
	HBCA = hBCA;
}

public String getPartitaIVALab()
  {
    return partitaIVALab;
  }
  public void setPartitaIVALab(String partitaIVALab)
  {
    this.partitaIVALab = partitaIVALab;
  }
  public String getGFEP()
  {
    return GFEP;
  }
  public void setGFEP(String GFEP)
  {
    this.GFEP = GFEP;
  }
	public String getIVA0()
	{
		return IVA0;
	}
	public void setIVA0(String iVA0)
	{
		IVA0 = iVA0;
	}
	public String getIVA1()
	{
		return IVA1;
	}
	public void setIVA1(String iVA1)
	{
		IVA1 = iVA1;
	}
	public String getNUMR()
	{
		return NUMR;
	}

	public void setNUMR(String nUMR)
	{
		NUMR = nUMR;
	}
	public String getNUMC() {
		return NUMC;
	}

	public void setNUMC(String nUMC) {
		NUMC = nUMC;
	}

	public String getMetalliPesantiScontoNumero()
	{
		return metalliPesantiScontoNumero;
	}

	public void setMetalliPesantiScontoNumero(String metalliPesantiScontoNumero)
	{
		this.metalliPesantiScontoNumero = metalliPesantiScontoNumero;
	}

	public String getMetalliPesantiScontoPercentuale()
	{
		return metalliPesantiScontoPercentuale;
	}

	public void setMetalliPesantiScontoPercentuale(String metalliPesantiScontoPercentuale)
	{
		this.metalliPesantiScontoPercentuale = metalliPesantiScontoPercentuale;
	}	
	public AnagServiceCSIInterface getAnagServiceCSIInterface()
	{
		return anagServiceCSIInterface;
	}
	public void setAnagServiceCSIInterface(AnagServiceCSIInterface anagServiceCSIInterface)
	{
		this.anagServiceCSIInterface = anagServiceCSIInterface;
	}
	public String getSIPW() {
		return SIPW;
	}

	public void setSIPW(String sIPW) {
		SIPW = sIPW;
	}

	public String getSIUS() {
		return SIUS;
	}

	public void setSIUS(String sIUS) {
		SIUS = sIUS;
	}

	/*public SigwgssrvSrv getSigwgssrvSrv() {
		return sigwgssrvSrv;
	}

	public void setSigwgssrvSrv(SigwgssrvSrv sigwgssrvSrv) {
		this.sigwgssrvSrv = sigwgssrvSrv;
	}*/
	
	

  public String getTokenApiManagerEndpoint()
  {
    return tokenApiManagerEndpoint;
  }

  public void setTokenApiManagerEndpoint(String tokenApiManagerEndpoint)
  {
    this.tokenApiManagerEndpoint = tokenApiManagerEndpoint;
  }

  public String getTokenApiManagerKey()
  {
    return tokenApiManagerKey;
  }

  public void setTokenApiManagerKey(String tokenApiManagerKey)
  {
    this.tokenApiManagerKey = tokenApiManagerKey;
  }

  public String getTokenApiManagerSecret()
  {
    return tokenApiManagerSecret;
  }

  public void setTokenApiManagerSecret(String tokenApiManagerSecret)
  {
    this.tokenApiManagerSecret = tokenApiManagerSecret;
  }

  public String getTokenApiManagerXAuth()
  {
    return tokenApiManagerXAuth;
  }

  public void setTokenApiManagerXAuth(String tokenApiManagerXAuth)
  {
    this.tokenApiManagerXAuth = tokenApiManagerXAuth;
  }

  public String getSigwgssrvSigwgssrvEndpoint()
  {
    return sigwgssrvSigwgssrvEndpoint;
  }

  public void setSigwgssrvSigwgssrvEndpoint(String sigwgssrvSigwgssrvEndpoint)
  {
    this.sigwgssrvSigwgssrvEndpoint = sigwgssrvSigwgssrvEndpoint;
  }

  public String getSigwgssrvSigwgssrvWsdl()
  {
    return sigwgssrvSigwgssrvWsdl;
  }

  public void setSigwgssrvSigwgssrvWsdl(String sigwgssrvSigwgssrvWsdl)
  {
    this.sigwgssrvSigwgssrvWsdl = sigwgssrvSigwgssrvWsdl;
  }

public void logParametri()
  {
    CuneoLogger.debug(this, "BOBA: "+getBOBA());
    CuneoLogger.debug(this, "VBCC: "+getVBCC());
    CuneoLogger.debug(this, "TXAS: "+getAssessorato());
    CuneoLogger.debug(this, "TXDI: "+getDirezione());
    CuneoLogger.debug(this, "TXLA: "+getLabAgr());
    CuneoLogger.debug(this, "TXSF: "+getSettore());
    CuneoLogger.debug(this, "TXLC: "+getLabConsegna2());
    CuneoLogger.debug(this, "TXT2: "+getTXT2());
    CuneoLogger.debug(this, "TXT5: "+getTXT5());
    CuneoLogger.debug(this, "TXT6: "+getTXT6());
    CuneoLogger.debug(this, "HBTO: "+getHBTO());
    CuneoLogger.debug(this, "HBAL: "+getHBAL());
    CuneoLogger.debug(this, "HBCE: "+getHBCE());
    CuneoLogger.debug(this, "TXPI: "+getPartitaIVALab());
    CuneoLogger.debug(this, "GFEP: "+getGFEP());
    CuneoLogger.debug(this, "IVA1: "+getIVA1());
    CuneoLogger.debug(this, "NUMR: "+getNUMR());
    CuneoLogger.debug(this, "NUMC: "+getNUMC());
    CuneoLogger.debug(this, "HBCA: "+getHBCA());
  }

public String getAgripagopaWSDL() {
	return agripagopaWSDL;
}

public void setAgripagopaWSDL(String agripagopaWSDL) {
	this.agripagopaWSDL = agripagopaWSDL;
    CuneoLogger.debug(this, "BO agripagopaWSDL "+agripagopaWSDL);
}
}