# openssl rand
- openssl rand -hex 16 -out outfile
- openssl rand -base64 16

# openssl dgst
- openssl dgst -md5 <file>

# openssl enc
## Key - 密钥
- 密钥是加密算法直接使用的原始二进制数据，可以直接参与加密与解密运算，长度固定（如AES-256密钥长度为32字节），通常为随机生成，不易记忆。
## Key - 密钥 - 生成
- openssl rand -out aes-256.key 32
- openssl rand -hex 32 > aes-256-hex.key
## Key - 密钥 - 加密
- openssl enc -aes-256-cbc -e -in <plain>.txt -out <encrypted>.enc -K $(cat aes-256-hex.key) -iv 05041820bd5c59775dde622f764af3bd
- openssl enc -aes-256-cbc -e -in <plain>.txt -out <encrypted>.enc -K $(xxd -p -c 256 aes-256.key) -iv 00000000000000000000000000000000
## Key - 密钥 - 解密
- openssl enc -aes-256-cbc -d -in <encrypted>.enc -out <plain>.txt -K $(cat aes-256-hex.key) -iv 05041820bd5c59775dde622f764af3bd
- openssl enc -aes-256-cbc -d -in <encrypted>.enc -out <plain>.txt -K $(xxd -p -c 256 aes-256.key) -iv 00000000000000000000000000000000

## Passphrase - 密码短语
- 密码短语是人类可读，通常比较长的字符串，不能直接用于加密与解密，需要通过密钥派生函数（如PBKDF2）转换为密钥和IV后用于加密与解密，密码短语便于记忆和输入。
## Passphrase - 密码短语 - 生成
- openssl rand -base64 <number of length: 32>
## Passphrase - 密码短语 - 加密
- openssl enc -aes-256-cbc -pbkdf2 -e -in <plain>.txt -out <encrypted>.enc -pass pass：<your passphrase>
- openssl enc -aes-256-cbc -pbkdf2 -e -in <plain>.txt -out <encrypted>.enc -pass file：/path/to/passphrase.txt
## Passphrase - 密码短语 - 解密
- openssl enc -aes-256-cbc -pbkdf2 -d -in <encrypted>.enc -out <plain>.txt -pass pass：<your passphrase>
- openssl enc -aes-256-cbc -pbkdf2 -d -in <encrypted>.enc -out <plain>.txt -pass file：/path/to/passphrase.txt

## IV - 初始化向量， 建议使用随即值
- openssl rand -hex 16
