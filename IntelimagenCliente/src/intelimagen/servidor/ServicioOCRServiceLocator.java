/**
 * ServicioOCRServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package intelimagen.servidor;

@SuppressWarnings("serial")
public class ServicioOCRServiceLocator extends org.apache.axis.client.Service
		implements intelimagen.servidor.ServicioOCRService {

	@SuppressWarnings("rawtypes")
	private java.util.HashSet ports = null;

	// Ruta del ServicioOCR
	private java.lang.String ServicioOCR_address = "http://localhost:8080/IntelimagenServidor/services/ServicioOCR";

	// Puerto por defecto del servicio
	private java.lang.String ServicioOCRWSDDServiceName = "ServicioOCR";

	/**
	 * Constructor de la clase
	 */
	public ServicioOCRServiceLocator() {
	}

	/**
	 * Constructor de la clase
	 * 
	 * @param wsdlLoc
	 *            Ruta del servicio OCR
	 * @param sName
	 *            Definición del servicio OCR
	 * @throws javax.xml.rpc.ServiceException
	 */
	public ServicioOCRServiceLocator(java.lang.String wsdlLoc,
			javax.xml.namespace.QName sName)
			throws javax.xml.rpc.ServiceException {
		super(wsdlLoc, sName);
	}

	/**
	 * Constructor de la clase
	 * 
	 * @param config
	 *            Configuración del servicio OCR
	 */
	public ServicioOCRServiceLocator(org.apache.axis.EngineConfiguration config) {
		super(config);
	}

	/**
	 * Obtiene la definición del servicio
	 */
	@SuppressWarnings("rawtypes")
	public java.rmi.Remote getPort(Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		try {
			if (intelimagen.servidor.ServicioOCR.class
					.isAssignableFrom(serviceEndpointInterface)) {
				intelimagen.servidor.ServicioOCRSoapBindingStub _stub = new intelimagen.servidor.ServicioOCRSoapBindingStub(
						new java.net.URL(ServicioOCR_address), this);
				_stub.setPortName(getServicioOCRWSDDServiceName());
				return _stub;
			}
		} catch (java.lang.Throwable t) {
			throw new javax.xml.rpc.ServiceException(t);
		}
		throw new javax.xml.rpc.ServiceException(
				"There is no stub implementation for the interface:  "
						+ (serviceEndpointInterface == null ? "null"
								: serviceEndpointInterface.getName()));
	}

	/**
	 * Obtiene la definición del servicio
	 * 
	 * @param portName
	 *            Definicion del servicio OCR
	 */
	@SuppressWarnings("rawtypes")
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName,
			Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		if (portName == null) {
			return getPort(serviceEndpointInterface);
		}
		java.lang.String inputPortName = portName.getLocalPart();
		if ("ServicioOCR".equals(inputPortName)) {
			return getServicioOCR();
		} else {
			java.rmi.Remote _stub = getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	/**
	 * Obtiene la definición del servicio
	 * 
	 * @return Puertos Servicio OCR
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public java.util.Iterator getPorts() {
		if (ports == null) {
			ports = new java.util.HashSet();
			ports.add(new javax.xml.namespace.QName(
					"http://servidor.intelimagen", "ServicioOCR"));
		}
		return ports.iterator();
	}

	/**
	 * Obtiene el nombre de la definición del servicio
	 */
	public javax.xml.namespace.QName getServiceName() {
		return new javax.xml.namespace.QName("http://servidor.intelimagen",
				"ServicioOCRService");
	}

	/**
	 * Obtiene una instancia del servicio para realizar OCR
	 * 
	 * @return Servicio OCR
	 * @throws javax.xml.rpc.ServiceException
	 */
	public intelimagen.servidor.ServicioOCR getServicioOCR()
			throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(ServicioOCR_address);
		} catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getServicioOCR(endpoint);
	}

	/**
	 * Obtiene una instancia del servicio para realizar OCR
	 * 
	 * @param portAddress
	 *            Ruta del servicio OCR
	 * @return Servicio OCR
	 * @throws javax.xml.rpc.ServiceException
	 */

	public intelimagen.servidor.ServicioOCR getServicioOCR(
			java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
		try {
			intelimagen.servidor.ServicioOCRSoapBindingStub _stub = new intelimagen.servidor.ServicioOCRSoapBindingStub(
					portAddress, this);
			_stub.setPortName(getServicioOCRWSDDServiceName());
			return _stub;
		} catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}

	/**
	 * Obtiene la ruta del servicio para realizar OCR
	 * 
	 * @return Ruta del servicio OCR
	 */
	public java.lang.String getServicioOCRAddress() {
		return ServicioOCR_address;
	}

	/**
	 * Obtiene el nombre del servicio para realizar OCR
	 * 
	 * @return Ruta del servicio OCR
	 */
	public java.lang.String getServicioOCRWSDDServiceName() {
		return ServicioOCRWSDDServiceName;
	}

	/**
	 * Coloca la ruta del servicio para realizar OCR
	 * 
	 * @param portName
	 *            Definicion del servicio OCR
	 * @param address
	 *            Ruta del servicio OCR
	 * @throws javax.xml.rpc.ServiceException
	 */
	public void setEndpointAddress(java.lang.String portName,
			java.lang.String address) throws javax.xml.rpc.ServiceException {

		if ("ServicioOCR".equals(portName)) {
			setServicioOCREndpointAddress(address);
		} else { // Unknown Port Name
			throw new javax.xml.rpc.ServiceException(
					" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

	/**
	 * Coloca la ruta del servicio para realizar OCR
	 * 
	 * @param portName
	 *            Definicion del servicio OCR
	 * @param address
	 *            Ruta del servicio OCR
	 * @throws javax.xml.rpc.ServiceException
	 */
	public void setEndpointAddress(javax.xml.namespace.QName portName,
			java.lang.String address) throws javax.xml.rpc.ServiceException {
		setEndpointAddress(portName.getLocalPart(), address);
	}

	/**
	 * Coloca la ruta del servicio para realizar OCR
	 * 
	 * @param address
	 *            Ruta del servicio OCR
	 */
	public void setServicioOCREndpointAddress(java.lang.String address) {
		ServicioOCR_address = address;
	}

	/**
	 * Coloca la ruta del servicio para realizar OCR
	 * 
	 * @param name
	 *            Nombre del servicio OCR
	 */
	public void setServicioOCRWSDDServiceName(java.lang.String name) {
		ServicioOCRWSDDServiceName = name;
	}

}
