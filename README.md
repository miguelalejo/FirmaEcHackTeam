# FirmaEcHackTeam
El objetivo del presente reto es gestionar y ampliar las capacidades de firma electrónica provistas.

# Reto Gestión Firmas EC
Se encuentra construido en JAVA. Se divide en varios módulos uno para cada tipo de problema. Se realiza modificaciones a la liberia de firma EC y se incluyen pruebas Unitarias. Se modifica version base de la herramienta de firmado para incluir este tipo de documetos. Se realizar ejemplo de firmado con HSM. Se crea servicio rest con SpringBoot para la firma de intercambio entre el plug in del aplicativo de Word. Se 

Se presenta las soluciones para:

  - Soporte para documentos de ofimática(Office).
  - Asegurar el almacenamiento de las firmas
  - Usabilidad del software de firmado

Videos Explicativos

  - HTVideo-WorExcel.mp4
  - Video-HSM.mp4
  - VideoPlugin-Office.mov
  
Modulos

firmadigital-firmador-master - Applicacion de firma digital compilar con **mvn clean install**. Luego, ejecutar java -jar firmador-jar-with-dependencies.jar.

firmadigital-libreria-master - Libreria de firma compilar con **mvn clean install**

servidor-rest-firma - Servicio para conectarse con el plug in de firma digital compilar con **mvn clean install**. Luego, ejecutar java -jar firmador-jar-with-dependencies.jar.

  - Se puede verificar el servicio en el URL: https://localhost:8443/servicio-firma/saludo

htec-word-sign-add-in - Instalar node js compilar segun las intruccion del video VideoPlugin-Office.mov

## Iniciar el Add-in

Ejecutar el comando `npm run start:add-in` 
Para detener ejecute el comando `npm run stop:add-in`


Ejemplo Resultado

  - prueba-signed.docx
  - prueba-signed.xlsx
  - cp850-signed.xml
  

