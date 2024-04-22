# Java keystores can only store Secret Keys, Key Pairs(private key + certificate chain) and Certificates. Public keys cannot stand on their own, they are usually embedded in the certification.

# Key classes of KeyStore
- java.security.KeyStore
- java.security.KeyStore.Entry
- java.security.KeyStore.SecretKeyEntry
- java.security.KeyStore.PrivateKeyEntry
- java.security.KeyStore.TrustedCertificateEntry