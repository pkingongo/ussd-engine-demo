package com.example.ussd.handler;

import com.example.ussd.model.Beneficiary;
import com.example.ussd.model.UssdResult;
import com.example.ussd.model.UssdSession;
import com.example.ussd.service.BeneficiaryService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ListBeneficiariesHandler implements StateHandler {

    private final BeneficiaryService beneficiaryService;

    public ListBeneficiariesHandler(BeneficiaryService beneficiaryService) {
        this.beneficiaryService = beneficiaryService;
    }

    @Override
    public String getState() {
        return "LIST_BENEFICIARIES";
    }

    @Override
    public UssdResult handle(UssdSession session, String input) {
        List<Beneficiary> beneficiaries = beneficiaryService.getBeneficiariesForCustomer(session.getMsisdn());
        if (beneficiaries.isEmpty()) {
            return new UssdResult("END", "No beneficiaries found.", false);
        }

        List<Map<String, String>> fullData = beneficiaries.stream()
                .map(b -> Map.of("id", b.id(), "label", b.name()))
                .collect(Collectors.toList());

        session.setDataType("BENEFICIARY");
        session.setFullData(fullData);
        session.setTotalItems(fullData.size());
        session.setPage(1);
        session.getContext().put("dataType", "BENEFICIARY");
        session.getContext().put("selectionContext", "BENEFICIARY_" + session.getContext().get("nextAction"));

        return new UssdResult("LIST", "", true);
    }
}
