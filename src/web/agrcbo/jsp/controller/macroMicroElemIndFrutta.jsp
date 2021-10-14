<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="macroMicroElemIndFrutta"
     scope="request"
     class="it.csi.agrc.MacroMicroElemIndFrutta">
     <%
      macroMicroElemIndFrutta.setDataSource(dataSource);
      macroMicroElemIndFrutta.setAut(aut);
    %>
</jsp:useBean>

<jsp:setProperty name="macroMicroElemIndFrutta" property="*"/>

<%
  String azione=request.getParameter("azione");

  boolean analisiCalcio=false,analisiMagnesio=false,analisiPotassio=false,
          analisiAzoto=false,analisiFosforo=false;

  if ("true".equals(request.getParameter("analisiCalcio"))) analisiCalcio=true;
  if ("true".equals(request.getParameter("analisiMagnesio"))) analisiMagnesio=true;
  if ("true".equals(request.getParameter("analisiPotassio"))) analisiPotassio=true;
  if ("true".equals(request.getParameter("analisiAzoto"))) analisiAzoto=true;
  if ("true".equals(request.getParameter("analisiFosforo"))) analisiFosforo=true;


  if ("delete".equals(azione))
  {
    macroMicroElemIndFrutta.delete();
    Utili.forwardConParametri(request, response, "/jsp/view/macroMicroElemIndFrutta.jsp");
    return;
  }
  else
  {
    /**
     * Controllo gli eventuali errori tramite jsp
     * */
    String errore=macroMicroElemIndFrutta.ControllaDati(analisiCalcio,
                                      analisiMagnesio,analisiPotassio,
                                      analisiAzoto,analisiFosforo);
    /**
    * Se c'è un errore vuol dire che non è stato eseguito corretamente il
    * javascript
    */
    if (errore!=null)
    {
      Utili.forwardConParametri(request, response, "/jsp/view/macroMicroElemIndFrutta.jsp?modifica=no&errore="+errore);
      return;
    }
    if ("update".equals(azione) || macroMicroElemIndFrutta.select()) macroMicroElemIndFrutta.update();
    else macroMicroElemIndFrutta.insert();
    Utili.forwardConParametri(request, response, "/jsp/view/macroMicroElemIndFrutta.jsp?modifica=si");
  }
%>
