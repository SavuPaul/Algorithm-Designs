README - Tema1 PA

Pentru implementarea fiecarui task, am folosit scheletul de laborator pentru citirea de date si furnizarea rezultatului.

------------------------------------------------------------------------------------------------------------------------

Problema 1: Servere

Implementarea problemei se bazeaza pe o cautare binara executata in range-ul dintre limita minima si cea maxima de
alimentare. Functia getValues calculeaza valorile puterilor serverlor in punctul x dat ca parametru. Astfel, pentru
fiecare nou mijloc selectat, se calculeaza valoarea minima atat la stanga cat si la dreapta sa cu o unitate. Scopul
cautarii este de a gasi maximul global in range-ul de valori, cautarea oprindu-se fie cand left > right, fie cand
s-a gasit un punct de maxim (unde valoarea minima este mai mare decat cea de la stanga si dreapta cu o unitate).
Cautarea binara: Daca valoarea minima calculata in mid este mai mare decat cea din mid - 1, dar mai mica decat cea
                din mid + 1, atunci cautarea continua in dreapta, altfel se continua in stanga.
In momentul in care se gaseste o valoare maxima globala (result), se calculeaza minimul pentru (result + 0.5) si
(result - 0.5). Stim ca valoarea punctului in care calculam expresiile puterilor serverelor poate atinge un minim
maximizat doar in numere de forma x.0 sau x.5 (pentru alte posibilitati de valori, nu s-ar atinge valorea cautata).
Ulterior, rezultatul va fi valoarea minima maximizata a puterilor calculata intre result - 0.5, result, result + 0.5.

Complexitate: O(log n)

------------------------------------------------------------------------------------------------------------------------

Problema 2: Colorare

Problema se poate rezolva doar prin formule matematice, intrucat stim cum este afectat rezultatul curent in functie de
orientarea piesei pe care o adaugam, si de culoarea sa. Se porneste de la un caz de baza (adica prima piesa):
1. PIESA ORIZONTALA -> apar 6 cazuri diferite de colorare
2. PIESA VERTICALA -> apar 3 cazuri diferite de colorare
Ulterior, pentru actualizarea numarului de posibilitati, analizam piesa curenta si piesa anterioara. 
1) Daca piesa curenta este orizontala:
    1.1) Daca piesa anterioara este orizontala, atunci numarul de posibilitati se inmulteste cu 3.
    1.2) Daca piesa anterioara este verticala, atunci numarul de posibilitati se inmulteste cu 2 (pentru PRIMA
         piesa verticala adugata), si apoi cu 3 pentru restul.
2) Daca pisa curenta este verticala:
    2.1) Daca piesa anterioara este orizontala, atunci numarul de posibilitati se inmulteste cu 2 pentru X - 1
         piese verticale (deoarece exista o singura posibilitate de colorare a piesei verticale dupa una orizontala,
         astfel prima inmultire ar fi cu 1, deci nu o luam in calcul).
    2.2) Daca piesa anterioara este verticala, atunci numarul de posibilitati se inmulteste cu 2.

Toate inmultirile cu puteri ale lui 2 si 3 au fost calculate cu o functie auxiliara bazata pe divide et impera.

Complexitate: O(n * log n)

------------------------------------------------------------------------------------------------------------------------

Problema 3: Compresie

Problema de compresie analizeaza cele 2 siruri simultan, fiind parcurse alternativ astfel:
a) Daca A[i] > B[i], atunci parcurgem B pana cand suma elementelor parcurse devine mai mare sau egala decat A[i].
   De asemenea, pe masura ce se adaugame elemente la suma, acestea sunt si sterse din sir.
   Daca suma este chiar A[i], atunci ne mutam mai departe in ambele siruri intrucat elementele au devenit egale.
b) Daca A[i] < B[i], atunci parcurgem A pana cand suma elementelor parcurse devine mai mare sau egala decat B[i].
   De asemenea, pe masura ce se adaugame elemente la suma, acestea sunt si sterse din sir.
   Daca suma este chiar B[i], atunci ne mutam mai departe in ambele siruri intrucat elementele au devenit egale.
c) Daca A[i] == B[i] inca de la inceput, ne mutam mai departe in ambele siruri.
Algoritmul va alterna intre aceste 3 conditii si va maximiza lungimea sirurilor, facandu-le totodata egale. Daca
la final, sirul A difera de sirul B, atunci nu se poate efectua compresia, deci se returneaza -1, altfel se returneaza
dimensiunea oricarui dintre cele 2 siruri.

Complexitate: O(n) intrucat parcurgerea se efectueaza o singura data, simultan, atat in functia de compresie cat si in
              cea de verificare a egalitatii sirurilor.

------------------------------------------------------------------------------------------------------------------------

Problema 4: Criptat

Pentru fiecare dintre cele maxim 8 litere diferite prezente in cuvinte se va executa urmatorul algoritm:
Fiecarui cuvant i se asociaza un raport: numar_aparitii_litera / lungime_cuvant. Aceste rapoarte sunt introduse intr-un
vector (values) care este ordonat descrescator. Cu cat valoarea raportului este mai aproape de 1, cu atat inseamna ca 
acel cuvant este mai probabil sa fie folosit in parola de lungime maxima cu litera respectiva.
Ulterior, fiecare cuvant este adaugat intr-o lista de map-uri. Am folosit o lista de map-uri intrucat map-urile nu
tolereaza aparitia unui element de 2 sau mai multe ori, iar problema nu precizeaza ca un cuvant apare o singura data in
lista de cuvinte. Cu alte cuvinte, lista va contine map-uri cu cate un singur element, acestea fiind marcate cu 0.
Apoi, in functia computeList, pentru fiecare valoare din values, iteram prin lista de map-uri pana cand este gasit un 
cuvant care are raportul respectiv. Odata gasit, este adaugat intr-un array de cuvinte ordonate, si este marcat cu 1
pentru a nu-l mai folosi in cazul in care exista rapoarte care apar de 2 sau mai multe ori. (Spre exemplu executam
pentru litera a: cuvintele "ban" si "balcan" au ambele raportul 0.5, dar difera in lungime, deci contribuie diferit la
formarea parolei finale).
Odata ordonate, formam parola finala iterand prin array-ul de cuvinte si adaugam cuvinte la parola cat timp litera
pe baza careia formam parola apare de mai mult de password_length / 2 ori.
Rezultatul final va fi lungimea maxima a parolei calculate pentru toate literele existente.

Complexitate: O(n^3 * log n) - tinand cont ca map-urile din lista contin mereu doar o singura pereche, acestea 
                               constituie o diferenta minimala in complexitatea temporala a algoritmului. Totusi,
                               luand in calcul si acest aspect, complexitatea ar fi O(n^4 * log n).

------------------------------------------------------------------------------------------------------------------------

Problema 5: Oferta

Problema se rezuma la o matrice de dimensiune N * N unde elementele de pe diagonala pricipala sunt preturile obiectelor.
Aceasta este completata doar in partea superioara, iar elementele de forma dp[i][j] reprezinta pretul minim posibil
calculat de la pozitia i la pozitia j.
Implementarea este recursiva, iar solutiile se calculeaza unele pe baza celorlalte. Exista 2 cazuri de baza pe baza
carora se pot calcula toate solutiile, si anume cand i == j (adica este pretul in sine) sau cand j - i == 1, adica cand
se aplica regula de reducere a produsului cel mai ieftin cu 50% dintre cele 2 produse grupate.

Fie A, B, C o secventa de 3 produse pe banda.
Pentru j - i == 3, se alege pretul minim din 3 cazuri, astfel:
(A, B), C sau A, (B, C) sau (A, B, C), unde parantezarea reprezinta gruparea produselor

Pentru j - i > 3, putem imparti problema in subprobleme calculate anterior, dar numai ultimele produse adaugate sunt de
interes pentru gruparea cat mai eficienta. De exemplu avem produsele: A, B, C, D, E, F, G, H. Pentru a calcula
rezultatul final (dp[1][8]), acesta este minimul dintre dp[1][4] + dp[5][8] sau dp[1][5] + dp[6][8] sau dp[1][6] +
+ dp[7][8]. La randul lor, acestea sunt calculate recursiv.

Complexitate: Algoritmul este foarte ineficient chiar si pentru valori mai mici ale lui N.
