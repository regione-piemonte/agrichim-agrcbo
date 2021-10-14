<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="microelementi"
     scope="request"
     class="it.csi.agrc.MicroelementiMetodoDTPA">
     <%
      microelementi.setDataSource(dataSource);
      microelementi.setAut(aut);
    %>
</jsp:useBean>

<jsp:setProperty name="microelementi" property="*"/>

<%
  String azione=request.getParameter("azione");
  boolean ferro=false,manganese=false,zinco=false,rame=false,boro=false;

  if ("true".equals(request.getParameter("analisiFerro"))) ferro=true;
  if ("true".equals(request.getParameter("analisiManganese"))) manganese=true;
  if ("true".equals(request.getParameter("analisiZinco"))) zinco=true;
  if ("true".equals(request.getParameter("analisiRame"))) rame=true;
  if ("true".equals(request.getParameter("analisiBoro"))) boro=true;

  if ("delete".equals(azione))
  {
    microelementi.delete();
    Utili.forwardConParametri(request, response, "/jsp/view/microelementi.jsp");
    return;
  }
  else
  {
    /**
     * Controllo gli eventuali errori tramite jsp
     * */
    String errore=microelementi.ControllaDati(ferro,manganese,zinco,rame,boro);
    /**
    * Se c'è un errore vuol dire che non è stato eseguito corretamente il
    * javascript
    */
    if (errore!=null)
    {
      Utili.forwardConParametri(request, response, "/jsp/view/microelementi.jsp?modifica=no&errore="+errore);
      return;
    }
    if ("update".equals(azione) || microelementi.select()) microelementi.update();
    else microelementi.insert();
    Utili.forwardConParametri(request, response, "/jsp/view/microelementi.jsp?modifica=si");
  }
%>
