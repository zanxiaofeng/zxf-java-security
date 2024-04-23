# Java keystores can only store Secret Keys, Key Pairs(private key + certificate chain) and Certificates. Public keys cannot stand on their own, they are usually embedded in the certification.

# Key classes of KeyStore
- java.security.KeyStore
- java.security.KeyStore.Entry
- java.security.KeyStore.SecretKeyEntry(SecretKey sKey, Set<Entry.Attribute> attributes)
- java.security.KeyStore.PrivateKeyEntry(PrivateKey privKey, Certificate[] chain, Set<Entry.Attribute> attributes)
- java.security.KeyStore.TrustedCertificateEntry(Certificate cert, Set<Entry.Attribute> attributes)