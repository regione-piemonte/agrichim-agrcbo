<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="conducibilitaSalinita"
     scope="request"
     class="it.csi.agrc.ConducibilitaSalinita">
     <%
      conducibilitaSalinita.setDataSource(dataSource);
      conducibilitaSalinita.setAut(aut);
    %>
</jsp:useBean>

<jsp:setProperty name="conducibilitaSalinita" property="*"/>

<%
  String azione=request.getParameter("azione");
  if ("delete".equals(azione))
  {
    conducibilitaSalinita.delete();
    Utili.forwardConParametri(request, response, "/jsp/view/conducibilitaSalinita.jsp");
    return;
  }
  else
  {
    /**
     * Controllo gli eventuali errori tramite jsp
     * */
    String errore=conducibilitaSalinita.ControllaDati();
    /**
    * Se c'è un errore vuol dire che non è stato eseguito corretamente il
    * javascript
    */
    if (errore!=null)
    {
      Utili.forwardConParametri(request, response, "/jsp/view/conducibilitaSalinita.jsp?modifica=no&errore="+errore);
      return;
    }
    if ("update".equals(azione) || conducibilitaSalinita.select()) conducibilitaSalinita.update();
    else conducibilitaSalinita.insert();
    Utili.forwardConParametri(request, response, "/jsp/view/conducibilitaSalinita.jsp?modifica=si");
  }
%>
