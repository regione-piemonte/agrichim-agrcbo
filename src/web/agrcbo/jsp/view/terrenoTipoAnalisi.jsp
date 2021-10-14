<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>
<%@page import="java.util.Iterator"%>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/terrenoTipoAnalisi.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>
<%@ include file="/jsp/view/caricaLabelAnalisi.inc" %>

<%

   if (aut.isCoordinateGeografiche())
   {
     /**
     * Devo attivare il link relativo alle coordinate geografiche
     */
     templ.newBlock("SiGis");
   }
   else
   {
     /**
     * Devo inibire il link relativo alle coordinate geografiche
     */
     templ.newBlock("NoGis");
   }
%>

<%= templ.text() %>
