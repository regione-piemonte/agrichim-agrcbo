<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>
<%@page import="java.util.Vector"%>
<%@page import="it.csi.solmr.dto.anag.AnagAziendaVO"%>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/datiAnagrafiche.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="organizzazioniTecnico"
     scope="page"
     class="it.csi.agrc.OrganizzazioniTecnico">
<%
  organizzazioniTecnico.setDataSource(dataSource);
  organizzazioniTecnico.setAut(aut);
%>
</jsp:useBean>


<jsp:useBean
     id="comuni"
     scope="page"
     class="it.csi.agrc.Comuni">
<%
  comuni.setDataSource(dataSource);
  comuni.setAut(aut);
%>
</jsp:useBean>

<jsp:useBean
     id="etichettaCampione"
     scope="page"
     class="it.csi.agrc.EtichettaCampione">
    <%
      etichettaCampione.setDataSource(dataSource);
      etichettaCampione.setAut(aut);
    %>
</jsp:useBean>

<jsp:useBean
     id="anagUte"
     scope="page"
     class="it.csi.agrc.Anagrafica">
    <%
      anagUte.setDataSource(dataSource);
      anagUte.setAut(aut);
    %>
</jsp:useBean>

<jsp:useBean
     id="anagTec"
     scope="page"
     class="it.csi.agrc.Anagrafica">
    <%
      anagTec.setDataSource(dataSource);
      anagTec.setAut(aut);
    %>
</jsp:useBean>

<jsp:useBean
     id="anagAzi"
     scope="request"
     class="it.csi.agrc.Anagrafica">
     <%
      anagAzi.setDataSource(dataSource);
      anagAzi.setAut(aut);
    %>
</jsp:useBean>

<!--
  Il file che includo serve a gestire gli errori dovuti al funzionamento non
  corretto del javascript
-->
<%@ include file="/jsp/view/problemiJavascript.inc" %>

<%
  /**
  * Se modifica non è valorizzato significa che sono arrivato a questa pagina
  * dal menu. Se modifica è uguale a si significa che ho appena modificato i
  * dati dell'etichetta. Se invece modifica è uguale a no significa che
  * ho tentato di modificare i dati ma qualcosa non è andato a buon fine
  * */
  String modifica=request.getParameter("modifica");
  if ("si".equals(modifica))
       templ.newBlock("modifica");
   String utenti[]= etichettaCampione.selectUtenti(aut.getIdRichiestaCorrente());
   String stato_pagamento = etichettaCampione.getPagamento();
   boolean isAnagUte=false,isAnagTec=false,isAnagAzi=false;
   String organiz=null;
   if (utenti[EtichettaCampione.UTENTE]!=null)
   {
     anagUte.getAnagrafica(utenti[EtichettaCampione.UTENTE]);
     isAnagUte=true;
     if ("T".equals(anagUte.getTipoUtente())) organiz=utenti[EtichettaCampione.UTENTE];
   }
   if (utenti[EtichettaCampione.TECNICO]!=null)
   {
     anagTec.getAnagrafica(utenti[EtichettaCampione.TECNICO]);
     isAnagTec=true;
     organiz=utenti[EtichettaCampione.TECNICO];
   }

   /**
   * Se sono in questa pagina perché c'è stato un errore non devo leggere i
   * dati che si trovano memorizzati dentro al database (riguardanti il
   * proprietario che è l0unico che posso midificare) ma devo far vedere
   * quelli che l'utente ha inserito, che si trovano nella request
   */
   if (request.getParameter("errore") == null)
   {
     if (utenti[EtichettaCampione.PROPRIETARIO]!=null)
     {
       anagAzi.getAnagrafica(utenti[EtichettaCampione.PROPRIETARIO]);
       isAnagAzi=true;
     }
  }
  else
  {
    isAnagAzi=true;
  }
  if (isAnagUte)
  {
    /**
    * Creo il blocco relativo all'anagrafica dell'utente e valorizzo i suoi dati
    */
    templ.newBlock("AnagraficaUtente");
    /**
    * Leggo i parametri che si tovano nel bean
    * */
    String codiceFiscale=anagUte.getCodiceIdentificativo();
    String cognome=anagUte.getCognomeRagioneSociale();
    String nome=anagUte.getNome();
    String eMail=anagUte.getEmail();
    String telefono=anagUte.getTelefono();
    String cellulare=anagUte.getCellulare();
    String fax=anagUte.getFax();
    String tipoUtente=anagUte.getTipoUtente();

    /**
     * Se qualche parametro è nullo devo impostarlo a vuoto altrimenti
     * htmpl si arrabbia
     * */
    if (codiceFiscale==null) codiceFiscale="";
    if (cognome==null) cognome="";
    if (nome==null) nome="";
    if (eMail==null) eMail="";
    if (telefono==null) telefono="";
    if (cellulare==null) cellulare="";
    if (fax==null) fax="";
    if (tipoUtente==null) tipoUtente="P";


    templ.set("AnagraficaUtente.codiceFiscale",codiceFiscale);
    templ.set("AnagraficaUtente.cognome",cognome);
    templ.set("AnagraficaUtente.nome",nome);
    templ.set("AnagraficaUtente.eMail",eMail);
    templ.set("AnagraficaUtente.telefono",telefono);
    templ.set("AnagraficaUtente.cellulare",cellulare);
    templ.set("AnagraficaUtente.fax",fax);
    if ("P".equals(tipoUtente))
    {
      templ.set("AnagraficaUtente.privato","checked");
      
      //AGRICHIM-28
      //Se la richiesta di analisi è stata presenta da un utente privato senza legami con alcuna azienda, il sistema deve visualizzare l'indicazione di presenza in anagrafe in base al cuaa del richiedente
      if (! isAnagAzi)
      {
  			//Visualizzazione se proprietario del campione è un'azienda agricola
  			boolean isPresenteAnagrafe = false;
			  if (codiceFiscale != null && ! "".equals(codiceFiscale))
			  {
			  	AnagAziendaVO anagAziendaVO = new AnagAziendaVO();
			  	anagAziendaVO.setCUAA(codiceFiscale);
			  	Vector elencoAziende = beanParametriApplication.getAnagServiceCSIInterface().serviceGetListIdAziende(anagAziendaVO, Boolean.TRUE, Boolean.FALSE);
			  	isPresenteAnagrafe = elencoAziende != null && elencoAziende.size() > 0;
			  	
			  //jira 136
			  	if(!isPresenteAnagrafe)
			  	{
			  		//se non è presente provo a cercarlo per partita IVA
					CuneoLogger.debug(this,"non l'ho trovato per CUAA lo cerca per P.iva");
			  		anagAziendaVO.setPartitaIVA(codiceFiscale);
			  		elencoAziende = beanParametriApplication.getAnagServiceCSIInterface().serviceGetListIdAziende(anagAziendaVO, Boolean.TRUE, Boolean.FALSE);
			  	  	isPresenteAnagrafe = elencoAziende != null && elencoAziende.size() > 0;
					CuneoLogger.debug(this,"dopo la ricerca per p.iva isPresenteAnagrafe " + isPresenteAnagrafe);
			  	  	if(!isPresenteAnagrafe)
			  	  	{
					   CuneoLogger.debug(this,"visto che l'utente collegato è un privato (il controllo è a monte) provo ancora a cercare utilizzando serviceGetAziendeAAEPAnagrafe");
					  //se non ho ancora trovato nulla utilizzo il servizio serviceGetAziendeAAEPAnagrafe
			  	  	  Long[] idAziende = beanParametriApplication.getAnagServiceCSIInterface().serviceGetAziendeAAEPAnagrafe(codiceFiscale, false, Boolean.FALSE, Boolean.TRUE, false, false).getIdAzienda();
			  	  	  isPresenteAnagrafe = idAziende != null && idAziende.length > 0;
					  CuneoLogger.debug(this,"dopo l'ulteriore chiamata isPresenteAnagrafe " + isPresenteAnagrafe);
					}
			  	}
			  	
			  }
			  templ.newBlock("AnagraficaUtente.blkPresenteAnagrafeRichiedentePrivato");
			  if (isPresenteAnagrafe)
			  {
			  	templ.set("AnagraficaUtente.blkPresenteAnagrafeRichiedentePrivato.checkedPresenteAnagrafe", "checked");
			  }
      }
    }
    if ("T".equals(tipoUtente))
      templ.set("AnagraficaUtente.tecnico","checked");
    if ("L".equals(tipoUtente))
      templ.set("AnagraficaUtente.lar","checked");
  }
  if (isAnagTec)
  {
    /**
    * Creo il blocco relativo all'anagrafica del tecnico e valorizzo i suoi dati
    */
    templ.newBlock("AnagraficaTecnico");
    /**
    * Leggo i parametri che si tovano nel bean
    * */
    String cognome=anagTec.getCognomeRagioneSociale();
    String nome=anagTec.getNome();
    String codiceFiscale=anagTec.getCodiceIdentificativo();
    String indirizzo=anagTec.getIndirizzo();
    String cap=anagTec.getCap();
    String istat=anagTec.getComuneResidenza();
    if (istat==null) istat="";
    String comune=comuni.getDescrizioneComune(istat);


    /**
     * Se qualche parametro è nullo devo impostarlo a vuoto altrimenti
     * htmpl si arrabbia
     * */
    if (cognome==null) cognome="";
    if (nome==null) nome="";
    if (codiceFiscale==null) codiceFiscale="";
    if (indirizzo==null) indirizzo="";
    if (cap==null) cap="";
    if (comune==null) comune="";



    templ.set("AnagraficaTecnico.codiceFiscale",codiceFiscale);
    templ.set("AnagraficaTecnico.cognome",cognome);
    templ.set("AnagraficaTecnico.nome",nome);
    templ.set("AnagraficaTecnico.indirizzo",indirizzo);
    templ.set("AnagraficaTecnico.cap",cap);
    templ.set("AnagraficaTecnico.comune",comune);
  }
  if (organiz!=null)
  {
    /**
    * Creo il blocco relativo all'anagrafica del proprietario e valorizzo i suoi dati
    */
    templ.newBlock("Organizzazione");
    String risul[]=organizzazioniTecnico.select(organiz);
    templ.set("Organizzazione.comune",risul[OrganizzazioniTecnico.ORG_COMUNE]);
    templ.set("Organizzazione.indirizzo",risul[OrganizzazioniTecnico.ORG_INDIRIZZO]);
    templ.set("Organizzazione.partitaIva",risul[OrganizzazioniTecnico.ORG_PARTITA_IVA]);
    templ.set("Organizzazione.ragioneSociale",risul[OrganizzazioniTecnico.ORG_RAGIONE_SOCIALE]);
    templ.set("Organizzazione.sede",risul[OrganizzazioniTecnico.ORG_SEDE_TERRITORIALE]);
  }
  if (isAnagAzi)
  {
	  System.out.println("");
    /**
    * Creo il blocco relativo all'anagrafica del proprietario e valorizzo i suoi dati
    */
    templ.newBlock("AnagraficaProprietario");
    /**
    * Leggo i parametri che si tovano nel bean
    * */
    String idAnagrafica=anagAzi.getIdAnagrafica();
    String codiceFiscale=anagAzi.getCodiceIdentificativo();
    String cognome=anagAzi.getCognomeRagioneSociale();
    String nome=anagAzi.getNome();
    String indirizzo=anagAzi.getIndirizzo();
    String cap=anagAzi.getCap();
    String istat=anagAzi.getComuneResidenza();
    if (istat==null) istat="";
    String comune=comuni.getDescrizioneComune(istat);
    String email=anagAzi.getEmail();
    String telefono=anagAzi.getTelefono();
    String fax=anagAzi.getFax();

    /**
     * Se qualche parametro è nullo devo impostarlo a vuoto altrimenti
     * htmpl si arrabbia
     * */
    if (idAnagrafica==null) idAnagrafica="";
    if (codiceFiscale==null) codiceFiscale="";
    if (cognome==null) cognome="";
    if (nome==null) nome="";
    if (indirizzo==null) indirizzo="";
    if (cap==null) cap="";
    if (comune==null) comune="";
    if (email==null) email="";
    if (telefono==null) telefono="";
    if (fax==null) fax="";


    templ.set("AnagraficaProprietario.idAnagrafica",idAnagrafica);
    templ.set("AnagraficaProprietario.codiceFiscale",codiceFiscale);
    templ.set("AnagraficaProprietario.cognome",cognome);
    templ.set("AnagraficaProprietario.nome",nome);
    templ.set("AnagraficaProprietario.ragioneSociale",cognome+" "+nome);
    templ.set("AnagraficaProprietario.indirizzo",indirizzo);
    templ.set("AnagraficaProprietario.cap",cap);
    templ.set("AnagraficaProprietario.comune",comune);
    templ.set("AnagraficaProprietario.email",email);
    templ.set("AnagraficaProprietario.telefono",telefono);
    templ.set("AnagraficaProprietario.fax",fax);
    if(stato_pagamento.equals("S")){
        templ.set("AnagraficaProprietario.disab","disabilitato");
        templ.set("AnagraficaProprietario.messaggio_disab","Modifica non possibile su richiesta pagata con IUV");
    }
    templ.bset("istat",istat);

		//AGRICHIM-28
	  //Visualizzazione se proprietario del campione è presente come azienda nell'Anagrafe Unica SIAP
	  boolean isPresenteAnagrafe = false;
	  if (codiceFiscale != null && ! "".equals(codiceFiscale))
	  {
	  	AnagAziendaVO anagAziendaVO = new AnagAziendaVO();
	  	anagAziendaVO.setCUAA(codiceFiscale);
	  	Vector elencoAziende = beanParametriApplication.getAnagServiceCSIInterface().serviceGetListIdAziende(anagAziendaVO, Boolean.TRUE, Boolean.FALSE);
	  	isPresenteAnagrafe = elencoAziende != null && elencoAziende.size() > 0;
	  //jira 136
	  	if(!isPresenteAnagrafe)
	  	{
	  		//se non è presente provo a cercarlo per partita IVA
			CuneoLogger.debug(this,"non l'ho trovato per CUAA lo cerca per P.iva");
	  		anagAziendaVO.setPartitaIVA(codiceFiscale);
	  		elencoAziende = beanParametriApplication.getAnagServiceCSIInterface().serviceGetListIdAziende(anagAziendaVO, Boolean.TRUE, Boolean.FALSE);
	  	  	isPresenteAnagrafe = elencoAziende != null && elencoAziende.size() > 0;
			CuneoLogger.debug(this,"dopo la ricerca per p.iva isPresenteAnagrafe " + isPresenteAnagrafe);
	  	  	if(!isPresenteAnagrafe && "P".equals(anagUte.getTipoUtente()))
	  	  	{
			   CuneoLogger.debug(this,"visto che l'utente collegato è un privato provo ancora a cercare utilizzando serviceGetAziendeAAEPAnagrafe");
			  //se non ho ancora trovato nulla utilizzo il servizio serviceGetAziendeAAEPAnagrafe
	  	  	  Long[] idAziende = beanParametriApplication.getAnagServiceCSIInterface().serviceGetAziendeAAEPAnagrafe(codiceFiscale, false, Boolean.FALSE, Boolean.TRUE, false, false).getIdAzienda();
	  	  	  isPresenteAnagrafe = idAziende != null && idAziende.length > 0;
			  CuneoLogger.debug(this,"dopo l'ulteriore chiamata isPresenteAnagrafe " + isPresenteAnagrafe);
			}
	  	}
	  }
	  if (isPresenteAnagrafe)
	  {
	  	//templ.set("AnagraficaProprietario.checkedPresenteAnagrafe", "checked");
	  	templ.set("AnagraficaProprietario.presenteAnagrafe", "azienda censita");
	  }
	  else
	  {
		 templ.set("AnagraficaProprietario.presenteAnagrafe", "azienda NON censita");
	  }
  }

  if (aut.isCoordinateGeografiche())
  {
    /**
    * Devo attivare il link relativo alle coordinate geografiche
    */
    templ.newBlock("SiGis");
  }
  else
  {
    /**
    * Devo inibire il link relativo alle coordinate geografiche
    */
    templ.newBlock("NoGis");
  }
  templ.bset("idRichiesta",aut.getIdRichiestaCorrente()+"");
  templ.bset("codiceMateriale",aut.getCodMateriale());
%>

<%= templ.text() %>
