<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="azoto"
     scope="request"
     class="it.csi.agrc.Azoto">
     <%
      azoto.setDataSource(dataSource);
      azoto.setAut(aut);
    %>
</jsp:useBean>

<jsp:setProperty name="azoto" property="*"/>

<%
  String azione=request.getParameter("azione");
  if ("delete".equals(azione))
  {
    azoto.delete();
    Utili.forwardConParametri(request, response, "/jsp/view/azoto.jsp");
    return;
  }
  else
  {
    /**
     * Controllo gli eventuali errori tramite jsp
     * */
    String errore=azoto.ControllaDati();
    /**
    * Se c'è un errore vuol dire che non è stato eseguito corretamente il
    * javascript
    */
    if (errore!=null)
    {
      Utili.forwardConParametri(request, response, "/jsp/view/azoto.jsp?modifica=no&errore="+errore);
      return;
    }
    if ("update".equals(azione) || azoto.select()) azoto.update();
    else azoto.insert();
    Utili.forwardConParametri(request, response, "/jsp/view/azoto.jsp?modifica=si");
  }
%>
