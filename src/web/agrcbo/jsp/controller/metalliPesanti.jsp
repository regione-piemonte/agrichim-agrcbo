<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="metalliPesanti"
     scope="request"
     class="it.csi.agrc.MetalliPesanti">
     <%
      metalliPesanti.setDataSource(dataSource);
      metalliPesanti.setAut(aut);
    %>
</jsp:useBean>

<jsp:setProperty name="metalliPesanti" property="*"/>

<%
  String azione = request.getParameter("azione");
  boolean ferroTotale = false, manganeseTotale = false, zincoTotale = false, rameTotale = false, piomboTotale = false, cromoTotale = false;
  boolean boroTotale = false, nichelTotale = false, cadmioTotale = false, stronzioTotale = false, altroMetalloTotale = false;

  if ("true".equals(request.getParameter("analisiFerroTotale"))) ferroTotale = true;
  if ("true".equals(request.getParameter("analisiManganeseTotale"))) manganeseTotale = true;
  if ("true".equals(request.getParameter("analisiZincoTotale"))) zincoTotale = true;
  if ("true".equals(request.getParameter("analisiRameTotale"))) rameTotale = true;
  if ("true".equals(request.getParameter("analisiPiomboTotale"))) piomboTotale = true;
  if ("true".equals(request.getParameter("analisiCromoTotale"))) cromoTotale = true;
  if ("true".equals(request.getParameter("analisiBoroTotale"))) boroTotale = true;
  if ("true".equals(request.getParameter("analisiNichelTotale"))) nichelTotale = true;
  if ("true".equals(request.getParameter("analisiCadmioTotale"))) cadmioTotale = true;
  if ("true".equals(request.getParameter("analisiStronzioTotale"))) stronzioTotale = true;
  if ("true".equals(request.getParameter("analisiAltroMetalloTotale"))) altroMetalloTotale = true;

  if ("delete".equals(azione))
  {
    metalliPesanti.delete();
    Utili.forwardConParametri(request, response, "/jsp/view/metalliPesanti.jsp");
    return;
  }
  else
  {
    /**
     * Controllo gli eventuali errori tramite jsp
     */
    String errore = metalliPesanti.ControllaDati(ferroTotale, manganeseTotale, zincoTotale, rameTotale, piomboTotale, cromoTotale, boroTotale, nichelTotale, cadmioTotale, stronzioTotale, altroMetalloTotale);

    /**
     * Se c'è un errore vuol dire che non è stato eseguito corretamente il javascript
     */
    if (errore != null)
    {
      Utili.forwardConParametri(request, response, "/jsp/view/metalliPesanti.jsp?modifica=no&errore="+errore);
      return;
    }

    if ("update".equals(azione) || metalliPesanti.select())
    {
    	metalliPesanti.update();
    }
    else
    {
    	metalliPesanti.insert();
    }
    Utili.forwardConParametri(request, response, "/jsp/view/metalliPesanti.jsp?modifica=si");
  }
%>