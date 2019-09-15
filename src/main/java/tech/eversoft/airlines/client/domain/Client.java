package tech.eversoft.airlines.client.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Client {
    private ClientId clientId;
    @NonNull private final LocalDate dateOfBirth;
}
