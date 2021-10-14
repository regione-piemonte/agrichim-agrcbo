<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
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


<%
  String cancella=request.getParameter("cancella");
  preventivi.setIdPreventivoStr(cancella);
  preventivi.delete();
  preventivi.ControllaDati(false);
  Utili.forward(request, response, "/jsp/view/ricercaPreventivi.jsp");
%>


