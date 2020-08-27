# S3T-PT-2020
PT 2020 en S3T

__ADAPTER CE README à votre projet__

Le code d'example qui a été donné dans le projet tutoré est placé ici juste pour vérifier que l'environnement supporte bien javaFx.
  * Le test qu'il contient n'a pour seule valeur que de vérifier que l'application s'exécute (mvn test).
  * le test ne fonctionne pas dans Eclipse (problème de thread)
  * Pour exécuter l'application qui elle fonctionne : 
    ```shell
    mvn exec:java -Dexec.mainClass=example.App &
    mvn exec:java -Dexec.mainClass=example.App &
   ```
