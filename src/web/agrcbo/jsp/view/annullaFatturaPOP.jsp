<%@ page errorPage="/jsp/view/errorePopup.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/annullaFatturaPOP.htm");
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
  String ultimaFattura[]=fattura.ultimaFattura();
  String anno=request.getParameter("annoSelected");
  String numero=request.getParameter("fatturaSelected");
  String dataFattura=request.getParameter("dataFattura");


  if (anno==null) anno="";
  if (numero==null) numero="";
  if (dataFattura==null) dataFattura="";
  templ.bset("dataFattura",dataFattura);
  templ.bset("numero",numero);
  templ.bset("anno",anno);

  if (anno.equals(ultimaFattura[0])
      && numero.equals(ultimaFattura[1]))
  {
    templ.newBlock("cancella");
  }
  else
  {
    templ.newBlock("annulla");
  }

%>

<%= templ.text() %>
