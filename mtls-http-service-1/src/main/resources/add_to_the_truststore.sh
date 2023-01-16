SERVICE_2_ALIAS=http-service-2
PASSWORD=password
EXEC_FOLDER=/home/$USER/mtls-demo/mtls-http-service-1/src/main/resources
keytool -keystore $EXEC_FOLDER/server.truststore.jks -alias $SERVICE_2_ALIAS -import -file /home/$USER/mtls-demo/mtls-http-service-2/src/main/resources/ca-cert -storepass $PASSWORD -keypass $PASSWORD -noprompt