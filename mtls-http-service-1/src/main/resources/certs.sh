CN_NAME=http-service-1
HOSTNAME=localhost
PASSWORD=password
EXEC_FOLDER=/home/aleksei/mtls-demo/mtls-http-service-1/src/main/resources
rm $EXEC_FOLDER/ca-* $EXEC_FOLDER/cert-* $EXEC_FOLDER/*.jks

openssl req -new -newkey rsa:4096 -days 365 -x509 -subj "/CN=$CN_NAME" -keyout $EXEC_FOLDER/ca-key -out $EXEC_FOLDER/ca-cert -nodes
openssl x509 -in $EXEC_FOLDER/ca-cert -out $EXEC_FOLDER/ca-cert.pem
openssl rsa -in $EXEC_FOLDER/ca-key -out $EXEC_FOLDER/ca-key.pem
keytool -genkey -keyalg RSA -keystore $EXEC_FOLDER/server.keystore.jks -validity 365 -storepass $PASSWORD -keypass $PASSWORD -dname "CN=$HOSTNAME" -storetype pkcs12
keytool -keystore $EXEC_FOLDER/server.keystore.jks -certreq -file $EXEC_FOLDER/cert-file -storepass $PASSWORD -keypass $PASSWORD
openssl x509 -req -CA $EXEC_FOLDER/ca-cert -CAkey $EXEC_FOLDER/ca-key -in $EXEC_FOLDER/cert-file -out $EXEC_FOLDER/cert-file-signed -days 365 -CAcreateserial -passin pass:$PASSWORD
keytool -keystore $EXEC_FOLDER/server.keystore.jks -alias $CN_NAME -import -file $EXEC_FOLDER/ca-cert -storepass $PASSWORD -keypass $PASSWORD -noprompt
keytool -keystore $EXEC_FOLDER/server.keystore.jks -import -file $EXEC_FOLDER/cert-file-signed -storepass $PASSWORD -keypass $PASSWORD -noprompt
keytool -keystore $EXEC_FOLDER/server.truststore.jks -alias $CN_NAME -import -file $EXEC_FOLDER/ca-cert -storepass $PASSWORD -keypass $PASSWORD -noprompt
