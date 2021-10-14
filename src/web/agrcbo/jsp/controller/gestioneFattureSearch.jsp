<%@ page errorPage="/jsp/view/errorePopup.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>

<jsp:useBean  id="beanRicerca"  scope="session"  class="it.csi.agrc.BeanRicerca"/>

<%
  String annoFattura= request.getParameter("annoFattura");
  String numeroFattura= request.getParameter("numeroFattura");
  String dataDaGiorno = request.getParameter("dataDaGiorno");
  String dataDaMese = request.getParameter("dataDaMese");
  String dataDaAnno = request.getParameter("dataDaAnno");
  String dataAGiorno = request.getParameter("dataAGiorno");
  String dataAMese = request.getParameter("dataAMese");
  String dataAAnno = request.getParameter("dataAAnno");
  String numeroRichiesta= request.getParameter("numeroRichiesta");
  String annoCampione= request.getParameter("annoCampione");
  String numeroCampione= request.getParameter("numeroCampione");
  String codFisPIVA= request.getParameter("codFisPIVA");
  String statoPagamento= request.getParameter("statoPagamento");
  String statoFattura = request.getParameter("statoFattura");

  String dataDa= request.getParameter("dataDa");
  String dataA= request.getParameter("dataA");


  beanRicerca.setAnnoFattura(annoFattura);
  beanRicerca.setNumeroFattura(numeroFattura);
  beanRicerca.setDataDaGiorno(dataDaGiorno);
  beanRicerca.setDataDaMese(dataDaMese);
  beanRicerca.setDataDaAnno(dataDaAnno);
  beanRicerca.setDataAGiorno(dataAGiorno);
  beanRicerca.setDataAMese(dataAMese);
  beanRicerca.setDataAAnno(dataAAnno);
  beanRicerca.setNumeroRichiesta(numeroRichiesta);
  beanRicerca.setAnnoCampione(annoCampione);
  beanRicerca.setNumeroCampione(numeroCampione);
  beanRicerca.setCodFisPIVA(codFisPIVA);
  beanRicerca.setStatoFattura(statoFattura);
  beanRicerca.setStatoPagamento(statoPagamento);

  /**
   * Memorizzo i cambiamenti del bean di sessione beanRicerca
   */
  session.setAttribute("beanRicerca",beanRicerca);

  if ("".equals(annoFattura)) annoFattura = null;
  if ("".equals(numeroFattura)) numeroFattura = null;
  if ("".equals(dataDa)) dataDa = null;
  if ("".equals(dataA)) dataA = null;
  if ("".equals(dataDaGiorno)) dataDaGiorno = null;
  if ("".equals(dataDaMese)) dataDaMese = null;
  if ("".equals(dataDaAnno)) dataDaAnno = null;
  if ("".equals(dataAGiorno)) dataAGiorno = null;
  if ("".equals(dataAMese)) dataAMese = null;
  if ("".equals(dataAAnno)) dataAAnno = null;
  if ("".equals(numeroRichiesta)) numeroRichiesta = null;
  if ("".equals(annoCampione)) annoCampione = null;
  if ("".equals(numeroCampione)) numeroCampione = null;
  if ("".equals(codFisPIVA)) codFisPIVA = null;
  if ("".equals(statoFattura)) statoFattura = null;
  if ("".equals(statoPagamento)) statoPagamento = null;


  aut.ricercaFatture(true,annoFattura,numeroFattura,dataDa,dataA,
                     numeroRichiesta,annoCampione,numeroCampione,codFisPIVA,
                     statoPagamento,statoFattura);

  Utili.forward(request, response, "/jsp/view/gestioneFatture.jsp");

%>
