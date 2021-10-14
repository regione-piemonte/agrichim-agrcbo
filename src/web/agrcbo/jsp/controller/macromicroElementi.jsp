<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>

<%
  /*A seconda del tipo di materiale da analizzare dovorò andare in una delle
    seguenti pagine*/
  String codMateriale = aut.getCodMateriale();
  if (Analisi.MAT_ERBACEE.equals(codMateriale) || Analisi.MAT_FOGLIE.equals(codMateriale))
      Utili.forward(request, response, "/jsp/view/macroMicroElemIndFoglie.jsp");
  else if (Analisi.MAT_FRUTTA.equals(codMateriale))
      Utili.forward(request, response, "/jsp/view/macroMicroElemIndFrutta.jsp");
%>