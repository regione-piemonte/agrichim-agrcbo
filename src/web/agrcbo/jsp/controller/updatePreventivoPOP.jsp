<%@ page errorPage="/jsp/view/errorePopup.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/controllaUtente.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="preventivi"
     scope="page"
     class="it.csi.agrc.Preventivi">
    <%
    preventivi.setDataSource(dataSource);
    preventivi.setAut(aut);
    preventivi.setIdRichiesta(aut.getIdRichiestaCorrente());
    %>
</jsp:useBean>

<jsp:setProperty name="preventivi" property="*"/>


<%
  /**
   * Controllo gli eventuali errori tramite jsp
   * */
  String errore=preventivi.ControllaDati(true);
  /**
  * Se c'è un errore vuol dire che non è stato eseguito corretamente il
  * javascript
  */
  if (errore!=null)
  {
    Utili.forwardConParametri(request, response, "/jsp/view/updatePreventivoPOP.jsp?dettaglio="+preventivi.getIdPreventivoStr()+"&errore="+errore);
    return;
  }
  else
  {
    preventivi.update();
  }
%>


<html>
<head></head>
<body onLoad="window.opener.focus(); window.close(); window.opener.location=window.opener.location"></body>
</html>
