<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/coordinatePrelievo.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="datiAppezzamento"
     scope="request"
     class="it.csi.agrc.DatiAppezzamento">
    <%
      datiAppezzamento.setDataSource(dataSource);
      datiAppezzamento.setAut(aut);
    %>
</jsp:useBean>

<%--
  Il file che includo serve a gestire gli errori dovuti al funzionamento non
  corretto del javascript
--%>
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

  /**
  * Se sono in questa pagina perché c'è stato un errore non devo leggere i
  * dati che si trovano memorizzati dentro al database ma devo far vedere
  * quelli che l'utente ha inserito, che si trovano nella request
  */
  if (request.getParameter("errore") == null && request.getParameter("erroreCongruenzaSigmater")==null && request.getParameter("erroreCongruenzaSigmaterCatastali")==null && request.getParameter("parametro")==null)
	    datiAppezzamento.select(aut.getIdRichiestaCorrente());

  
  String istatComune=datiAppezzamento.getIstatComune();
  String comuneAppezzamento=datiAppezzamento.getComuneAppezzamento();
  String sigla = datiAppezzamento.getSiglaProvincia();
	
	System.out.println("istatComune " +istatComune );
	System.out.println("comuneAppezzamento " +comuneAppezzamento );
	System.out.println("sigla " +sigla );
  
  String tipoGeoreferenziazione=datiAppezzamento.getTipoGeoreferenziazione();
  String localitaAppezzamento=datiAppezzamento.getLocalitaAppezzamento();
  String sezione=datiAppezzamento.getSezione();
  String foglio=datiAppezzamento.getFoglio();
  String particellaCatastale=datiAppezzamento.getParticellaCatastale();
  String subparticella=datiAppezzamento.getSubparticella();
  String coordinataNordBoaga=datiAppezzamento.getCoordinataNordBoaga();
  String coordinataEstBoaga=datiAppezzamento.getCoordinataEstBoaga();
  String coordinataNordUtm=datiAppezzamento.getCoordinataNordUtm();
  String coordinataEstUtm=datiAppezzamento.getCoordinataEstUtm();
  String piemonte=datiAppezzamento.getPiemonte();
  String stato_pagamento=datiAppezzamento.getPagamento();
  
  String coordGradi=datiAppezzamento.getCoordGradi();
  String gradiNord=datiAppezzamento.getGradiNord();
  String minutiNord=datiAppezzamento.getMinutiNord();
  String secondiNord=datiAppezzamento.getSecondiNord();
  String decimaliNord=datiAppezzamento.getDecimaliNord();
  String gradiEst=datiAppezzamento.getGradiEst();
  String minutiEst=datiAppezzamento.getMinutiEst();
  String secondiEst=datiAppezzamento.getSecondiEst();
  String decimaliEst=datiAppezzamento.getDecimaliEst();
  

  //if (comuneAppezzamentoDescr==null) comuneAppezzamentoDescr="";
  if (istatComune==null) istatComune="";
  if (tipoGeoreferenziazione==null) tipoGeoreferenziazione="";
  if (localitaAppezzamento==null) localitaAppezzamento="";
  if (sezione==null) sezione="";
  if (foglio==null) foglio="";
  if (particellaCatastale==null) particellaCatastale="";
  if (subparticella==null) subparticella="";
  if (coordinataNordBoaga==null) coordinataNordBoaga="";
  if (coordinataEstBoaga==null) coordinataEstBoaga="";
  if (coordinataNordUtm==null) coordinataNordUtm="";
  if (coordinataEstUtm==null) coordinataEstUtm="";
  if (piemonte==null) piemonte="";
  if (sigla==null) sigla="";
  if (stato_pagamento==null) stato_pagamento="";
  
  if (coordGradi==null) coordGradi="";
  if (gradiNord==null) gradiNord="";
  if (minutiNord==null) minutiNord="";
  if (secondiNord==null) secondiNord="";
  if (decimaliNord==null) decimaliNord="";
  if (gradiEst==null) gradiEst="";
  if (minutiEst==null) minutiEst="";
  if (secondiEst==null) secondiEst="";
  if (decimaliEst==null) decimaliEst="";

  if ("01".equals(piemonte))
    templ.newBlock("gis");
  else
    tipoGeoreferenziazione="M";

  //if ("G".equals(tipoGeoreferenziazione) || !"".equals(coordinataNordBoaga))
  if ("G".equals(tipoGeoreferenziazione))
  {
    templ.newBlock("disableNordUTM");
    templ.newBlock("disableEstUTM");
  }

  templ.bset("tipoGeoreferenziazione",tipoGeoreferenziazione);
  templ.bset("comune",comuneAppezzamento);
  templ.bset("istatComune",istatComune);
  templ.bset("comuneBis",comuneAppezzamento);
  templ.bset("localitaAppezzamento",localitaAppezzamento);
  templ.bset("sezione",sezione);
  templ.bset("foglio",foglio);
  templ.bset("particellaCatastale",particellaCatastale);
  templ.bset("subparticella",Utili.strikeSpecialChars(subparticella));

  templ.bset("coordinataNordBoaga",coordinataNordBoaga);
  templ.bset("coordinataEstBoaga",coordinataEstBoaga);
  templ.bset("coordinataNordUtm",coordinataNordUtm);
  templ.bset("coordinataEstUtm",coordinataEstUtm);
  
  templ.bset("gradiNord",gradiNord);
  templ.bset("minutiNord",minutiNord);
  templ.bset("secondiNord",secondiNord);
  templ.bset("decimaliNord",decimaliNord);
  templ.bset("gradiEst",gradiEst);
  templ.bset("minutiEst",minutiEst);
  templ.bset("secondiEst",secondiEst);
  templ.bset("decimaliEst",decimaliEst);
  
  if (datiAppezzamento.GRADI_DECIMALI.equals(coordGradi))
	templ.bset("checkedGradiDecimali","checked");
  else  
  	if (datiAppezzamento.GRADI_MINUTI_DECIMALI.equals(coordGradi))
  	  templ.bset("checkedGradiMinuti","checked");
  	else
 	  if (datiAppezzamento.GRADI_MINUTI_SECONDI.equals(coordGradi))
 	    templ.bset("checkedGradiMinutiSecondi","checked");
 	  else templ.bset("checkedGradiMinutiSecondi","checked");
  
  templ.bset("coordGradiOld",coordGradi);
  
  templ.bset("piemonte",piemonte);
  templ.bset("siglaProvincia",sigla);
  if(stato_pagamento.equals("S"))
	  templ.bset("disab","disabilitato");

  templ.bset("idRichiesta",aut.getIdRichiestaCorrente()+"");
  templ.bset("codiceMateriale",aut.getCodMateriale());
%>

<%= templ.text() %>
