package org.example.springdemo4.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {

    @NotNull(message = "fromAccountId can't null")
    private Long fromAccountId;

    @NotNull(message = "toAccountId can't null")
    private Long toAccountId;

    @NotNull(message = "amount can't null")
    @DecimalMin(value = "0.01", message = "amount great than 0")
    private BigDecimal amount;
}