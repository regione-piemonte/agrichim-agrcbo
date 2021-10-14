<%@ page errorPage="/jsp/view/errorePopup.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>

<jsp:useBean  id="beanRicerca"  scope="session"  class="it.csi.agrc.BeanRicerca"/>

<%
  String dataDaGiorno = request.getParameter("dataDaGiorno");
  String dataDaMese = request.getParameter("dataDaMese");
  String dataDaAnno = request.getParameter("dataDaAnno");
  String dataAGiorno = request.getParameter("dataAGiorno");
  String dataAMese = request.getParameter("dataAMese");
  String dataAAnno = request.getParameter("dataAAnno");
  String annoFatturaA= request.getParameter("annoFatturaA");
  String numeroFatturaA= request.getParameter("numeroFatturaA");
  String annoFatturaDa= request.getParameter("annoFatturaDa");
  String numeroFatturaDa= request.getParameter("numeroFatturaDa");
  String codFisPIVA= request.getParameter("codFisPIVA");
  String statoPagamento= request.getParameter("statoPagamento");
  String statoFattura = request.getParameter("statoFattura");

  String dataDa= request.getParameter("dataDa");
  String dataA= request.getParameter("dataA");


  beanRicerca.setDataDaGiorno(dataDaGiorno);
  beanRicerca.setDataDaMese(dataDaMese);
  beanRicerca.setDataDaAnno(dataDaAnno);
  beanRicerca.setDataAGiorno(dataAGiorno);
  beanRicerca.setDataAMese(dataAMese);
  beanRicerca.setDataAAnno(dataAAnno);
  beanRicerca.setAnnoFatturaA(annoFatturaA);
  beanRicerca.setNumeroFatturaA(numeroFatturaA);
  beanRicerca.setAnnoFattura(annoFatturaDa);
  beanRicerca.setNumeroFattura(numeroFatturaDa);
  beanRicerca.setCodFisPIVA(codFisPIVA);
  beanRicerca.setStatoFattura(statoFattura);
  beanRicerca.setStatoPagamento(statoPagamento);

  /**
   * Memorizzo i cambiamenti del bean di sessione beanRicerca
   */
  session.setAttribute("beanRicerca",beanRicerca);

  if ("".equals(dataDa)) dataDa = null;
  if ("".equals(dataA)) dataA = null;
  if ("".equals(dataDaGiorno)) dataDaGiorno = null;
  if ("".equals(dataDaMese)) dataDaMese = null;
  if ("".equals(dataDaAnno)) dataDaAnno = null;
  if ("".equals(dataAGiorno)) dataAGiorno = null;
  if ("".equals(dataAMese)) dataAMese = null;
  if ("".equals(dataAAnno)) dataAAnno = null;
  if ("".equals(annoFatturaA)) annoFatturaA = null;
  if ("".equals(numeroFatturaA)) numeroFatturaA = null;
  if ("".equals(annoFatturaDa)) annoFatturaDa = null;
  if ("".equals(numeroFatturaDa)) numeroFatturaDa = null;
  if ("".equals(codFisPIVA)) codFisPIVA = null;
  if ("".equals(statoFattura)) statoFattura = null;
  if ("".equals(statoPagamento)) statoPagamento = null;


  aut.elencoFatture(true,dataDa,dataA,annoFatturaDa,numeroFatturaDa,
                     annoFatturaA,numeroFatturaA,codFisPIVA,
                     statoPagamento,statoFattura);
  if (codFisPIVA==null)
    Utili.forward(request, response, "/jsp/report/elencoFatture.pdf");
  else
    Utili.forward(request, response, "/jsp/report/elencoFatturePIoCF.pdf");


%>