<%@ page errorPage="/jsp/view/errorePopup.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/controllaUtente.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="fattura"
     scope="page"
     class="it.csi.agrc.Fattura">
    <%
      fattura.setDataSource(dataSource);
      fattura.setAut(aut);
    %>
</jsp:useBean>


<%
  String anno=request.getParameter("anno");
  String numero=request.getParameter("numero");
  fattura.cancella(anno,numero);
%>


<html>
<head></head>
<body onLoad="window.opener.focus(); window.opener.location=window.opener.location; window.close();"></body>
</html>
