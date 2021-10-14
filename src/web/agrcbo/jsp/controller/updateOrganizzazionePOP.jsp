<%@ page errorPage="/jsp/view/errorePopup.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/controllaUtente.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="organizzazioneProfessionale"
     scope="request"
     class="it.csi.agrc.OrganizzazioneProfessionale">
<%
  organizzazioneProfessionale.setDataSource(dataSource);
  organizzazioneProfessionale.setAut(aut);
%>
</jsp:useBean>

<jsp:setProperty name="organizzazioneProfessionale" property="*"/>


<%
  /**
   * Controllo gli eventuali errori tramite jsp
   * */
  String errore=organizzazioneProfessionale.ControllaDati();
  /**
  * Se c'è un errore vuol dire che non è stato eseguito corretamente il
  * javascript
  */
  if (errore!=null)
  {
    Utili.forwardConParametri(request, response, "/jsp/view/updateOrganizzazionePOP.jsp?errore="+errore);
    return;
  }
  else
  {
    organizzazioneProfessionale.update();
  }
%>


<html>
<head></head>
<body onLoad="window.opener.focus(); window.close(); window.opener.location=window.opener.location"></body>
</html>
