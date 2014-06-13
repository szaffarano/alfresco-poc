package afip.tecno.alfresco.model;

import static org.alfresco.service.namespace.QName.createQName;
import org.alfresco.service.namespace.QName;

public class TecnoModel {
	public static final String NAMESPACE_TECNO = "http://tecno.afip.gob.ar/model/content/1.0";
	public static final String NAMESPACE_TECNOWF = "http://tecno.afip.gob.ar/model/workflow/1.0";

	public static final QName TYPE_TECNO_FORM1900 = createQName(NAMESPACE_TECNO, "form1900");

	// Aspectos
	public static final QName ASPECT_TECNO_INCUMPLIDO = createQName(NAMESPACE_TECNO, "incumplido");
	public static final QName ASPECT_TECNO_CONFORMIDAD_FINALIZADA = createQName(NAMESPACE_TECNO,
			"conformidadFinalizada");
	public static final QName ASPECT_TECNO_FORM1900_DATA = createQName(NAMESPACE_TECNO, "form1900Data");

	// Properties
	public static final QName PROP_AREA_RESPONSABLE = createQName(NAMESPACE_TECNO, "areaResponsable");
	public static final QName PROP_DIRECCION = createQName(NAMESPACE_TECNO, "direccion");
	public static final QName PROP_DOMICILIO = createQName(NAMESPACE_TECNO, "domicilio");
	public static final QName PROP_EXPEDIENTE = createQName(NAMESPACE_TECNO, "expediente");
	public static final QName PROP_ORDEN_COMPRA = createQName(NAMESPACE_TECNO, "ordenCompra");
	public static final QName PROP_FECHA_INICIO = createQName(NAMESPACE_TECNO, "fechaInicio");
	public static final QName PROP_FECHA_VENCIMIENTO = createQName(NAMESPACE_TECNO, "fechaVencimiento");
	public static final QName PROP_OBJETO_SERVICIO = createQName(NAMESPACE_TECNO, "objetoServicio");

	public static final QName PROP_TIPO_SERVICIO = createQName(NAMESPACE_TECNO, "tipoServicio");
	public static final QName PROP_OTRO_TIPO_SERVICIO = createQName(NAMESPACE_TECNO, "otroTipoServicio");
	public static final QName PROP_PERIODICIDAD = createQName(NAMESPACE_TECNO, "periodicidad");
	public static final QName PROP_PERIODO_DESDE = createQName(NAMESPACE_TECNO, "periodoDesde");
	public static final QName PROP_PERIODO_HASTA = createQName(NAMESPACE_TECNO, "periodoHasta");
	public static final QName PROP_PROVEEDOR = createQName(NAMESPACE_TECNO, "proveedor");
	public static final QName PROP_CUIT_PROVEEDOR = createQName(NAMESPACE_TECNO, "cuitProveedor");
	public static final QName PROP_REMITO_NUMERO = createQName(NAMESPACE_TECNO, "remitoNumero");
	public static final QName PROP_FACTURA_NUMERO = createQName(NAMESPACE_TECNO, "facturaNumero");
	public static final QName PROP_REGIMEN_CONTRATACION = createQName(NAMESPACE_TECNO, "regimenContratacion");
	public static final QName PROP_NOTA_CREDITO = createQName(NAMESPACE_TECNO, "notaCredito");
	public static final QName PROP_NOTA_DEBITO = createQName(NAMESPACE_TECNO, "notaDebito");
	public static final QName PROP_ITEMS = createQName(NAMESPACE_TECNO, "items");

	// aspecto incumplido
	public static final QName PROP_NUMERO = createQName(NAMESPACE_TECNO, "numero");
	public static final QName PROP_MOTIVO = createQName(NAMESPACE_TECNO, "motivo");
	public static final QName PROP_IMPORTE = createQName(NAMESPACE_TECNO, "importe");

	public static final QName PROP_CONFORME = createQName(NAMESPACE_TECNOWF, "conforme");
	public static final QName PROP_APROBADO = createQName(NAMESPACE_TECNOWF, "aprobado");

	// Associations
	public static final String ASSN_ADJUNTOS = "adjuntos";
}
