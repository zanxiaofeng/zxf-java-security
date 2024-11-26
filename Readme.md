# Java keystores can only store Secret Keys, Key Pairs(private key + certificate chain) and Certificates. Public keys cannot stand on their own, they are usually embedded in the certification.

# In cryptography, a public key certificate, also known as a digital certificate or identity certificate, is an electronic document used to prove the validity of a public key. The certificate includes the public key and information about it, information about the identity of its owner (called the subject), and the digital signature of an entity that has verified the certificate's contents (called the issuer).

# 注意： 证书只是包含公钥，私钥不包含在证书里， Trust Store里面只包含证书，而Key Store里面可以包含证书、私钥以及其他Key。

# Key classes of KeyStore
- java.security.KeyStore
- java.security.KeyStore.Entry
- java.security.KeyStore.SecretKeyEntry(SecretKey sKey, Set<Entry.Attribute> attributes)
- java.security.KeyStore.PrivateKeyEntry(PrivateKey privKey, Certificate[] chain, Set<Entry.Attribute> attributes)
- java.security.KeyStore.TrustedCertificateEntry(Certificate cert, Set<Entry.Attribute> attributes)