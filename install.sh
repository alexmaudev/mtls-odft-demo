/home/aleksei/mtls-demo/mtls-http-service-1/src/main/resources/certs.sh
/home/aleksei/mtls-demo/mtls-http-service-2/src/main/resources/certs.sh
/home/aleksei/mtls-demo/mtls-http-service-1/src/main/resources/add_to_the_truststore.sh
/home/aleksei/mtls-demo/mtls-http-service-2/src/main/resources/add_to_the_truststore.sh

mvn clean install -f /home/aleksei/mtls-demo/mtls-http-service-1/pom.xml
mvn clean install -f /home/aleksei/mtls-demo/mtls-http-service-2/pom.xml
