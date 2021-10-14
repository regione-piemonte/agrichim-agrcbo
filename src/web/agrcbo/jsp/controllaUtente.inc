<%
    if (!aut.isUtenteEsperto())
    {
      Utili.forward(request, response, "/jsp/view/attenzione.jsp");
      return;
    }
%>