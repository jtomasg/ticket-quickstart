ticket-quickstart
=================

Quickstart basado en un Sistema de Tickets de ejemplo para arquitectura de referencia SOA/BPM 

Configuración
-------------
1. Copiar archivos ticket-rest.properties y ticket.properties en directorios de configuración de JBoss EAP y FSW respectivamente.
2. Ejecutar script ticket.sql sobre base de datos nueva.
3. Posteriormente crear Data Source en JBoss FSW con la siguiente info:
	
4. Desplegar proyectos de la forma:
	Jboss EAP:
		- ticket-model.jar
		- ticket-web.war
	JBoss FSW:
		- ticket-model.jar
		- ticket-business.jar
		- ticket-services.jar
		
