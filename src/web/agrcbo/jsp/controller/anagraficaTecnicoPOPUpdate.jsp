<%@ page errorPage="/jsp/view/errorePopup.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="anagUte"
     scope="request"
     class="it.csi.agrc.Anagrafica">
<%
  anagUte.setDataSource(dataSource);
  anagUte.setAut(aut);
%>
</jsp:useBean>

<jsp:setProperty name="anagUte" property="*"/>

<%
  /**
   * Controllo gli eventuali errori tramite jsp
   * */
  String errore=anagUte.ControllaDatiUpdate();

  /**
  * Se c'è un errore vuol dire che non è stato eseguito corretamente il
  * javascript
  */
  //CuneoLogger.debug(this, "Errore "+errore);

  if (errore!=null)
  {
    Utili.forwardConParametri(request, response, "/jsp/view/updateUserTecnicoPOP.jsp?errore="+errore);
    return;
  }
  else
  {
    anagUte.updateAnag();
  }
%>


<html>
<head></head>
<body onLoad="window.opener.focus(); window.close(); window.opener.location=window.opener.location"></body>
</html>
