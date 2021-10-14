<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="reazioneSuolo"
     scope="request"
     class="it.csi.agrc.ReazioneSuolo">
     <%
      reazioneSuolo.setDataSource(dataSource);
      reazioneSuolo.setAut(aut);
    %>
</jsp:useBean>

<jsp:setProperty name="reazioneSuolo" property="*"/>

<%
  String azione=request.getParameter("azione");
  if ("delete".equals(azione))
  {
    reazioneSuolo.delete();
    Utili.forwardConParametri(request, response, "/jsp/view/reazioneSuolo.jsp");
    return;
  }
  else
  {
    /**
     * Controllo gli eventuali errori tramite jsp
     * */
    String errore=reazioneSuolo.ControllaDati();
    /**
    * Se c'è un errore vuol dire che non è stato eseguito corretamente il
    * javascript
    */
    if (errore!=null)
    {
      Utili.forwardConParametri(request, response, "/jsp/view/reazioneSuolo.jsp?modifica=no&errore="+errore);
      return;
    }
    if ("update".equals(azione) || reazioneSuolo.select()) reazioneSuolo.update();
    else reazioneSuolo.insert();
    Utili.forwardConParametri(request, response, "/jsp/view/reazioneSuolo.jsp?modifica=si");
  }
%>
