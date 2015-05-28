/*
 * Tanaguru - Automated webpage assessment
 * Copyright (C) 2008-2015  Tanaguru.org
 *
 * This file is part of Tanaguru.
 *
 * Tanaguru is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact us by mail: tanaguru AT tanaguru DOT org
 */
package org.opens.tanaguru.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.opens.tanaguru.consolidator.Consolidator;
import org.opens.tanaguru.consolidator.ConsolidatorFactory;
import org.opens.tanaguru.entity.audit.ProcessResult;
import org.opens.tanaguru.entity.reference.Test;
import org.opens.tanaguru.entity.service.audit.EvidenceDataService;
import org.opens.tanaguru.entity.service.audit.EvidenceElementDataService;
import org.opens.tanaguru.entity.service.audit.ProcessRemarkDataService;
import org.opens.tanaguru.processing.ProcessRemarkServiceFactory;
import org.opens.tanaguru.ruleimplementation.RuleImplementation;

/**
 * 
 * @author jkowalczyk
 */
public class ConsolidatorServiceImpl implements ConsolidatorService {

    protected RuleImplementationLoaderService ruleImplementationLoaderService;
    private ProcessRemarkDataService processRemarkDataService;
    private EvidenceElementDataService evidenceElementDataService;
    private EvidenceDataService evidenceDataService;
    private ConsolidatorFactory consolidatorFactory;

    public ConsolidatorServiceImpl() {
        super();
    }

    @Override
    public Collection<ProcessResult> consolidate(Collection<ProcessResult> grossResultList,
            Collection<Test> testList) {
        List<ProcessResult> resultList = new ArrayList<>();
        for (Test test : testList) {
            // if the rule archive name is empty, the test is not launched
            if (!test.getNoProcess()) {
                RuleImplementation ruleImplementation = ruleImplementationLoaderService.loadRuleImplementation(test);
                Consolidator consolidator = consolidatorFactory.create(
                        grossResultList, 
                        ruleImplementation, 
                        ProcessRemarkServiceFactory.create(
                            processRemarkDataService, 
                            evidenceElementDataService, 
                            evidenceDataService));
                consolidator.run();
                resultList.addAll(consolidator.getResult());
            }
        }
        return resultList;
    }

    @Override
    public void setRuleImplementationLoaderService(RuleImplementationLoaderService ruleImplementationLoaderService) {
        this.ruleImplementationLoaderService = ruleImplementationLoaderService;
    }

    @Override
    public void setEvidenceDataService(EvidenceDataService evidenceDataService) {
        this.evidenceDataService = evidenceDataService;
    }

    @Override
    public void setEvidenceElementDataService(EvidenceElementDataService evidenceElementDataService) {
        this.evidenceElementDataService = evidenceElementDataService;
    }

    @Override
    public void setProcessRemarkDataService(ProcessRemarkDataService processRemarkDataService) {
        this.processRemarkDataService = processRemarkDataService;
    }

    @Override
    public void setConsolidatorFactory(ConsolidatorFactory consolidatorFactory) {
        this.consolidatorFactory = consolidatorFactory;
    }

}