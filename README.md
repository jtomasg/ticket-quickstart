ticket-quickstart
=================

Quickstart basado en un Sistema de Tickets de ejemplo para arquitectura de referencia SOA/BPM 

Configuración
-------------
1. Copiar archivos ticket-rest.properties y ticket.properties en directorios de configuración de JBoss EAP y FSW respectivamente.
2. Ejecutar script ticket.sql sobre base de datos nueva (nombrar a la BD "ticket").
3. Posteriormente crear Data Source en JBoss FSW con la siguiente info:
- Name: TicketQuickstartDS
- JNDI: java:/TicketQuickstartDS
- Connection: jdbc:postgresql://localhost:5432/ticket

Npta: Instalar y desplegar (habilitar) el conector/driver jdbc de postgres (postgresql-9.1-903.jdbc4.jar u otro según version del server instalado)
	
4. Desplegar proyectos de la forma:
	Jboss EAP:
		- ticket-model.jar
		- ticket-web.war
	JBoss FSW:
		- ticket-model.jar
		- ticket-business.jar
		- ticket-services.jar
		
