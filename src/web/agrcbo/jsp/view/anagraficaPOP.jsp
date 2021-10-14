<%@ page errorPage="/jsp/view/errorePopup.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/anagraficaPOP.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/controllaUtente.inc" %>
<%@ include file="/jsp/dataSource.inc" %>


<jsp:useBean
     id="anagrafica"
     scope="page"
     class="it.csi.agrc.Anagrafica">
    <%
      anagrafica.setDataSource(dataSource);
      anagrafica.setAut(aut);
    %>
</jsp:useBean>

<jsp:useBean
     id="comuni"
     scope="page"
     class="it.csi.agrc.Comuni">
<%
  comuni.setDataSource(dataSource);
  comuni.setAut(aut);
%>
</jsp:useBean>


<%
  /**
  * Leggo l'id dell'anagrafica da visualizzare, eseguo la select e carico i dati
  * sulla pasina html
  * */
  String idAnagrafica=request.getParameter("dettaglio");
  anagrafica.select(Utili.isNumber(idAnagrafica)?idAnagrafica:"-1");
  templ.newBlock("anagrafica");
  templ.set("anagrafica.tipoPersona",anagrafica.getTipoPersona());
  templ.set("anagrafica.tipoUtente",anagrafica.getTipoUtente());
  if (anagrafica.getIdOrganizzazione()!=null)
  {
    templ.newBlock("anagrafica.organizzazione");
    templ.set("anagrafica.organizzazione.nomeOrganizzazione",anagrafica.getIdOrganizzazione());
  }
  templ.set("anagrafica.codiceIdentificativo",anagrafica.getCodiceIdentificativo());
  templ.set("anagrafica.cognomeRagioneSociale",anagrafica.getCognomeRagioneSociale());
  templ.set("anagrafica.nome",anagrafica.getNome());
  templ.set("anagrafica.indirizzo",anagrafica.getIndirizzo());
  templ.set("anagrafica.cap",anagrafica.getCap());
  templ.set("anagrafica.comuneResidenza",comuni.getDescrizioneComune(anagrafica.getComuneResidenza()));
  templ.set("anagrafica.email",anagrafica.getEmail());
  templ.set("anagrafica.telefono",anagrafica.getTelefono());
  templ.set("anagrafica.cellulare",anagrafica.getCellulare());
  templ.set("anagrafica.fax",anagrafica.getFax());
%>

<%= templ.text() %>
