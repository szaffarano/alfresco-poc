package afip.tecno.alfresco.model;

import org.alfresco.service.namespace.QName;

public class TecnoModel {
	public static final String NAMESPACE_TECNO = "http://tecno.afip.gob.ar/model/content/1.0";

	public static final QName TYPE_TECNO_FORM = QName.createQName(NAMESPACE_TECNO, "form1900");

	// Aspects
	public static final QName ASPECT_TECNO_INCUMPLIDO = QName.createQName(NAMESPACE_TECNO, "incumplido");

	// Properties
	public static final QName PROP_AREA_RESPONSABLE = QName.createQName(NAMESPACE_TECNO, "areaResponsable");
	public static final QName PROP_DIRECCION = QName.createQName(NAMESPACE_TECNO, "direccion");
	public static final QName PROP_DOMICILIO = QName.createQName(NAMESPACE_TECNO, "domicilio");
	public static final QName PROP_EXPEDIENTE = QName.createQName(NAMESPACE_TECNO, "expediente");
	public static final QName PROP_ORDEN_COMPRA = QName.createQName(NAMESPACE_TECNO, "ordenCompra");
	public static final QName PROP_FECHA_INICION = QName.createQName(NAMESPACE_TECNO, "fechaInicio");
	public static final QName PROP_FECHA_VENCIMIENTO = QName.createQName(NAMESPACE_TECNO, "fechaVencimiento");
	public static final QName PROP_OBJETO_SERVICIO = QName.createQName(NAMESPACE_TECNO, "objetoServicio");
	public static final QName PROP_ITEMS = QName.createQName(NAMESPACE_TECNO, "items");
	public static final QName PROP_ENABLED = QName.createQName(NAMESPACE_TECNO, "enabled");
	public static final QName PROP_WORKFLOW_FINISHESD = QName.createQName(NAMESPACE_TECNO, "workflowFinished");

	// Associations
	public static final String ASSN_ADJUNTOS = "adjuntos";

	public static final QName qnameFor(String id) {
		return QName.createQName(TecnoModel.NAMESPACE_TECNO, id);
	}
}
