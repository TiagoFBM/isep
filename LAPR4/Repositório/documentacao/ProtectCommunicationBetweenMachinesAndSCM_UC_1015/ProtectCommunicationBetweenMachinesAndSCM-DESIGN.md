# UC 1015 - Protect Communication Between Machines And SCM #

## DESIGN ##

Visto que se pretende que esta funcionalidade seja implementada em C, optamos por esquematizar as relações entre diferentes métodos que operam em conjunto para o bom funcionamento do sistema.

### FUNÇÕES UTILIZADAS ###

* config_request();

### FUNCIONAMENTO DA COMUNICAÇÃO ###

* Utilizando o SSL/TLS, podemos garantir uma autenticação sólida entre duas aplicações de rede, neste caso, entre o simulador de máquinas e o SCM.

* Ambas as aplicações necessitam de ter um certificado de chave pública. Não é relevante se a aplicação é cliente ou servidor, no entanto é necessário ter presente que por omissão o servidor não exige um certificado de chave pública ao cliente, logo para haver autenticação mútua é necessário que a aplicação servidora solicite o certificado do cliente.
* Geramos os certificados, através do make_certs para que seja feita uma comunicação SSL entre as aplicações:
    * Em C/OpenSSL: SSL_CTX_set_verify(ctx, SSL_VERIFY_PEER|SSL_VERIFY_FAIL_IF_NO_PEER_CERT, NULL;
    * Em Java: setNeedClientAuth(true);
* Cada aplicação tem de ter um certificado de chave pública (e a correspondente chave privada);
    * Para gerir chaves e certificados de chave pública da aplicação C/OpenSSL deve ser usado o comando openssl
        * openssl req -config ssl-tmp.cnf -new -x509 -days 3650 -nodes -sha256 -out ${name}.pem -keyout ${name}.key
    * Para gerir chaves e certificados de chave pública da aplicação Java deve ser usado o comando keytool
        * keytool -genkey -v -alias ${name} -keyalg RSA -keysize 2048 -validity 365 -keystore ${name}.jks -storepass ${STOREPASS}
* Estes certificados são auto assinados e a informação documental neles constante não é relevante porque vão ser manualmente fornecidos às aplicações, o importante é mesmo a chave pública.
* Depois disto serão gerados os ficheiros .pem, .key e .jks
* Na aplicação C/OpenSSL, a chave privada e o certificado da aplicação são definidos através das funções:
    * SSL_CTX_use_certificate_file(…, “client1.pem” , …)
    * SSL_CTX_use_PrivateKey_file(…, “client1.key”, … )

* Para que as aplicações se aceitem mutuamente como autênticas é necessário que:
    * No SCM, os certificados de confiança incluam o certificado de chave pública do simulador da máquina
    * No simulador da máquina, os certificados de confiança incluam o certificado de chave pública do SCM


* O certificado de chave pública do simulador da máquina está na key store B.jks, mas pode ser exportado em formato PEM através do comando:
    * keytool -exportcert -alias ${name} -keystore ${name}.jks -storepass ${STOREPASS} -rfc -file ${name}.pem
* O ficheiro .pem tem de ser fornecido ao SCM, sendo assim o único certificado aceite pelo SCM:
    * SSL_CTX_load_verify_locations(ctx, ”server_J.pem”, NULL)
