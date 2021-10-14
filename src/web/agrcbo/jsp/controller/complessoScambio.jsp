<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="complessoScambio"
     scope="request"
     class="it.csi.agrc.ComplessoScambio">
     <%
      complessoScambio.setDataSource(dataSource);
      complessoScambio.setAut(aut);
    %>
</jsp:useBean>

<jsp:setProperty name="complessoScambio" property="*"/>

<%
  String azione=request.getParameter("azione");
  boolean calcio=false,magnesio=false,potassio=false,csc=false,vBACl2=false;

  if ("true".equals(request.getParameter("analisiCalcio"))) calcio=true;
  if ("true".equals(request.getParameter("analisiMagnesio"))) magnesio=true;
  if ("true".equals(request.getParameter("analisiPotassio"))) potassio=true;
  if ("true".equals(request.getParameter("analisiCsc"))) csc=true;
  if ("true".equals(request.getParameter("analisivBACl2"))) vBACl2=true;


  if ("delete".equals(azione))
  {
    complessoScambio.delete();
    Utili.forwardConParametri(request, response, "/jsp/view/complessoScambio.jsp");
    return;
  }
  else
  {
    /**
     * Controllo gli eventuali errori tramite jsp
     * */
    String errore=null;//complessoScambio.ControllaDati(calcio,magnesio,potassio,csc,vBACl2);
    /**
    * Se c'è un errore vuol dire che non è stato eseguito corretamente il
    * javascript
    */
    if (errore!=null)
    {
      Utili.forwardConParametri(request, response, "/jsp/view/complessoScambio.jsp?modifica=no&errore="+errore);
      return;
    }
    if ("update".equals(azione) || complessoScambio.select()) complessoScambio.update();
    else complessoScambio.insert();
    Utili.forwardConParametri(request, response, "/jsp/view/complessoScambio.jsp?modifica=si");
  }
%>
