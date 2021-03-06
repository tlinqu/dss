package gov.samhsa.c2s.dss.config;

import ch.qos.logback.audit.AuditException;
import gov.samhsa.c2s.common.audit.AuditClient;
import gov.samhsa.c2s.common.audit.AuditClientImpl;
import gov.samhsa.c2s.common.document.accessor.DocumentAccessor;
import gov.samhsa.c2s.common.document.accessor.DocumentAccessorImpl;
import gov.samhsa.c2s.common.document.converter.DocumentXmlConverter;
import gov.samhsa.c2s.common.document.converter.DocumentXmlConverterImpl;
import gov.samhsa.c2s.common.document.transformer.XmlTransformer;
import gov.samhsa.c2s.common.document.transformer.XmlTransformerImpl;
import gov.samhsa.c2s.common.filereader.FileReader;
import gov.samhsa.c2s.common.filereader.FileReaderImpl;
import gov.samhsa.c2s.common.marshaller.SimpleMarshaller;
import gov.samhsa.c2s.common.marshaller.SimpleMarshallerImpl;
import gov.samhsa.c2s.common.namespace.DefaultNamespaceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {

    @Bean
    @ConditionalOnBean(AuditClientProperties.class)
    public AuditClient auditClient(AuditClientProperties auditClientProperties) throws AuditException {
        return new AuditClientImpl("DSSAuditClient", auditClientProperties.getHost(), auditClientProperties.getPort());
    }

    @Bean
    public SimpleMarshaller simpleMarshaller() {
        return new SimpleMarshallerImpl();
    }

    @Bean
    public FileReader fileReader() {
        return new FileReaderImpl();
    }

    @Bean
    public DocumentXmlConverter documentXmlConverter() {
        return new DocumentXmlConverterImpl();
    }

    @Bean
    public DocumentAccessor documentAccessor(FhirIncludedNamespaceContext fhirIncludedNamespaceContext) {
        final DocumentAccessorImpl documentAccessor = new DocumentAccessorImpl();
        documentAccessor.setNamespaceContext(fhirIncludedNamespaceContext);
        return documentAccessor;
    }



    @Bean
    public XmlTransformer xmlTransformer() {
        return new XmlTransformerImpl(simpleMarshaller());
    }

    @Bean
    public RestOperations restTemplate() {
        return new RestTemplate();
    }

    @Component
    static class FhirIncludedNamespaceContext extends DefaultNamespaceContext {
        @Override
        public String getNamespaceURI(String prefix) {
            return prefix.equals("fhir") ? "http://hl7.org/fhir" : super.getNamespaceURI(prefix);
        }
    }
}