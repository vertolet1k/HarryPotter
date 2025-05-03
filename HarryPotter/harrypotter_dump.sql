--
-- PostgreSQL database dump
--

-- Dumped from database version 14.17 (Homebrew)
-- Dumped by pg_dump version 14.17 (Homebrew)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: components; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.components (
    id bigint NOT NULL,
    name character varying(255),
    quantity_in_stock integer,
    type character varying(255)
);


ALTER TABLE public.components OWNER TO postgres;

--
-- Name: components_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.components_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.components_id_seq OWNER TO postgres;

--
-- Name: components_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.components_id_seq OWNED BY public.components.id;


--
-- Name: customers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customers (
    id bigint NOT NULL,
    contactinfo character varying(255),
    name character varying(255)
);


ALTER TABLE public.customers OWNER TO postgres;

--
-- Name: customers_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.customers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.customers_id_seq OWNER TO postgres;

--
-- Name: customers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.customers_id_seq OWNED BY public.customers.id;


--
-- Name: supplies; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.supplies (
    id bigint NOT NULL,
    date date,
    quantity integer NOT NULL,
    component_id bigint
);


ALTER TABLE public.supplies OWNER TO postgres;

--
-- Name: supplies_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.supplies_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.supplies_id_seq OWNER TO postgres;

--
-- Name: supplies_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.supplies_id_seq OWNED BY public.supplies.id;


--
-- Name: wands; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.wands (
    id bigint NOT NULL,
    datecreated date,
    issold boolean NOT NULL,
    core_id bigint,
    customer_id bigint,
    wood_id bigint
);


ALTER TABLE public.wands OWNER TO postgres;

--
-- Name: wands_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.wands_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.wands_id_seq OWNER TO postgres;

--
-- Name: wands_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.wands_id_seq OWNED BY public.wands.id;


--
-- Name: components id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.components ALTER COLUMN id SET DEFAULT nextval('public.components_id_seq'::regclass);


--
-- Name: customers id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customers ALTER COLUMN id SET DEFAULT nextval('public.customers_id_seq'::regclass);


--
-- Name: supplies id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.supplies ALTER COLUMN id SET DEFAULT nextval('public.supplies_id_seq'::regclass);


--
-- Name: wands id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wands ALTER COLUMN id SET DEFAULT nextval('public.wands_id_seq'::regclass);


--
-- Data for Name: components; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.components (id, name, quantity_in_stock, type) FROM stdin;
11	перо феникса	3	core
12	береза	3	wood
13	роорм	8	wood
14	орммр	8	wood
15	гор	98	core
16	оорм	7	core
\.


--
-- Data for Name: customers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.customers (id, contactinfo, name) FROM stdin;
6	vertoletik@hogwarts.abracadabra	vertoletik
7	8н89н	нгмгн
8	луовисс	оиыва
\.


--
-- Data for Name: supplies; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.supplies (id, date, quantity, component_id) FROM stdin;
12	2025-05-03	3	\N
13	2025-05-03	21	12
14	2025-05-03	12	12
\.


--
-- Data for Name: wands; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.wands (id, datecreated, issold, core_id, customer_id, wood_id) FROM stdin;
16	2025-05-03	f	\N	\N	\N
17	2025-05-03	f	11	\N	12
18	2025-05-03	f	\N	\N	\N
19	2025-05-03	f	11	\N	12
20	2025-05-03	f	15	\N	13
21	2025-05-03	f	16	\N	14
15	2025-05-03	t	11	7	12
\.


--
-- Name: components_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.components_id_seq', 16, true);


--
-- Name: customers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.customers_id_seq', 8, true);


--
-- Name: supplies_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.supplies_id_seq', 14, true);


--
-- Name: wands_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.wands_id_seq', 21, true);


--
-- Name: components components_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.components
    ADD CONSTRAINT components_pkey PRIMARY KEY (id);


--
-- Name: customers customers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customers
    ADD CONSTRAINT customers_pkey PRIMARY KEY (id);


--
-- Name: supplies supplies_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.supplies
    ADD CONSTRAINT supplies_pkey PRIMARY KEY (id);


--
-- Name: wands wands_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wands
    ADD CONSTRAINT wands_pkey PRIMARY KEY (id);


--
-- Name: wands fkhm6mmfjauujkhfqfxjb5l8oal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wands
    ADD CONSTRAINT fkhm6mmfjauujkhfqfxjb5l8oal FOREIGN KEY (core_id) REFERENCES public.components(id);


--
-- Name: wands fkqq14byrctspj33fkn7klua9kv; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wands
    ADD CONSTRAINT fkqq14byrctspj33fkn7klua9kv FOREIGN KEY (wood_id) REFERENCES public.components(id);


--
-- Name: supplies fkt7j7fkjr80qo6q4f0q2y6uhq0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.supplies
    ADD CONSTRAINT fkt7j7fkjr80qo6q4f0q2y6uhq0 FOREIGN KEY (component_id) REFERENCES public.components(id);


--
-- Name: wands fktjbeyre13rhxccbtwryy0ald; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wands
    ADD CONSTRAINT fktjbeyre13rhxccbtwryy0ald FOREIGN KEY (customer_id) REFERENCES public.customers(id);


--
-- PostgreSQL database dump complete
--

