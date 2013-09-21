CREATE TABLE achievoAccount
(
   id uuid NOT NULL,
   username text NOT NULL,
   password text,
   account_id uuid NOT NULL,
   PRIMARY KEY (id),
   FOREIGN KEY (account_id) REFERENCES account (id)
)