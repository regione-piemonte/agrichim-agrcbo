<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="anagAzi"
     scope="request"
     class="it.csi.agrc.Anagrafica">
<%
  anagAzi.setDataSource(dataSource);
  anagAzi.setAut(aut);
%>
</jsp:useBean>

<jsp:setProperty name="anagAzi" property="*"/>

<%

  anagAzi.setComuneResidenza(request.getParameter("istat"));
  /**
   * Controllo gli eventuali errori tramite jsp
   * */
  String errore=anagAzi.ControllaDati();
  /**
  * Se c'è un errore vuol dire che non è stato eseguito corretamente il
  * javascript
  */
  //CuneoLogger.debug(this, "errore "+ errore);
  if (errore!=null)
  {
    Utili.forwardConParametri(request, response, "/jsp/view/datiAnagrafiche.jsp?modifica=no&errore="+errore);
    return;
  }
  anagAzi.update();


  Utili.forwardConParametri(request, response, "/jsp/view/datiAnagrafiche.jsp?modifica=si");

%>
