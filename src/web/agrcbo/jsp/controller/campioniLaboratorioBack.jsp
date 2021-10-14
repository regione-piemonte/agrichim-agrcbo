<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/controllaUtente.inc" %>
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
  String note = request.getParameter("note");
  String richieste = request.getParameter("idRichiestaSearch");

  /**
  *  Riporto il campione allo stato di campione in laboratorio
  */
  etichettaCampione.ritornoCampLab(richieste, note);

  Utili.forward(request, response, "/jsp/controller/campioniLaboratorio.jsp");
%>