<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*" isThreadSafe="true" %>


<jsp:useBean
  id="beanParametriApplication"
  scope="application"
  class="it.csi.agrc.BeanParametri">
</jsp:useBean>

<%
  String urlStartApplication = beanParametriApplication.getUrlStartApplication();

  Utili.removeAllSessionAttributes(session);
  session.invalidate();

  CuneoLogger.debug(this, "Agrichim BO - Fine sessione - sendRedirect verso: "+urlStartApplication);
  response.sendRedirect(java.net.URLDecoder.decode(urlStartApplication));
%>
