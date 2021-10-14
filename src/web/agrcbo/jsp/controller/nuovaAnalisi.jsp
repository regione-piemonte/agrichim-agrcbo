<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>


<jsp:useBean
  id="beanParametriApplication"
  scope="application"
  class="it.csi.agrc.BeanParametri">
</jsp:useBean>

<%
  //Utili.removeAllSessionAttributes(session);
  //session.invalidate();
  response.sendRedirect(java.net.URLDecoder.decode(beanParametriApplication.getUrlNuovaAnalisi()));
%>
