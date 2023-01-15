# export cert and private key from keystore
- keytool -importkeystore -srckeystore server.jks -destkeystore server.p12 -deststoretype PKCS12
- keytool -export -keystore <keystore-file> -storetype <type> -storepass <pass> -alias <alias> -rfc -file <server.cer.pem>
- openssl pkcs12 -in server.p12 -nokeys -out server.cer.pem
- openssl pkcs12 -in server.p12 -nodes -nocerts -out server.key.pem

# pem format for public key, private key and cert
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