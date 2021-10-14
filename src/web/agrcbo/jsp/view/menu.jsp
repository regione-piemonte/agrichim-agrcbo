<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/menu.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%-- @ include file="/jsp/controllaUtente.inc" --%>

<%
  templ.bset("HBTO",beanParametriApplication.getHBTO(),null);
  templ.bset("HBAL",beanParametriApplication.getHBAL(),null);
  templ.bset("HBCE",beanParametriApplication.getHBCE(),null);
  templ.bset("HBCA",beanParametriApplication.getHBCA(),null);
%>

<%= templ.text() %>
