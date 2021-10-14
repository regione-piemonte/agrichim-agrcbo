<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="fosforoMetodoOLSEN"
     scope="request"
     class="it.csi.agrc.FosforoMetodoOLSEN">
     <%
      fosforoMetodoOLSEN.setDataSource(dataSource);
      fosforoMetodoOLSEN.setAut(aut);
    %>
</jsp:useBean>

<jsp:setProperty name="fosforoMetodoOLSEN" property="*"/>

<%
  String azione=request.getParameter("azione");
  if ("delete".equals(azione))
  {
    fosforoMetodoOLSEN.delete();
    Utili.forwardConParametri(request, response, "/jsp/view/fosforoOLSEN.jsp");
    return;
  }
  else
  {
    /**
     * Controllo gli eventuali errori tramite jsp
     * */
    String errore=fosforoMetodoOLSEN.ControllaDati();
    /**
    * Se c'è un errore vuol dire che non è stato eseguito corretamente il
    * javascript
    */
    if (errore!=null)
    {
      Utili.forwardConParametri(request, response, "/jsp/view/fosforoOLSEN.jsp?modifica=no&errore="+errore);
      return;
    }
    if ("update".equals(azione) || fosforoMetodoOLSEN.select()) fosforoMetodoOLSEN.update();
    else fosforoMetodoOLSEN.insert();
    Utili.forwardConParametri(request, response, "/jsp/view/fosforoOLSEN.jsp?modifica=si");
  }
%>
