<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.servlet.*" isThreadSafe="true" %>

<%
//  // Invoca la precompilazione
//  PrecompilazioneInvoker pi = new PrecompilazioneInvoker(request);
//  Thread thread = new Thread(pi);
//  thread.start();

  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/HomeBackOffice.htm");

  templ.bset("serverPath","https://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath());
%>

<%= templ.text() %>
