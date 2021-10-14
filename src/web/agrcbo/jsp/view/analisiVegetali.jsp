<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/analisiVegetali.htm");
  boolean richiestaAnalisi[]=new boolean[2];
  boolean bCalcio=false,bMagnesio=false,bPotassio=false,bFerro=false,bManganese=false;
  boolean bZinco=false,bRame=false,bBoro=false,bFosforo=false,bAzoto=false;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>


<jsp:useBean
     id="etichettaCampioni"
     scope="page"
     class="it.csi.agrc.EtichettaCampioni">
    <%
      etichettaCampioni.setDataSource(dataSource);
      etichettaCampioni.setAut(aut);
    %>
</jsp:useBean>

<jsp:useBean
     id="etichettaCampione"
     scope="page"
     class="it.csi.agrc.EtichettaCampione">
    <%
      etichettaCampione.setDataSource(dataSource);
      etichettaCampione.setAut(aut);
    %>
</jsp:useBean>

<%@ include file="/jsp/view/campioniLaboratorioAnalisi.inc" %>
<%@ include file="/jsp/view/abilitaAnalisiVegFru.inc" %>

<%= templ.text() %>
