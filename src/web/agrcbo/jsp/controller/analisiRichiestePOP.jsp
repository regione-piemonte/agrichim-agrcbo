<%@ page errorPage="/jsp/view/errorePopup.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>

<jsp:useBean  id="beanRicerca"  scope="session"  class="it.csi.agrc.BeanRicerca"/>

<%
  /**
   * Per prima cosa controllo se il parametro breve è valorizzato a "si". In
   * questo caso vuol dire che è stata fatta una ricerca ridotta
   * */
  boolean ricercaBreve=false;
  boolean tutti=false;
  String breve = request.getParameter("breve");
  if ("si".equals(breve)) ricercaBreve=true;

  String idRichiestaDa = request.getParameter("idRichiestaDa");
  String idRichiestaA = request.getParameter("idRichiestaA");
  String dataDaGiorno = request.getParameter("dataDaGiorno");
  String dataDaMese = request.getParameter("dataDaMese");
  String dataDaAnno = request.getParameter("dataDaAnno");
  String dataAGiorno = request.getParameter("dataAGiorno");
  String dataAMese = request.getParameter("dataAMese");
  String dataAAnno = request.getParameter("dataAAnno");
  String tipoMateriale = request.getParameter("tipoMateriale");
  String cognomeProprietario = request.getParameter("cognomeProprietario");
  String nomeProprietario = request.getParameter("nomeProprietario");
  String cognomeTecnico = request.getParameter("cognomeTecnico");
  String nomeTecnico = request.getParameter("nomeTecnico");
  String etichetta = request.getParameter("etichetta");
  String organizzazione = request.getParameter("organizzazione");
  String tipoOrganizzazione = request.getParameter("tipoOrganizzazione");
  String labConsegna = request.getParameter("labConsegna");
  String temp = request.getParameter("tutti");
  if (!"N".equals(temp)) tutti=true;
  String statoPagamento = request.getParameter("statoPagamento");
  String analisiFattura = request.getParameter("analisiFattura");
  String iuv = request.getParameter("iuv");


  String dataDa = request.getParameter("dataDa");
  String dataA = request.getParameter("dataA");

  beanRicerca.setIdRichiestaDa(idRichiestaDa);
  beanRicerca.setIdRichiestaA(idRichiestaA);
  beanRicerca.setDataDaGiorno(dataDaGiorno);
  beanRicerca.setDataDaMese(dataDaMese);
  beanRicerca.setDataDaAnno(dataDaAnno);
  beanRicerca.setDataAGiorno(dataAGiorno);
  beanRicerca.setDataAMese(dataAMese);
  beanRicerca.setDataAAnno(dataAAnno);
  beanRicerca.setTipoMateriale(tipoMateriale);
  beanRicerca.setCognomeProprietario(cognomeProprietario);
  beanRicerca.setNomeProprietario(nomeProprietario);
  beanRicerca.setCognomeTecnico(cognomeTecnico);
  beanRicerca.setNomeTecnico(nomeTecnico);
  beanRicerca.setEtichetta(etichetta);
  beanRicerca.setOrganizzazione(organizzazione);
  beanRicerca.setTipoOrganizzazione(tipoOrganizzazione);
  beanRicerca.setLabConsegna(labConsegna);
  beanRicerca.setTutti(tutti);
  beanRicerca.setStatoPagamento(statoPagamento);
  beanRicerca.setAnalisiFattura(analisiFattura);
  beanRicerca.setIuv(iuv);

  /**
   * Memorizzo i cambiamenti del bean di sessione beanRicerca
   */
   session.setAttribute("beanRicerca",beanRicerca);

  if ("".equals(idRichiestaDa)) idRichiestaDa = null;
  if ("".equals(idRichiestaA)) idRichiestaA = null;
  if ("".equals(dataDa)) dataDa = null;
  if ("".equals(dataA)) dataA = null;
  if ("".equals(tipoMateriale)) tipoMateriale = null;
  if ("".equals(cognomeProprietario)) cognomeProprietario = null;
  if ("".equals(nomeProprietario)) nomeProprietario = null;
  if ("".equals(cognomeTecnico)) cognomeTecnico = null;
  if ("".equals(nomeTecnico)) nomeTecnico = null;
  if ("".equals(etichetta)) etichetta = null;
  if ("".equals(organizzazione)) organizzazione = null;
  if ("".equals(tipoOrganizzazione)) tipoOrganizzazione = null;
  if ("".equals(labConsegna)) labConsegna = null;
  if ("".equals(statoPagamento)) statoPagamento = null;
  if ("".equals(analisiFattura)) analisiFattura = null;
  if ("".equals(iuv)) iuv = null;

  if (ricercaBreve){
    aut.ricercaElencoCampioni(Autenticazione.RICERCA_ANALISI_RICHIESTE,null,null,idRichiestaDa,
                              tipoMateriale);
  }else{
    aut.ricercaElencoCampioni(Autenticazione.RICERCA_ANALISI_RICHIESTE,true,null,null,null,
                              idRichiestaDa, idRichiestaA, dataDa, dataA, null, null, null, null,
                              cognomeProprietario,nomeProprietario,
                              cognomeTecnico,nomeTecnico,
                              tipoMateriale, etichetta, labConsegna,null,
                              statoPagamento,organizzazione,null,analisiFattura,null,tutti,false,iuv);
  }
  session.setAttribute("aut",aut);

  session.setAttribute("tipoRicerca", new Integer(Autenticazione.RICERCA_ANALISI_RICHIESTE));
  if (ricercaBreve){
    Utili.forward(request, response, "/jsp/view/analisiRichieste.jsp");
  } else {
%>
<html>
<head></head>
<body onLoad="window.opener.location.href='../../jsp/view/analisiRichieste.jsp';window.opener.focus();window.close();"></body>
</html>
<%
  }
%>
