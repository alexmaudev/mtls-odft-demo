SERVICE_2_ALIAS=http-service-1
PASSWORD=password
EXEC_FOLDER=/home/$USER/mtls-odft-demo/mtls-http-service-2/src/main/resources
keytool -keystore $EXEC_FOLDER/server.truststore.jks -alias $SERVICE_2_ALIAS -import -file /home/$USER/mtls-odft-demo/mtls-http-service-1/src/main/resources/ca-cert -storepass $PASSWORD -keypass $PASSWORD -noprompt