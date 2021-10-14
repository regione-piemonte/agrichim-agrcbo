<%@page import="java.util.Vector"%>
<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/identificazioneCampione.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="beanTipoCampione"
     scope="application"
     class="it.csi.agrc.BeanTipoCampione"/>

<jsp:useBean
     id="tipoCampione"
     scope="request"
     class="it.csi.agrc.TipoCampione">
    <%
      tipoCampione.setDataSource(dataSource);
      tipoCampione.setAut(aut);
    %>
</jsp:useBean>
<!-- Il file che includo serve a gestire gli errori dovuti al funzionamento non corretto del javascript -->
<%@ include file="/jsp/view/problemiJavascript.inc" %>
<%
	/**
	* Se modifica non è valorizzato significa che sono arrivato a questa pagina
	* dal menu. Se modifica è uguale a si significa che ho appena modificato i
	* dati dell'etichetta. Se invece modifica è uguale a no significa che
	* ho tentato di modificare i dati ma qualcosa non è andato a buon fine
	* */
	String modifica=request.getParameter("modifica");
	//se provengo da verificaPagamento.jsp allora avrò probabilmente qualche messaggio
	String messaggi=request.getParameter("messaggi");
	if(request.getParameter("messaggi")!=null && !"".equals(request.getParameter("messaggi"))){
	     templ.newBlock("avvisoControlli");
	     templ.set("avvisoControlli.alertAvvisoControlli",request.getParameter("messaggi"));
	}
	/* Controllo se sono in questa pagina perché provengo
	* da analisiRichieste. Se si vuol dire che devo leggere il parametro il
	* numero dell'etichetta su cui si vuole lavorare e poi andare a leggere i
	* dati corrispondenti sul database */
	long idRichiesta=-1;
	if (modifica==null){
		try {
		  idRichiesta=Integer.parseInt(request.getParameter("idRichiestaSearch"));
		}catch(Exception e){
		  idRichiesta=-1;
		}
		if (idRichiesta!=-1){
			/**
			* Imposto il numero della richiesta ed il materiale usato nella sessione
			* per poterlo utilizzare all'interno delle altre voci inerenti la modifica
			* del campione. Inoltre imposta la variabile coordinateGeografiche
			*/
			tipoCampione.setIdRichiesta(idRichiesta);
			tipoCampione.select();
			aut.setCodMateriale(tipoCampione.getCodMateriale());
			if (Analisi.MAT_TERRENO.equals(tipoCampione.getCodMateriale()) ||
			  Analisi.MAT_FOGLIE.equals(tipoCampione.getCodMateriale())  ||
			  Analisi.MAT_FRUTTA.equals(tipoCampione.getCodMateriale())
			  ){
			    aut.setCoordinateGeografiche(true);
			} else 
			    aut.setCoordinateGeografiche(false);
			aut.setIdRichiestaCorrente(idRichiesta);
			session.setAttribute("aut",aut);
		}else{
			idRichiesta=aut.getIdRichiestaCorrente();
			tipoCampione.setIdRichiesta(idRichiesta);
			tipoCampione.select();
		}
	}else{
	    if ("si".equals(modifica))
	        templ.newBlock("modifica");
	}
	// Leggo i parametri che si tovano nel bean
	idRichiesta= tipoCampione.getIdRichiesta();
	String giorno = tipoCampione.getGiornoInserimentoRic();
	String mese = tipoCampione.getMeseInserimentoRic();
	String anno = tipoCampione.getAnnoInserimentoRic();
	String descMateriale=tipoCampione.getDescMateriale();
	String etichettaCampione=tipoCampione.getEtichettaCampione();
	String codModalitaConsegna=tipoCampione.getCodModalitaConsegna();
	String codLaboratorio=tipoCampione.getCodLaboratorio();
	String note=tipoCampione.getNote();
	String codiceMisuraPsr = tipoCampione.getCodiceMisuraPsr();
	String noteMisuraPsr = tipoCampione.getNoteMisuraPsr();
	String iuv = tipoCampione.getIuv();
	String tipoPagamento = tipoCampione.getTipoPagamento();
	String statoPagamento = tipoCampione.getStatoPagamento();
	
	// Se qualche parametro è nullo devo impostarlo a vuoto altrimenti htmpl si arrabbia
	if (giorno==null) giorno="";
	if (mese==null) mese="";
	if (anno==null) anno="";
	if (descMateriale==null) descMateriale="";
	if (etichettaCampione==null) etichettaCampione="";
	if (codLaboratorio==null) codLaboratorio="";
	if (codModalitaConsegna==null) codModalitaConsegna="";
	if (note==null) note="";
	if (codiceMisuraPsr == null) codiceMisuraPsr = "";
	if (noteMisuraPsr == null) noteMisuraPsr = "";
	if (iuv == null) iuv = "";
	if (tipoPagamento == null) tipoPagamento = "";
	if (statoPagamento == null) statoPagamento = "";
	
	templ.bset("idRichiesta",idRichiesta+"");
	templ.bset("giorno",giorno);
	templ.bset("mese",mese);
	templ.bset("anno",anno);
	templ.bset("codiceMateriale",aut.getCodMateriale());
	templ.bset("descMateriale",descMateriale);
	templ.bset("etichettaCampione",etichettaCampione);
	templ.bset("note",note);
	templ.set("noteMisuraPsr", noteMisuraPsr);
	templ.bset("iuv", iuv);
	templ.bset("tipoPagamento", tipoPagamento);
	templ.bset("statoPagamento", statoPagamento);
	if(statoPagamento.equals("S")){
		templ.bset("disab", "disabilitato");
	}
	
	//Carico i dati di tutti i laboratori per visualizzarli nella combo
	String codStr[],descStr[];
	codStr=beanTipoCampione.getCodLaboratorio();
	descStr=beanTipoCampione.getDescLaboratorio();
	for(int i=0;i<codStr.length;i++){
		if (codLaboratorio.equals(codStr[i]))
			  templ.set("laboratorio.selected","selected");
		else
			  templ.set("laboratorio.selected","");
		templ.set("laboratorio.codice",codStr[i]);
		templ.set("laboratorio.descrizione",descStr[i]);
	}
	
	//Carico i dati di tutte le modalità di consegna per visualizzarle nella combo
	codStr=beanTipoCampione.getCodModalita();
	descStr=beanTipoCampione.getDescModalita();
	for(int i=0;i<codStr.length;i++){
	    if (codModalitaConsegna.equals(codStr[i]))
	        templ.set("modalita.selected","selected");
	    else
	        templ.set("modalita.selected","");
	    templ.set("modalita.codice",codStr[i]);
	    templ.set("modalita.descrizione",descStr[i]);
	}
	
	CodiciMisuraPsr codiciMisuraPsr = beanTipoCampione.getCodiciMisuraPsr();
	int size = codiciMisuraPsr.size();
	for (int i = 0; i < size; i++){
		CodiceMisuraPsr cod = codiciMisuraPsr.get(i);
		templ.newBlock("codiceMisuraPsr");
		templ.set("codiceMisuraPsr.codice", cod.getCodiceMisuraPsr());
		templ.set("codiceMisuraPsr.descrizione", cod.getDescrizioneMisuraPsr());
		templ.set("codiceMisuraPsr.motivoObbligatorio", cod.getMotivoObbligatorio());
		if (cod.getCodiceMisuraPsr().equals(codiceMisuraPsr))
			templ.set("codiceMisuraPsr.selected", "selected");
	}
	if (aut.isCoordinateGeografiche()) //Devo attivare il link relativo alle coordinate geografiche
		templ.newBlock("SiGis");
	else                               // Devo inibire il link relativo alle coordinate geografiche
		templ.newBlock("NoGis");
%>
<%= templ.text() %>