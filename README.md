Länk till repository: https://github.com/Miggelele/Flexidle20

Tryck på knappen "<> CODE" och ladda ner Source Code (zip).

Instruktioner för att komma igång med G20 Flexidle!

1. Installera IntelliJ (Community Edition räcker)
Länk: https://www.jetbrains.com/idea/

2. Skapa ett nytt projekt i IntelliJ
File -> New -> Project -> Välj JDK 21 (eller senare) -> Create

3. Navigera till projekt mappen ~\IdeaProjects\ProjektNamn

4. Klistra in de nedladdade filerna i er projektmapp

5. Öppna IntelliJ och kontrollera att filerna dyker upp i ert projekt

6. Skapa en lib-mapp i projektet (i projektfilen)

7. Lägg til jar-filen från https://mvnrepository.com/artifact/org.postgresql/postgresql/42.7.8 i lib-mappen 
Project Structure -> Module -> Dependencies -> tryck på "+" och välj jar

8. Döp om config.example.properties till config.properties

9. Öppna config.properties filen och lägg fyll i följande:
url =  
username = 
password = 

10. Välj "Main" filen till vänster och tryck på run knappen (Grön triangel uppe till höger)


