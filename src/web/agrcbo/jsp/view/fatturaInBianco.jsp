<%@page import="java.util.HashMap"%>
<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/fatturaInBianco.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/controllaUtente.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="campioneFatturato"
     scope="page"
     class="it.csi.agrc.CampioneFatturato">
    <%
      campioneFatturato.setDataSource(dataSource);
      campioneFatturato.setAut(aut);
    %>
</jsp:useBean>

<jsp:useBean
     id="datiFattura"
     scope="page"
     class="it.csi.agrc.DatiFattura">
    <%
      datiFattura.setDataSource(dataSource);
      datiFattura.setAut(aut);
    %>
</jsp:useBean>

<jsp:useBean
     id="fatture"
     scope="page"
     class="it.csi.agrc.Fatture">
    <%
      fatture.setDataSource(dataSource);
      fatture.setAut(aut);
      fatture.setPasso(beanParametriApplication.getMaxRecordXPagina());
    %>
</jsp:useBean>

<jsp:useBean
     id="beanRicerca"
     scope="session"
     class="it.csi.agrc.BeanRicerca"/>

<%
 /**
  * Facendo così quando un utente va ad effettuare una ricerca non
  * si trova precaricato nessun dato
  **/
  beanRicerca.setNonValido();
  session.setAttribute("beanRicerca",beanRicerca);

  aut.ricercaFatture();
  session.setAttribute("aut",aut);

	templ.set("iva0", (String) request.getAttribute("iva0"));
	templ.set("iva1", (String) request.getAttribute("iva1"));
	templ.set("costoSpedizione", (String) request.getAttribute("costoSpedizione"));

	templ.set("descrizione", datiFattura.getDescrizione());
  templ.set("cfPartitaIva", datiFattura.getCfPartitaIva());
  templ.set("ragioneSociale", datiFattura.getRagioneSociale());
  templ.set("indirizzo", datiFattura.getIndirizzo());
  templ.set("cap", datiFattura.getCap());
  templ.set("comuneDesc", datiFattura.getComuneDesc());
  templ.set("siglaProvincia", datiFattura.getSiglaProvincia());
  if ("S".equals(datiFattura.getPagata()))
  {
  	templ.set("checkedPagataS", "checked");
  }
  else if ("N".equals(datiFattura.getPagata()))
  {
  	templ.set("checkedPagataN", "checked");
  }
  templ.set("costoSpedizione", campioneFatturato.getImportoSpedizione());
  templ.set("totaleImponibile", campioneFatturato.getImportoImponibile());
  templ.set("totaleIva", campioneFatturato.getImportoIva());
%>

<%= templ.text() %>