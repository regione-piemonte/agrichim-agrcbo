<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="etichettaCampione"
     scope="page"
     class="it.csi.agrc.EtichettaCampione">
    <%
      etichettaCampione.setDataSource(dataSource);
      etichettaCampione.setAut(aut);
    %>
</jsp:useBean>

<%
  /**
   *  Annullamento richiesta
   */
  String note = request.getParameter("note");
  etichettaCampione.setNote(note);   
  etichettaCampione.annullaRichiesta(note);

  Utili.forward(request, response, "/jsp/view/campioniLaboratorio.jsp");
%>