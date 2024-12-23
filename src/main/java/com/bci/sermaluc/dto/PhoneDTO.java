package com.bci.sermaluc.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PhoneDTO implements Serializable {

    @NotEmpty(message = "Number is required")
    private String number;

    @NotEmpty(message = "City code is required")
    private String cityCode;

    @NotEmpty(message = "Country code is required")
    private String countryCode;
}
