<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="sostanzaOrganica"
     scope="request"
     class="it.csi.agrc.SostanzaOrganica">
     <%
      sostanzaOrganica.setDataSource(dataSource);
      sostanzaOrganica.setAut(aut);
    %>
</jsp:useBean>

<jsp:setProperty name="sostanzaOrganica" property="*"/>

<%
  String azione=request.getParameter("azione");
  if ("delete".equals(azione))
  {
    sostanzaOrganica.delete();
    Utili.forwardConParametri(request, response, "/jsp/view/sostanzaOrganica.jsp");
    return;
  }
  else
  {
    /**
     * Controllo gli eventuali errori tramite jsp
     * */
    String errore=sostanzaOrganica.ControllaDati();
    /**
    * Se c'è un errore vuol dire che non è stato eseguito corretamente il
    * javascript
    */
    if (errore!=null)
    {
      Utili.forwardConParametri(request, response, "/jsp/view/sostanzaOrganica.jsp?modifica=no&errore="+errore);
      return;
    }
    if ("update".equals(azione) || sostanzaOrganica.select()) sostanzaOrganica.update();
    else sostanzaOrganica.insert();
    Utili.forwardConParametri(request, response, "/jsp/view/sostanzaOrganica.jsp?modifica=si");
  }
%>
