# Peer Review 
#####Sjors_Witteveen-pset6

######Gedaan door Yorick de Boer

## Scope
Lijkt me prima.

## Correctness
Ook goed.

## Design
In ReadJokeFromJsonURL.class wordt er gebruik gemaakt van AsyncTask. Beter is het om AsynctTaskLoader te gebruiken. Deze reset niet wanneer bijvoorbeeld het scherm roteerd, waardoor je weer opnieuw moet beginnen met laden. Daarbij is het overzichtelijker om ui aanpassingen te doen in de activity zelf in plaats van naar hele views in een asynctask te refereren. 

Er wordt heel vreemd gebruik gemaakt van de asynctask. Waarom overal void. De randomJoke had gewoon gereturned kunnen worden. Dan is die hele instance variabele constructie ook niet nodig.

API logica kan uit de asynctask. Dan kan die eventueel hergebruikt worden.

Ik snap niet helemaal het nut van De Jokes class. Het is feite een wrapper voor een arraylist? Maar zal wel vanwege pset5 zijn.

Op verschillende plekken zie ik een call naar printStackTrace(). Beter niet, is nergens voor nodig. http://stackoverflow.com/questions/7469316/why-is-exception-printstacktrace-considered-bad-practice. Een simpele Log.e() zou ook voldoende zijn.

JokesAdapter had ook een anonieme class kunnen zijn. Het wordt maar op 1 plek gebruikt en override maar 1 functie.

## Stijl


De OnCreate functie kan wat korter door het op te delen in verschillende functies. Hetzelfde geldt voor dingen in OnClick methods. Wanneer die logica in aparte functies zit met een duidelijke naam hoeft er ook geen commentaar meer bij. De functienaam is dan het commentaar, terwijl het tegelijkertijd de flow van de eerste functie beter maakt.

Het aantal lijnen commentaar kan wat minder. Enkele voorbeelden, waarbij de regel commentaar niet echt wat toevoegd.

```java
// sign out from FirebaseAuth
public void signOut(View view) {
	mAuth.signOut();
}
```

```java
// disable saveButton
saveButton.setEnabled(false);
```

```java
// build a JSON object
try {
	randomJoke = new JSONObject(str);
} catch (JSONException e) {
	e.printStackTrace();
}
```

Commentaar boven functies kan beter in javadoc stijl. Aangezien dat duidelijker is. En het daar tenslotte voor is..

Er wordt inconsistent gebruik gemaakt van "m" prefixing. Daarbij zou er eigenlijk helemaal geen gebruik van gemaakt hoeven worden. Deze notatie wordt alleen gebruikt bij AOSP. Instance variabelen zijn duidelijk genoeg doormiddel van kleur in Android Studio.
