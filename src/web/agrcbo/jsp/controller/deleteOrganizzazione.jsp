<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/controllaUtente.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="organizzazioneProfessionale"
     scope="request"
     class="it.csi.agrc.OrganizzazioneProfessionale">
<%
  organizzazioneProfessionale.setDataSource(dataSource);
  organizzazioneProfessionale.setAut(aut);
%>
</jsp:useBean>


<%
  String cancella=request.getParameter("cancella");
  organizzazioneProfessionale.setIdOrganizzazione(cancella);
  organizzazioneProfessionale.delete();
  organizzazioneProfessionale.ControllaDati();
  Utili.forward(request, response, "/jsp/view/ricercaOrganizzazione.jsp");
%>


