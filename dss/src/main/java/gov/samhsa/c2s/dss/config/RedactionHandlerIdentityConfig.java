package gov.samhsa.c2s.dss.config;

import gov.samhsa.c2s.brms.domain.ClinicalFact;
import gov.samhsa.c2s.brms.domain.FactModel;
import gov.samhsa.c2s.brms.domain.RuleExecutionContainer;
import gov.samhsa.c2s.brms.domain.XacmlResult;
import gov.samhsa.c2s.common.document.accessor.DocumentAccessor;
import gov.samhsa.c2s.dss.service.document.DocumentRedactor;
import gov.samhsa.c2s.dss.service.document.dto.RedactionHandlerResult;
import gov.samhsa.c2s.dss.service.document.redact.base.AbstractClinicalFactLevelRedactionHandler;
import gov.samhsa.c2s.dss.service.document.redact.base.AbstractDocumentLevelRedactionHandler;
import gov.samhsa.c2s.dss.service.document.redact.base.AbstractObligationLevelRedactionHandler;
import gov.samhsa.c2s.dss.service.document.redact.base.AbstractPostRedactionLevelRedactionHandler;
import gov.samhsa.c2s.dss.service.document.redact.dto.PdpObligationsComplementSetDto;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.w3c.dom.Document;

@Configuration
@ConditionalOnBean(DocumentRedactor.class)
public class RedactionHandlerIdentityConfig {

    public static final String IDENTITY = "IDENTITY";

    @Bean
    @ConditionalOnMissingBean(AbstractDocumentLevelRedactionHandler.class)
    public AbstractDocumentLevelRedactionHandler identityDocumentLevelRedactionHandler() {
        return new AbstractDocumentLevelRedactionHandler() {
            @Override
            public RedactionHandlerResult execute(Document xmlDocument, String documentType) {
                return RedactionHandlerResult.empty();
            }

            @Override
            public String toString() {
                return IDENTITY;
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(AbstractObligationLevelRedactionHandler.class)
    public AbstractObligationLevelRedactionHandler identityObligationLevelRedactionHandler(DocumentAccessor documentAccessor) {
        return new AbstractObligationLevelRedactionHandler(documentAccessor) {
            @Override
            public RedactionHandlerResult execute(Document xmlDocument, XacmlResult xacmlResult, FactModel factModel, Document factModelDocument, RuleExecutionContainer ruleExecutionContainer, String obligationValue, PdpObligationsComplementSetDto pdpObligationsComplementSetDto) {
                return RedactionHandlerResult.empty();
            }

            @Override
            public String toString() {
                return IDENTITY;
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(AbstractClinicalFactLevelRedactionHandler.class)
    public AbstractClinicalFactLevelRedactionHandler identityClinicalFactLevelRedactionHandler(DocumentAccessor documentAccessor) {
        return new AbstractClinicalFactLevelRedactionHandler(documentAccessor) {
            @Override
            public RedactionHandlerResult execute(Document xmlDocument, XacmlResult xacmlResult, FactModel factModel, Document factModelDocument, ClinicalFact fact, RuleExecutionContainer ruleExecutionContainer, PdpObligationsComplementSetDto pdpObligationsComplementSetDto) {
                return RedactionHandlerResult.empty();
            }

            @Override
            public String toString() {
                return IDENTITY;
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(AbstractPostRedactionLevelRedactionHandler.class)
    public AbstractPostRedactionLevelRedactionHandler identityPostRedactionLevelRedactionHandler(DocumentAccessor documentAccessor) {
        return new AbstractPostRedactionLevelRedactionHandler(documentAccessor) {
            @Override
            public void execute(Document xmlDocument, XacmlResult xacmlResult,
                                FactModel factModel, Document factModelDocument,
                                RuleExecutionContainer ruleExecutionContainer,
                                RedactionHandlerResult preRedactionResults,
                                PdpObligationsComplementSetDto pdpObligationsComplementSetDto,
                                String documentType) {
                // do nothing
            }

            @Override
            public String toString() {
                return IDENTITY;
            }
        };
    }
}
