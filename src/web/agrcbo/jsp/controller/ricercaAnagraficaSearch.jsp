<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/controllaUtente.inc" %>

<jsp:useBean  id="beanRicerca"  scope="session"  class="it.csi.agrc.BeanRicerca"/>

<%
  /**
   * Vado a leggere i valori dei tre parametri della ricerca
   * */
  String tipoUtente = request.getParameter("tipoUtente");
  String tipoOrganizzazione = request.getParameter("tipoOrganizzazione");
  String organizzazione = request.getParameter("organizzazione");
  String ragioneSociale = request.getParameter("ragioneSociale");
  String codFisPIVA = request.getParameter("codFisPIVA");

  /**
   * Memorizzo i parametri nel bean di ricerca
   * */
  beanRicerca.setTipoUtente(tipoUtente);
  beanRicerca.setTipoOrganizzazione(tipoOrganizzazione);
  beanRicerca.setOrganizzazione(organizzazione);
  beanRicerca.setRagioneSociale(ragioneSociale);
  beanRicerca.setCodFisPIVA(codFisPIVA);

  /**
   * Memorizzo i cambiamenti del bean di sessione beanRicerca
   */
  session.setAttribute("beanRicerca",beanRicerca);

  if ("-1".equals(tipoUtente)) tipoUtente = null;
  if ("-1".equals(tipoOrganizzazione)) tipoOrganizzazione = null;
  if ("-1".equals(organizzazione)) organizzazione = null;
  if ("".equals(ragioneSociale)) ragioneSociale = null;
  if ("".equals(codFisPIVA)) codFisPIVA = null;


  /**
   * Chiamo il metodo che andrà a preparare la query pre eseguire questa ricerca
   * */
  aut.ricercaAnagrafiche(true,tipoUtente,tipoOrganizzazione,organizzazione,ragioneSociale,codFisPIVA);
  session.setAttribute("aut",aut);

  Utili.forward(request, response, "/jsp/view/ricercaAnagrafica.jsp");

%>