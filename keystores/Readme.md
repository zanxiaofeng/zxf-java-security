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
- keytool -importcert -file <file.cer> -keystore <keystore> -alias <aias> -storepass <pass>

# How to generate cert(With private key)
## Create keystore
- ```keytool -genkeypair -v -alias <aias> -keyalg RSA -keysize 2048 - -validity 365 -keystore <keystore.p12> -storetype pkcs12 -storepass <pass> -keypass <pass> -dname="CN=***,O=***,L=***,C=CN"```
## Create CSR(Certificate Signing Request)
- ```keytool -certreq -alias <alias> -file <file.csr> -keystore <keystore> -storetype pkcs12```
## Get Cert from CSR
- ```<file.cer>```
## Extract private key from keystore
- ```openssl pkcs12 -in <keystore.p12> -passin pass:<pass> -nodes -nocerts -out <file.key.pem>```
## Create new keystore and import private key and signed cert
- ```openssl pkcs12 -export -in <file.cer> -inkey <file.key.pem> -out <new-keystore.p12> -passout pass:<pass>```
## Import Root CA Cert and Intermediate Cert
- ```keytool -import -trustcacerts -file <root-or-intermediate.cer> -keystore <new-keystore.p12> -storetype pkcs12 -storepass <pass> -alias <alias>```