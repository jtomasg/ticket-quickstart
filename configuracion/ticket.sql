--
-- PostgreSQL database dump
--

-- Dumped from database version 9.1.11
-- Dumped by pg_dump version 9.3.1
-- Started on 2015-01-24 16:14:56 CLST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 2206 (class 1262 OID 81466)
-- Name: ticket; Type: DATABASE; Schema: -; Owner: ticket
--

CREATE DATABASE ticket WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'C' LC_CTYPE = 'C';


ALTER DATABASE ticket OWNER TO ticket;

\connect ticket

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 174 (class 3079 OID 11907)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2209 (class 0 OID 0)
-- Dependencies: 174
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 161 (class 1259 OID 82905)
-- Name: comentario; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE comentario (
    id bigint NOT NULL,
    fecha date,
    glosa character varying(255) NOT NULL,
    ticket_id bigint
);


ALTER TABLE public.comentario OWNER TO postgres;

--
-- TOC entry 168 (class 1259 OID 82992)
-- Name: comentario_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE comentario_id_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.comentario_id_seq OWNER TO postgres;

--
-- TOC entry 162 (class 1259 OID 82910)
-- Name: estado; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE estado (
    id bigint NOT NULL,
    nombre character varying(255) NOT NULL
);


ALTER TABLE public.estado OWNER TO postgres;

--
-- TOC entry 169 (class 1259 OID 82994)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

--
-- TOC entry 163 (class 1259 OID 82915)
-- Name: prioridad; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE prioridad (
    id bigint NOT NULL,
    nombre character varying(255) NOT NULL,
    valor integer NOT NULL
);


ALTER TABLE public.prioridad OWNER TO postgres;

--
-- TOC entry 170 (class 1259 OID 82996)
-- Name: sistema_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sistema_id_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sistema_id_seq OWNER TO postgres;

--
-- TOC entry 164 (class 1259 OID 82920)
-- Name: sistema; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE sistema (
    id bigint DEFAULT nextval('sistema_id_seq'::regclass) NOT NULL,
    descripcion character varying(255),
    nombre character varying(255) NOT NULL
);


ALTER TABLE public.sistema OWNER TO postgres;

--
-- TOC entry 171 (class 1259 OID 82998)
-- Name: ticket_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE ticket_id_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.ticket_id_seq OWNER TO postgres;

--
-- TOC entry 165 (class 1259 OID 82928)
-- Name: ticket; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ticket (
    caseid bigint DEFAULT nextval('ticket_id_seq'::regclass) NOT NULL,
    asunto character varying(255) NOT NULL,
    descripcion character varying(255) NOT NULL,
    fecha_creacion timestamp without time zone NOT NULL,
    fecha_modificacion timestamp without time zone,
    estado_id bigint,
    owner_id bigint,
    prioridad_id bigint,
    support_owner_id bigint,
    sistema_id bigint
);


ALTER TABLE public.ticket OWNER TO postgres;

--
-- TOC entry 172 (class 1259 OID 83000)
-- Name: usuario_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE usuario_id_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usuario_id_seq OWNER TO postgres;

--
-- TOC entry 166 (class 1259 OID 82936)
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE usuario (
    id bigint DEFAULT nextval('usuario_id_seq'::regclass) NOT NULL,
    email character varying(255) NOT NULL,
    nombres character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    role character varying(255) NOT NULL,
    telefono character varying(255) NOT NULL,
    username character varying(20)
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- TOC entry 173 (class 1259 OID 83002)
-- Name: ver_sistema_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE ver_sistema_id_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.ver_sistema_id_seq OWNER TO postgres;

--
-- TOC entry 167 (class 1259 OID 82944)
-- Name: version_sistema; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE version_sistema (
    id bigint NOT NULL,
    descripcion character varying(255),
    version character varying(255) NOT NULL,
    sistema_id bigint
);


ALTER TABLE public.version_sistema OWNER TO postgres;

--
-- TOC entry 2189 (class 0 OID 82905)
-- Dependencies: 161
-- Data for Name: comentario; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2210 (class 0 OID 0)
-- Dependencies: 168
-- Name: comentario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('comentario_id_seq', 1, false);


--
-- TOC entry 2190 (class 0 OID 82910)
-- Dependencies: 162
-- Data for Name: estado; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO estado VALUES (1, 'Espera soporte');
INSERT INTO estado VALUES (2, 'Espera usuario');
INSERT INTO estado VALUES (3, 'Cerrado');


--
-- TOC entry 2211 (class 0 OID 0)
-- Dependencies: 169
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('hibernate_sequence', 1, false);


--
-- TOC entry 2191 (class 0 OID 82915)
-- Dependencies: 163
-- Data for Name: prioridad; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO prioridad VALUES (1, '1 (Urgente)', 1);
INSERT INTO prioridad VALUES (2, '2 (Alta)', 2);
INSERT INTO prioridad VALUES (3, '3 (Normal)', 3);
INSERT INTO prioridad VALUES (4, '4 (Baja)', 4);


--
-- TOC entry 2192 (class 0 OID 82920)
-- Dependencies: 164
-- Data for Name: sistema; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO sistema VALUES (1, 'Sistema prueba', 'Sistema de integracion X');


--
-- TOC entry 2212 (class 0 OID 0)
-- Dependencies: 170
-- Name: sistema_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('sistema_id_seq', 1, false);


--
-- TOC entry 2193 (class 0 OID 82928)
-- Dependencies: 165
-- Data for Name: ticket; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO ticket VALUES (1, 'Ticket prueba', 'dadasdsdasdsadsad', '2014-01-01 00:00:00', NULL, 2, 2, 3, 1, 1);
INSERT INTO ticket VALUES (51, 'Ticket prueba', 'dasd', '2015-01-01 00:00:00', NULL, 3, 2, 2, 1, 1);
INSERT INTO ticket VALUES (101, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (151, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (201, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (251, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (301, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (351, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (401, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (451, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (501, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (551, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (601, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (651, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (701, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (751, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (801, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (851, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (901, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (951, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (1001, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (1051, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (1101, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (1151, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (1201, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (1251, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (1301, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (1351, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (1401, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (1451, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (1501, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (1551, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (1601, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (1651, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (1701, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (1751, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (1801, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (1851, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (1901, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (1951, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (2001, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);
INSERT INTO ticket VALUES (2051, 'Ticket dasdsadsa', 'ddasdasdsadasd', '2015-01-22 00:00:00', NULL, 1, 2, 3, NULL, 1);


--
-- TOC entry 2213 (class 0 OID 0)
-- Dependencies: 171
-- Name: ticket_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('ticket_id_seq', 2051, true);


--
-- TOC entry 2194 (class 0 OID 82936)
-- Dependencies: 166
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO usuario VALUES (1, 'tgubeli@redhat.com', 'Tomas Gubeli', 'tomas.14', 'SYSTEM', '+569 95073388', 'tgubeli');
INSERT INTO usuario VALUES (2, 'pepito@pepitomail.com', 'Jose Perez', 'pepito.15', 'SYSTEM', '+569 9999999', 'pepito');


--
-- TOC entry 2214 (class 0 OID 0)
-- Dependencies: 172
-- Name: usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('usuario_id_seq', 1, false);


--
-- TOC entry 2215 (class 0 OID 0)
-- Dependencies: 173
-- Name: ver_sistema_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('ver_sistema_id_seq', 1, false);


--
-- TOC entry 2195 (class 0 OID 82944)
-- Dependencies: 167
-- Data for Name: version_sistema; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO version_sistema VALUES (1, NULL, '0.0.1-SNAPSHOT', 1);


--
-- TOC entry 2055 (class 2606 OID 82909)
-- Name: comentario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY comentario
    ADD CONSTRAINT comentario_pkey PRIMARY KEY (id);


--
-- TOC entry 2057 (class 2606 OID 82914)
-- Name: estado_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY estado
    ADD CONSTRAINT estado_pkey PRIMARY KEY (id);


--
-- TOC entry 2061 (class 2606 OID 82919)
-- Name: prioridad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY prioridad
    ADD CONSTRAINT prioridad_pkey PRIMARY KEY (id);


--
-- TOC entry 2067 (class 2606 OID 82927)
-- Name: sistema_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sistema
    ADD CONSTRAINT sistema_pkey PRIMARY KEY (id);


--
-- TOC entry 2072 (class 2606 OID 82935)
-- Name: ticket_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ticket
    ADD CONSTRAINT ticket_pkey PRIMARY KEY (caseid);


--
-- TOC entry 2074 (class 2606 OID 82986)
-- Name: uk_4tdehxj7dh8ghfc68kbwbsbll; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT uk_4tdehxj7dh8ghfc68kbwbsbll UNIQUE (email);


--
-- TOC entry 2059 (class 2606 OID 82958)
-- Name: uk_acra0y07mokujj6pfxlj0ngrf; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY estado
    ADD CONSTRAINT uk_acra0y07mokujj6pfxlj0ngrf UNIQUE (nombre);


--
-- TOC entry 2063 (class 2606 OID 82960)
-- Name: uk_fwrx1qmdpsxk9nssrh3tfiqv; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY prioridad
    ADD CONSTRAINT uk_fwrx1qmdpsxk9nssrh3tfiqv UNIQUE (nombre);


--
-- TOC entry 2069 (class 2606 OID 82964)
-- Name: uk_g52cusaqahb5kg9050rrksbvl; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sistema
    ADD CONSTRAINT uk_g52cusaqahb5kg9050rrksbvl UNIQUE (nombre);


--
-- TOC entry 2065 (class 2606 OID 82962)
-- Name: uk_k4e0biijmgm69snsi0msuq6ev; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY prioridad
    ADD CONSTRAINT uk_k4e0biijmgm69snsi0msuq6ev UNIQUE (valor);


--
-- TOC entry 2076 (class 2606 OID 83012)
-- Name: uk_usuario_username; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT uk_usuario_username UNIQUE (username);


--
-- TOC entry 2078 (class 2606 OID 82943)
-- Name: usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 2080 (class 2606 OID 82951)
-- Name: version_sistema_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY version_sistema
    ADD CONSTRAINT version_sistema_pkey PRIMARY KEY (id);


--
-- TOC entry 2070 (class 1259 OID 83021)
-- Name: fki_ticket_sistema; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX fki_ticket_sistema ON ticket USING btree (sistema_id);


--
-- TOC entry 2081 (class 2606 OID 82952)
-- Name: fk_45ayhf2kl40eskqtniehvdjtn; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY comentario
    ADD CONSTRAINT fk_45ayhf2kl40eskqtniehvdjtn FOREIGN KEY (ticket_id) REFERENCES ticket(caseid);


--
-- TOC entry 2085 (class 2606 OID 82980)
-- Name: fk_6dg424l8mkks6nx62dioi0qy1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ticket
    ADD CONSTRAINT fk_6dg424l8mkks6nx62dioi0qy1 FOREIGN KEY (support_owner_id) REFERENCES usuario(id);


--
-- TOC entry 2083 (class 2606 OID 82970)
-- Name: fk_71utihq9xqtb1nqsd0so31dy1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ticket
    ADD CONSTRAINT fk_71utihq9xqtb1nqsd0so31dy1 FOREIGN KEY (owner_id) REFERENCES usuario(id);


--
-- TOC entry 2086 (class 2606 OID 83006)
-- Name: fk_7e2f71aqwdplrs3g75gc666o8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ticket
    ADD CONSTRAINT fk_7e2f71aqwdplrs3g75gc666o8 FOREIGN KEY (sistema_id) REFERENCES sistema(id);


--
-- TOC entry 2082 (class 2606 OID 82965)
-- Name: fk_7mi9m1ak512iuo5ni7j6stkft; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ticket
    ADD CONSTRAINT fk_7mi9m1ak512iuo5ni7j6stkft FOREIGN KEY (estado_id) REFERENCES estado(id);


--
-- TOC entry 2084 (class 2606 OID 82975)
-- Name: fk_ivjfcn58rc4koco8j6g2ad29m; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ticket
    ADD CONSTRAINT fk_ivjfcn58rc4koco8j6g2ad29m FOREIGN KEY (prioridad_id) REFERENCES prioridad(id);


--
-- TOC entry 2087 (class 2606 OID 82987)
-- Name: fk_ph2t0wod02ojy3e1p3396gqsj; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY version_sistema
    ADD CONSTRAINT fk_ph2t0wod02ojy3e1p3396gqsj FOREIGN KEY (sistema_id) REFERENCES sistema(id);


--
-- TOC entry 2208 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2015-01-24 16:14:56 CLST

--
-- PostgreSQL database dump complete
--

