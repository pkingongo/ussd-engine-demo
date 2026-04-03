package com.example.ussd.service;

import com.example.ussd.model.Beneficiary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeneficiaryService {

    public List<Beneficiary> getBeneficiariesForCustomer(String msisdn) {
        return List.of(
                new Beneficiary("BEN001", "John"),
                new Beneficiary("BEN002", "Jane"),
                new Beneficiary("BEN003", "Alice"),
                new Beneficiary("BEN004", "Bob"),
                new Beneficiary("BEN005", "Charles"),
                new Beneficiary("BEN006", "Diana"),
                new Beneficiary("BEN007", "Eve"),
                new Beneficiary("BEN008", "Frank"),
                new Beneficiary("BEN009", "Grace"),
                new Beneficiary("BEN010", "Henry")
        );
    }

    public Beneficiary getBeneficiary(String beneficiaryId) {
        return getBeneficiariesForCustomer("ignored").stream()
                .filter(b -> b.id().equals(beneficiaryId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown beneficiary " + beneficiaryId));
    }
}
