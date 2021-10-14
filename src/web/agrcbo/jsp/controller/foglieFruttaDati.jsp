<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*" isThreadSafe="true" %>

<%
  it.csi.jsf.htmpl.Htmpl templ=null ;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>


<jsp:useBean
     id="campioneVegetaliFoglieFrutta"
     scope="page"
     class="it.csi.agrc.CampioneVegetaliFoglieFrutta">
    <%
      campioneVegetaliFoglieFrutta.setDataSource(dataSource);
      campioneVegetaliFoglieFrutta.setAut(aut);
      campioneVegetaliFoglieFrutta.setIdRichiesta(aut.getIdRichiestaCorrente());
    %>
</jsp:useBean>

<jsp:setProperty name="campioneVegetaliFoglieFrutta" property="*"/>

<%
  /**
   * Controllo gli eventuali errori tramite jsp
   * */
  String errore=campioneVegetaliFoglieFrutta.ControllaDati();
  /**
  * Se c'è un errore vuol dire che non è stato eseguito corretamente il
  * javascript
  */
  if (errore!=null)
  {
    Utili.forwardConParametri(request, response, "/jsp/view/foglieFruttaDati.jsp?modifica=no&errore="+errore);
    return;
  }
  campioneVegetaliFoglieFrutta.update();
  Utili.forwardConParametri(request, response, "/jsp/view/foglieFruttaDati.jsp?modifica=si");
%>


