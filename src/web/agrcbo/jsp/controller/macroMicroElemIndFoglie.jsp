<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="macroMicroElemIndFoglie"
     scope="request"
     class="it.csi.agrc.MacroMicroElemIndFoglie">
     <%
      macroMicroElemIndFoglie.setDataSource(dataSource);
      macroMicroElemIndFoglie.setAut(aut);
    %>
</jsp:useBean>

<jsp:setProperty name="macroMicroElemIndFoglie" property="*"/>

<%
  String azione=request.getParameter("azione");
  if ("delete".equals(azione))
  {
    macroMicroElemIndFoglie.delete();
    Utili.forwardConParametri(request, response, "/jsp/view/macroMicroElemIndFoglie.jsp");
    return;
  }
  else
  {
    /**
     * Controllo gli eventuali errori tramite jsp
     * */
    String errore=macroMicroElemIndFoglie.ControllaDati();
    /**
    * Se c'è un errore vuol dire che non è stato eseguito corretamente il
    * javascript
    */
    if (errore!=null)
    {
      Utili.forwardConParametri(request, response, "/jsp/view/macroMicroElemIndFoglie.jsp?modifica=no&errore="+errore);
      return;
    }
    if ("update".equals(azione) || macroMicroElemIndFoglie.select()) macroMicroElemIndFoglie.update();
    else macroMicroElemIndFoglie.insert();
    Utili.forwardConParametri(request, response, "/jsp/view/macroMicroElemIndFoglie.jsp?modifica=si");
  }
%>
