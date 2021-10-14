<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="granulometriaMetodoBojoucos"
     scope="request"
     class="it.csi.agrc.GranulometriaMetodoBojoucos">
     <%
      granulometriaMetodoBojoucos.setDataSource(dataSource);
      granulometriaMetodoBojoucos.setAut(aut);
    %>
</jsp:useBean>

<jsp:setProperty name="granulometriaMetodoBojoucos" property="*"/>

<%
  String azione=request.getParameter("azione");
  if ("delete".equals(azione))
  {
    granulometriaMetodoBojoucos.delete();
    Utili.forwardConParametri(request, response, "/jsp/view/granulometriaMetodoBojoucos.jsp");
    return;
  }
  else
  {
    /**
     * Controllo gli eventuali errori tramite jsp
     * */
    String errore=granulometriaMetodoBojoucos.ControllaDati();
    /**
    * Se c'è un errore vuol dire che non è stato eseguito corretamente il
    * javascript
    */
    if (errore!=null)
    {
      Utili.forwardConParametri(request, response, "/jsp/view/granulometriaMetodoBojoucos.jsp?modifica=no&errore="+errore);
      return;
    }
    if ("update".equals(azione) || granulometriaMetodoBojoucos.select()) granulometriaMetodoBojoucos.update();
    else granulometriaMetodoBojoucos.insert();
    Utili.forwardConParametri(request, response, "/jsp/view/granulometriaMetodoBojoucos.jsp?modifica=si");
  }
%>
