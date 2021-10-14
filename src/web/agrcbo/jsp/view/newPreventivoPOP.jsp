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
    templ.bset("insertUpdate","newPreventivoPOP.jsp");
	String numero_preventivo = "";
	String codice_fiscale = "";
	String importo = "";
	String note = "";

if (errore != null){
	numero_preventivo = request.getParameter("numero_preventivo");
	codice_fiscale = request.getParameter("codice_fiscale");
	importo = request.getParameter("importo");
	note = request.getParameter("note_aggiuntive");
	if(numero_preventivo==null || numero_preventivo.equals("")){
		numero_preventivo = preventivi.getNumero_preventivo()!=null?preventivi.getNumero_preventivo():"";
        codice_fiscale = preventivi.getCodice_fiscale()!=null?preventivi.getCodice_fiscale():"";
        importo = preventivi.getImporto()!=null?preventivi.getImporto():"";
        note = preventivi.getNote_aggiuntive()!=null?preventivi.getNote_aggiuntive():"";
	}
}
	templ.bset("numero_preventivo",numero_preventivo);
	templ.bset("codice_fiscale",codice_fiscale);
	templ.bset("importo",importo);
	templ.bset("note_aggiuntive",note);
%>

<%= templ.text() %>