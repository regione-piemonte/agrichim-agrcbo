<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/utility.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/controllaUtente.inc" %>

<%= templ.text() %>
