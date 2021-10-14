<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*" isThreadSafe="true" %>
<%
  it.csi.jsf.htmpl.Htmpl templ=null ;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="tipoCampione"
     scope="request"
     class="it.csi.agrc.TipoCampione">
    <%
      tipoCampione.setDataSource(dataSource);
      tipoCampione.setAut(aut);
    %>
</jsp:useBean>

<jsp:setProperty name="tipoCampione" property="*"/>

<%
  /**
   * Controllo gli eventuali errori tramite jsp
   * */
  String errore=tipoCampione.ControllaDati();
  /**
  * Se c'è un errore vuol dire che non è stato eseguito corretamente il
  * javascript
  */
  if (errore!=null)
  {
    Utili.forwardConParametri(request, response, "/jsp/view/identificazioneCampione.jsp?modifica=no&errore="+errore);
    return;
  }
  tipoCampione.setIdRichiesta(aut.getIdRichiestaCorrente());
  tipoCampione.update();


  Utili.forwardConParametri(request, response, "/jsp/view/identificazioneCampione.jsp?modifica=si");

%>


