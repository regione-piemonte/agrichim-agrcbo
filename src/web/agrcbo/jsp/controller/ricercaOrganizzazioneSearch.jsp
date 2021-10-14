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
  String tipoOrganizzazione = request.getParameter("tipoOrganizzazione");
  String ragioneSociale = request.getParameter("ragioneSociale");
  String istat = request.getParameter("istat");

  /**
   * Memorizzo i parametri nel bean di ricerca
   * */
  //Modifiche vulnerabilità 14/01/2020
  
  beanRicerca.setTipoOrganizzazione(Utili.isNumber(tipoOrganizzazione)?tipoOrganizzazione:"-1");
  beanRicerca.setRagioneSociale(ragioneSociale);
  beanRicerca.setIstat(istat);

  /**
   * Memorizzo i cambiamenti del bean di sessione beanRicerca
   */
  session.setAttribute("beanRicerca",beanRicerca);

  if ("-1".equals(tipoOrganizzazione)) tipoOrganizzazione = null;
  if ("".equals(ragioneSociale)) ragioneSociale = null;
  if ("".equals(istat)) istat = null;


  /**
   * Chiamo il metodo che andrà a preparare la query pre eseguire questa ricerca
   * */
  aut.ricercaOrganizzazione(true,tipoOrganizzazione,ragioneSociale,istat);
  session.setAttribute("aut",aut);

  Utili.forward(request, response, "/jsp/view/ricercaOrganizzazione.jsp");

%>
