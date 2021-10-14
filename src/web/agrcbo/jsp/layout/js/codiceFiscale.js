function controllaCodiceFiscale(cf)
{
	var i, s, set1, set2, setpari, setdisp;
	if( cf == '' )  return "Inserire un codice fiscale valido";
	cf = cf.toUpperCase();
	if( cf.length != 16 )
		return "Un codice fiscale deve essere composto esattamente da 16 caratteri";


	set1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	set2 = "ABCDEFGHIJABCDEFGHIJKLMNOPQRSTUVWXYZ";
	setpari = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	setdisp = "BAKPLCQDREVOSFTGUHMINJWZYX";
	s = 0;
	for( i = 1; i <= 13; i += 2 )
		s += setpari.indexOf( set2.charAt( set1.indexOf( cf.charAt(i) )));
	for( i = 0; i <= 14; i += 2 )
		s += setdisp.indexOf( set2.charAt( set1.indexOf( cf.charAt(i) )));
	if( s%26 != cf.charCodeAt(15)-'A'.charCodeAt(0) )
		return "Codice di controllo del codice fiscale (ultima lettera) non valido";
	return "";
}
