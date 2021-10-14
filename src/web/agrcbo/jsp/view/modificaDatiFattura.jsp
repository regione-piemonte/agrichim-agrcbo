<%@page import="java.util.HashMap"%>
<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/modificaDatiFattura.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/controllaUtente.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="beanTipoCampione"
     scope="application"
     class="it.csi.agrc.BeanTipoCampione"/>

<jsp:useBean
     id="etichettaCampioni"
     scope="page"
     class="it.csi.agrc.EtichettaCampioni">
    <%
      etichettaCampioni.setDataSource(dataSource);
      etichettaCampioni.setAut(aut);
    %>
</jsp:useBean>

<jsp:useBean
     id="datiFattura"
     scope="page"
     class="it.csi.agrc.DatiFattura">
    <%
      datiFattura.setDataSource(dataSource);
      datiFattura.setAut(aut);
    %>
</jsp:useBean>

<jsp:useBean
     id="storicoIva"
     scope="page"
     class="it.csi.agrc.StoricoIva">
    <%
    	storicoIva.setDataSource(dataSource);
    	storicoIva.setAut(aut);
    %>
</jsp:useBean>

<%
	String idRichieste = request.getParameter("idRichiestaSearch");
	templ.set("idRichiestaSearch", idRichieste);
	etichettaCampioni.selectRichiesteByIdRange(idRichieste);
  int size = etichettaCampioni.size();
  if (size > 0)
  {
   EtichettaCampione e;
   for (int i = 0; i < size; i++)
   {
     templ.newBlock("elencoCampioneBody");
     e = etichettaCampioni.get(i);
     templ.set("elencoCampioneBody.idRichiesta", e.getIdRichiesta());
     templ.set("elencoCampioneBody.numeroCampione", e.getNumeroCampione());
     templ.set("elencoCampioneBody.annoCampione", e.getAnnoCampione());
     templ.set("elencoCampioneBody.proprietario", e.getProprietario());
     templ.set("elencoCampioneBody.richiedente", e.getRichiedente());
     templ.set("elencoCampioneBody.note", e.getNote());
	}
 }

	//AGRICHIM-24
	//Modificare inoltre la visualizzazione delle opzioni di intestazione fattura: 
 	//- se anagrafica_tecnico di etichetta_campione non è valorizzata anche solo per una delle richieste selezionate, non visualizzare le opzioni "tecnico" e "organizzazione del tecnico" 
 	//- se anagrafica_proprietario di etichetta_campione non è valorizzata anche solo per una delle richieste selezionate, non visualizzare l'opzione "proprietario del campione" 
	boolean visualizzaIntestazioneTecnico = datiFattura.selectConteggioIntestatarioFatturaNonValorizzatoByIdRichiesteRange(idRichieste, "T") == 0;
	boolean visualizzaIntestazioneProprietario = datiFattura.selectConteggioIntestatarioFatturaNonValorizzatoByIdRichiesteRange(idRichieste, "P") == 0;
	if (visualizzaIntestazioneTecnico)
	{
		templ.newBlock("blkIntestazioneTecnico");
	}
	if (visualizzaIntestazioneProprietario)
	{
		templ.newBlock("blkIntestazioneProprietario");
	}
	
  //Leggo i dati della fattura
  String fatturareA = null;
  boolean isImpostaAltriEstremi = false;
  if (idRichieste.indexOf(",") == -1)
  {
  	//E' stata selezionata un'unica richiesta di cui modificare i dati fattura

	  datiFattura.select(idRichieste);

	  fatturareA = datiFattura.getFatturare();
		if ("A".equals(fatturareA))
	  {
	  	isImpostaAltriEstremi = true;
	  }

		templ.set("fatturareA", datiFattura.getFatturare());
	  templ.set("cfPartitaIva", datiFattura.getCfPartitaIva());
	  templ.set("ragioneSociale", datiFattura.getRagioneSociale());
	  templ.set("indirizzo", datiFattura.getIndirizzo());
	  templ.set("cap", datiFattura.getCap());
	  templ.set("comune", datiFattura.getComune());
	  templ.set("comuneDesc", datiFattura.getComuneDesc());
	  templ.set("siglaProvincia", datiFattura.getSiglaProvincia());
	  templ.set("pec", datiFattura.getPec());
	  templ.set("cod_destinatario", datiFattura.getCod_destinatario());
	}
	else
	{
		//Sono state selezionate più richieste di cui modificare i dati fattura

		HashMap<String, String> hmFatturare = datiFattura.selectIntestatarioFatturaByIdRichiesteRange(idRichieste);
		int sizeFatturare = hmFatturare != null ? hmFatturare.size() : 0;
		if (sizeFatturare == 1)
		{
			fatturareA = String.valueOf(hmFatturare.keySet().toArray()[0]);
		}
	}
	
	if (fatturareA != null)
	{
	  if ("T".equals(fatturareA) && visualizzaIntestazioneTecnico) 
	  {
			templ.set("blkIntestazioneTecnico.checkedFatturareTecnico", "CHECKED");
	  }
	  else if ("O".equals(fatturareA) && visualizzaIntestazioneTecnico) 
	  {  
	  	templ.set("blkIntestazioneTecnico.checkedFatturareOrganizzazioneTecnico", "CHECKED");
	  }
	  else if ("P".equals(fatturareA) && visualizzaIntestazioneProprietario)
	  {
			templ.set("blkIntestazioneProprietario.checkedFatturareProprietario", "CHECKED");
	  }
	  else if ("U".equals(fatturareA))
	  {
	  	templ.set("checkedFatturarePrivato", "CHECKED");
	  }
	  else if ("A".equals(fatturareA))
	  {
	  	templ.set("checkedFatturareAltriEstremi", "CHECKED");
	  }	  
	}
	
	if (isImpostaAltriEstremi)
	{
		//Nel caso in cui l'utente abbia selezionato un'unica richiesta di cui modificare la fattura e sia da fatturare ad Altri estremi
		//vengono valorizzati i dati relativi ad altri estremi

	  templ.set("cfPartitaIva", datiFattura.getCfPartitaIva());
	  templ.set("ragioneSociale", datiFattura.getRagioneSociale());
	  templ.set("indirizzo", datiFattura.getIndirizzo());
	  templ.set("cap", datiFattura.getCap());
	  templ.set("comuneDesc", datiFattura.getComuneDesc());
	  templ.set("pec", datiFattura.getPec());
	  templ.set("cod_destinatario", datiFattura.getCod_destinatario());
	}
%>

<%= templ.text() %>