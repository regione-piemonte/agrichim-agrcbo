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
  if (Analisi.MAT_TERRENO.equals(codMateriale))
      Utili.forward(request, response, "/jsp/view/terrenoDati.jsp");
  else if (Analisi.MAT_ERBACEE.equals(codMateriale))
      Utili.forward(request, response, "/jsp/view/erbaceeDati.jsp");
  else if (Analisi.MAT_FOGLIE.equals(codMateriale) || Analisi.MAT_FRUTTA.equals(codMateriale))
      Utili.forward(request, response, "/jsp/view/foglieFruttaDati.jsp");
%>
