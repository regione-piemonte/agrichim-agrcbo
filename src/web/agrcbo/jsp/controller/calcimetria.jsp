<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="calcimetria"
     scope="request"
     class="it.csi.agrc.Calcimetria">
     <%
      calcimetria.setDataSource(dataSource);
      calcimetria.setAut(aut);
    %>
</jsp:useBean>

<jsp:setProperty name="calcimetria" property="*"/>

<%
  String azione=request.getParameter("azione");
  if ("delete".equals(azione))
  {
    calcimetria.delete();
    Utili.forwardConParametri(request, response, "/jsp/view/calcimetria.jsp");
    return;
  }
  else
  {
    /**
     * Controllo gli eventuali errori tramite jsp
     * */
    String errore=calcimetria.ControllaDati();
    /**
    * Se c'è un errore vuol dire che non è stato eseguito corretamente il
    * javascript
    */
    if (errore!=null)
    {
      Utili.forwardConParametri(request, response, "/jsp/view/calcimetria.jsp?modifica=no&errore="+errore);
      return;
    }
    if ("update".equals(azione) || calcimetria.select()) calcimetria.update();
    else calcimetria.insert();
    Utili.forwardConParametri(request, response, "/jsp/view/calcimetria.jsp?modifica=si");
  }
%>
