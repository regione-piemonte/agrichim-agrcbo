<jsp:useBean
  id="beanParametriApplication"
  scope="application"
  class="it.csi.agrc.BeanParametri">
</jsp:useBean>

<%
    it.csi.agrc.Autenticazione aut =
         (it.csi.agrc.Autenticazione)session.getAttribute("aut");
    if (aut==null)
    {
      it.csi.cuneo.CuneoLogger.debug(this, "Sessione non valida");
      response.sendRedirect(beanParametriApplication.getUrlStartApplication());
      return;
    }
    else
    {
      if (templ!=null)
      {
        templ.bset("nomeUtente",aut.getNome()+" "
                   + aut.getCognome());
      }
    }

%>