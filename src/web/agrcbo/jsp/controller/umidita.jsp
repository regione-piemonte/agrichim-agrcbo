<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="umiditaCampione"
     scope="request"
     class="it.csi.agrc.UmiditaCampione">
     <%
      umiditaCampione.setDataSource(dataSource);
      umiditaCampione.setAut(aut);
    %>
</jsp:useBean>

<jsp:setProperty name="umiditaCampione" property="*"/>

<%
  String azione=request.getParameter("azione");
  if ("delete".equals(azione))
  {
    umiditaCampione.delete();
    Utili.forwardConParametri(request, response, "/jsp/view/umidita.jsp");
    return;
  }
  else
  {
    /**
     * Controllo gli eventuali errori tramite jsp
     * */
    String errore=umiditaCampione.ControllaDati();
    /**
    * Se c'è un errore vuol dire che non è stato eseguito corretamente il
    * javascript
    */
    if (errore!=null)
    {
      Utili.forwardConParametri(request, response, "/jsp/view/umidita.jsp?modifica=no&errore="+errore);
      return;
    }
    if ("update".equals(azione) || umiditaCampione.select())
    {
      CuneoLogger.debug(this, "azione "+azione);
      umiditaCampione.update();
    }
    else umiditaCampione.insert();
    Utili.forwardConParametri(request, response, "/jsp/view/umidita.jsp?modifica=si");
  }
%>
