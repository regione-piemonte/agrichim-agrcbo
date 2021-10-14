<%@ page import="it.csi.iride2.policy.entity.Identita"%>
<%@ page import="it.csi.csi.porte.InfoPortaDelegata" %>
<%@ page import="it.csi.csi.util.xml.PDConfigReader" %>
<%@ page import="it.csi.csi.porte.proxy.PDProxy" %>
<%@ page import="it.csi.iride2.policy.interfaces.PolicyEnforcerBaseService" %>

<%
  String codiceFiscale = request.getParameter("codiceFiscale");
  if (codiceFiscale != null)
  {
    String cognome = request.getParameter("cognome");
    String nome = request.getParameter("nome");
    String password = request.getParameter("password");
    String dominio = request.getParameter("dominio");
    
  	InfoPortaDelegata infoPDPEP = PDConfigReader.read(session.getServletContext().getResourceAsStream("/WEB-INF/defPDPEPEJB.xml"));
 		PolicyEnforcerBaseService iridePEPClient = (PolicyEnforcerBaseService) PDProxy.newInstance(infoPDPEP);
  
  	Identita identita = iridePEPClient.identificaUserPassword(cognome + "." + nome + dominio, password);
    
    session.setAttribute("identita", identita);
    
    if ("@ipa".equals(dominio))
    {
   		response.sendRedirect("secure/wrup/login.jsp");
    }
    else
    {
    	response.sendRedirect("secure/sisp/login.jsp");
    }
  }
%>

<html>
  <head>
    <title>Agrichim Back Office - Home page di svilupo/test</title>
    <META http-equiv="Content-Type" content="text/html; charset=ISO-8859-15"></META>

    <script>
    nomi = new Array(); //viene creato l'array
    nomi[0]="DEMO 21";
    nomi[1]="DEMO 22";
    nomi[2]="DEMO 28";
    nomi[3]="DEMO 30";    

    cognomi = new Array(); //viene creato l'array
    cognomi[0]="CSI";
    cognomi[1]="CSI";
    cognomi[2]="CSI";
    cognomi[3]="CSI";

    function updateFieldsPA()
    {
      var codiceFiscaleSelect=document.getElementById("codiceFiscalePA");
      var nome=document.getElementById("nomePA");
      var cognome=document.getElementById("cognomePA");
      var ruolo=document.getElementById("ruoloPA");

   	  nome.value=nomi[codiceFiscaleSelect.selectedIndex];
   	  cognome.value=cognomi[codiceFiscaleSelect.selectedIndex];
   	  ruolo.value=ruoli[codiceFiscaleSelect.selectedIndex];
    }
    </script>
  </head>
  <body>
    <h1>Agrichim Back Office<br />Home page di sviluppo/test</h1>
    <p><a href="/agrcbo/secure/wrup/login.jsp">URL RUPAR</a></p>
    <h3>Login per test senza sistemi di autenticazione</h3>

    <p>RUPAR</p>
    <form method="POST" action="">
    <select id="codiceFiscalePA" name="codiceFiscale" onChange="updateFieldsPA();">
      <option value="AAAAAA00A11B000J" selected>CSI.DEMO 21 - Utente privato (solo fo)</option>
      <option value="AAAAAA00A11C000K" selected>CSI.DEMO 22 - Tecnico (solo fo)</option>
      <option value="AAAAAA00A11I000Q" selected>CSI.DEMO 28 - LAR Base</option>
      <option value="AAAAAA00A11K000S" selected>CSI.DEMO 30 - LAR Esperto</option>
    </select>
    <input type="hidden" id="passwordPA" name="password" value="PIEMONTE" />
    <input type="hidden" id="dominioPA" name="dominio" value="@ipa" />
    <input type="text" id="nomePA" name="nome" value="DEMO 30" />
    <input type="text" id="cognomePA" name="cognome" value="CSI" />
    <input type="submit" value="Accedi" />
    </form>

  </body>
</html>