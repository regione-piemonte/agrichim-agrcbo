<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*" isThreadSafe="true" %>

<%
  it.csi.jsf.htmpl.Htmpl templ=null ;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>


<jsp:useBean
     id="campioneVegetaliErbacee"
     scope="page"
     class="it.csi.agrc.CampioneVegetaliErbacee">
    <%
      campioneVegetaliErbacee.setDataSource(dataSource);
      campioneVegetaliErbacee.setAut(aut);
      campioneVegetaliErbacee.setIdRichiesta(aut.getIdRichiestaCorrente());
    %>
</jsp:useBean>

<jsp:setProperty name="campioneVegetaliErbacee" property="*"/>

<%
  /**
   * Controllo gli eventuali errori tramite jsp
   * */
  String errore=campioneVegetaliErbacee.ControllaDati();
  /**
  * Se c'è un errore vuol dire che non è stato eseguito corretamente il
  * javascript
  */
  if (errore!=null)
  {
    Utili.forwardConParametri(request, response, "/jsp/view/erbaceeDati.jsp?modifica=no&errore="+errore);
    return;
  }
  campioneVegetaliErbacee.update();
  Utili.forwardConParametri(request, response, "/jsp/view/erbaceeDati.jsp?modifica=si");
%>


