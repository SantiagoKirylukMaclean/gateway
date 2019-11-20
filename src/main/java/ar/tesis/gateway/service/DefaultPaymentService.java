package ar.tesis.gateway.service;

import ar.tesis.gateway.model.Autorizacion;
import ar.tesis.gateway.model.Fraude;
import ar.tesis.gateway.model.Seller;
import ar.tesis.gateway.model.Transaction;
import ar.tesis.gateway.modelDTO.*;
import ar.tesis.gateway.repository.AutorizacionRepository;
import ar.tesis.gateway.repository.FraudeRepository;
import ar.tesis.gateway.repository.SellerRepository;
import ar.tesis.gateway.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service("DefaultPaymentService")
public class DefaultPaymentService implements PaymentServiceInterface {

    @Autowired
    SellerRepository sellerRepository;

	@Autowired
    FraudeRepository fraudeRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AutorizacionRepository autorizacionRepository;

    public ResponseStartTransactionDTO startTransaction (RequestTransactionDTO requestTransactionDTO){

        ResponseStartTransactionDTO respose = new ResponseStartTransactionDTO();

        if (validTransaction(requestTransactionDTO) == true){
            Seller seller = sellerRepository.findByUsername(requestTransactionDTO.getSellerUsername()).get();

            Transaction transaction = new Transaction();
            transaction.setMailComprador(requestTransactionDTO.getMailComprador());
            transaction.setMonto(requestTransactionDTO.getMonto());
            transaction.setEstado(1);
            Set<Transaction> transacciones = seller.getTransaction() == null ? new HashSet<>() : seller.getTransaction();
            transacciones.add(transaction);
            seller.setTransaction(transacciones);
            Seller savedSeller = sellerRepository.save(seller);
            long lastTransaction = savedSeller.getTransaction().stream().max(Comparator.comparing(Transaction::getId)).get().getId();
            savedSeller.getTransaction().stream().max(Comparator.comparing(Transaction::getId));


            respose.setSellerUsername(requestTransactionDTO.getSellerUsername());
            respose.setMailComprador(requestTransactionDTO.getMailComprador());
            respose.setMonto(requestTransactionDTO.getMonto());
            respose.setTransaccionId(savedSeller.getTransaction().stream().max(Comparator.comparing(Transaction::getId)).get().getId());
            respose.setDescuento(sellerRepository.findByUsername(requestTransactionDTO.getSellerUsername())
                    .get().getDescuento());
        }

        return respose;
    }

    public ResponseApplyTransactionDTO applyTransaction (RequestApplyTransactionDTO requestApplyTransactionDTO){


        if (hayFraude(requestApplyTransactionDTO) == false &&
            autorizar(requestApplyTransactionDTO) == true){
            Seller seller = sellerRepository.findByUsername(requestApplyTransactionDTO.getSellerUsername()).get();
            Set<Transaction> transacciones = seller.getTransaction() == null ? new HashSet<>() : seller.getTransaction();

            transacciones
                    .stream()
                    .filter(trx -> trx.getId() == requestApplyTransactionDTO.getTransaccionId())
                    .collect(Collectors.toList()).get(0).setTarjetaCredito(requestApplyTransactionDTO.getTarjetaComprador());

            transacciones
                    .stream()
                    .filter(trx -> trx.getId() == requestApplyTransactionDTO.getTransaccionId())
                    .collect(Collectors.toList()).get(0).setCuotas("1");
            transacciones
                    .stream()
                    .filter(trx -> trx.getId() == requestApplyTransactionDTO.getTransaccionId())
                    .collect(Collectors.toList()).get(0).setEstado(2);
;
            seller.setTransaction(transacciones);
            sellerRepository.save(seller);
            ResponseOkApplyTransactionDTO responseApplyTransactionDTO = new ResponseOkApplyTransactionDTO();
            responseApplyTransactionDTO.setMesaje("Todo Ok");
            responseApplyTransactionDTO.setUrlOk(seller.getConfiguracion().getURLOk());
            return responseApplyTransactionDTO;

        }else{
            ResponseErrorApplyTransactionDTO responseApplyTransactionDTO = new ResponseErrorApplyTransactionDTO();
            responseApplyTransactionDTO.setMesaje("Fraude o no valida");
            responseApplyTransactionDTO.setUrlNoOk("Mal URL");
            return responseApplyTransactionDTO;
        }

    }

    private boolean validTransaction (RequestTransactionDTO requestTransactionDTO){

        Optional<Seller> optional = sellerRepository.findByUsername(requestTransactionDTO.getSellerUsername());

        boolean existeUsuario = optional.isPresent();

        return existeUsuario;
    }

	private boolean hayFraude(RequestApplyTransactionDTO requestTransactionDTO) {
		Optional<Fraude> tarjetasFraudulentas = fraudeRepository.findById(requestTransactionDTO.getTarjetaComprador());

		return tarjetasFraudulentas.isPresent();
	}
	
	
	
	private boolean autorizar(RequestApplyTransactionDTO requestTransactionDTO) {
		// Si el monto de la transaccion es menor al maximo y a su vez si el monto de la transaccion
        // m�s lo que llevo acumulado es menor al m�ximo la dejo pasar
        // (el acumulado se limpia 1 vez por dia automaticamente)
		return autorizacionRepository.findById(requestTransactionDTO.getTarjetaComprador()).filter( 
				autorizacion -> autorizacion != null && 
				autorizacion.getMontoMaximo() >= requestTransactionDTO.getMonto() 
				&& autorizacion.getMontoMaximo() >= requestTransactionDTO.getMonto() + autorizacion.getMontoAcumulado()
				).isPresent();
		
		//return autorizacionRepository.findById(requestTransactionDTO.getTarjetaComprador()).filter( compararMontos(requestTransactionDTO.getMonto()) ).isPresent();
	}
	
	
	private void actualizarMontoAcumulado(RequestApplyTransactionDTO requestTransactionDTO) {
		Autorizacion autorizacion = autorizacionRepository.findById(requestTransactionDTO.getTarjetaComprador()).get();
		autorizacion.setMontoAcumulado(autorizacion.getMontoAcumulado() + requestTransactionDTO.getMonto());
		autorizacionRepository.save(autorizacion);
	}
	
	// Si en el filter queres usar esta funcion es lo mismo pero quiz�s queda mas prolijo? no se
	private Predicate<Autorizacion> compararMontos(Double montoTransaccion) {

		return autorizacion -> autorizacion != null && 
				autorizacion.getMontoMaximo() >= montoTransaccion 
				&& autorizacion.getMontoMaximo() >= montoTransaccion + autorizacion.getMontoAcumulado();
	}
}
