package ar.edu.unlam.proyecto.convertidores;

import ar.edu.unlam.proyecto.clientes.mercadopago.ClienteMercadoPago;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Suspendable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ImagenBase64Deserializer extends JsonDeserializer<byte[]> {

    private final Logger logger = LoggerFactory.getLogger(ImagenBase64Deserializer.class.getSimpleName());
    @Override
    public byte[] deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        String imagenEnBase64 = p.getText();
        byte[] datos = imagenEnBase64.substring(imagenEnBase64.indexOf(",") + 1).getBytes(StandardCharsets.UTF_8);
        logger.info(imagenEnBase64);
        return Base64.getDecoder().decode(datos);
    }
}
