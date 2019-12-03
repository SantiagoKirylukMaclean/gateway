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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
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

    public ResponseEntity<?> startTransaction(RequestTransactionDTO requestTransactionDTO) {

        try {

            if (requestTransactionDTO == null
                    || requestTransactionDTO.getSellerUsername() == null || requestTransactionDTO.getMonto() == null
                    || requestTransactionDTO.getMonto() <= 0) {
                ResponseErrorDTO respuesta = new ResponseErrorDTO();
                respuesta.setCode("90");
                respuesta.setMessage("El request enviado es invalido");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
            }

            ResponseStartTransactionDTO response = new ResponseStartTransactionDTO();

            Seller seller = sellerRepository.findByUsername(requestTransactionDTO.getSellerUsername()).get();

            if (!validTransaction(seller)) {
                ResponseErrorDTO respuesta = new ResponseErrorDTO();
                respuesta.setCode("98");
                respuesta.setMessage("Vendedor no encontrado");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }

            Transaction transaction = new Transaction();
            transaction.setMailComprador(requestTransactionDTO.getMailComprador());
            transaction.setMonto(requestTransactionDTO.getMonto());
            transaction.setEstado(1);
            transaction.setCreationDateTime(Calendar.getInstance());
            Set<Transaction> transacciones = seller.getTransaction() == null ? new HashSet<>() : seller.getTransaction();
            transacciones.add(transaction);
            seller.setTransaction(transacciones);
            Seller savedSeller = sellerRepository.save(seller);

            savedSeller.getTransaction().stream().max(Comparator.comparing(Transaction::getId));


            response.setSellerUsername(requestTransactionDTO.getSellerUsername());
            //response.setMailComprador(requestTransactionDTO.getMailComprador());
            response.setMonto(requestTransactionDTO.getMonto());
            response.setTransaccionId(savedSeller.getTransaction().stream().max(Comparator.comparing(Transaction::getId)).get().getId());
            response.setDescuento(seller.getDescuento());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ResponseErrorDTO respuesta = new ResponseErrorDTO();
            respuesta.setCode("99");
            respuesta.setMessage("Ha ocurrido un error al realizar la transaccion");
            respuesta.setDetailedMessage(getStackTrace(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }


    }

    public ResponseEntity<?> applyTransaction(RequestApplyTransactionDTO requestApplyTransactionDTO) {


        try {
            Seller seller;

            if (requestApplyTransactionDTO.getSellerUsername() == null ||
                    sellerRepository.findByUsername(requestApplyTransactionDTO.getSellerUsername()).isPresent() == false){
                ResponseErrorDTO respuesta = new ResponseErrorDTO();
                respuesta.setCode("98");
                respuesta.setMessage("Vendedor no encontrado");
                respuesta.setUrl("Mal URL");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }else{
                seller = sellerRepository.findByUsername(requestApplyTransactionDTO.getSellerUsername()).get();
            }


            if (requestApplyTransactionDTO == null || requestApplyTransactionDTO.getTarjetaComprador() == null
                    || requestApplyTransactionDTO.getMonto() == null
                    || requestApplyTransactionDTO.getMonto() <= 0
                    || requestApplyTransactionDTO.getTransaccionId() == null) {
                ResponseErrorDTO respuesta = new ResponseErrorDTO();
                respuesta.setCode("90");
                respuesta.setMessage("El request enviado es invalido");
                respuesta.setUrl("Mal URL");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
            }

            if (!validTransaction(seller)) {
                ResponseErrorDTO respuesta = new ResponseErrorDTO();
                respuesta.setCode("98");
                respuesta.setMessage("Vendedor no encontrado");
                respuesta.setUrl("Mal URL");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }

            Set<Transaction> transacciones = seller.getTransaction() == null ? new HashSet<>() : seller.getTransaction();

            Transaction transaccionModificada;

            if (transacciones.stream().filter(trx -> trx.getId() == requestApplyTransactionDTO.getTransaccionId())
                    .collect(Collectors.toList()).isEmpty() == true) {
                ResponseErrorDTO respuesta = new ResponseErrorDTO();
                respuesta.setCode("93");
                respuesta.setMessage("El Id de la transaccion es incorrecto");
                respuesta.setUrl("Mal URL");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
            }else{
                transaccionModificada =
                        transacciones
                                .stream()
                                .filter(trx -> trx.getId() == requestApplyTransactionDTO.getTransaccionId())
                                .collect(Collectors.toList()).get(0);
            }

            if (transaccionModificada.getEstado() == 2) {
                ResponseErrorDTO respuesta = new ResponseErrorDTO();
                respuesta.setCode("95");
                respuesta.setMessage("La transaccion esta en estado finalizada");
                respuesta.setUrl("Mal URL");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
            }

            if (Double.compare(transaccionModificada.getMonto(), requestApplyTransactionDTO.getMonto()) != 0){
                ResponseErrorDTO respuesta = new ResponseErrorDTO();
                respuesta.setCode("94");
                respuesta.setMessage("El monto enviado es distinto al monto original");
                respuesta.setUrl("Mal URL");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
            }

            if (hayFraude(requestApplyTransactionDTO)) {
                ResponseErrorDTO respuesta = new ResponseErrorDTO();
                respuesta.setCode("91");
                respuesta.setMessage("La tarjeta enviada tiene fraude");
                respuesta.setUrl("Mal URL");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
            }

            if (!autorizar(requestApplyTransactionDTO)) {
                ResponseErrorDTO respuesta = new ResponseErrorDTO();
                respuesta.setCode("92");
                respuesta.setMessage("No se puede autorizar la tarjeta por el monto indicado");
                respuesta.setUrl("Mal URL");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
            }

            transaccionModificada.setTarjetaCredito(requestApplyTransactionDTO.getTarjetaComprador());
            transaccionModificada.setMonto(
                    transaccionModificada.getMonto() - (transaccionModificada.getMonto() * requestApplyTransactionDTO.getDescuento().getDescuento() / 100.00)
            );
            transaccionModificada.setCuotas(requestApplyTransactionDTO.getCuotas());
            transaccionModificada.setEstado(2);
            transaccionModificada.setModificationDateTime(Calendar.getInstance());

            seller.setTransaction(transacciones);
            sellerRepository.save(seller);
            ResponseOkApplyTransactionDTO responseApplyTransactionDTO = new ResponseOkApplyTransactionDTO();
            responseApplyTransactionDTO.setMensaje("Todo Ok");
            responseApplyTransactionDTO.setUrl(seller.getConfiguracion().getURLOk());
            return ResponseEntity.status(HttpStatus.OK).body(responseApplyTransactionDTO);
        } catch (Exception e) {
            ResponseErrorDTO respuesta = new ResponseErrorDTO();
            respuesta.setCode("99");
            respuesta.setMessage("Ha ocurrido un error al realizar la transaccion");
            respuesta.setDetailedMessage(getStackTrace(e));
            respuesta.setUrl("Mal URL");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }

    }

    private boolean validTransaction(Seller seller) {
        return seller != null;
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

    private String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
