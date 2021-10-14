<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="granulometriaA4Frazioni"
     scope="request"
     class="it.csi.agrc.GranulometriaA4Frazioni">
     <%
      granulometriaA4Frazioni.setDataSource(dataSource);
      granulometriaA4Frazioni.setAut(aut);
    %>
</jsp:useBean>

<jsp:useBean
     id="granulometriaA5Frazioni"
     scope="request"
     class="it.csi.agrc.GranulometriaA5Frazioni">
     <%
      granulometriaA5Frazioni.setDataSource(dataSource);
      granulometriaA5Frazioni.setAut(aut);
    %>
</jsp:useBean>

<jsp:useBean
     id="granulometriaStandard"
     scope="request"
     class="it.csi.agrc.GranulometriaStandard">
     <%
      granulometriaStandard.setDataSource(dataSource);
      granulometriaStandard.setAut(aut);
    %>
</jsp:useBean>

<jsp:setProperty name="granulometriaA4Frazioni" property="*"/>
<jsp:setProperty name="granulometriaA5Frazioni" property="*"/>
<jsp:setProperty name="granulometriaStandard" property="*"/>

<%

  String analisi=request.getParameter("analisi");
  String azione=request.getParameter("azione");
  if ("delete".equals(azione))
  {
    if (it.csi.agrc.Analisi.ANA_STANDARD.equals(analisi))
    {
      granulometriaStandard.delete();
    }
    if (it.csi.agrc.Analisi.ANA_A4FRAZIONI.equals(analisi))
    {
      granulometriaA4Frazioni.delete();
    }
    if (it.csi.agrc.Analisi.ANA_A5FRAZIONI.equals(analisi))
    {
      granulometriaA5Frazioni.delete();
    }
    Utili.forwardConParametri(request, response, "/jsp/view/granulometriaMetodoAutomatico.jsp");
    return;
  }
  else
  {
   /**
    * Controllo gli eventuali errori tramite jsp
    * */
    String errore=null;
    if (it.csi.agrc.Analisi.ANA_STANDARD.equals(analisi))
    {
      errore=granulometriaStandard.ControllaDati();
    }
    if (it.csi.agrc.Analisi.ANA_A4FRAZIONI.equals(analisi))
    {
      errore=granulometriaA4Frazioni.ControllaDati();
    }
    if (it.csi.agrc.Analisi.ANA_A5FRAZIONI.equals(analisi))
    {
      errore=granulometriaA5Frazioni.ControllaDati();
    }
   /**
   * Se c'è un errore vuol dire che non è stato eseguito corretamente il
   * javascript
   */
   if (errore!=null)
   {
     Utili.forwardConParametri(request, response, "/jsp/view/granulometriaMetodoAutomatico.jsp?modifica=no&errore="+errore);
     return;
   }
   if (it.csi.agrc.Analisi.ANA_STANDARD.equals(analisi))
   {
     if ("update".equals(azione) || granulometriaStandard.select()) granulometriaStandard.update();
     else granulometriaStandard.insert();
   }
   if (it.csi.agrc.Analisi.ANA_A4FRAZIONI.equals(analisi))
   {
     if ("update".equals(azione) || granulometriaA4Frazioni.select()) granulometriaA4Frazioni.update();
    else granulometriaA4Frazioni.insert();
   }
   if (it.csi.agrc.Analisi.ANA_A5FRAZIONI.equals(analisi))
   {
     if ("update".equals(azione) || granulometriaA5Frazioni.select()) granulometriaA5Frazioni.update();
    else granulometriaA5Frazioni.insert();
   }

   Utili.forwardConParametri(request, response, "/jsp/view/granulometriaMetodoAutomatico.jsp?modifica=si");
  }
%>
