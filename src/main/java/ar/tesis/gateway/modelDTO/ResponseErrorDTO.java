package ar.tesis.gateway.modelDTO;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ResponseErrorDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String code;
	private String message;
	private String detailedMessage;
	private String url;

}
