<%@ page errorPage="/jsp/view/errorePopup.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>
<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/preventivoPOP.htm");
%>
<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/controllaUtente.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="preventivi"
     scope="page"
     class="it.csi.agrc.Preventivi">
    <%
    preventivi.setDataSource(dataSource);
    preventivi.setAut(aut);
    preventivi.setIdRichiesta(aut.getIdRichiestaCorrente());
    %>
</jsp:useBean>

<!--
  Il file che includo serve a gestire gli errori dovuti al funzionamento non
  corretto del javascript
-->
<%@ include file="/jsp/view/problemiJavascript.inc" %>

<%
  /**
   * Valorizzo la pagina a cui deve html deve mandare l'elaborazione de dati
   * */
  templ.bset("insertUpdate","updatePreventivoPOP.jsp");

  String id_preventivo=null;
  String numero_preventivo=null;
  String codice_fiscale=null;
  String importo=null;
  String note_aggiuntive=null;
  
  /**
  * Leggo l'id del preventivo da visualizzare, eseguo la select e carico i dati
  * sulla pagina html
  * */
  id_preventivo=request.getParameter("dettaglio");
  if (id_preventivo!=null) {
	preventivi.select(id_preventivo);
	numero_preventivo = preventivi.getPreventivi().get(0).getNumero_preventivo();
	codice_fiscale = preventivi.getPreventivi().get(0).getCodice_fiscale();
	importo = preventivi.getPreventivi().get(0).getImporto();
	note_aggiuntive = preventivi.getPreventivi().get(0).getNote_aggiuntive();
  }else{
	id_preventivo = "";
	numero_preventivo = "";
	codice_fiscale = "";
	importo = "";
	note_aggiuntive = "";
  }

  templ.bset("readonly","readonly");
  templ.bset("idPreventivo",id_preventivo);
  templ.bset("numero_preventivo",numero_preventivo);
  templ.bset("codice_fiscale",codice_fiscale);
  templ.bset("importo",importo.equals("")?"":Utili.valuta(importo));
  templ.bset("note_aggiuntive",note_aggiuntive);
%>

<%= templ.text() %>