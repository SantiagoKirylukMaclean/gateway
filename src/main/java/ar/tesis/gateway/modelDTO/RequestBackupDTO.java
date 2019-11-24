package ar.tesis.gateway.modelDTO;

import javax.validation.constraints.NotNull;

import lombok.Getter;


public class RequestBackupDTO {

    @Getter
    @NotNull(message = "*Please provide a backup name")
    private String id;
}
