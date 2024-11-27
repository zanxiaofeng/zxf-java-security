# PEM format for public key, private key and cert
```
-----BEGIN PUBLIC KEY-----
...Base64 encoded key...
-----END PUBLIC KEY-----

-----BEGIN RSA PUBLIC KEY-----
...Base64 encoded key...
-----END RSA PUBLIC KEY-----

-----BEGIN PRIVATE KEY-----
...Base64 encoded key...
-----END PRIVATE KEY-----

-----BEGIN RSA PRIVATE KEY-----
...Base64 encoded key...
-----END RSA PRIVATE KEY-----

-----BEGIN CERTIFICATE-----
...Base64 encoded key...
-----END CERTIFICATE-----

-----BEGIN CERTIFICATE REQUEST-----
...Base64 encoded key...
-----END CERTIFICATE REQUEST-----

-----BEGIN ENCRYPTED PRIVATE KEY-----
...Base64 encoded key...
-----END ENCRYPTED PRIVATE KEY-----
```

# how to import trust cert
## import first cert
- keytool -importcert -file <ca-cert.cer> -keystore <keystore> -alias <aias> -storepass <pass>

# How to generate cert(With private key)
## Create keystore
- ```keytool -genkeypair -v -alias <aias> -keyalg RSA -keysize 2048 - -validity 365 -keystore <keystore.p12> -storetype pkcs12 -storepass <pass> -keypass <pass> -dname="CN=***,O=***,L=***,C=CN"```
## Create CSR(Certificate Signing Request)
- ```keytool -certreq -alias <alias> -file <server-cert.csr> -keystore <keystore> -storetype pkcs12```
## Get Cert from CSR
- ```<server-cert.cer>```
## Extract private key from keystore
- ```openssl pkcs12 -in <keystore.p12> -passin pass:<pass> -nodes -nocerts -out <server.key.pem>```
## Create new keystore and import private key and signed cert
- ```openssl pkcs12 -export -in <new-server-cert.cer> -inkey <server.key.pem> -out <new-keystore.p12> -passout pass:<pass>```
## Import Root CA Cert and Intermediate Cert
- ```keytool -import -trustcacerts -file <root-or-intermediate.cer> -keystore <new-keystore.p12> -storetype pkcs12 -storepass <pass> -alias <alias>```

# How to re-new cert(With private key)
## Extract private key from old keystore
- ```openssl pkcs12 -in <old-keystore.p12> -passin pass:<pass> -nodes -nocerts -out <server.key.pem>```
## Create new keystore and import private key and new signed cert
- ```openssl pkcs12 -export -in <re-new-server-cert.cer> -inkey <server.key.pem> -out <new-keystore.p12> -passout pass:<pass>```
## Import Root CA Cert and Intermediate Cert
- ```keytool -import -trustcacerts -file <root-or-intermediate.cer> -keystore <new-keystore.p12> -storetype pkcs12 -storepass <pass> -alias <alias>```
